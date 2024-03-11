package com.mainmodule.www.views;

import com.mainmodule.www.views.user.UserListView;
import com.profilemodule.www.config.security.AuthenticatedUser;
import com.profilemodule.www.model.entity.UserEntity;
import com.profilemodule.www.model.repository.UserRepository;
import com.profilemodule.www.view.user.UserListViewImpl;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.Optional;

@Route(value = "user")
//@RouteAlias("")
@PermitAll
public class MainLayout extends AppLayout implements RouterLayout, BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private final UserRepository userRepository;
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserRepository userRepository) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userRepository = userRepository;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent(UI.getCurrent());
    }

    private void addDrawerContent() {
        final String title = "AIOLDS";
        H1 appName = new H1(title);
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

//        if(accessChecker.hasAccess(UserListView.class)) {
//
//                final SideNavItem sideNavItem = new SideNavItem("asd", UserListView.class, LumoIcon.USER.create());
//                nav.addItem(sideNavItem);
//        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<UserEntity> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserEntity userEntity = maybeUser.get();

            Avatar avatar = new Avatar(userEntity.getName());
//            if(userEntity.getProfilePicture() != null){
//                StreamResource resource = new StreamResource("profile-pic",
//                        () -> new ByteArrayInputStream(userEntity.getProfilePicture()));
//                avatar.setImageResource(resource);
//            }


//            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");


            MenuItem userName = userMenu.addItem("");

            Div div = new Div();
            div.add(avatar);
            div.add(userEntity.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);

            if(accessChecker.hasAccess(UserListView.class)) {
                final String usersListTitle = UserEntity.NAME;
                userName.getSubMenu().addItem(usersListTitle, e -> {
                    getUI().ifPresent(ui -> ui.navigate(UserListViewImpl.VIEW));
                });
            }


//            final String profileTitle = languageProvider.getTranslation("Profile", Locale.of(userLocale));
            final String profileTitle = "Profile";
            userName.getSubMenu().addItem(profileTitle, e -> {
                getUI().ifPresent(ui -> ui.navigate("profile"));
            });
//            final String signOutTitle = languageProvider.getTranslation("SignOut", Locale.of(userLocale));
            final String signOutTitle = "Sign out";
            userName.getSubMenu().addItem(signOutTitle, e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        }
        else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    private void addHeaderContent(UI ui) {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

//        viewTitle = new H2();
//        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Add the bell icon to the header
        Span numberOfNotifications = new Span("4");
        numberOfNotifications.getElement().getThemeList().addAll(
                Arrays.asList("badge", "error", "primary", "small", "pill"));
        numberOfNotifications.getStyle().set("position", "absolute")
                .set("transform", "translate(-40%, -85%)");

        Button bellBtn = new Button(VaadinIcon.BELL_O.create());
        bellBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        bellBtn.getElement().appendChild(numberOfNotifications.getElement());
        bellBtn.getElement().getStyle().set("flex-grow", "1");

        Div sampleNotification = new Div(new Text("Show notifications here"));
        sampleNotification.getStyle().set("padding", "var(--lumo-space-l)");

        ContextMenu menu = new ContextMenu();
        menu.setOpenOnClick(true);
        menu.setTarget(bellBtn);
        menu.add(sampleNotification);


        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "35");



//        final Button button = notification.initUI(userId, userLocale, ui);
        addToNavbar(false, toggle, spacer, bellBtn);
//        addToNavbar(bellBtn);


    }


}
