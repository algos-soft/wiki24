package it.algos.wiki24.backend.packages.nazsingolare;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 */
@Service
public class NazSingolareModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NazSingolareModulo() {
        super(NazSingolareEntity.class, NazSingolareList.class, NazSingolareForm.class);
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
    public NazSingolareEntity newEntity() {
        return newEntity(VUOTA,VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NazSingolareEntity newEntity(final String keyPropertyValue, String plurale) {
        NazSingolareEntity newEntityBean = NazSingolareEntity.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .numBio(0)
                .build();

        return (NazSingolareEntity) fixKey(newEntityBean);
    }



}// end of CrudModulo class
