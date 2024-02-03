package it.algos.base24.backend.packages.utility.logs;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 16-Jan-2024
 * Time: 18:34
 */
@Service
public class LogModulo extends CrudModulo {

    @Inject
    FileService fileService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public LogModulo() {
        super(LogEntity.class, LogView.class, LogList.class, LogForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public LogEntity newEntity() {
        return newEntity(null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param typeLog     (obbligatorio)
     * @param typeLevel   (obbligatorio)
     * @param descrizione (descrizione)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public LogEntity newEntity(TypeLog typeLog, LogLevel typeLevel, String descrizione) {
        LogEntity newEntityBean = LogEntity.builder()
                .typeLog(typeLog == null ? TypeLog.system : typeLog)
                .typeLevel(typeLevel == null ? LogLevel.info : typeLevel)
                .evento(LocalDateTime.now())
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return newEntityBean;
    }

    public void crea(final WrapLog wrap) {
        LogEntity newBean;
        TypeLog typeLog = wrap.getType();
        LogLevel typeLevel = wrap.getLivello();
        String message = wrap.getMessage();
        //        String companySigla = wrap.getCompanySigla();
        //        String userName = wrap.getUserName();
        //        String addressIP = wrap.getAddressIP();
        String classe = VUOTA;
        String metodo = VUOTA;
        String descrizione = VUOTA;
        int linea = 0;

        if (wrap.getException() != null) {
            classe = wrap.getException().getClazz();
            classe = fileService.estraeClasseFinale(classe);
            metodo = wrap.getException().getMethod();
            linea = wrap.getException().getLineNum();
        }

        //        entity.descrizione = textService.isValid(message) ? message : null;
        //        entity.company = textService.isValid(companySigla) ? companySigla : null;
        //        entity.user = textService.isValid(userName) ? userName : null;
        //        entity.address = textService.isValid(addressIP) ? addressIP : null;
        //        entity.classe = textService.isValid(classe) ? classe : null;
        //        entity.metodo = textService.isValid(metodo) ? metodo : null;
        //        entity.linea = linea;

        newBean = newEntity(typeLog, typeLevel, descrizione);
        insert(newBean);
    }


    @Override
    public AbstractEntity afterInsert(AbstractEntity entityBean) {
        int appenderMax = APPENDER_MAX;
        int appenderOffset = APPENDER_OFFSET;
        int numEntities = count();
        List<LogEntity> listOrdinata = findAll();
        appenderMax = 70;
        appenderOffset = 5;

        if (numEntities > appenderMax) {
            for (LogEntity bean : listOrdinata.subList(0, appenderOffset)) {
                delete(bean);
            }
        }

        return super.afterSave(entityBean);
    }

}// end of CrudModulo class
