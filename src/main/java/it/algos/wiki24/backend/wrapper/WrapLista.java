package it.algos.wiki24.backend.wrapper;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;

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

    public String annonato;

    public String annoMorto;

    @Deprecated
    public String didascaliaBreve;

    @Deprecated
    public String didascaliaLunga;

    public WrapLista(String titoloParagrafo, String titoloParagrafoLink, String titoloSottoParagrafo, String didascaliaBreve, String didascaliaBreveOld) {
        this.titoloParagrafo = titoloParagrafo;
        this.titoloParagrafoLink = titoloParagrafoLink;
        this.titoloSottoParagrafo = titoloSottoParagrafo;
        this.didascaliaBreve = didascaliaBreve;
        if (titoloSottoParagrafo != null && titoloSottoParagrafo.length() > 0) {
            this.didascaliaLunga = titoloSottoParagrafo + SEP + didascaliaBreve;
        }
        else {
            this.didascaliaLunga = didascaliaBreve;
        }
    }


    public WrapLista(AETypeLista typeLista, String titoloParagrafo, String titoloParagrafoLink, String ordinamento, String titoloSottoParagrafo, String lista, String giornoNato, String giornoMorto, String annonato, String annoMorto) {
        this.typeLista = typeLista;
        this.titoloParagrafo = titoloParagrafo;
        this.titoloParagrafoLink = titoloParagrafoLink;
        this.ordinamento = ordinamento;
        this.titoloSottoParagrafo = titoloSottoParagrafo;
        this.lista = lista;
        this.giornoNato = giornoNato;
        this.giornoMorto = giornoMorto;
        this.annonato = annonato;
        this.annoMorto = annoMorto;
    }

}
