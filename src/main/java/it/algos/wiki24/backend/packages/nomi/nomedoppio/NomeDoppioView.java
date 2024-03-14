package it.algos.wiki24.backend.packages.nomi.nomedoppio;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 01-Feb-2024
 * Time: 15:01
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NomiDoppi")
@Route(value = "nomedoppio", layout = MainLayout.class)
@AView(menuGroupName = "nomi")
public class NomeDoppioView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NomeDoppioView(@Autowired NomeDoppioModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
