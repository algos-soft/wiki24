package it.algos.upload;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 23-Jul-2023
 * Time: 07:37
 */
public abstract class UploadTest extends WikiTest {


    protected static int MAX = 5;

    protected void fixSenzaParametroNelCostruttore() {
        super.fixSenzaParametroNelCostruttore("nomeLista", "esegue");
    }

    protected void fixBeanStandard(final Object istanza) {
        super.fixBeanStandard(istanza, "nomeLista", "esegue()", "test()");
    }

    protected void fixConParametroNelCostruttore() {
        super.fixConParametroNelCostruttore("nomeLista", "esegue()","test()");
    }

    protected void printUpload(Upload uploadEntityBean) {
        if (uploadEntityBean == null) {
            return;
        }

        System.out.println(String.format("Valori STANDARD per un'istanza di [%s], creata con il solo '%s' e pronta per essere utilizzata", uploadEntityBean.getClass().getSimpleName(),"nomeLista"));
        System.out.println(VUOTA);

        System.out.println(String.format("%s%s%s", "nomeLista: [fissato col costruttore]", FORWARD, uploadEntityBean.nomeLista));
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