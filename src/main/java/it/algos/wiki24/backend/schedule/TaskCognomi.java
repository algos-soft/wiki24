package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.upload.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 20-Oct-2022
 * Time: 06:59
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskCognomi extends VaadTask {


    public TaskCognomi() {
        super.descrizioneTask = WPref.uploadCognomi.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getCognomi();
        super.flagAttivazione = WPref.usaTaskCognomi;
        super.flagPrevisione = WPref.uploadCognomiPrevisto;
    }


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public CognomeBackend cognomeBackend;

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        super.execute(taskExecutionContext);

        if (flagAttivazione.is()) {
            super.fixNext();

            //--Upload
            inizio = System.currentTimeMillis();
            appContext.getBean(UploadCognomi.class).uploadAll();
            loggerUpload(inizio);
            super.loggerTask();
        }
        else {
            super.loggerNoTask();
        }
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

