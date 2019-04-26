/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.classes;

/**
 *
 * @author Czyzuniu
 */
public class Pagination {
    
    private int viewSize;
    private int viewIndex;
    
    /** 
     * Creates a new instance of Pagination
     * @param viewSize
     * @param viewIndex
     */
    public Pagination(int viewSize, int viewIndex) {
        this.viewSize = viewSize;
        this.viewIndex = viewIndex;
    }
    
    /**
     * Creates a new instance of Pagination
     */
    public Pagination() {
        this.viewSize = 5;
        this.viewIndex = 1;
    }       

    /**
     * returns the view size
     * @return viewSize
     */
    public int getViewSize() {
        return viewSize;
    }

    /**
     * sets view size
     * @param viewSize
     */
    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    /**
     * returns the view index
     * @return viewIndex
     */
    public int getViewIndex() {
        return viewIndex;
    }

    /** 
     * sets the view index
     * @param viewIndex
     */
    public void setViewIndex(int viewIndex) {
        this.viewIndex = viewIndex;
    }
    
    
}
