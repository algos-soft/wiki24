package it.algos.wiki24.backend.packages.parametri.cognome;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Dec-2023
 * Time: 07:23
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParCognome")
@Route(value = "parcognome", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParCognomeView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParCognomeView(@Autowired ParCognomeModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
