package it.algos.base24.backend.boot;

import com.google.common.base.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.base24.backend.boot.BaseVar.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.schedule.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.context.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.lang.reflect.*;
import java.time.*;
import java.util.Objects;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 10-Aug-2023
 * Time: 20:52
 */
@Service
@Component("baseBoot")
public class BaseBoot {

    @Inject
    public Environment environment;
    @Inject
    public ApplicationContext appContext;

    @Inject
    public BaseVar baseVar;

    @Inject
    public PreferenzaModulo preferenzaModulo;

    @Inject
    public LogService logger;

    @Inject
    public TextService textService;

    @Inject
    public ResourceService resourceService;

    @Inject
    public ReflectionService reflectionService;

    @Inject
    public AnnotationService annotationService;

    @Inject
    public DateService dateService;

    private String property;

    //    public BaseBoot() {
    //        BaseVar.bootClazz = this.getClass();
    //    }

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
        //        this.fixVariabili();
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
        this.fixVariabiliProperty();
        this.addPreferenze();
        this.fixEnumerationPreferenze();
        this.creaPreferenzeMongoDB();
        //        logger.setUpIni();

        //        this.fixDBMongo();
        //        this.fixDebug();

        //        this.fixVariabiliRiferimentoIstanzeGenerali();
        this.fixMenuRoutes();
        this.fixTask();
        this.printInfo();
        //        this.fixData();
        //        this.fixVersioni();
        this.fixLogin();

