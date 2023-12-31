package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoForm extends WikiForm {


    public BioMongoForm(BioMongoModulo crudModulo, BioMongoEntity entityBean, CrudOperation operation) {
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