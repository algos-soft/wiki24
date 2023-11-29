package it.algos.wiki24.backend.boot;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 15:01
 */
@Service
public class WikiCost {
    public static final String TAG_MODULO ="Modulo:Bio/";

    public static final String KEY_MAPPA_PARSE = "parse";

    public static final String KEY_MAPPA_PAGEID = "pageid";

    public static final String KEY_MAPPA_TITLE = "title";

    public static final String KEY_MAPPA_TEXT = "wikitext";

    public static final String KEY_MAPPA_DOMAIN = "domain";


    public static final String JSON_SUCCESS = "Success";

    public static final String JSON_ERROR = "error";

    public static final String JSON_REASON = "reason";

    public static final String JSON_FAILED = "Failed";

    public static final String JSON_COOKIES = "cookies";

    public static final String JSON_BOT_LOGIN = "botLogin";

    public static final String JSON_NOT_QUERY_LOGIN = "notQueryLogin";

    public static final String JSON_NO_BOT = "assertbotfailed";

    public static final String JSON_CODE = "code";

    public static final String JSON_INFO = "info";

    public static final String SUMMARY = "[[Utente:Biobot|Biobot]]";

    public static final String ENCODE = "UTF-8";

}
