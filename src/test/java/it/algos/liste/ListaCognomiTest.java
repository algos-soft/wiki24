package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 17:33
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Lista Cognomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaCognomiTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaCognomi istanza;

    //--cognome
    protected static Stream<Arguments> COGNOMI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Adam"),
                Arguments.of("Battaglia"),
                Arguments.of("Camweron"),
                Arguments.of("Cameron"),
                Arguments.of("cameron"),
                Arguments.of("Capri"),
                Arguments.of("Gomez")
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
        super.clazz = ListaCognomi.class;
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

        appContext.getBean(ListaCognomi.class).listaBio();

        System.out.println(String.format("Non è possibile creare un'istanza della classe [%s] SENZA parametri", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("È obbligatorio il 'nomeLista' nel costruttore.");
        System.out.println(String.format("Seguendo il Pattern Builder, non si può chiamare il metodo %s se l'istanza non è correttamente istanziata.", "listaBio"));
    }

    @Test
    @Order(4)
    @DisplayName("4 - Istanza (completa) coi valori standard")
    void beanStandardIncompleta() {
        System.out.println(String.format("4 - istanza (completa) di [%s] coi valori standard", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("Il Pattern Builder NON richiede regolazioni aggiuntive");
        System.out.println(VUOTA);

        sorgente = "Gomez";
        istanza = appContext.getBean(ListaCognomi.class, sorgente);
        assertNotNull(istanza);
        printLista(istanza);
    }

    @Test
    @Order(5)
    @DisplayName("5 - listaBioSenzaTypeLista")
    void listaBioSenzaTypeLista() {
        System.out.println(("5 - listaBioSenzaTypeLista"));
        System.out.println(VUOTA);

        sorgente = "Gomez";
        appContext.getBean(ListaCognomi.class, sorgente).listaBio();

        System.out.println(String.format("Questa classe funziona anche SENZA '%s' perché è già inserito in fixPreferenze().", "typeLista"));
    }

    @Test
    @Order(6)
    @DisplayName("6 - Istanza completa coi valori standard")
    void beanStandardCompleta() {
        System.out.println(String.format("6 - istanza (completa) di [%s] coi valori standard", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("Non ci sono parametri essenziali per il Pattern Builder");
        System.out.println("Pronta per listaBio(), listaWrap() e mappaWrap()");
        System.out.println(VUOTA);

        sorgente = "Gomez";
        istanza = (ListaCognomi) appContext.getBean(ListaCognomi.class, sorgente);
        assertNotNull(istanza);
        printLista(istanza);
    }

    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(13)
    @DisplayName("13 - Lista bio")
        //--cognome
    void listaBio(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listBio = appContext.getBean(ListaCognomi.class, sorgente).listaBio();
        System.out.println("13- Lista bio");

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano il cognome %s", listBio.size(), sorgente);
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
    @MethodSource(value = "COGNOMI")
    @Order(20)
    @DisplayName("20 - Lista wrapLista STANDARD con linkParagrafi=nessunLink")
        //--cognome
    void listaWrapDidascalie(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).listaWrap();
        System.out.println("20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano la lista %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

//    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(21)
    @DisplayName("21 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true")
        //--cognome
    void listaWrapDidascalie2(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.nessunLink).listaWrap();
        System.out.println("21 - WrapLista ALTERNATIVA con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }

//    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(22)
    @DisplayName("22 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true")
        //--cognome
    void listaWrapDidascalie3(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkVoce).listaWrap();
        System.out.println("22 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(23)
    @DisplayName("23- WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true")
        //--cognome
    void listaWrapDidascalie4(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).typeLinkParagrafi(AETypeLink.linkLista).listaWrap();
        System.out.println("23 - WrapLista ALTERNATIVA con linkParagrafi=linkLista e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(24)
    @DisplayName("24- WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false")
        //--cognome
    void listaWrapDidascalie6(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext
                .getBean(ListaCognomi.class, sorgente)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();
        System.out.println("24 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il cognome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, Math.min(5, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(50)
    @DisplayName("50 - Lista didascalie")
        //--cognome
    void listaDidascalie(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        listWrapLista = appContext.getBean(ListaCognomi.class, sorgente).listaWrap();
        System.out.println("50 - Lista didascalie");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista) {
                System.out.println(wrap.didascalia);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }




    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(60)
    @DisplayName("60 - Key della mappa wrapLista")
        //--cognome
    void mappaWrap44(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        System.out.println("60 - Key della mappa wrapLista");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano la lista %s", wikiUtility.getSizeAllWrap(mappaWrap), sorgente);
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "COGNOMI")
    @Order(70)
    @DisplayName("70 - Mappa STANDARD wrapLista (paragrafi e righe)")
        //--cognome
    void mappaWrapDidascalie(final String cognome) {
        sorgente = cognome;
        if (textService.isEmpty(cognome)) {
            return;
        }
        mappaWrap = appContext.getBean(ListaCognomi.class, sorgente).mappaWrap();
        System.out.println("70 - Mappa STANDARD wrapLista (paragrafi e righe)");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            printMappaDidascalie(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

}