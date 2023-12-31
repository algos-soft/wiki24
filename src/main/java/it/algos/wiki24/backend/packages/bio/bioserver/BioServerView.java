package it.algos.wiki24.backend.packages.bio.bioserver;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 21:41
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("BioServer")
@Route(value = "bioserver", layout = MainLayout.class)
@AView(menuGroupName = "bio")
public class BioServerView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public BioServerView(@Autowired BioServerModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
