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
 * Date: Mon, 08-Aug-2022
 * Time: 14:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskGiorni extends VaadTask {


    public TaskGiorni() {
        super.descrizioneTask = WPref.uploadGiorni.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getGiorni();
        super.flagAttivazione = WPref.usaTaskGiorni;
        super.flagPrevisione = WPref.uploadGiorniPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            //--L'upload comprende anche le info per la view
            appContext.getBean(UploadGiorni.class).uploadAll();

            super.loggerTask();
        }
    }

}

