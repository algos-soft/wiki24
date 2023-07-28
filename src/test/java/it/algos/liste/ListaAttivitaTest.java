package it.algos.liste;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

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
@Tag("liste")
@DisplayName("Lista Attivita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAttivitaTest extends ListeTest {

    /**
     * Classe principale di riferimento <br>
     */
    private ListaAttivita istanza;

    //--nome attività
    //--typeLista
    protected static Stream<Arguments> ATTIVITA_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.listaBreve),
                Arguments.of(VUOTA, AETypeLista.nazionalitaSingolare),
                //                Arguments.of("soprano", AETypeLista.giornoNascita),
                //                Arguments.of("soprano", AETypeLista.attivitaSingolare),
                //                Arguments.of("abate", AETypeLista.attivitaSingolare),
                Arguments.of("badessa", AETypeLista.attivitaSingolare),
                //                Arguments.of("abati e badesse", AETypeLista.attivitaPlurale),
                //                Arguments.of("bassisti", AETypeLista.attivitaPlurale),
                Arguments.of("allevatori", AETypeLista.attivitaPlurale),
                //                Arguments.of("agenti segreti", AETypeLista.attivitaPlurale),
                Arguments.of("romanzieri", AETypeLista.attivitaPlurale),
                Arguments.of("dogi", AETypeLista.attivitaPlurale),
                Arguments.of("accademici", AETypeLista.attivitaPlurale)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAttivita.class;
        super.backendClazzName = AttPluraleBackend.class.getSimpleName();
        super.collectionName = "attplurale";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = true;
        super.istanzaValidaSubitoDopoCostruttore = true;
        super.metodoDefault = "plurale()";
        super.metodiBuilderPattern += ", nascita(), morte()";
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
    @Order(7)
    @DisplayName("7 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "23 maggio";
        super.fixBeanStandard(sorgente);

        System.out.println(VUOTA);

        sorgente = "abati e badesse";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "allevatori";
        super.fixConParametroNelCostruttore(sorgente);
    }

    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "allevatori";
        istanza = appContext.getBean(ListaAttivita.class, sorgente);
        super.fixBuilderPatternListe(istanza, AETypeLista.attivitaPlurale);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listBio = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void wrapLista(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrapKey(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAttivita.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(120)
    @DisplayName("120 - WrapLista ALTERNATIVA")
    void wrapListaAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaAttivita.class, nomeLista)
                .typeLista(type)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapLista(nomeLista, listWrapLista, "120 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(130)
    @DisplayName("130 - Didascalie ALTERNATIVE")
    void listaDidascalieAlternative(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaAttivita.class, nomeLista)
                .typeLista(type)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapListaDidascalie(nomeLista, listWrapLista, "130 - Lista ALTERNATIVA didascalie con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(150)
    @DisplayName("150 - MappaWrap ALTERNATIVA")
    void mappaWrapAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaAttivita.class, nomeLista)
                .typeLista(type)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "150 - MappaWrap ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_LISTA")
    @Order(151)
    @DisplayName("151 - MappaWrap ALTERNATIVA(2)")
    void mappaWrapAlternativa2(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaAttivita.class, nomeLista)
                .typeLista(type)
                .typeLinkParagrafi(AETypeLink.linkLista)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "151 - MappaWrap ALTERNATIVA(2) con linkParagrafi=linkLista e linkCrono=linkVoce e usaIcona=false");
    }


    @Test
    @Order(220)
    @DisplayName("220 - WrapLista di sottoPagina")
    void listaWrapSottoPagina() {
        System.out.println("220 - WrapLista di sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Vescovi anglicani";
        sorgente2 = "Britannici";

        mappaWrap = appContext.getBean(ListaAttivita.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(sorgente2);

        sorgente3 = sorgente + SLASH + sorgente2;
        this.printSotto(sorgente, sorgente2, wikiUtility.wikiTitleAttivita(sorgente3), listWrapLista);
        super.fixWrapLista(sorgente3, listWrapLista);
    }

    @Test
    @Order(230)
    @DisplayName("230 - Didascalie sottoPagina")
    void listaDidascalieSottoPagina() {
        System.out.println("230 - Didascalie sottoPagina");
        System.out.println(VUOTA);

        sorgente = "Vescovi anglicani";
        sorgente2 = "Britannici";

        mappaWrap = appContext.getBean(ListaAttivita.class, sorgente).mappaWrap();
        listWrapLista = mappaWrap.get(sorgente2);

        sorgente3 = sorgente + SLASH + sorgente2;
        this.printSotto(sorgente, sorgente2, wikiUtility.wikiTitleAttivita(sorgente3), listWrapLista);
        super.fixWrapListaDidascalie(sorgente3, listWrapLista);
    }

    //    //    @Test
    //    @Order(117)
    //    @DisplayName("117 - nobiliTedeschi")
    //    void nobiliTedeschi() {
    //        System.out.println("117 - nobiliTedeschi");
    //        sorgente = "nobili";
    //        sorgente2 = "Tedeschi";
    //        int numVoci;
    //        List<WrapLista> listaSotto;
    //
    //        //        mappaWrap = appContext.getBean(ListaAttivita.class).plurale(sorgente).mappaWrap();
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            listWrapLista = mappaWrap.get(sorgente2);
    //            numVoci = listWrapLista.size();
    //            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s", numVoci, sorgente, sorgente2);
    //            System.out.println(message);
    //
    //            //            listaSotto = new ArrayList<>();
    //            //
    //            //            for (WrapLista wrap : listWrapLista) {
    //            //                stringa;
    //            //            }
    //
    //            sorgente3 = "C";
    //            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
    //            numVoci = listaSotto.size();
    //            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
    //            System.out.println(VUOTA);
    //            System.out.println(message);
    //            printSub(listaSotto);
    //
    //            sorgente3 = "E";
    //            listaSotto = listWrapLista.stream().filter(wrap -> wrap.titoloSottoParagrafo.equals(sorgente3)).collect(Collectors.toList());
    //            numVoci = listaSotto.size();
    //            message = String.format("Ci sono %d wrapLista che implementano l'attività %s e sono %s ed iniziano con %s", numVoci, sorgente, sorgente2, sorgente3);
    //            System.out.println(VUOTA);
    //            System.out.println(message);
    //            printSub(listaSotto);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }


    void printSotto(String sorgente, String sorgente2, String sottoTitolo, List<WrapLista> listWrapLista) {
        String sorgente3 = UPLOAD_TITLE_DEBUG + textService.primaMaiuscola(sorgente) + SLASH + textService.primaMaiuscola(sorgente2);

        if (listWrapLista == null) {
            message = String.format("Manca la lista di [%s] con sottoPagina '%s'", sorgente, sorgente2);
            logService.warn(new WrapLog().message(message));
            return;
        }

        System.out.println(VUOTA);
        System.out.println(String.format("Test dell'attività [%s'] con nazionalità '%s'", sorgente, sorgente2));
        System.out.println(String.format("Lista della sottopagina - Contiene %d elementi", listWrapLista.size()));
        System.out.println(String.format("Titolo della sottopagina: %s", sottoTitolo));
        System.out.println(String.format("Pagina di test: %s", sorgente3));

        System.out.println(VUOTA);
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
