package it.algos.wiki24.backend.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

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

    public String legge(final long pageIds) {
        return appContext.getBean(QueryRead.class).getContent(pageIds);
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

    public int getSizeCat(final String categoryTitle) {
        return appContext.getBean(QueryInfoCat.class).getSize(categoryTitle);
    }

    //    public List<Long> getCatIdsOrdered(final String catTitleGrezzo) {
    //        if (appContext != null) {
    //            return appContext.getBean(QueryCat.class).getPageIdsOrdered(catTitleGrezzo);
    //        }
    //        else {
    //            QueryCat queryCat = new QueryCat();
    //            return queryCat.getPageIdsOrdered(catTitleGrezzo);
    //        }
    //    }

    public List<Long> getPageIds(final String categoryTitle) {
        return appContext.getBean(QueryCat.class).getPageIds(categoryTitle);
    }

    public List<WrapBio> getListaBio(final List<Long> listaPageids) {
        return appContext.getBean(QueryListBio.class).getLista(listaPageids);
    }

    public boolean logAsBot() {
        WResult result = appContext.getBean(QueryLogin.class).urlRequestBot();
        return result != null ? result.isValido() : false;
    }

    public List<WrapTime> getMiniWrap(final List<Long> listaPageids) {
        return appContext.getBean(QueryTimestamp.class).getLista(listaPageids);
    }

    public WResult write(final String wikiTitleGrezzo, final String newTesto) {
        return appContext.getBean(QueryWrite.class).urlRequest(wikiTitleGrezzo, newTesto);
    }

    public WResult write(final String wikiTitleGrezzo, final String newTesto, final String summary) {
        return appContext.getBean(QueryWrite.class).urlRequest(wikiTitleGrezzo, newTesto, summary);
    }

    public WResult writeCheck(final String wikiTitleGrezzo, final String newTesto, final String newTextSignificativo, final String summary) {
        return appContext.getBean(QueryWriteCheck.class).urlRequestCheck(wikiTitleGrezzo, newTesto, newTextSignificativo, summary);
    }


}
