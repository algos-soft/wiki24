package it.algos.wiki24.wiki.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.Wiki23Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.json.simple.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 19:42
 * UrlRequest:
 * urlDomain = "&rvslots=main&prop=revisions&rvprop=content|ids|timestamp"
 * GET request
 * No POST text
 * No upload cookies
 * No bot needed
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryBio extends AQuery {


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
        queryType = AETypeQuery.getSenzaLoginSenzaCookies;
        WResult result = requestGetTitle(WIKI_QUERY_BASE_TITLE, wikiTitleGrezzoBio);

        if (result.getTypePage() == AETypePage.contienePipe) {
            result.setWrap(new WrapBio().valida(false).title(result.getWikiTitle()).pageid(result.getPageid()).type(AETypePage.contienePipe));
        }

        return result;
    }

    /**
     * Wrap della pagina biografica <br>
     *
     * @param wikiTitleGrezzoBio della pagina biografica wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return wrap della pagina
     */
    public WrapBio getWrap(final String wikiTitleGrezzoBio) {
        return urlRequest(wikiTitleGrezzoBio).getWrap();
    }

    /**
     * Request principale <br>
     * <p>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param pageid della pagina wiki usato nella urlRequest
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final long pageid) {
        queryType = AETypeQuery.getSenzaLoginSenzaCookies;
        return requestGetPage(WIKI_QUERY_BASE_PAGE, pageid);
    }

    /**
     * Wrap della pagina biografica <br>
     *
     * @param pageid della pagina wiki usato nella urlRequest
     *
     * @return wrap della pagina
     */
    public WrapBio getWrap(final long pageid) {
        return urlRequest(pageid).getWrap();
    }

    /**
     * Bio della pagina biografica <br>
     *
     * @param pageid della pagina wiki usato nella urlRequest
     *
     * @return bio della pagina
     */
    public Bio getBio(final long pageid) {
        Bio bio;
        WrapBio wrap = getWrap(pageid);
        bio = bioBackend.newEntity(wrap);
        bio = elaboraService.esegue(bio);
        return bio;
    }

    /**
     * Bio della pagina biografica <br>
     *
     * @param wikiTitleGrezzoBio della pagina biografica wiki (necessita di codifica) usato nella urlRequest. Non accetta il separatore PIPE
     *
     * @return bio della pagina
     */
    public Bio getBio(final String wikiTitleGrezzoBio) {
        Bio bio = null;
        WrapBio wrap;

        if (textService.isEmpty(wikiTitleGrezzoBio)) {
            return null;
        }

        wrap = getWrap(wikiTitleGrezzoBio);
        if (wrap != null) {
            bio = bioBackend.newEntity(wrap);
            bio = elaboraService.esegue(bio);
        }

        return bio;
    }


    /**
     * Elabora la risposta <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     */
    protected WResult elaboraResponse(WResult result, final String rispostaDellaQuery) {
        result = super.elaboraResponse(result, rispostaDellaQuery);
        String wikiTitle = result.getWikiTitle();
        long pageId = result.getPageid();
        WrapBio wrap;

        //--controllo del missing e leggera modifica delle informazioni di errore
        if ((boolean) mappaUrlResponse.get(KEY_JSON_MISSING)) {
            result.setErrorMessage(String.format("La pagina bio '%s' non esiste", result.getWikiTitle()));
            result.typePage(AETypePage.nonEsiste);
            result.setWrap(new WrapBio().type(AETypePage.nonEsiste).valida(false).title(wikiTitle));
            return result;
        }

        if (result.getTypePage() == AETypePage.disambigua) {
            result.setWrap(new WrapBio().valida(false).title(wikiTitle).pageid(pageId).type(AETypePage.disambigua));
            return result;
        }

        if (result.getTypePage() == AETypePage.redirect) {
            result.setWrap(new WrapBio().valida(false).title(wikiTitle).pageid(pageId).type(AETypePage.redirect));
            return result;
        }

        //--estrae il tmplBio dal content
        if (mappaUrlResponse.get(KEY_JSON_ZERO) instanceof JSONObject jsonZero) {
            wrap = getWrap(jsonZero);
            result.setWrap(wrap);
            result.setValido(wrap.isValida());
            result.setTypePage(wrap.getType());
        }

        return result;
    }

}
