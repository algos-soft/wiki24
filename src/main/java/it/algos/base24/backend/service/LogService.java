package it.algos.base24.backend.service;

import com.vaadin.flow.component.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.utility.logs.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.view.*;
import jakarta.annotation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 17-Aug-2023
 * Time: 21:15
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public LogService logger; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class LogService {

    @Inject
    public Environment environment;

    @Inject
    TextService textService;

    @Inject
    UtilityService utilityService;


    // Riferimento al logger usato <br>
    public Logger slf4jLogger;

    public MongoOperations mongoOp;

    private String dataBaseName;

    @Autowired
    public LogService(MongoTemplate mongoOp, @Value("${spring.data.mongodb.database}") String dataBaseName) {
        this.mongoOp = mongoOp;
        this.dataBaseName = dataBaseName;
    }

    /**
     * Riferimento al logger usato <br>
     * È nella directory 'config', il file 'logback-spring.xml' <br>
     * Deve essere creato subito dalla factory class LoggerFactory <br>
     * Va selezionato un appender da usare e che sia presente nel file di configurazione <br>
     */
    @PostConstruct
    private void postConstruct() {
        String fileAppender;
        String property = "logging.algos.admin";

        try {
            fileAppender = Objects.requireNonNull(environment.getProperty(property));
            slf4jLogger = LoggerFactory.getLogger(fileAppender);
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            System.out.println(message);
        }
    }

    public void debug(final String message) {
        //        if (Pref.debug.is()) { @todo ATTENTION QUI
        //            slf4jLogger.info(message);
        //        }
    }

    public void info(final String message) {
        slf4jLogger.info(message);
    }

    public void warn(final String message) {
        slf4jLogger.warn(message);
    }

    public void error(final String message) {
        slf4jLogger.error(message);
    }

    public void info(final WrapLog wrap) {
        wrap(wrap.livello(LogLevel.info));
    }

    public void warn(final WrapLog wrap) {
        wrap(wrap.livello(LogLevel.warn));
    }

    public void error(final WrapLog wrap) {
        wrap(wrap.livello(LogLevel.error));
    }

    public void debug(final WrapLog wrap) {
        wrap(wrap.livello(LogLevel.debug));
    }

    public void mail(final WrapLog wrap) {
        wrap(wrap.livello(LogLevel.mail));
    }

    public void wrap(final WrapLog wrap) {
        LogLevel livello = wrap.getLivello();
        String message = VUOTA;
        TypeLog type = wrap.getType();
        TypeNotifica notifica = wrap.getNotifica();
        String typeTag = textService.fixSizeQuadre(wrap.getTag(), PAD_TYPE);

        //--1) Inserimento fisso iniziale del type merceologico - se manca di default usa 'system'
        message = typeTag;

        //--2) Messaggio fisso della descrizione
        message += textService.isValid(wrap.getMessage()) ? SEP + wrap.getMessage() : VUOTA;

        //--3) Inserimento opzionale dei dati (company, user, IP) se multiCompany
        if (wrap.isMultiCompany()) {
            //            message += SPAZIO + utilityService.getCompanyPack(wrap);
        }

        //--4) Inserimento opzionale dello stackTrace dell'errore (o della partenza del log)
        //--Obbligatorio per AETypeLog.error, facoltativo per gli altri type
        if (type == TypeLog.error || wrap.isUsaStackTrace()) {
            message += SPAZIO + utilityService.getStackTrace(wrap.getException()) + SPAZIO;
            message = DUE_PUNTI_SPAZIO + message;
            //--Se forzo con WrapLog().message() un messaggio diverso da quello generato dall'errore, li mostra entrambi
            //--Quello forzato in testa e quello generato dall'errore in coda
            if (wrap.getMessage() != null && !wrap.getMessage().equals(wrap.getException().getMessage())) {
                message += SPAZIO + wrap.getException().getMessage();
            }
        }
        else {
            message = String.format("%s%s", DUE_PUNTI_SPAZIO, message);
        }

        //--5) Inserimento opzionale nella collection di mongoDB
        if (wrap.isUsaDB()) {
            this.crea(wrap);
        }

        //--6) Invio opzionale di una mail
        //        if (flagUsaMail) {
        //        }

        // logback-spring.xml
        switch (livello) {
            case info -> slf4jLogger.info(message);
            case warn -> slf4jLogger.warn(message);
            case error -> slf4jLogger.error(message);
            case debug -> {
                slf4jLogger.debug(message);
                if (Pref.debug.is()) {
                    slf4jLogger.info(message);
                }
            }
            default -> slf4jLogger.info(message);
        }

        if (notifica != null && UI.getCurrent() != null) {
            switch (notifica) {
                case success -> Notifica.message(String.format(message)).success().open();
                case primary -> Notifica.message(String.format(message)).primary().open();
                case contrast -> Notifica.message(String.format(message)).contrast().open();
                case error -> Notifica.message(String.format(message)).error().open();
                default -> {}
            } ;
        }

    }

    public void crea(final WrapLog wrap) {
        LogEntity newEntityBean;
        TypeLog typeLog = wrap.getType();
        LogLevel typeLevel = wrap.getLivello();
        String descrizione = wrap.getMessage();

        newEntityBean = newEntity(typeLog, typeLevel, descrizione);
        if (newEntityBean != null) {
            mongoOp.insert(newEntityBean, "logger");
            afterInsert();
        }

    }

    public LogEntity newEntity(TypeLog typeLog, LogLevel typeLevel, String descrizione) {
        return LogEntity.builder()
                .typeLog(typeLog == null ? TypeLog.system : typeLog)
                .typeLevel(typeLevel == null ? LogLevel.info : typeLevel)
                .evento(LocalDateTime.now())
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();
    }

    public void afterInsert() {
        int appenderMax = APPENDER_MAX;
        int appenderOffset = APPENDER_OFFSET;
        int numEntities = ((Long) this.mongoOp.count(new Query(), LogEntity.class, "logger")).intValue();
        List<LogEntity> listOrdinata = this.mongoOp.findAll(LogEntity.class,"logger");
        appenderMax = 70;
        appenderOffset = 5;

        if (numEntities > appenderMax) {
            for (LogEntity bean : listOrdinata.subList(0, appenderOffset)) {
                this.mongoOp.remove(bean,"logger");
            }
        }
    }

}// end of Service class