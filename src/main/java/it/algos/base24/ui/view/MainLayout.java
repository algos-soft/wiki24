package it.algos.base24.ui.view;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseVar.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import jakarta.annotation.*;
import org.vaadin.lineawesome.*;

import javax.inject.*;
import java.util.*;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    @Inject
    private AnnotationService annotationService;

    @Inject
    private LayoutService layoutService;

    @Inject
    private TextService textService;

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
        if (projectCurrent.equals(frameworkBase) ){
            addDrawerContentBase();
        }
        else {
            addDrawerContentProject();
        }
    }


    private void addDrawerContentBase() {
        H1 appName = new H1(projectCurrent);
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        //--Colorazione di controllo <br>
        if (Pref.debug.is() && Pref.usaBackgroundColor.is()) {
            header.getElement().getStyle().set("background-color", "lightblue");
        }
        Scroller scroller = new Scroller(createNavigation(menuRouteListVaadin));

        addToDrawer(header, scroller, createFooter());
    }

    private void addDrawerContentProject() {
        H1 appName = new H1(frameworkBase);
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        H1 projectName = new H1(projectCurrent);
        projectName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header headerProject = new Header(projectName);

        //--Colorazione di controllo <br>
        if (Pref.debug.is() && Pref.usaBackgroundColor.is()) {
            header.getElement().getStyle().set("background-color", "lightblue");
        }
        if (Pref.debug.is() && Pref.usaBackgroundColor.is()) {
            headerProject.getElement().getStyle().set("background-color", "lightgreen");
        }
        Scroller scroller = new Scroller(createNavigation(menuRouteListVaadin));
        Scroller scrollerProject = new Scroller(createNavigation(menuRouteListProject));

        addToDrawer(header, scroller, headerProject, scrollerProject, createFooter());
    }

    private SideNav createNavigation(List<Class<? extends CrudView>> lista) {
        SideNav nav = new SideNav();
        String menuGroup;
        String menuName;
        LineAwesomeIcon icon;
        LinkedHashMap<String, List<SideNavItem>> mappa = new LinkedHashMap<>();
        SideNavItem sideItem;
        SideNavItem itemSection;

        if (lista != null && lista.size() > 0) {
            for (Class clazz : lista) {
                menuGroup = annotationService.getMenuGroupName(clazz);
                menuName = annotationService.getMenuName(clazz);
                icon = annotationService.getMenuIcon(clazz);
                sideItem = new SideNavItem(menuName, clazz, icon.create());
                if (!mappa.containsKey(menuGroup)) {
                    mappa.put(menuGroup, new ArrayList<SideNavItem>());
                }
                mappa.get(menuGroup).add(sideItem);
            }
        }

        mappa = fixOrderMappa(mappa);
        if (mappa != null) {
            for (String key : mappa.keySet()) {
                if (textService.isValid(key)) {
                    itemSection = new SideNavItem(key);
                    for (SideNavItem item : mappa.get(key)) {
                        itemSection.addItem(item);
                    }
                    nav.addItem(itemSection);
                }
                else {
                    for (SideNavItem item : mappa.get(key)) {
                        nav.addItem(item);
                    }
                }
            }
        }

        return nav;
    }


    private LinkedHashMap<String, List<SideNavItem>> fixOrderMappa(LinkedHashMap<String, List<SideNavItem>> mappa) {
        LinkedHashMap<String, List<SideNavItem>> mappaOrdered = new LinkedHashMap<>();
        String key;

        for (MenuGroup group : MenuGroup.getAllOrderedEnums()) {
            key = group.getTag();
            if (mappa.containsKey(key)) {
                mappaOrdered.put(key, mappa.get(key));
            }
        }

        for (String key2 : mappa.keySet()) {
            if (!mappaOrdered.containsKey(key2)) {
                mappaOrdered.put(key2, mappa.get(key2));
            }
        }

        if (mappaOrdered.containsKey(VUOTA)) {
            mappaOrdered.remove(VUOTA);
            mappaOrdered.put(VUOTA, mappa.get(VUOTA));
        }

        return mappaOrdered;
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
