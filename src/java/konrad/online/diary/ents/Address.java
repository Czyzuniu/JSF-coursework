/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ents;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Czyzuniu
 */
@Entity
public class Address implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String country;
    private String city;
    private String postalAddress;
    private String street;
    
    @OneToOne(mappedBy = "address", cascade = CascadeType.PERSIST)
    private Person person;

    /**
     * get the value of country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     *  get the person at the address
     * @return person
     */
    public Person getPerson() {
        return person;
    }

    /**
     *  set the person to the address
     * @param person
     */
    public void setResident(Person person) {
        this.person = person;
    }

    /**
     * set the country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * get the city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * set the city
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * get postal address
     * @return postalAddress
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * set postal address
     * @param postalAddress
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     *  get the street
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * set the street
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }
    
    /**
     * get the id of the record
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set the id of the record
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash +=  (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "online.diary.ents.Address[ id=" + id + " ]";
    }
    
}
