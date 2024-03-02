package com.example.application.views.subpages.layout;

import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.TestingSite.TestingSiteCollection;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * This is a layout  contain a list that shows all existing Testing site
 */
public class TestingSiteListLayout extends VerticalLayout {
    Grid<TestingSite> grid = new Grid<>(TestingSite.class,false);
    TextField filterText = new TextField();
    TestingSiteCollection collection = new TestingSiteCollection();

    /**
     * populate the layout with components
     */
    public TestingSiteListLayout(){
        configureGrid();
        populateList();
        add(getToolbar(), grid);
        setSizeFull();
    }

    /**
     * Configuring Grid/List
     */
    private void configureGrid() {
        grid.addColumn(site -> site.getName()).setHeader("Name").setWidth("20%").setSortable(true);
        grid.addColumn(site -> site.getDescription()).setHeader("Description").setWidth("20%").setTextAlign(ColumnTextAlign.START);
        grid.addColumn(site -> site.getAddress().getStreets()).setHeader("Street").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(site -> site.getAddress().getSuburb()).setHeader("Suburb").setAutoWidth(true).setTextAlign(ColumnTextAlign.START).setSortable(true);
        grid.addColumn(site -> site.getAddress().getPostcode()).setHeader("Postcode").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(site -> site.getAddress().getState()).setHeader("State").setAutoWidth(true).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(site -> site.getFacilityType()).setHeader("Facility Type").setAutoWidth(true).setTextAlign(ColumnTextAlign.END).setSortable(true);
        grid.addColumn(site -> site.getOperationTime()).setHeader("Operation Time").setAutoWidth(true);
        grid.addColumn(site -> site.getWaitingTime()).setHeader("Waiting Time").setAutoWidth(true);
        grid.setItems(collection.getCollection());
        grid.setSizeFull();
        grid.setHeight("700px");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    /**
     * Populate the Grid of testing site
     */
    private void populateList(){
        grid.setItems(collection.getCollection());
    }

    /**
     * Update the grid based on the keyword typed in the search field
     */
    private void updateList(String keyword) {
        grid.setItems(collection.searchCollection(keyword));
    }

    /**
     * Configuring Toolbar for the grid
     */
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search site by suburb or facility type");
        filterText.setWidth("300px");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        Button searchButton = new Button("Search");
        searchButton.addClickListener(e -> updateList(filterText.getValue()));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, searchButton);

        return toolbar;
    }
}
