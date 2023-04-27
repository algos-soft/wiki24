package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.upload.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 05-Jul-2022
 * Time: 13:58
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskAttivita extends VaadTask {

    public TaskAttivita() {
        super.descrizioneTask = WPref.uploadAttPlurale.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getAttivita();
        super.flagAttivazione = WPref.usaTaskAttivita;
        super.flagPrevisione = WPref.uploadAttPluralePrevisto;
    }


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        super.execute(taskExecutionContext);

        if (flagAttivazione.is()) {
            super.fixNext();

            //--Statistiche
//            inizio = System.currentTimeMillis();
//            appContext.getBean(StatisticheAttivita.class).upload();
//            loggerElabora(inizio);

            //--Upload
            inizio = System.currentTimeMillis();
            appContext.getBean(UploadAttivita.class).uploadAll();
            loggerUpload(inizio);
            super.loggerTask();
        }
        else {
            super.loggerNoTask();
        }
    }


    public void loggerElabora(long inizio) {
        long fine = System.currentTimeMillis();
        String message;
        long delta = fine - inizio;
        delta = delta / 1000 / 60;

        message = String.format("Task per elaborare le attività in %s minuti", delta);
        logger.info(new WrapLog().type(AETypeLog.elabora).message(message).usaDb());
    }


    public void loggerUpload(long inizio) {
        long fine = System.currentTimeMillis();
        String message;
        long delta = fine - inizio;
        delta = delta / 1000 / 60;

        message = String.format("Task per l'upload delle liste di attività in %s minuti", delta);
        logger.info(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
    }

}
