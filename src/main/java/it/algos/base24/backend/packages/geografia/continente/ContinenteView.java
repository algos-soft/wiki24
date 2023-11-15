package it.algos.base24.backend.packages.geografia.continente;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 29-ott-2023
 * Time: 06:59
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Continenti")
@Route(value = "continente", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.geografia)
public class ContinenteView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ContinenteView(@Autowired ContinenteModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
