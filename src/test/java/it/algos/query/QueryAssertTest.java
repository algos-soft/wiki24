package it.algos.query;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.wiki.query.*;
import static it.algos.wiki24.wiki.query.AQuery.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import java.io.*;
import java.net.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mar, 17-mag-2022
 * Time: 17:03
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("query")
@DisplayName("Test QueryAssert")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryAssertTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryAssert istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        assertNull(istanza);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @Test
    @Order(1)
    @DisplayName("1- Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new QueryAssert();
        assertNotNull(istanza);
        System.out.println(("1- Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Non collegato")
    void collegatoUser() {
        appContext.getBean(QueryLogin.class).urlRequest(AETypeUser.user);
        assertNotNull(botLogin);
        assertTrue(botLogin.isValido());
        assertEquals(botLogin.getUserType(), AETypeUser.user);

        ottenutoBooleano = appContext.getBean(QueryAssert.class).isEsiste();
        assertFalse(ottenutoBooleano);
    }

//    @Test
//    @Order(3)
//    @DisplayName("3 - Collegato come bot")
//    void collegatoBot() {
//        appContext.getBean(QueryLogin.class).urlRequest(AETypeUser.bot);
//        assertNotNull(botLogin);
//        assertTrue(botLogin.isValido());
//        assertEquals(botLogin.getUserType(), AETypeUser.bot);
//
//        ottenutoBooleano = appContext.getBean(QueryAssert.class).isEsiste();
//        assertTrue(ottenutoBooleano);
//    }

    @Test
    @Order(33)
    @DisplayName("33 - Semplice")
    void provaCookies() {
        URLConnection urlConn;
//        String urlDomain = WIKI_PARSE;
        String urlDomain = WIKI_QUERY;
        urlDomain+="Abeloya";
        String urlResponse;

        try {
            urlConn = new URL(urlDomain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
            urlResponse = this.sendRequest(urlConn);
            int a=87;
        } catch (Exception unErrore) {
            logger.error(new WrapLog().message(unErrore.getMessage()).usaDb());
        }

    }


    /**
     * Invia la request (GET oppure POST) <br>
     *
     * @param urlConn connessione con la request
     *
     * @return valore di ritorno della request
     */
    public String sendRequest(URLConnection urlConn) throws Exception {
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        if (urlConn == null) {
            return VUOTA;
        }

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, "UTF8");

        // read the response
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
        }

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        return textBuffer.toString();
    }

}