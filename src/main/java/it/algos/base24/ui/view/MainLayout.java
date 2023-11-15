package it.algos.base24.ui.view;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.*;
import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.lineawesome.*;

import java.util.*;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    @Autowired
    private AnnotationService annotationService;
    @Autowired
    private LayoutService layoutService;

    private H2 viewTitle;

    public MainLayout() {
    }

    @PostConstruct
    private void postConstruct() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("base24");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        //--Colorazione di controllo <br>
        if (Pref.debug.is() && Pref.usaBackgroundColor.is()) {
            header.getElement().getStyle().set("background-color","red");
        }
        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        List<Class> lista = new ArrayList<>();
        String menuGroup;
        String keyGroup;
        String menuName;
        LineAwesomeIcon icon;
        LinkedHashMap<String, List<SideNavItem>> mappa = new LinkedHashMap<>();
        SideNavItem sideItem;
        SideNavItem itemSection;

//        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
//        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

        if (BaseVar.menuRouteListVaadin != null && BaseVar.menuRouteListVaadin.size() > 0) {
            lista.addAll(BaseVar.menuRouteListVaadin);
        }
        if (BaseVar.menuRouteListProject != null && BaseVar.menuRouteListProject.size() > 0) {
            lista.addAll(BaseVar.menuRouteListProject);
        }

        if (lista != null && lista.size() > 0) {
            for (Class clazz : lista) {
                menuGroup = annotationService.getMenuGroup(clazz).getTag();
                menuName = annotationService.getMenuName(clazz);
                icon = annotationService.getMenuIcon(clazz);
                sideItem = new SideNavItem(menuName, clazz, icon.create());
                if (!mappa.containsKey(menuGroup)) {
                    mappa.put(menuGroup, new ArrayList<SideNavItem>());
                }
                mappa.get(menuGroup).add(sideItem);
            }
        }

        for (MenuGroup group : MenuGroup.getAllOrderedEnums()) {
            keyGroup = group.getTag();
            if (group != MenuGroup.nessuno) {
                itemSection = new SideNavItem(keyGroup);
                if (mappa.get(keyGroup) != null) {
                    for (SideNavItem item : mappa.get(keyGroup)) {
                        itemSection.addItem(item);
                    }
                    nav.addItem(itemSection);
                }
            }
            else {
                if (mappa.get(keyGroup) != null) {
                    for (SideNavItem item : mappa.get(keyGroup)) {
                        nav.addItem(item);
                    }
                }
            }
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        layout.add(layoutService.bottomAlgos());

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
