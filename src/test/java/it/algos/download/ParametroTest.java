package it.algos.download;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.parametro.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
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
 * Date: Thu, 09-Mar-2023
 * Time: 09:08
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@DisplayName("Parametro")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParametroTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    private ParametroBackend backend;

    private WrapBio wrapBio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        assertNotNull(backend);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        wrapBio = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - getBioWrap con templBio")
    void costruttoreBase() {
        System.out.println(("1 - getBioWrap con templBio"));
        System.out.println(VUOTA);

        sorgente = "Matteo Renzi";
        wrapBio = queryService.getBioWrap(sorgente);
        assertNotNull(wrapBio);

        sorgenteLong = 134247;
        wrapBio = queryService.getBioWrap(sorgenteLong);
        assertNotNull(wrapBio);
    }


    @ParameterizedTest
    @MethodSource(value = "PAGINE_E_CATEGORIE")
    @Order(2)
    @DisplayName("2 - esistenza pagina e biografia")
        //--titolo
        //--pagina esistente
        //--biografia esistente
    void getClazz(String wikiTitle, boolean isEsiste, boolean isBiografia) {
        System.out.println("2 - esistenza pagina e biografia");
        System.out.println(VUOTA);

        sorgente = wikiTitle;
        ottenutoBooleano = queryService.isEsiste(sorgente);
        assertEquals(isEsiste, ottenutoBooleano);
        System.out.println(String.format("La pagina %s %s", sorgente, ottenutoBooleano ? "esiste" : "non esiste"));

        ottenutoBooleano = queryService.isBio(sorgente);
        assertEquals(isBiografia, ottenutoBooleano);
        System.out.println(String.format("La biografia %s %s", sorgente, ottenutoBooleano ? "esiste" : "non esiste"));

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