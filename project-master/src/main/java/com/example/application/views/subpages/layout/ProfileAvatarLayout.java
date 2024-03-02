package com.example.application.views.subpages.layout;

import com.example.application.data.entity.User.Customer;
import com.example.application.data.entity.User.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

/**
 * Profile Avatar layout that shows user profile
 * Allow user to navigate to profile and sign out
 */
public class ProfileAvatarLayout extends HorizontalLayout {

    public ProfileAvatarLayout() {
        VaadinSession ui = UI.getCurrent().getSession();
        User user = new Customer(ui.getAttribute("userId").toString(), ui.getAttribute("userGivenName").toString(), ui.getAttribute("userFamilyName").toString(), ui.getAttribute("userName").toString(), ui.getAttribute("userPhoneNumber").toString());

        String name = user.getFamilyName() + " " + user.getGivenName();
        Avatar avatar = new Avatar(name);
        avatar.setAbbreviation(user.getUserName());
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);

        MenuItem menuItem = menuBar.addItem(avatar);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.addItem("Profile", e -> {
            UI.getCurrent().navigate("userprofile");
        });
        subMenu.addItem("Sign out", e -> {
            UI.getCurrent().getSession().setAttribute("userId","");
            UI.getCurrent().getSession().setAttribute("userGivenName","");
            UI.getCurrent().getSession().setAttribute("userFamilyName","");
            UI.getCurrent().getSession().setAttribute("userName","");
            UI.getCurrent().getSession().setAttribute("userPhoneNumber","");
            UI.getCurrent().navigate("");
        });

        add(menuBar);
    }
}
