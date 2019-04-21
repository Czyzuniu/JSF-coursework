/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Czyzuniu
 */

@Named(value = "paginationController")
@Dependent
public class PaginationController {
    private ArrayList<Integer>pages = new ArrayList();
    

    /**
     * Creates a new instance of PaginationController
     */
    public PaginationController() {
        
    }

    public ArrayList<Integer> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Integer> pages) {
        System.out.println(pages);
        this.pages = pages;
    }
    
    
    
}
