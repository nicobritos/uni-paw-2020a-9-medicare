package ar.edu.itba.paw.models;

import ar.edu.itba.paw.persistenceAnnotations.Column;
import ar.edu.itba.paw.persistenceAnnotations.ManyToOne;
import ar.edu.itba.paw.persistenceAnnotations.OneToMany;
import ar.edu.itba.paw.persistenceAnnotations.Table;

import java.util.Collection;

@Table(name = "office", primaryKey = "office_id")
public class Office extends GenericModel<Office, Integer> {
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "name", required = true)
    private String name;
    @ManyToOne(name = "locality_id", required = true)
    private Locality locality;
    @Column(name = "street", required = true)
    private String street;
    @OneToMany(name = "office_id", className = Staff.class)
    private JoinedCollection<Staff> staffs = new JoinedCollection<>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Locality getLocality() {
        return this.locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Collection<Staff> getStaffs() {
        return this.staffs.getModels();
    }

    @Override
    public Office copy() {
        Office office = new Office();
        office.email = this.email;
        office.locality = this.locality.copy();
        office.name = this.name;
        office.phone = this.phone;
        office.street = this.street;
        office.id = this.id;
        office.staffs = this.staffs.copy();
        return office;
    }
}
