/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import online.diary.ents.Person;
import javax.inject.Named;
import online.diary.bus.PersonException;
import online.diary.bus.PersonService;
import online.diary.bus.AddressService;
import online.diary.ents.Address;
/**
 *
 * @author Konrad
 */
@ManagedBean
@RequestScoped
@Named(value = "personController")
public class PersonController {
    private Person newUser = new Person();
    private Address address = new Address();
    private Person currentUser = new Person();
    private String repeatPassword = "";
    
    @EJB
    private PersonService personService;
    
    @EJB
    private AddressService addressService;

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
   
    
    
    public String registerUser() {
        String responseView = "";
        try {
            Person registeredPerson = personService.registerUser(newUser); 
            address.setPerson(registeredPerson);
            addressService.createAddressAndSetResident(address);
            responseView = "login";
        } catch (PersonException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseView;
    }
           
    
    public String goToRegister() {    
        return "register";
    }
    
    
    public String login() {
        boolean isAuthenticated = false;
        
        List<Person> results;
        try {
            results = personService.findPersonByUsernameAndPassword(currentUser.getUserName(), currentUser.getPassword());
            if (!results.isEmpty()) {
                currentUser = results.get(0);
                isAuthenticated = true;
            } 
        } catch (PersonException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        String responseView = "";
        if (isAuthenticated) {
            responseView = "home";
        } 
        
        return responseView;
    }
    
    
    
}
