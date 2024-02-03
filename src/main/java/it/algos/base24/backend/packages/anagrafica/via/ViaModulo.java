package it.algos.base24.backend.packages.anagrafica.via;

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
 * Date: mer, 25-ott-2023
 * Time: 07:46
 */
@Service
public class ViaModulo extends CrudModulo {


    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ViaModulo() {
        super(ViaEntity.class, ViaView.class, ViaList.class, ViaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public ViaEntity creaIfNotExists(ViaEntity entityBean) {
        if (existById(entityBean.getId())) {
            return null;
        }
        else {
            return (ViaEntity) insert(entityBean);
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ViaEntity newEntity() {
        return newEntity(0, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ViaEntity newEntity(final int ordine, final String nome) {
        ViaEntity newEntityBean = ViaEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (ViaEntity) fixKey(newEntityBean);
    }

    @Override
    public ViaEntity findByKey(final Object keyPropertyValue) {
        return (ViaEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        return resetBase(typeReset);
    }

    @Override
    public RisultatoReset resetAdd() {
        RisultatoReset typeReset = super.resetAdd();
        return resetBase(typeReset);
    }

    private RisultatoReset resetBase(RisultatoReset typeReset) {
        String nomeFileCSV = "vie.csv";
        int pos = 1;
        String message;
        ViaEntity newBean;

        Map<String, List<String>> mappaSource = resourceService.leggeMappa(nomeFileCSV);
        if (mappaSource != null && mappaSource.size() > 0) {
            for (List<String> rigaUnValore : mappaSource.values()) {
                newBean = newEntity(pos++, rigaUnValore.get(0));
                mappaBeans.put(rigaUnValore.get(0), newBean);
            }
            mappaBeans.values().stream().forEach(bean -> creaIfNotExists((ViaEntity) bean));
        }
        else {
            message = String.format("Manca il file [%s] nella directory /config o sul server", nomeFileCSV);
            logger.warn(new WrapLog().message(message).type(TypeLog.startup));
            typeReset = RisultatoReset.nonIntegrato;
        }

        return typeReset;
    }


}// end of CrudModulo class
