package it.algos.wiki24.backend.schedule;

import it.algos.base24.backend.schedule.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 17:18
 */
@Service
public class Wiki24Schedule extends ScheduleTaskUsingCronExpression {

    @Inject
    public AttSingolareModulo nazSingolareModulo;

    protected void ogniMinuto() {
//        nazSingolareModulo.resetDelete();
    }

}
