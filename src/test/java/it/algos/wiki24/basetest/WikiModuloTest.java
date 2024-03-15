package it.algos.wiki24.basetest;

import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.packages.crono.anno.*;
import it.algos.vbase.backend.packages.crono.giorno.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 15:31
 */
public abstract class WikiModuloTest extends ModuloTest {

    @Inject
    protected BioMongoModulo modulo;

    protected List<BioMongoEntity> listaBio;

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> GIORNI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte)
        );
    }

    //--nome giorno
    //--typeCrono
    protected static Stream<Arguments> ANNI_LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("1857", TypeLista.annoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte),
                Arguments.of("1425", TypeLista.annoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte)
        );
    }

    @Test
    @Order(50)
    @DisplayName("50 - resetStartup")
    void resetStartup() {
        System.out.println("50 - resetStartup");
        System.out.println(VUOTA);
        System.out.println("Non eseguita per questo modulo");
    }


    @Test
    @Order(60)
    @DisplayName("60 - resetDelete")
    void resetDelete() {
        System.out.println("60 - resetDelete");
        System.out.println(VUOTA);
        System.out.println("Non eseguita per questo modulo");
    }


    @Test
    @Order(70)
    @DisplayName("70 - resetAdd")
    void resetAdd() {
        System.out.println("70 - resetAdd");
        System.out.println(VUOTA);
        System.out.println("Non eseguita per questo modulo");
    }


    protected boolean validoGiorno(final String nomeGiorno, final TypeLista type) {
        if (textService.isEmpty(nomeGiorno)) {
            System.out.println("Manca il nome del giorno");
            return false;
        }

        if (type != TypeLista.giornoNascita && type != TypeLista.giornoMorte) {
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

    protected boolean validoAnno(final String nomeAnno, final TypeLista type) {
        if (textService.isEmpty(nomeAnno)) {
            System.out.println("Manca il nome del anno");
            return false;
        }

        if (type != TypeLista.annoNascita && type != TypeLista.annoMorte) {
            message = String.format("Il type 'TypeLista.%s' indicato è incompatibile con la classe [%s]", type, "nomeAnno");
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


    protected void fixListaBio(final String sorgente, final List<BioMongoEntity> listaBio) {
        if (listaBio != null && listaBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano la lista %s", listaBio.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listaBio);
        }
        else {
            message = "La listaBio è nulla";
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
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
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

}