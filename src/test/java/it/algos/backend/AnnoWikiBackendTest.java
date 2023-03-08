package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.anno.*;
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
 * Date: Tue, 07-Mar-2023
 * Time: 16:47
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("anno")
@Tag("backend")
@DisplayName("AnnoWiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoWikiBackendTest extends WikiBackendTest {

    private AnnoWikiBackend backend;

    private List<AnnoWiki> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = AnnoWiki.class;
        backend = annoWikiBackend;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.setUpAll();
        super.typeBackend = TypeBackend.anno;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

}
