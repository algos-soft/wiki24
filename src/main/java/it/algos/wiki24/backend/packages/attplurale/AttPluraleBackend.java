package it.algos.wiki24.backend.packages.attplurale;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 16:50
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttPluraleBackend extends WikiBackend {


    public AttPluraleBackend() {
        super(AttPlurale.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.resetAttPlurale;
        super.lastDownload = WPref.downloadAttPlurale;
        super.durataDownload = WPref.downloadAttPluraleTime;
        super.lastElaborazione = WPref.elaboraAttPlurale;
        super.durataElaborazione = WPref.elaboraAttPluraleTime;
        super.lastUpload = WPref.uploadAttPlurale;
        super.durataUpload = WPref.uploadAttPluraleTime;
        super.nextUpload = WPref.uploadAttPluralePrevisto;
        super.lastStatistica = WPref.statisticaNazPlurale;

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AttPlurale newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AttPlurale newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome     (obbligatorio, unico)
     * @param lista    (obbligatorio, unico)
     * @param attivita (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AttPlurale newEntity(final String nome, List<AttSingolare> singolari, final String lista, final String attivita) {
        AttPlurale newEntityBean = AttPlurale.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .singolari(singolari)
                .lista(textService.isValid(lista) ? lista : null)
                .attivita(textService.isValid(attivita) ? attivita : null)
                .numBio(0)
                .numSingolari(0)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        newEntityBean.lista = textService.primaMaiuscola(newEntityBean.nome);
        return (AttPlurale) super.fixKey(newEntityBean);
    }

    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Link attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     */
    public WResult download() {
        WResult result = attSingolareBackend.download();
        int tempo = WPref.downloadAttPluraleTime.getInt();

        message = String.format("Initio %s() di %s. Tempo previsto: circa %d %s.", METHOD_NAME_DOWLOAD, AttSingolare.class.getSimpleName(), tempo, unitaMisuraDownload);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, "attività plurali")));

        result = downloadAttivitaLink(result);
        result.typeResult(AETypeResult.downloadValido);

        result = super.fixDownload(result, "attività plurali");

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Link attività <br>
     *
     * @return entities create
     */
    public WResult downloadAttivitaLink(WResult result) {
        String moduloLink = PATH_MODULO + PATH_LINK + ATT_LOWER;
        String singolareEx;
        String singolareNew;
        AttSingolare attivita;
        List lista = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloLink);

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolareEx = entry.getKey();
                singolareNew = TAG_EX_SPAZIO + singolareEx;
//                attivita = findByKey(singolareEx);

//                if (attivita != null) {
//                    try {
//                        attivita = (AttSingolare) insert(newEntity(singolareNew, attivita.plurale, true));
//                    } catch (Exception unErrore) {
//                        message = String.format("Duplicate error key %", singolareEx);
//                        System.out.println(message);
//                        logger.error(new WrapLog().exception(new AlgosException(unErrore)));
//                    }
//                }
//                else {
//                    logger.info(new WrapLog().message(String.format("Nelle attività base manca la definizione '%s'", singolareEx)));
//                }

//                if (attivita != null) {
//                    lista.add(attivita);
//                }
            }
            result.setIntValue(lista.size());
            result.setLista(lista);
            result.eseguito(lista.size() > 0);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloLink);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return result;
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
        List<String> nomiAttivitaPluraliDistinte = null;
        AEntity entityBean = null;
        List lista = new ArrayList();
        String clazzName = entityClazz.getSimpleName();

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result = attSingolareBackend.download();
        }
        else {
            return result;
        }

        if (result.isValido()) {
            nomiAttivitaPluraliDistinte = attSingolareBackend.findAllDistinctByPlurali();
        }

        if (nomiAttivitaPluraliDistinte != null && nomiAttivitaPluraliDistinte.size() > 0) {
            for (String plurale : nomiAttivitaPluraliDistinte) {
                try {
                    entityBean = insert(newEntity(plurale));
                } catch (Exception unErrore) {
                    message = String.format("Duplicate error key %", plurale);
                    System.out.println(message);
                    logger.error(new WrapLog().exception(new AlgosException(unErrore)));
                }
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", plurale))));
                }
            }
        }

        return super.fixReset(result, clazzName, lista, logInfo);
    }

}// end of crud backend class
