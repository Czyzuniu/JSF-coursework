/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import online.diary.ents.Address;
import online.diary.pers.AddressFacade;

/**
 *
 * @author Czyzuniu
 */
@Stateless
public class AddressService {
   
    @EJB
    private AddressFacade af;
    
    public Address createAddress(Address address) {
        af.create(address);
        return address;  
    }
    
    public Address updateAddress(Address address) {
        af.edit(address);
        return address;
    }
    
       
}
