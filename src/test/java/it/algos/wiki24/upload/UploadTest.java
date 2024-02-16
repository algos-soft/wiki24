package it.algos.wiki24.upload;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 27-Jan-2024
 * Time: 14:59
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("listaupload")
@DisplayName("Upload giorno/anno nato/morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadTest extends WikiStreamTest {

    @Inject
    QueryService queryService;

    private Upload istanza;

    private TypeLista type;

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
        type = null;
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

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(201)
    @DisplayName("201 - getNumBio")
    void getNumBio(String nomeLista, TypeLista type) {
        System.out.println(("201 - getNumBio"));
        System.out.println("Numero delle biografie (Bio) che hanno una valore valido per l'intera pagina (letto dalla lista)");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenutoIntero = appContext.getBean(Upload.class, nomeLista).type(type).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di type%s[%s] per %s [%s] sono [%s]", FORWARD, type.name(), type.getGiornoAnno(), nomeLista, ottenuto);
            System.out.println(message);
        }
        else {
            if (ottenutoIntero == INT_ERROR) {
                message = String.format("Probabilmente manca il typeLista di [%s]", nomeLista);
                logger.info(new WrapLog().message(message));
                assertTrue(false);
            }
            else {
                printMancanoBio("La listaBio", nomeLista, type);
            }
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(202)
    @DisplayName("202 - getHeaderText")
    void getHeaderText(String nomeLista, TypeLista type) {
        System.out.println(("202 - getHeaderText"));
        System.out.println("Testo header della pagina con Avviso, Toc, Unconnected, Uneditable, Torna, TmplBio");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getHeaderText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Header di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(203)
    @DisplayName("203 - getIncipitText")
    void getIncipitText(String nomeLista, TypeLista type) {
        System.out.println(("203 - getIncipitText"));
        System.out.println("Testo incipit della pagina con Note a piè pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getIncipitText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }
        if (type == TypeLista.attivitaPlurale || type == TypeLista.nazionalitaPlurale) {
            assertTrue(textService.isValid(ottenuto));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
        }

        message = String.format("Incipit di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(204)
    @DisplayName("204 - getBodyText")
    void getBodyText(String nomeLista, TypeLista type) {
        System.out.println(("204 - getBodyText"));
        System.out.println("Testo body della pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getBodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Body di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(205)
    @DisplayName("205 - getBottomText")
    void getBottomText(String nomeLista, TypeLista type) {
        System.out.println(("205 - getBottomText"));
        System.out.println("Testo bottom della pagina con Correlate, InterProgetto, Portale, Categorie");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getBottomText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Bottom di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(206)
    @DisplayName("206 - getUploadText")
    void getUploadText(String nomeLista, TypeLista type) {
        System.out.println(("206 - getUploadText"));
        System.out.println("Testo completo della pagina con Header, Incipit, Body e Bottom");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getUploadText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("UploadText di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(211)
    @DisplayName("211 - uploadTestPaginaPrincipaleOnly")
    void uploadTestPaginaPrincipaleOnly(String nomeLista, TypeLista type) {
        System.out.println(("211 - uploadTestPaginaPrincipaleOnly"));
        message = String.format("Upload pagina (principale only) di test di [%s%s]", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }
        if (false) {
            System.out.println("Sospesa");
            return;
        }
        if (type == TypeLista.attivitaSingolare || type == TypeLista.nazionalitaSingolare) {
            System.out.println("Disabilitata la possibilità (che esiste nel codice) di stampare un'attività/nazionalità singolare");
            return;
        }

        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).test().uploadOnly();

        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(212)
    @DisplayName("212 - uploadRealPaginaPrincipaleOnly")
    void uploadRealPaginaPrincipaleOnly(String nomeLista, TypeLista type) {
        System.out.println(("212 - uploadRealPaginaPrincipaleOnly"));
        message = String.format("Upload pagina (principale only) di test di [%s%s]", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }
        if (true) {
            System.out.println("Sospesa");
            return;
        }
        if (type == TypeLista.attivitaSingolare || type == TypeLista.nazionalitaSingolare) {
            System.out.println("Disabilitata la possibilità (che esiste nel codice) di stampare un'attività/nazionalità singolare");
            return;
        }

        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).uploadOnly();

        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(303)
    @DisplayName("303 - printIncipitText")
    void printIncipitText(String nomeLista, TypeLista type) {
        System.out.println(("303 - printIncipitText"));
        System.out.println("Stampa del testo incipit della pagina con Note a piè pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getIncipitText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Incipit di %s %s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
        if (textService.isValid(ottenuto)) {
            queryService.writeTest(ottenuto);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(501)
    @DisplayName("501 - listaSottoPagine")
    void listaSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("501 - listaSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoArray = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();
        assertNotNull(previstoArray);
        istanza = appContext.getBean(Upload.class, nomeLista).type(type);
        assertNotNull(istanza);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);
        assertEquals(previstoArray, listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Recupera la lista da Lista, se non già esistente";
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
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(502)
    @DisplayName("502 - getNumBioSottopagina")
    void getNumBioSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("502 - getNumBioSottopagina"));
        System.out.println("Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico (letto dalla lista)");
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        int previstoTotalePagina;
        int totaleEffettivoParagrafiSenzaSottopagina = 0;
        int totaleEffettivoSottoPagine = 0;

        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista).type(type);
        assertNotNull(istanza);
        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);
        previstoTotalePagina = istanza.numBio();
        assertTrue(previstoTotalePagina > 0);

        if (listaStr.size() > 0) {
            message = String.format("Nella pagina [%s] di type%s[%s], ci sono [%d] sottopagine", nomeLista, FORWARD, type.name(), listaStr.size());
            System.out.println(message);
            for (String keySottopagina : listaStr) {
                previstoIntero = istanza.sottopagina(keySottopagina).numBio();
                ottenutoIntero = istanza.numBio(keySottopagina);
                assertEquals(previstoIntero, ottenutoIntero);
                if (ottenutoIntero > 0) {
                    totaleEffettivoSottoPagine += ottenutoIntero;
                    message = String.format("Le biografie di type%s[%s] per [%s/%s], sono [%d]", FORWARD, type.name(), nomeLista, keySottopagina, ottenutoIntero);
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
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(503)
    @DisplayName("503 - getHeaderTextSottopagina")
    void getHeaderTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("503 - getHeaderTextSottopagina"));
        System.out.println("Testo header della sottopagina con Avviso, Toc, Unconnected, Uneditable, Torna, TmplBio, Incipit");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getHeaderText();
                System.out.println(VUOTA);
                message = String.format("Header di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(504)
    @DisplayName("504 - getIncipitTextSottopagina")
    void getIncipitTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("504 - getIncipitTextSottopagina"));
        System.out.println("Testo incipit della sottopagina con <ref> per le note");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getIncipitText();
                System.out.println(VUOTA);
                message = String.format("Incipit di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(505)
    @DisplayName("505 - getBodyTextSottopagina")
    void getBodyTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("505 - getBodyTextSottopagina"));
        System.out.println("Testo body della sottopagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getBodyText();
                message = String.format("Body di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(506)
    @DisplayName("506 - getBottomTextSottopagina")
    void getBottomTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("506 - getBottomTextSottopagina"));
        System.out.println("Testo bottom della sottopagina con Correlate, InterProgetto, Portale, Categorie");
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getBottomText();
                System.out.println(VUOTA);
                message = String.format("Bottom di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(507)
    @DisplayName("507 - getUploadTextSottopagina")
    void getUploadTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("507 - getUploadTextSottopagina"));
        System.out.println("Testo completo della sottopagina, Header, Body e Bottom");
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getUploadText();
                System.out.println(VUOTA);
                message = String.format("UploadText di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(511)
    @DisplayName("511 - uploadTestSottopagina")
    void uploadTestSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("511 - uploadTestSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }
        if (false) {
            System.out.println("Sospesa");
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();
        assertNotNull(listaStr);

        if (listaStr.size() > 0) {
            for (String keySottopagina : listaStr.subList(0, Math.min(3, listaStr.size()))) {
                ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).test().uploadOnly();
                message = String.format("Upload pagina di test di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

//    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(512)
    @DisplayName("512 - uploadRealSottopagina")
    void uploadRealSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("512 - uploadRealSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        if (false) {
            System.out.println("Sospesa");
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).uploadOnly();
                message = String.format("Upload pagina REAL di %s [%s]", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }




        @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(601)
    @DisplayName("601 - listaSottoSottoPagine")
    void listaSottoSottoPagine(String nomeLista, TypeLista type) {
        System.out.println(("601 - listaSottoSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        previstoArray = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();
        assertNotNull(previstoArray);
        istanza = appContext.getBean(Upload.class, nomeLista).type(type);
        assertNotNull(istanza);
//        listaStr = istanza.listaSottoPagine();
        assertNotNull(listaStr);
        assertEquals(previstoArray, listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, type.name());
            System.out.println(message);
            message = "Recupera la lista da Lista, se non già esistente";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    @Test
    @Order(901)
    @DisplayName("901 - uploadTestAll")
    void uploadTestAll() {
        System.out.println(("901 - uploadTestAll"));
        System.out.println(VUOTA);

        sorgente = 2023 + VUOTA;
        type = TypeLista.annoMorte;
        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).test().type(type).uploadOnly();
        message = String.format("Upload reale only di '%s' per [%s]", type.getCategoria(), sorgente);
        System.out.println(message);

        System.out.println(VUOTA);
        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).test().type(type).uploadAll();
        message = String.format("Upload reale completo di '%s' per [%s]", type.getCategoria(), sorgente);
        System.out.println(message);
    }


    //@todo da utilizzare solo DOPO aver controllato che BioServer sia allineato
    //    @Test
    @Order(902)
    @DisplayName("902 - uploadRealAll")
    void uploadRealAll() {
        System.out.println(("902 - uploadRealAll"));
        System.out.println(VUOTA);

        sorgente = 2023 + VUOTA;
        type = TypeLista.annoMorte;
        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).uploadOnly();
        message = String.format("Upload reale only di '%s' per [%s]", type.getCategoria(), sorgente);
        System.out.println(message);

        System.out.println(VUOTA);
        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).uploadAll();
        message = String.format("Upload reale completo di '%s' per [%s]", type.getCategoria(), sorgente);
        System.out.println(message);
    }

}
