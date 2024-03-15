package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 22:27
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Log Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogServiceTest extends ServiceTest {


    private static final String DIRECTORY = SEP + "Questo messaggio lo trovi anche nella directory di log in [base2023-admin.log]";

    private static final String NO_DIRECTORY = SEP + "Questo messaggio NON lo trovi nella directory di log";

    @Autowired
    private LogService service;

    private WrapLog wrapLog;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = LogService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(10)
    @DisplayName("10 - info")
    void info() {
        System.out.println(("10 - info"));
        System.out.println(VUOTA);

        message = "Info base" + DIRECTORY;
        service.info(message);
    }

    @Test
    @Order(20)
    @DisplayName("20 - warn")
    void warn() {
        System.out.println(("20 - warn"));
        System.out.println(VUOTA);

        message = "Warn base" + DIRECTORY;
        service.warn(message);
    }

    @Test
    @Order(30)
    @DisplayName("30 - error")
    void error() {
        System.out.println(("30 - error"));
        System.out.println(VUOTA);

        message = "Error base" + DIRECTORY;
        service.error(message);
    }


    @Test
    @Order(50)
    @DisplayName("50 - wrapLogCrea")
    void wrapLogCrea() {
        System.out.println(("50 - wrapLogCrea"));
        System.out.println(VUOTA);

        message = "wrapLog base" + NO_DIRECTORY;
        wrapLog = WrapLog.crea().message(message);
        System.out.println("toString" + FORWARD + wrapLog);
        System.out.println("get" + FORWARD + wrapLog.get());
        System.out.println("getMessage" + FORWARD + wrapLog.getMessage());
    }


    @Test
    @Order(51)
    @DisplayName("51 - wrapLogNew")
    void wrapLogNew() {
        System.out.println(("51 - wrapLogNew"));
        System.out.println(VUOTA);

        message = "wrapLog base" + NO_DIRECTORY;
        wrapLog = new WrapLog().message(message);
        System.out.println("toString" + FORWARD + wrapLog);
        System.out.println("get" + FORWARD + wrapLog.get());
        System.out.println("getMessage" + FORWARD + wrapLog.getMessage());
    }

    @Test
    @Order(52)
    @DisplayName("52 - wrapLogType")
    void wrapLogType() {
        System.out.println(("52 - wrapLogType"));
        System.out.println(VUOTA);

        message = "wrapLog type test";
        wrapLog = WrapLog.crea().message(message).type(TypeLog.test);
        System.out.println(wrapLog.get());

        message = "wrapLog type debug";
        wrapLog = WrapLog.crea().message(message).type(TypeLog.debug);
        System.out.println(wrapLog.get());
    }

    @Test
    @Order(53)
    @DisplayName("53 - wrapLogLevel")
    void wrapLogLevel() {
        System.out.println(("53 - wrapLogLevel"));
        System.out.println(VUOTA);

        message = "wrapLog level info";
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.info);
        System.out.println(wrapLog.get());

        message = "wrapLog level debug";
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.debug);
        System.out.println(wrapLog.get());

        message = "wrapLog level warn";
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.warn);
        System.out.println(wrapLog.get());
    }

    @Test
    @Order(60)
    @DisplayName("60 - wrap")
    void wrap() {
        System.out.println(("60 - wrap"));
        System.out.println(VUOTA);

        message = "wrapLog level info" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.info);
        service.wrap(wrapLog);

        message = "wrapLog level debug" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.debug);
        service.wrap(wrapLog);

        message = "wrapLog level warn" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).livello(LogLevel.warn);
        service.wrap(wrapLog);
    }


    @Test
    @Order(61)
    @DisplayName("61 - wrap2")
    void wrap2() {
        System.out.println(("61 - wrap2"));
        System.out.println(VUOTA);

        message = "wrapLog level info" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).info();
        service.wrap(wrapLog);

        message = "wrapLog level debug" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).debug();
        service.wrap(wrapLog);

        message = "wrapLog level warn" + DIRECTORY;
        wrapLog = WrapLog.crea().message(message).warn();
        service.wrap(wrapLog);
    }

    @Test
    @Order(70)
    @DisplayName("70 - wrap3")
    void wrap3() {
        System.out.println(("70 - wrap3"));
        System.out.println(VUOTA);

        message = "logService wrapLog level info" + DIRECTORY;
        service.info(WrapLog.crea(message));

        message = "logService wrapLog level debug" + DIRECTORY;
        service.debug(WrapLog.crea(message));

        message = "logService wrapLog level warn" + DIRECTORY;
        service.warn(WrapLog.crea(message));

        message = "logService wrapLog level error" + DIRECTORY;
        service.error(WrapLog.crea(message));
    }

}

