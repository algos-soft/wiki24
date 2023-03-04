package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:30
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Secolo Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloBackendTest extends BackendTest {

    @InjectMocks
    private SecoloBackend backend;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Secolo.class;
        super.crudBackend = backend;
        super.setUpAll();
        super.typeBackend = TypeBackend.secolo;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(51)
    @DisplayName("51 - findByOrdine")
    void findByOrdine() {
        System.out.println("51 - findByOrdine");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dal numero d'ordine che parte dal X secolo a.C.");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 27;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 35;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Test
    @Order(52)
    @DisplayName("52 - findByAnnoAC")
    void findByAnnoAC() {
        System.out.println("52 - findByAnnoAC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno a.C. (senza segno meno)");
        System.out.println(VUOTA);

        sorgenteIntero = 4;
        entityBean = backend.findByAnnoAC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);
    }


    @Test
    @Order(53)
    @DisplayName("53 - findByAnnoDC")
    void findByAnnoDC() {
        System.out.println("53 - findByAnnoDC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno d.C. (senza segno più)");
        System.out.println(VUOTA);

        sorgenteIntero = 4;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1900;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1901;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1999;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2000;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2001;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);
    }


}