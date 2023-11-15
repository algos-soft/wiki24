package it.algos.base24.backend.packages.geografia.regione;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 07-Nov-2023
 * Time: 16:47
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Regioni")
@Route(value = "regione", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.geografia)
public class RegioneView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public RegioneView(@Autowired RegioneModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
