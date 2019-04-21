/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import online.diary.ents.Person;

/**
 *
 * @author Czyzuniu
 */
@Named(value = "homeController")
@Dependent
public class HomeController {

    private List<Person> allUsers = new ArrayList();

    public List<Person> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Person> allUsers) {
        this.allUsers = allUsers;
    }
    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }
    
    
//    public void onPageLoad() {
//        try {
//            allUsers = personService.getAllUsersWithoutLoggedUser(currentUser);
//            currentUser.setContacts(personService.getPersonContacts(currentUser));
//            currentUser.setAppointments(appointmentService.getAllAppointments(currentUser));
//            
//        } catch (PersonException ex) {
//            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
