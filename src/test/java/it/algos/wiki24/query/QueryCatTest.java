package it.algos.wiki24.query;


import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 11-mag-2022
 * Time: 17:42
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryCat")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryCatTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryCat istanza;




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
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new QueryCat();
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test per una categoria inesistente")
    void nonEsiste() {
        System.out.println(("2 - Test per una categoria inesistente"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryCat.class);
        assertNotNull(istanza);

        sorgente = CATEGORIA_INESISTENTE;
        ottenutoRisultato = istanza.urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("La categoria [[%s]] non esiste su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Categoria (istanza) -> ids")
    void istanzaIds() {
        System.out.println(("3 - Categoria (istanza) -> ids"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryCat.class);
        assertNotNull(istanza);

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = istanza.urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Categoria (istanza) -> title")
    void istanzaTitle() {
        System.out.println(("4 - Categoria (istanza) -> title"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryCat.class).title();
        assertNotNull(istanza);

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = istanza.urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Categoria (urlRequest) -> ids")
    void urlRequestIds() {
        System.out.println(("5 - Categoria (urlRequest) -> ids"));

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Categoria (urlRequest) -> title")
    void urlRequestTitle() {
        System.out.println(("6 - Categoria (urlRequest) -> title"));

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = appContext.getBean(QueryCat.class).title().urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(7)
    @DisplayName("7 - Categoria (getListaPageIds) -> ids")
    void getListaPageIds() {
        System.out.println(("7 - Categoria (getListaPageIds) -> ids"));

        sorgente = CATEGORIA_ESISTENTE_BREVE;
        listaPageIds = appContext.getBean(QueryCat.class).getPageIds(sorgente);
        assertNotNull(listaPageIds);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printLong(listaPageIds,20);
    }

    @Test
    @Order(8)
    @DisplayName("8 - Categoria (getListaTitles) -> title")
    void getListaTitles() {
        System.out.println(("8 - Categoria (getListaTitles) -> title"));

        sorgente = CATEGORIA_ESISTENTE_BREVE;
        listaStr = appContext.getBean(QueryCat.class).getTitles(sorgente);
        assertNotNull(listaStr);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        print(listaStr);
    }

    @Test
    @Order(9)
    @DisplayName("9 - Categoria titolo errato rimediabile")
    void getListaTitles2() {
        System.out.println(("9 - Categoria titolo errato rimediabile"));

        sorgente = "Categoria:" + CATEGORIA_ESISTENTE_BREVE;
        listaStr = appContext.getBean(QueryCat.class).getTitles(sorgente);
        assertNotNull(listaStr);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        print(listaStr);
    }


    //    @Test
    @Order(10)
    @DisplayName("10 - Categoria esistente login come user")
    void esisteUser() {
        System.out.println(("10 - Categoria esistente login come user"));
        appContext.getBean(QueryLogin.class).urlRequest();

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        //        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(30)
    @DisplayName("30 - Obbligatorio PRIMA del 31 per regolare il botLogin")
    void nonCollegato() {
        assertNotNull(botLogin);
        botLogin.setUserType(TypeUser.anon);
        assertTrue(botLogin.getUserType().name().equals(TypeUser.anon.name()));
    }

    //    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(31)
    @DisplayName("31 - Test per categorie senza collegamento")
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    void esisteNonCollegato(final String wikiCategoria, final boolean categoriaEsistenteAnonymous) {
        System.out.println("31 - Test per categorie senza collegamento");
        System.out.println("Il botLogin è stato regolato nel test '30'");

        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(wikiCategoria);
        assertNotNull(ottenutoRisultato);
        assertEquals(categoriaEsistenteAnonymous, ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come anonymous", wikiCategoria));
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    //    @Order(30)
    //    @DisplayName("30 - Test per categorie senza collegamento")
    //    void esisteNonCollegato() {
    //        System.out.println("30 - Test per categorie senza collegamento");
    //        System.out.println("Il botLogin viene regolato come 'anonymous'");
    //        assertNotNull(botLogin);
    //        assertTrue(botLogin.getUserType().name().equals(AETypeUser.anonymous.name()));
    //
    //        //--categoria
    //        //--esiste
    //        System.out.println(VUOTA);
    //        CATEGORIE().forEach(this::esisteNonCollegatoBase);
    //    }
    //
    //
    //    //--categoria
    //    //--esiste
    //    void esisteNonCollegatoBase(Arguments arg) {
    //        Object[] mat = arg.get();
    //        sorgente = (String) mat[0];
    //        previstoBooleano = (boolean) mat[1];
    //
    //        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(sorgente);
    //        assertNotNull(ottenutoRisultato);
    //        assertEquals(previstoBooleano, ottenutoRisultato.isValido());
    //
    //        System.out.println(VUOTA);
    //        System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come anonymous", sorgente));
    //        printRisultato(ottenutoRisultato);
    //    }


    //    @Test
    @Order(40)
    @DisplayName("40 - Obbligatorio PRIMA del 41 per regolare il botLogin")
    void collegatoUser() {
        appContext.getBean(QueryLogin.class).urlRequest();
        assertNotNull(botLogin);
        assertTrue(botLogin.isValido());
        assertEquals(botLogin.getUserType(), TypeUser.user);
    }


    //    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(41)
    @DisplayName("41 - Test per categorie collegamento user")
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    void esisteCollegatoUser(final String wikiCategoria, final boolean nonUsato, final boolean categoriaEsistenteUser) {
        System.out.println("41 - Test per categorie collegamento user");
        System.out.println("Il botLogin è stato regolato nel test '40'");

        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(wikiCategoria);
        assertNotNull(ottenutoRisultato);
        assertEquals(categoriaEsistenteUser, ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come user", wikiCategoria));
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    //    @Test
    @Order(50)
    @DisplayName("50 - Obbligatorio PRIMA del 51 per regolare il botLogin")
    void collegatoAdmin() {
        appContext.getBean(QueryLogin.class).urlRequest();
        assertNotNull(botLogin);
        assertTrue(botLogin.isValido());
        assertEquals(botLogin.getUserType(), TypeUser.bot);
    }


    //    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(51)
    @DisplayName("51 - Test per categorie collegamento admin")
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    void esisteCollegatoAdmin(final String wikiCategoria, final boolean nonUsato, final boolean categoriaEsistenteAdmin) {
        System.out.println("51 - Test per categorie collegamento admin");
        System.out.println("Il botLogin è stato regolato nel test '50'");

        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(wikiCategoria);
        assertNotNull(ottenutoRisultato);
        assertEquals(categoriaEsistenteAdmin, ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come admin", wikiCategoria));
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(60)
    @DisplayName("60 - Obbligatorio PRIMA del 61 per regolare il botLogin")
    void collegatoBot() {
        appContext.getBean(QueryLogin.class).urlRequest();
        assertNotNull(botLogin);
        assertTrue(botLogin.isValido());
        assertEquals(botLogin.getUserType(), TypeUser.bot);
    }

    //    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    @Order(61)
    @DisplayName("61 - Test per categorie collegamento bot")
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    void esisteCollegatoBot(final String wikiCategoria, boolean nonUsato, boolean nonUsato2, final boolean categoriaEsistenteBot) {
        System.out.println("61 - Test per categorie collegamento bot");
        System.out.println("Il botLogin è stato regolato nel test '60'");
        sorgente = wikiCategoria;

        ottenutoRisultato = appContext.getBean(QueryCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertEquals(categoriaEsistenteBot, ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come bot", sorgente));
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }


    //    @ParameterizedTest
    @MethodSource(value = "CATEGORIE")
    //--categoria
    //--esiste
    @Order(70)
    @DisplayName("70 - Recupera direttamente la lista di pageids")
    void getLista(final String wikiCategoria, final boolean categoriaEsistente) {
        System.out.println("70 - Recupera direttamente la lista di pageids");
        System.out.println("Il botLogin viene resettato per collegarsi come anonymous");
        sorgente = wikiCategoria;

        listaPageIds = appContext.getBean(QueryCat.class).getPageIds(wikiCategoria);
        if (categoriaEsistente) {
            assertNotNull(listaPageIds);
            assertEquals(categoriaEsistente, listaPageIds.size() > 0);

            System.out.println(VUOTA);
            System.out.println(String.format("Esamino la categoria [[%s]] in collegamento come anonymous", sorgente));
            System.out.println(VUOTA);
            System.out.println(String.format("La categoria [[%s]] contiene %d elementi. Ne stampo SOLO i primi 10 (se ci sono)", sorgente, listaPageIds.size()));
            printLista(listaPageIds.subList(0, Math.min(10, listaPageIds.size())));
        }
        else {
            System.out.println(VUOTA);
            System.out.println(String.format("La categoria [[%s]] non esiste su wikipedia", sorgente));
        }
    }

    @Test
    @Order(90)
    @DisplayName("90 - Costruttore col nome -default")
    void costruttoreNomeDefault() {
        System.out.println(("90 - Costruttore col nome - default"));

        sorgente = "Cognomi per lingua";
        ottenutoRisultato = appContext.getBean(QueryCat.class, sorgente).urlRequest();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(91)
    @DisplayName("91 - Costruttore col nome - ids")
    void costruttoreNomeIds() {
        System.out.println(("91 - Costruttore col nome - ids"));

        sorgente = "Cognomi per lingua";
        ottenutoRisultato = appContext.getBean(QueryCat.class, sorgente).ids().urlRequest();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(92)
    @DisplayName("92 - Costruttore col nome - title")
    void costruttoreNomeTitle() {
        System.out.println(("92 - Costruttore col nome - title"));

        sorgente = "Cognomi per lingua";
        ottenutoRisultato = appContext.getBean(QueryCat.class, sorgente).title().urlRequest();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(93)
    @DisplayName("93 - Costruttore col nome - subCat")
    void costruttoreNomeSubCat() {
        System.out.println(("93 - Costruttore col nome - subCat"));

        sorgente = "Cognomi per lingua";
        ottenutoRisultato = appContext.getBean(QueryCat.class, sorgente).subCat().urlRequest();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(94)
    @DisplayName("94 - Lista subCat")
    void listaSubCat() {
        System.out.println(("94 - Lista subCat"));

        sorgente = "Cognomi per lingua";
        listaStr = appContext.getBean(QueryCat.class, sorgente).getSubCat();
        assertNotNull(listaStr);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        print(listaStr);
    }

    @Test
    @Order(95)
    @DisplayName("95 - Lista normale")
    void listaNormale() {
        System.out.println(("95 - Lista normale"));

        sorgente = "Cognomi coreani";
        listaStr = appContext.getBean(QueryCat.class, sorgente).getTitles();
        assertNotNull(listaStr);
        assertEquals(11, listaStr.size());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        print(listaStr);
    }
    @Test
    @Order(96)
    @DisplayName("96 - Lista con filtro")
    void listaConFiltro() {
        System.out.println(("96 - Lista con filtro"));

        sorgente = "Cognomi coreani";
        listaStr = appContext.getBean(QueryCat.class, sorgente).filtro().getTitles();
        assertNotNull(listaStr);
        assertEquals(10, listaStr.size());

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        print(listaStr);
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}