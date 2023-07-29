package it.algos.upload;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.upload.liste.*;
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
 * Time: 09:18
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("Upload Nomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadNomiTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadNomi istanza;

    //--nome
    protected static Stream<Arguments> NOMI_UPLOAD() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Silvana"),
                //                Arguments.of("Akira"),
                //                Arguments.of("Alexandra"),
                Arguments.of("Tomas"),
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
        super.clazz = UploadNomi.class;
        super.backendClazzName = NomeBackend.class.getSimpleName();
        super.collectionName = "nome";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
        super.istanzaValidaSubitoDopoCostruttore = false;
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

//    @Test
//    @Order(8)
//    @DisplayName("8 - esegueConParametroNelCostruttore")
//    void esegueConParametroNelCostruttore() {
//        sorgente = "tiziano";
//        super.fixConParametroNelCostruttore(sorgente);
//    }

    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "amalia";
        istanza = appContext.getBean(UploadNomi.class, sorgente);
        super.fixBuilderPatternUpload(istanza, AETypeLista.nomi);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_UPLOAD")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_UPLOAD")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(UploadNomi.class, nomeLista).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_UPLOAD")
    @Order(60)
    @DisplayName("60 - Testo header")
    void testoHeader(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadNomi.class, nomeLista).esegue().testoHeader();
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI_UPLOAD")
    @Order(70)
    @DisplayName("70 - Testo body STANDARD con paragrafi e righe")
    void testoBody(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        ottenuto = appContext.getBean(UploadNomi.class, nomeLista).testoBody();
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI_UPLOAD")
    @Order(80)
    @DisplayName("80 - Esegue upload test STANDARD")
    void esegue(final String nomeLista) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }
        System.out.println("80 - Esegue upload test STANDARD");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadNomi.class, nomeLista).test().upload();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(330)
    @DisplayName("330 - Esegue upload sottoPagina")
    void esegueSottoPaginaIsolata() {
        System.out.println("330 - Esegue upload sottoPagina");
        System.out.println(VUOTA);

        sorgente = "adam";
        sorgente2 = "calciatori";

        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        sorgente = textService.primaMaiuscola(sorgente);
        sorgente2 = textService.primaMaiuscola(sorgente2);
        sorgente3 = UPLOAD_TITLE_DEBUG + sorgente + SLASH + sorgente2;
        listWrapLista = mappaWrap.get(textService.primaMaiuscola(sorgente2));
        assertNotNull(listWrapLista);

        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente3).test().sottoPagina(listWrapLista).upload();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Test del nome '%s' con attività '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", wikiUtility.wikiTitleNomi(sorgente3)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + sorgente3));

        System.out.println(VUOTA);
    }

    @Test
    @Order(340)
    @DisplayName("340 - Esegue upload pagina con sottoPagina")
    void eseguePaginaConSottoPagina() {
        System.out.println("80 - Esegue upload pagina con sottoPagina");
        System.out.println(VUOTA);

        sorgente = "diego";
        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().upload();
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(3)
    @DisplayName("3 - Upload test di un nome con noToc")
    void uploadNoToc() {
        System.out.println("3 - Upload test di un nome con noToc");
        System.out.println(VUOTA);

        sorgente = "Adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).noToc().test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(4)
    @DisplayName("4 - Upload test di un nome con forceTOC")
    void uploadForceToc() {
        System.out.println("4 - Upload test di un nome con forceTOC");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).forceToc().test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(5)
    @DisplayName("5 - Upload test di un nome senza numeri paragrafo")
    void uploadNoNumVoci() {
        System.out.println("5 - Upload test di un nome senza numeri paragrafo");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).noNumVoci().test().esegue();

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(6)
    @DisplayName("6 - Upload test di un nome con numeri paragrafo")
    void uploadSiNumVoci() {
        System.out.println("6 - Upload test di un nome con numeri paragrafo");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).siNumVoci().test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(7)
    @DisplayName("7 - Upload test di un nome con con typeLink=linkLista")
    void uploadLinkLista() {
        System.out.println("7 - Upload test di un nome con con typeLink=linkLista");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(8)
    @DisplayName("8 - Upload test di un nome con typeLink=linkVoce")
    void uploadLinkVoce() {
        System.out.println("8 - Upload test di un nome con typeLink=linkVoce");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(9)
    @DisplayName("9 - Upload test di un nome inesistente (senza voci)")
    void uploadInesistente() {
        System.out.println("9 - Upload test di un nome inesistente (senza voci)");
        System.out.println(VUOTA);

        sorgente = "questoNomeNonEsiste";
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        assertFalse(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println("Nome non esistente - Sicuramente 0 elementi");
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(10)
    @DisplayName("10 - Upload test di un nome standard")
    void upload() {
        System.out.println("10 - Upload test di un nome standard");
        System.out.println(VUOTA);

        sorgente = "adalberto";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(11)
    @DisplayName("11 - Upload test di una sottopagina da sola")
    void uploadOnlySottoPagina() {
        System.out.println("11 - Upload test di una sottopagina da sola");
        System.out.println(VUOTA);

        sorgente = "adam";
        sorgente2 = "calciatori";
        sorgente3 = UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente) + SLASH + textService.primaMaiuscola(sorgente2);
        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        List<WrapLista> lista = mappaWrap.get(textService.primaMaiuscola(sorgente2));

        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente3).sottoPagina(lista).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista con sottopagina - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(12)
    @DisplayName("12 - Upload test di un nome con sottopagina")
    void uploadSottoPagina() {
        System.out.println("12 - Upload test di un nome con sottopagina");
        System.out.println(VUOTA);

        sorgente = "adam";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista con sottopagina - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(31)
    @DisplayName("31 - Upload test di un nome con linkCrono=nessunLink")
    void uploadLink() {
        System.out.println("31 - Upload test di un nome linkCrono=nessunLink");
        System.out.println(VUOTA);

        sorgente = "adalberto";

        AETypeLink typeLinkCronoOld = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        WPref.linkCrono.setValue(AETypeLink.nessunLink);
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        WPref.linkCrono.setValue(typeLinkCronoOld);

        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //        @Test
    @Order(32)
    @DisplayName("32 - Upload test di un nome con linkCrono=linkVoce")
    void uploadVoce() {
        System.out.println("32 - Upload test di un nome linkCrono=linkVoce");
        System.out.println(VUOTA);

        sorgente = "adalberto";

        AETypeLink typeLinkCronoOld = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        WPref.linkCrono.setValue(AETypeLink.linkVoce);
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        WPref.linkCrono.setValue(typeLinkCronoOld);

        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(40)
    @DisplayName("40 - Upload test di un nome femminile")
    void uploadFemminile() {
        System.out.println("40 - Upload test di un nome femminile");
        System.out.println(VUOTA);

        sorgente = "adriana";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di piccole dimensioni - Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);

        message += String.format("Controllate=87 voci. %s", AETypeTime.millisecondi.message(ottenutoRisultato));
        logService.info(new WrapLog().message(message).type(AETypeLog.upload));
    }

    //        @Test
    @Order(50)
    @DisplayName("50 - Upload test di un nome grosso con sottopagina")
    void uploadSotto() {
        System.out.println("50 - Upload test di un nome grosso con sottopagina");

        sorgente = "giovanni";
        ottenutoIntero = appContext.getBean(ListaNomi.class, sorgente).getSize();
        //        ottenutoRisultato = appContext.getBean(UploadNomi.class, sorgente).test().esegue();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(String.format("Test del nome %s", sorgente));
        System.out.println(String.format("Lista di grosse dimensioni con sottopagina- Probabilmente %d elementi", ottenutoIntero));
        System.out.println(String.format("Titolo della voce: %s", wikiUtility.wikiTitleNomi(sorgente)));
        System.out.println(String.format("Pagina di test: %s", UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente)));

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(40)
    @DisplayName("40 - Upload all")
    void uploadAll() {
        System.out.println("40 - Upload all");

        ottenutoRisultato = appContext.getBean(UploadNomi.class).uploadAll();
        assertTrue(ottenutoRisultato.isValido());

        System.out.println("Upload di tutti i nomi");

        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}