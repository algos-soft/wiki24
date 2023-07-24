package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.*;
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

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 25-Apr-2023
 * Time: 13:23
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Lista Anni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAnniTest extends ListeTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaAnni istanza;


    //--nome anno
    //--typeCrono
    protected static Stream<Arguments> ANNI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.annoNascita),
                Arguments.of(VUOTA, AETypeLista.annoMorte),
                Arguments.of("4 gennaio", AETypeLista.annoNascita),
                Arguments.of("1985", AETypeLista.nazionalitaSingolare),
                Arguments.of("1º gennaio", AETypeLista.annoMorte),
                Arguments.of("1467", AETypeLista.giornoNascita),
                Arguments.of("406 a.C.", AETypeLista.annoMorte),
                Arguments.of("1467", AETypeLista.annoNascita),
                Arguments.of("560", AETypeLista.annoMorte)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaAnni.class;
        super.setUpAll();
        super.costruttoreNecessitaAlmenoUnParametro = true;
        super.istanzaValidaSubitoDopoCostruttore = false;
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
    @Order(6)
    @DisplayName("6 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "560";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(7)
    @DisplayName("7 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "560";
        super.fixConParametroNelCostruttore(sorgente);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listBio = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void listaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista, final AETypeLista type) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (textService.isEmpty(nomeLista)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(13)
    //    @DisplayName("13 - Lista bio con switch")
    //        //--nome anno
    //        //--typeLista
    //    void listaBio(final String nomeAnno, final AETypeLista type) {
    //        System.out.println("13 - Lista bio con switch");
    //        sorgente = nomeAnno;
    //
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //
    //        listBio = switch (type) {
    //            case annoNascita -> appContext.getBean(ListaAnni.class, sorgente).nascita().listaBio();
    //            case annoMorte -> appContext.getBean(ListaAnni.class, sorgente).morte().listaBio();
    //            default -> null;
    //        };
    //
    //        if (listBio != null && listBio.size() > 0) {
    //            message = String.format("Ci sono %d biografie che implementano l'anno di %s %s", listBio.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            switch (type) {
    //                case annoNascita -> printBioListaAnniNato(listBio);
    //                case annoMorte -> printBioListaAnniMorto(listBio);
    //            }
    //        }
    //        else {
    //            message = String.format("Non esiste la listBio per le persone %s l'anno [%s]", type.getTagF(), nomeAnno);
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(14)
    //    @DisplayName("14 - Lista bio con type")
    //        //--nome anno
    //        //--typeLista
    //    void listaBioType(final String nomeAnno, final AETypeLista type) {
    //        System.out.println("14 - Lista bio con type");
    //        sorgente = nomeAnno;
    //
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //
    //        listBio = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).listaBio();
    //
    //        if (listBio != null && listBio.size() > 0) {
    //            message = String.format("Ci sono %d biografie che implementano il giorno di %s %s", listBio.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            printBio(type, listBio);
    //        }
    //        else {
    //            message = String.format("Non esiste la listBio per le persone %s il giorno [%s]", type.getTagF(), nomeAnno);
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(20)
    //    @DisplayName("20 - Lista wrapLista")
    //        //--nome anno
    //        //--typeLista
    //    void listaWrap(final String nomeAnno, final AETypeLista type) {
    //        System.out.println("20 - Lista wrapLista");
    //        sorgente = nomeAnno;
    //
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //
    //        listWrapLista = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).listaWrap();
    //
    //        if (listWrapLista != null && listWrapLista.size() > 0) {
    //            message = String.format("Ci sono %d wrapLista che implementano l'anno di %s %s", listWrapLista.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
    //                super.printWrap(wrap, this.textService);
    //            }
    //        }
    //        else {
    //            message = "La lista è nulla";
    //            System.out.println(message);
    //        }
    //    }
    //
    //
    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(40)
    //    @DisplayName("40 - Key della mappa wrapLista di vari anni")
    //        //--nome anno
    //        //--typeLista
    //    void mappaWrap5(final String nomeAnno, final AETypeLista type) {
    //        System.out.println("40 - Key della mappa wrapLista di vari anni");
    //        sorgente = nomeAnno;
    //        int numVoci;
    //
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //
    //        mappaWrap = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).mappaWrap();
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
    //            message = String.format("Ci sono %d wrapLista che implementano l'anno %s %s", mappaWrap.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            printMappaWrapKeyOrder(mappaWrap);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(41)
    //    @DisplayName("41 - Mappa wrapLista (paragrafi e righe)")
    //        //--nome anno
    //        //--typeLista
    //    void mappaWrap3(final String nomeAnno, final AETypeLista type) {
    //        System.out.println("41 - Mappa wrapLista (paragrafi e righe)");
    //        sorgente = nomeAnno;
    //        int numVoci;
    //
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //
    //        mappaWrap = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).mappaWrap();
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
    //            message = String.format("Ci sono %d wrapLista che implementano l'anno di %s %s", numVoci, type.getCivile(), sorgente);
    //            System.out.println(message);
    //            printMappaDidascalie(mappaWrap);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }


    //    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(117)
    @DisplayName("117 - Mappa wrapLista di vari anni")
    //--nome anno
    //--typeLista
    void mappaWrap2(final String nomeAnno, final AETypeLista type) {
        System.out.println("117 - Mappa wrapLista di vari anni");
        sorgente = nomeAnno;
        int numVoci;

        if (!valido(nomeAnno, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'anno %s %s", mappaWrap.size(), type.getCivile(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(50)
    //    @DisplayName("50 - Lista didascalie")
    //        //--nome anno
    //        //--typeLista
    //    void listaDidascalie(final String nomeAnno, final AETypeLista type) {
    //        sorgente = nomeAnno;
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //        listWrapLista = appContext.getBean(ListaAnni.class, sorgente).listaWrap();
    //        System.out.println("50 - Lista didascalie");
    //
    //        if (listWrapLista != null && listWrapLista.size() > 0) {
    //            System.out.println(VUOTA);
    //            for (WrapLista wrap : listWrapLista) {
    //                System.out.println(wrap.didascalia);
    //            }
    //        }
    //        else {
    //            message = "La lista è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(60)
    //    @DisplayName("60 - Key della mappa wrapLista")
    //        //--nome anno
    //        //--typeLista
    //    void mappaWrap(final String nomeAnno, final AETypeLista type) {
    //        sorgente = nomeAnno;
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //        mappaWrap = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).mappaWrap();
    //        System.out.println("60 - Key della mappa wrapLista");
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            message = String.format("Ci sono %d wrapLista che implementano la lista %s", wikiUtility.getSizeAllWrap(mappaWrap), sorgente);
    //            System.out.println(message);
    //            printMappaWrapKeyOrder(mappaWrap);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "ANNI_LISTA")
    //    @Order(70)
    //    @DisplayName("70 - Mappa STANDARD wrapLista (paragrafi e righe)")
    //        //--nome anno
    //        //--typeLista
    //    void mappaWrapDidascalie(final String nomeAnno, final AETypeLista type) {
    //        sorgente = nomeAnno;
    //        if (!valido(nomeAnno, type)) {
    //            return;
    //        }
    //        mappaWrap = appContext.getBean(ListaAnni.class, sorgente).typeLista(type).mappaWrap();
    //        System.out.println("70 - Mappa STANDARD wrapLista (paragrafi e righe)");
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            printMappaDidascalie(mappaWrap);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }


    @Test
    @Order(91)
    @DisplayName("91 - Paragrafo singolo")
    void costruttoreBase() {
        System.out.println(("91 - Paragrafo singolo"));
        System.out.println(VUOTA);

        sorgente = "1576";
        sorgente2 = "settembre";

        listWrapLista = appContext.getBean(ListaAnni.class, sorgente).nascita().getWrapLista(sorgente2);
        printSub(listWrapLista);
    }


    protected void printBio(AETypeLista type, List<Bio> listaBio) {
        switch (type) {
            case annoNascita -> printBioListaAnniNato(listaBio);
            case annoMorte -> printBioListaAnniMorto(listaBio);
        }
    }

    protected void printBioListaAnniNato(List<Bio> listaBio) {
        String message;
        int max = 50;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
            System.out.println(message);
            message = "Ordinate per giorni, forzaOrdinamento";
            System.out.println(message);
            message = "Giorno, ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBaseAnniNato(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBaseAnniNato(listaBio.subList(iniB, endB), iniB);
        }
    }

    protected void printBioListaAnniMorto(List<Bio> listaBio) {
        String message;
        int max = 50;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
            System.out.println(message);
            message = "Ordinate per giorni, forzaOrdinamento";
            System.out.println(message);
            message = "Giorno, ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBaseAnniMorto(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBaseAnniMorto(listaBio.subList(iniB, endB), iniB);
        }
    }


    protected void printBioBaseAnniNato(List<Bio> listaBio, final int inizio) {
        int cont = inizio;

        for (Bio bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.giornoNato + SPAZIO + "(" + bio.giornoNatoOrd + ")"));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.ordinamento));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.wikiTitle));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.nome) ? bio.nome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.cognome) ? bio.cognome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.println(SPAZIO);
        }
    }

    protected void printBioBaseAnniMorto(List<Bio> listaBio, final int inizio) {
        int cont = inizio;

        for (Bio bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.giornoMorto + SPAZIO + "(" + bio.giornoMortoOrd + ")"));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.ordinamento));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.wikiTitle));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.nome) ? bio.nome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.cognome) ? bio.cognome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.println(SPAZIO);
        }
    }

    private boolean valido(final String nomeAnno, final AETypeLista type) {
        if (textService.isEmpty(nomeAnno)) {
            System.out.println("Manca il nome dell'anno");
            return false;
        }

        if (type != AETypeLista.annoNascita && type != AETypeLista.annoMorte) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, ListaAnni.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (!annoWikiBackend.isExistByKey(nomeAnno)) {
            message = String.format("L'anno [%s] indicato NON esiste", nomeAnno);
            System.out.println(message);
            return false;
        }

        return true;
    }

}