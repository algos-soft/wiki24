package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.cognome.*;
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
 * Date: Thu, 13-Jul-2023
 * Time: 07:19
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("cognomibackend")
@DisplayName("Cognome Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CognomeBackendTest extends WikiBackendTest {

    private CognomeBackend backend;

    private List<Cognome> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.cognomeBackend;
        super.entityClazz = Cognome.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    //--Lentissimo
    @Order(14)
    @DisplayName("14 - resetForcing")
    protected void resetForcing() {
        System.out.println("14 - resetForcing");
        System.out.println(VUOTA);
    }

    @Test
    @Order(92)
    @DisplayName("92 - findAllByNumBio")
    void findAllByNumBio() {
        System.out.println("92 - findAllByNumBio");
        sorgenteIntero = 30;
        listaBeans = cognomeBackend.findAllByNumBio(sorgenteIntero);
        assertNotNull(listaBeans);


    }


}
