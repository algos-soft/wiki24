package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
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
    @DisplayName("2 - Lista bio di varie nazionalità")
        //--nome nazionalità plurale (maiuscola o minuscola)
        //--esiste
    void listaBio(final String nomePlurale, final boolean esiste) {
        System.out.println("2 - Lista bio di varie nazionalità");
        sorgente = nomePlurale;
        previstoBooleano = esiste;
//        List<String> listaNazionalitàSingoleComprese=nazPluraleBackend.;

        listBio = appContext.getBean(ListaAttivita.class).plurale(sorgente).listaBio();

        if (listBio != null && listBio.size() > 0) {
            assertTrue(previstoBooleano);
            message = String.format("Ci sono %d biografie che implementano la nazionalità plurale %s", listBio.size(), sorgente);
            System.out.println(message);
            List<String> listaNomiSingoli = attivitaBackend.findAllSingolariByPlurale(sorgente);
            System.out.println(listaNomiSingoli);

            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            assertFalse(previstoBooleano);
            message = String.format("Non esiste la nazionalità plurale '%s'", sorgente);
            System.out.println(message);
            System.out.println("La listBio è nulla");
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