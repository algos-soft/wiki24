package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Dec-2023
 * Time: 07:26
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPage extends AQuery {

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
        WResult result = requestGetTitle(WIKI_QUERY_BASE_TITLE, wikiTitleGrezzo);

        if (result.getTypePage() == TypePage.contienePipe) {
            result.setWrapPage(new WrapPage().title(result.getWikiTitle()).pageid(result.getPageid()).type(TypePage.contienePipe));
        }

        return result;
    }


    /**
     * Request principale <br>
     * <p>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param pageIds della pagina wiki usato nella urlRequest.
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final long pageIds) {
        typeQuery = TypeQuery.getSenzaLoginSenzaCookies;
        return requestGetPageIds(WIKI_QUERY_BASE_PAGE, pageIds);
    }


    /**
     * Costruisce un wrapper con le informazioni essenziali <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @param wikiTitleGrezzo della pagina wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return WrapPage risultante
     */
    public WrapPage getPage(final String wikiTitleGrezzo) {
        if (textService.isEmpty(wikiTitleGrezzo)) {
            return WrapPage.nonValida();
        }

        WResult result = urlRequest(wikiTitleGrezzo);
        return result != null ? result.getWrapPage() : null;
    }

    /**
     * Costruisce un wrapper con le informazioni essenziali <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @param pageIds della pagina wiki usato nella urlRequest.
     *
     * @return testo della pagina
     */
    public WrapPage getPage(final long pageIds) {
        if (pageIds == 0) {
            return WrapPage.nonValida();
        }

        WResult result = urlRequest(pageIds);
        return result != null ? result.getWrapPage() : null;
    }

}

