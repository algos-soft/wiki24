package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 03-Jun-2022
 * Time: 20:06
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("liste")
@DisplayName("Lista Attivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAttivitaTest extends WikiTest {

    /**
     * Classe principale di riferimento <br>
     */
    private ListaAttivita istanza;


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
     * Qui passa a ogni test delle sottoclassi <br>
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
    void costruttore() {
        istanza = appContext.getBean(ListaAttivita.class);
        assertNotNull(istanza);

        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(3)
    @DisplayName("3 - Lista bio di varie attivita")
        //--nome attivita
        //--typeLista
    void listaBio(final String nomeAttivita, final AETypeLista type) {
        System.out.println("3 - Lista bio di varie attivita");
        sorgente = nomeAttivita;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        listBio = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class).singolare(sorgente).listaBio();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class).plurale(sorgente).listaBio();
            default -> null;
        };

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano l'attività %s %s", listBio.size(), type.getTagLower(), sorgente);
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(4)
    @DisplayName("4 - Lista wrapLista di varie attivita")
        //--nome attivita
        //--typeLista
    void listaWrapDidascalie(final String nomeAttivita, final AETypeLista type) {
        System.out.println("4 - Lista wrapLista di varie attivita");
        sorgente = nomeAttivita;
        int size;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        listWrapLista = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class).singolare(sorgente).listaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class).plurale(sorgente).listaWrap();
            default -> null;
        };

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s %s", listWrapLista.size(), sorgente, type.getTagLower());
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
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
    @MethodSource(value = "ATTIVITA")
    @Order(5)
    @DisplayName("5 - Key della mappa wrapLista di varie attività")
        //--nome attivita
        //--typeLista
    void mappaWrap(final String nomeAttivita, final AETypeLista type) {
        System.out.println("5 - Key della mappa wrapLista di varie attività");
        sorgente = nomeAttivita;
        int numVoci;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class).singolare(sorgente).mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'attività di %s %s", numVoci, type.getCivile(), sorgente);
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(6)
    @DisplayName("6 - Mappa wrapLista di varie attivita")
        //--nome attivita
        //--typeLista
    void mappaWrapDidascalie(final String nomeAttivita, final AETypeLista type) {
        System.out.println("6 - Mappa wrapLista di varie attivita");
        sorgente = nomeAttivita;
        int numVoci;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class).singolare(sorgente).mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s %s", numVoci, sorgente, type.getTagLower());
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    //    @Test
    @Order(7)
    @DisplayName("7 - nobiliTedeschi")
    void nobiliTedeschi() {
        System.out.println("7 - nobiliTedeschi");
        sorgente = "nobili";
        sorgente2 = "Tedeschi";
        int numVoci;
        List<WrapLista> listaSotto;

        mappaWrap = appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            listWrapLista = mappaWrap.get(sorgente2);
            numVoci = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s", numVoci, sorgente, sorgente2);
            System.out.println(message);

            //            listaSotto = new ArrayList<>();
            //
            //            for (WrapLista wrap : listWrapLista) {
            //                stringa;
            //            }

            sorgente3 = "C";
            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
            numVoci = listaSotto.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
            System.out.println(VUOTA);
            System.out.println(message);
            printSub(listaSotto);

            sorgente3 = "E";
            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
            numVoci = listaSotto.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
            System.out.println(VUOTA);
            System.out.println(message);
            printSub(listaSotto);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    private boolean valido(final String nomeAttivita, final AETypeLista type) {
        if (textService.isEmpty(nomeAttivita)) {
            System.out.println("Manca il nome dell'attività");
            return false;
        }

        if (type != AETypeLista.attivitaSingolare && type != AETypeLista.attivitaPlurale) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, ListaAttivita.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaSingolare && !attSingolareBackend.isExistByKey(nomeAttivita)) {
            message = String.format("L'attività singolare [%s] indicata NON esiste", nomeAttivita);
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaPlurale && !attPluraleBackend.isExistByKey(nomeAttivita)) {
            message = String.format("L'attività plurale [%s] indicata NON esiste", nomeAttivita);
            System.out.println(message);
            return false;
        }

        return true;
    }

    private void printAttivitaSingole(final AETypeLista type, final String nomeAttivita) {
        if (type == AETypeLista.attivitaPlurale) {
            List<String> listaAttivitaSingoleComprese = attPluraleBackend.findAllFromNomiSingolariByPlurale(nomeAttivita);
            message = String.format("Che raggruppa le %d attività singolari%s%s", listaAttivitaSingoleComprese.size(), FORWARD, listaAttivitaSingoleComprese);
            System.out.println(message);
        }
    }

}
