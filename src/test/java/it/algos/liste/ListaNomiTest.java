package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

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
@DisplayName("Lista Nomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaNomiTest extends ListeTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaNomi istanza;

    //--nome
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(VUOTA),
                //                Arguments.of("Aaron"),
                //                Arguments.of("Alexandra"),
                Arguments.of("adriana"),
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
        super.clazz = ListaNomi.class;
        super.setUpAll();
        super.costruttoreNecessitaAlmenoUnParametro = true;
        super.istanzaValidaSubitoDopoCostruttore = true;
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
    @Order(6)
    @DisplayName("6 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "lorenzo";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(7)
    @DisplayName("7 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "lorenzo";
        super.fixConParametroNelCostruttore(sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listBio = appContext.getBean(ListaNomi.class, nomeLista).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void listaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, nomeLista).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, nomeLista).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    //    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(121)
    @DisplayName("121 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true")
    //--nome
    void listaWrapDidascalie2(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.nessunLink).listaWrap();
        System.out.println("121 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(122)
    @DisplayName("122 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true")
    //--nome
    void listaWrapDidascalie3(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).listaWrap();
        System.out.println("122 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(123)
    @DisplayName("123- WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true")
    //--nome
    void listaWrapDidascalie4(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).listaWrap();
        System.out.println("123 - WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(124)
    @DisplayName("124- WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false")
    //--nome
    void listaWrapDidascalie6(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext
                .getBean(ListaNomi.class, sorgente)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();
        System.out.println("124 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


}