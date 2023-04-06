package it.algos.vaad24.backend.boot;

import com.vaadin.flow.component.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.schedule.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 07:19
 * <p>
 * Classe statica (astratta) per le variabili generali dell'applicazione <br>
 * Le variabili (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle variabili è unico per tutta l'applicazione, ma può essere modificato <br>
 * The compiler automatically initializes class fields to their default values before setting them with any
 * initialization values, so there is no need to explicitly set a field to its default value. <br>
 * Further, under the logic that cleaner code is better code, it's considered poor style to do so. <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadVar {

    /**
     * Lista dei moduli di menu del framework base, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<? extends Component>> menuRouteListVaadin;

    /**
     * Lista dei moduli di menu del project corrente, da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<? extends Component>> menuRouteListProject;

    /**
     * Lista delle (eventuali) task per gli eventi Schedule. <br>
     * Deve essere regolato in VaadSchedule o in una sua sottoclasse <br>
     */
    public static List<VaadTask> taskList = new ArrayList<>();

    /**
     * Lista delle preferenze. <br>
     * Lista di quelle base di Vaadin24 <br>
     * Lista (eventuale) di quelle del progetto specifico <br>
     * Deve essere regolato in backend.boot.VaadBoot.fixPreferenze() del progetto base <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixPreferenze() del progetto sèecifico <br>
     */
    public static List<AIGenPref> prefList = new ArrayList<>();


    /**
     * Nome identificativo minuscolo del progetto base vaadin23 <br>
     * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
     */
    public static String frameworkVaadin24;

    /**
     * Nome identificativo minuscolo del modulo base vaad23 <br>
     * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
     */
    public static String moduloVaadin24;

    /**
     * Nome della classe di partenza col metodo 'main' <br>
     * Spesso coincide (non obbligatoriamente) con projectCurrent + Application <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String vaadin24MainApplication;


    /**
     * Nome identificativo minuscolo del progetto corrente <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectCurrent;

    /**
     * Nome della classe di partenza col metodo 'main' <br>
     * Spesso coincide (non obbligatoriamente) con projectCurrent + Application <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectCurrentMainApplication;

    /**
     * Nome identificativo minuscolo dell' applicazione nella directory dei projects Idea <br>
     * Usato come base per costruire i path delle varie directory <br>
     * Spesso coincide (non obbligatoriamente) con projectNameModulo <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNameDirectoryIdea;

    /**
     * Nome identificativo minuscolo del modulo dell' applicazione <br>
     * Usato come parte del path delle varie directory <br>
     * Spesso coincide (non obbligatoriamente) con projectNameIdea <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNameModulo;

    /**
     * Nome identificativo maiuscolo dell' applicazione <br>
     * Usato (eventualmente) nella barra di menu in testa pagina <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectCurrentUpper;


    /**
     * Classe da usare per lo startup del programma <br>
     * Di default VaadData oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabiliRiferimentoIstanzeGenerali() del progetto corrente <br>
     */
    public static VaadData istanzaData;


    /**
     * Classe da usare per gestire le versioni <br>
     * Di default FlowVers oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static Class versionClazz;

    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public static AIEnumPref prefInstance;


    /**
     * Versione dell' applicazione base vaadflow14 <br>
     * Usato solo internamente <br>
     * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto corrente <br>
     */
    public static double vaadin24Version;


    /**
     * Versione dell' applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static double projectVersion;

    /**
     * Data di rilascio della versione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectDate;

    /**
     * Eventuali note di rilascio della versione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNote;

    /**
     * Controlla se l' applicazione è multi-company oppure no <br>
     * Di default uguale a false <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     * Se usaCompany=true anche usaSecurity deve essere true <br>
     */
    public static boolean usaCompany;

    /**
     * Controlla se l' applicazione usa il login oppure no <br>
     * Se si usa il login, occorre la classe SecurityConfiguration <br>
     * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
     * Di default uguale a false <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     * Se usaCompany=true anche usaSecurity deve essere true <br>
     * Può essere true anche se usaCompany=false <br>
     */
    public static boolean usaSecurity;

    /**
     * Titolo del banner <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String vaadin24BannerTitle;

    /**
     * Titolo del banner <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectBannerTitle;

    /**
     * Directory config di recovery sul server Algos <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String serverConfig;

    /**
     * File name per i logger nella directory 'log' <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String logbackName;

    /**
     * Nome del database mongo collegato <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String mongoDatabaseName;

    /**
     * Versione del database mongo collegato <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String mongoDatabaseVersion;

    /**
     * Creazione automatica degli indici del database mongo collegato <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static boolean usaCreazioneAutomaticaIndiciMongoDB;
}