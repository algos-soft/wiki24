package it.algos.wiki24.backend.packages.anno;

import com.mongodb.*;
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
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

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
        return (AnnoWiki) super.findByKey(keyValue);
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
        int cont = 0;
        int blocco = 303;
        String size;
        String time;
        int tot = count();
        Anno anno;
        int bioNati = 0;
        int bioMorti = 0;
        String wikiTitleNati;
        String wikiTitleMorti;
        boolean esistePaginaNati = false;
        boolean esistePaginaMorti = false;
        boolean natiOk;
        boolean mortiOk;
        String message;
        Map mappa;
        String bioMongoDB;
        String numPagesServerWiki;
        int tempo = WPref.elaboraAnniTime.getInt();

        //--Check di validità del database mongoDB
        if (checkValiditaDatabase().isErrato()) {
            return WResult.errato();
        }

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_ELABORA, Anno.class.getSimpleName(), tempo, unitaMisuraElaborazione);
        logService.debug(new WrapLog().message(message));

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (AnnoWiki annoWiki : findAllSortCorrenteReverse()) {
            bioNati = bioBackend.countAnnoNato(annoWiki);
            bioMorti = bioBackend.countAnnoMorto(annoWiki);

            annoWiki.bioNati = bioNati;
            annoWiki.bioMorti = bioMorti;

            annoWiki.bioNati = bioBackend.countAnnoNato(annoWiki);
            annoWiki.bioMorti = bioBackend.countAnnoMorto(annoWiki);

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

            update(annoWiki);

            if (Pref.debug.is()) {
                cont++;
                if (mathService.multiploEsatto(blocco, cont)) {
                    size = textService.format(cont);
                    time = dateService.deltaText(inizio);
                    message = String.format("Finora controllata l'esistenza di %s/%s anni, in %s", size, tot, time);
                    logService.info(new WrapLog().message(message).type(AETypeLog.elabora));
                }
            }
        }

        return super.fixElabora(result);
    }

    public Map elaboraValidi() {
        Map<String, Integer> mappa = new HashMap<>();
        List<String> nati = mongoService.projectionString(Bio.class, "annoNato");
        List<String> morti = mongoService.projectionString(Bio.class, "annoMorto");
        int vociBiografiche = mongoService.count(Bio.class);
        Long natiSenzaParametro; //senza parametro
        Long natiParametroVuoto; //parametro vuoto
        Long natiValoreEsistente; //qualsiasi valore
        Long mortiSenzaParametro; //senza parametro
        Long mortiParametroVuoto; //parametro vuoto
        Long mortiValoreEsistente; //qualsiasi valore
        List<String> mortiLinkati;
        int checkSum;

        natiSenzaParametro = nati.stream().filter(anno -> anno == null).count();
        natiParametroVuoto = nati.stream().filter(anno -> anno != null && anno.length() == 0).count();
        natiValoreEsistente = nati.stream().filter(anno -> anno != null && anno.length() > 0).count();

        mortiSenzaParametro = morti.stream().filter(anno -> anno == null).count();
        mortiParametroVuoto = morti.stream().filter(anno -> anno != null && anno.length() == 0).count();
        mortiValoreEsistente = morti.stream().filter(anno -> anno != null && anno.length() > 0).count();

        checkSum = natiSenzaParametro.intValue() + natiParametroVuoto.intValue() + natiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logService.warn(WrapLog.build().message("Somma anno di nascita errata"));
        }
        checkSum = mortiSenzaParametro.intValue() + mortiParametroVuoto.intValue() + mortiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logService.warn(WrapLog.build().message("Somma anno di morte errata"));
        }

        mappa.put(KEY_MAP_NATI_SENZA_PARAMETRO, natiSenzaParametro.intValue());
        mappa.put(KEY_MAP_NATI_PARAMETRO_VUOTO, natiParametroVuoto.intValue());
        mappa.put(KEY_MAP_NATI_VALORE_ESISTENTE, natiValoreEsistente.intValue());

        mappa.put(KEY_MAP_MORTI_SENZA_PARAMETRO, mortiSenzaParametro.intValue());
        mappa.put(KEY_MAP_MORTI_PARAMETRO_VUOTO, mortiParametroVuoto.intValue());
        mappa.put(KEY_MAP_MORTI_VALORE_ESISTENTE, mortiValoreEsistente.intValue());

        return mappa;
    }


}// end of crud backend class
