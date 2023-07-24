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
        sorgente = "14 marzo";
        super.fixBeanStandard(sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - esegueConParametroNelCostruttore")
    void esegueConParametroNelCostruttore() {
        sorgente = "27 novembre";
        super.fixConParametroNelCostruttore(sorgente);
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
    void listaWrapDidascalie(final String nomeLista, final AETypeLista type) {
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
    void mappaWrap(final String nomeLista, final AETypeLista type) {
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
    void mappaWrapDidascalie(final String nomeLista, final AETypeLista type) {
        if (!valido(nomeLista, type)) {
            return;
        }

        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).typeLista(type).mappaWrap();
        super.fixMappaWrapDidascalie(nomeLista, mappaWrap);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "GIORNI_LISTA")
    //    @Order(13)
    //    @DisplayName("13 - Lista bio con switch")
    //        //--nome giorno
    //        //--typeLista
    //    void listaBio(final String nomeGiorno, final AETypeLista type) {
    //        System.out.println("13 - Lista bio con switch");
    //        sorgente = nomeGiorno;
    //
    //        if (!valido(nomeGiorno, type)) {
    //            return;
    //        }
    //
    //        listBio = switch (type) {
    //            case giornoNascita -> appContext.getBean(ListaGiorni.class, sorgente).nascita().listaBio();
    //            case giornoMorte -> appContext.getBean(ListaGiorni.class, sorgente).morte().listaBio();
    //            default -> null;
    //        };
    //
    //        if (listBio != null && listBio.size() > 0) {
    //            message = String.format("Ci sono %d biografie che implementano il giorno di %s %s", listBio.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            switch (type) {
    //                case giornoNascita -> printBioListaGiorniNato(listBio);
    //                case giornoMorte -> printBioListaGiorniMorto(listBio);
    //            }
    //        }
    //        else {
    //            message = String.format("Non esiste la listBio per le persone %s il giorno [%s]", type.getTagF(), nomeGiorno);
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "GIORNI_LISTA")
    //    @Order(14)
    //    @DisplayName("14 - Lista bio con type")
    //        //--nome giorno
    //        //--typeLista
    //    void listaBioType(final String nomeGiorno, final AETypeLista type) {
    //        System.out.println("14 - Lista bio con type");
    //        sorgente = nomeGiorno;
    //
    //        if (!valido(nomeGiorno, type)) {
    //            return;
    //        }
    //
    //        listBio = appContext.getBean(ListaGiorni.class, sorgente).typeLista(type).listaBio();
    //
    //        if (listBio != null && listBio.size() > 0) {
    //            message = String.format("Ci sono %d biografie che implementano il giorno di %s %s", listBio.size(), type.getCivile(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            printBio(type, listBio);
    //        }
    //        else {
    //            message = String.format("Non esiste la listBio per le persone %s il giorno [%s]", type.getTagF(), nomeGiorno);
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "GIORNI_LISTA")
    //    @Order(20)
    //    @DisplayName("20 - Lista wrapLista")
    //        //--nome giorno
    //        //--typeLista
    //    void listaWrap(final String nomeGiorno, final AETypeLista type) {
    //        sorgente = nomeGiorno;
    //        if (!valido(nomeGiorno, type)) {
    //            return;
    //        }
    //        listWrapLista = appContext.getBean(ListaGiorni.class, sorgente).typeLista(type).listaWrap();
    //        System.out.println("20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
    //
    //        if (listWrapLista != null && listWrapLista.size() > 0) {
    //            message = String.format("Ci sono %d wrapLista che implementano la lista %s", listWrapLista.size(), sorgente);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            for (WrapLista wrap : listWrapLista.subList(0,5)) {
    //                super.printWrap(wrap,this.textService);
    //            }
    //        }
    //        else {
    //            message = "La lista è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    @ParameterizedTest
    //    @MethodSource(value = "GIORNI_LISTA")
    //    @Order(40)
    //    @DisplayName("40 - Key della mappa wrapLista di vari giorni")
    //        //--nome giorno
    //        //--typeLista
    //    void mappaWrapKey(final String nomeGiorno, final AETypeLista type) {
    //        System.out.println("40 - Key della mappa wrapLista di vari giorni");
    //        sorgente = nomeGiorno;
    //        int numVoci;
    //
    //        if (!valido(nomeGiorno, type)) {
    //            return;
    //        }
    //
    //        mappaWrap = appContext.getBean(ListaGiorni.class, sorgente).typeLista(type).mappaWrap();
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
    //            message = String.format("Ci sono %d wrapLista che implementano il giorno di %s %s", numVoci, type.getCivile(), sorgente);
    //            System.out.println(message);
    //            printMappaWrapKeyOrder(mappaWrap);
    //        }
    //        else {
    //            message = "La mappa è nulla";
    //            System.out.println(message);
    //        }
    //    }








//    //    @Test
//    @Order(91)
//    @DisplayName("91 - Paragrafo singolo")
//    void costruttoreBase() {
//        System.out.println(("91 - Paragrafo singolo"));
//        System.out.println(VUOTA);
//
//        sorgente = "4 gennaio";
//        sorgente2 = "XVI secolo";
//
//        listWrapLista = appContext.getBean(ListaGiorni.class, sorgente).nascita().getWrapLista(sorgente2);
//        printSub(listWrapLista);
//    }


//    //    @Test
//    @Order(141)
//    @DisplayName("141 - linkCrono -> linkVoce")
//    void linkVoce() {
//        System.out.println(("141 - linkCrono -> linkVoce"));
//        System.out.println(VUOTA);
//        AETypeLink typeLinkCrono = AETypeLink.linkVoce;
//
//        sorgente = "29 febbraio";
//        listWrapLista = appContext
//                .getBean(ListaGiorni.class, sorgente)
//                .typeLista(AETypeLista.giornoMorte)
//                .typeLinkCrono(typeLinkCrono)
//                .listaWrap();
//
//        if (listWrapLista != null && listWrapLista.size() > 0) {
//            message = String.format("Ci sono %d wrapLista che implementano il giorno %s con %s", listWrapLista.size(), sorgente, typeLinkCrono);
//            System.out.println(message);
//            System.out.println(VUOTA);
//            printWrapLista(listWrapLista.subList(listWrapLista.size() - 5, listWrapLista.size()));
//        }
//        else {
//            message = "La lista è nulla";
//            System.out.println(message);
//        }
//    }



    //    //    @Test
    //    @Order(142)
    //    @DisplayName("142 - linkCrono -> linkLista")
    //    void linkLista() {
    //        System.out.println(("142 - linkCrono -> linkLista"));
    //        System.out.println(VUOTA);
    //        AETypeLink typeLinkCrono = AETypeLink.linkLista;
    //
    //        sorgente = "29 febbraio";
    //        listWrapLista = appContext
    //                .getBean(ListaGiorni.class, sorgente)
    //                .typeLista(AETypeLista.giornoMorte)
    //                .typeLinkCrono(typeLinkCrono)
    //                .listaWrap();
    //
    //        if (listWrapLista != null && listWrapLista.size() > 0) {
    //            message = String.format("Ci sono %d wrapLista che implementano il giorno %s con %s", listWrapLista.size(), sorgente, typeLinkCrono);
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            printWrapLista(listWrapLista.subList(listWrapLista.size() - 5, listWrapLista.size()));
    //        }
    //        else {
    //            message = "La lista è nulla";
    //            System.out.println(message);
    //        }
    //    }


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