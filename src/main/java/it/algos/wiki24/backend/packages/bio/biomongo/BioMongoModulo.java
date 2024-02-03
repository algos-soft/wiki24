package it.algos.wiki24.backend.packages.bio.biomongo;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.parametri.annomorto.*;
import it.algos.wiki24.backend.packages.parametri.annonato.*;
import it.algos.wiki24.backend.packages.parametri.attivita.*;
import it.algos.wiki24.backend.packages.parametri.cognome.*;
import it.algos.wiki24.backend.packages.parametri.giornomorto.*;
import it.algos.wiki24.backend.packages.parametri.giornonato.*;
import it.algos.wiki24.backend.packages.parametri.luogomorto.*;
import it.algos.wiki24.backend.packages.parametri.luogonato.*;
import it.algos.wiki24.backend.packages.parametri.nazionalita.*;
import it.algos.wiki24.backend.packages.parametri.nome.*;
import it.algos.wiki24.backend.packages.parametri.sesso.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

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
    ArrayService arrayService;

    @Inject
    ParNomeModulo parNomeModulo;

    @Inject
    ParCognomeModulo parCognomeModulo;

    @Inject
    ParSessoModulo parSessoModulo;

    @Inject
    ParLuogoNatoModulo parLuogoNatoModulo;

    @Inject
    ParGiornoNatoModulo parGiornoNatoModulo;

    @Inject
    ParAnnoNatoModulo parAnnoNatoModulo;

    @Inject
    ParLuogoMortoModulo parLuogoMortoModulo;

    @Inject
    ParGiornoMortoModulo parGiornoMortoModulo;

    @Inject
    ParAnnoMortoModulo parAnnoMortoModulo;

    @Inject
    ParAttivitaModulo parAttivitaModulo;

    @Inject
    ParNazionalitaModulo parNazionalitaModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public BioMongoModulo() {
        super(BioMongoEntity.class, BioMongoView.class, BioMongoList.class, BioMongoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.flagElabora = WPref.usaElaboraBioMongo;
        super.scheduledElabora = TaskElaboraBioMongo.TYPE_SCHEDULE;
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
        return Arrays.asList("wikiTitle", "nome", "cognome", "sesso", "luogoNato", "giornoNato", "annoNato", "luogoMorto", "giornoMorto", "annoMorto", "attivita", "nazionalita");
    }

    public List<Long> findOnlyPageId() {
        return mongoService.projectionLong(BioMongoEntity.class, FIELD_NAME_PAGE_ID);
    }

    public BioMongoEntity findByKey(final Object keyPropertyValue) {
        return (BioMongoEntity) super.findByKey(keyPropertyValue);
    }

    public BioMongoEntity findByWikiTitle(final String wikiTitle) {
        return (BioMongoEntity) super.findOneByProperty(FIELD_NAME_WIKI_TITLE, wikiTitle);
    }

    public List<BioMongoEntity> findAllByProperty(final String propertyName, final Object propertyValue) {
        return super.findAllBeanByProperty(propertyName, propertyValue)
                .stream()
                .map(bean -> (BioMongoEntity) bean)
                .collect(Collectors.toList());
    }

    public int countAllByGiornoNato(final String propertyValue) {
        return mongoService.count(queryByGiornoNato(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByGiornoNato(final String propertyValue) {
        return mongoService.find(queryByGiornoNato(propertyValue), BioMongoEntity.class);
    }

    public Query queryByGiornoNato(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ANNO_NATO_ORD, FIELD_NAME_ORDINAMENTO);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_NATO).is(propertyValue));
        query.with(sort);
        return query;
    }


    public int countAllByGiornoMorto(final String propertyValue) {
        return mongoService.count(queryByGiornoMorto(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByGiornoMorto(final String propertyValue) {
        return mongoService.find(queryByGiornoMorto(propertyValue), BioMongoEntity.class);
    }

    public Query queryByGiornoMorto(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ANNO_MORTO_ORD, FIELD_NAME_ORDINAMENTO);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_MORTO).is(propertyValue));
        query.with(sort);
        return query;
    }


    public int countAllByAnnoNato(final String propertyValue) {
        return mongoService.count(queryByAnnoNato(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByAnnoNato(final String propertyValue) {
        return mongoService.find(queryByAnnoNato(propertyValue), BioMongoEntity.class);
    }

    public Query queryByAnnoNato(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_GIORNO_NATO_ORD, FIELD_NAME_ORDINAMENTO);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_ANNO_NATO).is(propertyValue));
        query.with(sort);
        return query;
    }


    public int countAllByAnnoMorto(final String propertyValue) {
        return mongoService.count(queryByAnnoMorto(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByAnnoMorto(final String propertyValue) {
        return mongoService.find(queryByAnnoMorto(propertyValue), BioMongoEntity.class);
    }

    public Query queryByAnnoMorto(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_GIORNO_MORTO_ORD, FIELD_NAME_ORDINAMENTO);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_ANNO_MORTO).is(propertyValue));
        query.with(sort);
        return query;
    }

    public int countAllByAttivitaSingolare(final String propertyValue) {
        return mongoService.count(queryByAttivitaSingolare(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByAttivitaSingolare(final String propertyValue) {
        return mongoService.find(queryByAttivitaSingolare(propertyValue), BioMongoEntity.class);
    }

    public Query queryByAttivitaSingolare(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ATTIVITA);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).is(propertyValue));
        query.with(sort);
        return query;
    }

    public String elabora() {
        inizio = System.currentTimeMillis();

        elaboraService.elaboraAll();
        this.eliminaMongoCancellatiDaServer();

        super.fixElabora(inizio);
        return VUOTA;
    }

    public void elaboraDue() {
        inizio = System.currentTimeMillis();

        elaboraService.elaboraAll();
        this.elaboraParametri();

        super.fixElabora(inizio);
    }

    public void elaboraParametri() {
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);

        parNomeModulo.elabora(lista);
        parCognomeModulo.elabora(lista);
        parSessoModulo.elabora(lista);
        parLuogoNatoModulo.elabora(lista);
        parGiornoNatoModulo.elabora(lista);
        parAnnoNatoModulo.elabora(lista);
        parLuogoMortoModulo.elabora(lista);
        parGiornoMortoModulo.elabora(lista);
        parAnnoMortoModulo.elabora(lista);
        parAttivitaModulo.elabora(lista);
        parNazionalitaModulo.elabora(lista);
    }

    public void eliminaMongoCancellatiDaServer() {
        List<Long> listaServerIds = bioServerModulo.findOnlyPageId();
        List<Long> listaMongoIds = this.findOnlyPageId();
        List<Long> delta = null;
        BioMongoEntity mongoBean;

        if (listaMongoIds.size() > listaServerIds.size()) {
            delta = arrayService.deltaBinary(listaMongoIds, listaServerIds);
        }

        if (delta != null) {
            for (Long pageId : delta) {
                mongoBean = this.findByKey(pageId);
                delete(mongoBean);
            }
        }
    }

    @Override
    public void transfer(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);

        if (bioServerEntity != null) {
            bioServerModulo.creaForm(bioServerEntity, CrudOperation.update);
        }
    }

    @Override
    public AbstractEntity resetEntity(AbstractEntity crudEntityBean) {
        BioServerEntity bioServerEntity = getBioServer(crudEntityBean);
        BioMongoEntity bioMongoEntity = null;

        if (bioServerEntity != null) {
            bioMongoEntity = elaboraService.creaModificaBeanMongo(bioServerEntity);
            bioMongoEntity = (BioMongoEntity) insertSave(bioMongoEntity);
        }

        return bioMongoEntity;
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
