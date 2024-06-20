package com.mainmodule.www.views;

import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.view.Impl.ContactViewImpl;
import com.customermodule.www.view.sideNav.ContractorNav;
//import com.mainmodule.www.views.contact.ContactListView;
//import com.mainmodule.www.views.contractor.sideNav.ContactorSideNav;
import com.mainmodule.www.views.user.UserListView;
import com.profilemodule.www.config.security.AuthenticatedUser;
import com.profilemodule.www.model.entity.CityEntity;
import com.profilemodule.www.model.entity.GroupEntity;
import com.profilemodule.www.model.entity.LanguageEntity;
import com.profilemodule.www.model.entity.UserEntity;
import com.profilemodule.www.model.repository.UserRepository;
import com.profilemodule.www.model.service.LanguageService;
import com.profilemodule.www.model.service.UserService;
import com.profilemodule.www.shared.clock.DigitalClock;
import com.profilemodule.www.shared.i18n.CustomI18nProvider;
import com.profilemodule.www.shared.i18n.Intl;
import com.profilemodule.www.shared.profileImg.ProfileImage;
import com.profilemodule.www.view.Impl.city.CityListView;
import com.profilemodule.www.view.Impl.group.GroupListView;
import com.profilemodule.www.view.Impl.language.LanguageListView;
import com.profilemodule.www.view.Impl.profile.ProfileView;
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
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;
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

    private RouteConfiguration configuration;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserRepository userRepository, UserService userService, LanguageService languageService) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userRepository = userRepository;
        this.userService = userService;
        this.languageService = languageService;
        CustomI18nProvider.user = authenticatedUser;
        setPrimarySection(Section.DRAWER);

        configuration =
                RouteConfiguration.forApplicationScope();

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

        if(accessChecker.hasAccess(ContactViewImpl.class)) {
            if (!configuration.isRouteRegistered(ContactViewImpl.class)) {
                configuration.setRoute(ContactEntity.VIEW_ROUTE, ContactViewImpl.class, MainLayout.class);
            }
            final SideNavItem item = new SideNavItem(ContactEntity.TITLE, ContactViewImpl.class, VaadinIcon.QUESTION.create());
            nav.addItem(item);
        }

        if(accessChecker.hasAccess(ContractorNav.class)) {
            final SideNavItem contractorItem = ContractorNav.initNav(MainLayout.class, accessChecker);
            nav.addItem(contractorItem);
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<UserEntity> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserEntity userEntity = maybeUser.get();

            Avatar avatar = new Avatar(userEntity.getName());
            avatar.getStyle().set("border-radius", "15%");
//            if(userEntity.getProfilePicture() != null){
//                StreamResource resource = new StreamResource("profile-pic",
//                        () -> new ByteArrayInputStream(userEntity.getProfilePicture()));
//                avatar.setImageResource(resource);
//            }
        authenticatedUser.get().ifPresent(entity -> {
            avatar.setImageResource(ProfileImage.getImgStream(entity));
        });

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
                final String usersListTitle = CustomI18nProvider.getTranslationStatic(UserEntity.getTranslateTitle());
                userName.getSubMenu().addItem(usersListTitle, e -> {
                    getUI().ifPresent(ui -> ui.navigate(UserEntity.VIEW_ROUTE));
                })
                        .addComponentAsFirst(UserEntity.icon.create());
            }

            if(accessChecker.hasAccess(GroupListView.class)) {
                if (!configuration.isRouteRegistered(GroupListView.class)) {
                    configuration.setRoute(GroupEntity.VIEW_ROUTE, GroupListView.class, MainLayout.class);
                }
                final String groupListTitle = CustomI18nProvider.getTranslationStatic(GroupEntity.getTranslateTitle());
                userName.getSubMenu().addItem(groupListTitle, e -> {
                   getUI().ifPresent(ui -> ui.navigate(GroupEntity.VIEW_ROUTE));
                })
                        .addComponentAsFirst(GroupEntity.icon.create());
            }

            if(accessChecker.hasAccess(LanguageListView.class)) {
                if (!configuration.isRouteRegistered(LanguageListView.class)) {
                    configuration.setRoute(LanguageEntity.VIEW_ROUTE, LanguageListView.class, MainLayout.class);
                }
                final String languageListTitle = LanguageEntity.getTranslateTitle();
                       userName.getSubMenu().addItem(languageListTitle, e -> {
                    getUI().ifPresent(ui -> ui.navigate(LanguageEntity.VIEW_ROUTE));
                })
                               .addComponentAsFirst(LanguageEntity.icon.create());
            }

//            if(accessChecker.hasAccess(CityView.class)) {
            if(accessChecker.hasAccess(CityListView.class)) {
                if (!configuration.isRouteRegistered(CityListView.class)) {
                    configuration.setRoute(CityEntity.VIEW_ROUTE, CityListView.class, MainLayout.class);
                }

                final String cityListTitle = CityEntity.getTranslateTitle();
                userName.getSubMenu().addItem(cityListTitle, e -> {
                    getUI().ifPresent(ui -> {
//                        ui.getPage().setTitle(cityListTitle); // Set page title
                        ui.navigate(CityEntity.VIEW_ROUTE);
                    });
                })
                        .addComponentAsFirst(CityEntity.icon.create());
            }


            final String profileTitle = CustomI18nProvider.getTranslationStatic(Intl.getProfile());
            if (!configuration.isRouteRegistered(ProfileView.class)) {
                configuration.setRoute(ProfileView.VIEW_ROUTE, ProfileView.class, MainLayout.class);
            }

            userName.getSubMenu().addItem(profileTitle, e -> {
                getUI().ifPresent(ui -> ui.navigate(ProfileView.VIEW_ROUTE));
            })
                    .addComponentAsFirst(VaadinIcon.USER.create());

            final String signOutTitle = CustomI18nProvider.getTranslationStatic(Intl.getSignOut());
            userName.getSubMenu().addItem(signOutTitle, e -> {
                authenticatedUser.logout();
            })
                    .addComponentAsFirst(VaadinIcon.EXIT_O.create());

            layout.add(userMenu);
        }
        else {
            Anchor loginLink = new Anchor("login", CustomI18nProvider.getTranslationStatic(Intl.getSignIn()));
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

    private void makeHoverLeaveEffect(SideNavItem sideNavItem) {
        sideNavItem.getElement().addEventListener("mouseover", event -> {
            sideNavItem.getStyle().set("padding-left", "10px");
        });
        sideNavItem.getElement().addEventListener("mouseleave", event -> {
            sideNavItem.getStyle().remove("padding-left");
        });
    }


}
