package it.algos.wiki24.backend.statistiche;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Jul-2022
 * Time: 11:23
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatisticheGiorni extends Statistiche {


    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.currentWikiBackend = giornoWikiBackend;
        super.wikiTitleUpload = PATH_BIOGRAFIE + "Giorni";
        super.typeSummary = AETypeSummary.statBio;
        super.typeToc = AETypeToc.noToc;
        super.lastStatistica = WPref.statisticaGiorni;
        super.durataStatistica = WPref.statisticaGiorniTime;
        super.typeTime = AETypeTime.minuti;
        super.infoTime = "dei giorni";
    }

    protected void fixNomeParametri() {
        super.tagNato = "giornoMeseNascita";
        super.tagMorto = "giornoMeseMorte";

        super.fixNomeParametri();
    }


    @Override
    protected String incipit() {
        StringBuffer buffer = new StringBuffer();
        String message;

        buffer.append(wikiUtility.setParagrafo("Giorni"));
        buffer.append("Statistiche dei nati e morti per ogni giorno dell'anno");
        message = String.format("Previsto il [[29 febbraio]] per gli [[Anno bisestile|anni bisestili]]");
        buffer.append(textService.setRef(message));
        buffer.append(PUNTO + SPAZIO);
        buffer.append(VALORI_CERTI + SPAZIO + " i giorni di nascita e morte della persona.");

        return buffer.toString();
    }


    /**
     * Recupera la lista
     */
    @Override
    protected void creaLista() {
        lista = giornoWikiBackend.findAllSortCorrente();
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
        String percNati;
        String percMorti;

        for (GiornoWiki giorno : (List<GiornoWiki>) lista) {
            nati = giorno.bioNati;
            morti = giorno.bioMorti;
            mappaSingola = new MappaStatistiche(++pos, giorno.nome, nati, morti);
            mappa.put(giorno.nome, mappaSingola);
            totNati += nati;
            totMorti += morti;
        }

        for (String key : mappa.keySet()) {
            mappaSingola = mappa.get(key);
            nati = mappaSingola.getNati();
            morti = mappaSingola.getMorti();
            percNati = mathService.percentualeTxt(nati, totNati);
            percMorti = mathService.percentualeTxt(morti, totMorti);
            mappaSingola.setPercNati(percNati);
            mappaSingola.setPercMorti(percMorti);
            mappa.put(key, mappaSingola);
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
        Map<String, Integer> mappa = giornoWikiBackend.elaboraValidi();

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

        natiSenzaPer = mathService.percentualeTxt(mappa.get(KEY_MAP_NATI_SENZA_PARAMETRO), vociBiografiche);
        natiVuotoPer = mathService.percentualeTxt(mappa.get(KEY_MAP_NATI_PARAMETRO_VUOTO), vociBiografiche);
        natiValidoPer = mathService.percentualeTxt(mappa.get(KEY_MAP_NATI_VALORE_ESISTENTE), vociBiografiche);

        buffer.append(wikiUtility.setParagrafo("Nascita"));
        message = String.format("Nelle '''%s''' voci biografiche esistenti, il giorno di nascita", numVoci);
        buffer.append(message);
        buffer.append(textService.setRef(VALIDO_CORRISPONDENTE, NOTA_VALIDO));
        message = " risulta:";
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "'''Manca'''";
        buffer.append(message);
        buffer.append(textService.setRef(mancaNato));
        message = String.format(" il parametro in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_NATI_SENZA_PARAMETRO)), natiSenzaPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "Il parametro è '''vuoto'''";
        buffer.append(message);
        buffer.append(textService.setRef(vuotoNato));
        message = String.format(" in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_NATI_PARAMETRO_VUOTO)), natiVuotoPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "Esiste un valore '''valido'''";
        buffer.append(message);
        buffer.append(textService.setRef(validoNato));
        message = String.format(" in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_NATI_VALORE_ESISTENTE)), natiValidoPer);
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

        mortiSenzaPer = mathService.percentualeTxt(mappa.get(KEY_MAP_MORTI_SENZA_PARAMETRO), vociBiografiche);
        mortiVuotoPer = mathService.percentualeTxt(mappa.get(KEY_MAP_MORTI_PARAMETRO_VUOTO), vociBiografiche);
        mortiValidoPer = mathService.percentualeTxt(mappa.get(KEY_MAP_MORTI_VALORE_ESISTENTE), vociBiografiche);

        buffer.append(wikiUtility.setParagrafo("Morte"));
        message = String.format("Nelle '''%s''' voci biografiche esistenti, il giorno di morte", numVoci);
        buffer.append(message);
        buffer.append(textService.setRef(VALIDO_CORRISPONDENTE, NOTA_VALIDO));
        message = " risulta:";
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "'''Manca'''";
        buffer.append(message);
        buffer.append(textService.setRef(mancaMorto));
        message = String.format(" il parametro in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_MORTI_SENZA_PARAMETRO)), mortiSenzaPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "Il parametro è '''vuoto'''";
        buffer.append(message);
        buffer.append(textService.setRef(vuotoMorto));
        message = String.format(" in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_MORTI_PARAMETRO_VUOTO)), mortiVuotoPer);
        buffer.append(message);
        buffer.append(CAPO_ASTERISCO);
        message = "Esiste un valore '''valido'''";
        buffer.append(message);
        buffer.append(textService.setRef(validoMorto));
        message = String.format(" in %s voci ('''%s''' del totale)", textService.format(mappa.get(KEY_MAP_MORTI_VALORE_ESISTENTE)), mortiValidoPer);
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
        message = "Voci biografiche esistenti per ogni giorno di nascita e di morte";
        buffer.append(message);

        buffer.append(CAPO_ASTERISCO);
        buffer.append(super.body());

        return buffer.toString();
    }

    @Override
    protected String colonne() {
        StringBuffer buffer = new StringBuffer();
        String message;
        String color = "! style=\"background-color:#CCC;\" |";
        String natiTxt = textService.format(totNati);
        String mortiTxt = textService.format(totMorti);

        buffer.append(color);
        buffer.append("#");
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Giorno");
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Nati");
        buffer.append(textService.setRef(DEVE + tagNato));
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("Morti");
        buffer.append(textService.setRef(DEVE + tagMorto));
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("% nati ");
        message = String.format("Percentuale dei nati in questo giorno, rispetto al totale valido (%s) dei nati", natiTxt);
        buffer.append(textService.setRef(message));
        buffer.append(CAPO);
        buffer.append(color);
        buffer.append("% morti ");
        message = String.format("Percentuale dei nati in questo giorno, rispetto al totale valido (%s) dei morti", mortiTxt);
        buffer.append(textService.setRef(message));
        buffer.append(CAPO);

        return buffer.toString();
    }


    protected String riga(int numRiga, MappaStatistiche mappa) {
        StringBuffer buffer = new StringBuffer();
        String iniTag = "|-";
        String doppioTag = " || ";
        String pipe = "|";
        String nato;
        String morto;

        buffer.append(iniTag);
        buffer.append(CAPO);
        buffer.append(pipe);
        buffer.append(mappa.getPos());

        buffer.append(doppioTag);
        buffer.append(textService.setDoppieQuadre(mappa.getNome()));

        buffer.append(doppioTag);
        nato = wikiUtility.wikiTitleNatiGiorno(mappa.getNome()) + PIPE + mappa.getNati();
        buffer.append(textService.setDoppieQuadre(nato));

        buffer.append(doppioTag);
        morto = wikiUtility.wikiTitleMortiGiorno(mappa.getNome()) + PIPE + mappa.getMorti();
        buffer.append(textService.setDoppieQuadre(morto));

        buffer.append(doppioTag);
        buffer.append(mappa.getPercNati());

        buffer.append(doppioTag);
        buffer.append(mappa.getPercMorti());

        buffer.append(CAPO);

        return buffer.toString();
    }

}
