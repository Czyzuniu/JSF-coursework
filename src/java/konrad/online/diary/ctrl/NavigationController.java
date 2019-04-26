/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konrad.online.diary.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import konrad.online.diary.classes.NavigationItem;

/**
 *
 * @author Czyzuniu
 */
@SessionScoped
@ManagedBean(name="navigationController")
public class NavigationController {
    private String currentView = "home";
    private List<NavigationItem> navigationItems;

    /**
     * Constructor for navigation controller
     */
    public NavigationController() {
        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationItem("Home", "home"));
        navigationItems.add(new NavigationItem("My Appointments", "appointments"));
        navigationItems.add(new NavigationItem("Book Appointment", "bookAppointment"));
        navigationItems.add(new NavigationItem("My Profile", "profile"));
    }

    /**
     * returns a list of navigationsItems
     * @return navigationItems
     */
    public List<NavigationItem> getNavigationItems() {
        return navigationItems;
    }

    /**
     * sets navigationItems
     * @param navigationItems
     */
    public void setNavigationItems(List<NavigationItem> navigationItems) {
        this.navigationItems = navigationItems;
    }

    /**
     * returns the value of currentView
     * @return currentView
     */
    public String getCurrentView() {
        return currentView;
    }

    /**
     * sets the value of currentView
     * @param currentView
     */
    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }
    
    /**
     * navigates to the passed page with redirect
     * @param page
     * @return page
     */
    public String navigate(String page) {
        setCurrentView(page);
        return page + "?faces-redirect=true";
    }
    
}
