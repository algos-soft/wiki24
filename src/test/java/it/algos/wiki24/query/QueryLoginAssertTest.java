package it.algos.wiki24.query;


import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.base24.backend.boot.BaseCost.WIKI_PARSE;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import static it.algos.wiki24.backend.query.AQuery.*;
import static it.algos.wiki24.backend.query.QueryLogin.*;
import static it.algos.wiki24.backend.service.WikiApiService.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.json.simple.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mar, 17-mag-2022
 * Time: 17:03
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryAssert")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryLoginAssertTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryAssert istanza;

    private String logintoken;

    private String lgtoken;

    private String lgname;

    private String lgpassword;

    private long lguserid;

    private String lgusername;

    private String code;

    private String risultato;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNull(istanza);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        istanza = null;
        logintoken = VUOTA;
        lgtoken = VUOTA;
        lguserid = 0;
        lgusername = VUOTA;
    }

    @Test
    @Order(11)
    @DisplayName("11 - wikiParse")
    void wikiParse() {
        System.out.println("11 - wikiParse");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        String urlDomain1;
        urlDomain1 = WIKI_PARSE;
        urlDomain1 += "Abeloya";
        String urlResponse1;

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(12)
    @DisplayName("12 - wikiParseAssert")
    void wikiParseAssert() {
        System.out.println("12 - wikiParseAssert");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        String urlDomain1;
        urlDomain1 = WIKI_PARSE;
        urlDomain1 += "Abeloya";
        urlDomain1 += "&assert=user";
        String urlResponse1;

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }

    @Test
    @Order(21)
    @DisplayName("21 - wikiQuery")
    void wikiQuery() {
        System.out.println("21 - wikiQuery");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        String urlDomain1;
        urlDomain1 = WIKI_QUERY;
        urlDomain1 += "Abeloya";
        String urlResponse1;

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(22)
    @DisplayName("22 - wikiQueryAssert")
    void wikiQueryAssert() {
        System.out.println("22 - wikiQueryAssert");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        String urlDomain1;
        urlDomain1 = WIKI_QUERY;
        urlDomain1 += "Abeloya";
        urlDomain1 += "&assert=user";
        String urlResponse1;

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(31)
    @DisplayName("31 - preliminaryRequestGet")
    void preliminaryRequestGet() {
        System.out.println("31 - preliminaryRequestGet");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        Map<String, String> cookiesPrimary = null;
        String urlDomain1 = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlResponse1;

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(32)
    @DisplayName("32 - secondaryRequestPost")
    void secondaryRequestPost() {
        System.out.println("32 - secondaryRequestPost");
        System.out.println(VUOTA);
        URLConnection urlConn1;
        URLConnection urlConn2;
        Map<String, String> cookiesPrimary = null;
        Map<String, String> cookiesSecondary = null;
        String urlDomain1 = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlDomain2 = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlResponse1;
        String urlResponse2;
        lgname = "hamed";
        lgpassword = "sokoto79";

        try {
            urlConn1 = this.creaGetConnection(urlDomain1);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1%s%s", FORWARD, urlDomain1));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI per la SecondaryRequest (nel POST)");
            System.out.println(String.format("lgname%s%s", FORWARD, lgname));
            System.out.println(String.format("lgpassword%s%s", FORWARD, lgpassword));
            System.out.println("POST" + FORWARD + elaboraPost());
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesPrimary) per la SecondaryRequest");
            printCookies(cookiesPrimary);

            urlConn2 = this.creaGetConnection(urlDomain2);
            uploadCookies(urlConn2, cookiesPrimary);
            addPostConnection(urlConn2);
            urlResponse2 = this.sendRequest(urlConn2);
            cookiesSecondary = downlodCookies(urlConn2);
            risultato = elaboraSecondaryResponse(urlResponse2);

            System.out.println(VUOTA);
            System.out.println(String.format("Url2%s%s", FORWARD, urlDomain2));
            System.out.println(urlResponse2);
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest");
            System.out.println(String.format("risultato%s%s", FORWARD, risultato));
            System.out.println(String.format("code%s%s", FORWARD, code));
            System.out.println(String.format("lguserid%s%s", FORWARD, lguserid));
            System.out.println(String.format("lgusername%s%s", FORWARD, lgusername));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest (cookiesSecondary)");
            printCookies(cookiesSecondary);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(41)
    @DisplayName("41 - assertCookiesSecondary")
    void assertCookiesSecondary() {
        System.out.println("41 - assertCookiesSecondary");
        URLConnection urlConn1;
        URLConnection urlConn2;
        URLConnection urlConn3;
        Map<String, String> cookiesPrimary = null;
        Map<String, String> cookiesSecondary = null;
        String urlDomain1GET = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlDomain2POST = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlDomain3GET = WIKI_QUERY;
        urlDomain3GET += "Abeloya";
        urlDomain3GET += "&assert=user";
        System.out.println(String.format("Url%s%s", FORWARD, urlDomain1GET));
        System.out.println(VUOTA);
        String urlResponse1 = VUOTA;
        String urlResponse2 = VUOTA;
        String urlResponse3 = VUOTA;
        lgname = "hamed";
        lgpassword = "sokoto79";

        try {
            urlConn1 = this.creaGetConnection(urlDomain1GET);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1GET%s%s", FORWARD, urlDomain1GET));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI per la SecondaryRequest (nel POST)");
            System.out.println(String.format("lgname%s%s", FORWARD, lgname));
            System.out.println(String.format("lgpassword%s%s", FORWARD, lgpassword));
            System.out.println("POST" + FORWARD + elaboraPost());
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesPrimary) per la SecondaryRequest");
            printCookies(cookiesPrimary);

            urlConn2 = this.creaGetConnection(urlDomain2POST);
            uploadCookies(urlConn2, cookiesPrimary);
            addPostConnection(urlConn2);
            urlResponse2 = this.sendRequest(urlConn2);
            cookiesSecondary = downlodCookies(urlConn2);
            risultato = elaboraSecondaryResponse(urlResponse2);

            System.out.println(VUOTA);
            System.out.println(String.format("Url2POST%s%s", FORWARD, urlDomain2POST));
            System.out.println(urlResponse2);
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest");
            System.out.println(String.format("risultato%s%s", FORWARD, risultato));
            System.out.println(String.format("code%s%s", FORWARD, code));
            System.out.println(String.format("lguserid%s%s", FORWARD, lguserid));
            System.out.println(String.format("lgusername%s%s", FORWARD, lgusername));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest (cookiesSecondary)");
            printCookies(cookiesSecondary);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println(String.format("Url3GET%s%s", FORWARD, urlDomain3GET));
            urlConn3 = this.creaGetConnection(urlDomain3GET);
            uploadCookies(urlConn3, cookiesSecondary);
            urlResponse3 = this.sendRequest(urlConn3);
            elaboraAssertResponse(urlResponse3);
            System.out.println(urlResponse3);
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(42)
    @DisplayName("42 - assertCookiesLoginHamed")
    void assertCookiesLoginHamed() {
        System.out.println("41 - assertCookiesLoginHamed");
        URLConnection urlConn1;
        URLConnection urlConn2;
        URLConnection urlConn3;
        Map<String, String> cookiesPrimary = null;
        Map<String, String> cookiesSecondary = null;
        Map<String, String> cookiesLogin = null;
        String urlDomain1GET = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlDomain2POST = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlDomain3GET = WIKI_QUERY;
        urlDomain3GET += "Abeloya";
        urlDomain3GET += "&assert=user";
        System.out.println(String.format("Url%s%s", FORWARD, urlDomain1GET));
        System.out.println(VUOTA);
        String urlResponse1;
        String urlResponse2;
        String urlResponse3;
        lgname = "hamed";
        lgpassword = "sokoto79";

        try {
            urlConn1 = this.creaGetConnection(urlDomain1GET);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1GET%s%s", FORWARD, urlDomain1GET));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI per la SecondaryRequest (nel POST)");
            System.out.println(String.format("lgname%s%s", FORWARD, lgname));
            System.out.println(String.format("lgpassword%s%s", FORWARD, lgpassword));
            System.out.println("POST" + FORWARD + elaboraPost());
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesPrimary) per la SecondaryRequest");
            printCookies(cookiesPrimary);

            urlConn2 = this.creaGetConnection(urlDomain2POST);
            uploadCookies(urlConn2, cookiesPrimary);
            addPostConnection(urlConn2);
            urlResponse2 = this.sendRequest(urlConn2);
            cookiesSecondary = downlodCookies(urlConn2);
            risultato = elaboraSecondaryResponse(urlResponse2);

            System.out.println(VUOTA);
            System.out.println(String.format("Url2POST%s%s", FORWARD, urlDomain2POST));
            System.out.println(urlResponse2);
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest");
            System.out.println(String.format("risultato%s%s", FORWARD, risultato));
            System.out.println(String.format("code%s%s", FORWARD, code));
            System.out.println(String.format("lguserid%s%s", FORWARD, lguserid));
            System.out.println(String.format("lgusername%s%s", FORWARD, lgusername));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest (cookiesSecondary)");
            printCookies(cookiesSecondary);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesLogin) per la QueryAssert");
            cookiesLogin = cookiesLogin(cookiesSecondary);
            printCookies(cookiesLogin);
            System.out.println(VUOTA);
            System.out.println(String.format("Url3GET%s%s", FORWARD, urlDomain3GET));

            urlConn3 = this.creaGetConnection(urlDomain3GET);
            uploadCookies(urlConn3, cookiesLogin);
            urlResponse3 = this.sendRequest(urlConn3);
            elaboraAssertResponse(urlResponse3);
            System.out.println(urlResponse3);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("REGOLATO BotLogin con le properties ed i cookies");
            botLogin.setQuery(false, lguserid, lgname, TypeUser.user, risultato, cookiesLogin);
            printBotLogin();
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(43)
    @DisplayName("43 - assertCookiesLoginGac")
    void assertCookiesLoginGac() {
        System.out.println("43 - assertCookiesLoginGac");
        URLConnection urlConn1;
        URLConnection urlConn2;
        URLConnection urlConn3;
        Map<String, String> cookiesPrimary = null;
        Map<String, String> cookiesSecondary = null;
        Map<String, String> cookiesLogin = null;
        String urlDomain1GET = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlDomain2POST = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlDomain3GET = WIKI_QUERY;
        urlDomain3GET += "Abeloya";
        urlDomain3GET += "&assert=user";
        System.out.println(String.format("Url%s%s", FORWARD, urlDomain1GET));
        System.out.println(VUOTA);
        String urlResponse1;
        String urlResponse2;
        String urlResponse3;
        lgname = "gac";
        lgpassword = "Sokoto@1979";

        try {
            urlConn1 = this.creaGetConnection(urlDomain1GET);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1GET%s%s", FORWARD, urlDomain1GET));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI per la SecondaryRequest (nel POST)");
            System.out.println(String.format("lgname%s%s", FORWARD, lgname));
            System.out.println(String.format("lgpassword%s%s", FORWARD, lgpassword));
            System.out.println("POST" + FORWARD + elaboraPost());
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesPrimary) per la SecondaryRequest");
            printCookies(cookiesPrimary);

            urlConn2 = this.creaGetConnection(urlDomain2POST);
            uploadCookies(urlConn2, cookiesPrimary);
            addPostConnection(urlConn2);
            urlResponse2 = this.sendRequest(urlConn2);
            cookiesSecondary = downlodCookies(urlConn2);
            risultato = elaboraSecondaryResponse(urlResponse2);

            System.out.println(VUOTA);
            System.out.println(String.format("Url2POST%s%s", FORWARD, urlDomain2POST));
            System.out.println(urlResponse2);
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest");
            System.out.println(String.format("risultato%s%s", FORWARD, risultato));
            System.out.println(String.format("code%s%s", FORWARD, code));
            System.out.println(String.format("lguserid%s%s", FORWARD, lguserid));
            System.out.println(String.format("lgusername%s%s", FORWARD, lgusername));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest (cookiesSecondary)");
            printCookies(cookiesSecondary);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesLogin) per la QueryAssert");
            cookiesLogin = cookiesLogin(cookiesSecondary);
            printCookies(cookiesLogin);
            System.out.println(VUOTA);
            System.out.println(String.format("Url3GET%s%s", FORWARD, urlDomain3GET));

            urlConn3 = this.creaGetConnection(urlDomain3GET);
            uploadCookies(urlConn3, cookiesLogin);
            urlResponse3 = this.sendRequest(urlConn3);
            elaboraAssertResponse(urlResponse3);
            System.out.println(urlResponse3);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("REGOLATO BotLogin con le properties ed i cookies");
            botLogin.setQuery(false, lguserid, lgname, TypeUser.user, risultato, cookiesLogin);
            printBotLogin();
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    @Test
    @Order(44)
    @DisplayName("44 - assertCookiesLoginBot")
    void assertCookiesLoginBot() {
        System.out.println("44 - assertCookiesLoginBot");
        URLConnection urlConn1;
        URLConnection urlConn2;
        URLConnection urlConn3;
        Map<String, String> cookiesPrimary = null;
        Map<String, String> cookiesSecondary = null;
        Map<String, String> cookiesLogin = null;
        String urlDomain1GET = TAG_LOGIN_PRELIMINARY_REQUEST_GET;
        String urlDomain2POST = TAG_LOGIN_SECONDARY_REQUEST_POST;
        String urlDomain3GET = WIKI_QUERY;
        urlDomain3GET += "Abeloya";
        urlDomain3GET += "&assert=bot";
        System.out.println(String.format("Url%s%s", FORWARD, urlDomain1GET));
        System.out.println(VUOTA);
        String urlResponse1;
        String urlResponse2;
        String urlResponse3;
        lgname = "Biobot";
        lgpassword = "lhgfmeb8ckefkniq85qmhul18r689nbq";

        try {
            urlConn1 = this.creaGetConnection(urlDomain1GET);
            urlResponse1 = this.sendRequest(urlConn1);
            cookiesPrimary = downlodCookies(urlConn1);
            elaboraPreliminaryResponse(urlResponse1);

            System.out.println(String.format("Url1GET%s%s", FORWARD, urlDomain1GET));
            System.out.println(urlResponse1);
            System.out.println(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("RICEVUTO da PrimaryRequest (token in ingresso)");
            System.out.println(String.format("logintoken%s%s", FORWARD, logintoken));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da PrimaryRequest (cookiesPrimary)");
            printCookies(cookiesPrimary);
            System.out.println(VUOTA);
            System.out.println("MODIFICATO in elaboraPreliminaryResponse (token in uscita per il POST della SecondaryRequest)");
            System.out.println(String.format("lgtoken%s%s", FORWARD, lgtoken));

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI per la SecondaryRequest (nel POST)");
            System.out.println(String.format("lgname%s%s", FORWARD, lgname));
            System.out.println(String.format("lgpassword%s%s", FORWARD, lgpassword));
            System.out.println("POST" + FORWARD + elaboraPost());
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesPrimary) per la SecondaryRequest");
            printCookies(cookiesPrimary);

            urlConn2 = this.creaGetConnection(urlDomain2POST);
            uploadCookies(urlConn2, cookiesPrimary);
            addPostConnection(urlConn2);
            urlResponse2 = this.sendRequest(urlConn2);
            cookiesSecondary = downlodCookies(urlConn2);
            risultato = elaboraSecondaryResponse(urlResponse2);

            System.out.println(VUOTA);
            System.out.println(String.format("Url2POST%s%s", FORWARD, urlDomain2POST));
            System.out.println(urlResponse2);
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest");
            System.out.println(String.format("risultato%s%s", FORWARD, risultato));
            System.out.println(String.format("code%s%s", FORWARD, code));
            System.out.println(String.format("lguserid%s%s", FORWARD, lguserid));
            System.out.println(String.format("lgusername%s%s", FORWARD, lgusername));
            System.out.println(VUOTA);
            System.out.println("RICEVUTI da SecondaryRequest (cookiesSecondary)");
            printCookies(cookiesSecondary);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("INSERITI (cookiesLogin) per la QueryAssert");
            cookiesLogin = cookiesLogin(cookiesSecondary);
            printCookies(cookiesLogin);
            System.out.println(VUOTA);
            System.out.println(String.format("Url3GET%s%s", FORWARD, urlDomain3GET));

            urlConn3 = this.creaGetConnection(urlDomain3GET);
            uploadCookies(urlConn3, cookiesLogin);
            urlResponse3 = this.sendRequest(urlConn3);
            elaboraAssertResponse(urlResponse3);
            System.out.println(urlResponse3);

            System.out.println(VUOTA);
            System.out.println("******************");
            System.out.println(VUOTA);
            System.out.println("REGOLATO BotLogin con le properties ed i cookies");
            botLogin.setQuery(true, lguserid, lgname, TypeUser.bot, risultato, cookiesLogin);
            printBotLogin();
        } catch (Exception unErrore) {
            System.out.println("ERRORE");
        }
    }


    protected URLConnection creaGetConnection(String urlDomain) throws Exception {
        URLConnection urlConn;

        urlConn = new URL(urlDomain).openConnection();
        urlConn.setDoOutput(true);
        urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");

        return urlConn;
    }


    /**
     * Invia la request (GET oppure POST) <br>
     *
     * @param urlConn connessione con la request
     *
     * @return valore di ritorno della request
     */
    public String sendRequest(URLConnection urlConn) throws Exception {
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        if (urlConn == null) {
            return VUOTA;
        }

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, "UTF8");

        // read the response
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
        }

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        return textBuffer.toString();
    }


    protected LinkedHashMap downlodCookies(URLConnection urlConn) {
        LinkedHashMap cookiesMap = new LinkedHashMap();
        String headerName;
        String cookie;
        String name;
        String value;

        if (urlConn != null) {
            for (int i = 1; (headerName = urlConn.getHeaderFieldKey(i)) != null; i++) {
                if (headerName.equals("set-cookie") || headerName.equals("Set-Cookie")) {
                    cookie = urlConn.getHeaderField(i);
                    cookie = cookie.substring(0, cookie.indexOf(";"));
                    name = cookie.substring(0, cookie.indexOf("="));
                    value = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    cookiesMap.put(name, value);
                }
            }
        }

        return cookiesMap;
    }


    protected void elaboraPreliminaryResponse(final String rispostaDellaQuery) {
        JSONObject jsonAll;
        JSONObject jsonQuery = null;
        JSONObject jsonTokens = null;

        jsonAll = (JSONObject) JSONValue.parse(rispostaDellaQuery);

        if (jsonAll != null && jsonAll.get(QUERY) != null) {
            jsonQuery = (JSONObject) jsonAll.get(QUERY);
        }

        if (jsonQuery != null && jsonQuery.get(TOKENS) != null) {
            jsonTokens = (JSONObject) jsonQuery.get(TOKENS);
        }

        if (jsonTokens != null && jsonTokens.get(LOGIN_TOKEN) != null) {
            logintoken = (String) jsonTokens.get(LOGIN_TOKEN);
        }

        try {
            lgtoken = URLEncoder.encode(logintoken, ENCODE);
        } catch (Exception unErrore) {
        }
    }

    protected void uploadCookies(final URLConnection urlConn, final Map<String, String> cookies) {
        String cookiesText;

        if (cookies != null) {
            cookiesText = this.creaCookiesText(cookies);
            urlConn.setRequestProperty("Cookie", cookiesText);
        }
    }


    protected String creaCookiesText(Map<String, String> cookies) {
        String cookiesTxt = VUOTA;
        String sep = UGUALE_SEMPLICE;
        Object valObj;
        String key;

        if (cookies != null && cookies.size() > 0) {
            for (Object obj : cookies.keySet()) {
                if (obj instanceof String) {
                    key = (String) obj;
                    valObj = cookies.get(key);
                    cookiesTxt += key;
                    cookiesTxt += sep;
                    cookiesTxt += valObj;
                    cookiesTxt += PUNTO_VIRGOLA;
                }
            }
        }

        return cookiesTxt;
    }

    protected void addPostConnection(URLConnection urlConn) throws Exception {
        if (urlConn != null) {
            PrintWriter out = new PrintWriter(urlConn.getOutputStream());
            out.print(elaboraPost());
            out.close();
        }
    }


    /**
     * Crea il testo del POST della request <br>
     */
    protected String elaboraPost() {
        String testoPost = VUOTA;

        testoPost += TAG_NAME;
        testoPost += lgname;
        testoPost += TAG_PASSWORD;
        testoPost += lgpassword;
        testoPost += TAG_TOKEN;
        testoPost += lgtoken;

        return testoPost;
    }


    protected String elaboraSecondaryResponse(final String rispostaDellaQuery) {
        JSONObject jsonAll = (JSONObject) JSONValue.parse(rispostaDellaQuery);
        JSONObject jsonLogin = null;

        //--controllo del batchcomplete
        if (jsonAll != null && jsonAll.get(KEY_JSON_BATCH) != null) {
            if (!(boolean) jsonAll.get(KEY_JSON_BATCH)) {
                return VUOTA;
            }
        }

        //--regola il login
        if (jsonAll != null && jsonAll.get(LOGIN) != null) {
            jsonLogin = (JSONObject) jsonAll.get(LOGIN);
        }

        if (jsonLogin != null && jsonLogin.get(RESULT) != null) {
            if (jsonLogin.get(RESULT) != null) {
                code = (String) jsonLogin.get(RESULT);
            }

            if ((Long) jsonLogin.get(LOGIN_USER_ID) > 0) {
                lguserid = (Long) jsonLogin.get(LOGIN_USER_ID);
            }
            if (jsonLogin.get(LOGIN_USER_NAME) != null) {
                lgusername = (String) jsonLogin.get(LOGIN_USER_NAME);
            }
        }
        return jsonLogin != null ? jsonLogin.toString() : VUOTA;
    }

    protected void elaboraAssertResponse(final String rispostaDellaQuery) {
        JSONObject jsonAll = (JSONObject) JSONValue.parse(rispostaDellaQuery);
        JSONObject jsonError = null;

        //--controllo del batchcomplete
        if (jsonAll != null && jsonAll.get(KEY_JSON_BATCH) != null) {
            if (!(boolean) jsonAll.get(KEY_JSON_BATCH)) {
                return;
            }
        }

        //--regola l'errore
        if (jsonAll != null && jsonAll.get(JSON_ERROR) != null) {
            jsonError = (JSONObject) jsonAll.get(JSON_ERROR);
        }

        if (jsonError != null) {
            risultato = JSON_ERROR + DUE_PUNTI_SPAZIO + jsonError.get(JSON_CODE);
        }
    }


    protected Map<String, String> cookiesLogin(Map<String, String> cookies) {
        Map<String, String> cookiesLogin = new HashMap<>();

        cookiesLogin.put("ss0-itwikiSession", cookies.get("ss0-itwikiSession"));
        cookiesLogin.put("itwikiss0-UserID", cookies.get("itwikiss0-UserID"));
        cookiesLogin.put("centralauth_ss0-User", cookies.get("centralauth_ss0-User"));
        cookiesLogin.put("centralauth_ss0-Token", cookies.get("centralauth_ss0-Token"));

        return cookiesLogin;
    }

    protected void printCookies(Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                System.out.print(key);
                System.out.print(UGUALE);
                System.out.println(cookies.get(key));
            }
        }
    }

}