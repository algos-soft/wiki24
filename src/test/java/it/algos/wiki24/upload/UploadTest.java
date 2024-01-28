package it.algos.wiki24.upload;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
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
@DisplayName("Upload giorno/anno nato/morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadTest extends WikiStreamTest {

    private Upload istanza;

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
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
        currentModulo = null;

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
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista type) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
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
            printMancanoBio("La listaBio", nomeLista, type);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(201)
    @DisplayName("201 - getHeaderText")
    void getHeaderText(String nomeLista, TypeLista type) {
        System.out.println(("201 - getHeaderText"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getHeaderText();
        message = String.format("Header di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(301)
    @DisplayName("301 - getBodyText")
    void getBodyText(String nomeLista, TypeLista type) {
        System.out.println(("301 - getBodyText"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getBodyText();
        message = String.format("Body di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(401)
    @DisplayName("401 - getBottomText")
    void getBottomText(String nomeLista, TypeLista type) {
        System.out.println(("401 - getBottomText"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getBottomText();
        message = String.format("Bottom di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(501)
    @DisplayName("501 - getUploadText")
    void getUploadText(String nomeLista, TypeLista type) {
        System.out.println(("501 - getUploadText"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Upload.class, nomeLista).type(type).getUploadText();
        message = String.format("UploadText di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    //        @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(601)
    @DisplayName("601 - uploadTest")
    void uploadTest(String nomeLista, TypeLista type) {
        System.out.println(("601 - uploadTest"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).test().upload();
        assertTrue(ottenutoRisultato.isValido());
        message = String.format("Upload pagina di test di %s%s", type.getCategoria(), nomeLista);
        System.out.println(message);
    }

    //    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(701)
    @DisplayName("701 - headerTextSottopagina")
    void headerTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("701 - headerTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String key : listaStr) {
                sorgente = nomeLista + SLASH + key;
                ottenuto = appContext.getBean(Upload.class, sorgente).type(type).sottopagina().getHeaderText();
                System.out.println(VUOTA);
                message = String.format("Header di %s%s", type.getCategoria(), sorgente);
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
    @Order(702)
    @DisplayName("702 - bodyTextSottopagina")
    void bodyTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("702 - bodyTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String key : listaStr) {
                sorgente = nomeLista + SLASH + key;
                ottenuto = appContext.getBean(Upload.class, sorgente).type(type).sottopagina().getBodyText();
                System.out.println(VUOTA);
                message = String.format("Body di %s%s", type.getCategoria(), sorgente);
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
    @Order(703)
    @DisplayName("703 - bottomTextSottopagina")
    void bottomTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("703 - bottomTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String key : listaStr) {
                sorgente = nomeLista + SLASH + key;
                ottenuto = appContext.getBean(Upload.class, sorgente).type(type).sottopagina().getBottomText();
                System.out.println(VUOTA);
                message = String.format("Bottom di %s%s", type.getCategoria(), sorgente);
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
    @DisplayName("704 - uploadTextSottopagina")
    void uploadTextSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("704 - uploadTextSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String key : listaStr) {
                sorgente = nomeLista + SLASH + key;
                ottenuto = appContext.getBean(Upload.class, sorgente).type(type).sottopagina().getUploadText();
                System.out.println(VUOTA);
                message = String.format("UploadText di %s%s", type.getCategoria(), sorgente);
                System.out.println(message);
                System.out.println(ottenuto.trim());
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(801)
    @DisplayName("801 - uploadTestSottopagina")
    void uploadTestSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("801 - uploadTestSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Upload.class, nomeLista).type(type).listaSottopagine();
        if (listaStr != null && listaStr.size() > 0) {
            for (String key : listaStr) {
                sorgente = nomeLista + SLASH + key;
                ottenutoRisultato = appContext.getBean(Upload.class, sorgente).type(type).test().sottopagina().upload();
                message = String.format("Upload pagina di test di %s%s", type.getCategoria(), sorgente);
                System.out.println(message);
                printRisultato(ottenutoRisultato);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //@todo da utilizzare solo DOPO aver controllato che BioServer sia allineato
    //    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(801)
    @DisplayName("801 - uploadReal")
    void uploadReal(String nomeLista, TypeLista type) {
        System.out.println(("801 - uploadReal"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenutoRisultato = appContext.getBean(Upload.class, nomeLista).type(type).upload();
        message = String.format("Upload reale di '%s' per [%s]", type.getCategoria(), nomeLista);
        System.out.println(message);
    }

}
