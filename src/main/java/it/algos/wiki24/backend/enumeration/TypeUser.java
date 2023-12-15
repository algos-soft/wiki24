package it.algos.wiki24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: mer, 14-lug-2021
 * Time: 16:53
 */
public enum TypeUser {

    anonymous(VUOTA, VUOTA, 50),
    user("user", ".user", 500),
    admin("user", ".admin", 500),
    bot("bot", ".bot", 5000),
    ;

    private static final String ASSERT = "&assert=";

    private static final String LIMIT = "&cmlimit=";

    private final String affermazione;

    private final String tagLogin;

    private final int limit;

    TypeUser(final String affermazione, final String tagLogin, final int limit) {
        this.affermazione = affermazione;
        this.tagLogin = tagLogin;
        this.limit = limit;
    }


    public String affermazione() {
        return affermazione.length() > 0 ? ASSERT + affermazione : VUOTA;
    }

    public String limit() {
        return LIMIT + limit;
    }

    public String tagLogin() {
        return tagLogin;
    }

    public int getLimit() {
        return limit;
    }
}
