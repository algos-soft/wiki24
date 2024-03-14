package it.algos.wiki24.backend.packages.bio.biomongo;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.packages.crono.mese.*;
import it.algos.vbase.backend.packages.crono.secolo.*;
import it.algos.vbase.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.FIELD_NAME_NOME;
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
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
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
    MeseModulo meseModulo;

    @Inject
    SecoloModulo secoloModulo;

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

    @Inject
    AttSingolareModulo attSingolareModulo;

    @Inject
    AttPluraleModulo attPluraleModulo;

    @Inject
    NazSingolareModulo nazSingolareModulo;

    @Inject
    NazPluraleModulo nazPluraleModulo;


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


    public int countByGiornoNatoAndSecolo(final String giorno, String secoloTxt) {
        return mongoService.count(queryByGiornoNatoAndSecolo(giorno, secoloTxt), BioMongoEntity.class);
    }


    public Query queryByGiornoNatoAndSecolo(final String giorno, String secoloTxt) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ANNO_NATO_ORD, FIELD_NAME_ORDINAMENTO);
        SecoloEntity secoloBean;
        int inizio = 0;
        int fine = 0;

        if (textService.isEmpty(giorno) || textService.isEmpty(secoloTxt)) {
            return null;
        }
        secoloBean = secoloModulo.findByKey(secoloTxt);
        if (secoloBean != null) {
            inizio = secoloBean.inizio + DELTA_ANNI;
            fine = secoloBean.fine + DELTA_ANNI;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_NATO).is(giorno));
        query.addCriteria(Criteria.where(FIELD_NAME_ANNO_NATO_ORD).gte(inizio).lte(fine));
        query.with(sort);
        return query;
    }


    public int countByGiornoMortoAndSecolo(final String giorno, String secoloTxt) {
        return mongoService.count(queryByGiornoMortoAndSecolo(giorno, secoloTxt), BioMongoEntity.class);
    }


    public Query queryByGiornoMortoAndSecolo(final String giorno, String secoloTxt) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ANNO_MORTO_ORD, FIELD_NAME_ORDINAMENTO);
        SecoloEntity secoloBean;
        int inizio = 0;
        int fine = 0;

        if (textService.isEmpty(giorno) || textService.isEmpty(secoloTxt)) {
            return null;
        }
        secoloBean = secoloModulo.findByKey(secoloTxt);
        if (secoloBean != null) {
            inizio = secoloBean.inizio + DELTA_ANNI;
            fine = secoloBean.fine + DELTA_ANNI;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_MORTO).is(giorno));
        query.addCriteria(Criteria.where(FIELD_NAME_ANNO_MORTO_ORD).gte(inizio).lte(fine));
        query.with(sort);
        return query;
    }


    public int countByAnnoNatoAndMese(final String anno, String meseTxt) {
        return mongoService.count(queryByAnnoNatoAndMese(anno, meseTxt), BioMongoEntity.class);
    }


    public Query queryByAnnoNatoAndMese(final String anno, final String meseTxt) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_GIORNO_NATO_ORD, FIELD_NAME_ORDINAMENTO);
        MeseEntity meseBean;
        int primo = 0;
        int ultimo = 0;

        if (textService.isEmpty(anno) || textService.isEmpty(meseTxt)) {
            return null;
        }
        meseBean = meseModulo.findByKey(textService.primaMinuscola(meseTxt));
        if (meseBean != null) {
            primo = meseBean.primo;
            ultimo = meseBean.ultimo;
            query.addCriteria(Criteria.where(FIELD_NAME_ANNO_NATO).is(anno));
            query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_NATO_ORD).gte(primo).lte(ultimo));
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_ANNO_NATO).is(anno));
            query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_NATO).is(VUOTA));
        }

        query.with(sort);
        return query;
    }


    public int countByAnnoMortoAndMese(final String anno, String meseTxt) {
        return mongoService.count(queryByAnnoMortoAndMese(anno, meseTxt), BioMongoEntity.class);
    }


    public Query queryByAnnoMortoAndMese(final String anno, final String meseTxt) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_GIORNO_MORTO_ORD, FIELD_NAME_ORDINAMENTO);
        MeseEntity meseBean;
        int primo = 0;
        int ultimo = 0;

        if (textService.isEmpty(anno) || textService.isEmpty(meseTxt)) {
            return null;
        }
        meseBean = meseModulo.findByKey(textService.primaMinuscola(meseTxt));
        if (meseBean != null) {
            primo = meseBean.primo;
            ultimo = meseBean.ultimo;
            query.addCriteria(Criteria.where(FIELD_NAME_ANNO_MORTO).is(anno));
            query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_MORTO_ORD).gte(primo).lte(ultimo));
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_ANNO_MORTO).is(anno));
            query.addCriteria(Criteria.where(FIELD_NAME_GIORNO_MORTO).is(VUOTA));
        }

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
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_NAZIONALITA, FIELD_NAME_COGNOME);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).is(propertyValue));
        query.with(sort);
        return query;
    }

    public int countAllByAttivitaPlurale(final String propertyValue) {
        int numBio = 0;
        AttPluraleEntity attivitaPluraleBean;
        List<String> attivitaSingolari = null;

        attivitaPluraleBean = attPluraleModulo.findByKey(propertyValue);
        if (attivitaPluraleBean != null) {
            attivitaSingolari = attivitaPluraleBean.getTxtSingolari();
        }

        if (attivitaSingolari != null && attivitaSingolari.size() > 0) {
            for (String attivita : attivitaSingolari) {
                numBio += countAllByAttivitaSingolare(attivita);
            }
        }

        return numBio;
    }


    public List<BioMongoEntity> findAllByAttivitaPlurale(final String propertyValue) {
        List<BioMongoEntity> lista = new ArrayList<>();
        AttPluraleEntity attivitaPluraleBean;
        List<String> attivitaSingolari = null;

        attivitaPluraleBean = attPluraleModulo.findByKey(propertyValue);
        if (attivitaPluraleBean != null) {
            attivitaSingolari = attivitaPluraleBean.getTxtSingolari();
        }

        if (attivitaSingolari != null && attivitaSingolari.size() > 0) {
            for (String attivita : attivitaSingolari) {
                lista.addAll(findAllByAttivitaSingolare(attivita));
            }
        }

        return lista;
    }


    public int countAllByNazionalitaSingolare(final String propertyValue) {
        return mongoService.count(queryByNazionalitaSingolare(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByNazionalitaSingolare(final String propertyValue) {
        return mongoService.find(queryByNazionalitaSingolare(propertyValue), BioMongoEntity.class);
    }

    public Query queryByNazionalitaSingolare(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ATTIVITA, FIELD_NAME_COGNOME);

        if (textService.isEmpty(propertyValue)) {
            return null;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_NAZIONALITA).is(propertyValue));
        query.with(sort);
        return query;
    }


    public int countAllByNazionalitaPlurale(final String propertyValue) {
        int numBio = 0;
        NazPluraleEntity nazionalitaPluraleBean;
        List<String> nazionalitaSingolari = null;

        nazionalitaPluraleBean = nazPluraleModulo.findByKey(propertyValue);
        if (nazionalitaPluraleBean != null) {
            nazionalitaSingolari = nazionalitaPluraleBean.txtSingolari;
        }

        if (nazionalitaSingolari != null && nazionalitaSingolari.size() > 0) {
            for (String nazionalita : nazionalitaSingolari) {
                numBio += countAllByNazionalitaSingolare(nazionalita);
            }
        }

        return numBio;
    }


    public List<BioMongoEntity> findAllByNazionalitaPlurale(final String propertyValue) {
        List<BioMongoEntity> lista = new ArrayList<>();
        NazPluraleEntity nazionalitaPluraleBean;
        List<String> nazionalitaSingolari = null;

        nazionalitaPluraleBean = nazPluraleModulo.findByKey(propertyValue);
        if (nazionalitaPluraleBean != null) {
            nazionalitaSingolari = nazionalitaPluraleBean.txtSingolari;
        }

        if (nazionalitaSingolari != null && nazionalitaSingolari.size() > 0) {
            for (String nazionalita : nazionalitaSingolari) {
                lista.addAll(findAllByNazionalitaSingolare(nazionalita));
            }
        }

        return lista;
    }

    public int countByAttivitaAndNazionalita(String attivitaPlurale, String nazionalitaPlurale) {
        return mongoService.count(queryByAttivitaAndNazionalita(attivitaPlurale, nazionalitaPlurale), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByAttivitaAndNazionalita(String attivitaPlurale, String nazionalitaPlurale) {
        return mongoService.find(queryByAttivitaAndNazionalita(attivitaPlurale, nazionalitaPlurale), BioMongoEntity.class);
    }

    public Query queryByAttivitaAndNazionalita(final String attivitaPlurale, final String nazionalitaPlurale) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_NAZIONALITA, FIELD_NAME_COGNOME);
        List<String> listaSingoleAttività;
        List<String> listaSingoleNazionalità;

        if (textService.isEmpty(attivitaPlurale) || textService.isEmpty(nazionalitaPlurale)) {
            return query;
        }
        listaSingoleAttività = this.findAllAttivitaSingole(attivitaPlurale);
        if (listaSingoleAttività.isEmpty()) {
            return query;
        }

        listaSingoleNazionalità = this.findAllNazionalitaSingole(nazionalitaPlurale);

        if (listaSingoleNazionalità != null&&listaSingoleNazionalità.size()>0) {
            query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).in(listaSingoleAttività));
            query.addCriteria(Criteria.where(FIELD_NAME_NAZIONALITA).in(listaSingoleNazionalità));
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).in(listaSingoleAttività));
            query.addCriteria(Criteria.where(FIELD_NAME_NAZIONALITA).is(VUOTA));
        }

        query.with(sort);
        return query;
    }

    public int countByNazionalitaAndAttivita(String nazionalitaPlurale, String attivitaPlurale) {
        return mongoService.count(queryByNazionalitaAndAttivita(nazionalitaPlurale, attivitaPlurale), BioMongoEntity.class);
    }

    /**
     * Query per nazionalità (lista) e attività (sottopagina) <br>
     *
     * @param nazionalitaPlurale (obbligatorio)
     * @param attivitaPlurale    (facoltativa)
     *
     * @return query per la ricerca
     */
    public Query queryByNazionalitaAndAttivita(final String nazionalitaPlurale, final String attivitaPlurale) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ATTIVITA, FIELD_NAME_COGNOME);
        List<String> listaSingoleNazionalità;
        List<String> listaSingoleAttività;

        if (textService.isEmpty(nazionalitaPlurale) || textService.isEmpty(attivitaPlurale)) {
            return query;
        }

        listaSingoleNazionalità = this.findAllNazionalitaSingole(nazionalitaPlurale);
        if (listaSingoleNazionalità.isEmpty()) {
            return query;
        }

        listaSingoleAttività = this.findAllAttivitaSingole(attivitaPlurale);
        if (listaSingoleAttività != null && listaSingoleAttività.size() > 0) {
            query.addCriteria(Criteria.where(FIELD_NAME_NAZIONALITA).in(listaSingoleNazionalità));
            query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).in(listaSingoleAttività));
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_NAZIONALITA).in(listaSingoleNazionalità));
            query.addCriteria(Criteria.where(FIELD_NAME_ATTIVITA).is(VUOTA));
        }

        query.with(sort);
        return query;
    }

    /**
     * Recupera la lista dei nomi delle attività singole <br>
     * L'attività in ingresso può essere singola o plurale <br>
     *
     * @param attività (obbligatorio)
     *
     * @return lista (String) delle attività singole
     */
    public List<String> findAllAttivitaSingole(final String attività) {
        List<String> lista = new ArrayList<>();
        AttPluraleEntity attPluraleEntity;
        AttSingolareEntity attSingolareEntity;

        attPluraleEntity = attPluraleModulo.findByKey(attività);
        if (attPluraleEntity != null) {
            return attPluraleEntity.txtSingolari;
        }

        attSingolareEntity = attSingolareModulo.findByKey(attività);
        if (attSingolareEntity != null) {
            attPluraleEntity = attPluraleModulo.findByKey(attSingolareEntity.plurale);
            if (attPluraleEntity != null) {
                return attPluraleEntity.txtSingolari;
            }
        }

        return lista;
    }


    /**
     * Recupera la lista dei nomi delle nazionalità singole <br>
     * La nazionalità in ingresso può essere singola o plurale <br>
     *
     * @param nazionalità (obbligatorio)
     *
     * @return lista (String) delle attività singole
     */
    public List<String> findAllNazionalitaSingole(final String nazionalità) {
        List<String> lista = new ArrayList<>();
        NazPluraleEntity nazPluraleEntity;
        NazSingolareEntity nazSingolareEntity;

        nazPluraleEntity = nazPluraleModulo.findByKey(nazionalità);
        if (nazPluraleEntity != null) {
            return nazPluraleEntity.txtSingolari;
        }

        nazSingolareEntity = nazSingolareModulo.findByKey(nazionalità);
        if (nazSingolareEntity != null) {
            nazPluraleEntity = nazPluraleModulo.findByKey(nazSingolareEntity.plurale);
            if (nazPluraleEntity != null) {
                return nazPluraleEntity.txtSingolari;
            }
        }

        return lista;
    }


    public int countAllByNome(final String propertyValue) {
        return mongoService.count(queryByNome(propertyValue), BioMongoEntity.class);
    }

    public List<BioMongoEntity> findAllByNome(final String propertyValue) {
        return mongoService.find(queryByNome(propertyValue), BioMongoEntity.class);
    }

    public Query queryByNome(final String propertyValue) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_NOME);

        if (textService.isEmpty(propertyValue)) {
            return query;
        }

        query.addCriteria(Criteria.where(FIELD_NAME_NOME).is(propertyValue));
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

//        parNomeModulo.elabora(lista);
//        parCognomeModulo.elabora(lista);
//        parSessoModulo.elabora(lista);
        parLuogoNatoModulo.elabora(lista);
//        parGiornoNatoModulo.elabora(lista);
//        parAnnoNatoModulo.elabora(lista);
//        parLuogoMortoModulo.elabora(lista);
//        parGiornoMortoModulo.elabora(lista);
//        parAnnoMortoModulo.elabora(lista);
//        parAttivitaModulo.elabora(lista);
//        parNazionalitaModulo.elabora(lista);
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
