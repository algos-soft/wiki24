package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 09:35
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Secolo Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloBackendTest extends AlgosUnitTest {

    /**
     * The Service.
     */
    @InjectMocks
    private SecoloBackend backend;

    @Autowired
    private SecoloRepository repository;

    private Secolo entityBean;

    private List<Secolo> listaBeans;

    private String dbName = "Secolo";

    private String backendName = "SecoloBackend";

    private Class entityClazz = Secolo.class;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        Assertions.assertNotNull(backend);

        backend.repository = repository;
        backend.crudRepository = repository;
        backend.arrayService = arrayService;
        backend.dateService = dateService;
        backend.textService = textService;
        backend.resourceService = resourceService;
        backend.reflectionService = reflectionService;
        backend.mongoService = mongoService;
    }


    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        super.initMocks();
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();
    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.entityBean = null;
        this.listaBeans = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - count")
    void count() {
        System.out.println("1 - count");
        String message;

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("Ci sono in totale %s entities di '%s' nel database mongoDB", textService.format(ottenutoIntero), dbName);
        }
        else {
            if (reflectionService.isEsisteMetodo(backend.getClass(), TAG_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", dbName, backendName, TAG_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' è ancora vuota", dbName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }



    @Test
    @Order(3)
    @DisplayName("3 - findNomiDiscendenti (nome)")
    void findNomi() {
        System.out.println("3 - findNomiDiscendenti (nome)");
        String message;

        listaStr = backend.findNomi();
        assertNotNull(listaStr);
        message = String.format("Ci sono in totale %s secoli", textService.format(listaStr.size()));
        System.out.println(message);
        printNomiSecoli(listaStr);
    }


    @Test
    @Order(4)
    @DisplayName("4 - findNomiAscendenti (nome)")
    void findNomiAscendenti() {
        System.out.println("4 - findNomiAscendenti (nome)");
        String message;

        listaStr = backend.findNomiAscendenti();
        assertNotNull(listaStr);
        message = String.format("Ci sono in totale %s secoli", textService.format(listaStr.size()));
        System.out.println(message);
        printNomiSecoli(listaStr);
    }

    //    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty pieno")
    void resetOnlyEmptyPieno() {
        System.out.println("91 - resetOnlyEmpty pieno");
        String message;

        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    //    @Test
    @Order(92)
    @DisplayName("92 - resetOnlyEmpty vuoto")
    void resetOnlyEmptyVuoto() {
        System.out.println("92 - resetOnlyEmpty vuoto");
        String message;

        mongoService.deleteAll(entityClazz);
        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }

    //    @Test
    @Order(93)
    @DisplayName("93 - resetForcing pieno")
    void resetForcingPieno() {
        System.out.println("93 - resetForcing pieno");
        String message;

        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    //    @Test
    @Order(94)
    @DisplayName("94 - resetForcing vuoto")
    void resetForcingVuoto() {
        System.out.println("94 - resetForcing vuoto");
        String message;

        mongoService.deleteAll(entityClazz);
        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    void printNomiSecoli(List<String> listaSecoli) {
        int k = 0;

        for (String secolo : listaSecoli) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(secolo);
        }
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

    void printBeans(List<Secolo> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        for (Secolo bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

}