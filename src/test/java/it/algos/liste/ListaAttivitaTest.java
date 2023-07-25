package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
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
@Tag("liste")
@DisplayName("Lista Attivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAttivitaTest extends ListeTest {

    /**
     * Classe principale di riferimento <br>
     */
    private ListaAttivita istanza;

    //--nome attività
    //--typeLista
    protected static Stream<Arguments> ATTIVITA_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.listaBreve),
                Arguments.of(VUOTA, AETypeLista.nazionalitaSingolare),
                Arguments.of("soprano", AETypeLista.giornoNascita),
                //                Arguments.of("soprano", AETypeLista.attivitaSingolare),
                Arguments.of("abate", AETypeLista.attivitaSingolare),
                Arguments.of("badessa", AETypeLista.attivitaSingolare),
                Arguments.of("abati e badesse", AETypeLista.attivitaPlurale),
                //                Arguments.of("bassisti", AETypeLista.attivitaPlurale),
                Arguments.of("allevatori", AETypeLista.attivitaPlurale),
                Arguments.of("agenti segreti", AETypeLista.attivitaPlurale),
                Arguments.of("romanziere", AETypeLista.attivitaSingolare),
                //                Arguments.of("dogi", AETypeLista.attivitaPlurale),
                Arguments.of("accademici", AETypeLista.attivitaPlurale)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAttivita.class;
        super.backendClazzName = AttPluraleBackend.class.getSimpleName();
        super.setUpAll();
        super.costruttoreNecessitaAlmenoUnParametro = true;
        super.istanzaValidaSubitoDopoCostruttore = false;
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
    @Order(7)
    @DisplayName("7 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "accademici";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "scrittori";
        super.fixConParametroNelCostruttore(sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listBio = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void listaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }




//    //    @ParameterizedTest
//    @MethodSource(value = "ATTIVITA")
//    @Order(113)
//    @DisplayName("113 - Lista bio")
//    //--nome attivita
//    //--typeLista
//    void listaBio2(final String nomeAttivita, final AETypeLista type) {
//        sorgente = nomeAttivita;
//        if (textService.isEmpty(nomeAttivita)) {
//            return;
//        }
//
//        listBio = switch (type) {
//            case attivitaSingolare -> appContext.getBean(ListaAttivita.class, sorgente).singolare().listaBio();
//            case attivitaPlurale -> appContext.getBean(ListaAttivita.class, sorgente).listaBio();
//            default -> null;
//        };
//        System.out.println("113- Lista bio");
//
//        if (listBio != null && listBio.size() > 0) {
//            message = String.format("Ci sono %d biografie che implementano l'attività %s %s", listBio.size(), type.getTagLower(), sorgente);
//            System.out.println(message);
//            this.printAttivitaSingole(type, sorgente);
//            System.out.println(VUOTA);
//            printBioLista(listBio);
//        }
//        else {
//            message = "La listBio è nulla";
//            System.out.println(message);
//        }
//    }
//
//    //    @Test
//    @Order(117)
//    @DisplayName("117 - nobiliTedeschi")
//    void nobiliTedeschi() {
//        System.out.println("117 - nobiliTedeschi");
//        sorgente = "nobili";
//        sorgente2 = "Tedeschi";
//        int numVoci;
//        List<WrapLista> listaSotto;
//
//        //        mappaWrap = appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();
//
//        if (mappaWrap != null && mappaWrap.size() > 0) {
//            listWrapLista = mappaWrap.get(sorgente2);
//            numVoci = listWrapLista.size();
//            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s", numVoci, sorgente, sorgente2);
//            System.out.println(message);
//
//            //            listaSotto = new ArrayList<>();
//            //
//            //            for (WrapLista wrap : listWrapLista) {
//            //                stringa;
//            //            }
//
//            sorgente3 = "C";
//            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
//            numVoci = listaSotto.size();
//            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
//            System.out.println(VUOTA);
//            System.out.println(message);
//            printSub(listaSotto);
//
//            sorgente3 = "E";
//            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
//            numVoci = listaSotto.size();
//            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
//            System.out.println(VUOTA);
//            System.out.println(message);
//            printSub(listaSotto);
//        }
//        else {
//            message = "La mappa è nulla";
//            System.out.println(message);
//        }
//    }


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
