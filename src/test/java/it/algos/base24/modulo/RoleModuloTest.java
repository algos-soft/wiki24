package it.algos.base24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.basetest.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 10:19
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Role Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleModuloTest extends ModuloTest {

    @Autowired
    private RoleModulo modulo;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = RoleEntity.class;
        super.listClazz = RoleList.class;
        super.viewClazz = RoleView.class;

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
        RoleEntity obj1;
        RoleEntity obj2;
        RoleEntity obj3;

        obj1 = modulo.newEntity(17, "alfa");
        obj2 = modulo.newEntity(17, "alfa");
        obj3 = modulo.newEntity( 17,"beta");

        super.fixEquals(obj1,obj2,obj3);
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof RoleEntity bean) {
                System.out.print(bean.ordine);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean.code);
                System.out.println();
            }
        }
    }

}
