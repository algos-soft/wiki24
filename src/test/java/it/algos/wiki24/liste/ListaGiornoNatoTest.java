package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.giorno.*;
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
        super.setUpAll();
        super.currentModulo = giornoModulo;
        super.currentType = TypeLista.giornoNascita;
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
        sorgente = "29 febbraio";

        listaStr = appContext.getBean(ListaGiornoNato.class, sorgente).listaTestoDidascalie();
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
        sorgente = "29 febbraio";

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).testoBody();
        assertTrue(textService.isValid(ottenuto));
        queryService.write(wikiTitle, ottenuto);
    }

}



