package it.algos.wiki24.backend.packages.nomi.nomemodulo;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 09-Mar-2024
 * Time: 13:53
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NomiModulo")
@Route(value = "nomemodulo", layout = MainLayout.class)
@AView(menuGroupName = "nomi")
public class NomeModuloView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NomeModuloView(@Autowired NomeModuloModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
