package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.statistiche.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sat, 31-Dec-2022
 * Time: 19:06
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskStatistiche extends VaadTask {

    public TaskStatistiche() {
        super.descrizioneTask = WPref.usaTaskStatistiche.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getStatistiche();
        super.flagAttivazione = WPref.usaTaskStatistiche;
        super.flagPrevisione = WPref.statistichePrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        super.execute(taskExecutionContext);

        if (flagAttivazione.is()) {
            super.fixNext();

            //--Le statistiche dei giorni comprendono anche l'elaborazione
            appContext.getBean(StatisticheGiorni.class).upload();

            //--Le statistiche degli anni comprendono anche l'elaborazione
            appContext.getBean(StatisticheAnni.class).upload();

            super.loggerTask();
        }
        else {
            super.loggerNoTask();
        }
    }

}
