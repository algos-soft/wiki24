package it.algos.base24.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.*;
import it.algos.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 11:56
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Field Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FieldServiceTest extends ServiceTest {

    @Autowired
    private FieldService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = FieldService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(10)
    @DisplayName("10 - crea")
    void crea() {
        AbstractField field = null;

        clazz = RoleEntity.class;

        sorgente = "nessuno";
        field = service.crea(clazz, sorgente);
        assertNull(field);

        sorgente = "nome";
        field = service.crea(clazz, sorgente);
        assertNull(field);

        sorgente = "ordine";
        field = service.crea(clazz, sorgente);
        assertNotNull(field);
        assertTrue(field instanceof IntegerField);

        //        clazz = ContinenteModel.class;
        //        sorgente = "livello";
        //        field = service.crea(clazz, sorgente);
        //        assertNotNull(field);
        //        assertTrue(field instanceof AComboField);

        //        sorgente = "inizio";
        //        field = service.crea(clazz, sorgente);
        //        assertNotNull(field);
        //        assertTrue(field instanceof DatePicker);

        //        clazz = ALogger.class;
        //        sorgente = "evento";
        //        field = service.crea(clazz, sorgente);
        //        assertNotNull(field);
        //        assertTrue(field instanceof DateTimePicker);
    }

}
