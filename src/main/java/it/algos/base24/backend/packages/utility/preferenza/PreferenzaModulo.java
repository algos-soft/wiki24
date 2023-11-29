package it.algos.base24.backend.packages.utility.preferenza;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 13:47
 */
@Service
public class PreferenzaModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public PreferenzaModulo() {
        super(PreferenzaEntity.class, PreferenzaList.class, PreferenzaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Regola le property di una ModelClazz <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     */
    @Override
    public List<String> getPropertyNames() {
        return Arrays.asList("code", "type", "value", "descrizione");
    }


    public PreferenzaEntity creaIfNotExists(PreferenzaEntity entityBean) {
        if (existById(entityBean.getId())) {
            return null;
        }
        else {
            return (PreferenzaEntity) insert(entityBean);
        }
    }

    public PreferenzaEntity updateIfExists(PreferenzaEntity newBean) {
        PreferenzaEntity oldBean = (PreferenzaEntity) findOneById(newBean.id);

        if (oldBean == null) {
            return (PreferenzaEntity) insert(newBean);
        }

        if (oldBean.equals(newBean)) {
            return newBean;
        }
        else {
            oldBean.descrizione = newBean.descrizione;
            return (PreferenzaEntity) save(oldBean);
        }

    }

    public PreferenzaEntity newEntity() {
        return newEntity(VUOTA, null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(Pref pref) {
        String keyCode = pref.getKeyCode();
        TypePref type = pref.getType();
        Object defaultValue = pref.getDefaultValue();
        String descrizione = pref.getDescrizione();

        return newEntity(keyCode, type, defaultValue, descrizione);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code         (obbligatorio, unico)
     * @param type         (obbligatorio)
     * @param defaultValue (obbligatorio)
     * @param descrizione  (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(String code, TypePref type, Object defaultValue, String descrizione) {
        PreferenzaEntity newEntityBean = PreferenzaEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .type(type)
                .value(type != null ? type.objectToBytes(defaultValue) : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (PreferenzaEntity) fixKey(newEntityBean);
    }


    public RisultatoReset resetStartup() {
        RisultatoReset typeReset = RisultatoReset.nessuno;
        String message;

        if (reflectionService.isEsisteMetodo(getClass(), METHOD_RESET_ADD)) {
            typeReset = collectionNullOrEmpty() ? RisultatoReset.vuotoMaCostruito : RisultatoReset.esistenteNonModificato;
            resetBase();
            mappaBeans.values().stream().forEach(bean -> creaIfNotExists((PreferenzaEntity) bean));
            return typeReset;
        }
        else {
            message = String.format("La POJO [%s] ha il flag usaStartupReset=true ma manca il metodo %s() nella classe %s", currentCrudEntityClazz.getSimpleName(), METHOD_RESET_ADD, getClass().getSimpleName());
            logger.warn(new WrapLog().message(message).type(TypeLog.startup));
        }

        return typeReset;
    }


    @Override
    public RisultatoReset resetAdd() {
        RisultatoReset typeReset = super.resetAdd();
        resetBase();
        mappaBeans.values().stream().forEach(bean -> updateIfExists((PreferenzaEntity) bean));
        return typeReset;
    }


    private void resetBase() {
        PreferenzaEntity newBean;

        for (Pref pref : Pref.getAllEnums()) {
            newBean = newEntity(pref);
            mappaBeans.put(pref.name(), newBean);
        }
    }

    public boolean is(Pref pref) {
        return is(pref.getType(), pref.getKeyCode());
    }

    public boolean is(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.bool) {
            obj = getValue(type, keyCode);
            if (obj instanceof Boolean value) {
                return value;
            }
            return false;
        }
        else {
            //            log(type, keyCode, "getInt");
            return false;
        }
    }

    public String get(Pref pref) {
        return get(pref.getType(), pref.getKeyCode());
    }

    public String get(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.integer) {
            obj = getValue(type, keyCode);
            if (obj instanceof String value) {
                return value;
            }
            return VUOTA;
        }
        else {
            //            log(type, keyCode, "getInt");
            return VUOTA;
        }
    }

    public int getInt(Pref pref) {
        return getInt(pref.getType(), pref.getKeyCode());
    }

    public int getInt(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.integer) {
            obj = getValue(type, keyCode);
            if (obj instanceof Integer value) {
                return value;
            }
            return 0;
        }
        else {
            //            log(type, keyCode, "getInt");
            return 0;
        }
    }

    public Object getValue(TypePref type, String keyCode) {
        Object javaValue;
        PreferenzaEntity preferenza = (PreferenzaEntity) findOneById(keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getValue()) : null;

        return javaValue;
    }

    public boolean saveDifferences(Pref enumPref) {
        boolean status = false;
        PreferenzaEntity existingEntityBean;
        String keyCode = enumPref.getKeyCode();

        existingEntityBean = (PreferenzaEntity) findOneById(keyCode);
        if (!existingEntityBean.code.equals(enumPref.getKeyCode()) || !existingEntityBean.descrizione.equals(enumPref.getDescrizione())) {
            existingEntityBean.code = enumPref.getKeyCode();
            existingEntityBean.descrizione = enumPref.getDescrizione();
            save(existingEntityBean);
        }

        return status;
    }

}// end of CrudModulo class