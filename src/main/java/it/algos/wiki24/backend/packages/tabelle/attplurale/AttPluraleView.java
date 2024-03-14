package it.algos.wiki24.backend.packages.tabelle.attplurale;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 10-Dec-2023
 * Time: 11:54
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("AttivitàPlurali")
@Route(value = "attplurale", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class AttPluraleView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public AttPluraleView(@Autowired AttPluraleModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
