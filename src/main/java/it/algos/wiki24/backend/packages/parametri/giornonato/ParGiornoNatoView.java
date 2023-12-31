package it.algos.wiki24.backend.packages.parametri.giornonato;

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
 * Date: Sun, 31-Dec-2023
 * Time: 11:56
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParGiornoNato")
@Route(value = "pargiornonato", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParGiornoNatoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParGiornoNatoView(@Autowired ParGiornoNatoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class