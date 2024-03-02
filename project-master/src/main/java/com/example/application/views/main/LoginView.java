package com.example.application.views.main;

import com.example.application.data.entity.User.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "")
/**
 * This is the Landing page of user
 */
public class LoginView extends HorizontalLayout {
    private VerticalLayout loginLayout = new VerticalLayout();
    private VerticalLayout dialogLayout = new VerticalLayout();
    private Dialog redirectOptions = new Dialog();
    private H1 title = new H1();
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private UserCollection uc = new UserCollection();
    private Button loginButton = new Button("Log In");
    private Button redirectToHealthcareWorkerView = new Button("Test Recommendation");
    private Button redirectToReceptionistView = new Button("Make a Booking (On-site Tests only)");
    private Button redirectToCustomerView = new Button("Make a Booking (On-site Tests and Online Tests)");

    /**
     * Populating the Page with username field, password field and submit button
     */
    public LoginView() {
        this.loginUser();

        title.setText("COVID Testing Made Easy");

        username.setPrefixComponent(VaadinIcon.USER.create());
        username.setLabel("Username");
        username.setRequired(true);

        password.setRevealButtonVisible(false);
        password.setLabel("Password");
        password.setRevealButtonVisible(true);
        password.setRequired(true);

        loginLayout.setSizeFull();
        loginLayout.setAlignItems(Alignment.CENTER);
        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        loginLayout.add(title, username, password, loginButton);

        add(loginLayout);

    }

    /**
     * Main logic for interaction
     */
    public void loginUser() {
        redirectOptions = new Dialog();

        dialogLayout.add(new H1("Choose where you want to go: "));
        dialogLayout.add(new Hr());

        redirectToHealthcareWorkerView = new Button("Test Recommendation");
        redirectToHealthcareWorkerView.addClickListener(g -> {
                redirectToHealthcareWorkerView.getUI().ifPresent(ui -> ui.navigate("onsiteinterview"));
                redirectOptions.close();
            }
        );

        redirectToReceptionistView = new Button("Make a Booking (On-site Tests only)");
        redirectToReceptionistView.addClickListener(h -> {
                redirectToReceptionistView.getUI().ifPresent(ui -> ui.navigate("receptionist"));
                redirectOptions.close();
            }
        );

        redirectToCustomerView = new Button("Make a Booking (On-site Tests and Online Tests)");
        redirectToCustomerView.addClickListener(i -> {
                redirectToCustomerView.getUI().ifPresent(ui -> ui.navigate("systembooking"));
                redirectOptions.close();
            }
        );

        loginButton.addClickListener(e -> {
            if (username.getValue().equals("") && password.getValue().equals("")) {
                Notification.show("Please fill in all the fields");
            }
            else {
                User user = uc.verifyUserId(username.getValue(), password.getValue());
                if (user != null) {
                    UI.getCurrent().getSession().setAttribute("userId",user.getId());
                    UI.getCurrent().getSession().setAttribute("userGivenName",user.getGivenName());
                    UI.getCurrent().getSession().setAttribute("userFamilyName",user.getFamilyName());
                    UI.getCurrent().getSession().setAttribute("userName",user.getUserName());
                    UI.getCurrent().getSession().setAttribute("userPhoneNumber",user.getPhoneNumber());
                    if (user.getClass().equals(Customer.class)) {
                        UI.getCurrent().getSession().setAttribute("role", Role.CUSTOMER);
                        UI.getCurrent().navigate("customer");
                    }
                    else if (user.getClass().equals(HealthcareWorker.class)){
                        UI.getCurrent().getSession().setAttribute("role",Role.HEALTHCAREWORKER);
                        UI.getCurrent().navigate("healthcareworker");
                    }

                    else if (user.getClass().equals(Receptionist.class)){
                        UI.getCurrent().getSession().setAttribute("role",Role.RECEPTIONIST);
                        UI.getCurrent().getSession().setAttribute("testingSiteId",((Receptionist) user).getTestingSiteId());
                        UI.getCurrent().navigate("receptionist");
                    }
                    else {
                        Notification.show("User has no role");
                    }
                }
                else {
                    Notification.show("Incorrect username and/or password");
                }
            }
        });
    }
}
