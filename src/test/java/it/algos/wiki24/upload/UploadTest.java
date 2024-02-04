package it.algos.wiki24.upload;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

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

    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista type) {
        System.out.println(("101 - numBio"));
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

    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(201)
    @DisplayName("201 - getHeaderText")
    void getHeaderText(String nomeLista, TypeLista type) {
        System.out.println(("201 - getHeaderText"));
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


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(301)
    @DisplayName("301 - getBodyText")
    void getBodyText(String nomeLista, TypeLista type) {
        System.out.println(("301 - getBodyText"));
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

        message = String.format("Body di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(401)
    @DisplayName("401 - getBottomText")
    void getBottomText(String nomeLista, TypeLista type) {
        System.out.println(("401 - getBottomText"));
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

        message = String.format("Bottom di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(501)
    @DisplayName("501 - getUploadText")
    void getUploadText(String nomeLista, TypeLista type) {
        System.out.println(("501 - getUploadText"));
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

        message = String.format("UploadText di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(601)
    @DisplayName("601 - uploadTestPaginaPrincipaleOnly")
    void uploadTestPaginaPrincipaleOnly(String nomeLista, TypeLista type) {
        System.out.println(("601 - uploadTestPaginaPrincipaleOnly"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).test().uploadOnly();

        assertTrue(ottenutoRisultato.isValido());
        message = String.format("Upload pagina (principale only) di test di [%s%s]", type.getCategoria(), nomeLista);
        System.out.println(message);
        printRisultato(ottenutoRisultato);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "LISTA_TEST")
    //    @Order(602)
    //    @DisplayName("602 - upload voce e sottopagine")
    //    void uploadAll(String nomeLista, TypeLista type) {
    //        System.out.println(("602 - upload voce e sottopagine"));
    //        System.out.println(VUOTA);
    //        if (!fixListe(nomeLista, type)) {
    //            return;
    //        }
    //
    //        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).test().uploadAll();
    //        assertTrue(ottenutoRisultato.isValido());
    //        message = String.format("Upload pagina di test di %s%s", type.getCategoria(), nomeLista);
    //        System.out.println(message);
    //    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(701)
    @DisplayName("701 - numBioSottopagina")
    void numBioSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("701 - numBioSottopagina"));
        System.out.println(VUOTA);
        String keySottopaginaErrata;
        int previstoTotalePagina;
        int totaleEffettivoParagrafiSenzaSottopagina = 0;
        int totaleEffettivoSottoPagine = 0;

        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
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
    @Order(702)
    @DisplayName("702 - headerTextSottopagina")
    void headerTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("702 - headerTextSottopagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getHeaderText();
                System.out.println(VUOTA);
                message = String.format("Header di %s%s", type.getCategoria(), nomeLista + SLASH + keySottopagina);
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
    @MethodSource(value = "LISTA")
    @Order(703)
    @DisplayName("703 - bodyTextSottopagina")
    void bodyTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("703 - bodyTextSottopagina"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }

        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
//                sorgente = nomeLista + SLASH + keySottopagina;
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getBodyText();
                System.out.println(VUOTA);
                message = String.format("Body di %s%s", type.getCategoria(), nomeLista + SLASH + keySottopagina);
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
    @MethodSource(value = "LISTA_TEST")
    @Order(704)
    @DisplayName("704 - bottomTextSottopagina")
    void bottomTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("704 - bottomTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
//                sorgente = nomeLista + SLASH + keySottopagina;
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getBottomText();
                System.out.println(VUOTA);
                message = String.format("Bottom di %s%s", type.getCategoria(), nomeLista + SLASH + keySottopagina);
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
    @MethodSource(value = "LISTA_TEST")
    @Order(705)
    @DisplayName("705 - uploadTextSottopagina")
    void uploadTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("705 - uploadTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
//                sorgente = nomeLista + SLASH + keySottopagina;
                ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).getUploadText();
                System.out.println(VUOTA);
                message = String.format("UploadText di %s%s", type.getCategoria(), nomeLista + SLASH + keySottopagina);
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
    @MethodSource(value = "LISTA_TEST")
    @Order(801)
    @DisplayName("801 - uploadTestSottopagina")
    void uploadTestSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("801 - uploadTestSottopagina"));
        System.out.println(VUOTA);
        if (!fixListe(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
//                sorgente = nomeLista + SLASH + keySottopagina;
                ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).test().sottopagina(keySottopagina).uploadOnly();
                message = String.format("Upload pagina di test di %s%s", type.getCategoria(), nomeLista + SLASH + keySottopagina);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    //    @Test
    @Order(802)
    @DisplayName("802 - uploadRealSottopagina")
    void uploadRealSottopagina() {
        System.out.println(("802 - uploadRealSottopagina"));
        System.out.println(VUOTA);

        sorgente = 2023 + VUOTA;
        type = TypeLista.annoMorte;
        listaStr = appContext.getBean(Upload.class, sorgente).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            sorgente2 = sorgente + SLASH + listaStr.get(0);
            ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).sottopagina(sorgente2).uploadOnly();
            message = String.format("Upload pagina REAL di %s%s", type.getCategoria(), sorgente2);
            System.out.println(message);
            printRisultato(ottenutoRisultato);
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
