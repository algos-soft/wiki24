package it.algos.quickly;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import org.bson.*;
import org.bson.conversions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.mongodb.core.query.*;

import java.util.*;
import java.util.regex.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 05-Aug-2023
 * Time: 07:24
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@DisplayName("BioFetch")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioFetchTest extends WikiQuicklyTest {

    //    @InjectMocks
    //    private MongoService mongoService;
    //
    //    @InjectMocks
    //    private BioBackend bioBackend;


    private MongoCollection<Document> collectionBio;

    private BasicDBObject query;

    private Document doc;

    private FindIterable<Document> documents;

    private Bson projection;

    private Bio bio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        MockitoAnnotations.openMocks(this);

        this.collectionBio = getBioCollection();
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        query = new BasicDBObject();
        documents = null;
        projection = null;
        bio = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("1 - Non usato in questo test"));
    }

    @Test
    @Order(2)
    @DisplayName("2 - appContext.getBean con/senza parametri")
    void getBean() {
        System.out.println(("3 - Non usato in questo test"));
    }


    @Test
    @Order(3)
    @DisplayName("3 - fetch singolo wikiTitle")
    void fetch() {
        System.out.println(("3 - fetch anno"));
        System.out.println(VUOTA);

        sorgente = "Matteo Salvini";
        query.put(FIELD_NAME_WIKI_TITLE, sorgente);
        doc = collectionBio.find(query).first();
        bio = bioBackend.newEntity(doc);
        message = String.format("Costruita biografia di %s", sorgente);
        logService.warn(new WrapLog().message(message));
        printBio(bio);
    }

    @Test
    @Order(4)
    @DisplayName("4 - fetchBioAnnoNato")
    void fetchBioAnnoNato() {
        System.out.println(("4 - fetchBioAnnoNato"));
        System.out.println(VUOTA);
        String anno;
        listaStr = new ArrayList<>();

        sorgente = "285";
        query.put(FIELD_NAME_ANNO_NATO, sorgente);
        projection = Projections.fields(Projections.include(FIELD_NAME_ANNO_NATO), Projections.excludeId());
        documents = collectionBio.find(query).projection(projection);

        for (var singleDoc : documents) {
            anno = singleDoc.getString(FIELD_NAME_ANNO_NATO);
            listaStr.add(anno);
        }

        assertNotNull(listaStr);
        print(listaStr);
    }


    @Test
    @Order(5)
    @DisplayName("5 - fetchWikiTitleAnnoNato")
    void fetchWikiTitleAnnoNato() {
        System.out.println(("5 - fetchWikiTitleAnnoNato"));
        System.out.println(VUOTA);
        listBio = new ArrayList<>();

        sorgente = "285";
        query.put(FIELD_NAME_ANNO_NATO, sorgente);
        documents = collectionBio.find(query);

        for (var singleDoc : documents) {
            bio = bioBackend.newEntity(singleDoc);
            listBio.add(bio);
        }

        assertNotNull(listBio);
        printBioLista(listBio);
    }

    @Test
    @Order(6)
    @DisplayName("6 - fetchNome")
    void fetchNome() {
        System.out.println(("6 - fetchNome"));
        System.out.println(VUOTA);
        String nome;
        listaStr = new ArrayList<>();
        Query query = new Query();
        sorgente = "^Andrew";

//sorgente="{$regex: \"^Andrew\"}";

        Document regexQuery = new Document();
//        regexQuery.append("$regex", ".*" + Pattern.quote(sorgente) + ".*");
        regexQuery.append("$regex",  sorgente );
        BasicDBObject criteria = new BasicDBObject("nome", regexQuery);
//        DBCursor cursor = collection.find(criteria);

//        query.addCriteria(Criteria.where("nome").regex("^Andrew"));
        projection = Projections.fields(Projections.include("nome"), Projections.excludeId());
        documents = collectionBio.find(criteria).projection(projection);

        for (var singleDoc : documents) {
            nome = singleDoc.getString("nome");
            listaStr.add(nome);
        }

        print(listaStr);
    }


    protected MongoCollection<Document> getBioCollection() {
        MongoCollection<Document> collection;
        MongoDatabase client = mongoService.getDB("wiki24");
        collection = client.getCollection("bio");

        return collection;
    }


    protected void printBio(Bio bio) {
        System.out.println(String.format("wikiTitle: %s", bio.wikiTitle));
        System.out.println(String.format("pageId: %s", bio.pageId));
        System.out.println(String.format("nome: %s", textService.isValid(bio.nome) ? bio.nome : VUOTA));
        System.out.println(String.format("cognome: %s", textService.isValid(bio.cognome) ? bio.cognome : VUOTA));
        System.out.println(String.format("sesso: %s", textService.isValid(bio.sesso) ? bio.sesso : VUOTA));
        System.out.println(String.format("luogoNato: %s", textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA));
        System.out.println(String.format("luogoNatoLink: %s", textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA));
        System.out.println(String.format("giornoNato: %s", textService.isValid(bio.giornoNato) ? bio.giornoNato : VUOTA));
        System.out.println(String.format("annoNato: %s", textService.isValid(bio.annoNato) ? bio.annoNato : VUOTA));
        System.out.println(String.format("luogoMorto: %s", textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA));
        System.out.println(String.format("luogoMortoLink: %s", textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA));
        System.out.println(String.format("giornoMorto: %s", textService.isValid(bio.giornoMorto) ? bio.giornoMorto : VUOTA));
        System.out.println(String.format("annoMorto: %s", textService.isValid(bio.annoMorto) ? bio.annoMorto : VUOTA));
        System.out.println(String.format("attivita: %s", textService.isValid(bio.attivita) ? bio.attivita : VUOTA));
        System.out.println(String.format("attivita2: %s", textService.isValid(bio.attivita2) ? bio.attivita2 : VUOTA));
        System.out.println(String.format("attivita3: %s", textService.isValid(bio.attivita3) ? bio.attivita3 : VUOTA));
        System.out.println(String.format("nazionalita: %s", textService.isValid(bio.nazionalita) ? bio.nazionalita : VUOTA));
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

}