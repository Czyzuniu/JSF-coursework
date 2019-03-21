/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import online.diary.ents.Person;
import java.util.List;
import java.util.Objects;
import online.diary.ents.Contact;

/**
 *
 * @author Konrad
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {
    @PersistenceContext(unitName = "OnlineDiaryProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    public List<Person> findPersonByUsernameAndPassword(String userName, String password) {
        System.out.println(userName);
        System.out.println(password);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.userName = :userName AND p.password = :password", Person.class);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        return query.getResultList();
    }
    
    public List<Person> getAllUsersWithoutLoggedUser(Person person) {
        TypedQuery<Person> query;
        
        List<Person> toRemove = new ArrayList<>();
 
        query = em.createQuery("SELECT p FROM Person p LEFT JOIN p.contacts c WHERE p.userName != :userName", Person.class);

        query.setParameter("userName", person.getUserName());
    
        List<Person> results = query.getResultList();
        
        List<Contact> contacts = getPersonContacts(person);

        
        results.forEach((r) -> {
            contacts.forEach((c) -> {
                if (Objects.equals(c.getContact().getId(), r.getId())) {
                    toRemove.add(r);
                }
            });
        }); 
        
        results.removeAll(toRemove);
        
        System.out.println(results);

        
        return results;
    }
    
    public List<Person> searchForPerson(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.firstName LIKE :search OR p.lastName LIKE :search OR p.emailAddress LIKE :search OR p.phoneNumber LIKE :search", Person.class);

        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }
    
    public List<Person> searchForContact(Person person, String search) {
        TypedQuery<Person> query = em.createQuery("SELECT ref from Contact c JOIN c.contact ref WHERE c.person.id = :personId "
                + "AND (ref.firstName LIKE :search OR ref.lastName LIKE :search OR ref.emailAddress LIKE :search OR ref.phoneNumber LIKE :search)", Person.class);
        query.setParameter("search", "%" + search + "%");
        query.setParameter("personId", person.getId());
        return query.getResultList();
    }
    
    public List<Contact> getPersonContacts(Person person) {
        TypedQuery<Contact> query = em.createQuery("SELECT c FROM Contact c WHERE c.person.id = :personId", Contact.class);
        query.setParameter("personId", person.getId());
        List<Contact> results = query.getResultList();
        return results;
    }
    
   
    
}
