package it.algos.wiki24.backend.packages.parametri.attivita;

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
 * Date: Tue, 02-Jan-2024
 * Time: 19:01
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParAttivita")
@Route(value = "parattivita", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParAttivitaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParAttivitaView(@Autowired ParAttivitaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
