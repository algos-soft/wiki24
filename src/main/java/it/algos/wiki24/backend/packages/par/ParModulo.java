package it.algos.wiki24.backend.packages.par;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.packages.parcognome.*;
import it.algos.wiki24.backend.packages.parnome.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

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

        for (BioServerEntity bioServerBean : lista) {
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

        if (crudEntityBean != null && crudEntityBean instanceof ParCognomeEntity parCognomeEntity) {
            pageId = parCognomeEntity.pageId;
        }
        if (pageId > 0) {
            bioServerEntity = bioServerModulo.findByKey(pageId);
        }

        return bioServerEntity;
    }

}// end of CrudModulo class
