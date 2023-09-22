package it.algos.wiki24.backend.packages.cognome;

import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognomecategoria.*;
import it.algos.wiki24.backend.packages.cognomeincipit.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Aug-2022
 * Time: 08:43
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class CognomeBackend extends WikiBackend {


    @Autowired
    public CognomeCategoriaBackend cognomeCategoriaBackend;

    @Autowired
    public CognomeIncipitBackend cognomeIncipitBackend;

    public CognomeBackend() {
        super(Cognome.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadCognomi;
        super.lastDownload = WPref.downloadCognomi;
        super.durataDownload = WPref.downloadCognomiTime;
        super.lastElaborazione = WPref.elaboraCognomi;
        super.durataElaborazione = WPref.elaboraCognomiTime;
        super.lastUpload = WPref.uploadCognomi;
        super.durataUpload = WPref.uploadCognomiTime;

        this.unitaMisuraDownload = AETypeTime.minuti;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
        this.unitaMisuraUpload = AETypeTime.minuti;
    }

    public Cognome creaIfNotExist(final String keyPropertyValue, int numBio, boolean categoria, boolean modulo, boolean mongo) {
        return creaIfNotExist(keyPropertyValue, numBio, categoria, modulo, mongo, VUOTA, VUOTA);
    }

    public Cognome creaIfNotExist(final String keyPropertyValue, int numBio, boolean categoria, boolean modulo, boolean mongo, String paginaVoce, String paginaLista) {
        Cognome newCognome;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            newCognome = newEntity(keyPropertyValue, numBio, categoria, modulo, mongo, paginaVoce, paginaLista);
            return newCognome != null ? insert(newCognome) : null;
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Cognome newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public Cognome newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, 0, false, false, false, VUOTA, VUOTA);
    }

    public Cognome newEntity(final String keyPropertyValue, int numBio, boolean categoria, boolean modulo, boolean mongo, String paginaVoce, String paginaLista) {
        Cognome newEntityBean = Cognome.builder()
                .cognome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .numBio(numBio)
                .categoria(categoria)
                .modulo(modulo)
                .mongo(mongo)
                .paginaVoce(textService.isValid(paginaVoce) ? paginaVoce : null)
                .paginaLista(textService.isValid(paginaLista) ? PATH_NOMI + paginaLista : null)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (Cognome) super.fixKey(newEntityBean);
    }


    @Override
    public Cognome findById(final String keyID) {
        return (Cognome) super.findById(keyID);
    }

    @Override
    public Cognome findByKey(final String keyValue) {
        return (Cognome) super.findByKey(keyValue);
    }

    @Override
    public Cognome findByProperty(final String propertyName, final Object propertyValue) {
        return (Cognome) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public Cognome save(AEntity entity) {
        return (Cognome) super.save(entity);
    }

    @Override
    public Cognome insert(AEntity entity) {
        return (Cognome) super.insert(entity);
    }

    @Override
    public Cognome update(AEntity entity) {
        return (Cognome) super.update(entity);
    }

    @Override
    public List<Cognome> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<Cognome> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<Cognome> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Cognome> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<Cognome> findAll() {
        List<Cognome> lista = super.findAll();
        return lista.stream().sorted(Comparator.comparing(cognome -> ((Cognome) cognome).cognome)).collect(Collectors.toList());
    }

    public List<Cognome> findAllSortNumBio() {
        List<Cognome> lista = super.findAllSortCorrente();

        lista = lista.stream()
                .sorted(Comparator.comparingInt(c -> c.numBio))
                .collect(Collectors.toList());
        Collections.reverse(lista);

        return lista;
    }

    public List<Cognome> findAllByNumBio(int soglia) {
        Collator collator = Collator.getInstance(Locale.getDefault());

        return findAllNoSort()
                .stream()
                .filter(cognome -> cognome.numBio > soglia)
                .sorted(Comparator.comparing(Cognome::getCognome, collator))
                .collect(Collectors.toList());
    }

    public List<Cognome> findAllStampabili() {
        List<Cognome> lista = super.findAllSortCorrente();
        int max = WPref.sogliaWikiCognomi.getInt();

        lista = lista.stream()
                .sorted(Comparator.comparing(c -> c.cognome))
                .filter(c -> c.numBio >= max)
                .collect(Collectors.toList());

        return lista;
    }

    public List<Cognome> findAllStampabileSortNumBio() {
        return findAllStampabileSortNumBio(WPref.sogliaWikiCognomi.getInt());
    }

    public List<Cognome> findAllStampabileSortNumBio(int numBio) {
        List<Cognome> lista = findAllSortNumBio();

        lista = lista.stream()
                .filter(c -> c.numBio >= numBio)
                .collect(Collectors.toList());
        Collections.reverse(lista);

        return lista;
    }

    public List<Cognome> findAllEccessiviMongo() {
        return findAllEccessivi(WPref.sogliaMongoCognomi.getInt());
    }

    public List<Cognome> findAllEccessiviServer() {
        return findAllEccessivi(WPref.sogliaWikiCognomi.getInt());
    }

    public List<Cognome> findAllEccessivi(int max) {
        List<Cognome> lista = findAllSortNumBio();

        lista = lista.stream()
                .filter(c -> c.numBio < max)
                .collect(Collectors.toList());
        Collections.reverse(lista);

        return lista;
    }

    /**
     * Fetches all code of cognome <br>
     *
     * @return all selected property
     */
    public List<String> findCognomi() {
        return findAll()
                .stream()
                .map(cognome -> cognome.cognome)
                .collect(Collectors.toList());
    }


    public List<String> findCognomiSortNumBio() {
        return findAllSortNumBio()
                .stream()
                .map(cognome -> cognome.cognome)
                .collect(Collectors.toList());
    }

    public List<String> findCognomiStampabiliSortNumBio() {
        return findAllStampabileSortNumBio()
                .stream()
                .map(cognome -> cognome.cognome)
                .collect(Collectors.toList());
    }

    public List<String> findCognomiStampabili() {
        return findAllStampabili()
                .stream()
                .map(cognome -> cognome.cognome)
                .collect(Collectors.toList());
    }

    public int countBySopraSoglia() {
        Query query = new Query();
        String keyProperty = "superaSoglia";
        Long lungo;
        query.addCriteria(Criteria.where(keyProperty).is(true));
        lungo = mongoService.mongoOp.count(query, Cognome.class);
        return lungo > 0 ? lungo.intValue() : 0;
    }

    public List<String> findAllForKeyByNumBio() {
        int soglia = WPref.sogliaWikiNomi.getInt();
        List<Cognome> listaAll = findAllSortKey();
        return listaAll != null ? listaAll.stream().filter(cognome -> cognome.numBio > soglia).map(cognome -> cognome.cognome).collect(Collectors.toList()) : null;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Recupera una lista di nomi distinti dalle biografie. Crea una entity se bio>%s. <br>
     * Download -> Esegue un Download del Template:Incipit. Aggiunge i valori alla lista. <br>
     * Download -> Aggiunge alla lista i nomi doppi. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singolo nome e la effettiva presenza della paginaLista <br>
     * Upload -> Previsto per tutte le liste di nomi con bio>%s. <br>
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String message;

        message = String.format("Creazione completa cognomi delle biografie. Circa %d secondi.", WPref.downloadCognomiTime.getInt());
        System.out.println(message);

        //--Cancella tutte le entities della collezione
        deleteAll();

        //--Cognomi 'categoria'. Ricavati dalla categoria su wiki
        result = addCognomiCategoria(result);

        //--Cognomi 'incipit'. Ricavati dal Modulo sul server Wiki.
        result = addCognomiModulo(result);

        //--Cognomi 'semplici'. Ricavati dalla collection Bio sul server mongo.
        result = estraeCognomiDistinti(result);

        result.fine();
        result.valido(true).eseguito();

        return super.fixResetDownload(result);

    }// end of method


    /**
     * Legge i valori dalla tavola NomeCategoria
     *
     * @return entities create
     */
    public AResult addCognomiCategoria(AResult result) {
        Cognome entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<CognomeCategoria> listaCognomiCategorie = null;

        //--Controllo e recupero di NomiCategoria
        cognomeCategoriaBackend.resetDownload();
        listaCognomiCategorie = cognomeCategoriaBackend.findAll();

        if (listaCognomiCategorie != null) {
            for (CognomeCategoria cognomeCategoria : listaCognomiCategorie) {
                entityBean = creaIfNotExist(cognomeCategoria.cognome, 0, true, false, false, cognomeCategoria.linkPagina, null);
                result.setValido(fixLista(result, entityBean, cognomeCategoria.cognome));
            }
        }
        else {
            message = String.format("Mancano i CognomiCategoria");
            logService.warn(new WrapLog().message(message));
        }

        return super.fixResult(result, lista);
    }


    /**
     * Legge i valori dalla tavola NomeModulo
     *
     * @return lista dei valori
     */
    public AResult addCognomiModulo(AResult result) {
        Cognome entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<CognomeIncipit> listaCognomiIncipit = null;

        //--Controllo e recupero di NomiDoppi
        cognomeIncipitBackend.resetDownload();
        listaCognomiIncipit = cognomeIncipitBackend.findAll();

        if (listaCognomiIncipit != null) {
            for (CognomeIncipit cognomeIncipit : listaCognomiIncipit) {
                if (isExistByKey(cognomeIncipit.cognome)) {
                    entityBean = findByKey(cognomeIncipit.cognome);
                    entityBean.modulo = true;
                    if (entityBean.paginaLista == null) {
                        entityBean.paginaLista = cognomeIncipit.linkPagina;
                    }
                    save(entityBean);
                }
                else {
                    entityBean = creaIfNotExist(cognomeIncipit.cognome, 0, false, true, false, cognomeIncipit.linkPagina, null);
                    result.setValido(fixLista(result, entityBean, cognomeIncipit.cognome));
                }
            }
        }
        else {
            message = String.format("Mancano i NomiModulo");
            logService.warn(new WrapLog().message(message));
        }

        return super.fixResult(result, lista);
    }


    /**
     * @return lista dei valori
     */
    public AResult estraeCognomiDistinti(AResult result) {
        DistinctIterable<String> listaCognomiMongo = mongoService.getCollection(TAG_BIO).distinct("cognome", String.class);
        Cognome entityBean;
        int numBio;
        int sogliaMongo = WPref.sogliaMongoCognomi.getInt();
        List<AEntity> lista = new ArrayList<>();
        boolean debug = Pref.debug.is();

        if (listaCognomiMongo == null) {
            message = "listaCognomiMongo is null";
            logService.warn(new WrapLog().message(message));
            return result.errorMessage(message);
        }

        for (String cognomeMongo : listaCognomiMongo) {
            if (isExistByKey(cognomeMongo)) {
                entityBean = findByKey(cognomeMongo);
                entityBean.mongo = true;
                save(entityBean);
            }
            else {
                numBio = bioBackend.countCognome(cognomeMongo);
                if (numBio > sogliaMongo) {
                    entityBean = creaIfNotExist(cognomeMongo, 0, false, true, false, null, null);
                    result.setValido(fixLista(result, entityBean, cognomeMongo));
                }
                else {
                    if (debug) {
                        message = String.format("Le %d occorrenze di %s non sono sufficienti per creare una entity di %s su mongo", numBio, cognomeMongo, Cognome.class.getSimpleName());
                        logService.info(new WrapLog().message(message).type(AETypeLog.resetForcing));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Cancella i cognomi esistenti <br>
     * Crea tutti i cognomi <br>
     * Controlla che ci siano almeno n voci biografiche per il singolo cognome <br>
     * Registra la entity <br>
     * Non registra la entity col cognomi mancante <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();
        int sogliaMongo = WPref.sogliaMongoCognomi.getInt();
        int sogliaWiki = WPref.sogliaWikiCognomi.getInt();

        //check temporale per elaborare la collection SOLO se non è già stata elaborata di recente (1 ora)
        //visto che l'elaborazione impiega più di parecchio tempo
        LocalDateTime elaborazioneAttuale = LocalDateTime.now();
        LocalDateTime lastElaborazione = (LocalDateTime) this.lastElaborazione.get();

        lastElaborazione = lastElaborazione.plusHours(WPref.oreValiditaElaborazione.getInt());
        if (elaborazioneAttuale.isBefore(lastElaborazione)) {
            this.lastElaborazione.setValue(elaborazioneAttuale);
            return result;
        }

        resetDownload();

        for (Cognome cognome : findAll()) {
            cognome.numBio = bioBackend.countCognome(cognome.cognome);
            cognome.superaSoglia = cognome.numBio > sogliaWiki;
            if (cognome.numBio > sogliaMongo) {
                cognome.paginaLista = PATH_COGNOMI + cognome.cognome;
            }
            if (cognome.superaSoglia) {
                cognome.esisteLista = queryService.isEsiste(cognome.paginaLista);
            }
            update(cognome);
        }

        return super.fixElabora(result);
    }


    /**
     * Registra il numero di voci biografiche che hanno il cognome indicato <br>
     */
    public boolean saveCognome(String cognomeTxt, int sogliaMongo) {
        Cognome cognome = null;
        long numBio = bioBackend.countCognome(cognomeTxt);

        if (numBio >= sogliaMongo) {
            //            cognome = creaIfNotExist(cognomeTxt, (int) numBio, esistePagina(cognomeTxt));
        }

        return cognome != null;
    }


    /**
     * Controlla l'esistenza della pagina wiki relativa a questo cognome <br>
     */
    public boolean esistePagina(String cognome) {
        String wikiTitle = PATH_COGNOMI + textService.primaMaiuscola(cognome);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }

    /**
     * Esegue la scrittura di tutte le pagine <br>
     */
    public WResult uploadAll() {
        WResult result = null;
        String message;
        int pagineCreate = 0;
        int pagineModificate = 0;
        int pagineEsistenti = 0;
        int pagineControllate = 0;

        List<String> listaCognomi = this.findAllForKeyByNumBio();
        for (String cognome : listaCognomi) {
            result = appContext.getBean(UploadCognomi.class, cognome).upload();

            switch (result.getTypeResult()) {
                case queryWriteCreata -> pagineCreate++;
                case queryWriteModificata -> pagineModificate++;
                case queryWriteEsistente -> pagineEsistenti++;
                default -> pagineControllate++;
            }

            if (Pref.debug.is()) {
                if (result.isValido()) {
                    if (result.isModificata()) {
                        message = String.format("Upload della singola pagina%s [%s%s]", FORWARD, PATH_COGNOMI, cognome);
                        logService.info(new WrapLog().message(message).type(AETypeLog.upload));
                    }
                    else {
                        message = String.format("La pagina: [%s%s] esisteva già e non è stata modificata", PATH_COGNOMI, cognome);
                        logService.info(new WrapLog().message(message).type(AETypeLog.upload));
                    }
                }
                else {
                    message = String.format("Non sono riuscito a caricare su wiki la pagina: [%s%s]", PATH_COGNOMI, cognome);
                    logService.error(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload).usaDb());
                }
            }
        }
        result.fine();

        logService.info(new WrapLog().message(String.format("Create %d", pagineCreate)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Modificate %d", pagineModificate)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Esistenti %d", pagineEsistenti)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Controllate %d", pagineControllate)).type(AETypeLog.upload));

        message = String.format("Upload di %d pagine di cognomi con numBio>%d.", listaCognomi.size(), WPref.sogliaWikiCognomi.getInt());
        message += String.format(" Nuove=%s - Modificate=%s - Esistenti=%s - Controllate=%s.", pagineCreate, pagineModificate, pagineEsistenti, pagineControllate);
        message += String.format(" %s", AETypeTime.minuti.message(result));
        logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());

        return result;
    }

}// end of crud backend class
