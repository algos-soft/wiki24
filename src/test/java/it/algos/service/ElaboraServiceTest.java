package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 02-Mar-2023
 * Time: 07:23
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Elabora Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElaboraServiceTest extends WikiTest {

    private ElaboraService service;

    public WPref lastDownload;

    protected WPref durataDownload;

    public WPref lastElabora;

    public WPref durataElaborazione;

    protected WPref lastUpload;

    protected WPref durataUpload;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        service = elaboraService;

        lastElabora = WPref.elaboraGiorni;
        durataElaborazione = WPref.elaboraGiorniTime;
        //        super.nextUpload = WPref.uploadGiorniPrevisto;
        //        super.lastStatistica = WPref.statisticaGiorni;
        //        super.durataStatistica = WPref.statisticaGiorniTime;
    }

    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Pu?? essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();
        giornoWikiBackend.lastElabora = WPref.elaboraGiorni;
        giornoWikiBackend.durataElaborazione = WPref.elaboraGiorniTime;

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
    @DisplayName("1 - elabora giorni")
    void getLabelHost() {
        System.out.println("1 - elabora giorni");
        System.out.println(VUOTA);
        System.out.println("Elabora controlla solo il numero di voci biografiche per ogni giorno");
        System.out.println("Non effettua controlli sulle pagine esistenti o meno sul server");
        System.out.println("Perch?? i giorni hanno tutti un numero di voci tale da giustificare sempre la pagina wiki");
        System.out.println("Viene registrata anche la data e la durata nelle preferenze visibili da programma");

        giornoWikiBackend.elabora();
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
