package it.algos.liste;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
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
    protected static Stream<Arguments> NOMI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA),
                //                Arguments.of("Aaron"),
                Arguments.of("Andrew"),
                Arguments.of("Anton"),
                //                Arguments.of("Alexandra"),
                Arguments.of("adriana"),
                Arguments.of("maria teresa")
        );
    }


    //--nome
    protected static Stream<Arguments> NOMI_DOPPI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Andrew"),
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
        super.backendClazzName = NomeBackend.class.getSimpleName();
        super.collectionName = "nome";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
        super.istanzaValidaSubitoDopoCostruttore = true;
        super.metodiDaRegolare = "(nessuno)";
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
    @DisplayName("7 - Istanza/e STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        //--costruisce un'istanza con un parametro e controlla che il valore sia accettabile per la collection
        sorgente = "1876";
        super.fixBeanStandard(sorgente);

        sorgente = "adriana";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        super.fixBuilderPatternIniziale();

        sorgente = "amalia";
        istanza = appContext.getBean(ListaNomi.class, sorgente);
        super.fixBuilderPatternListe(istanza, AETypeLista.nomi);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
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
    @MethodSource(value = "NOMI_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void wrapLista(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, nomeLista).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
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
    @MethodSource(value = "NOMI_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrapKey(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
    @Order(120)
    @DisplayName("120 - WrapLista ALTERNATIVA")
    void wrapListaAlternativa(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaNomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapLista(nomeLista, listWrapLista, "120 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
    @Order(130)
    @DisplayName("130 - Didascalie ALTERNATIVE")
    void listaDidascalieAlternative(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaNomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapListaDidascalie(nomeLista, listWrapLista, "130 - Lista ALTERNATIVA didascalie con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
    @Order(150)
    @DisplayName("150 - MappaWrap ALTERNATIVA")
    void mappaWrapAlternativa(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaNomi.class, nomeLista)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "150 - MappaWrap ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_LISTA")
    @Order(151)
    @DisplayName("151 - MappaWrap ALTERNATIVA(2)")
    void mappaWrapAlternativa2(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaNomi.class, nomeLista)
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

        sorgente = "Anton";
        sorgente2 = "Calciatori";

        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(sorgente2);

        sorgente3 = sorgente + SLASH + sorgente2;
        this.printSotto(sorgente, sorgente2, wikiUtility.wikiTitleNomi(sorgente3), listWrapLista);
        super.fixWrapLista(sorgente3, listWrapLista);
    }


    @Test
    @Order(230)
    @DisplayName("230 - Didascalie sottoPagina")
    void listaDidascalieSottoPagina() {
        System.out.println("230 - Didascalie sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Adam";
        sorgente2 = "Calciatori";

        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(sorgente2);

        sorgente3 = sorgente + SLASH + sorgente2;
        this.printSotto(sorgente, sorgente2, wikiUtility.wikiTitleNomi(sorgente3), listWrapLista);
        super.fixWrapListaDidascalie(sorgente3, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_DOPPI")
    @Order(3)
    @DisplayName("410 - Lista di nomi nel database - singoli/doppi")
    void nomiDoppi(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }
        System.out.println("410 - Lista di nomi nel database - singoli/doppi");
        System.out.println(VUOTA);
        listBio = bioService.fetchNomi(nomeLista);
        assertNotNull(listBio);
        printBio(listBio);
    }

    void printSotto(String sorgente, String sorgente2, String sottoTitolo, List<WrapLista> listWrapLista) {
        String sorgente3 = UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente) + SLASH + textService.primaMaiuscola(sorgente2);

        assertNotNull(listWrapLista);

        System.out.println(VUOTA);
        System.out.println(String.format("Test del nome '%s' con attività '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", sottoTitolo));
        System.out.println(String.format("Pagina di test: %s", sorgente3));

        System.out.println(VUOTA);
    }

}