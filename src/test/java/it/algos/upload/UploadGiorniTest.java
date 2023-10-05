package it.algos.upload;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.giorno.*;
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
 * Date: Wed, 08-Mar-2023
 * Time: 19:42
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("Upload Giorni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadGiorniTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadGiorni istanza;

    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> GIORNI_UPLOAD() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.giornoNascita),
                Arguments.of("43 marzo", AETypeLista.giornoNascita),
                Arguments.of("12 ottobre", AETypeLista.annoMorte),
                Arguments.of("21 febbraio", AETypeLista.giornoMorte)
                //                Arguments.of("1º gennaio", AETypeLista.giornoNascita),
                //                Arguments.of("29 febbraio", AETypeLista.giornoNascita),
                //                Arguments.of("29 febbraio", AETypeLista.giornoMorte),
                //                Arguments.of("3 luglio", AETypeLista.attivitaSingolare),
                //                Arguments.of("19 dicembra", AETypeLista.giornoNascita),
                //                Arguments.of("4gennaio", AETypeLista.giornoNascita)
        );
    }

    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> GIORNI_UPLOAD_SOTTO_PAGINE() {
        return Stream.of(
                Arguments.of("1º gennaio", AETypeLista.giornoNascita),
                Arguments.of("1º gennaio", AETypeLista.giornoMorte),
                Arguments.of("2 gennaio", AETypeLista.giornoNascita),
                Arguments.of("2 gennaio", AETypeLista.giornoMorte)
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadGiorni.class;
        super.backendClazzName = GiornoWikiBackend.class.getSimpleName();
        super.collectionName = "giornoWiki";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
        super.istanzaValidaSubitoDopoCostruttore = false;
        super.metodiDaRegolare += ", nascita(), morte()";
        super.metodiBuilderPattern += ", nascita(), morte()";
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
        sorgente = "Mazzoni";
        super.fixBeanStandard(sorgente);

        sorgente = "9 giugno";
        super.fixBeanStandard(sorgente);
    }


    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "29 febbraio";
        istanza = appContext.getBean(UploadGiorni.class, sorgente);
        super.fixBuilderPatternUpload(istanza, AETypeLista.giornoMorte);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).esegue().mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(60)
    @DisplayName("60 - Testo header")
    void testoHeader(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).testoHeader();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(70)
    @DisplayName("70 - Testo body STANDARD con paragrafi e righe")
    void testoBody(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).testoBody();
        System.out.println(ottenuto);
    }


//    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(80)
    @DisplayName("80 - Esegue upload test STANDARD")
    void uploadTest(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).test().upload();
        assertTrue(ottenutoRisultato.isValido());
        printUpload(ottenutoRisultato);
    }

    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD_SOTTO_PAGINE")
    @Order(81)
    @DisplayName("81 - Esegue upload test pagine con sottopagine")
    void uploadTestSottoPagine(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).test().upload();
        assertTrue(ottenutoRisultato.isValido());
        printUpload(ottenutoRisultato);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(90)
    @DisplayName("90 - Esegue upload REALE (attenzione)")
    void uploadReale(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }
        System.out.println("90 - Esegue upload REALE (attenzione)");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadGiorni.class, nomeLista).typeLista(type).upload();
        printUpload(ottenutoRisultato);
    }


    //    @Test
    @Order(331)
    @DisplayName("331 - Esegue upload sottoPagina (keyParagrafo)")
    void esegueSottoPaginaIsolata2() {
        System.out.println("331 - Esegue upload sottoPagina (keyParagrafo)");
        System.out.println(VUOTA);

        sorgente = "1º gennaio";
        sorgente2 = "XVIII secolo";
        sorgente3 = sorgente + SLASH + sorgente2;

        ottenutoRisultato = appContext.getBean(UploadGiorni.class, sorgente)
                .typeLista(AETypeLista.giornoNascita)
                .sottoPagina(sorgente2)
                .test()
                .upload();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Test del giorno '%s' con secolo '%s'", sorgente, sorgente2));
        System.out.println(String.format("Titolo della sottopagina: %s", wikiUtility.wikiTitleNatiGiorno(sorgente3)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + sorgente3));

        System.out.println(VUOTA);
    }

    private boolean valido(final String nomeGiorno, final AETypeLista type) {
        if (textService.isEmpty(nomeGiorno)) {
            System.out.println("Manca il nome del giorno");
            return false;
        }

        if (type != AETypeLista.giornoNascita && type != AETypeLista.giornoMorte) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, UploadGiorni.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (!giornoWikiBackend.isExistByKey(nomeGiorno)) {
            message = String.format("Il giorno [%s] indicato NON esiste", nomeGiorno);
            System.out.println(message);
            return false;
        }

        return true;
    }

}