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
        super.setUpAll();
        super.currentModulo = annoModulo;
        super.currentType = TypeLista.annoNascita;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    //    @Test
    @Order(9001)
    @DisplayName("9001 - print (da disabilitare)")
    void print() {
        String wikiTitle = "Utente:Biobot/2";
        System.out.println(("9001 - print (da disabilitare)"));
        System.out.println(VUOTA);
        sorgente = "38 a.C.";
        sorgente = "1467";

        listaStr = appContext.getBean(ListaAnnoNato.class, sorgente).listaTestoDidascalie();
        assertNotNull(listaStr);
        StringBuffer buffer = new StringBuffer();
        for (String riga : listaStr) {
            buffer.append(ASTERISCO);
            buffer.append(riga);
            buffer.append(CAPO);
        }
        queryService.write(wikiTitle, buffer.toString());
    }

//    @Test
    @Order(9002)
    @DisplayName("9002 - print (da disabilitare)")
    void print2() {
        String wikiTitle = "Utente:Biobot/2";
        System.out.println(("9002 - print (da disabilitare)"));
        System.out.println(VUOTA);
        sorgente = "38 a.C.";
        sorgente = "1467";

        ottenuto = appContext.getBean(ListaAnnoNato.class, sorgente).testoBody();
        assertTrue(textService.isValid(ottenuto));
        queryService.write(wikiTitle, ottenuto);
    }

}
