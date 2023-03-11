package it.algos.vaad24.backend.packages.crono.anno;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 02-mag-2022
 * Time: 16:05
 */
@Service
public class AnnoBackend extends CrudBackend {

    @Autowired
    public SecoloBackend secoloBackend;


    public AnnoBackend() {
        super(Anno.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Anno newEntity() {
        return newEntity(0, VUOTA, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public Anno newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       corrente
     * @param secolo     di appartenenza
     * @param dopoCristo flag per gli anni prima/dopo cristo
     * @param bisestile  flag per gli anni bisestili
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Anno newEntity(final int ordine, final String nome, final Secolo secolo, final boolean dopoCristo, final boolean bisestile) {
        Anno newEntityBean = Anno.builderAnno()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();

        return (Anno) super.fixKey(newEntityBean);
    }

    @Override
    public Anno findById(final String keyID) {
        return (Anno) super.findById(keyID);
    }

    @Override
    public Anno findByKey(final String keyValue) {
        return (Anno) super.findByKey(keyValue);
    }

    @Override
    public Anno findByProperty(final String propertyName, final Object propertyValue) {
        return (Anno) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<Anno> findAllNoSort() {
        return (List<Anno>) super.findAllNoSort();
    }

    @Override
    public List<Anno> findAllSortCorrente() {
        return (List<Anno>) super.findAllSortCorrente();
    }

    public List<Anno> findAllBySecolo(Secolo secolo) {
        return findAllByProperty(FIELD_NAME_SECOLO, secolo);
    }

    public Anno findByOrdine(final int ordine) {
        return (Anno) super.findByOrdine(ordine);
    }


    @Override
    public Anno save(AEntity entity) {
        return (Anno) super.save(entity);
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(logInfo);
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        AEntity entityBean;
        List<AEntity> lista;
        String message;
        int tempo = 3;

        if (secoloBackend.count() < 1) {
            AResult resultMese = secoloBackend.resetOnlyEmpty(logInfo);
            if (resultMese.isErrato()) {
                logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Secolo'")).usaDb());
                return result.fine();
            }
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result.setValido(true);
            lista = new ArrayList<>();
            message = String.format("Inizio resetOnlyEmpty() di %s. Tempo previsto: circa %d secondi.", clazzName, tempo);
            logger.debug(new WrapLog().message(message));

            //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
            for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
                entityBean = creaPrima(k);
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
                }
            }

            //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
            for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
                entityBean = creaDopo(k);
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
                    result.setValido(false);
                }
            }
            return super.fixResult(result, clazzName, collectionName, lista, logInfo);
        }
        else {
            return result.fine();
        }
    }

    public AEntity creaPrima(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nomeVisibile = numeroAnno + tagPrima;
        Secolo secolo = secoloBackend.findByAnnoAC(numeroAnno);

        return insert(newEntity(ordine, nomeVisibile, secolo, false, false));

    }

    public AEntity creaDopo(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = numeroProgressivo;
        int ordine = numeroProgressivo + delta;
        String nomeVisibile = numeroProgressivo + VUOTA;
        Secolo secolo = secoloBackend.findByAnnoDC(numeroAnno);
        boolean bisestile = dateService.isBisestile(numeroAnno);

        return insert(newEntity(ordine, nomeVisibile, secolo, true, bisestile));
    }

}// end of crud backend class