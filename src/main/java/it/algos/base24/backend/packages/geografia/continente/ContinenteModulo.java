package it.algos.base24.backend.packages.geografia.continente;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 29-ott-2023
 * Time: 06:59
 */
@Service
public class ContinenteModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ContinenteModulo() {
        super(ContinenteEntity.class, ContinenteList.class, ContinenteForm.class);
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
    public ContinenteEntity newEntity() {
        return newEntity(0, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine (opzionale, unico)
     * @param nome   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ContinenteEntity newEntity(final int ordine, final String nome) {
        ContinenteEntity newEntityBean = ContinenteEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (ContinenteEntity) fixKey(newEntityBean);
    }

    public List<ContinenteEntity> findAll() {
        return super.findAll();
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        ContinenteEntity newBean;
        String message;

        if (typeReset == RisultatoReset.esistenteNonModificato) {
            return typeReset;
        }

        for (ContinenteEnum contEnum : ContinenteEnum.values()) {
            newBean = newEntity(contEnum.ordinal() + 1, contEnum.getTag());
            if (newBean != null) {
                mappaBeans.put(contEnum.getTag(), newBean);
            }
            else {
                message = String.format("La entity %s non è stata salvata", contEnum.getTag());
                logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
            }
        }

        boolean usaNotification = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);
        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        Pref.usaNotification.setValue(usaNotification);

        return typeReset;
    }


}// end of CrudModulo class
