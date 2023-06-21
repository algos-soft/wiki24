package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.upload.liste.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

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
@Tag("production")
@Tag("upload")
@DisplayName("Nomi upload")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadNomiTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadNomi istanza;


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

        istanza = new UploadNomi();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(UploadNomi.class);
        assertNotNull(istanza);
        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Upload test di un nome senza TOC")
    void uploadNoToc() {
        System.out.println("3 - Upload test di un nome senza TOC");
        sorgente = "adalberto";
        appContext.getBean(UploadNomi.class,sorgente).noToc().test().upload();
    }
    @Test
    @Order(4)
    @DisplayName("4 - Upload test di un nome con TOC")
    void uploadToc() {
        System.out.println("4 - Upload test di un nome con TOC");
        sorgente = "adalberto";
        appContext.getBean(UploadNomi.class,sorgente).forceToc().test().upload();
    }

    @Test
    @Order(5)
    @DisplayName("5 - Upload test di un nome")
    void upload() {
        System.out.println("5 - Upload test di un nome");
        sorgente = "aaron";
        appContext.getBean(UploadNomi.class,sorgente).forceToc().test().upload();
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