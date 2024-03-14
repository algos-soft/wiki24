package it.algos.wiki24.backend.enumeration;


import static it.algos.vbase.backend.boot.BaseCost.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 17-Jul-2022
 * Time: 13:54
 */
public enum TypeLista {
    giornoNascita("nati", "nate", "giorno", "nascita", "Liste di nati per giorno", "Lista persone per giorno", TypeLivello.giorni),
    giornoMorte("morti", "morte", "giorno", "morte", "Liste di morti per giorno", "Lista persone per giorno", TypeLivello.giorni),
    annoNascita("nati", "nate", "anno", "nascita", "Liste di nati nel ", "Lista persone per anno", TypeLivello.anni),
    annoMorte("morti", "morte", "anno", "morte", "Liste di morti nel ", "Lista persone per anno", TypeLivello.anni),
    attivitaSingolare("singolare", VUOTA, VUOTA, VUOTA, "Lista attività", "", TypeLivello.attivita),
    attivitaPlurale("plurale", VUOTA, VUOTA, VUOTA, "Lista attività", "", TypeLivello.attivita),
    nazionalitaSingolare("singolare", VUOTA, VUOTA, VUOTA, "Lista nazionalità", "", TypeLivello.nazionalita),
    nazionalitaPlurale("plurale", VUOTA, VUOTA, VUOTA, "Lista nazionalità", "", TypeLivello.nazionalita),
    nomi("nomi", VUOTA, VUOTA, VUOTA, "Liste di persone per nome", "", TypeLivello.nomi),
    cognomi(VUOTA, VUOTA, VUOTA, VUOTA, "Liste di persone per cognome", "", null),
    listaBreve(VUOTA, VUOTA, VUOTA, VUOTA, "", "", null),
    listaEstesa(VUOTA, VUOTA, VUOTA, VUOTA, "", "", null),
    nessunaLista(VUOTA, VUOTA, VUOTA, VUOTA, "", "", null);

    private String tagLower;

    private String tag;

    private String tagF;

    private String tagUpper;

    private String giornoAnno;

    private String civile;

    private String categoria;

    private String persone;

    private TypeLivello typeLivello;

    TypeLista(String tag, String tagF, String giornoAnno, String civile, String categoria, String persone, TypeLivello typeLivello) {
        this.tagLower = tag;
        this.tag = tag;
        this.tagF = tagF;
        this.giornoAnno = giornoAnno;
        this.civile = civile;
        this.categoria = categoria;
        this.persone = persone;
        this.tagUpper = tag != null && tag.length() > 0 ? tag.substring(0, 1).toUpperCase() + tag.substring(1) : VUOTA;
        this.typeLivello = typeLivello != null ? typeLivello : TypeLivello.vuota;
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

    public TypeLivello getTypeLivello() {
        return typeLivello;
    }
}
