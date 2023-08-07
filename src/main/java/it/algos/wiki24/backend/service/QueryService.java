package it.algos.wiki24.backend.service;

import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 18-mag-2022
 * Time: 11:23
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(QueryService.class); <br>
 * 3) @Autowired public QueryService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class QueryService extends WAbstractService {


    public boolean esisteLogin() {
        return botLogin != null;
    }

    public boolean isBotLogin() {
        return botLogin != null && botLogin.isBot();
    }

    public AETypeUser getLoginUserType() {
        return botLogin != null ? botLogin.getUserType() : null;
    }

    public boolean logAsUser() {
        return appContext.getBean(QueryLogin.class).urlRequest(AETypeUser.user).isValido();
    }

    public boolean logAsAdmin() {
        return appContext.getBean(QueryLogin.class).urlRequest(AETypeUser.admin).isValido();
    }

    public boolean logAsBot() {
        return appContext.getBean(QueryLogin.class).urlRequest().isValido();
    }

    public AETypeUser logAsType(final AETypeUser type) {
        return appContext.getBean(QueryLogin.class).urlRequest(type).getUserType();
    }


    public boolean assertBot() {
        return logAsBot();
    }

    public int getSizeCat(final String catTitleGrezzo) {
        return appContext.getBean(QueryInfoCat.class).urlRequest(catTitleGrezzo).getIntValue();
    }

    public List<Long> getCatIdsOrdered(final String catTitleGrezzo) {
        if (appContext != null) {
            return appContext.getBean(QueryCat.class).getPageIdsOrdered(catTitleGrezzo);
        }
        else {
            QueryCat queryCat = new QueryCat();
            return queryCat.getPageIdsOrdered(catTitleGrezzo);
        }
    }

    public List<String> getCatTitles(final String catTitleGrezzo) {
        return appContext.getBean(QueryCat.class).getTitles(catTitleGrezzo);
    }

    public List<String> getSubCat(final String catTitleGrezzo) {
        return appContext.getBean(QueryCat.class, catTitleGrezzo).getSubCat();
    }

    public List<WrapTime> getMiniWrap(final List<Long> listaPageids) {
        return appContext.getBean(QueryTimestamp.class).getWrap(listaPageids);
    }

    public List<WrapBio> getBioWrap(final List<Long> listaPageids) {
        return appContext.getBean(QueryWrapBio.class).getWrap(listaPageids);
    }

    public WrapBio getBioWrap(final long pageId) {
        WrapBio wrapBio = null;
        List<Long> listaPageids = null;
        List<WrapBio> listaWraps = null;

        if (pageId > 0) {
            listaPageids = List.of(pageId);
        }

        if (listaPageids != null && listaPageids.size() > 0) {
            listaWraps = getBioWrap(listaPageids);
        }

        if (listaWraps != null && listaWraps.size() > 0) {
            wrapBio = listaWraps.get(0);
        }

        return wrapBio;
    }

    public WrapBio getBioWrap(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryBio.class).getWrap(wikiTitleGrezzo);
    }

    public Bio getBioGrezzo(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryBio.class).getBioGrezzo(wikiTitleGrezzo);
    }

    public Bio getBioGrezzo(final Long pageId) {
        return appContext.getBean(QueryBio.class).getBioGrezzo(pageId);
    }

    public String getBioTmpl(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryBio.class).getWrap(wikiTitleGrezzo).getTemplBio();
    }


    public boolean isEsiste(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitleGrezzo);
    }

    public boolean isBio(final String wikiTitleGrezzo) {
        return getBioGrezzo(wikiTitleGrezzo) != null;
    }

    public List<String> getList(final String tagIniziale) {
        return appContext.getBean(QueryList.class).getLista(tagIniziale);
    }

    public List<String> getList(final String tagIniziale, final int nameSpace) {
        return appContext.getBean(QueryList.class).nameSpace(nameSpace).getLista(tagIniziale);
    }

    public int getLength(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryInfo.class).getLength(wikiTitleGrezzo);
    }

    public String getTouched(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryInfo.class).getTouched(wikiTitleGrezzo);
    }

    public LocalDateTime getLast(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryInfo.class).getLast(wikiTitleGrezzo);
    }

    public boolean isRedirect(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryRedirect.class).isRedirect(wikiTitleGrezzo);
    }

    public String getWikiLink(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryRedirect.class).getWikiLink(wikiTitleGrezzo);
    }

    public String legge(final String wikiTitleGrezzo) {
        return appContext.getBean(QueryRead.class).getText(wikiTitleGrezzo);
    }


}