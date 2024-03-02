package com.example.application.views.subpages.layout;

import com.example.application.data.entity.Booking.Booking;
import com.example.application.data.entity.Booking.BookingCollection;
import com.example.application.data.entity.Booking.HomeTestingBooking;
import com.example.application.data.entity.CovidTest.CovidTest;
import com.example.application.data.entity.CovidTest.CovidTestCollection;
import com.example.application.data.entity.TestingType.RAT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.net.http.HttpResponse;

/**
 * QR verification Layout that verify given qr code which can be reused for pages
 */
public class QrVerifyLayout extends VerticalLayout {
    private Button submitVerification;
    private final TextField qrString = new TextField("QR string");
    private final Dialog dialog = new Dialog();
    private final TextArea label = new TextArea();

    /**
     * populate the layout with components
     */
    public QrVerifyLayout(){
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.add(closeButton,label);
        this.configureVerifyButton();
        add(qrString,submitVerification,dialog);
    }

    /**
     * Configuring Verify Button logic
     */
    private void configureVerifyButton(){
        qrString.addValueChangeListener(e -> {
            if (!qrString.isEmpty() && !qrString.isInvalid())
                submitVerification.setEnabled(true);
            else
                submitVerification.setEnabled(false);
        });
        submitVerification = new Button("Verify");
        submitVerification.setEnabled(false);
        attachListenerToSubmitVerificationButton();
    }

    /**
     * Helper method to attach listener with logic to the button
     */
    private void attachListenerToSubmitVerificationButton() {
        submitVerification.addClickListener(e -> {
            if (!qrString.isEmpty() && !qrString.isInvalid()){
                BookingCollection bookingCollection = new BookingCollection();
                Booking userBooking = bookingCollection.verifyQr(qrString.getValue());
                label.clear();
                // Check if this QR code valid in the bookings
                if (userBooking != null){
                    CovidTestCollection covidTestCollection = new CovidTestCollection();
                    CovidTest covidTest = covidTestCollection.verifyQrCovidTest(qrString.getValue());
                    // if covidTest was created before (!=null)
                    if (covidTest!=null){
                        label.setWidth("500px");
                        label.setValue(covidTest.toString());
                        dialog.open();
                        Notification noti = Notification.show("Error! Covid Test already exist");
                        noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                    // if covidTest was not created before (==null)
                    else {
                        covidTest = new CovidTest(new RAT(), userBooking.getCustomer(), userBooking.getCustomer(), userBooking ,"INITIATED","RAT kit redeemed", "", "");
                        try {
                            HttpResponse<String> response = covidTestCollection.addCovidTestService(covidTest.getTestingType().getType(), covidTest.getPatient().getId(), covidTest.getAdministerer().getId() , covidTest.getBooking().getBookingId(), covidTest.getResult(), covidTest.getStatus(), covidTest.getNotes());
                            ObjectNode mappedResponse = new ObjectMapper().readValue(response.body(),ObjectNode.class);
                            label.setWidth("500px");
                            label.setValue(mappedResponse.toPrettyString());
                            dialog.open();
                            Notification.show("Acknowledged user RAT kit was collected along with meeting url "+ ((HomeTestingBooking) covidTest.getBooking()).getUrl());
                        }
                        catch (Exception exception){
                            System.out.println(exception);
                            Notification noti = Notification.show("Error! Unable to add covid test");
                            noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    }
                }
                else {
                    Notification noti = Notification.show("Error! QR code doesn't exist");
                    noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
            else {
                Notification noti = Notification.show("Error! Invalid QR string");
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}
