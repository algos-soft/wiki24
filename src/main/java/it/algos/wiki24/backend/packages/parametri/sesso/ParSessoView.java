package it.algos.wiki24.backend.packages.parametri.sesso;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 27-Dec-2023
 * Time: 07:43
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("ParSesso")
@Route(value = "parsesso", layout = MainLayout.class)
@AView(menuGroupName = "parametri")
public class ParSessoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public ParSessoView(@Autowired ParSessoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
