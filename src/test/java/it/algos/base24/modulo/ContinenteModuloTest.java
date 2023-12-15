package it.algos.base24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.geografia.continente.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 29-ott-2023
 * Time: 07:33
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Continente Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContinenteModuloTest extends ModuloTest {

    @Autowired
    private ContinenteModulo modulo;

    private ContinenteEntity entityBean;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = ContinenteEntity.class;
        super.listClazz = ContinenteList.class;
        super.viewClazz = ContinenteView.class;

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
        ContinenteEntity obj1;
        ContinenteEntity obj2;
        ContinenteEntity obj3;

        obj1 = modulo.newEntity(14, "alfa");
        obj2 = modulo.newEntity(14, "alfa");
        obj3 = modulo.newEntity(14, "beta");

        super.fixEquals(obj1, obj2, obj3);
    }


    @Test
    @Order(110)
    @DisplayName("110 - getAllEnums")
    void getAllEnums() {
        System.out.println("110 - getAllEnums");
        System.out.println(VUOTA);

        for (ContinenteEnum contEnum : ContinenteEnum.getAllEnums()) {
            entityBean = (ContinenteEntity) modulo.findOneByKey(contEnum.getTag());
            assertNotNull(entityBean);
            System.out.println(entityBean.nome);
        }

    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof ContinenteEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean.nome);
                System.out.println();
            }
        }
    }

}
