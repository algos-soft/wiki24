package it.algos.liste;

import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 22-Jul-2023
 * Time: 13:10
 */
public abstract class ListeTest extends WikiTest {

    protected static int MAX = 5;

    protected static String PARAMETRO = "nomeLista";

    protected static String CHECK = "checkValidita()";

    protected static String FUNZIONE = "isExistByKey";



    protected void setUpAll() {
        super.setUpAll();

        nomeParametro = "nomeLista";
        metodiDaRegolare = "typeLista()";
        metodiBuilderPattern = "typeLista(), typeLinkParagrafi(), typeLinkCrono(), icona()";
        metodiEseguibili = "listaBio(), listaWrap() e mappaWrap()";
    }


    @Test
    @Order(5)
    @DisplayName("5 - senzaParametroNelCostruttore")
    void senzaParametroNelCostruttore() {
        //--prova a costruire un'istanza SENZA parametri e controlla che vada in errore se è obbligatorio avere un parametro
        super.fixSenzaParametroNelCostruttore();
    }

    @Test
    @Order(6)
    @DisplayName("6 - checkParametroNelCostruttore")
    void checkParametroNelCostruttore() {
        //--costruisce un'istanza con un parametro farlocco
        super.fixCheckParametroNelCostruttore(PARAMETRO, "...nonEsiste...", CHECK, FUNZIONE);
    }


    protected void fixBeanStandard(final String valore) {
        //--7 - Istanza della classe [%s] costruita col solo parametro e SENZA altre regolazioni", clazzName
        //--costruisce un'istanza col parametro 'valore'
        super.fixBeanStandard(nomeParametro, valore, metodiEseguibili, metodiDaRegolare, metodiBuilderPattern);
        //        this.debug( valore,"forse","pippoz",true,false);
    }

