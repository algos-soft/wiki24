package it.algos.base24.backend.packages.crono.secolo;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 08:01
 */
@Service
public class SecoloModulo extends CrudModulo {
    public static final String INIZIO = "inizio";
    public static final String FINE = "fine";
    public static final String CRISTO = "anteCristo";

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public SecoloModulo() {
        super(SecoloEntity.class, SecoloList.class, SecoloForm.class);
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
    public SecoloEntity newEntity() {
        return newEntity(0, VUOTA, 0, 0, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       descrittivo e visualizzabile
     * @param inizio     primo anno del secolo
     * @param fine       ultimo anno del secolo
     * @param anteCristo secolo prima o dopo Cristo
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public SecoloEntity newEntity(final int ordine, final String nome, final int inizio, final int fine, final boolean anteCristo) {
        SecoloEntity newEntityBean = SecoloEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .inizio(inizio)
                .fine(fine)
                .anteCristo(anteCristo)
                .build();

        return (SecoloEntity) fixKey(newEntityBean);
    }

    /**
     * Seleziona un secolo dall'anno indicato <br>
     * SOLO per secoli AC <br>
     *
     * @param anno indicato per la selezione del secolo
     *
     * @return secolo Ante Cristo selezionato
     */
    public SecoloEntity getSecoloAC(final int anno) {
        Query query = new Query();
        String collectionName = annotationService.getCollectionName(SecoloEntity.class);

        query.addCriteria(Criteria.where(INIZIO).gte(anno));
        query.addCriteria(Criteria.where(FINE).lte(anno));
        query.addCriteria(Criteria.where(CRISTO).is(true));
        return mongoService.mongoOp.findOne(query, SecoloEntity.class, collectionName);
    }


    /**
     * Seleziona un secolo dall'anno indicato <br>
     * SOLO per secoli DC <br>
     *
     * @param anno indicato per la selezione del secolo
     *
     * @return secolo Dopo Cristo selezionato
     */
    public SecoloEntity getSecoloDC(int anno) {
        Query query = new Query();
        String collectionName = annotationService.getCollectionName(SecoloEntity.class);

        query.addCriteria(Criteria.where(INIZIO).lte(anno));
        query.addCriteria(Criteria.where(FINE).gte(anno));
        query.addCriteria(Criteria.where(CRISTO).is(false));
        return mongoService.mongoOp.findOne(query, SecoloEntity.class, collectionName);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        String nomeFileCSV = "secoli.csv";
        int ordine;
        String nome;
        int inizio;
        int fine;
        boolean anteCristo;
        String anteCristoText;
        String message;
        SecoloEntity newBean;

        Map<String, List<String>> mappaSource = resourceService.leggeMappa(nomeFileCSV);
        if (mappaSource != null) {
            for (List<String> riga : mappaSource.values()) {
                if (riga.size() == 5) {
                    try {
                        ordine = Integer.decode(riga.get(0));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        ordine = 0;
                    }
                    nome = riga.get(1);
                    try {
                        inizio = Integer.decode(riga.get(2));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        inizio = 0;
                    }
                    try {
                        fine = Integer.decode(riga.get(3));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        fine = 0;
                    }
                    anteCristoText = riga.get(4);
                    anteCristo = anteCristoText.equals("true") || anteCristoText.equals("vero") || anteCristoText.equals("si");
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb().type(TypeLog.startup));
                    return typeReset;
                }
                nome += anteCristo ? " secolo a.C." : " secolo";

                newBean = newEntity(ordine, nome, inizio, fine, anteCristo);
                if (newBean != null) {
                    mappaBeans.put(nome, newBean);
                }
                else {
                    message = String.format("La entity %s non è stata salvata", nome);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
                }
            }
        }
        else {
            message = String.format("Manca il file [%s] nella directory /config o sul server", nomeFileCSV);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
            return typeReset;
        }

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        return typeReset;
    }


}// end of CrudModulo class
