package it.algos.wiki24.backend.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 17-Jul-2022
 * Time: 13:54
 */
public enum AETypeLista {
    giornoNascita("nati", "nate", "giorno", "nascita"),
    giornoMorte("morti", "morte", "giorno", "morte"),
    annoNascita("nati", "nate", "anno", "nascita"),
    annoMorte("morti", "morte", "anno", "morte"),
    nazionalitaSingolare("singolare", VUOTA, VUOTA, VUOTA),
    nazionalitaPlurale("plurale", VUOTA, VUOTA, VUOTA),
    attivitaSingolare("singolare", VUOTA, VUOTA, VUOTA),
    attivitaPlurale("plurale", VUOTA, VUOTA, VUOTA),
    cognomi(VUOTA, VUOTA, VUOTA, VUOTA),
    listaBreve(VUOTA, VUOTA, VUOTA, VUOTA),
    listaEstesa(VUOTA, VUOTA, VUOTA, VUOTA);

    private String tagLower;

    private String tagF;

    private String tagUpper;

    private String giornoAnno;

    private String civile;


    AETypeLista(String tag, String tagF, String giornoAnno, String civile) {
        this.tagLower = tag;
        this.tagF = tagF;
        this.giornoAnno = giornoAnno;
        this.civile = civile;
        this.tagUpper = tag != null && tag.length() > 0 ? tag.substring(0, 1).toUpperCase() + tag.substring(1) : VUOTA;
    }

    public String getTagLower() {
        return tagLower;
    }

    public String getTagUpper() {
        return tagUpper;
    }

    public String getGiornoAnno() {
        return giornoAnno;
    }

    public String getTagF() {
        return tagF;
    }

    public String getCivile() {
        return civile;
    }
}
