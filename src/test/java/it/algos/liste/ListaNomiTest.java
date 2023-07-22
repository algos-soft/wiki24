package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
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
                Arguments.of("Aaron"),
                Arguments.of("Alexandra"),
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
        super.setUpAll();
        super.clazz = ListaNomi.class;
        super.costruttoreNecessitaAlmenoUnParametro = true;
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
        System.out.println(("5 - listaBioSenzaParametroNelCostruttore"));
        System.out.println(VUOTA);

        try {
            appContext.getBean(ListaNomi.class).listaBio();
        } catch (Exception unErrore) {
            System.out.println(String.format("Non è possibile creare un'istanza della classe [%s] SENZA parametri", clazz != null ? clazz.getSimpleName() : VUOTA));
            System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazz != null ? clazz.getSimpleName() : VUOTA));
            System.out.println("È obbligatorio il 'nomeLista' nel costruttore.");
            System.out.println(String.format("Seguendo il Pattern Builder, non si può chiamare il metodo %s se l'istanza non è correttamente istanziata.", "listaBio"));
        }
    }


    @Test
    @Order(6)
    @DisplayName("6 - Istanza costruita col parametro obbligatorio")
    void beanStandardIncompleta() {
        System.out.println(String.format("6 - Istanza costruita col parametro obbligatorio", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("Il Pattern Builder NON richiede regolazioni aggiuntive");
        System.out.println("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
        System.out.println("Non ci sono parametri essenziali per il Pattern Builder");
        System.out.println("Pronta per listaBio(), listaWrap() e mappaWrap()");
        System.out.println(VUOTA);

        sorgente = "lorenzo";
        istanza = appContext.getBean(ListaNomi.class, sorgente);
        assertNotNull(istanza);
        assertTrue(istanza.isValida());

        printLista(istanza);
    }

    @Test
    @Order(7)
    @DisplayName("7 - listaBioSenzaTypeLista")
    void listaBioSenzaTypeLista() {
        System.out.println(("7 - listaBioSenzaTypeLista"));
        System.out.println(VUOTA);

        sorgente = "lorenzo";
        appContext.getBean(ListaNomi.class, sorgente).listaBio();

        System.out.println(String.format("Questa classe funziona anche SENZA '%s' perché è già inserito in fixPreferenze().", "typeLista"));
    }



    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
        //--nome
    void listaBio(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }

        listBio = appContext.getBean(ListaNomi.class, sorgente).listaBio();
        super.fixListaBio(sorgente, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
        //--nome
    void listaWrapDidascalie(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();
        super.fixWrapLista(sorgente, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
        //--nome
    void listaDidascalie(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();
        super.fixWrapListaDidascalie(sorgente, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
        //--nome
    void mappaWrap(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        super.fixMappaWrapKey(sorgente, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
        //--nome
    void mappaWrapDidascalie(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        super.fixMappaWrapDidascalie(sorgente, mappaWrap);
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