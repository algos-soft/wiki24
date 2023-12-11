package it.algos.base24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 06-apr-2022
 * Time: 06:27
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 3) @Autowired public WebService webService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class WebService {

    public static final String URL_BASE_ALGOS = "http://www.algos.it/";

    public static final String URL_BASE_VAADIN24 = URL_BASE_ALGOS + "vaadin24/";

    public static final String URL_BASE_VAADIN24_CONFIG = URL_BASE_VAADIN24 + "config/";

    public static final String INPUT = "UTF8";

    @Autowired
    private TextService textService;

    @Autowired
    private HtmlService htmlService;

    /**
     * Crea la connessione di tipo GET
     */
    public URLConnection getURLConnection(String domain) throws Exception {
        URLConnection urlConn = null;

        if (domain != null && domain.length() > 0) {
            domain = domain.replaceAll(SPAZIO, UNDERSCORE);
            urlConn = new URL(domain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
        }

        return urlConn;
    }


    /**
     * Request di tipo GET
     */
    public String getUrlRequest(URLConnection urlConn) throws Exception {
        String risposta;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, INPUT);

        // read the request
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
            textBuffer.append(CAPO);
        }

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        risposta = textBuffer.toString();

        return risposta;
    }

    public boolean isEsisteWiki(final String wikiTitleGrezzo) {
        boolean esiste = false;
        String tag = "{\"error";
        String testoLeggibile = leggeWikiParse(wikiTitleGrezzo);

        if (textService.isValid(testoLeggibile) && !testoLeggibile.startsWith(tag)) {
            esiste = true;
        }

        return esiste;
    }

    /**
     * Request di tipo GET. Legge la pagina intera. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col testo grezzo in formato html oppure BSON
     */
    public String legge(final String urlDomain) {
        String codiceSorgente = VUOTA;
        URLConnection urlConn;
        String tag = TAG_INIZIALE;
        String tag2 = TAG_INIZIALE_SECURE;

        try {
            String webUrl = urlDomain.startsWith(tag) || urlDomain.startsWith(tag2) ? urlDomain : tag2 + urlDomain;
            urlConn = getURLConnection(webUrl);
            codiceSorgente = getUrlRequest(urlConn);
        } catch (Exception unErrore) {
        }

        return codiceSorgente;
    }


    /**
     * Request di tipo GET <br>
     * Sorgente completo di una pagina wiki <br>
     * Non usa le API di Mediawiki <br>
     * Elabora il wikiTitle per eliminare gli spazi vuoti <br>
     *
     * @param wikiTitleGrezzo da controllare per riempire gli spazi vuoti
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public String leggeWikiParse(final String wikiTitleGrezzo) {
        String testoLeggibile;
        String testoGrezzo;
        String tagIniziale = "wikitext\":\"";
        String tagFinale = "\"}}";
        String wikiTitleElaborato = wikiTitleGrezzo.replaceAll(SPAZIO, UNDERSCORE);
        wikiTitleElaborato = wikiTitleElaborato.replaceAll("à", "%E0");

        testoGrezzo = legge(WIKI_PARSE + wikiTitleElaborato);

        testoLeggibile = textService.levaPrimaAncheTag(testoGrezzo, tagIniziale);
        testoLeggibile = textService.levaCoda(testoLeggibile, tagFinale);
        return testoLeggibile;
    }

    public String leggeWikiTable(final String wikiTitleGrezzo) {
        return leggeWikiTable(wikiTitleGrezzo, 1);
    }

    public String leggeWikiTable(final String wikiTitleGrezzo, int pos) {
        String testoTabella;
        String testoCompletoPagina = leggeWikiParse(wikiTitleGrezzo);
        String tagEnd = "|}";
        List<String> listaRegex = tableRegex();
        int ini = 0;
        int end = 0;

        testoTabella = testoCompletoPagina;
        for (String tagIni : listaRegex) {
            if (testoCompletoPagina.contains(tagIni)) {
                for (int k = 0; k < pos; k++) {
                    ini = testoCompletoPagina.indexOf(tagIni, end);
                    if (ini < 1) {
                        return VUOTA;
                    }

                    end = testoCompletoPagina.indexOf(tagEnd, ini);
                }
                testoTabella = testoCompletoPagina.substring(ini, end + tagEnd.length());
            }
        }

        return testoTabella.length() == testoCompletoPagina.length() ? VUOTA : testoTabella;
    }

    public List<List<String>> getWikiTable(final String wikiTitleGrezzo) {
        return getWikiTable(wikiTitleGrezzo, 1);
    }

    public List<List<String>> getWikiTable(final String wikiTitleGrezzo, int pos) {
        String testoTable = leggeWikiTable(wikiTitleGrezzo, pos);
        return elaboraTable(testoTable);
    }


    /**
     * Legge una mappa di risorse <br>
     * La mappa NON contiene i titoli <br>
     *
     * @param testoTable testo della table
     *
     * @return mappa delle righe grezze con eventualmente i titoli
     */
    public List<List<String>> elaboraTable(String testoTable) {
        List<List<String>> listaTable = null;
        String[] righeTable = null;
        String tagTable = "\\|-";
        String testoRigaSingola;
        String[] partiRiga;
        List<String> listaRiga;

        //--dopo aver eliminato la testa della tabella, la coda della tabella e i titoli, individua le righe valide
        testoTable = estraeTable(testoTable);
        if (textService.isEmpty(testoTable)) {
            return listaTable;
        }

        righeTable = testoTable.split(tagTable);
        if (righeTable != null && righeTable.length > 0) {
            listaTable = new ArrayList<>();
            for (int k = 0; k < righeTable.length; k++) {
                testoRigaSingola = righeTable[k].trim();
                partiRiga = getParti(testoRigaSingola);
                if (partiRiga != null && partiRiga.length > 0) {
                    listaRiga = new ArrayList<>();
                    for (String value : partiRiga) {
                        if (textService.isValid(value)) {
                            value = value.trim();
                            if (value.startsWith(PIPE)) {
                                value = textService.levaTesta(value, PIPE);
                            }
                            value = value.trim();
                            listaRiga.add(value);
                        }
                    }
                    if (!listaRiga.get(0).equals(ESCLAMATIVO)) {
                        listaTable.add(listaRiga);
                    }
                }
            }
        }

        return listaTable;
    }

    /**
     * Elimina inizio e fine della table <br>
     * Il primo |- dopo l'ultimo ! <br>
     *
     * @param testoTable testo generale della table
     *
     * @return testo effettivo delle sole righe della table
     */
    public String estraeTable(String testoTable) {
        int posLastEsclamativo;

        //--elimina la testa di apertura della table per evitare fuffa
        if (textService.isValid(testoTable)) {
            posLastEsclamativo = testoTable.lastIndexOf(ESCLAMATIVO);
            testoTable = testoTable.substring(posLastEsclamativo);
            testoTable = textService.levaPrimaAncheTag(testoTable, SEP_TABLE);
            testoTable = testoTable.trim();
        }

        //--elimina la coda di chiusura della table per evitare che la suddivisione in righe contenga anche la chiusura della table
        if (textService.isValid(testoTable)) {
            if (testoTable.endsWith(GRAFFA_END)) {
                testoTable = textService.levaCodaDaUltimo(testoTable, GRAFFA_END);
            }
            testoTable = testoTable.trim();
            if (testoTable.endsWith(PIPE)) {
                testoTable = textService.levaCodaDaUltimo(testoTable, PIPE);
            }

            testoTable = testoTable.trim();
            if (testoTable.endsWith(CAPO_REGEX)) {
                testoTable = textService.levaCoda(testoTable, CAPO_REGEX);
            }
        }

        return testoTable;
    }

    private String[] getParti(String testoRigaSingola) {
        List<String> listaGrezza = new ArrayList<>();
        List<String> listaDefinitiva = new ArrayList<>();
        String[] partiRiga;
        String[] sottoParti;
        String tagSort = "data-sort-value";
        String tagBand = "band";
        String tagBandiera = "bandiera";

        testoRigaSingola = textService.trim(testoRigaSingola);

        //--primo tentativo
        partiRiga = testoRigaSingola.split(CAPO);

        //--secondo tentativo
        if (partiRiga != null && partiRiga.length == 1) {
            partiRiga = testoRigaSingola.split(DOPPIO_PIPE_REGEX);
        }

        //--terzo tentativo
        if (partiRiga != null && partiRiga.length == 1) {
            partiRiga = testoRigaSingola.split(DOPPIO_ESCLAMATIVO);
        }

        //--quarto tentativo
        if (partiRiga != null && partiRiga.length == 1) {
            partiRiga = testoRigaSingola.split(CAPO_REGEX);
        }

        //--ricontrollo finale
        for (String singolaParte : partiRiga) {
            if (singolaParte.contains(tagSort)) {
                singolaParte = textService.levaTesta(singolaParte, PIPE);
                singolaParte = textService.levaPrimaDelTag(singolaParte, PIPE);
            }
            if (singolaParte.contains(tagBand) && !singolaParte.contains(tagBandiera)) {
                singolaParte = textService.levaTesta(singolaParte, PIPE);
                sottoParti = singolaParte.split(PIPE_REGEX);
                if (sottoParti.length == 3) {
                    singolaParte = sottoParti[1];
                }
            }
            if (singolaParte.contains(tagBandiera)) {
                singolaParte = textService.levaTesta(singolaParte, PIPE);
                sottoParti = singolaParte.split(PIPE_REGEX);
                if (sottoParti.length == 3) {
                    singolaParte = sottoParti[1];
                }
            }

            singolaParte = textService.trim(singolaParte);
            singolaParte = textService.levaTesta(singolaParte, CAPO_SPLIT);
            singolaParte = textService.levaCoda(singolaParte, CAPO_SPLIT);
            singolaParte = textService.levaTesta(singolaParte, PIPE);
            singolaParte = textService.levaCodaDaPrimo(singolaParte, TAG_REF);
            if (singolaParte.startsWith(DOPPIE_QUADRE_INI) && singolaParte.endsWith(DOPPIE_QUADRE_END)) {
                listaGrezza.add(textService.trim(singolaParte));
            }
            else {
                sottoParti = singolaParte.split(PIPE_REGEX);
                for (String parte : sottoParti) {
                    listaGrezza.add(parte);
                }
            }
        }

        //--leva \n
        //--leva graffe e quadre
        //--leva tag ricorrenti
        //--leva ref e code di testo
        for (String value : listaGrezza) {
            value = textService.trim(value);
            value = textService.setNoDoppieGraffe(value);
            value = textService.setNoDoppieQuadre(value);
            value = htmlService.setNoHtmlTag(value, HTML_CODE);
            value = htmlService.setNoHtmlTag(value, HTML_KBD);
            value = textService.levaPrimaAncheTag(value, PIPE);
            value = textService.levaCodaDaPrimo(value, TAG_REF);
            if (textService.isValid(value)) {
                listaDefinitiva.add(value);
            }
        }

        return listaDefinitiva.toArray(new String[0]);
    }

    protected List<String> tableRegex() {
        List<String> lista = new ArrayList<>();
        //        {|class="wikitable
        lista.add("{|class=\\\"wikitable");
        lista.add("{|class= \\\"wikitable");
        lista.add("{| class=\\\"wikitable");
        lista.add("{| class= \\\"wikitable");
        lista.add("{ |class=\\\"wikitable");
        lista.add("{ |class= \\\"wikitable");
        lista.add("{ | class=\\\"wikitable");
        lista.add("{ | class= \\\"wikitable");
        lista.add("{|class =\\\"wikitable");
        lista.add("{|class = \\\"wikitable");
        lista.add("{| class =\\\"wikitable");
        lista.add("{| class = \\\"wikitable");
        lista.add("{ |class =\\\"wikitable");
        lista.add("{ |class = \\\"wikitable");
        lista.add("{ | class =\\\"wikitable");
        lista.add("{ | class = \\\"wikitable");

        return lista;
    }

}