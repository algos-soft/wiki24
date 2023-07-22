package it.algos.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
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


    protected void fixSenzaParametroNelCostruttore() {
        System.out.println(("5 - listaBioSenzaParametroNelCostruttore"));
        System.out.println(VUOTA);

        System.out.println(String.format("Non è possibile creare un'istanza della classe [%s] SENZA parametri", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println("È obbligatorio il 'nomeLista' nel costruttore.");
        System.out.println(String.format("Seguendo il Pattern Builder, non si può chiamare il metodo '%s' se l'istanza non è correttamente istanziata.", "listaBio"));
    }


    protected void fixBeanStandardCompleta(final Object istanza) {
        System.out.println(String.format("6 - Istanza costruita col parametro obbligatorio", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(VUOTA);
        System.out.println(String.format("L'istanza della classe [%s] è stata creata con '%s'", clazz != null ? clazz.getSimpleName() : VUOTA, "nomeLista"));
        System.out.println("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
        System.out.println("Pronta per listaBio(), listaWrap() e mappaWrap()");
        System.out.println(VUOTA);

        assertNotNull(istanza);
    }

    protected void fixListaBioSenzaTypeLista() {
        System.out.println(("7 - listaBioSenzaTypeLista"));
        System.out.println(VUOTA);
        System.out.println(String.format("L'istanza della classe [%s] è stata creata con '%s'", clazz != null ? clazz.getSimpleName() : VUOTA, "nomeLista"));
        System.out.println(String.format("Questa classe funziona anche SENZA '%s' perché è già inserito in fixPreferenze().", "typeLista"));
        System.out.println("L'invocazione del metodo listaBio() è accettabile");
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

}
