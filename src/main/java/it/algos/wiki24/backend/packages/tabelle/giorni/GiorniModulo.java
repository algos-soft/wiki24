package it.algos.wiki24.backend.packages.tabelle.giorni;

import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

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
public class GiorniModulo extends WikiModulo {

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
    public GiorniModulo() {
        super(GiorniEntity.class, GiorniList.class, GiorniForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraGiorni;
        super.durataElabora = WPref.elaboraGiorniTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
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
        return Arrays.asList("ordine", "nome","bioNati", "pageNati", "bioMorti", "pageMorti");
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public GiorniEntity newEntity() {
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
    public GiorniEntity newEntity(final int ordine, final String nome, final MeseEntity mese, String pageNati, String pageMorti) {
        GiorniEntity newEntityBean = GiorniEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .mese(mese)
                .bioNati(0)
                .bioMorti(0)
                .pageNati(textService.isValid(pageNati) ? pageNati : null)
                .pageMorti(textService.isValid(pageMorti) ? pageMorti : null)
                //                .esistePaginaNati(false)
                //                .esistePaginaMorti(false)
                //                .natiOk(false)
                //                .mortiOk(false)
                .build();

        return (GiorniEntity) fixKey(newEntityBean);
    }

    @Override
    public List<GiorniEntity> findAll() {
        return super.findAll();
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        List<GiornoEntity> giorniBase;
        GiorniEntity newBean;
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

    public void elabora() {
        inizio = System.currentTimeMillis();

        for (GiorniEntity giornoBean : findAll()) {
            giornoBean.bioNati = bioMongoModulo.countAllByGiornoNato(giornoBean.nome);
            giornoBean.bioMorti = bioMongoModulo.countAllByGiornoMorto(giornoBean.nome);
            save(giornoBean);
        }

        super.fixElabora(inizio);
    }


}// end of CrudModulo class
