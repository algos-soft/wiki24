package it.algos.base24.backend.packages.crono.giorno;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 */
@Service
public class GiornoModulo extends CrudModulo {

    @Autowired
    public MeseModulo meseModulo;

    @Autowired
    public DateService dateService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public GiornoModulo() {
        super(GiornoEntity.class, GiornoList.class, GiornoForm.class);
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
    public GiornoEntity newEntity() {
        return newEntity(0, VUOTA, null, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome      corrente
     * @param mese      di appartenenza
     * @param trascorsi di inizio anno
     * @param mancanti  alla fine dell'anno
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public GiornoEntity newEntity(final int ordine, final String nome, final MeseEntity mese, final int trascorsi, final int mancanti) {
        GiornoEntity newEntityBean = GiornoEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .mese(mese)
                .trascorsi(trascorsi)
                .mancanti(mancanti)
                .build();

        return (GiornoEntity) fixKey(newEntityBean);
    }


    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        int ordine;
        String nome;
        String meseTxt;
        MeseEntity mese ;
        int trascorsi;
        int mancanti;
        String message;
        GiornoEntity newBean ;
        List<HashMap<String, Object>> lista ;

        if (meseModulo.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione [mese]")).usaDb().type(TypeLog.startup));
            return typeReset;
        }

        lista = dateService.getAllGiorni();
        if (lista != null && lista.size() == NUM_GIORNI_ANNO) {
            for (HashMap<String, Object> mappaGiorno : lista) {
                nome = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
                meseTxt = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
                mese = (MeseEntity) meseModulo.findOneByKey(meseTxt);
                if (mese == null) {
                    message = String.format("Manca il mese di %s", meseTxt);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
                }

                ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
                trascorsi = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
                mancanti = NUM_GIORNI_ANNO - trascorsi;

                newBean = newEntity(ordine, nome, mese, trascorsi, mancanti);
                if (newBean != null) {
                    mappaBeans.put(nome, newBean);
                }
            }
        }

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        return typeReset;
    }

}// end of CrudModulo class
