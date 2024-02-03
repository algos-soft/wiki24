package it.algos.base24.service;

import com.vaadin.flow.component.grid.*;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 22-Oct-2023
 * Time: 10:25
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Column Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ColumnServiceTest extends ServiceTest {

    @Autowired
    private ColumnService service;

    @Autowired
    private TextService textService;

    //--class
    //--property
    //--booleano
    //--type
    //--width
    //--header
    private Stream<Arguments> colonna() {
        return Stream.of(
                Arguments.of(null, VUOTA, false, null, VUOTA, VUOTA),
                Arguments.of(RoleView.class, VUOTA, false, null, VUOTA, VUOTA),
                //                Arguments.of(ContinenteModel.class, FIELD_NAME_DESCRIZIONE, false, null, VUOTA, VUOTA),
                Arguments.of(RoleEntity.class, FIELD_NAME_ORDINE, true, TypeField.integer, "4rem", FIELD_HEADER_ORDINE),
                //                Arguments.of(ContinenteModel.class, FIELD_NAME_NOME, true, TypeField.text, "12rem", textService.primaMaiuscola(FIELD_NAME_NOME)),
                //                Arguments.of(ContinenteModel.class, "abitato", true, TypeField.booleano, "5rem", "Ab.")
                Arguments.of(RoleEntity.class, VUOTA, false, null, VUOTA, VUOTA)
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ColumnService.class;
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
        System.out.println(("10 - crea"));
        System.out.println(VUOTA);

        //--class
        //--property
        //--booleano
        //--type
        //--width
        //--header
        colonna().forEach(this::fixCrea);
    }

    //--class
    //--property
    //--booleano
    //--type
    //--width
    //--header
    void fixCrea(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        sorgente = (String) mat[1];
        previstoBooleano = (boolean) mat[2];
        TypeField type = (TypeField) mat[3];
        String width = (String) mat[4];
        String header = (String) mat[5];
        Grid grid = clazz != null ? new Grid<>(clazz, false) : null;
        Grid.Column<AbstractEntity> colonna = null;

        colonna = service.crea(grid, clazz, sorgente, false);
        assertEquals(previstoBooleano, colonna != null);
        if (colonna != null) {
            System.out.println(VUOTA);
            this.printColonna(clazz, sorgente, colonna, type, width, header);
        }
    }

    protected void printColonna(final Class clazz,
                                final String sorgente,
                                final Grid.Column<AbstractEntity> colonna,
                                final TypeField type,
                                final String width,
                                final String header) {
        if (clazz == null || textService.isEmpty(sorgente) || colonna == null) {
            return;
        }

        message = String.format("Colonna della Grid per la property '%s' della classe [%s]:", sorgente, clazz.getSimpleName());
        System.out.println(message);

        assertEquals(sorgente, colonna.getKey());
        Object alfa = colonna.getWidth();
        assertEquals(width, colonna.getWidth());
        assertEquals(header, colonna.getHeaderText());

        System.out.println(String.format("Key%s%s", FORWARD, colonna.getKey()));
        System.out.println(String.format("Field di tipo%s%s", FORWARD, type));
        System.out.println(String.format("Width%s%s", FORWARD, colonna.getWidth()));
        System.out.println(String.format("Header%s%s", FORWARD, colonna.getHeaderText()));
    }

}
