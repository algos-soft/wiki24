package it.algos.wiki24.backend.packages.tabelle.attplurale;

import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.form.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AttPluraleForm extends CrudForm {


    public AttPluraleForm(AttPluraleModulo crudModulo, AttPluraleEntity entityBean, CrudOperation operation) {
        super(crudModulo, entityBean, operation);
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.numResponsiveStepColumn = 3;
    }

    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addFields() {
        super.addFields();

        formLayout.setColspan(mappaFields.get("plurale"), 3);
        formLayout.setColspan(mappaFields.get("lista"), 1);
        formLayout.setColspan(mappaFields.get("pagina"), 1);
    }

}// end of CrudForm class
