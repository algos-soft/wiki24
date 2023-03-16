package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
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
 * Date: Tue, 14-Mar-2023
 * Time: 07:24
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("backend")
@DisplayName("Nazionalita Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazionalitaBackendTest extends BackendTest {

    @InjectMocks
    private NazionalitaBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Nazionalita.class;
        super.crudBackend = backend;
        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

}
