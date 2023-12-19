package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 16-Dec-2023
 * Time: 11:26
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test BotLogin ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BotLoginTest extends WikiTest {

    @Inject
    private BotLogin botLogin;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = BotLogin.class;
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
    @Order(11)
    @DisplayName("11 - valoreIniziale")
    void valoreIniziale() {
        System.out.println(("11 - valoreIniziale"));

        assertNotNull(botLogin);
        printBotLogin();
    }

    @Test
    @Order(12)
    @DisplayName("12 - urlRequestHamed")
    void urlRequestHamed() {
        System.out.println(("12 - urlRequestHamed"));

        assertNotNull(botLogin);
        printBotLogin();
        System.out.println(VUOTA);

        appContext.getBean(QueryLogin.class).urlRequestHamed();
        assertTrue(botLogin.isValido());
        printBotLogin();
    }

    @Test
    @Order(13)
    @DisplayName("13 - reset")
    void reset() {
        System.out.println(("13 - reset"));

        assertNotNull(botLogin);
        printBotLogin();
        System.out.println(VUOTA);

        appContext.getBean(QueryLogin.class).urlRequestHamed();
        assertTrue(botLogin.isValido());
        printBotLogin();

        System.out.println(VUOTA);
        System.out.println(("Effettuo un reset"));
        botLogin.reset();
        assertFalse(botLogin.isValido());
        printBotLogin();
    }

}
