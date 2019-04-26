/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import konrad.online.diary.ents.Appointment;
import konrad.online.diary.ents.Person;
import konrad.online.diary.pers.AppointmentFacade;

/**
 *
 * @author Czyzuniu
 */
@Stateless
public class AppointmentService {

    @EJB
    private AppointmentFacade appointmentFacade;
    
    /**
     *
     * @param appointment
     * @return
     */
    public Appointment createAppointment(Appointment appointment) {
        appointmentFacade.create(appointment);
        return appointment;
    }
    
    /**
     * queries database for all Appointments for the passed person object
     * @param p
     * @return appointments list
     */
    public List<Appointment> getAllAppointments(Person p) {
        return appointmentFacade.getAllAppointments(p);
    }    
    
    /**
     * returns a list of appointments which clash with the passed appointment object
     * @param appointment
     * @return appointments list
     */
    public List<Appointment> checkAvailability(Appointment appointment) {
        return appointmentFacade.checkAvailability(appointment);
    }

    /**
     * searches for appointments for person using the search value
     * @param searchValue
     * @param currentUser
     * @return
     */
    public List<Appointment> findAppointment(String searchValue, Person currentUser) {
       return appointmentFacade.findAppointment(searchValue, currentUser);
    }
    
    
}
