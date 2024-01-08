package it.algos.wiki24.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Jan-2024
 * Time: 18:29
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista anno morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAnnoMortoTest extends ListaTest {


    private ListaAnnoMorto istanza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAnnoNato.class;
        super.currentModulo = annoModulo;
        super.currentType = TypeLista.annoMorte;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }

//    @ParameterizedTest
//    @MethodSource(value="ANNI")
//    @Order(101)
//    @DisplayName("101 - listaBio")
//    void listaBio(String nomeLista, TypeLista typeSuggerito) {
//        super.fixListaBio(nomeLista, typeSuggerito);
//    }
}
