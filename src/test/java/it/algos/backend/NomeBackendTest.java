package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.nome.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 14-Jun-2023
 * Time: 17:29
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nomibackend")
@Tag("nomi")
@Tag("backend")
@DisplayName("Nome Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NomeBackendTest extends WikiBackendTest {

    private NomeBackend backend;

    private List<Nome> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nomeBackend;
        super.entityClazz = Nome.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        listaBackendClazz = classService.getAllBackend();
    }


    @Test
    //--Lentissimo
    @Order(14)
    @DisplayName("14 - resetForcing")
    protected void resetForcing() {
        System.out.println("14 - resetForcing");
        System.out.println(VUOTA);
    }

    //    @Test
    //--Circa 3 minuti
    @Order(15)
    @DisplayName("15 - elabora (solo su wiki)")
    protected void elabora() {
        System.out.println("15 - elabora (solo su wiki)");
        System.out.println(VUOTA);

        if (entityClazz == null) {
            System.out.println("Manca la variabile entityClazz in questo test");
            return;
        }

        ottenutoRisultato = wikiBackend.elabora();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }

    @Test
    @Order(16)
    @DisplayName("16 - uploadModulo (test in ordine alfabetico)")
    protected void upload() {
        System.out.println("16 - uploadModulo (non previsto per questa collection)");
    }

    @Test
    @Order(75)
    @DisplayName("75 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("75 - findAllDistinctByPlurali (non previsto per questa collection)");
    }

    @Test
    @Order(81)
    protected void isEsiste() {
        sorgente = "Persone di nome Ada";

        ottenutoBooleano = queryService.isEsiste(sorgente);
        System.out.println(sorgente);
        System.out.println(ottenutoBooleano);
    }

}
