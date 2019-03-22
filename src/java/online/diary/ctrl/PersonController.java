/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import online.diary.ents.Person;
import javax.inject.Named;
import online.diary.bus.PersonException;
import online.diary.bus.PersonService;
import online.diary.bus.AddressService;
import online.diary.bus.AppointmentService;
import online.diary.ents.Address;
import online.diary.ents.Appointment;
import online.diary.ents.Contact;
/**
 *
 * @author Konrad
 */
@ManagedBean
@SessionScoped
@Named(value = "personController")
public class PersonController {
    private Person newUser = new Person();
    private Address address = new Address();
    private Person currentUser = new Person();
    private String repeatPassword = "";
    private List<Person> allUsers = new ArrayList();
    
    @EJB
    private PersonService personService;
    
    @EJB
    private AddressService addressService;
    
    @EJB
    private AppointmentService appointmentService;
    

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public Person getNewUser() {
        return newUser;
    }

    public void setNewUser(Person newUser) {
        this.newUser = newUser;
    }

    public Person getCurrentUser() {
//        currentUser = personService.reattachPerson(currentUser);
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    
    /**
     * Creates a new instance of PersonController
     */
    public PersonController() {

    }
   
       public List<Person> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<Person> allUsers) {
        this.allUsers = allUsers;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
    
    public String registerUser() {
        String responseView = "";
        try {
            newUser.setAddress(addressService.createAddress(address));
            personService.registerUser(newUser); 
            responseView = "/login.xhtml?faces-redirect=true";
        } catch (PersonException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseView;
    }
           
    
    public String goToRegister() {    
        newUser = new Person();
        address = new Address();
        return "/register.xhtml?faces-redirect=true";
    }
    
    public String searchForPerson(AjaxBehaviorEvent event) throws PersonException {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();
        if (searchValue.equals("")) {
            allUsers = personService.getAllUsersWithoutLoggedUser(currentUser);
        } else {
            allUsers = personService.searchForPerson(searchValue);   
        }
        return "";
    }
    
    
    public List<Person> getGuestForAppointment(Appointment appointment) {
        return appointment.getGuests();
    }
    
    
    public String login() {
        boolean isAuthenticated = false;
        
        List<Person> results;
        try {
            results = personService.findPersonByUsernameAndPassword(currentUser.getUserName(), currentUser.getPassword());
            if (!results.isEmpty()) {
                currentUser = results.get(0);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("loggedUser", currentUser);
                isAuthenticated = true;
            } 
        } catch (PersonException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        String responseView = "";
        if (isAuthenticated) {
            responseView = "/home.xhtml?faces-redirect=true";;
        } 
        
        return responseView;
    }
    
   
    public String addToContacts(Person personToAdd) throws PersonException {
        Contact contact = new Contact();
        contact.setPerson(currentUser);
        contact.setContact(personToAdd);
        currentUser.getContacts().add(personService.addToContacts(contact));
        
        //refresh the users list
        allUsers = personService.getAllUsersWithoutLoggedUser(currentUser);
       
        return "";
    }
    
    
    public String removeFromContacts(Contact contact) throws PersonException {
        personService.removeFromContacts(contact);
        currentUser.getContacts().remove(contact);
        
        //refresh the users list
        allUsers = personService.getAllUsersWithoutLoggedUser(currentUser);
       
        return "";
    }
    
    
    public String signOut() {
        currentUser = new Person();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
    
    public String editUser() {
        personService.updateUser(currentUser);
        addressService.updateAddress(currentUser.getAddress());
        return "/home.xhtml?faces-redirect=true";
    }
    
    
    public void onPageLoad() {
        try {
            allUsers = personService.getAllUsersWithoutLoggedUser(currentUser);
            currentUser.setContacts(personService.getPersonContacts(currentUser));
            currentUser.setAppointments(appointmentService.getAllAppointments(currentUser));
            
        } catch (PersonException ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
