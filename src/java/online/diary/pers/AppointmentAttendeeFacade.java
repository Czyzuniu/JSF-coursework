/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import online.diary.ents.AppointmentAttendee;

/**
 *
 * @author Konrad
 */
@Stateless
public class AppointmentAttendeeFacade extends AbstractFacade<AppointmentAttendee> {
    @PersistenceContext(unitName = "OnlineDiaryProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppointmentAttendeeFacade() {
        super(AppointmentAttendee.class);
    }
    
}
