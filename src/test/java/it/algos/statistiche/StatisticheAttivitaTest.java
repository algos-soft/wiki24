package it.algos.statistiche;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.statistiche.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 30-Apr-2023
 * Time: 07:39
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("statistiche")
@DisplayName("Statistiche Attivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatisticheAttivitaTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private StatisticheAttivita istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.clazz = StatisticheAttivita.class;
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri (non fa nulla)")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base senza parametri (non fa nulla)"));
        System.out.println(VUOTA);

        istanza = new StatisticheAttivita();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
        System.out.println("Questa classe NON accetta parametri nel costruttore");
    }

    @Test
    @Order(2)
    @DisplayName("2 - Istanza costruita con appContext.getBean (non fa nulla)")
    void getBean() {
        System.out.println(("2 - Istanza costruita con appContext.getBean (non fa nulla)"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(StatisticheAttivita.class);
        assertNotNull(istanza);
        System.out.println(String.format("Istanza costruita con appContext.getBean(%s.class)", istanza.getClass().getSimpleName()));
        System.out.println("Questa classe NON accetta parametri nel costruttore");
        System.out.println(String.format("Non fa nulla, occorre (obbligatorio) invocare il metodo esegue()"));
        System.out.println(String.format("Le classi [Statistica] (ed altre) implementano il Design Pattern 'Builder'"));
        System.out.println(String.format("Per permettere la costruzione 'modulare' dell'istanza con variabili come [test] e altre"));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Upload con metodo .test() ed .esegue()")
    void esegueConTest() {
        System.out.println(("3 - Upload con metodo .test() ed .esegue()"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(StatisticheAttivita.class).test().esegue();
        assertNotNull(ottenutoRisultato);
        System.out.println(VUOTA);
        System.out.println(String.format("Istanza costruita con appContext.getBean(%s.class).test().esegue()", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("È indispensabile invocare il metodo .esegue(), dopo aver eventualmente modificato qualche variabile"));
        System.out.println(VUOTA);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

}