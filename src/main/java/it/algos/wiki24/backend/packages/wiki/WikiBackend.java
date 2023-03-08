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
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attivita.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;

import java.time.*;

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

    protected String message;

    public WPref lastDownload;

    protected WPref durataDownload;

    public WPref lastElabora;

    public WPref durataElaborazione;

    protected WPref lastUpload;

    protected WPref durataUpload;

    protected AETypeTime unitaMisuraDownload;

    protected AETypeTime unitaMisuraElaborazione;

    protected AETypeTime unitaMisuraUpload;

    protected AETypeTime unitaMisuraStatistiche;

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

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param wikiTitle della pagina su wikipedia
     */
    public void download(final String wikiTitle) {
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void elabora() {
    }

    public void fixDownload(final long inizio) {
        fixDownload(inizio, VUOTA);
    }

    public void fixDownload(final long inizio, String modulo) {
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

        message = String.format("Download di %s. %s", modulo, unitaMisuraDownload.message(inizio));
        logger.info(new WrapLog().message(message).type(AETypeLog.download).usaDb());
    }

    public void fixElabora(final long inizio) {
        fixElabora(inizio, VUOTA);
    }

    public void fixElabora(final long inizio, String modulo) {
        if (lastElabora != null) {
            lastElabora.setValue(LocalDateTime.now());
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

        if (lastElabora != null) {
            lastElabora.setValue(LocalDateTime.now());
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
    public void fixElaboraMinuti(final long inizio, final String modulo) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;
        String mongoTxt = textService.format(count());

        if (lastElabora != null) {
            lastElabora.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
            return;
        }

        if (durataElaborazione != null) {
            delta = delta / 1000 / 60;
            durataElaborazione.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
            return;
        }

        message = String.format("Elaborate %s %s in %d millisecondi", mongoTxt, modulo, delta);
        logger.info(new WrapLog().message(message));
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
