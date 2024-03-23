package it.algos.wiki24.backend.packages.parametri.nome;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.nomi.nomebio.*;
import it.algos.wiki24.backend.packages.parametri.*;
import org.springframework.stereotype.*;

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


    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParNomeModulo() {
        super(ParNomeEntity.class, ParNomeView.class, ParNomeList.class, ParNomeForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParNome;
        super.durataElabora = WPref.elaboraParNomeTime;

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

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param valido (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParNomeEntity newEntity(long pageId, String wikiTitle, String grezzo, String valido) {
        ParNomeEntity newEntityBean = ParNomeEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .valido(textService.isValid(valido) ? valido : null)
                .build();

        return (ParNomeEntity) fixKey(newEntityBean);
    }

    @Override
    public String getElaborato(String wikiTitle, String grezzo) {
        return elaboraService.fixNome(grezzo);
    }

    //    public AbstractEntity fixParametri(AbstractEntity parametroEntity, String grezzo, String elaborato) {
    //        ((ParNomeEntity) parametroEntity).grezzoVuoto = textService.isEmpty(grezzo);
    //        ((ParNomeEntity) parametroEntity).elaboratoVuoto = textService.isEmpty(elaborato);
    //        ((ParNomeEntity) parametroEntity).uguale = elaborato != null ? elaborato.equals(grezzo) : grezzo != null ? true : false;
    //
    //        return parametroEntity;
    //    }

    //        public String elabora() {
    //        inizio = System.currentTimeMillis();
    //        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);
    //
    //        for (BioServerEntity bioServerBean : lista) {
    //            elabora(bioServerBean);
    //        }
    //
    //        super.fixElabora(inizio);
    //    }

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
