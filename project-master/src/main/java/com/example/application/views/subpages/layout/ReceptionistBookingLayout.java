package com.example.application.views.subpages.layout;

import com.example.application.data.entity.BookingMethod.FacilityBookingMethod;
import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.TestingSite.TestingSiteCollection;
import com.example.application.data.entity.User.User;
import com.example.application.data.entity.User.UserCollection;
import com.example.application.data.entity.User.UserNotifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This is a layout for receptionist booking where receptionist can
 * - create a new user for booking,
 * - verify existing user for booking
 */
public class ReceptionistBookingLayout extends VerticalLayout {
    private final TestingSiteCollection collection = new TestingSiteCollection();
    private ComboBox<TestingSite> testingSite;
    private DateTimePicker startTime;
    private final TextArea notes = new TextArea("Notes");
    private final TextField userName = new TextField("Username");
    private final PasswordField userPassword = new PasswordField("Password");;
    private final TextField userGivenName = new TextField("Given Name");
    private final TextField userFamilyName = new TextField("Family Name");
    private final IntegerField userPhoneNumber = new IntegerField("Phone Number");
    private Button submitRegistration;
    private final FormLayout registrationCommonForm = new FormLayout();
    private final Dialog bookingFeedbackDialog = new Dialog();
    private final TextArea bookingFeedbackContent = new TextArea();
    private final VerticalLayout content = new VerticalLayout();
    private final Tab tabExistUser = new Tab("Existing User");
    private final Tab tabNewUser = new Tab("New User");
    private final Tabs registrationSubTabs = new Tabs(tabExistUser, tabNewUser);

    public ReceptionistBookingLayout(){
        this.clearFields();
        this.configureRegistrationNotification();
        this.configureRegistrationTabs();
        this.configureDateTimePicker();
        this.configureComboBox();
        this.populateComboBox();
        this.configureRegistrationButton();
        this.configureRegistrationForm();
        add(registrationSubTabs,content,registrationCommonForm);
    }

    /**
     * Configuring Registration Form
     */
    private void configureRegistrationForm(){
        registrationCommonForm.setColspan(notes, 2);
        registrationCommonForm.setColspan(submitRegistration, 2);
        registrationCommonForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("30%",2)
        );
        registrationCommonForm.add(
                testingSite, startTime,
                notes,
                submitRegistration
        );
    }

    /**
     * Configuring Registration Tab
     */
    private void configureRegistrationTabs(){
        // First form
        FormLayout existingUserLayout = new FormLayout();
        existingUserLayout.add(
                userName, userPassword
        );

        // Second form
        FormLayout newUserLayout = new FormLayout();

        content.setSpacing(false);
        content.add(existingUserLayout);
        registrationSubTabs.addSelectedChangeListener(event -> {
                    this.clearFields();
                    content.removeAll();
                    newUserLayout.removeAll();
                    existingUserLayout.removeAll();
                    if (event.getSelectedTab().equals(tabNewUser)){
                        newUserLayout.add(
                                userGivenName, userFamilyName,
                                userName, userPhoneNumber,
                                userPassword
                        );
                        newUserLayout.setColspan(userPassword, 2);
                        content.add(newUserLayout);
                    }
                    else if (event.getSelectedTab().equals(tabExistUser)){
                        existingUserLayout.add(
                                userName, userPassword
                        );
                        existingUserLayout.setColspan(userPassword, 1);
                        content.add(existingUserLayout);
                    }
                }
        );
    }

    /**
     * Configuring Registration Notification
     */
    private void configureRegistrationNotification(){
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> bookingFeedbackDialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        bookingFeedbackDialog.add(closeButton, bookingFeedbackContent);
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
                // Get User ID via http request
                // if statement here to check the tab, post to verify or post to create
                UserCollection collection = new UserCollection();
                User user = null;
                try {
                    if (registrationSubTabs.getSelectedTab().equals(tabExistUser))
                        user = collection.verifyUserId(userName.getValue(), userPassword.getValue());
                    else
                        user = collection.addUserService(userGivenName.getValue(), userFamilyName.getValue(), userName.getValue(), userPassword.getValue(), userPhoneNumber.getValue().toString(), true, false,false);
                }
                catch (Exception exception){
                    Notification.show(exception.toString());
                }
                HttpResponse<String> response = null;
                ObjectNode mappedResponse = null;
                try {
                    response = new FacilityBookingMethod().registerBooking(testingSite.getValue(),startTime.getValue().format(DateTimeFormatter.ISO_DATE_TIME), user,notes.getValue());
                    mappedResponse = new ObjectMapper().readValue(response.body(),ObjectNode.class);
                    testingSite.getValue().setWaitingTime((Integer.parseInt(testingSite.getValue().getWaitingTime().substring(0,testingSite.getValue().getWaitingTime().length()-3))+10)+"min");
                } catch (Exception exception){
                    System.out.println("Error creating: " + exception);
                }
                if (mappedResponse!=null){
                    bookingFeedbackContent.setWidth("500px");
                    bookingFeedbackContent.setEnabled(false);
                    bookingFeedbackContent.clear();
                    bookingFeedbackContent.setValue("Booking ID: "+ mappedResponse.get("id").asText() + "\nPIN: "+ mappedResponse.get("smsPin").asText());
                    bookingFeedbackDialog.open();

                    String message = "CREATED - "+"Booking: "+ mappedResponse.get("id") +" | USER: "+ user.getUserName() + " " + startTime.getValue().format(DateTimeFormatter.ISO_DATE_TIME);
                    ArrayList<String> testingSiteIds = new ArrayList<>();
                    testingSiteIds.add(testingSite.getValue().getId());
                    notifyReceptionists(message,testingSiteIds);
                    Notification noti = Notification.show("Application submitted");

                    noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
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

    /**
     * Validate the fields in Registration tab layout
     */
    private boolean validateFields(){
        boolean validation = true;
        if(registrationSubTabs.getSelectedTab().equals(tabExistUser)){
            if(userName.isEmpty() || userPassword.isEmpty()){
                validation = false;
            }
        }
        else if (registrationSubTabs.getSelectedTab().equals(tabNewUser)){
            if(userName.isEmpty() || userPassword.isEmpty() || userGivenName.isEmpty() || userFamilyName.isEmpty() || userPhoneNumber.isEmpty()){
                validation = false;
            }
        }
        if (startTime.getValue().toLocalTime().getHour() < Integer.parseInt(testingSite.getValue().getOperationTime().substring(0,2)) || startTime.getValue().toLocalTime().getHour() >= Integer.parseInt(testingSite.getValue().getOperationTime().substring(7,9)))
            validation = false;
        return validation;
    }

    /**
     * Clear out all fields
     */
    private void clearFields(){
        userName.clear();
        userPassword.clear();
        userFamilyName.clear();
        userGivenName.clear();
        userPhoneNumber.clear();
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
     * Configuring Data Time Picker to valid range
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
}
