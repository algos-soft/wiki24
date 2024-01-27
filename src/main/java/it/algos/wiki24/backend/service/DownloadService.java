package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.time.temporal.*;
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
    ArrayService arrayService;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    BioServerModulo bioMongoModulo;

    @Inject
    BotLogin botLogin;

    @Inject
    WikiBotService wikiBotService;

    @Inject
    MailService mailService;

    @Inject
    ElaboraService elaboraService;

    private long inizio;

    private String message;

    /**
     * Ciclo iniziale di download di BioServer con un reset completo <br>
     * Cancella (drop) il database 'BioServer' <br>
     * Legge tutte le pagine dal server di wikipedia, per la categoria prevista <br>
     */
    public String cicloIniziale() {
        String risultato = VUOTA;
        long inizio = System.currentTimeMillis();
        String categoryTitle = WPref.categoriaBio.getStr();
        List<Long> listaPageIdsDaCreare;

        //--inizio
        risultato += inizio("cicloIniziale");

        //--Controlla l'esistenza/non esistenza della collection
        if (isCollectionEsistente()) {
            return VUOTA;
        }

        //-- Controlla il collegamento come bot in funzione della dimensione della categoria <br>
        risultato += checkBot(categoryTitle);
        if (textService.isEmpty(risultato)) {
            return VUOTA;
        }

        //--Crea la lista di tutti i pageIds (long) della category
        listaPageIdsDaCreare = getListaPageIds(categoryTitle);

        //--Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale
        risultato += creaNuoveVoci(listaPageIdsDaCreare);

        //--durata del ciclo completo
        risultato += fixInfoDurataCiclo("cicloIniziale", "Ciclo iniziale", inizio);

        return risultato;
    }

    /**
     * Ciclo corrente di download <br>
     * Parte dalla lista di tutti i (long) pageIds della categoria <br>
     * Usa la lista di pageIds e si recupera una lista (stessa lunghezza) di miniWrap <br>
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     */
    public String cicloCorrente() {
        String risultato = VUOTA;
        long inizio = System.currentTimeMillis();
        String categoryTitle = WPref.categoriaBio.getStr();
        List<Long> listaPageIds;
        List<Long> listaMongoIds;
        List<Long> listaMongoIdsDaCancellare;
        List<Long> listaPageIdsDaCreare;
        List<WrapTime> listaWrapTime;
        List<Long> listaPageIdsDaModificare;

        //--inizio
        risultato += inizio("cicloCorrente");

        //--Controlla l'esistenza/non esistenza della collection
        if (isCollectionVuota()) {
            return VUOTA;
        }

        //-- Controlla il collegamento come bot in funzione della dimensione della categoria <br>
        risultato += checkBot(categoryTitle);
        if (textService.isEmpty(risultato)) {
            return VUOTA;
        }

        //--Crea la lista di tutti i pageIds (long) della category
        listaPageIds = getListaPageIds(categoryTitle);

        //--Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale
        listaMongoIds = getListaMongoIds();

        //--Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale
        listaMongoIdsDaCancellare = deltaCancella(listaMongoIds, listaPageIds);

        //--Cancella dal database (mongo) locale le entities non più presenti nella category <br>
        risultato += cancellaEntitiesNonInCategory(listaMongoIdsDaCancellare);

        //--Recupera i (long) pageIds presenti nella category e non ancora esistenti nel database (mongo) locale e da creare
        listaPageIdsDaCreare = deltaCreare(listaPageIds, listaMongoIds);

        //--Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale
        risultato += creaNuoveVoci(listaPageIdsDaCreare);

        //--Usa la lista di pageIds della categoria e recupera una lista (stessa lunghezza) di wrapTimes con l'ultima modifica sul server
        listaWrapTime = getListaWrapTime(listaPageIds);

        //--Elabora la lista di wrapTimes e costruisce una lista di pageIds da leggere
        listaPageIdsDaModificare = elaboraListaWrapTime(listaWrapTime);

        //--Legge tutte le pagine
        risultato += modificaVociEsistenti(listaPageIdsDaModificare);

        //--durata del ciclo completo
        risultato += fixInfoDurataCiclo("cicloCorrente", "Ciclo corrente", inizio);

        return risultato;
    }

    /**
     * Inizio del ciclo di download <br>
     */
    public String inizio(String ciclo) {
        String risultato;

        message = String.format("Inizio del %s() di download per la collection BioServer", ciclo);
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));

        risultato = String.format("%s (%s)", "BioServer", "download");
        risultato += CAPO;
        risultato += String.format("%s (%s)", "BioMongo", "elabora");
        risultato += CAPO;
        risultato += WPref.usaDownloadBioServer.getDescrizione();
        risultato += CAPO;

        return risultato;
    }


    /**
     * Controlla l'esistenza della collection <br>
     * Se esiste NON esegue il cicloIniziale() <br>
     */
    public boolean isCollectionEsistente() {
        boolean esiste = mongoService.existsCollectionClazz(BioServerEntity.class);

        if (esiste) {
            message = String.format("La collection [%s] esiste e non viene eseguito il [%s]", "bioServer", "cicloIniziale()");
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
            message = String.format("La collection [%s] è vuota e non viene eseguito il [%s]", "bioServer", "cicloCorrente()");
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
     * Controlla il collegamento come bot in funzione della dimensione della categoria <br>
     * Categorie di piccole dimensioni NON necessitano necessariamente del bot <br>
     *
     * @return true se collegato come bot
     */
    public String checkBot(String categoryTitle) {
        int numPages;
        String message;
        int limit = 0;

        if (botLogin != null) {
            limit = botLogin.getUserType().getLimit();
        }

        //--Controlla quante pagine ci sono nella categoria
        numPages = checkCategoria(categoryTitle);

        if (numPages < limit || botLogin.isBot()) {
            message = String.format("Regolarmente collegato come [%s] di nick [%s]", botLogin.getUserType(), botLogin.getUsername());
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
            return String.format("Nella categoria [%s] ci sono [%s] pagine. ", categoryTitle, textService.format(numPages));
        }
        else {
            message = String.format("Collegato come [%s] col limite di %d mentre la categoria ha %s voci", botLogin.getUserType(), limit, textService.format(numPages));
            logger.warn(new WrapLog().message(message).type(TypeLog.bio).usaDb());
            return VUOTA;
        }
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

        lista = bioServerModulo.findOnlyPageId();

        if (lista == null || lista.size() == 0) {
            message = "La lista bio è vuota";
            logger.warn(new WrapLog().message(message));
            return null;
        }

        size = textService.format(lista.size());
        time = dateService.deltaText(inizio);
        message = String.format("Recuperate %s entities (long) dal database (mongo). Tempo %s", size, time);
        logger.info(new WrapLog().message(message).type(TypeLog.bio));

        return lista;
    }

    public List<Long> deltaCancella(List<Long> listaMongoIds, List<Long> listaPageIds) {
        List<Long> delta;
        long inizio = System.currentTimeMillis();
        String message;

        if (listaMongoIds == null || listaPageIds == null) {
            return null;
        }
        delta = arrayService.deltaBinary(listaMongoIds, listaPageIds);
        if (delta != null && delta.size() > 0) {
            message = String.format("Nel database (mongo) sono state individuate %s biografie da cancellare. Tempo %s", delta.size(), dateService.deltaText(inizio));
        }
        else {
            message = String.format("Nel database (mongo) non ci sono biografie da cancellare.", delta.size());
        }
        logger.info(new WrapLog().message(message).type(TypeLog.bio));

        return delta;
    }


    /**
     * Cancella dal database (mongo) locale le entities non più presenti nella category <br>
     *
     * @param listaMongoIdsDaCancellare dal database mongo locale
     */
    public String cancellaEntitiesNonInCategory(List<Long> listaMongoIdsDaCancellare) {
        long inizio = System.currentTimeMillis();
        String message;
        String size;
        BioServerEntity bio;
        boolean usaNotificationCurrentValue = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);

        if (listaMongoIdsDaCancellare == null || listaMongoIdsDaCancellare.size() < 1) {
            return String.format("%sNel database non ci sono pagine da cancellare. ", CAPO,
                    textService.format(listaMongoIdsDaCancellare.size())
            );
        }

        for (Long pageId : listaMongoIdsDaCancellare) {
            bio = bioServerModulo.findByKey(pageId);
            if (bio != null) {
                bioServerModulo.delete(bio);
            }
        }

        size = textService.format(listaMongoIdsDaCancellare.size());
        message = String.format("Nel database (mongo) sono state cancellate %s entities non più presenti sul server wiki. Tempo %s", size, dateService.deltaText(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio).usaDb());
        Pref.usaNotification.setValue(usaNotificationCurrentValue);

        return String.format("%sCancellate [%s] entities dal database (mongo). ", CAPO,
                textService.format(listaMongoIdsDaCancellare.size())
        );
    }


    public List<Long> deltaCreare(List<Long> listaPageIds, List<Long> listaMongoIds) {
        long inizio = System.currentTimeMillis();
        List<Long> delta;
        String message;
        String cat = WPref.categoriaBio.getStr();
        String deltaTxt;

        if (listaPageIds == null) {
            return null;
        }

        if (listaMongoIds == null) {
            return listaPageIds;
        }

        delta = arrayService.deltaBinary(listaPageIds, listaMongoIds);
        deltaTxt = textService.format(delta);
        if (delta != null && delta.size() > 0) {
            message = String.format("Nel database (mongo) mancano %s biografie esistenti nella categoria [%s]. Tempo %s", deltaTxt, cat, dateService.deltaText(inizio));
        }
        else {
            message = String.format("Nel database (mongo) non ci sono biografie da creare.", delta.size());
        }
        logger.info(new WrapLog().message(message).type(TypeLog.bio));

        return delta;
    }


    /**
     * Usa la lista di pageIds e recupera una lista (stessa lunghezza) di miniWrap <br>
     * Deve riuscire a gestire una lista di circa 500.000 long per la category BioBot <br>
     * Tempo medio previsto = circa 20 minuti (come bot la query legge 500 pagine per volta) <br>
     * Nella listaMiniWrap possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param listaPageIds di tutti i (long) pageIds
     *
     * @return lista di tutti i WrapTime con l'ultima modifica sul server
     */
    public List<WrapTime> getListaWrapTime(final List<Long> listaPageIds) {
        List<WrapTime> listaMiniWrap = null;
        long inizio = System.currentTimeMillis();
        String message;
        String size;
        String cat = WPref.categoriaBio.getStr();

        if (Pref.debug.is()) {
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
            message = String.format("Creazione dei wrapTimes dalla categoria [%s] in (previsti) %s", cat, "8 minuti");
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }

        listaMiniWrap = queryService.getMiniWrap(listaPageIds);
        size = textService.format(listaMiniWrap.size());
        message = String.format("Creati %s wrapTimes dai pageIds della categoria [%s] in %s", size, cat, dateService.deltaText(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));

        return listaMiniWrap;
    }


    /**
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     * Vengono usati quelli che hanno un miniWrap.pageid senza corrispondente bio.pageid nel mongoDb <br>
     * Vengono usati quelli che hanno miniWrap.lastModifica maggiore di bio.lastModifica <br>
     * A regime deve probabilmente gestire una lista di circa 10.000 miniWrap
     * si tratta delle voci nuove e di quelle modificate nelle ultime 24 (?) ore <br>
     * Nella listaPageIdsDaLeggere possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param listaWrapTimes con il pageIds e lastModifica
     *
     * @return listaPageIdsDaLeggere
     */
    public List<Long> elaboraListaWrapTime(final List<WrapTime> listaWrapTimes) {
        List<Long> listaPageIdsDaLeggere;
        long inizio = System.currentTimeMillis();
        String size;
        String voci;

        listaPageIdsDaLeggere = wikiBotService.elaboraWrapTime(listaWrapTimes);
        size = textService.format(listaWrapTimes.size());
        voci = textService.format(listaPageIdsDaLeggere.size());
        String message = String.format("Elaborati in totale %s wrapTimes e trovate %s voci da aggiornare, in %s", size, voci, dateService.deltaText(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
        return listaPageIdsDaLeggere;
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
    public String creaNuoveVoci(List<Long> listaPageIdsDaCreare) {
        String risultato = VUOTA;
        inizio = System.currentTimeMillis();
        long inizioBloccoPageIds;
        long inizioBloccoWrapBio;
        String message;
        BioServerEntity bioServerBean;
        BioMongoEntity bioMongoBean;
        int numVociBlocco;
        int numVociCreate = 0;
        int numVociNonBio = 0;
        int numVociTotali = listaPageIdsDaCreare.size();
        String vociBlocco;
        String vociCreate;
        String vociNonBio;
        String vociTotali = textService.format(numVociTotali); ;
        boolean usaNotificationCurrentValue = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);
        int blocco = WPref.bloccoDownload.getInt();
        List<Long> subListPageIds;
        List<WrapBio> listaWrapBio;

        message = String.format("Vengono creati i [%s] e salvate le entities [%s] in blocchi di [%d] pagine per volta", "WrapBio", "BioServer", blocco);
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        for (int k = 0; k < listaPageIdsDaCreare.size(); k = k + blocco) {
            numVociBlocco = 0;
            inizioBloccoPageIds = System.currentTimeMillis();
            subListPageIds = listaPageIdsDaCreare.subList(k, Math.min(k + blocco, numVociTotali));
            listaWrapBio = queryService.getListaBio(subListPageIds);
            inizioBloccoWrapBio = System.currentTimeMillis();
            for (WrapBio wrapBio : listaWrapBio) {
                if (wrapBio.isValida()) {
                    bioServerBean = wrapBio.getBeanBioServer();
                    bioServerModulo.insert(bioServerBean);
                    elaboraService.creaModificaBeanMongo(bioServerBean);
                    numVociBlocco++;
                    numVociCreate++;
                }
                else {
                    if (Pref.debug.is()) {
                        // la pagina NON è una biografia
                        numVociNonBio++;
                    }
                }
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

        vociNonBio = textService.format(numVociNonBio);
        vociCreate = textService.format(numVociCreate);
        risultato += String.format("%sTrovate [%s] nuove voci presenti nella categoria e non nel database (mongo). ", CAPO, vociTotali);
        risultato += String.format("%sTrovate [%s] pagine che NON sono pagine biografiche (templBio). ", CAPO, vociNonBio);
        risultato += String.format("%sCreate [%s] nuove voci di BioServer e BioMongo. ", CAPO, vociCreate);

        return risultato;
    }


    /**
     * Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale <br>
     * Legge tutte le pagine <br>
     * Recupera i contenuti di tutte le voci biografiche da creare/modificare <br>
     * Controlla che esista il tmpl BIO <br>
     * Nella listaWrapBio ci sono solo voci CON il tmpl BIO valido <br>
     *
     * @param listaPageIdsDaModificare tutti i pageIds (long) presenti sul server wiki e da creare/modificare
     */
    public String modificaVociEsistenti(List<Long> listaPageIdsDaModificare) {
        String risultato = VUOTA;
        inizio = System.currentTimeMillis();
        long inizioBloccoPageIds;
        long inizioBloccoWrapBio;
        String message;
        BioServerEntity bioServerBean;
        BioMongoEntity bioMongoBean;
        int numVociBlocco;
        int numVociCreate = 0;
        int numVociNonBio = 0;
        int numVociTotali = listaPageIdsDaModificare.size();
        int bioWikiModificate = listaPageIdsDaModificare.size();
        int bioServerModificate = 0;
        int bioMongoModificate = 0;
        int bioMongoNuove = 0;
        String vociBlocco;
        String vociCreate;
        String vociNonBio;
        String vociWikiModificate;
        String vociServerModificate;
        String vociMongoModificate;
        String vociMongoNuove;
        String vociTotali = textService.format(numVociTotali); ;
        boolean usaNotificationCurrentValue = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);
        int blocco = WPref.bloccoDownload.getInt();
        List<Long> subListPageIds;
        List<WrapBio> listaWrapBio = null;
        LocalDateTime ultimoCheck = WPref.lastDownloadBioServer.getDateTime();
        LocalDateTime adesso = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(ultimoCheck, adesso);

        message = String.format("Vengono creati i [%s] e salvate le entities [%s] in blocchi di [%d] pagine per volta", "WrapBio", "BioServer", blocco);
        logger.info(new WrapLog().message(message).type(TypeLog.bio));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.bio));
        for (int k = 0; k < listaPageIdsDaModificare.size(); k = k + blocco) {
            numVociBlocco = 0;
            inizioBloccoPageIds = System.currentTimeMillis();
            subListPageIds = listaPageIdsDaModificare.subList(k, Math.min(k + blocco, numVociTotali));
            listaWrapBio = queryService.getListaBio(subListPageIds);
            inizioBloccoWrapBio = System.currentTimeMillis();
            for (WrapBio wrapBio : listaWrapBio) {
                if (wrapBio.isValida()) {
                    bioServerBean = wrapBio.getBeanBioServer();
                    if (bioServerModulo.isModificato(bioServerBean)) {
                        bioServerModulo.save(bioServerBean);
                        bioServerModificate++;
                        bioMongoBean = elaboraService.creaBeanMongo(bioServerBean);
                        if (bioMongoModulo.isModificato(bioMongoBean)) {
                            bioMongoModulo.save(bioMongoBean);
                            bioMongoModificate++;
                        }
                    }
                    else {
                        if (!bioMongoModulo.existById(bioServerBean.getId())) {
                            elaboraService.creaModificaBeanMongo(bioServerBean);
                            bioMongoNuove++;
                        }
                    }
                    numVociBlocco++;
                    numVociCreate++;
                }
                else {
                    if (Pref.debug.is()) {
                        // la pagina NON è una biografia
                        numVociNonBio++;
                    }
                }
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

        vociNonBio = textService.format(numVociNonBio);
        vociCreate = textService.format(numVociCreate);
        vociWikiModificate = textService.format(bioWikiModificate - numVociNonBio);
        vociServerModificate = textService.format(bioServerModificate);
        vociMongoModificate = textService.format(bioMongoModificate);
        vociMongoNuove = textService.format(bioMongoNuove);

        risultato += String.format("%sTrovate [%s] pagine che NON sono pagine biografiche (templBio). ", CAPO, vociNonBio);
        risultato += String.format("%sWiki: Pagine wiki modificate [%s] nelle ultime [%d] ore. ", CAPO, vociWikiModificate, hours);
        risultato += String.format("%sBioServer: Property tmplBio di BioServer modificate: [%s]. ", CAPO, vociServerModificate);
        risultato += String.format("%sBioMongo: Parametri di BioMongo modificate: [%s]. ", CAPO, vociMongoModificate);
        if (bioMongoNuove > 0) {
            risultato += String.format("%sBioMongo: Create nuove voci mancanti: [%s]. ", CAPO, vociMongoNuove);
        }

        return risultato;
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

    public String fixInfoDurataCiclo(String cicloMth, String cicloTxt, final long inizio) {
        String message = String.format("Esecuzione di %s() in %s", cicloMth, dateService.deltaText(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.bio));

        return String.format("%s%s eseguito in %s", CAPO, cicloTxt, dateService.deltaText(inizio));
    }

}// end of Service class