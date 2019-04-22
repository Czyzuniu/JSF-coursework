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
    
    public Pagination(int viewSize, int viewIndex) {
        this.viewSize = viewSize;
        this.viewIndex = viewIndex;
    }
    
    public Pagination() {
        this.viewSize = 3;
        this.viewIndex = 1;
    }
    

    public int getViewSize() {
        return viewSize;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    public int getViewIndex() {
        return viewIndex;
    }

    public void setViewIndex(int viewIndex) {
        this.viewIndex = viewIndex;
    }
    
    
}
