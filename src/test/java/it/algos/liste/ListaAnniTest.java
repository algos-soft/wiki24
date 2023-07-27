package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
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
        super.backendClazzName = AnnoWikiBackend.class.getSimpleName();
        super.collectionName = "annoWiki";
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
    @Order(7)
    @DisplayName("7 - Istanza STANDARD col parametro obbligatorio")
    void beanStandardCompleta() {
        sorgente = "4 marzo";
        super.fixBeanStandard(sorgente);

        sorgente = "1967";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "560";
        super.fixConParametroNelCostruttore(sorgente);
    }



    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        System.out.println("9 - Metodi builderPattern per validare l'istanza");

        sorgente = "560";

        appContext.getBean(ListaAnni.class, sorgente).listaBio();

        istanza = appContext.getBean(ListaAnni.class, sorgente);
        super.debug(istanza, VUOTA);

        sorgente2 = "nascita()";
        istanza = appContext.getBean(ListaAnni.class, sorgente).nascita();
        super.debug(istanza, sorgente2);

        sorgente2 = "morte()";
        istanza = appContext.getBean(ListaAnni.class, sorgente).morte();
        super.debug(istanza, sorgente2);

        sorgente2 = "typeLista(AETypeLista.annoNascita)";
        istanza = appContext.getBean(ListaAnni.class, sorgente).typeLista(AETypeLista.annoNascita);
        super.debug(istanza, sorgente2);

        sorgente2 = "typeLista(AETypeLista.annoMorte)";
        istanza = appContext.getBean(ListaAnni.class, sorgente).typeLista(AETypeLista.annoMorte);
        super.debug(istanza, sorgente2);

        sorgente2 = "typeLista(AETypeLista.attivitaSingolare)";
        istanza = appContext.getBean(ListaAnni.class, sorgente).typeLista(AETypeLista.attivitaSingolare);
        super.debug(istanza, sorgente2);
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
    void wrapLista(final String nomeLista, final AETypeLista type) {
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
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrapKey(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaAnni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }








    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(120)
    @DisplayName("120 - WrapLista ALTERNATIVA")
    void wrapListaAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaAnni.class, nomeLista)
                .typeLista(AETypeLista.annoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapLista(nomeLista, listWrapLista, "120 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(130)
    @DisplayName("130 - Didascalie ALTERNATIVE")
    void listaDidascalieAlternative(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaAnni.class, nomeLista)
                .typeLista(AETypeLista.annoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapListaDidascalie(nomeLista, listWrapLista, "130 - Lista ALTERNATIVA didascalie con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(150)
    @DisplayName("150 - MappaWrap ALTERNATIVA")
    void mappaWrapAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaAnni.class, nomeLista)
                .typeLista(AETypeLista.annoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "150 - MappaWrap ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(151)
    @DisplayName("151 - MappaWrap ALTERNATIVA(2)")
    void mappaWrapAlternativa2(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaAnni.class, nomeLista)
                .typeLista(AETypeLista.annoNascita)
                .typeLinkParagrafi(AETypeLink.linkLista)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "151 - MappaWrap ALTERNATIVA(2) con linkParagrafi=linkLista e linkCrono=linkVoce e usaIcona=false");
    }








    //    @Test
    //    @Order(91)
    //    @DisplayName("91 - Paragrafo singolo")
    //    void costruttoreBase() {
    //        System.out.println(("91 - Paragrafo singolo"));
    //        System.out.println(VUOTA);
    //
    //        sorgente = "1576";
    //        sorgente2 = "settembre";
    //
    //        listWrapLista = appContext.getBean(ListaAnni.class, sorgente).nascita().getWrapLista(sorgente2);
    //        printSub(listWrapLista);
    //    }


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