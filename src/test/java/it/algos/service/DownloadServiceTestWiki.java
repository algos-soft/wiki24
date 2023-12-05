package it.algos.service;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.utility.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.bson.*;
import org.bson.conversions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;

import org.springframework.boot.test.context.*;
import org.springframework.data.mongodb.core.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 11-Mar-2023
 * Time: 17:41
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("quickly")
@Tag("service")
@DisplayName("Download Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadServiceTestWiki extends WikiQuicklyTest {

    private static String CAT = "Nati nel 560";

    private static String CAT2 = "Nati nel 1560";

    private static String CAT3 = "Nati nel 1960";

    @InjectMocks
    JvmMonitor2 monitor;

    @InjectMocks
    DateService dateService;

    @InjectMocks
    TextService textService;

    @InjectMocks
    QueryService queryService;

    private MongoDatabase dataBase;

    @Mock
    private MongoOperations mongoOp;

    private List<Long> listaPageIdsCategoria3;

    private List<Long> listaPageIdsCategoriaBio;

    private List<Long> listaMongoIdsAll;

    private List<Long> listaPageIdsDaLeggere;

    private List<Long> listaParzialeMongo;

    private List<WrapTime> listaWrapTime;

    private List<WrapTime> listaWrapTimeCat3;

    private List<WrapTime> listaWrapTimeAllBio;

    List<WrapBio> listaWrapBioCat3;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    private DownloadService service;

    //--pagina wiki della categoria
    protected static Stream<Arguments> CATEGORIE() {
        return Stream.of(
                Arguments.of(CAT),
                Arguments.of(CAT2),
                Arguments.of(CAT3)
        );
    }

    //--numero di pagine
    protected static Stream<Arguments> NUMERO_PAGINE() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(3586),
                Arguments.of(100000)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = WikiUtility.class;

        super.setUpAll();

        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(service);
        MockitoAnnotations.openMocks(monitor);
        MockitoAnnotations.openMocks(dateService);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(queryService);
    }

    protected void crossReferences() {
        super.crossReferences();

        service.logService = logService;
        service.appContext = appContext;
        service.monitor = monitor;
        service.dateService = dateService;
        service.textService = textService;
        service.bioBackend = bioBackend;
        service.arrayService = arrayService;
        dateService.textService = textService;
        service.queryService = queryService;
        queryService.appContext = appContext;
        wikiBotService.logService = logService;
        wikiBotService.bioBackend = bioBackend;
        bioBackend.annotationService = annotationService;
    }

    protected void fixRegolazioni() {
        super.fixRegolazioni();

        dataBase = mongoService.getDB("wiki24");
        mongoService.setDataBase(dataBase);

        this.listaPageIdsCategoria3 = this.listaPageIdsCategoria(CAT3);
        this.listaMongoIdsAll = this.listaPageIdsMongo();
        this.listaParzialeMongo = listaMongoIdsAll.subList(0, 50000);
    }

    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.collection = null;
        queryCat.infoCatResult = null;
        listaWrapTime = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - queryWrapBio")
    void queryWrapBio() {
        System.out.println("1 - queryWrapBio");
        System.out.println("Legge 2 WrapBio");
        System.out.println(VUOTA);
        List<Long> listaPageIdsDaLeggere = Arrays.asList(689981L, 702527L);

        List<WrapBio> lista = queryWrapBio.getWrap(listaPageIdsDaLeggere);
        assertNotNull(lista);
        assertTrue(lista.size() == 2);
        printWrapBio(lista);
    }

    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(2)
    @DisplayName("2 - queryInfoCat")
    void queryInfoCat(final String nomeCategoria) {
        System.out.println("2 - Controlla quante pagine ci sono nella categoria");
        System.out.println(VUOTA);
        sorgente = nomeCategoria;

        ottenutoIntero = queryInfoCat.urlRequest(sorgente).getIntValue();
        assertTrue(ottenutoIntero > 0);
        System.out.println(String.format("Ci sono %d pagine nella categoria [%s]", ottenutoIntero, sorgente));
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(3)
    @DisplayName("3 - getTitles")
    void getTitles(final String nomeCategoria) {
        System.out.println("3 - Crea la lista di tutti i (String) wikiTitle della category");
        sorgente = nomeCategoria;

        //--di servizio - solo per iniettare infoCatResult nella queryCat
        ottenutoRisultato = queryInfoCat.urlRequest(sorgente);
        queryCat.infoCatResult = (WResult) ottenutoRisultato;
        //--fine

        List<String> lista = queryCat.getTitles(sorgente);
        assertNotNull(lista);
        ottenutoIntero = lista.size();
        assertTrue(ottenutoIntero > 0);
        System.out.println(VUOTA);
        System.out.println(String.format("Le %d pagine sono state recuperate in %s", ottenutoIntero, dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        print(lista);
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(4)
    @DisplayName("4 - getListaPageIds")
    void getListaPageIds(final String nomeCategoria) {
        System.out.println("4 - Crea la lista di tutti i (long) pageIds della category");
        System.out.println(VUOTA);
        sorgente = nomeCategoria;

        listaPageIds = service.getListaPageIds(sorgente);
        assertNotNull(listaPageIds);
        ottenutoIntero = listaPageIds.size();
        assertTrue(ottenutoIntero > 0);
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Ci sono %s pagine nella categoria [%s]", ottenuto, sorgente));
        System.out.println(VUOTA);
        System.out.println(String.format("I %s pageIds sono stati recuperati con getListaPageIds in %s", ottenuto, dateService.deltaText(inizio)));
        System.out.println(VUOTA);
    }

    //    @Test
    @Order(10)
    @DisplayName("10 - downloadServiceGetListaPageIds")
    void downloadServiceGetListaPageIds() {
        System.out.println("Crea la lista di tutti i (long) pageIds della category BioBot");
        System.out.println(VUOTA);
        sorgente = "BioBot";

        listaPageIds = service.getListaPageIds(sorgente);
        assertNotNull(listaPageIds);
        ottenutoIntero = listaPageIds.size();
        assertTrue(ottenutoIntero > 0);
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Ci sono %s pagine nella categoria [%s]", ottenuto, sorgente));
        System.out.println(VUOTA);
        System.out.println(String.format("I %s pageIds della categoria sono stati recuperati con DownloadService.getListaPageIds in %s", ottenuto, dateService.deltaText(inizio)));
        System.out.println(VUOTA);

        assertTrue(ottenutoIntero > 10);
        listaPageIdsCategoriaBio = listaPageIds;
    }

    //    @Test
    @Order(20)
    @DisplayName("20 - downloadServiceGetListaMongoIds")
    void downloadServiceGetListaMongoIds() {
        System.out.println("Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale");
        System.out.println(VUOTA);

        listaPageIds = service.getListaMongoIds();
        assertNotNull(listaPageIds);
        ottenutoIntero = listaPageIds.size();
        assertTrue(ottenutoIntero > 0);
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Ci sono %s pagine nel database", ottenuto));
        System.out.println(VUOTA);
        System.out.println(String.format("Le %s entities del database sono state recuperate con DownloadService.getListaPageIds in %s", ottenuto, dateService.deltaText(inizio)));
        System.out.println(VUOTA);
        assertTrue(ottenutoIntero > 10);
    }


    //    @Test
    @Order(30)
    @DisplayName("30 - downloadServiceDeltaCancella")
    void downloadServiceDeltaCancella() {
        System.out.println("Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale");
        System.out.println(VUOTA);
        if (listaPageIdsCategoriaBio == null || listaPageIdsCategoriaBio.size() < 1) {
            message = String.format("La %s è vuota", "listaPageIdsCategoriaBio");
            logService.warn(new WrapLog().message(message));
            return;
        }
        listaPageIds = service.deltaCancella(listaMongoIdsAll, listaPageIdsCategoriaBio);
        assertNotNull(listaPageIds);
    }

    //    @Test
    @Order(40)
    @DisplayName("40 - downloadServiceDeltaCreare")
    void downloadServiceDeltaCreare() {
        System.out.println("Recupera i (long) pageIds presenti nella category e non ancora esistenti nel database (mongo) locale e da creare");
        System.out.println(VUOTA);
        if (listaPageIdsCategoriaBio == null || listaPageIdsCategoriaBio.size() < 1) {
            message = String.format("La %s è vuota", "listaPageIdsCategoriaBio");
            logService.warn(new WrapLog().message(message));
            return;
        }

        listaPageIds = service.deltaCreare(listaPageIdsCategoriaBio, listaMongoIdsAll);
        assertNotNull(listaPageIds);
    }


    //    @Test
    @Order(50)
    @DisplayName("50 - downloadServiceGetListaWrapTime")
    void downloadServiceGetListaWrapTime() {
        System.out.println("Usa la lista di pageIds della categoria e recupera una lista (stessa lunghezza) di wrapTimes con l'ultima modifica sul server");
        System.out.println(VUOTA);

        listaWrapTime = service.getListaWrapTime(listaPageIdsCategoria3);
        assertNotNull(listaWrapTime);
        ottenutoIntero = listaWrapTime.size();
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Numero di '%s'%s%s. Tempo%s", "WrapTime", FORWARD, ottenuto, dateService.deltaText(inizio)));
        // mette da parte 50.000 per risparmiare tempo
        listaWrapTimeCat3 = listaWrapTime;

        inizio = System.currentTimeMillis();
        listaWrapTime = service.getListaWrapTime(listaPageIdsCategoriaBio.subList(0, 50000));
        assertNotNull(listaWrapTime);
        ottenutoIntero = listaWrapTime.size();
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Numero di '%s'%s%s. Tempo%s", "WrapTime", FORWARD, ottenuto, dateService.deltaText(inizio)));
    }


    @Test
    @Order(60)
    @DisplayName("60 - downloadServiceElaboraListaWrapTime")
    void downloadServiceElaboraListaWrapTime() {
        System.out.println("Elabora la lista di wrapTimes e costruisce una lista di pageIds da leggere");
        System.out.println(VUOTA);

        listaWrapTime = service.getListaWrapTime(listaPageIdsCategoria3);
        assertNotNull(listaWrapTime);

        listaPageIds = this.elaboraWrapTime(listaWrapTime);
        assertNotNull(listaPageIds);
        ottenutoIntero = listaPageIds.size();
        ottenuto = textService.format(ottenutoIntero);
        message = String.format("Elaborati %s pageIds (long) da listaWrapTime in %s", ottenuto, dateService.deltaText(inizio));
        logService.warn(new WrapLog().message(message));
    }

    //    @Test
    @Order(61)
    @DisplayName("61 - downloadServiceElaboraListaWrapTime")
    void downloadServiceElaboraListaWrapTime2() {
        System.out.println("Elabora la lista di wrapTimes e costruisce una lista di pageIds da leggere (all)");
        System.out.println(VUOTA);

        if (listaWrapTimeAllBio != null && listaWrapTimeAllBio.size() > 0) {
        }
        else {
            listaWrapTimeAllBio = service.getListaWrapTime(listaPageIdsCategoriaBio);
        }

        inizio = System.currentTimeMillis();
        listaPageIds = wikiBotService.elaboraWrapTime(listaWrapTimeAllBio);
        ottenutoIntero = listaPageIds.size();
        ottenuto = textService.format(ottenutoIntero);
        System.out.println(String.format("Elaborati %s pageIds (long) da listaWrapTime in %s", ottenuto, dateService.deltaText(inizio)));
        logService.warn(new WrapLog().message(message));

        // mette da parte per risparmiare tempo
        listaPageIdsDaLeggere = listaPageIdsCategoriaBio;
    }


    //    @Test
    @Order(70)
    @DisplayName("70 - downloadServiceGetListaWrapBio")
    void downloadServiceGetListaWrapBio() {
        System.out.println("Legge tutte le pagine");
        System.out.println(VUOTA);

        if (listaPageIdsCategoria3 == null) {
            return;
        }

//        List<WrapBio> lista = service.getListaWrapBio(listaPageIdsCategoria3);
//        assertNotNull(lista);
//        ottenutoIntero = listaPageIds.size();
//        message = String.format("Letti %s WrapBio dal server in %s", ottenuto, dateService.deltaText(inizio));
//        logService.warn(new WrapLog().message(message));
//        printWrapBio(lista.subList(0, 10));

        // mette da parte per risparmiare tempo
//        listaWrapBioCat3 = lista;
    }


    //    @Test
    @Order(80)
    @DisplayName("80 - downloadServiceCreaElaboraListaBio")
    void downloadServiceCreaElaboraListaBio() {
        System.out.println("Crea/aggiorna le voci biografiche");
        System.out.println(VUOTA);

//        ottenutoIntero = service.creaElaboraListaBio(listaWrapBioCat3);
//        message = String.format("Create o aggiornate %s pagine sul database in %s", ottenutoIntero, dateService.deltaText(inizio));
    }


    //    @ParameterizedTest
    @MethodSource(value = "NUMERO_PAGINE")
    @Order(130)
    @DisplayName("130 - deltaPageIds")
    void deltaPageIds(int numero) {
        System.out.println("130 - Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale");
        System.out.println(VUOTA);
        List<Long> listaParzialeMongo;
        List<Long> subLista;

        listaParzialeMongo = this.listaMongoIdsAll.subList(0, numero);
        assertNotNull(listaParzialeMongo);

        System.out.println(String.format("Ci sono %s pageIds nella categoria", listaPageIdsCategoria3.size()));
        System.out.println(String.format("Ci sono %s pageMongo nella subLista del dataBase", listaParzialeMongo.size()));
        System.out.println(VUOTA);

        inizio = System.currentTimeMillis();
        subLista = addSub(listaParzialeMongo, listaPageIdsCategoria3);
        System.out.println(String.format("deltaPageIds: %s%s%s", "listaMongo", FORWARD, "listaCategoria"));
        System.out.println(String.format("Le %s pagine sono state elaborate in %s", subLista.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);

        inizio = System.currentTimeMillis();
        subLista = addSub(listaPageIdsCategoria3, listaParzialeMongo);
        System.out.println(String.format("deltaPageIds: %s%s%s", "listaCategoria", FORWARD, "listaMongo"));
        System.out.println(String.format("Le %s pagine sono state elaborate in %s", subLista.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
    }


    //    @Test
    @Order(140)
    @DisplayName("140 - streaming")
    void stream() {
        System.out.println("Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale");
        System.out.println("Streaming comparativo");
        System.out.println(VUOTA);
        List<Long> subLista;

        System.out.println(String.format("Ci sono %s pageIds nella categoria di test", textService.format(listaPageIdsCategoria3.size())));
        System.out.println(String.format("Ci sono %s pageMongo nella subLista del dataBase", textService.format(listaParzialeMongo.size())));
        System.out.println(VUOTA);

        inizio = System.currentTimeMillis();
        subLista = addSub(listaPageIdsCategoria3, listaParzialeMongo);
        System.out.println(String.format("Le %s pagine cat->mongo sono state elaborate in %s col metodo addSub", subLista.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);

        inizio = System.currentTimeMillis();
        subLista = addSub(listaParzialeMongo, listaPageIdsCategoria3);
        System.out.println(String.format("Le %s pagine mongo->cat sono state elaborate in %s col metodo addSub", subLista.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);

        inizio = System.currentTimeMillis();
        subLista = listaParzialeMongo.stream().filter(p -> !listaPageIdsCategoria3.contains(p)).collect(Collectors.toList());
        System.out.println(String.format("Le %s pagine mongo->cat sono state elaborate in %s col metodo stream", subLista.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);

    }

    //    @Test
    @Order(150)
    @DisplayName("150 - addSub")
    void addSub() {
        List<Long> listaPageIdsCategoria;
        List<Long> subLista = new ArrayList<>();
        Long pageId;
        listaPageIdsCategoria = this.listaPageIdsCategoria("BioBot");

        Collections.sort(listaPageIdsCategoria);
        Collections.sort(listaMongoIdsAll);

        inizio = System.currentTimeMillis();
        for (int k = 0; k < listaPageIdsCategoria.size(); k++) {
            pageId = listaPageIdsCategoria.get(k);
            int pos = Collections.binarySearch(listaMongoIdsAll, pageId);

            if (pos > 0) {
                subLista.add(listaPageIdsCategoria.get(pos));
            }
        }
        System.out.println(String.format("Tempo in %s", dateService.deltaTextEsatto(inizio)));
    }


    public List<Long> elaboraWrapTime(final List<WrapTime> listaWrapTimesWiki) {
        List<Long> listaPageIdsDaLeggere = new ArrayList<>();
        long pageId;
        WrapTime wrapMongo;
        LocalDateTime lastWiki;
        LocalDateTime lastMongo;

        long inizio = System.currentTimeMillis();
        List<WrapTime> listaWrapTimesMongo = projectionWrapTime();
        System.out.println(String.format("Tempo projectionWrapTime in %s", dateService.deltaTextEsatto(inizio)));

        LinkedHashMap<Long, WrapTime> mappaMongo = new LinkedHashMap<>();
        inizio = System.currentTimeMillis();
        for (WrapTime wrapMongoMappa : listaWrapTimesMongo) {
            mappaMongo.put(wrapMongoMappa.getPageid(), wrapMongoMappa);
        }
        System.out.println(String.format("Tempo creazione mappaMongo in %s", dateService.deltaTextEsatto(inizio)));

        inizio = System.currentTimeMillis();
        if (listaWrapTimesWiki != null) {
            for (WrapTime wrapWiki : listaWrapTimesWiki) {
                pageId = wrapWiki.getPageid();
                wrapMongo = mappaMongo.get(pageId);
                lastWiki = wrapWiki.getLastModifica();
                lastMongo = wrapMongo != null ? wrapMongo.getLastModifica() : ROOT_DATA_TIME;
                if (lastWiki.isAfter(lastMongo)) {
                    listaPageIdsDaLeggere.add(pageId);
                }
            }
        }
        System.out.println(String.format("Tempo spazzolamento mappaMongo in %s", dateService.deltaTextEsatto(inizio)));

        return listaPageIdsDaLeggere;
    }


    public List<WrapTime> projectionWrapTime() {
        List<WrapTime> listaWrap = new ArrayList();
        long pageId;
        Date dateLastServer;
        LocalDateTime lastServer;
        long inizio = System.currentTimeMillis();
        collection = dataBase.getCollection("bio");

        Bson bSort = Sorts.ascending(FIELD_NAME_PAGE_ID).toBsonDocument();
        Bson projection = Projections.fields(Projections.include(FIELD_NAME_PAGE_ID, "lastServer"), Projections.excludeId());
        FindIterable<Document> documents = collection.find().projection(projection).sort(bSort);

        for (var singolo : documents) {
            pageId = singolo.get(FIELD_NAME_PAGE_ID, Long.class);
            dateLastServer = singolo.get("lastServer", Date.class);
            lastServer = dateService.dateToLocalDateTime(dateLastServer);
            listaWrap.add(new WrapTime(pageId, lastServer));
        }
        //        System.out.println(String.format("Tempo projectionWrapTime in %s", dateService.deltaTextEsatto(inizio)));

        return listaWrap;
    }

    public List<Long> projectionLong() {
        List<Long> listaProperty = new ArrayList();
        collection = dataBase.getCollection("bio");

        Bson bSort = Sorts.ascending(FIELD_NAME_PAGE_ID).toBsonDocument();
        Bson projection = Projections.fields(Projections.include(FIELD_NAME_PAGE_ID), Projections.excludeId());
        FindIterable<Document> documents = collection.find().projection(projection).sort(bSort);

        for (var singolo : documents) {
            listaProperty.add(singolo.get(FIELD_NAME_PAGE_ID, Long.class));
        }
        return listaProperty;
    }


    // copiato pari pari da DownloadService
    public List<Long> addSub(List<Long> listaPageIds, List<Long> listaMongoIds) {
        List<Long> subLista = new ArrayList<>();
        long pageId;

        for (int k = 0; k < listaPageIds.size(); k++) {
            inizio = System.currentTimeMillis();
            pageId = listaPageIds.get(k);
            if (!listaMongoIds.contains(pageId)) {
                subLista.add(pageId);
            }
            System.out.println(String.format("Tempo in %s", dateService.deltaTextEsatto(inizio)));
        }

        return subLista;
    }

    protected void print(List<String> lista) {
        int k = 1;
        if (arrayService.isAllValid(lista)) {
            for (String stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }

    protected void printLong(List<Long> lista) {
        int k = 1;
        if (arrayService.isAllValid(lista)) {
            for (Long stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }


    protected List<Long> listaPageIdsCategoria(final String nomeCategoria) {
        List<Long> listaPageIdsCategoria;
        if (appContext != null) {
            queryCat = appContext.getBean(QueryCat.class);
        }
        else {
            ottenutoRisultato = queryInfoCat.urlRequest(nomeCategoria);
            queryCat.infoCatResult = (WResult) ottenutoRisultato;
        }
        listaPageIdsCategoria = queryCat.getPageIdsOrdered(nomeCategoria);

        return listaPageIdsCategoria;
    }

    protected List<Long> listaPageIdsMongo() {
        List<Long> listaPageIdsMongo;
        listaPageIdsMongo = projectionLong();
        return listaPageIdsMongo;
    }

    protected void printWrapBio(List<WrapBio> listaWrapBio) {
        if (listaWrapBio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Wrap pageid e wikiTitle"));
            System.out.println(VUOTA);
            for (WrapBio wrap : listaWrapBio) {
                System.out.println(String.format("%s (%s)", wrap.getPageid(), wrap.getTitle()));
            }
        }
    }

}
