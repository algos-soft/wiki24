package it.algos.base24.backend.packages.geografia.comune;

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
 * Time: 09:13
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Comuni italiani")
@Route(value = "comune", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.geografia)
public class ComuneView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ComuneView(@Autowired ComuneModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
