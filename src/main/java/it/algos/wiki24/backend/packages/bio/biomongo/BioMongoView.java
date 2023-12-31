package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 25-Dec-2023
 * Time: 21:21
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("BioMongo")
@Route(value = "biomongo", layout = MainLayout.class)
@AView(menuGroupName = "bio")
public class BioMongoView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public BioMongoView(@Autowired BioMongoModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
