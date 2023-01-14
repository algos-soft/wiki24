package it.algos.vaad24.backend.interfaces;


import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.service.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 27-nov-2020
 * Time: 14:29
 */
public interface AIResult {


    AIResult validMessage(final String validMessage);

    AIResult errorMessage(final String errorMessage);

    AIResult query(final String queryType);

    AIResult target(final String target);

    boolean isValido();

    void setValido(final boolean valido);

    boolean isErrato();

    String getPreliminaryResponse();

    void setPreliminaryResponse(final String preliminaryResponse);

    String getResponse();

    void setResponse(final String response);

    String getCodeMessage();

    void setCodeMessage(final String codeMessage);

    String getMessage();

    void setMessage(final String message);

    String getErrorMessage();

    AIResult setErrorMessage(final String message);

    String getErrorCode();

    void setErrorCode(String errorCode);

    String getValidMessage();

    void setValidMessage(String validMessage);

    int getIntValue();

    void setIntValue(int intValue);

    long getLongValue();

    void setLongValue(long longValue);

    void print(final LogService logger, final AETypeLog typeLog);

    String getWebTitle();

    void setWebTitle(String webTitle);

    String getWikiTitle();

    void setWikiTitle(String wikiTitle);

    String getUrlPreliminary();

    void setUrlPreliminary(String urlPreliminary);

    String getUrlRequest();

    void setUrlRequest(String urlRequest);

    String getToken();

    void setToken(final String token);

    String getQueryType();

    void setQueryType(String queryType);

    String getWikiText();

    void setWikiText(String wikiText);

    String getWikiBio();

    void setWikiBio(String wikiBio);

    List getLista();

    void setLista(List lista);

    Map getMappa();

    void setMappa(Map mappa);

    String getTarget();

}// end of interface

