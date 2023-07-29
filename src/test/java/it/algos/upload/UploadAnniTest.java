package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.upload.liste.*;
import org.junit.jupiter.api.*;

import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 24-Apr-2023
 * Time: 11:35
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("uploadcrono")
@DisplayName("Anni upload")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadAnniTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadAnni istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = UploadAnni.class;
        super.backendClazzName = AnnoWikiBackend.class.getSimpleName();
        super.collectionName = "annoWiki";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
        super.istanzaValidaSubitoDopoCostruttore = false;
        super.metodiDaRegolare += ", nascita(), morte()";
        super.metodiBuilderPattern += ", nascita(), morte()";
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