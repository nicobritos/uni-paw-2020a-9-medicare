package ar.edu.itba.paw.models;

import ar.edu.itba.paw.persistenceAnnotations.*;

import java.util.Collection;
import java.util.LinkedList;

@Table(name = "staff", primaryKey = "staff_id")
public class Staff extends GenericModel<Staff, Integer> {
    @Column(name = "first_name", required = true)
    private String firstName;
    @Column(name = "surname", required = true)
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "registration_number")
    private Integer registrationNumber;
    @ManyToOne(name = "user_id", inverse = true)
    private User user;
    @ManyToMany(name = "staff_id", otherName = "specialty_id", tableName = "system_staff_specialty_staff", className = StaffSpecialty.class)
    private Collection<StaffSpecialty> staffSpecialties = new LinkedList<>();
    @ManyToOne(name = "office_id", inverse = true)
    private Office office;
    @OneToMany(name = "staff_id", className = Workday.class)
    private Collection<Workday> workdays;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRegistrationNumber() {
        return this.registrationNumber == null ? 0 : this.registrationNumber;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Collection<StaffSpecialty> getStaffSpecialties() {
        return this.staffSpecialties;
    }

    public Collection<Workday> getWorkdays() {
        return this.workdays;
    }

    public Office getOffice() {
        return this.office;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    protected boolean isSameInstance(Object o) {
        return o instanceof Staff;
    }
}
