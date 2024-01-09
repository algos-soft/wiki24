package it.algos.wiki24.backend.enumeration;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: mar, 27-lug-2021
 * Time: 09:25
 * GET più semplice
 * POST più elaborata
 */
public enum TypeQuery {
    getSenzaLoginSenzaCookies("GET senza login e senza cookies"),
    getLoggatoConCookies("GET loggato con cookies di botLogin"),
    post("POST senza loginCookies"),
    postPiuCookies("POST con loginCookies"),
    login("preliminary GET + POST"),
    uploadSostanzialmenteUguale(""),
    ;

    private String tag;


    TypeQuery(String tag) {
        this.tag = tag;
    }

    public String get() {
        return tag;
    }
}
