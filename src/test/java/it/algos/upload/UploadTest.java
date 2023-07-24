package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 23-Jul-2023
 * Time: 07:37
 */
public abstract class UploadTest extends WikiTest {


    protected static int MAX = 5;


    @Test
    @Order(5)
    @DisplayName("5 - esegueSenzaParametroNelCostruttore")
    void esegueSenzaParametroNelCostruttore() {
        try {
            ((Upload) appContext.getBean(clazz)).esegue();
        } catch (Exception unErrore) {
            super.fixSenzaParametroNelCostruttore("nomeLista", "esegue");
        }
    }


    protected void fixBeanStandard(final String sorgente) {
        Upload istanza = (Upload) appContext.getBean(clazz, sorgente);
        super.fixBeanStandard(istanza, "nomeLista", "esegue()", "test()");
        assertEquals(super.istanzaValidaSubitoDopoCostruttore, istanza.isValida());
        printUpload(istanza);
    }

    protected void fixConParametroNelCostruttore(String sorgente) {
        long inizio = System.currentTimeMillis();
        WResult result = null;
        Upload istanza = null;
        boolean istanzaEffettivamenteValida;
        boolean metodoEseguito = false;

        try {
            istanza = (Upload) appContext.getBean(clazz, sorgente);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
        }
        assertNotNull(istanza);

        istanzaEffettivamenteValida = istanza.isValida();
        assertEquals(istanzaValidaSubitoDopoCostruttore, istanzaEffettivamenteValida);

        if (istanzaEffettivamenteValida) {
            try {
                metodoEseguito = istanza.fixMappaWrap();
                assertTrue(metodoEseguito);
            } catch (Exception unErrore) {
                assertNull(result);
                logService.error(new WrapLog().exception(new AlgosException(unErrore)));
            }
        }

        super.fixConParametroNelCostruttore("nomeLista", "fixMappaWrap()", "test()", istanzaEffettivamenteValida, inizio);

        System.out.println(VUOTA);
        System.out.println("Debug");
        System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
        System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, istanzaEffettivamenteValida));
        System.out.println(String.format("metodoEseguito%s%s", FORWARD, metodoEseguito));
    }

//    protected void fixConParametroNelCostruttore() {
//        super.fixConParametroNelCostruttore("nomeLista", "esegue()","test()");
//    }

    protected void printUpload(Upload uploadEntityBean) {
        if (uploadEntityBean == null) {
            return;
        }

        message = String.format("Valori STANDARD per un'istanza di [%s], creata con il solo '%s'", uploadEntityBean.getClass().getSimpleName(), "nomeLista");
        if (uploadEntityBean.isValida()) {
            message += String.format("%sPronta per essere utilizzata.", SEP);
        }
        else {
            message += String.format("%sNon ancora utilizzabile.", SEP);
        }
        System.out.println(message);
        System.out.println(VUOTA);

        System.out.println(String.format("%s%s%s", "nomeLista: [fissato nel costruttore]", FORWARD, uploadEntityBean.nomeLista));
        System.out.println(String.format("%s%s%s", "wikiTitleUpload: [creato in automatico]", FORWARD, uploadEntityBean.wikiTitleUpload));
        System.out.println(String.format("%s%s%s", "summary: [regolato in fixPreferenze()]", FORWARD, uploadEntityBean.summary));
        System.out.println(String.format("%s%s%s", "typeLista: [regolato in fixPreferenze()]", FORWARD, uploadEntityBean.typeLista));
        System.out.println(String.format("%s%s%s", "typeToc: [standard da preferenze ma regolabile in fixPreferenze()]", FORWARD, uploadEntityBean.typeToc));
        System.out.println(String.format("%s%s%s", "typeLinkParagrafi: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, uploadEntityBean.typeLinkParagrafi));
        System.out.println(String.format("%s%s%s", "usaNumeriTitoloParagrafi: [standard da preferenze ma regolabile in fixPreferenze()]", FORWARD, uploadEntityBean.usaNumeriTitoloParagrafi));
        System.out.println(String.format("%s%s%s", "typeLinkCrono: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, uploadEntityBean.typeLinkCrono));
        System.out.println(String.format("%s%s%s", "usaIcona: [standard da preferenze ma regolabile coi metodi PatternBuilder]", FORWARD, uploadEntityBean.usaIcona));
        System.out.println(String.format("%s%s%s", "uploadTest: [di default=true (per sicurezza) ma regolabile coi metodi PatternBuilder]", FORWARD, uploadEntityBean.uploadTest));
        System.out.println(VUOTA);
    }

}