package it.algos.base24.backend.packages.utility.nota;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 02-nov-2023
 * Time: 09:14
 */
@Service
public class NotaModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NotaModulo() {
        super(NotaEntity.class, NotaView.class, NotaList.class, NotaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Regola le property visibili in una scheda CrudForm <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    public List<String> getFormPropertyNames() {
        return Arrays.asList("typeLog", "typeLevel", "inizio", "descrizione", "fatto");
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NotaEntity newEntity() {
        return newEntity(null, null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param typeLog     merceologico della nota
     * @param typeLevel   di importanza o rilevanza della nota
     * @param descrizione dettagliata della nota
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NotaEntity newEntity(TypeLog typeLog, LogLevel typeLevel, LocalDate inizio, String descrizione) {
        NotaEntity newEntityBean = NotaEntity.builder()
                .typeLog(typeLog == null ? TypeLog.system : typeLog)
                .typeLevel(typeLevel == null ? LogLevel.info : typeLevel)
                .inizio(inizio != null ? inizio : LocalDate.now())
                .descrizione(descrizione != null ? descrizione : null)
                .build();

        return newEntityBean;
    }

    @Override
    public Sort getBasicSort() {
        return Sort.by(Sort.Order.asc("fatto"), Sort.Order.desc("evento"));
    }

    @Override
    public AbstractEntity beforeSave(AbstractEntity entityBean) {
        NotaEntity notaBean = (NotaEntity) entityBean;

        if (notaBean.fatto) {
            if (notaBean.fine == null) {
                notaBean.fine = LocalDate.now();
            }
        }
        else {
            notaBean.fine = null;
        }

        return super.beforeSave(notaBean);
    }

}// end of CrudModulo class
