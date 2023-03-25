package it.algos.wiki24.backend.packages.wiki;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attivita.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;

import java.math.*;
import java.time.*;
import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 08:38
 */
public abstract class WikiBackend extends CrudBackend {

    @Autowired
    public GiornoBackend giornoBackend;

    @Autowired
    public AnnoBackend annoBackend;

    @Autowired
    public MeseBackend meseBackend;

    @Autowired
    public SecoloBackend secoloBackend;

    @Autowired
    public NazSingolareBackend nazSingolareBackend;

    @Autowired
    public NazPluraleBackend nazPluraleBackend;

    protected String message;

    public WPref lastDownload;

    public WPref durataDownload;

    public WPref lastElaborazione;

    public WPref durataElaborazione;

    public WPref lastUpload;

    public WPref durataUpload;

    public WPref nextUpload;

    public WPref lastStatistica;

    public WPref durataStatistica;

    public AETypeTime unitaMisuraDownload;

    public AETypeTime unitaMisuraElaborazione;

    public AETypeTime unitaMisuraUpload;

    public AETypeTime unitaMisuraStatistiche;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiApiService wikiApiService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GenereBackend genereBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BioBackend bioBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AttivitaBackend attivitaBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NazionalitaBackend nazionalitaBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ElaboraService elaboraService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiUtility wikiUtility;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public QueryService queryService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BioService bioService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public CognomeBackend cognomeBackend;

    @Autowired
    private AETypeTime.TypeInjector nonUsatoDirettamenteServeSoloPerInjectionDiSpring;

    public WikiBackend(final Class<? extends AEntity> entityClazz) {
        super(entityClazz);
    }// end of constructor with @Autowired

    public WikiBackend(final MongoRepository crudRepository, final Class<? extends AEntity> entityClazz) {
        super(crudRepository, entityClazz);
    }// end of constructor with @Autowired

