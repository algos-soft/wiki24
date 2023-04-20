package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.InjectMocks;

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
 * Date: Fri, 14-Apr-2023
 * Time: 20:11
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Bio Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioServiceTest extends WikiTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private BioService service;

    private Bio bio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = bioService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        bio = null;
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource(value = "VOCE_BIOGRAFICA")
    @DisplayName("1 - getBioWrap")
        //--nome della pagina
        //--esiste sul server wiki
    void getBioWrap(String nomePagina, boolean esiste) {
        System.out.println("1 - getBioWrap");
        sorgente = nomePagina;
        previstoBooleano = esiste;

        if (previstoBooleano && textService.isValid(sorgente)) {
            wrapBio = service.getBioWrap(sorgente);
        }
        else {
            message = String.format("Biografia inesistente [%s] o nulla o pagina non biografica", sorgente);
            System.out.println(VUOTA);
            System.out.println(message);
            return;
        }

        if (wrapBio != null) {
            message = String.format("Recuperato il wrapBio della pagina %s", sorgente);
            System.out.println(message);
            printWrapBio(wrapBio);
        }
        else {
            message = String.format("Non sono riuscito a recuperare il wrapBio della pagina %s", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @Order(2)
    @MethodSource(value = "VOCE_BIOGRAFICA")
    @DisplayName("2 - getBioGrezzo")
        //--nome della pagina
        //--esiste sul server wiki
    void getBioGrezzo(String nomePagina, boolean esiste) {
        System.out.println("2 - getBioGrezzo");
        sorgente = nomePagina;
        previstoBooleano = esiste;

        if (previstoBooleano && textService.isValid(sorgente)) {
            bio = service.getBioGrezzo(sorgente);
            System.out.println(VUOTA);
        }
        else {
            message = String.format("Biografia inesistente [%s] o nulla o pagina non biografica", sorgente);
            System.out.println(VUOTA);
            System.out.println(message);
            return;
        }

        if (bio != null) {
            message = String.format("Creata un'istanza Bio (grezza, non elaborata) della pagina %s", sorgente);
            System.out.println(message);
            message = String.format("Contiene solo %s, %s e %s", "wikiTitle", "pageId", "tmplBio");
            System.out.println(message);
            message = String.format("Nel %s sono stati eliminati (solo visivamente) i ritorni a capo", "tmplBio");
            System.out.println(message);
            System.out.println(VUOTA);
            printBioTmpl(bio);
        }
        else {
            message = String.format("Non sono riuscito a creare un'istanza Bio dalla pagina %s", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @Order(3)
    @MethodSource(value = "VOCE_BIOGRAFICA")
    @DisplayName("3 - getTmplBio")
        //--nome della pagina
        //--esiste sul server wiki
    void getTmplBio(String nomePagina, boolean esiste) {
        System.out.println("3 - getTmplBio");
        sorgente = nomePagina;
        previstoBooleano = esiste;

        if (previstoBooleano && textService.isValid(sorgente)) {
            ottenuto = service.getTmplBio(sorgente);
            System.out.println(VUOTA);
        }
        else {
            message = String.format("Biografia inesistente [%s] o nulla o pagina non biografica", sorgente);
            System.out.println(VUOTA);
            System.out.println(message);
            return;
        }

        if (textService.isValid(ottenuto)) {
            message = String.format("Recuperato un tmplBio dalla pagina %s", sorgente);
            System.out.println(message);
            message = String.format("Nel %s sono stati eliminati (solo visivamente) i ritorni a capo", "tmplBio");
            System.out.println(message);
            System.out.println(VUOTA);

            ottenuto = textService.sostituisce(ottenuto, CAPO, SPAZIO);
            System.out.println(ottenuto);
        }
        else {
            message = String.format("Non sono riuscito a recuperare un tmplBio dalla pagina %s", sorgente);
            System.out.println(message);
        }

    }


    @ParameterizedTest
    @Order(4)
    @MethodSource(value = "VOCE_BIOGRAFICA")
    @DisplayName("4 - estraeMappa")
        //--nome della pagina
        //--esiste sul server wiki
    void estraeMappa(String nomePagina, boolean esiste) {
        System.out.println("4 - estraeMappa");
        sorgente = nomePagina;
        previstoBooleano = esiste;
        System.out.println(VUOTA);

        if (previstoBooleano && textService.isValid(sorgente)) {
            mappaOttenuta = service.estraeMappa(sorgente);
        }
        else {
            return;
        }

        if (mappaOttenuta != null) {
            message = String.format("Mappa dei valori di %s", sorgente);
        }
        else {
            message = String.format("Non sono riuscito a costruire la mappa di %s", sorgente);
        }
        System.out.println(message);
        System.out.println(VUOTA);
        if (mappaOttenuta != null) {
            printMappaBio(mappaOttenuta);
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
