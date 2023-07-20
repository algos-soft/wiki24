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
@Tag("liste")
@DisplayName("Lista Nomi")
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
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }

        listBio = appContext.getBean(ListaNomi.class, sorgente).listaBio();
        System.out.println("13- Lista bio di vari nomi");

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
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD con linkParagrafi=nessunLink")
        //--nome
    void listaWrapDidascalie(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).listaWrap();
        System.out.println("20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(21)
    @DisplayName("21 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true")
        //--nome
    void listaWrapDidascalie2(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.nessunLink).listaWrap();
        System.out.println("21 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(22)
    @DisplayName("22 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true")
        //--nome
    void listaWrapDidascalie3(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).listaWrap();
        System.out.println("22 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(23)
    @DisplayName("23- WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true")
        //--nome
    void listaWrapDidascalie4(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaNomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).listaWrap();
        System.out.println("23 - WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(24)
    @DisplayName("24- WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false")
        //--nome
    void listaWrapDidascalie6(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        listWrapLista = appContext
                .getBean(ListaNomi.class, sorgente)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();
        System.out.println("24 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapLista(listWrapLista);
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }





    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(60)
    @DisplayName("60 - Key della MappaWrap")
        //--nome
    void mappaWrap(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        System.out.println("60 - Key della MappaWrap di vari nomi");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", wikiUtility.getSizeAllWrap(mappaWrap), sorgente);
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
    @Order(70)
    @DisplayName("70 - MappaWrap STANDARD")
        //--nome
    void mappaWrapDidascalie(final String nome) {
        sorgente = nome;
        if (textService.isEmpty(nome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaNomi.class, sorgente).mappaWrap();
        System.out.println("70 - MappaWrap STANDARD");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", wikiUtility.getSizeAllWrap(mappaWrap), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

}