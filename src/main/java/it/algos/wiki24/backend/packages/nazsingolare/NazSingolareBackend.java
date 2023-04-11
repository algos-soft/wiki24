package it.algos.wiki24.backend.packages.nazsingolare;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
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
 * Date: Sat, 18-Mar-2023
 * Time: 15:17
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NazSingolareBackend extends WikiBackend {


    public NazSingolareBackend() {
        super(NazSingolare.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.resetNazSingolare;
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
    public NazSingolare newEntity() {
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
    public NazSingolare newEntity(final String keyPropertyValue, String plurale) {
        NazSingolare newEntityBean = NazSingolare.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .numBio(0)
                .build();

        return (NazSingolare) super.fixKey(newEntityBean);
    }


    @Override
    public NazSingolare findById(final String keyID) {
        return (NazSingolare) super.findById(keyID);
    }

    @Override
    public NazSingolare findByKey(final String keyValue) {
        return (NazSingolare) super.findByKey(keyValue);
    }

    @Override
    public NazSingolare findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public NazSingolare findByProperty(final String propertyName, final Object propertyValue) {
        return (NazSingolare) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<NazSingolare> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NazSingolare> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NazSingolare> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NazSingolare> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<NazSingolare> findAll() {
        return this.findAllNoSort();
    }

    public List<NazSingolare> findAllByPlurale(String plurale) {
        return super.findAllByProperty("plurale", plurale);
    }

    public List<String> findAllForSingolareByPlurale(String plurale) {
        return findAllByPlurale(plurale).stream().map(nazSin -> nazSin.nome).collect(Collectors.toList());
    }

    public Map<String, String> getMappaSingolarePlurale() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (NazSingolare naz : findAll()) {
            mappa.put(naz.nome, naz.plurale);
        }

        return mappa;
    }

    public WResult download() {
        AResult result = resetForcing();
        return WResult.aResult(result);
    }

    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Plurale nazionalità
     * <p>
     * Cancella la (eventuale) precedente lista di attività <br>
     */
    public WResult downloadReset() {
        WResult result = super.download();
        int tempo = WPref.downloadNazSingolareTime.getInt();

        //        String moduloNazionalita = PATH_MODULO + PATH_PLURALE + NAZ_LOWER;
        //        int tempo = 3;
        //        int size = 0;
        //        List<AEntity> lista;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_DOWLOAD, NazSingolare.class.getSimpleName(), tempo, unitaMisuraDownload);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, "nazionalità singolari")));

        result = downloadNazionalita(result);
        result.typeResult(AETypeResult.downloadValido);

        //        lista = downloadNazionalita(moduloNazionalita);
        //        result.setIntValue(size);
        //
        //        result.setIntValue(size);
        //        result.setLista(lista);
        //        public WResult fixDownload(WResult result, String clazzName, String collectionName, String modulo, List lista) {

        //        result = super.fixDownload(result, entityClazz.getSimpleName(), lista);
        //        result = this.elabora();

        result = super.fixDownload(result, "nazionalità singolari");
        return result;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale nazionalità <br>
     * Crea le nazionalità <br>
     *
     * @return entities create
     */
    public WResult downloadNazionalita(WResult result) {
        String moduloNazionalità = PATH_MODULO + PATH_PLURALE + NAZ_LOWER;
        //        List<AEntity> lista = null;
        //        int size = 0;
        String singolare;
        String plurale;
        List lista = new ArrayList();
        AEntity entityBean;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloNazionalità);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                entityBean = null;
                singolare = entry.getKey();
                plurale = entry.getValue();

                entityBean = insert(newEntity(singolare, plurale));
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
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloNazionalità);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result = appContext.getBean(UploadModuloPluraleNazionalita.class).uploadOrdinatoSenzaModifiche();
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

        for (NazSingolare nazionalita : findAll()) {
            nazionalita.numBio = bioBackend.countNazionalita(nazionalita.nome);
            update(nazionalita);
        }

        return super.fixElabora(result, inizio, "nazionalità singolari");
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
        String clazzName = entityClazz.getSimpleName();

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result = this.downloadReset();
        }
        else {
            return result;
        }

        return super.fixReset(result, clazzName, logInfo);
    }

}// end of crud backend class
