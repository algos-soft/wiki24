package it.algos.wiki24.backend.packages.tabelle.nazplurale;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 08:49
 */
@Service
public class NazPluraleModulo extends WikiModulo {

    @Inject
    private NazSingolareModulo nazSingolareModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NazPluraleModulo() {
        super(NazPluraleEntity.class, NazPluraleView.class, NazPluraleList.class, NazPluraleForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.scheduledDownload = TypeSchedule.zeroQuaranta;
        super.lastDownload = WPref.lastDownloadNazPlu;
        super.durataDownload = WPref.downloadNazPluTime;
        super.unitaMisuraDownload = TypeDurata.minuti;

        super.lastElabora = WPref.lastElaboraNazPlu;
        super.durataElabora = WPref.elaboraNazPluTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NazPluraleEntity newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA, 0, 0, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param plurale (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NazPluraleEntity newEntity(
            String plurale,
            List<String> txtSingolari,
            String lista,
            String pagina,
            int numBio,
            int numSingolari,
            boolean superaSoglia,
            boolean esisteLista) {
        NazPluraleEntity newEntityBean = NazPluraleEntity.builder()
                .plurale(textService.isValid(plurale) ? plurale : null)
                .txtSingolari(txtSingolari)
                .lista(textService.isValid(lista) ? lista : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .numBio(numBio)
                .numSingolari(numSingolari)
                .superaSoglia(superaSoglia)
                .esisteLista(esisteLista)
                .build();

        return (NazPluraleEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NazPluraleEntity> findAll() {
        return super.findAll();
    }


    @Override
    public NazPluraleEntity findByKey(final Object keyPropertyValue) {
        return (NazPluraleEntity) super.findByKey(keyPropertyValue);
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

        nazSingolareModulo.download();
        nazSingolareModulo.elabora();
        this.creaTavolaDistinct();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    public void creaTavolaDistinct() {
        List<NazSingolareEntity> listaNazSingolariDistinte = nazSingolareModulo.findAllByDistinctPlurale(); ;
        NazPluraleEntity newBean;
        List<String> listaSingolari;

        if (listaNazSingolariDistinte == null || listaNazSingolariDistinte.size() < 1) {
            message = String.format("Non sono riuscito a leggere da mongoDB la collection  %s", "nazsingolare");
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (NazSingolareEntity naz : listaNazSingolariDistinte) {
            listaSingolari = nazSingolareModulo.findSingolariByPlurale(naz);
            newBean = newEntity(naz.plurale, listaSingolari, textService.primaMaiuscola(naz.plurale), textService.primaMaiuscola(naz.pagina), 0, 0, false, false);
            mappaBeans.put(naz.plurale, newBean);
        }
    }


    @Override
    public String elabora() {
        super.elabora();

        List<NazPluraleEntity> listaBeans = findAll();
        List<String> listaNazionalitaSingolari;
        int numBio;

        if (listaBeans != null && listaBeans.size() > 0) {
            for (NazPluraleEntity entityBean : listaBeans) {
                numBio = 0;
                listaNazionalitaSingolari = entityBean.txtSingolari;
                if (listaNazionalitaSingolari != null && listaNazionalitaSingolari.size() > 0) {
                    for (String nazionalitaSingolare : listaNazionalitaSingolari) {
                        numBio += bioMongoModulo.countAllByNazionalitaSingolare(nazionalitaSingolare);
                    }
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

        message = String.format("Inizio del ciclo di upload di tutte le nazionalità (plurali)");
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));
        logger.info(new WrapLog().message(message).type(TypeLog.upload));

        for (NazPluraleEntity nazionalitaBean : findAll()) {
            uploadPagina(nazionalitaBean);
        }

        message = String.format("Fine del ciclo di upload di tutte le nazionalità (plurali)");
        logger.info(new WrapLog().message(message).type(TypeLog.upload));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));

        return super.fixUpload(inizio);
    }


    @Override
    public void testPagina(AbstractEntity nazionalitaPlurale) {
        uploadService.nazionalitaTest((NazPluraleEntity) nazionalitaPlurale);
    }


    @Override
    public void uploadPagina(AbstractEntity nazionalitaPlurale) {
        uploadService.nazionalita((NazPluraleEntity) nazionalitaPlurale);
    }


}// end of CrudModulo class
