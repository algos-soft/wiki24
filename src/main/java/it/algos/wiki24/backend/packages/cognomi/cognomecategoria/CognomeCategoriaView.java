package it.algos.wiki24.backend.packages.cognomi.cognomecategoria;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.view.*;
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
 * Date: Tue, 19-Mar-2024
 * Time: 07:36
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("CognomiCategoria")
@Route(value = "cognomecategoria", layout = MainLayout.class)
@AView(menuGroupName = "cognomi")
public class CognomeCategoriaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public CognomeCategoriaView(@Autowired CognomeCategoriaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
