package it.algos.base24.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Wed, 06-Dec-2023
 * Time: 21:03
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration TemplateBandierina")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TemplateBandierinaTest extends EnumTest {


    private TemplateBandierina[] matrice;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = TemplateBandierina.getAllEnums().get(0);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        matrice = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = TemplateBandierina.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (TemplateBandierina valore : matrice) {
            System.out.println(valore);
        }
    }

}