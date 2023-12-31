package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 26-Dec-2023
 * Time: 17:11
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Elabora Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElaboraServiceTest extends WikiTest {

    @Inject
    private ElaboraService service;

    @Inject
    TextService textService;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    private BioServerEntity bioServerEntity;

    @Inject
    private BioMongoEntity bioMongoEntity;

    private Map<String, String> mappaBio;

    //--wikiTitle
    //--previsto
    protected static Stream<Arguments> GIORNO_NATO() {
        return Stream.of(
//                Arguments.of("Malcolm IV di Scozia", VUOTA),
//                Arguments.of("William Dobson", "4 marzo"),
//                Arguments.of("Carolina Matilde di Hannover", "22 luglio"),
//                Arguments.of("Giuseppe Lillo", VUOTA),
//                Arguments.of("Hendrick Goltzius", VUOTA),
//                Arguments.of("Eraldo Da Roma", "1º marzo"),
//                Arguments.of("Papa Leone II", VUOTA),
                Arguments.of("Maurice O'Fihely", VUOTA),
                Arguments.of("John Arbuthnot", "29 aprile"),
                Arguments.of("Stefan Merrill Block", "26 febbraio"),
                Arguments.of("Maurizio Roffredi", "22 novembre"),
                Arguments.of("Aleksandr Ivanovič Vvedenskij", "23 novembre"),
                Arguments.of("Aleksej Feofilaktovič Pisemskij", "22 marzo")

        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ElaboraService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.mappaBio = null;
        this.bioServerEntity = null;
        this.bioMongoEntity = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - costruttore")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service) ed ha un costruttore SENZA parametri", clazzName));
        System.out.println("Viene creata da SpringBoot");
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean")
    void getBean() {
        System.out.println(("2 - getBean"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service)", clazzName));
        System.out.println("Viene creata da SpringBoot");
        System.out.println(String.format("Non si può usare appContext.getBean(%s.class)", clazzName));
    }

    //    @ParameterizedTest
    @MethodSource(value = "BIOGRAFIE")
    @Order(101)
    @DisplayName("101 - estraeMappa")
    //--wikiTitle
    //--numero parametri
    void estraeMappa(String wikiTitleVoce, int numParametri) {
        System.out.println(("101 - estraeMappa"));
        sorgente = wikiTitleVoce;
        previstoIntero = numParametri;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        if (bioServerEntity != null) {
            assertNotNull(bioServerEntity);
            sorgente2 = bioServerEntity.tmplBio;
            mappaBio = service.estraeMappa(sorgente2);
            assertNotNull(mappaBio);
            printMappa(mappaBio);
            assertEquals(previstoIntero, mappaBio.size());
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "BIOGRAFIE")
    @Order(102)
    @DisplayName("102 - creaBeanMongo")
        //--wikiTitle
        //--numero parametri
    void creaBeanMongo(String wikiTitleVoce, int numParagrafi) {
        System.out.println(("102 - creaBeanMongo"));
        sorgente = wikiTitleVoce;
        previstoIntero = numParagrafi;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        bioMongoEntity = service.creaBeanMongo(bioServerEntity);
        if (bioMongoEntity != null) {
            printBioMongo(bioMongoEntity);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNO_NATO")
    @Order(504)
    @DisplayName("504 - giornoNato")
        //--wikiTitle
        //--previsto
    void giornoNato(String wikiTitleVoce, String giornoNato) {
        System.out.println(("504 - giornoNato"));
        sorgente = wikiTitleVoce;
        previsto = giornoNato;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = mappaBio.get(KEY_MAPPA_GIORNO_NASCITA);

        ottenuto = service.fixGiornoNato(sorgente2);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


}
