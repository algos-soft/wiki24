package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.exception.*;
import it.algos.vbase.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import static it.algos.wiki24.backend.service.WikiApiService.*;
import static it.algos.wiki24.backend.service.WikiBotService.*;
import it.algos.wiki24.backend.wrapper.*;
import org.json.simple.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.net.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 11-mag-2022
 * Time: 06:48
 * Query per recuperare una lista di pageIds di una categoria wiki <br>
 * È di tipo GET <br>
 * Necessita dei cookies, recuperati da BotLogin (singleton) <br>
 * Restituisce una lista di pageIds <br>
 * <p>
 * Ripete la request finché riceve un valore valido di cmcontinue <br>
 * La query restituisce SOLO pageIds <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryCat extends AQuery {

    public WResult infoCatResult;

    private TypeQueryProp queryProp = TypeQueryProp.ids;

    private TypeQueryType queryType = TypeQueryType.page;

    private String wikiTitoloGrezzoPaginaCategoria;

    private boolean filtroNomeCat = false;

    public QueryCat() {
    }

    public QueryCat(String wikiTitoloGrezzoPaginaCategoria) {
        this.wikiTitoloGrezzoPaginaCategoria = wikiTitoloGrezzoPaginaCategoria;
    }

    public QueryCat title() {
        queryProp = TypeQueryProp.title;
        return this;
    }

    public QueryCat ids() {
        queryProp = TypeQueryProp.ids;
        return this;
    }

    public QueryCat filtro() {
        filtroNomeCat = true;
        return this;
    }

    public QueryCat subCat() {
        this.queryType = TypeQueryType.subCat;
        this.queryProp = TypeQueryProp.title;
        return this;
    }

    public WResult urlRequest() {
        return urlRequest(wikiTitoloGrezzoPaginaCategoria);
    }

    /**
     * Lista dei pageIds di una categoria <br>
     *
     * @param catTitleGrezzo della categoria wiki (necessita di codifica) usato nella urlRequest
     *
     * @return lista (pageids) degli elementi contenuti nella categoria
     */
    public List<Long> getPageIds(final String catTitleGrezzo) {
        queryProp = TypeQueryProp.ids;
        return (List<Long>) urlRequest(catTitleGrezzo).getLista();
    }

    public List<Long> getPageIdsOrdered(final String catTitleGrezzo) {
        List<Long> lista = getPageIds(catTitleGrezzo);
        Collections.sort(lista);
        return lista;
    }

    /**
     * Lista dei titoli di una categoria <br>
     *
     * @param catTitleGrezzo della categoria wiki (necessita di codifica) usato nella urlRequest
     *
     * @return lista (title) degli elementi contenuti nella categoria
     */
    public List<String> getTitles(final String catTitleGrezzo) {
        queryProp = TypeQueryProp.title;
        return (List<String>) urlRequest(catTitleGrezzo).getLista();
    }

    public List<String> getTitles() {
        queryProp = TypeQueryProp.title;
        return (List<String>) urlRequest().getLista();
    }

    public List<String> getSubCat() {
        this.queryType = TypeQueryType.subCat;
        this.queryProp = TypeQueryProp.title;
        return (List<String>) urlRequest().getLista();
    }
    public List<String> getSubCat(final String catTitleGrezzo) {
        this.queryType = TypeQueryType.subCat;
        this.queryProp = TypeQueryProp.title;
        return (List<String>) urlRequest(catTitleGrezzo).getLista();
    }

    /**
     * Request principale <br>
     * <p>
     * Non accetta il separatore PIPE nel wikiTitoloGrezzoPaginaCategoria <br>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     * <p>
     * Nella risposta negativa la gerarchia è: <br>
     * ....error <br>
     * ........code (forse asserbotfailed) <br>
     * ........docref (info -> You do not have the "bot" right, so the action could not be completed) <br>
     * ........info <br>
     * <p>
     * Nella risposta positiva la gerarchia è: <br>
     * ....batchcomplete <br>
     * ....continue <br>
     * ........continue <br>
     * ........cmcontinue <br>
     * ....query <br>
     * ........normalized <br>
     * ........categorymembers (potrebbe essere vuoto ma tipicamente più di uno) <br>
     * ............[0] <br>
     * ............[1] <br>
     * ................pageid <br>
     *
     * @param wikiTitoloGrezzoPaginaCategoria della pagina/categoria wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(String wikiTitoloGrezzoPaginaCategoria) {
        typeQuery = TypeQuery.getLoggatoConCookies;
        String message;
        int num;
        int limit;
        TypeUser type;
        String urlDomain;
        String wikiTitoloConCATIniziale = wikiTitoloGrezzoPaginaCategoria;

        if (botLogin == null || botLogin.getCookies() == null) {
            appContext.getBean(QueryLogin.class).urlRequest();
            if (botLogin == null || botLogin.getCookies() == null) {
                message = "Il botLogin non ha cookies validi";
                logger.info(new WrapLog().exception(new AlgosException(message)).usaDb());
            }
        }

        if (wikiTitoloGrezzoPaginaCategoria == null) {
            message = String.format("Il titolo della categoria è nullo");
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return WResult.errato(message).queryType(TypeQuery.getLoggatoConCookies);
        }

        if (textService.isEmpty(wikiTitoloGrezzoPaginaCategoria)) {
            message = String.format("Manca il titolo della categoria");
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return WResult.errato(message).queryType(TypeQuery.getLoggatoConCookies);
        }

        if (!wikiTitoloGrezzoPaginaCategoria.startsWith(CAT)) {
            wikiTitoloConCATIniziale = CAT + wikiTitoloGrezzoPaginaCategoria;
        }
        if (wikiTitoloGrezzoPaginaCategoria.startsWith(CAT)) {
            wikiTitoloGrezzoPaginaCategoria = textService.levaTesta(wikiTitoloGrezzoPaginaCategoria, CAT);
        }

        WResult result = checkIniziale(QUERY_CAT_REQUEST, wikiTitoloConCATIniziale);
        if (result.isErrato()) {
            return result.queryType(TypeQuery.getLoggatoConCookies);
        }

        if (infoCatResult == null) {
            infoCatResult = appContext.getBean(QueryInfoCat.class).urlRequest(wikiTitoloGrezzoPaginaCategoria);
        }

        if (infoCatResult.isValido()) {
            result.setPageid(infoCatResult.getPageid());
            type = botLogin != null ? botLogin.getUserType() : null;
            num = infoCatResult.getIntValue();
            limit = type != null ? type.getLimit() : 0;
            result.limit(limit);

            switch (type) {
                case anon -> {
                    if (num > TypeUser.user.getLimit()) {
                        message = String.format("Sei collegato come %s e nella categoria ci sono %s voci", type, textService.format(num));
                        logger.info(new WrapLog().exception(new AlgosException(message)).usaDb());
                        return (WResult) result.errorMessage(message);
                    }
                }
                case user -> {
                    if (num > TypeUser.bot.getLimit()) {
                        message = String.format("Sei collegato come %s e nella categoria ci sono %s voci", type, textService.format(num));
                        logger.info(new WrapLog().exception(new AlgosException(message)).usaDb());
                        return (WResult) result.errorMessage(message);
                    }
                }
                case bot -> {}
                default -> {}
            }
        }
        else {
            return infoCatResult.queryType(TypeQuery.getLoggatoConCookies);
        }

        if (botLogin == null) {
            result.errorMessage("Manca il botLogin");
            return result;
        }
        else {
            if (botLogin != null) {
                result.setUserType(botLogin.getUserType());
                result.setLimit(botLogin.getUserType().getLimit());
            }
        }

        urlDomain = fixUrlCat(wikiTitoloGrezzoPaginaCategoria, VUOTA);
        result.setCookies(botLogin != null ? botLogin.getCookies() : null);
        result.setGetRequest(urlDomain);

        return urlRequestContinue(result, urlDomain, wikiTitoloGrezzoPaginaCategoria);
    }


    public WResult urlRequestContinue(WResult result, final String urlDomainGrezzo, final String info) {
        String message;
        String urlDomain;
        String tokenContinue = VUOTA;
        URLConnection urlConn;
        String urlResponse;
        int pageIdsRecuperati;
        int cicli = 0;

        try {
            do {
                urlDomain = urlDomainGrezzo + tokenContinue;
                urlConn = this.creaGetConnection(urlDomain);
                uploadCookies(urlConn, result.getCookies());
                urlResponse = sendRequest(urlConn);
                result = elaboraResponse(result, urlResponse);
                if (result.isValido()) {
                    result.setCicli(++cicli);
                }
                tokenContinue = WIKI_QUERY_CAT_CONTINUE + result.getToken();
            }
            while (textService.isValid(result.getToken()));
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

        if (result.isValido()) {
            pageIdsRecuperati = result.getIntValue();
            message = String.format("Recuperati %s pageIds dalla categoria '%s' in %d cicli", textService.format(pageIdsRecuperati), info, cicli);
            result.setMessage(message);
        }
        else {
            message = String.format("Nessun pageIds dalla categoria '%s'", info);
            result.setMessage(message);
        }

        return result;
    }

    protected WResult elaboraResponse(WResult result, final String rispostaDellaQuery) {
        List listaNew = new ArrayList<>();
        List listaOld;
        long pageid;
        String title;
        result = super.elaboraResponse(result, rispostaDellaQuery);
        String tokenContinue = VUOTA;

        if (mappaUrlResponse.get(KEY_JSON_CONTINUE) instanceof JSONObject jsonContinue) {
            tokenContinue = (String) jsonContinue.get(KEY_JSON_CONTINUE_CM);
        }
        result.setToken(tokenContinue);

        if (mappaUrlResponse.get(KEY_JSON_CATEGORY_MEMBERS) instanceof JSONArray jsonCategory) {
            if (jsonCategory.size() > 0) {

                if (((JSONObject) jsonCategory.get(0)).get(PAGE_ID) != null) {
                    for (Object obj : jsonCategory) {
                        pageid = (long) ((JSONObject) obj).get(PAGE_ID);
                        listaNew.add(pageid);
                    }
                }

                if (((JSONObject) jsonCategory.get(0)).get(TITLE) != null) {
                    for (Object obj : jsonCategory) {
                        title = (String) ((JSONObject) obj).get(TITLE);
                        if (queryType == TypeQueryType.subCat) {
                            if (title.startsWith(CAT)) {
                                title = textService.levaTesta(title, CAT);
                            }
                        }
                        if (filtroNomeCat) {
                            if (!title.equals(wikiTitoloGrezzoPaginaCategoria)) {
                                listaNew.add(title.trim());
                            }
                        }
                        else {
                            listaNew.add(title.trim());
                        }
                    }
                }

                result.setCodeMessage(JSON_SUCCESS);
                listaOld = (List) result.getLista();
                if (listaOld != null) {
                    listaOld.addAll(listaNew);
                }
                else {
                    listaOld = listaNew;
                }
                result.setLista(listaOld);
                result.setIntValue(listaOld.size());
                return result;
            }
            else {
                result.setErrorMessage("Non ci sono pagine nella categoria");
            }
        }

        return result;
    }

    /**
     * Costruisce un url come user/admin/bot <br>
     * Come 'anonymous' tira 500 pagine
     * Come 'bot' tira 5.000 pagine
     *
     * @param catTitle      da cui estrarre le pagine
     * @param continueParam per la successiva query
     *
     * @return testo dell'url
     */
    private String fixUrlCat(final String catTitle, final String continueParam) {
        String query = QUERY_CAT_REQUEST + CAT + wikiBot.wikiApiService.fixWikiTitle(catTitle);
        String type = queryType.get();
        String prop = queryProp.get();//--potrebbe essere anche "ids|title"
        String limit = botLogin.getUserType().limit();
        String user = botLogin.getUserType().affermazione();

        if (textService.isValid(continueParam)) {
            String continua = WIKI_QUERY_CAT_CONTINUE + continueParam;
            return String.format("%s%s%s%s%s%s", query, type, prop, limit, user, continua);
        }
        else {
            return String.format("%s%s%s%s%s", query, type, prop, limit, user);
        }

    }

}
