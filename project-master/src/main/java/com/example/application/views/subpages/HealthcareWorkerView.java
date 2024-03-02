package com.example.application.views.subpages;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.*;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.notification.Notification;

@Route(value = "/healthcareworker")
@PageTitle("Healthcare Worker")
/**
 * This is the page where interview was conducted
 */
public class HealthcareWorkerView extends VerticalLayout {
    private RadioButtonGroup<String> firstQuestion = new RadioButtonGroup<>();
    private RadioButtonGroup<String> secondQuestion = new RadioButtonGroup<>();
    private RadioButtonGroup<String> thirdQuestion = new RadioButtonGroup<>();
    private RadioButtonGroup<String> fourthQuestion = new RadioButtonGroup<>();
    private RadioButtonGroup<String> fifthQuestion = new RadioButtonGroup<>();
    private RadioButtonGroup<String> sixthQuestion = new RadioButtonGroup<>();
    private Button submitButton = new Button("Submit");
    private Button cancel = new Button("Cancel");
    private final HtmlComponent br = new HtmlComponent("br");
    private final HtmlComponent br2 = new HtmlComponent("br");
    private final H1 title = new H1("Interview Questions for Preferred Testing Method");
    private final Hr hr1 = new Hr();
    private final Hr hr2 = new Hr();
    private final Hr hr3 = new Hr();
    private final Hr hr4 = new Hr();
    private final Hr hr5 = new Hr();
    private final Hr hr6 = new Hr();

    /**
     * Populating page with questions and choices
     */
    public HealthcareWorkerView() {
        this.configureSubmitButton();

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttons = new HorizontalLayout(submitButton, cancel);

        // First interview question
        Span label_first = new Span("1. Are you exhibiting 2 or more symptoms as listed below?");

        UnorderedList ul1 = new UnorderedList(
                new ListItem("Fever"),
                new ListItem("Chills"),
                new ListItem("Shivering"),
                new ListItem("Body Ache"),
                new ListItem("Headache"),
                new ListItem("Sore Throat"),
                new ListItem("Nausea or vomiting"),
                new ListItem("Diarrhea"),
                new ListItem("Fatigue"),
                new ListItem("Runny nose or nasal congestion")
        );
        firstQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        firstQuestion.setLabel("Choose one:");
        firstQuestion.setRequired(true);
        firstQuestion.setItems("Yes", "No");
        firstQuestion.setValue("No");

        // Second interview question
        Span label_second = new Span("2. Besides the above, are you exhibiting any of the symptoms listed below?");
        UnorderedList ul2 = new UnorderedList(
                new ListItem("Cough"),
                new ListItem("Difficulty Breathing"),
                new ListItem("Loss of smell"),
                new ListItem("Loss of taste")
        );
        secondQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        secondQuestion.setLabel("Choose one:");
        secondQuestion.setRequired(true);
        secondQuestion.setItems("Yes", "No");
        secondQuestion.setValue("No");

        // Third interview question
        Span label_third = new Span("3. Have you attended any event / areas associated with known COVID-19 cluster?");
        thirdQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        thirdQuestion.setLabel("Choose one:");
        thirdQuestion.setRequired(true);
        thirdQuestion.setItems("Yes", "No");
        thirdQuestion.setValue("No");

        // Fourth interview question
        Span label_fourth = new Span("4. Have you travelled abroad within the last 14 days?");
        fourthQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        fourthQuestion.setLabel("Choose one:");
        fourthQuestion.setRequired(true);
        fourthQuestion.setItems("Yes", "No");
        fourthQuestion.setValue("No");

        // Fifth interview question
        Span label_fifth = new Span("5. Have you had any close contact with any confirmed or suspected COVID-19 cases within the last 14 days?");
        fifthQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        fifthQuestion.setLabel("Choose one:");
        fifthQuestion.setRequired(true);
        fifthQuestion.setItems("Yes", "No");
        fifthQuestion.setValue("No");

        // Sixth interview question
        Span label_sixth = new Span("6. Are you a MOH COVID-19 volunteer in the last 14 days?");
        sixthQuestion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        sixthQuestion.setLabel("Choose one:");
        sixthQuestion.setRequired(true);
        sixthQuestion.setItems("Yes", "No");
        sixthQuestion.setValue("No");

        // Extra response
        TextArea extra_question = new TextArea();
        extra_question.setWidthFull();
        extra_question.setLabel("Anything else you feel like we should know? Leave blank, if none.");

        add(    title,
                label_first, ul1, firstQuestion, hr1,
                label_second, ul2, secondQuestion, hr2,
                label_third, thirdQuestion, hr3,
                label_fourth, fourthQuestion, hr4,
                label_fifth, fifthQuestion, hr5,
                label_sixth, sixthQuestion, hr6,
                extra_question, buttons, br, br2

        );

    }

