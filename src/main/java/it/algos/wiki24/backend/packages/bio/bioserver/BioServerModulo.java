package it.algos.wiki24.backend.packages.bio.bioserver;

import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 21:41
 */
@Service
public class BioServerModulo extends WikiModulo {

    @Inject
    WikiBotService wikiBotService;

    @Inject
    DownloadService downloadService;

    @Inject
    MailService mailService;

    @Inject
    ElaboraService elaboraService;

    @Inject
    BioMongoModulo bioMongoModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public BioServerModulo() {
        super(BioServerEntity.class, BioServerList.class, BioServerForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.flagDownload = WPref.usaDownloadBioServer;
        super.scheduledDownload = TaskDownloadBioServer.TYPE_SCHEDULE;
        super.lastDownload = WPref.lastDownloadBioServer;
        super.durataDownload = WPref.downloadBioServerTime;
        super.unitaMisuraDownload = TypeDurata.minuti;
    }

    public BioServerEntity newEntity(WrapPage wrapPage) {
        String tmplBio = wikiBotService.estraeTmplBio(wrapPage.getContent());
        if (textService.isValid(tmplBio)) {
            return newEntity(wrapPage.getPageid(), wrapPage.getTitle(), tmplBio, wrapPage.getTimeStamp(), null);
        }
        else {
            return null;
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public BioServerEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, null, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId     (obbligatorio)
     * @param wikiTitle  (obbligatorio)
     * @param tmplBio    (facoltativo)
     * @param lastServer (facoltativo)
     * @param lastMongo  (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public BioServerEntity newEntity(
            long pageId,
            String wikiTitle,
            String tmplBio,
            LocalDateTime lastServer,
            LocalDateTime lastMongo) {
        BioServerEntity newEntityBean = BioServerEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .tmplBio(textService.isValid(tmplBio) ? tmplBio : null)
                .lastServer(lastServer != null ? lastServer : ROOT_DATA_TIME)
                .lastMongo(lastMongo != null ? lastMongo : LocalDateTime.now())
                .build();

        return (BioServerEntity) fixKey(newEntityBean);
    }

    @Override
    public List<BioServerEntity> findAll() {
        return super.findAll();
    }

    public BioServerEntity findOneById(String idValue) {
        return (BioServerEntity) super.findOneById(idValue);
    }

    public List<Long> findOnlyPageId() {
        return mongoService.projectionLong(BioServerEntity.class, FIELD_NAME_PAGE_ID);
    }

    public BioServerEntity findByKey(final Object keyPropertyValue) {
        return (BioServerEntity) super.findByKey(keyPropertyValue);
    }

    public BioServerEntity findByWikiTitle(final String wikiTitle) {
        return (BioServerEntity) super.findOneByProperty(FIELD_NAME_WIKI_TITLE, wikiTitle);
    }


    //--serve solo ad 'oscurare' il metodo sovrascritto
    //--le funzionalità inerenti sono eseguite da download
    public RisultatoReset resetDelete() {
        return null;
    }

    /**
     * Ciclo di download <br>
     */
    public void download() {
        String risultato;
        inizio = System.currentTimeMillis();

        if (count() == 0) {
            risultato = downloadService.cicloIniziale();
        }
        else {
            risultato = downloadService.cicloCorrente();
        }
        //        message = String.format("%s (%s)", "BioServer", "download");
        //        message += CAPO;
        //        message += String.format("%s (%s)", "BioMongo", "elabora");
        //        message += CAPO;
        //        message += WPref.usaDownloadBioServer.getDescrizione();
        //        message += CAPO;
        //        message += risultato;
        mailService.send(BaseVar.projectCurrent, risultato);

        super.fixDownload(inizio);
    }

    public AbstractEntity insertSave(AbstractEntity newModifiedServerBean) {
        BioServerEntity bioServerBean = (BioServerEntity) super.insertSave(newModifiedServerBean);

        elaboraService.creaModificaBeanMongo(bioServerBean);

        return bioServerBean;
    }

}// end of CrudModulo class
