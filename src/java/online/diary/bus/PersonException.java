/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.bus;

/**
 *
 * @author Czyzuniu
 */
public class PersonException extends Exception {

    /**
     * Creates a new instance of <code>PersonException</code> without detail
     * message.
     */
    public PersonException() {
    }

    /**
     * Constructs an instance of <code>PersonException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public PersonException(String msg) {
        super(msg);
    }
}
