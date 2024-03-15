package it.algos.base24.enumeration;

import it.algos.base24.basetest.*;
import it.algos.vbase.backend.boot.*;
import it.algos.vbase.backend.enumeration.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Wed, 06-Dec-2023
 * Time: 20:58
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration RegioneSpeciali")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegioneSpecialiTest extends EnumTest {


    private RegioneSpeciali[] matrice;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = RegioneSpeciali.getAllEnums().get(0);
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
        matrice = RegioneSpeciali.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(BaseCost.VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(BaseCost.VUOTA);
        for (RegioneSpeciali valore : matrice) {
            System.out.println(valore);
        }
    }

}