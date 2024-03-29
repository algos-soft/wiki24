package it.algos.vaad24.backend.boot;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.packages.utility.logger.*;
import it.algos.vaad24.backend.packages.utility.nota.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.packages.utility.versione.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.utility.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.wizard.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 08:03
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * Any class that use the @EventListener annotation, will be executed before the application is up and its
 * onContextRefreshEvent method will be called
 * <p>
 * Questa classe astratta riceve un @EventListener dalla sottoclasse concreta alla partenza del programma <br>
 * Deve essere creata una sottoclasse (obbligatoria) per l' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@Service
public class VaadBoot {

    public static void start() {
        VaadBoot.CREA_LISTA_PREFERENZE_VAADIN();
    }

    /**
     * Crea le Enumeration in memoria <br>
     * Aggiunge le singole Enumeration alla lista globale <br>
     * NON usa la injection di SpringBoot <br>
     * NON crea le preferenze su mondoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public static void CREA_LISTA_PREFERENZE_VAADIN() {
        for (Pref pref : Pref.values()) {
            VaadVar.prefList.add(pref);
        }

        Logger slf4jLogger = LoggerFactory.getLogger(TAG_LOG_ADMIN);
        slf4jLogger.warn("Preferenze - Aggiunge le singole Enumeration generali alla lista globale 'VaadVar.prefList' (metodo statico)");
    }

    protected boolean allDebugSetup;

    private String property;

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public AIVers versInstance;

    //    /**
    //     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    public AIData dataInstance;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public Environment environment;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected MongoService mongoService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected DateService dateService;

    @Autowired
    protected PreferenceService preferenceService;

    @Autowired
    protected PreferenzaBackend preferenzaBackend;

    @Autowired
    protected ViaBackend viaBackend;

    @Autowired
    protected ContinenteBackend continenteBackend;

    @Autowired
    protected GiornoBackend giornoBackend;

    @Autowired
    protected AnnoBackend annoBackend;

    @Autowired
    protected SecoloBackend secoloBackend;

    @Autowired
    protected MeseBackend meseBackend;

    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public VaadBoot() {
        //        this.setMongo(mongo);
        //        this.setLogger(logger)
        //        this.setEnvironment(environment);
        this.setVersInstance(versInstance);
        //        this.setDataInstance(dataInstance);
    }// end of constructor with @Autowired on setter


    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) regola alcuni parametri standard del database MongoDB <br>
     * 2) regola le variabili generali dell'applicazione <br>
     * 3) crea le preferenze standard e specifiche dell'applicazione <br>
     * 4) crea i dati di alcune collections sul DB mongo <br>
     * 5) aggiunge al menu le @Route (view) standard e specifiche <br>
     * 6) lancia gli schedulers in background <br>
     * 7) costruisce una versione demo <br>
     * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void inizia() {
        this.fixVariabili();
        this.fixEnumerationPreferenze();
        this.creaPreferenzeMongoDB();
        logger.setUpIni();

        this.printInfo();
        this.fixEnvironment();
        this.fixDBMongo();
        this.fixDebug();

        this.fixVariabiliRiferimentoIstanzeGenerali();
        this.fixMenuRoutes();
        this.fixData();
        this.fixVersioni();
        this.fixSchedule();
        this.fixLogin();

        logger.setUpEnd();
    }


    /**
     * Injection di SpringBoot <br>
     * Usa la injection di SpringBoot per ogni Enumeration della lista globale <br>
     * NON crea le preferenze su mondoDB <br>
     * Non deve essere sovrascritto <br>
     */
    public void fixEnumerationPreferenze() {
        for (AIGenPref pref : VaadVar.prefList) {
            pref.setText(textService);
            pref.setLogger(logger);
            pref.setDate(dateService);
            pref.setPreferenceService(preferenceService);
        }
    }


    /**
     * Crea le preferenze su mondoDB <br>
     * Non deve essere sovrascritto <br>
     */
    public void creaPreferenzeMongoDB() {
        preferenzaBackend.resetDownload();
    }

    public void allResetOnlyEmpty() {
        viaBackend.resetOnlyEmpty();
        continenteBackend.resetOnlyEmpty();
        giornoBackend.resetOnlyEmpty();
        annoBackend.resetOnlyEmpty();
        secoloBackend.resetOnlyEmpty();
        meseBackend.resetOnlyEmpty();
    }


