package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.statistiche.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 05-Jul-2022
 * Time: 19:03
 * Il ciclo normale di download (questo task) viene effettuata tutti i giorni ESCLUSO il lunedì
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskBio extends VaadTask {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private DownloadService downloadService;


    public TaskBio() {
        super.descrizioneTask = WPref.downloadBio.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getUpdateBio();
        super.flagAttivazione = WPref.usaTaskBio;
        super.flagPrevisione = WPref.downloadBioPrevisto;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        if (super.execute()) {

            downloadService.cicloCorrente();

//            appContext.getBean(StatisticheBio.class).upload();

            super.loggerNoTask();
        }
    }

}
