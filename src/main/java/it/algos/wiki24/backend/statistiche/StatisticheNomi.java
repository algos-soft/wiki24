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

    @Autowired
    protected NomeBackend nomeBackend;

    public StatisticheNomi() {
    }// end of constructor

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaNomi;
        super.durataStatistica = WPref.statisticaNomiTime;
        super.typeTime = AETypeTime.minuti;
    }

    public StatisticheNomi test() {
        this.uploadTest = true;
        return this;
    }

    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();
        String message;
        int cont = 0;
        String totNomi = VUOTA;
        DistinctIterable<String> listaNomiDistinti;

        String totNomiUtilizzati = textService.format(nomeBackend.count());
        String totVoci = textService.format(bioBackend.count());
        String totListe = textService.format(nomeBackend.countBySopraSoglia());
        int sogliaMongo = WPref.sogliaMongoNomi.getInt();
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();
        listaNomiDistinti = mongoService.getCollection(TAG_BIO).distinct("nome", String.class);
        for (String stringa : listaNomiDistinti) {
            cont++;
        }
        totNomi = textService.format(cont);

        buffer.append(wikiUtility.setParagrafo("Nomi"));
        buffer.append(String.format("Elenco dei '''%s''' nomi '''differenti''' ", totNomiUtilizzati));
        buffer.append(textService.setRef(String.format("Nelle voci biografiche dell'enciclopedia ci sono circa '''%s''' nomi diversi.", totNomi)));
        buffer.append(" utilizzati");
        buffer.append(textService.setRef(String.format("In questa statistica vengono elencati i nomi che nelle '''%s''' voci biografiche presenti nell'enciclopedia ricorrono con una frequenza di almeno '''%d''' volte.", totVoci, sogliaMongo)));
        buffer.append(String.format(" Ci sono '''%s''' pagine di liste per i nomi con occorrenze superiori a '''%d'''", totListe, sogliaWiki));

        return buffer.toString();

    }

    /**
     * Elabora i dati
     */
    protected void elabora() {
        //                        nomeBackend.elabora();
    }

    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        lista = nomeBackend.findAllSortCorrente();
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
        buffer.append("Nome");
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


    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult esegue() {
        if (super.uploadTest) {
            return uploadTest();
        }
        else {
            return upload();
        }
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload() {
        super.prepara();
        return super.upload(TAG_ANTROPONIMI + TAG_LISTA_NOMI);
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult uploadTest() {
        super.prepara();
        return super.upload(UPLOAD_TITLE_DEBUG + TAG_LISTA_NOMI);
    }

}
