package ar.edu.itba.paw.models;

import ar.edu.itba.paw.persistenceAnnotations.Column;
import ar.edu.itba.paw.persistenceAnnotations.Table;
import ar.edu.itba.paw.persistenceAnnotations.TableRelation;

import java.util.Collection;

@Table(name = "staff", primaryKey = "staff_id")
public class Staff extends GenericModel<Integer> {
    @Column(name = "office_id", required = true, relation = TableRelation.MANY_TO_ONE)
    private Office office;
    @Column(name = "first_name", required = true)
    private String firstName;
    @Column(name = "surname", required = true)
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "registration_number", required = true)
    private int registrationNumber;
    @Column(relation = TableRelation.MANY_TO_MANY, elementClass = StaffSpecialty.class, intermediateTable = "system_staff_specialty_staff")
    private Collection<StaffSpecialty> staffSpecialties;

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

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
        return this.registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
