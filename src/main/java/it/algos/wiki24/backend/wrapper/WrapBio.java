package it.algos.wiki24.backend.wrapper;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;

import java.time.*;

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

    private BioServerEntity beanBioServer;

    private boolean valida;

    public void setCreataBioServer(boolean creataBioServer) {
        this.creataBioServer = creataBioServer;
    }

    public void setCreataBioMongo(boolean creataBioMongo) {
        this.creataBioMongo = creataBioMongo;
    }

    private boolean creataBioServer;

    private boolean creataBioMongo;

    public WrapBio() {
    }


    public static WrapBio bean(final BioServerEntity beanBioServer) {
        WrapBio wrapBio = new WrapBio();
        wrapBio.beanBioServer = beanBioServer;
        wrapBio.valida = beanBioServer != null;
        return  wrapBio;
    }


    public long getPageid() {
        return beanBioServer != null ? beanBioServer.getPageId() : 0;
    }

    public String getTitle() {
        return beanBioServer != null ? beanBioServer.getWikiTitle() : VUOTA;
    }

    public LocalDateTime getTimeStamp() {
        return beanBioServer != null ? beanBioServer.getTimestamp() : ROOT_DATA_TIME;
    }

    public String getTemplBio() {
        return beanBioServer != null ? beanBioServer.getTmplBio() : VUOTA;
    }

    public BioServerEntity getBeanBioServer() {
        return beanBioServer;
    }

    public boolean isValida() {
        return valida;
    }

    public boolean isCreataBioServer() {
        return creataBioServer;
    }

    public boolean isCreataBioMongo() {
        return creataBioMongo;
    }

}
