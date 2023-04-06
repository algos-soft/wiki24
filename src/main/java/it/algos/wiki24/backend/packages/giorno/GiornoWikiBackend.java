package it.algos.wiki24.backend.packages.giorno;

import com.mongodb.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 */
@Service
public class GiornoWikiBackend extends WikiBackend {


    public GiornoWikiBackend() {
        super(GiornoWiki.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.resetGiorni;
        super.lastElaborazione = WPref.elaboraGiorni;
        super.durataElaborazione = WPref.elaboraGiorniTime;
        super.lastUpload = WPref.uploadGiorni;
        super.durataUpload = WPref.uploadGiorniTime;
        super.nextUpload = WPref.uploadGiorniPrevisto;
        super.lastStatistica = WPref.statisticaGiorni;
        super.durataStatistica = WPref.statisticaGiorniTime;

        this.unitaMisuraElaborazione = AETypeTime.secondi;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public GiornoWiki newEntity() {
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
    public GiornoWiki newEntity(final String keyPropertyValue) {
        GiornoWiki newEntityBean = GiornoWiki.builderGiornoWiki().build();
        Giorno giornoBase = giornoBackend.findByKey(keyPropertyValue);
        if (giornoBase == null) {
            logger.error(new WrapLog().message(String.format("Manca il giorno base di riferimento dal nome %s", keyPropertyValue)));
            return null;
        }

        beanService.copiaAncheID(giornoBase, newEntityBean);
        newEntityBean.pageNati = wikiUtility.wikiTitleNatiGiorno(giornoBase.nome);
        newEntityBean.pageMorti = wikiUtility.wikiTitleMortiGiorno(giornoBase.nome);

        return newEntityBean;
    }


    @Override
    public GiornoWiki findById(final String keyID) {
        return (GiornoWiki) super.findById(keyID);
    }

    @Override
    public GiornoWiki findByKey(final String keyValue) {
        return (GiornoWiki) super.findByKey(keyValue);
    }

    @Override
    public GiornoWiki findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public GiornoWiki findByProperty(final String propertyName, final Object propertyValue) {
        return (GiornoWiki) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<GiornoWiki> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<GiornoWiki> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<GiornoWiki> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<GiornoWiki> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<GiornoWiki> findAllByMese(Mese mese) {
        return super.findAllByProperty(FIELD_NAME_MESE, mese);
    }


    public List<String> findAllForNome() {
        return findAllForKeySortOrdine();
    }

    public List<String> findAllForNomeByMese(Mese mese) {
        return findAllByMese(mese).stream().map(giorno -> giorno.nome).collect(Collectors.toList());
    }


    public List<String> findAllPagine() {
        List<String> listaNomi = new ArrayList<>();
        List<Giorno> listaGiorni = giornoBackend.findAllNoSort();

        for (Giorno giorno : listaGiorni) {
            listaNomi.add(wikiUtility.wikiTitleNatiGiorno(giorno.nome));
            listaNomi.add(wikiUtility.wikiTitleMortiGiorno(giorno.nome));
        }

        return listaNomi;
    }


    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * L'elaborazione ha senso SOLO se il database è completo o quasi completo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        long inizio = System.currentTimeMillis();
        WResult result = super.elabora();
        String message;
        int tempo = WPref.elaboraGiorniTime.getInt();

        //--Check di validità del database mongoDB
        if (checkValiditaDatabase().isErrato()) {
            return WResult.errato();
        }

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_ELABORA, Giorno.class.getSimpleName(), tempo, unitaMisuraElaborazione);
        logger.debug(new WrapLog().message(message));

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (GiornoWiki giornoWiki : findAllSortCorrente()) {
            giornoWiki.bioNati = bioBackend.countGiornoNato(giornoWiki);
            giornoWiki.bioMorti = bioBackend.countGiornoMorto(giornoWiki);

            update(giornoWiki);
        }

        return super.fixElabora(result, inizio, "dei giorni");
    }


    public Map elaboraValidi() {
        Map<String, Integer> mappa = new HashMap<>();
        List<String> nati = mongoService.projectionString(Bio.class, "giornoNato");
        List<String> morti = mongoService.projectionString(Bio.class, "giornoMorto");
        int vociBiografiche = mongoService.count(Bio.class);
        Long natiSenzaParametro; //senza parametro
        Long natiParametroVuoto; //parametro vuoto
        Long natiValoreEsistente; //qualsiasi valore
        Long mortiSenzaParametro; //senza parametro
        Long mortiParametroVuoto; //parametro vuoto
        Long mortiValoreEsistente; //qualsiasi valore
        List<String> mortiLinkati;
        int checkSum;

        natiSenzaParametro = nati.stream().filter(giorno -> giorno == null).count();
        natiParametroVuoto = nati.stream().filter(giorno -> giorno != null && giorno.length() == 0).count();
        natiValoreEsistente = nati.stream().filter(giorno -> giorno != null && giorno.length() > 0).count();

        mortiSenzaParametro = morti.stream().filter(giorno -> giorno == null).count();
        mortiParametroVuoto = morti.stream().filter(giorno -> giorno != null && giorno.length() == 0).count();
        mortiValoreEsistente = morti.stream().filter(giorno -> giorno != null && giorno.length() > 0).count();

        checkSum = natiSenzaParametro.intValue() + natiParametroVuoto.intValue() + natiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logger.warn(WrapLog.build().message("Somma giorno di nascita errata"));
        }
        checkSum = mortiSenzaParametro.intValue() + mortiParametroVuoto.intValue() + mortiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logger.warn(WrapLog.build().message("Somma giorno di morte errata"));
        }

        mappa.put(KEY_MAP_NATI_SENZA_PARAMETRO, natiSenzaParametro.intValue());
        mappa.put(KEY_MAP_NATI_PARAMETRO_VUOTO, natiParametroVuoto.intValue());
        mappa.put(KEY_MAP_NATI_VALORE_ESISTENTE, natiValoreEsistente.intValue());

        mappa.put(KEY_MAP_MORTI_SENZA_PARAMETRO, mortiSenzaParametro.intValue());
        mappa.put(KEY_MAP_MORTI_PARAMETRO_VUOTO, mortiParametroVuoto.intValue());
        mappa.put(KEY_MAP_MORTI_VALORE_ESISTENTE, mortiValoreEsistente.intValue());

        return mappa;
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(logInfo);
        String clazzName = entityClazz.getSimpleName();
        List<Giorno> giorniBase;
        AEntity entityBean;
        List<AEntity> lista;
        String nome;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            message = String.format("Inizio resetOnlyEmpty() di %s. Tempo previsto: meno di 1 secondo.", clazzName);
            logger.debug(new WrapLog().message(message));
            giorniBase = giornoBackend.findAllNoSort();
            result.setValido(true);
            lista = new ArrayList<>();

            for (Giorno giorno : giorniBase) {
                nome = giorno.nome;
                entityBean = insert(newEntity(nome));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                    result.setValido(false);
                }
            }
        }
        else {
            return result;
        }

        return super.fixReset(result, clazzName, lista, logInfo);
    }

}// end of crud backend class
