package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 26-Dec-2023
 * Time: 17:11
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Elabora Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElaboraServiceTest extends WikiTest {

    @Inject
    private ElaboraService service;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    private BioServerEntity bioServerEntity;

    private Map<String, String> mappaBio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ElaboraService.class;
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

        this.mappaBio = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - costruttore")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service) ed ha un costruttore SENZA parametri", clazzName));
        System.out.println("Viene creata da SpringBoot");
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean")
    void getBean() {
        System.out.println(("2 - getBean"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service)", clazzName));
        System.out.println("Viene creata da SpringBoot");
        System.out.println(String.format("Non si può usare appContext.getBean(%s.class)", clazzName));
    }

    @ParameterizedTest
    @MethodSource(value = "BIOGRAFIE")
    @Order(101)
    @DisplayName("101 - Recupera wrapBioTitle")
        //--wikiTitle
        //--numero parametri
    void elaboraBean(String wikiTitleVoce, int numParagrafi) {
        System.out.println(("101 - Recupera wrapBioTitle"));
        sorgente = wikiTitleVoce;
        previstoIntero = numParagrafi;

        bioServerEntity = (BioServerEntity)bioServerModulo.findOneByProperty(FIELD_NAME_WIKI_TITLE,sorgente);
        assertNotNull(bioServerEntity);
        sorgente2 = bioServerEntity.tmplBio;
        mappaBio = service.estraeMappa(sorgente2);
        assertNotNull(mappaBio);
        printMappaTab(mappaBio);
    }


}
