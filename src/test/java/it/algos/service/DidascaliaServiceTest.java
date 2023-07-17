package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 17-Jul-2023
 * Time: 19:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Didascalia Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DidascaliaServiceTest extends WikiTest {

    private Bio bioBean;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private DidascaliaService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = didascaliaService;

//        bioBean = creaBio();
//        assertNotNull(bioBean);
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(1)
    @DisplayName("1 - luogoNato1")
    void luogoNato() {
        System.out.println(("1 - luogoNato1"));
        System.out.println(VUOTA);

        ottenuto = service.luogoNato(bioBean);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }

    @Test
    @Order(1)
    @DisplayName("1 - luogoNato")
    void luogoNato2() {
        System.out.println(("1 - luogoNato"));
        System.out.println(VUOTA);

        ottenuto = service.luogoNato(bioBean);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }


    @Test
    @Order(2)
    @DisplayName("2 - linkAnnoNatoTesta")
    void linkAnnoNatoTesta() {
        System.out.println(("2 - linkAnnoNatoTesta"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoNatoTesta(bioBean, null);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }

    @Test
    @Order(3)
    @DisplayName("3 - luogoNatoAnno")
    void luogoNatoAnno() {
        System.out.println(("3 - luogoNatoAnno"));
        System.out.println(VUOTA);

        ottenuto = service.luogoNatoAnno(bioBean, AETypeLink.nessunLink);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }



//    private Bio creaBio() {
//        Bio beanBio = null;
//        MongoCollection collection;
//
//        FindIterable<Document> documents;
//        MongoDatabase client = mongoService.getDB("wiki24");
//        collection = client.getCollection("bio");
//
//        documents = collection.find();
//        documents.limit(1);
//        for (var singolo : documents) {
//            beanBio = bioBackend.newEntity(singolo);
//        }
//
//        return beanBio;
//    }

}
