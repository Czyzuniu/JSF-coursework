/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import online.diary.ents.Appointment;
import online.diary.pers.AppointmentFacade;

/**
 *
 * @author Czyzuniu
 */
@Stateless
public class AppointmentService {

    @EJB
    private AppointmentFacade appointmentFacade;
    
    public Appointment createAppointment(Appointment appointment) {
        appointmentFacade.create(appointment);
        return appointment;
    }
}
