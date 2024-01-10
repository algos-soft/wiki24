package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Jan-2024
 * Time: 09:05
 */
public abstract class Upload implements AlgosBuilderPattern {

    @Inject
    TextService textService;

    @Inject
    AnnotationService annotationService;

    @Inject
    LogService logger;

    @Inject
    WikiUtilityService wikiUtilityService;

    @Inject
    GiornoModulo giornoModulo;

    @Inject
    AnnoModulo annoModulo;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected TypeLista type;

    protected String collectionName;

    protected String message;

    public String headerText;

    public String bodyText;

    public String uploadText;

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Upload(String nomeLista) {
        this.nomeLista = nomeLista;
    }// end of constructor not @Autowired and used


    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
        this.patternCompleto = type != TypeLista.nessunaLista;
        this.checkValiditaCostruttore();
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.type = TypeLista.nessunaLista;
        //        this.usaDimensioneParagrafi = true;
        //        this.usaIncludeSottoMax = true;
        //        this.usaSottopaginaOltreMax = true;
    }


    public WResult upload() {
        String message;

        if (type == null || type == TypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "upload", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "upload");
            logger.error(new WrapLog().message(message));
            return null;
        }
        if (!costruttoreValido) {
            System.out.println(VUOTA);
            message = String.format("Upload non effettuato perché il valore [%s.%s] della collection '%s' non esiste", collectionName, nomeLista, collectionName);
            logger.error(new WrapLog().message(message));
            return null;
        }
        if (!patternCompleto) {
            System.out.println(VUOTA);
            message = String.format("AlgosBuilderPattern per l'istanza [%s.%s] non completo. Non funziona il metodo '%s'", collectionName, nomeLista, "upload");
            logger.error(new WrapLog().message(message));
            return null;
        }

        if (textService.isValid(uploadText)) {
            //            this.esegue();
        }
        if (textService.isEmpty(uploadText)) {
            return WResult.errato();
        }

        //        return registra(uploadText);
        return null;
    }


    public Upload esegue() {
        StringBuffer buffer = new StringBuffer();
        String message;
        if (textService.isValid(uploadText)) {
            return this;
        }

        if (type == null || type == TypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "esegue", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "esegue");
            logger.warn(new WrapLog().message(message));
            return null;
        }

        //        this.fixMappaWrap();
        //
        //        if (mappaWrap != null && mappaWrap.size() > 0) {
        //            buffer.append(creaHader());
        //            buffer.append(creaBodyLayer());
        //            buffer.append(creaBottom(buffer.toString().trim()));
        //
        //            this.uploadText = buffer.toString().trim();
        //        }

        return this;
    }

    protected void checkValiditaCostruttore() {
        if (moduloCorrente != null) {
            this.costruttoreValido = moduloCorrente.existByKey(textService.primaMaiuscola(nomeLista)) || moduloCorrente.existByKey(textService.primaMinuscola(nomeLista));
        }
        else {
            message = String.format("Manca il backend in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        if (this.type == TypeLista.nessunaLista) {
            message = String.format("Manca il type della lista in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        this.collectionName = costruttoreValido ? annotationService.getCollectionName(BioMongoEntity.class) : VUOTA;
    }

    protected boolean checkValiditaPattern() {
        if (costruttoreValido && patternCompleto) {
            return true;
        }
        else {
            if (!costruttoreValido) {
                message = String.format("Non è valido il costruttore di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
            }
            if (!patternCompleto) {
                message = String.format("Pattern non completo di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
            }

            return false;
        }
    }

    @Override
    public boolean isCostruttoreValido() {
        return this.costruttoreValido;
    }

    @Override
    public boolean isPatternCompleto() {
        return this.patternCompleto;
    }

    @Override
    public String getNome() {
        return this.nomeLista;
    }

}
