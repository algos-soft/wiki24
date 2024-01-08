package it.algos.wiki24.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
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
 * Time: 08:22
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista anno nato")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAnnoNatoTest extends ListaTest {

    private ListaAnnoNato istanza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAnnoNato.class;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(101)
    @DisplayName("101 - listaBio")
        //--nome giorno
        //--typeCrono
    void listaBio(String nomeAnno, TypeLista type) {
        System.out.println(("101 - listaBio"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        listaBio = appContext.getBean(ListaAnnoNato.class, sorgente).listaBio();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaBio);
            return;
        }
        assertNotNull(listaBio);
        printBioLista(listaBio);
    }

}
