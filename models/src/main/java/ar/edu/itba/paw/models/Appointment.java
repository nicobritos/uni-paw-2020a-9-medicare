package ar.edu.itba.paw.models;

import ar.edu.itba.paw.persistenceAnnotations.Column;
import ar.edu.itba.paw.persistenceAnnotations.ManyToOne;
import ar.edu.itba.paw.persistenceAnnotations.Table;

import java.util.Date;

@Table(name = "appointment", primaryKey = "appointment_id")
public class Appointment extends GenericModel<Appointment, Integer> {
    public static final int DURATION = 15;

    @Column(name = "status", required = true)
    private String appointmentStatus;
    @ManyToOne(name = "patient_id", required = true)
    private Patient patient;
    @ManyToOne(name = "staff_id", required = true)
    private Staff staff;
    @Column(name = "from_date", required = true)
    private Date fromDate;
    // TODO: Remove to date

    public String getAppointmentStatus() {
        return this.appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate(){
        Date date = fromDate;
        date.setMinutes(fromDate.getMinutes() + 15);
        return date;
    }

    @Override
    protected boolean isSameInstance(Object o) {
        return o instanceof Appointment;
    }
}
