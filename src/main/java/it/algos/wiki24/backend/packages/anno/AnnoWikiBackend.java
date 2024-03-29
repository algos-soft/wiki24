package it.algos.wiki24.backend.packages.anno;

import com.mongodb.*;
import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.bson.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 08-Jul-2022
 * Time: 06:34
 */
@Service
public class AnnoWikiBackend extends WikiBackend {


    public AnnoWikiBackend() {
        super(AnnoWiki.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.resetAnni;
        super.lastElaborazione = WPref.elaboraAnni;
        super.durataElaborazione = WPref.elaboraAnniTime;
        super.lastUpload = WPref.uploadAnni;
        super.durataUpload = WPref.uploadAnniTime;
        super.nextUpload = WPref.uploadAnniPrevisto;
        super.lastStatistica = WPref.statisticaAnni;
        super.durataStatistica = WPref.statisticaAnniTime;

        this.sortOrder = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
        this.unitaMisuraElaborazione = AETypeTime.minuti;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AnnoWiki newEntity() {
        return newEntity(VUOTA);
    }


    public AnnoWiki newEntity(final Document doc) {
        AnnoWiki annoWiki = new AnnoWiki();
        Secolo secolo;

        DBRef dbRef = (DBRef) doc.get("secolo");
        secolo = secoloBackend.findDocumentById((String) dbRef.getId());

        annoWiki.ordine = doc.getInteger("ordine");
        annoWiki.nome = doc.getString("nome");
        annoWiki.secolo = secolo;
        annoWiki.bioNati = doc.getInteger("bioNati");
        annoWiki.bioMorti = doc.getInteger("bioMorti");
        annoWiki.pageNati = doc.getString("pageNati");
        annoWiki.pageMorti = doc.getString("pageMorti");
        annoWiki.esistePaginaNati = doc.getBoolean("esistePaginaNati");
        annoWiki.esistePaginaMorti = doc.getBoolean("esistePaginaMorti");
        annoWiki.natiOk = doc.getBoolean("natiOk");
        annoWiki.mortiOk = doc.getBoolean("mortiOk");
        annoWiki.ordineSecolo = doc.getInteger("ordineSecolo");

        return annoWiki;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param keyPropertyValue proveniente da vaad24
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AnnoWiki newEntity(final String keyPropertyValue) {
        AnnoWiki newEntityBean = AnnoWiki.builderAnnoWiki().build();
        Anno annoBase = annoBackend.findByKey(keyPropertyValue);
        if (annoBase == null) {
            logService.error(new WrapLog().message(String.format("Manca l'anno base di riferimento dal nome %s", keyPropertyValue)));
            return null;
        }

        beanService.copiaAncheID(annoBase, newEntityBean);
        newEntityBean.pageNati = wikiUtility.wikiTitleNatiAnno(annoBase.nome);
        newEntityBean.pageMorti = wikiUtility.wikiTitleMortiAnno(annoBase.nome);
        newEntityBean.ordine = annoBase.ordine * 100;
        newEntityBean.ordineSecolo = annoBase.secolo.ordine;

        return newEntityBean;
    }


    @Override
    public AnnoWiki findById(final String keyID) {
        return (AnnoWiki) super.findById(keyID);
    }

    @Override
    public AnnoWiki findByKey(final String keyValue) {
        AnnoWiki anno = (AnnoWiki) super.findByKey(keyValue);

        if (anno == null) {
            anno = this.findDocumentByKey(keyValue);
        }

        return anno;
    }

    @Override
    public AnnoWiki findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public AnnoWiki findByProperty(final String propertyName, final Object propertyValue) {
        return (AnnoWiki) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<AnnoWiki> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<AnnoWiki> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<AnnoWiki> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<AnnoWiki> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public List<AnnoWiki> findAllSortKey() {
        return (List<AnnoWiki>) super.findAllSortKey();
    }

    @Override
    public List<AnnoWiki> findAllSortOrder() {
        return (List<AnnoWiki>) super.findAllSortOrder();
    }

    @Override
    public List<AnnoWiki> findAllByProperty(final String propertyName, final Object propertyValue) {
        return (List<AnnoWiki>) super.findAllByProperty(propertyName, propertyValue);
    }

    public List<AnnoWiki> findAllBySecolo(Secolo secolo) {
        return super.findAllByProperty(FIELD_NAME_SECOLO, secolo);
    }

    public List<AnnoWiki> findAllBySecolo(Secolo secolo, boolean ascendente) {
        Sort sort = Sort.by(ascendente ? Sort.Direction.ASC : Sort.Direction.DESC, "ordine");
        return super.findAllByProperty(FIELD_NAME_SECOLO, secolo, sort);
    }

    public List<String> findAllForNomeBySecolo(Secolo secolo) {
        return findAllBySecolo(secolo).stream().map(anno -> anno.nome).collect(Collectors.toList());
    }


    public List<String> findAllForNome() {
        return findAllForKeySortOrdine();
    }


    public List<String> findAllPagine() {
        List<String> listaNomi = new ArrayList<>();
        List<Anno> listaAnni = annoBackend.findAllNoSort();

        for (Anno anno : listaAnni) {
            listaNomi.add(wikiUtility.wikiTitleNatiAnno(anno.nome));
            listaNomi.add(wikiUtility.wikiTitleMortiAnno(anno.nome));
        }

        return listaNomi;
    }

    public List<String> findAllPagineReverseOrder() {
        List<String> listaNomi = new ArrayList<>();
        List<Anno> listaAnni = annoBackend.findAllSortCorrenteReverse();

        for (Anno anno : listaAnni) {
            listaNomi.add(wikiUtility.wikiTitleNatiAnno(anno.nome));
            listaNomi.add(wikiUtility.wikiTitleMortiAnno(anno.nome));
        }

        return listaNomi;
    }

    public int countListeDaCancellare() {
        int daCancellare = 0;

        //        daCancellare += ((Long) repository.countAnnoWikiByNatiOkFalse()).intValue();
        //        daCancellare += ((Long) repository.countAnnoWikiByMortiOkFalse()).intValue();

        return daCancellare;
    }

    public List<AnnoWiki> fetchDaCancellare() {
        List<AnnoWiki> lista = null;
        //        lista = repository.findAllByNatiOkFalseOrMortiOkFalse(); //@todo va in errore
        return lista;
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        AEntity entityBean;
        List<Anno> anniBase;
        List<AEntity> lista;
        String nome;

        anniBase = annoBackend.findAllNoSort();
        if (anniBase.size() < 1) {
            logService.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Anno'")).usaDb());
            return result.fine();
        }

        result.setValido(true);
        lista = new ArrayList<>();
        for (Anno anno : anniBase) {
            nome = anno.nome;

            entityBean = insert(newEntity(nome));
            if (entityBean != null) {
                lista.add(entityBean);
            }
            else {
                logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                result.setValido(false);
            }
        }

        if (lista.size() < 1) {
            result.typeResult(AETypeResult.error);
            message = String.format("Non sono riuscito a creare la collection '%s'. Controlla il metodo [%s].resetDownload()", collectionName, clazzName);
            return result.errorMessage(message);
        }

        return fixReset(result, lista);
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        WResult result = super.elabora();
        long inizio = System.currentTimeMillis();
        List<Secolo> secoli = secoloBackend.findAllSortKey();
        int bioNati;
        int bioMorti;
        String wikiTitleNati;
        String wikiTitleMorti;
        boolean esistePaginaNati;
        boolean esistePaginaMorti;
        boolean natiOk;
        boolean mortiOk;
        String message;
        int tempo = WPref.elaboraAnniTime.getInt();

        //--Check di validità del database mongoDB
        if (checkValiditaDatabase().isErrato()) {
            return WResult.errato();
        }

        //check temporale per elaborare la collection SOLO se non è già stata elaborata di recente (1 ora)
        //visto che l'elaborazione impiega più di parecchio tempo
        LocalDateTime elaborazioneAttuale = LocalDateTime.now();
        LocalDateTime lastElaborazione = (LocalDateTime) this.lastElaborazione.get();

        lastElaborazione = lastElaborazione.plusHours(WPref.oreValiditaElaborazione.getInt());
        if (elaborazioneAttuale.isBefore(lastElaborazione)) {
            this.lastElaborazione.setValue(elaborazioneAttuale);
            return result;
        }

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_ELABORA, Anno.class.getSimpleName(), tempo, unitaMisuraElaborazione);
        logService.debug(new WrapLog().message(message));

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (Secolo secolo : secoli) {
            for (AnnoWiki annoWiki : this.findAllBySecolo(secolo)) {
                bioNati = bioBackend.countAnnoNato(annoWiki);
                bioMorti = bioBackend.countAnnoMorto(annoWiki);

                annoWiki.bioNati = bioNati;
                annoWiki.bioMorti = bioMorti;

                if (WPref.controllaPagine.is()) {
                    wikiTitleNati = wikiUtility.wikiTitleNatiAnno(annoWiki);
                    wikiTitleMorti = wikiUtility.wikiTitleMortiAnno(annoWiki);

                    esistePaginaNati = queryService.isEsiste(wikiTitleNati);
                    esistePaginaMorti = queryService.isEsiste(wikiTitleMorti);

                    annoWiki.esistePaginaNati = esistePaginaNati;
                    annoWiki.esistePaginaMorti = esistePaginaMorti;

                    if (esistePaginaNati) {
                        natiOk = bioNati > 0;
                    }
                    else {
                        natiOk = bioNati == 0;
                    }
                    if (esistePaginaMorti) {
                        mortiOk = bioMorti > 0;
                    }
                    else {
                        mortiOk = bioMorti == 0;
                    }

                    annoWiki.natiOk = natiOk;
                    annoWiki.mortiOk = mortiOk;
                }

                update(annoWiki);

                //                if (Pref.debug.is()) {
                //                    cont++;
                //                    if (mathService.multiploEsatto(blocco, cont)) {
                //                        size = textService.format(cont);
                //                        time = dateService.deltaText(inizio);
                //                        message = String.format("Finora controllata l'esistenza di %s/%s anni, in %s", size, tot, time);
                //                        logService.info(new WrapLog().message(message).type(AETypeLog.elabora));
                //                    }
                //                }
            }
            if (Pref.debug.is()) {
                message = String.format("Elaborati gli anni del %s, in %s", secolo.nome, dateService.deltaText(inizio));
                logService.info(new WrapLog().message(message).type(AETypeLog.elabora));
            }
        }

        return super.fixElabora(result);
    }

    public Map elaboraValidi() {
        Map<String, Integer> mappa = new HashMap<>();
        int vociBiografiche = mongoService.count(Bio.class);
        int natiSenzaParametro = natiSenzaParametro();
        int natiParametroVuoto = natiParametroVuoto();
        int natiValoreEsistente = natiValoreEsistente();
        int mortiSenzaParametro = mortiSenzaParametro();
        int mortiParametroVuoto = mortiParametroVuoto();
        int mortiValoreEsistente = mortiValoreEsistente();
        int checkSum;

        checkSum = natiSenzaParametro + natiParametroVuoto + natiValoreEsistente + mortiSenzaParametro + mortiParametroVuoto + mortiValoreEsistente;
        if (checkSum != vociBiografiche) {
            logService.info(WrapLog.build().message("Somma degli anni di nascita e morte errata"));
        }

        mappa.put(KEY_MAP_NATI_SENZA_PARAMETRO, natiSenzaParametro);
        mappa.put(KEY_MAP_NATI_PARAMETRO_VUOTO, natiParametroVuoto);
        mappa.put(KEY_MAP_NATI_VALORE_ESISTENTE, natiValoreEsistente);

        mappa.put(KEY_MAP_MORTI_SENZA_PARAMETRO, mortiSenzaParametro);
        mappa.put(KEY_MAP_MORTI_PARAMETRO_VUOTO, mortiParametroVuoto);
        mappa.put(KEY_MAP_MORTI_VALORE_ESISTENTE, mortiValoreEsistente);

        return mappa;
    }

    public int natiSenzaParametro() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoNato").isNull());
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }

    public int natiParametroVuoto() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoNato").is(VUOTA));
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }

    public int natiValoreEsistente() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoNato").regex(".+"));
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }


