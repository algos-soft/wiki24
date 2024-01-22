package it.algos.wiki24.backend.packages.tabelle.giorni;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 17:08
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Giorni")
@Route(value = "giorni", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class GiornoWikiView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public GiornoWikiView(@Autowired GiornoWikiModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
