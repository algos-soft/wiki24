package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.cognomeincipit.*;
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
 * Date: Wed, 12-Jul-2023
 * Time: 07:06
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("cognomibackend")
@Tag("cognomi")
@Tag("backend")
@DisplayName("CognomeIncipit Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CognomeIncipitBackendTest extends WikiBackendTest {

    private CognomeIncipitBackend backend;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.cognomeIncipitBackend;
        super.entityClazz = CognomeIncipit.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.nomeModulo = "cognomemodulo";

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
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

}
