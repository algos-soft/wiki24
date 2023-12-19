package it.algos.wiki24.backend.wrapper;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;

import java.time.*;
import java.time.format.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: ven, 30-lug-2021
 * Time: 21:48
 * <p>
 * Semplice wrapper per i dati essenziali (tutti) di una pagina biografica recuperata da MediaWiki <br>
 */
public class WrapBio {

    //    private TypePage type;
    //
    //    private long pageid;
    //
    //    private String title;
    //
    //    private LocalDateTime timeStamp;
    private WrapPage wrapPage;

    private String templBio;

    private boolean valida;

    public WrapBio() {
    }

    //    public WrapBio type(final TypePage type) {
    //        this.type = type;
    //        return this;
    //    }
    //
    //    public WrapBio pageid(final long pageid) {
    //        this.pageid = pageid;
    //        return this;
    //    }
    //
    //    public WrapBio title(final String title) {
    //        this.title = title;
    //        return this;
    //    }
    //
    //    public WrapBio timeStamp(final LocalDateTime timeStamp) {
    //        this.timeStamp = timeStamp;
    //        return this;
    //    }

    public WrapBio WrapPage(final WrapPage wrapPage) {
        this.wrapPage = wrapPage;
        return this;
    }

    //    public WrapBio valida(final boolean valida) {
    //        this.valida = valida;
    //        return this;
    //    }

    //    public WrapBio time(final String stringTimestamp) {
    //        this.timeStamp = (stringTimestamp != null && stringTimestamp.length() > 0) ? LocalDateTime.parse(stringTimestamp, DateTimeFormatter.ISO_DATE_TIME) : null;
    //        return this;
    //    }

    public TypePage getType() {
        return wrapPage != null ? wrapPage.getType() : TypePage.indeterminata;
    }

    public long getPageid() {
        return wrapPage != null ? wrapPage.getPageid() : 0;
    }

    public String getTitle() {
        return wrapPage != null ? wrapPage.getTitle() : VUOTA;
    }

    public LocalDateTime getTimeStamp() {
        return wrapPage != null ? wrapPage.getTimeStamp() : ROOT_DATA_TIME;
    }

    public String getTemplBio() {
        return templBio;
    }

    public boolean isValida() {
        return valida;
    }

}
