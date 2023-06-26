package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.wrapper.AResult.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.giorno.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 08:20
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("ListaNomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaNomiTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaNomi istanza;

    //--nome
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Aaron"),
                Arguments.of("Marcello"),
                Arguments.of("giovanni"),
                Arguments.of("maria teresa")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        assertNull(istanza);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);

        istanza = new ListaNomi();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(ListaNomi.class);
        assertNotNull(istanza);
        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(3)
    @DisplayName("3 - Lista bio di vari nomi")
        //--nome
    void listaBio(final String nome) {
        System.out.println("3- Lista bio di vari nomi");
        sorgente = nome;

        if (textService.isEmpty(nome)) {
            return;
        }

        listBio =  appContext.getBean(ListaNomi.class,sorgente).listaBio();

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano il nome %s", listBio.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(4)
    @DisplayName("4 - Lista wrapLista di vari nomi con typeLink=linkLista")
        //--nome
    void listaWrapDidascalie(final String nome) {
        System.out.println("4 - Lista wrapLista di vari nomi con typeLink=linkLista");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class,sorgente).typeLink(AETypeLink.linkLista).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }
    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(5)
    @DisplayName("5 - Lista wrapLista di vari nomi con typeLink=linkVoce")
        //--nome
    void listaWrapDidascalie2(final String nome) {
        System.out.println("5 - Lista wrapLista di vari nomi con typeLink=linkVoce");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class,sorgente).typeLink(AETypeLink.linkVoce).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }
    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(6)
    @DisplayName("6 - Lista wrapLista di vari nomi con typeLink=nessunLink (default)")
        //--nome
    void listaWrapDidascalie3(final String nome) {
        System.out.println("6 - Lista wrapLista di vari nomi con typeLink=nessunLink (default)");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class,sorgente).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(7)
    @DisplayName("7 - Key della mappa wrapLista di vari nomi")
        //--nome
    void mappaWrap(final String nome) {
        System.out.println("7 - Key della mappa wrapLista di vari nomi");
        sorgente = nome;
        int numVoci;

        if (textService.isEmpty(nome)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class,sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano il nome di %s", numVoci, sorgente);
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(8)
    @DisplayName("8 - Mappa wrapLista di vari nomi con typeLink=linkLista")
        //--nome
    void mappaWrapDidascalie(final String nome) {
        System.out.println("8 - Mappa wrapLista di vari nomi con typeLink=linkLista");
        sorgente = nome;
        int numVoci;

        if (textService.isEmpty(nome)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class,sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", numVoci, sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}