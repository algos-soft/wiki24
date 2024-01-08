package it.algos.wiki24.basetest;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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

    protected List<WrapDidascalia> listaWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;

    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("20 marzo", TypeLista.giornoNascita),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte)
        );
    }

    //--nome anno
    //--typeCrono
    protected static Stream<Arguments> ANNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.annoNascita),
                Arguments.of(VUOTA, TypeLista.annoMorte),
                Arguments.of("2002", TypeLista.annoMorte),
                Arguments.of("37 a.C.", TypeLista.annoNascita),
                Arguments.of("37 a.C.", TypeLista.annoMorte),
                Arguments.of("37 A.C.", TypeLista.annoMorte),
                Arguments.of("4 gennaio", TypeLista.annoNascita),
                Arguments.of("1985", TypeLista.nazionalitaSingolare),
                Arguments.of("1º gennaio", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.giornoNascita),
                Arguments.of("406 a.C.", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.annoNascita),
                Arguments.of("1984", TypeLista.annoNascita),
                Arguments.of("560", TypeLista.annoMorte)
        );
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
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d wrap", max);
            System.out.println(message);
            System.out.println(VUOTA);

            printWrapBase(wrap.subList(iniA, endA), iniA, sorgente);
            System.out.println(TRE_PUNTI);
            printWrapBase(wrap.subList(iniB, endB), iniB, sorgente);
        }
    }

    protected void printWrapBase(List<WrapDidascalia> listaWrap, final int inizio, String sorgente) {
        int cont = inizio;

        for (WrapDidascalia wrap : listaWrap) {
            printWrap(wrap, sorgente);
            System.out.println(SPAZIO);
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

    protected boolean validoGiornoNato(final String nomeGiorno, final TypeLista type) {
        return validoGiorno(nomeGiorno, type, TypeLista.giornoNascita);
    }


    protected boolean validoGiornoMorto(final String nomeGiorno, final TypeLista type) {
        return validoGiorno(nomeGiorno, type, TypeLista.giornoMorte);
    }


    protected boolean validoGiorno(final String nomeGiorno, final TypeLista typeOttenuto, final TypeLista typePrevisto) {
        if (textService.isEmpty(nomeGiorno)) {
            System.out.println("Manca il nome del giorno");
            return false;
        }

        if (typeOttenuto != typePrevisto) {
            message = String.format("Il type 'TypeLista.%s' indicato è incompatibile con metodo [%s]", typeOttenuto, "nomeGiorno");
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
            message = String.format("Il type 'TypeLista.%s' indicato è incompatibile con metodo [%s]", typeOttenuto, "nomeAnno");
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

}
