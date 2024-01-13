package it.algos.wiki24.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 07:31
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("upload")
@DisplayName("UploadAnnoMortoTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadAnnoMortoTest extends UploadTest {

    /**
     * Classe principale di riferimento <br>
     */
    private UploadAnnoMorto istanza;


    protected Stream<Arguments> getListeStream() {
        return ANNO_MORTO();
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadAnnoMorto.class;
        super.setUpAll();
        super.currentModulo = annoModulo;
        super.currentType = TypeLista.annoMorte;
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
