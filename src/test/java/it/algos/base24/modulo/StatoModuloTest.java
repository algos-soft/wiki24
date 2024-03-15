package it.algos.base24.modulo;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.packages.geografia.stato.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;


/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 10:20
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Stato Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatoModuloTest extends ModuloTest {

    @Autowired
    private StatoModulo modulo;

    private List<StatoEntity> listaStati;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = StatoEntity.class;
        super.listClazz = StatoList.class;
        super.viewClazz = StatoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(20)
    @DisplayName("20 - equals")
    void equals() {
        System.out.println("20 - equals");
        System.out.println(VUOTA);
        StatoEntity obj1;
        StatoEntity obj2;
        StatoEntity obj3;

        obj1 = modulo.newEntity(87, "alfaalfa", "alfa");
        obj2 = modulo.newEntity(87, "alfaalfa", "alfa");
        obj3 = modulo.newEntity(87, "betabeta", "beta");

        super.fixEquals(obj1, obj2, obj3);
    }

    @Test
    @Order(110)
    @DisplayName("110 - findAllEuropa")
    void findAllEuropa() {
        System.out.println("110 - findAllEuropa");
        System.out.println(VUOTA);

        listaStati = modulo.findAllEuropa();
        assertNotNull(listaStati);
        listaStati.stream().forEach(bean -> System.out.println(bean));
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof StatoEntity bean) {
                System.out.print(bean.ordine);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean.nome);
                System.out.print(SPAZIO);
                System.out.print(bean.capitale);
                System.out.print(SPAZIO);
                System.out.print(bean.alfa3);
                System.out.print(SPAZIO);
                System.out.print(bean.alfa2);
                System.out.print(SPAZIO);
                System.out.print(bean.numerico);
                System.out.println();
            }
        }
    }

}