    /**
     * Configuring Submit Interview Button logic
     */
    private void configureSubmitButton(){
        submitButton = new Button("Submit");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(e -> {
                    if (! validateFields()) {
                        Notification.show("Please answer all the questions");
                    }
                    else {
                        if (firstQuestion.getValue().equals("No") &&
                                secondQuestion.getValue().equals("No") &&
                                thirdQuestion.getValue().equals("No") &&
                                fourthQuestion.getValue().equals("No") &&
                                fifthQuestion.getValue().equals("No") &&
                                sixthQuestion.getValue().equals("No")){

                            Dialog dialogSafe = new Dialog();
                            VerticalLayout verticalLayoutNoRisk = new VerticalLayout();
                            Button ratButton = new Button("Book a RAT test");
                            Button closeDialogButton = new Button("Close");

                            dialogSafe.setModal(true);
                            dialogSafe.setDraggable(true);

                            ratButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                            ratButton.addClickListener(g -> {
                                ratButton.getUI().ifPresent(ui ->
                                        ui.navigate("systembooking"));
                                dialogSafe.close();
                            });

                            closeDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
                            closeDialogButton.addClickListener(h -> dialogSafe.close());

                            verticalLayoutNoRisk.add(new H1("Customer is not at risk!"));
                            verticalLayoutNoRisk.add(new Label("RAT test is advised"));
                            verticalLayoutNoRisk.add(new HorizontalLayout(ratButton, closeDialogButton));

                            dialogSafe.add(verticalLayoutNoRisk);
                            dialogSafe.open();

                        }
                        else {
                            Notification.show("Customer is AT RISK! Advise customer to do a PCR test.");
                            Dialog dialogRisk = new Dialog();
                            VerticalLayout verticalLayoutAtRisk = new VerticalLayout();
                            Button pcrButton = new Button("Book a PCR test");
                            Button closeDialogButton = new Button("Close");

                            dialogRisk.setModal(true);
                            dialogRisk.setDraggable(true);

                            pcrButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                            pcrButton.addClickListener(g -> {
                                dialogRisk.close();
                                pcrButton.getUI().ifPresent(ui ->
                                        ui.navigate(""));
                            });

                            closeDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
                            closeDialogButton.addClickListener(h -> dialogRisk.close());

                            verticalLayoutAtRisk.add(new H1("Customer is at risk!"));
                            verticalLayoutAtRisk.add(new Label("PCR test is advised"));
                            verticalLayoutAtRisk.add(new HorizontalLayout(pcrButton, closeDialogButton));

                            dialogRisk.add(verticalLayoutAtRisk);
                            dialogRisk.open();

                        }

                    }
                }
        );
    }

    /**
     * Validate the fields in choices/fields provided
     */
    private boolean validateFields(){
        return !firstQuestion.isEmpty() &&
                !secondQuestion.isEmpty() &&
                !thirdQuestion.isEmpty() &&
                !fourthQuestion.isEmpty() &&
                !fifthQuestion.isEmpty() &&
                !sixthQuestion.isEmpty();
    }
}
