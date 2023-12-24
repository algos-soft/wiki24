package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 22-Dec-2023
 * Time: 13:53
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public DownloadService DownloadService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DownloadService {

    @Inject
    private TextService textService;

    @Inject
    MongoService mongoService;

    @Inject
    DateService dateService;

    @Inject
    AnnotationService annotationService;

    @Inject
    QueryService queryService;

    @Inject
    LogService logger;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    BotLogin botLogin;

    private long inizio;

    private String message;

    /**
     * Ciclo iniziale di download di BioServer con un reset completo <br>
     * Cancella (drop) il database 'BioServer' <br>
     * Legge tutte le pagine dal server di wikipedia, per la categoria prevista <br>
     */
    public void cicloIniziale() {
        String categoryTitle = WPref.categoriaBio.getStr();
        int numPages;
        List<Long> listaPageIds;

        //--inizio
        this.iniziale();

        //--Controlla l'esistenza/non esistenza della collection
        if (isCollectionEsistente()) {
            return;
        }

        //--Controlla quante pagine ci sono nella categoria
        numPages = checkCategoria(categoryTitle);

        //--Controlla il collegamento come bot
        if (!checkBot(numPages)) {
            return;
        }

        //--Crea la lista di tutti i pageIds (long) della category
        listaPageIds = getListaPageIds(categoryTitle);

        //--Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale
        creaNewEntities(listaPageIds);

        //--durata del ciclo completo
        fixInfoDurataReset(inizio);
    }

    /**
     * Ciclo corrente di download <br>
     * Parte dalla lista di tutti i (long) pageIds della categoria <br>
     * Usa la lista di pageIds e si recupera una lista (stessa lunghezza) di miniWrap <br>
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     */
    public void cicloCorrente() {
        String categoryTitle = WPref.categoriaBio.getStr();
        int numPages;
        List<Long> listaPageIds;
        List<Long> listaMongoIds;

        //--inizio
        this.corrente();

        //--Controlla l'esistenza/non esistenza della collection
        if (isCollectionVuota()) {
            return;
        }

        //--Controlla quante pagine ci sono nella categoria
        numPages = checkCategoria(categoryTitle);

        //--Controlla il collegamento come bot
        if (!checkBot(numPages)) {
            return;
        }

        //--Crea la lista di tutti i pageIds (long) della category
        listaPageIds = getListaPageIds(categoryTitle);

        //--Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale
        listaMongoIds = getListaMongoIds();
    }

    /**
     * Inizio del ciclo di download <br>
     */
    public void iniziale() {
        inizio = System.currentTimeMillis();
        message = "Inizio del cicloIniziale() di download per la collection BioServer";
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
    }
    /**
     * Inizio del ciclo di download <br>
     */
    public void corrente() {
        inizio = System.currentTimeMillis();
        message = "Inizio del cicloCorrente() di download per la collection BioServer";
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
    }


    /**
     * Controlla l'esistenza della collection <br>
     * Se esiste NON esegue il cicloIniziale() <br>
     */
    public boolean isCollectionEsistente() {
        boolean esiste = mongoService.existsCollectionClazz(BioServerEntity.class);

        if (esiste) {
            message = String.format("La collection [%s] esiste e non viene eseguito il [%s]","bioServer","cicloIniziale()");
            logger.warn(new WrapLog().message(message).type(TypeLog.bio));
        }

        return esiste;
    }
    /**
     * Controlla l'esistenza della collection <br>
     * Se è vuota NON esegue il cicloCorrente() <br>
     */
    public boolean isCollectionVuota() {
        boolean vuota = !mongoService.existsCollectionClazz(BioServerEntity.class);

        if (vuota) {
            message = String.format("La collection [%s] è vuota e non viene eseguito il [%s]","bioServer","cicloCorrente()");
            logger.warn(new WrapLog().message(message).type(TypeLog.bio));
        }

        return vuota;
    }


    /**
     * Legge (anche come anonymous) il numero di pagine di una categoria wiki <br>
     * Si collega come anonymous; non serve essere loggati <br>
     * Serve però per le operazioni successive <br>
     *
     * @param categoryTitle da controllare
     *
     * @return numero di pagine (subcategorie escluse)
     */
    public int checkCategoria(final String categoryTitle) {
        int numPages = queryService.getSizeCat(categoryTitle);
        String message;

        if (numPages > 0) {
            message = String.format("La categoria utilizzata [%s] esiste e contiene %s voci", categoryTitle, textService.format(numPages));
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }
        else {
            message = String.format("La categoria [%s] non esiste oppure è vuota", categoryTitle);
            logger.warn(new WrapLog().message(message).type(TypeLog.bio).usaDb());
        }

        return numPages;
    }


    /**
     * Controlla il collegamento come bot <br>
     *
     * @return true se collegato come bot
     */
    public boolean checkBot(int numPages) {
        boolean status = false;
        String message;
        int limit = 0;

        if (botLogin != null) {
            limit = botLogin.getUserType().getLimit();
        }

        if (numPages < limit || botLogin.isBot()) {
            message = String.format("Regolarmente collegato come [%s] di nick [%s]", botLogin.getUserType(), botLogin.getUsername());
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
            status = true;
        }
        else {
            message = String.format("Collegato come [%s] col limite di %d mentre la categoria ha %s voci", botLogin.getUserType(), limit, textService.format(numPages));
            logger.warn(new WrapLog().message(message).type(TypeLog.bio).usaDb());
        }
        return status;
    }


    /**
     * Crea la lista di tutti i (long) pageIds della categoria <br>
     * Deve riuscire a gestire una lista di circa 500.000 long per la category BioBot <br>
     * Tempo medio previsto = circa 1 minuto (come bot la categoria legge 5.000 pagine per volta) <br>
     * Nella listaPageIds possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param categoryTitle da controllare
     *
     * @return lista di tutti i (long) pageIds
     */
    public List<Long> getListaPageIds(final String categoryTitle) {
        long inizio = System.currentTimeMillis();
        List<Long> listaPageIds;
        String size;
        String time;
        String message;

        listaPageIds = queryService.getPageIds(categoryTitle);

        if (listaPageIds != null && listaPageIds.size() > 0) {
            size = textService.format(listaPageIds.size());
            time = dateService.deltaText(inizio);
            message = String.format("Recuperati %s pageIds (long) dalla categoria [%s]. Tempo %s", size, categoryTitle, time);
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }
        else {
            message = String.format("La categoria [%s] è vuota oppure non è corretta oppure non esiste", categoryTitle);
            logger.info(new WrapLog().message(message));

            logger.warn(new WrapLog().message(message).type(TypeLog.bio));
        }

        return listaPageIds;
    }

    /**
     * Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale <br>
     *
     * @return lista di tutti i (long) pageId del database (mongo) locale
     */
    public List<Long> getListaMongoIds() {
        List<Long> lista;
        String message;
         inizio = System.currentTimeMillis();
        String size;
        String time;

        lista = bioBackend.findOnlyPageId();

        if (lista == null || lista.size() == 0) {
            message = "La lista bio è vuota";
            logService.warn(new WrapLog().message(message));
            return null;
        }

        size = textService.format(lista.size());
        time = dateService.deltaText(inizio);
        message = String.format("Recuperate %s entities (long) dal database (mongo). Tempo %s", size, time);
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));

        return lista;
    }

    /**
     * Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale <br>
     * Legge tutte le pagine <br>
     * Recupera i contenuti di tutte le voci biografiche da creare/modificare <br>
     * Controlla che esista il tmpl BIO <br>
     * Nella listaWrapBio ci sono solo voci CON il tmpl BIO valido <br>
     *
     * @param listaPageIdsDaCreare tutti i pageIds (long) presenti sul server wiki e da creare/modificare
     */
    public void creaNewEntities(List<Long> listaPageIdsDaCreare) {
        inizio = System.currentTimeMillis();
        long inizioBloccoPageIds;
        long inizioBloccoWrapBio;
        String message;
        int numVociBlocco = 0;
        int numVociCreate = 0;
        int numVociTotali = listaPageIdsDaCreare.size();
        String vociBlocco;
        String vociCreate;
        String vociTotali = textService.format(numVociTotali); ;
        boolean usaNotificationCurrentValue = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);
        int blocco = WPref.bloccoDownload.getInt();
        List<Long> subListPageIds;
        List<WrapBio> listaWrapBio = null;

        message = String.format("Vengono creati [%s] e salvate le entities [%s] in blocchi di [%d] pagine per volta", "WrapBio", "BioServer", blocco);
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        for (int k = 0; k < listaPageIdsDaCreare.size(); k = k + blocco) {
            numVociBlocco = 0;
            inizioBloccoPageIds = System.currentTimeMillis();
            subListPageIds = listaPageIdsDaCreare.subList(k, Math.min(k + blocco, numVociTotali));
            listaWrapBio = queryService.getListaBio(subListPageIds);
            inizioBloccoWrapBio = System.currentTimeMillis();
            for (WrapBio wrapBio : listaWrapBio) {
                bioServerModulo.insertSave(wrapBio.getBeanBioServer());
                numVociBlocco++;
                numVociCreate++;
            }
            vociBlocco = textService.format(numVociBlocco);
            vociCreate = textService.format(numVociCreate);
            message = String.format("Lette %s/%s/%s nuove pagine in %s/%s", vociBlocco, vociCreate, vociTotali, dateService.deltaText(inizioBloccoPageIds), dateService.deltaText(inizio));
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
            message = String.format("Create %s/%s/%s nuove biografie in %s/%s", vociBlocco, vociCreate, vociTotali, dateService.deltaText(inizioBloccoWrapBio), dateService.deltaText(inizio));
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }

        if (numVociCreate > 0) {
            vociCreate = textService.format(numVociCreate);
            message = String.format("Create in totale %s nuove biografie in %s", vociCreate, dateService.deltaText(inizio));
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
            message = String.format("Ci sono probabilmente %s pagine della categoria [%s] che NON hanno un tmplBio valido", numVociTotali - numVociCreate, WPref.categoriaBio.getStr());
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }

        Pref.usaNotification.setValue(usaNotificationCurrentValue);
    }


    /**
     * Legge tutte le pagine <br>
     * Recupera i contenuti di tutte le voci biografiche da creare/modificare <br>
     * Controlla che esiste il tmpl BIO <br>
     * Nella listaWrapBio possono ci sono solo voci CON il tmpl BIO valido <br>
     *
     * @param listaPageIdsDaLeggere dal server wiki
     *
     * @return listaWrapBio
     */
    public List<WrapBio> getListaWrapBio(final List<Long> listaPageIdsDaLeggere) {
        List<WrapBio> listaWrap = new ArrayList<>();
        long inizio = System.currentTimeMillis();
        long inizio2;
        List<Long> subList;
        List<WrapBio> listaWrapTmp = null;
        String message;
        int stock = 5000;
        int dim;

        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        if (listaPageIdsDaLeggere != null) {
            dim = listaPageIdsDaLeggere.size();
            for (int k = 0; k < dim; k = k + stock) {
                subList = listaPageIdsDaLeggere.subList(k, Math.min(k + stock, dim));

                inizio2 = System.currentTimeMillis();
                listaWrapTmp = queryService.getListaBio(subList);
                if (listaWrapTmp != null) {
                    listaWrap.addAll(listaWrapTmp);
                    message = String.format("Recuperate %s WrapBio di biografie da aggiornare in %s", textService.format(listaWrapTmp.size()), dateService.deltaText(inizio2));
                    logger.info(new WrapLog().message(message).type(TypeLog.bio));
                }
            }
            message = String.format("Recuperate in totale %s WrapBio di biografie da aggiornare in %s", textService.format(listaWrap.size()), dateService.deltaText(inizio));
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }

        return listaWrap;
    }


    public void fixInfoDurataReset(final long inizio) {
        String message;
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;

        //        if (WPref.resetBio != null) {
        //            WPref.resetBio.setValue(LocalDateTime.now());
        //        }

        //        if (WPref.resetBioTime != null) {
        //            delta = delta / 1000 / 60;
        //            WPref.resetBioTime.setValue(delta.intValue());
        //        }

        //        if (WPref.downloadBio != null) {
        //            WPref.downloadBio.setValue(LocalDateTime.now());
        //        }

        //        if (WPref.downloadBioTime != null) {
        //            WPref.downloadBioTime.setValue(0);
        //        }

        //        if (WPref.downloadBioPrevisto != null) {
        //            WPref.downloadBioPrevisto.setValue(ROOT_DATA_TIME);
        //        }

        //        if (WPref.elaboraBio != null) {
        //            WPref.elaboraBio.setValue(LocalDateTime.now());
        //        }

        //        if (WPref.elaboraBioTime != null) {
        //            WPref.elaboraBioTime.setValue(0);
        //        }

        message = String.format("Ciclo completo di reset in, %s", dateService.deltaText(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
    }

}// end of Service class