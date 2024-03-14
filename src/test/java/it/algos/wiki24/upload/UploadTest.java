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

    public static final boolean ESEGUE_SOLO_UPLOD = false;

    private Lista istanzaLista;

    private Upload istanza;

    private TypeLista typeLista;

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
        typeLista = null;
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(101)
    @DisplayName("101 - getNumBio")
    void getNumBio(String nomeLista, TypeLista typeLista) {
        System.out.println(("101 - getNumBio"));
        System.out.println("Numero delle biografie (Bio) che hanno una valore valido per l'intera pagina (letto dalla Lista)");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            System.out.println(VUOTA);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenutoIntero = istanza.numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di type%s[%s] per %s [%s] sono [%s]", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, ottenuto);
            System.out.println(message);
        }
        else {
            if (ottenutoIntero == INT_ERROR) {
                message = String.format("Probabilmente manca il typeLista di [%s]", nomeLista);
                logger.info(new WrapLog().message(message));
                assertTrue(false);
            }
            else {
                printMancanoBio("La listaBio", nomeLista, typeLista);
            }
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(202)
    @DisplayName("202 - getHeaderText")
    void getHeaderText(String nomeLista, TypeLista typeLista) {
        System.out.println(("202 - getHeaderText"));
        System.out.println("Testo header della pagina con Avviso, Toc, Unconnected, Uneditable, Torna, TmplBio");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_UPLOD) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getHeaderText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Header di %s %s", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(203)
    @DisplayName("203 - getIncipitText")
    void getIncipitText(String nomeLista, TypeLista typeLista) {
        System.out.println(("203 - getIncipitText"));
        System.out.println("Testo incipit della pagina con Note a piè pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_UPLOD) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getIncipitText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }
        if (typeLista == TypeLista.attivitaPlurale || typeLista == TypeLista.nazionalitaPlurale|| typeLista == TypeLista.nomi) {
            assertTrue(textService.isValid(ottenuto));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
        }

        message = String.format("Incipit di %s %s", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(204)
    @DisplayName("204 - getBodyText")
    void getBodyText(String nomeLista, TypeLista typeLista) {
        System.out.println(("204 - getBodyText"));
        System.out.println("Testo body della pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_UPLOD) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
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

        message = String.format("Body di %s %s", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(205)
    @DisplayName("205 - getBottomText")
    void getBottomText(String nomeLista, TypeLista typeLista) {
        System.out.println(("205 - getBottomText"));
        System.out.println("Testo bottom della pagina con Correlate, InterProgetto, Portale, Categorie");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_UPLOD) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getBottomText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Bottom di %s %s", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(206)
    @DisplayName("206 - getUploadText")
    void getUploadText(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("206 - getUploadText"));
        System.out.println("Testo completo della pagina con Header, Incipit, Body e Bottom");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esistePagina) {
            message = String.format("Non sono previste pagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        ottenuto = istanza.getUploadText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("UploadText di %s %s", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(210)
    @DisplayName("210 - getIstanzaLista")
    void getIstanzaLista(String nomeLista, TypeLista typeLista) {
        System.out.println(("210 - getIstanzaLista"));
        System.out.println("Istanza della lista collegata");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (ESEGUE_SOLO_UPLOD) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (ottenuto.equals(STRING_ERROR)) {
            assertTrue(false);
            return;
        }

        message = String.format("Esiste l'istanza della lista per %s", nomeLista);
        System.out.println(message);
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(211)
    @DisplayName("211 - uploadTestPaginaPrincipaleOnly")
    void uploadTestPaginaPrincipaleOnly(String nomeLista, TypeLista typeLista) {
        System.out.println(("211 - uploadTestPaginaPrincipaleOnly"));
        message = String.format("Upload pagina (principale only) di test di [%s%s]", typeLista.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (false) {
            System.out.println("Sospesa");
            return;
        }
        if (typeLista == TypeLista.attivitaSingolare || typeLista == TypeLista.nazionalitaSingolare) {
            System.out.println("Disabilitata la possibilità (che esiste nel codice) di stampare un'attività/nazionalità singolare");
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        ottenutoRisultato = istanza.test().uploadOnly();
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
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

        //        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).uploadOnly();

        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(230)
    @DisplayName("230 - printIncipitText")
    void printIncipitText(String nomeLista, TypeLista type) {
        System.out.println(("230 - printIncipitText"));
        System.out.println("Stampa del testo incipit della pagina con Note a piè pagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        //        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getIncipitText();

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


    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(501)
    @DisplayName("501 - listaSottoPagine")
    void listaSottoPagine(String nomeLista, TypeLista typeLista) {
        System.out.println(("501 - listaSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, typeLista.name());
            System.out.println(message);
            message = "Recupera la lista da Lista, se non già esistente";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(502)
    @DisplayName("502 - getNumBioSottoPagina")
    void getNumBioSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("502 - getNumBioSottoPagina"));
        System.out.println("Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico (letto dalla lista)");
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        int previstoTotalePagina;
        int totaleEffettivoParagrafiSenzaSottopagina = 0;
        int totaleEffettivoSottoPagine = 0;

        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);
        previstoTotalePagina = istanza.numBio();
        assertTrue(previstoTotalePagina > 0);

        if (listaStr.size() > 0) {
            message = String.format("Nella pagina [%s] di type%s[%s], ci sono [%d] sottopagine", nomeLista, FORWARD, typeLista.name(), listaStr.size());
            System.out.println(message);
            for (String keySottopagina : listaStr) {
                previstoIntero = istanza.sottoPagina(keySottopagina).numBio();
                //                ottenutoIntero = istanza.numBio(keySottopagina);
                assertEquals(previstoIntero, ottenutoIntero);
                if (ottenutoIntero > 0) {
                    totaleEffettivoSottoPagine += ottenutoIntero;
                    message = String.format("Le biografie di type%s[%s] per [%s/%s], sono [%d]", FORWARD, typeLista.name(), nomeLista, keySottopagina, ottenutoIntero);
                    System.out.println(message);
                }
            }
            System.out.println(VUOTA);
            message = String.format("In totale nella lista [%s] ci sono [%d] biografie", nomeLista, totaleEffettivoSottoPagine);
            System.out.println(message);
            assertTrue(previstoTotalePagina >= totaleEffettivoSottoPagine);
            message = String.format("Che sono meno delle [%d] totali perché alcuni paragrafi NON hanno sottopagina", previstoTotalePagina);
            System.out.println(message);
            ottenutoArray = istanzaLista.getKeyParagrafi();
            for (String keyParagrafoSenzaSottopagina : ottenutoArray) {
                if (!listaStr.contains(keyParagrafoSenzaSottopagina)) {
                    //                    ottenutoIntero = istanzaLista.getNumBioSottoPagina(keyParagrafoSenzaSottopagina);
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
                //                ottenutoIntero = istanzaLista.getNumBioSottoPagina(keySottopaginaErrata);
                if (ottenutoIntero == INT_ERROR) {
                    message = String.format("Nella lista [%s] non esiste un paragrafo/sottopagina [%s]", nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertNotEquals(INT_ERROR, ottenutoIntero);
                }
                else {
                    message = String.format("Non ci sono biografie di tipo [%s] per il paragrafo/sottopagina [%s/%s]", typeLista.name(), nomeLista, keySottopaginaErrata);
                    System.out.println(message);
                    assertEquals(INT_ERROR, ottenutoIntero);
                }
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(503)
    @DisplayName("503 - getHeaderSottoPagina")
    void getHeaderSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("503 - getHeaderSottoPagina"));
        System.out.println("Testo header della sottopagina con Avviso, Toc, Unconnected, Uneditable, Torna, TmplBio, Incipit");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSotto) {
            message = String.format("Non sono previste sottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getHeaderText();
                System.out.println(VUOTA);
                message = String.format("Header di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(504)
    @DisplayName("504 - getIncipitTextSottoPagina")
    void getIncipitTextSottoPagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("504 - getIncipitTextSottoPagina"));
        System.out.println("Testo incipit della sottopagina con <ref> per le note");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getIncipitText();
                System.out.println(VUOTA);
                message = String.format("Incipit di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(505)
    @DisplayName("505 - getBodyTextSottopagina")
    void getBodyTextSottopagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("505 - getBodyTextSottopagina"));
        System.out.println("Testo body della sottopagina");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(ottenutoMappa);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottopagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String keySottopagina : listaStr) {
                ottenuto = istanza.getBodySottoPagina(keySottopagina);
                System.out.println(VUOTA);
                message = String.format("Body di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
                System.out.println(VUOTA);
                System.out.println(VUOTA);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(506)
    @DisplayName("506 - getBottomSottopagina")
    void getBottomSottopagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("506 - getBottomSottopagina"));
        System.out.println("Testo bottom della sottopagina con Correlate, InterProgetto, Portale, Categorie");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSotto) {
            message = String.format("Non sono previste sottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getBottomText();
                System.out.println(VUOTA);
                message = String.format("Bottom di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
                System.out.println(VUOTA);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(507)
    @DisplayName("507 - getUploadTextSottopagina")
    void getUploadTextSottopagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("507 - getUploadTextSottopagina"));
        System.out.println("Testo completo della sottopagina, Header, Body e Bottom");
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSotto) {
            message = String.format("Non sono previste sottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottopagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getUploadText();
                System.out.println(VUOTA);
                message = String.format("UploadText di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(511)
    @DisplayName("511 - uploadTestSottoPagina")
    void uploadTestSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("511 - uploadTestSottoPagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSotto) {
            message = String.format("Non sono previste sottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottopagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
                System.out.println(message);
                System.out.println(VUOTA);
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista).test();
                assertNotNull(istanza);
                ottenutoRisultato = istanza.uploadOnly();
                message = String.format("Upload pagina di test di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(512)
    @DisplayName("512 - uploadTestAll")
    void uploadTestAll(String nomeLista, TypeLista typeLista) {
        System.out.println(("512 - uploadTestAll"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, typeLista)) {
            return;
        }

        if (false) {
            System.out.println("Sospesa");
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista).test();
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoPagine();
        assertNotNull(listaStr);
        ottenutoRisultato = istanza.uploadOnly();

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottopagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String keySottopagina : listaStr) {
                ottenutoRisultato = appContext.getBean(Upload.class, nomeLista, typeLista, istanzaLista, keySottopagina).test().uploadOnly();
                message = String.format("Upload pagina di test di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "SOTTO_PAGINE")
    @Order(513)
    @DisplayName("513 - uploadRealSottopagina")
    void uploadRealSottopagina(String nomeLista, TypeLista typeLista) {
        System.out.println(("513 - uploadRealSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, typeLista)) {
            return;
        }

        if (false) {
            System.out.println("Sospesa");
            return;
        }

        //        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottoPagine();

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                //                ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).sottoPagina(keySottopagina).uploadOnly();
                message = String.format("Upload pagina REAL di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(601)
    @DisplayName("601 - listaSottoSottoPagine")
    void listaSottoSottoPagine(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("601 - listaSottoSottoPagine"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoSottoPagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr == null) {
            assertTrue(false);
            return;
        }
        if (esisteSottoSotto && listaStr.size() == 0) {
            message = String.format("Mancano le sottoSottoPagine (previste) per la lista [%s]", nomeLista);
            System.out.println(message);
            assertTrue(false);
            return;
        }

        if (listaStr.size() > 0) {
            message = String.format("Ci sono %s sottosottopagine nella lista [%s] di type [%s]", listaStr.size(), nomeLista, typeLista.name());
            System.out.println(message);
            message = "Recupera la lista da Lista, se non già esistente";
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottosottopagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(602)
    @DisplayName("602 - getNumBioSottoSottoPagina")
    void getNumBioSottoSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("602 - getNumBioSottoSottoPagina"));
        System.out.println("Numero delle biografie (Bio) che hanno una valore valido per la pagina (sottosottopagina) specifica (letto dalla lista)");
        System.out.println(VUOTA);

        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (listaStr.size() > 0) {
            message = String.format("Nella pagina [%s] di type%s[%s], ci sono [%d] sottoSottoPagine", nomeLista, FORWARD, typeLista.name(), listaStr.size());
            System.out.println(message);
            for (String keySottopagina : listaStr) {
                previstoIntero = istanzaLista.getNumBioSottoSottoPagina(keySottopagina);
                ottenutoIntero = istanza.getNumBioSottoSottoPagina(keySottopagina);
                assertEquals(previstoIntero, ottenutoIntero);
                if (ottenutoIntero > 0) {
                    message = String.format("Le biografie di type%s[%s] per [%s/%s], sono [%d]", FORWARD, typeLista.name(), nomeLista, keySottopagina, ottenutoIntero);
                    System.out.println(message);
                }
            }
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(603)
    @DisplayName("603 - getHeaderTextSottoSottoPagina")
    void getHeaderTextSottoSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("603 - getHeaderTextSottoSottoPagina"));
        System.out.println("Testo header della sottoSottoPagina con Avviso, Toc, Unconnected, Uneditable, Torna, TmplBio, Incipit");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getHeaderText();
                System.out.println(VUOTA);
                message = String.format("Header di %s [%s]", nomeLista, keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(604)
    @DisplayName("604 - getIncipitTextSottoSottoPagina")
    void getIncipitTextSottoSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("604 - getIncipitTextSottoSottoPagina"));
        System.out.println("Testo incipit della sottoSottoPagina con <ref> per le note");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getIncipitText();
                System.out.println(VUOTA);
                message = String.format("Incipit di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(605)
    @DisplayName("605 - getBottomTextSottoSottoPagina")
    void getBottomTextSottoSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("605 - getBottomTextSottoSottoPagina"));
        System.out.println("Testo bottom della sottopagina con Correlate, InterProgetto, Portale, Categorie");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getBottomText();
                System.out.println(VUOTA);
                message = String.format("Bottom di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(607)
    @DisplayName("607 - getUploadTextSottoSottoPagina")
    void getUploadTextSottoSottoPagina(String nomeLista, TypeLista typeLista, boolean esistePagina, boolean esisteSotto, boolean esisteSottoSotto) {
        System.out.println(("507 - getUploadTextSottoSottoPagina"));
        System.out.println("Testo completo della sottoSottoPagina, Header, Body e Bottom");
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            return;
        }
        if (!esisteSottoSotto) {
            message = String.format("Non sono previste sottoSottoPagine per la lista [%s]", nomeLista);
            System.out.println(message);
            return;
        }

        istanza = appContext.getBean(Upload.class, nomeLista, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);
        listaStr = istanza.getListaSottoSottoPagine();
        assertNotNull(listaStr);

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("La lista di type%s[%s] per %s [%s] ha %d chiavi (sottoSottoPagine)", FORWARD, typeLista.name(), typeLista.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            for (String keySottopagina : listaStr) {
                istanza = appContext.getBean(Upload.class, nomeLista, typeLista, keySottopagina, istanzaLista);
                assertNotNull(istanza);
                ottenuto = istanza.getUploadText();
                System.out.println(VUOTA);
                message = String.format("UploadText di %s [%s]", typeLista.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottoSottoPagine nella lista [%s] di type [%s]", nomeLista, typeLista.name());
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
        typeLista = TypeLista.annoMorte;
        //        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).test().type(type).uploadOnly();
        message = String.format("Upload reale only di '%s' per [%s]", typeLista.getCategoria(), sorgente);
        System.out.println(message);

        System.out.println(VUOTA);
        //        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).test().type(type).uploadAll();
        message = String.format("Upload reale completo di '%s' per [%s]", typeLista.getCategoria(), sorgente);
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
        typeLista = TypeLista.annoMorte;
        //        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).uploadOnly();
        message = String.format("Upload reale only di '%s' per [%s]", typeLista.getCategoria(), sorgente);
        System.out.println(message);

        System.out.println(VUOTA);
        //        ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).uploadAll();
        message = String.format("Upload reale completo di '%s' per [%s]", typeLista.getCategoria(), sorgente);
        System.out.println(message);
    }

    //    @Test
    @Order(1001)
    @DisplayName("1001 - writeCheck")
    void writeCheck() {
        System.out.println(("1001 - writeCheck"));
        System.out.println(("Controlla che NON esegua la registrazione se cambia SOLO la data"));
        System.out.println(VUOTA);

        sorgente = "399 a.C.";
        typeLista = TypeLista.annoMorte;

        istanza = appContext.getBean(Upload.class, sorgente, typeLista);
        ottenutoRisultato = istanza.test().uploadOnly();
        printRisultato(ottenutoRisultato);

        //        istanza = appContext.getBean(Upload.class, sorgente, typeLista);
        //        ottenutoRisultato = istanza.test().uploadOnly();
        //        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(2001)
    @DisplayName("2001 - testPrintSottoSottoPagina")
    void testPrintSottoSottoPagina() {
        System.out.println(("2001 - testPrintSottoSottoPagina"));
        System.out.println(VUOTA);

        sorgente = "brasiliani";
        typeLista = TypeLista.nazionalitaPlurale;

        istanza = appContext.getBean(Upload.class, sorgente, typeLista);
        assertNotNull(istanza);
        istanzaLista = istanza.getIstanzaLista();
        assertNotNull(istanzaLista);

        sorgente2 = "allenatori di calcio/D";
        istanza = appContext.getBean(Upload.class, sorgente, typeLista, sorgente2, istanzaLista);
        ottenutoRisultato = istanza.test().uploadOnly();
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

}
