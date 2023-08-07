package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.bio.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.stream.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 07-Aug-2023
 * Time: 07:39
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Bio Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BioBackendTest extends WikiBackendTest {

    @InjectMocks
    private BioBackend backend;

    private List<Bio> listaBeans;

    private Bio bio;

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> BIO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("1312382", true, false, false),
                Arguments.of("1313175", true, false, false),
                Arguments.of("brumaio", false, false, false),
                Arguments.of("settembre", false, false, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("annoNato", "1604", true),
                Arguments.of("forse", "1604", false),
                Arguments.of("annoMorto", "nix", false)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(847, true),
                Arguments.of(1, true),
                Arguments.of(12, true),
                Arguments.of(-5, false)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.bioBackend;
        super.entityClazz = Bio.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = BIO();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();

        bio = null;
    }

    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("42 - newEntity con ID ma non registrata");
    }

    @Test
    @Order(45)
    @DisplayName("45 - toString")
    protected void toStringTest() {
        System.out.println("45 - toString");
    }

    @Test
    @Order(51)
    @DisplayName("51 - findAllNoSort (entityBeans)")
    protected void findAllNoSort() {
        System.out.println("51 - findAllNoSort (entityBeans)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }

    @Test
    @Order(52)
    @DisplayName("52 - findAllSortCorrente (entityBeans)")
    protected void findAllSortCorrente() {
        System.out.println("52 - findAll findAllSortCorrente (entityBeans)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }


    @Test
    @Order(53)
    @DisplayName("53 - findAllSortCorrenteReverse (entityBeans)")
    protected void findAllSort() {
        System.out.println("53 - findAllSortCorrenteReverse (entityBeans)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }

    @Test
    @Order(54)
    @DisplayName("54 - findAllSortKey (entityBeans)")
    protected void findAllSortKey() {
        System.out.println("54 - findAllSortKey (entityBeans)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }

    @Test
    @Order(55)
    @DisplayName("55 - findAllSortOrder (entityBeans)")
    protected void findAllSortOrder() {
        System.out.println("55 - findAllSortOrder (entityBeans)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }





    @Test
    @Order(61)
    @DisplayName("61 - findAllForKeySortKey (String)")
    protected void findAllForKeySortKey() {
        System.out.println("61 - findAllForKeySortKey (String)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }


    @Test
    @Order(62)
    @DisplayName("62 - findAllForKeySortKeyReverse (String)")
    protected void findAllForKeySortKeyReverse() {
        System.out.println("62 - findAllForKeySortKeyReverse (String)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }


    @Test
    @Order(63)
    @DisplayName("63 - findAllForKeySortOrdine (String)")
    protected void findAllForKeySortOrdine() {
        System.out.println("63 - findAllForKeySortOrdine (String)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }


    @Test
    @Order(64)
    @DisplayName("64 - findAllForKeySortOrdineReverse (String)")
    protected void findAllForKeySortOrdineReverse() {
        System.out.println("64 - findAllForKeySortOrdineReverse (String)");
        System.out.println(VUOTA);
        System.out.println("Disabilitato per tempi eccessivi");
        System.out.println("Se serve 'oscurare' il metodo nella sottoClasse di test");
    }

    @Test
    @Order(81)
    @DisplayName("81 - findByKey")
    protected void findByKey() {
        System.out.println("81 - findByKey (long)");
        System.out.println(VUOTA);

        sorgenteLong = 20L;
        bio = backend.findByKey(sorgenteLong);
        assertNotNull(bio);

        message = String.format("Trovata la pagina %s tramite %s", bio.wikiTitle, "findByKey");
        logService.warn(new WrapLog().message(message));
        printBio(bio);
    }


    protected void printBio(Bio bio) {
        System.out.println(String.format("wikiTitle: %s", bio.wikiTitle));
        System.out.println(String.format("pageId: %s", bio.pageId));
        System.out.println(String.format("nome: %s", textService.isValid(bio.nome) ? bio.nome : VUOTA));
        System.out.println(String.format("cognome: %s", textService.isValid(bio.cognome) ? bio.cognome : VUOTA));
        System.out.println(String.format("sesso: %s", textService.isValid(bio.sesso) ? bio.sesso : VUOTA));
        System.out.println(String.format("luogoNato: %s", textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA));
        System.out.println(String.format("luogoNatoLink: %s", textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA));
        System.out.println(String.format("giornoNato: %s", textService.isValid(bio.giornoNato) ? bio.giornoNato : VUOTA));
        System.out.println(String.format("annoNato: %s", textService.isValid(bio.annoNato) ? bio.annoNato : VUOTA));
        System.out.println(String.format("luogoMorto: %s", textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA));
        System.out.println(String.format("luogoMortoLink: %s", textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA));
        System.out.println(String.format("giornoMorto: %s", textService.isValid(bio.giornoMorto) ? bio.giornoMorto : VUOTA));
        System.out.println(String.format("annoMorto: %s", textService.isValid(bio.annoMorto) ? bio.annoMorto : VUOTA));
        System.out.println(String.format("attivita: %s", textService.isValid(bio.attivita) ? bio.attivita : VUOTA));
        System.out.println(String.format("attivita2: %s", textService.isValid(bio.attivita2) ? bio.attivita2 : VUOTA));
        System.out.println(String.format("attivita3: %s", textService.isValid(bio.attivita3) ? bio.attivita3 : VUOTA));
        System.out.println(String.format("nazionalita: %s", textService.isValid(bio.nazionalita) ? bio.nazionalita : VUOTA));
    }

}
