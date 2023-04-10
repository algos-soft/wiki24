package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.moduli.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 09-Apr-2023
 * Time: 19:46
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("uploadModulo")
@DisplayName("UploadModuloLinkAttivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadModuloLinkAttivitaTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadModuloLinkAttivita istanza;


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
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);

        istanza = new UploadModuloLinkAttivita();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(UploadModuloLinkAttivita.class);
        assertNotNull(istanza);
        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Titolo pagina wiki del modulo")
    void getWikiTitleModulo() {
        System.out.println(("3 - Titolo pagina wiki del modulo"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(UploadModuloLinkAttivita.class);
        assertNotNull(istanza);
        ottenuto = istanza.getWikiTitleModulo();
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Titolo della pagina wiki da cui estrarre il modulo%s'%s'", FORWARD, ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Titolo pagina test su cui scrivere il modulo ordinato")
    void getWikiTitleUpload() {
        System.out.println(("4 - Titolo pagina test su cui scrivere il modulo ordinato"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(UploadModuloLinkAttivita.class);
        assertNotNull(istanza);
        ottenuto = istanza.getWikiTitleUpload();
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Titolo della pagina test su cui scrivere il modulo ordinato%s'%s'", FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Testo della pagina wiki del modulo")
    void getTextPagina() {
        System.out.println(("5 - Testo della pagina wiki del modulo"));
        System.out.println(VUOTA);

        ottenuto = appContext.getBean(UploadModuloLinkAttivita.class).leggeTestoPagina();
        assertTrue(textService.isValid(ottenuto));
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Testo del modulo")
    void leggeTestoModulo() {
        System.out.println(("6 - Testo del modulo"));
        System.out.println(VUOTA);

        ottenuto = appContext.getBean(UploadModuloLinkAttivita.class).leggeTestoModulo();
        assertTrue(textService.isValid(ottenuto));
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Mappa dei dati del modulo")
    void leggeMappa() {
        System.out.println(("7 - Mappa dei dati del modulo"));
        System.out.println(VUOTA);

        mappaOttenuta = appContext.getBean(UploadModuloLinkAttivita.class).leggeMappa();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @Test
    @Order(8)
    @DisplayName("8 - Mappa ordinata dei dati del modulo")
    void getMappaOrdinata() {
        System.out.println(("8 - Mappa ordinata dei dati del modulo"));
        System.out.println(VUOTA);

        mappaOttenuta = appContext.getBean(UploadModuloLinkAttivita.class).getMappaOrdinata();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }

    @Test
    @Order(9)
    @DisplayName("9 - Upload mappa ordinata su pagina di test")
    void uploadOrdinatoSenzaModifiche() {
        System.out.println(("9 - Upload mappa ordinata su pagina di test"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadModuloLinkAttivita.class).uploadOrdinatoSenzaModifiche();
        printRisultato(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }

}