    /**
     * Preferenze usate da questa 'backend' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.unitaMisuraDownload = AETypeTime.minuti;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
        this.unitaMisuraUpload = AETypeTime.minuti;
        this.unitaMisuraStatistiche = AETypeTime.minuti;
    }


    //--Check di validità del database mongoDB
    public WResult checkValiditaDatabase() {
        WResult result;
        String message;
        Map mappa;
        String bioMongoDB;
        String numPagesServerWiki;
        BigDecimal decimal;
        Double doppio;
        String minimo;
        Integer percentuale;
        String perc;

        result = wikiUtility.checkValiditaDatabase();
        if (result.isErrato()) {
            mappa = result.getMappa();
            bioMongoDB = textService.format(mappa.get(KEY_MAP_VOCI_DATABASE_MONGO));
            numPagesServerWiki = textService.format(mappa.get(KEY_MAP_VOCI_SERVER_WIKI));
            percentuale = (Integer) mappa.get(KEY_MAP_VOCI_DATABASE_MONGO) * 100 * 100 / (Integer) mappa.get(KEY_MAP_VOCI_SERVER_WIKI);
            perc = percentuale.toString();
            perc = perc.substring(0, perc.length() - 2) + VIRGOLA + perc.substring(perc.length() - 2) + PERCENTUALE;
            decimal = WPref.percentualeMinimaBiografie.getDecimal();
            doppio = decimal.doubleValue();
            minimo = doppio.toString() + PERCENTUALE;
            message = "Nel database mongoDB non ci sono abbastanza voci biografiche per effettuare l'elaborazione richiesta.";
            message += String.format(" Solo %s su %s (=%s). Percentuale richiesta (da pref) %s", bioMongoDB, numPagesServerWiki, perc, minimo);
            logger.warn(WrapLog.build().type(AETypeLog.elabora).message(message).usaDb());
        }

        return result;
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param wikiTitle della pagina su wikipedia
     */
    public void download(final String wikiTitle) {
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    public WResult download() {
        return WResult.build().method("download").target(getClass().getSimpleName());
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        return WResult.build().method("elabora").target(getClass().getSimpleName());
    }


    public WResult fixDownload(WResult result, final long inizio) {
        return fixDownload(result, inizio, VUOTA);
    }

    public WResult fixDownload(WResult result, final long inizio, String modulo) {
        result.valido(true).fine().typeLog(AETypeLog.download).eseguito().typeResult(AETypeResult.downloadValido);
        if (lastDownload != null) {
            lastDownload.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastDownload è nullo")));
        }

        if (durataDownload != null) {
            durataDownload.setValue(unitaMisuraDownload.durata(inizio));
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
        }

        message = String.format("Download di %s. Pagine caricate %s. %s", modulo, textService.format(count()), unitaMisuraDownload.message(inizio));
        logger.info(new WrapLog().message(message).type(AETypeLog.download).usaDb());
        result.setValidMessage(message);

        message = String.format("Tempo effettivo in millisecondi: %d", result.durataLong());
        logger.debug(new WrapLog().message(message));

        return result;
    }

    public WResult fixElabora(WResult result, final long inizio) {
        return fixElabora(result, inizio, VUOTA);
    }

    public WResult fixElabora(WResult result, final long inizio, String modulo) {
        result.fine();
        if (lastElaborazione != null) {
            lastElaborazione.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
        }

        if (durataElaborazione != null) {
            durataElaborazione.setValue(unitaMisuraElaborazione.durata(inizio));
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
        }

        message = String.format("Elaborazione %s. Pagine esaminate %s. %s", modulo, textService.format(count()), unitaMisuraElaborazione.message(inizio));
        logger.info(new WrapLog().message(message).type(AETypeLog.elabora).usaDb());

        message = String.format("Tempo effettivo in millisecondi: %d", result.durataLong());
        logger.debug(new WrapLog().message(message));

        return result;
    }


    @Deprecated
    public void fixDownloadSecondi(final long inizio, final String wikiTitle, final int sizeServerWiki, final int sizeMongoDB) {
        fixDownload(inizio, wikiTitle, sizeServerWiki, sizeMongoDB, false);
    }

    @Deprecated
    public void fixDownloadMinuti(final long inizio, final String wikiTitle, final int sizeServerWiki, final int sizeMongoDB) {
        fixDownload(inizio, wikiTitle, sizeServerWiki, sizeMongoDB, true);
    }

    @Deprecated
    public void fixDownload(final long inizio, final String wikiTitle, final int sizeServerWiki, final int sizeMongoDB, boolean usaMinuti) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;
        String wikiTxt = textService.format(sizeServerWiki);
        String mongoTxt = textService.format(sizeMongoDB);

        if (lastDownload != null) {
            lastDownload.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastDownload è nullo")));
            return;
        }

        if (durataDownload != null) {
            delta = delta / 1000;
            if (usaMinuti) {
                delta = delta / 60;
            }

            durataDownload.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
            return;
        }

        if (textService.isValid(wikiTitle) && sizeServerWiki > 0 && sizeMongoDB > 0) {
            if (sizeServerWiki == sizeMongoDB) {
                message = String.format("Download di %s righe da [%s] in %d millisecondi", wikiTxt, wikiTitle, delta);
            }
            else {
                message = String.format("Download di %s righe da [%s] convertite in %s elementi su mongoDB", wikiTxt, wikiTitle, mongoTxt);
            }

            logger.info(new WrapLog().message(message));
        }
    }


    @Deprecated
    public void fixElaboraSecondi(final long inizio, final String modulo) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;
        String mongoTxt = textService.format(count());

        if (lastElaborazione != null) {
            lastElaborazione.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
            return;
        }

        if (durataElaborazione != null) {
            delta = delta / 1000;
            durataElaborazione.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
            return;
        }

        message = String.format("Elaborate %s %s in circa %d secondi", mongoTxt, modulo, delta);
        logger.info(new WrapLog().message(message));
    }

    @Deprecated
    public WResult fixElaboraMinuti(WResult result, final long inizio, final String modulo) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;
        String mongoTxt = textService.format(count());

        if (lastElaborazione != null) {
            lastElaborazione.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
            return result;
        }

        if (durataElaborazione != null) {
            delta = delta / 1000 / 60;
            durataElaborazione.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
            return result;
        }

        message = String.format("Elaborate %s %s in %d millisecondi", mongoTxt, modulo, delta);
        logger.info(new WrapLog().message(message));

        return result;
    }

    @Deprecated
    public void fixUploadMinuti(final long inizio, int sottoSoglia, int daCancellare, int nonModificate, int modificate, final String modulo) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;
        int tot = sottoSoglia + nonModificate + modificate;

        if (lastUpload != null) {
            lastUpload.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastUpload è nullo")));
            return;
        }

        if (durataUpload != null) {
            delta = delta / 1000 / 60;
            durataUpload.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataUpload è nullo")));
            return;
        }

        message = String.format("Check di %s %s, in %s minuti. Sotto soglia: %s. Da cancellare: %s. Non modificate: %s. Modificate: %s.", tot, modulo, delta, sottoSoglia, daCancellare, nonModificate, modificate);
        logger.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
    }

}
