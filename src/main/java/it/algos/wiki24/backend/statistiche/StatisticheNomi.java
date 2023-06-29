package it.algos.wiki24.backend.statistiche;

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
        String totNomi = textService.format(nomeBackend.count());
        String totVoci = textService.format(bioBackend.count());
        int sogliaMongo = WPref.sogliaMongoNomi.getInt();
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();

        buffer.append(wikiUtility.setParagrafo("Nomi"));
        message = String.format("Elenco dei '''%s''' nomi '''differenti''' utilizzati nelle %s voci biografiche con occorrenze maggiori di '''%d'''.", totNomi, totVoci, sogliaMongo);
        buffer.append(message);
        message = String.format(" Per ogni nome costruita una pagina con la lista delle voci biografiche se le occorrenze sono superiori a '''%d'''", sogliaWiki);
        buffer.append(message);

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

        for (Nome nome : (List<Nome>) lista) {
            mappa.put(nome.nome, MappaStatistiche.nome(nome.nome, nome.numBio, nome.superaSoglia));
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
        String color = "! style=\"background-color:#CCC;\" |";

        buffer.append(color);
        buffer.append("#");
        buffer.append(CAPO);

        buffer.append(color);
        buffer.append("Nome");
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

        buffer.append(iniTag);
        buffer.append(CAPO);
        buffer.append(pipe);

        buffer.append(numRiga);
        buffer.append(doppioTag);

        nomePersona = mappa.getChiave();
        buffer.append(textService.setDoppieQuadre(nomePersona));
        buffer.append(doppioTag);

        if (mappa.isSuperaSoglia()) {
            nomePersona = textService.setDoppieQuadre(nomePersona);
            nomePersona = textService.setBold(nomePersona);
        }
        buffer.append(nomePersona);
        buffer.append(doppioTag);

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
