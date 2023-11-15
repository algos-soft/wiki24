package it.algos.base24.backend.packages.utility.preferenza;

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
 * Time: 13:47
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Preferenze")
@Route(value = "preferenza", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.utility, menuName = "preferenze")
public class PreferenzaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public PreferenzaView(@Autowired PreferenzaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
