package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.nomecategoria.*;
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
 * Date: Sat, 01-Jul-2023
 * Time: 08:41
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nomibackend")
@Tag("nomi")
@Tag("backend")
@DisplayName("NomeCategoria Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NomeCategoriaBackendTest extends WikiBackendTest {

    @InjectMocks
    private NomeCategoriaBackend backend;

    private List<NomeCategoria> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nomeCategoriaBackend;
        super.entityClazz = NomeCategoria.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.nomeModulo = "nomecategoria";

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(75)
    @DisplayName("75 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("75 - findAllDistinctByPlurali (non previsto per questa collection)");
    }

}
