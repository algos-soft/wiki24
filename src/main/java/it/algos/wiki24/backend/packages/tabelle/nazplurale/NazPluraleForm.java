package it.algos.wiki24.backend.packages.tabelle.nazplurale;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.form.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NazPluraleForm extends CrudForm {


    public NazPluraleForm(NazPluraleModulo crudModulo, NazPluraleEntity entityBean, CrudOperation operation) {
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

}// end of CrudForm class
