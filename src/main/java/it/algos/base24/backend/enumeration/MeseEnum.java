package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.service.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public enum MeseEnum implements Type {

    gennaio("gennaio", 31, 31, "gen", "Jan"),

    febbraio("febbraio", 28, 29, "feb", "Feb"),

    marzo("marzo", 31, 31, "mar", "Mar"),

    aprile("aprile", 30, 30, "apr", "Apr"),

    maggio("maggio", 31, 31, "mag", "May"),

    giugno("giugno", 30, 30, "giu", "Jun"),

    luglio("luglio", 31, 31, "lug", "Jul"),

    agosto("agosto", 31, 31, "ago", "Aug"),

    settembre("settembre", 30, 30, "set", "Sep"),

    ottobre("ottobre", 31, 31, "ott", "Oct"),

    novembre("novembre", 30, 30, "nov", "Nov"),

    dicembre("dicembre", 31, 31, "dic", "Dec");


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Inject
    public TextService text;

    String nome;

    int giorni;

    int giorniBisestili;

    String sigla;

    String siglaEn;


    /**
     * Costruttore interno dell'Enumeration <br>
     */
    MeseEnum(final String nome, final int giorni, final int giorniBisestili, final String sigla, final String siglaEn) {
        this.nome = nome;
        this.giorni = giorni;
        this.giorniBisestili = giorniBisestili;
        this.sigla = sigla;
        this.siglaEn = siglaEn;
    }

    public static List<MeseEnum> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<MeseEnum> getAll() {
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
        return nome;
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
        MeseEnum mese = getMese(numMeseDellAnno);

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
    public static MeseEnum getMese(final int numMeseDellAnno) {
        MeseEnum mese = null;

        if (numMeseDellAnno > 0 && numMeseDellAnno < 13) {
            for (MeseEnum meseTmp : MeseEnum.values()) {
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
    public static MeseEnum getMese(final String sigla) {
        MeseEnum mese = null;

        if (sigla != null && !sigla.equals(VUOTA)) {
            for (MeseEnum meseTmp : MeseEnum.values()) {
                if (meseTmp.sigla.equals(sigla.toLowerCase()) || meseTmp.nome.equals(sigla.toLowerCase())) {
                    mese = meseTmp;
                }
            }
        }

        return mese;
    }


    // l'anno parte da gennaio che è il numero 1
    private static String getMese(final int ord, final boolean flagBreve) {
        String nome = VUOTA;
        MeseEnum mese;

        mese = getMese(ord);
        if (mese != null) {
            if (flagBreve) {
                nome = mese.sigla;
            }
            else {
                nome = mese.nome;
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
        MeseEnum mese = getMese(nomeBreveLungo);

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

        for (MeseEnum mese : MeseEnum.values()) {
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

        for (MeseEnum mese : MeseEnum.values()) {
            stringa += mese.nome;
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

        for (MeseEnum mese : MeseEnum.values()) {
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

        for (MeseEnum mese : MeseEnum.values()) {
            lista.add(mese.nome);
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
        List<MeseEnum> meseList = Arrays.asList(MeseEnum.values());
        Stream<MeseEnum> meseStream = meseList.stream();

        num = meseStream
                .filter(mese -> mese.siglaEn.equals(siglaEn))
                .collect(Collectors.summingInt(MeseEnum::getOrd));

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
    public String toString() {
        return nome;
    }


    public int getOrd() {
        return this.ordinal() + 1;
    }


    public String getNome() {
        return nome;
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


    public void setText(TextService text) {
        this.text = text;
    }


    @Component
    public static class MeseServiceInjector {

        @Autowired
        private TextService text;


        @PostConstruct
        public void postConstruct() {
            for (MeseEnum mese : MeseEnum.values()) {
                mese.setText(text);
            }
        }

    }
}
