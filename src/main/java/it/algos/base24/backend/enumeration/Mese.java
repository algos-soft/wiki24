package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Sun, 08-Oct-2023
 * Time: 13:40
 * Enumeration type: con interfaccia [type]
 */
public enum Mese implements Type {

    gennaio("gennaio", 31, 31, "gen", "Jan", 1, 31),

    febbraio("febbraio", 28, 29, "feb", "Feb", 32, 60),

    marzo("marzo", 31, 31, "mar", "Mar", 61, 91),

    aprile("aprile", 30, 30, "apr", "Apr", 92, 121),

    maggio("maggio", 31, 31, "mag", "May", 122, 152),

    giugno("giugno", 30, 30, "giu", "Jun", 153, 182),

    luglio("luglio", 31, 31, "lug", "Jul", 183, 213),

    agosto("agosto", 31, 31, "ago", "Aug", 214, 244),

    settembre("settembre", 30, 30, "set", "Sep", 245, 274),

    ottobre("ottobre", 31, 31, "ott", "Oct", 275, 305),

    novembre("novembre", 30, 30, "nov", "Nov", 306, 335),

    dicembre("dicembre", 31, 31, "dic", "Dec", 336, 366);


    String tag;

    int giorni;

    int giorniBisestili;

    String sigla;

    String siglaEn;

    int primo;

    int ultimo;


    /**
     * Costruttore interno della Enumeration <br>
     */
    Mese(final String tag, final int giorni, final int giorniBisestili, final String sigla, final String siglaEn, final int primo, final int ultimo) {
        this.tag = tag;
        this.giorni = giorni;
        this.giorniBisestili = giorniBisestili;
        this.sigla = sigla;
        this.siglaEn = siglaEn;
        this.primo = primo;
        this.ultimo = ultimo;
    }

    public static List<Mese> getAllEnums() {
        return Arrays.stream(values()).toList();
    }



    /**
     * Numero di giorni di ogni mese <br>
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @param anno            l'anno di riferimento (per sapere se è bisestile)
     *
     * @return Numero di giorni del mese
     */
    public static int getGiorni(final int numMeseDellAnno, final int anno) {
        int giorniDelMese = 0;
        Mese mese = getMese(numMeseDellAnno);

        if (mese != null) {
            if (!Year.of(anno).isLeap()) {
                giorniDelMese = mese.giorni;
            }
            else {
                giorniDelMese = mese.giorniBisestili;
            }
        }

        return giorniDelMese;
    }


    /**
     * Mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     *
     * @return Mese
     */
    public static Mese getMese(final int numMeseDellAnno) {
        Mese mese = null;

        if (numMeseDellAnno > 0 && numMeseDellAnno < 13) {
            for (Mese meseTmp : Mese.values()) {
                if (meseTmp.ordinal() == numMeseDellAnno - 1) {
                    mese = meseTmp;
                }
            }
        }

        return mese;
    }


    /**
     * Mese
     *
     * @return Mese
     */
    public static Mese getMese(final String sigla) {
        Mese mese = null;

        if (sigla != null && !sigla.equals(VUOTA)) {
            for (Mese meseTmp : Mese.values()) {
                if (meseTmp.sigla.equals(sigla.toLowerCase()) || meseTmp.tag.equals(sigla.toLowerCase())) {
                    mese = meseTmp;
                }
            }
        }

        return mese;
    }


    // l'anno parte da gennaio che è il numero 1
    private static String getMese(final int ord, final boolean flagBreve) {
        String nome = VUOTA;
        Mese mese;

        mese = getMese(ord);
        if (mese != null) {
            if (flagBreve) {
                nome = mese.sigla;
            }
            else {
                nome = mese.tag;
            }
        }

        return nome;
    }


    /**
     * Numero del mese nell'anno
     *
     * @param nomeBreveLungo L'anno parte da gennaio che è il mese numero 1
     *
     * @return Numero del mese
     */
    public static int getOrder(final String nomeBreveLungo) {
        int numMeseDellAnno = 0;
        Mese mese = getMese(nomeBreveLungo);

        if (mese != null) {
            numMeseDellAnno = mese.ordinal();
            numMeseDellAnno = numMeseDellAnno + 1;
        }

        return numMeseDellAnno;
    }


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Stringa dei nomi brevi separati da virgola
     */
    public static String getAllShortString() {
        String stringa = "";
        String sep = ", ";

        for (Mese mese : Mese.values()) {
            stringa += mese.sigla;
            stringa += sep;
        }
        stringa = stringa.substring(0, stringa.length() - sep.length());

        return stringa;
    }


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Stringa dei nomi completi separati da virgola
     */
    public static String getAllLongString() {
        String stringa = "";
        String sep = ", ";

        for (Mese mese : Mese.values()) {
            stringa += mese.tag;
            stringa += sep;
        }
        stringa = stringa.substring(0, stringa.length() - sep.length());

        return stringa;
    }


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Array dei nomi brevi
     */
    public static List<String> getAllShortList() {
        List<String> lista = new ArrayList<String>();

        for (Mese mese : Mese.values()) {
            lista.add(mese.sigla);
        }

        return lista;
    }


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Array dei nomi completi
     */
    public static List<String> getAllLongList() {
        List<String> lista = new ArrayList<String>();

        for (Mese mese : Mese.values()) {
            lista.add(mese.tag);
        }

        return lista;
    }

    /**
     * Numero del mese dalla sigla inglese <br>
     *
     * @return numero del mese (gennaio=1)
     *
     * @since java 11
     */
    public static int getNumMese(final String siglaEn) {
        int num;
        List<Mese> meseList = Arrays.asList(Mese.values());
        Stream<Mese> meseStream = meseList.stream();

        num = meseStream
                .filter(mese -> mese.siglaEn.equals(siglaEn))
                .collect(Collectors.summingInt(Mese::getOrd));

        return num;
    }


    /**
     * Nome completo del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     *
     * @return Nome breve del mese
     */
    public static String getLong(final int numMeseDellAnno) {
        return getMese(numMeseDellAnno, false);
    }

    @Override
    public List<Mese> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        return getAllEnums()
                .stream()
                .map(type->type.getTag())
                .collect(Collectors.toList());
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag;
    }


    public int getOrd() {
        return this.ordinal() + 1;
    }




    public String getSigla() {
        return sigla;
    }

    public String getSiglaEn() {
        return siglaEn;
    }


    public int getGiorni() {
        return giorni;
    }


    public int getGiorniBisestili() {
        return giorniBisestili;
    }

    public int getPrimo() {
        return primo;
    }

    public int getUltimo() {
        return ultimo;
    }
}
