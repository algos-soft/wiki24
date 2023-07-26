package it.algos.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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

    protected String metodoDefault;


    protected void setUpAll() {
        super.setUpAll();
    }


    @Test
    @Order(5)
    @DisplayName("5 - checkParametroNelCostruttore")
    void checkParametroNelCostruttore() {
        sorgente = "...nonEsiste...";

        System.out.println(String.format("5 - checkParametroNelCostruttore"));
        System.out.println(VUOTA);
        System.out.println(String.format("La classe [%s] controlla la validità del parametro '%s' usato nel costruttore", clazzName, PARAMETRO));
        System.out.println(String.format("Controllo nel metodo %s.%s, invocato da  @PostConstruct", clazzName, CHECK));
        System.out.println(String.format("Funzione%s%s.%s(%s)", FORWARD, backendClazzName, FUNZIONE, sorgente));

        fixCheckParametroNelCostruttore(sorgente);
    }


    void fixCheckParametroNelCostruttore(String valore) {
        Lista istanza = null;
        boolean isCostruttoreValido;

        try {
            istanza = (Lista) appContext.getBean(clazz, valore);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
        }
        assertNotNull(istanza);
        isCostruttoreValido = istanza.isCostruttoreValido();

        if (isCostruttoreValido) {
            System.out.println(String.format("Istanza valida col valore accettabile [%s] del parametro '%s'", valore, PARAMETRO));
        }
        else {
            message = String.format("Istanza NON valida perché il valore [%s] del parametro '%s' non è previsto", valore, PARAMETRO);
            logService.warn(new WrapLog().message(message));
        }
    }

    @Test
    @Order(6)
    @DisplayName("6 - esegueSenzaParametroNelCostruttore")
    void esegueSenzaParametroNelCostruttore() {
        try {
            ((Lista) appContext.getBean(clazz)).listaBio();
        } catch (Exception unErrore) {
            super.fixSenzaParametroNelCostruttore("nomeLista", "listaBio() o listaWrap() o mappaWrap()");
        }
    }

    protected void fixBeanStandard(final String sorgente) {
        Lista istanza = null;
        String nomeParametro = "nomeLista";
        String metodiEseguibili = "listaBio(), listaWrap() e mappaWrap()";
        String metodoDaRegolare = "typeLista()";
        String metodiBuilderPattern = "typeLista(), typeLinkParagrafi(), typeLinkCrono(), icona(), nascita(), morte()";

        istanza = (Lista) appContext.getBean(clazz, sorgente);
        if (istanza.isCostruttoreValido()) {
            super.fixBeanStandard(istanza, sorgente, metodiEseguibili, metodoDaRegolare, metodiBuilderPattern);
            printLista(istanza);
        }
        else {
            super.fixBeanStandardNo(nomeParametro, sorgente, CHECK, FUNZIONE, sorgente, metodiBuilderPattern);
        }

        assertEquals(super.istanzaValidaSubitoDopoCostruttore, istanza.isValida());
    }

    protected void fixConParametroNelCostruttore(String sorgente) {
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

        istanzaEffettivamenteValida = istanza.isValida();
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
        if (istanza == null) {
            return;
        }
        assertNotNull(istanza);

        if (!istanza.isCostruttoreValido()) {
            message = String.format("Il valore '%s' non è accettabile per un'istanza di classe [%s]", istanza.nomeLista, clazzName);
            logService.warn(new WrapLog().message(message));
            return;
        }
        System.out.println(VUOTA);

        if (istanza.isValida()) {
            if (istanzaValidaSubitoDopoCostruttore) {
                if (textService.isEmpty(metodoEseguito)) {
                    System.out.println(String.format("L'istanza è immediatamente eseguibile dopo il costruttore anche senza nessun metodo BuilderPattern"));
                    System.out.println(String.format("Debug%s%s", FORWARD, sorgente));
                    System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, true));
                    System.out.println(String.format("metodo BuilderPattern Eseguito%s%s", FORWARD, "(default) " + metodoDefault));
                }
                else {
                    System.out.println(String.format("L'stanza era eseguibile anche senza il metodo '%s'", metodoEseguito));
                    System.out.println(String.format("Debug%s%s", FORWARD, sorgente));
                    System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, true));
                    System.out.println(String.format("metodo BuilderPattern Eseguito%s%s", FORWARD, metodoEseguito));
                }
            }
            else {
                assertTrue(istanza.isValida());
                System.out.println(String.format("La chiamata del metodo '%s' rende utilizzabile l'istanza", metodoEseguito));
                System.out.println(String.format("Debug%s%s", FORWARD, sorgente));
                System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
                System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, true));
                System.out.println(String.format("metodo BuilderPattern Eseguito%s%s", FORWARD, metodoEseguito));
            }
        }
        else {
            assertFalse(istanza.isValida());
            if (textService.isEmpty(metodoEseguito)) {
                metodoEseguito = "(nessuno)";
                message = String.format("Senza chiamare nessun metodo builderPattern, l'istanza NON è utilizzabile");
            }
            else {
                message = String.format("Il metodo builderPattern '%s' NON è congruo e l'istanza NON è utilizzabile", metodoEseguito);
            }
            logService.warn(new WrapLog().message(message));

            System.out.println(String.format("Debug%s%s", FORWARD, sorgente));
            System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
            System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
            System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, false));
            System.out.println(String.format("metodo BuilderPattern Eseguito%s%s", FORWARD, metodoEseguito));
        }
    }

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

    //    void fixMappaWrapKey(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        System.out.println(VUOTA);
    //        System.out.println("40 - Key della mappaWrap");
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            message = String.format("La mappaWrap della lista %s ha %d chiavi (paragrafi) per %d didascalie", sorgente, mappaWrap.size(), wikiUtility.getSizeAllWrap(mappaWrap));
    //            System.out.println(message);
    //            printMappaWrapKeyOrder(mappaWrap);
    //        }
    //        else {
    //            message = "La mappaWrap è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        System.out.println(VUOTA);
    //        System.out.println("50 - Mappa STANDARD wrapLista (paragrafi e righe)");
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            message = String.format("La mappaWrap della lista %s ha %d didascalie", sorgente, wikiUtility.getSizeAllWrap(mappaWrap));
    //            System.out.println(message);
    //            printMappaDidascalie(mappaWrap);
    //        }
    //        else {
    //            message = "La mappaWrap è nulla";
    //            System.out.println(message);
    //        }
    //    }

    protected void printLista(Lista listaEntityBean) {
        if (listaEntityBean == null) {
            return;
        }
        message = String.format("Valori STANDARD per un'istanza di [%s], creata con il solo '%s'", listaEntityBean.getClass().getSimpleName(), "nomeLista");
        if (listaEntityBean.isValida()) {
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
