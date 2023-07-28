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
@Tag("upload")
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
                Arguments.of("23 febbraio"),
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
        super.ammessoCostruttoreVuoto = true;
        super.istanzaValidaSubitoDopoCostruttore = false;
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
        System.out.println("9 - Metodi builderPattern per validare l'istanza");

        sorgente = "10 novembre";
        istanza = appContext.getBean(UploadGiorni.class, sorgente);
        super.debug(istanza, VUOTA);

        sorgente = "11 novembre";
        appContext.getBean(UploadGiorni.class, sorgente).mappaWrap();
        super.debug(istanza, VUOTA);

        sorgente = "12 novembre";
        appContext.getBean(UploadGiorni.class, sorgente).esegue();
        super.debug(istanza, VUOTA);

        sorgente = "13 novembre";
        appContext.getBean(UploadGiorni.class, sorgente).upload();
        super.debug(istanza, VUOTA);

        sorgente = "21 novembre";
        sorgente2 = "nascita()";
        istanza = appContext.getBean(UploadGiorni.class, sorgente).nascita();
        super.debug(istanza, sorgente2);

        sorgente = "22 novembre";
        sorgente2 = "morte()";
        istanza = appContext.getBean(UploadGiorni.class, sorgente).morte();
        super.debug(istanza, sorgente2);

        sorgente = "23 novembre";
        sorgente2 = "typeLista(AETypeLista.giornoNascita)";
        istanza = appContext.getBean(UploadGiorni.class, sorgente).typeLista(AETypeLista.giornoNascita);
        super.debug(istanza, sorgente2);

        sorgente = "24 novembre";
        sorgente2 = "typeLista(AETypeLista.giornoMorte)";
        istanza = appContext.getBean(UploadGiorni.class, sorgente).typeLista(AETypeLista.giornoMorte);
        super.debug(istanza, sorgente2);

        sorgente = "25 novembre";
        sorgente2 = "typeLista(AETypeLista.attivitaSingolare)";
        istanza = appContext.getBean(UploadGiorni.class, sorgente).typeLista(AETypeLista.attivitaSingolare);
        super.debug(istanza, sorgente2);
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

}