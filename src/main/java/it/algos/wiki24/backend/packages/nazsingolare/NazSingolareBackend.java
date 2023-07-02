package it.algos.wiki24.backend.packages.nazsingolare;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.upload.moduliSoloAdmin.*;
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

        super.lastReset = null;
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

    public NazSingolare newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA);
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

    @Override
    public List<NazSingolare> findAllByPlurale(String plurale) {
        return super.findAllByPlurale(plurale);
    }

    @Override
    public List<String> findAllForKeyByPlurale(String plurale) {
        return findAllByPlurale(plurale).stream().map(naz -> naz.nome).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllDistinctByPlurali() {
        List<String> lista = new ArrayList<>();
        Set<String> setPlurali = new HashSet();
        List<NazSingolare> listaAll = findAll();

        for (NazSingolare nazionalita : listaAll) {
            if (setPlurali.add(nazionalita.plurale)) {
                lista.add(nazionalita.plurale);
            }
        }

        Collections.sort(lista);
        return lista;
    }


    public Map<String, String> getMappaSingolarePlurale() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (NazSingolare naz : findAll()) {
            mappa.put(naz.nome, naz.plurale);
        }

        return mappa;
    }

    public List<String> findAllForKeyBySingolare(final String keyValueSingolare) {
        NazSingolare nazSingolare = findByKey(keyValueSingolare);
        return findAllForKeyByPlurale(nazSingolare.plurale);
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Cancella tutto e scarica 1 modulo wiki: Singolare/Plurale nazionalità. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola nazionalità singolare. <br>
     * Upload -> Non previsto. <br>
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Plurale nazionalità
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nazionalità singolari
        deleteAll();

        //--Scarica 1 modulo wiki: Singolare/Plurale nazionalità.
        result = downloadNazionalita(result);

        return super.fixResetDownload(result);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale nazionalità <br>
     * Crea le nazionalità <br>
     *
     * @return entities create
     */
    public AResult downloadNazionalita(AResult result) {
        String moduloNazionalità = PATH_MODULO + PATH_PLURALE + NAZ_LOWER;
        String singolare;
        String plurale;
        List lista = new ArrayList();
        AEntity entityBean;

        if (wikiApiService == null) {
            wikiApiService = appContext.getBean(WikiApiService.class);
        }
        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloNazionalità);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolare = entry.getKey();
                plurale = entry.getValue();

                entityBean = insert(newEntity(singolare, plurale));
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
            super.fixDownloadModulo(moduloNazionalità);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloNazionalità);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public WResult uploadModulo() {
        WResult result = appContext.getBean(UploadModuloPluraleNazionalita.class).uploadOrdinatoSenzaModifiche();
        return super.fixRiordinaModulo(result);
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Cancella tutto e scarica 1 modulo wiki: Singolare/Plurale nazionalità. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola nazionalità singolare. <br>
     * Upload -> Non previsto. <br>
     * <p>
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        WResult result = super.elabora();

        for (NazSingolare nazionalita : findAll()) {
            nazionalita.numBio = bioBackend.countNazionalitaSingola(nazionalita.nome);
            update(nazionalita);
        }

        return super.fixElabora(result);
    }


}// end of crud backend class
