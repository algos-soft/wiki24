package it.algos.wiki24.backend.enumeration;

import static it.algos.vbase.backend.boot.BaseCost.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 27-Feb-2024
 * Time: 06:45
 */
public enum TypeLivello {
    vuota(VUOTA, 0, 0, VUOTA, VUOTA, VUOTA),
    giorni("Nati il", 1, 0, "giorno", "secolo", "decadi"),
    anni("Nati nel", 1, 1, "anno", "mese", "decine"),
    attivita("Progetto:Biografie/Attività/", 2, 2, "attività", "nazionalita", "alfabetico"),
    nazionalita("Progetto:Biografie/Nazionalita/", 2, 2, "nazionalita", "attivita", "alfabetico"),
    nomi("Persone di nome ", 2, 2, "nomi", "attivita", "alfabetico"),
    ;

    private String prefix;


    // livello=1 (sempre) per usare i paragrafi (se superano i criteri locali) nella paginaLista principale
    //   (Nati il 29 febbraio/XIV secolo; Dominicani/Cestisti; Brasiliani/Cestisti; Agronomi/Italiani; Morti nel 2023/Gennaio)
    // livello=2 per usare i paragrafi (se superano i criteri locali) nella sottoPagina
    //   (Dominicani/Cestisti/D; Brasiliani/Cestisti/D; Agronomi/Italiani/C; Morti nel 2023/Gennaio/1-10)
    // livello=3 per usare i paragrafi (se superano i criteri locali) nella sottoSottoPagina
    //   (Brasiliani/Cestisti/D/DA)
    private int livelloParagrafi;

    // livello=0 per NON usare sottoPagine (Nati il 29 febbraio)
    // livello=1 per creare la sottoPagina (Morti nel 2023/Gennaio; Dominicani/Cestisti; Brasiliani/Calciatori; Agronomi/Italiani)
    // livello=2 per creare la sottoSottoPagina (Dominicani/Cestisti/D; Brasiliani/Cestisti/D; Agronomi/Italiani/C)
    // livello=3 (improbabile) per creare la sottoSottoSottoPagina (Dominicani/Cestisti/D/DA; Brasiliani/Cestisti/D/DB; Agronomi/Italiani/C/CA)
    private int livelloSottoPagine;

    private String pagina;

    private String paragrafoSottoPagina;

    private String paragrafoSottoSottoPagina;


    TypeLivello(String prefix, int livelloParagrafi, int livelloSottoPagine, String pagina, String paragrafoSottoPagina, String paragrafoSottoSottoPagina) {
        this.prefix = prefix;
        this.livelloParagrafi = livelloParagrafi;
        this.livelloSottoPagine = livelloSottoPagine;
        this.pagina = pagina;
        this.paragrafoSottoPagina = paragrafoSottoPagina;
        this.paragrafoSottoSottoPagina = paragrafoSottoSottoPagina;
    }

    public int getLivelloParagrafi() {
        return livelloParagrafi;
    }

    public int getLivelloSottoPagine() {
        return livelloSottoPagine;
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
