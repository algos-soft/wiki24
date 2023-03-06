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
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;

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
@DisplayName("GiornoWiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoWikiBackendTest extends WikiBackendTest {

    private GiornoWikiBackend backend;

    private List<GiornoWiki> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = GiornoWiki.class;
        backend = giornoWikiBackend;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.setUpAll();
        super.typeBackend = TypeBackend.giorno;
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
//            giornoBase = giornoBackend.findByKey(sorgente);
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
    @Order(42)
    @DisplayName("42 - CRUD operations")
    protected void crud() {
        System.out.println("42 - CRUD operations");
        System.out.println(VUOTA);

        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro di tipo [Giorno]", METHOD_NAME_NEW_ENTITY, backendName);

    }

    @Test
    @Order(51)
    @DisplayName("51 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("51 - findAllForNome (String)");
        System.out.println("Uguale a 31 - findAllForKey");
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
    @Order(52)
    @DisplayName("52 - findAllByMese (entity)")
    protected void findAllByMese() {
        System.out.println("52 - findAllByMese (entity)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");

        for (Mese sorgente : meseBackend.findAllNoSort()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            System.out.println(VUOTA);
            printBackend(listaBeans, 3);
        }
    }

    @Test
    @Order(53)
    @DisplayName("53 - findAllForNomeByMese (nomi)")
    protected void findAllForNomeByMese() {
        System.out.println("54 - findAllForNomeByMese (nomi)");
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
    @Order(61)
    @DisplayName("61 - isExistKey")
    protected void isExistKey() {
        System.out.println("61 - isExistKey");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dal numero progressivo nell'anno");
        System.out.println(VUOTA);

        //--giorno
        //--esistente
        System.out.println(VUOTA);
        GIORNI().forEach(this::isExistKeyBase);
    }

    //--giorno
    //--esistente
    void isExistKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = backend.isExistKey(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        if (ottenutoBooleano) {
            System.out.println(String.format("Il giorno %s esiste", sorgente));
        }
        else {
            System.out.println(String.format("Il giorno %s non esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(62)
    @DisplayName("62 - findByOrdine")
    protected void findByOrdine() {
        System.out.println("62 - findByOrdine");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dal numero progressivo nell'anno");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 127;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 250;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Override
    protected Object getParamEsistente() {
        sorgente = "3 ottobre";
        previsto = "3ottobre";
        previsto2 = sorgente;

        return sorgente;
    }


}
