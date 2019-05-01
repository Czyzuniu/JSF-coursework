/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import konrad.online.diary.bus.AddressException;
import konrad.online.diary.bus.AddressService;
import konrad.online.diary.bus.PersonException;
import konrad.online.diary.bus.PersonService;
import konrad.online.diary.ents.Address;
import konrad.online.diary.ents.Person;

/**
 *
 * @author Czyzuniu
 */
@ViewScoped
@ManagedBean(name="setupController")
public class SetupController {
    
    private boolean isDataPopulated = false;
    
    
    @EJB
    private PersonService personService;
    
    private ArrayList<String> names =  new ArrayList();
    
    /**
     * Creates a new instance of SetupController
     * adds names to array to generate dummy data
     */
    public SetupController() {
        names.add("John");
        names.add("Tom");
        names.add("Jim");
        names.add("Luke");
        names.add("Jake");
        names.add("Rob");
        names.add("Simon");
    }
    
    
    @EJB
    private AddressService addressService;

    /**
     * returns the value of isDataPopulated
     * @return isDataPopulated
     */
    public boolean isIsDataPopulated() {
        return isDataPopulated;
    }

    /**
     * sets the vale of isDataPopulated
     * @param isDataPopulated
     */
    public void setIsDataPopulated(boolean isDataPopulated) {
        this.isDataPopulated = isDataPopulated;
    }
    
    /**
     * creates dummy records in the database for testing
     * @return ""
     */
    public String setDummyData() {
        try {
            
            try {
                personService.findPersonByUsernameAndPassword("TestUser", "TestUser");
            } catch (PersonException ex) {
                Person person = new Person();
                person.setFirstName("TestUser");
                person.setLastName("TestUser");
                person.setPassword("TestUser");
                person.setUserName("TestUser");
                person.setEmailAddress("TestUser150@gmail.com");
                person.setPhoneNumber("0754867312");

                Address address = new Address();
                address.setCity("Portsmouth");
                address.setStreet("Portsmouth road 91");
                address.setCountry("GB");
                address.setPostalAddress("PO4 9ED");

                person.setAddress(addressService.createAddress(address));
                personService.registerUser(person); 
                
                Logger.getLogger(SetupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            for (int i = 0; i < names.size(); i++) {
                Person person = new Person();
                person.setFirstName(names.get(i));
                person.setLastName("Smith");
                person.setPassword("TestUser" + names.get(i));
                person.setUserName("TestUser" + names.get(i));
                person.setEmailAddress(names.get(i)+".smith@email.com");
                person.setPhoneNumber("0754867312" + String.valueOf(i));

                Address address = new Address();
                address.setCity("Portsmouth");
                address.setStreet("Portsmouth road 9" + String.valueOf(i));
                address.setCountry("GB");
                address.setPostalAddress("PO4 9ED");

                person.setAddress(addressService.createAddress(address));

                personService.registerUser(person); 
            }
   
            
            this.setIsDataPopulated(true);
            
            
        } catch (AddressException ex) {
            Logger.getLogger(SetupController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return "";
    }
    
    
    

    
}
