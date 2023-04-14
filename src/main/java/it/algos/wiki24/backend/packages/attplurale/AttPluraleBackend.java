package it.algos.wiki24.backend.packages.attplurale;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.moduli.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 16:50
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttPluraleBackend extends WikiBackend {


    public AttPluraleBackend() {
        super(AttPlurale.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = null;
        super.lastDownload = WPref.downloadAttPlurale;
        super.durataDownload = WPref.downloadAttPluraleTime;
        super.lastElaborazione = WPref.elaboraAttPlurale;
        super.durataElaborazione = WPref.elaboraAttPluraleTime;

        super.lastUpload = WPref.uploadAttPlurale;
        super.durataUpload = WPref.uploadAttPluraleTime;
        super.nextUpload = WPref.uploadAttPluralePrevisto;
        super.lastStatistica = WPref.statisticaAttPlurale;

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
    public AttPlurale newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AttPlurale newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, null, VUOTA, VUOTA);
    }

    public AttPlurale newEntity(final String keyPropertyValue, List<AttSingolare> listaSingolari) {
        return newEntity(keyPropertyValue, listaSingolari, VUOTA, VUOTA);
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
     * @param linkAttivita   (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AttPlurale newEntity(final String nome, List<AttSingolare> listaSingolari, final String paginaLista, final String linkAttivita) {
        AttPlurale newEntityBean = AttPlurale.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .listaSingolari(listaSingolari)
                .paginaLista(textService.isValid(paginaLista) ? paginaLista : textService.primaMaiuscola(nome))
                .linkAttivita(textService.isValid(linkAttivita) ? linkAttivita : null)
                .numBio(0)
                .numSingolari(listaSingolari != null ? listaSingolari.size() : 0)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (AttPlurale) super.fixKey(newEntityBean);
    }


    @Override
    public AttPlurale findById(final String keyID) {
        return (AttPlurale) super.findById(keyID);
    }

    @Override
    public AttPlurale findByKey(final String keyValue) {
        return (AttPlurale) super.findByKey(keyValue);
    }

    @Override
    public AttPlurale findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public AttPlurale findByProperty(final String propertyName, final Object propertyValue) {
        return (AttPlurale) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<AttPlurale> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<AttPlurale> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<AttPlurale> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<AttPlurale> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<AttPlurale> findAll() {
        return this.findAllNoSort();
    }

    public List<String> findAllDistinctByPlurali() {
        return findAllForKeySortKey();
    }

    public Map<String, String> getMappaPluraleAttivita() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (AttPlurale att : findAll()) {
            mappa.put(att.nome, att.linkAttivita);
        }

        return mappa;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Esegue un Download di AttSingolare. <br>
     * Download -> Crea una nuova tabella ricavandola dalle attività DISTINCT di AttSingolare <br>
     * Download -> Aggiunge un link alla paginaLista di ogni attività in base al nome dell'attività plurale <br>
     * Download -> Scarica 1 modulo wiki: Link attività <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola attività plurale e la presenza o meno della pagina con la lista di ogni attività <br>
     * Upload -> Previsto per tutte le liste di attività plurale con numBio>50 <br>
     * <p>
     * Esegue un Download di AttSingolare
     * Cancella la (eventuale) precedente lista di attività plurali <br>
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Link attività
     */
    public WResult download() {
        WResult result = super.download();

        //--Esegue un Download di AttSingolare
        WResult resultSingolari = attSingolareBackend.download();

        //--Cancella la (eventuale) precedente lista di attività plurali
        deleteAll();

        //--Crea la tabella ricavandola dalle attività DISTINCT di AttSingolare
        creaTabella(result);

        //--Scarica 1 modulo wiki: Singolare/Link attività.
        result = downloadAttivitaLink(result);

        return super.fixDownload(result);
    }

    /**
     * Crea la tabella ricavandola dalle attività DISTINCT di AttSingolare <br>
     */
    public WResult creaTabella(WResult result) {
        List<String> nomiAttivitaPluraliDistinte = attSingolareBackend.findAllDistinctByPlurali();
        List<AttSingolare> listaSingolari;
        AEntity entityBean = null;
        List lista = new ArrayList();

        if (nomiAttivitaPluraliDistinte != null && nomiAttivitaPluraliDistinte.size() > 0) {
            for (String plurale : nomiAttivitaPluraliDistinte) {
                listaSingolari = attSingolareBackend.findAllByPlurale(plurale);
                entityBean = insert(newEntity(plurale, listaSingolari));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", plurale))));
                }
            }
        }

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Link attività <br>
     *
     * @return entities create
     */
    public WResult downloadAttivitaLink(WResult result) {
        String moduloLink = PATH_MODULO + PATH_LINK + ATT_LOWER;
        String attSingolareNome;
        String attPluraleNome;
        String paginaAttivitaOld;
        String paginaAttivitaNew;
        AttSingolare attivitaSin;
        AttPlurale attivitaPlur;
        List listaMancanti = new ArrayList();
        List listaDiversi = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloLink);

        for (AttPlurale att : findAll()) {
            att.linkAttivita = VUOTA;
            update(att);
        }

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                attSingolareNome = entry.getKey();
                paginaAttivitaNew = entry.getValue();
                paginaAttivitaNew = textService.primaMaiuscola(paginaAttivitaNew);
                attivitaSin = attSingolareBackend.findByKey(attSingolareNome);

                if (attivitaSin == null) {
                    listaMancanti.add(attSingolareNome);
                    continue;
                }

                attPluraleNome = attivitaSin.plurale;
                attivitaPlur = findByKey(attPluraleNome);
                paginaAttivitaOld = attivitaPlur.linkAttivita;

                if (textService.isEmpty(paginaAttivitaOld)) {
                    attivitaPlur.linkAttivita = paginaAttivitaNew;
                    update(attivitaPlur);
                }
                else {
                    if (!paginaAttivitaNew.equals(paginaAttivitaOld)) {
                        listaDiversi.add(paginaAttivitaNew);
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
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
    }

    public void fixDiversi(List lista) {
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result = appContext.getBean(UploadModuloLinkAttivita.class).uploadOrdinatoSenzaModifiche();
        return super.fixRiordinaModulo(result);
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Esegue un Download di AttSingolare. <br>
     * Download -> Crea una nuova tabella ricavandola dalle attività DISTINCT di AttSingolare <br>
     * Download -> Aggiunge un link alla paginaLista di ogni attività in base al nome dell'attività plurale <br>
     * Download -> Scarica 1 modulo wiki: Link attività <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola attività plurale e la presenza o meno della pagina con la lista di ogni attività <br>
     * Upload -> Previsto per tutte le liste di attività plurale con numBio>50 <br>
     * <p>
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();

        for (AttPlurale attivita : findAll()) {
            attivita.numBio = 0;
            attivita.superaSoglia = false;
            attivita.esisteLista = false;

            attivita.numBio = bioBackend.countAttivitaPlurale(attivita.nome);
            attivita.esisteLista = esistePagina(attivita.paginaLista);

            update(attivita);
        }

        return super.fixElabora(result);
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Esegue un Download di AttSingolare. <br>
     * Download -> Crea una nuova tabella ricavandola dalle attività DISTINCT di AttSingolare <br>
     * Download -> Aggiunge un link alla paginaLista di ogni attività in base al nome dell'attività plurale <br>
     * Download -> Scarica 1 modulo wiki: Link attività <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singola attività plurale e la presenza o meno della pagina con la lista di ogni attività <br>
     * Upload -> Previsto per tutte le liste di attività plurale con numBio>50 <br>
     * <p>
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(logInfo);

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result = this.download();
        }

        return result;
    }


    /**
     * Controlla l'esistenza della pagina wiki relativa a questa attività (lista) <br>
     */
    public boolean esistePagina(String paginaLista) {
        String wikiTitle = "Progetto:Biografie/Attività/" + textService.primaMaiuscola(paginaLista);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }

}// end of crud backend class
