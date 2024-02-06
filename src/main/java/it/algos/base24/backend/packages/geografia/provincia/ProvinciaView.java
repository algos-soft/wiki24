package it.algos.base24.backend.packages.geografia.provincia;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 03-Feb-2024
 * Time: 09:48
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Province italiane")
@Route(value = "provincia", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.geografia)
public class ProvinciaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ProvinciaView(@Autowired ProvinciaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
