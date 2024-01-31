package it.algos.wiki24.modulo;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.geografia.stato.*;
import it.algos.base24.basetest.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import static it.algos.wiki24.backend.wrapper.WResult.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 11:04
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("BioMongo Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioMongoModuloTest extends WikiModuloTest {



    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = BioMongoEntity.class;
        super.listClazz = BioMongoList.class;
        super.viewClazz = BioMongoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


//    @Test
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = currentModulo.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di [%s]", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(101)
    @DisplayName("101 - findAllByGiornoNato")
    void findAllByGiornoNato(final String nomeLista, final TypeLista type) {
        System.out.println(("101 - findAllByGiornoNato"));
        System.out.println(VUOTA);
        if (!validoGiorno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.giornoNascita) {
            listaBio = modulo.findAllByGiornoNato(nomeLista);
            this.fixListaBio(nomeLista, listaBio);
        }

    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(201)
    @DisplayName("201 - findAllByGiornoMorto")
    void findAllByGiornoMorto(final String nomeLista, final TypeLista type) {
        System.out.println(("201 - findAllByGiornoMorto"));
        System.out.println(VUOTA);
        if (!validoGiorno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.giornoMorte) {
            listaBio = modulo.findAllByGiornoMorto(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(301)
    @DisplayName("301 - findAllByAnnoNato")
    void findAllByAnnoNato(final String nomeLista, final TypeLista type) {
        System.out.println(("301 - findAllByAnnoNato"));
        System.out.println(VUOTA);
        if (!validoAnno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.annoNascita) {
            listaBio = modulo.findAllByAnnoNato(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(401)
    @DisplayName("401 - findAllByAnnoMorto")
    void findAllByAnnoMorto(final String nomeLista, final TypeLista type) {
        System.out.println(("401 - findAllByAnnoMorto"));
        System.out.println(VUOTA);
        if (!validoAnno(nomeLista, type)) {
            return;
        }

        if (type == TypeLista.annoMorte) {
            listaBio = modulo.findAllByAnnoMorto(nomeLista);
        }

        this.fixListaBio(nomeLista, listaBio);
    }

}
