package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import org.apache.commons.lang3.*;
import org.json.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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
