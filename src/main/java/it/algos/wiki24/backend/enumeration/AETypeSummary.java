package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.interfaces.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 14-Jul-2023
 * Time: 10:20
 */
public enum AETypeSummary implements AITypePref {
    bioBot(VUOTA),
    giorniBio("giorniBio"),
    anniBio("anniBio"),
    statBio("statBio"),
    ;

    private final String tag;

    private static String BIOBOT = "Utente:Biobot";

    AETypeSummary(final String tag) {
        this.tag = tag;
    }


    public static List<AETypeSummary> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola, viene il valore corrente <br>
     *
     * @return stringa di valori e valore di default
     */
    @Override
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAllEnums().forEach(level -> buffer.append(level.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }

    @Override
    public AETypeSummary get(String nome) {
        return getAllEnums()
                .stream()
                .filter(type -> type.name().equals(nome))
                .findAny()
                .orElse(null);
    }

    public String get() {
        if (tag != null && tag.length() > 0) {
            return String.format("%s%s%s%s%s%s%s", DOPPIE_QUADRE_INI, BIOBOT, SLASH, tag, PIPE, tag, DOPPIE_QUADRE_END);
        }
        else {
            return String.format("%s%s%s%s%s", DOPPIE_QUADRE_INI, BIOBOT, PIPE, this, DOPPIE_QUADRE_END);
        }
    }

}
