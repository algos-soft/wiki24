package it.algos.base24.backend.packages.anagrafica.persona;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Thu, 01-Feb-2024
 * Time: 12:28
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Persone")
@Route(value = "persona", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.anagrafe)
public class PersonaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public PersonaView(@Autowired PersonaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
