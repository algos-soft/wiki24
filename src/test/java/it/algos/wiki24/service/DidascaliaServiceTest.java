package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 07:40
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Didascalia Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DidascaliaServiceTest extends WikiTest {

    @Autowired
    private DidascaliaService service;

    //--anno
    //--decade
    private Stream<Arguments> decadi() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("1803", "1-10"),
                Arguments.of("1413", "11-20"),
                Arguments.of("525", "21-30"),
                Arguments.of("834", "31-40"),
                Arguments.of("750", "41-50"),
                Arguments.of("1151", "51-60"),
                Arguments.of("269", "61-70"),
                Arguments.of("874", "71-80"),
                Arguments.of("1588", "81-90"),
                Arguments.of("1800", "91-00"),
                Arguments.of("43", "41-50"),
                Arguments.of("80", "71-80"),
                Arguments.of("81", "81-90"),
                Arguments.of("481", "81-90"),
                Arguments.of("1781", "81-90"),
                Arguments.of("4", "1-10"),
                Arguments.of("9", "1-10"),
                Arguments.of("10", "1-10"),
                Arguments.of(" 4 ", "1-10"),
                Arguments.of("40", "31-40"),
                Arguments.of("460 a.C.", "51-60"),
                Arguments.of("8 a.C.", "1-10"),
                Arguments.of("04", "1-10")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = DidascaliaService.class;
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


    @Test
    @Order(10)
    @DisplayName("10 - nomeCognome")
    void nomeCognome() {
        System.out.println(("10 - nomeCognome"));
        System.out.println(VUOTA);

        sorgente = "Mario Rossi";
        sorgente2 = "Mario";
        sorgente3 = "Rossi";
        previsto = "[[Mario Rossi]]";
        ottenuto = service.nomeCognome(sorgente, sorgente2, sorgente3);
        assertTrue(textService.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("La biografia [%s] ha (%s)%s%s", sorgente, "nomeCognome", FORWARD, ottenuto));

        sorgente = "Mary Anderson (attrice 1918)";
        sorgente2 = "Mary";
        sorgente3 = "Anderson";
        previsto = "[[Mary Anderson (attrice 1918)|Mary Anderson]]";
        ottenuto = service.nomeCognome(sorgente, sorgente2, sorgente3);
        assertTrue(textService.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("La biografia [%s] ha (%s)%s%s", sorgente, "nomeCognome", FORWARD, ottenuto));
    }

    @Test
    @Order(11)
    @DisplayName("11 - nomeCognome")
    void nomeCognome2() {
        System.out.println(("11 - nomeCognome"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.nomeCognome(bio);
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "nomeCognome", FORWARD, ottenuto));
        }
    }


    @Test
    @Order(20)
    @DisplayName("20 - attivitaNazionalita")
    void attivitaNazionalita() {
        System.out.println(("20 - attivitaNazionalita"));
        System.out.println(VUOTA);

        sorgente = "Nicholas Geōrgiadīs";
        sorgente2 = "scenografo";
        sorgente3 = "costumista";
        String sorgente4 = "pittore";
        String sorgente5 = "greco";
        previsto = "scenografo, costumista e pittore greco";
        ottenuto = service.attivitaNazionalita(sorgente, sorgente2, sorgente3, sorgente4, sorgente5);
        assertTrue(textService.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("La biografia [%s] ha (%s)%s%s", sorgente, "attivitaNazionalita", FORWARD, ottenuto));
    }


    @Test
    @Order(21)
    @DisplayName("21 - attivitaNazionalita")
    void attivitaNazionalita2() {
        System.out.println(("21 - attivitaNazionalita"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.attivitaNazionalita(bio);
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "attivitaNazionalita", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(31)
    @DisplayName("31 - luogoNato")
    void luogoNato() {
        System.out.println(("31 - luogoNato"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.luogoNato(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "luogoNato", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(41)
    @DisplayName("41 - luogoMorto")
    void luogoMorto() {
        System.out.println(("41 - luogoMorto"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.luogoMorto(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "luogoMorto", FORWARD, ottenuto));
        }
    }


    @Test
    @Order(51)
    @DisplayName("51 - giornoNato")
    void giornoNato() {
        System.out.println(("51 - giornoNato"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.giornoNato(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "giornoNato", FORWARD, ottenuto));
        }
    }


    @Test
    @Order(61)
    @DisplayName("61 - giornoMorto")
    void giornoMorto() {
        System.out.println(("61 - giornoMorto"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.giornoMorto(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "giornoMorto", FORWARD, ottenuto));
        }
    }


    @Test
    @Order(71)
    @DisplayName("71 - annoNato")
    void annoNato() {
        System.out.println(("71 - annoNato"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.annoNato(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "annoNato", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(72)
    @DisplayName("72 - annoNatoIcona")
    void annoNatoIcona() {
        System.out.println(("72 - annoNatoIcona"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.annoNatoIcona(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "annoNatoIcona", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(81)
    @DisplayName("81 - annoMorto")
    void annoMorto() {
        System.out.println(("81 - annoMorto"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.annoMorto(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "annoMorto", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(82)
    @DisplayName("82 - annoMortoIcona")
    void annoMortoIcona() {
        System.out.println(("82 - annoMortoIcona"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.annoMortoIcona(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "annoMortoIcona", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(91)
    @DisplayName("91 - annoMortoIcona")
    void localita() {
        System.out.println(("91 - annoMortoIcona"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.localita(bio);
            System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "annoMortoIcona", FORWARD, ottenuto));
        }
    }

    @Test
    @Order(101)
    @DisplayName("101 - didascaliaGiornoNato")
    void didascaliaGiornoNato() {
        System.out.println(("101 - didascaliaGiornoNato"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.didascaliaGiornoNato(bio);
            previsto = textService.isValid(bio.giornoNato) ? bio.giornoNato : NULLO;
            message = String.format("[%s] nato il giorno [%s]%s%s", bio.wikiTitle, previsto, FORWARD, ottenuto);
            System.out.println(message);
        }
    }

    @Test
    @Order(201)
    @DisplayName("201 - didascaliaGiornoMorto")
    void didascaliaGiornoMorto() {
        System.out.println(("201 - didascaliaGiornoMorto"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.didascaliaGiornoMorto(bio);
            previsto = textService.isValid(bio.giornoMorto) ? bio.giornoMorto : NULLO;
            message = String.format("[%s] morto il giorno [%s]%s%s", bio.wikiTitle, previsto, FORWARD, ottenuto);
            System.out.println(message);
        }
    }


    @Test
    @Order(301)
    @DisplayName("301 - didascaliaAnnoNato")
    void didascaliaAnnoNato() {
        System.out.println(("301 - didascaliaAnnoNato"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.didascaliaAnnoNato(bio);
            previsto = textService.isValid(bio.annoNato) ? bio.annoNato : NULLO;
            message = String.format("[%s] nato l'anno [%s]%s%s", bio.wikiTitle, previsto, FORWARD, ottenuto);
            System.out.println(message);
        }
    }


    @Test
    @Order(401)
    @DisplayName("401 - didascaliaAnnoMorto")
    void didascaliaAnnoMorto() {
        System.out.println(("401 - didascaliaAnnoMorto"));
        System.out.println(VUOTA);

        for (BioMongoEntity bio : listaBio) {
            ottenuto = service.didascaliaAnnoMorto(bio);
            previsto = textService.isValid(bio.annoMorto) ? bio.annoMorto : NULLO;
            message = String.format("[%s] morto l'anno [%s]%s%s", bio.wikiTitle, previsto, FORWARD, ottenuto);
            System.out.println(message);
        }
    }


    @Test
    @Order(501)
    @DisplayName("501 - getDecade")
    void getDecade() {
        System.out.println("501 - getDecade");
        System.out.println(VUOTA);

        //--anno
        //--decade
        decadi().forEach(this::fixDecade);
    }

    //--anno
    //--decade
    void fixDecade(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.getDecade(sorgente);
        assertEquals(textService.isValid(previsto),textService.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        message = String.format("L'anno '%s' ricade nella decade -> [%s]", sorgente, ottenuto);
        System.out.println(message);
    }


}
