/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import online.diary.classes.NavigationItem;

/**
 *
 * @author Czyzuniu
 */
@SessionScoped
@ManagedBean(name="navigationController")
public class NavigationController {
    private String currentView = "home";
    private List<NavigationItem> navigationItems;

    public NavigationController() {
        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationItem("Home", "home"));
        navigationItems.add(new NavigationItem("My Contacts", "contacts"));
        navigationItems.add(new NavigationItem("My Appointments", "appointments"));
        navigationItems.add(new NavigationItem("My Profile", "profile"));
    }

    public List<NavigationItem> getNavigationItems() {
        return navigationItems;
    }

    public void setNavigationItems(List<NavigationItem> navigationItems) {
        this.navigationItems = navigationItems;
    }
    
    

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }
    
    public String navigate(String page) {
        setCurrentView(page);
        return page + "?faces-redirect=true";
    }
    
}
