package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
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

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    public TaskGiorni() {
        super.descrizioneTask = WPref.usaTaskGiorni.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getGiorni();
        super.typeSchedule = Wiki24Var.typeSchedule.getGiorni();


        super.flagAttivazione = WPref.usaTaskGiorni;
        super.flagPrevisione = WPref.uploadGiorniPrevisto;
    }


    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            //--L'upload comprende anche le info per la view
            giornoWikiBackend.uploadAll();
            super.loggerTask();
        }
    }

}

