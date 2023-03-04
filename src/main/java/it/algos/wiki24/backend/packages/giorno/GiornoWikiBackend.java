package it.algos.wiki24.backend.packages.giorno;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 */
@Service
public class GiornoWikiBackend extends WikiBackend {


//    @Autowired
//    public GiornoBackend giornoBackend;
//
//    @Autowired
//    public MeseBackend meseBackend;

    public GiornoWikiBackend() {
        super(GiornoWiki.class);
    }

    //    public GiornoWiki creaIfNotExist(final Giorno giornoBase) {
    //        return checkAndSave(newEntity(giornoBase));
    //    }

    //    public GiornoWiki checkAndSave(final GiornoWiki giornoWiki) {
    //        return findByNome(giornoWiki.nomeWiki) != null ? null : repository.insert(giornoWiki);
    //    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public GiornoWiki newEntity() {
        return newEntity( VUOTA);
    }


    public GiornoWiki newEntity(final String keyPropertyValue) {
        return newEntity(giornoBackend.findByKey(keyPropertyValue));
    }

    @Override
    public AEntity newEntity(Object keyPropertyValue) {
        return newEntity((Giorno)keyPropertyValue);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param giornoBase proveniente da vaadin23
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public GiornoWiki newEntity(final Giorno giornoBase) {
        GiornoWiki newEntityBean = GiornoWiki.builder()
                .ordine(giornoBase.ordine)
                .nomeWiki(giornoBase.nome)
                .build();

        return (GiornoWiki) fixKey(newEntityBean);
    }

    public GiornoWiki fixProperties(GiornoWiki giornoWiki) {
        giornoWiki.pageNati = wikiUtility.wikiTitleNatiGiorno(giornoWiki.nomeWiki);
        giornoWiki.pageMorti = wikiUtility.wikiTitleMortiGiorno(giornoWiki.nomeWiki);
        return giornoWiki;
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
    public GiornoWiki findByProperty(final String propertyName, final Object propertyValue) {
        return (GiornoWiki) super.findByProperty(propertyName, propertyValue);
    }


    public List<GiornoWiki> findAllNoSort() {
        return super.findAllNoSort();
    }

    public List<GiornoWiki> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    public List<GiornoWiki> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

//    public List<String> findAllNomi() {
//        return this.findAllStringKey();
//    }

    //    public GiornoWiki findByNome(final String nome) {
    //        return repository.findFirstByNomeWiki(nome);
    //    }

    //    public boolean isEsiste(final String nome) {
    //        return repository.findFirstByNomeWiki(nome) != null;
    //    }

    //    public boolean isNotEsiste(final String nome) {
    //        return !isEsiste(nome);
    //    }


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
    public void elabora() {
        long inizio = System.currentTimeMillis();
        WResult result;
        String message;
        Map mappa;
        String bioMongoDB;
        String numPagesServerWiki;

        //--Check di validità del database mongoDB
        result = wikiUtility.checkValiditaDatabase();
        if (result.isErrato()) {
            mappa = result.getMappa();
            bioMongoDB = textService.format(mappa.get(KEY_MAP_VOCI_DATABASE_MONGO));
            numPagesServerWiki = textService.format(mappa.get(KEY_MAP_VOCI_SERVER_WIKI));
            message = "Nel database mongoDB non ci sono abbastanza voci biografiche per effettuare l'elaborazione dei giorni.";
            message += String.format(" Solo %s su %s", bioMongoDB, numPagesServerWiki);
            logger.warn(WrapLog.build().type(AETypeLog.elabora).message(message).usaDb());
            return;
        }

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (GiornoWiki giornoWiki : findAllSortCorrente()) {
            giornoWiki.bioNati = bioBackend.countGiornoNato(giornoWiki);
            giornoWiki.bioMorti = bioBackend.countGiornoMorto(giornoWiki);

            update(giornoWiki);
        }

        super.fixElabora(inizio, "dei giorni");
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
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        List<Giorno> giorniBase;
        AEntity entityBean;
        List<AEntity> lista;
        String nome;

        if (meseBackend.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Mese'")).usaDb());
            return result;
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            giorniBase = giornoBackend.findAllNoSort();
            result.setValido(true);
            lista = new ArrayList<>();

            for (Giorno giorno : giorniBase) {
                nome = giorno.nome;
                entityBean = insert(newEntity(giorno));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                    result.setValido(false);
                }
            }
            result.setIntValue(lista.size());
            result.setLista(lista);
        }
        else {
            return result;
        }

        return super.fixResult(result, clazzName, collectionName, lista.size());
    }

}// end of crud backend class
