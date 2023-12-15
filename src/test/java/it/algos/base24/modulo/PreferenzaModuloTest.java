package it.algos.base24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import it.algos.base24.basetest.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: lun, 30-ott-2023
 * Time: 14:06
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Preferenza Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreferenzaModuloTest extends ModuloTest {

    @Autowired
    private PreferenzaModulo modulo;

    private List<PreferenzaEntity> listaBeansPreferenza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = PreferenzaEntity.class;
        super.listClazz = PreferenzaList.class;
        super.viewClazz = PreferenzaView.class;

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
        PreferenzaEntity obj1;
        PreferenzaEntity obj2;
        PreferenzaEntity obj3;

        obj1 = modulo.newEntity( "alfa", TypePref.bool,null,"alfaalfa");
        obj2 = modulo.newEntity( "alfa", TypePref.bool,null,"alfaalfa");
        obj3 = modulo.newEntity( "beta", TypePref.bool,null,"betabeta");

        super.fixEquals(obj1, obj2, obj3);
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof PreferenzaEntity bean) {
                System.out.print(bean.code);
                System.out.print(FORWARD);
                System.out.print(QUADRA_INI);
                System.out.print(bean.type);
                System.out.print(QUADRA_END);
                System.out.print(SPAZIO);
                System.out.print(NULLO);
                System.out.print(SPAZIO);
                System.out.print(APICETTI);
                System.out.print(bean.descrizione);
                System.out.print(APICETTI);
                System.out.println();
            }
        }
    }

}
