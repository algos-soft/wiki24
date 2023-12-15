package it.algos.wiki24.backend.wrapper;

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

    private TypePage type;

    private long pageid;

    private String title;

    private LocalDateTime timeStamp;

    private String templBio;

    private boolean valida;

    public WrapBio() {
    }


    public WrapBio type(final TypePage type) {
        this.type = type;
        return this;
    }

    public WrapBio pageid(final long pageid) {
        this.pageid = pageid;
        return this;
    }

    public WrapBio title(final String title) {
        this.title = title;
        return this;
    }

    public WrapBio timeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public WrapBio templBio(final String templBio) {
        this.templBio = templBio;
        return this;
    }

    public WrapBio valida(final boolean valida) {
        this.valida = valida;
        return this;
    }

    public WrapBio time(final String stringTimestamp) {
        this.timeStamp = (stringTimestamp != null && stringTimestamp.length() > 0) ? LocalDateTime.parse(stringTimestamp, DateTimeFormatter.ISO_DATE_TIME) : null;
        return this;
    }

    public TypePage getType() {
        return type;
    }

    public long getPageid() {
        return pageid;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getTemplBio() {
        return templBio;
    }

    public boolean isValida() {
        return valida;
    }

}
