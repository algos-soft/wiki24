package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sat, 31-Dec-2022
 * Time: 19:57
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskNomi extends VaadTask {


    public TaskNomi() {
        super.descrizioneTask = WPref.usaTaskNomi.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getNomi();
        super.flagAttivazione = WPref.usaTaskNomi;
        super.flagPrevisione = WPref.uploadNomiPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {

        if (flagAttivazione.is()) {
            super.fixNext();

            //--Statistiche

            //--Upload
            appContext.getBean(UploadNomi.class).uploadAll();
            super.loggerTask();
        }
        else {
            super.loggerNoTask();
        }

    }

    public String infoFlag() {
        return super.infoFlag() + WPref.sogliaWikiNomi.getInt();
    }

}

