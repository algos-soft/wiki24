package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 24-Apr-2023
 * Time: 11:35
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("Upload Anni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadAnniTest extends UploadTest {

    /**
     * Classe principale di riferimento <br>
     */
    private UploadAnni istanza;


    //--nome anno
    //--typeCrono
    protected static Stream<Arguments> ANNI_UPLOAD() {
        return Stream.of(
                Arguments.of("43 marzo", AETypeLista.giornoNascita),
                Arguments.of("2002", AETypeLista.annoMorte),
                Arguments.of("560", AETypeLista.annoNascita),
                Arguments.of("560", AETypeLista.annoMorte),
                Arguments.of("1984", AETypeLista.annoNascita),
                Arguments.of("azeri", AETypeLista.attivitaSingolare)
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadAnni.class;
        super.backendClazzName = AnnoWikiBackend.class.getSimpleName();
        super.collectionName = "annoWiki";
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
        sorgente = "4 marzo";
        super.fixBeanStandard(sorgente);

        sorgente = "1967";
        super.fixBeanStandard(sorgente);
    }


    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "560";
        istanza = appContext.getBean(UploadAnni.class, sorgente);
        super.fixBuilderPatternUpload(istanza, AETypeLista.annoMorte);
    }


    @ParameterizedTest
    @Disabled()
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).esegue().mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @Disabled()
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @Disabled()
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(60)
    @DisplayName("60 - Testo header")
    void testoHeader(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).testoHeader();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @Disabled()
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(70)
    @DisplayName("70 - Testo body STANDARD con paragrafi e righe")
    void testoBody(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).testoBody();
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @Disabled()
    @Order(71)
    @DisplayName("71 - Testo body sottopagina")
    @CsvSource({"2005,febbraio"})
    void testoBodySottopagina(final String nomeLista, final String keyParagrafo) {
        AETypeLista typeLista = AETypeLista.annoMorte;

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(typeLista).listaWrap(keyParagrafo);
        sorgente = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
        ottenuto = appContext.getBean(UploadAnni.class, sorgente).typeLista(typeLista).test(true).sottoPagina(listWrapLista).testoBody();

        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @Disabled()
    @Order(72)
    @DisplayName("72 - Testo upload sottopagina")
    @CsvSource({"2005,febbraio"})
    void testoUploadSottopagina(final String nomeLista, final String keyParagrafo) {
        AETypeLista typeLista = AETypeLista.annoMorte;

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(typeLista).listaWrap(keyParagrafo);
        sorgente = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
        int ordineCategoriaSottopagina = AEMese.getOrder(keyParagrafo);

        ottenuto = appContext.getBean(UploadAnni.class, sorgente)
                .typeLista(typeLista)
                .test(true)
                .sottoPagina(listWrapLista)
                .ordineCategoriaSottopagina(ordineCategoriaSottopagina)
                .testoUpload();

        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @Disabled()
    @Order(73)
    @DisplayName("73 - Testo upload sottopagina")
    @CsvSource({"2005,Senza giorno specificato"})
    void testoUploadSottopagina2(final String nomeLista, final String keyParagrafo) {
        AETypeLista typeLista = AETypeLista.annoMorte;

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(typeLista).listaWrap(keyParagrafo);
        sorgente = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
        int ordineCategoriaSottopagina = AEMese.getOrder(keyParagrafo);
        if (ordineCategoriaSottopagina == 0 && keyParagrafo.equals(TAG_LISTA_NO_GIORNO)) {
            ordineCategoriaSottopagina = 13;
        }

        ottenuto = appContext.getBean(UploadAnni.class, sorgente)
                .typeLista(typeLista)
                .test(true)
                .sottoPagina(listWrapLista)
                .ordineCategoriaSottopagina(ordineCategoriaSottopagina)
                .testoUpload();

        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @Disabled()
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(80)
    @DisplayName("80 - Esegue upload test STANDARD")
    void esegue(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }
        System.out.println("80 - Esegue upload test STANDARD");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).test().upload();
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    @ParameterizedTest
    @Disabled()
    @Order(81)
    @DisplayName("81 - Esegue upload test pagine con sottopagine")
    @CsvSource({"2005"})
    void uploadTestSottoPagine(final String nomeLista) {
        AETypeLista typeLista = AETypeLista.annoMorte;

        ottenutoRisultato = appContext.getBean(UploadAnni.class, nomeLista).typeLista(typeLista).test().upload();
        assertTrue(ottenutoRisultato.isValido());
        printUpload(ottenutoRisultato);
    }

    @ParameterizedTest
    @Disabled("Test su pagine on line")
    @MethodSource(value = "ANNI_UPLOAD")
    @Order(90)
    @DisplayName("90 - Esegue upload REALE (attenzione)")
    void uploadReale(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }
        System.out.println("90 - Esegue upload REALE (attenzione)");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadAnni.class, nomeLista).typeLista(type).upload();
        printUpload(ottenutoRisultato);
    }


    @Test
    @Order(110)
    @DisplayName("110 - test su anno specifico")
    void annoSpecifico() {
        sorgente = "1986";
        AETypeLista type = AETypeLista.annoMorte;
        ottenutoRisultato = appContext.getBean(UploadAnni.class, sorgente).typeLista(type).test().upload();
    }

    private boolean valido(final String nomeAnno, final AETypeLista type) {
        if (textService.isEmpty(nomeAnno)) {
            System.out.println("Manca il nome dell'anno");
            return false;
        }

        if (type != AETypeLista.annoNascita && type != AETypeLista.annoMorte) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, UploadAnni.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (!annoWikiBackend.isExistByKey(nomeAnno)) {
            message = String.format("L'anno [%s] indicato NON esiste", nomeAnno);
            System.out.println(message);
            return false;
        }

        return true;
    }

}