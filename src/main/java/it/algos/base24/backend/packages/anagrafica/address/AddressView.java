package it.algos.base24.backend.packages.anagrafica.address;

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
 * Time: 11:53
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Address")
@Route(value = "address", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.anagrafe)
public class AddressView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public AddressView(@Autowired AddressModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
