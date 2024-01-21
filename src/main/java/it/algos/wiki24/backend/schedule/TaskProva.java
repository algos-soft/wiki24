package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.schedule.*;
import it.algos.wiki24.backend.enumeration.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.time.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Aug-2022
 * Time: 14:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskProva extends BaseTask {


    public TaskProva() {
        super.descrizioneTask = "Task di prova";
        super.typeSchedule = TypeSchedule.minuto;

        super.flagAttivazione = WPref.usaDownloadBioServer;
        //        super.flagPrevisione = WPref.uploadGiorniPrevisto;
    }


    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            //--L'upload comprende anche le info per la view
            System.out.println(String.format("Ogni minuto -> ", LocalTime.now()));
            super.loggerTask();
        }
    }

}

