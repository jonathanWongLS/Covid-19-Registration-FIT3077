package com.example.application.views.subpages;

import com.example.application.data.entity.User.Role;
import com.example.application.views.subpages.layout.ProfileAvatarLayout;
import com.example.application.views.subpages.layout.UserBookingsLayout;
import com.example.application.views.subpages.layout.UserProfileLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/userprofile")
@PageTitle("Profile")
/**
 * This is the page for showing user details and their existing booking
 */
public class UserProfileView extends VerticalLayout {
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final Tab tabUserProfile = new Tab("UserProfile");
    private final Tab tabActiveBookings = new Tab("User Bookings");
    private final Tab tabHomeBooking = new Tab("| Main Page |");
    private final Tabs mainTabs = new Tabs(tabUserProfile, tabActiveBookings, tabHomeBooking);

    public UserProfileView(){
        // Change layout based on selected tab
        mainLayout.add(new UserProfileLayout());
        mainTabs.addSelectedChangeListener(event -> {
            mainLayout.removeAll();
            if (event.getSelectedTab().equals(tabUserProfile)) {
                mainLayout.add(new UserProfileLayout());
            }
            else if (event.getSelectedTab().equals(tabActiveBookings)) {
                mainLayout.add(new UserBookingsLayout());
            }
            else if (event.getSelectedTab().equals(tabHomeBooking)) {
                if (UI.getCurrent().getSession().getAttribute("role").equals(Role.CUSTOMER))
                    UI.getCurrent().navigate("customer");
                else if (UI.getCurrent().getSession().getAttribute("role").equals(Role.RECEPTIONIST))
                    UI.getCurrent().navigate("receptionist");
                else if (UI.getCurrent().getSession().getAttribute("role").equals(Role.HEALTHCAREWORKER))
                    UI.getCurrent().navigate("healthcareworker");
            }
        });
        if (!UI.getCurrent().getSession().getAttribute("role").equals(Role.CUSTOMER))
            mainTabs.remove(tabActiveBookings);
//            tabActiveBookings.setEnabled(false);
        setMargin(false);
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new ProfileAvatarLayout(),mainTabs,mainLayout);
    }
}
