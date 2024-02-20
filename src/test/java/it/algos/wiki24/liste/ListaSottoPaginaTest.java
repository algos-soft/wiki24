package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Feb-2024
 * Time: 06:04
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Liste secondo livello")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaSottoPaginaTest extends WikiStreamTest {



    @Inject
    QueryService queryService;

    public static final boolean ESEGUE_SOLO_BODY = false;

    /**
     * Classe principale di riferimento <br>
     */
    private ListaSottoPagina istanza;

    private List<WrapDidascalia> listaWrapDidascalie;

    private Map<String, List<WrapDidascalia>> mappaWrapDidascalie;

    private Map<String, List<WrapDidascalia>> mappaSottoPagine;

    private Map<String, Integer> mappaDimensioni;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaSottoPagina.class;
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
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_LIVELLO_PAGINA")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista typeLista) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (byPassaErrori && !fixListe(nomeLista, typeLista)) {
            System.out.println(VUOTA);
            System.out.println("numBio ERRATA - mancano parametri validi");
            return;
        }

        if (ESEGUE_SOLO_BODY) {
            return;
        }

//        istanza = appContext.getBean(ListaPagina.class, nomeLista, typeLista);
//        assertNotNull(istanza);
//        ottenutoIntero = istanza.getNumBio();

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