package it.algos.base24.backend.schedule;

import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 16:09
 */
@Service
public class ScheduleTaskUsingCronExpression {

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleOgniMinuto() {
        ogniMinuto();
    }

    protected void ogniMinuto() {
    }


    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleOgniOra() {
        ogniOra();
    }

    protected void ogniOra() {
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleOgniGiorno() {
        ogniGiorno();
    }

    protected void ogniGiorno() {
    }

}
