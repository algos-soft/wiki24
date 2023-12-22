package it.algos.base24.backend.packages.utility.nota;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
import org.springframework.stereotype.*;

import java.time.*;

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
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NotaModulo() {
        super(NotaEntity.class, NotaList.class, NotaForm.class);
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
    public NotaEntity newEntity() {
        return newEntity(VUOTA,0,null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NotaEntity newEntity(String code, long lungo, LocalDateTime evento) {
        NotaEntity newEntityBean = NotaEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .lungo(lungo)
                .evento(evento!=null?evento:ROOT_DATA_TIME)
                .build();

        return (NotaEntity) fixKey(newEntityBean);
    }

}// end of CrudModulo class
