package it.algos.wiki24.basetest;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 15-Dec-2023
 * Time: 21:04
 */
public abstract class QueryTest extends WikiStreamTest {

    @Inject
    public ApplicationContext appContext;

    @Inject
    public BotLogin botLogin;

    @Inject
    public TextService textService;

    @Inject
    public LogService logger;

    @Inject
    public QueryService queryService;

    protected Object istanza;

    protected WResult previstoRisultato;

    protected WResult ottenutoRisultato;

    protected String clazzName;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    protected void setUpAll() {
        super.setUpAll();
        assertNotNull(clazz);
        clazzName = clazz.getSimpleName();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        super.setUpEach();
        this.previstoRisultato = null;
        this.ottenutoRisultato = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = appContext.getBean(clazz);
        System.out.println(("1 - Costruttore base (standard di ogni query) senza parametri"));
        System.out.println(VUOTA);
        if (ammessoCostruttoreVuoto) {
            message = String.format("La classe [%s] prevede il costruttore vuoto", clazzName);
            assertNotNull(istanza);
        }
        else {
            message = String.format("La classe [%s] NON ammette il costruttore vuoto", clazzName);
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Request (prevista) errata. Manca il wikiTitle")
    void getBean() {
        if (ammessoCostruttoreVuoto) {
            System.out.println(("2 - Request (prevista) errata."));
            message = String.format("La classe [%s] prevede il costruttore vuoto", clazzName);
            System.out.println(message);
            message = String.format("Non è previsto il metodo %s.%s(...) con un solo parametro", clazzName, "urlRequest");
            System.out.println(message);
        }
        else {
            System.out.println(("2 - Request (prevista) errata."));

            ottenutoRisultato = ((AQuery) appContext.getBean(clazz)).urlRequest(sorgente);
            assertNotNull(ottenutoRisultato);
            assertFalse(ottenutoRisultato.isValido());
            printRisultato(ottenutoRisultato);
        }
    }


    @Test
    @Order(3)
    @DisplayName("3 - Request (prevista) errata. Non esiste la pagina")
    void paginaMancante() {
        if (ammessoCostruttoreVuoto) {
            System.out.println(("2 - Request (prevista) errata."));
            message = String.format("La classe [%s] prevede il costruttore vuoto", clazzName);
            System.out.println(message);
            message = String.format("Non è previsto il metodo %s.%s(...) con un solo parametro", clazzName, "urlRequest");
            System.out.println(message);
        }
        else {
            sorgente = "Pippoz Belloz";
            message = String.format("3 - Request (prevista) errata. Non esiste la pagina [%s]", sorgente);
            System.out.println(message);

            ottenutoRisultato = ((AQuery) appContext.getBean(clazz)).urlRequest(sorgente);
            assertNotNull(ottenutoRisultato);
            assertFalse(ottenutoRisultato.isValido());
            printRisultato(ottenutoRisultato);
        }
    }

    protected void printIstanza(Object istanza) {
    }

    //    protected void printBotLogin() {
    //        System.out.println(VUOTA);
    //        if (botLogin != null) {
    //            System.out.println("Valori attuali del singleton BotLogin");
    //            System.out.println(String.format("Valido: %s", botLogin.isValido() ? "true" : "false"));
    //            System.out.println(String.format("Bot: %s", botLogin.isBot() ? "true" : "false"));
    //            System.out.println(String.format("Username: %s", botLogin.getUsername()));
    //            System.out.println(String.format("Userid: %d", botLogin.getUserid()));
    //            System.out.println(String.format("UserType: %s", botLogin.getUserType()));
    //        }
    //        else {
    //            logger.warn(new WrapLog().message("Manca il BotLogin"));
    //        }
    //        System.out.println(VUOTA);
    //    }


}