    protected void fixConParametroNelCostruttore(String sorgente) {
        //--8 - Costruttore con parametro
        long inizio = System.currentTimeMillis();
        List<Bio> listaBio = null;
        Lista istanza = null;
        boolean istanzaEffettivamenteValida;
        boolean metodoEseguito = false;

        try {
            istanza = (Lista) appContext.getBean(clazz, sorgente);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
        }
        assertNotNull(istanza);

        istanzaEffettivamenteValida = istanza.isCostruttoreValido();
        assertEquals(istanzaValidaSubitoDopoCostruttore, istanzaEffettivamenteValida);

        if (istanzaEffettivamenteValida) {
            try {
                listaBio = istanza.listaBio();
                assertNotNull(listaBio);
                metodoEseguito = true;
            } catch (Exception unErrore) {
                assertNull(listaBio);
                logService.error(new WrapLog().exception(new AlgosException(unErrore)));
            }
        }

        super.fixConParametroNelCostruttore("nomeLista", "listaBio", "nascita() o morte()", istanzaEffettivamenteValida, inizio);

        System.out.println(VUOTA);
        System.out.println(String.format("Debug%s%s", FORWARD, sorgente));
        System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
        System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, istanzaEffettivamenteValida));
        System.out.println(String.format("metodoEseguito%s%s", FORWARD, metodoEseguito));
    }

    protected void debug(Lista istanza, String metodoEseguito) {
        debug(istanza, sorgente, metodoEseguito);
    }

    protected void debug(Lista istanza, String keyValue, String metodoEseguito) {
        if (istanza == null) {
            return;
        }
        assertNotNull(istanza);

        if (!istanza.isCostruttoreValido()) {
            message = String.format("Il valore '%s' non è accettabile per un'istanza di classe [%s]", istanza.nomeLista, clazzName);
            logService.warn(new WrapLog().message(message));
            return;
        }

        if (istanza.isPatternCompleto()) {
            if (istanzaValidaSubitoDopoCostruttore) {
                if (textService.isEmpty(metodoEseguito)) {
                    //                    System.out.println(String.format("L'istanza è immediatamente eseguibile dopo il costruttore anche senza nessun metodo BuilderPattern"));
                    System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                    System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                    System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoDefault));
                }
                else {
                    System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                    System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                    System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));
                    System.out.println(String.format("L'istanza era eseguibile anche senza il metodo '%s'", metodoEseguito));
                }
            }
            else {
                assertTrue(istanza.isPatternCompleto());
                System.out.println(String.format("La chiamata del metodo '%s' rende utilizzabile l'istanza", metodoEseguito));
                System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));
            }
        }
        else {
            assertFalse(istanza.isPatternCompleto());
            if (textService.isEmpty(metodoEseguito)) {
                metodoEseguito = "(nessuno)";
                message = String.format("Senza chiamare nessun metodo builderPattern, l'istanza NON è utilizzabile");
            }
            else {
                message = String.format("Il metodo builderPattern '%s' NON è congruo e l'istanza NON è utilizzabile", metodoEseguito);
            }
            logService.warn(new WrapLog().message(message));

            System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
            System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
            System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
            System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, false));
            System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));
        }
    }


    @Deprecated
    protected void fixConParametroNelCostruttore() {
        String nomeParametro = "nomeLista";
        String metodiDaEseguire = "listaBio()";
        String metodoDaRegolare = "nascita() o morte()";
        super.fixConParametroNelCostruttore(nomeParametro, "listaBio", metodoDaRegolare);
    }

    protected void fixListaBio(final String sorgente, final List<Bio> listBio) {
        System.out.println("10 - Lista bio");

        if (listBio != null && listBio.size() > 0) {
            message = String.format("Ci sono %d biografie che implementano la lista %s", listBio.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printBioLista(listBio);
        }
        else {
            message = "La listBio è nulla";
            System.out.println(message);
        }
    }

    protected void fixWrapLista(final String sorgente, final List<WrapLista> listWrapLista) {
        fixWrapLista(sorgente, listWrapLista, "20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
    }

    protected void fixWrapLista(final String sorgente, final List<WrapLista> listWrapLista, String incipit) {
        System.out.println(VUOTA);
        System.out.println(incipit);

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d wrapLista che implementano la lista %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            message = String.format("Faccio vedere una lista delle prime %s", MAX);
            System.out.println(message);
            System.out.println(VUOTA);

            for (WrapLista wrap : listWrapLista.subList(0, Math.min(MAX, listWrapLista.size()))) {
                super.printWrap(wrap, this.textService);
            }
        }
        else {
            message = "La wrapLista è nulla";
            System.out.println(message);
        }
    }

    protected void fixWrapListaDidascalie(final String sorgente, final List<WrapLista> listWrapLista) {
        fixWrapListaDidascalie(sorgente, listWrapLista, "30 - Lista STANDARD didascalie con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
    }

    protected void fixWrapListaDidascalie(final String sorgente, final List<WrapLista> listWrapLista, String incipit) {
        System.out.println(VUOTA);
        System.out.println(incipit);

        if (listWrapLista != null && listWrapLista.size() > 0) {
            message = String.format("Ci sono %d didascalie che implementano la lista %s", listWrapLista.size(), sorgente);
            System.out.println(message);
            message = String.format("Faccio vedere una lista delle prime %s", MAX);
            System.out.println(message);
            System.out.println(VUOTA);

            for (WrapLista wrap : listWrapLista.subList(0, Math.min(MAX, listWrapLista.size()))) {
                System.out.println(wrap.didascalia);
            }
        }
        else {
            message = "La wrapLista è nulla";
            System.out.println(message);
        }
    }




    void fixBuilderPatternListe(Lista istanza,AETypeLista typeListaDefault) {
        super.fixBuilderPattern(istanza, sorgente, VUOTA);

        if (istanzaValidaSubitoDopoCostruttore) {
            assertTrue(istanza.listaBioTest());
            fixBuilderPattern(istanza, sorgente, "listaBio()");

            assertTrue(istanza.listaWrapTest());
            fixBuilderPattern(istanza, sorgente, "listaWrap()");

            assertTrue(istanza.mappaWrapTest());
            fixBuilderPattern(istanza, sorgente, "mappaWrap()");
        }
        else {
            assertFalse(istanza.listaBioTest());
            fixBuilderPattern(istanza, sorgente, "listaBio()");

            assertFalse(istanza.listaWrapTest());
            fixBuilderPattern(istanza, sorgente, "listaWrap()");

            assertFalse(istanza.mappaWrapTest());
            fixBuilderPattern(istanza, sorgente, "mappaWrap()");

            istanza.typeLista(typeListaDefault);

            assertTrue(istanza.listaBioTest());
            fixBuilderPattern(istanza, sorgente, "listaBio()");

            assertTrue(istanza.listaWrapTest());
            fixBuilderPattern(istanza, sorgente, "listaWrap()");

            assertTrue(istanza.mappaWrapTest());
            fixBuilderPattern(istanza, sorgente, "mappaWrap()");
        }

        istanza.typeLista(AETypeLista.nomi);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.nomi)");

        istanza.typeLista(AETypeLista.cognomi);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.cognomi)");

        istanza.typeLista(AETypeLista.giornoNascita);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.giornoNascita)");

        istanza.typeLista(AETypeLista.giornoMorte);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.giornoMorte)");

        istanza.typeLista(AETypeLista.annoNascita);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.annoNascita)");

        istanza.typeLista(AETypeLista.annoMorte);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.annoMorte)");

        istanza.typeLista(AETypeLista.attivitaSingolare);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.attivitaSingolare)");

        istanza.typeLista(AETypeLista.attivitaPlurale);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.attivitaPlurale)");

        istanza.typeLista(AETypeLista.nazionalitaSingolare);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.nazionalitaSingolare)");

        istanza.typeLista(AETypeLista.nazionalitaPlurale);
        fixBuilderPattern(istanza, sorgente, "typeLista(AETypeLista.nazionalitaPlurale)");
    }

