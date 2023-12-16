package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.net.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: sab, 24-lug-2021
 * Time: 18:52
 * <p>
 * Query di controllo per 'testare' il collegamento come bot <br>
 * È di tipo GET <br>
 * Necessita dei cookies, recuperati da BotLogin (singleton) <br>
 * Restituisce true or false <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryAssert extends AQuery {

    public static final String RESPONSE_NO_LOGIN = "Manca il botLogin";

    public static final String RESPONSE_NO_QUERY = "BotLogin non ha registrato nessuna chiamata di QueryLogin";

    public static final String RESPONSE_NO_COOKIES = "Mancano i cookies nel result di botLogin";

    public static final String RESPONSE_NO_RIGHT = "You do not have the bot right, so the action could not be completed.";


    public QueryAssert() {
        super();
    }


    /**
     * La request unica <br>
     * <p>
     * Nella risposta negativa la gerarchia è: <br>
     * ....error <br>
     * ........code <br>
     * ........docref <br>
     * ........info <br>
     * <p>
     * Nella risposta positiva la gerarchia è: <br>
     * ....batchcomplete=true <br>
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest() {
        WResult result = WResult.valido();
        Map<String, String> cookies;
        String urlDomain = TAG_REQUEST_ASSERT;
        String urlResponse = VUOTA;
        URLConnection urlConn;

        result.setUrlRequest(TAG_REQUEST_ASSERT);
        result.setQueryType(TypeQuery.getLoggatoConCookies);

        //--se manca il botLogin
        if (botLogin == null) {
            result.setValido(false);
            result.setErrorCode(ERROR_JSON_BOT_NO_LOGIN);
            result.setMessage(RESPONSE_NO_LOGIN);
            return result;
        }

        //--se il botLogin non ha registrato nessuna chiamata di QueryLogin
        if (botLogin.getResult() == null) {
            result.setValido(false);
            result.setErrorCode(ERROR_JSON_BOT_NO_QUERY);
            result.setMessage(RESPONSE_NO_QUERY);
            return result;
        }

        //--se mancano i cookies
        cookies = botLogin.getCookies();
        if (cookies == null || cookies.size() < 1) {
            result.setValido(false);
            result.setErrorCode(ERROR_JSON_BOT_NO_COOKIES);
            result.setMessage(RESPONSE_NO_COOKIES);
            return result;
        }

        try {
            urlConn = this.creaGetConnection(urlDomain);
            uploadCookies(urlConn, cookies);
            urlResponse = sendRequest(urlConn);
        } catch (Exception unErrore) {
        }

        return elaboraResponse(result, urlResponse);
    }

    /**
     * Esistenza del botLogin valido e del collegamento come bot <br>
     *
     * @return esistenza del botLogin valido
     */
    public boolean isEsiste() {
        return urlRequest().isValido();
    }

    /**
     * Elabora la risposta <br>
     * <p>
     * Recupera il token 'logintoken' dalla preliminaryRequestGet <br>
     * Viene convertito in lgtoken necessario per la successiva secondaryRequestPost <br>
     */
    protected WResult elaboraResponse(WResult result, final String rispostaDellaQuery) {
        result = super.elaboraResponse(result, rispostaDellaQuery);

        if ((boolean) mappaUrlResponse.get(KEY_JSON_MISSING)) {
            result.setValido(false);
        }

        if ((boolean) mappaUrlResponse.get(KEY_JSON_ERROR)) {
            result.setValido(false);
            result.setErrorCode(ERROR_JSON_BOT_NO_RIGHT);
            result.setErrorMessage(RESPONSE_NO_RIGHT);
        }

        return result;
    }


}
