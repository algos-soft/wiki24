package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 16:50
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Anno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoBackendTest extends BackendTest {

    @InjectMocks
    private AnnoBackend backend;

    @InjectMocks
    private SecoloBackend secoloBackend;

    private List<Anno> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        assertNotNull(secoloBackend);
        super.entityClazz = Anno.class;
        super.crudBackend = backend;
        super.setUpAll();
        super.typeBackend = TypeBackend.anno;
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();

        backend.secoloBackend = secoloBackend;
        backend.secoloBackend.mongoService = mongoService;
        backend.secoloBackend.annotationService = annotationService;
        backend.secoloBackend.textService = textService;
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
        System.out.println("Anno ricavato dal numero d'ordine che parte da ?");
        System.out.println(VUOTA);

        sorgenteIntero = 8527;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2508;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 304;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2963;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Test
    @Order(53)
    @DisplayName("53 - findAllBySecolo (entity)")
    void findAllBySecolo() {
        System.out.println("53 - findAllBySecolo (entity)");

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaBeans = backend.findAllBySecolo(sorgente);
            assertNotNull(listaBeans);
            message = String.format("Nel secolo %s ci sono %s anni", sorgente, textService.format(listaBeans.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printBackend(listaBeans);
        }
    }



}