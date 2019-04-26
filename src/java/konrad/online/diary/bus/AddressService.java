/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import konrad.online.diary.ents.Address;
import konrad.online.diary.pers.AddressFacade;

/**
 *
 * @author Czyzuniu
 */
@Stateless
public class AddressService {
   
    @EJB
    private AddressFacade af;
    
    /**
     * creates a new Address in the database
     * checks for postal code regex if country is United Kingdom
     * @param address
     * @return
     * @throws AddressException
     */
    public Address createAddress(Address address) throws AddressException {
      
        String ukPostalCodeRegExp = "([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|"
                + "(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-"
                + "z][A-Ha-hJ-Yj-y][0-9][A-Za-z]?))))\\s?[0-9][A-Za-z]{2})";
        
        if (address.getCountry().equals("GB")) {
            if (address.getPostalAddress().matches(ukPostalCodeRegExp)) {
                af.create(address); 
            } else {
                throw new AddressException("Not a valid UK postcode");
            }
        }
        
        return address;
    }
    
    /**
     *
     * @param address
     * @return
     */
    public Address updateAddress(Address address) {
        af.edit(address);
        return address;
    }
    
       
}