    public int mortiSenzaParametro() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoMorto").isNull());
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }

    public int mortiParametroVuoto() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoMorto").is(VUOTA));
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }

    public int mortiValoreEsistente() {
        Long lungo;
        Query query = new Query();
        query.addCriteria(Criteria.where("annoMorto").regex(".+"));
        lungo = mongoService.mongoOp.count(query, Bio.class);

        return lungo > 0 ? lungo.intValue() : 0;
    }


    public AnnoWiki findDocumentById(String keyCode) {
        AnnoWiki beanAnno = null;
        Document doc = super.getDocumentById(keyCode);

        if (doc != null) {
            beanAnno = this.newEntity(doc);
        }

        return beanAnno;
    }

    public AnnoWiki findDocumentByKey(String keyCode) {
        AnnoWiki beanAnno = null;
        Document doc = super.getDocumentByKey(keyCode);

        if (doc != null) {
            beanAnno = this.newEntity(doc);
        }

        return beanAnno;
    }

    /**
     * Esegue la scrittura di tutti gli anni di un secolo <br>
     * Tutti gli anni nati <br>
     * Tutti gli anni morti <br>
     */
    public WResult uploadSecolo(Secolo secolo, boolean ascendente) {
        WResult result = WResult.errato();
        logger.info(new WrapLog().type(AETypeLog.upload).message("Inizio upload liste nati e morti nel secolo " + secolo.nome));
        List<String> anni;
        String message;
        int modificatiNati;
        int modificatiMorti;
        List<String> subList;
        int stock = 10;
        int dim;

        if (secoloBackend == null) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("Manca secoloBackend").usaDb());
        }

        if (ascendente) {
            anni = annoBackend.findAllForNomeBySecoloAsc(secolo);
        }
        else {
            anni = annoBackend.findAllForNomeBySecoloDesc(secolo);
        }

        if (anni!=null&&anni.size()>0) {
            dim = anni.size();
            for (int k = 0; k < dim; k = k + stock) {
                subList = anni.subList(k, Math.min(k + stock, dim));
                modificatiNati = 0;
                modificatiMorti = 0;
                for (String nomeAnno : subList) {
                    result = appContext.getBean(UploadAnni.class, nomeAnno).nascita().upload();
                    if (result.isValido() && result.isModificata()) {
                        modificatiNati++;
                    }
                    else {
                        message = result.getMessage();
                        message += String.format(" della pagina %s/%s", nomeAnno, AETypeLista.annoNascita.getTag());
                        logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                    }

                    result = appContext.getBean(UploadAnni.class, nomeAnno).morte().upload();
                    if (result.isValido() && result.isModificata()) {
                        modificatiMorti++;
                    }
                    else {
                        message = result.getMessage();
                        message += String.format(" della pagina %s/%s", nomeAnno, AETypeLista.annoMorte.getTag());
                        logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                    }
                }
                if (Pref.debug.is()) {
                    message = String.format("Controllate/modificate sul server %s pagine di 'nati' e 'morti' per il secolo %s (anni %s-%s)", stock, secolo,subList.get(0),subList.get(stock-1));
                    message += String.format(" in %s", dateService.deltaText(result.getInizio()));
                    logger.info(new WrapLog().type(AETypeLog.upload).message(message));
                }
            }

        }

        return result;
    }

    /**
     * Esegue la scrittura di tutte le pagine <br>
     * Tutti gli anni nati <br>
     * Tutti gli anni morti <br>
     */
    public WResult uploadAll() {
        WResult result = WResult.errato();
        logger.info(new WrapLog().type(AETypeLog.upload).message("Inizio upload liste nati e morti negli anni"));
        List<Secolo> secoli = null;
        List<String> anni;
        String message;
        int modificatiNati;
        int modificatiMorti;

        if (secoloBackend == null) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("Manca secoloBackend").usaDb());
        }

        try {
            secoli = secoloBackend.findAllSortCorrente();
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        if (secoli == null) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("Mancano i secoli").usaDb());
        }
        if (secoli.size() != 31) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("I secoli sono sbagliati").usaDb());
        }

        for (Secolo secolo : secoli) {
            anni = annoBackend.findAllForNomeBySecolo(secolo);
            if (anni == null) {
                message = String.format("Mancano gli anni del secolo %s", anni);
                logger.error(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
            }
            if (anni.size() < 1) {
                message = String.format("Nel secolo %s ci sono troppi pochi anni", secolo);
                logger.error(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
            }
            modificatiNati = 0;
            modificatiMorti = 0;
            for (String nomeAnno : anni) {
                result = appContext.getBean(UploadAnni.class, nomeAnno).nascita().upload();
                if (result.isValido() && result.isModificata()) {
                    modificatiNati++;
                }
                else {
                    message = result.getMessage();
                    message += String.format(" della pagina %s/%s", nomeAnno, AETypeLista.annoNascita.getTag());
                    logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                }

                result = appContext.getBean(UploadAnni.class, nomeAnno).morte().upload();
                if (result.isValido() && result.isModificata()) {
                    modificatiMorti++;
                }
                else {
                    message = result.getMessage();
                    message += String.format(" della pagina %s/%s", nomeAnno, AETypeLista.annoMorte.getTag());
                    logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                }
            }

            if (Pref.debug.is()) {
                message = String.format("Modificate sul server %d pagine di 'nati' e %d di 'morti' per il secolo %s", modificatiNati, modificatiMorti, secolo);
                message += String.format(" in %s", dateService.deltaText(result.getInizio()));
                logger.info(new WrapLog().type(AETypeLog.upload).message(message));
            }
        }

        return result;
    }

}// end of crud backend class
