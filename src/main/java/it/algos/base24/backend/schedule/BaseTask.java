package it.algos.base24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import javax.inject.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 28-Dec-2022
 * Time: 20:34
 */
@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class BaseTask extends Task {

    protected long inizio;

    protected TypeSchedule typeSchedule;

    protected String descrizioneTask;

    protected IPref flagAttivazione;

    protected IPref flagPrevisione;

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;

    @Inject
    MailService mailService;

    /**
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
    }


    public boolean execute() {
        inizio = System.currentTimeMillis();

        if (flagAttivazione == null || flagAttivazione.is()) {
            this.fixNext();
            return true;
        }
        else {
            this.logTaskNonEseguito();
            return false;
        }
    }


    protected void fixNext() {
        //        int nextDays;
        //        LocalDateTime adesso = LocalDateTime.now();
        //        LocalDateTime prossimo = null;
        //
        //        if (typeSchedule != null) {
        //            nextDays = typeSchedule.getGiorniNext();
        //            prossimo = adesso.plusDays(nextDays);
        //        }
        //
        //        if (flagPrevisione != null && prossimo != null) {
        //            flagPrevisione.setValue(prossimo);
        //        }
    }

    public String getPattern() {
        return typeSchedule.getPattern();
    }


    public String getDescrizioneTask() {
        return descrizioneTask;
    }


    public TypeSchedule getTypeSchedule() {
        return typeSchedule;
    }

    public IPref getFlagAttivazione() {
        return flagAttivazione;
    }

    public void logTaskEseguito(String risultato) {
        String message = descrizioneTask + CAPO + risultato;
        mailService.send(BaseVar.projectCurrent, message);
    }

    public void logTaskEseguito() {
        long fine = System.currentTimeMillis();
        String message;
        String clazzName;
        long delta = fine - inizio;
        delta = delta / 1000 / 60;

        clazzName = this.getClass().getSimpleName();
        message = String.format("%s%s%s [%s] eseguita in %s minuti", clazzName, FORWARD, descrizioneTask, getPattern(), delta);

        logger.info(new WrapLog().type(TypeLog.task).message(message).usaDb());
        if (Pref.usaSendMail.is()) {
            message = String.format("%s %s eseguita in %s minuti", descrizioneTask, getPattern(), delta);
            mailService.send(getClass().getSimpleName(), message);
        }
    }

    public void logTaskNonEseguito() {
        String message;
        String clazzName;

        clazzName = this.getClass().getSimpleName();
        message = String.format("%s%s%s [%s] non eseguita per flag disabilitato", clazzName, FORWARD, descrizioneTask, getPattern());
        logger.info(new WrapLog().type(TypeLog.task).message(message).usaDb());

        if (Pref.usaSendMail.is()) {
            message = this.getClass().getSimpleName();
            message += CAPO;
            message += typeSchedule.getPattern();
            message += CAPO;
            message += String.format("%s=spento", flagAttivazione);
            message += descrizioneTask;
            mailService.send(BaseVar.projectCurrent, message);

//            message = String.format("%s %s non eseguita per flag disabilitato", descrizioneTask, getPattern());
//            mailService.send(BaseVar.projectCurrent, message);
        }
    }


    public String info() {
        String message;
        String clazzName = this.getClass().getSimpleName();
        String desc = this.getDescrizioneTask();
        TypeSchedule type = this.getTypeSchedule();
        String pattern = type.getPattern();
        String nota = type.getNota();

        message = String.format("%s [%s] %s %s %s", clazzName, pattern, FORWARD, desc, nota);
        return message;
    }

    public String infoFlag() {
        String message;
        String flagText = TASK_NO_FLAG + TASK_FLAG_SEMPRE_ATTIVA;
        String clazzName = this.getClass().getSimpleName();
        String desc = this.getDescrizioneTask();
        TypeSchedule type = this.getTypeSchedule();
        String pattern = type.getPattern();
        String nota = type.getNota();
        //        int nextDays = this.getTypeSchedule().getGiorniNext();
        IPref flagTask = this.getFlagAttivazione();
        if (flagTask != null) {
            if (flagTask.is()) {
                flagText = flagTask.getKeyCode() + TASK_FLAG_ATTIVA;
            }
            else {
                flagText = flagTask.getKeyCode() + TASK_FLAG_DISATTIVA;
            }
        }

        message = String.format("%s %s %s%s%s %s", clazzName, pattern, flagText, FORWARD, nota, desc);
        return message;
    }

    public static String infoFlag(Class taskNonIstanziata) {
        BaseTask task = getTask(taskNonIstanziata);
        return task != null ? task.infoFlag() : VUOTA;
    }


    public static BaseTask getTask(Class taskNonIstanziata) {
        List<BaseTask> listaTasks = BaseVar.taskList;

        if (listaTasks != null) {
            for (BaseTask task : listaTasks) {
                if (task.getClass().getSimpleName().equals(taskNonIstanziata.getSimpleName())) {
                    return task;
                }
            }
        }

        return null;
    }

}