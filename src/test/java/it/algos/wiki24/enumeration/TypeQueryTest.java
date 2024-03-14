package it.algos.wiki24.enumeration;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.basetest.*;
import it.algos.wiki24.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Dec-2023
 * Time: 12:51
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration TypeQuery")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TypeQueryTest extends AlgosTest {


    private TypeQuery[] matrice;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
//        super.setUpAll();
//        super.typeZero = TypeQuery.getAllEnums().get(0);
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
        matrice = TypeQuery.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (TypeQuery valore : matrice) {
            System.out.println(valore);
        }
    }

}