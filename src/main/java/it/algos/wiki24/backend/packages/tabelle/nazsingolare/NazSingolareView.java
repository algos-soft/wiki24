package it.algos.wiki24.backend.packages.tabelle.nazsingolare;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 06:55
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("NazionalitàSingolari")
@Route(value = "nazsingolare", layout = MainLayout.class)
@AView(menuGroupName = "tabelle")
public class NazSingolareView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NazSingolareView(@Autowired NazSingolareModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
