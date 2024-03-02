package com.example.application.views.subpages.layout;

import com.example.application.data.entity.BookingMethod.SystemBookingMethod;
import com.example.application.data.entity.User.Customer;
import com.example.application.data.entity.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is a layout for logged in user to book for home testing
 */
public class HomeBookingLayout extends VerticalLayout {
    private DateTimePicker startTime;
    private final TextArea notes = new TextArea("Notes");
    private Button submitRegistration;
    private final RadioButtonGroup<String> needTestKit = new RadioButtonGroup<>();
    private final ConfirmDialog confirmDialog = new ConfirmDialog();
    private final FormLayout siteTestingForm = new FormLayout();

    public HomeBookingLayout(){
        this.configureDateTimePicker();
        this.configureRegistrationButton();
        this.configureRadioGroup();
        this.configureRegistrationForm();
        add(siteTestingForm);
    }

    /**
     * Configuring Registration Form
     */
    private void configureRegistrationForm(){
        siteTestingForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("10%",2)
        );
        siteTestingForm.setColspan(notes, 2);
        siteTestingForm.setColspan(submitRegistration, 2);
        siteTestingForm.add(
                needTestKit, startTime,
                notes,
                submitRegistration
        );
    }

    /**
     * Configuring Radio Input
     */
    private void configureRadioGroup(){
        needTestKit.setLabel("Test Kit Needed");
        needTestKit.setItems("Yes", "No");
        needTestKit.setValue("No");
    }

    /**
     * Configuring Registration Button for Online Testing
     */
    private void configureRegistrationButton(){
        submitRegistration = new Button("Submit");
        submitRegistration.addClickListener(e -> {
            if (startTime.getValue().toLocalTime().getHour() < 8  || startTime.getValue().toLocalTime().getHour() >= 21){
                Notification.show("Online testing only available during 0800 - 2100");
            }
            else {
                confirmDialog.setHeader("Confirm Appointment");
                confirmDialog.setText("Do you want to book an online testing appointment at "+startTime.getValue().toLocalDate()+ " " +startTime.getValue().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
                confirmDialog.setRejectable(true);
                confirmDialog.setRejectText("Discard");
                confirmDialog.setConfirmText("Save");
                confirmDialog.addConfirmListener(event -> {
                    // send POST to make booking
                    VaadinSession ui = UI.getCurrent().getSession();
                    User user = new Customer(ui.getAttribute("userId").toString(), ui.getAttribute("userGivenName").toString(), ui.getAttribute("userFamilyName").toString(), ui.getAttribute("userName").toString(), ui.getAttribute("userPhoneNumber").toString());
                    ObjectNode mappedResponse = null;
                    try {
                        HttpResponse<String> response = new SystemBookingMethod().registerBooking(startTime.getValue().toString(),user,notes.getValue());
                        mappedResponse = new ObjectMapper().readValue(response.body(), ObjectNode.class);
                        TextArea label = new TextArea();

                        Dialog responseDialog = new Dialog();
                        label.setWidth("500px");
                        label.clear();
                        // need QR code display to user
                        if (needTestKit.getValue().equals("Yes"))
                            label.setValue("Booking ID: "+ mappedResponse.get("id").asText() + "\nPIN code: "+mappedResponse.get("smsPin").asText()+"\nQR code: "+mappedResponse.get("additionalInfo").get("qrcode").asText()+"\nMeeting Url: "+mappedResponse.get("additionalInfo").get("url").asText());
                            // QR code not needed for user
                        else
                            label.setValue("Booking ID: "+ mappedResponse.get("id").asText() + "\nPIN code: "+mappedResponse.get("smsPin").asText()+"\nMeeting Url: "+mappedResponse.get("additionalInfo").get("url").asText());
                        Button closeButton = new Button(new Icon("lumo", "cross"), (ev) -> responseDialog.close());
                        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                        responseDialog.add(closeButton,label);

                        if (response.statusCode()==201){
                            Notification noti = Notification.show("Application submitted");
                            noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            this.clearFields();
                            responseDialog.open();
                        }
                        else
                            throw new Exception("Appointment Adding Failed "+ response.body());

                    }
                    catch (Exception exception) {
                        Notification noti = Notification.show("Appointment failed");
                        noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        System.out.println(exception);
                    }
                });
                confirmDialog.open();
            }
        });
    }

    /**
     * Clear out all fields
     */
    private void clearFields(){
        notes.clear();
    }

    /**
     * Configuring Data Time Picker
     */
    private void configureDateTimePicker() {
        startTime = new DateTimePicker();
        startTime.setLabel("Appointment Date and Time");
        startTime.setAutoOpen(true);
        startTime.setMin(LocalDateTime.now());
        startTime.setValue(LocalDateTime.now().plusDays(1));
        startTime.setMax(LocalDateTime.now().plusDays(90));
    }

}
