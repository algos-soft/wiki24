package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 03-Jun-2022
 * Time: 20:06
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("liste")
@DisplayName("Lista Attivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAttivitaTest extends WikiTest {

    /**
     * Classe principale di riferimento <br>
     */
    private ListaAttivita istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.clazz = ListaAttivita.class;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
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

        appContext.getBean(ListaAttivita.class).listaBio();

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

        sorgente = "accademici";
        istanza = appContext.getBean(ListaAttivita.class, sorgente);
        assertNotNull(istanza);
        printLista(istanza);
    }

    @Test
    @Order(5)
    @DisplayName("5 - listaBioSenzaTypeLista")
    void listaBioSenzaTypeLista() {
        System.out.println(("5 - listaBioSenzaTypeLista"));
        System.out.println(VUOTA);

        sorgente = "agenti segreti";
        appContext.getBean(ListaAttivita.class, sorgente).listaBio();

        System.out.println(String.format("Questa classe funziona anche SENZA '%s' perché è già inserito in fixPreferenze().", "typeLista"));
    }

    @Test
    @Order(6)
    @DisplayName("6 - Istanza completa coi valori standard")
    void beanStandardCompleta() {
        System.out.println(String.format("6 - istanza (completa) di [%s] coi valori standard", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("Sono stati regolari tutti i parametri indispensabili per il Pattern Builder");
        System.out.println("Pronta per listaBio(), listaWrap() e mappaWrap()");
        System.out.println(VUOTA);

        sorgente = "accademici";
        istanza = (ListaAttivita) appContext.getBean(ListaAttivita.class, sorgente).typeLista(AETypeLista.attivitaPlurale);
        assertNotNull(istanza);
        printLista(istanza);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(13)
    @DisplayName("13 - Lista bio")
        //--nome attivita
        //--typeLista
    void listaBio(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (textService.isEmpty(nomeAttivita)) {
            return;
        }

        listBio = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().listaBio();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).listaBio();
            default -> null;
        };
        System.out.println("13- Lista bio");

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano l'attività %s %s", listBio.size(), type.getTagLower(), sorgente);
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }



    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(20)
    @DisplayName("20 - Lista wrapLista STANDARD con linkParagrafi=nessunLink")
        //--nome attivita
        //--typeLista
    void listaWrapDidascalie(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (textService.isEmpty(nomeAttivita)) {
            return;
        }
        listWrapLista = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().listaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).listaWrap();
            default -> null;
        };
        System.out.println("20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano il nome %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0,5)) {
                super.printWrap(wrap,this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }




    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(40)
    @DisplayName("40 - Key della mappa wrapLista")
        //--nome attivita
        //--typeLista
    void mappaWrap2(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (textService.isEmpty(nomeAttivita)) {
            return;
        }
        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).mappaWrap();
            default -> null;
        };
        System.out.println("40 - Key della mappa wrapLista");

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
    @MethodSource(value = "ATTIVITA")
    @Order(41)
    @DisplayName("41 - Mappa wrapLista (paragrafi e righe)")
        //--nome attivita
        //--typeLista
    void mappaWrap22(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (textService.isEmpty(nomeAttivita)) {
            return;
        }
        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).mappaWrap();
            default -> null;
        };
        System.out.println("41 - Mappa wrapLista (paragrafi e righe)");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            printMappaDidascalie(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(50)
    @DisplayName("50 - Lista didascalie")
        //--nome attivita
        //--typeLista
    void listaDidascalie(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (!valido(nomeAttivita, type)) {
            return;
        }
        listWrapLista = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().listaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).listaWrap();
            default -> null;
        };
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
    @MethodSource(value = "ATTIVITA")
    @Order(60)
    @DisplayName("60 - Key della mappa wrapLista")
        //--nome attivita
        //--typeLista
    void mappaWrap(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (!valido(nomeAttivita, type)) {
            return;
        }
        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).mappaWrap();
            default -> null;
        };
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
    @MethodSource(value = "ATTIVITA")
    @Order(70)
    @DisplayName("70 - Mappa STANDARD wrapLista (paragrafi e righe)")
        //--nome attivita
        //--typeLista
    void mappaWrapDidascalie(final String nomeAttivita, final AETypeLista type) {
        sorgente = nomeAttivita;
        if (!valido(nomeAttivita, type)) {
            return;
        }
        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).mappaWrap();
            default -> null;
        };
        System.out.println("70 - MappaWrap STANDARD");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            printMappaDidascalie(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(114)
    @DisplayName("114 - Lista wrapLista di varie attivita")
        //--nome attivita
        //--typeLista
    void listaWrapDidascalie2(final String nomeAttivita, final AETypeLista type) {
        System.out.println("114 - Lista wrapLista di varie attivita");
        sorgente = nomeAttivita;
        int size;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        listWrapLista = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().listaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).listaWrap();
            default -> null;
        };

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s %s", listWrapLista.size(), sorgente, type.getTagLower());
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0,5)) {
                super.printWrap(wrap,this.textService);
            }
        }
        else {
            message = "La lista è nulla";
            System.out.println(message);
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(115)
    @DisplayName("115 - Key della mappa wrapLista di varie attività")
        //--nome attivita
        //--typeLista
    void mappaWrap33(final String nomeAttivita, final AETypeLista type) {
        System.out.println("115 - Key della mappa wrapLista di varie attività");
        sorgente = nomeAttivita;
        int numVoci;

        if (!valido(nomeAttivita, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case attivitaSingolare -> appContext.getBean(ListaAttivita.class,sorgente).singolare().mappaWrap();
            case attivitaPlurale -> appContext.getBean(ListaAttivita.class,sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'attività di %s %s", numVoci, type.getCivile(), sorgente);
            System.out.println(message);
            this.printAttivitaSingole(type, sorgente);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }




    //    @Test
    @Order(117)
    @DisplayName("117 - nobiliTedeschi")
    void nobiliTedeschi() {
        System.out.println("117 - nobiliTedeschi");
        sorgente = "nobili";
        sorgente2 = "Tedeschi";
        int numVoci;
        List<WrapLista> listaSotto;

//        mappaWrap = appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            listWrapLista = mappaWrap.get(sorgente2);
            numVoci = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s", numVoci, sorgente, sorgente2);
            System.out.println(message);

            //            listaSotto = new ArrayList<>();
            //
            //            for (WrapLista wrap : listWrapLista) {
            //                stringa;
            //            }

            sorgente3 = "C";
            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
            numVoci = listaSotto.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
            System.out.println(VUOTA);
            System.out.println(message);
            printSub(listaSotto);

            sorgente3 = "E";
            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
            numVoci = listaSotto.size();
            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
            System.out.println(VUOTA);
            System.out.println(message);
            printSub(listaSotto);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    private boolean valido(final String nomeAttivita, final AETypeLista type) {
        if (textService.isEmpty(nomeAttivita)) {
            System.out.println("Manca il nome dell'attività");
            return false;
        }

        if (type != AETypeLista.attivitaSingolare && type != AETypeLista.attivitaPlurale) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, ListaAttivita.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaSingolare && !attSingolareBackend.isExistByKey(nomeAttivita)) {
            message = String.format("L'attività singolare [%s] indicata NON esiste", nomeAttivita);
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaPlurale && !attPluraleBackend.isExistByKey(nomeAttivita)) {
            message = String.format("L'attività plurale [%s] indicata NON esiste", nomeAttivita);
            System.out.println(message);
            return false;
        }

        return true;
    }

    private void printAttivitaSingole(final AETypeLista type, final String nomeAttivita) {
        if (type == AETypeLista.attivitaPlurale) {
            List<String> listaAttivitaSingoleComprese = attPluraleBackend.findAllFromNomiSingolariByPlurale(nomeAttivita);
            message = String.format("Che raggruppa le %d attività singolari%s%s", listaAttivitaSingoleComprese.size(), FORWARD, listaAttivitaSingoleComprese);
            System.out.println(message);
        }
    }

}
