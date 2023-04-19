package it.algos.backend;

import it.algos.*;
import static it.algos.backend.GiornoBackendTest.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Feb-2023
 * Time: 17:37
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("giorno")
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("GiornoWiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoWikiBackendTest extends WikiBackendTest {

    private GiornoWikiBackend backend;

    private List<GiornoWiki> listaBeans;


    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    public static Stream<Arguments> GIORNO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("23 febbraio", false, true, false),
                Arguments.of("43 marzo", false, false, false),
                Arguments.of("19 dicembra", false, false, false),
                Arguments.of("4gennaio", true, false, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("pageNati", "Nati il 2 termidoro", false),
                Arguments.of("pageNati", "Nati il 25 gennaio", true)
        );
    }



    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.giornoWikiBackend;
        super.entityClazz = GiornoWiki.class;
        super.typeBackend = TypeBackend.giorno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = GIORNO();
        super.streamProperty = PROPERTY();
        super.streamOrder = GiornoBackendTest.ORDINE();
    }

    @Test
    @Order(41)
    @DisplayName("41 - creaIfNotExist (non previsto per questa collection)")
    protected void creaIfNotExist() {
        System.out.println("41 - creaIfNotExist (non previsto per questa collection)");
        System.out.println(message);
        System.out.println(VUOTA);
    }


    @Test
    @Order(16)
    @DisplayName("16 - upload (non previsto per questa collection)")
    protected void upload() {
        System.out.println("16 - upload (non previsto per questa collection)");
    }

    @Test
    @Order(42)
    @DisplayName("42 - newEntity")
    protected void newEntity() {
        System.out.println("42 - newEntity");
        System.out.println(VUOTA);
        Giorno giornoSempliceVaad24;
        GiornoWiki giorno;

        sorgente = "14 gennaio";
        previsto = "14gennaio";
        giornoSempliceVaad24 = giornoBackend.findByKey(sorgente);
        assertNotNull(giornoSempliceVaad24);
        int ordine = giornoSempliceVaad24.ordine;
        int bioNati = 0;
        int bioMorti = 0;
        String pageNati = wikiUtility.wikiTitleNatiGiorno(sorgente);
        String pageMorti = wikiUtility.wikiTitleMortiGiorno(sorgente);

        entityBean = backend.newEntity(sorgente);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof GiornoWiki);
        giorno = (GiornoWiki) entityBean;
        assertEquals(previsto, giorno.id);
        assertEquals(ordine, giorno.ordine);
        assertEquals(sorgente, giorno.nome);
        assertEquals(bioNati, giorno.bioNati);
        assertEquals(bioMorti, giorno.bioMorti);
        assertEquals(pageNati, giorno.pageNati);
        assertEquals(pageMorti, giorno.pageMorti);
        assertFalse(giorno.esistePaginaNati);
        assertFalse(giorno.esistePaginaMorti);
        assertFalse(giorno.natiOk);
        assertFalse(giorno.mortiOk);
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
    @DisplayName("54 - findAllByMese (entityBeans)")
    protected void findAllByMese() {
        System.out.println("54 - findAllByMese (entity)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");

        for (Mese sorgente : meseBackend.findAllNoSort()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            System.out.println(VUOTA);
            message = String.format("Mese di %s", sorgente.nome);
            System.out.println(message);

            printBackend(listaBeans, 3);
        }
    }

    @Test
    @Order(65)
    @DisplayName("65 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("65 - findAllForNome (String)");
        System.out.println("Uguale a 61 - findAllForKeySortKey");
        System.out.println(VUOTA);
    }

    @Test
    @Order(66)
    @DisplayName("66 - findAllForNomeByMese (String)")
    protected void findAllForNomeByMese() {
        System.out.println("66 - findAllForNomeByMese (String)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");
        int num = 3;

        for (Mese sorgente : meseBackend.findAllSortCorrente()) {
            listaStr = backend.findAllForNomeByMese(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel mese di %s ci sono %s giorni. Mostro solo i primi %s", sorgente, textService.format(listaStr.size()), num);
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
        System.out.println("68 - findAllPagine (String)");
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
    @Order(75)
    @DisplayName("75 - findAllDistinctByPlurali (solo Attività e Nazionalità)")
    protected void findAllDistinctByPlurali() {
    }

    @Test
    @Order(91)
    @DisplayName("91 - riordinaModulo (solo Attività e Nazionalità)")
    protected void riordinaModulo() {
    }


    @Override
    protected Object getParamEsistente() {
        sorgente = "3 ottobre";
        previsto = "3ottobre";
        previsto2 = sorgente;

        return sorgente;
    }

    protected void printTestaGiorno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
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
        System.out.println(SPAZIO);
    }

    protected void printGiorno(Object obj) {
        if (obj instanceof Giorno giorno) {
            super.printGiorno(obj);
            return;
        }
        if (obj instanceof GiornoWiki giorno) {
            System.out.print(giorno.ordine);
            System.out.print(SEP);
            System.out.print(giorno.nome);
            System.out.print(SEP);
            System.out.print(giorno.bioNati);
            System.out.print(SEP);
            System.out.print(giorno.bioMorti);
            System.out.print(SEP);
            System.out.print(giorno.pageNati);
            System.out.print(SEP);
            System.out.print(giorno.pageMorti);
            System.out.print(SEP);
            System.out.print(giorno.esistePaginaNati);
            System.out.print(SEP);
            System.out.print(giorno.esistePaginaMorti);
            System.out.print(SEP);
            System.out.print(giorno.natiOk);
            System.out.print(SEP);
            System.out.print(giorno.mortiOk);
            System.out.println(SPAZIO);
        }
    }

}
