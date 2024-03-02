package com.example.application.views.subpages;

import com.example.application.views.subpages.layout.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/receptionist")
@PageTitle("Receptionist")
/**
 * This is the page for Receptionist to
 * book user at site,
 * verify PIN,
 * verify QR for RAT-kit,
 * manage all booking,
 * modify booking when called by user.
 * Lastly with a profile avatar to go to profile tab
 */
public class ReceptionistView extends VerticalLayout implements BeforeLeaveObserver {

    private final Tab tabRegistration = new Tab("Registration");
    private final Tab tabVerifyPin = new Tab("Verify PIN") ;
    private final Tab tabScanQr = new Tab("RAT-kit verify QR");
    private final Tab tabBookings = new Tab("Booking Management");
    private final Tab tabModifyByPhone = new Tab("Modify Booking By Phone");
    private final Tabs mainTabs = new Tabs(tabRegistration,tabVerifyPin,tabScanQr,tabBookings,tabModifyByPhone);
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final NotificationDialog notification = new NotificationDialog();

    /**
     * Populating page with components based on tab selected
     */
    public ReceptionistView(){
        mainLayout.add(new ReceptionistBookingLayout());
        mainTabs.addSelectedChangeListener(event -> {
            mainLayout.removeAll();
            if (event.getSelectedTab().equals(tabRegistration)) {
                mainLayout.add(new ReceptionistBookingLayout());
            }
            else if (event.getSelectedTab().equals(tabVerifyPin)) {
                mainLayout.add(new PinVerifyLayout());
            }
            else if (event.getSelectedTab().equals(tabScanQr)) {
                mainLayout.add(new QrVerifyLayout());
            }
            else if (event.getSelectedTab().equals(tabBookings)) {
                mainLayout.add(new BookingManagementLayout());
            }
            else if (event.getSelectedTab().equals(tabModifyByPhone)) {
                mainLayout.add(new ModifyBookingByPhoneLayout());
            }
        });
        if (notification.newNotificationExist())
            notification.open();

        setMargin(false);
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new ProfileAvatarLayout(),mainTabs,mainLayout);
    }

    /**
     * Lifecycle event that occur before leaving the page
     * @param event
     */
    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        BeforeLeaveEvent.ContinueNavigationAction action = event.postpone();
        notification.close();
        action.proceed();
    }
}
