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
@Tag("liste")
@DisplayName("Lista Nazionalita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaNazionalitaTest extends ListeTest {


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
        super.clazz = ListaNazionalita.class;
        super.costruttoreNecessitaAlmenoUnParametro = true;
        super.istanzaValidaSubitoDopoCostruttore = false;
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
    @Order(5)
    @DisplayName("5 - listaBioSenzaParametroNelCostruttore")
    void listaBioSenzaParametroNelCostruttore() {
        try {
            appContext.getBean(ListaNazionalita.class).listaBio();
        } catch (Exception unErrore) {
            super.fixSenzaParametroNelCostruttore();
        }
    }


    @Test
    @Order(6)
    @DisplayName("6 - Istanza costruita col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "assiri";
        istanza = appContext.getBean(ListaNazionalita.class, sorgente);

        super.fixBeanStandard(istanza);
        assertEquals(super.istanzaValidaSubitoDopoCostruttore, istanza.isValida());
        printLista(istanza);
    }

    @Test
    @Order(7)
    @DisplayName("7 - listaBioSenzaTypeLista")
    void listaBioSenzaTypeLista() {
        sorgente = "assiri";
        appContext.getBean(ListaNazionalita.class, sorgente).listaBio();

        super.fixListaBioSenzaTypeLista();
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listBio = appContext.getBean(ListaNazionalita.class, nomeLista).typeLista(type).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void listaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNazionalita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNazionalita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNazionalita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNazionalita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
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