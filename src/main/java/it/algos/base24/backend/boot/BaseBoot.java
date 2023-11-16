package it.algos.base24.backend.boot;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 10-Aug-2023
 * Time: 20:52
 */
@Service
public class BaseBoot {

    @Autowired
    public Environment environment;

    @Autowired
    public BaseVar baseVar;

    @Autowired
    public PreferenzaModulo preferenzaModulo;

    @Autowired
    public LogService logger;

    @Autowired
    public TextService textService;

    @Autowired
    public ResourceService resourceService;

    @Autowired
    public ReflectionService reflectionService;

    @Autowired
    public AnnotationService annotationService;

    private String property;

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.fixVariabili();
    }

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

        //        this.creaPreferenzeMongoDB();
        //        logger.setUpIni();

        //        this.printInfo();
        //        this.fixDBMongo();
        //        this.fixDebug();

        //        this.fixVariabiliRiferimentoIstanzeGenerali();
        this.fixMenuRoutes();
        //        this.fixData();
        //        this.fixVersioni();
        //        this.fixSchedule();
        //        this.fixLogin();

        //        logger.setUpEnd();
    }

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Alcuni valori sono hardcoded, altri sono 'letti' da [application.properties] <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabili() {

        /**
         * Nome identificativo minuscolo del framework base2023 <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        BaseVar.frameworkBase2023 = "base2023";

        /**
         * Nome del database mongo collegato <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        BaseVar.mongoDatabaseName = "base2023";

        /**
         * Nome identificativo del progetto corrente <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.name";
            BaseVar.projectCurrent = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome identificativo minuscolo del progetto corrente <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.name";
            BaseVar.projectCurrentLower = Objects.requireNonNull(environment.getProperty(property)).toLowerCase();
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome identificativo maiuscolo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.name";
            BaseVar.projectCurrentUpper = Objects.requireNonNull(environment.getProperty(property).toUpperCase());
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.version";
            BaseVar.projectVersion = Double.parseDouble(Objects.requireNonNull(environment.getProperty(property)));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Data di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.version.date";
            BaseVar.projectDate = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Note di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.version.note";
            BaseVar.projectNote = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Lista dei moduli di menu del framework base, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        BaseVar.menuRouteListVaadin = new ArrayList<>();

        /**
         * Lista dei moduli di menu del project corrente, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        BaseVar.menuRouteListProject = new ArrayList<>();

        /**
         * Classe da usare per il Boot iniziale di regolazione <br>
         * Di default BaseBoot oppure una sottoclasse specifica del progetto <br>
         * Deve essere regolata in resources.application.properties <br>
         */
        try {
            property = "algos.project.modulo";
            String projectModulo = Objects.requireNonNull(environment.getProperty(property));
            property = "algos.project.name";
            String projectName = Objects.requireNonNull(environment.getProperty(property));
            BaseVar.bootClazz = resourceService.getClazzBoot(projectModulo, projectName);
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Classe da usare per il Boot iniziale di regolazione <br>
         * Di default BaseBoot oppure una sottoclasse specifica del progetto <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.base24.modulo";
            String moduloBase = Objects.requireNonNull(environment.getProperty(property));
            BaseVar.pathModuloBase = PATH_ALGOS + PUNTO + moduloBase;
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Path del modulo di progetto <br>
         * Di default BaseVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
         */
        try {
            property = "algos.project.modulo";
            String moduloProject = Objects.requireNonNull(environment.getProperty(property));
            BaseVar.pathModuloProgetto = PATH_ALGOS + PUNTO + moduloProject;
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default BaseVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolata in backend.boot.BaseBoot.fixVariabili() del modulo [base2023] <br>
         */
        //        public static Class versionClazz;
    }

    /**
     * Injection di SpringBoot <br>
     * Usa la injection di SpringBoot per ogni Enumeration della lista globale <br>
     * NON crea le preferenze su mondoDB <br>
     * Non deve essere sovrascritto <br>
     */
    public void fixEnumerationPreferenze() {
        for (Pref pref : Pref.values()) {
            pref.preferenzaModulo = this.preferenzaModulo;
        }
    }


    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixMenuRoutes() {
        if (Pref.usaMenuAutomatici.is()) {
            for (Class clazz : reflectionService.getSubClazzViewBase()) {
                if (annotationService.usaMenuAutomatico(clazz)) {
                    BaseVar.menuRouteListVaadin.add(clazz);
                }
            }
        }
        else {
            BaseVar.menuRouteListVaadin.add(RoleView.class);
            BaseVar.menuRouteListVaadin.add(PreferenzaView.class);
            BaseVar.menuRouteListVaadin.add(ViaView.class);
        }

    }

    private void logError(Exception unErrore, String property) {
        String message = String.format("Non ho trovato la property %s nelle risorse", property);
        logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
    }

}
