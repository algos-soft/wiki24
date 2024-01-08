package it.algos.wiki24.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
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

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Jan-2024
 * Time: 08:22
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista anno nato")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAnnoNatoTest extends ListaTest {

    private ListaAnnoNato istanza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAnnoNato.class;
        super.currentModulo = annoModulo;
        super.currentType = TypeLista.annoNascita;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }

//    @ParameterizedTest
//    @MethodSource(value = "ANNI")
//    @Order(101)
//    @DisplayName("101 - listaBio")
//    void listaBio(String nomeLista, TypeLista typeSuggerito) {
//        super.fixListaBio(nomeLista, typeSuggerito);
//    }


//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(201)
    @DisplayName("201 - listaWrapDidascalie")
        //--nome anno
        //--typeCrono
    void listaWrapDidascalie(String nomeAnno, TypeLista type) {
        System.out.println(("201 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        listaWrap = appContext.getBean(ListaAnnoNato.class, sorgente).listaWrapDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        message = String.format("Lista dei [%d] wrap di type%s[%s] per l'anno [%s]", listaWrap.size(), FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(VUOTA);
        printWrapDidascalie(listaWrap, sorgente);
    }


//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(301)
    @DisplayName("301 - listaTestoDidascalia")
        //--nome anno
        //--typeCrono
    void listaTestoDidascalia(String nomeAnno, TypeLista type) {
        System.out.println(("301 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        listaStr = appContext.getBean(ListaAnnoNato.class, sorgente).listaTestoDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("Lista delle [%d] didascalie di type%s[%s] per l'anno [%s]", listaStr.size(), FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }

//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(401)
    @DisplayName("401 - mappaDidascalie")
        //--nome anno
        //--typeCrono
    void mappaDidascalie(String nomeAnno, TypeLista type) {
        System.out.println(("401 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        mappaDidascalie = appContext.getBean(ListaAnnoNato.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("nati", sorgente, mappaDidascalie);
    }

//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(501)
    @DisplayName("501 - key della mappa")
        //--nome anno
        //--typeCrono
    void keyMappa(String nomeAnno, TypeLista type) {
        System.out.println(("501 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        listaStr = appContext.getBean(ListaAnnoNato.class, sorgente).keyMappa();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("La mappa della lista di type%s[%s] per l'anno [%s] ha %d chiavi (paragrafi)", FORWARD, type.name(), sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }

//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(601)
    @DisplayName("601 - paragrafi")
        //--nome anno
        //--typeCrono
    void paragrafi(String nomeAnno, TypeLista type) {
        System.out.println(("601 - paragrafi"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).paragrafi();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per l'anno [%s]", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }

//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(701)
    @DisplayName("701 - paragrafiDimensionati")
        //--nome anno
        //--typeCrono
    void paragrafiDimensionati(String nomeAnno, TypeLista type) {
        System.out.println(("701 - paragrafiDimensionati"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).paragrafiDimensionati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi dimensionati della lista di type%s[%s] per l'anno [%s]", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(801)
    @DisplayName("801 - paragrafiElaborati")
        //--nome anno
        //--typeCrono
    void paragrafiElaborati(String nomeAnno, TypeLista type) {
        System.out.println(("801 - paragrafiElaborati"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per l'anno [%s] con eventuali sottopagine e divisori colonne", FORWARD, type.name(), sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


//    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(9001)
    @DisplayName("9001 - print")
        //--nome anno
        //--typeCrono
    void print(String nomeAnno, TypeLista type) {
        System.out.println(("9001 - print"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        printBodyLista(VUOTA);
    }

}
