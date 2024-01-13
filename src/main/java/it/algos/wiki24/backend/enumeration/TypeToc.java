package it.algos.wiki24.backend.enumeration;


import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 01-Jul-2022
 * Time: 12:21
 */
public enum TypeToc  {

    nessuno(VUOTA),
    noToc("__NOTOC__"),
    forceToc("__FORCETOC__"),
    toc("__TOC__");

    private final String tag;

    TypeToc(final String tag) {
        this.tag = tag;
    }


    public static List<TypeToc> getAllEnums() {
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

    public TypeToc get(String nome) {
        return getAllEnums()
                .stream()
                .filter(type -> type.name().equals(nome))
                .findAny()
                .orElse(null);
    }

    public String get() {
        return tag;
    }

}
