package it.algos.wiki24.backend.packages.tabelle.giorni;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import it.algos.wiki24.ui.*;
import jakarta.annotation.security.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.lineawesome.*;


import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 17:08
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Giorni")
@Route(value = "giorni", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class GiorniView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public GiorniView(@Autowired GiorniModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
