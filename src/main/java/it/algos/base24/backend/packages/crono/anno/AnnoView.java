package it.algos.base24.backend.packages.crono.anno;

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
 * Time: 18:52
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Anni")
@Route(value = "anno", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.crono)
public class AnnoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public AnnoView(@Autowired AnnoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
