package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import static it.algos.wiki24.backend.service.WikiApiService.*;
import it.algos.wiki24.backend.wrapper.*;
import org.apache.poi.ss.formula.functions.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.*;

import javax.inject.*;
import java.net.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: gio, 08-lug-2021
 * Time: 18:35
 * Collegamento al server wiki <ul>
 * <li> Ogni utilizzo del bot deve essere preceduto da un login </li>
 * <li> Il login deve essere effettuato tramite le API </li>
 * <li> Il login deve essere effettuato con nickname e password </li>
 * <li> Nickname e password vengono letti da config.application.properties </li>
 * <li> Controlla che l'accesso abbia un risultato positivo </li>
 * </ul>
 * Due request: GET e POST
 * GET
 * urlDomain = "&meta=tokens&type=login"
 * no testo POST
 * no upload cookies
 * nella response -> logintoken
 * download cookies -> itwikisession
 * elabora logintoken -> lgtoken
 * <p>
 * POST
 * urlDomain = "&action=login"
 * testo POST -> lgname, lgpassword, lgtoken
 * upload cookies -> itwikisession
 * nella response -> lguserid, lgusername, success
 * download cookies -> itwiki_BPsession
 * elabora -> memorizza in BotLogin: itwiki_BPsession, lguserid, lgusername
 * <p>
 * Tipicamente viene chiamato una volta sola (SCOPE_PROTOTYPE) all'inizio del programma <br>
 * Memorizza i dati per le successive query come bot in BotLogin (SCOPE_SINGLETON) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryLogin extends AQuery {


    /**
     * Valore di collegamento iniziale <br>
     * Eventualmente inseribile in 'application.properties' (meglio) <br>
     */
    private String loginName;

    /**
     * Valore di collegamento iniziale <br>
     * Eventualmente inseribile in 'application.properties' (meglio) <br>
     */
    private String loginPassword;

    /**
     * Tag per il testo POS da inviare nella secondaryRequestPost <br>
     */
    public static final String TAG_NAME = "&lgname=";

    /**
     * Tag per il testo POS da inviare nella secondaryRequestPost <br>
     */
    public static final String TAG_PASSWORD = "&lgpassword=";

    /**
     * Tag per il testo POS da inviare nella secondaryRequestPost <br>
     */
    public static final String TAG_TOKEN = "&lgtoken=";


    /**
     * Token recuperato dalla preliminaryRequestGet <br>
     * Viene convertito in lgtoken necessario per la successiva secondaryRequestPost <br>
     */
    private String logintoken = VUOTA;

    /**
     * Token elaborato da logintoken e necessario per la successiva secondaryRequestPost <br>
     * Property indispensabile inviata nel POST di testo della secondaryRequestPost<br>
     */
    private String lgtoken = VUOTA;

    /**
     * Property indispensabile inviata nel POST di testo della secondaryRequestPost<br>
     */
    private String lgname;

    /**
     * Property indispensabile inviata nel POST di testo della secondaryRequestPost<br>
     */
    private String lgpassword;

    /**
     * Property indispensabile ricevuta nella secondaryResponse <br>
     */
    private long lguserid;

    /**
     * Property indispensabile ricevuta nella secondaryResponse <br>
     */
    private String lgusername;

    private boolean valido = false;

    private TypeUser typeUser;

    private String property;

    private String message;

    public QueryLogin() {
    }

    public WResult urlRequestHamed() {
        typeUser = TypeUser.user;

        try {
            property = "wiki24.user.loginName";
            loginName = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }
        try {
            property = "wiki24.user.loginPassword";
            loginPassword = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        return urlRequest();
    }

    public WResult urlRequestGac() {
        typeUser = TypeUser.user;

        try {
            property = "wiki24.admin.loginName";
            loginName = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }
        try {
            property = "wiki24.admin.loginPassword";
            loginPassword = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        return urlRequest();
    }

    public WResult urlRequestBot() {
        typeUser = TypeUser.bot;

        try {
            property = "wiki24.bot.loginName";
            loginName = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }
        try {
            property = "wiki24.bot.loginPassword";
            loginPassword = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        return urlRequest();
    }

    public WResult urlRequest(String loginName, String loginPassword) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        return urlRequest();
    }


    /**
     * Request al software mediawiki composta di due request <br>
     * <p>
     * La prima request preliminare è di tipo GET, per recuperare token e session <br>
     * urlDomain = "&meta=tokens&type=login" <br>
     * Invia la request senza cookies e senza testo POST <br>
     * Recupera i cookies della connessione (in particolare 'itwikisession') <br>
     * Recupera il logintoken dalla urlResponse <br>
     * <p>
     * La seconda request è di tipo POST <br>
     * urlDomain = "&action=login" <br>
     * Invia la request con i cookies ricevuti (solo 'session') <br>
     * Scrive il testo post con i valori di lgname, lgpassword e lgtoken <br>
     * <p>
     * La response viene elaborata per confermare il login andato a buon fine <br>
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest() {
        typeQuery = TypeQuery.login;
        WResult result;

        this.reset();

        //--La prima request è di tipo GET
        result = this.preliminaryRequestGet();

        //--La seconda request è di tipo POST
        //--Indispensabile aggiungere i cookies
        //--Indispensabile aggiungere il testo POST
        return this.secondaryRequestPost(result);
    }

    /**
     * <br>
     * Request preliminare. Crea la connessione base di tipo GET <br>
     * La request preliminare è di tipo GET, per recuperare token e session <br>
     * <p>
     * Request 1
     * URL: https://it.wikipedia.org/w/api.php?action=query&format=json&meta=tokens&type=login
     * This will return a "logintoken" parameter in JSON form, as documented at API:Login.
     * Other output formats are available. It will also return HTTP cookies as described below.
     * <p>
     * urlDomain = "&meta=tokens&type=login" <br>
     * Invia la request senza cookies e senza testo POST <br>
     * Recupera i cookies della connessione (in particolare 'itwikisession') <br>
     * Recupera il logintoken dalla urlResponse <br>
     */
    protected WResult preliminaryRequestGet() {
        WResult result = WResult.valido().queryType(typeQuery);
        String urlDomain = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlResponse = VUOTA;
        URLConnection urlConn;

        result.setUrlPreliminary(TAG_LOGIN_PRELIMINARY_REQUEST_GET);
        try {
            urlConn = this.creaGetConnection(urlDomain);
            urlResponse = sendRequest(urlConn);
            cookiesPrimary = downlodCookies(urlConn);
            result.setCookies(cookiesPrimary);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

        return elaboraPreliminaryResponse(result, urlResponse);
    }


    /**
     * Elabora la risposta <br>
     * <p>
     * Recupera il token 'logintoken' dalla preliminaryRequestGet <br>
     * Viene convertito in lgtoken necessario per la successiva secondaryRequestPost <br>
     */
    protected WResult elaboraPreliminaryResponse(final WResult result, final String rispostaDellaQuery) {
        JSONObject jsonAll;
        JSONObject jsonQuery = null;
        JSONObject jsonTokens = null;

        result.setPreliminaryResponse(rispostaDellaQuery);
        jsonAll = (JSONObject) JSONValue.parse(rispostaDellaQuery);

        if (jsonAll != null && jsonAll.get(QUERY) != null) {
            jsonQuery = (JSONObject) jsonAll.get(QUERY);
        }

        if (jsonQuery != null && jsonQuery.get(TOKENS) != null) {
            jsonTokens = (JSONObject) jsonQuery.get(TOKENS);
        }

        if (jsonTokens != null && jsonTokens.get(LOGIN_TOKEN) != null) {
            logintoken = (String) jsonTokens.get(LOGIN_TOKEN);
        }

        try {
            lgtoken = URLEncoder.encode(logintoken, ENCODE);
            result.setToken(lgtoken);
        } catch (Exception unErrore) {
        }

        return result;
    }


    /**
     * Request principale. Crea la connessione base di tipo POST <br>
     * <p>
     * Request 2
     * URL: https://it.wikipedia.org/w/api.php?action=login&format=json
     * COOKIES parameters:
     * itwikiSession
     * POST parameters:
     * lgname=BOTUSERNAME
     * lgpassword=BOTPASSWORD
     * lgtoken=TOKEN
     * <p>
     * where TOKEN is the token from the previous result. The HTTP cookies from the previous request must also be passed with the second request.
     * <p>
     * A successful login attempt will result in the Wikimedia server setting several HTTP cookies. The bot must save these cookies and send them back every time it makes a request (this is particularly crucial for editing). On the English Wikipedia, the following cookies should be used: enwikiUserID, enwikiToken, and enwikiUserName. The enwikisession cookie is required to actually send an edit or commit some change, otherwise the MediaWiki:Session fail preview error message will be returned.
     * <p>
     * Crea la connessione base di tipo POST <br>
     * Invia la request con i cookies ricevuti (solo 'session') <br>
     * Scrive il testo post con i valori di lgname, lgpassword e lgtoken <br>
     * <p>
     * Risposta in formato testo JSON <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     * Recupera i cookies allegati alla risposta e li memorizza in WikiLogin per poterli usare in query successive <br>
     *
     * @return true se il collegamento come bot è confermato
     */
    protected WResult secondaryRequestPost(final WResult result) {
        String urlDomain = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlResponse = VUOTA;
        URLConnection urlConn;
        String testoPost;
        result.setUrlRequest(urlDomain);

        try {
            urlConn = this.creaGetConnection(urlDomain);
            uploadCookies(urlConn, cookiesPrimary);
            testoPost = addPostConnection(urlConn);
            result.post(testoPost);
            urlResponse = sendRequest(urlConn);
            cookiesSecondary = downlodCookies(urlConn);
        } catch (Exception unErrore) {
        }

        return elaboraSecondaryResponse(result, urlResponse);
    }


    /**
     * Crea il testo del POST della request <br>
     */
    @Override
    protected String elaboraPost() {
        String testoPost = VUOTA;

        lgname = textService.isValid(lgname) ? lgname : loginName;
        lgpassword = textService.isValid(lgpassword) ? lgpassword : loginPassword;

        testoPost += TAG_NAME;
        testoPost += lgname;
        testoPost += TAG_PASSWORD;
        testoPost += lgpassword;
        testoPost += TAG_TOKEN;
        testoPost += lgtoken;

        return testoPost;
    }


    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @return true se il collegamento come bot è confermato
     */
    protected WResult elaboraSecondaryResponse(WResult result, final String rispostaDellaQuery) {
        result = super.elaboraResponse(result, rispostaDellaQuery);
        result.typePage(TypePage.login);
        JSONObject jsonLogin = (JSONObject) mappaUrlResponse.get(KEY_JSON_LOGIN);
        String jsonResult = null;
        String message;

        if (jsonLogin != null) {
            result.setResponse(jsonLogin.toString());
        }

        if (jsonLogin != null && jsonLogin.get(RESULT) != null) {
            if (jsonLogin.get(RESULT) != null) {
                jsonResult = (String) jsonLogin.get(RESULT);
                valido = textService.isValid(jsonResult) && jsonResult.equals(JSON_SUCCESS);
            }
            if (valido) {
                result.setCodeMessage(JSON_SUCCESS);
                result.typeResult(TypeResult.querySuccess);
            }
            else {
                result.setValido(false);
                result.typeResult(TypeResult.queryError);
                result.setErrorCode(jsonResult.toString());
                result.setErrorMessage((String) jsonLogin.get(JSON_REASON));
                return result;
            }

            if ((Long) jsonLogin.get(LOGIN_USER_ID) > 0) {
                lguserid = (Long) jsonLogin.get(LOGIN_USER_ID);
            }
            if (textService.isValid(jsonLogin.get(LOGIN_USER_NAME))) {
                lgusername = (String) jsonLogin.get(LOGIN_USER_NAME);
            }
        }

        //--trasferisce nella istanza singleton BotLogin i cookies per essere utilizzati in tutte le query
        if (valido) {
            botLogin.setQuery(true, lguserid, lgname, TypeUser.bot, result.getResponse(), cookiesLogin(cookiesSecondary));
            result.setUserType(typeUser);
            result.setResponse(jsonLogin.toString());
            result.setValidMessage(String.format("%s: %d, %s: %s", LOGIN_USER_ID, lguserid, LOGIN_USER_NAME, lgusername));
        }

        //--controllo finale tramite una query GET coi cookies che controlla assert=bot
        if (valido) {
            //--controlla l'esistenza e la validità del collegamento come bot
            if (typeUser == TypeUser.bot) {
                result = checkBot(result);
            }
        }

        if (result.isValido()) {
            message = String.format("Collegato come %s di nick '%s'", botLogin.getUserType(), botLogin.getUsername());
            logger.info(new WrapLog().message(message).type(TypeLog.login));
        }

        return result;
    }

    protected Map<String, String> cookiesLogin(Map<String, String> cookies) {
        Map<String, String> cookiesLogin = new HashMap<>();

        cookiesLogin.put("ss0-itwikiSession", cookies.get("ss0-itwikiSession"));
        cookiesLogin.put("itwikiss0-UserID", cookies.get("itwikiss0-UserID"));
        cookiesLogin.put("centralauth_ss0-User", cookies.get("centralauth_ss0-User"));
        cookiesLogin.put("centralauth_ss0-Token", cookies.get("centralauth_ss0-Token"));

        return cookiesLogin;
    }

    public void reset() {
        logintoken = VUOTA;
        lgtoken = VUOTA;
        lgname = VUOTA;
        lgpassword = VUOTA;
        lguserid = 0;
        lgusername = VUOTA;
    }

    public boolean isValido() {
        return valido;
    }

}
