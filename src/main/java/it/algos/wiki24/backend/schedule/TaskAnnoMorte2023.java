package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.schedule.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 20-Sep-2023
 * Time: 07:58
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskAnnoMorte2023 extends VaadTask {

    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    public TaskAnnoMorte2023() {
        super.descrizioneTask = WPref.uploadAnni.getDescrizione();
        super.typeSchedule = Wiki24Var.typeSchedule.getAnni();
        super.flagAttivazione = WPref.usaTaskAnni;
    }

}
