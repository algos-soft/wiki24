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

}
