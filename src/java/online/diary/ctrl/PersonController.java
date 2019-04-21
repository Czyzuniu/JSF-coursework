/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import online.diary.ents.Person;
import javax.inject.Named;
import online.diary.bus.AddressException;
import online.diary.bus.PersonException;
import online.diary.bus.PersonService;
import online.diary.bus.AddressService;
import online.diary.bus.AppointmentService;
import online.diary.ents.Address;
import online.diary.ents.Appointment;
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
    private final int viewSize = 3;
    private int viewIndex = 1;

    
    @EJB
    private PersonService personService;
    
    @EJB
    private AddressService addressService;
    
    @EJB
    private AppointmentService appointmentService;
    @ManagedProperty("#{paginationController}")
    private PaginationController paginationController;

    
    /**
     * Creates a new instance of PersonController
     */
    public PersonController() {
         for (String countryCode:Locale.getISOCountries()) {
             Locale country = new Locale("", countryCode);
             countries.add(country);
         }
    }
    
    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    

    public PaginationController getPaginationController() {
        return paginationController;
    }

    public void setPaginationController(PaginationController paginationController) {
        this.paginationController = paginationController;
    }
  
    
    public boolean isPageActive(int page) {
        return page == viewIndex;
    }

    public ArrayList<Integer> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Integer> pages) {
        System.out.println(pages);
        this.pages = pages;
    }


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
          
    
    public boolean isGB() {
        return address.getCountry().equals("GB");
    }
    
    public String goToRegister() {    
        newUser = new Person();
        address = new Address();
        return "/register.xhtml?faces-redirect=true";
    }
    
    public String searchForPerson(AjaxBehaviorEvent event) throws PersonException {
        String searchValue = (String) ((UIOutput) event.getSource()).getValue();
        if (searchValue.equals("")) {
            HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser,viewSize,viewIndex);
            allUsers = (List<Person>) results.get("users");
        } else {
            allUsers = personService.searchForPerson(searchValue,currentUser);   
        }
        return "";
    }
   

    public ArrayList<Locale> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Locale> countries) {
        this.countries = countries;
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
    
    public void changePage(int page) throws PersonException {
        viewIndex = page;
        HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser, viewSize,viewIndex);
        allUsers = (List<Person>) results.get("users");
    }
    
    public void onPageLoad() {
        try {
            HashMap<String,Object> results = personService.getAllUsersWithoutLoggedUser(currentUser,viewSize, viewIndex);
            allUsers = (List<Person>) results.get("users");
            ArrayList<Integer> pageCount = new ArrayList<>();
            
            for (int i = 0; i < (int) results.get("pages"); i++) {
                pageCount.add(i+1);
            }
          
            this.setPages(pageCount);
            
            
            System.out.println(pageCount);
            currentUser.setAppointments(appointmentService.getAllAppointments(currentUser));
        } catch (PersonException ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
