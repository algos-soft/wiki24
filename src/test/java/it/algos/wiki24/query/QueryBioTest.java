package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Dec-2023
 * Time: 20:03
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryPage")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QueryBioTest extends QueryTest {

    /**
     * Classe principale di riferimento <br>
     */
    private QueryBio istanza;

    private WrapBio wrapBio;

    private BioServerEntity bioServerEntity;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryBio.class;
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
        wrapBio = null;
        bioServerEntity = null;
    }


    @Test
    @Order(2)
    @DisplayName("2 - Request errata. Manca il wikiTitle")
    void manca() {
        System.out.println(("2 - Request errata. Manca il wikiTitle"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryRead.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    @ParameterizedTest
    @MethodSource(value = "PAGINE_BIO")
    @Order(51)
    @DisplayName("51 - Recupera wrapBioTitle")
        //--titolo
        //--pagina esistente
    void wrapBioTitle(String wikiTitleVoce, boolean paginaEsistente, boolean biografiaEsistente) {
        System.out.println(("51 - Recupera wrapBioTitle"));

        sorgente = wikiTitleVoce;
        wrapBio = appContext.getBean(QueryBio.class).getWrapBio(sorgente);
        assertEquals(paginaEsistente, wrapBio != null);
        if (paginaEsistente) {
            System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        }
        else {
            System.out.println(String.format("La pagina [[%s]] non esiste su wikipedia", sorgente));
        }
        printWrapBio(wrapBio);
    }

    @Test
    @Order(52)
    @DisplayName("52 - Recupera wrapBioPageIds")
    void wrapBioPageIds() {
        System.out.println(("52 - Recupera wrapBioPageIds"));

        sorgenteLong = 14118;
        wrapBio = appContext.getBean(QueryBio.class).getWrapBio(sorgenteLong);
        if (wrapBio.isValida()) {
            System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        }
        printWrapBio(wrapBio);


        System.out.println(VUOTA);
        sorgenteLong = 2741616;
        wrapBio = appContext.getBean(QueryBio.class).getWrapBio(sorgenteLong);
        if (wrapBio.isValida()) {
            System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        }
        else {
            System.out.println(String.format("La pagina [[%s]] non esiste su wikipedia", sorgente));
        }
        printWrapBio(wrapBio);
    }


    @Test
    @Order(101)
    @DisplayName("101 - QueryService.getBio tramite wikiTile (text)")
    void getWrapBioText() {
        System.out.println(("101 - QueryService.getBio tramite wikiTile (text)"));

        sorgente="Louis Winslow Austin";
        wrapBio= appContext.getBean(QueryBio.class).getWrapBio(sorgente);
        if (wrapBio.isValida()) {
            System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        }
        printWrapBio(wrapBio);
    }
    @Test
    @Order(102)
    @DisplayName("102 - QueryService.getBio tramite pageIds (long)")
    void getWrapBioLong() {
        System.out.println(("102 - QueryService.getBio tramite pageIds (long)"));

        sorgenteLong=241617;
        wrapBio= appContext.getBean(QueryBio.class).getWrapBio(sorgenteLong);
        if (wrapBio.isValida()) {
            System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        }
        printWrapBio(wrapBio);
    }

}
