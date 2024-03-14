package it.algos.wiki24.modulo;

import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.packages.geografia.stato.*;
import it.algos.vbase.backend.wrapper.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 24-Jan-2024
 * Time: 10:38
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("BioServer Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioServerModuloTest extends WikiModuloTest {

    @Autowired
    private BioServerModulo modulo;

    private BioServerEntity bioServerBean;

    private List<BioServerEntity> listaBioServerBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = BioServerEntity.class;
        super.listClazz = BioServerList.class;
        super.viewClazz = BioServerView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.bioServerBean = null;
        this.listaBioServerBeans = null;
    }


    @Test
    @Order(101)
    @DisplayName("101 - isModificato")
    void isModificato() {
        System.out.println("101 - isModificato");
        System.out.println(VUOTA);
        int pos = 5;
        BioServerEntity equalBean;
        BioServerEntity modifiedBean;

        listaBioServerBeans = modulo.findAll();
        assertNotNull(listaBioServerBeans);
        if (listaBioServerBeans.size() > pos) {
            bioServerBean = listaBioServerBeans.get(pos);
        }
        else {
            message = String.format("La collection [%s] non sembra adeguata", dbName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        assertNotNull(bioServerBean);

        ottenutoBooleano = modulo.isModificato(bioServerBean);
        assertFalse(ottenutoBooleano);
        System.out.println(ottenutoBooleano);

        bioServerBean.wikiTitle = "Xyz";
        ottenutoBooleano = modulo.isModificato(bioServerBean);
        assertTrue(ottenutoBooleano);
        System.out.println(ottenutoBooleano);
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof BioServerEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                // System.out.print(bean.code);
                System.out.println();
            }
        }
    }

}
