package it.algos.base24.backend.packages.anagrafica.via;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

import javax.inject.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: mer, 25-ott-2023
 * Time: 07:46
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Vie")
@Route(value = "via", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.anagrafe)
public class ViaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ViaView(@Autowired ViaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
