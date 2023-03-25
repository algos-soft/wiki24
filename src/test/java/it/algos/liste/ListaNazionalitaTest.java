package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 09:23
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@DisplayName("ListaNazionalita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaNazionalitaTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaNazionalita istanza;


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
        istanza = new ListaNazionalita();
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA_PLURALE")
    @Order(2)
    @DisplayName("2 - Lista bio di varie nazionalità plurali")
        //--nome nazionalità plurale (solo minuscola)
        //--esiste
    void listaBio(final String nomePlurale, final boolean esiste) {
        System.out.println("2 - Lista bio di varie nazionalità plurali");
        sorgente = nomePlurale;
        previstoBooleano = esiste;

        listBio = appContext.getBean(ListaNazionalita.class).plurale(sorgente).listaBio();
        printSingoleAndBio(sorgente, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA_PLURALE")
    @Order(3)
    @DisplayName("3 - Lista wrapLista di varie nazionalità plurali")
        //--nome nazionalità plurale (solo minuscola)
        //--esiste
    void listaWrapDidascalie(final String nomePlurale, final boolean esiste) {
        System.out.println("3 - Lista wrapLista di varie nazionalità plurali");
        sorgente = nomePlurale;
        previstoBooleano = esiste;

        listWrapLista = appContext.getBean(ListaNazionalita.class).plurale(sorgente).listaWrap();
        printSingoleAndListaWrap(sorgente, listWrapLista);
    }



    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA_PLURALE")
    @Order(4)
    @DisplayName("4 - Mappa wrapLista di varie nazionalità")
        //--nome nazionalità plurale (solo minuscola)
        //--esiste
    void mappaWrapDidascalie(final String nomePlurale, final boolean esiste) {
        System.out.println("4 - Mappa wrapLista di varie nazionalità");
        sorgente = nomePlurale;
        previstoBooleano = esiste;

        mappaWrap = appContext.getBean(ListaNazionalita.class).plurale(sorgente).mappaWrap();
        printSingoleAndMappaWrap(sorgente, mappaWrap);
    }


    private void printSingoleAndBio(final String nomePlurale, final List<Bio> listaBio) {
        List<String> listaNazionalitàSingoleComprese = nazPluraleBackend.findSingolari(nomePlurale);

        if (listaBio != null && listaBio.size() > 0) {
            assertTrue(previstoBooleano);
            message = String.format("Ci sono %d biografie che usano la nazionalità plurale %s", listaBio.size(), nomePlurale);
            System.out.println(message);
            message = String.format("Che raggruppa le %d nazionalità singolari%s%s", listaNazionalitàSingoleComprese.size(), FORWARD, listaNazionalitàSingoleComprese);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            assertFalse(previstoBooleano);
            message = String.format("Non esiste la nazionalità plurale '%s'", nomePlurale);
            System.out.println(message);
            System.out.println("La listBio è nulla");
        }
    }


    private void printSingoleAndListaWrap(final String nomePlurale, final List<WrapLista> listaWrapLista) {
        List<String> listaNazionalitàSingoleComprese = nazPluraleBackend.findSingolari(nomePlurale);

        if (listaWrapLista != null && listaWrapLista.size() > 0) {
            assertTrue(previstoBooleano);
            message = String.format("Ci sono %d biografie che usano la nazionalità plurale %s", listaWrapLista.size(), nomePlurale);
            System.out.println(message);
            message = String.format("Che raggruppa le %d nazionalità singolari%s%s", listaNazionalitàSingoleComprese.size(), FORWARD, listaNazionalitàSingoleComprese);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listaWrapLista);
        }
        else {
            assertFalse(previstoBooleano);
            message = String.format("Non esiste la nazionalità plurale '%s'", nomePlurale);
            System.out.println(message);
            System.out.println("La wrapLista è nulla");
        }
    }
    private void printSingoleAndMappaWrap(final String nomePlurale, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        List<String> listaNazionalitàSingoleComprese = nazPluraleBackend.findSingolari(nomePlurale);

        if (mappaWrap != null && mappaWrap.size() > 0) {
            assertTrue(previstoBooleano);
            message = String.format("Ci sono %d biografie che usano la nazionalità plurale %s", mappaWrap.size(), nomePlurale);
            System.out.println(message);
            message = String.format("Che raggruppa le %d nazionalità singolari%s%s", listaNazionalitàSingoleComprese.size(), FORWARD, listaNazionalitàSingoleComprese);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            assertFalse(previstoBooleano);
            message = String.format("Non esiste la nazionalità plurale '%s'", nomePlurale);
            System.out.println(message);
            System.out.println("La mappaWrap è nulla");
        }
    }

}