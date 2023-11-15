package it.algos.base24.backend.service;

import com.vaadin.flow.data.provider.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.layer.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Sun, 20-Aug-2023
 * Time: 18:46
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public DataProviderService dataProviderService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DataProviderService {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MongoService mongoService;


    /**
     * Metodo NON utilizzato.
     * Serve solo per leggere meglio il comportamento del metodo getProvider()
     */
    public DataProvider<AbstractEntity, String> getFilterProviderFormaEstesaPiuLeggibileUsataSoloPerCapirla() {
        DataProvider<AbstractEntity, String> dataProvider =
                DataProvider.fromFilteringCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            // getFilter returns Optional<String>
                            String filter = query.getFilter().orElse(VUOTA);

                            return fetch(offset, limit, (FiltroSort) null, null);
                        },
                        // Second callback fetches the total number of items currently in the Grid.
                        // The grid can then use it to properly adjust the scrollbars.
                        query -> {
                            // getFilter returns Optional<String>
                            String filter = query.getFilter().orElse(VUOTA);

                            return count((FiltroSort) null);
                        }
                );

        return dataProvider;
    }


    /**
     * Costruisce un provider CON filtri
     * Provider filtrato da usare per la Grid
     * Metodo effettivamente utilizzato.
     */
    public DataProvider<AbstractEntity, FiltroSort> getProvider(FiltroSort filtro) {
        return DataProvider.fromFilteringCallbacks(
                query -> this.fetch(query.getOffset(), query.getLimit(), filtro, query.getSortOrders()),
                query -> this.count(filtro)
        );
    }


    public Stream<AbstractEntity> fetch(final int offset, final int limit, FiltroSort filtroCorrente, List<QuerySortOrder> vaadinSortOrders) {
        Class<AbstractEntity> modelClazz = filtroCorrente.getModelClazz();
        Sort.Order sortOrder = fixSingleSortOrder(vaadinSortOrders);

        if (sortOrder != null) {
            filtroCorrente.sort(sortOrder);
        }

        Query query = filtroCorrente != null ? filtroCorrente.getQuery() : new Query();
        query.skip(offset);
        query.limit(limit);
        return mongoService.findStream(query, modelClazz);
    }

    public int count(FiltroSort filtroCorrente) {
        Query query = filtroCorrente != null ? filtroCorrente.getQuery() : new Query();
        Class<AbstractEntity> modelClazz = filtroCorrente.getModelClazz();
        return mongoService.count(query, modelClazz);
    }


    /**
     * Costruisce un provider SENZA filtri
     * Provider NON filtrato da usare per la Grid
     * Metodo effettivamente utilizzato.
     */
    public DataProvider<AbstractEntity, Void> getProvider(Class<AbstractEntity> modelClazz) {
        return DataProvider.fromCallbacks(
                query -> this.fetch(query.getOffset(), query.getLimit(), modelClazz),
                query -> this.count(modelClazz)
        );
    }


    /**
     * Query find()
     * Usa la query base per essere sicuro di 'interrogare' gli stessi dati del count()
     */
    public Stream<AbstractEntity> fetch(final int offset, final int limit, Class<AbstractEntity> modelClazz) {
        Query query = getQuery();
        query.skip(offset);
        query.limit(limit);
        return mongoService.findStream(query, modelClazz);
    }


    /**
     * Query count()
     * Usa la query base per essere sicuro di 'interrogare' gli stessi dati del fetch()
     */
    public int count(Class<AbstractEntity> modelClazz) {
        Query query = getQuery();
        return mongoService.count(query, modelClazz);
    }


    /**
     * Query base, creata per unificare le due query: count() e fetch()
     */
    public Query getQuery() {
        Query query = new Query();

        //        Sort sort = Sort.by(Sort.Direction.ASC, keySort);
        //        query.with(sort);

        return query;
    }


    public Sort.Order fixSingleSortOrder(List<QuerySortOrder> vaadinSortOrders) {
        Sort.Order sortOrder = null;

        List<Sort.Order> springSortOrders = new ArrayList<>();
        for (QuerySortOrder so : vaadinSortOrders) {
            String colKey = so.getSorted();
            if (so.getDirection() == SortDirection.ASCENDING) {
                springSortOrders.add(Sort.Order.asc(colKey));
            }
            if (so.getDirection() == SortDirection.DESCENDING) {
                springSortOrders.add(Sort.Order.desc(colKey));
            }
        }
        if (springSortOrders.size() == 1) {
            sortOrder = springSortOrders.get(0);
        }

        return sortOrder;
    }

}// end of Service class