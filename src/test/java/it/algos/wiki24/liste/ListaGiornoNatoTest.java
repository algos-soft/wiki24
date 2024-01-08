package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 17:33
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista giorno nato")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiornoNatoTest extends ListaTest {


    private ListaGiornoNato istanza;

    protected Stream<Arguments> getListeStream() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("20 marzo", TypeLista.giornoNascita),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaGiornoNato.class;
        super.currentModulo = giornoModulo;
        super.currentType = TypeLista.giornoNascita;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
        super.streamCollection = GIORNI();
    }


//    @ParameterizedTest
//    @MethodSource(value = "GIORNI")
//    @Order(101)
//    @DisplayName("101 - listaBio")
//    void listaBio(String nomeLista, TypeLista typeSuggerito) {
//        super.fixListaBio(nomeLista, typeSuggerito);
//    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(201)
    @DisplayName("201 - listaWrapDidascalie")
    //--nome giorno
    //--typeCrono
    void listaWrapDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("201 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaWrap = appContext.getBean(ListaGiornoNato.class, sorgente).listaWrapDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        message = String.format("Lista dei [%d] wrap di type%s[%s] per il giorno [%s]", listaWrap.size(), FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(VUOTA);
        printWrapDidascalie(listaWrap, sorgente);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(301)
    @DisplayName("301 - listaTestoDidascalia")
    //--nome giorno
    //--typeCrono
    void listaTestoDidascalia(String nomeGiorno, TypeLista type) {
        System.out.println(("301 - listaTestoDidascalia"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoNato.class, sorgente).listaTestoDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("Lista delle [%d] didascalie di type%s[%s] per il giorno [%s]", listaStr.size(), FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(401)
    @DisplayName("401 - mappaDidascalie")
    //--nome giorno
    //--typeCrono
    void mappaDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("401 - mappaDidascalie"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        mappaDidascalie = appContext.getBean(ListaGiornoNato.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("nati", sorgente, mappaDidascalie);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(501)
    @DisplayName("501 - key della mappa")
    //--nome giorno
    //--typeCrono
    void keyMappa(String nomeGiorno, TypeLista type) {
        System.out.println(("501 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoNato.class, sorgente).keyMappa();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("La mappa della lista di type%s[%s] per il giorno [%s] ha %d chiavi (paragrafi)", FORWARD, type.name(), sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(601)
    @DisplayName("601 - paragrafi")
    //--nome giorno
    //--typeCrono
    void paragrafi(String nomeGiorno, TypeLista type) {
        System.out.println(("601 - paragrafi"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafi();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per il giorno [%s]", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(701)
    @DisplayName("701 - paragrafiDimensionati")
    //--nome giorno
    //--typeCrono
    void paragrafiDimensionati(String nomeGiorno, TypeLista type) {
        System.out.println(("701 - paragrafiDimensionati"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafiDimensionati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi dimensionati della lista di type%s[%s] per il giorno [%s]", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(801)
    @DisplayName("801 - paragrafiElaborati")
    //--nome giorno
    //--typeCrono
    void paragrafiElaborati(String nomeGiorno, TypeLista type) {
        System.out.println(("801 - paragrafiElaborati"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per il giorno [%s] con eventuali sottopagine e divisori colonne", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(9001)
    @DisplayName("9001 - print")
    //--nome giorno
    //--typeCrono
    void print(String nomeGiorno, TypeLista type) {
        System.out.println(("9001 - print"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        printBodyLista(VUOTA);
    }

}