    public void printInfo() {
        printInfo("VaadVar.frameworkVaadin24", VaadVar.frameworkVaadin24);
        printInfo("VaadVar.moduloVaadin24", VaadVar.moduloVaadin24);

        printInfo("VaadVar.projectCurrentUpper", VaadVar.projectCurrentUpper);
        printInfo("VaadVar.projectCurrent", VaadVar.projectCurrent);
        printInfo("VaadVar.projectNameModulo", VaadVar.projectNameModulo);
        printInfo("VaadVar.projectCurrentMainApplication", VaadVar.projectCurrentMainApplication);

        printInfo("VaadVar.mongoDatabaseName", VaadVar.mongoDatabaseName);
        printInfo("VaadVar.mongoDatabaseVersion", VaadVar.mongoDatabaseVersion);
        printInfo("VaadVar.usaCreazioneAutomaticaIndiciMongoDB", VaadVar.usaCreazioneAutomaticaIndiciMongoDB ? "vero" : "falso");
        printInfo("VaadVar.projectDate", VaadVar.projectDate);
        printInfo("VaadVar.projectNote", VaadVar.projectNote);
    }

    private void printInfo(String nome, String valore) {
        String message;
        if (textService.isValid(valore)) {
            message = String.format("%s: %s", nome, valore);
            logger.info(new WrapLog().message(message).type(AETypeLog.info));
        }
    }

    /**
     * Controllo di alcune regolazioni
     */
    public void fixEnvironment() {
        String message;
        String databaseName;
        String databaseVersion = "6-?";
        String autoIndexCreation;
        String allDebugSetupTxt;

        if (environment == null) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la property 'environment'")).usaDb());
            return;
        }

        allDebugSetupTxt = environment.getProperty("algos.vaadin23.all.debug.setup");
        if (allDebugSetupTxt != null && allDebugSetupTxt.length() > 0 && allDebugSetupTxt.equals(VERO)) {
            allDebugSetup = true;
        }

        //        databaseName = environment.getProperty("spring.data.mongodb.database");
        //        message = String.format("Database mongo in uso: %s", databaseName);
        //        logger.info(new WrapLog().message(message).type(AETypeLog.info));
        //
        //        MongoDatabase db = mongoService.getDataBase();
        //                MongoDatabase dbAdmin = mongoService.getDBAdmin();
        //        message = String.format("Database mongo versione: %s", databaseVersion);
        //        logger.info(new WrapLog().message(message).type(AETypeLog.info));

