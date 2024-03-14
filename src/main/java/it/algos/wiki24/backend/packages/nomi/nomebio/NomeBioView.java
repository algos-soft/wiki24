package it.algos.wiki24.backend.packages.nomi.nomebio;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 11-Mar-2024
 * Time: 15:03
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NomiBio")
@Route(value = "nomebio", layout = MainLayout.class)
@AView(menuGroupName = "nomi")
public class NomeBioView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NomeBioView(@Autowired NomeBioModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
