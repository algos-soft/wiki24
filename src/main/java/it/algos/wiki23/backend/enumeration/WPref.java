package it.algos.wiki23.backend.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25 apr 22
 * <p>
 * Creazione da code di alcune preferenze del progetto <br>
 */
public enum WPref implements AIGenPref {

    downloadGenere("downloadGenere", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale attività genere."),

    downloadAttivita("downloadAttivita", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale attività."),
    downloadAttivitaTime("downloadAttivitaTime", AETypePref.integer, 0, "Durata download delle attività in minuti."),
    elaboraAttivita("elaboraAttivita", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le attività."),
    elaboraAttivitaTime("elaboraAttivitaTime", AETypePref.integer, 0, "Durata elaborazione delle attività in minuti."),
    uploadAttivita("uploadAttivita", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di attività"),
    uploadAttivitaTime("uploadAttivitaTime", AETypePref.integer, 0, "Durata upload delle attività in minuti."),
    uploadAttivitaPrevisto("uploadAttivitaPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per le attività."),


    downloadNazionalita("downloadNazionalita", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale nazionalità."),
    downloadNazionalitaTime("downloadNazionalitaTime", AETypePref.integer, 0, "Durata download delle nazionalità in minuti."),
    elaboraNazionalita("elaboraNazionalita", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le nazionalità."),
    elaboraNazionalitaTime("elaboraNazionalitaTime", AETypePref.integer, 0, "Durata elaborazione delle nazionalità in minuti."),
    uploadNazionalita("uploadNazionalita", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nazionalità"),
    uploadNazionalitaTime("uploadNazionalitaTime", AETypePref.integer, 0, "Durata upload delle nazionalità in minuti."),
    uploadNazionalitaPrevisto("uploadNazionalitaPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per le nazionalità."),


    uploadCognomi("uploadCognomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di cognomi oltre la soglia di 50 biografie"),
    uploadCognomiTime("uploadCognomiTime", AETypePref.integer, 0, "Durata upload dei cognomi in minuti."),
    uploadCognomiPrevisto("uploadCognomiPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i cognomi."),

    uploadNomi("uploadNomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nomi oltre la soglia di 50 biografie"),
    uploadNomiTime("uploadNomiTime", AETypePref.integer, 0, "Durata upload dei nomi in minuti."),
    uploadNomiPrevisto("uploadNomiPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i nomi."),

    usaTaskResetBio("usaTaskResetBio", AETypePref.bool, false, "Reset calendarizzato di tutte le biografie"),
    resetBio("resetBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset completo delle voci biografiche"),
    resetBioTime("resetBioTime", AETypePref.integer, ROOT_DATA_TIME, "Durata Reset completo delle biografie in minuti."),
    resetBioPrevisto("resetBioPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo reset previsto delle voci biografiche."),

    downloadBio("downloadBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di tutte le biografie nuove e modificate"),
    downloadBioTime("downloadBioTime", AETypePref.integer, ROOT_DATA_TIME, "Durata ciclo completo download delle biografie in minuti."),
    downloadBioPrevisto("downloadBioPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo download previsto delle voci biografiche."),
    elaboraBio("elaboraBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le biografie."),
    elaboraBioTime("elaboraBioTime", AETypePref.integer, ROOT_DATA_TIME, "Durata elaborazione delle biografie in minuti."),

    //    uploadAttivitaTime("uploadAttivitaTime", AETypePref.integer, "Durata upload delle attività.", 0),
    //    downloadNazionalita("downloadNazionalita", AETypePref.localdatetime, "Download di Modulo:Bio/Plurale nazionalità.", ROOT_DATA_TIME),
    //    downloadProfessione("downloadProfessione", AETypePref.localdatetime, "Download di Modulo:Bio/Link attività.", ROOT_DATA_TIME),
    //    downloadNomi("downloadNomi", AETypePref.localdatetime, "Download di Progetto:Antroponimi/Nomi doppi.", ROOT_DATA_TIME),

    elaboraAnni("elaboraAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutti gli anni."),
    elaboraAnniTime("elaboraAnniTime", AETypePref.integer, 0, "Durata elaborazione di tutti gli anni in minuti."),
    elaboraGiorni("elaboraGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutti i giorni."),
    elaboraGiorniTime("elaboraGiorniTime", AETypePref.integer, 0, "Durata elaborazione di tutti i giorni in minuti."),

    elaboraCognomi("elaboraCognomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione dei cognomi."),
    elaboraCognomiTime("elaboraCognomiTime", AETypePref.integer, 0, "Durata elaborazione dei cognomi in minuti."),

    statisticaAttivita("statisticaAttivita", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per le attività."),
    statisticaNazionalita("statisticaNazionalita", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per le nazionalità."),


    uploadGiorni("uploadGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nati/morti per giorno"),
    uploadGiorniTime("uploadGiorniTime", AETypePref.integer, 0, "Durata upload dei giorni in minuti."),
    uploadGiorniPrevisto("uploadGiorniPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i giorni."),
    statisticaGiorni("statisticaGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per i giorni."),
    statisticaGiorniTime("statisticaGiorniTime", AETypePref.integer, 0, "Durata elaborazione e upload delle statistiche dei giorni in minuti."),


    uploadAnni("uploadAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nati/morti per anno"),
    uploadAnniTime("uploadAnniTime", AETypePref.integer, 0, "Durata upload degli anni in minuti."),
    uploadAnniPrevisto("uploadAnniPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per gli anni."),
    statisticaAnni("statisticaAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per gli anni."),
    statisticaAnniTime("statisticaAnniTime", AETypePref.integer, 0, "Durata elaborazione e upload delle statistiche degli anni in minuti."),


    usaSottoGiorniAnni("usaSottoGiorniAnni", AETypePref.bool, false, "Usa le sotto-sottopagine (secoli/mesi) per giorni/anni"),
    sogliaSottoPaginaGiorniAnni("sogliaSottoPaginaGiorniAnni", AETypePref.integer, 50, "Soglia minima per creare una sottopagina di un giorno o anno sul server wiki"),

    categoriaBio("categoriaBio", AETypePref.string, "BioBot", "Categoria di riferimento per le Biografie"),
    sogliaAttNazWiki("sogliaAttNazWiki", AETypePref.integer, 50, "Soglia minima per creare la pagina di una attività o nazionalità sul server wiki"),
    sogliaSottoPagina("sogliaSottoPagina", AETypePref.integer, 50, "Soglia minima per creare una sottopagina di una attività o nazionalità sul server wiki"),
    sogliaDiv("sogliaDiv", AETypePref.integer, 1, "Soglia minima per usare {{Div col}}"),
    sogliaIncludeAll("sogliaIncludeAll", AETypePref.integer, 200, "Soglia minima per 'includere' la voce in giorni/anni"),
    sogliaIncludeParagrafo("sogliaIncludeParagrafo", AETypePref.integer, 50, "Soglia minima per usare i paragrafi 'inclusi' di secondo livello"),

    usaTreAttivita("usaTreAttivita", AETypePref.bool, false, "Considera tutte le attività (tre) nelle liste di attività"),
    usaTreAttivitaStatistiche("usaTreAttivitaStatistiche", AETypePref.bool, true, "Considera tutte le attività (tre) nelle statistiche di attività"),
    usaParagrafiDimensionati("usaParagrafiDimensionati", AETypePref.bool, true, "Nel titolo del paragrafo aggiunge la dimensione delle " +
            "voci elencate"),
    usaLinkStatistiche("usaLinkStatistiche", AETypePref.bool, true, "Link alle liste di attività nel template statistiche, anche se " +
            "rossi"),
    typeTocAttNaz("typeTocAttNaz", AETypePref.enumerationType, AETypeToc.noToc, "Type di TOC in attività e nazionalità",
            AETypeToc.noToc
    ),
    typeTocGiorni("typeTocGiorni", AETypePref.enumerationType, AETypeToc.forceToc, "Type di TOC nelle liste di giorni",
            AETypeToc.noToc
    ),
    typeTocAnni("typeTocAnni", AETypePref.enumerationType, AETypeToc.forceToc, "Type di TOC nelle liste di anni",
            AETypeToc.noToc
    ),
    typeChiaveNulla("typeChiaveNulla", AETypePref.enumerationType, AETypeChiaveNulla.inCoda, "Posizione del paragrafo 'nullo'",
            AETypeChiaveNulla.inCoda
    ),

    usaSottoCognomi("usaSottoCognomi", AETypePref.bool, false, "Usa le sotto-sottopagine per i cognomi"),

    usaParagrafiGiorni("usaParagrafiGiorni", AETypePref.bool, true, "Usa i paragrafi (secoli) nelle pagine dei giorni"),
    usaParagrafiAnni("usaParagrafiAnni", AETypePref.bool, true, "Usa i paragrafi (mesi) nelle pagine degli anni"),
    usaParagrafiAttNaz("usaParagrafiAttNaz", AETypePref.bool, true, "Usa i paragrafi (nazionalità/attività) nelle pagine attività/nazionalità"),
    usaSottoSottoAttNaz("usaSottoSottoAttNaz", AETypePref.bool, false, "Usa le sotto-sottopagine (nazionalità/attività) di lettere alfabetiche"),
    usaDivAttNaz("usaDivAttNaz", AETypePref.bool, false, "Usa i {{Div col}} nelle pagine attività e nazionalità"),

    simboloNato("simboloNato", AETypePref.string, "n.", "Simbolo della nascita nelle didascalie"),
    simboloMorto("simboloMorto", AETypePref.string, "†", "Simbolo della morte nelle didascalie"),
    usaSimboliCrono("usaSimboliCrono", AETypePref.bool, true, "Uso dei simboli crono per nati e morti"),

    linkCrono("linkCrono", AETypePref.enumerationType, AETypeLink.voce, "Type di link a giorni/anni nelle didascalie",
            AETypeLink.nessuno
    ),
    linkAttNaz("linkAttNaz", AETypePref.enumerationType, AETypeLink.voce, "Type di link nei titoli dei paragrafi in attività/nazionalità",
            AETypeLink.nessuno
    ),
    linkGiorniAnni("linkGiorniAnni", AETypePref.enumerationType, AETypeLink.nessuno, "Type di link nei titoli dei paragrafi in giorni/anni",
            AETypeLink.nessuno
    ),
    linkCognomi("linkCognomi", AETypePref.enumerationType, AETypeLink.pagina, "Type di link nei titoli dei paragrafi in persone di cognome",
            AETypeLink.nessuno
    ),

    usaTaskBio("usaTaskBio", AETypePref.bool, false, "Download calendarizzato di tutte le biografie"),
    usaTaskGiorni("usaTaskGiorni", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine dei giorni nato/morto"),
    usaTaskAnni("usaTaskAnni", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine degli anni nato/morto"),
    usaTaskAttivita("usaTaskAttivita", AETypePref.bool, false, "Upload calendarizzato di tutte le attività"),
    usaTaskNazionalita("usaTaskNazionalita", AETypePref.bool, false, "Upload calendarizzato di tutte le nazionalità"),
    usaTaskCognomi("usaTaskCognomi", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine dei cognomi"),
    usaTaskNomi("usaTaskNomi", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine dei nomi"),
    usaTaskElabora("usaTaskElabora", AETypePref.bool, false, "Lista pagine da cancellare e lista errori"),
    usaTaskStatistiche("usaTaskStatistiche", AETypePref.bool, false, "Elaborazione di alcune statistiche"),


    usaRigheGiorni("usaRigheGiorni", AETypePref.bool, true, "Usa righe raggruppate per anno nelle liste dei giorni"),
    usaRigheAnni("usaRigheAnni", AETypePref.bool, true, "Usa righe raggruppate per giorno nelle liste degli anni"),
    sogliaCognomiMongo("sogliaCognomiMongo", AETypePref.integer, 30, "Soglia minima per creare una entity nella collezione Cognomi sul mongoDB"),
    sogliaCognomiWiki("sogliaCognomiWiki", AETypePref.integer, 50, "Soglia minima per creare una pagina Cognomi sul server wiki"),
    usaLoggerTask("usaLoggerTask", AETypePref.bool, true, "Registra sul log interno l'esecuzione dell task programmate"),

    elaboraPagineCancella("elaboraPagineCancella", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione delle pagine da cancellare"),
    elaboraPagineCancellaTime("elaboraPagineCancellaTime", AETypePref.integer, 0, "Durata elaborazione delle pagine da cancellare in minuti."),
    elaboraPagineCancellaPrevisto("elaboraPagineCancellaPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossima elaborazione delle pagine da cancellare."),

    statistichePrevisto("statistichePrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossima elaborazione delle statistiche"),

    maxPageLength("maxPageLength", AETypePref.integer, 200000, "Soglia massima di una pagina in byte"),
    maxBioPageAnniGiorni("maxBioPageAnniGiorni", AETypePref.integer, 1700, "Soglia massima di bio per usare le sottopagine  giorni/anni"),
    scriveComunque("scriveComunque", AETypePref.bool, false, "Forza comunque la registrazione della pagina anche se le modifiche sono sulla data"),
    sottoCategorieNatiPerAnno("sottoCategorieNatiPerAnno", AETypePref.bool, false, "Categorizzazione per secoli delle liste di 'Nati per anno'"),
    ;


    //--codice di riferimento.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    private AITypePref typeEnum;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    //--Link injettato da un metodo static
    private PreferenceService preferenceService;

    //--Link injected da un metodo static
    private LogService logger;

    //--Link injected da un metodo static
    private DateService date;

    //--Link injettato da un metodo static
    private TextService text;

    WPref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione) {
        this(keyCode, type, defaultValue, descrizione, null);
    }// fine del costruttore

    WPref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione, AITypePref typeEnum) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
        this.typeEnum = typeEnum;
    }// fine del costruttore


    public static List<WPref> getAllEnums() {
        return Arrays.stream(values()).toList();
    }
    //------------------------------------------------
    //--copiare tutti i metodi (Instance Method e non Static Method) nelle sottoclassi xPref
    //--cambiando in static PreferenzaServiceInjector.postConstruct() Pref.values() -> xPref.values()
    //------------------------------------------------

    @Override
    public void setPreferenceService(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @Override
    public void setLogger(LogService logger) {
        this.logger = logger;
    }

    @Override
    public void setDate(DateService date) {
        this.date = date;
    }

    @Override
    public void setText(TextService text) {
        this.text = text;
    }

    @Override
    public void setValue(Object javaValue) {
        preferenceService.setValue(type, keyCode, javaValue);
    }


    @Override
    public Object get() {
        return getValue();
    }

    @Override
    public Object getValue() {
        return preferenceService.getValue(type, keyCode);
    }

    @Override
    public String getStr() {
        return preferenceService.getStr(type, keyCode);
    }

    @Override
    public boolean is() {
        return preferenceService.is(type, keyCode);
    }

    @Override
    public int getInt() {
        return preferenceService.getInt(type, keyCode);
    }

    @Override
    public AETypePref getType() {
        return type;
    }

    @Override
    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public AITypePref getTypeEnum() {
        return typeEnum;
    }

    /**
     * Tutti i valori della enum <br>
     */
    @Override
    public String getEnumAll() {
        return preferenceService.getEnumAll(type, keyCode);
    }

    @Override
    public AITypePref getEnumCurrentObj() {
        return preferenceService.getEnumCurrentObj(typeEnum, type, keyCode);
    }

    /**
     * Valore selezionato della enum <br>
     */
    @Override
    public String getEnumCurrent() {
        return preferenceService.getEnumCurrentTxt(type, keyCode);
    }

    /**
     * Valore selezionato della enum <br>
     */
    @Override
    public void setEnumCurrent(String currentValue) {
        preferenceService.setEnumCurrentTxt(type, keyCode, currentValue);
    }

    @Override
    public void setEnumCurrentObj(AITypePref currentValue) {
        preferenceService.setEnumCurrentObj(type, keyCode, currentValue);
    }

    @Component
    public static class PreferenzaServiceInjector {

        @Autowired
        private PreferenceService preferenceService;

        @Autowired
        private LogService logger;

        @Autowired
        private DateService date;

        @Autowired
        private TextService text;

        @PostConstruct
        public void postConstruct() {
            for (WPref pref : WPref.values()) {
                pref.setPreferenceService(preferenceService);
                pref.setLogger(logger);
                pref.setDate(date);
                pref.setText(text);
            }
        }

    }
}
