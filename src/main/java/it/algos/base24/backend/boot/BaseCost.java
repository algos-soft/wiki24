package it.algos.base24.backend.boot;

import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 16:19
 */
@Service
public class BaseCost {


    public static final String KEY_NULL = "null";

    public static final String TAG_INIZIALE = "http://";

    public static final String TAG_INIZIALE_SECURE = "https://";

    public static final String TAG_WIKI = "https://it.wikipedia.org/wiki/";

    public static final String TAG_ISO_3166 = "ISO 3166-2:";

    public static final String WIKI_PARSE = "https://it.wikipedia.org/w/api.php?&format=json&formatversion=2&action=parse&prop=wikitext&page=";

    public static final String ESCLAMATIVO = "!";

    public static final String PIPE = "|";

    public static final String UGUALE = "=";

    public static final String SEP_TABLE = "|-";

    public static final String CONFIG = "config";

    public static final String FRONTEND = "frontend";

    public static final String PATH_ALGOS = "it.algos";

    public static final String PATH_BOOT = "backend.boot";

    public static final String HTML_WIDTH_UNIT = "rem"; //Font size of the root element.

    public static final String VUOTA = "";

    public static final String TRATTINO = "-";

    public static final String SMALL = "<small>";

    public static final String ASTERISCO = "*";

    public static final String ASTERISCO_REGEX = "\\*";

    public static final String CAPO = "\n";

    public static final String CAPO_SPLIT = "\\n";

    public static final String CAPO_REGEX = "\\\\n";

    public static final String CAPO_HTML = "</br>";

    public static final String TAB = "\t";

    public static final String APICETTI = "\"";

    public static final String VIRGOLA = ",";

    public static final String PUNTO_VIRGOLA = ";";

    public static final String PUNTO_INTERROGATIVO = "?";

    public static final String VIRGOLA_CAPO = VIRGOLA + CAPO;

    public static final String SLASH = "/";

    public static final String PUNTO = ".";

    public static final String DUE_PUNTI = ":";

    public static final String TRE_PUNTI = "...";

    public static final String SPAZIO = " ";

    public static final String SOMMA = "+";

    public static final String SOMMA_SPAZIO = SPAZIO + SOMMA + SPAZIO;

    public static final String SPAZIO_NON_BREAKING = "&nbsp;";

    public static final String UNDERSCORE = "_";

    public static final String DUE_PUNTI_SPAZIO = DUE_PUNTI + SPAZIO;

    public static final String DOPPIO_SPAZIO = SPAZIO + SPAZIO;

    public static final String TAB_SPAZIO = DOPPIO_SPAZIO + DOPPIO_SPAZIO;

    public static final String VIRGOLA_SPAZIO = VIRGOLA + SPAZIO;

    public static final String UGUALE_SEMPLICE = "=";

    public static final String UGUALE_SPAZIATO = SPAZIO + UGUALE_SEMPLICE + SPAZIO;

    public static final String PIPE_REGEX = "\\|";

    public static final String DOPPIO_ESCLAMATIVO = ESCLAMATIVO + ESCLAMATIVO;

    public static final String DOPPIO_PIPE_REGEX = PIPE_REGEX + PIPE_REGEX;

    public static final String PERCENTUALE = "%";

    public static final String FORWARD = " -> ";

    public static final String NULLO = "(null)";

    public static final String TAG_VUOTA = "VUOTA";

    public static final String SEP = SPAZIO + TRATTINO + SPAZIO;

    public static final String GRAFFA_INI = "{";

    public static final String DOPPIE_GRAFFE_INI = GRAFFA_INI + GRAFFA_INI;

    public static final String GRAFFA_END = "}";

    public static final String DOPPIE_GRAFFE_END = GRAFFA_END + GRAFFA_END;

    public static final String PARENTESI_TONDA_INI = "(";

    public static final String PARENTESI_TONDA_END = ")";

    public static final String MILLI_SECONDI = " millisecondi";


    public static final String FIELD_NAME_ID_SENZA = "id";

    public static final String FIELD_NAME_ID_CON = "_id";

    public static final String FIELD_NAME_ID_LINK = ".$id";

    public static final String FIELD_NAME_ORDINE = "ordine";

    public static final String FIELD_NAME_PREF_INIZIALE = "iniziale";

    public static final String FIELD_NAME_PREF_CORRENTE = "corrente";

    public static final String FIELD_HEADER_ORDINE = "#";

    public static final String TAG_ALTRE = "...";

    public static final String TAG_REF = "<ref";

    public static final String TAG_ALTRE_BY = TAG_ALTRE + SPAZIO + "by" + SPAZIO;

