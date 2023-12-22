package it.algos.wiki24.backend.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 21-Dec-2023
 * Time: 14:34
 */
@Service
public class QueryService {

    @Inject
    public ApplicationContext appContext;

    public String legge(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryRead.class).getContent(wikiTitleGrezzo);
    }

    public WrapPage getPage(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryPage.class).getPage(wikiTitleGrezzo);
    }

    public WrapPage getPage(final long pageIds) {
        return appContext.getBean(QueryPage.class).getPage(pageIds);
    }

    public WrapBio getBio(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryBio.class).getWrapBio(wikiTitleGrezzo);
    }

    public WrapBio getBio(final long pageIds) {
        return appContext.getBean(QueryBio.class).getWrapBio(pageIds);
    }

    public boolean isEsiste(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitleGrezzo);
    }
    public int getSizeCat(final String wikiTitleGrezzoCategoria) {
        return appContext.getBean(QueryInfoCat.class).getSize(wikiTitleGrezzoCategoria);
    }


}
