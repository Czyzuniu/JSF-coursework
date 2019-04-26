/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Konrad
 */
@Entity
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private Date startTime;
    private Date finishTime;
    private boolean includeOwner;
    @ManyToOne
    private Person owner;
    
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Person> guests = new ArrayList<>();

    /**
     *
     */
    public Appointment() {
        
    }

    /**
     * returns the value of includeOwner
     * @return
     */
    public boolean isIncludeOwner() {
        return includeOwner;
    }

    /**
     * set the value of include owner
     * @param includeOwner
     */
    public void setIncludeOwner(boolean includeOwner) {
        this.includeOwner = includeOwner;
    }
    
    /**
     * get the id of the record
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * get all of the guests at the appointment
     * @return guests
     */
    public List<Person> getGuests() {
        return guests;
    }

    /**
     * set guests for the appointment
     * @param guests
     */
    public void setGuests(List<Person> guests) {
        this.guests = guests;
    }
    
    /**
     * get appointments description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of the appointment
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }    

    /**
     * get the start date and time of the appointment
     * @return startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * set the start date and time of the appointment
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * get the finish date and time of the appointment
     * @return finishTime
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * set the finish date and time of the appointment
     * @param finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * get the creator of the appointment
     * @return owner
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * set the appointment owner
     * @param owner
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }
    
    /**
     * set the id of record
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "online.diary.ents.Appointment[ id=" + id + " ]";
    }
    
}
