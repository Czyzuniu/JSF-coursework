/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ents;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Konrad
 */
@Entity
public class Person implements Serializable {

    @OneToMany(mappedBy = "owner")
    @ManyToMany(mappedBy = "guests", fetch=FetchType.EAGER)
    private List<Appointment> appointments;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String emailAddress;
    private String phoneNumber;
   

    @OneToOne
    private Address address;

    /**
     * get appointments for this person
     * @return appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * set appointments for this person
     * @param appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    /**
     * get the first name of the person
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get the address record of the person
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     * set the address for person
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    
    /**
     * set first name for person
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get last name for person
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set last name for person
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get the username of the person
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get the password for person
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password for person
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get person's email address
     * @return emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * set person's email address
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * get person's phone number
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set person's phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get the id of the record
     * @return id
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "online.diary.ents.Person[ id=" + id + " ]";
    }
    
}
