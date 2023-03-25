package it.algos.wiki24.backend.packages.attsingolare;

import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 16:49
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttSingolareBackend extends WikiBackend {


    public AttSingolareBackend() {
        super(AttSingolare.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.downloadNazSingolare;
        super.durataDownload = WPref.downloadNazSingolareTime;
        super.lastElaborazione = WPref.elaboraNazSingolare;
        super.durataElaborazione = WPref.elaboraNazSingolareTime;

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.secondi;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AttSingolare newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AttSingolare newEntity(final String keyPropertyValue, String plurale) {
        AttSingolare newEntityBean = AttSingolare.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .numBio(0)
                .build();

        return (AttSingolare) super.fixKey(newEntityBean);
    }


    @Override
    public AttSingolare findById(final String keyID) {
        return (AttSingolare) super.findById(keyID);
    }

    @Override
    public AttSingolare findByKey(final String keyValue) {
        return (AttSingolare) super.findByKey(keyValue);
    }

    @Override
    public AttSingolare findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public AttSingolare findByProperty(final String propertyName, final Object propertyValue) {
        return (AttSingolare) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<AttSingolare> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<AttSingolare> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<AttSingolare> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<AttSingolare> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<AttSingolare> findAll() {
        return this.findAllNoSort();
    }


    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Plurale attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività <br>
     */
    public WResult download() {
        WResult result = super.download();
        long inizio = System.currentTimeMillis();
        String moduloAttività = PATH_MODULO + PATH_PLURALE + ATT_LOWER;
        int tempo = 3;
        int size = 0;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_DOWLOAD, NazSingolare.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, moduloAttività)));

        size += downloadAttivita(moduloAttività);
        result.setIntValue(size);

        result = super.fixDownload(result, inizio, "attività");
        result = this.elabora();

        return result;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le nazionalità <br>
     *
     * @param moduloAttività della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadAttivita(String moduloAttività) {
        int size = 0;
        String singolare;
        String plurale;
        AEntity entityBean;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloAttività);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolare = entry.getKey();
                plurale = entry.getValue();

                if (singolare.equals("parrucchiera")) {
                    int a=87;
                }

                entityBean = insert(newEntity(singolare, plurale));
                if (entityBean != null) {
                    size++;
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", singolare))));
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloAttività);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return size;
    }


    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();
        long inizio = System.currentTimeMillis();
        int tempo = 2;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_ELABORA, NazSingolare.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));

        for (AttSingolare attivita : findAll()) {
            attivita.numBio = bioBackend.countAttivitaSingola(attivita.nome);
            update(attivita);
        }

        return super.fixElabora(result, inizio, "attivita");
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(false);

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result.setValido(true);
            result = this.download();
            return result;
        }
        else {
            return result;
        }
    }

}// end of crud backend class
