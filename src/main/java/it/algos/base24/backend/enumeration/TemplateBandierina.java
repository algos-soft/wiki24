package it.algos.base24.backend.enumeration;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 12-Nov-2023
 * Time: 18:46
 */
public enum TemplateBandierina {
    GLP("Guadalupa"),
    MTQ("Martinica"),
    GUF("Guyana francese"),
    REU("La Riunione"),
    MYT("Mayotte"),
    BLM("Saint-Barthélemy"),
    MAF("Saint-Martin"),
    NCL("Nuova Caledonia"),
    PYF("Polinesia francese"),
    SPM("Saint-Pierre e Miquelon"),
    ATF("Terre Australi e Antartiche Francesi"),
    WLF("Wallis e Futuna"),
    ABW("Aruba"),
    CUW("Curaçao"),
    SXM("Sint Maarten"),
    BQ1("Bonaire"),
    BQ2("Saba"),
    BQ3("Sint Eustatius"),
;
    String tag;



    /**
     * Costruttore interno della Enumeration <br>
     */
    TemplateBandierina(final String tag ) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
