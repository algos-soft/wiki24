package it.algos.wiki24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 27-Feb-2024
 * Time: 06:45
 */
public enum TypeLivello {
    vuota(VUOTA, 0, 0, VUOTA, VUOTA, VUOTA),
    giorni("Nati il", 1, 1, "giorno", "secolo", "decadi"),
    anni("Nati nel", 2, 1, "anno", "mese", "decine"),
    attivita("Progetto:Biografie/Attività/", 3, 2, "attività", "nazionalita", "alfabetico"),
    nazionalita("Progetto:Biografie/Nazionalita/", 3, 2, "nazionalita", "attivita", "alfabetico"),
    ;

    private String prefix;

    private int livelloPagine;
    private int livelloParagrafi;


    private String pagina;

    private String paragrafoSottoPagina;

    private String paragrafoSottoSottoPagina;


    TypeLivello(String prefix, int livelloPagine, int livelloParagrafi, String pagina, String paragrafoSottoPagina, String paragrafoSottoSottoPagina) {
        this.prefix = prefix;
        this.livelloPagine = livelloPagine;
        this.livelloParagrafi = livelloParagrafi;
        this.pagina = pagina;
        this.paragrafoSottoPagina = paragrafoSottoPagina;
        this.paragrafoSottoSottoPagina = paragrafoSottoSottoPagina;
    }

    public int getLivelloPagine() {
        return livelloPagine;
    }

    public int getLivelloParagrafi() {
        return livelloParagrafi;
    }

    public String getPagina() {
        return pagina;
    }

    public String getParagrafoSottoPagina() {
        return paragrafoSottoPagina;
    }

    public String getParagrafoSottoSottoPagina() {
        return paragrafoSottoSottoPagina;
    }
}
