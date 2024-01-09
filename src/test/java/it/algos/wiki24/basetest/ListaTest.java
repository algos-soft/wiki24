package it.algos.wiki24.basetest;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 17:34
 */
public abstract class ListaTest extends WikiTest {

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    protected CrudModulo currentModulo;

    protected TypeLista currentType;

    @Inject
    protected QueryService queryService;

    protected Stream<Arguments> streamCollection;

    protected List<WrapDidascalia> listaWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;

    //--nome giorno
    //--typeCrono per il test
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("20 marzo", TypeLista.giornoNascita),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte)
        );
    }

    //--nome anno
    //--typeCrono per il test
    protected static Stream<Arguments> ANNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.annoNascita),
                Arguments.of(VUOTA, TypeLista.annoMorte),
                Arguments.of("2002", TypeLista.annoMorte),
                Arguments.of("38 a.C.", TypeLista.annoNascita),
                Arguments.of("38 a.C.", TypeLista.annoMorte),
                Arguments.of("38 A.C.", TypeLista.annoNascita),
                Arguments.of("4 gennaio", TypeLista.annoNascita),
                Arguments.of("1985", TypeLista.nazionalitaSingolare),
                Arguments.of("1º gennaio", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.giornoNascita),
                Arguments.of("406 a.C.", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.annoNascita),
                Arguments.of("560", TypeLista.annoMorte)
        );
    }

    //--nome giorno
    //--typeCrono per il test
    protected Stream<Arguments> getListeStream() {
        return null;
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
        this.fixCheckIniziale();
    }

