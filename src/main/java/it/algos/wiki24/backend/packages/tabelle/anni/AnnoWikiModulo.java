package it.algos.wiki24.backend.packages.tabelle.anni;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 07:47
 */
@Service
public class AnnoWikiModulo extends WikiModulo {

    @Inject
    AnnoModulo annoModulo;

    @Inject
    WikiUtilityService wikiUtilityService;

    @Inject
    BioMongoModulo bioMongoModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AnnoWikiModulo() {
        super(AnnoWikiEntity.class, AnnoWikiList.class, AnnoWikiForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.scheduledElabora = TypeSchedule.dieciVenerdi;
        super.lastElabora = WPref.lastElaboraAnni;
        super.durataElabora = WPref.elaboraAnniTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.scheduledUpload = TypeSchedule.dieciDomenica;
        super.lastUpload = WPref.lastUploadAnni;
        super.durataUpload = WPref.uploadAnniTime;
        super.unitaMisuraUpload = TypeDurata.minuti;
    }

    /**
     * Regola le property visibili in una lista CrudList <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    public List<String> getListPropertyNames() {
        return Arrays.asList("ordine", "bioNati", "pageNati", "bioMorti", "pageMorti");
    }
    /**
     * Regola le property visibili in una scheda CrudForm <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    public List<String> getFormPropertyNames() {
        return Arrays.asList("ordine", "nome", "bioNati", "pageNati", "bioMorti", "pageMorti");
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AnnoWikiEntity newEntity() {
        return newEntity(0, VUOTA, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome      corrente
     * @param secolo      di appartenenza
     * @param pageNati  anchorLink
     * @param pageMorti anchorLink
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AnnoWikiEntity newEntity(final int ordine, final String nome, final SecoloEntity secolo, String pageNati, String pageMorti) {
        AnnoWikiEntity newEntityBean = AnnoWikiEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .bioNati(0)
                .bioMorti(0)
                .pageNati(textService.isValid(pageNati) ? pageNati : null)
                .pageMorti(textService.isValid(pageMorti) ? pageMorti : null)
                //                .esistePaginaNati(false)
                //                .esistePaginaMorti(false)
                //                .natiOk(false)
                //                .mortiOk(false)
                .build();

        return (AnnoWikiEntity) fixKey(newEntityBean);
    }

    @Override
    public List<AnnoWikiEntity> findAll() {
        return super.findAll();
    }


    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        List<AnnoEntity> anniBase;
        AnnoWikiEntity newBean;
        int ordine;
        String nome;
        SecoloEntity secolo;
        String pageNati;
        String pageMorti;
        anniBase = annoModulo.findAll();
        if (anniBase == null || anniBase.size() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Anno'")));
            return null;
        }

        for (AnnoEntity anno : anniBase) {
            nome = anno.nome;
            ordine = anno.ordine;
            secolo = anno.secolo;
            pageNati = wikiUtilityService.wikiTitleNatiAnno(nome);
            pageMorti = wikiUtilityService.wikiTitleMortiAnno(nome);
            newBean = newEntity(ordine, nome, secolo, pageNati, pageMorti);
            insertSave(newBean);
        }

        return null;
    }

    @Override
    public void elabora() {
        inizio = System.currentTimeMillis();

        for (AnnoWikiEntity annoBean : findAll()) {
            annoBean.bioNati = bioMongoModulo.countAllByAnnoNato(annoBean.nome);
            annoBean.bioMorti = bioMongoModulo.countAllByAnnoMorto(annoBean.nome);
            save(annoBean);
        }

        super.fixElabora(inizio);
    }

    @Override
    public void uploadAll() {
        inizio = System.currentTimeMillis();

        for (AnnoWikiEntity annoBean : findAll().subList(17, 18)) {
            uploadPaginaNati(annoBean);
            uploadPaginaMorti(annoBean);
        }

        super.fixUpload(inizio);
    }

    @Override
    public void wikiView(AbstractEntity annoBean) {
        wikiApiService.openWikiPage(((AnnoWikiEntity) annoBean).nome);
    }

    @Override
    public void testPaginaNati(AbstractEntity annoBean) {
        appContext.getBean(UploadAnnoNato.class, ((AnnoWikiEntity) annoBean).nome).test().upload().isValido();
    }

    @Override
    public void testPaginaMorti(AbstractEntity annoBean) {
        appContext.getBean(UploadAnnoMorto.class, ((AnnoWikiEntity) annoBean).nome).test().upload().isValido();
    }

    @Override
    public void uploadPaginaNati(AbstractEntity annoBean) {
        appContext.getBean(UploadAnnoNato.class, ((AnnoWikiEntity) annoBean).nome).upload().isValido();
    }

    @Override
    public void uploadPaginaMorti(AbstractEntity annoBean) {
        appContext.getBean(UploadAnnoMorto.class, ((AnnoWikiEntity) annoBean).nome).upload().isValido();
    }

}// end of CrudModulo class

