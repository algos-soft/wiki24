package it.algos.wiki24.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.utility.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.doppionome.*;
import it.algos.wiki24.backend.packages.errore.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.pagina.*;
import it.algos.wiki24.backend.packages.parametro.*;
import it.algos.wiki24.backend.packages.statistica.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.utility.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.*;

import javax.annotation.*;
import javax.servlet.*;

/**
 * Project Wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29 apr 22
 * <p>
 * Questa classe astratta riceve un @EventListener implementato nella superclasse <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Wiki24Boot extends VaadBoot implements ServletContextListener {

    public static void start() {
        Wiki24Boot.CREA_LISTA_PREFERENZE_SPECIFICHE();
    }

    /**
     * Crea le Enumeration in memoria <br>
     * Aggiunge le singole Enumeration alla lista globale <br>
     * NON usa la injection di SpringBoot <br>
     * NON crea le preferenze su mondoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    private static void CREA_LISTA_PREFERENZE_SPECIFICHE() {
        for (WPref pref : WPref.values()) {
            VaadVar.prefList.add(pref);
        }

        Logger slf4jLogger = LoggerFactory.getLogger(TAG_LOG_ADMIN);
        slf4jLogger.warn("Preferenze - Aggiunge le singole Enumeration specifiche alla lista globale 'VaadVar.prefList' (metodo statico)");
    }

    private String property;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public QueryService queryService;

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Autowired
    public NazSingolareBackend nazSingolareBackend;

    @Autowired
    public NazPluraleBackend nazPluraleBackend;


    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti <br>
     * L'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una istanza di sottoclasse, passa di qui per ogni istanza <br>
     */
    @PostConstruct
    private void postConstruct() {
        this.inizia();
    }

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        //        this.allResetOnlyEmpty();
    }

    public void allResetOnlyEmpty() {
        super.allResetOnlyEmpty();

        giornoWikiBackend.resetOnlyEmpty();
        annoWikiBackend.resetOnlyEmpty();
        attSingolareBackend.resetOnlyEmpty();
        attPluraleBackend.resetOnlyEmpty();
        nazSingolareBackend.resetOnlyEmpty();
        nazPluraleBackend.resetOnlyEmpty();
    }

    /**
     * Costruisce alcune istanze generali dell'applicazione e ne mantiene i riferimenti nelle apposite variabili <br>
     * Le istanze (prototype) sono uniche per tutta l' applicazione <br>
     * Vengono create SOLO in questa classe o in una sua sottoclasse <br>
     * La selezione su quale istanza creare tocca a questa sottoclasse xxxBoot <br>
     * Se la sottoclasse non ha creato l'istanza, ci pensa la superclasse <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabiliRiferimentoIstanzeGenerali() {
        /*
         * Classe da usare per lo startup del programma <br>
         * Di default FlowData oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabiliRiferimentoIstanzeGenerali() del progetto corrente <br>
         */
        VaadVar.istanzaData = appContext.getBean(Wiki24Data.class);

        /*
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = Wiki24Vers.class;

        super.fixVariabiliRiferimentoIstanzeGenerali();
    }

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabili() {
        super.fixVariabili();

        /**
         * Nome identificativo minuscolo del progetto corrente <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectCurrent = "wiki24";

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = Wiki24Vers.class;

        /**
         * Titolo del banner <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectBannerTitle = " _______ _______ _______ _______ _______ _______\n" +
                "|\\     /|\\     /|\\     /|\\     /|\\     /|\\     /|\n" +
                "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
                "| |   | | |   | | |   | | |   | | |   | | |   | |\n" +
                "| |w  | | |i  | | |k  | | |i  | | |2  | | |4  | |\n" +
                "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
                "|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|\n";

        /**
         * Schedule per ogni task del programma <br>
         */
        Wiki24Var.typeSchedule = AETypeSchedule.schema2;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_WIKI23_VERSION)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }


    /**
     * Eventuali task <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixSchedule() {
        appContext.getBean(Wiki23Schedule.class);

        super.fixSchedule();
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
    @Override
    protected void fixMenuRoutes() {
        super.fixMenuRoutes();

        //sostituisce un link
        Object daSostituire = UtilityView.class;
        if (VaadVar.menuRouteListVaadin.contains(daSostituire)) {
            VaadVar.menuRouteListVaadin.remove(daSostituire);
        }
        VaadVar.menuRouteListVaadin.add(ViaView.class);
        VaadVar.menuRouteListVaadin.add(ContinenteView.class);

        VaadVar.menuRouteListProject.add(WikiUtilityView.class);
        VaadVar.menuRouteListProject.add(GiornoWikiView.class);
        VaadVar.menuRouteListProject.add(AnnoWikiView.class);
        VaadVar.menuRouteListProject.add(AttSingolareView.class);
        VaadVar.menuRouteListProject.add(AttPluraleView.class);
        VaadVar.menuRouteListProject.add(NazSingolareView.class);
        VaadVar.menuRouteListProject.add(NazPluraleView.class);

        //        VaadVar.menuRouteListProject.add(GenereView.class);
        //        VaadVar.menuRouteListProject.add(ProfessioneView.class);
        VaadVar.menuRouteListProject.add(DoppioNomeView.class);
        VaadVar.menuRouteListProject.add(CognomeView.class);
        VaadVar.menuRouteListProject.add(BioView.class);
        VaadVar.menuRouteListProject.add(BioTempView.class);
        VaadVar.menuRouteListProject.add(StatisticaBioView.class);
        VaadVar.menuRouteListProject.add(ErroreBioView.class);
        VaadVar.menuRouteListProject.add(PaginaView.class);
        VaadVar.menuRouteListProject.add(ParametroView.class);
    }


    @Override
    public void fixLogin() {
        queryService.logAsBot();
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
    }

}
