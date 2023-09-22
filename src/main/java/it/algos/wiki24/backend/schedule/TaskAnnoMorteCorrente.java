package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 20-Sep-2023
 * Time: 07:58
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskAnnoMorteCorrente extends VaadTask {


    public TaskAnnoMorteCorrente() {
        super.descrizioneTask = "Upload di tutte le liste di morti per il solo anno corrente";
        super.typeSchedule = AESchedule.alba;
        super.flagAttivazione = WPref.usaTaskAnni;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            //--L'upload comprende anche le info per la view
            inizio = System.currentTimeMillis();
            appContext.getBean(UploadAnni.class, String.valueOf(LocalDate.now().getYear())).morte().upload();
            super.loggerTask();
        }
    }

}
