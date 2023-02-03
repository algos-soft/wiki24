package it.algos.wiki24.wiki.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 11:13
 * Controllo semplice per l'esistenza della pagina <br>
 * Una request di tipo GET, senza necessità di collegamento come Bot <br>
 * UrlRequest:
 * urlDomain = "&action=query&titles=="
 * GET request
 * No POST text
 * No upload cookies
 * No bot needed
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryExist extends AQuery {

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
     *
     * @param wikiTitoloGrezzoPaginaCategoria della pagina/categoria wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final String wikiTitoloGrezzoPaginaCategoria) {
        queryType = AETypeQuery.getSenzaLoginSenzaCookies;
        return requestGetTitle(WIKI_QUERY, wikiTitoloGrezzoPaginaCategoria);
    }

    /**
     * Request principale <br>
     * <p>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param pageid della pagina/categoria wik usato nella urlRequest.
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final long pageid) {
        queryType = AETypeQuery.getSenzaLoginSenzaCookies;
        return requestGetPage(WIKI_QUERY_PAGEIDS, pageid);
    }


    /**
     * Esistenza della pagina <br>
     *
     * @param wikiTitoloGrezzoPaginaCategoria della pagina/categoria wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return true se la pagina/categoria esiste
     */
    public boolean isEsiste(final String wikiTitoloGrezzoPaginaCategoria) {
        return urlRequest(wikiTitoloGrezzoPaginaCategoria).isValido();
    }

    /**
     * Esistenza della pagina <br>
     *
     * @param pageid della pagina/categoria wik usato nella urlRequest.
     *
     * @return true se la pagina/categoria esiste
     */
    public boolean isEsiste(final long pageid) {
        return urlRequest(pageid).isValido();
    }

    @Override
    protected String fixAssert(String urlDomain) {
        return urlDomain;
    }

    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta <br>
     * Controllo del contenuto (testo) ricevuto <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param rispostaDellaQuery in formato JSON da elaborare
     *
     * @return wrapper di informazioni
     */
    protected WResult elaboraResponse(WResult result, final String rispostaDellaQuery) {
        result = super.elaboraResponse(result, rispostaDellaQuery);
        result.setResponse(rispostaDellaQuery);
        String message;

        if ((boolean) mappaUrlResponse.get(KEY_JSON_BATCH)) {
        }
        else {
            result.setErrorCode("batchcomplete=false");
            message = String.format("Qualcosa non ha funzionato nella lettura della pagina wiki '%s'", result.getWikiTitle());
            result.setErrorMessage(message);
            return result;
        }

        if (result.isValido()) {
            if (result.getNameSpace() == 0L) {
                message = String.format("La pagina [[%s]] esiste", result.getWikiTitle());
                result.setValidMessage(message);
                result.typePage(AETypePage.pagina);
            }
            if (result.getNameSpace() == 14) {
                message = String.format("La categoria [[%s]] esiste", result.getWikiTitle());
                result.setValidMessage(message);
                result.typePage(AETypePage.categoria);
            }
        }

        return result;
    }

}