        autoIndexCreation = environment.getProperty("spring.data.mongodb.auto-index-creation");
        message = String.format("Auto creazione degli indici (per la classi @Document): %s", autoIndexCreation);
        if (allDebugSetup) {
            logger.info(new WrapLog().message(message).type(AETypeLog.info));
        }
    }

    /**
     * Regola alcuni parametri standard del database MongoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDBMongo() {
        if (allDebugSetup) {
            mongoService.getMaxBlockingSortBytes();//@todo da controllare
            mongoService.fixMaxBytes();//@todo da controllare
        }
    }

    /**
     * Costruisce alcune istanze generali dell'applicazione e ne mantiene i riferimenti nelle apposite variabili <br>
     * Le istanze (prototype) sono uniche per tutta l' applicazione <br>
     * Vengono create SOLO in questa classe o in una sua sottoclasse <br>
     * La selezione su quale istanza creare tocca a questa sottoclasse xxxBoot <br>
     * Se la sottoclasse non ha creato l'istanza, ci pensa la superclasse <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    protected void fixVariabiliRiferimentoIstanzeGenerali() {
        /**
         * Istanza da usare per lo startup del programma <br>
         * Di default VaadData oppure possibile sottoclasse del progetto xxxData <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabiliRiferimentoIstanzeGenerali() del progetto corrente <br>
         */
        VaadVar.istanzaData = VaadVar.istanzaData == null ? appContext.getBean(VaadData.class) : VaadVar.istanzaData;

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = VaadVers.class;
    }

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabili() {

        /**
         * Nome identificativo maiuscolo del progetto base vaadin24 <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        try {
            property = "algos.vaad24.name";
            VaadVar.frameworkVaadin24 = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Nome identificativo minuscolo del modulo base vaad24 <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        try {
            property = "algos.vaad24.module";
            VaadVar.moduloVaadin24 = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Classe 'main' di partenza del framework base vaad24 <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        try {
            property = "algos.vaad24.application";
            VaadVar.vaadin24MainApplication = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Lista dei moduli di menu del framework base, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        VaadVar.menuRouteListVaadin = new ArrayList<>();

        /**
         * Lista dei moduli di menu del project corrente, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        VaadVar.menuRouteListProject = new ArrayList<>();


        /**
         * Versione dell' applicazione base vaadflow14 <br>
         * Usato solo internamente <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.vaadin24Version = Double.parseDouble(Objects.requireNonNull(environment.getProperty("algos.vaad24.version")));

        /**
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         */
        VaadVar.usaCompany = false;

        /**
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         * Può essere true anche se usaCompany=false <br>
         */
        VaadVar.usaSecurity = false;

        /**
         * Titolo del banner <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.vaadin24BannerTitle = " _______ _______ _______ _______ _______ _______\n" +
                "|\\     /|\\     /|\\     /|\\     /|\\     /|\\     /|\n" +
                "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
                "| |   | | |   | | |   | | |   | | |   | | |   | |\n" +
                "| |v  | | |a  | | |a  | | |d  | | |2  | | |4  | |\n" +
                "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
                "|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|\n";

        /**
         * Nome della classe di partenza col metodo 'main' <br>
         * Spesso coincide (non obbligatoriamente) con projectCurrent + Application <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.application";
            VaadVar.projectCurrentMainApplication = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Nome identificativo maiuscolo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.name";
            VaadVar.projectCurrentUpper = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Nome identificativo minuscolo del modulo dell' applicazione <br>
         * Usato come parte del path delle varie directory <br>
         * Spesso coincide (non obbligatoriamente) con projectNameIdea <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.nameModulo";
            VaadVar.projectNameModulo = Objects.requireNonNull(environment.getProperty(property)).toLowerCase();
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Nome identificativo minuscolo del progetto corrente <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.name";
            VaadVar.projectCurrent = Objects.requireNonNull(environment.getProperty(property)).toLowerCase();
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
            VaadVar.projectCurrent = "simple";
        }

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.version";
            VaadVar.projectVersion = Double.parseDouble(Objects.requireNonNull(environment.getProperty(property)));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Data di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.version.date";
            VaadVar.projectDate = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Note di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.project.version.note";
            VaadVar.projectNote = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Directory config di recovery sul server Algos <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.serverConfig = WebService.URL_BASE_VAADIN24_CONFIG;

        //        /**
        //         * File name per i logger nella directory 'log' <br>
        //         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
        //         */
        //        try {
        //            property = "logging.algos.admin";
        //            VaadVar.logbackName = Objects.requireNonNull(environment.getProperty(property));
        //        } catch (Exception unErrore) {
        //            String message = String.format("Non ho trovato la property %s nelle risorse", property);
        //            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        //            VaadVar.logbackName = TAG_LOG_ADMIN;
        //        }

        /**
         * Nome del database mongo collegato <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "spring.data.mongodb.database";
            VaadVar.mongoDatabaseName = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Versione del database mongo collegato <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        if (mongoService != null) {
            VaadVar.mongoDatabaseVersion = mongoService.versione();
        }

        /**
         * Creazione automatica degli indici del database mongo collegato <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "spring.data.mongodb.auto-index-creation";
            VaadVar.usaCreazioneAutomaticaIndiciMongoDB = Objects.requireNonNull(environment.getProperty(property) == "true");
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

    }

    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in VaadVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixMenuRoutes() {
        //        VaadVar.menuRouteList.add(HelloWorldView.class);
        //        VaadVar.menuRouteList.add(AboutView.class);
        //        VaadVar.menuRouteList.add(AddressFormView.class);
        //        VaadVar.menuRouteList.add(CarrelloFormView.class);
        //        VaadVar.menuRouteList.add(ContinenteView.class);
        VaadVar.menuRouteListVaadin.add(WizardView.class);
        VaadVar.menuRouteListVaadin.add(UtilityView.class);
        VaadVar.menuRouteListVaadin.add(NotaView.class);
        VaadVar.menuRouteListVaadin.add(VersioneView.class);
        VaadVar.menuRouteListVaadin.add(ALoggerView.class);
        VaadVar.menuRouteListVaadin.add(PreferenzaView.class);
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(QUALIFIER_VERSION_VAAD)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }

    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    @Qualifier(QUALIFIER_DATA_VAAD)
    //    public void setDataInstance(final AIData dataInstance) {
    //        this.dataInstance = dataInstance;
    //    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(QUALIFIER_PREFERENCES_VAAD)
    public void setPrefInstance(final AIEnumPref prefInstance) {
        VaadVar.prefInstance = prefInstance;
    }


    /**
     * Inizializzazione dei database di vaadinFlow <br>
     * Inizializzazione dei database del programma specifico <br>
     */
    protected void fixData() {
    }

    /**
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    protected void fixVersioni() {
        this.versInstance.inizia();
    }


    /**
     * Eventuale collegamento <br>
     * Sviluppato nelle sottoclassi <br>
     */
    public void fixLogin() {
    }

    public void fixDebug() {
        String message;

        if (Pref.debug != null && Pref.debug.is()) {
            message = "Stiamo girando in modalità 'debug'";
        }
        else {
            message = "Stiamo girando in modalità 'normale'";
        }
        logger.info(new WrapLog().message(message).type(AETypeLog.setup));
    }


    /**
     * Eventuali task <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixSchedule() {
        String message;

        if (VaadVar.taskList != null && VaadVar.taskList.size() > 0) {
            for (VaadTask task : VaadVar.taskList) {
                message = task.infoFlag();
                logger.info(new WrapLog().message(message).type(AETypeLog.schedule));
            }
        }
        else {
            message = String.format("Nel modulo %s non ci sono 'task'", VaadVar.projectNameModulo);
            logger.info(new WrapLog().message(message).type(AETypeLog.schedule));
        }

        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
    }


}