    public static final String FIELD_NAME_CODE = "code";

    public static final String FIELD_NAME_NOME = "nome";

    public static final List<String> FIELD_KEY_NAME_LIST = Arrays.asList("code", "codice", "sigla", "nome", "ordine"); // principali (solo) suggeriti

    public static final List<String> FIELD_SORT_NAME_LIST = Arrays.asList("ordine", "code", "codice", "sigla", "nome"); // principali (solo) suggeriti

    public static final String FIELD_NAME_DESCRIZIONE = "descrizione";

    public static final String VALORE_STANDARD = "valore standard";

    public static final String VALORE_SPECIFICO = "valore specifico";

    public static final String DATABASE_ADMIN = "admin";


    public static final String SUFFIX_LIST = "List";

    public static final String SUFFIX_MODULO = "Modulo";

    public static final String SUFFIX_FORM = "Form";

    public static final String SUFFIX_ENTITY = "Entity";

    public static final String SUFFIX_BACKEND = "Backend";

    public static final String SUFFIX_REPOSITORY = "Repository";

    public static final String SUFFIX_ROUTE = "Route";

    public static final String SUFFIX_VIEW = "View";

    public static final String SUFFIX_DIALOG = "Dialog";

    public static final String SUFFIX_SERVICE = "Service";

    public static final String SUFFIX_BOOT = "Boot";

    public static final String SUFFIX_VERS = "Vers";

    public static final String SUFFIX_DATA = "Data";

    public static final String SUFFIX_VAR = "Var";

    public static final String SUFFIX_CSV = ".csv";

    public static final String NO_INCLUDE = "<noinclude>";

    public static final String HTML_CODE = "code";

    public static final String HTML_KBD = "kbd";

    public static String TEXT_STANDARD = "Si possono creare, modificare e cancellare i valori.";

    public static String TEXT_TAVOLA = "Tavola di servizio usata solo in background.";

    public static String TEXT_BASE = "Tavola di base.";

    public static String TEXT_WIKI = TEXT_BASE + SPAZIO + "Costruita dalle pagine Wiki: ";


    public static String TEXT_CSV = "File originale (CSV) sul server [/www.algos.it/vaadin24/config/%s]";

    public static String TEXT_HARD = "Solo hard coded. Non creabili, non modificabili e non cancellabili.";

    public static String TEXT_CODE = "Costruita hardcoded in %sModulo.resetDelete().";

    public static String TEXT_ENUM = "Costruita dalla enum %sEnum in %sModulo.resetDelete().";

    public static String TEXT_ENUM_PREF = "Costruita dalla enum Pref in PreferenzaModulo.resetDelete().";

    public static String TEXT_NEWS = "Creazione automatica della tavola. Si possono modificare i valori. Se ne possono aggiungere altri.";

    public static String TEXT_PREF = "Creazione automatica di tutti i valori. Non se ne possono creare di nuovi. Si può modificare la descrizione ed il valore.";

    public static String DELETE = "Cancella dal database tutti i valori della collezione";

    public static String TEXT_DELETE = "Delete: " + DELETE;

    public static String DELETE_RICREA = DELETE + " e li ricrea.";

    public static String TEXT_RESET_DELETE = "ResetDelete. " + DELETE_RICREA;

    public static String TEXT_SICURO = "Sei sicuro? L'operazione è irreversibile";

    public static String TEXT_RESET_ADD = "ResetAdd. Reinserisce elementi originali mancanti. Mantiene le modifiche ed i nuovi inserimenti.";

    public static String TEXT_RESET_PREF = "ResetPref. Aggiunge nuove preferenze; aggiorna i valori di default e le descrizioni esistenti; inalterati i valori correnti.";

    public static String TEXT_DOWNLOAD = "Download. Ricarica tutti i valori dal server.";

    public static String TEXT_SEARCH = "Search ...by [%s] è 'caseInsensitive' su inizio della property di Text.";

    public static String TEXT_EXPORT = "Export dati come esempio.";

    public static String TEXT_TYPE_DATE = "Test di date utilizzabili nelle liste in vari formati selezionati da [TypeDate] in @AField di xxxModel.";


    public static final String TAG_INNER_HTML = "innerHTML";

    public static final String TAG_HTML_COLOR = "color";

    public static final String TAG_HTML_FONT_WEIGHT = "font-weight";

    public static final String TAG_HTML_FONT_SIZE = "font-size";

    public static final String TAG_HTML_LINE_HEIGHT = "line-height";

    public static final String TAG_HTML_FONT_STYLE = "font-style";

