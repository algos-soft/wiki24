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

    public static final String TAG_MODULO = "Modulo:Bio/";

    public static final String PATH_MODULO = TAG_WIKI + TAG_MODULO;

    public static final String PATH_BIOGRAFIE = "Progetto:Biografie/";

    public static final String KEY_MAPPA_PARSE = "parse";

    public static final String KEY_MAPPA_PAGEID = "pageid";

    public static final String KEY_MAPPA_TITLE = "title";

    public static final String KEY_MAPPA_TEXT = "wikitext";

    public static final String KEY_MAPPA_DOMAIN = "domain";

    public static final String KEY_MAPPA_NOME = "Nome";

    public static final String KEY_MAPPA_COGNOME = "Cognome";

    public static final String KEY_MAPPA_SESSO = "Sesso";

    public static final String KEY_MAPPA_LUOGO_NASCITA = "LuogoNascita";

    public static final String KEY_MAPPA_LUOGO_NASCITA_LINK = "LuogoNascitaLink";

    public static final String KEY_MAPPA_GIORNO_NASCITA = "GiornoMeseNascita";

    public static final String KEY_MAPPA_ANNO_NASCITA = "AnnoNascita";

    public static final String KEY_MAPPA_LUOGO_MORTE = "LuogoMorte";

    public static final String KEY_MAPPA_LUOGO_MORTE_LINK = "LuogoMorteLink";

    public static final String KEY_MAPPA_GIORNO_MORTE = "GiornoMeseMorte";

    public static final String KEY_MAPPA_ANNO_MORTE = "AnnoMorte";

    public static final String KEY_MAPPA_ATTIVITA = "Attività";

    public static final String KEY_MAPPA_ATTIVITA_DUE = "Attività2";

    public static final String KEY_MAPPA_ATTIVITA_TRE = "Attività3";

    public static final String KEY_MAPPA_ATTIVITA_ALTRE = "AttivitàAltre";

    public static final String KEY_MAPPA_NAZIONALITA = "Nazionalità";


    public static final String JSON_SUCCESS = "Success";

    public static final String JSON_ERROR = "error";

    public static final String JSON_REASON = "reason";

    public static final String JSON_FAILED = "Failed";


    public static final String ERROR_JSON_BOT_NO_LOGIN = "noBotLogin";

    public static final String ERROR_JSON_BOT_NO_QUERY = "notQueryLogin";

    public static final String ERROR_JSON_BOT_NO_COOKIES = "noCookies";

    public static final String ERROR_JSON_BOT_NO_RIGHT = "assertbotfailed";

    public static final String JSON_NO_BOT = "assertbotfailed";

    public static final String JSON_CODE = "code";

    public static final String JSON_INFO = "info";

    public static final String SUMMARY = "[[Utente:Biobot|Biobot]]";

    public static final String ENCODE = "UTF-8";

    public static final String TAG_EX_SPAZIO = "Ex" + SPAZIO;

    public static final String ATT = "Attività";

    public static final String NAZ = "Nazionalità";

    public static final String ATT_LOWER = ATT.toLowerCase();

    public static final String PATH_STATISTICHE_ATTIVITA = PATH_BIOGRAFIE + ATT;

    public static final String PATH_STATISTICHE_NAZIONALITA = PATH_BIOGRAFIE + NAZ;

    public static final String STATISTICHE = "Statistiche";


    public static final String WIKI_TITLE_DEBUG = "Utente:Biobot/2";

    public static final String FIELD_NAME_SINGOLARE = "singolare";

    public static final String FIELD_NAME_PLURALE = "plurale";

    public static final String FIELD_NAME_LUOGO_NATO = "luogoNato";

    public static final String FIELD_NAME_LUOGO_MORTO = "luogoMorto";

    public static final String FIELD_NAME_GIORNO_NATO = "giornoNato";

    public static final String FIELD_NAME_GIORNO_MORTO = "giornoMorto";

    public static final String FIELD_NAME_ANNO_NATO = "annoNato";

    public static final String FIELD_NAME_ANNO_MORTO = "annoMorto";

    public static final int DELTA_ORDINE_ANNI = 100;

    public static final String TAG_BOLD = "'''";

    public static final String TAG_ITALIC = "''";

    public static final String PATH_LINK = "Link ";

    public static final String UNCONNECTED = "__EXPECTED_UNCONNECTED_PAGE__";

    public static final String NOEDITSECTION = "__NOEDITSECTION__";

    public static final String PATH_SINGOLARE = "Singolare/";

    public static final String PATH_PLURALE = "Plurale ";

    public static final String PATH_EX = "Ex ";

    public static final String WIKI_TAG_UTILITY = "wikiUtility";

    public static final String TAG_WIKI23_VERSION = "wikiversion";

    public static final String TAG_WIKI23_PREFERENCES = "wikipreferences";

    public static final String INCLUDE_INI = "<includeonly>";

    public static final String INCLUDE_END = "</includeonly>";

    public static final String PARAGRAFO_INCLUDE_INI = INCLUDE_INI + UGUALE_SEMPLICE + INCLUDE_END + UGUALE_SEMPLICE + UGUALE_SEMPLICE;

    public static final String PARAGRAFO_INCLUDE_END = UGUALE_SEMPLICE + UGUALE_SEMPLICE + INCLUDE_INI + UGUALE_SEMPLICE + INCLUDE_END;

    public static final String NO_INCLUDE_INI = "<noinclude>";

    public static final String NO_INCLUDE_END = "</noinclude>";

    public static final String NO_WIKI_INI = "<nowiki>";

    public static final String NO_WIKI_END = "</nowiki>";

    public static final String PATH_MODULO_LINK = PATH_MODULO + PATH_LINK;

    public static final String PATH_MODULO_PLURALE = PATH_MODULO + PATH_PLURALE;

    public static final String GENERE = "genere";

    public static final String GIORNI = "Giorni";

    public static final String ANNI = "Anni";

    public static final String ANNI_AC = "a.C.";

    public static final String DOPPI = "Nomi doppi";

    public static final String TAG_LISTA_ALTRE = "Altre...";

    public static final String TAG_LISTA_NO_ATTIVITA = "Senza attività specificata";

    public static final String TAG_LISTA_NO_NAZIONALITA = "Senza nazionalità specificata";

    public static final String TAG_LISTA_NO_GIORNO = "Senza giorno specificato";

    public static final String TAG_LISTA_NO_ANNO = "Senza anno specificato";

    public static final String TAG_NO_SECOLO = "Secolo inesistente";

    public static final String TAG_NO_MESE = "Mese inesistente";


    public static final String PATH_WIKI = "https://it.wikipedia.org/wiki/";

    public static final String PATH_WIKI_EDIT = "https://it.wikipedia.org/w/index.php?title=";

    public static final String TAG_EDIT = "&action=edit";

    public static final String TAG_EDIT_ZERO = "&action=edit&section=0";

    public static final String TAG_DELETE = "&action=delete";

    public static final String API_BASE = "https://it.wikipedia.org/w/api.php?&format=json&formatversion=2";

    public static final String ACTION_PARSE = API_BASE + "&action=parse";

    //    public static final String WIKI_PARSE = ACTION_PARSE + "&prop=wikitext&page=";

    public static final String ACTION_QUERY = API_BASE + "&action=query";


    public static final String WIKI_QUERY_LIST = ACTION_QUERY + "&list=allpages&aplimit=500&apprefix=";

    public static final String WIKI_QUERY_INFO = ACTION_QUERY + "&prop=info&titles=";

    public static final String LIST_NAME_SPACE = "&apnamespace=";

    public static final String API_TITLES = "&titles=";

    public static final String API_PAGEIDS = "&pageids=";

    //    public static final String CAT = "Category:";
    public static final String CAT = "Categoria:";

    public static final String API_CAT = "&cmtitle=";

    public static final String API_CAT_INFO = API_TITLES;

    public static final String WIKI_QUERY = ACTION_QUERY + API_TITLES;

    public static final String WIKI_QUERY_PAGEIDS = ACTION_QUERY + API_PAGEIDS;

    public static final String QUERY_TIMESTAMP = "&prop=revisions&rvprop=timestamp";

    public static final String QUERY_INFO = QUERY_TIMESTAMP + "|content";

    public static final String QUERY_INFO_ALL = "&rvslots=main&prop=info|revisions&rvprop=content|ids|flags|timestamp|user|userid|comment|size";

    public static final String QUERY_CAT_INFO = "&prop=categoryinfo";

    public static final String QUERY_CAT_REQUEST = ACTION_QUERY + "&list=categorymembers" + API_CAT;

    public static final String WIKI_QUERY_BASE_TITLE = ACTION_QUERY + QUERY_INFO + API_TITLES;

    public static final String WIKI_QUERY_BASE_PAGE = ACTION_QUERY + QUERY_INFO + API_PAGEIDS;

    public static final String WIKI_QUERY_TIMESTAMP = ACTION_QUERY + QUERY_TIMESTAMP + API_PAGEIDS;

    public static final String WIKI_QUERY_ALL = ACTION_QUERY + QUERY_INFO_ALL + API_TITLES;

    public static final String WIKI_QUERY_CAT_INFO = ACTION_QUERY + QUERY_CAT_INFO + API_CAT_INFO;

    //
    //    public static final String API = "https://it.wikipedia.org/w/api.php?&format=json&formatversion=2";
    //
    //    public static final String API_QUERY = API + "&action=query&titles=";
    //
    //    public static final String API_PARSE = "https://it.wikipedia.org/w/api.php?action=parse&prop=wikitext&formatversion=2&format=json&page=";

    //    public static final String API_EDIT = "https://it.wikipedia.org/w/index.php?action=edit&section=0&title=";

    //    public static final String API_HISTORY = "https://it.wikipedia.org/w/index.php?action=history&title=";

    public static final String TAG_ANTROPONIMI = "Progetto:Antroponimi/";

    public static final String PATH_ANTROPONIMI = PATH_WIKI + TAG_ANTROPONIMI;

    public static final String MESI_REGEX = "(gennaio|febbraio|marzo|aprile|maggio|giugno|luglio|agosto|settembre|ottobre|novembre|dicembre)$";

    public static final String PATH_ATTIVITA = PATH_BIOGRAFIE + ATT;

    public static final String PATH_NOMI = "Persone di nome" + SPAZIO;

    public static final String PATH_COGNOMI = "Persone di cognome" + SPAZIO;

    public static final String PATH_NAZIONALITA = PATH_BIOGRAFIE + NAZ;

    public static final String PATH_GIORNI = PATH_BIOGRAFIE + GIORNI;

    public static final String PATH_ANNI = PATH_BIOGRAFIE + ANNI;

    public static final String PATH_STATISTICHE = PATH_BIOGRAFIE + STATISTICHE;

    public static final String PATH_MODULO_ATTIVITA = PATH_MODULO_PLURALE + ATT_LOWER;

    public static final String PATH_MODULO_GENERE = PATH_MODULO_PLURALE + ATT_LOWER + SPAZIO + GENERE;

    public static final String PATH_MODULO_PROFESSIONE = PATH_MODULO_LINK + ATT_LOWER;

    public static final String PATH_TABELLA_NOMI_DOPPI = TAG_ANTROPONIMI + DOPPI;

    public static final String TAG_NOMI = "Nomi";

    public static final String TAG_COGNOMI = "Cognomi";

    public static final String TAG_LISTA_NOMI = "Liste nomi";

    public static final String TAG_LISTA_COGNOMI = "Liste cognomi";

    public static final String INCIPIT_NOMI = "Incipit nomi";

    public static final String INCIPIT_COGNOMI = "Incipit cognomi";

    public static final String TAG_INCIPIT_NOMI = "Modulo:" + INCIPIT_NOMI;

    public static final String TAG_INCIPIT_COGNOMI = "Modulo:" + INCIPIT_COGNOMI;

    public static final String MODULO = "Modulo";

    public static final String PROGETTO = "Progetto";

    public static final String CATEGORIA = "Categoria";

    public static final String TEMPLATE = "Template";

    public static final String TEST = "Test";

    public static final String COGNOMI_LINGUA = "Cognomi per lingua";

    public static final String CATEGORIA_COGNOMI = CATEGORIA + DUE_PUNTI + COGNOMI_LINGUA;


    public static final String NAZ_LOWER = NAZ.toLowerCase();

    public static final String PATH_MODULO_NAZIONALITA = PATH_MODULO_PLURALE + NAZ_LOWER;

    public static final String PATH_STATISTICHE_COGNOMI = PATH_ANTROPONIMI + "Liste cognomi";

    public static final String PATH_STATISTICHE_ANNI = PATH_BIOGRAFIE + ANNI;

    public static final String PATH_CATEGORIA = PATH_WIKI + "Categoria:";

    public static final String LINK_PAGINA_ATTIVITA = "Progetto:Biografie/Attività/";

    public static final String LINK_PAGINA_NAZIONALITA = "Progetto:Biografie/Nazionalità/";

    public static final String TAG_EX2 = "ex-";

    public static final String TAG_GENERE = "genere";

    public static final String TAG_ATTIVITA = "attivita";

    public static final String TAG_NAZIONALITA = "nazionalita";

    public static final String TAG_PROFESSIONE = "professione";

    //    public static final String TAG_DOPPIO_NOME = "doppionome";
    public static final String TAG_NOME_DOPPIO = "nomedoppio";

    public static final String TAG_BIO = "bio";

    public static final String FIELD_NAME_PAGE_ID = "pageId";

    public static final String FIELD_NAME_WIKI_TITLE = "wikiTitle";

    public static final String FIELD_NAME_NOME = "nome";

    public static final String FIELD_NAME_COGNOME = "cognome";

    public static final String FIELD_NAME_SESSO = "sesso";

    public static final String FIELD_NAME_GREZZO = "grezzo";

    public static final String FIELD_NAME_ELABORATO = "elaborato";

    public static final String FIELD_NAME_GREZZO_VUOTO = "grezzoVuoto";

    public static final String FIELD_NAME_ELABORATO_VUOTO = "elaboratoVuoto";

    public static final String FIELD_NAME_UGUALE = "uguale";

    public static final String KEY_JSON_QUERY = "query";

    public static final String KEY_JSON_ERROR = "error";

    public static final String KEY_JSON_NORMALIZED = "normalized";

    public static final String KEY_JSON_ENCODED = "fromencoded";

    public static final String KEY_JSON_CONTINUE = "continue";

    public static final String KEY_JSON_CONTINUE_CM = "cmcontinue";

    public static final String KEY_JSON_CONTINUE_LIST = "apcontinue";

    public static final String KEY_JSON_ALL = "all";

    public static final String KEY_JSON_PAGES = "pages";

    public static final String KEY_JSON_ALL_PAGES = "allpages";

    public static final String KEY_JSON_NUM_PAGES = "numpages";

    public static final String KEY_JSON_PAGE_ID = "pageid";

    public static final String KEY_JSON_NS = "ns";

    public static final String KEY_JSON_TITLE = "title";

    public static final String KEY_JSON_MISSING = "missing";

    public static final String KEY_JSON_MISSING_TRUE = "missing=true";

    public static final String KEY_JSON_REVISIONS = "revisions";

    public static final String KEY_JSON_TIMESTAMP = "timestamp";

    public static final String KEY_JSON_LOGIN = "login";

    public static final String KEY_JSON_SLOTS = "slots";

    public static final String KEY_JSON_MAIN = "main";

    public static final String KEY_JSON_CONTENT = "content";


    public static final String KEY_JSON_CATEGORY_MEMBERS = "categorymembers";

    public static final String KEY_JSON_CATEGORY_INFO = "categoryinfo";

    public static final String KEY_JSON_CATEGORY_PAGES = "categorypages";

    public static final String KEY_JSON_CATEGORY_SIZE = "size";

    public static final String KEY_JSON_TOUCHED = "touched";

    public static final String KEY_JSON_LENGTH = "length";

    public static final String KEY_JSON_CODE = "code";

    public static final String KEY_JSON_INFO = "info";

    public static final String KEY_JSON_ZERO = "zero";

    public static final String KEY_JSON_BATCH = "batchcomplete";

    public static final String KEY_JSON_DISAMBIGUA = "disambigua";

    public static final String KEY_JSON_REDIRECT = "redirect";


    public static final String KEY_MAP_GRAFFE_ESISTONO = "keyMapGraffeEsistono";

    public static final String KEY_MAP_GRAFFE_TYPE = "keyMapGraffeType";

    public static final String KEY_MAP_GRAFFE_NUMERO = "keyMapGraffeNumero";

    public static final String KEY_MAP_GRAFFE_VALORE_CONTENUTO = "keyMapGraffeValoreContenuto";

    public static final String KEY_MAP_GRAFFE_TESTO_PRECEDENTE = "keyMapGraffeTestoPrecedente";

    public static final String KEY_MAP_GRAFFE_NOME_PARAMETRO = "keyMapGraffeNomeParametro";

    public static final String KEY_MAP_GRAFFE_VALORE_PARAMETRO = "keyMapGraffeValoreParametro";

    public static final String KEY_MAP_GRAFFE_LISTA_WRAPPER = "keyMapGraffeListaWrapper";

    private static final String PROPERTY_ERROR = "In application.properties non esiste il valore di ";

    public static final String PROPERTY_LOGIN_NAME = PROPERTY_ERROR + "loginName";

    public static final String PROPERTY_LOGIN_PASSWORD = PROPERTY_ERROR + "loginPassword";

    public static final String SUCCESS_LOGIN_RESPONSE = "lguserid: 124123, lgusername: Biobot";


    public static final String ATTIVITA_PROPERTY = "attivita";

    public static final String ATTIVITA_PROPERTY_2 = "attivita2";

    public static final String ATTIVITA_PROPERTY_3 = "attivita3";

    public static final String DEFAULT_SORT = "{{DEFAULTSORT:";

    public static final String DUBBIO_O = SPAZIO + "o" + SPAZIO;

    public static final String DUBBIO_O_PAR = PARENTESI_TONDA_INI + "o" + SPAZIO;

    public static final String DUBBIO_OPPURE = SPAZIO + "oppure" + SPAZIO;

    public static final String DUBBIO_TRATTINO = SPAZIO + TRATTINO + SPAZIO;

    public static final String PRIMO_WIN = "º";

    public static final String PRIMO_MAC = "°";

    public static final String DUBBIO_CALENDARIO = "calendario";

}
