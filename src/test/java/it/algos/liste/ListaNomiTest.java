package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 08:20
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("liste")
@DisplayName("ListaNomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaNomiTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaNomi istanza;

    //--nome
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Aaron"),
                Arguments.of("Alexandra"),
                Arguments.of("adriana"),
                Arguments.of("maria teresa")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.clazz = ListaNomi.class;
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


    @Test
    @Order(3)
    @DisplayName("3 - listaBioSenzaParametroNelCostruttore")
    void listaBioSenzaParametroNelCostruttore() {
        System.out.println(("3 - listaBioSenzaParametroNelCostruttore"));
        System.out.println(VUOTA);

        appContext.getBean(ListaNomi.class).listaBio();

        System.out.println(String.format("Non è possibile creare un'istanza della classe [%s] SENZA parametri", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("È obbligatorio il 'nomeLista' nel costruttore.");
        System.out.println(String.format("Seguendo il Pattern Builder, non si può chiamare il metodo %s se l'istanza non è correttamente istanziata.", "listaBio"));
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(13)
    @DisplayName("13 - Lista bio di vari nomi")
        //--nome
    void listaBio(final String nome) {
        System.out.println("13- Lista bio di vari nomi");
        sorgente = nome;

        if (textService.isEmpty(nome)) {
            return;
        }

        listBio = appContext.getBean(ListaNomi.class, sorgente).listaBio();

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano il nome %s", listBio.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(14)
    @DisplayName("14 - Lista wrapLista di vari nomi con typeLinkParagrafi=linkLista")
        //--nome
    void listaWrapDidascalie(final String nome) {
        System.out.println("14 - Lista wrapLista di vari nomi con typeLinkParagrafi=linkLista");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(15)
    @DisplayName("15 - Lista wrapLista di vari nomi con typeLinkParagrafi=linkVoce")
        //--nome
    void listaWrapDidascalie2(final String nome) {
        System.out.println("15 - Lista wrapLista di vari nomi con typeLinkParagrafi=linkVoce");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(16)
    @DisplayName("16 - Lista wrapLista di vari nomi con linkCrono=nessunLink")
        //--nome
    void listaWrapDidascalie3(final String nome) {
        System.out.println("16 - Lista wrapLista di vari nomi con linkCrono=nessunLink");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }
        AETypeLink typeLinkCronoOld = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        WPref.linkCrono.setValue(AETypeLink.nessunLink);
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();
        WPref.linkCrono.setValue(typeLinkCronoOld);

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(17)
    @DisplayName("17 - Lista wrapLista di vari nomi con linkCrono=linkVoce")
        //--nome
    void listaWrapDidascalie4(final String nome) {
        System.out.println("17 - Lista wrapLista di vari nomi con linkCrono=linkVoce");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        AETypeLink typeLinkCronoOld = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        WPref.linkCrono.setValue(AETypeLink.linkVoce);
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();
        WPref.linkCrono.setValue(typeLinkCronoOld);

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(18)
    @DisplayName("18 - Lista wrapLista di default linkCrono=linkLista e typeLinkParagrafi=nessunLink")
        //--nome
    void listaWrapDidascalie5(final String nome) {
        System.out.println("18 - Lista wrapLista di default linkCrono=linkLista e typeLinkParagrafi=nessunLink");
        sorgente = nome;
        int size;

        if (textService.isEmpty(nome)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
            printWrapLista(listWrapLista.subList(size - 5, size));
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(20)
    @DisplayName("20 - Key della mappa wrapLista di vari nomi")
        //--nome
    void mappaWrap(final String nome) {
        System.out.println("20 - Key della mappa wrapLista di vari nomi");
        sorgente = nome;
        int numVoci;

        if (textService.isEmpty(nome)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano il nome di %s", numVoci, sorgente);
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(30)
    @DisplayName("30 - Mappa wrapLista di vari nomi con typeLink=linkLista")
        //--nome
    void mappaWrapDidascalie(final String nome) {
        System.out.println("30 - Mappa wrapLista di vari nomi con typeLink=linkLista");
        sorgente = nome;
        int numVoci;

        if (textService.isEmpty(nome)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", numVoci, sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}