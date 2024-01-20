package it.algos.base24.backend.packages.utility.preferenza;

import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.time.*;
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

    @Inject
    ApplicationContext applicationContext;

    private String message;

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
        return Arrays.asList("code", "type", "iniziale", "corrente", "descrizione", "critical", "dinamica", "base24");
    }


    public PreferenzaEntity insertAndUpdateIfExists(PreferenzaEntity newBean) {
        PreferenzaEntity oldBean = (PreferenzaEntity) findOneById(newBean.id);

        if (oldBean == null) {
            return (PreferenzaEntity) insert(newBean);
        }

        if (oldBean.equals(newBean)) {
            return newBean;
        }
        else {
            oldBean.iniziale = newBean.iniziale;
            oldBean.descrizione = newBean.descrizione;
            return (PreferenzaEntity) save(oldBean);
        }

    }

    public PreferenzaEntity newEntity() {
        return newEntity(VUOTA, null, null, VUOTA, false, false, true);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(IPref pref) {
        String keyCode = pref.getKeyCode();
        TypePref type = pref.getType();
        Object defaultValue = pref.getDefaultValue();
        String descrizione = pref.getDescrizione();
        boolean critical = pref.isCritical();
        boolean dinamica = pref.isDinamica();
        boolean base24 = pref.isBase();

        return newEntity(keyCode, type, defaultValue, descrizione, critical, dinamica, base24);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code        (obbligatorio, unico)
     * @param type        (obbligatorio)
     * @param iniziale    (obbligatorio)
     * @param descrizione (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(String code, TypePref type, Object iniziale, String descrizione, boolean critical, boolean dinamica, boolean base24) {
        PreferenzaEntity newEntityBean = PreferenzaEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .type(type)
                .iniziale(type != null ? type.objectToBytes(iniziale) : null)
                .corrente(type != null ? type.objectToBytes(iniziale) : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .critical(critical)
                .dinamica(dinamica)
                .base24(base24)
                .build();

        return (PreferenzaEntity) fixKey(newEntityBean);
    }


    @Override
    public RisultatoReset resetPref() {
        RisultatoReset typeReset = super.resetPref();
        PreferenzaEntity newBean;
        BaseBoot currentBoot = null;
        try {
            currentBoot = (BaseBoot) applicationContext.getBean(BaseVar.bootClazzQualifier);
        } catch (Exception unErrore) {
        }

        if (currentBoot != null) {
            for (IPref pref : BaseVar.prefList) {
                newBean = newEntity(pref);
                mappaBeans.put(pref.getTag(), newBean);
            }
        }

        mappaBeans.values().stream().forEach(bean -> insertAndUpdateIfExists((PreferenzaEntity) bean));
        return typeReset;
    }


    public String getStr(Pref pref) {
        return getStr(pref.getType(), pref.getKeyCode());
    }

    public String getStr(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.string) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof String value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getStr", TypePref.string.getTag());
        logger.warn(new WrapLog().message(message));
        return VUOTA;
    }


    public boolean is(Pref pref) {
        return is(pref.getType(), pref.getKeyCode());
    }

    public boolean is(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.bool) {
            obj = getValueCorrente(type, keyCode);

            if (obj == null) {
                message = String.format("Nel database non esiste la preferenza [%s]. Controlla che l'enumeration delle preferenze sia stata caricata", keyCode);
                logger.error(new WrapLog().message(message));
                return false;
            }

            if (obj instanceof Boolean value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "is", TypePref.bool.getTag());
        logger.warn(new WrapLog().message(message));
        return false;
    }

    public int getInt(Pref pref) {
        return getInt(pref.getType(), pref.getKeyCode());
    }

    public int getInt(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.integer) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof Integer value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getInt", TypePref.integer.getTag());
        logger.warn(new WrapLog().message(message));
        return 0;
    }


    public LocalDateTime getDateTime(Pref pref) {
        return getDateTime(pref.getType(), pref.getKeyCode());
    }

    public LocalDateTime getDateTime(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.localdatetime) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof LocalDateTime value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getDateTime", TypePref.localdatetime.getTag());
        logger.warn(new WrapLog().message(message));
        return ERROR_DATA_TIME;
    }


    public void setValueCorrente(TypePref type, String keyCode, Object javaValue) {
        PreferenzaEntity preferenza = (PreferenzaEntity) mongoService.findOneById(PreferenzaEntity.class, keyCode);

        if (preferenza == null) {
            return;
        }

        preferenza.setCorrente(type.objectToBytes(javaValue));
        mongoService.save(preferenza);
    }

    public Object getValueCorrente(TypePref type, String keyCode) {
        Object javaValue;
        PreferenzaEntity preferenza = (PreferenzaEntity) findOneById(keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getCorrente()) : null;

        return javaValue;
    }

    public Object getValue(String keyCode) {
        Object javaValue = null;
        TypePref type;

        PreferenzaEntity preferenza = (PreferenzaEntity) findOneById(keyCode);
        if (preferenza != null) {
            type = preferenza.type;
            javaValue = type.bytesToObject(preferenza.getCorrente());
        }

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

    //--serve solo ad 'oscurare' il metodo sovrascritto
    //--le funzionalità inerenti sono eseguite da download
    public RisultatoReset resetDelete() {
        return null;
    }

}// end of CrudModulo class
