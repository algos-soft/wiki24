package it.algos.wiki24.backend.enumeration;

import it.algos.vbase.backend.boot.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import org.apache.commons.lang3.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Jan-2024
 * Time: 15:40
 */
public enum TypeDopo {
    refOpen("<ref"),
    refName("<ref name="),
    refEnd("</ref>"),
    refTag("{{#tag:ref"),
    http("http"),
    html("html"),
    html2("[html"),
    note("<!--"),
    noWiki("<nowiki>"),
    doppiaGraffa("{{"),
    small("<small>"),
    ;


    private String tag;


    TypeDopo(String tag) {
        this.tag = tag;
    }

    public String get(String valoreGrezzo) {
        String testoOut = VUOTA;

        if (valoreGrezzo != null && valoreGrezzo.length() > 0) {
            testoOut = StringUtils.stripEnd(valoreGrezzo, BaseCost.SPAZIO);
            if (testoOut.contains(tag)) {
                testoOut = testoOut.substring(0, testoOut.indexOf(tag));
                testoOut = StringUtils.stripEnd(testoOut, BaseCost.SPAZIO);
            }
            else {
                testoOut = valoreGrezzo;
            }
        }

        return testoOut;
    }

}
