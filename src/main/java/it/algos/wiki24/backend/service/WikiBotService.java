package it.algos.wiki24.backend.service;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import static it.algos.wiki24.backend.service.WikiApiService.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.*;
import org.bson.conversions.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 10-mag-2021
 * Time: 14:06
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AWikiBotService.class); <br>
 * 3) @Autowired public AWikiBotService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiBotService {

    @Inject
    TextService textService;

    @Inject
    HtmlService htmlService;

    public static final String TAG_BIO = "Bio";

    public static final String CATEGORIA = "Categoria:";

    public static final String WIKI = "https://it.wikipedia.org/w/api.php?&format=json&formatversion=2&action=query";

//    public static final String WIKI_QUERY = WIKI + "&rvslots=main&prop=info|revisions&rvprop=content|ids|flags|timestamp|user|userid|comment|size&titles=";

    public static final String WIKI_PARSE = "https://it.wikipedia.org/w/api.php?action=parse&prop=wikitext&formatversion=2&format=json&page=";

    public static final String WIKI_QUERY_TITLES = WIKI + "&rvslots=main&prop=revisions&rvprop=content|ids|timestamp&titles=";

    public static final String WIKI_QUERY_PAGEIDS = WIKI + "&rvslots=main&prop=revisions&rvprop=content|ids|timestamp&pageids=";

    public static final String WIKI_QUERY_TIMESTAMP = WIKI + "&prop=revisions&rvprop=timestamp&pageids=";

    public static final String WIKI_QUERY_CATEGORY = WIKI + "&list=categorymembers&cmtitle=" + CATEGORIA;

    public static final String WIKI_QUERY_CAT_LIMIT_USER = "&cmlimit=500";

    public static final String WIKI_QUERY_CAT_LIMIT_BOT = "&cmlimit=5000";

    public static final String WIKI_QUERY_CAT_CONTINUE = "&cmcontinue=";

    public static final String WIKI_QUERY_LIST_CONTINUE = "&apcontinue=";

    public static final String WIKI_QUERY_CAT_TYPE = "&cmtype=";

    public static final String WIKI_QUERY_CAT_PROP = "&cmprop=";

    public static final String WIKI_QUERY_CAT_TOTALE = WIKI + "&prop=categoryinfo&titles=" + CATEGORIA;

    public static final String WIKI_QUERY_USER = "&assert=user";

    public static final String WIKI_QUERY_BOT = "&assert=bot";

    private static final LocalDateTime MONGO_TIME_ORIGIN = LocalDateTime.of(2000, 1, 1, 0, 0);

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiApiService wikiApiService;


    /**
     * Elimina un valore strano trovato (ed invisibile)
     * ATTENZIONE: non è uno spazio vuoto !
     * Trattasi del carattere: C2 A0 ovvero U+00A0 ovvero NO-BREAK SPACE
     * Non viene intercettato dal comando Java TRIM()
     */
    public String regBreakSpace(String valoreIn) {
        String valoreOut = valoreIn;
        String strano = " ";   //NON cancellare: sembra uno spazio, ma è un carattere invisibile

        if (valoreIn.startsWith(strano)) {
            valoreOut = valoreIn.substring(1);
        }

        if (valoreIn.endsWith(strano)) {
            valoreOut = valoreIn.substring(0, valoreIn.length() - 1);
        }

        return valoreOut.trim();
    }


    /**
     * Elimina gli eventuali contenuti IN CODA che non devono essere presi in considerazione <br>
     * Restituisce un valore GREZZO che deve essere ancora elaborato <br>
     * <p>
     * Tag chiave di troncature sempre valide:
     * REF = "<ref"
     * NOTE = "<!--"
     * GRAFFE = "{{"
     * UGUALE = "="
     * CIRCA = "circa";
     * ECC = "ecc."
     * INTERROGATIVO = "?"
     * <p>
     * Tag chiave di troncatura opzionali a seconda del parametro:
     * PARENTESI = "("
     * VIRGOLA = ","
     *
     * @param valorePropertyTmplBioServer testo originale proveniente dalla property tmplBioServer della entity Bio
     *
     * @return valore grezzo troncato dopo alcuni tag chiave (<ref>, {{, ecc.) <br>
     */
    public String troncaDopoTag(String valorePropertyTmplBioServer, boolean puntoAmmesso) {
        String valoreGrezzo = valorePropertyTmplBioServer.trim();

        if (textService.isEmpty(valorePropertyTmplBioServer)) {
            return VUOTA;
        }

        //--solo date certe ed esatte
        //        if (valoreGrezzo.contains(SPAZIO + CIRCA)) {
        //            return VUOTA;
        //        }

        if (valorePropertyTmplBioServer.equals(PUNTO_INTERROGATIVO) && puntoAmmesso) {
            return PUNTO_INTERROGATIVO;
        }

        //        valoreGrezzo = textService.levaDopoRef(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoTagRef(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoNote(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoGraffe(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoWiki(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoUguale(valoreGrezzo);
        //        //        valoreGrezzo = this.levaDopoCirca(valoreGrezzo); //solo per nomi e cognomi
        //        valoreGrezzo = textService.levaCoda(valoreGrezzo, CIRCA);//solo per date
        //        valoreGrezzo = textService.levaDopoEccetera(valoreGrezzo);
        //        valoreGrezzo = textService.levaDopoInterrogativo(valoreGrezzo);
        if (valoreGrezzo.endsWith("ca.")) {
            valoreGrezzo = textService.levaCodaDaUltimo(valoreGrezzo, "ca.");
        }

        //--solo date singole
        //        if (valoreGrezzo.contains(SLASH)) {
        //            return VUOTA;
        //        }
        if (valoreGrezzo.contains(" o ")) {
            return VUOTA;
        }
        if (valoreGrezzo.contains(" oppure ")) {
            return VUOTA;
        }

        valoreGrezzo = textService.setNoQuadre(valoreGrezzo);
        return valoreGrezzo.trim();
    }

    //    /**
    //     * Elimina gli eventuali contenuti IN CODA che non devono essere presi in considerazione <br>
    //     * Restituisce un valore GREZZO che deve essere ancora elaborato <br>
    //     * <p>
    //     * Tag chiave di contenuti che invalidano il valore:
    //     * UGUALE = "="
    //     * INTERROGATIVO = "?"
    //     * ECC = "ecc."
    //     *
    //     * @param valorePropertyTmplBioServer testo originale proveniente dalla property tmplBioServer della entity Bio
    //     *
    //     * @return valore grezzo ammesso
    //     */
    //    public String fixElimina(String valorePropertyTmplBioServer) {
    //        String valoreGrezzo = valorePropertyTmplBioServer.trim();
    //
    //        if (textService.isEmpty(valorePropertyTmplBioServer)) {
    //            return VUOTA;
    //        }
    //
    //        if (valoreGrezzo.endsWith(ECC)) {
    //            return VUOTA;
    //        }
    //        if (valoreGrezzo.endsWith(PUNTO_INTERROGATIVO)) {
    //            return VUOTA;
    //        }
    //        if (valoreGrezzo.endsWith(UGUALE)) {
    //            return VUOTA;
    //        }
    //        if (valoreGrezzo.endsWith(PUNTO_INTERROGATIVO + PARENTESI_TONDA_END)) {
    //            return VUOTA;
    //        }
    //
    //        return valoreGrezzo.trim();
    //    }

    //    /**
    //     * Elimina gli eventuali contenuti IN CODA che non devono essere presi in considerazione <br>
    //     * Restituisce un valore GREZZO che deve essere ancora elaborato <br>
    //     * <p>
    //     * Tag chiave di troncature sempre valide:
    //     * REF = "<ref"
    //     * REF = ""{{#tag:ref""
    //     * NOTE = "<!--"
    //     * GRAFFE = "{{"
    //     * NO WIKI = "<nowiki>"
    //     *
    //     * @param valorePropertyTmplBioServer testo originale proveniente dalla property tmplBioServer della entity Bio
    //     *
    //     * @return valore grezzo troncato dopo alcuni tag chiave (<ref>, {{, ecc.) <br>
    //     */
    //    public String fixDopo(String valorePropertyTmplBioServer) {
    //        String valoreGrezzo = valorePropertyTmplBioServer.trim();
    //
    //        if (textService.isEmpty(valorePropertyTmplBioServer)) {
    //            return VUOTA;
    //        }
    //
    //        valoreGrezzo = textService.levaDopoRef(valoreGrezzo);
    //        valoreGrezzo = textService.levaDopoTagRef(valoreGrezzo);
    //        valoreGrezzo = textService.levaDopoNote(valoreGrezzo);
    //        valoreGrezzo = textService.levaDopoGraffe(valoreGrezzo);
    //        valoreGrezzo = textService.levaDopoHtml(valoreGrezzo);
    //        valoreGrezzo = textService.levaDopoWiki(valoreGrezzo);
    //        valoreGrezzo = textService.setNoQuadre(valoreGrezzo);
    //        if (valoreGrezzo.startsWith(PARENTESI_TONDA_INI)) {
    //            valoreGrezzo = valoreGrezzo.replaceAll(PARENTESI_TONDA_INI_REGEX, VUOTA);
    //            valoreGrezzo = valoreGrezzo.replaceAll(PARENTESI_TONDA_END_REGEX, VUOTA);
    //
    //        }
    //        else {
    //            valoreGrezzo = textService.levaDopoParentesiIni(valoreGrezzo);
    //        }
    //
    //        return valoreGrezzo.trim();
    //    }

    //    public String levaDopoCirca(final String testoIn) {
    //        String testoOut = testoIn;
    //        String tag;
    //
    //        if (textService.isValid(testoOut)) {
    //            testoOut = StringUtils.stripEnd(testoIn, SPAZIO);
    //            tag = SPAZIO + CIRCA;
    //            if (testoOut.contains(tag)) {
    //                testoOut = testoOut.substring(0, testoOut.indexOf(tag));
    //                testoOut = StringUtils.stripEnd(testoOut, SPAZIO);
    //            }
    //            else {
    //                testoOut = testoIn;
    //            }
    //        }
    //
    //        return testoOut;
    //    }

    //    /**
    //     * Recupera il testo di una singola pagina dalla risposta alla query <br>
    //     * La query è la richiesta di una sola singola pagina <br>
    //     * <p>
    //     * 21 parametri
    //     * 10 generali
    //     * 8 revisions
    //     * 3 slots/main
    //     *
    //     * @param rispostaDellaQuery in ingresso
    //     *
    //     * @return testo della prima pagina
    //     */
    //    public String estraeTestoPaginaWiki(final String rispostaDellaQuery) {
    //        String testoPagina = VUOTA;
    //        JSONObject jsonPageZero = jSonService.getObjectPage(rispostaDellaQuery);
    //
    //        if (jsonPageZero != null) {
    //            testoPagina = this.getContent(jsonPageZero);
    //        }
    //
    //        return testoPagina;
    //    }


    /**
     * Recupera il contenuto testuale dal testo JSON di una singola pagina <br>
     * 21 parametri
     * 10 generali
     * 8 revisions
     * 3 slots/main
     *
     * @param paginaTextJSON in ingresso
     *
     * @return testo della pagina wiki
     */
    public String getContent(JSONObject paginaTextJSON) {
        String textContent = VUOTA;
        JSONArray arrayRevisions;
        JSONObject objectRevisions = null;
        JSONObject objectSlots;
        JSONObject objectMain = null;

        if (paginaTextJSON == null) {
            return null;
        }

        //--parametri revisions
        if (paginaTextJSON.get(REVISIONS) != null && paginaTextJSON.get(REVISIONS) instanceof JSONArray) {
            arrayRevisions = (JSONArray) paginaTextJSON.get(REVISIONS);
            if (arrayRevisions != null && arrayRevisions.size() > 0 && arrayRevisions.get(0) instanceof JSONObject) {
                objectRevisions = (JSONObject) arrayRevisions.get(0);
            }
        }

        //--parametri slots/main -> content
        if (objectRevisions != null && objectRevisions.get(SLOTS) != null && objectRevisions.get(SLOTS) instanceof JSONObject) {
            objectSlots = (JSONObject) objectRevisions.get(SLOTS);
            if (objectSlots.get(MAIN) != null && objectSlots.get(MAIN) instanceof JSONObject) {
                objectMain = (JSONObject) objectSlots.get(MAIN);
            }
        }

        if (objectMain != null && objectMain.get(CONTENT) != null) {
            textContent = (String) objectMain.get(CONTENT);
        }

        return textContent;
    }


    /**
     * Regola l'url per interrogare una pagina wiki <br>
     * Recupera spazio e caratteri strani nel titolo <br>
     * Aggiunge in testa il prefisso della API Mediawiki <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return webUrl completo
     */
    public String webUrlQuery(final String wikiTitleGrezzo) {
        return WIKI_QUERY + wikiApiService.fixWikiTitle(wikiTitleGrezzo);
    }

    /**
     * Regola l'url per interrogare una pagina wiki <br>
     * Recupera spazio e caratteri strani nel titolo <br>
     * Aggiunge in testa il prefisso della API Mediawiki <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return webUrl completo
     */
    public String webUrlQueryTitles(final String wikiTitleGrezzo) {
        return WIKI_QUERY_TITLES + wikiApiService.fixWikiTitle(wikiTitleGrezzo);
    }

    /**
     * Regola l'url per interrogare una pagina wiki <br>
     * Recupera spazio e caratteri strani nel titolo <br>
     * Aggiunge in testa il prefisso della API Mediawiki <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return webUrl completo
     */
    public String webUrlParse(final String wikiTitleGrezzo) {
        return WIKI_PARSE + wikiApiService.fixWikiTitle(wikiTitleGrezzo);
    }

    //    /**
    //     * Controlla (come anonymous) l'esistenza di una pagina/categoria wiki <br>
    //     *
    //     * @param wikiSimplePageCategoryTitle della pagina/categoria wiki
    //     *
    //     * @return true se esiste, false se non esiste
    //     */
    //    public WResult isEsisteResult(final String wikiSimplePageCategoryTitle) {
    //        WResult resultWiki;
    //        WResult resultWeb = null;
    //        String wikiTitleElaborato;
    //        String rispostaDellaQuery;
    //        JSONObject objectJson = null;
    //        String wikiText;
    //        String wikiBio;
    //        long pageId = 0;
    //
    //        if (wikiSimplePageCategoryTitle == null) {
    //            resultWiki = WResult.errato(NULL_WIKI_TITLE);
    //            resultWiki.setWebTitle(null);
    //            return resultWiki;
    //        }
    //        if (textService.isEmpty(wikiSimplePageCategoryTitle)) {
    //            resultWiki = WResult.errato(ERROR_WIKI_TITLE);
    //            resultWiki.setWebTitle(VUOTA);
    //            return resultWiki;
    //        }
    //        wikiTitleElaborato = wikiSimplePageCategoryTitle.replaceAll(SPAZIO, UNDERSCORE);
    //
    //        rispostaDellaQuery = webService.legge(WIKI_PARSE + wikiTitleElaborato);
    //        //        resultWeb = (WResult)webService.legge(WIKI_PARSE + wikiTitleElaborato);
    //        resultWeb = WResult.crea().webTitle(wikiSimplePageCategoryTitle).wikiTitle(wikiTitleElaborato);
    //
    //        //        rispostaDellaQuery = resultWeb.isValido() ? resultWeb.getResponse() : VUOTA;
    //        //        if (textService.isValid(rispostaDellaQuery)) {
    //        objectJson = jSonService.getObjectJSON(rispostaDellaQuery);
    //        if (objectJson == null) {
    //            return resultWeb;
    //        }
    //        else {
    //            if (objectJson.get(JSON_ERROR) != null) {
    //                resultWiki = WResult.errato(ERROR_WIKI_PAGINA);
    //                resultWiki.setUrlRequest(resultWeb.getUrlRequest());
    //                resultWiki.setResponse(rispostaDellaQuery);
    //                return resultWiki;
    //            }
    //            else {
    //                JSONObject parse = (JSONObject) objectJson.get("parse");
    //                pageId = (Long) parse.get("pageid");
    //                resultWeb.setLongValue(pageId);
    //                wikiText = (String) parse.get("wikitext");
    //                resultWeb.setWikiText(wikiText);
    //                wikiBio = wikiApi.estraeTmpl(wikiText, "Bio");
    //                resultWeb.setWikiBio(wikiBio);
    //                return resultWeb;
    //            }
    //        }
    //        //        }
    //        //        else {
    //        //            return resultWeb;
    //        //        }
    //    }

    //    /**
    //     * Controlla (come anonymous) l'esistenza di una pagina/categoria wiki <br>
    //     *
    //     * @param wikiSimplePageCategoryTitle della pagina/categoria wiki
    //     *
    //     * @return true se esiste, false se non esiste
    //     */
    //    public boolean isEsiste(final String wikiSimplePageCategoryTitle) {
    //        WResult result = isEsisteResult(wikiSimplePageCategoryTitle);
    //        return result.isValido();
    //    }

    //    /**
    //     * Controlla (come anonymous) l'esistenza di una categoria wiki <br>
    //     *
    //     * @param wikiSimpleCategoryTitle della categoria wiki
    //     *
    //     * @return true se esiste, false se non esiste
    //     */
    //    public boolean isEsisteCat(final String wikiSimpleCategoryTitle) {
    //        String catTitle = wikiSimpleCategoryTitle.startsWith(CATEGORIA) ? wikiSimpleCategoryTitle : CATEGORIA + wikiSimpleCategoryTitle;
    //        return isEsiste(catTitle);
    //    }

    /**
     * Legge una lista di pageid di una categoria wiki <br>
     * Se non si mette 'cmlimit' restituisce 10 pagine <br>
     * Valore massimo di 'cmlimit' (come user) 500 pagine <br>
     * Il valore massimo (come user) di 'cmlimit' è 20 <br>
     * La query restituisce SOLO pageid <br>
     *
     * @param catTitle da cui estrarre le pagine
     *
     * @return lista di pageid
     */
    public List<Long> getLongCat(final String catTitle) {
        //        return getLongCat(catTitle, null);
        return null;
    }

    //    /**
    //     * Legge la pagina web <br>
    //     * Interpreta la response ricevuta <br>
    //     * Se è falsa, non fa nulla e rinvia la response falsa <br>
    //     * Se è valida, conta le pagine e regola il valore numerico <br>
    //     * Se le pagine sono 0, mette a false la result <br>
    //     *
    //     * @param catTitle  da cui estrarre le pagine
    //     * @param urlDomain da cui estrarre le pagine
    //     *
    //     * @return result della query
    //     */
    //    public WResult readCategory(final String catTitle, final String urlDomain) {
    //        //        WResult result = webService.legge(urlDomain);
    //        WResult result = null;
    //        JSONArray jsonPagine = null;
    //        boolean valido = result != null && result.isValido();
    //        int totalePagine;
    //
    //        if (valido) {
    //            //            jsonPagine = jSonService.getJsonPagine(result.getResponse());
    //            totalePagine = jsonPagine != null ? jsonPagine.size() : 0;
    //            valido = totalePagine > 0;
    //            result.setIntValue(totalePagine);
    //            result.setValido(valido);
    //            //            result.setMessage(valido ? VUOTA : String.format("%s", NO_PAGES_CAT, catTitle));
    //        }
    //        else {
    //            //            result.setMessage(String.format("%s", NO_CAT, urlDomain));
    //        }
    //
    //        return result;
    //    }

    /**
     * Legge una lista di titles di una categoria wiki <br>
     * Se non si mette 'cmlimit' restituisce 10 pagine <br>
     * Valore massimo di 'cmlimit' (come user) 500 pagine <br>
     * Il valore massimo (come user) di 'cmlimit' è 20 <br>
     * La query restituisce SOLO pageid <br>
     *
     * @param catTitle da cui estrarre le pagine
     *
     * @return lista di titles
     */
    public List<String> getTitleCat(final String catTitle) {
        //        return getTitleCat(catTitle, null);
        return null;
    }


    /**
     * Recupera un lista di 'pageid'' dal testo JSON di risposta ad una query <br>
     *
     * @param jsonPagine in ingresso
     *
     * @return array di 'pageid'
     */
    private List<Long> getListaLongCategoria(JSONArray jsonPagine) {
        List<Long> lista = new ArrayList<>();
        long pageid;

        if (jsonPagine != null && jsonPagine.size() > 0) {
            for (Object obj : jsonPagine) {
                pageid = (long) ((JSONObject) obj).get(PAGE_ID);
                lista.add(pageid);
            }
        }

        return lista;
    }


    /**
     * Recupera un lista di 'title'' dal testo JSON di risposta ad una query <br>
     *
     * @param jsonPagine in ingresso
     *
     * @return array di 'title'
     */
    private List<String> getListaTitleCategoria(JSONArray jsonPagine) {
        List<String> lista = new ArrayList<>();
        String title;

        if (jsonPagine != null && jsonPagine.size() > 0) {
            for (Object obj : jsonPagine) {
                title = (String) ((JSONObject) obj).get(TITLE);
                lista.add(title);
            }
        }

        return lista;
    }

    //    /**
    //     * Legge (come anonymous) le info di una categoria wiki <br>
    //     * Controlla la validità della response <br>
    //     *
    //     * @param categoryTitle da controllare
    //     *
    //     * @return numero di pagine (subcategorie escluse)
    //     */
    //    public WResult getInfoCategoria(final String categoryTitle) {
    //        WResult result = null;
    //        String catTitleUnderscored;
    //        String catTitle;
    //        int totale;
    //        String rispostaDellaQuery;
    //        String webUrl;
    //
    //        if (categoryTitle == null) {
    //            result = WResult.errato(NULL_WIKI_TITLE);
    //            result.setWebTitle(null);
    //            return result;
    //        }
    //        if (textService.isEmpty(categoryTitle)) {
    //            result = WResult.errato(ERROR_WIKI_TITLE);
    //            result.setWebTitle(VUOTA);
    //            return result;
    //        }
    //
    //        catTitleUnderscored = categoryTitle.replaceAll(SPAZIO, UNDERSCORE);
    //        catTitle = catTitleUnderscored.startsWith(CATEGORIA) ? catTitleUnderscored : CATEGORIA + catTitleUnderscored;
    //        result = isEsisteResult(catTitle);
    //
    //        if (result.isValido()) {
    //            webUrl = WIKI_QUERY_CAT_TOTALE + catTitleUnderscored;
    //            //            result = webService.legge(webUrl);
    //            result.setWebTitle(categoryTitle);
    //            result.setWikiTitle(catTitleUnderscored);
    //        }
    //        else {
    //            result.setWebTitle(categoryTitle);
    //            result.setErrorCode(ERROR_WIKI_CATEGORIA);
    //            result.setErrorMessage(ERROR_WIKI_CATEGORIA);
    //            return result;
    //        }
    //
    //        rispostaDellaQuery = result.getResponse();
    //        //        JSONObject jsonPageZero = jSonService.getObjectPage(rispostaDellaQuery);
    //        //        if (isMissing(jsonPageZero)) {
    //        //            return AResult.errato();
    //        //        }
    //
    //        //        JSONObject categoryInfo = (JSONObject) jsonPageZero.get(CATEGORY_INFO);
    //        //        if (categoryInfo != null && categoryInfo.get(CATEGORY_PAGES) != null) {
    //        //            totale = ((Long) categoryInfo.get(CATEGORY_PAGES)).intValue();
    //        //            result.setIntValue(totale);
    //        //        }
    //
    //        return result;
    //    }


    /**
     * Recupera il tag per le categorie successive <br>
     * Nella forma 'page|token|pageid' <br>
     *
     * @param rispostaDellaQuery in ingresso
     *
     * @return token e prossima pagina
     */
    private String getContinuaCategoria(String rispostaDellaQuery) {
        String continua = VUOTA;
        JSONObject objectAll = (JSONObject) JSONValue.parse(rispostaDellaQuery);
        JSONObject jsonContinue;

        //--recupera il valore del parametro
        if (objectAll != null && objectAll.get(CONTINUE) != null && objectAll.get(CONTINUE) instanceof JSONObject) {
            jsonContinue = (JSONObject) objectAll.get(CONTINUE);
            continua = (String) jsonContinue.get(CONTINUE_CM);
        }

        return continua;
    }

    //    /**
    //     * Legge (come user) una pagina dal server wiki <br>
    //     * Usa una API con action=query SENZA bisogno di loggarsi <br>
    //     * Recupera dalla urlRequest tutti i dati della pagina <br>
    //     * Estrae il testo in linguaggio wiki visibile <br>
    //     * Elaborazione della urlRequest leggermente più complessa di leggeParse <br>
    //     * Tempo di download leggermente più corto di leggeParse <br>
    //     * Metodo base per tutte le API in semplice lettura <br>
    //     *
    //     * @param wikiTitle della pagina wiki
    //     *
    //     * @return risultato col testo completo (visibile) della pagina wiki
    //     */
    //    public String leggeQueryTxt(final String wikiTitle) {
    //        return leggeQuery(wikiTitle).getResponse();
    //    }

    //    public List<WrapTime> projectionWrapTime() {
    //        List<WrapTime> listaWrap = new ArrayList();
    //        long pageId;
    //        Date dateLastServer;
    //        LocalDateTime lastServer;
    //        MongoCollection collection = mongoService.getCollection("bio");
    //
    //        Bson bSort = Sorts.ascending(FIELD_NAME_PAGE_ID).toBsonDocument();
    //        Bson projection = Projections.fields(Projections.include(FIELD_NAME_PAGE_ID, "lastServer"), Projections.excludeId());
    //        FindIterable<Document> documents = collection.find().projection(projection).sort(bSort);
    //
    //        for (var singolo : documents) {
    //            pageId = singolo.get(FIELD_NAME_PAGE_ID, Long.class);
    //            dateLastServer = singolo.get("lastServer", Date.class);
    //            lastServer = dateService.dateToLocalDateTime(dateLastServer);
    //            listaWrap.add(new WrapTime(pageId, lastServer));
    //        }
    //
    //        return listaWrap;
    //    }

    public String getTmplBio(final String wikiTitleGrezzo) {
        String testoCompletoPagina = wikiApiService.legge(wikiTitleGrezzo);
        return estraeTmpl(testoCompletoPagina, "Bio");
    }

    /**
     * Legge il testo del template Bio da una voce <br>
     * Esamina solo il PRIMO template BIO che trova <br>
     * Gli estremi sono COMPRESI <br>
     * <p>
     * Recupera il tag iniziale con o senza ''Template''
     * Recupera il tag iniziale con o senza primo carattere maiuscolo
     * Recupera il tag finale di chiusura con o senza ritorno a capo precedente
     * Controlla che non esistano doppie graffe dispari all'interno del template
     *
     * @param testoCompletoPagina in ingresso
     *
     * @return template completo di doppie graffe iniziali e finali
     */
    public String estraeTmplBio(final String testoCompletoPagina) {
        return wikiApiService.estraeTmpl(testoCompletoPagina, "Bio");
    }


    /**
     * Estrae il testo di un template dal testo completo di una voce wiki <br>
     * Esamina il PRIMO template che trova <br>
     * Gli estremi sono COMPRESI <br>
     * <p>
     * Recupera il tag iniziale con o senza ''Template''
     * Recupera il tag iniziale con o senza primo carattere maiuscolo
     * Recupera il tag finale di chiusura con o senza ritorno a capo precedente
     * Controlla che non esistano doppie graffe dispari all'interno del template
     *
     * @return template completo di doppie graffe iniziali e finali
     */
    public String estraeTmpl(final String testoPagina, String tag) {
        String templateTxt = VUOTA;
        boolean continua = false;
        String patternTxt = "";
        Pattern patttern = null;
        Matcher matcher = null;
        int posIni;
        int posEnd;
        String tagIniTemplate = VUOTA;

        // controllo di congruità
        if (textService.isValid(testoPagina) && textService.isValid(tag)) {
            // patch per nome template minuscolo o maiuscolo
            // deve terminare con 'aCapo' oppure 'return' oppure 'tab' oppure '|'(pipe) oppure 'spazio'(u0020)
            if (tag.equals("Bio")) {
                tag = "[Bb]io[\n\r\t\\|\u0020(grafia)]";
            }

            // Create a Pattern text
            patternTxt = "\\{\\{(Template:)?" + tag;

            // Create a Pattern object
            patttern = Pattern.compile(patternTxt);

            // Now create matcher object.
            matcher = patttern.matcher(testoPagina);
            if (matcher.find() && matcher.groupCount() > 0) {
                tagIniTemplate = matcher.group(0);
            }

            // controlla se esiste una doppia graffa di chiusura
            // non si sa mai
            if (!tagIniTemplate.equals("")) {
                posIni = testoPagina.indexOf(tagIniTemplate);
                posEnd = testoPagina.indexOf(DOPPIE_GRAFFE_END, posIni);
                templateTxt = testoPagina.substring(posIni);
                if (posEnd != -1) {
                    continua = true;
                }
            }

            // cerco la prima doppia graffa che abbia all'interno
            // lo stesso numero di aperture e chiusure
            // spazzola il testo fino a pareggiare le graffe
            if (continua) {
                templateTxt = chiudeTmpl(templateTxt);
            }
        }

        return templateTxt;
    }


    /**
     * Chiude il template <br>
     * <p>
     * Il testo inizia col template, ma prosegue (forse) anche oltre <br>
     * Cerco la prima doppia graffa che abbia all'interno lo stesso numero di aperture e chiusure <br>
     * Spazzola il testo fino a pareggiare le graffe <br>
     * Se non riesce a pareggiare le graffe, ritorna una stringa nulla <br>
     *
     * @param templateTxt da spazzolare
     *
     * @return template completo
     */
    public String chiudeTmpl(String templateTxt) {
        String templateOut;
        int posIni = 0;
        int posEnd = 0;
        boolean pari = false;

        templateOut = templateTxt.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length()).trim();

        while (!pari) {
            posEnd = templateTxt.indexOf(DOPPIE_GRAFFE_END, posEnd + DOPPIE_GRAFFE_END.length());
            if (posEnd != -1) {
                templateOut = templateTxt.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length()).trim();
                pari = htmlService.isPariTag(templateOut, DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
            }
            else {
                break;
            }
        }

        if (!pari) {
            templateOut = VUOTA;
        }

        return templateOut;
    }

}