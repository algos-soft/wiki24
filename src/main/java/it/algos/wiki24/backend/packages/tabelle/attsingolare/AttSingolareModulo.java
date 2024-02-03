package it.algos.wiki24.backend.packages.tabelle.attsingolare;

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
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 */
@Service
public class AttSingolareModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttSingolareModulo() {
        super(AttSingolareEntity.class, AttSingolareView.class, AttSingolareList.class, AttSingolareForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadAttSin;
        super.durataDownload = WPref.downloadAttSinTime;
        super.unitaMisuraDownload = TypeDurata.secondi;

        super.lastElabora = WPref.lastElaboraAttSin;
        super.durataElabora = WPref.elaboraAttSinTime;
        super.unitaMisuraElabora = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttSingolareEntity newEntity() {
        return newEntity(VUOTA, VUOTA, false, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale) {
        return newEntity(keyPropertyValue, plurale, false, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale, boolean ex) {
        return newEntity(keyPropertyValue, plurale, ex, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale, boolean ex, String pagina) {
        AttSingolareEntity newEntityBean = AttSingolareEntity.builder()
                .singolare(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .ex(ex)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .bio(0)
                .build();

        return (AttSingolareEntity) fixKey(newEntityBean);
    }

    public AttSingolareEntity findOneById(String idValue) {
        return (AttSingolareEntity) super.findOneById(idValue);
    }

    @Override
    public List<AttSingolareEntity> findAll() {
        return super.findAll();
    }

    public List<String> findSingolareAll() {
        return findAll().stream().map(att -> att.singolare).collect(Collectors.toList());
    }

    public List<AttSingolareEntity> findAllByProperty(final String propertyName, final Object propertyValue) {
        return super.findAllBeanByProperty(propertyName, propertyValue)
                .stream()
                .map(bean -> (AttSingolareEntity) bean)
                .collect(Collectors.toList());
        //        Query query = new Query();
        //
        //        if (textService.isEmpty(propertyName)) {
        //            return null;
        //        }
        //        if (propertyValue == null) {
        //            return null;
        //        }
        //
        //        query.addCriteria(Criteria.where(propertyName).is(propertyValue));
        //
        //        return findQuery(query);
    }

    protected List<AttSingolareEntity> findQuery(Query query) {
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);

        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz, collectionName);
        }
        else {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz);
        }
    }

    public List<AttSingolareEntity> findAllByPlurale(AttSingolareEntity plurale) {
        return this.findAllByProperty("plurale", plurale.plurale);
    }

    public List<AttSingolareEntity> findAllByPlurale(String plurale) {
        return this.findAllByProperty("plurale", plurale);
    }


    public List<String> findSingolariByPlurale(AttSingolareEntity plurale) {
        return findAllByPlurale(plurale).stream().map(att -> att.singolare).collect(Collectors.toList());
    }

    public List<String> findSingolariByPlurale(String plurale) {
        return findAllByPlurale(plurale).stream().map(att -> att.singolare).collect(Collectors.toList());
    }

    public List<AttSingolareEntity> findAllByDistinctPlurale() {
        List<AttSingolareEntity> lista = new ArrayList<>();
        Set<String> setPlurali = new HashSet();
        List<AttSingolareEntity> listaAll = findAll();

        for (AttSingolareEntity attivita : listaAll) {
            if (setPlurali.add(attivita.plurale)) {
                lista.add(attivita);
            }
        }

        return lista;
    }

    public List<String> findPluraliByDistinct() {
        List<String> lista = new ArrayList<>();
        Set<String> setPlurali = new HashSet();
        List<AttSingolareEntity> listaAll = findAll();

        for (AttSingolareEntity attivita : listaAll) {
            if (setPlurali.add(attivita.plurale)) {
                lista.add(attivita.plurale);
            }
        }

        Collections.sort(lista);
        return lista;
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

        this.downloadAttivitaPlurali();
        this.downloadAttivitaExtra();
        this.downloadPagineAttivita();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     *
     * @return entities create
     */
    public void downloadAttivitaPlurali() {
        String modulo = TAG_MODULO + "Plurale attività";
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
     * Legge le mappa dal Modulo:Bio/Ex attività <br>
     *
     * @return entities create
     */
    public void downloadAttivitaExtra() {
        String modulo = TAG_MODULO + "Ex attività";
        String singolare;
        String plurale;
        List<String> lista = wikiApiService.leggeListaModulo(modulo);
        AttSingolareEntity oldBean;

        if (lista == null || lista.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il %s", modulo);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (String key : lista) {
            if (mappaBeans.containsKey(key)) {
                oldBean = (AttSingolareEntity) mappaBeans.get(key);
                singolare = TAG_EX_SPAZIO + oldBean.singolare;
                singolare = textService.primaMinuscola(singolare);
                plurale = oldBean.plurale;
                mappaBeans.put(singolare, newEntity(singolare, plurale, true));
            }
            else {
                message = String.format("Nel modulo %s c'è l'attività [%s] che però non trovo nel modulo %s", modulo, key, "Plurale attività");
                logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            }
        }
    }


    /**
     * Legge le mappa dal Modulo:Bio/Link attività <br>
     * Crea le attività <br>
     *
     * @return entities create
     */
    public void downloadPagineAttivita() {
        String modulo = TAG_MODULO + "Link attività";
        Map<String, String> mappa = wikiApiService.leggeMappaModulo(modulo);
        AttSingolareEntity oldBean;

        if (mappa == null || mappa.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il %s", modulo);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (String key : mappa.keySet()) {
            if (mappaBeans.containsKey(key)) {
                oldBean = (AttSingolareEntity) mappaBeans.get(key);
                oldBean.pagina = textService.primaMaiuscola(mappa.get(key));
            }
            else {
                message = String.format("Nel modulo %s c'è l'attività [%s] che però non trovo nelle attività singolari", modulo, key);
                logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            }
        }
    }

}// end of CrudModulo class
