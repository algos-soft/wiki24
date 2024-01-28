package it.algos.wiki24.liste;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 27-Jan-2024
 * Time: 10:05
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Lista giorno/anno nato/morto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaTest extends WikiStreamTest {


    private Lista istanza;

    protected List<WrapDidascalia> listaWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;

    protected LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottopagina;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = Lista.class;
        super.setUpAll();
        super.usaCollectionName = false;
        super.usaCurrentModulo = false;
        super.usaTypeLista = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
        currentModulo = null;
        mappaSottopagina = null;
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
    }

    @Test
    @Order(3)
    @DisplayName("3 - senzaParametroNelCostruttore")
    void senzaParametroNelCostruttore() {
        //--prova a costruire un'istanza SENZA parametri e controlla che vada in errore se è obbligatorio avere un parametro
        super.fixSenzaParametroNelCostruttore();
    }

    @Test
    @Order(4)
    @DisplayName("4 - checkParametroNelCostruttore")
    void checkParametroNelCostruttore() {
        //--costruisce un'istanza con un parametro farlocco
        //        super.fixCheckParametroNelCostruttore(PARAMETRO, "...nonEsiste...", CHECK, FUNZIONE);
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista type) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenutoIntero = appContext.getBean(Lista.class, nomeLista).type(type).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di type%s[%s] per %s [%s] sono [%s]", FORWARD, type.name(), type.getGiornoAnno(), nomeLista, ottenuto);
            System.out.println(message);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, type);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(201)
    @DisplayName("201 - listaBio")
    void listaBio(String nomeLista, TypeLista type) {
        System.out.println(("201 - listaBio"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaBio = appContext.getBean(Lista.class, nomeLista).type(type).listaBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaBio);
            return;
        }
        assertNotNull(listaBio);
        if (listaBio.size() > 0) {
            message = String.format("Lista delle [%d] biografie di type%s[%s] per %s [%s]", listaBio.size(), FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listaBio);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(301)
    @DisplayName("301 - listaWrapDidascalie")
    void listaWrapDidascalie(String nomeLista, TypeLista type) {
        System.out.println(("301 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaWrap = appContext.getBean(Lista.class, nomeLista).type(type).listaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        if (listaWrap.size() > 0) {
            message = String.format("Lista dei [%d] wrap di type%s[%s] per %s [%s]", listaWrap.size(), FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapDidascalie(listaWrap, sorgente);
        }
        else {
            printMancanoBio("La listaWrap", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(401)
    @DisplayName("401 - listaTestoDidascalia")
    void listaTestoDidascalia(String nomeLista, TypeLista type) {
        System.out.println(("401 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaTestoDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        if (listaStr.size() > 0) {
            message = String.format("Lista delle [%d] didascalie di type%s[%s] per %s [%s]", listaStr.size(), FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le lista delle didascalie", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(501)
    @DisplayName("501 - mappaDidascalie")
    void mappaDidascalie(String nomeLista, TypeLista type) {
        System.out.println(("501 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        mappaDidascalie = appContext.getBean(Lista.class, nomeLista).type(type).mappaDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        if (mappaDidascalie.size() > 0) {
            printMappa(type.getTag(), nomeLista, mappaDidascalie);
        }
        else {
            printMancanoBio("La mappa delle didascalie", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(601)
    @DisplayName("601 - key della mappa")
    void keyMappa(String nomeLista, TypeLista type) {
        System.out.println(("601 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).keyMappa();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d chiavi (paragrafi)", FORWARD, type.name(), type.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, type);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA")
    @Order(701)
    @DisplayName("701 - paragrafi")
    void paragrafi(String nomeLista, TypeLista type) {
        System.out.println(("701 - paragrafi"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        ottenuto = appContext.getBean(Lista.class, nomeLista).type(type).bodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s] con eventuali sottopagine e divisori colonne", FORWARD, type.name(), type.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi(if) normali con dimensioni(if), sottopagine(if) e include(if)");
            message = String.format("Paragrafi(if)%sPagine con più di %s voci ed almeno %s paragrafi (più di %s)", FORWARD, 50, 3, 2);
            System.out.println(message);
            message = String.format("Dimensioni(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            message = String.format("Sottopagine(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            message = String.format("Include(if)%sPagine giorni/anni con meno di %s voci", FORWARD, 200);
            System.out.println(message);
            System.out.println(VUOTA);
            System.out.println(ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, type);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(801)
    @DisplayName("801 - listaSottopagine")
    void listaSottopagine(String nomeLista, TypeLista type) {
        System.out.println(("801 - listaSottopagine"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottopagine();
        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null && listaStr.size() > 0) {
            print(listaStr);
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(901)
    @DisplayName("901 - testoSottopagina")
    void testoSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("901 - testoSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottopagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                ottenuto = appContext.getBean(Lista.class, nomeLista).type(type).getTestoSottopagina(keySottopagina);
                assertTrue(textService.isValid(ottenuto));
                System.out.println(ottenuto);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    //        @ParameterizedTest
    @MethodSource(value = "LISTA_TEST")
    @Order(902)
    @DisplayName("902 - mappaSottopagina")
    void mappaSottopagina(String nomeLista, TypeLista type) {
        System.out.println(("902 - mappaSottopagina"));
        System.out.println(VUOTA);
        if (!fixGiornoAnno(nomeLista, type)) {
            return;
        }

        listaStr = appContext.getBean(Lista.class, nomeLista).type(type).listaSottopagine();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        if (listaStr != null && listaStr.size() > 0) {
            for (String keySottopagina : listaStr) {
                mappaSottopagina = appContext.getBean(Lista.class, nomeLista).type(type).getMappaSottopagina(keySottopagina);
            }
        }
        else {
            message = String.format("Non ci sono sottopagine nella lista [%s] di type [%s]", nomeLista, type.name());
            System.out.println(message);
        }
    }


    protected void printBioLista(List<BioMongoEntity> listaBio) {
        String message;
        int max = 10;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie su un totale di %s", max, listaBio.size());
            System.out.println(message);
            message = "Ordinate per forzaOrdinamento";
            System.out.println(message);
            message = "Ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBase(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBase(listaBio.subList(iniB, endB), iniB);
        }
    }


    protected void printBioBase(List<BioMongoEntity> listaBio, final int inizio) {
        int cont = inizio;

        for (BioMongoEntity bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(bio.ordinamento != null ? textService.setQuadre(bio.ordinamento) : VUOTA);
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

    protected void printWrapDidascalie(List<WrapDidascalia> wrap, String sorgente) {
        String message;
        int max = 10;
        int tot = wrap.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (wrap != null) {
            message = String.format("Faccio vedere una lista dei primi e degli ultimi %d wrap", max);
            System.out.println(message);

            printWrapBase(wrap.subList(iniA, endA), iniA, sorgente);
            System.out.println(TRE_PUNTI);
            printWrapBase(wrap.subList(iniB, endB), iniB, sorgente);
        }
    }

    protected void printWrapBase(List<WrapDidascalia> listaWrap, final int inizio, String sorgente) {
        int cont = inizio;

        for (WrapDidascalia wrap : listaWrap) {
            printWrap(wrap, sorgente);
        }
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

}
