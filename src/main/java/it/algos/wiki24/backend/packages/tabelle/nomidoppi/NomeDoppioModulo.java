package it.algos.wiki24.backend.packages.tabelle.nomidoppi;

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
 * Date: Thu, 01-Feb-2024
 * Time: 15:01
 */
@Service
public class NomeDoppioModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeDoppioModulo() {
        super(NomeDoppioEntity.class, NomeDoppioView.class, NomeDoppioList.class, NomeDoppioForm.class);
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
    public NomeDoppioEntity newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomeDoppioEntity newEntity(String code) {
        NomeDoppioEntity newEntityBean = NomeDoppioEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .build();

        return (NomeDoppioEntity) fixKey(newEntityBean);
    }

    @Override
    public NomeDoppioEntity findByKey(final Object keyPropertyValue) {
        return (NomeDoppioEntity) super.findByKey(keyPropertyValue);
    }

}// end of CrudModulo class
