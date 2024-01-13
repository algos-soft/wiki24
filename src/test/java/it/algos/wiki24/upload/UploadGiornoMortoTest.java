package it.algos.wiki24.upload;

import it.algos.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
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
        super.currentModulo = giornoModulo;
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

}
