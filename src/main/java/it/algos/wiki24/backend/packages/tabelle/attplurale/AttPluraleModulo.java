package it.algos.wiki24.backend.packages.tabelle.attplurale;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 10-Dec-2023
 * Time: 11:54
 */
@Service
public class AttPluraleModulo extends WikiModulo {

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    QueryService queryService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttPluraleModulo() {
        super(AttPluraleEntity.class, AttPluraleView.class, AttPluraleList.class, AttPluraleForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.scheduledDownload = TypeSchedule.zeroTrenta;

        super.lastDownload = WPref.lastDownloadAttPlu;
        super.durataDownload = WPref.downloadAttPluTime;
        super.unitaMisuraDownload = TypeDurata.minuti;

        super.lastElabora = WPref.lastElaboraAttPlu;
        super.durataElabora = WPref.elaboraAttPluTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.lastUpload = WPref.lastUploadAttPlu;
        super.durataUpload = WPref.uploadAttPluTime;
        super.unitaMisuraUpload = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttPluraleEntity newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA, VUOTA, 0, 0, false, false, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param plurale (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttPluraleEntity newEntity(
            String plurale,
            List<String> txtSingolari,
            String lista,
            String pagina,
            String categoria,
            int numBio,
            int numSingolari,
            boolean superaSoglia,
            boolean esisteLista,
            boolean esistePagina,
            boolean esisteCategoria) {
        AttPluraleEntity newEntityBean = AttPluraleEntity.builder()
                .plurale(textService.isValid(plurale) ? plurale : null)
                .txtSingolari(txtSingolari)
                .lista(textService.isValid(lista) ? lista : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .categoria(textService.isValid(categoria) ? categoria : null)
                .numBio(numBio)
                .numSingolari(numSingolari == 0 ? txtSingolari != null ? txtSingolari.size() : 0 : 0)
                .superaSoglia(superaSoglia)
                .esisteLista(esisteLista)
                .esistePagina(esistePagina)
                .esisteCategoria(esisteCategoria)
                .build();

        return (AttPluraleEntity) fixKey(newEntityBean);
    }

    @Override
    public List<AttPluraleEntity> findAll() {
        return super.findAll();
    }


    @Override
    public AttPluraleEntity findByKey(final Object keyPropertyValue) {
        return (AttPluraleEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }


    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Link attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività plurali <br>
     */
    public void download() {
        inizio = System.currentTimeMillis();

        super.deleteAll();

        attSingolareModulo.download();
        attSingolareModulo.elabora();
        this.creaTavolaDistinct();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    public void creaTavolaDistinct() {
        List<AttSingolareEntity> listaAttSingolariDistinte = attSingolareModulo.findAllByDistinctPlurale(); ;
        AttPluraleEntity newBean;
        List<String> listaSingolari;

        if (listaAttSingolariDistinte == null || listaAttSingolariDistinte.size() < 1) {
            message = String.format("Non sono riuscito a leggere da mongoDB la collection  %s", "attsingolare");
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (AttSingolareEntity att : listaAttSingolariDistinte) {
            listaSingolari = attSingolareModulo.findSingolariByPlurale(att);
            newBean = newEntity(att.plurale, listaSingolari, textService.primaMaiuscola(att.plurale), textService.primaMaiuscola(att.pagina), VUOTA, 0, 0, false, false, false, false);
            mappaBeans.put(att.plurale, newBean);
        }
    }

    @Override
    public String elabora() {
        super.elabora();
        int soglia = WPref.sogliaPaginaAttivita.getInt();

        List<AttPluraleEntity> listaBeans = findAll();
        List<String> listaAttivitaSingolari;
        int numBio;

        if (listaBeans != null && listaBeans.size() > 0) {
            for (AttPluraleEntity entityBean : listaBeans) {
                numBio = 0;
                listaAttivitaSingolari = entityBean.txtSingolari;
                if (listaAttivitaSingolari != null && listaAttivitaSingolari.size() > 0) {
                    for (String attivitaSingolare : listaAttivitaSingolari) {
                        numBio += bioMongoModulo.countAllByAttivitaSingolare(attivitaSingolare);
                    }
                }
                entityBean.categoria = textService.isValid(entityBean.plurale) ? textService.primaMaiuscola(entityBean.plurale) : VUOTA;
                if (numBio > soglia) {
                    entityBean.superaSoglia = true;
                }

                if (queryService.isEsiste(annotationService.getAnchorPrefix(AttPluraleEntity.class, entityBean.lista))) {
                    entityBean.esisteLista = true;
                }
                if (queryService.isEsiste(entityBean.pagina)) {
                    entityBean.esistePagina = true;
                }
                if (queryService.isEsiste(entityBean.categoria)) {
                    entityBean.esisteCategoria = true;
                }

                entityBean.numBio = numBio;
                save(entityBean);
            }
        }

        super.fixInfoElabora();
        return VUOTA;
    }

    @Override
    public String uploadAll() {
        inizio = System.currentTimeMillis();

        message = String.format("Inizio del ciclo di upload di tutte le attività (plurali)");
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));
        logger.info(new WrapLog().message(message).type(TypeLog.upload));

        for (AttPluraleEntity attivitaBean : findAll()) {
            uploadPagina(attivitaBean);
        }

        message = String.format("Fine del ciclo di upload di tutte le attività (plurali)");
        logger.info(new WrapLog().message(message).type(TypeLog.upload));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));

        return super.fixUpload(inizio);
    }


    @Override
    public void testPagina(AbstractEntity attivitaPlurale) {
        uploadService.attivitaTest((AttPluraleEntity) attivitaPlurale);
    }


    @Override
    public void uploadPagina(AbstractEntity attivitaPlurale) {
        uploadService.attivita((AttPluraleEntity) attivitaPlurale);
    }


}// end of CrudModulo class
