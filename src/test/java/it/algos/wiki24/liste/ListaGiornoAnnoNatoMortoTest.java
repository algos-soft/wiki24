package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
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
@DisplayName("Lista giorno/anno nato/morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiornoAnnoNatoMortoTest extends WikiTest {

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    private Lista istanza;

    //--nome giorno/anno
    //--typeLista per il test
    protected static Stream<Arguments> LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("20 marzo", TypeLista.giornoNascita),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte),
                Arguments.of("2024", TypeLista.annoMorte),
                Arguments.of("2023", TypeLista.annoMorte),
                Arguments.of("38 a.C.", TypeLista.annoNascita),
                Arguments.of("38 a.C.", TypeLista.annoMorte),
                Arguments.of("38 A.C.", TypeLista.annoNascita),
                Arguments.of("4 gennaio", TypeLista.annoNascita),
                Arguments.of("1985", TypeLista.nazionalitaSingolare),
                Arguments.of("1º gennaio", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.giornoNascita),
                Arguments.of("406 a.C.", TypeLista.annoMorte),
                Arguments.of("1567", TypeLista.annoNascita),
                Arguments.of("560", TypeLista.annoMorte)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = Lista.class;
        super.setUpAll();
        //        super.currentModulo = giornoModulo;
        //        super.currentType = TypeLista.giornoNascita;
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

    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        //        ottenutoIntero = ((Lista) appContext.getBean(clazz, nomeLista)).numBio();
        ottenutoIntero = appContext.getBean(Lista.class, nomeLista).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di type%s[%s] per %s [%s] sono [%s]", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista, ottenuto);
            System.out.println(message);
        }
        else {
            //            printMancanoBio("La listaBio", nomeLista, typeSuggerito);
        }
    }


    protected boolean fixGiornoAnno(final String nomeLista, final TypeLista typeSuggerito) {
        if (textService.isEmpty(nomeLista)) {
            message = String.format("Manca il nome di %s per un'istanza di type%s[%s]", typeSuggerito.getGiornoAnno(), FORWARD, typeSuggerito.name());
            System.out.println(message);
            return false;
        }

        currentType = typeSuggerito;

        currentModulo = switch (typeSuggerito) {
            case giornoNascita, giornoMorte -> giornoModulo;
            case annoNascita, annoMorte -> annoModulo;
            default -> null;
        };

        if (currentModulo.findByKey(nomeLista) == null) {
            message = String.format("%s [%s] indicato NON esiste per un'istanza di type%s[%s]", textService.primaMaiuscola(typeSuggerito.getGiornoAnno()), nomeLista, FORWARD, currentType.name());
            System.out.println(message);
            return false;
        }

        if (currentType != typeSuggerito) {
            message = String.format("Il type suggerito%s[%s] è incompatibile per un'istanza che prevede type%s[%s]", FORWARD, typeSuggerito, FORWARD, currentType);
            System.out.println(message);
            return false;
        }

        return true;
    }

}
