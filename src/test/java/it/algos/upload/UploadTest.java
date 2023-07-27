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

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 23-Jul-2023
 * Time: 07:37
 */
public abstract class UploadTest extends WikiTest {

    protected static int MAX = 5;

    protected static String PARAMETRO = "nomeLista";

    protected static String CHECK = "checkValidita()";

    protected static String FUNZIONE = "isExistByKey";

    protected void setUpAll() {
        super.setUpAll();

        nomeParametro = "nomeLista";
        metodiEseguibili = "esegue(), upload()";
        metodoDaRegolare = "typeLista(), test()";
        metodiBuilderPattern = "typeLista(), typeLinkParagrafi(), typeLinkCrono(), icona(), noToc(), forceToc(), siNumVoci(), noNumVoci(), sottoPagina(), test()";
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
        super.fixBeanStandard(nomeParametro, valore, metodiEseguibili, metodoDaRegolare, metodiBuilderPattern);
//        this.debug(valore, "forse", "pippoz", true, false);
//        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    protected void fixConParametroNelCostruttore(String sorgente) {
        this.fixConParametroNelCostruttore(sorgente, "test()");
    }

    protected void fixConParametroNelCostruttore(String sorgente, String metodiDaRegolare) {
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

        super.fixConParametroNelCostruttore("nomeLista", "fixMappaWrap()", metodiDaRegolare, istanzaEffettivamenteValida, inizio);

        System.out.println(VUOTA);
        System.out.println("Debug");
        System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
        System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, istanzaEffettivamenteValida));
        System.out.println(String.format("metodoEseguito%s%s", FORWARD, metodoEseguito));
    }


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
        System.out.println(String.format("%s%s%s", "uploadTest: [di default=false (ATTENZIONE) ma regolabile coi metodi PatternBuilder]", FORWARD, uploadEntityBean.uploadTest));
        System.out.println(VUOTA);
    }

}