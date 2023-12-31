package it.algos.wiki24.backend.packages.parnome;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.packages.par.*;
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
 * Date: Sat, 30-Dec-2023
 * Time: 19:06
 */
@Service
public class ParNomeModulo extends ParModulo {

    @Inject
    ElaboraService elaboraService;

    @Inject
    BioServerModulo bioServerModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParNomeModulo() {
        super(ParNomeEntity.class, ParNomeList.class, ParNomeForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParNome;
        super.durataElabora = WPref.elaboraParNomeTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_NOME;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParNomeEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    public ParNomeEntity newEntity(long pageId, String wikiTitle, String grezzo) {
        return newEntity(pageId, wikiTitle, grezzo, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParNomeEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParNomeEntity newEntityBean = ParNomeEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParNomeEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String grezzo) {
        return elaboraService.fixNome(grezzo);
    }

    public AbstractEntity fixParametri(AbstractEntity parametroEntity, String grezzo, String elaborato) {
        ((ParNomeEntity) parametroEntity).grezzoVuoto = textService.isEmpty(grezzo);
        ((ParNomeEntity) parametroEntity).elaboratoVuoto = textService.isEmpty(elaborato);
        ((ParNomeEntity) parametroEntity).uguale = elaborato != null ? elaborato.equals(grezzo) : grezzo != null ? true : false;

        return parametroEntity;
    }


    //    public void elabora() {
//        inizio = System.currentTimeMillis();
//        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);
//
//        for (BioServerEntity bioServerBean : lista) {
//            elabora(bioServerBean);
//        }
//
//        super.fixElabora(inizio);
//    }
//
//
//    public void elabora(BioServerEntity bioServerBean) {
//        ParNomeEntity parametroNomeEntity;
//        Map<String, String> mappa;
//        long pageId;
//        String wikiTitle;
//        String grezzo;
//        String elaborato;
//
//        mappa = elaboraService.estraeMappa(bioServerBean);
//        pageId = bioServerBean.getPageId();
//        wikiTitle = bioServerBean.getWikiTitle();
//        grezzo = mappa.get(KEY_MAPPA_NOME);
//        elaborato = elaboraService.fixNome(grezzo);
//        parametroNomeEntity = newEntity(pageId, wikiTitle, grezzo, elaborato);
//
//        parametroNomeEntity.grezzoVuoto = textService.isEmpty(grezzo);
//        parametroNomeEntity.elaboratoVuoto = textService.isEmpty(elaborato);
//        parametroNomeEntity.uguale = elaborato != null ? elaborato.equals(grezzo) : grezzo != null ? true : false;
//
//        insertSave(parametroNomeEntity);
//    }
//
//
//    @Override
//    public void transfer(AbstractEntity crudEntityBean) {
//        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);
//
//        if (bioServerEntity != null) {
//            bioServerModulo.creaForm(bioServerEntity, CrudOperation.update);
//        }
//
//        bioServerEntity = getBioServer(crudEntityBean);
//        elabora(bioServerEntity);
//    }
//
//    @Override
//    public void resetEntity(AbstractEntity crudEntityBean) {
//        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);
//
//        if (bioServerEntity != null) {
//            elabora(bioServerEntity);
//        }
//    }
//
//    public BioServerEntity getBioServer(AbstractEntity crudEntityBean) {
//        BioServerEntity bioServerEntity = null;
//        long pageId = 0;
//
//        if (crudEntityBean != null && crudEntityBean instanceof ParNomeEntity parNomeEntity) {
//            pageId = parNomeEntity.pageId;
//        }
//        if (pageId > 0) {
//            bioServerEntity = bioServerModulo.findByKey(pageId);
//        }
//
//        return bioServerEntity;
//    }

}// end of CrudModulo class
