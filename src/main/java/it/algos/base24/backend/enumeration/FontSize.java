package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 15:46
 * Enumeration type: con interfaccia [type]
 */
public enum FontSize implements Type {

    normal("1"),
    number16("1.6"),
    number20("2"),
    em6("0.6em"),
    em7("0.7em"),
    em8("0.8em"),
    em9("0.9em"),
    px6("6px"),
    px10("10px"),
    px14("14px"),
    px18("18px"),
    px20("20px"),
    px22("22px"),
    cento80("80%"),
    cento120("120%"),
    ;

    public static final String HTML = "font-size";

    private String tag;


    FontSize(String tag) {
        this.tag = tag;
    }

    public static List<FontSize> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<FontSize> getAll() {
        return Arrays.stream(values()).toList();
    }


    @Override
    public List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }


    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola, viene il valore corrente <br>
     *
     * @return stringa di valori e valore di default
     */
    //    @Override
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAllEnums().forEach(enumeration -> buffer.append(enumeration.name() + BaseCost.VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(BaseCost.PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }

}
