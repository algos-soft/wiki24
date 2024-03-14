package it.algos.wiki24.ui;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.sidenav.*;
import it.algos.vbase.backend.packages.crono.mese.*;
import it.algos.vbase.backend.service.*;
import jakarta.annotation.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 15-Jan-2024
 * Time: 18:04
 */
public class AlternativeMainLayout extends AppLayout {

    @Inject
    private AnnotationService annotationService;

    private H2 viewTitle;

    @PostConstruct
    private void postConstruct() {
        DrawerToggle toggle = new DrawerToggle();
        SideNavItem itemSection;
        String menuName;

        H1 title = new H1("Ultima");

        SideNav nav = new SideNav("Forse");
//        for (Class clazz : menuRouteListVaadin) {
//            menuName = annotationService.getMenuName(clazz);
//            itemSection = new SideNavItem(menuName, clazz);
//            nav.addItem(itemSection);
//        }

        itemSection = new SideNavItem("Pippoz", MeseView.class);
        nav.addItem(itemSection);

//        Scroller scroller = new Scroller(nav);
//        scroller.setClassName(LumoUtility.Padding.SMALL);

//        addToDrawer(scroller);
        addToDrawer(nav);
        addToNavbar(toggle, title);
    }

}
