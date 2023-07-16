package it.algos.wiki24.backend.statistiche;

import com.mongodb.client.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 29-Jun-2023
 * Time: 07:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatisticheNomi extends Statistiche {


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.currentWikiBackend = nomeBackend;
        super.wikiTitleUpload = TAG_ANTROPONIMI + TAG_NOMI;
        super.wikiTitleTest = UPLOAD_TITLE_DEBUG + TAG_NOMI;
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaNomi;
        super.durataStatistica = WPref.statisticaNomiTime;
        super.typeTime = AETypeTime.minuti;
    }

    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();

        String totListe = textService.format(nomeBackend.countBySopraSoglia());
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();

        buffer.append(wikiUtility.setParagrafo("Nomi"));
        buffer.append(String.format("Elenco dei '''%s''' nomi che superano le '''%s''' occorrenze nelle voci biografiche", totListe, sogliaWiki));

        return buffer.toString();

    }

    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        int soglia = 50;
        lista = nomeBackend.findAllByNumBio(soglia);
    }


    /**
     * Costruisce la mappa <br>
     */
    @Override
    protected void creaMappa() {
        super.creaMappa();
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();
        boolean supera;

        for (Nome nome : (List<Nome>) lista) {
            supera = nome.numBio > sogliaWiki;
            mappa.put(nome.nome, MappaStatistiche.nome(nome.nome, nome.numBio, nome.paginaVoce, nome.paginaLista, supera));
        }
    }


    protected String body() {
        StringBuffer buffer = new StringBuffer();
        MappaStatistiche mappaSingola;

        buffer.append("{{Div col}}");

        for (String key : mappa.keySet()) {
            mappaSingola = mappa.get(key);
            buffer.append(CAPO);
            buffer.append(ASTERISCO);
            buffer.append(DOPPIE_QUADRE_INI);
            buffer.append(PATH_NOMI);
            buffer.append(mappaSingola.getChiave());
            buffer.append(PIPE);
            buffer.append(mappaSingola.getChiave());
            buffer.append(DOPPIE_QUADRE_END);
            buffer.append(String.format(" '''(%s)'''", mappaSingola.getNumNomi()));
        }
        buffer.append("{{Div col end}}");

        return buffer.toString();
    }


}