//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(101)
    @DisplayName("101 - listaBio")
    void listaBio(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("101 - listaBio"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        listaBio = ((Lista) appContext.getBean(clazz, nomeLista)).listaBio();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaBio);
            return;
        }
        assertNotNull(listaBio);
        message = String.format("Lista delle [%d] biografie di type%s[%s] per il giorno [%s]", listaBio.size(), FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        printBioLista(listaBio);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(201)
    @DisplayName("201 - listaWrapDidascalie")
    void listaWrapDidascalie(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("201 - listaWrapDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        listaWrap = ((Lista) appContext.getBean(clazz, nomeLista)).listaWrapDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaWrap);
            return;
        }
        assertNotNull(listaWrap);
        message = String.format("Lista dei [%d] wrap di type%s[%s] per il giorno [%s]", listaWrap.size(), FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        printWrapDidascalie(listaWrap, sorgente);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(301)
    @DisplayName("301 - listaTestoDidascalia")
    void listaTestoDidascalia(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("301 - listaTestoDidascalia"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        listaStr = ((Lista) appContext.getBean(clazz, nomeLista)).listaTestoDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("Lista delle [%d] didascalie di type%s[%s] per il giorno [%s]", listaStr.size(), FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(401)
    @DisplayName("401 - mappaDidascalie")
    void mappaDidascalie(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("401 - mappaDidascalie"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        mappaDidascalie = ((Lista) appContext.getBean(clazz, nomeLista)).mappaDidascalie();

        if (textService.isEmpty(nomeLista)) {
            assertNull(mappaDidascalie);
            return;
        }
        assertNotNull(mappaDidascalie);
        printMappa(typeSuggerito.getTag(), nomeLista, mappaDidascalie);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(501)
    @DisplayName("501 - key della mappa")
    void keyMappa(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("501 - key della mappa (paragrafi)"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        listaStr = ((Lista) appContext.getBean(clazz, nomeLista)).keyMappa();

        if (textService.isEmpty(nomeLista)) {
            assertNull(listaStr);
            return;
        }
        assertNotNull(listaStr);
        message = String.format("La mappa della lista di type%s[%s] per il giorno [%s] ha %d chiavi (paragrafi)", FORWARD, typeSuggerito.name(), nomeLista, listaStr.size());
        System.out.println(message);
        System.out.println(VUOTA);
        print(listaStr);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(601)
    @DisplayName("601 - paragrafiSenzaDimensioni")
    void paragrafiSenzaDimensioni(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("601 - paragrafiSenzaDimensioni"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).nonUsaDimensioneParagrafi().testoBody();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per il giorno [%s]", FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(701)
    @DisplayName("701 - paragrafiDimensionati")
    void paragrafiDimensionati(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("701 - paragrafiDimensionati"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).testoBody();

        if (textService.isEmpty(ottenuto)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi dimensionati della lista di type%s[%s] per il giorno [%s]", FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


//    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(801)
    @DisplayName("801 - paragrafiElaborati")
    void paragrafiElaborati(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("801 - paragrafiElaborati"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).testoBody();

        if (textService.isEmpty(ottenuto)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Paragrafi della lista di type%s[%s] per il giorno [%s] con eventuali sottopagine e divisori colonne", FORWARD, typeSuggerito.name(), nomeLista);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    protected void fixCheckIniziale() {
        System.out.println("0 - Check iniziale dei parametri necessari per il test");

        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe [%s] nel metodo setUpAll() e prima di invocare super.setUpAll() ", clazzTestName));

        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "clazz", clazzTestName);
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }
        message = String.format("Il flag '%s' è previsto e regolato nel metodo setUpAll() della classe [%s]", "clazz", clazzName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        if (textService.isEmpty(clazzName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "clazzName", clazzTestName);
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }
        message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() della classe [%s]", "clazzName", clazzTestName, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe [%s] nel metodo setUpAll() e dopo aver invocato super.setUpAll() ", clazzTestName));

        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto", ammessoCostruttoreVuoto, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));

        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "istanzaValidaSubitoDopoCostruttore", istanzaValidaSubitoDopoCostruttore, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
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


    protected boolean validoGiornoAnno(final String nomeLista, final TypeLista typeSuggerito) {
        if (textService.isEmpty(nomeLista)) {
            message = String.format("Manca il nome del giorno/anno per un'istanza di type%s[%s]", FORWARD, currentType.name());
            System.out.println(message);
            return false;
        }

        if (currentModulo.findByKey(nomeLista) == null) {
            message = String.format("Il giorno/anno [%s] indicato NON esiste per un'istanza di type%s[%s]", nomeLista, FORWARD, currentType.name());
            System.out.println(message);
            return false;
        }

        if (currentType != typeSuggerito) {
            message = String.format("Il type suggerito%s[%s] è incompatibile per un'istanza che prevede type%s[%s]", FORWARD, typeSuggerito, FORWARD, currentType);
            System.out.println(message);
            return false;
        }

        return true;
    }


    protected boolean validoAnnoNato(final String nomeAnno, final TypeLista type) {
        return validoAnno(nomeAnno, type, TypeLista.annoNascita);
    }


    protected boolean validoAnnoMorto(final String nomeAnno, final TypeLista type) {
        return validoAnno(nomeAnno, type, TypeLista.annoMorte);
    }


    protected boolean validoAnno(final String nomeAnno, final TypeLista typeOttenuto, final TypeLista typePrevisto) {
        if (textService.isEmpty(nomeAnno)) {
            System.out.println("Manca il nome dell'anno");
            return false;
        }

        if (typeOttenuto != typePrevisto) {
            message = String.format("Il type indicato%s[%s] è incompatibile col type previsto%s[%s]", FORWARD, typeOttenuto, FORWARD, typePrevisto);
            System.out.println(message);
            return false;
        }

        if (annoModulo.findByKey(nomeAnno) == null) {
            message = String.format("L'anno [%s] indicato NON esiste", nomeAnno);
            System.out.println(message);
            return false;
        }

        return true;
    }

    protected void printBodyLista(final String bodyText) {
        if (textService.isEmpty(bodyText)) {
            System.out.println("Manca il testo da stampare");
            return;
        }
    }

}
