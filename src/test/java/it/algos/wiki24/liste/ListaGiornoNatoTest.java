package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 17:33
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("lista")
@DisplayName("Lista giorno nato")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaGiornoNatoTest extends ListaTest {

    @Inject
    GiornoModulo giornoModulo;

    private ListaGiornoNato istanza;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ListaGiornoNato.class;
        super.setUpAll();
        super.ammessoCostruttoreVuoto = false;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }

    //    @Test
    @Order(11)
    @DisplayName("11 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("11 - Costruttore base senza parametri"));
        System.out.println(VUOTA);

        //        istanza = new ListaGiornoNato();
        assertNotNull(istanza);
        message = String.format("Costruttore per un'istanza di %s, costruita con 'new ListaGiorni'", istanza.getClass().getSimpleName());
        System.out.println(message);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(101)
    @DisplayName("101 - listaBio")
    //--nome giorno
    //--typeCrono
    void listaBio(String nomeGiorno, TypeLista type) {
        System.out.println(("101 - listaBio"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaBio = appContext.getBean(ListaGiornoNato.class, sorgente).listaBio();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaBio);
            return;
        }
        assertNotNull(listaBio);
        printBioLista(listaBio);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(201)
    @DisplayName("201 - listaWrapDidascalie")
    //--nome giorno
    //--typeCrono
    void listaWrapDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("201 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaWrap = appContext.getBean(ListaGiornoNato.class, sorgente).listaWrapDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        printWrapDidascalie(listaWrap, sorgente);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(301)
    @DisplayName("301 - listaTestoDidascalia")
    //--nome giorno
    //--typeCrono
    void listaTestoDidascalia(String nomeGiorno, TypeLista type) {
        System.out.println(("301 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoNato.class, sorgente).listaTestoDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        print(listaStr);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(401)
    @DisplayName("401 - mappaDidascalie")
    //--nome giorno
    //--typeCrono
    void mappaDidascalie(String nomeGiorno, TypeLista type) {
        System.out.println(("401 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        mappaDidascalie = appContext.getBean(ListaGiornoNato.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("nati", sorgente, mappaDidascalie);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "GIORNI_LISTA")
    //    @Order(40)
    //    @DisplayName("40 - Key della mappaWrap STANDARD")
    //    void mappaWrapKey(final String nomeLista, final AETypeLista type) {
    //        if (!valido(nomeLista, type)) {
    //            return;
    //        }
    //
    //        mappaWrap = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(type).mappaWrap();
    //        super.fixMappaWrapKey(nomeLista, mappaWrap);
    //    }

//    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(501)
    @DisplayName("501 - key della mappa")
        //--nome giorno
        //--typeCrono
    void keyMappa(String nomeGiorno, TypeLista type) {
        System.out.println(("501 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        listaStr = appContext.getBean(ListaGiornoNato.class, sorgente).keyMappa();
        if (textService.isEmpty(sorgente)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("La mappa della lista [%s] ha %d chiavi (paragrafi)", sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


//    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(601)
    @DisplayName("601 - paragrafi")
        //--nome giorno
        //--typeCrono
    void paragrafi(String nomeGiorno, TypeLista type) {
        System.out.println(("601 - paragrafi"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafi();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista [%s]", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(701)
    @DisplayName("701 - paragrafiDimensionati")
        //--nome giorno
        //--typeCrono
    void paragrafiDimensionati(String nomeGiorno, TypeLista type) {
        System.out.println(("701 - paragrafiDimensionati"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafiDimensionati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi dimensionati della lista [%s]", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "GIORNI")
    @Order(801)
    @DisplayName("801 - paragrafiElaborati")
        //--nome giorno
        //--typeCrono
    void paragrafiElaborati(String nomeGiorno, TypeLista type) {
        System.out.println(("801 - paragrafiElaborati"));
        System.out.println(VUOTA);
        if (!validoGiornoNato(nomeGiorno, type)) {
            return;
        }
        sorgente = nomeGiorno;

        ottenuto = appContext.getBean(ListaGiornoNato.class, sorgente).paragrafiElaborati();
        if (textService.isEmpty(ottenuto)) {
            assertTrue(textService.isEmpty(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista [%s] con eventuali sottopagine e divisori colonne", sorgente);
        System.out.println(message);
        System.out.println(ottenuto);
    }

    //    @Test
    @Order(1401)
    @DisplayName("1401 - mappaDidascalie")
    void mappaDidascalie2() {
        System.out.println(("1401 - mappaDidascalie"));
        System.out.println(VUOTA);
        sorgente = "1º gennaio";
        sorgente = "29 febbraio";
        sorgente = "20 marzo";

        mappaDidascalie = appContext.getBean(ListaGiornoNato.class, sorgente).mappaDidascalie();
        if (textService.isEmpty(sorgente)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa("nati", sorgente, mappaDidascalie);
    }


    protected void printMappa(String tipo, String nome, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappa) {
        if (mappa == null || mappa.size() == 0) {
            message = String.format("La mappa di didascalie per la lista [%s] è vuota", sorgente);
            System.out.println(message);
            return;
        }

        message = String.format("Ci sono [%s] suddivisioni (ordinate) di 1° livello (paragrafi) per la mappa didascalie dei %s il [%s]", mappa.size(), tipo, nome);
        System.out.println(message);
        for (String primoLivello : mappa.keySet()) {
            System.out.println(primoLivello);

            for (String secondoLivello : mappa.get(primoLivello).keySet()) {
                System.out.print(TAB);
                System.out.println(textService.isValid(secondoLivello) ? secondoLivello : NULLO);

                for (String terzoLivello : mappa.get(primoLivello).get(secondoLivello).keySet()) {
                    System.out.print(TAB);
                    System.out.print(TAB);
                    System.out.println(textService.isValid(terzoLivello) ? terzoLivello : NULLO);

                    for (String didascalia : mappa.get(primoLivello).get(secondoLivello).get(terzoLivello)) {
                        System.out.print(TAB);
                        System.out.print(TAB);
                        System.out.print(TAB);
                        System.out.println(didascalia);
                    }
                }
            }

        }


    }


    protected boolean validoGiornoNato(final String nomeGiorno, final TypeLista type) {
        if (textService.isEmpty(nomeGiorno)) {
            System.out.println("Manca il nome del giorno");
            return false;
        }

        if (type != TypeLista.giornoNascita) {
            message = String.format("Il type 'TypeLista.%s' indicato è incompatibile con metodo [%s]", type, "nomeGiorno");
            System.out.println(message);
            return false;
        }

        if (giornoModulo.findByKey(nomeGiorno) == null) {
            message = String.format("Il giorno [%s] indicato NON esiste", nomeGiorno);
            System.out.println(message);
            return false;
        }

        return true;
    }

}
