package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

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

    protected Stream<Arguments> getListeStream() {
        return ANNI();
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAnnoNato.class;
        super.currentModulo = annoModulo;
        super.currentType = TypeLista.annoNascita;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    //    @ParameterizedTest
    @MethodSource(value = "ANNI")
    @Order(9001)
    @DisplayName("9001 - print")
    //--nome anno
    //--typeCrono
    void print(String nomeAnno, TypeLista type) {
        System.out.println(("9001 - print"));
        System.out.println(VUOTA);
        if (!validoAnnoNato(nomeAnno, type)) {
            return;
        }
        sorgente = nomeAnno;

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).testoBody();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        printBodyLista(VUOTA);
    }

}
