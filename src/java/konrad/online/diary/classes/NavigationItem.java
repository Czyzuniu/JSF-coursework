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
    public NavigationItem(String label, String page) {
        this.label = label;
        this.page = page;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
    
}
