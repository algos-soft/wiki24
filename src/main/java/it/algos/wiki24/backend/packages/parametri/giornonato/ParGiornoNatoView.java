package it.algos.wiki24.backend.packages.parametri.giornonato;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Dec-2023
 * Time: 11:56
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParGiornoNato")
@Route(value = "pargiornonato", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParGiornoNatoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParGiornoNatoView(@Autowired ParGiornoNatoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
