package it.algos.enumeration;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 14-Jul-2023
 * Time: 14:50
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("AETypeSummary")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AETypeSummaryTest extends AlgosTest {

    private AETypeSummary type;

    private AETypeSummary[] matrice;

    private List<AETypeSummary> listaEnum;

    private List<String> listaTag;



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
//    @BeforeAll
//    protected void setUpAll() {
//        super.setUpAll();
//    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        type = null;
        matrice = null;
        listaEnum = null;
        listaTag = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = AETypeSummary.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AETypeSummary valore : matrice) {
            System.out.println(valore);
        }
    }


    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori metodo forEach")
    void forEach() {
        listaEnum = AETypeSummary.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo forEach");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(3)
    @DisplayName("3 - lista dei valori metodo stream")
    void stream() {
        listaEnum = AETypeSummary.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo stream");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.stream().forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @Test
    @Order(4)
    @DisplayName("4 - tag di ogni singola enumeration")
    void tag() {
        listaEnum = AETypeSummary.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutti i tag di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.forEach(e -> {
            System.out.print(e);
            System.out.print(FORWARD);
            System.out.println(e.get());
        });
        System.out.println(VUOTA);
    }





//    /**
//     * Qui passa al termine di ogni singolo test <br>
//     */
//    @AfterEach
//    void tearDown() {
//    }
//
//
//    /**
//     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
//     */
//    @AfterAll
//    void tearDownAll() {
//    }

}