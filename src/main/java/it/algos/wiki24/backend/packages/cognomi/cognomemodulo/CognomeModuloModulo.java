package it.algos.wiki24.backend.packages.cognomi.cognomemodulo;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.logic.*;
import it.algos.wiki24.backend.logic.*;
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
 * Time: 07:35
 */
@Service
public class CognomeModuloModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public CognomeModuloModulo() {
        super(CognomeModuloEntity.class, CognomeModuloView.class, CognomeModuloList.class, CognomeModuloForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public CognomeModuloEntity creaIfNotExists(String sigla, String descrizione) {
        if (existByKey(sigla)) {
            return null;
        }
        else {
            return (CognomeModuloEntity) insert(newEntity(sigla, descrizione));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public CognomeModuloEntity newEntity() {
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
    public CognomeModuloEntity newEntity(final String sigla, final String descrizione) {
        CognomeModuloEntity newEntityBean = CognomeModuloEntity.builder()
                .nome(textService.isValid(sigla) ? sigla : null)
                .build();

        return (CognomeModuloEntity) fixKey(newEntityBean);
    }

    @Override
    public List<CognomeModuloEntity> findAll() {
        return super.findAll();
    }

    @Override
    public CognomeModuloEntity findByKey(final Object keyPropertyValue) {
        return (CognomeModuloEntity) super.findByKey(keyPropertyValue);
    }

}// end of CrudModulo class
