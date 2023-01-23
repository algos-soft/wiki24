package it.algos.wiki23.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki23.backend.boot.*;
import it.algos.wiki23.backend.enumeration.*;
import it.algos.wiki23.backend.statistiche.*;
import it.algos.wiki23.backend.upload.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Aug-2022
 * Time: 14:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskGiorni extends VaadTask {


    public TaskGiorni() {
        super.descrizioneTask = WPref.uploadGiorni.getDescrizione();
        super.typeSchedule = Wiki23Var.typeSchedule.getGiorni();
        super.flagAttivazione = WPref.usaTaskGiorni;
        super.flagPrevisione = WPref.uploadGiorniPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        super.execute(taskExecutionContext);

        if (flagAttivazione.is()) {
            super.fixNext();

            //--L'upload comprende anche le info per la view
            inizio = System.currentTimeMillis();
            appContext.getBean(UploadGiorni.class).uploadAll();
            super.loggerTask();
        }
        else {
            super.loggerNoTask();
        }
    }

}

