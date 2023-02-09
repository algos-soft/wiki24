package it.algos.wiki24.backend.wrapper;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: mar, 10-ago-2021
 * Time: 09:05
 * Semplice wrapper per veicolare una risposta con diverse property <br>
 */
@Component
public class WResult extends AResult {

    private String webTitle = VUOTA;

    private long pageid = 0;

    private String wikiTitle = VUOTA;

    private String urlPreliminary = VUOTA;

    private String urlRequest = VUOTA;

    private String getRequest = VUOTA;

    private String preliminaryResponse = VUOTA;

    private String response = VUOTA;

    private String wikiText = VUOTA;

    private String wikiBio = VUOTA;

    private String token = VUOTA;

    private AETypeQuery queryType = null;

    private AETypeUser userType = null;

    private int limit;

    private WrapBio wrap;

    private String summary = VUOTA;

    private String newtimestamp = VUOTA;

    private String newtext = VUOTA;

    private String post = VUOTA;

    private long newrevid = 0;

    private long nameSpace = 0L;

    private long inizio;

    private long fine;

    private boolean modificata = false;

    private int cicli;

    private AETypePage typePage;

    private Map<String, String> cookies;

    private String content = VUOTA;

    private WResult() {
        this(null);
    }

    private WResult(WrapBio wrap) {
        super();
        this.wrap = wrap;
        this.inizio = System.currentTimeMillis();
    }

    public WResult webTitle(final String webTitle) {
        this.webTitle = webTitle;
        return this;
    }

    public WResult wikiTitle(final String wikiTitle) {
        this.wikiTitle = wikiTitle;
        return this;
    }

    public WResult getRequest(final String getRequest) {
        this.getRequest = getRequest;
        return this;
    }

    public WResult cookies(final Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public static WResult crea() {
        return new WResult();
    }

    public static WResult crea(WrapBio wrap) {
        WResult wResult = new WResult(wrap);
        return wResult;
    }

    public static WResult errato() {
        WResult wResult = new WResult();
        wResult.setValido(false);
        return wResult;
    }

    public static WResult errato(final String errorMessage) {
        WResult wResult = new WResult();
        wResult.setErrorCode(errorMessage);
        wResult.setValido(false);
        return wResult;
    }

    public WResult queryType(final AETypeQuery queryType) {
        this.queryType = queryType;
        return this;
    }

    public WResult userType(final AETypeUser userType) {
        this.userType = userType;
        return this;
    }

    public WResult typePage(final AETypePage typePage) {
        this.typePage = typePage;
        return this;
    }

    public WResult limit(final int limit) {
        this.limit = limit;
        return this;
    }

    public WResult cicli(final int cicli) {
        this.cicli = cicli;
        return this;
    }
    public WResult content(final String content) {
        this.content = content;
        return this;
    }
    public WResult intValue(final int intValue) {
        this.setIntValue(intValue);
        return this;
    }

    public WResult fine() {
        this.setFine();
        return this;
    }

    public WResult wikiText(final String wikiText) {
        this.wikiText = wikiText;
        return this;
    }

    public WResult post(final String post) {
        this.post = post;
        return this;
    }

    public String getPost() {
        return post;
    }

    public WResult setValido() {
        this.setValido(true);
        return this;
    }
    public WResult validoWiki(boolean valido) {
        this.setValido(valido);
        return this;
    }

    public WResult pageid(final long pageid) {
        this.pageid = pageid;
        return this;
    }

    public WrapBio getWrap() {
        return wrap;
    }

    public void setWrap(WrapBio wrap) {
        this.wrap = wrap;
    }

    public static WResult valido() {
        return new WResult();
    }

    public String getWikiBio() {
        return wikiBio;
    }

    public void setWikiBio(String wikiBio) {
        this.wikiBio = wikiBio;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNewtimestamp() {
        return newtimestamp;
    }

    public void setNewtimestamp(String newtimestamp) {
        this.newtimestamp = newtimestamp;
    }

    public long getNewrevid() {
        return newrevid;
    }

    public void setNewrevid(long newrevid) {
        this.newrevid = newrevid;
    }

    public boolean isModificata() {
        return modificata;
    }

    public void setModificata(boolean modificata) {
        this.modificata = modificata;
    }

    public String getNewtext() {
        return newtext;
    }

    public void setNewtext(String newtext) {
        this.newtext = newtext;
    }

    public String getGetRequest() {
        return getRequest;
    }

    public void setGetRequest(String getRequest) {
        this.getRequest = getRequest;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void setFine() {
        this.fine = System.currentTimeMillis();
    }

    public long getDurata() {
        return fine - inizio;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWikiTitle() {
        return wikiTitle;
    }

    public void setWikiTitle(String wikiTitle) {
        this.wikiTitle = wikiTitle;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrlPreliminary() {
        return urlPreliminary;
    }

    public void setUrlPreliminary(String urlPreliminary) {
        this.urlPreliminary = urlPreliminary;
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }

    public String getPreliminaryResponse() {
        return preliminaryResponse;
    }

    public void setPreliminaryResponse(String preliminaryResponse) {
        this.preliminaryResponse = preliminaryResponse;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AETypeQuery getQueryType() {
        return queryType;
    }

    public void setQueryType(AETypeQuery queryType) {
        this.queryType = queryType;
    }

    public String getWikiText() {
        return wikiText;
    }

    public void setWikiText(String wikiText) {
        this.wikiText = wikiText;
    }

    public long getPageid() {
        return pageid;
    }

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }

    public long getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(long nameSpace) {
        this.nameSpace = nameSpace;
    }

    public AETypeUser getUserType() {
        return userType;
    }

    public void setUserType(AETypeUser userType) {
        this.userType = userType;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCicli() {
        return cicli;
    }

    public void setCicli(int cicli) {
        this.cicli = cicli;
    }

    public AETypePage getTypePage() {
        return typePage;
    }

    public void setTypePage(AETypePage typePage) {
        this.typePage = typePage;
    }

    public long getInizio() {
        return inizio;
    }

    public long getFine() {
        return fine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
