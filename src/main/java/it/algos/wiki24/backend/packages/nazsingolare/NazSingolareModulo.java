package it.algos.wiki24.backend.packages.nazsingolare;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 06:55
 */
@Service
public class NazSingolareModulo extends WikiModulo {

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
        return newEntity(VUOTA,VUOTA,VUOTA);
    }
    public NazSingolareEntity newEntity(String keyPropertyValue, String plurale) {
        return newEntity(keyPropertyValue,plurale,VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NazSingolareEntity newEntity(final String keyPropertyValue, String plurale,String pagina) {
        NazSingolareEntity newEntityBean = NazSingolareEntity.builder()
                .singolare(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .pagina(pagina)
                .bio(0)
                .build();

        return (NazSingolareEntity) fixKey(newEntityBean);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }


    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Plurale attività
     * Modulo:Bio/Ex attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     */
    public void download() {
        inizio = System.currentTimeMillis();

        super.deleteAll();

        this.downloadNazionalita();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale nazionalità <br>
     *
     * @return entities create
     */
    public void downloadNazionalita() {
        String modulo = TAG_MODULO + "Plurale nazionalità";
        String singolare;
        String plurale;
        Map<String, String> mappa = wikiApiService.leggeMappaModulo(modulo);

        if (mappa == null || mappa.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il %s", modulo);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (Map.Entry<String, String> entry : mappa.entrySet()) {
            singolare = entry.getKey();
            plurale = entry.getValue();
            mappaBeans.put(singolare, newEntity(singolare, plurale));
        }
    }

}// end of CrudModulo class
