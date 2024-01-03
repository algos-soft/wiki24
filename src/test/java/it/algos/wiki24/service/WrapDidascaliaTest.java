package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.basetest.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 14:07
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("WrapDidascalia")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WrapDidascaliaTest extends WikiTest {

    /**
     * Classe principale di riferimento <br>
     */
    private WrapDidascalia istanza;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = WrapDidascalia.class;
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
        istanza = null;
    }

    @Test
    @Order(11)
    @DisplayName("11 - wrapGiornoNato")
    void wrapGiornoNato() {
        System.out.println(("11 - wrapGiornoNato"));
        System.out.println(VUOTA);
        sorgente = "giornoNato";

        for (BioMongoEntity bio : listaBio) {
            istanza = (WrapDidascalia) appContext.getBean(clazz, bio);
            assertNotNull(istanza);
            printWrap(istanza, sorgente);
        }

    }

    protected void printWrap(WrapDidascalia wrap, String type) {
        System.out.println(String.format("WrapDidascalia di type: %s", type));
        System.out.println(VUOTA);
        System.out.println(String.format("Testo didascalia: %s", wrap.getDidascalia()));
        System.out.println(String.format("Primo livello: %s", wrap.getPrimoLivello()));
        System.out.println(String.format("Secondo livello: %s", wrap.getSecondoLivello()));
        System.out.println(String.format("Terzo livello: %s", wrap.getTerzoLivello()));
        System.out.println(String.format("Quaro livello: %s", wrap.getQuartoLivello()));
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

}
