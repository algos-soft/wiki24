package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 27-Jan-2024
 * Time: 10:05
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("listaupload")
@DisplayName("Liste giorno, anno, attività")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaTest extends WikiStreamTest {


    private Lista istanza;

    protected List<WrapDidascalia> listaWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;

    protected LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottopagina;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = Lista.class;
        super.setUpAll();
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
        mappaSottopagina = null;
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
    }

    @Test
    @Order(3)
    @DisplayName("3 - senzaParametroNelCostruttore")
    void senzaParametroNelCostruttore() {
        //--prova a costruire un'istanza SENZA parametri e controlla che vada in errore se è obbligatorio avere un parametro
        super.fixSenzaParametroNelCostruttore();
    }

    @Test
    @Order(4)
    @DisplayName("4 - checkParametroNelCostruttore")
    void checkParametroNelCostruttore() {
        //--costruisce un'istanza con un parametro farlocco
        //        super.fixCheckParametroNelCostruttore(PARAMETRO, "...nonEsiste...", CHECK, FUNZIONE);
    }

    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista type) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            System.out.println(VUOTA);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }

        ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di [%s] per type%s[%s] sono [%s]", nomeLista, FORWARD, type.name(), ottenuto);
            System.out.println(message);
            message = "Rimanda direttamente al service BioMongoModulo, SENZA nessuna elaborazione nell'istanza di Lista.";
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
                printMancanoBio("La listaBio", nomeLista, type);
                System.out.println(VUOTA);
                System.out.println("numBio VUOTA");
            }
        }
    }

    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(201)
    @DisplayName("201 - listaBio")
    void listaBio(String nomeLista, TypeLista type) {
        System.out.println(("201 - listaBio"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaBio = appContext.getBean(Lista.class, nomeLista).type(type).listaBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaBio);
            return;
        }
        if (listaBio == null) {
            assertTrue(false);
            return;
        }

        if (listaBio.size() > 0) {
            message = String.format("Lista delle [%d] biografie di [%s] per type%s[%s]", listaBio.size(), nomeLista, FORWARD, type.name(), type.getGiornoAnno());
            System.out.println(message);
            message = "Rimanda direttamente al service BioMongoModulo, SENZA nessuna elaborazione nell'istanza di Lista.";
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listaBio);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, type);
        }
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(301)
    @DisplayName("301 - listaWrapDidascalie")
    void listaWrapDidascalie(String nomeLista, TypeLista type) {
        System.out.println(("301 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaWrap = appContext.getBean(Lista.class, nomeLista).type(type).listaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaWrap);
            return;
        }
        if (listaWrap == null) {
            assertTrue(false);
            return;
        }

        if (listaWrap.size() > 0) {
            message = String.format("Lista dei [%d] wrap di type%s[%s] per %s [%s]", listaWrap.size(), FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            message = "Costruisce un wrap per ogni elemento della listaBio recuperata da BioMongoModulo.";
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapDidascalie(listaWrap, sorgente);
        }
        else {
            printMancanoBio("La listaWrap", nomeLista, type);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(401)
    @DisplayName("401 - listaTestoDidascalia")
    void listaTestoDidascalia(String nomeLista, TypeLista type) {
        System.out.println(("401 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaTestoDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr == null) {
            assertTrue(false);
            return;
        }

        if (listaStr.size() > 0) {
            message = String.format("Lista delle [%d] didascalie di type%s[%s] per %s [%s]", listaStr.size(), FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            message = "Lista esemplificativa fine a se stessa che NON viene utilizzata da bodyText che usa direttamente listaWrapDidascalie.";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le lista delle didascalie", nomeLista, type);
        }
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(501)
    @DisplayName("501 - mappaDidascalie")
    void mappaDidascalie(String nomeLista, TypeLista type) {
        System.out.println(("501 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        mappaDidascalie = appContext.getBean(Lista.class, nomeLista).type(type).mappaDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaDidascalie);
            return;
        }
        if (mappaDidascalie == null) {
            assertTrue(false);
            return;
        }

        if (mappaDidascalie.size() > 0) {
            printMappa(type.getTag(), nomeLista, mappaDidascalie);
        }
        else {
            printMancanoBio("La mappa delle didascalie", nomeLista, type);
        }
    }


    //            @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(601)
    @DisplayName("601 - key della mappa (paragrafi)")
    void keyMappa(String nomeLista, TypeLista type) {
        System.out.println(("601 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).keyMappa();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr == null) {
            assertTrue(false);
            return;
        }

        if (listaStr != null) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d chiavi (paragrafi)", FORWARD, type.name(), type.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(701)
    @DisplayName("701 - bodyText")
    void bodyText(String nomeLista, TypeLista type) {
        System.out.println(("701 - bodyText"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previsto = appContext.getBean(Lista.class, nomeLista).type(type).bodyText();
        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        ottenuto = istanza.bodyText();
        assertEquals(previsto, ottenuto);

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s] con eventuali sottopagine e divisori colonne", FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
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
            printMancanoBio("Il testoBody della lista", nomeLista, type);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(801)
    @DisplayName("801 - listaSottoPagine")
    void listaSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("801 - listaSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoArray = appContext.getBean(Lista.class, nomeLista).type(type).listaSottoPagine();
        assertNotNull(previstoArray);
        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);
        assertEquals(previstoArray, listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Elabora il testo body della pagina principale, se non già elaborato";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(802)
    @DisplayName("802 - mappaSottoPagine")
    void mappaSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("802 - mappaSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoMappa = appContext.getBean(Lista.class, nomeLista).type(type).mappaSottoPagine();
        assertNotNull(previstoMappa);
        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        ottenutoMappa = istanza.mappaSottoPagine();
        assertNotNull(ottenutoMappa);
        assertEquals(previstoMappa, ottenutoMappa);

        if (ottenutoMappa != null && ottenutoMappa.size() > 0) {
            message = String.format("Ci sono %s sottopagine nella lista [%s] di type [%s]", ottenutoMappa.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Elabora il testo body della pagina principale, se non già elaborato";
            System.out.println(message);
            message = String.format("SottoPagine: %s", ottenutoMappa.keySet());
            System.out.println(message);
            System.out.println(VUOTA);

            for (String key : ottenutoMappa.keySet()) {
                System.out.println(key);
                System.out.println(ottenutoMappa.get(key));
                System.out.println(VUOTA);
            }
        }
        else {
            message = String.format("Non ci sono sottosottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //        @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(803)
    @DisplayName("803 - listaSottoSottoPagine")
    void listaSottoSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("803 - listaSottoSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoArray = appContext.getBean(Lista.class, nomeLista).type(type).listaSottoSottoPagine();
        assertNotNull(previstoArray);
        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        listaStr = istanza.listaSottoSottoPagine();
        assertNotNull(listaStr);
        assertEquals(previstoArray, listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottosottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Elabora il testo body della pagina principale, se non già elaborato";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottosottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //        @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(804)
    @DisplayName("804 - getMappaSottoSottoPagine")
    void getMappaSottoSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("804 - getMappaSottoSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoMappa = appContext.getBean(Lista.class, nomeLista).type(type).mappaSottoSottoPagine();
        assertNotNull(previstoMappa);
        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        ottenutoMappa = istanza.mappaSottoSottoPagine();
        assertNotNull(ottenutoMappa);
        assertEquals(previstoMappa, ottenutoMappa);

        if (ottenutoMappa != null && ottenutoMappa.size() > 0) {
            message = String.format("Ci sono %s sottosottopagine nella lista [%s] di type [%s]", ottenutoMappa.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Elabora il testo body della pagina principale, se non già elaborato";
            System.out.println(message);
            message = String.format("SottoSottoPagine: %s", ottenutoMappa.keySet());
            System.out.println(message);
            System.out.println(VUOTA);

            for (String key : ottenutoMappa.keySet()) {
                System.out.println(key);
                System.out.println(ottenutoMappa.get(key));
                System.out.println(VUOTA);
            }
        }
        else {
            message = String.format("Non ci sono sottosottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    //    @ParameterizedTest
    //    @MethodSource(value = "LISTA_SOTTO_PAGINE")
    //    @Order(803)
    //    @DisplayName("803 - listaSottoSottoPagine")
    //    void listaSottoSottoPagine(String nomeLista, TypeLista type, String keySottoPagina) {
    //        System.out.println(("803 - listaSottoSottoPagine"));
    //        System.out.println(VUOTA);
    //        if (byPassaErrori && !fixListe(nomeLista, type)) {
    //            return;
    //        }
    //        List<String> listaParagrafi;
    //        List<String> listaSottopagineGetter;
    //        List<String> listaSottopagine;
    //        List<String> listaSottoSottopagine;
    //        LinkedHashMap<String, String> mappaSottoPagine;
    //        LinkedHashMap<String, String> mappaSottoSottoPagine;
    //
    //        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
    //        assertNotNull(istanza);
    //        listaParagrafi = istanza.keyMappa();
    //        assertNotNull(listaParagrafi);
    //
    //        listaSottopagineGetter = istanza.getListaSottoPagine();
    //        assertNotNull(listaSottopagineGetter);
    //        listaSottopagine = istanza.listaSottoPagine();
    //        assertNotNull(listaSottopagine);
    //        assertEquals(listaSottopagine, listaSottopagineGetter);
    //
    //        mappaSottoPagine = istanza.getMappaSottoPagine();
    //        assertNotNull(mappaSottoPagine);
    //        mappaSottoSottoPagine = istanza.getMappaSottoSottoPagine();
    //        assertNotNull(mappaSottoSottoPagine);
    //
    //        if (listaParagrafi.size() > 0) {
    //            message = String.format("Ci sono %s paragrafi nella lista [%s] di type [%s]", listaParagrafi.size(), nomeLista, type.name());
    //            System.out.println(message);
    //            System.out.println(listaParagrafi);
    //            System.out.println(VUOTA);
    //        }
    //
    //        if (listaSottopagine.size() > 0) {
    //            message = String.format("Ci sono %s sottoPagine nella lista [%s] di type [%s]", listaSottopagine.size(), nomeLista, type.name());
    //            System.out.println(message);
    //            System.out.println(listaSottopagine);
    //            System.out.println(VUOTA);
    //            for (String key : mappaSottoPagine.keySet()) {
    //                ottenuto = mappaSottoPagine.get(key);
    //                System.out.println(ottenuto);
    //                System.out.println(VUOTA);
    //            }
    //        }
    //
    //        if (listaSottopagine.size() > 0) {
    //            message = String.format("Ci sono %s sottoSottoPagine nella lista [%s] di type [%s]", listaSottopagine.size(), nomeLista, type.name());
    //            System.out.println(message);
    //            System.out.println(listaSottopagine);
    //            System.out.println(VUOTA);
    //            for (String key : mappaSottoPagine.keySet()) {
    //                ottenuto = mappaSottoPagine.get(key);
    //                System.out.println(ottenuto);
    //                System.out.println(VUOTA);
    //            }
    //        }
    //
    //        //        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottoSottoPagine();
    //        //        if (textService.isEmpty(nomeLista)) {
    //        //            assertNull(listaStr);
    //        //            return;
    //        //        }
    //        //        if (listaStr == null) {
    //        //            assertTrue(false);
    //        //            return;
    //        //        }
    //
    //        //        if (listaStr.size() > 0) {
    //        //            message = String.format("Ci sono %s sottoSottoPagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
    //        //            System.out.println(message);
    //        //            System.out.println(VUOTA);
    //        //            print(listaStr);
    //        //        }
    //        //        else {
    //        //            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, type.name());
    //        //            System.out.println(message);
    //        //        }
    //    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(901)
    @DisplayName("901 - numBioParagrafi")
    void numBioParagrafi(String nomeLista, TypeLista type) {
        System.out.println(("901 - numBioParagrafi"));
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        int previstoTotaleParagrafi;
        int totaleEffettivoPagina = 0;
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).keyMappa();
        previstoTotaleParagrafi = appContext.getBean(Lista.class, nomeLista).type(type).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null && listaStr.size() > 0) {
            for (String keyParagrafo : listaStr) {
                ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keyParagrafo);
                if (ottenutoIntero > 0) {
                    totaleEffettivoPagina += ottenutoIntero;
                    message = String.format("Le biografie di type%s[%s] per il paragrafo [%s] di [%s], sono [%d]", FORWARD, type.name(), keyParagrafo, nomeLista, ottenutoIntero);
                    System.out.println(message);
                }
            }

            if (totaleEffettivoPagina == INT_ERROR || totaleEffettivoPagina == 0) {
                if (type == TypeLista.attivitaSingolare || type == TypeLista.nazionalitaSingolare) {
                    message = String.format("La lista non calcola i paragrafi", nomeLista, previstoTotaleParagrafi);
                    System.out.println(message);
                    return;
                }
            }

            System.out.println(VUOTA);
            message = String.format("In totale nella pagina della lista [%s] ci sono [%d] biografie.", nomeLista, previstoTotaleParagrafi);
            System.out.println(message);
            message = String.format("Nella somma dei paragrafi (%d) della lista [%s] ci sono [%d] biografie.", listaStr.size(), nomeLista, totaleEffettivoPagina);
            System.out.println(message);
            assertEquals(previstoTotaleParagrafi, totaleEffettivoPagina);

            if (!byPassaErrori) {
                System.out.println(VUOTA);
                keySottopaginaErrata = "Brumaio";
                ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keySottopaginaErrata);
                if (ottenutoIntero == INT_ERROR) {
                    message = String.format("Nella lista [%s] non esiste un paragrafo [%s]", nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertNotEquals(INT_ERROR, ottenutoIntero);
                }
                else {
                    message = String.format("Non ci sono biografie di tipo [%s] per il paragrafo [%s/%s]", type.name(), nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertEquals(INT_ERROR, ottenutoIntero);
                }
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(902)
    @DisplayName("902 - numBioSottopagina")
    void numBioSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("902 - numBioSottopagina"));
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        int previstoTotalePagina;
        int totaleEffettivoParagrafiSenzaSottopagina = 0;
        int totaleEffettivoSottoPagine = 0;

        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottoPagine();
        previstoTotalePagina = appContext.getBean(Lista.class, nomeLista).type(type).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keySottopagina);
                if (ottenutoIntero > 0) {
                    totaleEffettivoSottoPagine += ottenutoIntero;
                    message = String.format("Le biografie di type%s[%s] per il mese di %s dell'anno %s, sono [%d]", FORWARD, type.name(), keySottopagina, nomeLista, ottenutoIntero);
                    System.out.println(message);
                }
            }
            System.out.println(VUOTA);
            message = String.format("In totale nella lista [%s] ci sono [%d] biografie", nomeLista, totaleEffettivoSottoPagine);
            System.out.println(message);
            assertTrue(previstoTotalePagina >= totaleEffettivoSottoPagine);
            message = String.format("Che sono meno delle [%d] totali perché alcuni paragrafi NON hanno sottopagina", previstoTotalePagina);
            System.out.println(message);
            ottenutoArray = appContext.getBean(Lista.class, nomeLista).type(type).keyMappa();
            for (String keyParagrafoSenzaSottopagina : ottenutoArray) {
                if (!listaStr.contains(keyParagrafoSenzaSottopagina)) {
                    ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keyParagrafoSenzaSottopagina);
                    totaleEffettivoParagrafiSenzaSottopagina += ottenutoIntero;
                }
            }
            assertEquals(totaleEffettivoSottoPagine + totaleEffettivoParagrafiSenzaSottopagina, previstoTotalePagina);
            System.out.println(VUOTA);
            System.out.println(String.format("totaleEffettivoSottoPagine [%d]", totaleEffettivoSottoPagine));
            System.out.println(String.format("totaleEffettivoParagrafiSenzaSottopagina [%d]", totaleEffettivoParagrafiSenzaSottopagina));
            System.out.println(String.format("previstoTotalePagina [%d]", previstoTotalePagina));

            if (!byPassaErrori) {
                System.out.println(VUOTA);
                keySottopaginaErrata = "Brumaio";
                ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keySottopaginaErrata);
                if (ottenutoIntero == INT_ERROR) {
                    message = String.format("Nella lista [%s] non esiste un paragrafo/sottopagina [%s]", nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertNotEquals(INT_ERROR, ottenutoIntero);
                }
                else {
                    message = String.format("Non ci sono biografie di tipo [%s] per il paragrafo/sottopagina [%s/%s]", type.name(), nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertEquals(INT_ERROR, ottenutoIntero);
                }
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(903)
    @DisplayName("903 - testoSottopagina")
    void testoSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("903 - testoSottopagina"));
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Nella pagina [%s] di type%s[%s], ci sono [%d] sottopagine", nomeLista, FORWARD, type.name(), listaStr.size());
            System.out.println(message);
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Lista.class, nomeLista).type(type).bodySottopagina(keySottopagina);
                assertTrue(textService.isValid(ottenuto));
                if (ottenuto.equals(STRING_ERROR)) {
                    assertTrue(false);
                }
                message = String.format("Testo body della sottopagina [%s] [%s]", nomeLista, keySottopagina);
                System.out.println(message);
                System.out.println(VUOTA);
                System.out.println(ottenuto);
                System.out.println(VUOTA);
            }

            if (!byPassaErrori) {
                keySottopaginaErrata = "Brumaio";
                ottenuto = appContext.getBean(Lista.class, nomeLista).type(type).bodySottopagina(keySottopaginaErrata);
                assertTrue(textService.isValid(ottenuto));
                if (ottenuto.equals(STRING_ERROR)) {
                    assertTrue(true);
                    message = String.format("Nella lista [%s] non esiste una sottopagina [%s]", nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                }
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA_SOTTO_PAGINE")
    @Order(904)
    @DisplayName("904 - getTestoSottoSottopagina")
    void getTestoSottoSottopagina(String nomeLista, TypeLista type, String keySottoPagina) {
        System.out.println(("904 - getTestoSottoSottopagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        istanza = appContext.getBean(Lista.class, nomeLista).type(type);
        assertNotNull(istanza);
        listaStr = istanza.keyMappa();
        assertNotNull(listaStr);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);

        if (listaStr.contains(textService.primaMaiuscola(keySottoPagina))) {
            for (String keySottoSotto : listaStr) {
                //                ottenuto = istanza.getTestoSottoSottopagina(keySottoSotto);
            }
        }
        else {
            message = String.format("Non esiste una sottoPagina [%s%s%s] di type [%s]", nomeLista, SLASH, keySottoPagina, type.name());
            System.out.println(message);
        }

        //        if (listaStr.size() > 0) {
        //            message = String.format("Ci sono %s sottoSottoPagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
        //            System.out.println(message);
        //            System.out.println(VUOTA);
        //            print(listaStr);
        //        }
        //        else {
        //            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, type.name());
        //            System.out.println(message);
        //        }
    }


    //    @Test
    @Order(905)
    @DisplayName("905 - getTestoSottoSottopagina")
    void getTestoSottoSottopagina() {
        System.out.println(("905 - getTestoSottoSottopagina"));
        System.out.println(VUOTA);
        sorgente = "allenatori di calcio";

        istanza = appContext.getBean(Lista.class, sorgente).type(TypeLista.attivitaPlurale);
        assertNotNull(istanza);
        listaStr = istanza.keyMappa();
        assertNotNull(listaStr);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);

        sorgente2 = "belgi";
        ottenuto = istanza.bodySottopagina(sorgente2);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Testo di [%s%s%s]", sorgente, SLASH, sorgente2);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
        System.out.println(VUOTA);

        sorgente2 = "bosniaci";
        ottenuto = istanza.bodySottopagina(sorgente2);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Testo di [%s%s%s]", sorgente, SLASH, sorgente2);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
        System.out.println(VUOTA);

        sorgente2 = "brasiliani";
        ottenuto = istanza.bodySottopagina(sorgente2);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Testo di [%s%s%s]", sorgente, SLASH, sorgente2);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
        System.out.println(VUOTA);

        sorgente2 = TAG_LISTA_NO_NAZIONALITA;
        ottenuto = istanza.bodySottopagina(sorgente2);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Testo di [%s%s%s]", sorgente, SLASH, sorgente2);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
        System.out.println(VUOTA);
    }


    //    @Test
    @Order(906)
    @DisplayName("906 - mappaSottopagina")
    void mappaSottopagina() {
        System.out.println(("906 - mappaSottopagina"));
        System.out.println(VUOTA);
        sorgente = "allenatori di calcio";

        istanza = appContext.getBean(Lista.class, sorgente).type(TypeLista.attivitaPlurale);
        assertNotNull(istanza);
        listaStr = istanza.keyMappa();
        assertNotNull(listaStr);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);

        sorgente2 = "belgi";
        mappaSottopagina = istanza.getMappaSottopagina(sorgente2);
        if (mappaSottopagina != null && mappaSottopagina.size() > 0) {
            message = String.format("Paragrafi di [%s%s%s]", sorgente, SLASH, sorgente2);
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : mappaSottopagina.keySet()) {
                System.out.println(key);
            }
        }
        System.out.println(VUOTA);

        sorgente2 = "bosniaci";
        mappaSottopagina = istanza.getMappaSottopagina(sorgente2);
        if (mappaSottopagina != null && mappaSottopagina.size() > 0) {
            message = String.format("Paragrafi di [%s%s%s]", sorgente, SLASH, sorgente2);
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : mappaSottopagina.keySet()) {
                System.out.println(key);
            }
        }
        System.out.println(VUOTA);

        sorgente2 = "brasiliani";
        mappaSottopagina = istanza.getMappaSottopagina(sorgente2);
        if (mappaSottopagina != null && mappaSottopagina.size() > 0) {
            message = String.format("Paragrafi di [%s%s%s]", sorgente, SLASH, sorgente2);
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : mappaSottopagina.keySet()) {
                System.out.println(key);
            }
        }
        System.out.println(VUOTA);

        sorgente2 = TAG_LISTA_NO_NAZIONALITA;
        mappaSottopagina = istanza.getMappaSottopagina(sorgente2);
        if (mappaSottopagina != null && mappaSottopagina.size() > 0) {
            message = String.format("Paragrafi di [%s%s%s]", sorgente, SLASH, sorgente2);
            System.out.println(message);
            System.out.println(VUOTA);
            for (String key : mappaSottopagina.keySet()) {
                System.out.println(key);
            }
        }
        System.out.println(VUOTA);
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

    protected void printMappa(String tipo, String nome, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappa) {
        if (mappa == null || mappa.size() == 0) {
            message = String.format("La mappa di didascalie per la lista [%s] è vuota", sorgente);
            System.out.println(message);
            return;
        }

        message = String.format("Ci sono [%s] suddivisioni (ordinate) di 1° livello (paragrafi) per la mappa didascalie dei %s il [%s]", mappa.size(), tipo, nome);
        System.out.println(message);
        for (String primoLivello : mappa.keySet()) {
            System.out.println(primoLivello);

            for (String secondoLivello : mappa.get(primoLivello).keySet()) {
                System.out.print(TAB);
                System.out.println(textService.isValid(secondoLivello) ? secondoLivello : NULLO);

                for (String terzoLivello : mappa.get(primoLivello).get(secondoLivello).keySet()) {
                    System.out.print(TAB);
                    System.out.print(TAB);
                    System.out.println(textService.isValid(terzoLivello) ? terzoLivello : NULLO);

                    for (String didascalia : mappa.get(primoLivello).get(secondoLivello).get(terzoLivello)) {
                        System.out.print(TAB);
                        System.out.print(TAB);
                        System.out.print(TAB);
                        System.out.println(didascalia);
                    }
                }
            }
        }
    }

    protected void printMappa(LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappa) {
        if (mappa == null || mappa.size() == 0) {
            message = String.format("La mappa è vuota");
            System.out.println(message);
            return;
        }

        for (String secondoLivello : mappa.keySet()) {
            System.out.print(TAB);
            System.out.println(textService.isValid(secondoLivello) ? secondoLivello : NULLO);

            for (String terzoLivello : mappa.get(secondoLivello).keySet()) {
                System.out.print(TAB);
                System.out.print(TAB);
                System.out.println(textService.isValid(terzoLivello) ? terzoLivello : NULLO);

                for (String didascalia : mappa.get(secondoLivello).get(terzoLivello)) {
                    System.out.print(TAB);
                    System.out.print(TAB);
                    System.out.print(TAB);
                    System.out.println(didascalia);
                }
            }
        }
    }

}
