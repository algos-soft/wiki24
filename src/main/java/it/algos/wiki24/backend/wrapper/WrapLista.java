package it.algos.wiki24.backend.wrapper;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import static it.algos.wiki24.backend.statistiche.Statistiche.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 27-Jul-2022
 * Time: 10:42
 * <p>
 * Semplice wrapper per gestire i dati necessari ad una lista <br>
 */
public class WrapLista {

    AETypeLista typeLista;

    public String titoloParagrafo;

    public String titoloParagrafoLink;

    public String titoloSottoParagrafo;

    public String ordinamento;

    public String lista;

    public String giornoNato;

    public String giornoMorto;

    public String annoNato;

    public String annoMorto;

    @Deprecated
    public String didascaliaBreve;

    public String didascalia;

    public WrapLista(String titoloParagrafo, String titoloParagrafoLink, String titoloSottoParagrafo, String didascaliaBreve, String didascaliaBreveOld) {
        this.titoloParagrafo = titoloParagrafo;
        this.titoloParagrafoLink = titoloParagrafoLink;
        this.titoloSottoParagrafo = titoloSottoParagrafo;
        this.didascaliaBreve = didascaliaBreve;
        if (titoloSottoParagrafo != null && titoloSottoParagrafo.length() > 0) {
            this.didascalia = titoloSottoParagrafo + SEP + didascaliaBreve;
        }
        else {
            this.didascalia = didascaliaBreve;
        }
    }


    public WrapLista(AETypeLista typeLista, String titoloParagrafo, String titoloParagrafoLink, String titoloSottoParagrafo, String ordinamento, String lista, String giornoNato, String giornoMorto, String annoNato, String annoMorto) {
        this.typeLista = typeLista;
        this.titoloParagrafo = titoloParagrafo;
        this.titoloParagrafoLink = titoloParagrafoLink;
        this.titoloSottoParagrafo = titoloSottoParagrafo;
        this.ordinamento = ordinamento;
        this.lista = lista;
        this.giornoNato = giornoNato;
        this.giornoMorto = giornoMorto;
        this.annoNato = annoNato;
        this.annoMorto = annoMorto;

        this.didascalia = switch (typeLista) {
            case giornoNascita -> giornoNato;
            case giornoMorte -> giornoMorto;
            case annoNascita -> annoNato;
            case annoMorte -> annoMorto;
            default -> VUOTO;
        };

        if (ordinamento != null && ordinamento.length() > 0) {
            this.didascalia = ordinamento + SEP + didascalia;
        }
    }

}
