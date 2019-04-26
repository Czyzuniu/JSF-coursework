/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import konrad.online.diary.ents.Person;
import javax.inject.Named;
import konrad.online.diary.bus.AddressException;
import konrad.online.diary.bus.PersonException;
import konrad.online.diary.bus.PersonService;
import konrad.online.diary.bus.AddressService;
import konrad.online.diary.bus.AppointmentService;
import konrad.online.diary.classes.Pagination;
import konrad.online.diary.ents.Address;
import konrad.online.diary.ents.Appointment;
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
    private ArrayList<Integer>pages = new ArrayList();
    private ArrayList<Locale>countries = new ArrayList();
    Pagination pagination = new Pagination();

    
    @EJB
    private PersonService personService;
    
    @EJB
    private AddressService addressService;
    
    @EJB
    private AppointmentService appointmentService;

    
    /**
     * Creates a new instance of PersonController
     * loads the countries from Locales
     */
    public PersonController() {
         for (String countryCode:Locale.getISOCountries()) {
             Locale country = new Locale("", countryCode);
             countries.add(country);
         }
    }
    
    /**
     * gets the appointment service
     * @return
     */
    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    /**
     *
     * @param appointmentService
     */
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * returns true if the current page equals the current pagination page
     * @param page
     * @return boolean
     */
    public boolean isPageActive(int page) {
        return page == pagination.getViewIndex();
    }

    /**
     * returns a list of pages for pagination
     * @return pages
     */
    public ArrayList<Integer> getPages() {
        return pages;
    }

    /**
     * sets the list of pages
     * @param pages
     */
    public void setPages(ArrayList<Integer> pages) {
        this.pages = pages;
    }

    /**
     * gets the value of repeatPassword
     * @return repeatPassword
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * gets the value of Address
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * sets the value of Address
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    
    /**
     * sets the value of repeat password
     * @param repeatPassword
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * returns the value of newUser
     * @return newUser
     */
    public Person getNewUser() {
        return newUser;
    }

    /**
     * sets the new user
     * @param newUser
     */
    public void setNewUser(Person newUser) {
        this.newUser = newUser;
    }

    /**
     * returns th current logged in user
     * @return currentUser
     */
    public Person getCurrentUser() {
//        currentUser = personService.reattachPerson(currentUser);
        return currentUser;
    }

    /**
     * sets the current user
     * @param currentUser
     */
    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }
   
    /**
     * returns a list of allUsers
     * @return allUsers
     */
    public List<Person> getAllUsers() {
        return allUsers;
    }

    /**
     * sets a list of allUsers
     * @param allUsers
     */
    public void setAllUsers(ArrayList<Person> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * gets the AddressService
     * @return addressService
     */
    public AddressService getAddressService() {
        return addressService;
    }

    /**
     * sets the AddressService
     * @param addressService
     */
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
    
    /**
     * registers a new user 
     * @return login view
     */
    public String registerUser() {
        
        if (!newUser.getPassword().equals(this.repeatPassword)) {
            FacesContext.getCurrentInstance().addMessage("passwords", new FacesMessage("Passwords do not match"));
            return "";
        } 
        
        Address newAddress = null;
        try {
            newAddress = addressService.createAddress(address);
            
        } catch (AddressException ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("postalAddressException", new FacesMessage(ex.getMessage()));
            
            return "";
           
            
            //messages do not show???
        }

        newUser.setAddress(newAddress);
        personService.registerUser(newUser); 
       
       
        return "/login.xhtml?faces-redirect=true";
    }
          
    
    /**
     * navigates to register page 
     * sets the newUser to new Person
     * sets the address to new Address
     * @return
     */
    public String goToRegister() {    
        newUser = new Person();
        address = new Address();
        return "/register.xhtml?faces-redirect=true";
    }
    
    /**
     * searches for person in the database
     * @param event
     * @return ""
     * @throws PersonException
     */
    public String searchForPerson(AjaxBehaviorEvent event) throws PersonException {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();
        if (searchValue.equals("")) {
            HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser,pagination.getViewSize(),pagination.getViewIndex());
            allUsers = (List<Person>) results.get("users");
        } else {
            allUsers = personService.searchForPerson(searchValue,currentUser);   
        }
        return "";
    }
   
    /**
     * returns a list of Locales
     * @return countries
     */
    public ArrayList<Locale> getCountries() {
        return countries;
    }

    /**
     * sets the list of locales
     * @param countries
     */
    public void setCountries(ArrayList<Locale> countries) {
        this.countries = countries;
    }
    
    /**
     * returns the list of guests for specified appointments
     * @param appointment
     * @return List
     */
    public List<Person> getGuestForAppointment(Appointment appointment) {
        return appointment.getGuests();
    }
    
    /**
     * logins to the system
     * @return "home" view
     */
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
    
    /**
     * logs out of the system
     * @return "login" view
     */
    public String signOut() {
        currentUser = new Person();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
    
    /**
     * edits the current user
     * @return "home"
     */
    public String editUser() {
        personService.updateUser(currentUser);
        addressService.updateAddress(currentUser.getAddress());
        return "/home.xhtml?faces-redirect=true";
    }
    
    /**
     * changes the page on the pagination
     * @param page
     * @throws PersonException
     */
    public void changePage(int page) throws PersonException {
        pagination.setViewIndex(page);
        HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser, pagination.getViewSize(),pagination.getViewIndex());
        allUsers = (List<Person>) results.get("users");
    }
    
    /**
     * sets up pagination and appointments on page load
     */
    public void onPageLoad() {
        try {
            HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser,pagination.getViewSize(), pagination.getViewIndex());
            allUsers = (List<Person>) results.get("users");
            ArrayList<Integer> pageCount = new ArrayList<>();
            
            for (int i = 0; i < (int) results.get("pages"); i++) {
                pageCount.add(i+1);
            }
          
            this.setPages(pageCount);
            
           
            currentUser.setAppointments(appointmentService.getAllAppointments(currentUser));
        } catch (PersonException ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
