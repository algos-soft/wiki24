package it.algos.wiki24.backend.packages.tabelle.attsingolare;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;


/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("AttivitàSingolari")
@Route(value = "attsingolare", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class AttSingolareView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public AttSingolareView(@Autowired AttSingolareModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
