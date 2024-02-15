package it.algos.wiki24.backend.enumeration;


import static it.algos.base24.backend.boot.BaseCost.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 17-Jul-2022
 * Time: 13:54
 */
public enum TypeLista {
    giornoNascita("nati", "nate", "giorno", "nascita", "Liste di nati per giorno", "Lista persone per giorno"),
    giornoMorte("morti", "morte", "giorno", "morte", "Liste di morti per giorno", "Lista persone per giorno"),
    annoNascita("nati", "nate", "anno", "nascita", "Liste di nati nel ", "Lista persone per anno"),
    annoMorte("morti", "morte", "anno", "morte", "Liste di morti nel ", "Lista persone per anno"),
    nazionalitaSingolare("singolare", VUOTA, VUOTA, VUOTA, "Lista nazionalità", ""),
    nazionalitaPlurale("plurale", VUOTA, VUOTA, VUOTA, "Lista nazionalità", ""),
    attivitaSingolare("singolare", VUOTA, VUOTA, VUOTA, "Lista attività", ""),
    attivitaPlurale("plurale", VUOTA, VUOTA, VUOTA, "Lista attività", ""),
    nomi(VUOTA, VUOTA, VUOTA, VUOTA, "Liste di persone per nome", ""),
    cognomi(VUOTA, VUOTA, VUOTA, VUOTA, "Liste di persone per cognome", ""),
    listaBreve(VUOTA, VUOTA, VUOTA, VUOTA, "", ""),
    listaEstesa(VUOTA, VUOTA, VUOTA, VUOTA, "", ""),
    nessunaLista(VUOTA, VUOTA, VUOTA, VUOTA, "", "");

    private String tagLower;

    private String tag;

    private String tagF;

    private String tagUpper;

    private String giornoAnno;

    private String civile;

    private String categoria;

    private String persone;


    TypeLista(String tag, String tagF, String giornoAnno, String civile, String categoria, String persone) {
        this.tagLower = tag;
        this.tag = tag;
        this.tagF = tagF;
        this.giornoAnno = giornoAnno;
        this.civile = civile;
        this.categoria = categoria;
        this.persone = persone;
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

    public String getTag() {
        return tag;
    }

    public String getTagF() {
        return tagF;
    }

    public String getCivile() {
        return civile;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPersone() {
        return persone;
    }
}
