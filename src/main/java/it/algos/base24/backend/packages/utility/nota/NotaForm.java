package it.algos.base24.backend.packages.utility.nota;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.form.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaForm extends CrudForm {

    public NotaForm() {
        super();
    }

    public NotaForm(NotaModulo crudModulo, NotaEntity entityBean, CrudOperation operation) {
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
        super.numResponsiveStepColumn = 1;
    }

    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addFields() {
        super.addFields();

        if (crudOperation == CrudOperation.add) {
            mappaFields.get("fatto").setEnabled(false);
        }
        else {
            mappaFields.get("typeLog").setEnabled(false);
            mappaFields.get("typeLevel").setEnabled(false);
            mappaFields.get("inizio").setEnabled(false);
            mappaFields.get("typeLog").setEnabled(false);
            mappaFields.get("descrizione").setEnabled(!((Checkbox) mappaFields.get("fatto")).getValue());
        }
    }

}// end of CrudForm class
