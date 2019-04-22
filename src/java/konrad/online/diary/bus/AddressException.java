/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.bus;

/**
 *
 * @author Czyzuniu
 */
public class AddressException extends Exception {

    /**
     * Creates a new instance of <code>AddressException</code> without detail
     * message.
     */
    public AddressException() {
    }

    /**
     * Constructs an instance of <code>AddressException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AddressException(String msg) {
        super(msg);
    }
}
