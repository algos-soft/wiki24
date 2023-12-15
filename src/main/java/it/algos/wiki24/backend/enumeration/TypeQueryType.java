package it.algos.wiki24.backend.enumeration;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Jul-2023
 * Time: 22:40
 */
public enum TypeQueryType {
    page("&cmtype=page"),
    subCat("&cmtype=subcat"),
    file("&cmtype=file"),
    ;

    private String tag;


    TypeQueryType(String tag) {
        this.tag = tag;
    }

    public String get() {
        return tag;
    }

}
