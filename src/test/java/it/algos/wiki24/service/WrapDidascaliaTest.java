package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.basetest.*;
import it.algos.base24.ui.form.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
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
        super.ammessoCostruttoreVuoto = true;
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

    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(101)
    @DisplayName("101 - wrapGiornoNato")
    void wrapGiornoNato(BioMongoEntity bio) {
        System.out.println(("101 - wrapGiornoNato"));
        sorgente = "giornoNato";
        WrapDidascalia wrap;

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).giornoNascita().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.giornoNato);
    }


    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(201)
    @DisplayName("201 - wrapGiornoMorto")
    void wrapGiornoMorto(BioMongoEntity bio) {
        System.out.println(("201 - wrapGiornoMorto"));
        sorgente = "giornoMorto";
        WrapDidascalia wrap;

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).giornoMorte().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.giornoMorto);
    }


    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(301)
    @DisplayName("301 - wrapAnnoNato")
    void wrapAnnoNato(BioMongoEntity bio) {
        System.out.println(("301 - wrapAnnoNato"));
        sorgente = "annoNato";
        WrapDidascalia wrap;

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).annoNascita().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.annoNato);
    }


    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(401)
    @DisplayName("401 - wrapAnnoMorte")
    void wrapAnnoMorte(BioMongoEntity bio) {
        System.out.println(("401 - wrapAnnoMorte"));
        sorgente = "annoMorte";
        WrapDidascalia wrap;

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).annoMorte().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.annoMorto);
    }
    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(501)
    @DisplayName("501 - wrapAttivita")
    void wrapAttivita(BioMongoEntity bio) {
        System.out.println(("501 - wrapAttivita"));
        sorgente = "attivita";
        WrapDidascalia wrap;

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).attivita().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.annoMorto);
    }




    //    protected void printWrap(WrapDidascalia wrap, String previsto, String type) {
//        System.out.println(VUOTA);
//        System.out.println(String.format("Type: %s (%s)", wrap.getType(), previsto));
//        System.out.println(String.format("Didascalia: %s", wrap.getDidascalia()));
//        System.out.println(String.format("Primo livello: %s", wrap.getPrimoLivello()));
//        System.out.println(String.format("Secondo livello: %s", wrap.getSecondoLivello()));
//        System.out.println(String.format("Terzo livello: %s", wrap.getTerzoLivello()));
//        System.out.println(String.format("Quarto livello: %s", wrap.getQuartoLivello()));
//    }

}
