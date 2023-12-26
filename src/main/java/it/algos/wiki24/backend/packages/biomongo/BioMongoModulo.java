package it.algos.wiki24.backend.packages.biomongo;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 25-Dec-2023
 * Time: 21:21
 */
@Service
public class BioMongoModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public BioMongoModulo() {
        super(BioMongoEntity.class, BioMongoList.class, BioMongoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public BioMongoEntity newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public BioMongoEntity newEntity(String code) {
        BioMongoEntity newEntityBean = BioMongoEntity.builder()
                .build();

        return (BioMongoEntity) fixKey(newEntityBean);
    }

}// end of CrudModulo class
