package it.algos.wiki24.backend.packages.tabelle.nazsingolare;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 06:55
 */
@Service
public class NazSingolareModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NazSingolareModulo() {
        super(NazSingolareEntity.class, NazSingolareView.class, NazSingolareList.class, NazSingolareForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadNazSin;
        super.durataDownload = WPref.downloadNazSinTime;
        super.unitaMisuraDownload = TypeDurata.secondi;

        super.lastElabora = WPref.lastElaboraNazSin;
        super.durataElabora = WPref.elaboraNazSinTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NazSingolareEntity newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA);
    }

    public NazSingolareEntity newEntity(String keyPropertyValue, String plurale) {
        return newEntity(keyPropertyValue, plurale, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NazSingolareEntity newEntity(final String keyPropertyValue, String plurale, String pagina) {
        NazSingolareEntity newEntityBean = NazSingolareEntity.builder()
                .singolare(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .bio(0)
                .build();

        return (NazSingolareEntity) fixKey(newEntityBean);
    }

    public List<NazSingolareEntity> findAllByDistinctPlurale() {
        List<NazSingolareEntity> lista = new ArrayList<>();
        Set<String> setPlurali = new HashSet();
        List<NazSingolareEntity> listaAll = findAll();

        for (NazSingolareEntity nazionalita : listaAll) {
            if (setPlurali.add(nazionalita.plurale)) {
                lista.add(nazionalita);
            }
        }

        return lista;
    }

    public List<NazSingolareEntity> findAllByPlurale(NazSingolareEntity plurale) {
        return this.findAllByProperty("plurale", plurale.plurale);
    }

    public List<String> findSingolariByPlurale(NazSingolareEntity plurale) {
        return findAllByPlurale(plurale).stream().map(naz -> naz.singolare).collect(Collectors.toList());
    }


    public List<NazSingolareEntity> findAllByProperty(final String propertyName, final Object propertyValue) {
        Query query = new Query();

        if (textService.isEmpty(propertyName)) {
            return null;
        }
        if (propertyValue == null) {
            return null;
        }

        query.addCriteria(Criteria.where(propertyName).is(propertyValue));

        return findQuery(query);
    }

    protected List<NazSingolareEntity> findQuery(Query query) {
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);

        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz, collectionName);
        }
        else {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz);
        }
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }


    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Plurale attività
     * Modulo:Bio/Ex attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività singolari <br>
     */
    public void download() {
        inizio = System.currentTimeMillis();

        super.deleteAll();

        this.downloadNazionalita();
        this.downloadPagineAttivita();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale nazionalità <br>
     *
     * @return entities create
     */
    public void downloadNazionalita() {
        String modulo = TAG_MODULO + "Plurale nazionalità";
        String singolare;
        String plurale;
        Map<String, String> mappa = wikiApiService.leggeMappaModulo(modulo);

        if (mappa == null || mappa.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il %s", modulo);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (Map.Entry<String, String> entry : mappa.entrySet()) {
            singolare = entry.getKey();
            plurale = entry.getValue();
            mappaBeans.put(singolare, newEntity(singolare, plurale));
        }
    }


    /**
     * Legge le mappa dal Modulo:Bio/Link nazionalità <br>
     * Crea le attività <br>
     *
     * @return entities create
     */
    public void downloadPagineAttivita() {
        String modulo = TAG_MODULO + "Link nazionalità";
        Map<String, String> mappa = wikiApiService.leggeMappaModulo(modulo);
        NazSingolareEntity oldBean;

        if (mappa == null || mappa.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il %s", modulo);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (String key : mappa.keySet()) {
            if (mappaBeans.containsKey(key)) {
                oldBean = (NazSingolareEntity) mappaBeans.get(key);
                oldBean.pagina = textService.primaMaiuscola(mappa.get(key));
            }
            else {
                message = String.format("Nel modulo %s c'è l'attività [%s] che però non trovo nelle nazionalità singolari", modulo, key);
                logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            }
        }
    }

}// end of CrudModulo class
