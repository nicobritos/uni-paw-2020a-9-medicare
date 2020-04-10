package ar.edu.itba.paw.models;

import ar.edu.itba.paw.persistenceAnnotations.Column;
import ar.edu.itba.paw.persistenceAnnotations.Table;
import ar.edu.itba.paw.persistenceAnnotations.TableRelation;

import java.util.Collection;

@Table(name = "office", primaryKey = "office_id")
public class Office extends GenericModel<Integer> {
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "name", required = true)
    private String name;
    @Column(name = "province_id", required = true, relation = TableRelation.ONE_TO_ONE)
    private Province province;
    @Column(name = "street_number", required = true)
    private int streetNumber;
    @Column(name = "street", required = true)
    private String street;
    @Column(relation = TableRelation.ONE_TO_MANY, elementClass = Staff.class)
    private Collection<Staff> staffs;

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

    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Collection<Staff> getStaffs() {
        return this.staffs;
    }

    public void setStaffs(Collection<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "Office{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", province=" + province +
                ", streetNumber=" + streetNumber +
                ", street='" + street + '\'' +
                ", staffs=" + staffs +
                ", id=" + id +
                '}';
    }
}
