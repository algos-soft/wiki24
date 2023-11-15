package it.algos.base24.backend.boot;

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
     * Nome identificativo minuscolo del framework base2023 <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String frameworkBase2023;

    /**
     * Nome identificativo del progetto corrente <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String projectCurrent;


    /**
     * Nome identificativo minuscolo del progetto corrente <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String projectCurrentLower;

    /**
     * Nome identificativo maiuscolo dell' applicazione <br>
     * Usato (eventualmente) nella barra di menu in testa pagina <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String projectCurrentUpper;


    /**
     * Lista dei moduli di menu del framework base, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da LayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<? extends CrudView>> menuRouteListVaadin;

    /**
     * Lista dei moduli di menu del project corrente, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<? extends CrudView>> menuRouteListProject;


    /**
     * Versione dell' applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static double projectVersion;

    /**
     * Data di rilascio della versione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String projectDate;

    /**
     * Note di rilascio della versione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String projectNote;

    /**
     * Nome del database mongo collegato <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String mongoDatabaseName;

    /**
     * Classe da usare per il Boot iniziale di regolazione <br>
     * Di default BaseBoot oppure una sottoclasse specifica del progetto <br>
     * Deve essere regolata in resources.application.properties <br>
     */
    public static Class bootClazz;

    /**
     * Classe da usare per gestire le versioni <br>
     * Di default BaseVers oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static Class versionClazz;

    /**
     * Path del modulo base <br>
     * Di default BaseVers oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String pathModuloBase;
    /**
     * Path del modulo di progetto <br>
     * Di default BaseVers oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.BaseBoot.fixVariabili() del modulo [base24] <br>
     */
    public static String pathModuloProgetto;

}
