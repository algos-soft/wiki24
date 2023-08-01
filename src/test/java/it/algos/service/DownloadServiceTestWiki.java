package it.algos.service;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.utility.*;
import it.algos.wiki24.backend.wrapper.*;
import org.bson.*;
import org.bson.conversions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.mongodb.core.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 11-Mar-2023
 * Time: 17:41
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("service")
@DisplayName("Download Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadServiceTestWiki extends WikiQuicklyTest {

    private static String CAT = "Nati nel 560";

    private static String CAT2 = "Nati nel 1560";

    private static String CAT3 = "Nati nel 1960";

    @InjectMocks
    JvmMonitor monitor;
    @InjectMocks
    DateService dateService;
    @InjectMocks
    TextService textService;

    @Mock
    private MongoOperations mongoOp;

    private List<Long> listaPageIdsCategoria3;

    private List<Long> listaPageIdsCategoriaBio;

    private List<Long> listaMongoIdsAll;

    private List<Long> listaParzialeMongo;

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

    }

    protected void crossReferences() {
        super.crossReferences();

        service.logService = logService;
        service.monitor = monitor;
        service.dateService = dateService;
        dateService.textService = textService;
        this.arrayService = arrayService;
    }

    protected void fixRegolazioni() {
        super.fixRegolazioni();

        this.listaPageIdsCategoria3 = this.listaPageIdsCategoria(CAT3);
        this.listaMongoIdsAll = this.listaPageIdsMongo();
        this.listaParzialeMongo = listaMongoIdsAll.subList(0, 100000);
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
    }


    @Test
    @Order(1)
    @DisplayName("Primo test")
    void xxx() {
        List<Long> listaPageIdsDaLeggere = Arrays.asList(689981L, 702527L);

        List<WrapBio> lista = queryWrapBio.getWrap(listaPageIdsDaLeggere);
        assertNotNull(lista);
        assertTrue(lista.size() == 2);
    }

    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(2)
    @DisplayName("2 - queryInfoCat")
    void queryInfoCat(final String nomeCategoria) {
        System.out.println("Controlla quante pagine ci sono nella categoria");
        sorgente = nomeCategoria;

        ottenutoIntero = queryInfoCat.urlRequest(sorgente).getIntValue();
        assertTrue(ottenutoIntero > 0);
        System.out.println(String.format("Ci sono %d pagine nella categoria [%s]", ottenutoIntero, sorgente));
        System.out.println(VUOTA);
    }


//    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(3)
    @DisplayName("3 - getTitles")
    void getTitles(final String nomeCategoria) {
        System.out.println("Crea la lista di tutti i (String) wikiTitle della category");
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


//    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(4)
    @DisplayName("4 - getPageIds")
    void getPageIds(final String nomeCategoria) {
        System.out.println("Crea la lista di tutti i (long) pageIds della category");
        sorgente = nomeCategoria;

        //--di servizio - solo per iniettare infoCatResult nella queryCat
        ottenutoRisultato = queryInfoCat.urlRequest(sorgente);
        queryCat.infoCatResult = (WResult) ottenutoRisultato;
        //--fine del servizio
        List<Long> longList = queryCat.getPageIds(sorgente);

        assertNotNull(longList);
        ottenutoIntero = longList.size();
        assertTrue(ottenutoIntero > 0);
        System.out.println(String.format("Ci sono %d pagine nella categoria [%s]", ottenutoIntero, sorgente));
        System.out.println(VUOTA);
        System.out.println(String.format("Le %d pagine sono state recuperate in %s", ottenutoIntero, dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        printLong(longList);
        System.out.println(VUOTA);
    }


//    @Test
    @Order(5)
    @DisplayName("5 - findOnlyPageId")
    void findOnlyPageId() {
        System.out.println("Crea la lista di tutti i (long) pageIds esistenti nel database (mongo) locale");

        listaPageIds = projectionLong();

        assertNotNull(listaPageIds);
        ottenutoIntero = listaPageIds.size();
        assertTrue(ottenutoIntero > 0);
        System.out.println(String.format("Ci sono %s pagine nel database", textService.format(ottenutoIntero)));
        System.out.println(VUOTA);
        System.out.println(String.format("Le %s pagine sono state recuperate in %s", textService.format(ottenutoIntero), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);

        assertTrue(ottenutoIntero > 10);
        printLong(listaPageIds.subList(0, 10));
    }


//    @ParameterizedTest
    @MethodSource(value = "NUMERO_PAGINE")
    @Order(6)
    @DisplayName("6 - deltaPageIds")
    void deltaPageIds(int numero) {
        System.out.println("Recupera i (long) pageIds non più presenti nella category e da cancellare dal database (mongo) locale");
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
    @Order(7)
    @DisplayName("7 - streaming")
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

    @Test
    @Order(8)
    @DisplayName("8 - addSub")
    void addSub() {

        List<Long> lungoRandom = new ArrayList<>();

        for (long k = 0; k < 450000; k++) {
            lungoRandom.add(k);
        }

        //        listaPageIdsCategoriaBio = listaPageIdsCategoria("BioBot");
        service.addSub(listaMongoIdsAll, lungoRandom);
    }


    public List<Long> projectionLong() {
        List<Long> listaProperty = new ArrayList();
        collection = dataBase.getCollection("bio");

        Bson projection = Projections.fields(Projections.include(FIELD_NAME_PAGE_ID), Projections.excludeId());
        FindIterable<Document> documents = collection.find().projection(projection);

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

        ottenutoRisultato = queryInfoCat.urlRequest(nomeCategoria);
        queryCat.infoCatResult = (WResult) ottenutoRisultato;
        listaPageIdsCategoria = queryCat.getPageIds(nomeCategoria);

        return listaPageIdsCategoria;
    }

    protected List<Long> listaPageIdsMongo() {
        List<Long> listaPageIdsMongo;
        listaPageIdsMongo = projectionLong();
        return listaPageIdsMongo;
    }

}
