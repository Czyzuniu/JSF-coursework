/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import online.diary.ents.Appointment;
import online.diary.ents.Person;

/**
 *
 * @author Konrad
 */
@Stateless
public class AppointmentFacade extends AbstractFacade<Appointment> {
    @PersistenceContext(unitName = "OnlineDiaryProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppointmentFacade() {
        super(Appointment.class);
    }

    public List<Appointment> getAllAppointments(Person p) {
        TypedQuery<Appointment> query = em.createQuery("SELECT DISTINCT a from Appointment a JOIN a.guests g WHERE a.owner.id = :personId OR g.id = :personId", Appointment.class);
        query.setParameter("personId", p.getId());
        return query.getResultList();
    }    
}
