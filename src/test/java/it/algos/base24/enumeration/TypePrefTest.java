package it.algos.base24.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 21:02
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration TypePref")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TypePrefTest extends EnumTest {


    private TypePref[] matrice;
    private byte[] bytes;

    protected String sorgente;
    protected String ottenuto;
    protected boolean sorgenteBooleano;
    protected boolean ottenutoBooleano;
    protected int sorgenteIntero;
    protected int ottenutoIntero;
    protected LocalDateTime sorgenteData;
    protected LocalDateTime ottenutoData;



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = TypePref.getAllEnums().get(0);
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
        matrice = TypePref.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (TypePref valore : matrice) {
            System.out.println(valore);
        }
    }


    @Test
    @Order(101)
    @DisplayName("101 - typeString")
    void typeString() {
        System.out.println(String.format("Esempio di trasformazione da e per [%s]", "string"));
        System.out.println(VUOTA);

        sorgente = "137";
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgente));
        bytes = TypePref.string.objectToBytes(sorgente);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenuto = TypePref.string.bytesToString(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenuto));
        assertEquals(ottenuto, sorgente);
        System.out.println(VUOTA);


        sorgente = "Pippoz";
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgente));
        bytes = TypePref.string.objectToBytes(sorgente);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenuto = TypePref.string.bytesToString(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenuto));
        assertEquals(ottenuto, sorgente);
        System.out.println(VUOTA);

        sorgente = "Nessuno ti può giudicare, nemmeno tu. Ma, nonostante tutto, ci credo. Davvero. Non ne posso fare a meno.";
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgente));
        bytes = TypePref.string.objectToBytes(sorgente);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenuto = TypePref.string.bytesToString(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenuto));
        assertEquals(ottenuto, sorgente);
        System.out.println(VUOTA);
    }


    @Test
    @Order(102)
    @DisplayName("102 - typeBool")
    void typeBool() {
        System.out.println(String.format("Esempio di trasformazione da e per [%s]", "bool"));
        System.out.println(VUOTA);

        sorgenteBooleano = false;
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteBooleano));
        bytes = TypePref.bool.objectToBytes(sorgenteBooleano);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoBooleano = (boolean)TypePref.bool.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoBooleano));
        assertEquals(sorgenteBooleano,ottenutoBooleano);
        System.out.println(VUOTA);

        sorgenteBooleano = true;
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteBooleano));
        bytes = TypePref.bool.objectToBytes(sorgenteBooleano);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoBooleano = (boolean)TypePref.bool.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoBooleano));
        assertEquals(sorgenteBooleano,ottenutoBooleano);
        System.out.println(VUOTA);
    }


    @Test
    @Order(103)
    @DisplayName("103 - typeInt")
    void typeInt() {
        System.out.println(String.format("Esempio di trasformazione da e per [%s]", "int"));
        System.out.println(VUOTA);

        sorgenteIntero = 3;
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteIntero));
        bytes = TypePref.integer.objectToBytes(sorgenteIntero);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoIntero = (int)TypePref.integer.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoIntero));
        assertEquals(sorgenteIntero,ottenutoIntero);
        System.out.println(VUOTA);

        sorgenteIntero = 1871;
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteIntero));
        bytes = TypePref.integer.objectToBytes(sorgenteIntero);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoIntero = (int)TypePref.integer.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoIntero));
        assertEquals(sorgenteIntero,ottenutoIntero);
        System.out.println(VUOTA);

        sorgenteIntero = -428;
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteIntero));
        bytes = TypePref.integer.objectToBytes(sorgenteIntero);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoIntero = (int)TypePref.integer.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoIntero));
        assertEquals(sorgenteIntero,ottenutoIntero);
        System.out.println(VUOTA);
    }


    @Test
    @Order(107)
    @DisplayName("107 - typeLocaldatetime")
    void typeLocaldatetime() {
        System.out.println(String.format("Esempio di trasformazione da e per [%s]", "localdatetime"));
        System.out.println(VUOTA);

        sorgenteData = LocalDateTime.of(2023,9,12,8,4,27);
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteData));
        bytes = TypePref.localdatetime.objectToBytes(sorgenteData);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoData = (LocalDateTime) TypePref.localdatetime.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoData));
        assertEquals(sorgenteData,ottenutoData);
        System.out.println(VUOTA);

        sorgenteData = LocalDateTime.now();
        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteData));
        bytes = TypePref.localdatetime.objectToBytes(sorgenteData);
        assertNotNull(bytes);
        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
        ottenutoData = (LocalDateTime) TypePref.localdatetime.bytesToObject(bytes);
        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoData));
        assertNotEquals(sorgenteData,ottenutoData);
        System.out.println(VUOTA);


//        sorgenteData = LocalDateTime.now();
//        System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgenteData));
//        bytes = TypePref.localdatetime.objectToBytes(sorgenteData);
//        assertNotNull(bytes);
//        System.out.println(String.format("%s%s%s (length=%d)", "bytes", FORWARD, bytes,bytes.length));
//        ottenutoData = (LocalDateTime) TypePref.localdatetime.bytesToObject(bytes);
//        System.out.println(String.format("%s%s%s", "Riconversione", FORWARD, ottenutoData));
//        assertEquals(sorgenteData,ottenutoData);
//        System.out.println(VUOTA);
    }

}
