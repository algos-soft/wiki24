package it.algos.base24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 13:51
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Secolo Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloModuloTest extends ModuloTest {

    @Autowired
    private SecoloModulo modulo;

    private SecoloEntity secoloBean;

    //--anno sorgente
    //--secolo previsto
    private static Stream<Arguments> anniPrima() {
        return Stream.of(
                Arguments.of(45, "I secolo a.C."),
                Arguments.of(3, "I secolo a.C."),
                Arguments.of(400, "IV secolo a.C."),
                Arguments.of(401, "V secolo a.C."),
                Arguments.of(450, "V secolo a.C."),
                Arguments.of(499, "V secolo a.C."),
                Arguments.of(500, "V secolo a.C."),
                Arguments.of(501, "VI secolo a.C.")
        );
    }

    //--anno sorgente
    //--secolo previsto
    private static Stream<Arguments> anniDopo() {
        return Stream.of(
                Arguments.of(1845, "IXX secolo"),
                Arguments.of(1800, "XVIII secolo"),
                Arguments.of(1801, "IXX secolo"),
                Arguments.of(1899, "IXX secolo"),
                Arguments.of(1900, "IXX secolo"),
                Arguments.of(1901, "XX secolo")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = SecoloEntity.class;
        super.listClazz = SecoloList.class;
        super.viewClazz = SecoloView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(110)
    @DisplayName("110 - secolo dell'anno prima di cristo")
    void getSecoloAC() {
        System.out.println(("110 - secolo dell'anno prima di cristo"));
        System.out.println(VUOTA);

        //--anno sorgente
        //--secolo previsto
        anniPrima().forEach(this::fixAnniPrima);
    }

    //--anno sorgente
    //--secolo previsto
    void fixAnniPrima(Arguments arg) {
        Object[] mat = arg.get();
        sorgenteIntero = (int) mat[0];
        previsto = (String) mat[1];

        secoloBean = modulo.getSecoloAC(sorgenteIntero);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.nome, previsto);
    }


    @Test
    @Order(120)
    @DisplayName("120 - secolo dell'anno dopo cristo")
    void getSecoloDC() {
        System.out.println(("120 - secolo dell'anno dopo cristo"));
        System.out.println(VUOTA);

        //--anno sorgente
        //--secolo previsto
        anniDopo().forEach(this::fixAnniDopo);
    }

    //--anno sorgente
    //--secolo previsto
    void fixAnniDopo(Arguments arg) {
        Object[] mat = arg.get();
        sorgenteIntero = (int) mat[0];
        previsto = (String) mat[1];

        secoloBean = modulo.getSecoloDC(sorgenteIntero);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.nome, previsto);

        message = String.format("%s%s%s", sorgenteIntero, FORWARD, secoloBean.nome);
        System.out.println(message);
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof SecoloEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                // System.out.print(bean.code);
                System.out.println();
            }
        }
    }

}
