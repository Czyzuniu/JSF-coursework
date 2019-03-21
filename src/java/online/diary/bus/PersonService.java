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
    
    public List<Person> getAllUsersWithoutLoggedUser(Person currentUser) throws PersonException {
        
        //filter the users which are in the contacts to not add the same user 
        
        List<Person> results = pf.getAllUsersWithoutLoggedUser(currentUser);
        return results;
    }
    
    public Contact addToContacts(Contact contact) {
        //check duplicates later        
        contactFacade.create(contact);
       
        return contact;
    }
    
    public List<Contact> getPersonContacts(Person person) {
        return pf.getPersonContacts(person);
    }
    
    public Person updateUser(Person person) {
        pf.edit(person);
        return person;
    }
    
    public List<Person> searchForPerson(String search) {
        return pf.searchForPerson(search);
    }

    public void removeFromContacts(Contact contact) {
        contactFacade.remove(contact);
    }

    public List<Person> searchForContact(Person person, String searchValue) {
        return pf.searchForContact(person,searchValue);
    }
}


//
//-- SELECT p1.USERNAME, p2.USERNAME  FROM PERSON p1 INNER JOIN CONTACT ON p1.ID = CONTACT.PERSON_ID INNER JOIN PERSON P2 ON CONTACT.CONTACT_ID = P2.ID
//
//-- SELECT P1.USERNAME, p1.ID, p2.ID, p2.USERNAME FROM PERSON p1 INNER JOIN CONTACT ON p1.ID = CONTACT.PERSON_ID INNER JOIN PERSON P2 ON CONTACT.CONTACT_ID = P2.ID WHERE p2.id not in (905)
//
//-- SELECT P1.USERNAME, p1.ID, CONTACT.CONTACT_ID FROM PERSON P1 LEFT OUTER JOIN CONTACT ON P1.ID = CONTACT.CONTACT_ID AND CONTACT.CONTACT_ID not in (905)
//
//-- SELECT * FROM PERSON EXCEPT SELECT * FROM CONTACT