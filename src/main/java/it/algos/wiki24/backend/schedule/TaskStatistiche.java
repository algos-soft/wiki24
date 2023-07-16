package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.wrapper.*;
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

    public static String INFO = Wiki24Var.typeSchedule.getStatistiche().getNota();

    public TaskStatistiche() {
        super.descrizioneTask = WPref.usaTaskStatistiche.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getStatistiche();
        super.flagAttivazione = WPref.usaTaskStatistiche;
        super.flagPrevisione = WPref.statistichePrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            //--Statistiche generali delle biografie
            //            appContext.getBean(StatisticheBio.class).upload();

            //--La statistica dei giorni comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheGiorni.class).esegue();

            //--La statistica degli anni comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheAnni.class).esegue();

            //--La statistica delle attività comprende anche una preliminare elaborazione
            //            appContext.getBean(StatisticheAttivita.class).upload();

            //--La statistica delle nazionalità comprende anche una preliminare elaborazione
            //            appContext.getBean(StatisticheNazionalita.class).upload();

            //--La statistica delle liste di nomi comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheListeNomi.class).esegue();

            //--La statistica dei nomi comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheNomi.class).esegue();

            //--La statistica delle liste di cognomi comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheListeCognomi.class).esegue();

            //--La statistica dei cognomi comprende anche una preliminare elaborazione
            //--La statistica comprende anche il messaggio di log sul db
            appContext.getBean(StatisticheCognomi.class).esegue();

            super.loggerTask();
        }
    }

}
