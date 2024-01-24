package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.schedule.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
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

    public static TypeSchedule TYPE_SCHEDULE = TypeSchedule.everyZeroTrenta;

    @Inject
    private BioServerModulo bioServerModulo;

    @Inject
    private DownloadService downloadService;


    public TaskDownloadBioServer() {
        super.flagAttivazione = WPref.usaDownloadBioServer;
        super.descrizioneTask = WPref.usaDownloadBioServer.getDescrizione();
        super.typeSchedule = TYPE_SCHEDULE;
//        super.typeSchedule = TypeSchedule.pomeriggio;

        //        super.flagPrevisione = WPref.downloadBioPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato;

        if (super.execute()) {
            if (bioServerModulo.count() == 0) {
                risultato = downloadService.cicloIniziale();
            }
            else {
                risultato = downloadService.cicloCorrente();
            }
            super.logTaskEseguito(risultato);
        }
    }

}
