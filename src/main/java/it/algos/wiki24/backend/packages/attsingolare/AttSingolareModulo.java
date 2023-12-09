package it.algos.wiki24.backend.packages.attsingolare;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 */
@Service
public class AttSingolareModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttSingolareModulo() {
        super(AttSingolareEntity.class, AttSingolareList.class, AttSingolareForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadAttSin;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttSingolareEntity newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale) {
        AttSingolareEntity newEntityBean = AttSingolareEntity.builder()
                .singolare(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .numBio(0)
                .build();

        return (AttSingolareEntity) fixKey(newEntityBean);
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
        String moduloPlurale = TAG_MODULO + "Plurale attività";
        String moduloEx = TAG_MODULO + "Ex attività";

        downloadAttivitaPlurali(moduloPlurale);
        //     downloadAttivitaExtra(moduloEx);

        super.fixDownload(inizio);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le attività <br>
     *
     * @param moduloPlurale della pagina su wikipedia
     *
     * @return entities create
     */
    public void downloadAttivitaPlurali(String moduloPlurale) {
        String singolare;
        String plurale;
        Map<String, String> mappaPlurale = wikiApiService.leggeMappaModulo(moduloPlurale);
        AttSingolareEntity newBean;

        if (mappaPlurale != null && mappaPlurale.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappaPlurale.entrySet()) {
                singolare = entry.getKey();
                plurale = entry.getValue();
                newBean = newEntity(singolare, plurale);
                insertSave(newBean);
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", moduloPlurale);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

    }

}// end of CrudModulo class
