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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
@ManagedBean
public class AppointmentController implements Serializable {

    private List<Person> contactList = new ArrayList<>();
    private Appointment appointment;
    private String appointmentDate = "";
    private boolean ownerAttending = true;
    
    @ManagedProperty(value="#{personController}")
    private PersonController personController;

    
    @EJB
    private PersonService personService;
    
    @EJB
    private AppointmentService appointmentService;
    
    public AppointmentController() {
       appointment = new Appointment();
    }

    public boolean isOwnerAttending() {
        return ownerAttending;
    }

    public PersonController getPersonController() {
        return personController;
    }

    public void setPersonController(PersonController personController) {
        this.personController = personController;
    }
    

    public void setOwnerAttending(boolean ownerAttending) {
        this.ownerAttending = ownerAttending;
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
    
    public String searchForGuest(AjaxBehaviorEvent event) {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();

        if (!searchValue.isEmpty()) {
            contactList = personService.searchForPerson(searchValue,this.getLoggedUser());
        } else {
            contactList.clear();
        }
        return "";
    }
    
    public String searchForAppointment(AjaxBehaviorEvent event) {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();

        if (!searchValue.isEmpty()) {
            this.personController.getCurrentUser().getAppointments() = appointmentService.findAppointment(searchValue,this.getLoggedUser());
        } else {
            contactList.clear();
        }
        return "";
    }
    
    public String createAppointment() throws ParseException {
        appointment.setOwner(getLoggedUser());
        
        appointment.setIncludeOwner(this.ownerAttending);
        
        DateFormat timeFormat = new SimpleDateFormat("HH:mm"); 
        String startTime = timeFormat.format(appointment.getStartTime());
        String finishTime = timeFormat.format(appointment.getFinishTime());
        
        DateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        
        appointment.setStartTime(finalFormat.parse(appointmentDate + " " + startTime));
        appointment.setFinishTime(finalFormat.parse(appointmentDate + " " + finishTime));
        
        
        List<Appointment>clashingAppointments = appointmentService.checkAvailability(appointment);
        
        StringBuilder str = new StringBuilder(); 
        
        str.append("The appointment clashes for users : ");
        
        
        
        if (clashingAppointments.size() > 0) {
            clashingAppointments.forEach((app) -> {
//                str.append(app.)
            });
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The appointment clashes for some user"));
            return "bookAppointment";
        } else {
            getLoggedUser().getAppointments().add(appointmentService.createAppointment(appointment));
        }
               
        
        return "appointments";
        
    }

    public String addToGuestList(Person p) {
       if (this.appointment.getGuests().indexOf(p) == -1) {
           this.appointment.getGuests().add(p);
           this.contactList.remove(p);
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
