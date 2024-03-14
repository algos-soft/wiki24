package it.algos.wiki24.backend.packages.parametri;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 30-Dec-2023
 * Time: 19:06
 */
public abstract class ParModulo extends WikiModulo {

    @Inject
    protected ElaboraService elaboraService;

    @Inject
    protected BioServerModulo bioServerModulo;


    protected String keyMapName;

    /**
     * Regola la modelClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo <br>
     * Regola la formClazz associata a questo Modulo <br>
     */
    public ParModulo(Class entityClazz, Class listClazz, Class formClazz) {
        this(entityClazz, null, listClazz, formClazz);
    }

    /**
     * Regola la modelClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo <br>
     * Regola la formClazz associata a questo Modulo <br>
     */
    public ParModulo(Class entityClazz, Class viewClass, Class listClazz, Class formClazz) {
        super(entityClazz, viewClass, listClazz, formClazz);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Regola le property di una ModelClazz <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     */
    @Override
    public List<String> getPropertyNames() {
        return Arrays.asList("pageId", "wikiTitle", "grezzo", "elaborato", "grezzoVuoto", "elaboratoVuoto", "uguale");
    }

    public AbstractEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        return null;
    }

    public String elabora() {
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);
        elabora(lista);
        return VUOTA;
    }

    public void elabora(List<BioServerEntity> lista) {
        inizio = System.currentTimeMillis();

        for (BioServerEntity bioServerBean : lista) {
            elabora(bioServerBean);
        }

        super.fixElabora(inizio);
    }

    public AbstractEntity elabora(BioServerEntity bioServerBean) {
        AbstractEntity parametroEntity;
        Map<String, String> mappa;
        long pageId;
        String wikiTitle;
        String grezzo;
        String elaborato;

        mappa = elaboraService.estraeMappa(bioServerBean);
        pageId = bioServerBean.getPageId();
        wikiTitle = bioServerBean.getWikiTitle();
        grezzo = mappa.get(keyMapName);
        elaborato = getElaborato(wikiTitle, grezzo);
        parametroEntity = newEntity(pageId, wikiTitle, grezzo, elaborato);

        parametroEntity = fixParametri(parametroEntity, grezzo, elaborato);
        return insertSave(parametroEntity);
    }

    public String getElaborato(String wikiTitle, String grezzo) {
        return VUOTA;
    }

    public AbstractEntity fixParametri(AbstractEntity parametroEntity, String grezzo, String elaborato) {
        boolean grezzoVuoto = textService.isEmpty(grezzo);
        boolean elaboratoVuoto = textService.isEmpty(elaborato);
        boolean uguale;

        if (grezzo == null && elaborato == null) {
            uguale = true;
        }
        else {
            uguale = elaborato.equals(grezzo);
        }

        reflectionService.setPropertyValue(parametroEntity, FIELD_NAME_GREZZO_VUOTO, grezzoVuoto);
        reflectionService.setPropertyValue(parametroEntity, FIELD_NAME_ELABORATO_VUOTO, elaboratoVuoto);
        reflectionService.setPropertyValue(parametroEntity, FIELD_NAME_UGUALE, uguale);

        return parametroEntity;
    }


    @Override
    public AbstractEntity resetEntity(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);
        BioMongoEntity bioMongoEntity = null;

        if (bioServerEntity != null) {
            bioMongoEntity = (BioMongoEntity) elabora(bioServerEntity);
        }

        return bioMongoEntity;
    }

    @Override
    public void transfer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);

        if (bioServerEntity != null) {
            bioServerModulo.creaForm(bioServerEntity, CrudOperation.update);
        }

        bioServerEntity = getBioServer(crudEntityBean);
        elabora(bioServerEntity);
    }

    public BioServerEntity getBioServer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = null;
        long pageId = 0;

        if (crudEntityBean != null) {
            pageId = (long) reflectionService.getPropertyValue(crudEntityBean, FIELD_NAME_PAGE_ID);
        }
        if (pageId > 0) {
            bioServerEntity = bioServerModulo.findByKey(pageId);
        }

        return bioServerEntity;
    }

}// end of CrudModulo class
