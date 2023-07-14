package it.algos.wiki24.backend.statistiche;

import com.mongodb.client.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 13-Jul-2023
 * Time: 19:17
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatisticheListeCognomi extends Statistiche {

    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiTitleUpload = TAG_ANTROPONIMI + TAG_LISTA_COGNOMI;
        super.wikiTitleTest = UPLOAD_TITLE_DEBUG + TAG_LISTA_COGNOMI;
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaNomi;
        super.durataStatistica = WPref.statisticaNomiTime;
        super.typeTime = AETypeTime.minuti;
    }


    public StatisticheListeCognomi test() {
        super.wikiTitleUpload = super.wikiTitleTest;
        this.uploadTest = true;
        return this;
    }


    public WResult esegue() {
        this.elabora();
        this.creaLista();
        this.creaMappa();

        return esegueUpload();
    }


    /**
     * Elabora i dati
     */
    protected void elabora() {
        //check temporale per elaborare la collection SOLO se non è già stata elaborata di recente (1 ora)
        //visto che l'elaborazione impiega più di 3 minuti
        LocalDateTime elaborazioneAttuale = LocalDateTime.now();
        LocalDateTime lastElaborazione = (LocalDateTime) cognomeBackend.lastElaborazione.get();

        lastElaborazione = lastElaborazione.plusHours(1);
        if (elaborazioneAttuale.isAfter(lastElaborazione)) {
            cognomeBackend.elabora();
        }
    }


    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        int soglia = 30;
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



    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();
        String message;
        int cont = 0;
        String totNomi = VUOTA;
        DistinctIterable<String> listaCognomiDistinti;

        String totCognomiUtilizzati = textService.format(cognomeBackend.count());
        String totVoci = textService.format(bioBackend.count());
        String totListe = textService.format(cognomeBackend.countBySopraSoglia());
        int sogliaWiki = WPref.sogliaWikiCognomi.getInt();
        int sogliaMongo = WPref.sogliaMongoCognomi.getInt();
        listaCognomiDistinti = mongoService.getCollection(TAG_BIO).distinct("cognome", String.class);
        for (String stringa : listaCognomiDistinti) {
            cont++;
        }
        totNomi = textService.format(cont);

        message = String.format("Nell'enciclopedia ci sono '''%s''' voci biografiche.", totVoci);
        buffer.append(message);
        buffer.append(CAPO_HTML);
        message = String.format("Nelle voci biografiche ci sono circa '''%s''' cognomi diversi.", totNomi);
        buffer.append(message);
        buffer.append(CAPO_HTML);
        message = String.format("Ci sono '''%s''' pagine di liste '''Persone di cognome Xxx''' con occorrenze superiori a '''%d'''.", totListe, sogliaWiki);
        buffer.append(message);
        buffer.append(CAPO_HTML);
        message = String.format("In questa statistica vengono elencati i cognomi con occorrenze superiori a '''%d'''.", sogliaMongo);
        buffer.append(message);

        return buffer.toString();
    }

    protected String inizioTabella() {
        String testo = VUOTA;

        testo += CAPO;
        testo += "{|class=\"wikitable sortable\" style=\"background-color:#EFEFEF; text-align: left;\"";
        testo += CAPO;

        return testo;
    }

    @Override
    protected String colonne() {
        StringBuffer buffer = new StringBuffer();
        String color = "! style=\"background-color:#CCC;text-align: left;\"; |";

        buffer.append(color);
        buffer.append("#");
        buffer.append(CAPO);

        buffer.append(color);
        buffer.append("Cognome");
        buffer.append(CAPO);

        buffer.append(color);
        buffer.append("Pagina");
        buffer.append(CAPO);

        buffer.append(color);
        buffer.append("Lista");
        buffer.append(CAPO);

        buffer.append(color);
        buffer.append("Voci");
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String riga(int numRiga, MappaStatistiche mappa) {
        StringBuffer buffer = new StringBuffer();
        String tagDex = "style=\"text-align: right;\" |";
        String iniTag = "|-";
        String doppioTag = " || ";
        String pipe = "|";
        String nomePersona;
        String paginaVoce;
        String paginaLista = VUOTA;

        buffer.append(iniTag);
        buffer.append(CAPO);
        buffer.append(pipe);

        //progressivo
        buffer.append(numRiga);
        buffer.append(doppioTag);

        //nome
        nomePersona = mappa.getChiave();
        if (mappa.isSuperaSoglia()) {
            nomePersona = textService.setBold(nomePersona);
        }
        buffer.append(nomePersona);
        buffer.append(doppioTag);

        //pagina
        paginaVoce = mappa.getPaginaVoce();
        buffer.append(textService.setDoppieQuadre(paginaVoce));
        buffer.append(doppioTag);

        if (mappa.isSuperaSoglia()) {
            paginaLista = mappa.getPaginaLista();
            paginaLista = textService.setDoppieQuadre(paginaLista);
        }
        buffer.append(paginaLista);
        buffer.append(doppioTag);

        //voci
        buffer.append(tagDex);
        buffer.append(mappa.getNumNomi());
        buffer.append(CAPO);

        return buffer.toString();
    }

}

