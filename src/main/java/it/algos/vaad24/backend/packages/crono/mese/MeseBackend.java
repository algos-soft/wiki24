package it.algos.vaad24.backend.packages.crono.mese;

import com.mongodb.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 01-mag-2022
 * Time: 08:51
 */
@Service
public class MeseBackend extends CrudBackend {


    public MeseBackend() {
        super(Mese.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Mese newEntity() {
        return newEntity(0, VUOTA, VUOTA, 0, 0, 0);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Mese newEntity(final String keyPropertyValue) {
        return newEntity(0, VUOTA, keyPropertyValue, 0, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param breve  (obbligatorio, unico)
     * @param nome   (obbligatorio, unico)
     * @param giorni (obbligatorio)
     * @param primo  giorno dell'anno (facoltativo)
     * @param ultimo giorno dell'anno (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Mese newEntity(int ordine, String breve, String nome, int giorni, int primo, int ultimo) {
        Mese newEntityBean = Mese.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .breve(textService.isValid(breve) ? breve : null)
                .giorni(giorni)
                .primo(primo)
                .ultimo(ultimo)
                .build();

        return (Mese) super.fixKey(newEntityBean);
    }

    @Override
    public Mese findById(final String keyID) {
        return (Mese) super.findById(keyID);
    }

    @Override
    public Mese findByKey(final String keyValue) {
        return (Mese) super.findByKey(keyValue);
    }

    @Override
    public Mese findByProperty(final String propertyName, final Object propertyValue) {
        return (Mese) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public Mese findByOrdine(final int ordine) {
        return (Mese) super.findByOrdine(ordine);
    }

    @Override
    public List<Mese> findAllNoSort() {
        return (List<Mese>) super.findAllNoSort();
    }

    @Override
    public List<Mese> findAllSortCorrente() {
        return (List<Mese>) super.findAllSortCorrente();
    }


    public List<String> findAllForKey() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, 1));
    }


    public List<String> findAllForKeyReverseOrder() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, -1));
    }

    @Override
    public Mese save(AEntity entity) {
        return (Mese) super.save(entity);
    }

    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(logInfo);
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        String nomeFileCSVSulServerAlgos = "mesi";
        Map<String, List<String>> mappa;
        List<String> riga;
        int giorni;
        String breve;
        String nome;
        List<AEntity> lista;
        AEntity entityBean;
        String message;
        int ordine = 0;
        int primo = 0;
        int ultimo = 0;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            message = String.format("Inizio resetOnlyEmpty() di %s. Tempo previsto: meno di 1 secondo.", clazzName);
            logger.debug(new WrapLog().message(message));
            mappa = resourceService.leggeMappa(nomeFileCSVSulServerAlgos);
            if (mappa != null) {
                result.setValido(true);
                lista = new ArrayList<>();

                for (String key : mappa.keySet()) {
                    riga = mappa.get(key);
                    if (riga.size() >= 3) {
                        try {
                            giorni = Integer.decode(riga.get(0));
                        } catch (Exception unErrore) {
                            logger.error(new WrapLog().exception(unErrore).usaDb());
                            giorni = 0;
                        }
                        breve = riga.get(1);
                        nome = riga.get(2);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb());
                        return null;
                    }
                    if (riga.size() >= 4) {
                        primo = Integer.decode(riga.get(3));
                    }
                    if (riga.size() >= 5) {
                        ultimo = Integer.decode(riga.get(4));
                    }

                    if (giorni > 0 && primo > 0 && ultimo > 0) {
                        if (giorni != (ultimo - primo + 1)) {
                            message = String.format("Il numero di 'giorni' da 'primo' a 'ultimo' non coincidono per il mese di %s", nome);
                            logger.error(new WrapLog().exception(new AlgosException(message)));
                            return null;
                        }
                    }

                    entityBean = insert(newEntity(++ordine, breve, nome, giorni, primo, ultimo));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                        result.setValido(false);
                    }
                }
                return super.fixResult(result, clazzName, collectionName, lista, logInfo);
            }
            else {
                return result.errorMessage("Non ho trovato il file sul server").fine();
            }
        }
        else {
            return result.fine();
        }
    }

}// end of crud backend class