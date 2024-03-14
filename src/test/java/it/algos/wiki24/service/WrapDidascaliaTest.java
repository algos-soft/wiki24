package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.backend.packages.anagrafica.via.*;
import it.algos.vbase.backend.packages.crono.anno.*;
import it.algos.vbase.basetest.*;
import it.algos.vbase.ui.form.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
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

import javax.inject.*;
import java.util.*;

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

    @Inject
    DidascaliaService didascaliaService;

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

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).attivita().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.annoMorto);
    }

    @ParameterizedTest
    @MethodSource(value = "getBio")
    @Order(601)
    @DisplayName("601 - wrapNazionalita")
    void wrapNazionalita(BioMongoEntity bio) {
        System.out.println(("601 - wrapNazionalita"));
        sorgente = "nazionalità";

        istanza = ((WrapDidascalia) appContext.getBean(clazz)).nazionalita().get(bio);
        assertNotNull(istanza);
        printBioMongo(bio);
        printWrap(istanza, bio.annoMorto);
    }




    @Test
    @Order(601)
    @DisplayName("601 - ordinamentoAlfabetico")
    void ordinamentoAlfabetico() {
        System.out.println(("601 - ordinamentoAlfabetico"));
        System.out.println(VUOTA);
        List<WrapDidascalia> disordinata = new ArrayList<>();
        List<WrapDidascalia> ordinata;

        for (BioMongoEntity bio : listaBio) {
            istanza = ((WrapDidascalia) appContext.getBean(clazz)).attivita().get(bio);
            if (textService.isValid(istanza.getDidascalia())) {
                disordinata.add(istanza);
            }
        }

        message = String.format("Lista %s di %s wrapDidascalie", "disordinata", disordinata.size());
        System.out.println(message);
        System.out.println(VUOTA);
        for (WrapDidascalia wrap : disordinata) {
            System.out.println(wrap.getDidascalia());
        }

        ordinata = didascaliaService.ordinamentoAlfabetico(disordinata);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
        message = String.format("Lista %s di %s wrapDidascalie", "ordinate alfabeticamente", ordinata.size());
        System.out.println(message);
        System.out.println(VUOTA);
        for (WrapDidascalia wrap : ordinata) {
            System.out.println(wrap.getDidascalia());
        }
    }



    @Test
    @Order(602)
    @DisplayName("602 - ordinamentoNumerico")
    void ordinamentoNumerico() {
        System.out.println(("602 - ordinamentoNumerico"));
        System.out.println(VUOTA);
        List<WrapDidascalia> disordinata = new ArrayList<>();
        List<WrapDidascalia> ordinata;

        for (BioMongoEntity bio : listaBio) {
            istanza = ((WrapDidascalia) appContext.getBean(clazz)).giornoMorte().get(bio);
            if (textService.isValid(istanza.getDidascalia())) {
                disordinata.add(istanza);
            }
        }

        message = String.format("Lista %s di %s wrapDidascalie", "disordinata", disordinata.size());
        System.out.println(message);
        System.out.println(VUOTA);
        for (WrapDidascalia wrap : disordinata) {
            System.out.println(wrap.getDidascalia());
        }

//        ordinata = didascaliaService.ordinamentoNumerico(disordinata);
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        message = String.format("Lista %s di %s wrapDidascalie", "ordinate numericamente", ordinata.size());
//        System.out.println(message);
//        System.out.println(VUOTA);
//        for (WrapDidascalia wrap : ordinata) {
//            System.out.println(wrap.getDidascalia());
//        }
    }

}
