package it.algos.wiki24.backend.packages.parametri.luogomorto;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Jan-2024
 * Time: 18:07
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParLuogoMorto")
@Route(value = "parluogomorto", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParLuogoMortoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParLuogoMortoView(@Autowired ParLuogoMortoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
