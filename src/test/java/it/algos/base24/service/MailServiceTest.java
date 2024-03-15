package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.service.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.time.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 14:35
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Mail Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MailServiceTest extends ServiceTest {

    @Autowired
    private MailService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = MailService.class;
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


//    @Test
    @Order(11)
    @DisplayName("11 - send completo")
    void send() {
        System.out.println(("11 - send completo"));
        System.out.println(VUOTA);

        sorgente2 = "gac@algos.it";
        sorgente = "Mail completa";
        message = String.format("Adesso sono le %s", LocalTime.now());
        ottenutoBooleano = service.send("info@algos.it", sorgente2, sorgente, message);
        assertTrue(ottenutoBooleano);
    }


//    @Test
    @Order(12)
    @DisplayName("12 - send semplice")
    void send2() {
        System.out.println(("12 - send semplice"));
        System.out.println(VUOTA);

        sorgente2 = "gac@algos.it";
        sorgente = "Mail semplice";
        message = String.format("Adesso sono le %s", LocalTime.now());
        ottenutoBooleano = service.send(sorgente2, sorgente, message);
        assertTrue(ottenutoBooleano);
    }

//    @Test
    @Order(13)
    @DisplayName("13 - send ridotto")
    void send3() {
        System.out.println(("13 - send ridotto"));
        System.out.println(VUOTA);

        sorgente = "Mail ridotta";
        message = String.format("Adesso sono le %s", LocalTime.now());
        ottenutoBooleano = service.send(sorgente, message);
        assertTrue(ottenutoBooleano);
    }

}
