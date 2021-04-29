package com.pbd.project.domain;

import com.pbd.project.domain.enums.Gender;
import com.pbd.project.domain.enums.UF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;



@MappedSuperclass
public abstract class People<ID extends Serializable> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Column(nullable = false)
    private String name;

    @NotNull(message = "{NotNull.people.rg}")
    @Column(nullable = false, unique = true, length = 9)
    private String rg;

    @NotNull(message = "{NotNull.people.sus}")
    @Column(nullable = false, unique = true, length = 18)
    private String sus;

    @NotNull(message = "{NotEmpty.people.dateOfBirth}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name ="date_of_birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @NotNull(message = "{NotNull.people.gender}")
    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private Gender Gender;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @NotNull(message = "{NotNull.people.cellPhone}")
    @Column(name="cell_phone")
    private String cellPhone;

    @ManyToOne
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    private String landline;
    private Boolean isActive;


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public com.pbd.project.domain.enums.Gender getGender() {
        return Gender;
    }

    public void setGender(com.pbd.project.domain.enums.Gender gender) {
        Gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People<?> people = (People<?>) o;

        if (id != null ? !id.equals(people.id) : people.id != null) return false;
        if (name != null ? !name.equals(people.name) : people.name != null) return false;
        if (rg != null ? !rg.equals(people.rg) : people.rg != null) return false;
        if (sus != null ? !sus.equals(people.sus) : people.sus != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(people.dateOfBirth) : people.dateOfBirth != null) return false;
        if (Gender != people.Gender) return false;
        if (address != null ? !address.equals(people.address) : people.address != null) return false;
        if (cellPhone != null ? !cellPhone.equals(people.cellPhone) : people.cellPhone != null) return false;
        if (healthCenter != null ? !healthCenter.equals(people.healthCenter) : people.healthCenter != null)
            return false;
        if (landline != null ? !landline.equals(people.landline) : people.landline != null) return false;
        return isActive != null ? isActive.equals(people.isActive) : people.isActive == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rg != null ? rg.hashCode() : 0);
        result = 31 * result + (sus != null ? sus.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (Gender != null ? Gender.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (cellPhone != null ? cellPhone.hashCode() : 0);
        result = 31 * result + (healthCenter != null ? healthCenter.hashCode() : 0);
        result = 31 * result + (landline != null ? landline.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}
