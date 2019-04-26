/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.pers;


import java.util.HashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import konrad.online.diary.ents.Person;
import java.util.List;
/**
 *
 * @author Konrad
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {
    @PersistenceContext(unitName = "OnlineDiaryProjectPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public PersonFacade() {
        super(Person.class);
    }
    
    /**
     *
     * @param userName
     * @param password
     * @return
     */
    public List<Person> findPersonByUsernameAndPassword(String userName, String password) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.userName = :userName AND p.password = :password", Person.class);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        return query.getResultList();
    }
    
    /**
     * returns a list of Person
     * @param person
     * @param viewSize
     * @param viewIndex
     * @return
     */
    public HashMap<String,Object> getAllUsersWithoutLoggedUser(Person person,int viewSize,int viewIndex) {
        
        int paginationStart = (viewIndex * viewSize) - viewSize;
        int paginationEnd = viewIndex * viewSize;

      
        if (paginationStart < 0) {
            paginationStart = 0;
        }
        
        if (paginationEnd == 0) {
            paginationEnd = viewSize;
        }
        
        
        
        TypedQuery<Person> query;
 
        query = em.createQuery("SELECT p FROM Person p WHERE p.id != :id", Person.class);

        query.setParameter("id", person.getId());
        
        
        List<Person> results = query.getResultList();
        
        if (paginationEnd > results.size()) {
            paginationEnd = results.size();
        }
        
        int pages = results.size() / viewSize;
        
        int remainder = results.size() % viewSize;
        
        if (remainder > 0) {
            pages++;
        }
       
        HashMap<String, Object> returnMap = new HashMap<>();

        returnMap.put("users", results.subList(paginationStart, paginationEnd));
        returnMap.put("pages", pages);
        
        
        return returnMap;
    }
    
    /**
     * queries the database for a person with the search value
     * @param search
     * @param currentUser
     * @return
     */
    public List<Person> searchForPerson(String search, Person currentUser) {        
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE (UPPER(p.firstName) LIKE :search OR UPPER(p.lastName) LIKE :search OR UPPER(p.emailAddress) LIKE :search OR UPPER(p.phoneNumber) LIKE :search) AND p.id != :myId", Person.class);
        query.setParameter("search", "%" + search.toUpperCase() + "%");
        query.setParameter("myId",currentUser.getId());
        return query.getResultList();
    }
      
   
    
}
