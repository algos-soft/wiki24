package it.algos.upload;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.upload.liste.*;
import org.junit.jupiter.api.*;

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
//@Tag("upload")
@DisplayName("Upload Giorni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadGiorniTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadGiorni istanza;

    //--nome
    protected static Stream<Arguments> GIORNI_UPLOAD() {
        return Stream.of(
                Arguments.of("43 marzo"),
                Arguments.of("29 febbraio"),
                Arguments.of("19 dicembra"),
                Arguments.of("4gennaio")
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

    //    @Test
    //    @Order(8)
    //    @DisplayName("8 - esegueConParametroNelCostruttore")
    //    void esegueConParametroNelCostruttore() {
    //        sorgente = "24 agosto";
    //        super.fixConParametroNelCostruttore(sorgente, "typeLista(), nascita()(, morte()");
    //    }


    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "29 febbraio";
        istanza = appContext.getBean(UploadGiorni.class, sorgente);
        super.fixBuilderPatternUpload(istanza, AETypeLista.giornoMorte);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadGiorni.class, nomeLista).morte().esegue().mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadGiorni.class, nomeLista).morte().mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(60)
    @DisplayName("60 - Testo header")
    void testoHeader(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadGiorni.class, nomeLista).morte().testoHeader();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(70)
    @DisplayName("70 - Testo body STANDARD con paragrafi e righe")
    void testoBody(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadGiorni.class, nomeLista).morte().testoBody();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_UPLOAD")
    @Order(80)
    @DisplayName("80 - Esegue upload test STANDARD")
    void esegue(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }
        System.out.println("80 - Esegue upload test STANDARD");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadGiorni.class, nomeLista).test().upload();
        printRisultato(ottenutoRisultato);
    }

}