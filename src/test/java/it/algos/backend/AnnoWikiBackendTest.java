package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.wiki24.backend.packages.anno.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import java.util.*;


/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 07-Mar-2023
 * Time: 16:47
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("anno")
@Tag("backend")
@DisplayName("AnnoWiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoWikiBackendTest extends WikiBackendTest {

    private AnnoWikiBackend backend;

    private List<AnnoWiki> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = AnnoWiki.class;
        backend = annoWikiBackend;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.setUpAll();
        super.typeBackend = TypeBackend.anno;
    }

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
            sorgente = "1845";
            try {
                entityBean = backend.newEntity(sorgente);
            } catch (Exception unErrore) {
                message = String.format("Non sono riuscito a creare una entityBean della classe [%s] col metodo newEntity() ad un solo parametro", clazzName);
                System.out.println(message);
                message = String.format("Probabilmente il valore [%s] usato per il metodo newEntity() non è adeguato", sorgente);
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
    @Order(41)
    @DisplayName("41 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("41 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);

        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro di tipo [Anno]", METHOD_NAME_NEW_ENTITY, backendName);

    }

    @Test
    @Order(42)
    @DisplayName("42 - CRUD operations")
    protected void crud() {
        System.out.println("42 - CRUD operations");
        System.out.println(VUOTA);

        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro di tipo [Anno]", METHOD_NAME_NEW_ENTITY, backendName);

    }

    @Test
    @Order(43)
    @DisplayName("43 - newEntityConParametri")
    protected void newEntityConParametri() {
        System.out.println("43 - newEntityConParametri");
        System.out.println(VUOTA);
        Anno annoSempliceVaad24;
        AnnoWiki anno;
        Secolo secolo;

        sorgente = "1875";
        previsto = "1875";
        annoSempliceVaad24 = annoBackend.findByKey(sorgente);
        assertNotNull(annoSempliceVaad24);
        secolo = annoSempliceVaad24.getSecolo();
        assertNotNull(secolo);
        int ordine = annoSempliceVaad24.ordine*100;
        int bioNati = 0;
        int bioMorti = 0;
        String pageNati = wikiUtility.wikiTitleNatiAnno(sorgente);
        String pageMorti = wikiUtility.wikiTitleMortiAnno(sorgente);
        int ordineSecolo = secolo.ordine;

        entityBean = backend.newEntity(sorgente);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof AnnoWiki);
        anno = (AnnoWiki) entityBean;
        assertEquals(previsto, anno.id);
        assertEquals(ordine, anno.ordine);
        assertEquals(sorgente, anno.nome);
        assertEquals(bioNati, anno.bioNati);
        assertEquals(bioMorti, anno.bioMorti);
        assertEquals(pageNati, anno.pageNati);
        assertEquals(pageMorti, anno.pageMorti);
        assertFalse(anno.esistePaginaNati);
        assertFalse(anno.esistePaginaMorti);
        assertFalse(anno.natiOk);
        assertFalse(anno.mortiOk);
        assertEquals(ordineSecolo, anno.ordineSecolo);
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }

}
