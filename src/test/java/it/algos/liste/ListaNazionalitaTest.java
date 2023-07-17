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
//@Tag("liste")
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
    @MethodSource(value = "NAZIONALITA")
    @Order(3)
    @DisplayName("3 - Lista bio di varie nazionalità")
        //--nome nazionalità
        //--typeLista
    void listaBio(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("3 - Lista bio di varie nazionalità plurali");
        sorgente = nomeNazionalita;

        if (!valido(nomeNazionalita, type)) {
            return;
        }

        listBio = switch (type) {
            case nazionalitaSingolare -> appContext.getBean(ListaNazionalita.class).singolare(sorgente).listaBio();
            case nazionalitaPlurale -> appContext.getBean(ListaNazionalita.class).plurale(sorgente).listaBio();
            default -> null;
        };

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano la nazionalità %s %s", listBio.size(), sorgente, type.getTagLower());
            System.out.println(message);
            this.printNazionalitaSingole(type, sorgente);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(4)
    @DisplayName("4 - Lista wrapLista di varie nazionalità")
        //--nome nazionalità
        //--typeLista
    void listaWrapDidascalie(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("4 - Lista wrapLista di varie nazionalità");
        sorgente = nomeNazionalita;
        int size;

        if (!valido(nomeNazionalita, type)) {
            return;
        }

        listWrapLista = switch (type) {
            case nazionalitaSingolare -> appContext.getBean(ListaNazionalita.class).singolare(sorgente).listaWrap();
            case nazionalitaPlurale -> appContext.getBean(ListaNazionalita.class).plurale(sorgente).listaWrap();
            default -> null;
        };

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano la nazionalità %s %s", listWrapLista.size(), sorgente, type.getTagLower());
            System.out.println(message);
            this.printNazionalitaSingole(type, sorgente);
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
    @MethodSource(value = "NAZIONALITA")
    @Order(5)
    @DisplayName("5 - Key della mappa wrapLista di varie nazionalità")
        //--nome nazionalità
        //--typeLista
    void mappaWrap(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("5 - Key della mappa wrapLista di varie nazionalità");
        sorgente = nomeNazionalita;
        int numVoci;

        if (!valido(nomeNazionalita, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case nazionalitaSingolare -> appContext.getBean(ListaNazionalita.class).singolare(sorgente).mappaWrap();
            case nazionalitaPlurale -> appContext.getBean(ListaNazionalita.class).plurale(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano la nazionalità di %s %s", numVoci, type.getCivile(), sorgente);
            System.out.println(message);
            this.printNazionalitaSingole(type, sorgente);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(6)
    @DisplayName("6 - Mappa wrapLista di varie nazionalità")
        //--nome nazionalità
        //--typeLista
    void mappaWrapDidascalie(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("6 - Mappa wrapLista di varie nazionalità");
        sorgente = nomeNazionalita;
        int numVoci;

        if (!valido(nomeNazionalita, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case nazionalitaSingolare -> appContext.getBean(ListaNazionalita.class).singolare(sorgente).mappaWrap();
            case nazionalitaPlurale -> appContext.getBean(ListaNazionalita.class).plurale(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano la nazionalità %s %s", numVoci, sorgente, type.getTagLower());
            System.out.println(message);
            this.printNazionalitaSingole(type, sorgente);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    private boolean valido(final String nomeNazionalita, final AETypeLista type) {
        if (textService.isEmpty(nomeNazionalita)) {
            System.out.println("Manca il nome della nazionalità");
            return false;
        }

        if (type != AETypeLista.nazionalitaSingolare && type != AETypeLista.nazionalitaPlurale) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, ListaNazionalita.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.nazionalitaSingolare && !nazSingolareBackend.isExistByKey(nomeNazionalita)) {
            message = String.format("La nazionalità singolare [%s] indicata NON esiste", nomeNazionalita);
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.nazionalitaPlurale && !nazPluraleBackend.isExistByKey(nomeNazionalita)) {
            message = String.format("La nazionalità plurale [%s] indicata NON esiste", nomeNazionalita);
            System.out.println(message);
            return false;
        }

        return true;
    }


    private void printNazionalitaSingole(final AETypeLista type, final String nomeNazionalita) {
        if (type == AETypeLista.nazionalitaPlurale) {
            List<String> listaNazionalitaSingoleComprese = nazPluraleBackend.findAllFromNomiSingolariByPlurale(nomeNazionalita);
            message = String.format("Che raggruppa le %d nazionalità singolari%s%s", listaNazionalitaSingoleComprese.size(), FORWARD, listaNazionalitaSingoleComprese);
            System.out.println(message);
        }
    }

}