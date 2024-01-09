package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 17:33
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista giorno nato")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiornoNatoTest extends ListaTest {


    private ListaGiornoNato istanza;


    protected Stream<Arguments> getListeStream() {
        return GIORNI();
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaGiornoNato.class;
        super.currentModulo = giornoModulo;
        super.currentType = TypeLista.giornoNascita;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }

















    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(9001)
    @DisplayName("9001 - print")
    //--nome giorno
    //--typeCrono
    void print(String nomeGiorno, TypeLista type) {
        System.out.println(("9001 - print"));
        System.out.println(VUOTA);
//        if (!validoGiornoNato(nomeGiorno, type)) {
//            return;
//        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).testoBody();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        printBodyLista(VUOTA);
    }

}



