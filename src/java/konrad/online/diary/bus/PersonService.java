/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.bus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import konrad.online.diary.pers.PersonFacade;
import konrad.online.diary.ents.Person;
/**
 *
 * @author Konrad
 */
@Stateless
public class PersonService {
    @EJB
    private PersonFacade pf;
    
    /**
     *
     * @param passwordToHash
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String hashPassword(String passwordToHash) throws NoSuchAlgorithmException  {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        
        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword);
    }

    /**
     *
     * @param p
     * @return
     */
    public Person registerUser(Person p) { 
        pf.create(p);
        return p;   
    }
    
    /**
     * searches the database for a person with passed username and password
     * @param userName
     * @param password
     * @return Person list
     * @throws PersonException
     */
    public List<Person> findPersonByUsernameAndPassword(String userName, String password) throws PersonException{
        List<Person> results = pf.findPersonByUsernameAndPassword(userName, password);
        if (results.isEmpty()) {
            throw new PersonException("Incorrect username or password");
        }
        return results;
    }
    
    /**
     * queries the database for all the users without the current user
     * returns a hashmap with personList and page count for pagination
     * @param currentUser
     * @param viewSize
     * @param viewIndex
     * @return HashMap
     * @throws PersonException
     */
    public HashMap<String, Object> getAllUsersWithoutLoggedUser(Person currentUser, int viewSize, int viewIndex) throws PersonException {        
        HashMap<String, Object> results = pf.getAllUsersWithoutLoggedUser(currentUser, viewSize, viewIndex);
        return results;
    }
    
    /**
     * calls the person facade to update the passed person record
     * @param person
     * @return person
     */
    public Person updateUser(Person person) {
        pf.edit(person);
        return person;
    }
    
    /**
     * searches for a person in the database using a search value
     * @param search
     * @param currentUser
     * @return list of person
     */
    public List<Person> searchForPerson(String search, Person currentUser) {
        return pf.searchForPerson(search,currentUser);
    }

}
