package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MediCareException;
import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.interfaces.services.exceptions.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.generics.GenericServiceImpl;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentServiceImpl extends GenericServiceImpl<AppointmentDao, Appointment, Integer> implements AppointmentService {
    @Autowired
    private AppointmentDao appointmentRepository;
    @Autowired
    private WorkdayService workdayService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private OfficeService officeService;

    @Override
    public List<Appointment> findAllAppointments(Doctor doctor) {
        return this.appointmentRepository.find(doctor);
    }

    @Override
    public List<Appointment> findAllAppointmentsOfDoctors(Collection<Doctor> doctors) {
        return this.appointmentRepository.findByDoctors(doctors);
    }

    @Override
    public List<Appointment> findAllAppointments(Patient patient) {
        return this.appointmentRepository.find(patient);
    }

    @Override
    public List<Appointment> findAllAppointmentsOfPatients(Collection<Patient> patients) {
        return this.appointmentRepository.findByPatients(patients);
    }

    @Override
    public List<Appointment> findTodayAppointments(Collection<Doctor> doctors) {
        return this.findAppointmentsOfDoctorsFromDate(doctors, LocalDateTime.now());
    }

    @Override
    public List<Appointment> findTodayAppointments(Patient patient) {
        return this.appointmentRepository.findByDate(patient, LocalDateTime.now());
    }

    @Override
    public List<Appointment> findByDate(Doctor doctor, LocalDateTime date) {
        return this.appointmentRepository.findByDoctorsAndDate(Collections.singletonList(doctor), date);
    }

    @Override
    public List<Appointment> findAppointmentsOfDoctorsFromDate(Collection<Doctor> doctors, LocalDateTime date) {
        return this.appointmentRepository.findByDoctorsAndDate(doctors, date);
    }

    @Override
    public List<Appointment> findAppointmentsOfDoctorsInDateInterval(Collection<Doctor> doctors, LocalDateTime from, LocalDateTime to) {
        if (from.isBefore(LocalDateTime.now())) {
            from = LocalDateTime.now();
        }

        return this.appointmentRepository.findByDoctorsAndDate(doctors, from, to);
    }

    @Override
    public List<Appointment> findAppointmentsOfPatientsInDateInterval(Collection<Patient> patients, LocalDateTime from, LocalDateTime to) {
        return this.appointmentRepository.findByPatientsAndDate(patients, from, to);
    }

    @Override
    public void setStatus(Appointment appointment, AppointmentStatus status) throws
            AppointmentAlreadyCancelledException,
            InvalidAppointmentStatusChangeException,
            AppointmentAlreadyCompletedException {
        if (appointment.getAppointmentStatus().equals(status))
            return;

        if (appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELLED)) {
            throw new AppointmentAlreadyCancelledException();
        } else if (appointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETE)) {
            throw new AppointmentAlreadyCompletedException();
        } else if (!this.isValidStatusChange(appointment.getAppointmentStatus(), status)) {
            throw new InvalidAppointmentStatusChangeException(appointment.getAppointmentStatus().name(), status.name());
        }

        appointment.setAppointmentStatus(status);
        this.appointmentRepository.update(appointment);
    }

    @Override
    public Appointment create(Appointment model) throws InvalidAppointmentDateException {
        if (model.getFromDate().getMinuteOfHour() % 15 != 0)
            throw new InvalidMinutesException();
        if (!this.isValidDate(model.getDoctor(), model.getFromDate()))
            throw new InvalidAppointmentDateException();

        model.setAppointmentStatus(AppointmentStatus.PENDING);

        List<Appointment> appointments = this.findByDate(model.getDoctor(), model.getFromDate());
        for (Appointment appointment : appointments) {
            if (model.getFromDate().isAfter(appointment.getFromDate()) && model.getFromDate().isBefore(appointment.getToDate())
                    || (model.getToDate().isAfter(appointment.getFromDate()) && model.getToDate().isBefore(appointment.getToDate()))) {
                throw new MediCareException("Workday date overlaps with an existing one");
            }
        }
        return this.appointmentRepository.create(model);
    }

    @Override
    public List<AppointmentTimeSlot> findAvailableTimeslotsInDateInterval(Doctor doctor, LocalDateTime fromDate, LocalDateTime toDate) {
        LocalDateTime now = LocalDateTime.now();
        List<AppointmentTimeSlot> appointmentTimeSlots = new LinkedList<>();
        if (now.isAfter(fromDate)) {
            fromDate = now;
        }
        int hour = fromDate.getHourOfDay();
        int minute = fromDate.getMinuteOfHour();
        minute = (int) Math.ceil((double) minute / Appointment.DURATION) * Appointment.DURATION;
        if (minute == 60) {
            hour += 1;
            minute = 0;
            if (hour == 24) {
                fromDate.plusDays(1);
                hour = 0;
            }
        }
        fromDate = fromDate.withTime(hour, minute, 0, 0);
        while (toDate.isAfter(fromDate)) {
            WorkdayDay workdayDay = WorkdayDay.from(fromDate);
            List<Workday> workdays = this.workdayService.findByDoctor(doctor, workdayDay); // Los horarios de ese día
            for (Workday workday : workdays) {
                int startHour = workday.getStartHour();
                int firstStartMinute = workday.getStartMinute();
                if (fromDate.getHourOfDay() > workday.getStartHour() || (fromDate.getHourOfDay() == workday.getStartHour() && fromDate.getMinuteOfHour() > workday.getStartMinute())) {
                    startHour = fromDate.getHourOfDay();
                    firstStartMinute = fromDate.getMinuteOfHour();
                }
                firstStartMinute = (int) Math.ceil((double) firstStartMinute / Appointment.DURATION) * Appointment.DURATION;
                if (firstStartMinute == 60) {
                    firstStartMinute = 0;
                    startHour++;
                }
                for (int ihour = startHour; ihour <= workday.getEndHour(); ihour++) {

                    int startMinute = 0;
                    if (ihour == startHour) {
                        startMinute = firstStartMinute;
                    }
                    int endMinute = 60;
                    if (ihour == workday.getEndHour()) {
                        endMinute = workday.getEndMinute();
                    }
                    for (int imin = startMinute; imin < endMinute; imin += Appointment.DURATION) {
                        AppointmentTimeSlot appointmentTimeSlot = new AppointmentTimeSlot();
                        appointmentTimeSlot.setDate(new LocalDateTime(
                                fromDate.getYear(),
                                fromDate.getMonthOfYear(),
                                fromDate.getDayOfMonth(),
                                ihour,
                                imin
                        ));
                        appointmentTimeSlots.add(appointmentTimeSlot);
                    }
                }

                this.findByDate(doctor, fromDate).forEach(appointment -> {
                    AppointmentTimeSlot appointmentTimeSlot = new AppointmentTimeSlot();
                    appointmentTimeSlot.setDate(appointment.getFromDate());
                    appointmentTimeSlots.remove(appointmentTimeSlot);
                });
            }
            fromDate = fromDate.plusDays(1);
            fromDate = fromDate.withTime(0, 0, 0, 0);
        }
        return appointmentTimeSlots;
    }

    @Override
    public List<List<AppointmentTimeSlot>> findTimeslotsSortedByWeekday(Doctor doctor, LocalDateTime from, LocalDateTime to) {
        List<AppointmentTimeSlot> timeSlots = findAvailableTimeslotsInDateInterval(doctor, from, to);
        List<List<AppointmentTimeSlot>> weekslots = new LinkedList<>();
        for (int i = 0; i <= 7; i++) {
            weekslots.add(new LinkedList<>());
        }
        for (AppointmentTimeSlot timeSlot : timeSlots) {
            if (timeSlot.getDate().getDayOfWeek() < 1 && timeSlot.getDate().getDayOfWeek() > 7) {
                weekslots.get(0).add(timeSlot);
            } else {
                weekslots.get(timeSlot.getDate().getDayOfWeek()).add(timeSlot);
            }
        }
        return weekslots;
    }

    @Override
    public List<AppointmentTimeSlot> findAvailableTimeslotsfromDate(Doctor doctor, LocalDateTime date) {
        return this.findAvailableTimeslotsInDateInterval(doctor, date, date.withTime(23, 59, 59, 999));
    }

    @Override
    public List<Appointment> cancelAppointments(Workday workday) {
        List<Appointment> cancelled = new LinkedList<>();
        List<Appointment> appointments = findByWorkday(workday);
        //check if user is allowed to cancel
        for (Appointment a : appointments) {
            if (workday.getDoctor().equals(a.getDoctor())) {
                remove(a.getId());
                cancelled.add(a);
            }
        }
        return cancelled;
    }

    @Override
    public List<Appointment> findByWorkday(Workday workday) {
        return this.appointmentRepository.findByWorkday(workday);
    }

    @Override
    public Map<Workday, Integer> appointmentQtyByWorkdayOfUser(User user) {
        Map<Workday, Integer> appointmentMap = new HashMap<>();
        List<Workday> workdays = this.workdayService.findByUser(user);
        for (Workday workday : workdays) {
            List<Appointment> appointments = findByWorkday(workday);
            List<Appointment> myAppts = new LinkedList<>();
            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().getUser().equals(user)) {
                    myAppts.add(appointment);
                }
            }
            appointmentMap.put(workday, myAppts.size());
        }
        return appointmentMap;
    }

    @Override
    public void remove(Integer id, User user) {
        Optional<Appointment> appointment = findById(id);
        if (appointment.isPresent()) {
            //get doctor for current user
            List<Doctor> doctors = this.doctorService.findByUser(user); // TODO: add doctor list inside User model
            //get patient for current user
            List<Patient> patient = this.patientService.findByUser(user);
            //check if user is allowed to cancel
            boolean isAllowed = false;
            for (Patient p : patient) {
                if (p.equals(appointment.get().getPatient())) {
                    isAllowed = true;
                    break;
                }
            }
            //check if user is allowed to cancel
            for (Doctor s : doctors) {
                if (s.equals(appointment.get().getDoctor())) {
                    isAllowed = true;
                    break;
                }
            }
            if (isAllowed) {
                super.remove(id);
            }
        }
    }

    @Override
    protected AppointmentDao getRepository() {
        return this.appointmentRepository;
    }

    private boolean isValidStatusChange(AppointmentStatus appointmentStatus, AppointmentStatus newStatus) {
        if (appointmentStatus.equals(AppointmentStatus.PENDING)) {
            return newStatus.equals(AppointmentStatus.WAITING) || newStatus.equals(AppointmentStatus.CANCELLED);
        } else if (appointmentStatus.equals(AppointmentStatus.WAITING)) {
            return newStatus.equals(AppointmentStatus.SEEN) || newStatus.equals(AppointmentStatus.CANCELLED);
        } else if (appointmentStatus.equals(AppointmentStatus.SEEN)) {
            return newStatus.equals(AppointmentStatus.COMPLETE);
        }
        return false;
    }

    private boolean isValidDate(Doctor doctor, LocalDateTime fromDate) {
        AppointmentTimeSlot appointmentTimeSlot = new AppointmentTimeSlot();
        appointmentTimeSlot.setDate(fromDate);
        if (!this.workdayService.doctorWorks(doctor, appointmentTimeSlot))
            return false;
        return this.findAvailableTimeslotsfromDate(doctor, fromDate).contains(appointmentTimeSlot);
    }
}
