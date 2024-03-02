package com.example.application.views.subpages;

import com.example.application.views.subpages.layout.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/customer")
@PageTitle("Customer")
/**
 * This is the page for User booking via System
 * Including home testing booking, on site testing booking, PIN verification, Booking ID verification
 * and testing site list
 * Lastly with a profile avatar to go to profile tab to access user own bookings
 */
public class CustomerView extends VerticalLayout {
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final Tab tabSiteBooking = new Tab("Book for Site Testing");
    private final Tab tabHomeBooking = new Tab("Book for Home Testing");
    private final Tab tabVerifyPin = new Tab("Verify PIN");
    private final Tab tabVerifyBookingId = new Tab("Verify Booking ID");
    private final Tab tabTestingSites = new Tab("Testing Sites");
    private final Tabs mainTabs = new Tabs(tabHomeBooking, tabSiteBooking, tabVerifyPin, tabVerifyBookingId, tabTestingSites);

    /**
     * Populating page with components based on tab selected
     */
    public CustomerView(){

        // Change layout based on selected tab
        mainLayout.add(new HomeBookingLayout());
        mainTabs.addSelectedChangeListener(event -> {

            mainLayout.removeAll();
            if (event.getSelectedTab().equals(tabHomeBooking)) {
                mainLayout.add(new HomeBookingLayout());
            }
            else if (event.getSelectedTab().equals(tabVerifyPin)) {
                mainLayout.add(new PinVerifyLayout());
            }
            else if (event.getSelectedTab().equals(tabVerifyBookingId)) {
                mainLayout.add(new BookingIdVerifyLayout());
            }
            else if (event.getSelectedTab().equals(tabSiteBooking)) {
                mainLayout.add(new OnSiteBookingLayout());
            }
            else if (event.getSelectedTab().equals(tabTestingSites)) {
                mainLayout.add(new TestingSiteListLayout());
            }
        });
        setMargin(false);
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new ProfileAvatarLayout(),mainTabs,mainLayout);
    }
}
