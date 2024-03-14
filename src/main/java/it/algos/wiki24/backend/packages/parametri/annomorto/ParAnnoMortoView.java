package it.algos.wiki24.backend.packages.parametri.annomorto;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Jan-2024
 * Time: 15:20
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParAnnoMorto")
@Route(value = "parannomorto", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParAnnoMortoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParAnnoMortoView(@Autowired ParAnnoMortoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
