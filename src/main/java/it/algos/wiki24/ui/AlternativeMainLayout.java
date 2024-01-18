package it.algos.wiki24.ui;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.*;
import static it.algos.base24.backend.boot.BaseVar.*;
import it.algos.base24.backend.service.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import jakarta.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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

        H1 title = new H1("MyApp");

        SideNav nav = new SideNav();
        for (Class clazz : menuRouteListVaadin) {
            menuName = annotationService.getMenuName(clazz);
            itemSection = new SideNavItem(menuName, clazz);
            nav.addItem(itemSection);
        }

        //        SideNavItem itemSection=new SideNavItem("Pippoz", GiorniView.class);
        //        nav.addItem(itemSection);

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }

}
