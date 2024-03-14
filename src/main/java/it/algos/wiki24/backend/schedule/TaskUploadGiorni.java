package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.schedule.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 18:19
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskUploadGiorni extends BaseTask {

    public static TypeSchedule TYPE_SCHEDULE = TypeSchedule.lunediGiovediSei;

    @Inject
    private GiornoWikiModulo giornoWikiModulo;

    public TaskUploadGiorni() {
        super.flagAttivazione = WPref.usaUploadGiorni;
        super.descrizioneTask = WPref.usaUploadGiorni.getDescrizione();
        super.typeSchedule = TYPE_SCHEDULE;
        //        super.flagPrevisione = WPref.downloadBioPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato;

        if (super.execute()) {
            risultato = giornoWikiModulo.uploadAll();
            super.logTaskEseguito(risultato);
        }
    }

}
