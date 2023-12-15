package it.algos.base24.basetest;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 30-Jul-2023
 * Time: 08:39
 * Unit test di una Enumeration di tipo Type <br>
 */
public abstract class EnumTest {

    protected List<Type> listaEnumType;

    protected List<String> listaTag;

    protected Type typeZero = null;

    protected String message;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        listaEnumType = null;
        listaTag = null;
        message = VUOTA;
    }


    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori metodo forEach")
    void forEach() {
        listaEnumType = typeZero.getAll();
        assertNotNull(listaEnumType);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo forEach");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumType.size()));
        System.out.println(VUOTA);
        listaEnumType.forEach(System.out::println);
    }


    @Test
    @Order(3)
    @DisplayName("3 - lista dei valori metodo stream")
    void stream() {
        listaEnumType = typeZero.getAll();
        assertNotNull(listaEnumType);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo stream");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumType.size()));
        System.out.println(VUOTA);
        listaEnumType.stream().forEach(System.out::println);
    }


    @Test
    @Order(4)
    @DisplayName("4 - tag di ogni singola enumeration")
    void tag() {
        listaEnumType = typeZero.getAll();
        assertNotNull(listaEnumType);

        System.out.println("Tutti i tag di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumType.size()));
        System.out.println(VUOTA);
        listaEnumType.forEach(e -> {
            System.out.print(e);
            System.out.print(FORWARD);
            System.out.println(e.getTag());
        });
    }


    @Test
    @Order(5)
    @DisplayName("5 - lista dei tag")
    void allTag() {
        listaTag = typeZero.getAllTags();
        assertNotNull(listaTag);

        System.out.println("Tutti i valori 'tag' della enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaTag.size()));
        System.out.println(VUOTA);
        listaTag.forEach(System.out::println);
    }

}