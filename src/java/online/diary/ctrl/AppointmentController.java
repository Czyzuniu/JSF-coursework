/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import online.diary.bus.AppointmentService;
import online.diary.bus.PersonService;
import online.diary.ents.Appointment;
import online.diary.ents.Person;

/**
 *
 * @author Czyzuniu
 */
@Named(value = "appointmentController")
@ViewScoped
@ManagedBean
public class AppointmentController implements Serializable {

    private List<Person> contactList = new ArrayList<>();
    private Appointment appointment;
    private String appointmentDate = "";

    
    @EJB
    private PersonService personService;
    
    @EJB
    private AppointmentService appointmentService;
    
    public AppointmentController() {
       appointment = new Appointment();
    }
    
    

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
   
    
    public Appointment getAppointment() {
        return appointment;
    }
   

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    
    public List<Person> getContactList() {
        return contactList;
    }

    public void setContactList(List<Person> contactList) {
        this.contactList = contactList;
    }
    
    public Person getLoggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (Person) context.getExternalContext().getSessionMap().get("loggedUser");
    }
    
    public String searchForContact(AjaxBehaviorEvent event) {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();
        
        if (!searchValue.isEmpty()) {
            contactList = personService.searchForContact(getLoggedUser(), searchValue);   
        } else {
            contactList.clear();
        }
        return "";
    }
    
    public String createAppointment() throws ParseException {
        appointment.setOwner(getLoggedUser());
        
        DateFormat timeFormat = new SimpleDateFormat("HH:mm"); 
        String startTime = timeFormat.format(appointment.getStartTime());
        String finishTime = timeFormat.format(appointment.getFinishTime());
        
        DateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        
        appointment.setStartTime(finalFormat.parse(appointmentDate + " " + startTime));
        appointment.setFinishTime(finalFormat.parse(appointmentDate + " " + finishTime));
        
        getLoggedUser().getAppointments().add(appointmentService.createAppointment(appointment));

        
        return "";
    }

    public String addToGuestList(Person p) {
       if (this.appointment.getGuests().indexOf(p) == -1) {
           this.appointment.getGuests().add(p);
       }
       return "";
    }
    
    public String removeFromGuestList(Person p) {
       if (this.appointment.getGuests().indexOf(p) != -1) {
           this.appointment.getGuests().remove(p);
       }
       return "";
    }
    
    
}
