package it.algos.wiki24.basetest;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 15-Dec-2023
 * Time: 21:04
 */
public abstract class QueryTest extends WikiTest {

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
        this.previstoRisultato = null;
        this.ottenutoRisultato = null;
    }


//    @Test
//    @Order(0)
//    @DisplayName("0")
//    void partenza() {
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = appContext.getBean(clazz);
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base (standard di ogni query) senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
        printIstanza(istanza);
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
