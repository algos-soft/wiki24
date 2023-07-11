package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.liste.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

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
@Tag("uploadcrono")
@DisplayName("Giorni upload")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadGiorniTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadGiorni istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        assertNull(istanza);
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
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new UploadGiorni();
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Upload test di un giorno di nascita")
    void uploadNascita() {
        System.out.println("2 - Upload test di un giorno di nascita");
        sorgente = "24 aprile";
        ottenutoRisultato = appContext.getBean(UploadGiorni.class).test().typeCrono(AETypeLista.giornoNascita).upload(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Upload della pagina di test su %s", UPLOAD_TITLE_DEBUG + sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Upload test di un giorno di morte")
    void uploadMorte() {
        System.out.println("3 - Upload test di un giorno di morte");
        sorgente = "24 aprile";
        ottenutoRisultato = appContext.getBean(UploadGiorni.class).test().morte().upload(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Upload della pagina di test su %s", UPLOAD_TITLE_DEBUG + sorgente));
        printRisultato(ottenutoRisultato);
    }


//    @Test
    @Order(4)
    @DisplayName("4 - Costruttore col giorno di nascita")
    void uploadCostruttoreNascita() {
        System.out.println(" - Costruttore col giorno di nascita");
        sorgente = "24 aprile";
        ottenutoRisultato = appContext.getBean(UploadGiorni.class, sorgente).nascita().test().esegue();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Upload della pagina di test su %s", UPLOAD_TITLE_DEBUG + sorgente));
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