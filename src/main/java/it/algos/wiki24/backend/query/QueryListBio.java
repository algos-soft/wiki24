package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.json.simple.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: dom, 15-mag-2022
 * Time: 07:19
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListBio extends AQuery {


    /**
     * Wraps di pagine biografiche <br>
     *
     * @param listaPageids lista dei pageIds delle pagine wiki da controllare
     *
     * @return lista di wraps delle pagine
     */
    public List<WrapBio> getLista(final List<Long> listaPageids) {
        return urlRequest(listaPageids).getLista();
    }

    //    /**
    //     * Pagine biografiche <br>
    //     *
    //     * @param listaPageids lista dei pageIds delle pagine wiki da controllare
    //     *
    //     * @return lista di bio
    //     */
    //    public List<BioServerEntity> getBio(final List<Long> listaPageids) {
    //        List<BioServerEntity> listaBio = new ArrayList<>();
    //        List<WrapBio> listaWrap = urlRequest(listaPageids).getLista();
    //
    //        for (WrapBio wrap : listaWrap) {
    //            listaBio.add(bioBackend.newEntity(wrap));
    //        }
    //
    //        return listaBio;
    //    }


    /**
     * Request principale <br>
     * <p>
     * La stringa urlDomain per la request viene elaborata <br>
     * Si crea una connessione di tipo GET <br>
     * Si invia la request <br>
     * La response viene sempre elaborata per estrarre le informazioni richieste <br>
     *
     * @param listaPageids lista dei pageIds delle pagine wiki da controllare
     *
     * @return wrapper di informazioni
     */
    public WResult urlRequest(final List<Long> listaPageids) {
        return urlRequestCiclica(listaPageids, WIKI_QUERY_BASE_PAGE);
    }


    protected WResult checkInizialePipe(WResult result, final String wikiTitleGrezzo) {
        return result;
    }

    @Override
    public String fixWikiTitle(String wikiTitleGrezzo) {
        return wikiTitleGrezzo;
    }

    @Override
    protected WResult elaboraResponse(WResult result, String rispostaDellaQuery) {
        List<WrapBio> listaNew = new ArrayList<>();
        List<WrapBio> listaOld;
        WrapBio wrapBio = null;
        String message;
        result = super.elaboraResponse(result, rispostaDellaQuery);
        result.typePage(TypePage.pageIds);
        result.setWikiTitle(VUOTA);
        result.setPageid(0L);

        if (result.isErrato()) {
            return result;
        }

        if (mappaUrlResponse.get(KEY_JSON_PAGES) instanceof JSONArray jsonPages) {
            if (jsonPages.size() > 0) {
                for (Object obj : jsonPages) {
                    wrapBio = getWrapBio((JSONObject) obj);
                    if (wrapBio != null) {
                        listaNew.add(wrapBio);
                    }
                    else {
                        message = String.format("La pagina [%s] non è una biografia",((JSONObject) obj).get(KEY_JSON_TITLE));
                        logger.info(new WrapLog().message(message).type(TypeLog.bio));
                    }
                }
                result.setCodeMessage(JSON_SUCCESS);
                listaOld = (List<WrapBio>) result.getLista();
                if (listaOld != null) {
                    listaOld.addAll(listaNew);
                }
                else {
                    listaOld = listaNew;
                }
                result.setLista(listaOld);
                result.setIntValue(listaOld.size());
                return result;
            }
            else {
                result.setErrorMessage("Qualcosa non ha funzionato");
                //                                result.setWrap(new WrapBio().valida(false).type(AETypePage.indeterminata));
            }
        }

        return result;
    }

}
