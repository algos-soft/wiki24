package it.algos.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
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


    protected void setUpAll() {
        super.setUpAll();
    }


    protected void fixListaBio(final String sorgente, final List<Bio> listBio) {
        System.out.println(VUOTA);
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
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista.subList(0, 5)) {
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
            System.out.println(VUOTA);
            for (WrapLista wrap : listWrapLista) {
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
            message = String.format("La mappaWrap della lista %s ha %d chiavi (paragrafi) per %d didascalie", sorgente, mappaWrap.size(),wikiUtility.getSizeAllWrap(mappaWrap));
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
