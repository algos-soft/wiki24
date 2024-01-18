package it.algos.base24.backend.schedule;

import it.algos.base24.backend.enumeration.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 16:09
 */
@Service
public class ScheduleTaskUsingCronExpression {
    private static final String ALFA="0/10 * * * * ?";
    private static final String BETA= TypeSchedule.minuto.getPattern();

    //    @Scheduled(cron = TypeSchedule.minuto.getPattern())
    public void scheduleOgniMinuto() {
        System.out.println(LocalTime.now());
    }



    //    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleOgniOra() {
    }


    //    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleOgniGiorno() {
    }


}
