package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.upload.liste.*;
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

import java.util.*;
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
    protected static Stream<Arguments> COGNOMI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA),
//                Arguments.of("Battaglia"),
                Arguments.of("Camweron"),
//                Arguments.of("Cameron"),
//                Arguments.of("cameron"),
//                Arguments.of("Capri"),
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
        super.backendClazzName = CognomeBackend.class.getSimpleName();
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
    @Order(7)
    @DisplayName("7 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "Brambilla";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "1876";
        super.fixBeanStandard(sorgente);

        System.out.println(VUOTA);

        sorgente = "Rossi";
        super.fixConParametroNelCostruttore(sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
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
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void wrapLista(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).listaWrap();
        super.fixWrapLista(sorgente, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
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
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrapKey(final String sorgente) {
        if (textService.isEmpty(sorgente)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        super.fixMappaWrapKey(sorgente, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(120)
    @DisplayName("120 - WrapLista ALTERNATIVA")
    void wrapListaAlternativa(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaCognomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapLista(nomeLista, listWrapLista, "120 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(130)
    @DisplayName("130 - Didascalie ALTERNATIVE")
    void listaDidascalieAlternative(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaCognomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapListaDidascalie(nomeLista, listWrapLista, "130 - Lista ALTERNATIVA didascalie con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(150)
    @DisplayName("150 - MappaWrap ALTERNATIVA")
    void mappaWrapAlternativa(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaCognomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "150 - MappaWrap ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_LISTA")
    @Order(151)
    @DisplayName("151 - MappaWrap ALTERNATIVA(2)")
    void mappaWrapAlternativa2(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaCognomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkLista)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "151 - MappaWrap ALTERNATIVA(2) con linkParagrafi=linkLista e linkCrono=linkVoce e usaIcona=false");
    }


    @Test
    @Order(220)
    @DisplayName("220 - WrapLista di sottoPagina")
    void listaWrapSottoPagina() {
        System.out.println("220 - WrapLista di sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Williams";
        sorgente2 = "calciatori";
        sorgente3 = UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente) + SLASH + textService.primaMaiuscola(sorgente2);
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(textService.primaMaiuscola(sorgente2));
        assertNotNull(listWrapLista);

        System.out.println(VUOTA);
        System.out.println(String.format("Test del cognome '%s' con attività '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", wikiUtility.wikiTitleNomi(sorgente + SLASH + sorgente2)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente + SLASH + sorgente2)));

        System.out.println(VUOTA);
        super.fixWrapLista(sorgente3, listWrapLista);
    }

    @Test
    @Order(230)
    @DisplayName("230 - Didascalie sottoPagina")
    void listaDidascalieSottoPagina() {
        System.out.println("230 - Didascalie sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Williams";
        sorgente2 = "calciatori";
        sorgente3 = UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente) + SLASH + textService.primaMaiuscola(sorgente2);
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(textService.primaMaiuscola(sorgente2));
        assertNotNull(listWrapLista);

        System.out.println(VUOTA);
        System.out.println(String.format("Test del cognome '%s' con attività '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", wikiUtility.wikiTitleNomi(sorgente + SLASH + sorgente2)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente + SLASH + sorgente2)));

        System.out.println(VUOTA);
        super.fixWrapListaDidascalie(sorgente3, listWrapLista);
    }


}




