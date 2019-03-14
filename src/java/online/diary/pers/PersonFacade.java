/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import online.diary.ents.Person;
import java.util.List;

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
    
}
