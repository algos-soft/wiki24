package it.algos.service;

import com.mongodb.client.*;
import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import org.bson.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.mongodb.core.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 17-Jul-2023
 * Time: 19:46
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@DisplayName("Didascalia Service")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PippozTest extends AlgosTest {

    private Bio bioBean;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    private DidascaliaService service;

    @InjectMocks
    private BioBackend bioBackend;

    @InjectMocks
    private TextService textService;

    @InjectMocks
    private FileService fileService;
    @InjectMocks
    private WikiUtility wikiUtility;
    @InjectMocks
    private PreferenceService preferenceService;
    @InjectMocks
    public LogService logService;

    @InjectMocks
    private MongoService mongoService;

    @Mock
    private MongoOperations mongoOp;



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected  void setUpAll() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(service);
        MockitoAnnotations.openMocks(bioBackend);
        MockitoAnnotations.openMocks(mongoService);
        MockitoAnnotations.openMocks(mongoOp);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(fileService);
        MockitoAnnotations.openMocks(wikiUtility);
        MockitoAnnotations.openMocks(preferenceService);
        MockitoAnnotations.openMocks(logService);

        service.textService = textService;
        service.wikiUtility = wikiUtility;
        fileService.textService = textService;
        mongoService.textService = textService;
        mongoService.fileService = fileService;
        wikiUtility.textService = textService;
        wikiUtility.logService = logService;
        logService.textService = textService;

        for (AIGenPref pref : WPref.values()) {
            pref.setPreferenceService(preferenceService);
        }

        bioBean = creaBio();
        assertNotNull(bioBean);
    }



    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);


        System.out.println(VUOTA);
        System.out.println("L'istanza è stata costruita SENZA usare SpringBoot.");
        System.out.println("NON passa da @PostConstruct().");
        System.out.println("@Autowired NON funziona.");
    }

    @Test
    @Order(3)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase3() {
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);


        System.out.println(VUOTA);
        System.out.println("L'istanza è stata costruita SENZA usare SpringBoot.");
        System.out.println("NON passa da @PostConstruct().");
        System.out.println("@Autowired NON funziona.");
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);
    }




    private Bio creaBio() {
        Bio beanBio = null;
        MongoCollection collection;

        FindIterable<Document> documents;
        MongoDatabase client = mongoService.getDB("wiki24");
        collection = client.getCollection("bio");

        documents = collection.find();
        documents.limit(1);
        for (var singolo : documents) {
            beanBio = bioBackend.newEntity(singolo);
        }

        return beanBio;
    }

}