    public static final String VERO = "true";

    public static final String FALSO = "false";

    public static final String TAG_LOG_ADMIN_DEFAULT = "base24.admin";

    public static final String FORM_TAG_NEW = "Nuovo/a";//"new"

    public static final String FORM_TAG_SHOW = "Mostra";//"Show"

    public static final String FORM_TAG_EDIT = "Modifica";//"Edit"


    public static final String BUTTON_REGISTRA = "Registra";

    public static final String BUTTON_CONFERMA = "Conferma";

    public static final String BUTTON_ANNULLAz = "Annulla";

    public static final String BUTTON_DELETE = "Cancella";

    public static final String BUTTON_RESET = "Reset";

    public static final String BUTTON_CANCELLA = "Annulla";

    public static final String BUTTON_REJECT = "Cancella";

    public static final String KEY_TAG_PROPERTY_KEY = "key";

    public static final String KEY_TAG_PROPERTY_CAPTION = "caption";

    //    public static final String COLOR_VERO = "#9FE2BF";
    public static final String COLOR_VERO = "green";

    //    public static final String COLOR_FALSO = "#FF7F50";
    public static final String COLOR_FALSO = "red";

    public static final Locale LOCALE = Locale.ITALIAN;

    public static final LocalDateTime ROOT_DATA_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);

    public static final LocalDateTime ERROR_DATA_TIME = LocalDateTime.of(1978, 4, 17, 14, 35);


    public static final String GIALLO = "\u001b[38;5;111m%s\u001b[0m";

    public static final String ROSSO = "\u001b[38;5;196m%s\u001b[0m";

    public static final String VERDE = "\u001b[38;5;35m%s\u001b[0m";

    public static final String BLUE = "\u001b[94m%s\u001b[0m";

    public static final String VIOLA = "\u001b[38;111;5m%s\u001b[0m";

    public static final int PAD_TYPE = 14;

    public static final int PAD_CLASS = 20;

    public static final int PAD_SYSTEM = 10;

    public static final int PAD_METHOD = 20;

    public static final int PAD_LINE = 4;

    public static final int PAD_COMPANY = 5;

    public static final int PAD_USER = 18;

    public static final int PAD_IP = 37;


    public static final String QUADRA_INI = "[";

    public static final String DOPPIE_QUADRE_INI = QUADRA_INI + QUADRA_INI;

    public static final String QUADRA_END = "]";

    public static final String DOPPIE_QUADRE_END = QUADRA_END + QUADRA_END;

    public static String METHOD_EXPORT = "creaExcelExporter";

    public static String METHOD_RESET_STARTUP = "resetStartup";

    public static String METHOD_RESET_DELETE = "resetDelete";

    public static String METHOD_RESET_ADD = "resetAdd";

    public static final String PRIMO_GIORNO_MESE = "1º";

    public static final String KEY_MAPPA_GIORNI_MESE_NUMERO = "meseNumero";

    public static final String KEY_MAPPA_GIORNI_MESE_TESTO = "meseTesto";

    public static final String KEY_MAPPA_GIORNI_NORMALE = "normale";

    public static final String KEY_MAPPA_GIORNI_BISESTILE = "bisestile";

    public static final String KEY_MAPPA_GIORNI_NOME = "nome";

    public static final String KEY_MAPPA_GIORNI_TITOLO = "titolo";

    public static final String KEY_MAPPA_GIORNI_MESE_MESE = "meseMese";

    public static final int NUM_GIORNI_ANNO = 366;

    public static final int DELTA_ANNI = 1000;

    public static final int ANTE_CRISTO_MAX = 1000;

    public static final int DOPO_CRISTO_MAX = 2030;

    public static final String QUALIFIER_LIST_BUTTON_BAR = "listButtonBar";

    public static final String MAIL_TO = "gac@algos.it";

    public static final String SCHEDULED_ZERO_TRENTA = "30 0 * * *";

    public static final String SCHEDULED_ZERO_QUARANTA = "40 0 * * *";


    public static final String REF = "<ref>";

    public static final String HTML = "[html";

    public static final String REF_OPEN = "<ref";

    public static final String REF_NAME = "<ref name=";

    public static final String REF_END = "</ref>";

    public static final String CIRCA = "circa";

    public static final String REF_TAG = SPAZIO + "{{#tag:ref";
    public static final String NOTE = "<!--";
    public static final String NO_WIKI = "<nowiki>";
    public static final String PARENTESI_TONDA_INI_REGEX = "\\(";

    public static final String PARENTESI_TONDA_END_REGEX = "\\)";
    public static final String ECC = "ecc.";

}

