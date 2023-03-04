package it.algos.wiki24.backend.statistiche;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Aug-2022
 * Time: 13:32
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatisticheAnni extends Statistiche {


    public static final String VALIDO_NASCITA = VALIDO + SPAZIO + "'''annoNascita'''";

    public static final String VALIDO_MORTE = VALIDO + SPAZIO + "'''annoMorte'''";


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class, attivita) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public StatisticheAnni() {
    }// end of constructor


    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaAnni;
        super.durataStatistica = WPref.statisticaAnniTime;
        super.typeTime = AETypeTime.minuti;
        super.infoTime = "degli anni";
    }


    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();
        String message;

        buffer.append(wikiUtility.setParagrafo("Anni"));
        buffer.append("'''Statistiche''' dei nati e morti per ogni anno");
        message = String.format("Potenzialmente dal [[1000 a.C.]] al [[{{CURRENTYEAR}}]], anche se non tutti gli anni hanno una propria pagina di nati o morti");
        buffer.append(textService.setRef(message));
        buffer.append(PUNTO + SPAZIO);
        buffer.append(VALORI_CERTI + SPAZIO + " gli anni di nascita e morte della persona.");

        return buffer.toString();
    }

    /**
     * Elabora i dati
     */
    protected void elabora() {
        annoWikiBackend.elabora();
        //        Map mappa = annoWikiBackend.elaboraValidi();
    }

    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        lista = annoWikiBackend.findAllSort(Sort.by(Sort.Direction.DESC, "ordine"));
    }


    /**
     * Costruisce la mappa <br>
     */
    @Override
    protected void creaMappa() {
        super.creaMappa();

        MappaStatistiche mappaSingola;
        int pos = 0;
        int nati;
        int morti;
        totNati = 0;
        totMorti = 0;
        String chiave;

        for (AnnoWiki anno : (List<AnnoWiki>) lista) {
            nati = anno.bioNati;
            morti = anno.bioMorti;
            if (nati > 0 || morti > 0) {
                ++pos;
                chiave = textService.format(pos);
                mappaSingola = new MappaStatistiche(chiave, anno.nome, nati, morti);
                mappa.put(anno.nome, mappaSingola);
            }

            totNati += nati;
            totMorti += morti;
        }
    }


    /**
     * Eventuale prima tabella <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected String bodyAnte() {
        StringBuffer buffer = new StringBuffer();
        int vociBiografiche = mongoService.count(Bio.class);
        String numVoci = textService.format(vociBiografiche);
        Map<String, Integer> mappa = annoWikiBackend.elaboraValidi();

        buffer.append(nascita(vociBiografiche, numVoci, mappa));
        buffer.append(morte(vociBiografiche, numVoci, mappa));

        return buffer.toString();
    }


    protected String nascita(int vociBiografiche, String numVoci, Map<String, Integer> mappa) {
        StringBuffer buffer = new StringBuffer();
        String message;

        String natiSenzaPer;
        String natiVuotoPer;
        String natiValidoPer;

        natiSenzaPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_NATI_SENZA_PARAMETRO));
        natiVuotoPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_NATI_PARAMETRO_VUOTO));
        natiValidoPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_NATI_VALORE_ESISTENTE));

        buffer.append(wikiUtility.setParagrafo("Nascita"));
        message = String.format("Nelle '''%s''' voci biografiche esistenti, l'anno di nascita", numVoci);
        buffer.append(message);
        buffer.append(textService.setRef(VALIDO_CORRISPONDENTE, NOTA_VALIDO));
        message = " risulta:";
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Manca il parametro in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_NATI_SENZA_PARAMETRO)), natiSenzaPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Il parametro è vuoto in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_NATI_PARAMETRO_VUOTO)), natiVuotoPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Esiste un valore valido in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_NATI_VALORE_ESISTENTE)), natiValidoPer);
        buffer.append(message);

        return buffer.toString();
    }

    /**
     * Eventuale seconda tabella <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected String morte(int vociBiografiche, String numVoci, Map<String, Integer> mappa) {
        StringBuffer buffer = new StringBuffer();
        String message;

        String mortiSenzaPer;
        String mortiVuotoPer;
        String mortiValidoPer;

        mortiSenzaPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_MORTI_SENZA_PARAMETRO));
        mortiVuotoPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_MORTI_PARAMETRO_VUOTO));
        mortiValidoPer = mathService.percentualeTxt(vociBiografiche, mappa.get(KEY_MAP_MORTI_VALORE_ESISTENTE));

        buffer.append(wikiUtility.setParagrafo("Morte"));
        message = String.format("Nelle '''%s''' voci biografiche esistenti, l'anno di morte", numVoci);
        buffer.append(message);
        buffer.append(textService.getRef(NOTA_VALIDO));
        message = " risulta:";
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Manca il parametro in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_MORTI_SENZA_PARAMETRO)), mortiSenzaPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Il parametro è vuoto in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_MORTI_PARAMETRO_VUOTO)), mortiVuotoPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = String.format("Esiste un valore valido in %s voci (%s del totale)", textService.format(mappa.get(KEY_MAP_MORTI_VALORE_ESISTENTE)), mortiValidoPer);
        buffer.append(message);

        return buffer.toString();
    }

    /**
     * Tabella normale <br>
     */
    @Override
    protected String body() {
        StringBuffer buffer = new StringBuffer();
        String message;

        buffer.append(wikiUtility.setParagrafo("Biografie"));
        message = "Voci biografiche esistenti per ogni anno";
        buffer.append(message);
        buffer.append(textService.setRef("In ordine cronologico discendente"));
        buffer.append(textService.setRef("Sono elencati '''solo''' gli anni che hanno almeno una voce biografica di nascita o morte"));
        message = " di nascita e di morte";
        buffer.append(message);

        buffer.append(CAPO_ASTERISCO);
        buffer.append(super.body());

        return buffer.toString();
    }

    @Override
    protected String colonne() {
        StringBuffer buffer = new StringBuffer();
        String color = "! style=\"background-color:#CCC;\" |";
        String message;

        buffer.append(color);
        buffer.append("#");
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Anno");
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Nati");
        buffer.append(textService.setRef(VALIDO_NASCITA));
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Morti");
        buffer.append(textService.setRef(VALIDO_MORTE));
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String riga(MappaStatistiche mappa) {
        StringBuffer buffer = new StringBuffer();
        String iniTag = "|-";
        String doppioTag = " || ";
        String pipe = "|";
        String nato;
        String morto;

        buffer.append(iniTag);
        buffer.append(CAPO);
        buffer.append(pipe);
        buffer.append(mappa.getChiave());

        buffer.append(doppioTag);
        buffer.append(textService.setDoppieQuadre(mappa.getNome()));

        buffer.append(doppioTag);
        nato = wikiUtility.wikiTitleNatiAnno(mappa.getNome()) + PIPE + mappa.getNati();
        buffer.append(textService.setDoppieQuadre(nato));

        buffer.append(doppioTag);
        morto = wikiUtility.wikiTitleMortiAnno(mappa.getNome()) + PIPE + mappa.getMorti();
        buffer.append(textService.setDoppieQuadre(morto));

        buffer.append(CAPO);

        return buffer.toString();
    }


    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload() {
        super.esegue();
        return super.upload(PATH_ANNI);
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult uploadTest() {
        super.esegue();
        return super.upload(UPLOAD_TITLE_DEBUG + ANNI);
    }

}
