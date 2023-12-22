package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.packages.utility.preferenza.*;

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
public enum Pref implements IPref {
    debug("debug", TypePref.bool, true, "Flag generale di debug"),
    usaBackgroundColor("usaBackgroundColor", TypePref.bool, true, "Uso dello sfondo [background] colorato."),
    nonBreaking("nonBreaking", TypePref.string, SPAZIO_NON_BREAKING, "Spazio non-breaking."),
    rilascio("rilascio", TypePref.localdatetime, ROOT_DATA_TIME, "Rilascio della versione."),
    usaNotification("usaNotification", TypePref.bool, true, "Usa i messaggi di avviso [Notification] in basso a sinistra."),
    durataNotification("durataNotification", TypePref.integer, 2, "Durata (secondi) del messaggio di avviso [Notification]."),
    usaMenuAutomatici("usaMenuAutomatici", TypePref.bool, true, "Creazione automatica dei menu per tutte le istanze di [xxxView]."),
    usaConfermaCancellazione("usaConfermaCancellazione", TypePref.bool, true, "Dialogo di conferma per il bottone [Delete]."),
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


    Pref(final String keyCode, final TypePref type, final Object defaultValue, final String descrizione) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
    }// fine del costruttore


    public static List<IPref> getAllEnums() {
        List<IPref> list = new ArrayList<>();
        for (Pref pref : values()) {
            list.add(pref);
        }
        return list;
    }

    @Override
    public List<Pref> getAll() {
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

        getAllEnums().forEach(pref -> listaTags.add(pref.getKeyCode()));
        return listaTags;
    }

    @Override
    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public TypePref getType() {
        return type;
    }


    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Object getCurrentValue() {
        return preferenzaModulo.getValueCorrente(type, keyCode);
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setValue(Object javaValue) {
        preferenzaModulo.setValueCorrente(type, keyCode, javaValue);
    }

    public String getStr() {
        return preferenzaModulo != null ? preferenzaModulo.getStr(this) : VUOTA;
    }

    public boolean is() {
        return preferenzaModulo != null ? preferenzaModulo.is(this) : false;
    }

    public int getInt() {
        return preferenzaModulo != null ? preferenzaModulo.getInt(this) : 0;
    }

    public LocalDateTime getDateTime() {
        return preferenzaModulo != null ? preferenzaModulo.getDateTime(this) : ROOT_DATA_TIME;
    }

    @Override
    public void setPreferenzaModulo(PreferenzaModulo preferenzaModulo) {
        this.preferenzaModulo = preferenzaModulo;
    }

}
