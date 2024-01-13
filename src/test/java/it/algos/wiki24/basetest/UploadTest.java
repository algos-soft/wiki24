package it.algos.wiki24.basetest;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.upload.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Jan-2024
 * Time: 09:38
 */
public abstract class UploadTest extends WikiTest {

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;


    //--nome giorno
    //--typeCrono per il test
    protected static Stream<Arguments> GIORNO_NATO() {
        return Stream.of(
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("20 marzo", TypeLista.giornoNascita)
        );
    }

    //--nome giorno
    //--typeCrono per il test
    protected static Stream<Arguments> GIORNO_MORTO() {
        return Stream.of(
                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                Arguments.of("21 febbraio", TypeLista.giornoMorte)
        );
    }


    //--nome anno
    //--typeCrono per il test
    protected static Stream<Arguments> ANNO_NATO() {
        return Stream.of(
                Arguments.of("38 a.C.", TypeLista.annoNascita),
                Arguments.of("1467", TypeLista.annoNascita)
        );
    }


    //--nome anno
    //--typeCrono per il test
    protected static Stream<Arguments> ANNO_MORTO() {
        return Stream.of(
                //                Arguments.of("2002", TypeLista.annoMorte),
                Arguments.of("406 a.C.", TypeLista.annoMorte),
                Arguments.of("560", TypeLista.annoMorte)
        );
    }


    //--nome giorno/anno
    //--typeCrono per il test
    protected Stream<Arguments> getListeStream() {
        return null;
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        //        super.usaCollectionName = true;
        //        super.usaCurrentModulo = true;
        //        super.usaTypeLista = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
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
        super.fixCheckParametroNelCostruttore(PARAMETRO, "...nonEsiste...", CHECK, FUNZIONE);
    }


    protected void fixBeanStandard(final String valore) {
        //--7 - Istanza della classe [%s] costruita col solo parametro e SENZA altre regolazioni", clazzName
        //--costruisce un'istanza col parametro 'valore'
        super.fixBeanStandard(nomeParametro, valore, metodiEseguibili, metodiDaRegolare, metodiBuilderPattern);
        //        this.debug(valore, "forse", "pippoz", true, false);
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(10)
    @DisplayName("10 - getHeaderText")
    void getHeaderText(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("10 - getHeaderText"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Upload) appContext.getBean(clazz, nomeLista)).getHeaderText();
        message = String.format("Header di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(20)
    @DisplayName("20 - getBodyText")
    void getBodyText(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("20 - getBodyText"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Upload) appContext.getBean(clazz, nomeLista)).getBodyText();
        message = String.format("Body di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(30)
    @DisplayName("30 - getBottomText")
    void getBottomText(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("30 - getBottomText"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Upload) appContext.getBean(clazz, nomeLista)).getBottomText();
        message = String.format("Bottom di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(40)
    @DisplayName("40 - getUploadText")
    void getUploadText(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("40 - getUploadText"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Upload) appContext.getBean(clazz, nomeLista)).getUploadText();
        message = String.format("UploadText di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(50)
    @DisplayName("50 - uploadTest")
    void uploadTest(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("50 - uploadTest"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenutoRisultato = ((Upload) appContext.getBean(clazz, nomeLista)).test().upload();
        message = String.format("Upload pagina di test di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(60)
    @DisplayName("60 - uploadReal")
    void uploadReal(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("60 - uploadReal"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenutoRisultato = ((Upload) appContext.getBean(clazz, nomeLista)).upload();
        message = String.format("Upload reale di '%s' per [%s]", typeSuggerito.getCategoria(), nomeLista);
        System.out.println(message);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "getListeStream()")
    //    @Order(101)
    //    @DisplayName("101 - listaBio")
    //    void listaBio(String nomeLista, TypeLista typeSuggerito) {
    //        System.out.println(("101 - listaBio"));
    //        System.out.println(VUOTA);
    //        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
    //            return;
    //        }
    //
    //        Object alfa=  ((Upload) appContext.getBean(clazz, nomeLista));
    //
    //        if (textService.isEmpty(nomeLista)) {
    //            assertNull(listaBio);
    //            return;
    //        }
    //        assertNotNull(listaBio);
    //        if (listaBio.size() > 0) {
    //            message = String.format("Lista delle [%d] biografie di type%s[%s] per %s [%s]", listaBio.size(), FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    ////            printBioLista(listaBio);
    //        }
    //        else {
    ////            printMancanoBio("La listaBio", nomeLista, typeSuggerito);
    //        }
    //    }

    protected void fixCheckIniziale() {
        System.out.println("0 - Check iniziale dei parametri necessari per il test");

        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe [%s] nel metodo setUpAll() e prima di invocare super.setUpAll() ", clazzTestName));

        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "clazz", clazzTestName);
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }
        message = String.format("Il flag '%s' è previsto e regolato nel metodo setUpAll() della classe [%s]", "clazz", clazzName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        if (textService.isEmpty(clazzName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "clazzName", clazzTestName);
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }
        message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() della classe [%s]", "clazzName", clazzTestName, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe [%s] nel metodo setUpAll() e dopo aver invocato super.setUpAll() ", clazzTestName));

        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto", ammessoCostruttoreVuoto, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "istanzaValidaSubitoDopoCostruttore", istanzaValidaSubitoDopoCostruttore, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
    }

}
