/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import java.util.ArrayList;
import java.util.Date;
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
    
    public List<Appointment> checkAvailability(Appointment appointment) {
//select * from appointment a join appointment_person ap on a.ID = ap.APPOINTMENTS_ID WHERE ('2019-04-21 20:30:00' BETWEEN a.STARTTIME and a.FINISHTIME OR '2019-04-21 20:45:00' BETWEEN a.STARTTIME and a.FINISHTIME) AND ap.GUESTS_ID in (202,102)            
        TypedQuery<Appointment> query = em.createQuery("SELECT a from Appointment a join a.guests g WHERE (:appointmentFromDate BETWEEN a.startTime and a.finishTime OR :appointmentFinishDate BETWEEN a.startTime and a.finishTime) AND g.id in :attendees", Appointment.class);
        query.setParameter("appointmentFromDate", appointment.getStartTime());
        query.setParameter("appointmentFinishDate", appointment.getFinishTime());
        
        ArrayList<Long>attendeesId = new ArrayList();
        
        appointment.getGuests().forEach((p) -> {
            attendeesId.add(p.getId());
        });
        
        query.setParameter("attendees", attendeesId);
        
        return query.getResultList();
        
      
    }

    public List<Appointment> getAllAppointments(Person p) {
        TypedQuery<Appointment> query = em.createQuery("SELECT DISTINCT a from Appointment a LEFT JOIN a.guests g WHERE a.owner.id = :personId OR g.id = :personId", Appointment.class);
        query.setParameter("personId", p.getId());
        return query.getResultList();
    }    
}
