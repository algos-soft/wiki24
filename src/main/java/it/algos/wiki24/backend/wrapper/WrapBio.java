package it.algos.wiki24.backend.wrapper;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bioserver.*;

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

    private WrapPage wrapPage;

    private String templBio;
    private BioServerEntity beanBioServer;

    private boolean valida;

    public WrapBio() {
    }


    public WrapBio WrapPage(final WrapPage wrapPage) {
        this.wrapPage = wrapPage;
        return this;
    }
    public static WrapBio nonValida() {
        return new WrapBio().wrapPage(WrapPage.nonValida());
    }
    public static WrapBio valida(WrapPage wrapPage) {
        return new WrapBio().wrapPage(wrapPage).valida();
    }
    public WrapBio valida() {
        this.valida = true;
        return this;
    }
    public WrapBio wrapPage(final WrapPage wrapPage) {
        this.wrapPage = wrapPage;
        return this;
    }
    public WrapBio templBio(final String templBio) {
        this.templBio = templBio;
        return this;
    }

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

    public WrapPage getWrapPage() {
        return wrapPage;
    }

}
