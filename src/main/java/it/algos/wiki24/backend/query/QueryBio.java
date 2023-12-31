package it.algos.wiki24.backend.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
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
    private BioServerModulo bioServerModulo;


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
    public WrapBio getWrapBio(final String wikiTitleGrezzo) {
        WrapPage wrapPage;
        BioServerEntity beanBioServer = null;
        if (textService.isEmpty(wikiTitleGrezzo)) {
            return null;
        }

        wrapPage = appContext.getBean(QueryPage.class).getPage(wikiTitleGrezzo);
        if (wrapPage != null) {
            beanBioServer = bioServerModulo.newEntity(wrapPage);
        }
        if (beanBioServer != null) {
            return WrapBio.bean(beanBioServer);
        }

        return null;
    }


    /**
     * Costruisce un wrapper con le informazioni essenziali <br>
     * <p>
     * Informazioni, contenuto e validità della risposta
     * Controllo del contenuto (testo) ricevuto
     *
     * @param pageIds della pagina wiki usato nella urlRequest.
     *
     * @return WrapPage risultante
     */
    public WrapBio getWrapBio(final long pageIds) {
        WrapPage wrapPage;
        BioServerEntity beanBioServer = null;
        if (pageIds < 1 ) {
            return null;
        }

        wrapPage = appContext.getBean(QueryPage.class).getPage(pageIds);
        if (wrapPage != null) {
            beanBioServer = bioServerModulo.newEntity(wrapPage);
        }
        if (beanBioServer != null) {
            return WrapBio.bean(beanBioServer);
        }

        return null;
    }


}
