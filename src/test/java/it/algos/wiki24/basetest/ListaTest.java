package it.algos.wiki24.basetest;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
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
public abstract class ListaTest extends WikiStreamTest {

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    //    protected CrudModulo currentModulo;
    //
    //    protected TypeLista currentType;

    @Inject
    protected QueryService queryService;

    protected Stream<Arguments> streamCollection;

    protected List<WrapDidascalia> listaWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;


    //--nome giorno/anno
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
        super.usaCollectionName = false;
        super.usaCurrentModulo = true;
        super.usaTypeLista = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
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
        super.fixCheckParametroNelCostruttore(PARAMETRO, "...nonEsiste...", CHECK, FUNZIONE);
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(101)
    @DisplayName("101 - numBio")
    void numBio(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("101 - numBio"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenutoIntero = ((Lista) appContext.getBean(clazz, nomeLista)).numBio();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(ottenutoIntero > 0);
            return;
        }
        if (ottenutoIntero > 0) {
            ottenuto = textService.format(ottenutoIntero);
            message = String.format("Le biografie di type%s[%s] per %s [%s] sono [%s]", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista, ottenuto);
            System.out.println(message);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, typeSuggerito);
        }
    }

        @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(102)
    @DisplayName("102 - listaBio")
    void listaBio(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("102 - listaBio"));
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
        if (listaBio.size() > 0) {
            message = String.format("Lista delle [%d] biografie di type%s[%s] per %s [%s]", listaBio.size(), FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listaBio);
        }
        else {
            printMancanoBio("La listaBio", nomeLista, typeSuggerito);
        }
    }


        @ParameterizedTest
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
        if (listaWrap.size() > 0) {
            message = String.format("Lista dei [%d] wrap di type%s[%s] per %s [%s]", listaWrap.size(), FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            printWrapDidascalie(listaWrap, sorgente);
        }
        else {
            printMancanoBio("La listaWrap", nomeLista, typeSuggerito);
        }
    }


        @ParameterizedTest
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
        if (listaStr.size() > 0) {
            message = String.format("Lista delle [%d] didascalie di type%s[%s] per %s [%s]", listaStr.size(), FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le lista delle didascalie", nomeLista, typeSuggerito);
        }
    }


        @ParameterizedTest
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
        if (mappaDidascalie.size() > 0) {
            printMappa(typeSuggerito.getTag(), nomeLista, mappaDidascalie);
        }
        else {
            printMancanoBio("La mappa delle didascalie", nomeLista, typeSuggerito);
        }
    }


        @ParameterizedTest
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
        if (listaStr != null) {
            message = String.format("La mappa della lista di type%s[%s] per %s [%s] ha %d chiavi (paragrafi)", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista, listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            printMancanoBio("Le mappa della lista", nomeLista, typeSuggerito);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(601)
    @DisplayName("601 - nonUsaDimensioneParagrafi")
    void nonUsaDimensioneParagrafi(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("601 - nonUsaDimensioneParagrafi"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).nonUsaDimensioneParagrafi().bodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s]", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi senza dimensioni");
            System.out.println(VUOTA);
            System.out.println(ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, typeSuggerito);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(701)
    @DisplayName("701 - nonUsaSottoPagina")
    void nonUsaSottoPagina(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("701 - nonUsaSottoPagina"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).nonUsaSottoPagina().bodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi dimensionati della lista di type%s[%s] per %s [%s]", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi senza sottopagine");
            System.out.println(VUOTA);
            System.out.println(ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, typeSuggerito);
        }
    }


    //        @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(801)
    @DisplayName("801 - nonUsaIncludeNeiParagrafi")
    void nonUsaIncludeNeiParagrafi(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("801 - nonUsaIncludeNeiParagrafi"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).nonUsaIncludeNeiParagrafi().bodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s] con eventuali sottopagine e divisori colonne", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi senza includeOnly");
            System.out.println(ottenuto);
        }
        else {
            printMancanoBio("Il testoBody della lista", nomeLista, typeSuggerito);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "getListeStream()")
    @Order(901)
    @DisplayName("901 - paragrafi")
    void paragrafi(String nomeLista, TypeLista typeSuggerito) {
        System.out.println(("901 - paragrafi"));
        System.out.println(VUOTA);
        if (!validoGiornoAnno(nomeLista, typeSuggerito)) {
            return;
        }

        ottenuto = ((Lista) appContext.getBean(clazz, nomeLista)).bodyText();

        if (textService.isEmpty(nomeLista)) {
            assertFalse(textService.isValid(ottenuto));
            return;
        }
        if (textService.isValid(ottenuto)) {
            message = String.format("Paragrafi della lista di type%s[%s] per %s [%s] con eventuali sottopagine e divisori colonne", FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
            System.out.println(message);
            System.out.println("Paragrafi(if) normali con dimensioni(if), sottopagine(if) e include(if)");
            message = String.format("Paragrafi(if)%sPagine con più di %s voci", FORWARD, 50);
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
            printMancanoBio("Il testoBody della lista", nomeLista, typeSuggerito);
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

    protected void printMancanoBio(String manca, String nomeLista, TypeLista typeSuggerito) {
        message = String.format("%s di type%s[%s] per %s [%s] è vuoto/a", manca, FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
        System.out.println(message);
        System.out.println("Probabilmente non ci sono biografie valide");
    }

    //    protected boolean validoGiornoAnno(final String nomeLista, final TypeLista typeSuggerito) {
    //        if (textService.isEmpty(nomeLista)) {
    //            message = String.format("Manca il nome di %s per un'istanza di type%s[%s]", typeSuggerito.getGiornoAnno(), FORWARD, currentType.name());
    //            System.out.println(message);
    //            return false;
    //        }
    //
    //        if (currentModulo.findByKey(nomeLista) == null) {
    //            message = String.format("%s [%s] indicato NON esiste per un'istanza di type%s[%s]", textService.primaMaiuscola(typeSuggerito.getGiornoAnno()), nomeLista, FORWARD, currentType.name());
    //            System.out.println(message);
    //            return false;
    //        }
    //
    //        if (currentType != typeSuggerito) {
    //            message = String.format("Il type suggerito%s[%s] è incompatibile per un'istanza che prevede type%s[%s]", FORWARD, typeSuggerito, FORWARD, currentType);
    //            System.out.println(message);
    //            return false;
    //        }
    //
    //        return true;
    //    }


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
