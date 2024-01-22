package it.algos.wiki24.backend.packages.tabelle.anni;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 07:47
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Anni")
@Route(value = "anni", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class AnnoWikiView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public AnnoWikiView(@Autowired AnnoWikiModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
