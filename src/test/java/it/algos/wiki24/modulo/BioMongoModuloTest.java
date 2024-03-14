package it.algos.wiki24.modulo;

import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.packages.crono.giorno.*;
import it.algos.vbase.backend.packages.geografia.stato.*;
import it.algos.vbase.basetest.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import static it.algos.wiki24.backend.wrapper.WResult.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 11:04
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("BioMongo Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioMongoModuloTest extends WikiModuloTest {

    //--nome attività/nazionalità
    //--typeLista per il test
    protected static Stream<Arguments> ATTIVITA_NAZIONALITA() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.nazionalitaSingolare),
                Arguments.of("politico", TypeLista.attivitaSingolare),
                Arguments.of("politici", TypeLista.attivitaPlurale),
                Arguments.of("errata", TypeLista.giornoMorte),
                Arguments.of("ingegnere", TypeLista.attivitaSingolare),
                Arguments.of("poliziotto", TypeLista.attivitaSingolare),
                Arguments.of("attrice", TypeLista.attivitaSingolare),
                Arguments.of("attrici", TypeLista.attivitaPlurale),
                Arguments.of("attori", TypeLista.attivitaPlurale),
                Arguments.of("direttore di scena", TypeLista.nazionalitaSingolare),
                Arguments.of("dominicani", TypeLista.nazionalitaPlurale),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale),
                Arguments.of("afghano", TypeLista.nazionalitaSingolare),
                Arguments.of("britannico", TypeLista.nazionalitaSingolare)
        );
    }

    //--value
    //--typeLista per il test
    //--key paragrafo
    //--numBio
    protected static Stream<Arguments> NUM_BIO() {
        return Stream.of(
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XV secolo", 1),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XVI secolo", 4),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XVII secolo", 3),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XVIII secolo", 10),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XIX secolo", 48),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XX secolo", 241),
                Arguments.of("29 febbraio", TypeLista.giornoNascita, "XXI secolo", 1),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "V secolo", 1),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "X secolo", 1),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XIV secolo", 1),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XV secolo", 2),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XVI secolo", 3),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XVII secolo", 2),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XVIII secolo", 10),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XIX secolo", 11),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XX secolo", 69),
                Arguments.of("29 febbraio", TypeLista.giornoMorte, "XXI secolo", 25),
                Arguments.of("1493", TypeLista.annoNascita, "gennaio", 8),
                Arguments.of("1493", TypeLista.annoNascita, "marzo", 2),
                Arguments.of("1493", TypeLista.annoNascita, "aprile", 1),
                Arguments.of("1493", TypeLista.annoNascita, "maggio", 2),
                Arguments.of("1493", TypeLista.annoNascita, "giugno", 2),
                Arguments.of("1493", TypeLista.annoNascita, "agosto", 2),
                Arguments.of("1493", TypeLista.annoNascita, "settembre", 1),
                Arguments.of("1493", TypeLista.annoNascita, "ottobre", 2),
                Arguments.of("1493", TypeLista.annoNascita, "novembre", 4),
                Arguments.of("1493", TypeLista.annoNascita, "dicembre", 3),
                Arguments.of("1493", TypeLista.annoNascita, TypeInesistente.giorno.getTag(), 28),
                Arguments.of("2023", TypeLista.annoMorte, "gennaio", 233),
                Arguments.of("2023", TypeLista.annoMorte, "febbraio", 180),
                Arguments.of("2023", TypeLista.annoMorte, "marzo", 203),
                Arguments.of("2023", TypeLista.annoMorte, "aprile", 168),
                Arguments.of("2023", TypeLista.annoMorte, "maggio", 180),
                Arguments.of("2023", TypeLista.annoMorte, "giugno", 170),
                Arguments.of("2023", TypeLista.annoMorte, "luglio", 167),
                Arguments.of("2023", TypeLista.annoMorte, "agosto", 173),
                Arguments.of("2023", TypeLista.annoMorte, "settembre", 165),
                Arguments.of("2023", TypeLista.annoMorte, "ottobre", 161),
                Arguments.of("2023", TypeLista.annoMorte, "novembre", 184),
                Arguments.of("2023", TypeLista.annoMorte, "dicembre", 174),
                Arguments.of("2023", TypeLista.annoMorte, TypeInesistente.giorno.getTag(), 6),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "arabi", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "austriaci", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "Austro-ungarici", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "brasiliani", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "britannici", 4),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "Bulgari", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "cartaginesi", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "cinesi", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "francesi", 13),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "giapponesi", 3),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "greci antichi", 2),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "haitiani", 2),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "italiani", 101),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "rumeni", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "russi", 3),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "sovietici", 1),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "spagnoli", 3),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "statunitensi", 8),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "svizzeri", 2),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, "tedeschi", 12),
                Arguments.of("agronomi", TypeLista.attivitaPlurale, TypeInesistente.nazionalita.getTag(), 0),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "allenatori di calcio", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "ambientalisti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "artisti", 3),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "artisti marziali misti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "assassini seriali", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "atleti paralimpici", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "attivisti", 12),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "attori", 5),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "avvocati", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "calciatori", 36),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "cantanti", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "ciclisti su strada", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "condottieri", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "cosmonauti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "criminali", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "diplomatici", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "direttori d'orchestra", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "fotografi", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "generali", 6),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "ginecologi", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "giornalisti", 3),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "guerriglieri", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "insegnanti", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "mezzofondisti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "militari", 8),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "musicisti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "nobili", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "poeti", 6),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "politici", 43),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "poliziotti", 2),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "principi", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "produttori cinematografici", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "rapper", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "registi", 3),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "registi cinematografici", 3),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "scacchisti", 1),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "scrittori", 7),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "sovrani", 19),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "taekwondoka", 3),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, "velocisti", 4),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale, TypeInesistente.attivita.getTag(), 4)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = BioMongoEntity.class;
        super.listClazz = BioMongoList.class;
        super.viewClazz = BioMongoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    //    @Test
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = currentModulo.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di [%s]", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(101)
    @DisplayName("101 - findAllByGiornoNato")
    void findAllByGiornoNato(final String nomeLista, final TypeLista type) {
        System.out.println(("101 - findAllByGiornoNato"));
        System.out.println(VUOTA);
        if (!validoGiorno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.giornoNascita) {
            listaBio = modulo.findAllByGiornoNato(nomeLista);
            this.fixListaBio(nomeLista, listaBio);
        }

    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(201)
    @DisplayName("201 - findAllByGiornoMorto")
    void findAllByGiornoMorto(final String nomeLista, final TypeLista type) {
        System.out.println(("201 - findAllByGiornoMorto"));
        System.out.println(VUOTA);
        if (!validoGiorno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.giornoMorte) {
            listaBio = modulo.findAllByGiornoMorto(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }


    //    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(301)
    @DisplayName("301 - findAllByAnnoNato")
    void findAllByAnnoNato(final String nomeLista, final TypeLista type) {
        System.out.println(("301 - findAllByAnnoNato"));
        System.out.println(VUOTA);
        if (!validoAnno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.annoNascita) {
            listaBio = modulo.findAllByAnnoNato(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }


    //    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(401)
    @DisplayName("401 - findAllByAnnoMorto")
    void findAllByAnnoMorto(final String nomeLista, final TypeLista type) {
        System.out.println(("401 - findAllByAnnoMorto"));
        System.out.println(VUOTA);
        if (!validoAnno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.annoMorte) {
            listaBio = modulo.findAllByAnnoMorto(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }


    //    @ParameterizedTest
    @MethodSource(value = "NUM_BIO")
    @DisplayName("501 - countParagrafo")
    void countParagrafo(final String value, final TypeLista type, String keyParagrafo, int numBio) {
        System.out.println(("501 - countParagrafo"));
        System.out.println(VUOTA);
        sorgente = value;
        sorgente2 = keyParagrafo;

        ottenutoIntero = switch (type) {
            case giornoNascita -> modulo.countByGiornoNatoAndSecolo(sorgente, sorgente2);
            case giornoMorte -> modulo.countByGiornoMortoAndSecolo(sorgente, sorgente2);
            case annoNascita -> modulo.countByAnnoNatoAndMese(sorgente, sorgente2);
            case annoMorte -> modulo.countByAnnoMortoAndMese(sorgente, sorgente2);
            case attivitaPlurale -> modulo.countByAttivitaAndNazionalita(sorgente, sorgente2);
            case nazionalitaPlurale -> modulo.countByNazionalitaAndAttivita(sorgente, sorgente2);
            default -> 0;
        };

        if (ottenutoIntero == numBio) {
            message = String.format("%s%s nel paragrafo [%s/%s] ci sono [%d] biografie", type.getCategoria(), FORWARD, sorgente, sorgente2, ottenutoIntero);
            System.out.println(message);
        }
        else {
            message = String.format("Nel paragrafo [%s/%s] NON ci sono biografie", sorgente, sorgente2);
            System.out.println(message);
        }
        assertEquals(numBio, ottenutoIntero);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_NAZIONALITA")
    @Order(601)
    @DisplayName("601 - findAllAttivitaSingole")
    void findAllAttivitaSingole(final String nomeAttivitaNazionalita, final TypeLista type) {
        System.out.println(("601 - findAllAttivitaSingole"));
        System.out.println(VUOTA);
        sorgente = nomeAttivitaNazionalita;

        if (type == TypeLista.attivitaSingolare || type == TypeLista.attivitaPlurale) {
            listaStr = modulo.findAllAttivitaSingole(sorgente);
            assertNotNull(listaStr);
            message = String.format("Singole attività per [%s] (%s)", sorgente, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println(listaStr);
        }

        if (type == TypeLista.nazionalitaSingolare || type == TypeLista.nazionalitaPlurale) {
            listaStr = modulo.findAllNazionalitaSingole(sorgente);
            assertNotNull(listaStr);
            message = String.format("Singole nazionalità per [%s] (%s)", sorgente, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println(listaStr);
        }
    }

}
