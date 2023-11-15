package it.algos.base24.backend.packages.crono.giorno;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Giorni")
@Route(value = "giorno", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.crono)
public class GiornoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public GiornoView(@Autowired GiornoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
