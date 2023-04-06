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
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

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
        return newEntity(VUOTA, VUOTA, false);
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
    public AttSingolare newEntity(final String keyPropertyValue, String plurale, boolean ex) {
        AttSingolare newEntityBean = AttSingolare.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .ex(ex)
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

    public List<String> findAllDistinctByPlurali() {
        List<String> lista = new ArrayList<>();
        Set<String> setPlurali = new HashSet();
        List<AttSingolare> listaAll = findAll();

        for (AttSingolare attivita : listaAll) {
            if (setPlurali.add(attivita.plurale)) {
                lista.add(attivita.plurale);
            }
        }

        return lista;
    }

    public List findAllByExSortKey() {
        List<AttSingolare> listaAll = findAllSortKey();
        return listaAll.stream().filter(att -> att.ex).collect(Collectors.toList());
    }

    public List findAllByNotExSortKey() {
        List<AttSingolare> listaAll = findAllSortKey();
        return listaAll.stream().filter(att -> !att.ex).collect(Collectors.toList());
    }

    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Plurale attività
     * Modulo:Bio/Ex attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     */
    public WResult download() {
        WResult result = super.download();
        int tempo = WPref.downloadAttSingolareTime.getInt();

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_DOWLOAD, AttSingolare.class.getSimpleName(), tempo, unitaMisuraDownload);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, "attività singolari")));

        result = downloadAttivitaNormale(result);
        result = downloadAttivitaEx(result);
        result.typeResult(AETypeResult.downloadValido);

        result = super.fixDownload(result, "attività singolari");

        return result;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le attività normali <br>
     *
     * @return entities create
     */
    public WResult downloadAttivitaNormale(WResult result) {
        String moduloAttività = PATH_MODULO + PATH_PLURALE + ATT_LOWER;
        String singolare;
        String plurale;
        AEntity entityBean;
        List lista = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloAttività);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                entityBean = null;
                singolare = entry.getKey();
                plurale = entry.getValue();

                try {
                    entityBean = insert(newEntity(singolare, plurale, false));
                } catch (Exception unErrore) {
                    message = String.format("Duplicate error key %", singolare);
                    System.out.println(message);
                    logger.error(new WrapLog().exception(new AlgosException(unErrore)));
                }
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", singolare))));
                }
            }
            result.setIntValue(lista.size());
            result.setLista(lista);
            result.eseguito(lista.size() > 0);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloAttività);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Ex attività <br>
     * Crea le attività ex <br>
     *
     * @return entities create
     */
    public WResult downloadAttivitaEx(WResult result) {
        String moduloEx = PATH_MODULO + PATH_EX + ATT_LOWER;
        String singolareEx;
        String singolareNew;
        AttSingolare attivita;
        List lista = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloEx);

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolareEx = entry.getKey();
                singolareNew = TAG_EX_SPAZIO + singolareEx;
                attivita = findByKey(singolareEx);

                if (attivita != null) {
                    try {
                        attivita = (AttSingolare) insert(newEntity(singolareNew, attivita.plurale, true));
                    } catch (Exception unErrore) {
                        message = String.format("Duplicate error key %", singolareEx);
                        System.out.println(message);
                        logger.error(new WrapLog().exception(new AlgosException(unErrore)));
                    }
                }
                else {
                    logger.info(new WrapLog().message(String.format("Nelle attività base manca la definizione '%s'", singolareEx)));
                }

                if (attivita != null) {
                    lista.add(attivita);
                }
            }
            result.eseguito(lista.size() > 0);
            result.setIntValue(result.getIntValue() + lista.size());
            result.getLista().addAll(lista);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloEx);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result = download();

        if (result.isValido() && result.isEseguito()) {
            result = appContext.getBean(UploadModuloPluraleAttivita.class).result(result).upload();
        }

        if (result.isValido() && result.isEseguito()) {
            result = appContext.getBean(UploadModuoloExAttivita.class).result(result).upload();
        }

        return super.fixRiordinaModulo(result);
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
    public WResult resetOnlyEmpty(boolean logInfo) {
        return this.download();
    }

}// end of crud backend class
