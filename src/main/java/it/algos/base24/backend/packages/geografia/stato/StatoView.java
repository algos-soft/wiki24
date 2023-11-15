package it.algos.base24.backend.packages.geografia.stato;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: mer, 25-ott-2023
 * Time: 11:42
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Stati")
@Route(value = "stato", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.geografia)
public class StatoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public StatoView(@Autowired StatoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
