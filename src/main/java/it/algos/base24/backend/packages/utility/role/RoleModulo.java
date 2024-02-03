package it.algos.base24.backend.packages.utility.role;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import org.springframework.stereotype.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 12:17
 */
@Service
public class RoleModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public RoleModulo() {
        super(RoleEntity.class, RoleView.class, RoleList.class, RoleForm.class);
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
    public RoleEntity newEntity() {
        return newEntity(0, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine (opzionale, unico)
     * @param code   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public RoleEntity newEntity(final int ordine, final String code) {
        RoleEntity newEntityBean = RoleEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .code(textService.isValid(code) ? code : null)
                .build();

        return (RoleEntity) fixKey(newEntityBean);
    }


    public RoleEntity findOneByKey(RoleEnum role) {
        return findOneByKey(role.name());
    }

    public RoleEntity findOneByKey(String keyValue) {
        return (RoleEntity) mongoService.findOneByKey(currentCrudEntityClazz, keyValue);
    }


    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        RoleEntity newBean;

        if (typeReset == RisultatoReset.esistenteNonModificato) {
            return typeReset;
        }

        for (RoleEnum roleEnum : RoleEnum.values()) {
            newBean = newEntity(roleEnum.ordinal() + 1, roleEnum.name());
            if (newBean != null) {
                mappaBeans.put(roleEnum.name(), newBean);
            }
        }
        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        return typeReset;
    }

}// end of CrudModulo class
