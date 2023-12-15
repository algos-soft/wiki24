package it.algos.base24.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 19:17
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration Utente")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtenteTest extends EnumTest {


    private Utente[] matrice;

    private List<Utente> listaEnumUtente;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = Utente.getAllEnums().get(0);
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
        matrice = Utente.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (Utente valore : matrice) {
            System.out.println(valore);
        }
    }


    @Test
    @Order(10)
    @DisplayName("10 - username e password")
    void namePass() {
        listaEnumUtente = Utente.getAllEnums();
        assertNotNull(listaEnumUtente);

        System.out.println("Tutti gli username e password");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumUtente.size()));
        System.out.println(VUOTA);
        for (Utente utente : listaEnumUtente) {
            message = String.format("%s%s%s", utente.username, FORWARD, utente.password);
            System.out.println(message);
        }
    }

    @Test
    @Order(11)
    @DisplayName("11 - username e permessi (Role)")
    void nameRole() {
        listaEnumUtente = Utente.getAllEnums();
        assertNotNull(listaEnumUtente);

        System.out.println("Tutti gli username e i permessi (Role)");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumUtente.size()));
        System.out.println(VUOTA);
        for (Utente utente : listaEnumUtente) {
            message = String.format("%s%s[%s]", utente.username, FORWARD, utente.userRoles);
            System.out.println(message);
        }
    }

}