package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.upload.liste.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 09-Jun-2022
 * Time: 14:22
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("production")
//@Tag("upload")
@DisplayName("Upload Attività")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadAttivitaTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadAttivita istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadAttivita.class;
        super.backendClazzName = AttPluraleBackend.class.getSimpleName();
        super.collectionName = "attplurale";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
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


//    @Test
    @Order(1)
    @DisplayName("1- Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new UploadAttivita();
        assertNotNull(istanza);
        System.out.println(("1- Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


//    @Test
    @Order(2)
    @DisplayName("2 - Upload test di una attività plurale con TOC (default)")
    void upload() {
        System.out.println("2 - Upload test di una attività plurale con TOC (default)");
        sorgente = "agenti segreti";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);
    }


    //    @Test
    @Order(3)
    @DisplayName("3 - Upload test di una attività plurale senza TOC")
    void uploadNoToc() {
        System.out.println("3 - Upload test di una attività senza TOC");
        sorgente = "agenti segreti";
        appContext.getBean(UploadAttivita.class).noToc().test().upload(sorgente);
    }


    //        @Test
    @Order(3)
    @DisplayName("3 - Upload di una attività plurale")
    void upload3() {
        System.out.println("3 - Upload di una attività plurale");
        sorgente = "Allenatori di hockey su ghiaccio";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);
    }

    //    @Test
    @Order(4)
    @DisplayName("4 - Upload test di una attività")
    void upload4() {
        System.out.println("4 - Upload test di una attività");
        sorgente = "abati e badesse";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);
    }

    //    @Test
    @Order(5)
    @DisplayName("5 - Upload all attività")
    void upload5() {
        System.out.println("5 - Upload all attività");
        appContext.getBean(UploadAttivita.class).test().uploadAll();
    }


//    @Test
    @Order(6)
    @DisplayName("6 - Upload attività test con poche voci")
    void upload6() {
        System.out.println("6 - Upload attività test con poche voci");
        sorgente = "agricoltori";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);
    }

    //    @Test
    @Order(7)
    @DisplayName("7 - Upload di una attività con sottoPagina")
    void upload7() {
        System.out.println("7 - Upload di una attività con sottoPagina");
        sorgente = "dogi";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);
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