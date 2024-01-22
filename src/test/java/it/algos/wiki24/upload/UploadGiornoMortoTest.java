package it.algos.wiki24.upload;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 06:32
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("UploadGiornoMortoTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UploadGiornoMortoTest extends UploadTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadGiornoMorto istanza;


    protected Stream<Arguments> getListeStream() {
        return GIORNO_MORTO();
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadGiornoMorto.class;
        super.setUpAll();
        super.currentModulo = giornoWikiModulo;
        super.currentType = TypeLista.giornoMorte;
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(101)
    @DisplayName("101 - Upload tramite QueryService (wikiTitle)")
    void uploadServiceTitle(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("101 - Legge tramite QueryService (wikiTitle)"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenutoBooleano = uploadService.giornoMortoTest(nomeLista);
        assertTrue(ottenutoBooleano);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(102)
    @DisplayName("102 - Upload tramite QueryService (giornoBean)")
    void uploadServiceGiorno(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("102 - Legge tramite QueryService (giornoBean)"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        giornoBean = giornoWikiModulo.findByKey(nomeLista);
        assertNotNull(giornoBean);
        ottenutoBooleano = uploadService.giornoMortoTest(giornoBean);
        assertTrue(ottenutoBooleano);
    }

}
