package it.algos.wiki24.backend.packages.bio.biomongo;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.parametri.cognome.*;
import it.algos.wiki24.backend.packages.parametri.nome.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 25-Dec-2023
 * Time: 21:21
 */
@Service
public class BioMongoModulo extends WikiModulo {

    @Inject
    ElaboraService elaboraService;

    @Inject
    BioServerModulo bioServerModulo;
    @Inject
    ParNomeModulo parNomeModulo;
    @Inject
    ParCognomeModulo parCognomeModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public BioMongoModulo() {
        super(BioMongoEntity.class, BioMongoList.class, BioMongoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraBioMongo;
        super.durataElabora = WPref.elaboraBioMongoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public BioMongoEntity newEntity() {
        return newEntity(0, VUOTA);
    }


    public BioMongoEntity newEntity(BioServerEntity bioServerBean) {
        if (bioServerBean != null) {
            return newEntity(bioServerBean.pageId, bioServerBean.wikiTitle);
        }
        else {
            return newEntity(0, VUOTA);
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public BioMongoEntity newEntity(long pageId, String wikiTitle) {
        BioMongoEntity newEntityBean = BioMongoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .build();

        return (BioMongoEntity) fixKey(newEntityBean);
    }


    /**
     * Regola le property visibili in una lista CrudList <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    @Override
    public List<String> getListPropertyNames() {
        return Arrays.asList("wikiTitle", "nome", "cognome", "sesso");
    }

    public List<Long> findOnlyPageId() {
        return mongoService.projectionLong(BioServerEntity.class, FIELD_NAME_PAGE_ID);
    }

    public BioMongoEntity findByKey(final Object keyPropertyValue) {
        return (BioMongoEntity) super.findByKey(keyPropertyValue);
    }

    public BioMongoEntity findByWikiTitle(final String wikiTitle) {
        return (BioMongoEntity) super.findOneByProperty(FIELD_NAME_WIKI_TITLE, wikiTitle);
    }

    public void elabora() {
        inizio = System.currentTimeMillis();

        elaboraService.elaboraAll();

        elaboraParametri();

        super.fixElabora(inizio);
    }

    public void elaboraParametri() {
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);

        parNomeModulo.elabora(lista);
        parCognomeModulo.elabora(lista);
    }

    @Override
    public void transfer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);

        if (bioServerEntity != null) {
            bioServerModulo.creaForm(bioServerEntity, CrudOperation.update);
        }
    }

    public BioServerEntity getBioServer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = null;
        long pageId = 0;

        if (crudEntityBean != null && crudEntityBean instanceof BioMongoEntity bioMongoEntity) {
            pageId = bioMongoEntity.pageId;
        }
        if (pageId > 0) {
            bioServerEntity = bioServerModulo.findByKey(pageId);
        }

        return bioServerEntity;
    }

}// end of CrudModulo class