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

    //--biografie
    private Stream<BioMongoEntity> biografie() {
        return Stream.of(
                creaBio("Johann Schweikhard von Kronberg"),
                creaBio("Vincenzo Ferreri"),
                creaBio("Roberto Rullo"),
                creaBio("Stanley Adams (attore)"),
                creaBio("Daniel Williams"),
                creaBio("Jameson Adams"),
                creaBio("Marianna Saltini"),
                creaBio("Giuseppe Marchetti"),
                creaBio("Jordan Adams (1981)")
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

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(11)
    @DisplayName("11 - nomeCognome")
        //--biografia
    void nomeCognome2(final BioMongoEntity bio) {
        System.out.println(("11 - nomeCognome"));
        System.out.println(VUOTA);

        ottenuto = service.nomeCognome(bio);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(String.format("La biografia [%s] ha (%s)%s%s", bio.wikiTitle, "nomeCognome", FORWARD, ottenuto));
    }

    protected BioMongoEntity creaBio(String wikiTitle) {
        return bioMongoModulo.findByWikiTitle(wikiTitle);
    }

}
