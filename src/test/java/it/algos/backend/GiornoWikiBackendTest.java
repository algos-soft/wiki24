package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Feb-2023
 * Time: 17:37
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("GiornoWiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoWikiBackendTest extends WikiBackendTest {

    private GiornoWikiBackend backend;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
//        assertNotNull(backend);
//        assertNotNull(giornoBackend);
        super.entityClazz = GiornoWiki.class;
        backend = giornoWikiBackend;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.setUpAll();
    }


//    /**
//     * Regola tutti riferimenti incrociati <br>
//     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
//     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
//     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
//     */
//    protected void fixRiferimentiIncrociati() {
//        super.fixRiferimentiIncrociati();
//
//        backend.giornoBackend = giornoBackend;
//        backend.giornoBackend.mongoService = mongoService;
//        backend.giornoBackend.annotationService = annotationService;
//        backend.giornoBackend.textService = textService;
//        backend.wikiUtility = wikiUtility;
//        backend.wikiUtility.textService = textService;
//        backend.wikiUtility.regexService = regexService;
//        backend.wikiUtility.queryService = queryService;
//
//        backend.meseBackend = meseBackend;
//        backend.meseBackend.mongoService = mongoService;
//    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(40)
    @DisplayName("40 - toString")
    protected void toStringTest() {
        System.out.println("40 - toString");
        System.out.println(VUOTA);
        Giorno giornoBase;

        if (annotationService.usaKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        }
        else {
            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            sorgente = "4 marzo";
            giornoBase = giornoBackend.findByKey(sorgente);
            try {
                entityBean = backend.newEntity(giornoBase);
            } catch (Exception unErrore) {
                message = String.format("Non sono riuscito a creare una entityBean della classe [%s] col metodo newEntity() ad un solo parametro", clazzName);
                System.out.println(message);
                message = String.format("Probabilmente il valore [%s] usato per il metodo newEntity() non è adeguato", giornoBase);
                System.out.println(message);
                return;
            }
            assertNotNull(entityBean);
            ottenuto = entityBean.toString();
            if (textService.isEmpty(ottenuto)) {
                message = String.format("Non esiste il valore toString() della entity appena creata di classe [%s]", clazzName);
                System.out.println(message);
                message = String.format("Devi creare/modificare il metodo [%s].toString()", clazzName);
                System.out.println(message);
            }
            assertTrue(textService.isValid(ottenuto));
            System.out.println(ottenuto);
            return;
        }
        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro solo o senza", METHOD_NAME_NEW_ENTITY, backendName);
        System.out.println(message);
        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Aggiungendo un metodo '%s' senza parametri oppure con un parametro", METHOD_NAME_NEW_ENTITY);
        System.out.println(message);
    }

    @Test
    @Order(42)
    @DisplayName("42 - CRUD operations")
    protected void crud() {
        System.out.println("42 - CRUD operations");
        System.out.println(VUOTA);

        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro di tipo [Giorno]", METHOD_NAME_NEW_ENTITY, backendName);

    }

//    @Test
    @Order(61)
    @DisplayName("61 - elabora")
    void elabora() {
        System.out.println("61 - elabora");
        System.out.println(VUOTA);

        backend.elabora();

    }

    @Override
    protected Object getParamEsistente() {
        sorgente = "3 ottobre";
        previsto = "3ottobre";
        previsto2 = sorgente;

        return giornoBackend.findByKey(sorgente);
    }


}
