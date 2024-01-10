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

    protected static String PARAMETRO = "nomeLista";

    protected static String CHECK = "checkValidita()";

    protected static String FUNZIONE = "isExistByKey";


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
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
        this.fixCheckIniziale();
    }

    @Test
    @Order(5)
    @DisplayName("5 - senzaParametroNelCostruttore")
    void senzaParametroNelCostruttore() {
        //--prova a costruire un'istanza SENZA parametri e controlla che vada in errore se è obbligatorio avere un parametro
        super.fixSenzaParametroNelCostruttore();
    }

    @Test
    @Order(6)
    @DisplayName("6 - checkParametroNelCostruttore")
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
