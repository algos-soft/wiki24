package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.schedule.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.tabelle.anni.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 21:06
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskUploadMortiAnnoCorrente extends BaseTask {
    public static TypeSchedule TYPE_SCHEDULE = TypeSchedule.everyCinqueTrenta;

    @Inject
    private AnnoWikiModulo annoWikiModulo;

    public TaskUploadMortiAnnoCorrente() {
        super.flagAttivazione = WPref.usaUploadMortiAnnoCorrente;
        super.descrizioneTask = WPref.usaUploadMortiAnnoCorrente.getDescrizione();
        super.typeSchedule = TYPE_SCHEDULE;
        //        super.flagPrevisione = WPref.downloadBioPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato;

        if (super.execute()) {
            risultato = annoWikiModulo.uploadMortiAnnoCorrente();
            super.logTaskEseguito(risultato);
        }
    }

}
