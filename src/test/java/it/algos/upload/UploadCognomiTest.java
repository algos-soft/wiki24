package it.algos.upload;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.upload.liste.*;
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
 * Date: Thu, 13-Jul-2023
 * Time: 11:25
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("Upload Cognomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadCognomiTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadCognomi istanza;

    //--cognome
    protected static Stream<Arguments> COGNOMI_UPLOAD() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Mazzoni"),
                Arguments.of("Piazza"),
                Arguments.of("Gómez")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadCognomi.class;
        super.backendClazzName = CognomeBackend.class.getSimpleName();
        super.collectionName = "cognome";
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
    @DisplayName("7 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        //--costruisce un'istanza con un parametro e controlla che il valore sia accettabile per la collection
        sorgente = "4 aprile";
        super.fixBeanStandard(sorgente);

        sorgente = "Mazzoni";
        super.fixBeanStandard(sorgente);
    }

//    @Test
//    @Order(8)
//    @DisplayName("8 - esegueConParametroNelCostruttore")
//    void esegueConParametroNelCostruttore() {
//        sorgente = "Piazza";
//        super.fixConParametroNelCostruttore(sorgente);
//    }


    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "Piazza";
        istanza = appContext.getBean(UploadCognomi.class, sorgente);
        super.fixBuilderPatternUpload(istanza, AETypeLista.cognomi);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_UPLOAD")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadCognomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_UPLOAD")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadCognomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_UPLOAD")
    @Order(60)
    @DisplayName("60 - Testo header")
    void testoHeader(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadCognomi.class, nomeLista).esegue().testoHeader();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI_UPLOAD")
    @Order(70)
    @DisplayName("70 - Testo body STANDARD con paragrafi e righe")
    void testoBody(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadCognomi.class, nomeLista).testoBody();
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI_UPLOAD")
    @Order(80)
    @DisplayName("80 - Esegue upload test STANDARD")
    void esegue(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(UploadCognomi.class, nomeLista).test().upload();
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(330)
    @DisplayName("330 - Esegue upload sottoPagina")
    void listaDidascalieSottoPagina() {
        System.out.println("330 - Esegue upload sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Williams";
        sorgente2 = "calciatori";

        mappaWrap = appContext.getBean(UploadCognomi.class, sorgente).mappaWrap();
        sorgente = textService.primaMaiuscola(sorgente);
        sorgente2 = textService.primaMaiuscola(sorgente2);
        sorgente3 = UPLOAD_TITLE_DEBUG + sorgente + SLASH + sorgente2;
        listWrapLista = mappaWrap.get(textService.primaMaiuscola(sorgente2));
        assertNotNull(listWrapLista);

        ottenutoRisultato = appContext.getBean(UploadCognomi.class, sorgente3).test().sottoPagina(listWrapLista).upload();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Test del cognome '%s' con attività '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", wikiUtility.wikiTitleNomi(sorgente3)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + sorgente3));

        System.out.println(VUOTA);
        //        super.fixWrapListaDidascalie(sorgente3, listWrapLista);
    }

}