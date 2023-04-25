package it.algos.liste;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
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
@DisplayName("ListaAnni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaAnniTest extends WikiTest {


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
        super.setUpAll();
        assertNull(istanza);
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
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new ListaAnni();
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(2)
    @DisplayName("2 - Lista bio di vari anni")
        //--nome anno
        //--typeLista
    void listaBio(final String nomeAnno, final AETypeLista type) {
        System.out.println("2 - Lista bio di vari anni");
        sorgente = nomeAnno;

        if (!valido(nomeAnno, type)) {
            return;
        }

        listBio = switch (type) {
            case annoNascita -> appContext.getBean(ListaAnni.class).nascita(sorgente).listaBio();
            case annoMorte -> appContext.getBean(ListaAnni.class).morte(sorgente).listaBio();
            default -> null;
        };

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano l'anno di %s %s", listBio.size(), type.getCivile(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            switch (type) {
                case annoNascita -> printBioListaAnniNato(listBio);
                case annoMorte -> printBioListaAnniMorto(listBio);
            }
        }
        else {
            message = String.format("Non esiste la listBio per le persone %s l'anno [%s]", type.getTagF(), nomeAnno);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(3)
    @DisplayName("3 - Lista wrapLista di vari anni")
        //--nome anno
        //--typeLista
    void listaWrap(final String nomeAnno, final AETypeLista type) {
        System.out.println("3 - Lista wrapLista di vari anni");
        sorgente = nomeAnno;
        int size;

        if (!valido(nomeAnno, type)) {
            return;
        }

        listWrapLista = switch (type) {
            case annoNascita -> appContext.getBean(ListaAnni.class).nascita(sorgente).listaWrap();
            case annoMorte -> appContext.getBean(ListaAnni.class).morte(sorgente).listaWrap();
            default -> null;
        };

        if (listWrapLista != null && listWrapLista.size() > 0) {
            size = listWrapLista.size();
            message = String.format("Ci sono %d wrapLista che implementano l'anno di %s %s", listWrapLista.size(), type.getCivile(), sorgente);
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
    @MethodSource(value = "ANNI_LISTA")
    @Order(4)
    @DisplayName("4 - Key della mappa wrapLista di vari anni")
        //--nome anno
        //--typeLista
    void mappaWrap(final String nomeAnno, final AETypeLista type) {
        System.out.println("4 - Key della mappa wrapLista di vari anni");
        sorgente = nomeAnno;
        int numVoci;

        if (!valido(nomeAnno, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case annoNascita -> appContext.getBean(ListaAnni.class).nascita(sorgente).mappaWrap();
            case annoMorte -> appContext.getBean(ListaAnni.class).morte(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'anno di %s %s", numVoci, type.getCivile(), sorgente);
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "ANNI_LISTA")
    @Order(5)
    @DisplayName("5 - Mappa wrapLista di vari anni")
        //--nome anno
        //--typeLista
    void mappaWrap2(final String nomeAnno, final AETypeLista type) {
        System.out.println("5 - Mappa wrapLista di vari anni");
        sorgente = nomeAnno;
        int numVoci;

        if (!valido(nomeAnno, type)) {
            return;
        }

        mappaWrap = switch (type) {
            case annoNascita -> appContext.getBean(ListaAnni.class).nascita(sorgente).mappaWrap();
            case annoMorte -> appContext.getBean(ListaAnni.class).morte(sorgente).mappaWrap();
            default -> null;
        };

        if (mappaWrap != null && mappaWrap.size() > 0) {
            numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
            message = String.format("Ci sono %d wrapLista che implementano l'anno di %s %s", numVoci, type.getGiornoAnno(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printMappaWrap(mappaWrap);
        }
        else {
            message = "La mappa è nulla";
            System.out.println(message);
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