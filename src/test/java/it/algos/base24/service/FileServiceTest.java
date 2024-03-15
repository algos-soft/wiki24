package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.service.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 20:02
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("File Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest extends ServiceTest {

    @Autowired
    private FileService service;


    @Autowired
    private TextService textService;

    //--indirizzo
    //--esiste
    //--testo
    private Stream<Arguments> files() {
        return Stream.of(
                Arguments.of(VUOTA, false, VUOTA),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/base24/config/vie", false, "nome\nvia,\nlargo,\ncorso,\npiazza,\nviale"),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/base24/config/vie.csv", true, "nome\nvia,\nlargo,\ncorso,\npiazza,\nviale"),
                Arguments.of("/Users/gac/Documents/ssnippets/avviso", false, VUOTA),
                Arguments.of("/Users/gac/Documents/snippets/avviso", true, "Notification.show(\"This is the caption\", \"This is the description\"")
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = FileService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(10)
    @DisplayName("10 - legge")
    void legge() {
        System.out.println(("10 - Legge il testo di un generico files"));
        System.out.println(VUOTA);

        //--indirizzo
        //--esiste
        //--testo
        files().forEach(this::fixLegge);
    }

    //--indirizzo
    //--esiste
    //--testo
    void fixLegge(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        int max = 40;

        ottenuto = service.legge(sorgente);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));

        inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : "";
        sorgente = textService.isValid(sorgente) ? sorgente : NULLO;
        inizio = textService.isValid(inizio) ? inizio : NULLO;
        message = String.format("[%s] %s%s%s", esistenza, sorgente, FORWARD, inizio);
        System.out.println(message);
    }



}
