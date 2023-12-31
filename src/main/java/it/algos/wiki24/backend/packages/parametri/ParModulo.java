package it.algos.wiki24.backend.packages.parametri;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
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
     * Regola la listClazz associata a questo Modulo <br>
     * Regola la formClazz associata a questo Modulo <br>
     */
    public ParModulo(Class entityClazz, Class listClazz, Class formClazz) {
        super(entityClazz, listClazz, formClazz);
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

    public void elabora() {
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);
        elabora(lista);
    }

    public void elabora(List<BioServerEntity> lista) {
        inizio = System.currentTimeMillis();
        int k = 0;

        for (BioServerEntity bioServerBean : lista) {
            k++;
            elabora(bioServerBean);
        }

        super.fixElabora(inizio);
    }

    public void elabora(BioServerEntity bioServerBean) {
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
        elaborato = getElaborato(grezzo);
        parametroEntity = newEntity(pageId, wikiTitle, grezzo, elaborato);

        parametroEntity = fixParametri(parametroEntity, grezzo, elaborato);
        insertSave(parametroEntity);
    }

    public String getElaborato(String grezzo) {
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
    public void resetEntity(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);

        if (bioServerEntity != null) {
            elabora(bioServerEntity);
        }
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
