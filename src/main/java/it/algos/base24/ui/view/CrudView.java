package it.algos.base24.ui.view;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.orderedlayout.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 02-Aug-2023
 * Time: 17:39
 * <p>
 * Vista iniziale e principale di un package <br>
 * Mantiene il riferimento al CrudModulo del package <br>
 * Presenta (eventualmente) la CrudList <br>
 * Presenta (eventualmente) il CrudForm <br>
 * La sottoclasse concreta specifica usa @Route e viene chiamata dal menu generale <br>
 */
public abstract class CrudView extends VerticalLayout {

    @Autowired
    public ApplicationContext appContext;

    protected CrudModulo currentCrudModulo;


    public CrudView(final CrudModulo currentCrudModulo) {
        this.currentCrudModulo = currentCrudModulo;
    }


    @PostConstruct
    public void postConstruct() {
        this.setPadding(false);
        this.setSpacing(false);
        this.setMargin(false);

        this.addCrudLista();
    }

    /**
     * Aggiunge la lista/Grid alla view <br>
     */
    protected void addCrudLista() {
        CrudList crudList = currentCrudModulo.creaList();
        if (crudList != null) {
            crudList.setSizeFull();
            this.add(crudList);
        }
    }

    /**
     * Ricarica interamente la pagina del browser (non solo la Grid) <br>
     */
    protected void reload() {
        UI.getCurrent().getPage().reload();
    }


}// end of abstract CrudView class
