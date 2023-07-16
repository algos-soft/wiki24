package it.algos.wiki24.backend.statistiche;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognome.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 16-Jul-2023
 * Time: 20:13
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatisticheCognomi extends Statistiche {


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.currentWikiBackend = cognomeBackend;
        super.wikiTitleUpload = TAG_ANTROPONIMI + TAG_COGNOMI;
        super.wikiTitleTest = UPLOAD_TITLE_DEBUG + TAG_COGNOMI;
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaCognomi;
        super.durataStatistica = WPref.statisticaCognomiTime;
        super.typeTime = AETypeTime.minuti;
    }

    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();

        String totListe = textService.format(cognomeBackend.countBySopraSoglia());
        int sogliaWiki = WPref.sogliaWikiCognomi.getInt();

        buffer.append(wikiUtility.setParagrafo("Cognomi"));
        buffer.append(String.format("Elenco dei '''%s''' cognomi che superano le '''%s''' occorrenze nelle voci biografiche", totListe, sogliaWiki));

        return buffer.toString();

    }

    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        int soglia = 50;
        lista = cognomeBackend.findAllByNumBio(soglia);
    }


    /**
     * Costruisce la mappa <br>
     */
    @Override
    protected void creaMappa() {
        super.creaMappa();
        int sogliaWiki = WPref.sogliaWikiCognomi.getInt();
        boolean supera;

        for (Cognome cognome : (List<Cognome>) lista) {
            supera = cognome.numBio > sogliaWiki;
            mappa.put(cognome.cognome, MappaStatistiche.nome(cognome.cognome, cognome.numBio, cognome.paginaVoce, cognome.paginaLista, supera));
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
            buffer.append(PATH_COGNOMI);
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
