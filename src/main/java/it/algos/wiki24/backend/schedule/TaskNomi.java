package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
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
        super.descrizioneTask = WPref.uploadNomi.getDescrizione();
        super.typeSchedule = Wiki23Var.typeSchedule.getNomi();
        super.flagAttivazione = WPref.usaTaskNomi;
        super.flagPrevisione = WPref.uploadNomiPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (execute()) {

            super.loggerTask();
        }
    }

}

