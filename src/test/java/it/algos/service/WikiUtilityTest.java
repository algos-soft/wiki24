package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 05-Oct-2023
 * Time: 12:21
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("WikiUtility Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WikiUtilityTest extends AlgosTest {

    @Autowired
    private WikiUtility service;

    //--anno
    //--decade
    private Stream<Arguments> decadi() {
        return Stream.of(
                Arguments.of("43", "41-50"),
                Arguments.of("80", "71-80"),
                Arguments.of("81", "81-90"),
                Arguments.of("481", "81-90"),
                Arguments.of("1781", "81-90"),
                Arguments.of("4", "1-10"),
                Arguments.of("9", "1-10"),
                Arguments.of("10", "1-10"),
                Arguments.of(" 4 ", "1-10"),
                Arguments.of("40", "31-40"),
                Arguments.of("460 a.C.", "51-60"),
                Arguments.of("8 a.C.", "1-10"),
                Arguments.of("04", "1-10")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = WikiUtility.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(1)
    @DisplayName("1 - getDecade")
    void getDecade() {
        System.out.println("1 - getDecade");
        System.out.println(VUOTA);

        //--anno
        //--decade
        decadi().forEach(this::fixDecade);
    }

    //--anno
    //--decade
    void fixDecade(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.getDecade(sorgente);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
        assertEquals(previsto, ottenuto);
    }

}
