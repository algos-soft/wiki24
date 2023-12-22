package it.algos.wiki24.backend.service;

import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 22-Dec-2023
 * Time: 13:53
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public DownloadService DownloadService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DownloadService {

    @Inject
    private TextService textService;

    @Inject
    MongoService mongoService;

    @Inject
    DateService dateService;

    @Inject
    QueryService queryService;

    @Inject
    LogService logger;


    /**
     * Ciclo iniziale di download di BioServer con un reset completo <br>
     * Cancella (drop) il database 'BioServer' <br>
     * Legge tutte le pagine dal server di wikipedia, per la categoria prevista <br>
     */
    public void cicloIniziale() {
        long inizio = System.currentTimeMillis();
        String categoryTitle = WPref.categoriaBio.getStr();
        List<Long> listaPageIdsDaLeggere = null;
        List<Long> subList;
        List<WrapBio> listaWrapBio;
        int dim;
        int stock = 10000;
        String message;
        int numVociCreate = 0;

        //--Cancella (drop) la collection
        mongoService.deleteAll(BioServerEntity.class);

        //--Controlla quante pagine ci sono nella categoria
        checkCategoria(categoryTitle);

        //--Controlla il collegamento come bot
        //        checkBot();

        //--Crea la lista di tutti i (long) pageIds della category
        //        listaPageIdsDaLeggere = getListaPageIds(categoryTitle);

//        dim = listaPageIdsDaLeggere!=null?listaPageIdsDaLeggere.size():0;
//        for (int k = 0; k < dim; k = k + stock) {
//            subList = listaPageIdsDaLeggere.subList(k, Math.min(k + stock, dim));
//
//            //--Legge le pagine
//            //            listaWrapBio = getListaWrapBio(subList, dim);
//
//            //--Crea/aggiorna le voci biografiche <br>
//            //            numVociCreate = creaElaboraListaBio(listaWrapBio, dim);
//
//            message = String.format("Create %s nuove biografie in %s", textService.format(numVociCreate), dateService.deltaText(inizio));
//            logger.info(new WrapLog().message(message).type(TypeLog.bio));
//        }

        //--durata del ciclo completo
        //        fixInfoDurataReset(inizio);
    }

    /**
     * Ciclo corrente di download <br>
     * Parte dalla lista di tutti i (long) pageIds della categoria <br>
     * Usa la lista di pageIds e si recupera una lista (stessa lunghezza) di miniWrap <br>
     * Elabora la lista di miniWrap e costruisce una lista di pageIds da leggere <br>
     */
    public void cicloCorrente() {
        String categoryTitle = WPref.categoriaBio.getStr();
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
            logger.info(new WrapLog().message(message).type(TypeLog.bio));
        }
        else {
            message = String.format("La categoria [%s] non esiste oppure è vuota", categoryTitle);
            logger.warn(new WrapLog().message(message).type(TypeLog.bio).usaDb());
        }

        return numPages;
    }

}// end of Service class