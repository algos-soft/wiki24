package it.algos.wiki24.backend.service;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.utility.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.bson.*;
import org.bson.conversions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;


/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 18-mag-2022
 * Time: 19:35
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(DownloadService.class); <br>
 * 3) @Autowired public DownloadService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DownloadService extends WAbstractService {

    @Autowired
    public JvmMonitor2 monitor;

    public void cicloIniziale() {
        cicloIniziale(WPref.categoriaBio.getStr());
    }


    /**
     * Ciclo iniziale di download con un reset completo <br>
     * Cancella (drop) il database <br>
     * Legge tutte le pagine sul server di wikipedia <br>
     */
    public void cicloIniziale(final String categoryTitle) {
        long inizio = System.currentTimeMillis();
        List<Long> listaPageIdsDaLeggere;
        List<Long> subList;
        List<WrapBio> listaWrapBio;
        int dim;
        int stock = 10000;
        String message;
        int numVociCreate = 0;

        //--Cancella (drop) il database
        mongoService.deleteAll(Bio.class);

        //--Controlla quante pagine ci sono nella categoria
        checkCategoria(categoryTitle);

        //--Controlla il collegamento come bot
        checkBot();

        //--Crea la lista di tutti i (long) pageIds della category
        listaPageIdsDaLeggere = getListaPageIds(categoryTitle);

        dim = listaPageIdsDaLeggere.size();
        for (int k = 0; k < dim; k = k + stock) {
            subList = listaPageIdsDaLeggere.subList(k, Math.min(k + stock, dim));

            //--Legge le pagine
            listaWrapBio = getListaWrapBio(subList, dim);

            //--Crea/aggiorna le voci biografiche <br>
            numVociCreate = creaElaboraListaBio(listaWrapBio, dim);

            message = String.format("Create %s nuove biografie in %s", textService.format(numVociCreate), dateService.deltaText(inizio));
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }

        //--durata del ciclo completo
        fixInfoDurataReset(inizio);
    }

    public void cicloCorrente() {
        cicloCorrente(WPref.categoriaBio.getStr());
    }

    /**
     * Ciclo corrente di download <br>
     * Parte dalla lista di tutti i (long) pageIds della categoria <br>
     * Usa la lista di pageIds e si recupera una lista (stessa lunghezza) di miniWrap <br>
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     */
    public void cicloCorrente(final String categoryTitle) {
        long inizio = System.currentTimeMillis();
        List<Long> listaPageIds;
        List<Long> listaMongoIds;
        List<Long> listaMongoIdsDaCancellare;
        List<Long> listaPageIdsDaCreare;
        List<WrapTime> listaWrapTime;
        List<Long> listaPageIdsDaLeggere;
        List<WrapBio> listaWrapBio;
        List<Long> subList;
        int dim;
        int stock = 10000;
        int numVociCreate = 0;
        String message;

        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));
        logService.info(new WrapLog().message("Inizio ciclo di download").type(AETypeLog.bio));
        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));

        //--Controlla quante pagine ci sono nella categoria
        checkCategoria(categoryTitle);

        //--Controlla il collegamento come bot
        checkBot();

        //--Crea la lista di tutti i (long) pageIds della category
        listaPageIds = getListaPageIds(categoryTitle);

        //--Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale
        listaMongoIds = getListaMongoIds();

        //--Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale
        listaMongoIdsDaCancellare = deltaCancella(listaMongoIds, listaPageIds);

        //--Cancella dal database (mongo) locale le entities non più presenti nella category <br>
        cancellaEntitiesNonInCategory(listaMongoIdsDaCancellare);

        //--Recupera i (long) pageIds presenti nella category e non ancora esistenti nel database (mongo) locale e da creare
        listaPageIdsDaCreare = deltaCreare(listaPageIds, listaMongoIds);

        dim = listaPageIdsDaCreare.size();
        for (int k = 0; k < dim; k = k + stock) {
            subList = listaPageIdsDaCreare.subList(k, Math.min(k + stock, dim));

            //--Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale
            creaNewEntities(subList, dim);

            //            //--Legge le pagine
            //            listaWrapBio = getListaWrapBio(subList);
            //
            //            //--Crea/aggiorna le voci biografiche <br>
            //            numVociCreate = creaElaboraListaBio(listaWrapBio);
            //
            //            message = String.format("Create %s nuove biografie in %s", textService.format(numVociCreate), dateService.deltaText(inizio));
            //            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }

        //        //--Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale
        //        creaNewEntities(listaPageIdsDaCreare);

        //--Usa la lista di pageIds della categoria e recupera una lista (stessa lunghezza) di wrapTimes con l'ultima modifica sul server
        listaWrapTime = getListaWrapTime(listaPageIds);

        //--Elabora la lista di wrapTimes e costruisce una lista di pageIds da leggere
        listaPageIdsDaLeggere = elaboraListaWrapTime(listaWrapTime);

        //--Legge tutte le pagine
        listaWrapBio = getListaWrapBio(listaPageIdsDaLeggere, dim);

        //--Crea/aggiorna le voci biografiche <br>
        creaElaboraListaBio(listaWrapBio, dim);

        //--durata del ciclo completo
        fixInfoDurataCiclo(inizio);
    }


    /**
     * Legge (anche come anonymous) il numero di pagine di una categoria wiki <br>
     * Si collega come anonymous; non serve essere loggati <br>
     * Serve però per le operazioni successive <br>
     *
     * @param categoryTitle da controllare
     *
     * @return numero di pagine (subcategorie escluse)
     */
    public int checkCategoria(final String categoryTitle) {
        int numPages = queryService.getSizeCat(categoryTitle);
        String message;

        if (numPages > 0) {
            message = String.format("La categoria [%s] esiste e ci sono %s voci", categoryTitle, textService.format(numPages));
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }
        else {
            message = String.format("La categoria [%s] non esiste oppure è vuota", categoryTitle);
            logService.warn(new WrapLog().message(message).type(AETypeLog.bio).usaDb());
        }

        return numPages;
    }


    /**
     * Controlla il collegamento come bot <br>
     *
     * @return true se collegato come bot
     */
    public boolean checkBot() {
        boolean status = false;
        String message;

        if (botLogin != null) {
            status = switch (botLogin.getUserType()) {
                case anonymous, user, admin -> false;
                case bot -> true;
            };
        }

        if (status) {
            message = String.format("Regolarmente collegato come %s di nick '%s'", botLogin.getUserType(), botLogin.getUsername());
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }
        else {
            message = String.format("Collegato come %s di nick '%s' e NON come bot", botLogin.getUserType(), botLogin.getUsername());
            logService.warn(new WrapLog().message(message).type(AETypeLog.bio).usaDb());
        }
        return status;
    }

    /**
     * Crea la lista di tutti i (long) pageIds della categoria <br>
     * Deve riuscire a gestire una lista di circa 500.000 long per la category BioBot <br>
     * Tempo medio previsto = circa 1 minuto (come bot la categoria legge 5.000 pagine per volta) <br>
     * Nella listaPageIds possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param categoryTitle da controllare
     *
     * @return lista di tutti i (long) pageIds
     */
    public List<Long> getListaPageIds(final String categoryTitle) {
        long inizio = System.currentTimeMillis();
        List<Long> listaPageIds;
        String size;
        String time;

        listaPageIds = queryService.getCatIdsOrdered(categoryTitle);

        size = textService.format(listaPageIds.size());
        time = dateService.deltaText(inizio);
        String message = String.format("Recuperati %s pageIds (long) dalla categoria [%s]. Tempo %s", size, categoryTitle, time);
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));

        return listaPageIds;
    }

    /**
     * Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale <br>
     *
     * @return lista di tutti i (long) pageId del database (mongo) locale
     */
    public List<Long> getListaMongoIds() {
        List<Long> lista;
        String message;
        long inizio = System.currentTimeMillis();
        String size;
        String time;

        lista = bioBackend.findOnlyPageId();

        if (lista == null || lista.size() == 0) {
            message = "La lista bio è vuota";
            logService.warn(new WrapLog().message(message));
            return null;
        }

        size = textService.format(lista.size());
        time = dateService.deltaText(inizio);
        message = String.format("Recuperate %s entities (long) dal database (mongo). Tempo %s", size, time);
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));

        return lista;
    }


    public List<Long> deltaCancella(List<Long> listaMongoIds, List<Long> listaPageIds) {
        List<Long> delta;
        long inizio = System.currentTimeMillis();
        String message;

        delta = arrayService.deltaBinary(listaMongoIds, listaPageIds);

        if (delta != null && delta.size() > 0) {
            message = String.format("Nel database (mongo) sono state individuate %s biografie da cancellare. Tempo %s", delta.size(), dateService.deltaText(inizio));
        }
        else {
            message = String.format("Nel database (mongo) non ci sono biografie da cancellare.", delta.size());
        }
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));

        return delta;
    }


    /**
     * Cancella dal database (mongo) locale le entities non più presenti nella category <br>
     *
     * @param listaMongoIdsDaCancellare dal database mongo locale
     */
    public void cancellaEntitiesNonInCategory(List<Long> listaMongoIdsDaCancellare) {
        long inizio = System.currentTimeMillis();
        String message;
        String size;
        Bio bio;

        if (listaMongoIdsDaCancellare == null || listaMongoIdsDaCancellare.size() < 1) {
            return;
        }

        for (Long pageId : listaMongoIdsDaCancellare) {
            bio = bioBackend.findByKey(pageId);
            if (bio != null) {
                bioBackend.delete(bio);
            }
        }

        size = textService.format(listaMongoIdsDaCancellare.size());
        message = String.format("Nel database (mongo) sono state cancellate %s entities non più presenti sul server wiki. Tempo %s", size, dateService.deltaText(inizio));
        logService.info(new WrapLog().message(message).type(AETypeLog.bio).usaDb());
    }


    public List<Long> deltaCreare(List<Long> listaPageIds, List<Long> listaMongoIds) {
        long inizio = System.currentTimeMillis();
        List<Long> delta;
        String message;
        String cat = "BioBot";
        String deltaTxt;

        delta = arrayService.deltaBinary(listaPageIds, listaMongoIds);
        deltaTxt = textService.format(delta);
        if (delta != null && delta.size() > 0) {
            message = String.format("Nel database (mongo) mancano %s biografie esistenti nella categoria [%s]. Tempo %s", deltaTxt, cat, dateService.deltaText(inizio));
        }
        else {
            message = String.format("Nel database (mongo) non ci sono biografie da creare.", delta.size());
        }
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));

        return delta;
    }

    //    /**
    //     * Recupera i (long) pageIds presenti nella category e non esistenti ancora nel database (mongo) locale e da creare <br>
    //     *
    //     * @param listaPageIds  tutti i (long) pageIds presenti sul server wiki
    //     * @param listaMongoIds tutti i (long) pageIds presenti nel database mongo locale
    //     *
    //     * @return lista di tutti i nuovi (long) pageId da inserire nel database (mongo) locale
    //     */
    //    @Deprecated
    //    public List<Long> deltaPageIds(List<Long> listaPageIds, List<Long> listaMongoIds, String nomeLog) {
    //        long inizio = System.currentTimeMillis();
    //        long inizioSub;
    //        long fine;
    //        long delta;
    //        long deltaSub;
    //        String size;
    //        String time;
    //        List<Long> listaPageIdsDaCreare = new ArrayList<>();
    //        int max = 1000;
    //        List<Long> subLista = new ArrayList<>();
    //        String message;
    //
    //        if (listaPageIds == null) {
    //            return listaMongoIds;
    //        }
    //
    //        if (listaMongoIds == null) {
    //            return listaPageIds;
    //        }
    //
    //        if (Pref.debug.is()) {
    //            logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));
    //        }
    //
    //        //--memoria
    //        Object stat = monitor.takeMemoryStat();
    //        int a = 87;
    //        //--memoria
    //
    //        for (int k = 0; k < listaPageIds.size(); k += max) {
    //            inizioSub = System.currentTimeMillis();
    //            subLista = addSub(listaPageIds.subList(k, Math.min(k + max, listaPageIds.size())), listaMongoIds);
    //
    //            listaPageIdsDaCreare.addAll(subLista);
    //
    //            if (Pref.debug.is()) {
    //                size = textService.format(k);
    //                fine = System.currentTimeMillis();
    //                deltaSub = fine - inizioSub;
    //                deltaSub = deltaSub / 1000;
    //                delta = fine - inizio;
    //                delta = delta / 1000;
    //                message = String.format("K= %s (%s/%s): adesso %s ha %s pageIds", size, deltaSub, delta, nomeLog, listaPageIdsDaCreare.size());
    //                logService.info(new WrapLog().message(message).type(AETypeLog.bio));
    //            }
    //        }
    //
    //        size = textService.format(listaPageIdsDaCreare.size());
    //        time = dateService.deltaText(inizio);
    //        message = String.format("Elaborata la lista %s di %s pageIds, in %s", nomeLog, size, time);
    //        logService.info(new WrapLog().message(message).type(AETypeLog.bio));
    //
    //        return listaPageIdsDaCreare;
    //    }

    public List<Long> addSub(List<Long> listaPageIds, List<Long> listaMongoIds) {
        List<Long> subLista = new ArrayList<>();
        long pageId;
        String message;

        //        long inizio = System.currentTimeMillis();
        //                subLista = listaPageIds.stream().filter(p -> !listaMongoIds.contains(p)).collect(Collectors.toList());
        //        message=String.format("Le %s pageIds sono state elaborate in %s col metodo stream in", subLista.size(), dateService.deltaTextEsatto(inizio));
        //        logService.debug(new WrapLog().message(message).type(AETypeLog.test));

        Object stat = monitor.takeMemoryStat();
        int a = 87;

        long inizio = System.currentTimeMillis();
        for (int k = 0; k < listaPageIds.size(); k++) {
            inizio = System.currentTimeMillis();
            pageId = listaPageIds.get(k);
            System.out.println(String.format("Tempo get in %s", dateService.deltaTextEsatto(inizio)));

            inizio = System.currentTimeMillis();
            if (!listaMongoIds.contains(pageId)) {
                //                System.out.println(String.format("Tempo contains %s", dateService.deltaTextEsatto(inizio)));
                //                inizio = System.currentTimeMillis();
                subLista.add(pageId);
                System.out.println(String.format("Tempo add %s", dateService.deltaTextEsatto(inizio)));
            }
            System.out.println(String.format("Tempo contains %s", dateService.deltaTextEsatto(inizio)));
            Object stat2 = monitor.takeMemoryStat();
            int as = 87;
        }
        inizio = System.currentTimeMillis();
        subLista = listaPageIds.stream().filter(p -> !listaMongoIds.contains(p)).collect(Collectors.toList());
        System.out.println(String.format("Le %s pagine mongo->cat sono state elaborate in %s col metodo old", subLista.size(), dateService.deltaTextEsatto(inizio)));

        return subLista;
    }


    /**
     * Crea le nuove voci presenti nella category e non ancora esistenti nel database (mongo) locale <br>
     *
     * @param listaPageIdsDaCreare tutti i (long) pageIds presenti sul server wiki e da creare
     */
    public void creaNewEntities(List<Long> listaPageIdsDaCreare, int dim) {
        long inizio = System.currentTimeMillis();
        List<WrapBio> listWrapBio;
        String message;
        String sizeNew;
        int numVociCreate = 0;

        if (listaPageIdsDaCreare != null && listaPageIdsDaCreare.size() > 0) {
            listWrapBio = getListaWrapBio(listaPageIdsDaCreare, dim);
            numVociCreate = creaElaboraListaBio(listWrapBio, dim);
        }

        if (numVociCreate > 0) {
            sizeNew = textService.format(numVociCreate);
            message = String.format("Create %s nuove biografie in %s", sizeNew, dateService.deltaText(inizio));
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }
    }


    /**
     * Usa la lista di pageIds e recupera una lista (stessa lunghezza) di miniWrap <br>
     * Deve riuscire a gestire una lista di circa 500.000 long per la category BioBot <br>
     * Tempo medio previsto = circa 20 minuti (come bot la query legge 500 pagine per volta) <br>
     * Nella listaMiniWrap possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param listaPageIds di tutti i (long) pageIds
     *
     * @return lista di tutti i WrapTime con l'ultima modifica sul server
     */
    public List<WrapTime> getListaWrapTime(final List<Long> listaPageIds) {
        List<WrapTime> listaMiniWrap = null;
        long inizio = System.currentTimeMillis();
        String message;
        String size;
        String cat = "BioBot";

        if (Pref.debug.is()) {
            logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));
            message = String.format("Creazione dei wrapTimes dalla categoria [%s] in (previsti) %s", cat, "9 minuti");
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }

        listaMiniWrap = queryService.getMiniWrap(listaPageIds);

        size = textService.format(listaMiniWrap.size());
        message = String.format("Creati %s wrapTimes dai pageIds della categoria [%s] in %s", size, cat, dateService.deltaText(inizio));
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));

        return listaMiniWrap;
    }


    /**
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     * Vengono usati quelli che hanno un miniWrap.pageid senza corrispondente bio.pageid nel mongoDb <br>
     * Vengono usati quelli che hanno miniWrap.lastModifica maggiore di bio.lastModifica <br>
     * A regime deve probabilmente gestire una lista di circa 10.000 miniWrap
     * si tratta delle voci nuove e di quelle modificate nelle ultime 24 ore <br>
     * Nella listaPageIdsDaLeggere possono esserci anche voci SENZA il tmpl BIO, che verranno scartate dopo <br>
     *
     * @param listaWrapTimes con il pageIds e lastModifica
     *
     * @return listaPageIdsDaLeggere
     */
    public List<Long> elaboraListaWrapTime(final List<WrapTime> listaWrapTimes) {
        List<Long> listaPageIdsDaLeggere;
        long inizio = System.currentTimeMillis();
        String size;
        String voci;

        listaPageIdsDaLeggere = wikiBotService.elaboraWrapTime(listaWrapTimes);
        size = textService.format(listaWrapTimes.size());
        voci = textService.format(listaPageIdsDaLeggere.size());
        String message = String.format("Elaborati in totale %s wrapTimes e trovate %s voci da aggiornare, in %s", size, voci, dateService.deltaText(inizio));
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        return listaPageIdsDaLeggere;
    }


    /**
     * Legge tutte le pagine <br>
     * Recupera i contenuti di tutte le voci biografiche da creare/modificare <br>
     * Controlla che esiste il tmpl BIO <br>
     * Nella listaWrapBio possono ci sono solo voci CON il tmpl BIO valido <br>
     *
     * @param listaPageIdsDaLeggere dal server wiki
     *
     * @return listaWrapBio
     */
    public List<WrapBio> getListaWrapBio(final List<Long> listaPageIdsDaLeggere, int tot) {
        List<WrapBio> listaWrap = new ArrayList<>();
        long inizio = System.currentTimeMillis();
        long inizio2;
        List<Long> subList;
        List<WrapBio> listaWrapTmp;
        String message;
        int stock = 1000;
        int dim;
        int num = 0;
        String size;
        String sizeStock;
        String sizeTot;
        String inizioTxt;
        String inizioTxtTot;

        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));
        if (listaPageIdsDaLeggere != null) {
            dim = listaPageIdsDaLeggere.size();
            sizeStock = textService.format(dim / 1000);
            sizeTot = textService.format(tot / 1000);
            for (int k = 0; k < dim; k = k + stock) {
                subList = listaPageIdsDaLeggere.subList(k, Math.min(k + stock, dim));
                inizio2 = System.currentTimeMillis();
                listaWrapTmp = appContext.getBean(QueryWrapBio.class).getWrap(subList);
                if (listaWrapTmp != null) {
                    num += listaWrapTmp.size();
                    size = textService.format(num / 1000);
                    inizioTxt = dateService.deltaText(inizio2);
                    inizioTxtTot = dateService.deltaText(inizio);
                    listaWrap.addAll(listaWrapTmp);
                    message = String.format("Recuperati %s/%s/%s WrapBio di biografie da aggiornare in %s sul totale di %s", size, sizeStock, sizeTot, inizioTxt, inizioTxtTot);
                    logService.info(new WrapLog().message(message).type(AETypeLog.bio));
                }
            }
            message = String.format("Recuperati in totale %s WrapBio di biografie da aggiornare in %s", textService.format(listaWrap.size()), dateService.deltaText(inizio));
            logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        }

        return listaWrap;
    }


    /**
     * Crea/aggiorna le voci biografiche <br>
     * Salva le entities Bio su mongoDB <br>
     * Elabora (e salva) le entities Bio <br>
     * Nella listaWrapBio possono esserci solo voci CON il tmpl BIO valido <br>
     *
     * @param listaWrapBio da elaborare e salvare
     */
    public int creaElaboraListaBio(final List<WrapBio> listaWrapBio, int tot) {
        int numVociCreate = 0;
        long inizio = System.currentTimeMillis();
        int stock = 1000;
        int dim;
        String sizeStock;
        String sizeTot;
        String message;
        List<WrapBio> subList;

        if (listaWrapBio != null && listaWrapBio.size() > 0) {
            dim = listaWrapBio.size();
            sizeStock = textService.format(dim / 1000);
            sizeTot = textService.format(tot / 1000);
            for (int k = 0; k < dim; k = k + stock) {
                subList = listaWrapBio.subList(k, Math.min(k + stock, dim));
                for (WrapBio wrap : subList) {
                    if (creaElaboraBio(wrap)) {
                        numVociCreate++;
                    }
                    else {
                        message = String.format("La pagina %s non è una biografia", listaWrapBio.get(k).getTitle());
                        logService.warn(new WrapLog().message(message).usaDb().type(AETypeLog.bio));
                    }
                }
                message = String.format("Aggiornate %s/%s/%s biografie in %s", textService.format(numVociCreate / 1000), sizeStock, sizeTot, dateService.deltaText(inizio));
                logService.info(new WrapLog().message(message).type(AETypeLog.bio));
            }
        }

        return numVociCreate;
    }


    /**
     * Crea/aggiorna una singola entity <br>
     */
    public boolean creaElaboraBio(WrapBio wrap) {
        Bio bio;
        Bio newBio;
        long pageId;
        String oldTemplBio;
        String newTemplBio;

        if (wrap != null && wrap.isValida()) {
            pageId = wrap.getPageid();
            bio = bioBackend.findByKey(pageId);
            if (bio != null) {
                oldTemplBio = bio.getTmplBio();
                newTemplBio = wrap.getTemplBio();
                if (newTemplBio.equals(oldTemplBio)) {
                    bio.lastServer = wrap.getTimeStamp();
                    bio.lastMongo = LocalDateTime.now();
                    newBio = bioBackend.save(bio);
                }
                else {
                    bio.setTmplBio(wrap.getTemplBio());
                    bio = elaboraService.esegue(bio);
                    bio.lastServer = wrap.getTimeStamp();
                    bio.lastMongo = LocalDateTime.now();
                    newBio = bioBackend.save(bio);
                }
            }
            else {
                bio = bioBackend.newEntity(wrap);
                bio = elaboraService.esegue(bio);
                bio.lastServer = wrap.getTimeStamp();
                bio.lastMongo = LocalDateTime.now();
                newBio = bioBackend.insert(bio);
            }
            return newBio != null;
        }

        return false;
    }

    public void fixInfoDurataReset(final long inizio) {
        String message;
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;

        if (WPref.resetBio != null) {
            WPref.resetBio.setValue(LocalDateTime.now());
        }

        if (WPref.resetBioTime != null) {
            delta = delta / 1000 / 60;
            WPref.resetBioTime.setValue(delta.intValue());
        }

        if (WPref.downloadBio != null) {
            WPref.downloadBio.setValue(LocalDateTime.now());
        }

        if (WPref.downloadBioTime != null) {
            WPref.downloadBioTime.setValue(0);
        }

        if (WPref.downloadBioPrevisto != null) {
            WPref.downloadBioPrevisto.setValue(ROOT_DATA_TIME);
        }

        if (WPref.elaboraBio != null) {
            WPref.elaboraBio.setValue(LocalDateTime.now());
        }

        if (WPref.elaboraBioTime != null) {
            WPref.elaboraBioTime.setValue(0);
        }

        message = String.format("Ciclo completo di reset in, %s", dateService.deltaText(inizio));
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));
    }


    public void fixInfoDurataCiclo(final long inizio) {
        String message;
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;

        if (WPref.downloadBio != null) {
            WPref.downloadBio.setValue(LocalDateTime.now());
        }

        if (WPref.downloadBioTime != null) {
            delta = delta / 1000 / 60;
            WPref.downloadBioTime.setValue(delta.intValue());
        }

        message = String.format("Ciclo completo di download in, %s", dateService.deltaText(inizio));
        logService.info(new WrapLog().message(message).type(AETypeLog.bio));
        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.bio));
    }

    /**
     * Durata del ciclo completo <br>
     */
    public void fixInfoDurataCiclo(final String categoryTitle, final long inizio) {
        String message;
        String time = dateService.deltaText(inizio);
        message = String.format("Ciclo completo di download della categoria [%s] in %s", categoryTitle, time);
        logService.info(new WrapLog().message(message).usaDb().type(AETypeLog.bio).usaDb());
    }

    public List<Long> projectionLong() {
        List<Long> listaProperty = new ArrayList();
        MongoDatabase dataBase = mongoService.getDataBase();
        MongoCollection<Document> collection = dataBase.getCollection("bio");

        Bson projection = Projections.fields(Projections.include(FIELD_NAME_PAGE_ID), Projections.excludeId());
        FindIterable<Document> documents = collection.find().projection(projection);

        for (var singolo : documents) {
            listaProperty.add(singolo.get(FIELD_NAME_PAGE_ID, Long.class));
        }
        return listaProperty;
    }

}