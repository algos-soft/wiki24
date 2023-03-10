package it.algos.vaad24.ui.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 12:48
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LayoutService extends AbstractService {

    public List<Component> getMenuItemsVaadin() {
        return getAllItem(VaadVar.menuRouteListVaadin);
    }
    public List<Component> getMenuItemsProject() {
        return getAllItem(VaadVar.menuRouteListProject);
    }

    /**
     * Tutti gli item di menu per la barra del Drawer alla partenza del programma <br>
     * Utilizza le classi Route della variabile VaadVar.menuRouteList <br>
     * Potrebbero essere modificati in seguito <br>
     * Metodo chiamato SOLO quando esiste già una sessione <br>
     *
     * @return lista degli item di menu del Drawer
     */
    public List<Component> getAllItem(List<Class<? extends Component>> lista) {
        List<Component> listaItems = new ArrayList<>();
        ListItem item;
        String menuName;
        VaadinIcon icon;
        String lineawesomeClassnames;

        if (lista != null && lista.size() > 0) {
            for (Class<? extends Component> clazz : lista) {
                menuName = annotationService.getMenuName(clazz);
                icon = annotationService.getMenuVaadinIcon(clazz);
                lineawesomeClassnames = getLineawesomeClassnames(clazz);
                item = getItem(menuName, icon, lineawesomeClassnames, clazz);
                if (item != null) {
                    listaItems.add(item);
                }
            }
        }

        return listaItems;
    }

    /**
     * Singolo item di menu per la barra del Drawer <br>
     * Metodo chiamato SOLO quando esiste già una sessione <br>
     *
     * @param menuTitle da visualizzare nel Drawer
     * @param iconClass da visualizzare nel Drawer
     * @param view      classe visibile chiamata da @Route
     *
     * @return item di menu del Drawer
     */
    public ListItem getItem(final String menuTitle, final VaadinIcon icon, final String iconClass, final Class<? extends Component> view) {
        ListItem item = new ListItem();
        RouterLink link = new RouterLink();

        // Controlla che esista una sessione
        if (VaadinService.getCurrent() == null) {
            System.out.println(VUOTA);
            System.out.println("ERRORE"); //@todo implementare logger generico per error/warn/info
            System.out.println(VUOTA);
            return null;
        }

        // Use Lumo classnames for various styling
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(view);

        Span text = new Span(menuTitle);
        // Use Lumo classnames for various styling
        text.addClassNames("font-medium", "text-s");

        if (icon != null) {
            link.add(new LineAwesomeIcon(iconClass), text);
        }
        else {
            link.add(new LineAwesomeIcon(iconClass), text);
        }
        item.add(link);

        return item;
    }

    /**
     * Fix lineawesomeClassnames <br>
     *
     * @param clazz of all types
     *
     * @return lineawesomeClassnames per l'icona del menu
     */
    private String getLineawesomeClassnames(final Class<?> clazz) {
        String prefix = "la la-";
        String lineawesomeClassnames = annotationService.getLineawesomeClassnames(clazz);

        if (!lineawesomeClassnames.contains("-")) {
            lineawesomeClassnames = prefix + lineawesomeClassnames;
        }

        return lineawesomeClassnames;
    }


    /**
     * Simple wrapper to create icons using LineAwesome iconset. See
     * https://icons8.com/line-awesome
     */
    @NpmPackage(value = "line-awesome", version = "1.3.0")
    private static class LineAwesomeIcon extends Span {

        public LineAwesomeIcon(String lineawesomeClassnames) {
            // Use Lumo classnames for suitable font size and margin
            addClassNames("me-s", "text-l");
            if (!lineawesomeClassnames.isEmpty()) {
                addClassNames(lineawesomeClassnames);
            }
        }

    }


    public Component bottomAlgos() {
        VerticalLayout bottomPlaceHolder = new VerticalLayout();
        bottomPlaceHolder.setPadding(false);
        bottomPlaceHolder.setSpacing(false);
        bottomPlaceHolder.setMargin(false);

        String message;
        double doppio = VaadVar.projectVersion;
        String nome = VaadVar.projectCurrentUpper;
        String data = VaadVar.projectDate;
        String note = VaadVar.projectNote;

        //--Locale.US per forzare la visualizzazione grafica di un punto anziché una virgola
        message = String.format(Locale.US, "Algos® - %s, %2.1f", nome, doppio);
        bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(message).color(AETypeColor.blue).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
        message = String.format(Locale.US, "%s", data);
        bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(message).color(AETypeColor.blue).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
        if (textService.isValid(note)) {
            bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(note).color(AETypeColor.blue).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
        }

        return bottomPlaceHolder;
    }

}