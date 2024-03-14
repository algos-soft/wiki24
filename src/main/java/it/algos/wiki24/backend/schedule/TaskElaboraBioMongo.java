package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.schedule.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 21-Jan-2024
 * Time: 10:53
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskElaboraBioMongo extends BaseTask {

    public static TypeSchedule TYPE_SCHEDULE = TypeSchedule.dieciGiovedi;

    @Inject
    private BioMongoModulo bioMongoModulo;


    public TaskElaboraBioMongo() {
        super.flagAttivazione = WPref.usaElaboraBioMongo;
        super.descrizioneTask = WPref.usaElaboraBioMongo.getDescrizione();
        super.typeSchedule = TYPE_SCHEDULE;
    }

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        String risultato = VUOTA;

        if (super.execute()) {
            risultato = bioMongoModulo.elabora();
            super.logTaskEseguito();
        }

        super.logTaskEseguito(risultato);
    }

}

