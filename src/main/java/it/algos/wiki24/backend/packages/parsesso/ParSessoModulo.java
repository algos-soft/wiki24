package it.algos.wiki24.backend.packages.parsesso;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 27-Dec-2023
 * Time: 07:43
 */
@Service
public class ParSessoModulo extends WikiModulo {

    @Inject
    ElaboraService elaboraService;

    @Inject
    BioServerModulo bioServerModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParSessoModulo() {
        super(ParSessoEntity.class, ParSessoList.class, ParSessoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParSesso;
        super.durataElabora = WPref.elaboraParSessoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParSessoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    public ParSessoEntity newEntity(long pageId, String wikiTitle, String grezzo) {
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
    public ParSessoEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParSessoEntity newEntityBean = ParSessoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParSessoEntity) fixKey(newEntityBean);
    }


    public void elabora() {
        inizio = System.currentTimeMillis();
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);

        for (BioServerEntity bioServerBean : lista) {
            elabora(bioServerBean);
        }

        super.fixElabora(inizio);
    }

    public void elabora(BioServerEntity bioServerBean) {
        ParSessoEntity parametroSessoEntity;
        Map<String, String> mappa;
        long pageId;
        String wikiTitle;
        String grezzo;
        String elaborato;

        mappa = elaboraService.estraeMappa(bioServerBean);
        pageId = bioServerBean.getPageId();
        wikiTitle = bioServerBean.getWikiTitle();
        grezzo = mappa.get(KEY_MAPPA_SESSO);
        elaborato = elaboraService.fixNome(grezzo);
        parametroSessoEntity = newEntity(pageId, wikiTitle, grezzo, elaborato);

        insertSave(parametroSessoEntity);
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

    @Override
    public void resetEntity(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);

        if (bioServerEntity != null) {
           elabora(bioServerEntity);
        }
    }
    public BioServerEntity getBioServer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = null;
        long pageId = 0;

        if (crudEntityBean != null && crudEntityBean instanceof ParSessoEntity parSessoEntity) {
            pageId = parSessoEntity.pageId;
        }
        if (pageId > 0) {
            bioServerEntity = bioServerModulo.findByKey(pageId);
        }

        return bioServerEntity;
    }

}// end of CrudModulo class
