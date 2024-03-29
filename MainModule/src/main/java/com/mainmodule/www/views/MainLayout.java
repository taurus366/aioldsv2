package com.mainmodule.www.views;

import com.mainmodule.www.views.group.GroupListView;
import com.mainmodule.www.views.language.LanguageListView;
import com.mainmodule.www.views.user.UserListView;
import com.profilemodule.www.config.security.AuthenticatedUser;
import com.profilemodule.www.model.entity.GroupEntity;
import com.profilemodule.www.model.entity.LanguageEntity;
import com.profilemodule.www.model.entity.UserEntity;
import com.profilemodule.www.model.repository.UserRepository;
import com.profilemodule.www.model.service.LanguageService;
import com.profilemodule.www.model.service.UserService;
import com.profilemodule.www.shared.clock.DigitalClock;
import com.profilemodule.www.shared.i18n.CustomI18nProvider;
import com.profilemodule.www.shared.i18n.LanguageSelector;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.internal.LocaleUtil;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;



@Route(value = "user")
@RouteAlias("user")
@PermitAll
public class MainLayout extends AppLayout implements RouterLayout, BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private final UserRepository userRepository;
    private final UserService userService;

//    INJECT DEPENDENCIES
    private final LanguageService languageService;
//    INJECT DEPENDENCIES
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    }

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserRepository userRepository, UserService userService, LanguageService languageService) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userRepository = userRepository;
        this.userService = userService;
        this.languageService = languageService;
        CustomI18nProvider.user = authenticatedUser;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent(UI.getCurrent());


    }

    private void addDrawerContent() {
        final String title = "AIOLDS";
        H2 appName = new H2(title);
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
                final String usersListTitle = UserEntity.TITLE;
                userName.getSubMenu().addItem(usersListTitle, e -> {
                    getUI().ifPresent(ui -> ui.navigate(UserEntity.VIEW_ROUTE));
                })
                        .addComponentAsFirst(UserEntity.icon.create());
            }

            if(accessChecker.hasAccess(GroupListView.class)) {
                final String groupListTitle = GroupEntity.TITLE;
                userName.getSubMenu().addItem(groupListTitle, e -> {
                   getUI().ifPresent(ui -> ui.navigate(GroupEntity.VIEW_ROUTE));
                })
                        .addComponentAsFirst(GroupEntity.icon.create());
            }

            if(accessChecker.hasAccess(LanguageListView.class)) {
                final String languageListTitle = LanguageEntity.TITLE;
                       userName.getSubMenu().addItem(languageListTitle, e -> {
                    getUI().ifPresent(ui -> ui.navigate(LanguageEntity.VIEW_ROUTE));
                })
                               .addComponentAsFirst(LanguageEntity.icon.create());


            }


//            final String translationStatic = CustomI18nProvider.getTranslationStatic("welcome", languageService, authenticatedUser);
//            System.out.println(translationStatic);

//            userName.getSubMenu().addItem("test", e -> {
//                getUI().ifPresent(ui -> ui.navigate("test"));
//            });


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
        numberOfNotifications.getStyle()
                .set("position", "absolute")
                .set("transform", "translate(-40%, -85%)")
                .set("background", "red")
                .set("border-radius", "42%")
                .set("padding", "1px 4px 1px 4px")
                .set("color", "white");

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

        final DigitalClock digitalClock = new DigitalClock();
        Div clockDiv = new Div();
            clockDiv.add(digitalClock);


//        LanguageSelector languageSelector = new LanguageSelector(authenticatedUser, languageService);
//        final ComboBox<LanguageEntity> test = languageSelector.getLanguageSelectorBox("test", true);
//        clockDiv.add(test);

//        final Button button = notification.initUI(userId, userLocale, ui);
        addToNavbar(false, toggle, spacer, bellBtn, clockDiv);
//        addToNavbar(bellBtn);


    }


}
