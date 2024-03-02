package com.example.application.views.subpages.layout;

import com.example.application.data.entity.Booking.Booking;
import com.example.application.data.entity.Booking.BookingCollection;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * This is a layout for verifying booking id based on booking id given
 */
public class BookingIdVerifyLayout extends VerticalLayout {
    private Button submitVerification;
    private final TextField verifyBookingId = new TextField("Booking ID");
    private final Dialog dialog = new Dialog();
    private final TextArea label = new TextArea();

    /**
     * populate the layout with components
     */
    public BookingIdVerifyLayout(){
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.add(closeButton,label);
        this.configureVerifyButton();
        add(verifyBookingId,submitVerification,dialog);
    }

    /**
     * Configuring Verify Button logic
     */
    private void configureVerifyButton(){
        verifyBookingId.addValueChangeListener(e -> {
            if (!verifyBookingId.isEmpty() && !verifyBookingId.isInvalid())
                submitVerification.setEnabled(true);
            else
                submitVerification.setEnabled(false);
        });
        submitVerification = new Button("Verify");
        submitVerification.setEnabled(false);
        submitVerification.addClickListener(e -> {
            if (!verifyBookingId.isEmpty() && !verifyBookingId.isInvalid()){
                BookingCollection collection = new BookingCollection();
                Booking userBooking = collection.verifyBookingId(verifyBookingId.getValue());
                label.clear();
                if (userBooking !=null){
                    label.setWidth("500px");
                    label.setValue(userBooking.toString());
                    dialog.open();
                }
                else {
                    Notification noti = Notification.show("Error! Invalid ID");
                    noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
            else {
                Notification noti = Notification.show("Error! Invalid ID");
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}
