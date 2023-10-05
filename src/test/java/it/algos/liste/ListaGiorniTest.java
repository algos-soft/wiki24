package it.algos.liste;

import it.algos.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
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

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 08-Mar-2023
 * Time: 21:06
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("liste")
@DisplayName("Lista Giorni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiorniTest extends ListeTest {


    /**
     * Classe principale di riferimento <br>
     */
    private ListaGiorni istanza;


    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> GIORNI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.giornoNascita),
                Arguments.of(VUOTA, AETypeLista.giornoMorte),
                Arguments.of("1857", AETypeLista.giornoNascita),
                Arguments.of("8 aprile", AETypeLista.attivitaPlurale),
                Arguments.of("34 febbraio", AETypeLista.giornoMorte),
                Arguments.of("1º gennaio", AETypeLista.giornoNascita),
                Arguments.of("23 marzo", AETypeLista.annoMorte),
                //                Arguments.of("1º gennaio", AETypeLista.giornoMorte),
                Arguments.of("29 febbraio", AETypeLista.giornoNascita),
                Arguments.of("29 febbraio", AETypeLista.giornoMorte)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaGiorni.class;
        super.backendClazzName = GiornoWikiBackend.class.getSimpleName();
        super.collectionName = "giornoWiki";
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
        super.istanzaValidaSubitoDopoCostruttore = false;
        super.metodiDaRegolare += ", nascita(), morte()";
        super.metodiBuilderPattern += ", nascita(), morte()";
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
        //--costruisce un'istanza con un parametro e controlla che il valore sia accettabile per la collection
        sorgente = "calciatori";
        super.fixBeanStandard(sorgente);

        sorgente = "14 marzo";
        super.fixBeanStandard(sorgente);
    }

    //    @Test
    //    @Order(8)
    //    @DisplayName("8 - esegueConParametroNelCostruttore")
    //    void esegueConParametroNelCostruttore() {
    //        sorgente = "27 novembre";
    //        super.fixConParametroNelCostruttore(sorgente);
    //    }

    @Test
    @Order(9)
    @DisplayName("9 - builderPattern")
    void builderPattern() {
        fixBuilderPatternIniziale();

        sorgente = "10 novembre";
        istanza = appContext.getBean(ListaGiorni.class, sorgente);
        super.fixBuilderPatternListe(istanza, AETypeLista.giornoMorte);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(10)
    @DisplayName("10 - Lista bio BASE")
    void listaBio(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listBio = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).listaBio();
        super.fixListaBio(nomeLista, listBio);
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(20)
    @DisplayName("20 - WrapLista STANDARD")
    void wrapLista(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapLista(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(30)
    @DisplayName("30 - Didascalie STANDARD")
    void listaDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).listaWrap();
        super.fixWrapListaDidascalie(nomeLista, listWrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(40)
    @DisplayName("40 - Key della mappaWrap STANDARD")
    void mappaWrapKey(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapKey(nomeLista, mappaWrap);
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(50)
    @DisplayName("50 - MappaWrap STANDARD con paragrafi e righe")
    void mappaWrap(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(120)
    @DisplayName("120 - WrapLista ALTERNATIVA")
    void wrapListaAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaGiorni.class, nomeLista)
                .typeLista(AETypeLista.giornoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapLista(nomeLista, listWrapLista, "120 - WrapLista ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(130)
    @DisplayName("130 - Didascalie ALTERNATIVE")
    void listaDidascalieAlternative(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        listWrapLista = appContext
                .getBean(ListaGiorni.class, nomeLista)
                .typeLista(AETypeLista.giornoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .listaWrap();

        super.fixWrapListaDidascalie(nomeLista, listWrapLista, "130 - Lista ALTERNATIVA didascalie con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(150)
    @DisplayName("150 - MappaWrap ALTERNATIVA")
    void mappaWrapAlternativa(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaGiorni.class, nomeLista)
                .typeLista(AETypeLista.giornoNascita)
                .typeLinkParagrafi(AETypeLink.linkVoce)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "150 - MappaWrap ALTERNATIVA con linkParagrafi=linkVoce e linkCrono=linkVoce e usaIcona=false");
    }

    @ParameterizedTest
    @MethodSource(value = "GIORNI_LISTA")
    @Order(151)
    @DisplayName("151 - MappaWrap ALTERNATIVA(2)")
    void mappaWrapAlternativa2(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext
                .getBean(ListaGiorni.class, nomeLista)
                .typeLista(AETypeLista.giornoNascita)
                .typeLinkParagrafi(AETypeLink.linkLista)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();

        fixMappaWrapDidascalie(nomeLista, mappaWrap, "151 - MappaWrap ALTERNATIVA(2) con linkParagrafi=linkLista e linkCrono=linkVoce e usaIcona=false");
    }

    @Test
    @Order(210)
    @DisplayName("210 - Paragrafo singolo")
    void costruttoreBase() {
        System.out.println(("210 - Paragrafo singolo"));
        System.out.println(VUOTA);

        sorgente = "4 gennaio";
        sorgente2 = "XVI secolo";

        listWrapLista = appContext.getBean(ListaGiorni.class, sorgente).nascita().getWrapLista(sorgente2);
        assertNotNull(listWrapLista);
        for (WrapLista wrap : listWrapLista.subList(0, Math.min(MAX, listWrapLista.size()))) {
            super.printWrap(wrap, this.textService);
        }
    }


    protected void printBio(AETypeLista type, List<Bio> listaBio) {
        switch (type) {
            case giornoNascita -> printBioListaGiorniNato(listaBio);
            case giornoMorte -> printBioListaGiorniMorto(listaBio);
        }
    }


    protected void printBioListaGiorniNato(List<Bio> listaBio) {
        String message;
        int max = 10;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            tot = listaBio.size();
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
            System.out.println(message);
            message = "Ordinate per anni, forzaOrdinamento";
            System.out.println(message);
            message = "Anno, ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBaseGiorniNato(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBaseGiorniNato(listaBio.subList(iniB, endB), iniB);
        }
    }

    protected void printBioListaGiorniMorto(List<Bio> listaBio) {
        String message;
        int max = 10;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
            System.out.println(message);
            message = "Ordinate per anni, forzaOrdinamento";
            System.out.println(message);
            message = "Anno, ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBaseGiorniMorto(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBaseGiorniMorto(listaBio.subList(iniB, endB), iniB);
        }
    }

    protected void printBioBaseGiorniNato(List<Bio> listaBio, final int inizio) {
        int cont = inizio;

        for (Bio bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.annoNato + SPAZIO + "(" + bio.annoNatoOrd + ")"));
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

    protected void printBioBaseGiorniMorto(List<Bio> listaBio, final int inizio) {
        int cont = inizio;

        for (Bio bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.annoMorto + SPAZIO + "(" + bio.annoMortoOrd + ")"));
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


    private boolean valido(final String nomeGiorno, final AETypeLista type) {
        if (textService.isEmpty(nomeGiorno)) {
            System.out.println("Manca il nome del giorno");
            return false;
        }

        if (type != AETypeLista.giornoNascita && type != AETypeLista.giornoMorte) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, ListaGiorni.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (!giornoWikiBackend.isExistByKey(nomeGiorno)) {
            message = String.format("Il giorno [%s] indicato NON esiste", nomeGiorno);
            System.out.println(message);
            return false;
        }

        return true;
    }

}