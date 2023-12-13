package it.algos.wiki24.backend.packages.nazplurale;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
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
 * Date: Wed, 13-Dec-2023
 * Time: 08:49
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NazionalitàPlurali")
@Route(value = "nazplurale", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class NazPluraleView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NazPluraleView(@Autowired NazPluraleModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
