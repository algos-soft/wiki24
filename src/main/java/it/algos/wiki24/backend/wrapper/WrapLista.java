package it.algos.wiki24.backend.wrapper;

import static it.algos.vaad24.backend.boot.VaadCost.*;

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

    public String titoloParagrafo;

    public String titoloParagrafoLink;

    public String titoloSottoParagrafo;

    public String ordinamento;

    public String didascaliaBreve;

    public String didascaliaLunga;

    public WrapLista(String titoloParagrafo, String titoloParagrafoLink, String titoloSottoParagrafo, String didascaliaBreve) {
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

    public WrapLista(String titoloParagrafo, String titoloParagrafoLink, String ordinamento, String titoloSottoParagrafo, String didascaliaBreve) {
        this.titoloParagrafo = titoloParagrafo;
        this.titoloParagrafoLink = titoloParagrafoLink;
        this.ordinamento = ordinamento;
        this.titoloSottoParagrafo = titoloSottoParagrafo;
        this.didascaliaBreve = didascaliaBreve;
        if (titoloSottoParagrafo != null && titoloSottoParagrafo.length() > 0) {
            this.didascaliaLunga = titoloSottoParagrafo + SEP + didascaliaBreve;
        }
        else {
            this.didascaliaLunga = didascaliaBreve;
        }
    }


}
