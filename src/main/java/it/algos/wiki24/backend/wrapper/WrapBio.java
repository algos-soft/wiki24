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

    private BioServerEntity beanBioServer;

    private boolean valida;

    private boolean creataBioServer;

    private boolean creataBioMongo;

    public WrapBio() {
    }


    public static WrapBio beanBio(final BioServerEntity beanBioServer) {
        WrapBio wrapBio = new WrapBio();
        wrapBio.beanBioServer = beanBioServer;
        wrapBio.valida = true;
        return wrapBio;
    }


    public long getPageid() {
        return beanBioServer != null ? beanBioServer.getPageId() : 0;
    }

    public String getTitle() {
        return beanBioServer != null ? beanBioServer.getWikiTitle() : VUOTA;
    }

    public LocalDateTime getTimeStamp() {
        return beanBioServer != null ? beanBioServer.getLastServer() : ROOT_DATA_TIME;
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
