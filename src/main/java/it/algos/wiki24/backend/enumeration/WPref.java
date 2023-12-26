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
    usaTaskAttSin("usaTaskAttSin", TypePref.bool, true, "Flag per usare la task di download AttivitàSingolare."),
    lastDownloadAttSin("lastDownloadAttSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di AttivitàSingolare."),
    downloadAttSinTime("downloadAttSinTime", TypePref.integer, 0, "Durata download di AttivitàSingolare in secondi."),
    usaElaboraAttSin("usaElaboraAttSin", TypePref.bool, true, "Flag per usare l'elaborazione di AttivitàSingolare."),
    lastElaboraAttSin("lastElaboraAttSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di AttivitàSingolare."),
    elaboraAttSinTime("elaboraAttSinTime", TypePref.integer, 0, "Durata elaborazione di AttivitàSingolare in minuti."),

    //***************
    usaTaskAttPlu("usaTaskAttPlu", TypePref.bool, true, "Flag per usare la task di download AttivitàPlurale."),
    lastDownloadAttPlu("lastDownloadAttPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di AttivitàPlurale."),
    downloadAttPluTime("downloadAttPluTime", TypePref.integer, 0, "Durata download di AttivitàPlurale in secondi."),
    usaElaboraAttPlu("usaElaboraAttPlu", TypePref.bool, true, "Flag per usare l'elaborazione di AttivitàPlurale."),
    lastElaboraAttPlu("lastElaboraAttPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di AttivitàPlurale."),
    elaboraAttPluTime("elaboraAttPluTime", TypePref.integer, 0, "Durata elaborazione di AttivitàPlurale in minuti."),

    //***************

    usaTaskNazSin("usaTaskNazSin", TypePref.bool, true, "Flag per usare la task di download NazionalitàSingolare."),
    lastDownloadNazSin("lastDownloadNazSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di NazionalitàSingolare."),
    downloadNazSinTime("downloadNazSinTime", TypePref.integer, 0, "Durata download di NazionalitàSingolare in minuti."),
    usaElaboraNazSin("usaElaboraNazSin", TypePref.bool, true, "Flag per usare l'elaborazione di NazionalitàSingolare."),
    lastElaboraNazSin("lastElaboraNazSin", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di NazionalitàSingolare."),
    elaboraNazSinTime("elaboraNazSinTime", TypePref.integer, 0, "Durata elaborazione di NazionalitàSingolare in minuti."),

    //***************
    usaTaskNazPlu("usaTaskNazPlu", TypePref.bool, true, "Flag per usare la task di download NazionalitàPlurale."),
    lastDownloadNazPlu("lastDownloadNazPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di NazionalitàPlurale."),
    downloadNazPluTime("downloadNazPluTime", TypePref.integer, 0, "Durata download di NazionalitàPlurale in secondi."),
    usaElaboraNazPlu("usaElaboraNazPlu", TypePref.bool, true, "Flag per usare l'elaborazione di NazionalitàPlurale."),
    lastElaboraNazPlu("lastElaboraNazPlu", TypePref.localdatetime, ROOT_DATA_TIME, "Last elaborazione date and time di NazionalitàPlurale."),
    elaboraNazPluTime("elaboraNazPluTime", TypePref.integer, 0, "Durata elaborazione di NazionalitàPlurale in minuti."),

    //***************
    categoriaBio("categoriaBio", TypePref.string, "BioBot", "Categoria di riferimento per le Biografie"),
    bloccoDownload("bloccoDownload", TypePref.integer, 10000, "Blocco di pagine da leggere in DownloadService; dimensione del ciclo."),

    //***************
    lastDownloadBioServer("lastDownloadBioServer", TypePref.localdatetime, ROOT_DATA_TIME, "Last download date and time di BioServer."),
    downloadBioServerTime("downloadBioServerTime", TypePref.integer, 0, "Durata download di BioServer in secondi."),


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

    //--preferenza che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    private Class<?> enumClazz;

    //--descrizione aggiuntiva eventuale
    private String note;


    WPref(final String keyCode, final TypePref type, final Object defaultValue, final String descrizione) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
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

    //    @Component
    //    public static class PreferenzaInjector {
    //
    //        @Autowired
    //        private PreferenzaModulo preferenzaModulo;
    //
    //
    //        @PostConstruct
    //        public void postConstruct() {
    //            for (WPref pref : WPref.values()) {
    //                pref.preferenzaModulo = this.preferenzaModulo;
    //            }
    //        }
    //
    //    }

}
