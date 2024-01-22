package it.algos.wiki24.backend.packages.tabelle.giorni;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 17:08
 */
@Service
public class GiornoWikiModulo extends WikiModulo {

    @Inject
    GiornoModulo giornoModulo;

    @Inject
    WikiUtilityService wikiUtilityService;

    @Inject
    BioMongoModulo bioMongoModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public GiornoWikiModulo() {
        super(GiornoWikiEntity.class, GiornoWikiList.class, GiornoWikiForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.scheduledElabora = TypeSchedule.dieciGiovedi;
        super.lastElabora = WPref.lastElaboraGiorni;
        super.durataElabora = WPref.elaboraGiorniTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.scheduledUpload = TypeSchedule.dieciVenerdi;
        super.lastUpload = WPref.lastUploadGiorni;
        super.durataUpload = WPref.uploadGiorniTime;
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
    public GiornoWikiEntity newEntity() {
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
     * @param mese      di appartenenza
     * @param pageNati  anchorLink
     * @param pageMorti anchorLink
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public GiornoWikiEntity newEntity(final int ordine, final String nome, final MeseEntity mese, String pageNati, String pageMorti) {
        GiornoWikiEntity newEntityBean = GiornoWikiEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .mese(mese)
                .bioNati(0)
                .bioMorti(0)
                .pageNati(textService.isValid(pageNati) ? pageNati : null)
                .pageMorti(textService.isValid(pageMorti) ? pageMorti : null)
                .build();

        return (GiornoWikiEntity) fixKey(newEntityBean);
    }

    @Override
    public List<GiornoWikiEntity> findAll() {
        return super.findAll();
    }

    @Override
    public GiornoWikiEntity findByKey(final Object keyPropertyValue) {
        return (GiornoWikiEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        List<GiornoEntity> giorniBase;
        GiornoWikiEntity newBean;
        int ordine;
        String nome;
        MeseEntity mese;
        String pageNati;
        String pageMorti;
        giorniBase = giornoModulo.findAll();
        if (giorniBase == null || giorniBase.size() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Giorno'")));
            return null;
        }

        for (GiornoEntity giorno : giorniBase) {
            nome = giorno.nome;
            ordine = giorno.ordine;
            mese = giorno.mese;
            pageNati = wikiUtilityService.wikiTitleNatiGiorno(nome);
            pageMorti = wikiUtilityService.wikiTitleMortiGiorno(nome);
            newBean = newEntity(ordine, nome, mese, pageNati, pageMorti);
            insertSave(newBean);
        }

        return null;
    }

    @Override
    public String elabora() {
        inizio = System.currentTimeMillis();

        for (GiornoWikiEntity giornoBean : findAll()) {
            giornoBean.bioNati = bioMongoModulo.countAllByGiornoNato(giornoBean.nome);
            giornoBean.bioMorti = bioMongoModulo.countAllByGiornoMorto(giornoBean.nome);
            save(giornoBean);
        }

        return super.fixElabora(inizio);
    }

    @Override
    public String uploadAll() {
        inizio = System.currentTimeMillis();

        for (GiornoWikiEntity giornoBean : findAll()) {
            uploadPaginaNati(giornoBean);
            uploadPaginaMorti(giornoBean);
        }

        return super.fixUpload(inizio);
    }

    @Override
    public void wikiView(AbstractEntity giornoBean) {
        wikiApiService.openWikiPage(((GiornoWikiEntity) giornoBean).nome);
    }

    @Override
    public void testPaginaNati(AbstractEntity giornoBean) {
        uploadService.giornoNatoTest((GiornoWikiEntity) giornoBean);
    }

    @Override
    public void testPaginaMorti(AbstractEntity giornoBean) {
        uploadService.giornoMortoTest((GiornoWikiEntity) giornoBean);
    }

    @Override
    public void uploadPaginaNati(AbstractEntity giornoBean) {
        uploadService.giornoNato((GiornoWikiEntity) giornoBean);
    }

    @Override
    public void uploadPaginaMorti(AbstractEntity giornoBean) {
        uploadService.giornoMorto((GiornoWikiEntity) giornoBean);
    }

}// end of CrudModulo class
