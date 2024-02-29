package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
import java.util.*;


/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 17-Feb-2024
 * Time: 06:33
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Liste")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaTest extends WikiStreamTest {

    @Inject
    QueryService queryService;

    public static final boolean ESEGUE_SOLO_BODY = false;

    /**
     * Classe principale di riferimento <br>
     */
    private Lista istanza;

    private List<WrapDidascalia> listaWrapDidascalie;

    private Map<String, List<WrapDidascalia>> mappaWrapDidascalie;

    private Map<String, List<WrapDidascalia>> mappaSottoPagine;

    private Map<String, Integer> mappaDimensioni;

    Map<String, WrapLista> mappaGenerale;

    private WrapLista wrapLista;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = Lista.class;
        super.setUpAll();

        super.ammessoCostruttoreVuoto = false;
        super.usaCollectionName = false;
        super.usaCurrentModulo = false;
        super.usaTypeLista = true;
        super.byPassaErrori = true; //false in fase di debug e true alla fine per essere sicuri che i tests non vadano negli errori previsti
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        istanza = null;
        currentModulo = null;
        listaWrapDidascalie = null;
        mappaWrapDidascalie = null;
        mappaDimensioni = null;
        mappaSottoPagine = null;
        mappaGenerale = null;
        wrapLista = null;
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(101)
    @DisplayName("101 - getNumBio")
    void getNumBio(String nomeLista, TypeLista typeLista) {
        System.out.println(("101 - getNumBio"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            System.out.println(VUOTA);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }

        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenutoIntero = istanza.getNumBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di [%s] per type%s[%s] sono [%s]", nomeLista, FORWARD, typeLista.name(), ottenuto);
            System.out.println(message);
            message = String.format("Rimanda direttamente al service BioMongoModulo, SENZA nessuna elaborazione nell'istanza di %s.", clazzName);
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println("numBio VALIDA");
        }
        else {
            if (ottenutoIntero == INT_ERROR) {
                if (byPassaErrori) {
                    message = String.format("Probabilmente manca il typeLista di [%s]", nomeLista);
                    logger.info(new WrapLog().message(message));
                }
                assertTrue(false);
                System.out.println(VUOTA);
                System.out.println("numBio ERRATA - mancano parametri validi");
            }
            else {
                printMancanoBio("La listaBio", nomeLista, typeLista);
                System.out.println(VUOTA);
                System.out.println("numBio VUOTA");
            }
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(201)
    @DisplayName("201 - listaBio")
    void listaBio(String nomeLista, TypeLista typeLista) {
        System.out.println(("201 - listaBio"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaBio = istanza.getListaBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaBio);
            return;
        }
        if (listaBio == null) {
            assertTrue(false);
            return;
        }

        if (listaBio.size() > 0) {
            message = String.format("Lista delle [%d] biografie di [%s] per type%s[%s]", listaBio.size(), nomeLista, FORWARD, typeLista.name(), typeLista.getGiornoAnno());
            System.out.println(message);
            message = String.format("Rimanda direttamente al service BioMongoModulo, SENZA nessuna elaborazione nell'istanza di %s.", clazzName);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listaBio);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(301)
    @DisplayName("301 - listaWrapDidascalie")
    void listaWrapDidascalie(String nomeLista, TypeLista typeLista) {
        System.out.println(("301 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaWrapDidascalie = istanza.getListaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaWrapDidascalie);
            return;
        }
        if (listaWrapDidascalie == null) {
            assertTrue(false);
            return;
        }

        if (listaWrapDidascalie.size() > 0) {
            message = String.format("Lista dei [%d] wrap di type%s[%s] per %s [%s]", listaWrapDidascalie.size(), FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista);
            System.out.println(message);
            message = "Costruisce un wrap per ogni elemento della listaBio recuperata da BioMongoModulo.";
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapDidascalie(listaWrapDidascalie, sorgente);
        }
        else {
            printMancanoBio("La listaWrap", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(401)
    @DisplayName("401 - listaTestoDidascalia")
    void listaTestoDidascalia(String nomeLista, TypeLista typeLista) {
        System.out.println(("401 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista, typeLista).listaTestoDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr == null) {
            assertTrue(false);
            return;
        }

        if (listaStr.size() > 0) {
            message = String.format("Lista delle [%s] didascalie di type%s[%s] per %s [%s]", textService.format(listaStr.size()), FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista);
            System.out.println(message);
            message = "Lista esemplificativa fine a se stessa che NON viene utilizzata da bodyText che usa direttamente listaWrapDidascalie.";
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println("Prime 10");
            System.out.println(VUOTA);
            print(listaStr.subList(0, Math.min(10, listaStr.size())));
        }
        else {
            printMancanoBio("Le lista delle didascalie", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(501)
    @DisplayName("501 - mappaWrap")
    void mappaWrap(String nomeLista, TypeLista typeLista) {
        System.out.println(("501 - mappaWrap"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        mappaWrapDidascalie = istanza.getMappaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaWrapDidascalie);
            return;
        }
        if (mappaWrapDidascalie == null) {
            assertTrue(false);
            return;
        }

        if (mappaWrapDidascalie.size() > 0) {
            System.out.println("Prime 10 per ogni paragrafo");
            System.out.println(VUOTA);
            printMappaLista(mappaWrapDidascalie);
        }
        else {
            printMancanoBio("La mappa delle didascalie", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(503)
    @DisplayName("503 - mappaWrapDidascalie")
    void mappaWrapDidascalie(String nomeLista, TypeLista typeLista) {
        System.out.println(("503 - mappaWrapDidascalie"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        mappaWrapDidascalie = istanza.getMappaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaWrapDidascalie);
            return;
        }
        if (mappaWrapDidascalie == null) {
            assertTrue(false);
            return;
        }

        message = String.format("Mappa di type%s[%s] per [%s]", FORWARD, typeLista.name(), nomeLista);
        System.out.println(message);
        if (mappaWrapDidascalie.size() > 0) {
            printMappaWrap(mappaWrapDidascalie);
        }
        else {
            printMancanoBio("La mappa delle didascalie", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(504)
    @DisplayName("504 - key della mappa (paragrafi)")
    void keyParagrafi(String nomeLista, TypeLista typeLista) {
        System.out.println(("504 - key della mappa (key)"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getKeyParagrafi();

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (paragrafi)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : listaStr) {
                message = String.format("%s", key);
                System.out.println(message);
            }
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, typeLista);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(505)
    @DisplayName("505 - dimensione dei paragrafi")
    void dimParagrafi(String nomeLista, TypeLista typeLista) {
        System.out.println(("505 - dimensione dei paragrafi"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        mappaDimensioni = istanza.getMappaParagrafi();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaDimensioni);
            return;
        }

        if (mappaDimensioni != null) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d chiavi (paragrafi)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, mappaDimensioni.size());
            System.out.println(message);

            for (String key : mappaDimensioni.keySet()) {
                message = String.format("%s%s%s", key, FORWARD, mappaDimensioni.get(key));
                System.out.println(message);
            }
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(506)
    @DisplayName("506 - key della mappa (sottopagine)")
    void keySottoPagine(String nomeLista, TypeLista typeLista) {
        System.out.println(("506 - key della mappa (sottopagine)"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottopagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : listaStr) {
                message = String.format("%s", key);
                System.out.println(message);
            }
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, typeLista);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(507)
    @DisplayName("507 - dimensione delle sottoPagine")
    void dimSottoPagine(String nomeLista, TypeLista typeLista) {
        System.out.println(("507 - dimensione delle sottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_BODY) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d chiavi (sottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);

            for (String key : listaStr) {
                message = String.format("%s%s%s", key, FORWARD, istanza.getNumBioSottoPagina(key));
                System.out.println(message);
            }
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(508)
    @DisplayName("508 - bodyTextNew")
    void bodyTextNew(String nomeLista, TypeLista typeLista) {
        System.out.println(("508 - bodyTextNew"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        mappaGenerale = istanza.getMappaGenerale();
        ottenuto = istanza.bodyTextNew();
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(601)
    @DisplayName("601 - bodyText")
    void bodyText(String nomeLista, TypeLista typeLista) {
        System.out.println(("601 - bodyText"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
//        if (true) {
//            System.out.println("Sospesa");
//            return;
//        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getBodyText();
        ottenutoIntero = istanza.getNumBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        if (textService.isValid(ottenuto)) {
            message = String.format("Le biografie di [%s] per type%s[%s] sono [%d]", nomeLista, FORWARD, typeLista.name(), ottenutoIntero);
            System.out.println(message);
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s] con eventuali sottopagine e divisori colonne", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi(if) normali con dimensioni(if), sottopagine(if) e include(if)");
            message = String.format("Paragrafi(if)%sPagine con più di %s voci ed almeno %s paragrafi (più di %s)", FORWARD, 50, 3, 2);
            System.out.println(message);
            message = String.format("Dimensioni(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            message = String.format("Sottopagine(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            message = String.format("Include(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println(ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, typeLista);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(602)
    @DisplayName("602 - printBodyText")
    void printBodyText(String nomeLista, TypeLista typeLista) {
        System.out.println(("602 - printBodyText"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (true) {
            System.out.println("Sospesa");
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getBodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        if (textService.isValid(ottenuto)) {
            queryService.writeTest(nomeLista + CAPO + ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(701)
    @DisplayName("701 - body singola sottopagina")
    void bodySottoPagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("701 - body singola sottopagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (false) {
            System.out.println("Sospesa");
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (listaStr.size() > 0) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d (sottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println("Prime 5 sottopagine");
            for (String keySottoPagina : listaStr.subList(0, Math.min(5, listaStr.size()))) {
                ottenuto = istanza.getBodySottoPagina(keySottoPagina);
                if (textService.isValid(ottenuto)) {
                    message = String.format("[%s%s%s]", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);

                    System.out.println(ottenuto);
                    System.out.println(VUOTA);
                }
                else {
                    message = String.format("Non sono riuscito a recuperare il bodyText della sottoPagina [%s%s%s] esistente", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);
                }
            }
        }
        else {
            printMancanoBio("Non ci sono sottoPagine nella lista", nomeLista, typeLista);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(702)
    @DisplayName("702 - bodyTextNewSottoPagina")
    void bodyTextNewSottoPagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("702 - bodyTextNewSottoPagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (listaStr.size() > 0) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d (sottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println("Prime 5 sottopagine");
            for (String keySottoPagina : listaStr.subList(0, Math.min(5, listaStr.size()))) {
                ottenuto = istanza.getBodySottoPaginaNew(keySottoPagina);
                if (textService.isValid(ottenuto)) {
                    message = String.format("[%s%s%s]", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);

                    System.out.println(ottenuto);
                    System.out.println(VUOTA);
                }
                else {
                    message = String.format("Non sono riuscito a recuperare il bodyText della sottoPagina [%s%s%s] esistente", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);
                }
            }
        }
        else {
            printMancanoBio("Non ci sono sottoPagine nella lista", nomeLista, typeLista);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(703)
    @DisplayName("703 - printBodySottoPagina")
    void printBodySottoPagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("703 - printBodySottoPagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (true) {
            System.out.println("Sospesa");
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (listaStr.size() > 0) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d (sottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String keySottoPagina : listaStr) {
                ottenuto = istanza.getBodySottoPagina(keySottoPagina);
                if (textService.isValid(ottenuto)) {
                    queryService.writeTest(nomeLista + CAPO + ottenuto);
                }
                else {
                    message = String.format("Non sono riuscito a recuperare il bodyText della sottoPagina [%s%s%s] esistente", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);
                }
            }
        }
        else {
            printMancanoBio("Non ci sono sottoPagine nella lista", nomeLista, typeLista);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(801)
    @DisplayName("801 - body singola sottoSottoPagina")
    void xyz(String nomeLista, TypeLista typeLista) {
        System.out.println(("801 - body singola sottoSottoPagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (true) {
            System.out.println("Sospesa");
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();

        if (listaStr.size() > 0) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d (sottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println("Prime 5 sottopagine");
            for (String keySottoPagina : listaStr.subList(0, Math.min(10, listaStr.size()))) {
                ottenuto = istanza.getBodySottoPagina(keySottoPagina);
                if (textService.isValid(ottenuto)) {
                    message = String.format("[%s%s%s]", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);

                    System.out.println(ottenuto);
                    System.out.println(VUOTA);
                }
                else {
                    message = String.format("Non sono riuscito a recuperare il bodyText della sottoPagina [%s%s%s] esistente", nomeLista, SLASH, keySottoPagina);
                    System.out.println(message);
                }
            }
        }
        else {
            printMancanoBio("Non ci sono sottoPagine nella lista", nomeLista, typeLista);
        }
    }

    @Test
    @DisplayName("Sovrascritto da WikiTest (checkIniziale - non usato)")
    void checkIniziale() {
    }


    @Test
    @DisplayName("Sovrascritto da WikiTest (senzaParametroNelCostruttore - non usato)")
    void senzaParametroNelCostruttore() {
    }

    @Test
    @DisplayName("Sovrascritto da WikiTest (checkParametroNelCostruttore - non usato)")
    void checkParametroNelCostruttore() {
    }

    protected void printBioLista(List<BioMongoEntity> listaBio) {
        String message;
        int max = 10;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie su un totale di %s", max, listaBio.size());
            System.out.println(message);
            message = "Ordinate per forzaOrdinamento";
            System.out.println(message);
            message = "Ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBase(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBase(listaBio.subList(iniB, endB), iniB);
        }
    }


    protected void printBioBase(List<BioMongoEntity> listaBio, final int inizio) {
        int cont = inizio;

        for (BioMongoEntity bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(bio.ordinamento != null ? textService.setQuadre(bio.ordinamento) : VUOTA);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.wikiTitle));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.nome) ? bio.nome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.cognome) ? bio.cognome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.println(SPAZIO);
        }
    }

    protected void printWrapDidascalie(List<WrapDidascalia> wrap, String sorgente) {
        String message;
        int max = 10;
        int tot = wrap.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (wrap != null) {
            message = String.format("Faccio vedere una lista dei primi e degli ultimi %d wrap", max);
            System.out.println(message);

            printWrapBase(wrap.subList(iniA, endA), iniA, sorgente);
            System.out.println(TRE_PUNTI);
            printWrapBase(wrap.subList(iniB, endB), iniB, sorgente);
        }
    }

    protected void printWrapBase(List<WrapDidascalia> listaWrap, final int inizio, String sorgente) {
        int cont = inizio;

        for (WrapDidascalia wrap : listaWrap) {
            printWrap(wrap, sorgente);
        }
    }

    protected void printMappaLista(Map<String, List<WrapDidascalia>> mappa) {
        System.out.println(VUOTA);
        System.out.println(String.format("Mappa di %s elementi (paragrafi)", mappa.size()));
        System.out.println(VUOTA);

        if (mappa != null && mappa.size() > 0) {
            for (String paragrafo : mappa.keySet()) {
                message = String.format("%s", paragrafo);
                System.out.println(message);
                for (WrapDidascalia wrap : mappa.get(paragrafo).subList(0, Math.min(10, mappa.get(paragrafo).size()))) {
                    printWrap(wrap);
                }
                System.out.println(VUOTA);
                System.out.println(VUOTA);
            }
        }
    }

    protected void printWrap(WrapDidascalia wrap) {
        System.out.println(VUOTA);
        System.out.println(String.format("Type: %s", wrap.getType()));
        System.out.println(String.format("Didascalia: %s", wrap.getDidascalia()));
        System.out.println(String.format("Ordinamento: %s", wrap.getOrdineNumerico()));
        System.out.println(String.format("Primo livello: %s", wrap.getPrimoLivello()));
        System.out.println(String.format("Secondo livello: %s", wrap.getSecondoLivello()));
        System.out.println(String.format("Terzo livello: %s", wrap.getTerzoLivello()));
        System.out.println(String.format("Quarto livello: %s", wrap.getQuartoLivello()));
    }

    protected void printMappaWrap(Map<String, List<WrapDidascalia>> mappa) {
        if (mappa == null || mappa.size() == 0) {
            message = String.format("La mappa di didascalie per la lista è vuota");
            System.out.println(message);
            return;
        }

        message = String.format("Ci sono [%s] suddivisioni (ordinate) di 1° livello (paragrafi) per la mappa", mappa.size());
        System.out.println(message);
        for (String paragrafo : mappa.keySet()) {
            System.out.println(paragrafo);

            for (WrapDidascalia wrap : mappa.get(paragrafo)) {
                System.out.print(TAB);
                System.out.println(wrap.getDidascalia());
            }
        }
    }

}