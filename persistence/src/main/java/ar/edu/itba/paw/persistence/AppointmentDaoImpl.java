package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.generics.GenericDaoImpl;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AppointmentDaoImpl extends GenericDaoImpl<Appointment, Integer> implements AppointmentDao {
    public AppointmentDaoImpl() {
        super(Appointment.class, Appointment_.id);
    }

    @Override
    public List<Appointment> find(Patient patient) {
        return this.findBy(Appointment_.patient, patient);
    }

    @Override
    public List<Appointment> findByPatients(List<Patient> patients) {
        return this.findByIn(Appointment_.patient, patients);
    }

    @Override
    public List<Appointment> find(Staff staff) {
        return this.findBy(Appointment_.staff, staff);
    }

    @Override
    public List<Appointment> findByStaffs(List<Staff> staffs) {
        return this.findByIn(Appointment_.staff, staffs);
    }

    @Override
    public List<Appointment> findPending(Patient patient) {
        if (patient == null)
            throw new IllegalArgumentException();

        Map<SingularAttribute<? super Appointment, ?>, Object> parameters = new HashMap<>();
        parameters.put(Appointment_.patient, patient);
        parameters.put(Appointment_.appointmentStatus, AppointmentStatus.PENDING);
        return this.findBy(parameters);
    }

    @Override
    public List<Appointment> findPending(Staff staff) {
        if (staff == null)
            throw new IllegalArgumentException();

        Map<SingularAttribute<? super Appointment, ?>, Object> parameters = new HashMap<>();
        parameters.put(Appointment_.staff, staff);
        parameters.put(Appointment_.appointmentStatus, AppointmentStatus.PENDING);
        return this.findBy(parameters);
    }

    @Override
    public List<Appointment> findPending(Patient patient, Staff staff) {
        if (patient == null || staff == null)
            throw new IllegalArgumentException();

        Map<SingularAttribute<? super Appointment, ?>, Object> parameters = new HashMap<>();
        parameters.put(Appointment_.patient, patient);
        parameters.put(Appointment_.staff, staff);
        parameters.put(Appointment_.appointmentStatus, AppointmentStatus.PENDING);
        return this.findBy(parameters);
    }

    @Override
    public List<Appointment> findByStaffsAndDate(Collection<Staff> staffs, LocalDateTime date) {
        if (date == null || staffs == null)
            throw new IllegalArgumentException();
        if (staffs.isEmpty())
            return Collections.emptyList();

        LocalDateTime fromDate = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0);
        LocalDateTime toDate = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59);
        return this.findByStaffsAndDate(staffs, fromDate, toDate);
    }

    @Override
    public List<Appointment> findByStaffsAndDate(Collection<Staff> staffs, LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate == null || toDate == null || staffs == null)
            throw new IllegalArgumentException();
        if (staffs.isEmpty())
            return Collections.emptyList();

        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        query.select(root);
        Path<?> expression = root.get(Appointment_.staff);
        Predicate predicate = expression.in(staffs);
        query.where(builder.and(
                predicate,
                builder.between(
                        root.get(Appointment_.fromDate),
                        fromDate,
                        toDate
                )
        ));

        return this.selectQuery(builder, query, root);
    }

    @Override
    public List<Appointment> findByPatientsAndDate(Collection<Patient> patients, LocalDateTime date) {
        if (date == null || patients == null)
            throw new IllegalArgumentException();
        if (patients.isEmpty())
            return Collections.emptyList();

        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        LocalDateTime fromDate = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0);
        LocalDateTime toDate = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59);

        query.select(root);
        Path<?> expression = root.get(Appointment_.patient);
        Predicate predicate = expression.in(patients);
        query.where(builder.and(
                predicate,
                builder.between(
                        root.get(Appointment_.fromDate),
                        fromDate,
                        toDate
                )
        ));

        return this.selectQuery(builder, query, root);
    }

    @Override
    public List<Appointment> findByDate(Patient patient, LocalDateTime date) {
        if (date == null || patient == null)
            throw new IllegalArgumentException();

        List<Patient> patients = new LinkedList<>();
        patients.add(patient);
        return this.findByPatientsAndDate(patients, date);
    }

    @Override
    public List<Appointment> findByPatientsFromDate(Collection<Patient> patients, LocalDateTime from) {
        if (from == null || patients == null)
            throw new IllegalArgumentException();
        if (patients.isEmpty())
            return Collections.emptyList();

        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        query.select(root);
        Path<?> expression = root.get(Appointment_.patient);
        Predicate predicate = expression.in(patients);
        query.where(builder.and(
                predicate,
                builder.greaterThanOrEqualTo(root.get(Appointment_.fromDate), from)
        ));

        return this.selectQuery(builder, query, root);
    }

    @Override
    public List<Appointment> findByWorkday(Workday workday) {
        if (workday == null || workday.getStaff() == null || workday.getDay() == null)
            throw new IllegalArgumentException();

        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fromDate = new LocalDateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0).plusDays(1);

        query.select(root);
        query.where(builder.and(
                builder.equal(root.get(Appointment_.staff), workday.getStaff()),
                builder.equal(root.get(Appointment_.appointmentStatus), AppointmentStatus.PENDING),
                builder.equal(
                        builder.function("DATE_PART", Integer.class, builder.literal("isodow"), root.get(Appointment_.fromDate)),
                        workday.getDay().toInteger()
                ),
                builder.and(
                        builder.or(
                                builder.and(
                                        builder.equal(
                                                builder.function("HOUR", Integer.class, root.get(Appointment_.fromDate)),
                                                workday.getStartHour()
                                        ),
                                        builder.greaterThanOrEqualTo(
                                                builder.function("MINUTE", Integer.class, root.get(Appointment_.fromDate)),
                                                workday.getStartMinute()
                                        )
                                ),
                                builder.greaterThan(
                                        builder.function("HOUR", Integer.class, root.get(Appointment_.fromDate)),
                                        workday.getStartHour()
                                )
                        ),
                        builder.or(
                                builder.and(
                                        builder.equal(
                                                builder.function("HOUR", Integer.class, root.get(Appointment_.fromDate)),
                                                workday.getEndHour()
                                        ),
                                        builder.lessThanOrEqualTo(
                                                builder.function("MINUTE", Integer.class, root.get(Appointment_.fromDate)),
                                                workday.getEndMinute()
                                        )
                                ),
                                builder.lessThan(
                                        builder.function("HOUR", Integer.class, root.get(Appointment_.fromDate)),
                                        workday.getEndHour()
                                )
                        )
                )
        ));
        return this.selectQuery(builder, query, root);
    }

    @Transactional
    @Override
    public void cancelAppointments(Collection<Appointment> appointments) {
        if (appointments == null)
            throw new IllegalArgumentException();
        if (appointments.isEmpty())
            return;

        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Appointment> query = builder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        query.set(root.get(Appointment_.appointmentStatus), AppointmentStatus.CANCELLED);
        Path<?> expression = root.get(Appointment_.id);
        Predicate predicate = expression.in(appointments.stream().map(GenericModel::getId).collect(Collectors.toList()));
        query.where(predicate);

        this.executeUpdate(query);
    }

    @Override
    protected void insertOrderBy(CriteriaBuilder builder, CriteriaQuery<Appointment> query, Root<Appointment> root) {
        query.orderBy(builder.asc(root.get(Appointment_.fromDate)));
    }
}
