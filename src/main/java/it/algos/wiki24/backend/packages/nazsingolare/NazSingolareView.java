package it.algos.wiki24.backend.packages.nazsingolare;

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
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NazioniSingolari")
@Route(value = "nazsingolare", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.nessuno)
public class NazSingolareView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NazSingolareView(@Autowired NazSingolareModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
