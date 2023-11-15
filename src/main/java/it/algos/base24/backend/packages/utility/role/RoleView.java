package it.algos.base24.backend.packages.utility.role;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 12:17
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Ruoli")
@Route(value = "role", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.utility)
public class RoleView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public RoleView(@Autowired RoleModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
