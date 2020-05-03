package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.services.exceptions.AppointmentAlreadyCancelledException;
import ar.edu.itba.paw.interfaces.services.exceptions.AppointmentAlreadyCompletedException;
import ar.edu.itba.paw.interfaces.services.exceptions.InvalidAppointmentStatusChangeException;
import ar.edu.itba.paw.interfaces.services.generic.GenericService;
import ar.edu.itba.paw.models.*;
import org.joda.time.DateTime;

import java.util.List;

public interface AppointmentService extends GenericService<Appointment, Integer> {
    List<Appointment> find(Staff staff);

    List<Appointment> find(List<Staff> staffs);

    List<Appointment> find(Patient patient);

    List<Appointment> findToday(List<Staff> staff);

    List<Appointment> findToday(Patient patient);

    List<Appointment> findByDay(Staff staff, DateTime date);

    List<Appointment> findByDay(List<Staff> staff, DateTime date);

    void setStatus(Appointment appointment, AppointmentStatus status) throws
            AppointmentAlreadyCancelledException,
            InvalidAppointmentStatusChangeException,
            AppointmentAlreadyCompletedException;

    List<AppointmentTimeSlot> findAvailableTimeslots(Staff staff, DateTime fromDate, DateTime toDate);

    List<AppointmentTimeSlot> findAvailableTimeslots(Staff staff, DateTime date);
}
