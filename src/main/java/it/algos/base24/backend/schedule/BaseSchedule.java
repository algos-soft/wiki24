package it.algos.base24.backend.schedule;


import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.boot.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 28-Dec-2022
 * Time: 19:07
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public  class BaseSchedule extends Scheduler {


    /**
     * Aggiunge i task alla superclasse per l'esecuzione <br>
     * Deve essere sovrascritto, invocando il metodo della superclasse DOPO <br>
     */
    public void start() throws IllegalStateException {
        if (!isStarted()) {
            super.start();
        }

        if (BaseVar.taskList != null && BaseVar.taskList.size() > 0) {
            for (BaseTask task : BaseVar.taskList) {
                schedule(task.getPatternSimple(), task);
            }
        }
    }

}