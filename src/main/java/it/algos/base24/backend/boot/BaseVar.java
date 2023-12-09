package it.algos.base24.backend.boot;

import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.ui.view.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 10-Aug-2023
 * Time: 20:46
 * Classe statica (astratta) per le variabili generali dell'applicazione <br>
 * Le variabili (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle variabili è unico per tutta l'applicazione, ma può essere modificato <br>
 * The compiler automatically initializes class fields to their default values before setting them with any
 * initialization values, so there is no need to explicitly set a field to its default value. <br>
 * Further, under the logic that cleaner code is better code, it's considered poor style to do so. <br>
 */
@Service
public class BaseVar {

    /**
     * Nome identificativo del framework base <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String frameworkBase;

    /**
     * Nome identificativo del progetto corrente <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String projectCurrent;


    /**
     * Nome identificativo minuscolo del modulo corrente <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String projectModulo;

    /**
     * Nome identificativo del prefisso di progetto corrente <br>
     * Usato (eventualmente) nella barra di menu in testa pagina <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String projectPrefix;


    /**
     * Versione del progetto corrente <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static double projectVersion;

    /**
     * Data di rilascio della versione di progetto corrente <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String projectDate;

    /**
     * Note di rilascio della versione di progetto corrente <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String projectNote;

    /**
     * Nome del database mongo collegato in esecuzione <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String mongoDatabaseName;


    /**
     * Path del modulo base <br>
     * Di default [it.algos.base24] <br>
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String pathModuloBase;

    /**
     * Path del modulo di progetto <br>
     * Non esiste default. Probabilmente [it.algos.xxx]
     * Regolata in backend.boot.BaseBoot.fixVariabiliProperty() del modulo [base24] <br>
     */
    public static String pathModuloProgetto;


    /**
     * Lista delle classi @Route di tipo [CrudView] del framework base <br>
     * Da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Usata da LayoutService per conto di MainLayout allo start della UI-logic <br>
     * Le view sono istanze SCOPE_PROTOTYPE <br>
     * Regolata in backend.boot.BaseBoot.fixMenuRoutes() del modulo [base24] <br>
     */
    public static List<Class<? extends CrudView>> menuRouteListVaadin = new ArrayList<>();

    /**
     * Lista delle classi @Route di tipo [CrudView] del progetto corrente <br>
     * Da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Usata da LayoutService per conto di MainLayout allo start della UI-logic <br>
     * Le view sono istanze SCOPE_PROTOTYPE <br>
     * Regolata in backend.boot.XxxBoot.fixMenuRoutes() del modulo [progettoCorrente] <br>
     */
    public static List<Class<? extends CrudView>> menuRouteListProject = new ArrayList<>();

    /**
     * Lista dei nomi delle views (@Routes) del framework base. <br>
     * Le view sono istanze SCOPE_PROTOTYPE <br>
     * Regolata in backend.boot.BaseBoot.fixMenuRoutes() del modulo [base24] <br>
     */
    public static List<String> nameViewListVaadin = new ArrayList<>();

    /**
     * Lista dei nomi delle views (@Routes) del project corrente. <br>
     * Le view sono istanze SCOPE_PROTOTYPE <br>
     * Regolata in backend.boot.XxxBoot.fixMenuRoutes() del modulo [progettoCorrente] <br>
     */
    public static List<String> nameViewListProject = new ArrayList<>();


    /**
     * Lista delle istanze di [CrudModulo] del framework base. <br>
     * I moduli sono istanze SCOPE_SINGLETON <br>
     * Regolata nel metodo postConstruct() di ogni istanza del modulo [base24] <br>
     */
    public static List<CrudModulo> crudModuloListVaadin = new ArrayList<>();


    /**
     * Lista delle istanze di [CrudModulo] del project corrente. <br>
     * I moduli sono istanze SCOPE_SINGLETON <br>
     * Regolata nel metodo postConstruct() di ogni istanza del modulo [progettoCorrente] <br>
     */
    public static List<CrudModulo> crudModuloListProject = new ArrayList<>();


    /**
     * Lista dei nomi dei moduli [CrudModulo] del framework base. <br>
     * I moduli sono istanze SCOPE_SINGLETON <br>
     * Regolata nel metodo postConstruct() di ogni istanza del modulo [base24] <br>
     */
    public static List<String> nameModuloListVaadin = new ArrayList<>();


    /**
     * Lista dei nomi dei moduli [CrudModulo] del project corrente. <br>
     * I moduli sono istanze SCOPE_SINGLETON <br>
     * Regolata nel metodo postConstruct() di ogni istanza del modulo [progettoCorrente] <br>
     */
    public static List<String> nameModuloListProject = new ArrayList<>();

    /**
     * Classe da usare per il Boot iniziale di regolazione <br>
     * Di default [BaseBoot] oppure una sottoclasse specifica del progetto <br>
     * Regolata nel metodo doSomethingAfterStartup() di [Application] <br>
     */
    public static Class bootClazz;

    /**
     * Qualifier da usare per il Boot iniziale di regolazione <br>
     * Di default [baseBoot] oppure un 'qualifier' specifico della classe xxxBoot di progetto <br>
     * Regolata nel metodo doSomethingAfterStartup() di [Application] <br>
     */
    public static String bootClazzQualifier;

    /**
     * Lista di tutte le [Preferenze] del framework base e del project corrente <br>
     * Le preferenze, di tipo [IPref], sono Enumeration <br>
     * Regolata in [BaseBoot.addPreferenze()] per le preferenze di [Base24] <br>
     * Regolata in [bootClazz.addPreferenze()] per le preferenze del [progettoCorrente] <br>
     */
    public static List<IPref> prefList = new ArrayList<>();

}
