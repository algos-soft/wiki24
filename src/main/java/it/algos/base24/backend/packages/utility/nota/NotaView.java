package it.algos.base24.backend.packages.utility.nota;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 02-nov-2023
 * Time: 09:14
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Note")
@Route(value = "nota", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.utility)
public class NotaView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public NotaView(@Autowired NotaModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
