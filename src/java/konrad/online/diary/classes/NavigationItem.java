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
public class NavigationItem {
    private String label;
    private String page;

    /**
     * Creates a new instance of navigation item
     * @param label
     * @param page
     */
    public NavigationItem(String label, String page) {
        this.label = label;
        this.page = page;
    }

    /**
     * returns the value of label
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label value
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * returns the current page value
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * sets the page value
     * @param page
     */
    public void setPage(String page) {
        this.page = page;
    }
    
    
}
