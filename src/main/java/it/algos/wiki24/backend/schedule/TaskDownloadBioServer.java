package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.schedule.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 21-Jan-2024
 * Time: 07:53
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskDownloadBioServer extends BaseTask {


    @Inject
    private DownloadService downloadService;


    public TaskDownloadBioServer() {
        super.descrizioneTask = WPref.usaDownloadBioServer.getDescrizione();
//        super.typeSchedule = TypeSchedule.zeroCinqueNoLunedi;
        super.typeSchedule =  TypeSchedule.pomeriggio;


        super.flagAttivazione = WPref.usaDownloadBioServer;
        //        super.flagPrevisione = WPref.downloadBioPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato;

        if (super.execute()) {
            risultato = downloadService.cicloCorrente();
            super.logTaskEseguito(risultato);
        }
    }

}
