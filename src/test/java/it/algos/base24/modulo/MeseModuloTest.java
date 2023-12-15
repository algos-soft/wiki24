package it.algos.base24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.basetest.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Nov-2023
 * Time: 18:41
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Mese Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseModuloTest extends ModuloTest {

    @Autowired
    private MeseModulo modulo;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = MeseEntity.class;
        super.listClazz = MeseList.class;
        super.viewClazz = MeseView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof MeseEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                 System.out.print(bean.nome);
                System.out.println();
            }
        }
    }

}
