package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.wiki24.backend.packages.anno.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
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
        this.backend = super.annoWikiBackend;
        super.entityClazz = AnnoWiki.class;
        super.typeBackend = TypeBackend.giorno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        sorgente = "3472";
        ottenutoBooleano = super.isExistById(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "986a.c.";
        ottenutoBooleano = super.isExistById(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);
        System.out.println("Anno ricavato dalla keyProperty");
        System.out.println(VUOTA);

        //--giorno
        //--esistente
        System.out.println(VUOTA);
        ANNI().forEach(this::isExistKeyBase);
    }

    //--giorno
    //--esistente
    void isExistKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = backend.isExistByKey(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        if (ottenutoBooleano) {
            System.out.println(String.format("L'anno [%s] esiste", sorgente));
        }
        else {
            System.out.println(String.format("L'anno [%s] non esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 4870;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgenteIntero = 600;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("24 - isExistByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgenteIntero = 27;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 8527;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 23300;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

        sorgente = "3472";
        entityBean = super.findById(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "986a.c.";
        entityBean = super.findById(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        sorgente = "986a.C.";
        entityBean = super.findByKey(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "986 a.C.";
        entityBean = super.findByKey(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 4870;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgenteIntero = 600;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByProperty")
    protected void findByProperty() {
        System.out.println("34 - findByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgenteIntero = 27;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 8527;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 23300;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNotNull(entityBean);
        System.out.println(VUOTA);
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity")
    protected void newEntity() {
        System.out.println("42 - newEntity");
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
        int ordine = annoSempliceVaad24.ordine * 100;
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
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }

    @Test
    @Order(45)
    @DisplayName("45 - toString")
    protected void toStringTest() {
    }


    @Test
    @Order(54)
    @DisplayName("54 - findAllBySecolo (entity)")
    void findAllBySecolo() {
        System.out.println("54 - findAllBySecolo (entity)");

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaBeans = backend.findAllBySecolo(sorgente);
            assertNotNull(listaBeans);
            message = String.format("Nel secolo %s ci sono %s anni", sorgente, textService.format(listaBeans.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printBackend(listaBeans);
        }
    }

    @Test
    @Order(65)
    @DisplayName("65 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("65 - findAllForNome (String)");
        System.out.println(VUOTA);

        listaStr = backend.findAllForNome();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s':", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        printSubLista(listaStr);
    }


    @Test
    @Order(66)
    @DisplayName("66 - findAllForNomeBySecolo (String)")
    protected void findAllForNomeByMese() {
        System.out.println("66 - findAllForNomeByMese (String)");
        int num = 3;

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaStr = backend.findAllForNomeBySecolo(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel secolo di %s ci sono %s anni. Mostro solo i primi %s", sorgente, textService.format(listaStr.size()), num);
            System.out.println(VUOTA);
            System.out.println(message);
            if (num > 0) {
                print(listaStr.subList(0, num));
            }
        }
    }


    @Test
    @Order(67)
    @DisplayName("67 - findAllPagine (String)")
    protected void findAllPagine() {
        System.out.println("67 - findAllPagine (String)");
        int num = 20;

        listaStr = backend.findAllPagine();
        assertNotNull(listaStr);
        message = String.format("Ci sono %s pagine. Mostro solo le prime %d", textService.format(listaStr.size()), num);
        System.out.println(VUOTA);
        System.out.println(message);
        if (num > 0) {
            print(listaStr.subList(0, num));
        }
    }


    @Test
    @Order(68)
    @DisplayName("68 - findAllPagineReverseOrder (String)")
    protected void findAllPagineReverseOrder() {
        System.out.println("68 - findAllPagineReverseOrder (String)");
        int num = 20;

        listaStr = backend.findAllPagineReverseOrder();
        assertNotNull(listaStr);
        message = String.format("Ci sono %s pagine. Mostro solo le prime %d", textService.format(listaStr.size()), num);
        System.out.println(VUOTA);
        System.out.println(message);
        if (num > 0) {
            print(listaStr.subList(0, num));
        }
    }

    protected void printTestaAnno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
        System.out.print(SEP);
        System.out.print("secolo");
        System.out.print(SEP);
        System.out.print("bioNati");
        System.out.print(SEP);
        System.out.print("bioMorti");
        System.out.print(SEP);
        System.out.print("pageNati");
        System.out.print(SEP);
        System.out.print("pageMorti");
        System.out.print(SEP);
        System.out.print("esistePaginaNati");
        System.out.print(SEP);
        System.out.print("esistePaginaMorti");
        System.out.print(SEP);
        System.out.print("natiOk");
        System.out.print(SEP);
        System.out.print("mortiOk");
        System.out.print(SEP);
        System.out.print("ordineSecolo");
        System.out.println(SPAZIO);
    }

    protected void printAnno(Object obj) {
        if (obj instanceof Anno anno) {
            super.printAnno(obj);
            return;
        }
        if (obj instanceof AnnoWiki anno) {
            System.out.print(anno.nome);
            System.out.print(SEP);
            System.out.print(anno.secolo);
            System.out.print(SEP);
            System.out.print(anno.bioNati);
            System.out.print(SEP);
            System.out.print(anno.bioMorti);
            System.out.print(SEP);
            System.out.print(anno.pageNati);
            System.out.print(SEP);
            System.out.print(anno.pageMorti);
            System.out.print(SEP);
            System.out.print(anno.esistePaginaNati);
            System.out.print(SEP);
            System.out.print(anno.esistePaginaMorti);
            System.out.print(SEP);
            System.out.print(anno.natiOk);
            System.out.print(SEP);
            System.out.print(anno.mortiOk);
            System.out.print(SEP);
            System.out.print(anno.ordineSecolo);
            System.out.println(SPAZIO);
        }
    }

}
