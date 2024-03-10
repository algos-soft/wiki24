package it.algos.wiki24.backend.packages.nomi.nome;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
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
 * Date: Sat, 09-Mar-2024
 * Time: 13:54
 */
@Service
public class NomeModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeModulo() {
        super(NomeEntity.class, NomeView.class, NomeList.class, NomeForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public NomeEntity creaIfNotExists(String sigla, String descrizione) {
        if (existByKey(sigla)) {
            return null;
        }
        else {
            return (NomeEntity) insert(newEntity(sigla, descrizione));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeEntity newEntity() {
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
    public NomeEntity newEntity(final String sigla, final String descrizione) {
        NomeEntity newEntityBean = NomeEntity.builder()
                .sigla(textService.isValid(sigla) ? sigla : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (NomeEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeEntity> findAll() {
        return super.findAll();
    }

    @Override
    public NomeEntity findByKey(final Object keyPropertyValue) {
        return (NomeEntity) super.findByKey(keyPropertyValue);
    }

}// end of CrudModulo class