//    void fixBuilderPattern(Object istanza, String keyValue, String nomeMetodo) {
//        debug((Lista) istanza, keyValue, nomeMetodo);
//        System.out.println(VUOTA);
//    }

    protected void printLista(Lista listaEntityBean) {
        if (listaEntityBean == null) {
            return;
        }
        message = String.format("Valori STANDARD per un'istanza di [%s], creata con il solo '%s'", listaEntityBean.getClass().getSimpleName(), "nomeLista");
        if (listaEntityBean.isPatternCompleto()) {
            message += String.format("%sPronta per essere utilizzata.", SEP);
        }
        else {
            message += String.format("%sNon utilizzabile.", SEP);
        }
        System.out.println(message);
        System.out.println(VUOTA);

        System.out.println(String.format("%s%s%s", "nomeLista: [fissato nel costruttore]", FORWARD, listaEntityBean.nomeLista));
        System.out.println(String.format("%s%s%s", "typeLista: [regolato in fixPreferenze()]", FORWARD, listaEntityBean.typeLista != null ? listaEntityBean.typeLista : OBBLIGATORIO));
        System.out.println(String.format("%s%s%s", "typeLinkParagrafi: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.typeLinkParagrafi));
        System.out.println(String.format("%s%s%s", "typeLinkCrono: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.typeLinkCrono));
        System.out.println(String.format("%s%s%s", "paragrafoAltre", FORWARD, listaEntityBean.paragrafoAltre));
        System.out.println(String.format("%s%s%s", "usaIcona: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.usaIcona));
        System.out.println(String.format("%s%s%s", "listaNomiSingoli", FORWARD, listaEntityBean.listaNomiSingoli != null ? listaEntityBean.listaNomiSingoli : FACOLTATIVO));
    }

}
