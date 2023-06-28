package it.algos.wiki24.backend.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.math.*;
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


    //giorni
    resetGiorni("resetGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'giorni'"),
    elaboraGiorni("elaboraGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutti i giorni."),
    elaboraGiorniTime("elaboraGiorniTime", AETypePref.integer, 0, "Durata elaborazione di tutti i giorni."),
    uploadGiorni("uploadGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nati/morti per giorno"),
    uploadGiorniTime("uploadGiorniTime", AETypePref.integer, 0, "Durata upload dei giorni."),
    uploadGiorniPrevisto("uploadGiorniPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i giorni."),
    statisticaGiorni("statisticaGiorni", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per i giorni."),
    statisticaGiorniTime("statisticaGiorniTime", AETypePref.integer, 0, "Durata elaborazione e upload delle statistiche dei giorni."),


    //anni
    resetAnni("resetAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'anni'"),
    elaboraAnni("elaboraAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutti gli anni."),
    elaboraAnniTime("elaboraAnniTime", AETypePref.integer, 0, "Durata elaborazione di tutti gli anni."),
    uploadAnni("uploadAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nati/morti per anno"),
    uploadAnniTime("uploadAnniTime", AETypePref.integer, 0, "Durata upload degli anni."),
    uploadAnniPrevisto("uploadAnniPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per gli anni."),
    statisticaAnni("statisticaAnni", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per gli anni."),
    statisticaAnniTime("statisticaAnniTime", AETypePref.integer, 0, "Durata elaborazione e upload delle statistiche degli anni."),


    //attività
    resetAttSingolare("resetAttSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'attivitàSingolari'"),
    downloadAttSingolare("downloadAttSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale attività e Modulo:Bio/Ex attività."),
    downloadAttSingolareTime("downloadAttSingolareTime", AETypePref.integer, 0, "Durata download delle attività singolari."),
    elaboraAttSingolare("elaboraAttSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le attività singolari."),
    elaboraAttSingolareTime("elaboraAttSingolareTime", AETypePref.integer, 0, "Durata elaborazione delle attività singolari."),


    resetAttPlurale("resetAttPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'attivitàPlurali'"),
    downloadAttPlurale("downloadAttPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Link attività."),
    downloadAttPluraleTime("downloadAttPluraleTime", AETypePref.integer, 0, "Durata download delle attività plurali."),
    elaboraAttPlurale("elaboraAttPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le attività plurali."),
    elaboraAttPluraleTime("elaboraAttPluraleTime", AETypePref.integer, 0, "Durata elaborazione delle attività plurali."),


    uploadAttPlurale("uploadAttPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di attività plurali"),
    uploadAttPluraleTime("uploadAttPluraleTime", AETypePref.integer, 0, "Durata upload delle attività plurali."),
    uploadAttPluralePrevisto("uploadAttPluralePrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per le attività plurali."),


    resetNazSingolare("resetNazSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'nazionalitàSingolari'"),
    downloadNazSingolare("downloadNazSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale nazionalità."),
    downloadNazSingolareTime("downloadNazSingolareTime", AETypePref.integer, 0, "Durata download delle nazionalità singolari."),
    elaboraNazSingolare("elaboraNazSingolare", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le nazionalità singolari."),
    elaboraNazSingolareTime("elaboraNazSingolareTime", AETypePref.integer, 0, "Durata elaborazione delle nazionalità singolari."),


    resetNazPlurale("resetNazPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset della tavola 'nazionalitàPlurali'"),
    downloadNazPlurale("downloadNazPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Link nazionalità."),
    downloadNazPluraleTime("downloadNazPluraleTime", AETypePref.integer, 0, "Durata download delle nazionalità plurali."),
    elaboraNazPlurale("elaboraNazPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le nazionalità plurali."),
    elaboraNazPluraleTime("elaboraNazPluraleTime", AETypePref.integer, 0, "Durata elaborazione delle nazionalità plurali."),


    uploadNazPlurale("uploadNazPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nazionalità plurali"),
    uploadNazPluraleTime("uploadNazPluraleTime", AETypePref.integer, 0, "Durata upload delle nazionalità plurali."),
    uploadNazPluralePrevisto("uploadNazPluralePrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per le nazionalità plurali."),


    downloadNomiDoppi("downloadNomiDoppi", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Progetto:Antroponimi/Nomi doppi."),
    downloadNomiTemplate("downloadNomiTemplate", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Template:Incipit lista nomi."),
    downloadNomi("downloadNomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Download dei nomi."),
    downloadNomiTime("downloadNomiTime", AETypePref.integer, 0, "Durata download dei nomi."),
    sogliaMongoNomi("sogliaMongoNomi", AETypePref.integer, 30, "Soglia minima per creare una entity nella collezione Nomi sul mongoDB", false),

    elaboraNomi("elaboraNomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione dei nomi."),
    elaboraNomiTime("elaboraNomiTime", AETypePref.integer, 0, "Durata elaborazione dei nomi."),
    sogliaWikiNomi("sogliaWikiNomi", AETypePref.integer, 50, "Soglia minima per creare una pagina Nomi sul server wiki", false),
    uploadNomi("uploadNomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di nomi che hanno numBio> "),
    uploadNomiTime("uploadNomiTime", AETypePref.integer, 0, "Durata upload dei nomi."),
    uploadNomiPrevisto("uploadNomiPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i nomi."),


    downloadCognomiModulo("downloadCognomiModulo", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Incipit cognomi."),
    downloadCognomi("downloadCognomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Download dei cognomi."),
    downloadCognomiTime("downloadCognomiTime", AETypePref.integer, 0, "Durata download dei cognomi."),

    elaboraCognomi("elaboraCognomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione dei cognomi."),
    uploadCognomi("uploadCognomi", AETypePref.localdatetime, ROOT_DATA_TIME, "Upload di tutte le liste di cognomi oltre la soglia di 50 biografie"),
    uploadCognomiTime("uploadCognomiTime", AETypePref.integer, 0, "Durata upload dei cognomi."),
    uploadCognomiPrevisto("uploadCognomiPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo upload previsto per i cognomi."),


    downloadGenere("downloadGenere", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di Modulo:Bio/Plurale attività genere."),
    downloadGenereTime("downloadGenereTime", AETypePref.integer, 0, "Durata download di Genere."),

    usaTaskResetBio("usaTaskResetBio", AETypePref.bool, false, "Reset calendarizzato di tutte le biografie con cancellazione completa", false),
    resetBio("resetBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Reset completo delle voci biografiche"),
    resetBioTime("resetBioTime", AETypePref.integer, 0, "Durata Reset completo delle biografie."),
    resetBioPrevisto("resetBioPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo reset previsto delle voci biografiche."),

    downloadBio("downloadBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Download di tutte le biografie nuove e modificate"),
    downloadBioTime("downloadBioTime", AETypePref.integer, 0, "Durata ciclo completo download delle biografie."),
    downloadBioPrevisto("downloadBioPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossimo download previsto delle voci biografiche."),
    elaboraBio("elaboraBio", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione di tutte le biografie."),
    elaboraBioTime("elaboraBioTime", AETypePref.integer, 0, "Durata elaborazione delle biografie."),

    //    uploadAttivitaTime("uploadAttivitaTime", AETypePref.integer, "Durata upload delle attività.", 0),
    //    downloadNazionalita("downloadNazionalita", AETypePref.localdatetime, "Download di Modulo:Bio/Plurale nazionalità.", ROOT_DATA_TIME),
    //    downloadProfessione("downloadProfessione", AETypePref.localdatetime, "Download di Modulo:Bio/Link attività.", ROOT_DATA_TIME),
    //    downloadNomi("downloadNomi", AETypePref.localdatetime, "Download di Progetto:Antroponimi/Nomi doppi.", ROOT_DATA_TIME),

    elaboraCognomiTime("elaboraCognomiTime", AETypePref.integer, 0, "Durata elaborazione dei cognomi."),

    statisticaAttPlurale("statisticaAttPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per le attività plurali."),
    statisticaNazPlurale("statisticaNazPlurale", AETypePref.localdatetime, ROOT_DATA_TIME, "Creazione della pagina di statistiche per le nazionalità plurali."),


    usaSottoGiorniAnni("usaSottoGiorniAnni", AETypePref.bool, true, "Usa le sotto-sottopagine (secoli/mesi) per giorni/anni", false),
    sogliaSottoPaginaGiorniAnni("sogliaSottoPaginaGiorniAnni", AETypePref.integer, 50, "Soglia minima per creare una sottopagina di un giorno o anno sul server wiki", false),

    categoriaBio("categoriaBio", AETypePref.string, "BioBot", "Categoria di riferimento per le Biografie", false),
    sogliaAttNazWiki("sogliaAttNazWiki", AETypePref.integer, 50, "Soglia minima per creare la pagina di una attività o nazionalità sul server wiki", false),
    sogliaSottoPagina("sogliaSottoPagina", AETypePref.integer, 50, "Soglia minima per creare una sottopagina di una attività o nazionalità sul server wiki", false),
    sogliaDiv("sogliaDiv", AETypePref.integer, 5, "Soglia minima per usare {{Div col}}", false),
    sogliaIncludeAll("sogliaIncludeAll", AETypePref.integer, 200, "Soglia minima per 'includere' la voce in giorni/anni", false),
    sogliaIncludeParagrafo("sogliaIncludeParagrafo", AETypePref.integer, 50, "Soglia minima per usare i paragrafi 'inclusi' di secondo livello", false),

    usaTreAttivita("usaTreAttivita", AETypePref.bool, false, "Considera tutte le attività (tre) nelle liste di attività", false),
    usaTreAttivitaStatistiche("usaTreAttivitaStatistiche", AETypePref.bool, true, "Considera tutte le attività (tre) nelle statistiche di attività", false),
    usaParagrafiDimensionati("usaParagrafiDimensionati", AETypePref.bool, true, "Nel titolo del paragrafo aggiunge la dimensione delle " + "voci elencate", false),
    usaLinkStatistiche("usaLinkStatistiche", AETypePref.bool, true, "Link alle liste di attività nel template statistiche, anche se " + "rossi", false),
    typeTocAttNaz("typeTocAttNaz", AETypePref.enumerationType, AETypeToc.forceToc, "[AETypeToc] in attività e nazionalità"),
    typeTocGiorni("typeTocGiorni", AETypePref.enumerationType, AETypeToc.forceToc, "[AETypeToc] nelle liste di giorni"),
    typeTocAnni("typeTocAnni", AETypePref.enumerationType, AETypeToc.forceToc, "[AETypeToc] nelle liste di anni"),
    typeTocNomi("typeTocNomi", AETypePref.enumerationType, AETypeToc.forceToc, "[AETypeToc] nelle liste di nomi"),
    typeTocCognomi("typeTocCognomi", AETypePref.enumerationType, AETypeToc.forceToc, "[AETypeToc] nelle liste di cognomi"),
    typeChiaveNulla("typeChiaveNulla", AETypePref.enumerationType, AETypeChiaveNulla.inCoda, "[AETypeChiaveNulla] Posizione del paragrafo 'nullo'"),

    usaSottoNomi("usaSottoNomi", AETypePref.bool, false, "Usa le sotto-sottopagine per i nomi", false),

    usaSottoCognomi("usaSottoCognomi", AETypePref.bool, false, "Usa le sotto-sottopagine per i cognomi", false),

    usaParagrafiGiorni("usaParagrafiGiorni", AETypePref.bool, true, "Usa i paragrafi (secoli) nelle pagine dei giorni", false),
    usaParagrafiAnni("usaParagrafiAnni", AETypePref.bool, true, "Usa i paragrafi (mesi) nelle pagine degli anni", false),
    usaParagrafiAttNaz("usaParagrafiAttNaz", AETypePref.bool, true, "Usa i paragrafi (nazionalità/attività) nelle pagine attività/nazionalità", false),
    usaSottoSottoAttNaz("usaSottoSottoAttNaz", AETypePref.bool, false, "Usa le sotto-sottopagine (nazionalità/attività) di lettere alfabetiche", false),
    usaDivAttNaz("usaDivAttNaz", AETypePref.bool, false, "Usa i {{Div col}} nelle pagine attività e nazionalità", false),

    simboloNato("simboloNato", AETypePref.string, "n.", "Simbolo della nascita nelle didascalie", false),
    simboloMorto("simboloMorto", AETypePref.string, "†", "Simbolo della morte nelle didascalie", false),
    usaSimboliCrono("usaSimboliCrono", AETypePref.bool, true, "Uso dei simboli crono per nati e morti", false),

    linkCrono("linkCrono", AETypePref.enumerationType, AETypeLink.linkLista, "[AETypeLink] a giorni/anni nelle didascalie"),
    linkAttNaz("linkAttNaz", AETypePref.enumerationType, AETypeLink.nessunLink, "[AETypeLink] nei titoli dei paragrafi in attività/nazionalità"),
    linkGiorniAnni("linkGiorniAnni", AETypePref.enumerationType, AETypeLink.nessunLink, "[AETypeLink] nei titoli dei paragrafi in giorni/anni"),
    linkParagrafiNomi("linkParagrafiNomi", AETypePref.enumerationType, AETypeLink.nessunLink, "[AETypeLink] nei titoli dei paragrafi in persone di nome"),
    linkCognomi("linkCognomi", AETypePref.enumerationType, AETypeLink.nessunLink, "[AETypeLink] nei titoli dei paragrafi in persone di cognome"),

    usaTaskBio("usaTaskBio", AETypePref.bool, true, "Download calendarizzato di tutte le biografie", false),
    usaTaskGiorni("usaTaskGiorni", AETypePref.bool, true, "Upload calendarizzato di tutte le pagine dei giorni nato/morto", false),
    usaTaskAnni("usaTaskAnni", AETypePref.bool, true, "Upload calendarizzato di tutte le pagine degli anni nato/morto", false),
    usaTaskAttivita("usaTaskAttivita", AETypePref.bool, false, "Upload calendarizzato di tutte le attività", false),
    usaTaskNazionalita("usaTaskNazionalita", AETypePref.bool, false, "Upload calendarizzato di tutte le nazionalità", false),
    usaTaskCognomi("usaTaskCognomi", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine dei cognomi", false),
    usaTaskNomi("usaTaskNomi", AETypePref.bool, false, "Upload calendarizzato di tutte le pagine dei nomi", false),
    usaTaskElabora("usaTaskElabora", AETypePref.bool, false, "Lista pagine da cancellare e lista errori", false),
    usaTaskStatistiche("usaTaskStatistiche", AETypePref.bool, false, "Elaborazione delle statistiche", false),


    usaRigheGiorni("usaRigheGiorni", AETypePref.bool, false, "Usa righe raggruppate per anno nelle liste dei giorni", false),
    usaRigheAnni("usaRigheAnni", AETypePref.bool, false, "Usa righe raggruppate per giorno nelle liste degli anni", false),
    sogliaCognomiMongo("sogliaCognomiMongo", AETypePref.integer, 30, "Soglia minima per creare una entity nella collezione Cognomi sul mongoDB", false),
    sogliaCognomiWiki("sogliaCognomiWiki", AETypePref.integer, 50, "Soglia minima per creare una pagina Cognomi sul server wiki", false),
    usaLoggerTask("usaLoggerTask", AETypePref.bool, true, "Registra sul log interno l'esecuzione delle task programmate", false),

    elaboraPagineCancella("elaboraPagineCancella", AETypePref.localdatetime, ROOT_DATA_TIME, "Elaborazione delle pagine da cancellare"),
    elaboraPagineCancellaTime("elaboraPagineCancellaTime", AETypePref.integer, 0, "Durata elaborazione delle pagine da cancellare."),
    elaboraPagineCancellaPrevisto("elaboraPagineCancellaPrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossima elaborazione delle pagine da cancellare."),

    statistichePrevisto("statistichePrevisto", AETypePref.localdatetime, ROOT_DATA_TIME, "Prossima elaborazione delle statistiche"),

    maxPageLength("maxPageLength", AETypePref.integer, 200000, "Soglia massima di una pagina in byte", false),
    maxBioPageAnniGiorni("maxBioPageAnniGiorni", AETypePref.integer, 1700, "Soglia massima di bio per usare le sottopagine  giorni/anni", false),
    scriveComunque("scriveComunque", AETypePref.bool, false, "Forza comunque la registrazione della pagina anche se le modifiche sono sulla data", false),
    sottoCategorieNatiPerAnno("sottoCategorieNatiPerAnno", AETypePref.bool, true, "Categorizzazione per secoli delle liste di 'Nati per anno'", false),


    percentualeMinimaBiografie("percentualeMinimaBiografie", AETypePref.decimal, new BigDecimal(99), "Percentuale minima di biografie per avere delle elaborazioni attendibili", false),
    controllaPagine("controllaPagine", AETypePref.bool, false, "Controlla le pagine da cancellare per gli Anni", false),

    usaNumVociGiorni("usaNumVociGiorni", AETypePref.bool, false, "Numero di voci nei paragrafi di Giorni'", false),
    usaNumVociAnni("usaNumVociAnni", AETypePref.bool, false, "Numero di voci nei paragrafi di Anni'", false),
    usaNumVociAttivita("usaNumVociAttivita", AETypePref.bool, false, "Numero di voci nei paragrafi di Attività'", false),
    usaNumVociNazionalita("usaNumVociNazionalita", AETypePref.bool, false, "Numero di voci nei paragrafi di Nazionalità'", false),
    usaNumVociNomi("usaNumVociNomi", AETypePref.bool, true, "Numero di voci nei paragrafi di Nomi'", false),
    usaNumVociCognomi("usaNumVociCognomi", AETypePref.bool, false, "Numero di voci nei paragrafi di Cognomi'", false),
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

    private boolean dinamica;

    //--Link injettato da un metodo static
    public PreferenceService preferenceService;

    //--Link injected da un metodo static
    private LogService logger;

    //--Link injected da un metodo static
    private DateService date;

    //--Link injettato da un metodo static
    private TextService text;

    private Class<?> enumClazz;

    WPref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione) {
        this(keyCode, type, defaultValue, descrizione, true);
    }// fine del costruttore


    WPref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione, boolean dinamica) {
        this.keyCode = keyCode;
        this.type = type;
        this.descrizione = descrizione;
        if (type == AETypePref.enumerationType) {
            if (defaultValue instanceof AITypePref enumeration) {
                this.defaultValue = enumeration.toString();
                this.typeEnum = enumeration;
                this.enumClazz = typeEnum.getClass();
            }
            else {
                this.defaultValue = defaultValue;
                this.typeEnum = null;
                this.enumClazz = null;
            }
            this.dinamica = false;
        }
        else {
            this.defaultValue = defaultValue;
            this.typeEnum = null;
            this.enumClazz = null;
            this.dinamica = dinamica;
        }
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
    public PreferenceService getPreferenceService() {
        return preferenceService;
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
        return preferenceService != null ? preferenceService.getInt(type, keyCode) : 0;
    }

    @Override
    public BigDecimal getDecimal() {
        return preferenceService.getDecimal(type, keyCode);
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

    public boolean isDinamica() {
        return dinamica;
    }

    public boolean needRiavvio() {
        return needRiavvio;
    }

    public boolean isVaad24() {
        return false;
    }

    public Class<?> getEnumClazz() {
        return enumClazz;
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
