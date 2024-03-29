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
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.upload.moduloProgettoSoloAdmin.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.bson.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;
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

        super.lastReset = null;
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
        this.unitaMisuraUpload = AETypeTime.minuti;
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

    public NazPlurale newEntity(final String keyPropertyValue, List<NazSingolare> listaSingolari) {
        return newEntity(keyPropertyValue, listaSingolari, VUOTA, VUOTA);
    }


    public NazPlurale newEntity(final Document doc) {
        NazPlurale nazPlurale = new NazPlurale();

        nazPlurale.nome = doc.getString("nome");
        nazPlurale.listaSingolari = (List<NazSingolare>) doc.get("listaSingolari");
        nazPlurale.paginaLista = doc.getString("paginaLista");
        nazPlurale.linkNazione = doc.getString("linkNazione");
        nazPlurale.numBio = doc.getInteger("numBio");
        nazPlurale.numSingolari = doc.getInteger("numSingolari");
        nazPlurale.superaSoglia = doc.getBoolean("superaSoglia");
        nazPlurale.esisteLista = doc.getBoolean("esisteLista");

        return nazPlurale;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome           (obbligatorio, unico)
     * @param listaSingolari (obbligatorio, unico)
     * @param paginaLista    (obbligatorio)
     * @param linkNazione    (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NazPlurale newEntity(final String nome, List<NazSingolare> listaSingolari, final String paginaLista, final String linkNazione) {
        NazPlurale newEntityBean = NazPlurale.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .listaSingolari(listaSingolari)
                .paginaLista(textService.isValid(paginaLista) ? paginaLista : textService.primaMaiuscola(nome))
                .linkNazione(textService.isValid(linkNazione) ? linkNazione : null)
                .numBio(0)
                .numSingolari(listaSingolari != null ? listaSingolari.size() : 0)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (NazPlurale) super.fixKey(newEntityBean);
    }


    @Override
    public NazPlurale findById(final String keyID) {
        return (NazPlurale) super.findById(keyID);
    }

    @Override
    public NazPlurale findByKey(final String keyValue) {
        NazPlurale nazPlurale = (NazPlurale) super.findByKey(textService.primaMinuscola(keyValue));

        if (nazPlurale == null) {
            nazPlurale = findDocumentByKey(textService.primaMinuscola(keyValue));
        }

        return nazPlurale;
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

    @Override
    public List<String> findAllDistinctByPlurali() {
        return findAllForKeySortKey();
    }

    //    public List<String> findAllForKeyBySingolari(final String keyValue) {
    //        NazPlurale naz = findByKey(keyValue);
    //        List<NazSingolare> listaSingolare = naz != null ? naz.listaSingolari : null;
    //        return listaSingolare != null ? listaSingolare.stream().map(singolo -> singolo.nome).collect(Collectors.toList()) : null;
    //    }
    //
    //    public List<String> findSingolari(final String keyValue) {
    //        return findAllForKeyBySingolari(keyValue);
    //    }


    public Map<String, String> getMappaPluraleNazione() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (NazPlurale naz : findAll()) {
            mappa.put(naz.nome, naz.linkNazione);
        }

        return mappa;
    }


    public List<NazSingolare> findAllFromNazionalitaSingolariByPlurale(String nomeNazionalitaPlurale) {
        List<NazSingolare> listaNazionalitaSingolePrevisteNellaNazionalitaPlurale = null;
        NazPlurale nazionalitaPlurale = this.findByKey(nomeNazionalitaPlurale);

        if (nazionalitaPlurale != null) {
            listaNazionalitaSingolePrevisteNellaNazionalitaPlurale = nazionalitaPlurale.listaSingolari;
        }

        return listaNazionalitaSingolePrevisteNellaNazionalitaPlurale;
    }

    public List<String> findAllFromNomiSingolariByPlurale(String nomeNazionalitaPlurale) {
        List<NazSingolare> listaNazSingole = findAllFromNazionalitaSingolariByPlurale(nomeNazionalitaPlurale);
        return listaNazSingole != null ? listaNazSingole.stream().map(att -> att.nome).collect(Collectors.toList()) : null;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Esegue un Download di NazSingolare. <br>
     * Download -> Crea una nuova tabella ricavandola dalle nazionalità DISTINCT di NazSingolare. <br>
     * Download -> Aggiunge un link alla paginaLista di ogni nazionalità in base al nome della nazionalità plurale. <br>
     * Download -> Scarica 1 modulo wiki: Link nazionalità <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola nazionalità plurale e la presenza o meno della pagina con la lista di ogni nazionalità <br>
     * Upload -> Previsto per tutte le liste di nazionalità plurale con numBio>50 <br>
     * <p>
     * Esegue un Download di NazSingolare
     * Cancella la (eventuale) precedente lista di nazionalità plurali <br>
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Link nazionalità
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Esegue un Download di NazSingolare
        AResult resultSingolari = nazSingolareBackend.resetDownload();

        //--Cancella la (eventuale) precedente lista di nazionalità plurali
        deleteAll();

        //--Crea la tabella ricavandola dalle nazionalità DISTINCT di NazSingolare
        creaTabella(result);

        //--Scarica 1 modulo wiki: Singolare/Link nazionalità.
        result = downloadNazionalitaLink(result);

        return super.fixResetDownload(result);
    }


    /**
     * Crea la tabella ricavandola dalle nazionalità DISTINCT di NazSingolare <br>
     */
    public AResult creaTabella(AResult result) {
        List<String> nomiNazionalitaPluraliDistinte = nazSingolareBackend.findAllDistinctByPlurali();
        List<NazSingolare> listaSingolari;
        AEntity entityBean = null;
        List lista = new ArrayList();

        if (nomiNazionalitaPluraliDistinte != null && nomiNazionalitaPluraliDistinte.size() > 0) {
            for (String plurale : nomiNazionalitaPluraliDistinte) {
                listaSingolari = nazSingolareBackend.findAllByPlurale(plurale);
                entityBean = insert(newEntity(plurale, listaSingolari));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", plurale))));
                }
            }
        }

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Link nazionalità <br>
     */
    public AResult downloadNazionalitaLink(AResult result) {
        String moduloLink = PATH_MODULO + PATH_LINK + NAZ_LOWER;
        String nazSingolareNome;
        String nazPluraleNome;
        String paginaNazioneOld;
        String paginaNazioneNew;
        NazSingolare nazionalitaSin;
        NazPlurale nazionalitaPlur;
        List listaMancanti = new ArrayList();
        List listaDiversi = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloLink);

        for (NazPlurale naz : findAll()) {
            naz.linkNazione = VUOTA;
            update(naz);
        }

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                nazSingolareNome = entry.getKey();
                paginaNazioneNew = entry.getValue();
                paginaNazioneNew = textService.primaMaiuscola(paginaNazioneNew);
                nazionalitaSin = nazSingolareBackend.findByKey(nazSingolareNome);

                if (nazionalitaSin == null) {
                    listaMancanti.add(nazSingolareNome);
                    continue;
                }

                nazPluraleNome = nazionalitaSin.plurale;
                nazionalitaPlur = findByKey(nazPluraleNome);
                if (nazionalitaPlur == null) {
                    return null;
                }

                paginaNazioneOld = nazionalitaPlur.linkNazione;

                if (textService.isEmpty(paginaNazioneOld)) {
                    nazionalitaPlur.linkNazione = paginaNazioneNew;
                    update(nazionalitaPlur);
                }
                else {
                    if (!paginaNazioneNew.equals(paginaNazioneOld)) {
                        listaDiversi.add(paginaNazioneNew);
                    }
                }
            }

            fixDiversi(listaDiversi);
            result.setLista(listaDiversi);
            result.setLista(listaMancanti);
            super.fixDownloadModulo(moduloLink);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloLink);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    public void fixDiversi(List lista) {
    }


    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Esegue un Download di NazSingolare. <br>
     * Download -> Crea una nuova tabella ricavandola dalle nazionalità DISTINCT di NazSingolare. <br>
     * Download -> Aggiunge un link alla paginaLista di ogni nazionalità in base al nome della nazionalità plurale. <br>
     * Download -> Scarica 1 modulo wiki: Link nazionalità <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola nazionalità plurale e la presenza o meno della pagina con la lista di ogni nazionalità <br>
     * Upload -> Previsto per tutte le liste di nazionalità plurale con numBio>50 <br>
     * <p>
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        WResult result = super.elabora();
        List<NazSingolare> singolari;

        //check temporale per elaborare la collection SOLO se non è già stata elaborata di recente (1 ora)
        //visto che l'elaborazione impiega più di parecchio tempo
        LocalDateTime elaborazioneAttuale = LocalDateTime.now();
        LocalDateTime lastElaborazione = (LocalDateTime) this.lastElaborazione.get();

        lastElaborazione = lastElaborazione.plusHours(WPref.oreValiditaElaborazione.getInt());
        if (elaborazioneAttuale.isBefore(lastElaborazione)) {
            this.lastElaborazione.setValue(elaborazioneAttuale);
            return result;
        }

        for (NazPlurale nazPlurale : findAll()) {
            nazPlurale.numBio = bioBackend.countNazPlurale(nazPlurale.nome);
            nazPlurale.esisteLista = esistePaginaLista(nazPlurale.nome);

            update(nazPlurale);
        }

        return super.fixElabora(result);
    }


    /**
     * Controlla l'esistenza della pagina wiki relativa a questa nazionalità (lista) <br>
     */
    public boolean esistePaginaLista(String plurale) {
        String wikiTitle = LINK_PAGINA_NAZIONALITA + textService.primaMaiuscola(plurale);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }


    /**
     * Scrive una pagina definitiva sul server wiki <br>
     */
    public WResult uploadPagina(String nazionalitaPlurale) {
        WResult result = WResult.errato();
        String message;
        int numVoci = bioBackend.countNazPlurale(nazionalitaPlurale);
        String voci = textService.format(numVoci);
        int soglia = WPref.sogliaAttNazWiki.getInt();

        if (numVoci > soglia) {
            result = appContext.getBean(UploadNazionalita.class).upload(nazionalitaPlurale);
            if (result.isValido()) {
                if (result.isModificata()) {
                    message = String.format("Lista %s utilizzati in %s voci biografiche", nazionalitaPlurale, voci);
                }
                else {
                    message = String.format("Nazionalità %s utilizzata in %s voci biografiche. %s", nazionalitaPlurale, voci, result.getValidMessage());
                }
                if (Pref.debug.is()) {
                    logService.info(new WrapLog().message(message).type(AETypeLog.upload));
                }
            }
            else {
                logService.warn(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload));
            }
        }
        else {
            message = String.format("La nazionalità %s ha solo %s voci biografiche e non raggiunge il numero necessario per avere una pagina dedicata", nazionalitaPlurale, voci);
            if (Pref.debug.is()) {
                result.setErrorMessage(message).setValido(false);
                logService.info(new WrapLog().message(message).type(AETypeLog.upload));
            }
            //            if (esistePagina(pluraleAttivitaMinuscola)) {
            //                result.setErrorCode(KEY_ERROR_CANCELLANDA);
            //                message = String.format("Esiste la pagina %s che andrebbe cancellata", wikiTitle);
            //                logService.warn(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
            //            }
        }

        return result;
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public WResult uploadModulo() {
        WResult result = appContext.getBean(UploadModuloLinkNazionalita.class).uploadOrdinatoSenzaModifiche();
        return super.fixRiordinaModulo(result);
    }


    public NazPlurale findDocumentById(String keyCode) {
        NazPlurale beanNazPlurale = null;
        Document doc = super.getDocumentById(keyCode);

        if (doc != null) {
            beanNazPlurale = this.newEntity(doc);
        }

        return beanNazPlurale;
    }

    public NazPlurale findDocumentByKey(String keyCode) {
        NazPlurale beanNazPlurale = null;
        Document doc = super.getDocumentByKey(keyCode);

        if (doc != null) {
            beanNazPlurale = this.newEntity(doc);
        }

        return beanNazPlurale;
    }

}// end of crud backend class
