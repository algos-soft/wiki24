package it.algos.wiki24.backend.packages.parnome;

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
 * Date: Sat, 30-Dec-2023
 * Time: 19:06
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParNome")
@Route(value = "parnome", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParNomeView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParNomeView(@Autowired ParNomeModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
