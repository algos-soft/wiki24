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

        this.unitaMisuraElaborazione = AETypeTime.minuti;
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
            logService.error(new WrapLog().message(String.format("Manca il giorno base di riferimento dal nome %s", keyPropertyValue)));
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
    public List<GiornoWiki> findAll() {
        return super.findAll();
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
        List<Giorno> giorniBase;
        List<AEntity> lista;
        String nome;

        giorniBase = giornoBackend.findAllNoSort();
        if (giorniBase.size() < 1) {
            logService.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Giorno'")).usaDb());
            return result.fine();
        }

        result.setValido(true);
        lista = new ArrayList<>();
        for (Giorno giorno : giorniBase) {
            nome = giorno.nome;
            entityBean = creaIfNotExist(nome);
            if (entityBean != null) {
                lista.add(entityBean);
            }
            else {
                logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata creata. Probabilmente esiste già.", nome))));
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
     * L'elaborazione ha senso SOLO se il database è completo o quasi completo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        WResult result = super.elabora();

        //--Check di validità del database mongoDB
        if (checkValiditaDatabase().isErrato()) {
            return WResult.errato();
        }

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (GiornoWiki giornoWiki : findAllSortCorrente()) {
            giornoWiki.bioNati = bioBackend.countGiornoNato(giornoWiki);
            giornoWiki.bioMorti = bioBackend.countGiornoMorto(giornoWiki);

            update(giornoWiki);
        }

        return super.fixElabora(result);
    }


    public Map elaboraValidi() {
        Map<String, Integer> mappa = new HashMap<>();
        List<Bio> biografie = bioBackend.findAll();
        int vociBiografiche = mongoService.count(Bio.class);
        Long natiSenzaParametro; //senza parametro
        Long natiParametroVuoto; //parametro vuoto
        Long natiValoreEsistente; //qualsiasi valore
        Long mortiSenzaParametro; //senza parametro
        Long mortiParametroVuoto; //parametro vuoto
        Long mortiValoreEsistente; //qualsiasi valore
        int checkSum;

         natiSenzaParametro = biografie.stream().filter(bio -> bio.giornoNato == null).count();
         natiParametroVuoto = biografie.stream().filter(bio -> bio.giornoNato != null && bio.giornoNato.length() == 0).count();
         natiValoreEsistente = biografie.stream().filter(bio -> bio.giornoNato != null && bio.giornoNato.length() > 0).count();

        mortiSenzaParametro = biografie.stream().filter(bio -> bio.giornoMorto == null).count();
        mortiParametroVuoto = biografie.stream().filter(bio -> bio.giornoMorto != null && bio.giornoMorto.length() == 0).count();
        mortiValoreEsistente = biografie.stream().filter(bio -> bio.giornoMorto != null && bio.giornoMorto.length() > 0).count();

        checkSum = natiSenzaParametro.intValue() + natiParametroVuoto.intValue() + natiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logService.info(WrapLog.build().message("Somma giorno di nascita errata"));
        }
        checkSum = mortiSenzaParametro.intValue() + mortiParametroVuoto.intValue() + mortiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logService.info(WrapLog.build().message("Somma giorno di morte errata"));
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
