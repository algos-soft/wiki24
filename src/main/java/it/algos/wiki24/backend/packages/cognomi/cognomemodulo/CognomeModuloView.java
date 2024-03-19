package it.algos.wiki24.backend.packages.cognomi.cognomemodulo;

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
 * Time: 07:35
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("CognomiModulo")
@Route(value = "cognomemodulo", layout = MainLayout.class)
@AView(menuGroupName = "cognomi")
public class CognomeModuloView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public CognomeModuloView(@Autowired CognomeModuloModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
