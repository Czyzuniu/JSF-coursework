/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.bus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import online.diary.ents.Contact;
import online.diary.pers.PersonFacade;
import online.diary.ents.Person;
import online.diary.pers.ContactFacade;
/**
 *
 * @author Konrad
 */
@Stateless
public class PersonService {
    @EJB
    private PersonFacade pf;
    
    @EJB
    private ContactFacade contactFacade;
    
 
    public String hashPassword(String passwordToHash) throws NoSuchAlgorithmException  {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        
        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword);
    }

    public Person registerUser(Person p) throws PersonException { 
        //check things: duplicates
        
        String validation = validateInputs(p);
        
        
        

        if (validation.equals("")) {
            
//            String hashedPassword;
//            try {
//                hashedPassword = hashPassword(p.getPassword());
//                System.out.println(hashedPassword);
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            pf.create(p);
            return p;   
        } else {
          throw new PersonException(validation);
        }
       
        
//        boolean ok = true;
//        
//        if (pf.getPersonListByName(p.getName()).isEmpty()) {
//            pf.create(p);
//            return p;
//        } else {
//           throw new PersonException("Person already exists with a name " + p.getName());
//        }
    }
    
    public String validateInputs(Person p) {
        if (p.getUserName().equals("") || p.getPassword().equals("") || p.getFirstName().equals("") || p.getLastName().equals("")) {
          return "some fields are empty";
        } else {
            return "";
        }
    }
    
    
    public List<Person> findPersonByUsernameAndPassword(String userName, String password) throws PersonException{
        List<Person> results = pf.findPersonByUsernameAndPassword(userName, password);
        if (results.isEmpty()) {
            throw new PersonException("Incorrect username or password");
        }
        return results;
    }
    
    public List<Person> getAllUsers() throws PersonException{
        
        //filter in db later to not return the same logged user
        
        List<Person> results = pf.getAllUsers();
        return results;
    }
    
    public Contact addToContacts(Person person, Contact contact) {
        //check duplicates later
        contactFacade.create(contact);
        person.getContacts().add(contact);
        return contact;
    }
}
