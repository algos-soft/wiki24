package it.algos.wiki24.backend.enumeration;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Jul-2023
 * Time: 07:01
 */
public enum TypeQueryProp {

    ids("&cmprop=ids"),
    title("&cmprop=title"),
    all("&cmprop=ids|title"),
    ;

    private String tag;


    TypeQueryProp(String tag) {
        this.tag = tag;
    }

    public String get() {
        return tag;
    }

}
