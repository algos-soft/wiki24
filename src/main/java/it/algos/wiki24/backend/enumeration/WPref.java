package it.algos.wiki24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 07-Sep-2023
 * Time: 10:53
 */
public enum WPref implements IPref {

    //***************
    categoriaBio("categoriaBio", TypePref.string, "BioBot", "Categoria di riferimento per le Biografie", true, false),
    bloccoDownload("bloccoDownload", TypePref.integer, 10000, "Blocco di pagine da leggere in DownloadService; dimensione del ciclo.", false, false),

    //***************
    usaDownloadBioServer("usaDownloadBioServer", TypePref.bool, true, "Download di tutte le biografie nuove/modificate.",true,false),
    lastDownloadBioServer("lastDownloadBioServer", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di BioServer.", true, true),
    downloadBioServerTime("downloadBioServerTime", TypePref.integer, 0, "Durata download di BioServer in minuti."),


    //***************
    usaElaboraBioMongo("usaElaboraBioMongo", TypePref.bool, true, "Elaborazione delle biografie. BioServer -> BioMongo.",true,false),
    lastElaboraBioMongo("lastElaboraBioMongo", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di BioMongo.", true, true),
    elaboraBioMongoTime("elaboraBioMongoTime", TypePref.integer, 0, "Durata elaborazione di BioMongo in minuti."),

    //***************
    lastElaboraParNome("lastElaboraParNome", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParNome."),
    elaboraParNomeTime("elaboraParNomeTime", TypePref.integer, 0, "Durata elaborazione di ParNome in minuti."),

    //***************
    lastElaboraParCognome("lastElaboraParCognome", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParCognome."),
    elaboraParCognomeTime("elaboraParCognomeTime", TypePref.integer, 0, "Durata elaborazione di ParCognome in minuti."),

    //***************
    lastElaboraParSesso("lastElaboraParSesso", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParSesso."),
    elaboraParSessoTime("elaboraParSessoTime", TypePref.integer, 0, "Durata elaborazione di ParSesso in minuti."),

    //***************
    lastElaboraParLuogoNato("lastElaboraParLuogoNato", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParLuogoNato."),
    elaboraParLuogoNatoTime("elaboraParLuogoNatoTime", TypePref.integer, 0, "Durata elaborazione di ParLuogoNato in minuti."),

    //***************
    lastElaboraParGiornoNato("lastElaboraParGiornoNato", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParGiornoNato."),
    elaboraParGiornoNatoTime("elaboraParGiornoNatoTime", TypePref.integer, 0, "Durata elaborazione di ParGiornoNato in minuti."),

    //***************
    lastElaboraParAnnoNato("lastElaboraParAnnoNato", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParAnnoNato."),
    elaboraParAnnoNatoTime("elaboraParAnnoNatoTime", TypePref.integer, 0, "Durata elaborazione di ParAnnoNato in minuti."),


    //***************
    lastElaboraParLuogoMorto("lastElaboraParLuogoMorto", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParLuogoMorto."),
    elaboraParLuogoMortoTime("elaboraParLuogoMortoTime", TypePref.integer, 0, "Durata elaborazione di ParLuogoMorto in minuti."),

    //***************
    lastElaboraParGiornoMorto("lastElaboraParGiornoMorto", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParGiornoMorto."),
    elaboraParGiornoMortoTime("elaboraParGiornoMortoTime", TypePref.integer, 0, "Durata elaborazione di ParGiornoMorto in minuti."),


    //***************
    lastElaboraParAnnoMorto("lastElaboraParAnnoMorto", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParAnnoMorto."),
    elaboraParAnnoMortoTime("elaboraParAnnoMortoTime", TypePref.integer, 0, "Durata elaborazione di ParAnnoMorto in minuti."),

    //***************
    lastElaboraParAttivita("lastElaboraParAttivita", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParAttivita."),
    elaboraParAttivitaTime("elaboraParAttivitaTime", TypePref.integer, 0, "Durata elaborazione di ParAttivita in minuti."),

    //***************
    lastElaboraParNazionalita("lastElaboraParNazionalita", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di ParNazionalita."),
    elaboraParNazionalitaTime("elaboraParNazionalitaTime", TypePref.integer, 0, "Durata elaborazione di ParNazionalita in minuti."),

    //***************
    usaElaboraGiorni("usaElaboraGiorni", TypePref.bool, false, "Flag per usare la task di elaborazione di Giorni."),
    lastElaboraGiorni("lastElaboraGiorni", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di Giorni."),
    elaboraGiorniTime("elaboraGiorniTime", TypePref.integer, 0, "Durata elaborazione di Giorni in minuti."),
    usaUploadGiorni("usaUploadGiorni", TypePref.bool, false, "Flag per usare la task di upload Giorni."),
    lastUploadGiorni("lastUploadGiorni", TypePref.localdatetime, ROOT_DATA_TIME, "Last upload date and time di Giorni.", true, true),
    uploadGiorniTime("uploadGiorniTime", TypePref.integer, 0, "Durata upload di Giorni in minuti."),

    //***************
    usaElaboraAnni("usaElaboraAnni", TypePref.bool, false, "Flag per usare la task di elaborazione di Anni."),
    lastElaboraAnni("lastElaboraAnni", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di Anni."),
    elaboraAnniTime("elaboraAnniTime", TypePref.integer, 0, "Durata elaborazione di Anni in minuti."),
    usaUploadAnni("usaUploadAnni", TypePref.bool, false, "Flag per usare la task di upload Anni."),
    lastUploadAnni("lastUploadAnni", TypePref.localdatetime, ROOT_DATA_TIME, "Last upload date and time di Anni.", true, true),
    uploadAnniTime("uploadAnniTime", TypePref.integer, 0, "Durata upload di Anni in minuti."),

    //***************
    usaTaskAttSin("usaTaskAttSin", TypePref.bool, false, "Flag per usare la task di download AttivitàSingolare."),
    lastDownloadAttSin("lastDownloadAttSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di AttivitàSingolare."),
    downloadAttSinTime("downloadAttSinTime", TypePref.integer, 0, "Durata download di AttivitàSingolare in secondi."),
    usaElaboraAttSin("usaElaboraAttSin", TypePref.bool, false, "Flag per usare l'elaborazione di AttivitàSingolare."),
    lastElaboraAttSin("lastElaboraAttSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di AttivitàSingolare."),
    elaboraAttSinTime("elaboraAttSinTime", TypePref.integer, 0, "Durata elaborazione di AttivitàSingolare in minuti."),

    //***************
    usaTaskAttPlu("usaTaskAttPlu", TypePref.bool, false, "Flag per usare la task di download AttivitàPlurale."),
    lastDownloadAttPlu("lastDownloadAttPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di AttivitàPlurale."),
    downloadAttPluTime("downloadAttPluTime", TypePref.integer, 0, "Durata download di AttivitàPlurale in secondi."),
    usaElaboraAttPlu("usaElaboraAttPlu", TypePref.bool, false, "Flag per usare l'elaborazione di AttivitàPlurale."),
    lastElaboraAttPlu("lastElaboraAttPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di AttivitàPlurale."),
    elaboraAttPluTime("elaboraAttPluTime", TypePref.integer, 0, "Durata elaborazione di AttivitàPlurale in minuti."),

    //***************

    usaTaskNazSin("usaTaskNazSin", TypePref.bool, false, "Flag per usare la task di download NazionalitàSingolare."),
    lastDownloadNazSin("lastDownloadNazSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di NazionalitàSingolare."),
    downloadNazSinTime("downloadNazSinTime", TypePref.integer, 0, "Durata download di NazionalitàSingolare in minuti."),
    usaElaboraNazSin("usaElaboraNazSin", TypePref.bool, false, "Flag per usare l'elaborazione di NazionalitàSingolare."),
    lastElaboraNazSin("lastElaboraNazSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di NazionalitàSingolare."),
    elaboraNazSinTime("elaboraNazSinTime", TypePref.integer, 0, "Durata elaborazione di NazionalitàSingolare in minuti."),

    //***************
    usaTaskNazPlu("usaTaskNazPlu", TypePref.bool, false, "Flag per usare la task di download NazionalitàPlurale."),
    lastDownloadNazPlu("lastDownloadNazPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di NazionalitàPlurale."),
    downloadNazPluTime("downloadNazPluTime", TypePref.integer, 0, "Durata download di NazionalitàPlurale in secondi."),
    usaElaboraNazPlu("usaElaboraNazPlu", TypePref.bool, false, "Flag per usare l'elaborazione di NazionalitàPlurale."),
    lastElaboraNazPlu("lastElaboraNazPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di NazionalitàPlurale."),
    elaboraNazPluTime("elaboraNazPluTime", TypePref.integer, 0, "Durata elaborazione di NazionalitàPlurale in minuti."),


    //***************
    iconaNato("iconaNato", TypePref.string, "n." + SPAZIO_NON_BREAKING, "Icona con spazio per le date di nascita", false, false),
    iconaMorto("iconaMorto", TypePref.string, "†" + SPAZIO_NON_BREAKING, "Icona con spazio per le date di morte", false, false),

    ;

    public PreferenzaModulo preferenzaModulo;

    //--codice di riferimento.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private TypePref type;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--Tipo TypePref per TypePref.enumerationType
    private TypePref typeEnum;

    private Class<?> enumClazz;

    //--preferenza rilevante mostrata in avvio programma
    private boolean critical;

    //--descrizione aggiuntiva eventuale
    private String note;

    //--preferenza che mantiene valori dinamici sempre variabili
    private boolean dinamica;

    //--preferenza che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio; // @todo da implementare

    //--preferenze del programma base
    private boolean base24; // @todo da implementare

    //--preferenze singole per ogni company; usa un prefisso col codice della company
    private boolean usaCompany; // @todo da implementare

    //--preferenze visibile agli admin se l'applicazione è usaSecurity=true
    private boolean visibileAdmin; // @todo da implementare


    WPref(final String keyCode, final TypePref type, final Object defaultValue, final String descrizione) {
        this(keyCode, type, defaultValue, descrizione, false, true);
    }// fine del costruttore

    WPref(final String keyCode, final TypePref type, final Object defaultValue, final String descrizione, final boolean critical, final boolean dinamica) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
        this.critical = critical;
        this.dinamica = type == TypePref.bool ? false : dinamica;
        this.base24 = false;
    }// fine del costruttore


    public static List<IPref> getAllEnums() {
        List<IPref> list = new ArrayList<>();
        for (WPref pref : values()) {
            list.add(pref);
        }
        return list;
    }

    @Override
    public List<WPref> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        return getAllEnums()
                .stream()
                .map(type -> type.getTag())
                .collect(Collectors.toList());
    }


    @Override
    public String getTag() {
        return keyCode;
    }


    public static List<String> getAllKeyCode() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getKeyCode()));
        return listaTags;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public TypePref getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Object getCurrentValue() {
        return preferenzaModulo.getValueCorrente(type, keyCode);
    }

    public String getDescrizione() {
        return descrizione;
    }


    @Override
    public void setValue(Object javaValue) {
        preferenzaModulo.setValueCorrente(type, keyCode, javaValue);
    }

    public String getStr() {
        Object obj;

        if (type == TypePref.string) {
            obj = preferenzaModulo.getValueCorrente(type, keyCode);
            if (obj instanceof String value) {
                return value;
            }
        }

        return VUOTA;
    }

    public boolean is() {
        Object obj;

        if (type == TypePref.bool) {
            obj = preferenzaModulo.getValueCorrente(type, keyCode);
            if (obj instanceof Boolean value) {
                return value;
            }
        }
        return false;
    }

    public int getInt() {
        Object obj;

        if (type == TypePref.integer) {
            obj = preferenzaModulo.getValueCorrente(type, keyCode);
            if (obj instanceof Integer value) {
                return value;
            }
        }

        return 0;
    }

    public LocalDateTime getDateTime() {
        Object obj;

        if (type == TypePref.localdatetime) {
            obj = preferenzaModulo.getValueCorrente(type, keyCode);
            if (obj instanceof LocalDateTime value) {
                return value;
            }
        }

        return ERROR_DATA_TIME;
    }

    @Override
    public void setPreferenzaModulo(PreferenzaModulo preferenzaModulo) {
        this.preferenzaModulo = preferenzaModulo;
    }

    @Override
    public boolean isBase() {
        return base24;
    }

    @Override
    public boolean isCritical() {
        return critical;
    }

    @Override
    public boolean isDinamica() {
        return dinamica;
    }

}
