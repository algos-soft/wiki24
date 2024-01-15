package it.algos.wiki24.ui;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 15-Jan-2024
 * Time: 18:04
 */
public class AlternativeMainLayout extends AppLayout {


    public AlternativeMainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = new SideNav();
        SideNavItem itemSection=new SideNavItem("Pippoz", GiorniView.class);
        nav.addItem(itemSection);

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }
}
