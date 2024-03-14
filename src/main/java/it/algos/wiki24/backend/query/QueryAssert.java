package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
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
 * https://www.mediawiki.org/wiki/API:Assert
 * anon, user, bot
 * assertanonfailed, assertuserfailed, assertbotfailed, assertnameduserfailed
 * Normally, you will not need to perform a separate request like this. Instead, set the assert=user parameter on each of your requests.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryAssert extends AQuery {

    public static final String RESPONSE_NO_LOGIN = "Manca il botLogin";

    public static final String RESPONSE_NO_QUERY = "BotLogin non ha registrato nessuna chiamata di QueryLogin";

    public static final String RESPONSE_NO_COOKIES = "Mancano i cookies nel result di botLogin";

    public static final String RESPONSE_NO_RIGHT = "You do not have the bot right, so the action could not be completed.";

    private String tagRequestAssert = VUOTA;

    private TypeUser typeUser;

    public QueryAssert() {
        super();
        this.tagRequestAssert = TAG_REQUEST_ASSERT_BOT;
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
        String urlDomain = tagRequestAssert;
        String urlResponse = VUOTA;
        URLConnection urlConn;

        result.setUrlRequest(tagRequestAssert);
        result.setQueryType(TypeQuery.getLoggatoConCookies);

        //--se manca il botLogin
        if (botLogin == null) {
            result.setValido(false);
            result.setErrorCode(ERROR_JSON_BOT_NO_LOGIN);
            result.setMessage(RESPONSE_NO_LOGIN);
            return result;
        }

        //--se il botLogin non ha registrato nessuna chiamata di QueryLogin
        if (textService.isEmpty(botLogin.getUrlResponse())) {
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

    /**
     * Esistenza del botLogin valido e del collegamento come anon <br>
     *
     * @return esistenza del botLogin valido
     */
    public TypeUser getTypeUser() {
        if (isBot()) {
            return TypeUser.bot;
        }
        else {
            if (isUser()) {
                return TypeUser.user;
            }
            else {
                return TypeUser.anon;
            }
        }
    }

    /**
     * Esistenza del botLogin valido e del collegamento come anon <br>
     *
     * @return esistenza del botLogin valido
     */
    public boolean isAnon() {
        this.typeUser = TypeUser.anon;
        this.tagRequestAssert = TAG_REQUEST_ASSERT_ANON;
        WResult result = urlRequest();
        return result.isValido();
    }

    /**
     * Esistenza del botLogin valido e del collegamento come user <br>
     *
     * @return esistenza del botLogin valido
     */
    public boolean isUser() {
        this.typeUser = TypeUser.user;
        this.tagRequestAssert = TAG_REQUEST_ASSERT_USER;
        WResult result = urlRequest();
        return result.isValido();
    }

    /**
     * Esistenza del botLogin valido e del collegamento come bot <br>
     *
     * @return esistenza del botLogin valido
     */
    public boolean isBot() {
        this.typeUser = TypeUser.bot;
        this.tagRequestAssert = TAG_REQUEST_ASSERT_BOT;
        WResult result = urlRequest();
        return result.isValido();
    }

    public boolean isValido() {
        return false;
    }

}
