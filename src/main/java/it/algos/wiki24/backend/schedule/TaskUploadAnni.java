package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.schedule.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anni.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 21:02
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskUploadAnni extends BaseTask  {
    public static TypeSchedule TYPE_SCHEDULE = TypeSchedule.martediVenerdiSei;

    @Inject
    private AnnoWikiModulo annoWikiModulo;

    public TaskUploadAnni() {
        super.flagAttivazione = WPref.usaUploadAnni;
        super.descrizioneTask = WPref.usaUploadAnni.getDescrizione();
        super.typeSchedule = TYPE_SCHEDULE;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato;

        if (super.execute()) {
            risultato = annoWikiModulo.uploadAll();
            super.logTaskEseguito(risultato);
        }
    }

}
