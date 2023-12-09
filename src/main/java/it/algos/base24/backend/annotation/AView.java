package it.algos.base24.backend.annotation;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import org.vaadin.lineawesome.*;

import java.lang.annotation.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: ven, 13-ott-2017
 * Time: 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class and interface.
public @interface AView {

    /**
     * (Optional) nome visibile nel menu (maiuscolo) solo se diverso dal nome della route <br>
     * Di default lo stesso nome della route con iniziale maiuscolo <br>
     */
    String menuName() default VUOTA;

    MenuGroup menuGroup() default MenuGroup.nessuno;

    String menuGroupName() default VUOTA;

    boolean menuAutomatico() default true;

    LineAwesomeIcon menuIcon() default LineAwesomeIcon.FOLDER;

}// end of interface annotation