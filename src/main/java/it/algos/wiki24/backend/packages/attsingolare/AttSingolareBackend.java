package it.algos.wiki24.backend.packages.attsingolare;

import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.moduli.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.data.domain.*;
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

        super.lastReset = null;
        super.lastDownload = WPref.downloadAttSingolare;
        super.durataDownload = WPref.downloadAttSingolareTime;
        super.lastElaborazione = WPref.elaboraAttSingolare;
        super.durataElaborazione = WPref.elaboraAttSingolareTime;

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

    public List<AttSingolare> findAllByPlurale(String plurale) {
        return super.findAllByPlurale(plurale);
    }

    public List<String> findAllForKeyByPlurale(String plurale) {
        return findAllByPlurale(plurale).stream().map(att -> att.nome).collect(Collectors.toList());
    }

    public List<AttSingolare> findAllByExSortKey() {
        List<AttSingolare> listaAll = findAllSortKey();
        return listaAll.stream().filter(att -> att.ex).collect(Collectors.toList());
    }

    public List<AttSingolare> findAllByNotExSortKey() {
        List<AttSingolare> listaAll = findAllSortKey();
        return listaAll.stream().filter(att -> !att.ex).collect(Collectors.toList());
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

        Collections.sort(lista);
        return lista;
    }


    public Map<String, String> getMappaSingolarePlurale() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (AttSingolare att : findAll()) {
            mappa.put(att.nome, att.plurale);
        }

        return mappa;
    }


    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Cancella tutto e scarica 2 moduli wiki: Singolare/Plurale attività, Ex attività. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola attività singolare. <br>
     * Upload -> Non previsto. <br>
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Plurale attività
     * Modulo:Bio/Ex attività
     */
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di attività singolari
        deleteAll();

        //--Scarica 2 moduli wiki: Singolare/Plurale attività, Ex attività.
        result = downloadAttivitaSingole(result);
        result = downloadAttivitaEx(result);

        return result;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le attività singolari normali <br>
     *
     * @return entities create
     */
    public AResult downloadAttivitaSingole(AResult result) {
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
                    logService.error(new WrapLog().exception(new AlgosException(unErrore)));
                }
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", singolare))));
                }
            }
            result.setIntValue(lista.size());
            result.setLista(lista);
            result.eseguito(lista.size() > 0);
            super.fixDownloadModulo(moduloAttività);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloAttività);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Ex attività <br>
     * Crea le attività ex <br>
     *
     * @return entities create
     */
    public AResult downloadAttivitaEx(AResult result) {
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
                        logService.error(new WrapLog().exception(new AlgosException(unErrore)));
                    }
                }
                else {
                    logService.info(new WrapLog().message(String.format("Nelle attività base manca la definizione '%s'", singolareEx)));
                }

                if (attivita != null) {
                    lista.add(attivita);
                }
            }
            result.eseguito(lista.size() > 0);
            result.setIntValue(result.getIntValue() + lista.size());
            result.getLista().addAll(lista);
            super.fixDownloadModulo(moduloEx);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloEx);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result;

        result = appContext.getBean(UploadModuloPluraleAttivita.class).uploadOrdinatoSenzaModifiche();
        super.fixRiordinaModulo(result);

        result = appContext.getBean(UploadModuloExAttivita.class).uploadOrdinatoSenzaModifiche();
        super.fixRiordinaModulo(result);

        return result;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Cancella tutto e scarica 2 moduli wiki: Singolare/Plurale attività, Ex attività. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola attività singolare. <br>
     * Upload -> Non previsto. <br>
     * <p>
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();

        for (AttSingolare attivita : findAll()) {
            attivita.numBio = bioBackend.countAttivitaSingola(attivita.nome);
            update(attivita);
        }

        return super.fixElabora(result);
    }

//    /**
//     * ResetOnlyEmpty -> Download. <br>
//     * Download -> Cancella tutto e scarica 2 moduli wiki: Singolare/Plurale attività, Ex attività. <br>
//     * Elabora -> Calcola le voci biografiche che usano ogni singola attività singolare. <br>
//     * Upload -> Non previsto. <br>
//     * <p>
//     * Creazione di alcuni dati <br>
//     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
//     * Viene invocato alla creazione del programma <br>
//     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
//     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
//     */
//    @Override
//    public AResult resetOnlyEmpty() {
//        AResult result = super.resetOnlyEmpty();
//
//        if (result.getTypeResult() == AETypeResult.collectionVuota) {
//            result = this.download();
//        }
//
//        return result;
//    }

}// end of crud backend class
