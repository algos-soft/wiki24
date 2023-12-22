package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.json.simple.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mar, 10-mag-2022
 * Time: 15:09
 * UrlRequest:
 * urlDomain = "&prop=categoryinfo"
 * GET request
 * No POST text
 * No upload cookies
 * No bot needed
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryInfoCat extends AQuery {


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
     * ....batchcomplete <br>
     * ....query <br>
     * ........normalized <br>
     * ........pages <br>
     * ............[0] (sempre solo uno se non si usa il PIPE) <br>
     * ................ns <br>
     * ................missing=true <br>
     * ................pageid <br>
     * ................title <br>
     * <p>
     * Nella risposta positiva la gerarchia è: <br>
     * ....batchcomplete <br>
     * ....query <br>
     * ........normalized <br>
     * ........pages <br>
     * ............[0] <br>
     * ................ns <br>
     * ................pageid <br>
     * ................title <br>
     * ................categoryinfo <br>
     * ....................pages <br>
     * ....................size <br>
     * ....................hidden <br>
     * ....................files <br>
     * ....................subcats <br>
     *
     * @param wikiTitoloGrezzoCategoria della categoria wiki (necessita di codifica) usato nella urlRequest.
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final String wikiTitoloGrezzoCategoria) {
        typeQuery = TypeQuery.getSenzaLoginSenzaCookies;
        wikiCategory = wikiTitoloGrezzoCategoria;
        return requestGetTitle(WIKI_QUERY_CAT_INFO, CAT + wikiTitoloGrezzoCategoria);
    }


    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     */
    protected WResult elaboraResponse(WResult result, final String rispostaDellaQuery) {
        result = super.elaboraResponse(result, rispostaDellaQuery);
        result.setResponse(rispostaDellaQuery);
        JSONObject jsonCategoryInfo = null;
        Long pagine = 0L;
        String message;

        //--controllo del missing e leggera modifica delle informazioni di errore
        if (result.getErrorCode().equals(KEY_JSON_MISSING_TRUE)) {
            result.setErrorMessage(String.format("La categoria wiki '%s' non esiste", result.getWikiTitle()));
        }

        if (result.isValido()) {
            if (mappaUrlResponse.get(KEY_JSON_ZERO) instanceof JSONObject queryPageZero) {
                jsonCategoryInfo = (JSONObject) queryPageZero.get(KEY_JSON_CATEGORY_INFO);
            }

            if (jsonCategoryInfo != null && jsonCategoryInfo.get(KEY_JSON_CATEGORY_SIZE) != null) {
                pagine = (Long) jsonCategoryInfo.get(KEY_JSON_CATEGORY_SIZE);
                result.setIntValue(pagine.intValue());
            }

            result.typePage(TypePage.categoria);
            message = String.format("La categoria [%s] ha %s pagine", wikiCategory, textService.format(pagine.intValue()));
            result.validMessage(message);
        }

        return result;
    }

    public int getSize(final String wikiTitoloGrezzoCategoria) {
        WResult result = urlRequest(wikiTitoloGrezzoCategoria);
        if (result != null) {
            return result.getIntValue();
        }
        else {
            return 0;
        }
    }


}
