package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.wrapper.*;
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
 * Time: 17:33
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Lista Cognomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaCognomiTest extends ListeTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaCognomi istanza;

    //--cognome
    protected static Stream<Arguments> COGNOMI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Battaglia"),
                Arguments.of("Camweron"),
                Arguments.of("Cameron"),
                Arguments.of("cameron"),
                Arguments.of("Capri"),
                Arguments.of("Gomez")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaCognomi.class;
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
        sorgente = "Gomez";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(7)
    @DisplayName("7 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "Gomez";
        super.fixConParametroNelCostruttore(sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }

        listBio = appContext.getBean(ListaCognomi.class, sorgente).listaBio();
        super.fixListaBio(sorgente, listBio);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void listaWrapDidascalie(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).listaWrap();
        super.fixWrapLista(sorgente, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).listaWrap();
        super.fixWrapListaDidascalie(sorgente, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        super.fixMappaWrapKey(sorgente, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }


    //    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(121)
    @DisplayName("121 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true")
    //--cognome
    void listaWrapDidascalie2(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.nessunLink).listaWrap();
        System.out.println("121 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(122)
    @DisplayName("122 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true")
    //--cognome
    void listaWrapDidascalie3(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).listaWrap();
        System.out.println("122 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(123)
    @DisplayName("123- WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true")
    //--cognome
    void listaWrapDidascalie4(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).listaWrap();
        System.out.println("123 - WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(124)
    @DisplayName("124- WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false")
    //--cognome
    void listaWrapDidascalie6(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext
                .getBean(ListaCognomi.class, sorgente)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();
        System.out.println("124 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

}




