package it.algos.base24.backend.packages.crono.mese;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Nov-2023
 * Time: 18:38
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class MeseModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public MeseModulo() {
        super(MeseEntity.class, MeseList.class, MeseForm.class);
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
    public MeseEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, 0, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public MeseEntity newEntity(int ordine, String sigla, String nome, int giorni, int primo, int ultimo) {
        MeseEntity newEntityBean = MeseEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .sigla(textService.isValid(sigla) ? sigla : null)
                .nome(textService.isValid(nome) ? nome : null)
                .giorni(giorni)
                .primo(primo)
                .ultimo(ultimo)
                .build();

        return (MeseEntity) fixKey(newEntityBean);
    }

    public List<MeseEntity> findAll() {
        return super.findAll();
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        MeseEntity newBean;
        int ordine = 0;
        String sigla = VUOTA;
        String nome = VUOTA;
        int giorni = 0;
        int primo = 1;
        int ultimo = 0;

        if (typeReset == RisultatoReset.esistenteNonModificato) {
            return typeReset;
        }

        for (MeseEnum meseEnum : MeseEnum.values()) {
            ordine = meseEnum.ordinal() + 1;
            sigla = meseEnum.getSigla();
            nome = meseEnum.getNome();
            primo = ultimo+1;
            giorni = meseEnum.getGiorni();
            ultimo = primo + giorni-1;
            newBean = newEntity(ordine, sigla, nome, giorni, primo, ultimo);
            if (newBean != null) {
                mappaBeans.put(sigla, newBean);
            }
        }

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        return typeReset;
    }

}// end of CrudModulo class
