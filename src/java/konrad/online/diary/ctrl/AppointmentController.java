/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ctrl;

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
import konrad.online.diary.bus.AppointmentService;
import konrad.online.diary.bus.PersonService;
import konrad.online.diary.ents.Appointment;
import konrad.online.diary.ents.Person;

/**
 *
 * @author Czyzuniu
 */
@Named(value = "appointmentController")
@SessionScoped
@ManagedBean
public class AppointmentController implements Serializable {

    private List<Person> guestList = new ArrayList<>();
    private Appointment appointment;
    private String appointmentDate = "";
    private boolean ownerAttending = true;
    
    @ManagedProperty(value="#{personController}")
    private PersonController personController;


    
    @EJB
    private PersonService personService;
    
    @EJB
    private AppointmentService appointmentService;
    
    /**
     * Constructor of AppointmentController
     * creates a new appointment at start
     */
    public AppointmentController() {
       appointment = new Appointment();
    }
    
    
    /**
     * Sets the appointment
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * get the personController
     * @return personController
     */
    public PersonController getPersonController() {
        return personController;
    }

    /**
     * set the personController
     * @param personController
     */
    public void setPersonController(PersonController personController) {
        this.personController = personController;
    }

    /**
     * get the list of all
     * @return
     */
    public List<Person> getContactList() {
        return guestList;
    }

    /**
     * set the contactList for this appointment
     * @param contactList
     */
    public void setContactList(List<Person> contactList) {
        this.guestList = contactList;
    }

    /**
     * get the appointmentDate for the appointment
     * @return appointmentDate
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * set the appointmentDate
     * @param appointmentDate
     */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * get the value of ownerAttending
     * @return ownerAttending
     */
    public boolean isOwnerAttending() {
        return ownerAttending;
    }

    /**
     * set the value of ownerAttending
     * @param ownerAttending
     */
    public void setOwnerAttending(boolean ownerAttending) {
        this.ownerAttending = ownerAttending;
    }

    /**
     * get the person service
     * @return personService
     */
    public PersonService getPersonService() {
        return personService;
    }

    /**
     * set the person service
     * @param personService
     */
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    /**
     * get the appointment service
     * @return appointmentService
     */
    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    /**
     * set appointment service
     * @param appointmentService
     */
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    

    
    /**
     * returns the appointment
     * @return appointment
     */
    public Appointment getAppointment() {
        return appointment;
    }
   
    
    /**
     * returns the current logged user from the session map
     * @return Person
     */
    public Person getLoggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (Person) context.getExternalContext().getSessionMap().get("loggedUser");
    }
    
    /**
     * used to perform a search to add a guest to the appointment
     * returns the same view
     * @param event
     * @return 
     */
    public String searchForGuest(AjaxBehaviorEvent event) {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();

        if (!searchValue.isEmpty()) {
            guestList = personService.searchForPerson(searchValue,this.getLoggedUser());
        } else {
            guestList.clear();
        }
        return "";
    }
    
    /**
     * used to perform a search of the appointment by its description
     * returns the same view
     * @param event
     * @return ""
     */
    public String searchForAppointment(AjaxBehaviorEvent event) {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();
        

        if (!searchValue.isEmpty()) {
            List<Appointment> s = appointmentService.findAppointment(searchValue,this.personController.getCurrentUser());
            this.personController.getCurrentUser().setAppointments(s);
        } else {
            this.personController.getCurrentUser().setAppointments(appointmentService.getAllAppointments(this.personController.getCurrentUser()));
        }
        return "";
    }
    
    /**
     * Creates an appointment a new appointment
     * returns the user to appointments screen
     * @return "appointments"
     * @throws ParseException
     */
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

    /**
     * adds the person to the guest list of the appointment
     * @param p
     * @return ""
     */
    public String addToGuestList(Person p) {
       if (this.appointment.getGuests().indexOf(p) == -1) {
           this.appointment.getGuests().add(p);
           this.guestList.remove(p);
       }
       return "";
    }
    
    /**
     * remove the person from the guest list
     * @param p
     * @return ""
     */
    public String removeFromGuestList(Person p) {
       if (this.appointment.getGuests().indexOf(p) != -1) {
           this.appointment.getGuests().remove(p);
       }
       return "";
    }
    
    
}
