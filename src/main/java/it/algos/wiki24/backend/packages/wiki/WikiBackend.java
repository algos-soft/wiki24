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
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.packages.giorno.*;
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
    public AttSingolareBackend attSingolareBackend;

    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Autowired
    public NazSingolareBackend nazSingolareBackend;

    @Autowired
    public NazPluraleBackend nazPluraleBackend;

    @Autowired
    public LogService logger;

    protected String message;

    protected WPref lastReset;

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

    public String sorgenteDownload;

    public String uploadTestName;

    public String tagIniSorgente;

    public String tagEndSorgente;

    public String tagSplitSorgente;

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

        this.unitaMisuraDownload = AETypeTime.nonUsata;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
        this.unitaMisuraUpload = AETypeTime.minuti;
        this.unitaMisuraStatistiche = AETypeTime.minuti;

        this.sorgenteDownload = VUOTA;
        this.tagIniSorgente = VUOTA;
        this.tagEndSorgente = VUOTA;
        this.tagSplitSorgente = VUOTA;
        this.uploadTestName = VUOTA;
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
            //            perc = perc.substring(0, perc.length() - 2) + VIRGOLA + perc.substring(perc.length() - 2) + PERCENTUALE;
            decimal = WPref.percentualeMinimaBiografie.getDecimal();
            doppio = decimal.doubleValue();
            minimo = doppio.toString() + PERCENTUALE;
            message = "Nel database mongoDB non ci sono abbastanza voci biografiche per effettuare l'elaborazione richiesta.";
            message += String.format(" Solo %s su %s (=%s). Percentuale richiesta (da pref) %s", bioMongoDB, numPagesServerWiki, perc, minimo);
            logService.warn(WrapLog.build().type(AETypeLog.elabora).message(message).usaDb());
        }

        return result;
    }

    public List findAllByPlurale(String plurale) {
        return super.findAllByProperty(FIELD_NAME_PLURALE, plurale);
    }

    public List<String> findAllForKeyByPlurale(String plurale) {
        return null;
    }

    public List<String> findAllDistinctByPlurali() {
        return null;
    }

    public LinkedHashMap<String, String> findMappa() {
        return null;
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
        if (durataDownload != null) {
            int tempo = durataDownload.getInt();
            message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_DOWLOAD, entityClazz.getSimpleName(), tempo, unitaMisuraDownload);
            logService.debug(new WrapLog().message(message));
        }

        return WResult.build().method("download").target(getClass().getSimpleName());
    }


    public boolean fixLista(AResult result, AEntity entityBean, String sigla) {
        List lista;

        if (entityBean != null) {
            if (result.getLista() == null) {
                result.setLista(new ArrayList<>());
            }
            lista = result.getLista();
            lista.add(entityBean);
            result.setLista(lista);
            return true;
        }
        else {
            logService.error(new WrapLog().exception(new AlgosException(String.format("La entityBean [%s]non è stata salvata", sigla))).usaDb());
            return false;
        }
    }

    @Deprecated
    public boolean fixLista(List<AEntity> lista, AEntity entityBean, String sigla) {
        if (entityBean != null) {
            lista.add(entityBean);
            return true;
        }
        else {
            logService.error(new WrapLog().exception(new AlgosException(String.format("La entityBean [%s]non è stata salvata", sigla))).usaDb());
            return false;
        }
    }

    public List<String> getRighe() {
        String testoCore;
        String[] righe;
        testoCore = getCore();

        if (textService.isValid(testoCore)) {
            righe = testoCore.split(tagSplitSorgente);
            return Arrays.stream(righe).map(riga -> riga.trim()).filter(riga -> textService.isValid(riga)).toList();
        }
        else {
            return new ArrayList<>();
        }
    }


    public String getCore() {
        String testoPaginaAll;
        String testoCore;

        testoPaginaAll = wikiApiService.legge(sorgenteDownload);
        if (textService.isValid(tagIniSorgente) && textService.isValid(tagEndSorgente)) {
            testoCore=wikiUtility.estraeTestoModulo(testoPaginaAll);
//            testoCore = textService.estraeLast(testoPaginaAll, tagIniSorgente, tagEndSorgente);
        }
        else {
            testoCore = wikiUtility.estraeTestoModulo(testoPaginaAll);
        }

        return testoCore;
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult uploadModulo() {
        return WResult.errato();
    }

    public WResult fixRiordinaModulo(WResult result) {
        int durata = 5;

        if (Pref.debug.is()) {
            message = String.format("Upload test del modulo ordinato%s%s.", FORWARD, result.getWikiTitle());
            if (result.isModificata()) {
                message += " Modificato";
                logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
            }
            else {
                message += " Non modificato";
                logService.info(new WrapLog().message(message).type(AETypeLog.upload));
            }

            message = String.format("Tempo effettivo in millisecondi: %d", result.durataLong());
            logService.debug(new WrapLog().message(message));
        }

        if (result.isValido()) {
            if (result.isModificata()) {
                message = String.format("Test %s modificato. Occorre copiare il testo.", result.getWikiTitle());
                Avviso.message(message).error().durata(durata).open();
            }
            else {
                message = String.format("Modulo %s non modificato.", result.getWikiTitle());
                Avviso.message(message).success().durata(durata).open();
            }
        }
        else {
            Avviso.message("Avviso di errore").error().open();
        }

        return result;
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        if (durataElaborazione != null) {
            int tempo = durataElaborazione.getInt();
            message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_ELABORA, entityClazz.getSimpleName(), tempo, unitaMisuraElaborazione);
            logService.debug(new WrapLog().message(message));
        }

        return WResult.build().method("elabora").target(getClass().getSimpleName());
    }


    public AResult fixReset(AResult result, List lista) {
        if (lista.size() > 0) {
            result.setIntValue(lista.size());
            result.setLista(lista);
        }

        if (lastReset != null) {
            lastReset.setValue(LocalDateTime.now());
        }
        else {
            message = String.format("lastReset è nullo nel target %s", result.getTarget());
            logService.warn(new WrapLog().exception(new AlgosException(message)));
        }

        if (lastElaborazione != null) {
            lastElaborazione.setValue(ROOT_DATA_TIME);
        }

        result = result.valido(true).fine().eseguito().typeResult(AETypeResult.collectionPiena);
        return result;
    }


    public AResult fixResetDownload(AResult result) {
        if (lastDownload != null) {
            lastDownload.setValue(LocalDateTime.now());
        }
        else {
            message = String.format("lastDownload è nullo nel target %s", result.getTarget());
            logService.warn(new WrapLog().exception(new AlgosException(message)));
        }

        if (durataDownload != null) {
            durataDownload.setValue(unitaMisuraDownload.durata(result.getInizio()));
        }
        else {
            if (unitaMisuraDownload != AETypeTime.nonUsata) {
                logService.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
            }
        }

        if (lastElaborazione != null) {
            lastElaborazione.setValue(ROOT_DATA_TIME);
        }

        result = result.valido(true).fine().eseguito().typeResult(AETypeResult.collectionPiena);
        return result;
    }

    public WResult fixDownload(WResult result) {
        String modulo = entityClazz.getSimpleName();
        result.valido(true).fine().typeLog(AETypeLog.download).eseguito().typeResult(AETypeResult.downloadValido);

        if (lastDownload != null) {
            lastDownload.setValue(LocalDateTime.now());
        }
        else {
            message = String.format("lastDownload è nullo nel target %s", result.getTarget());
            logService.warn(new WrapLog().exception(new AlgosException(message)));
        }

        if (durataDownload != null) {
            durataDownload.setValue(unitaMisuraDownload.durata(result.getInizio()));
        }
        else {
            if (unitaMisuraDownload != AETypeTime.nonUsata) {
                logService.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
            }
        }

        message = String.format("Download di %s. Pagine scaricate %s. %s", modulo, textService.format(count()), unitaMisuraDownload.message(result.getInizio()));
        logService.info(new WrapLog().message(message).type(AETypeLog.download).usaDb());
        result.setValidMessage(message);

        message = String.format("Tempo effettivo in millisecondi: %d", result.durataLong());
        logService.debug(new WrapLog().message(message));

        if (lastElaborazione != null) {
            lastElaborazione.setValue(ROOT_DATA_TIME);
        }

        return result;
    }


    public WResult fixDownload(WResult result, String clazzName, List lista) {
        result.valido(true).fine().typeLog(AETypeLog.download).eseguito().typeResult(AETypeResult.downloadValido);
        String message;

        if (lista.size() > 0) {
            result.setIntValue(lista.size());
            result.setLista(lista);

            message = String.format("Download %s. Pagine scaricate %s.", clazzName.getClass().getSimpleName(), textService.format(lista.size()));
            message += result.deltaSec();
            logService.info(new WrapLog().message(message).type(AETypeLog.elabora).usaDb());
            return result.validMessage(message);
        }
        else {
            result.typeResult(AETypeResult.error);
            message = String.format("Non sono riuscito a effettuare il download di %s.", clazzName.getClass().getSimpleName());
            System.out.println(message);
            return result.errorMessage(message);
        }
    }

    public void fixDownloadModulo(String modulo) {
        logService.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, modulo)));
    }

    public WResult fixElabora(WResult result) {
        long inizio = result.getInizio();
        String modulo = entityClazz.getSimpleName();
        result.typeLog(AETypeLog.elabora).typeResult(AETypeResult.elaborazioneValida).eseguito().fine().setIntValue(count());

        if (lastElaborazione != null) {
            lastElaborazione.setValue(LocalDateTime.now());
        }
        else {
            logService.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
        }

        if (durataElaborazione != null) {
            durataElaborazione.setValue(unitaMisuraElaborazione.durata(inizio));
        }
        else {
            logService.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
        }

        message = String.format("Elaborazione di %s. Pagine esaminate %s. %s", modulo, textService.format(count()), unitaMisuraElaborazione.message(inizio));
        result.setValidMessage(message);

        message = String.format("Tempo effettivo in millisecondi: %d", result.durataLong());
        logService.debug(new WrapLog().message(message));

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
            logService.warn(new WrapLog().exception(new AlgosException("lastDownload è nullo")));
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
            logService.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
            return;
        }

        if (textService.isValid(wikiTitle) && sizeServerWiki > 0 && sizeMongoDB > 0) {
            if (sizeServerWiki == sizeMongoDB) {
                message = String.format("Download di %s righe da [%s] in %d millisecondi", wikiTxt, wikiTitle, delta);
            }
            else {
                message = String.format("Download di %s righe da [%s] convertite in %s elementi su mongoDB", wikiTxt, wikiTitle, mongoTxt);
            }

            logService.info(new WrapLog().message(message));
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
            logService.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
            return;
        }

        if (durataElaborazione != null) {
            delta = delta / 1000;
            durataElaborazione.setValue(delta.intValue());
        }
        else {
            logService.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
            return;
        }

        message = String.format("Elaborate %s %s in circa %d secondi", mongoTxt, modulo, delta);
        logService.info(new WrapLog().message(message));
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
            logService.warn(new WrapLog().exception(new AlgosException("lastElabora è nullo")));
            return result;
        }

        if (durataElaborazione != null) {
            delta = delta / 1000 / 60;
            durataElaborazione.setValue(delta.intValue());
        }
        else {
            logService.warn(new WrapLog().exception(new AlgosException("durataElaborazione è nullo")));
            return result;
        }

        message = String.format("Elaborate %s %s in %d millisecondi", mongoTxt, modulo, delta);
        logService.info(new WrapLog().message(message));

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
            logService.warn(new WrapLog().exception(new AlgosException("lastUpload è nullo")));
            return;
        }

        if (durataUpload != null) {
            delta = delta / 1000 / 60;
            durataUpload.setValue(delta.intValue());
        }
        else {
            logService.warn(new WrapLog().exception(new AlgosException("durataUpload è nullo")));
            return;
        }

        message = String.format("Check di %s %s, in %s minuti. Sotto soglia: %s. Da cancellare: %s. Non modificate: %s. Modificate: %s.", tot, modulo, delta, sottoSoglia, daCancellare, nonModificate, modificate);
        logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult uploadStatistiche() {
        return WResult.errato();
    }

}
