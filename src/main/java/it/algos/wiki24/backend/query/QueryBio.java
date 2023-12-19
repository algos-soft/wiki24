package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Dec-2023
 * Time: 19:52
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryBio extends AQuery {

    @Inject
    private WikiBotService wikiBotService;

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
     * ............[0] (sempre solo uno se non si usa il PIPE) <br>
     * ................pageid <br>
     * ................title <br>
     * ................revisions <br>
     * ....................[0] (sempre solo uno con la query utilizzata) <br>
     * ........................revid <br>
     * ........................parentid <br>
     * ........................timestamp <br>
     * ........................slots <br>
     * ............................main <br>
     * ................................contentformat <br>
     * ................................contentmodel <br>
     * ................................content (da cui estrarre il tmpl bio) <br>
     *
     * @param wikiTitleGrezzoBio della pagina biografica wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final String wikiTitleGrezzoBio) {
        typeQuery = TypeQuery.getSenzaLoginSenzaCookies;
        WResult result = requestGetTitle(WIKI_QUERY_BASE_TITLE, wikiTitleGrezzoBio);

        if (result.getTypePage() == TypePage.contienePipe || result.getTypePage() == TypePage.redirect) {
            //            result.setWrapBio(new WrapBio().title(result.getWikiTitle()).pageid(result.getPageid()).type(result.getTypePage()));
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
    public WrapBio getPage(final String wikiTitleGrezzo) {
        String templBio = VUOTA;
        if (textService.isEmpty(wikiTitleGrezzo)) {
            return WrapBio.nonValida();
        }

        WResult result = urlRequest(wikiTitleGrezzo);
        if (result.isValido() && result.getWrapPage() != null && result.getWrapPage().isValida()) {
            templBio = wikiBotService.estraeTmplBio(result.getWrapPage().getContent());
        }
        if (textService.isValid(templBio)) {
            return WrapBio.valida(result.getWrapPage()).templBio(templBio);
        }

        return result != null ? result.getWrapBio() != null ? result.getWrapBio() : WrapBio.nonValida() : WrapBio.nonValida();
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
    public WrapBio getPage(final long pageIds) {
        if (pageIds == 0) {
            return WrapBio.nonValida();
        }

        WResult result = urlRequest(pageIds);
        return result != null ? result.getWrapBio() : null;
    }

}
