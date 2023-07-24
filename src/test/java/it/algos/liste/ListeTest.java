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

    protected void setUpAll() {
        super.setUpAll();
    }


    @Test
    @Order(5)
    @DisplayName("5 - esegueSenzaParametroNelCostruttore")
    void esegueSenzaParametroNelCostruttore() {
        try {
            ((Lista) appContext.getBean(clazz)).listaBio();
        } catch (Exception unErrore) {
            super.fixSenzaParametroNelCostruttore("nomeLista", "listaBio() o listaWrap() o mappaWrap()");
        }
    }

    protected void fixConParametroNelCostruttore(String sorgente) {
        long inizio = System.currentTimeMillis();
        List<Bio> listaBio = null;
        Lista istanza = null;
        boolean istanzaEffettivamenteValida;
        boolean listaBioCreata = false;

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
                listaBioCreata = true;
            } catch (Exception unErrore) {
                assertNull(listaBio);
                logService.error(new WrapLog().exception(new AlgosException(unErrore)));
            }
        }

        super.fixConParametroNelCostruttore("nomeLista", "listaBio", "nascita() o morte()", istanzaEffettivamenteValida, inizio);

        System.out.println(VUOTA);
        System.out.println("Debug");
        System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
        System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, istanzaEffettivamenteValida));
        System.out.println(String.format("listaBioCreata%s%s", FORWARD, listaBioCreata));
    }

    protected void fixBeanStandard(final String sorgente) {
        Lista istanza = (Lista) appContext.getBean(clazz, sorgente);
        super.fixBeanStandard(istanza, "nomeLista", "listaBio(), listaWrap() e mappaWrap()", "typeLista");
        assertEquals(super.istanzaValidaSubitoDopoCostruttore, istanza.isValida());
        printLista(istanza);
    }


    protected void fixConParametroNelCostruttore() {
        super.fixConParametroNelCostruttore("nomeLista", "listaBio", "nascita() o morte()");
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
        System.out.println(VUOTA);
        System.out.println("20 - WrapLista STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

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
        System.out.println(VUOTA);
        System.out.println("30 - Lista STANDARD didascalie con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");

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


    void fixMappaWrapKey(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        System.out.println(VUOTA);
        System.out.println("40 - Key della mappaWrap");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("La mappaWrap della lista %s ha %d chiavi (paragrafi) per %d didascalie", sorgente, mappaWrap.size(), wikiUtility.getSizeAllWrap(mappaWrap));
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappaWrap è nulla";
            System.out.println(message);
        }
    }

    void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        System.out.println(VUOTA);
        System.out.println("50 - Mappa STANDARD wrapLista (paragrafi e righe)");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("La mappaWrap della lista %s ha %d didascalie", sorgente, wikiUtility.getSizeAllWrap(mappaWrap));
            System.out.println(message);
            printMappaDidascalie(mappaWrap);
        }
        else {
            message = "La mappaWrap è nulla";
            System.out.println(message);
        }
    }

    protected void printLista(Lista listaEntityBean) {
        if (listaEntityBean == null) {
            return;
        }
        message = String.format("Valori STANDARD per un'istanza di [%s], creata con il solo '%s'", listaEntityBean.getClass().getSimpleName(), "nomeLista");
        if (listaEntityBean.isValida()) {
            message += String.format("%sPronta per essere utilizzata.", SEP);
        }
        else {
            message += String.format("%sNon ancora utilizzabile.", SEP);
        }
        System.out.println(message);
        System.out.println(VUOTA);

        System.out.println(String.format("%s%s%s", "nomeLista: [fissato col costruttore]", FORWARD, listaEntityBean.nomeLista));
        System.out.println(String.format("%s%s%s", "typeLista: [regolato in fixPreferenze()]", FORWARD, listaEntityBean.typeLista != null ? listaEntityBean.typeLista : OBBLIGATORIO));
        System.out.println(String.format("%s%s%s", "typeLinkParagrafi: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.typeLinkParagrafi));
        System.out.println(String.format("%s%s%s", "typeLinkCrono: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.typeLinkCrono));
        System.out.println(String.format("%s%s%s", "paragrafoAltre", FORWARD, listaEntityBean.paragrafoAltre));
        System.out.println(String.format("%s%s%s", "usaIcona: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, listaEntityBean.usaIcona));
        System.out.println(String.format("%s%s%s", "listaNomiSingoli", FORWARD, listaEntityBean.listaNomiSingoli != null ? listaEntityBean.listaNomiSingoli : FACOLTATIVO));
    }

}
