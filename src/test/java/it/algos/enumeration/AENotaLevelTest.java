package it.algos.enumeration;

import static com.vaadin.flow.server.frontend.FrontendUtils.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 25-mar-2022
 * Time: 16:58
 * Unit test di una enumeration <br>
 * Estende la classe astratta AlgosTest che contiene costanti e metodi base <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("enums")
@DisplayName("Enumeration AENotaLevel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AENotaLevelTest extends QuicklyTest {


    private AENotaLevel type;

    private AENotaLevel[] matrice;

    private List<AENotaLevel> listaEnum;

    private List<String> listaTag;


    //--tag
    //--esiste nella enumeration
    protected static Stream<Arguments> LIVELLI() {
        return Stream.of(
                Arguments.of(GREEN, true),
                Arguments.of(BRIGHT_BLUE, true),
                Arguments.of(YELLOW, true),
                Arguments.of(CHUNKS, false),
                Arguments.of(RED, true)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
    }


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
        matrice = AENotaLevel.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AENotaLevel valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori metodo forEach")
    void forEach() {
        listaEnum = AENotaLevel.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo forEach");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(3)
    @DisplayName("3 - lista dei valori metodo stream")
    void stream() {
        listaEnum = AENotaLevel.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo stream");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.stream().forEach(System.out::println);
        System.out.println(VUOTA);
    }




    @Test
    @Order(4)
    @DisplayName("4 - tag di ogni singola enumeration")
    void tag() {
        listaEnum = AENotaLevel.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutti i tag di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(e -> {System.out.print(e); System.out.print(FORWARD); System.out.println(e.getTag());});
        System.out.println(VUOTA);
    }


    @Test
    @Order(5)
    @DisplayName("5 - lista dei tag")
    void allTag() {
        listaTag = AENotaLevel.getAllTags();
        assertNotNull(listaTag);

        System.out.println("Tutti i valori 'tag' della enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaTag.size()));
        System.out.println(VUOTA);
        listaTag.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(6)
    @DisplayName("6 - getSingleType")
    void getType() {
        System.out.println("Tutte le occorrenze della enumeration con toString() -> tag");

        //--tag
        //--esiste nella enumeration
        System.out.println(VUOTA);
        LIVELLI().forEach(this::getTypeBase);
    }

    //--tag
    //--esiste nella enumeration
    void getTypeBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        type = AENotaLevel.getType(sorgente);
        assertTrue(previstoBooleano ? type != null : type == null);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", type, FORWARD, type != null ? type.getTag() : "non esiste"));
    }

    @Test
    @Order(7)
    @DisplayName("7 - getPref")
        //--tag
        //--esiste nella enumeration
    void getPref() {
        System.out.println("Stringa di valori (text) da usare per memorizzare la preferenza");
        System.out.println("La stringa è composta da tutti i valori separati da virgola");
        System.out.println("Poi, separato da punto e virgola, viene il valore corrente");
        System.out.println(VUOTA);

        LIVELLI().forEach(this::getPrefBase);
    }

    //--tag
    //--esiste nella enumeration
    void getPrefBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        type = AENotaLevel.getType(sorgente);
        assertTrue(previstoBooleano ? type != null : type == null);

        System.out.println(VUOTA);
        if (type != null) {
            System.out.println(String.format("%s%s%s", type, FORWARD, type.getPref()));
        }
        else {
            System.out.println(String.format("Enumeration '%s' non prevista", sorgente));
        }

    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}