package it.algos.wiki24.backend.packages.nazplurale;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Mar-2023
 * Time: 19:20
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NazPluraleBackend extends WikiBackend {


    public NazPluraleBackend() {
        super(NazPlurale.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.downloadNazPlurale;
        super.durataDownload = WPref.downloadNazPluraleTime;
        super.lastElaborazione = WPref.elaboraNazPlurale;
        super.durataElaborazione = WPref.elaboraNazPluraleTime;
        super.lastUpload = WPref.uploadNazPlurale;
        super.durataUpload = WPref.uploadNazPluraleTime;
        super.nextUpload = WPref.uploadNazPluralePrevisto;
        super.lastStatistica = WPref.statisticaNazPlurale;

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NazPlurale newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA);
    }

    @Override
    public NazPlurale newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome    (obbligatorio, unico)
     * @param lista   (obbligatorio, unico)
     * @param nazione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NazPlurale newEntity(final String nome, List<NazSingolare> singolari, final String lista, final String nazione) {
        NazPlurale newEntityBean = NazPlurale.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .singolari(singolari)
                .lista(textService.isValid(lista) ? lista : null)
                .nazione(textService.isValid(nazione) ? nazione : null)
                .numBio(0)
                .numSingolari(0)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        newEntityBean.lista = textService.primaMaiuscola(newEntityBean.nome);
        return (NazPlurale) super.fixKey(newEntityBean);
    }


    @Override
    public NazPlurale findById(final String keyID) {
        return (NazPlurale) super.findById(keyID);
    }

    @Override
    public NazPlurale findByKey(final String keyValue) {
        return (NazPlurale) super.findByKey(keyValue);
    }

    @Override
    public NazPlurale findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public NazPlurale findByProperty(final String propertyName, final Object propertyValue) {
        return (NazPlurale) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<NazPlurale> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NazPlurale> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NazPlurale> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NazPlurale> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<NazPlurale> findAll() {
        return this.findAllNoSort();
    }

    public List<String> findAllForKeyBySingolari(final String keyValue) {
        NazPlurale naz = findByKey(keyValue);
        List<NazSingolare> listaSingolare = naz != null ? naz.singolari : null;
        return listaSingolare != null ? listaSingolare.stream().map(singolo -> singolo.nome).collect(Collectors.toList()) : null;
    }

    public List<String> findSingolari(final String keyValue) {
        return findAllForKeyBySingolari(keyValue);
    }

    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Link nazionalità
     * <p>
     * Cancella la (eventuale) precedente lista di attività <br>
     */
    public WResult download() {
        WResult result = super.download();
        long inizio = System.currentTimeMillis();
        String moduloLink = PATH_MODULO + PATH_LINK + NAZ_LOWER;
        int tempo = 3;
        int size = 0;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_DOWLOAD, NazPlurale.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, moduloLink)));

        size += downloadNazionalitaLink(moduloLink);
        result.setIntValue(size);

        result = super.fixDownload(result, "nazionalità");
        result = this.elabora();

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Link nazionalità <br>
     * Aggiunge il link alla pagina wiki della nazionalità <br>
     *
     * @param moduloLink della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadNazionalitaLink(String moduloLink) {
        int cont = 0;
        String plurale;
        String nazione;
        NazPlurale nazPlurale;
        Map<String, String> mappaSingolareNazione = wikiApiService.leggeMappaModulo(moduloLink);
        Map<String, String> mappaSingolarePlurale = nazSingolareBackend.getMappaSingolarePlurale();
        Map<String, String> mappaPluraleNazione = new LinkedHashMap<>();

        if (mappaSingolareNazione != null && mappaSingolareNazione.size() > 0) {
            for (String key : mappaSingolareNazione.keySet()) {
                nazione = mappaSingolareNazione.get(key);
                if (mappaSingolarePlurale.containsKey(key)) {
                    plurale = mappaSingolarePlurale.get(key);
                    mappaPluraleNazione.put(plurale, nazione);
                }
            }
        }

        if (mappaPluraleNazione != null && mappaPluraleNazione.size() > 0) {
            for (String key : mappaPluraleNazione.keySet()) {
                nazPlurale = findByKey(key);
                if (nazPlurale != null) {
                    nazPlurale.nazione = mappaPluraleNazione.get(key);
                    update(nazPlurale);
                }
                else {
                    message = String.format("Manca la nazionalità %s", key);
                    System.out.println(message);
                }
            }
        }

        return cont;
    }


    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();
        long inizio = System.currentTimeMillis();
        int tempo = 75;
        List<NazSingolare> singolari;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_ELABORA, NazPlurale.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));

        for (NazPlurale nazPlurale : findAll()) {
            singolari = new ArrayList<>();
            nazPlurale.numBio = bioBackend.countNazionalita(nazPlurale.nome);
            singolari.addAll(nazSingolareBackend.findAllByPlurale((nazPlurale.nome)));
            nazPlurale.singolari = singolari;

            nazPlurale.numBio = bioBackend.countNazPlurale(nazPlurale.nome);
            nazPlurale.esisteLista = esistePaginaLista(nazPlurale.nome);
            nazPlurale.numSingolari = singolari.size();

            update(nazPlurale);
        }

        return super.fixElabora(result, inizio, "nazionalità");
    }


    /**
     * Controlla l'esistenza della pagina wiki relativa a questa nazionalità (lista) <br>
     */
    public boolean esistePaginaLista(String plurale) {
        String wikiTitle = LINK_PAGINA_NAZIONALITA + textService.primaMaiuscola(plurale);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
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
        String collectionName = result.getTarget();
        List<NazSingolare> listaSingolari = nazSingolareBackend.findAll();
        List<String> listaPlurali;
        AEntity entityBean;
        List<AEntity> lista;

        if (nazSingolareBackend.count() < 1) {
            AResult resultSingolare = nazSingolareBackend.resetOnlyEmpty(logInfo);
            if (resultSingolare.isErrato()) {
                logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'nazSingolare'")).usaDb());
                return result.fine();
            }
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota && listaSingolari != null && listaSingolari.size() > 0) {
            result.setValido(true);
            lista = new ArrayList<>();
            listaPlurali = new ArrayList<>();
            for (NazSingolare nazSingola : listaSingolari) {
                if (!listaPlurali.contains(nazSingola.plurale)) {
                    listaPlurali.add(nazSingola.plurale);
                }
            }
            if (listaPlurali.size() > 0) {
                for (String nome : listaPlurali) {
                    entityBean = insert(newEntity(nome));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                        result.setValido(false);
                    }
                }
                result.setIntValue(lista.size());
                result.setLista(lista);
            }
        }
        else {
            return result;
        }

        result = super.fixResult(result, clazzName, collectionName, lista, logInfo);
        result = this.elabora();
        return result;
    }

}// end of crud backend class
