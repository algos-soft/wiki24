package it.algos.base24.backend.packages.utility.logs;

import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 16-Jan-2024
 * Time: 18:34
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Logs")
@Route(value = "logger", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.utility)
public class LogView extends CrudView {


    /**
     * Regola il crudModulo associato a questa @Route e lo passa alla superclasse <br>
     */
    public LogView(@Autowired LogModulo crudModulo) {
        super(crudModulo);
    }


}// end of @Route CrudView class
