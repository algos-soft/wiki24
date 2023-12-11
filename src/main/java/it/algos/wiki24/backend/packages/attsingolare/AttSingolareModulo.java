package it.algos.wiki24.backend.packages.attsingolare;

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
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttSingolareModulo() {
        super(AttSingolareEntity.class, AttSingolareList.class, AttSingolareForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadAttSin;
        super.durataDownload = WPref.downloadAttSinTime;
        super.unitaMisuraDownload = TypeDurata.secondi;

        super.lastElaborazione = WPref.lastElaboraAttSin;
        super.durataElaborazione = WPref.elaboraAttSinTime;
        super.unitaMisuraElaborazione = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttSingolareEntity newEntity() {
        return newEntity(VUOTA, VUOTA, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale, boolean ex) {
        AttSingolareEntity newEntityBean = AttSingolareEntity.builder()
                .singolare(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .ex(ex)
                .numBio(0)
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
    private List<AttSingolareEntity> findQuery(Query query) {
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);

        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz, collectionName);
        }
        else {
            return mongoService.mongoOp.find(query, currentCrudEntityClazz);
        }
    }

    public List<AttSingolareEntity> findAllByPlurale(String plurale) {
        return this.findAllByProperty("plurale", plurale);
    }


    public List<String> findSingolariByPlurale(String plurale) {
        return  findAllByPlurale(plurale).stream().map(att -> att.singolare).collect(Collectors.toList()) ;
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
        String moduloPlurale = TAG_MODULO + "Plurale attività";
        String moduloEx = TAG_MODULO + "Ex attività";

        downloadAttivitaPlurali(moduloPlurale);
        downloadAttivitaExtra(moduloEx);

        super.fixDownload(inizio);
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le attività <br>
     *
     * @param moduloPlurale della pagina su wikipedia
     *
     * @return entities create
     */
    public void downloadAttivitaPlurali(String moduloPlurale) {
        String singolare;
        String plurale;
        Map<String, String> mappaPlurale = wikiApiService.leggeMappaModulo(moduloPlurale);
        AttSingolareEntity newBean;

        if (mappaPlurale != null && mappaPlurale.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappaPlurale.entrySet()) {
                singolare = entry.getKey();
                plurale = entry.getValue();
                newBean = newEntity(singolare, plurale, false);
                insertSave(newBean);
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", moduloPlurale);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
    }


    /**
     * Legge le mappa dal Modulo:Bio/Ex attività <br>
     * Crea le attività <br>
     *
     * @param moduloEx della pagina su wikipedia
     *
     * @return entities create
     */
    public void downloadAttivitaExtra(String moduloEx) {
        String singolare;
        String plurale;
        List<String> listaSingolari = findSingolareAll();
        List<String> listaEx = wikiApiService.leggeListaModulo(moduloEx);
        AttSingolareEntity oldBean;
        AttSingolareEntity newBean;

        if (listaEx != null && listaEx.size() > 0) {
            for (String key : listaEx) {
                if (listaSingolari.contains(key)) {
                    oldBean = findOneById(key);
                    if (oldBean != null) {
                        singolare = TAG_EX_SPAZIO + oldBean.singolare;
                        singolare = textService.primaMinuscola(singolare);
                        plurale = oldBean.plurale;
                        newBean = newEntity(singolare, plurale, true);
                        insertSave(newBean);
                    }
                }
                else {
                    message = String.format("Nel modulo %s c'è l'attività [%s] che però non trovo nelle attività singolari", moduloEx, key);
                    logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", moduloEx);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
    }

}// end of CrudModulo class
