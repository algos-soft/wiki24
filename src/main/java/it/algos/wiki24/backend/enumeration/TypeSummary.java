package it.algos.wiki24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 14-Jul-2023
 * Time: 10:20
 */
public enum TypeSummary {
    bioBot(VUOTA, false),
    nessuno(VUOTA, false),
    giorniBio("giorniBio", true),
    anniBio("anniBio", true),
    attivitàBio("attivitàBio", true),
    nazionalitàBio("nazionalitàBio", true),
    nomiBio("nomiBio", true),
    statBio("statBio", false),
    test("test", false),
    ;

    boolean usaQuadre;

    private final String tag;

    private static String BIOBOT = "Utente:Biobot";

    TypeSummary(final String tag, boolean usaQuadre) {
        this.tag = tag;
        this.usaQuadre = usaQuadre;
    }


    public static List<TypeSummary> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola, viene il valore corrente <br>
     *
     * @return stringa di valori e valore di default
     */
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAllEnums().forEach(level -> buffer.append(level.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }

    public TypeSummary get(String nome) {
        return getAllEnums()
                .stream()
                .filter(type -> type.name().equals(nome))
                .findAny()
                .orElse(null);
    }

    public String get() {
        if (tag != null && tag.length() > 0) {
            if (usaQuadre) {
                return String.format("%s%s%s%s%s%s%s", DOPPIE_QUADRE_INI, BIOBOT, SLASH, tag, PIPE, tag, DOPPIE_QUADRE_END);
            }
            else {
                return tag;
            }
        }
        else {
            if (usaQuadre) {
                return String.format("%s%s%s%s%s", DOPPIE_QUADRE_INI, BIOBOT, PIPE, this, DOPPIE_QUADRE_END);
            }
            else {
                return name();
            }
        }
    }

}
