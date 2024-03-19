package it.algos.wiki24.backend.packages.cognomi.cognomebio;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.logic.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Mar-2024
 * Time: 07:36
 */
@Service
public class CognomeBioModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public CognomeBioModulo() {
        super(CognomeBioEntity.class, CognomeBioView.class, CognomeBioList.class, CognomeBioForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public CognomeBioEntity creaIfNotExists(String sigla, String descrizione) {
        if (existByKey(sigla)) {
            return null;
        }
        else {
            return (CognomeBioEntity) insert(newEntity(sigla, descrizione));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public CognomeBioEntity newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param sigla       (obbligatorio)
     * @param descrizione (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public CognomeBioEntity newEntity(final String sigla, final String descrizione) {
        CognomeBioEntity newEntityBean = CognomeBioEntity.builder()
                .sigla(textService.isValid(sigla) ? sigla : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (CognomeBioEntity) fixKey(newEntityBean);
    }

    @Override
    public List<CognomeBioEntity> findAll() {
        return super.findAll();
    }

    @Override
    public CognomeBioEntity findByKey(final Object keyPropertyValue) {
        return (CognomeBioEntity) super.findByKey(keyPropertyValue);
    }

}// end of CrudModulo class
