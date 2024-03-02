package com.example.application.views.subpages.layout;

import com.example.application.data.entity.BookingMethod.SystemBookingMethod;
import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.TestingSite.TestingSiteCollection;
import com.example.application.data.entity.User.Customer;
import com.example.application.data.entity.User.User;
import com.example.application.data.entity.User.UserNotifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This is a layout for logged in user to book for on site testing
 */
public class OnSiteBookingLayout extends VerticalLayout {
    private final ConfirmDialog confirmDialog = new ConfirmDialog();
    private final FormLayout siteTestingForm = new FormLayout();
    private final TextArea notes = new TextArea("Notes");
    private final TestingSiteCollection collection = new TestingSiteCollection();
    private ComboBox<TestingSite> testingSite;
    private DateTimePicker startTime;
    private Button submitRegistration;

    /**
     * populate the layout with components
     */
    public OnSiteBookingLayout() {
        this.configureDateTimePicker();
        this.configureComboBox();
        this.populateComboBox();
        this.configureRegistrationButton();
        this.configureRegistrationForm();

        add(siteTestingForm);
    }

    /**
     * Configuring Registration Form
     */
    private void configureRegistrationForm() {
        siteTestingForm.setColspan(notes, 2);
        siteTestingForm.setColspan(submitRegistration, 2);
        siteTestingForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30%", 2)
        );
        siteTestingForm.add(
                testingSite, startTime,
                notes,
                submitRegistration
        );
    }

    /**
     * Configuring Combo Box of Testing Site
     */
    private void configureComboBox(){
        testingSite = new ComboBox<>("TestingSite");
        testingSite.setRequired(true);
        testingSite.addValueChangeListener(event -> {
            if (testingSite.getValue() != null){
                submitRegistration.setEnabled(true);
            }
            else {
                submitRegistration.setEnabled(false);
            }
        });
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

    /**
     * Populate the combo box of testing site
     */
    private void populateComboBox(){
        testingSite.setItems(collection.getCollection());
        testingSite.setItemLabelGenerator(TestingSite::getName);
    }

    /**
     * Validate the fields in Registration tab layout
     */
    private boolean validateFields(){
        boolean validation = true;
        if (startTime.getValue().toLocalTime().getHour() < Integer.parseInt(testingSite.getValue().getOperationTime().substring(0,2)) || startTime.getValue().toLocalTime().getHour() >= Integer.parseInt(testingSite.getValue().getOperationTime().substring(7,9)))
            validation = false;
        return validation;
    }

    /**
     * Configuring Registration Button
     */
    private void configureRegistrationButton(){
        submitRegistration = new Button("Submit");
        submitRegistration.setEnabled(false);
        submitRegistration.addClickListener(e -> {
            if (!validateFields()){
                Notification.show("Booking time is not within operation hour");
            }
            else {
                confirmDialog.setHeader("Confirm Appointment");
                confirmDialog.setText("Do you want to book an online testing appointment at " + startTime.getValue().toLocalDate() + " " + startTime.getValue().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
                confirmDialog.setRejectable(true);
                confirmDialog.setRejectText("Discard");
                confirmDialog.setConfirmText("Save");
                confirmDialog.addConfirmListener(event -> {
                    VaadinSession ui = UI.getCurrent().getSession();
                    User user = new Customer(ui.getAttribute("userId").toString(), ui.getAttribute("userGivenName").toString(), ui.getAttribute("userFamilyName").toString(), ui.getAttribute("userName").toString(), ui.getAttribute("userPhoneNumber").toString());
                    HttpResponse<String> response = null;
                    ObjectNode mappedResponse = null;
                    try {
                        response = new SystemBookingMethod().registerBooking(testingSite.getValue(), startTime.getValue().format(DateTimeFormatter.ISO_DATE_TIME), user, notes.getValue());
                        mappedResponse = new ObjectMapper().readValue(response.body(), ObjectNode.class);
                        if (response.statusCode()==201){

                            String message = "CREATED - "+"Booking: "+ mappedResponse.get("id") +" | USER: "+ user.getUserName() + " " + startTime.getValue().format(DateTimeFormatter.ISO_DATE_TIME);
                            ArrayList<String> testingSiteIds = new ArrayList<>();
                            testingSiteIds.add(testingSite.getValue().getId());
                            notifyReceptionists(message,testingSiteIds);

                            Notification noti = Notification.show("Application submitted");
                            noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        }
                        else
                            throw new Exception("Appointment Adding Failed");
                    } catch (Exception exception){
                        Notification noti = Notification.show("Appointment failed");
                        noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        System.out.println(exception);
                    }
                    TextArea label = new TextArea();
                    Dialog responseDialog = new Dialog();
                    label.setWidth("500px");
                    label.clear();
                    label.setValue("Booking ID: "+ mappedResponse.get("id").asText() + "\nPIN code: "+mappedResponse.get("smsPin").asText()+"\nQR code: "+mappedResponse.get("additionalInfo").get("qrcode").asText());
                    testingSite.getValue().setWaitingTime((Integer.parseInt(testingSite.getValue().getWaitingTime().substring(0, testingSite.getValue().getWaitingTime().length() - 3)) + 10) + "min");
                    Button closeButton = new Button(new Icon("lumo", "cross"), (ev) -> responseDialog.close());
                    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                    responseDialog.add(closeButton,label);
                    responseDialog.open();
                });
                confirmDialog.open();
            }
        });
    }

    /**
     * Helper method to notify receptionist based on the testing site
     * @param notificationMessage
     * @param testingSiteIds List of testing site IDs where the updated receptionists' notifications are updated
     */
    private void notifyReceptionists(String notificationMessage, ArrayList<String> testingSiteIds){
        // Initialize observable
        UserNotifier un = new UserNotifier();

        // Updates all subscribed users working in testingSiteIds  with notification "updatednoti00" (can check in API interactive documentation)
        try {
            un.updateUsers(notificationMessage, testingSiteIds);
        } catch (Exception exception){
            System.out.println(exception);
        }
    }

}
