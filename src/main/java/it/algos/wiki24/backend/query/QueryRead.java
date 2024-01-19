package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 03-Jul-2022
 * Time: 08:14
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryRead extends AQuery {

    /**
     * Request principale <br>
     * <p>
     * Non accetta il separatore PIPE nel wikiTitoloGrezzoPaginaCategoria <br>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param wikiTitleGrezzo della pagina wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final String wikiTitleGrezzo) {
        typeQuery = TypeQuery.getSenzaLoginSenzaCookies;
        WResult result = requestGetTitle(QUERY_TITLES, wikiTitleGrezzo);

        if (result.getTypePage() == TypePage.contienePipe) {
            result.setWrapPage(new WrapPage().title(result.getWikiTitle()).pageid(result.getPageid()).type(TypePage.contienePipe));
        }

        return result;
    }

    /**
     * Request principale <br>
     * <p>
     * Non accetta il separatore PIPE nel wikiTitoloGrezzoPaginaCategoria <br>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param pageId della pagina wiki usato nella urlRequest. <br>
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final long pageIds) {
        typeQuery = TypeQuery.getSenzaLoginSenzaCookies;
        return requestGetPageIds(QUERY_PAGEIDS, pageIds);
    }

    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta <br>
     * Controllo del contenuto (testo) ricevuto <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param result
     * @param rispostaDellaQuery in formato JSON da elaborare
     *
     * @return wrapper di informazioni
     */
    @Override
    protected WResult elaboraResponse(WResult result, String rispostaDellaQuery) {
        return super.elaboraResponse(result, rispostaDellaQuery);
    }

    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @param wikiTitleGrezzo della pagina wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return testo della pagina
     */
    public String getContent(final String wikiTitleGrezzo) {
        return urlRequest(wikiTitleGrezzo).getContent();
    }

    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @param pageIds della pagina wiki usato nella urlRequest
     *
     * @return testo della pagina
     */
    public String getContent(final long pageIds) {
        return urlRequest(pageIds).getContent();
    }

}
