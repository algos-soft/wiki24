package it.algos.wiki24.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Jan-2024
 * Time: 07:51
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista giorno morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiornoMortoTest extends ListaTest {


    private ListaGiornoMorto istanza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaGiornoMorto.class;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(101)
    @DisplayName("101 - listaBio")
        //--nome giorno
        //--typeCrono
    void listaBio(String nomeGiorno, TypeLista type) {
        System.out.println(("101 - listaBio"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaBio = appContext.getBean(ListaGiornoMorto.class, sorgente).listaBio();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaBio);
            return;
        }
        assertNotNull(listaBio);
        printBioLista(listaBio);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(201)
    @DisplayName("201 - listaWrapDidascalie")
        //--nome giorno
        //--typeCrono
    void listaWrapDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("201 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaWrap = appContext.getBean(ListaGiornoMorto.class, sorgente).listaWrapDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        printWrapDidascalie(listaWrap, sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(301)
    @DisplayName("301 - listaTestoDidascalia")
        //--nome giorno
        //--typeCrono
    void listaTestoDidascalia(String nomeGiorno, TypeLista type) {
        System.out.println(("301 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoMorto.class, sorgente).listaTestoDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        print(listaStr);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(401)
    @DisplayName("401 - mappaDidascalie")
        //--nome giorno
        //--typeCrono
    void mappaDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("401 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        mappaDidascalie = appContext.getBean(ListaGiornoMorto.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("morti", sorgente, mappaDidascalie);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(501)
    @DisplayName("501 - key della mappa")
        //--nome giorno
        //--typeCrono
    void keyMappa(String nomeGiorno, TypeLista type) {
        System.out.println(("501 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoMorto.class, sorgente).keyMappa();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("La mappa della lista [%s] ha %d chiavi (paragrafi)", sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(601)
    @DisplayName("601 - paragrafi")
        //--nome giorno
        //--typeCrono
    void paragrafi(String nomeGiorno, TypeLista type) {
        System.out.println(("601 - paragrafi"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoMorto.class, sorgente).paragrafi();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista [%s]", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(701)
    @DisplayName("701 - paragrafiDimensionati")
        //--nome giorno
        //--typeCrono
    void paragrafiDimensionati(String nomeGiorno, TypeLista type) {
        System.out.println(("701 - paragrafiDimensionati"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoMorto.class, sorgente).paragrafiDimensionati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi dimensionati della lista [%s]", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(801)
    @DisplayName("801 - paragrafiElaborati")
        //--nome giorno
        //--typeCrono
    void paragrafiElaborati(String nomeGiorno, TypeLista type) {
        System.out.println(("801 - paragrafiElaborati"));
        System.out.println(VUOTA);
        if (!validoGiornoMorto(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoMorto.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista [%s] con eventuali sottopagine e divisori colonne", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }

    //    @Test
    @Order(1401)
    @DisplayName("1401 - mappaDidascalie")
    void mappaDidascalie2() {
        System.out.println(("1401 - mappaDidascalie"));
        System.out.println(VUOTA);
        sorgente = "1º gennaio";
        sorgente = "29 febbraio";
        sorgente = "20 marzo";

        mappaDidascalie = appContext.getBean(ListaGiornoMorto.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("morti", sorgente, mappaDidascalie);
    }


}
