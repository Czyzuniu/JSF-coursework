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
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import online.diary.pers.PersonFacade;
import online.diary.ents.Person;
/**
 *
 * @author Konrad
 */
@Stateless
public class PersonService {
    @EJB
    private PersonFacade pf;
    

    
    public String hashPassword(String passwordToHash) throws NoSuchAlgorithmException  {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        
        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword);
    }

    public Person registerUser(Person p) { 
        pf.create(p);
        return p;   
    }
    
    public List<Person> findPersonByUsernameAndPassword(String userName, String password) throws PersonException{
        List<Person> results = pf.findPersonByUsernameAndPassword(userName, password);
        if (results.isEmpty()) {
            throw new PersonException("Incorrect username or password");
        }
        return results;
    }
    
    public HashMap<String, Object> getAllUsersWithoutLoggedUser(Person currentUser, int viewSize, int viewIndex) throws PersonException {        
        HashMap<String, Object> results = pf.getAllUsersWithoutLoggedUser(currentUser, viewSize, viewIndex);
        return results;
    }
    
    
    public Person updateUser(Person person) {
        pf.edit(person);
        return person;
    }
    
    public List<Person> searchForPerson(String search, Person currentUser) {
        return pf.searchForPerson(search,currentUser);
    }

}
