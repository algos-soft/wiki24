package it.algos.wiki24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.wrapper.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: dom, 20-feb-2022
 * Time: 08:56
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiUtility extends WAbstractService {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GiornoBackend giornoBackend;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoBackend annoBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    public String fixTitolo(String titoloGrezzo) {
        return fixTitolo(VUOTA, titoloGrezzo, 0);
    }

    public String fixTitolo(String wikiTitleBase, String titoloGrezzo) {
        return fixTitolo(wikiTitleBase, titoloGrezzo, 0);
    }

    public String fixTitolo(String titoloGrezzo, int numVoci) {
        return fixTitolo(VUOTA, titoloGrezzo, numVoci);
    }

    public String fixTitolo(String wikiTitleBase, String titoloGrezzo, int numVoci) {
        String paragrafoVisibile = VUOTA;

        if (textService.isValid(wikiTitleBase)) {
            if (!wikiTitleBase.endsWith(SLASH)) {
                wikiTitleBase += SLASH;
            }
            paragrafoVisibile = wikiTitleBase;
        }

        if (textService.isValid(titoloGrezzo)) {
            if (titoloGrezzo.length() > 1) {
                titoloGrezzo = textService.primaMaiuscola(titoloGrezzo);
            }
            paragrafoVisibile += titoloGrezzo;

            if (textService.isValid(wikiTitleBase)) {
                if (queryService.isEsiste(paragrafoVisibile)) {
                    paragrafoVisibile += PIPE;
                    paragrafoVisibile += titoloGrezzo;
                    paragrafoVisibile = textService.setDoppieQuadre(paragrafoVisibile);
                }
                else {
                    paragrafoVisibile = titoloGrezzo;
                }
            }
            paragrafoVisibile = setParagrafo(paragrafoVisibile, numVoci);
        }
        else {
            paragrafoVisibile = "Altre...";
            paragrafoVisibile = setParagrafo(paragrafoVisibile, numVoci);
        }

        return paragrafoVisibile;
    }

    public String fixTitoloLink(String titoloParagrafo, String titoloParagrafoLink, int numVociParagrafo) {
        String paragrafoVisibile = getParagrafoVisibile(titoloParagrafo, titoloParagrafoLink);
        return setParagrafo(paragrafoVisibile, numVociParagrafo);
    }

    public String fixTitoloLinkIncludeOnly(String titoloParagrafo, String titoloParagrafoLink, int numVociParagrafo) {
        String paragrafoVisibile = getParagrafoVisibile(titoloParagrafo, titoloParagrafoLink);
        return setParagrafoIncludeOnly(paragrafoVisibile, numVociParagrafo);
    }

    public String getParagrafoVisibile(String titoloParagrafo, String titoloParagrafoLink) {
        String paragrafoVisibile;

        if (textService.isEmpty(titoloParagrafo)) {
            return VUOTA;
        }

        titoloParagrafo = textService.primaMaiuscola(titoloParagrafo);
        paragrafoVisibile = titoloParagrafo;

        if (textService.isValid(titoloParagrafoLink)) {
            paragrafoVisibile = titoloParagrafoLink;
            paragrafoVisibile += PIPE;
            paragrafoVisibile += titoloParagrafo;
            paragrafoVisibile = textService.setDoppieQuadre(paragrafoVisibile);
        }

        return paragrafoVisibile;
    }

    /**
     * Inserisce un numero in caratteri ridotti <br>
     *
     * @param numero da visualizzare
     *
     * @return testo coi tag html
     */
    public String smallNumero(final int numero) {
        String testo = VUOTA;

        testo += "<span style=\"font-size:70%\">(";
        testo += numero;
        testo += ")</span>";

        return testo;
    }

    public String setParagrafoSub(final String titolo) {
        String paragrafo = VUOTA;

        paragrafo += PARAGRAFO_SUB;
        paragrafo += SPAZIO;
        paragrafo += titolo;
        paragrafo += SPAZIO;
        paragrafo += PARAGRAFO_SUB;
        paragrafo += CAPO;

        return paragrafo;
    }

    public String setParagrafo(final String titolo) {
        return setParagrafo(titolo, 0);
    }

    /**
     * Inserisce un numero in caratteri ridotti <br>
     *
     * @param titolo da inglobare nei tag wiki (paragrafo)
     * @param numero da visualizzare (maggiore di zero)
     *
     * @return testo coi tag html
     */
    public String setParagrafo(final String titolo, final int numero) {
        StringBuffer buffer = new StringBuffer();
        String titoloUpperCase = textService.primaMaiuscola(titolo);

        buffer.append(CAPO);
        buffer.append(PARAGRAFO);
        buffer.append(SPAZIO);
        buffer.append(titoloUpperCase);

        if (numero > 0) {
            buffer.append(SPAZIO);
            buffer.append(smallNumero(numero));
        }
        buffer.append(SPAZIO);
        buffer.append(PARAGRAFO);
        buffer.append(CAPO);

        return buffer.toString();
    }

    public String setParagrafoIncludeOnly(final String titolo, final int numero) {
        StringBuffer buffer = new StringBuffer();
        String titoloUpperCase = textService.primaMaiuscola(titolo);

        buffer.append(CAPO);
        buffer.append(PARAGRAFO_INCLUDE_INI);
        buffer.append(SPAZIO);
        buffer.append(titoloUpperCase);

        if (numero > 0) {
            buffer.append(SPAZIO);
            buffer.append(NO_INCLUDE_INI);
            buffer.append(smallNumero(numero));
            buffer.append(NO_INCLUDE_END);
        }
        buffer.append(SPAZIO);
        buffer.append(PARAGRAFO_INCLUDE_END);
        buffer.append(CAPO);

        return buffer.toString();
    }

    /**
     * Contorna il testo con un wiki bold. <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa regolata secondo la property mediawiki
     */
    public String bold(String stringaIn) {
        String stringaOut = VUOTA;

        if (textService.isValid(stringaIn)) {
            stringaOut = TAG_BOLD;
            stringaOut += stringaIn;
            stringaOut += TAG_BOLD;
        }

        return stringaOut.trim();
    }


    public int getSizeAll(LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappa) {
        int size = 0;

        if (mappa != null) {
            for (String key : mappa.keySet()) {
                size += getSize(mappa.get(key));
            }
        }

        return size;
    }

    public int getSizeAll(Map<String, List<String>> mappa) {
        int size = 0;

        if (mappa != null) {
            for (String key : mappa.keySet()) {
                size += mappa.get(key).size();
            }
        }

        return size;
    }

    public int getSizeAllWrap(Map<String, List<WrapLista>> mappa) {
        int size = 0;

        if (mappa != null) {
            for (String key : mappa.keySet()) {
                size += mappa.get(key) != null ? mappa.get(key).size() : 0;
            }
        }

        return size;
    }


    public int getSize(LinkedHashMap<String, List<String>> mappa) {
        int size = 0;

        if (mappa != null) {
            for (String key : mappa.keySet()) {
                size += mappa.get(key).size();
            }
        }

        return size;
    }

    public String wikiTitleNatiGiorno(String giorno) {
        return natiMortiGiorno("Nati", giorno);
    }

    public String wikiTitleMortiGiorno(String giorno) {
        return natiMortiGiorno("Morti", giorno);
    }


    public String wikiTitleNatiAnno(AnnoWiki anno) {
        return wikiTitleNatiAnno(anno != null ? anno.nome : VUOTA);
    }

    public String wikiTitleNatiAnno(String anno) {
        return natiMortiAnno("Nati", anno);
    }

    public String wikiTitleMortiAnno(AnnoWiki anno) {
        return wikiTitleMortiAnno(anno != null ? anno.nome : VUOTA);
    }

    public String wikiTitleMortiAnno(String anno) {
        return natiMortiAnno("Morti", anno);
    }

    public String wikiTitle(AETypeLista typeCrono, String wikiTitleSimple) {
        return switch (typeCrono) {
            case giornoNascita -> this.wikiTitleNatiGiorno(wikiTitleSimple);
            case giornoMorte -> this.wikiTitleMortiGiorno(wikiTitleSimple);
            case annoNascita -> this.wikiTitleNatiAnno(wikiTitleSimple);
            case annoMorte -> this.wikiTitleMortiAnno(wikiTitleSimple);
            case nomi -> this.wikiTitleNomi(wikiTitleSimple);
            case cognomi -> this.wikiTitleCognomi(wikiTitleSimple);
            default -> VUOTA;
        };
    }


    public String wikiTitleAttivita(String attivitaPlurale) {
        return PATH_ATTIVITA + SLASH + textService.primaMaiuscola(attivitaPlurale);
    }

    public String wikiTitleNazionalita(String nazionalitaPlurale) {
        return PATH_NAZIONALITA + SLASH + textService.primaMaiuscola(nazionalitaPlurale);
    }

    public String wikiTitleNomi(String nomePersona) {
        return PATH_NOMI + textService.primaMaiuscola(nomePersona);
    }

    public String wikiTitleCognomi(String cognomePersona) {
        return PATH_COGNOMI + textService.primaMaiuscola(cognomePersona);
    }

    public String fixSecoloNato(final Bio bio) {
        AnnoWiki anno = annoWikiBackend.findByKey(bio.annoNato);
        return anno != null ? anno.getSecolo() != null ? anno.getSecolo().nome : VUOTA : VUOTA;
    }

    public String fixSecoloMorto(final Bio bio) {
        AnnoWiki anno = annoWikiBackend.findByKey(bio.annoMorto);
        return anno != null ? anno.getSecolo() != null ? anno.getSecolo().nome : VUOTA : VUOTA;
    }

    public String fixMeseNato(final Bio bio) {
        GiornoWiki giorno = giornoWikiBackend.findByKey(bio.giornoNato);
        return giorno != null ? giorno.getMese() != null ? giorno.getMese().nome : VUOTA : VUOTA;
    }

    public String fixMeseMorto(final Bio bio) {
        GiornoWiki giorno = giornoWikiBackend.findByKey(bio.giornoMorto);
        return giorno != null ? giorno.getMese() != null ? giorno.getMese().nome : VUOTA : VUOTA;
    }

    /**
     * I numeri che iniziano (parlato) con vocale richiedono l'apostrofo  <br>
     * Sono:
     * 8
     * 11
     *
     * @param tag         nati/morti
     * @param textMatcher di riferimento
     *
     * @return titolo della pagina wiki
     */
    public String natiMortiGiorno(String tag, String textMatcher) {
        String tagBase = tag + SPAZIO + "il" + SPAZIO;
        String tagSpecifico = tag + SPAZIO + "l'";
        String textPattern = "^8 .+|^11 .+";

        if (textService.isEmpty(textMatcher)) {
            return VUOTA;
        }

        if (regexService.isEsiste(textMatcher, textPattern)) {
            return tagSpecifico + textMatcher;
        }
        else {
            return tagBase + textMatcher;
        }
    }

    /**
     * I numeri che iniziano (parlato) con vocale richiedono l'apostrofo  <br>
     * Sono:
     * 1
     * 11
     * tutti quelli che iniziano con 8
     *
     * @param tag         nati/morti
     * @param textMatcher di riferimento
     *
     * @return titolo della pagina wiki
     */
    private String natiMortiAnno(String tag, String textMatcher) {
        String tagBase = tag + SPAZIO + "nel" + SPAZIO;
        String tagSpecifico = tag + SPAZIO + "nell'";
        String textPattern = "^1$|^11$|^1 *a\\.C\\.|^11 *a\\.C\\.|^8.*";

        if (textService.isEmpty(textMatcher)) {
            return VUOTA;
        }

        if (regexService.isEsiste(textMatcher, textPattern)) {
            return tagSpecifico + textMatcher;
        }
        else {
            return tagBase + textMatcher;
        }
    }

    public String giornoNatoTesta(final Bio bio) {
        return giornoNatoTesta(bio, null);
    }

    public String giornoNatoTesta(final Bio bio, AETypeLink typeLinkCrono) {
        String giornoNato = bio.giornoNato;
        if (textService.isEmpty(giornoNato)) {
            return VUOTA;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }

        return switch (typeLinkCrono) {
            case linkVoce -> textService.setDoppieQuadre(giornoNato);
            case linkLista -> {
                giornoNato = this.wikiTitleNatiGiorno(giornoNato) + PIPE + giornoNato;
                yield textService.setDoppieQuadre(giornoNato);
            }
            case nessunLink -> giornoNato;
        };
    }

    public String giornoNatoCoda(final Bio bio) {
        return giornoNatoCoda(bio, null, WPref.usaSimboliCrono.is(), false);
    }

    public String giornoNatoCoda(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona, boolean usaParentesi) {
        String tagNato = usaIcona ? WPref.simboloNato.getStr() : VUOTA;
        String giornoNatoLinkato;

        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        giornoNatoLinkato = giornoNatoTesta(bio, typeLinkCrono);

        if (textService.isValid(giornoNatoLinkato)) {
            giornoNatoLinkato = textService.isValid(tagNato) ? tagNato + giornoNatoLinkato : giornoNatoLinkato;
            return usaParentesi ? textService.setTonde(giornoNatoLinkato) : giornoNatoLinkato;
        }
        else {
            return VUOTA;
        }
    }

    public String giornoMortoTesta(final Bio bio) {
        return giornoMortoTesta(bio, null);
    }

    public String giornoMortoTesta(final Bio bio, AETypeLink typeLinkCrono) {
        String giornoMorto = bio.giornoMorto;
        if (textService.isEmpty(giornoMorto)) {
            return VUOTA;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }

        return switch (typeLinkCrono) {
            case linkVoce -> textService.setDoppieQuadre(giornoMorto);
            case linkLista -> {
                giornoMorto = this.wikiTitleMortiGiorno(giornoMorto) + PIPE + giornoMorto;
                yield textService.setDoppieQuadre(giornoMorto);
            }
            case nessunLink -> giornoMorto;
        };
    }

    public String giornoMortoCoda(final Bio bio) {
        return giornoMortoCoda(bio, null, WPref.usaSimboliCrono.is(), false);
    }

    public String giornoMortoCoda(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona, boolean usaParentesi) {
        String tagMorto = usaIcona ? WPref.simboloMorto.getStr() : VUOTA;
        String giornoMortoLinkato;

        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        giornoMortoLinkato = giornoMortoTesta(bio, typeLinkCrono);

        if (textService.isValid(giornoMortoLinkato)) {
            giornoMortoLinkato = textService.isValid(tagMorto) ? tagMorto + SPAZIO_NON_BREAKING + giornoMortoLinkato : giornoMortoLinkato;
            return usaParentesi ? textService.setTonde(giornoMortoLinkato) : giornoMortoLinkato;
        }
        else {
            return VUOTA;
        }
    }

    public String annoNatoTesta(final Bio bio) {
        return annoNatoTesta(bio, null);
    }

    public String annoNatoTesta(final Bio bio, AETypeLink typeLinkCrono) {
        String annoNato = bio.annoNato;

        if (textService.isEmpty(annoNato)) {
            return VUOTA;
        }
        if (bio.annoNatoOrd < ANTE_CRISTO_MAX) {
            return annoNato;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }

        if (typeLinkCrono != null) {
            return switch (typeLinkCrono) {
                case linkVoce -> textService.setDoppieQuadre(annoNato);
                case linkLista -> {
                    annoNato = this.wikiTitleNatiAnno(annoNato) + PIPE + annoNato;
                    yield textService.setDoppieQuadre(annoNato);
                }
                case nessunLink -> annoNato;
            };
        }
        else {
            logService.warn(new WrapLog().message(String.format("In linkAnnoNatoTesta manca il typeLink per la bio %s", bio.wikiTitle)));
            return VUOTA;
        }
    }

    public String annoNatoCoda(final Bio bio) {
        return annoNatoCoda(bio, null, WPref.usaSimboliCrono.is(), false);
    }

    public String annoNatoCoda(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona, boolean usaParentesi) {
        String annoNato = bio.annoNato;

        String tagNato = usaIcona ? WPref.simboloNato.getStr() : VUOTA;
        String annoNatoLinkato;

        if (textService.isEmpty(annoNato)) {
            return VUOTA;
        }
        if (bio.annoNatoOrd < ANTE_CRISTO_MAX) {
            return annoNato;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        annoNatoLinkato = annoNatoTesta(bio, typeLinkCrono);

        if (textService.isValid(annoNatoLinkato)) {
            annoNatoLinkato = textService.isValid(tagNato) ? tagNato + annoNatoLinkato : annoNatoLinkato;
            return usaParentesi ? textService.setTonde(annoNatoLinkato) : annoNatoLinkato;
        }
        else {
            return VUOTA;
        }
    }

    public String annoMortoTesta(final Bio bio) {
        return annoMortoTesta(bio, null);
    }

    public String annoMortoTesta(final Bio bio, AETypeLink typeLinkCrono) {
        String annoMorto = bio.annoMorto;

        if (textService.isEmpty(annoMorto)) {
            return VUOTA;
        }
        if (bio.annoMortoOrd < ANTE_CRISTO_MAX) {
            return annoMorto;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }

        if (typeLinkCrono != null) {
            return switch (typeLinkCrono) {
                case linkVoce -> textService.setDoppieQuadre(annoMorto);
                case linkLista -> {
                    annoMorto = this.wikiTitleMortiAnno(annoMorto) + PIPE + annoMorto;
                    yield textService.setDoppieQuadre(annoMorto);
                }
                case nessunLink -> annoMorto;
            };
        }
        else {
            logService.warn(new WrapLog().message(String.format("In linkAnnoMortoTesta manca il typeLink per la bio %s", bio.wikiTitle)));
            return VUOTA;
        }
    }

    public String annoMortoCoda(final Bio bio) {
        return annoMortoCoda(bio, null, WPref.usaSimboliCrono.is(), false);
    }


    public String annoMortoCoda(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona, boolean usaParentesi) {
        String annoMorto = bio.annoMorto;

        String tagMorto = usaIcona ? WPref.simboloMorto.getStr() : VUOTA;
        String annoMortoLinkato;

        if (textService.isEmpty(annoMorto)) {
            return VUOTA;
        }
        if (bio.annoMortoOrd < ANTE_CRISTO_MAX) {
            return annoMorto;
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        annoMortoLinkato = annoMortoTesta(bio, typeLinkCrono);

        if (textService.isValid(annoMortoLinkato)) {
            annoMortoLinkato = textService.isValid(tagMorto) ? tagMorto + SPAZIO_NON_BREAKING + annoMortoLinkato : annoMortoLinkato;
            return usaParentesi ? textService.setTonde(annoMortoLinkato) : annoMortoLinkato;
        }
        else {
            return VUOTA;
        }
    }

    /**
     * Ordina la mappa secondo la chiave
     *
     * @param mappaDisordinata in ingresso
     *
     * @return mappa ordinata, null se mappaDisordinata è null
     */
    public LinkedHashMap<String, List<WrapLista>> sort(final LinkedHashMap<String, List<WrapLista>> mappaDisordinata) {
        LinkedHashMap<String, List<WrapLista>> mappaOrdinata = new LinkedHashMap();
        Object[] listaChiavi;

        try {
            listaChiavi = mappaDisordinata.keySet().toArray();
            Arrays.sort(listaChiavi);
            for (Object chiave : listaChiavi) {
                mappaOrdinata.put((String) chiave, mappaDisordinata.get(chiave));
            }
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        return mappaOrdinata;
    }

    /**
     * Primo giorno del mese col carattere ° corretto <br>
     *
     * @param primoGiorno in ingresso
     *
     * @return primo giorno con carattere corretto
     */
    public String fixPrimoMese(final String primoGiorno) {
        if (textService.isEmpty(primoGiorno)) {
            return VUOTA;
        }

        if (primoGiorno.startsWith("1" + SPAZIO)) {
            return primoGiorno.replace("1", "1" + CHAR_MASCULINE_ORDINAL_INDICATOR);
        }
        else {
            return primoGiorno.replace(CHAR_DEGREE_SIGN, CHAR_MASCULINE_ORDINAL_INDICATOR);
        }
    }


    public boolean isDiacritica(final String parola) {
        return textService.isValid(parola) ? !StringUtils.stripAccents(parola).equals(parola) : false;
    }

    public String fixDiacritica(final String parola) {
        return StringUtils.stripAccents(parola);
    }

    /**
     * Controlla che la collection 'Bio' del database mongoDB sia valida <br>
     * Per essere valida deve avere un numero di biografie che sia una frazione accettabile di quelle presenti sul server wiki <br>
     * Il valore di accettabilità (in percentuale) è fissato da una costante <br>
     */
    public WResult checkValiditaDatabase() {
        WResult result = WResult.crea();
        LinkedHashMap mappa = new LinkedHashMap();
        String categoryTitle = WPref.categoriaBio.getStr();
        int numPagesServerWiki = queryService.getSizeCat(categoryTitle);
        int bioMongoDB = bioBackend.count();

        mappa.put(KEY_MAP_VOCI_SERVER_WIKI, numPagesServerWiki);
        mappa.put(KEY_MAP_VOCI_DATABASE_MONGO, bioMongoDB);
        result.mappa(mappa);

        double attuale = mathService.percentuale(bioMongoDB, numPagesServerWiki);
        BigDecimal decimal = WPref.percentualeMinimaBiografie.getDecimal();
        double minimo = decimal.doubleValue();
        return result.validoWiki(attuale > minimo);
    }

    public String estraeTestoModulo(String testoPagina) {
        String testoModulo = testoPagina;
        String tagLocal = "local tabella = ";

        if (textService.isValid(testoPagina)) {
            if (testoModulo.contains(tagLocal)) {
                testoModulo = textService.levaTestoPrimaDiEscluso(testoModulo, tagLocal);
            }
            else {
                testoModulo = textService.levaTestoPrimaDiEscluso(testoModulo, TAG_RETURN);
            }
            testoModulo = textService.levaTesta(testoModulo, GRAFFA_INI);
            testoModulo = textService.levaCodaDaPrimo(testoModulo, GRAFFA_END);
        }

        return testoModulo;
    }

    public LinkedHashMap<String, List<WrapLista>> creaMappaSottopagina(List<WrapLista> listaAll) {
        LinkedHashMap<String, List<WrapLista>> mappaAlfabetica = new LinkedHashMap<>();
        String key;
        List<WrapLista> lista;

        if (listaAll == null) {
            return mappaAlfabetica;
        }

        if (WPref.usaParagrafiGiorniSotto.is()) {
            for (WrapLista wrap : listaAll) {
                key = wrap.titoloSottoParagrafo;
                if (mappaAlfabetica.containsKey(key)) {
                    lista = mappaAlfabetica.get(key);
                    lista.add(wrap);
                }
                else {
                    lista = new ArrayList<>();
                    lista.add(wrap);
                    mappaAlfabetica.put(key, lista);
                }
            }
        }
        else {
            mappaAlfabetica.put(VUOTA, listaAll);
        }

        return arrayService.sort(mappaAlfabetica);
    }

    public WrapDueStringhe creaWrapUguale(final String riga) {
        WrapDueStringhe wrap = null;
        String prima;
        String seconda;
        String[] parti = textService.isValid(riga) ? riga.split(UGUALE) : null;

        if (parti != null && parti.length == 2) {
            prima = parti[0].trim();
            seconda = parti[1].trim();

            wrap = new WrapDueStringhe(prima, seconda);
        }

        return wrap != null ? wrap : new WrapDueStringhe(VUOTA, VUOTA);
    }

    public WrapDueStringhe creaWrapUgualePulito(final String riga) {
        WrapDueStringhe wrap = creaWrapUguale(riga);
        String prima = wrap.prima;
        String seconda = wrap.seconda;

        prima = textService.setNoDoppiApici(prima);
        prima = textService.setNoQuadre(prima);
        prima = textService.setNoDoppiApici(prima);
        wrap.prima = prima;

        seconda = textService.setNoDoppiApici(seconda);
        seconda = textService.setNoQuadre(seconda);
        seconda = textService.setNoDoppiApici(seconda);
        wrap.seconda = seconda;

        return wrap;
    }

    public String getDecade(final String annoIn) {
        String decade = VUOTA;
        String anno;
        char character;
        char character2;

        if (textService.isEmpty(annoIn)) {
            return VUOTA;
        }
        anno = annoIn;
        character = anno.charAt(anno.length() - 2);
        character2 = anno.charAt(anno.length() - 1);
        if (character2 == 48) {
            if (character == 48) {
                character += 9;
            }
            else {
                character -= 1;
            }
        }

        decade = switch (character) {
            case 48 -> "1-10";
            case 49 -> "11-20";
            case 50 -> "21-30";
            case 51 -> "31-40";
            case 52 -> "41-50";
            case 53 -> "51-60";
            case 54 -> "61-70";
            case 55 -> "71-80";
            case 56 -> "81-90";
            case 57 -> "91-00";
            default -> VUOTA;
        };

        return decade;
    }

}
