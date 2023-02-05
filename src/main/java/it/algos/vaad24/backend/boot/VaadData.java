package it.algos.vaad24.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * <p>
 * Superclasse astratta per la costruzione iniziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite il <br>
 * metodo FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
 *
 * @since java 8
 */
@SpringComponent
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VaadData extends AbstractService {


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(VaadData.class); <br>
     * Non utilizzato e non necessario <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public VaadData() {
    }// end of constructor not @Autowired

    //    /**
    //     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
    //     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
    //     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
    //     * <p>
    //     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti <br>
    //     * L'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
    //     * Se viene implementata una istanza di sottoclasse, passa di qui per ogni istanza <br>
    //     */
    //    @PostConstruct
    //    private void postConstruct() {
    ////        this.resetData();
    //    }


    /**
     * Check iniziale. A ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    protected void resetData() {
        String message;

        boolean isJar = reflectionService.isJarRunning();
        message = String.format("Stiamo girando da %s", isJar ? "JAR" : "IDE");
        logger.info(new WrapLog().message(message).type(AETypeLog.info));

        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
        resetData(VaadVar.moduloVaadin24);
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
        resetData(VaadVar.projectCurrent);
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
    }


    /**
     * Check iniziale. A ogni avvio del programma spazzola tutte le collections <br>
     * Per ogni classe service di tipo xxxBackend esegue (se esistono) i metodi resetStartUp(), download() e reset(),<br>
     * resetStartUp() viene eseguito sempre se e solo se la collection è vuota <br>
     * download() viene eseguito sempre se e solo se la collection è vuota <br>
     * reset() viene eseguito sempre se e solo se la collection non contiene nessuna entity che abbia la property reset=true <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @param moduleName da controllare
     */
    protected void resetData(final String moduleName) {
        List<Class> allBackendClasses;
        List<String> nomi;
        List<Class> allResetOrderedClass;
        AResult result;
        String message;

        //--seleziono solo le classi di tipo 'backend'
        allBackendClasses = classService.allModuleBackendClass(moduleName);
        if (allBackendClasses != null && allBackendClasses.size() > 0) {
            message = String.format("Nel modulo %s sono stati trovati %d packages con classi di tipo 'backend'", moduleName, allBackendClasses.size());
        }
        else {
            message = String.format("Nel modulo %s non è stato trovato nessun package con classi di tipo 'backend'", moduleName);
        }
        logger.info(new WrapLog().message(message).type(AETypeLog.checkData));

        //--seleziono solo le classi di tipo 'backend' che implementano il metodo resetOnlyEmpty()
        allResetOrderedClass = classService.allModuleBackendResetOrderedClass(moduleName);
        if (allResetOrderedClass != null && allResetOrderedClass.size() > 0) {
            message = String.format("Nel modulo %s sono state trovate %d classi di tipo 'backend' che implementano il metodo %s():", moduleName, allResetOrderedClass.size(), TAG_RESET_ONLY);
            logger.info(new WrapLog().message(message).type(AETypeLog.checkData));
            nomi = allResetOrderedClass.stream().map(clazz -> clazz.getSimpleName()).collect(Collectors.toList()); ;
            message = arrayService.toStringaVirgolaSpazio(nomi);
            logger.info(new WrapLog().message(message.trim()).type(AETypeLog.checkData));
        }

        //--esegue il metodo resetOnlyEmpty() per tutte le classi di tipo 'backend' che lo implementano
        if (allResetOrderedClass != null) {
            for (Class clazz : allResetOrderedClass) {
                result = classService.esegueMetodo(clazz.getCanonicalName(), TAG_RESET_ONLY);
                if (result.isValido()) {
                    logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.checkData));
                }
            }
        }

    }


    /**
     * Controlla che il service xxxBackend abbia il metodo resetStartUp() <br>
     * Altrimenti i dati non possono essere ri-creati <br>
     */
    protected Predicate<Object> checkUsaReset = clazzName -> {
        boolean esiste = false;
        final String tag = "reset";
        Class clazz = null;
        String nomeMetodo;

        try {
            clazz = Class.forName(clazzName.toString());
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }
        if (clazz != null) {
            final Method[] methods = clazz.getDeclaredMethods();

            for (Method metodo : methods) {
                nomeMetodo = metodo.getName();
                if (nomeMetodo.equals(tag)) {
                    esiste = true;
                }
            }
        }

        return esiste;
    };

    /**
     * Controllo e ricreo (se serve) la singola collezione <br>
     * <p>
     * Costruisco un' istanza della classe xxxBackend corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxBackend è creabile <br>
     * Un package standard contiene sempre xxxBackend <br>
     */
    protected Consumer<Object> bootResetStartUp = canonicalBackend -> {
        final String canonicaBackendName = (String) canonicalBackend;
        final String tag = "resetStartUp";
        boolean eseguito;

        try {
            final Class clazz = classService.getClazzFromCanonicalName(canonicaBackendName);
            final Method metodo = clazz.getMethod(tag);
            final Object istanza = appContext.getBean(clazz);
            eseguito = (Boolean) metodo.invoke(istanza);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }
    };

}