        //        logger.setUpEnd();
    }


    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * I valori sono 'letti' da [application.properties] <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabiliProperty() {

        /**
         * Nome identificativo del framework base <br>
         */
        try {
            property = "algos.base24.name";
            BaseVar.frameworkBase = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome identificativo del progetto corrente <br>
         */
        try {
            property = "algos.project.current";
            BaseVar.projectCurrent = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome identificativo minuscolo del modulo corrente <br>
         */
        try {
            property = "algos.project.modulo";
            BaseVar.projectModulo = Objects.requireNonNull(environment.getProperty(property)).toLowerCase();
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome identificativo del prefisso di progetto corrente <br>
         */
        try {
            property = "algos.project.prefix";
            BaseVar.projectPrefix = Objects.requireNonNull(textService.primaMaiuscola(environment.getProperty(property)));
            int a = 87;
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Versione del progetto corrente <br>
         */
        try {
            property = "algos.project.version";
            BaseVar.projectVersion = Double.parseDouble(Objects.requireNonNull(environment.getProperty(property)));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Data di rilascio della versione di progetto corrente <br>
         */
        try {
            property = "algos.project.version.date";
            BaseVar.projectDate = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Note di rilascio della versione di progetto corrente <br>
         */
        try {
            property = "algos.project.version.note";
            BaseVar.projectNote = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Nome del database mongo collegato in esecuzione <br>
         */
        try {
            property = "spring.data.mongodb.database";
            BaseVar.mongoDatabaseName = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        /**
         * Path del modulo base <br>
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
         */
        try {
            property = "algos.project.modulo";
            String moduloProject = Objects.requireNonNull(environment.getProperty(property));
            BaseVar.pathModuloProgetto = PATH_ALGOS + PUNTO + moduloProject;
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }
    }

    /**
     * Aggiunta delle preferenze (Enumeration) alla lista BaseVar.prefList <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void addPreferenze() {
        for (Pref pref : Pref.values()) {
            prefList.add(pref);
        }
    }

    /**
     * Injection di SpringBoot <br>
     * Usa la injection di SpringBoot per ogni Enumeration della lista globale <br>
     * NON crea le preferenze su mondoDB <br>
     * Non deve essere sovrascritto <br>
     */
    public void fixEnumerationPreferenze() {
        for (IPref pref : prefList) {
            pref.setPreferenzaModulo(preferenzaModulo);
        }
    }

    public void creaPreferenzeMongoDB() {
        preferenzaModulo.resetPref();
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
        List<Class> listaViewsBase;
        String message;
        String viewName;

        if (Pref.usaMenuAutomatici.is()) {
            listaViewsBase = reflectionService.getSubClazzViewBase();
            if (listaViewsBase != null) {
                for (Class clazz : reflectionService.getSubClazzViewBase()) {
                    if (annotationService.usaMenuAutomatico(clazz)) {
                        menuRouteListVaadin.add(clazz);
                        viewName = clazz.getSimpleName();
                        viewName = textService.levaCoda(viewName, SUFFIX_VIEW);
                        BaseVar.nameViewListVaadin.add(viewName);
                    }
                }
            }
            else {
                message = "Non esiste nessuna [view] nel progetto [base]";
                logger.warn(new WrapLog().exception(new Exception(message)));
            }
        }
        else {
            menuRouteListVaadin.add(RoleView.class);
            menuRouteListVaadin.add(PreferenzaView.class);
            menuRouteListVaadin.add(ViaView.class);
        }

    }

    private void logError(Exception unErrore, String property) {
        String message = String.format("Non ho trovato la property %s nelle risorse", property);
        logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
    }

    protected void fixTask() {
//        BaseVar.taskList.add(appContext.getBean(TaskProva.class));
        appContext.getBean(BaseSchedule.class).start();
    }

    private void printInfo() {
        if (Pref.debug.is()) {
            this.printInfoVariabili();
            this.printInfoPreferenze();
            this.printInfoModuli();
            this.printInfoTask();
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.startup));
        }
    }


    private void printInfoVariabili() {
        List<Field> listaVar;
        Object value;
        String message;

        listaVar = Arrays.stream(BaseVar.class.getFields())
                .filter(element -> !element.getName().equals("menuRouteListVaadin"))
                .filter(element -> !element.getName().equals("menuRouteListProject"))
                .filter(element -> !element.getName().equals("crudModuloListVaadin"))
                .filter(element -> !element.getName().equals("crudModuloListProject"))
                .filter(element -> !element.getName().equals("prefList"))
                .toList();
        if (listaVar != null) {
            message = "Variabili globali";
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.startup));
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));

            for (Field field : listaVar) {
                value = reflectionService.getPropertyValue(baseVar, field.getName());
                message = String.format("%s%s%s", field.getName(), FORWARD, value);
                logger.info(new WrapLog().message(message).type(TypeLog.startup));
            }
        }
    }


    private void printInfoPreferenze() {
        Object currentValue;
        String message;

        if (Pref.debug.is()) {
            message = "Valori correnti [default] delle preferenze più rilevanti";
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));

            for (IPref pref : prefList) {
                if (pref.isCritical()) {
                    currentValue = pref.getType() == TypePref.localdatetime ? dateService.get((LocalDateTime) pref.getCurrentValue()) : pref.getCurrentValue();
                    message = String.format("%s%s%s", pref.getKeyCode(), FORWARD, currentValue);
                    if (!pref.isDinamica()) {
                        message += String.format("%s[%s]", SPAZIO, pref.getDefaultValue());
                    }
                    logger.info(new WrapLog().message(message).type(TypeLog.startup));
                }
            }
        }
    }

    private void printInfoModuli() {
        String message = "Moduli controllo del resetStartup";
        logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
        logger.info(new WrapLog().message(message).type(TypeLog.startup));
        logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));

        for (CrudModulo modulo : BaseVar.crudModuloListVaadin) {
            modulo.checkReset();
        }
    }

    private void printInfoTask() {
        String message;

        if (BaseVar.taskList != null && BaseVar.taskList.size() > 0) {
            message = "Task previste (scheduled)";
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
            for (BaseTask task : BaseVar.taskList) {
                message = String.format("%s%s%s", task.getPattern(), FORWARD, task.getDescrizioneTask());
                logger.info(new WrapLog().message(message).type(TypeLog.startup));
            }
        }
        else {
            message = String.format("Nel progetto [%s] non sono previste task (scheduled)", projectCurrent);
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
            logger.info(new WrapLog().message(Strings.repeat(TRATTINO, message.length())).type(TypeLog.startup));
        }
    }

    /**
     * Eventuale collegamento <br>
     * Sviluppato nelle sottoclassi <br>
     */
    protected void fixLogin() {
    }

}
