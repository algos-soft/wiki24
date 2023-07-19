package it.algos.wiki24.backend.packages.genere;

import com.mongodb.*;
import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.bson.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: dom, 24-apr-2022
 * Time: 10:17
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class GenereBackend extends WikiBackend {


    public GenereRepository repository;

    public GenereBackend() {
        super(Genere.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadGenere;
        super.lastDownload = WPref.downloadGenere;
        super.durataDownload = WPref.downloadGenereTime;

        super.sorgenteDownload = PATH_MODULO_GENERE;
        super.tagIniSorgente = "-- Attività al femminile plurale\nreturn {\n";
        super.tagEndSorgente = "}";
        super.tagSplitSorgente = VIRGOLA + CAPO;
        //        super.uploadTest = UPLOAD_TITLE_DEBUG + DOPPI;
    }


    public Genere creaIfNotExist(final String keyPropertyValue, final AETypeGenere type, final String pluraleMaschile, final String pluraleFemminile) {
        Genere newGenere;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            newGenere = newEntity(keyPropertyValue, type, pluraleMaschile, pluraleFemminile);
            return newGenere != null ? insert(newGenere) : null;
        }
    }

    public boolean isExist(final String singolare) {
        return findFirstBySingolare(singolare) != null;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Genere newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA);
    }

    public Genere newEntity(final Document doc) {
        Genere genere = new Genere();

        genere.singolare = doc.getString("singolare");
        genere.type = AETypeGenere.getType(doc.getString("type"));
        genere.pluraleMaschile = doc.getString("pluraleMaschile");
        genere.pluraleFemminile = doc.getString("pluraleFemminile");

        return genere;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param singolare        di riferimento
     * @param type             genere di riferimento
     * @param pluraleMaschile  di riferimento
     * @param pluraleFemminile di riferimento
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Genere newEntity(final String singolare, final AETypeGenere type, final String pluraleMaschile, final String pluraleFemminile) {
        Genere newEntityBean = Genere.builder()
                .singolare(textService.isValid(singolare) ? singolare : null)
                .type(type != null ? type : AETypeGenere.maschile)
                .pluraleMaschile(textService.isValid(pluraleMaschile) ? pluraleMaschile : null)
                .pluraleFemminile(textService.isValid(pluraleFemminile) ? pluraleFemminile : null)
                .build();

        return newEntityBean;
    }


    @Override
    public Genere findById(final String keyID) {
        return (Genere) super.findById(keyID);
    }

    @Override
    public Genere findByKey(final String keyValue) {
        return (Genere) super.findByKey(keyValue);
    }

    @Override
    public Genere findByProperty(final String propertyName, final Object propertyValue) {
        Genere genere = (Genere) super.findByProperty(propertyName, propertyValue);

        if (genere == null) {
            genere = creaGenere(propertyName, propertyValue);
        }

        return genere;
    }

    @Override
    public Genere save(AEntity entity) {
        return (Genere) super.save(entity);
    }

    @Override
    public Genere insert(AEntity entity) {
        return (Genere) super.insert(entity);
    }

    @Override
    public Genere update(AEntity entity) {
        return (Genere) super.update(entity);
    }

    @Override
    public List<Genere> findAll() {
        return super.findAll();
    }

    @Override
    public List<Genere> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<Genere> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<Genere> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Genere> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    protected Predicate<Genere> startEx = genere -> genere.singolare.startsWith(TAG_EX_SPAZIO) || genere.singolare.startsWith(TAG_EX2);


    public List<Genere> findStartingEx() {
        return (List<Genere>) findAllSortCorrente().stream().filter(startEx).collect(Collectors.toList());
    }

    /**
     * Retrieves the first entity by a 'singular' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param genereSingolare per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Genere findFirstBySingolare(final String genereSingolare) {
        return repository.findFirstBySingolare(genereSingolare);
    }

    public List<String> findAllSingolari() {
        List<String> singolari = new ArrayList<>();
        List<Genere> listaAll = findAllSortCorrente();

        for (Genere genere : listaAll) {
            singolari.add(genere.singolare);
        }

        return singolari;
    }

    public List<String> findAllPluraliMaschili() {
        List<String> plurali = new ArrayList<>();
        List<Genere> listaAll = findAllSortCorrente();

        for (Genere genere : listaAll) {
            if (genere.pluraleMaschile != null) {
                if (!plurali.contains(genere.pluraleMaschile)) {
                    plurali.add(genere.pluraleMaschile);
                }
            }
        }

        return plurali;
    }

    public List<String> findAllPluraliFemminili() {
        List<String> plurali = new ArrayList<>();
        List<Genere> listaAll = findAllSortCorrente();

        for (Genere genere : listaAll) {
            if (genere.pluraleFemminile != null) {
                if (!plurali.contains(genere.pluraleFemminile)) {
                    plurali.add(genere.pluraleFemminile);
                }
            }
        }

        return plurali;
    }

    public List<String> findAllPluraliDistinti() {
        List<String> plurali = new ArrayList<>();

        plurali.addAll(findAllPluraliMaschili());
        plurali.addAll(findAllPluraliFemminili());

        Collections.sort(plurali);
        return plurali;
    }

    public String getPluraleMaschile(String singolare) {
        Genere genere = findFirstBySingolare(singolare);
        return genere.pluraleMaschile != null ? genere.pluraleMaschile : VUOTA;
    }

    public String getPluraleFemminile(String singolare) {
        Genere genere = findFirstBySingolare(singolare);
        return genere.pluraleFemminile != null ? genere.pluraleFemminile : VUOTA;
    }

    public String getPlurale(String singolare) {
        Genere genere = findByKey(singolare);
        return genere != null ? getPlurale(singolare, genere.type) : VUOTA;
    }

    public String getPlurale(String singolare, AETypeGenere type) {
        Genere genere = findByKey(singolare);
        if (genere == null) {
            return VUOTA;
        }

        if (type == AETypeGenere.maschile && genere.pluraleMaschile != null) {
            return genere.pluraleMaschile;
        }

        if (type == AETypeGenere.femminile && genere.pluraleFemminile != null) {
            return genere.pluraleFemminile;
        }

        return VUOTA;
    }

    public String getPluraleParagrafo(Bio bio) {
        String attivita = bio != null ? bio.attivita : VUOTA;
        Genere genere = textService.isValid(attivita) ? findByKey(attivita) : null;
        AttSingolare entityBeanAttSingolare;

        if (genere != null) {
            return getPluraleParagrafo(bio, genere);
        }
        else {
            entityBeanAttSingolare = attSingolareBackend.findByKey(attivita);
            if (entityBeanAttSingolare != null) {
                return textService.primaMaiuscola(entityBeanAttSingolare.plurale);
            }
            else {
                return TAG_LISTA_NO_ATTIVITA;
            }
        }
    }


    public String getPluraleParagrafo(Bio bio, Genere genere) {
        String pluraleParagrafo = VUOTA;
        AETypeGenere typeBio = AETypeGenere.nessuno;
        if (genere == null) {
            return VUOTA;
        }
        pluraleParagrafo = switch (genere.type) {
            case maschile -> genere.pluraleMaschile;
            case femminile -> genere.pluraleFemminile;
            case entrambi -> VUOTA;
            case nessuno -> TAG_LISTA_NO_ATTIVITA;
        };
        if (textService.isEmpty(pluraleParagrafo) && genere.type == AETypeGenere.entrambi) {
            typeBio = bioBackend.getGenere(bio);
            pluraleParagrafo = switch (typeBio) {
                case maschile -> genere.pluraleMaschile;
                case femminile -> genere.pluraleFemminile;
                default -> TAG_LISTA_NO_ATTIVITA;
            };
        }

        return textService.isValid(pluraleParagrafo) ? textService.primaMaiuscola(pluraleParagrafo) : TAG_LISTA_NO_ATTIVITA;
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di genere
        deleteAll();

        download(PATH_MODULO_GENERE);
        //        result = downloadGenere(result);
        //        return super.fixResetDownload(result);
        return result;
    }


    /**
     * Legge la mappa di valori dal modulo di wiki <br>
     * Cancella la (eventuale) precedente lista di generi <br>
     * Elabora la mappa per creare i singoli generi <br>
     *
     * @param wikiTitle della pagina su wikipedia
     *
     * @return true se l'azione è stata eseguita
     */
    public void download(final String wikiTitle) {
        long inizio = System.currentTimeMillis();
        int size = 0;
        String singolare;
        String pluraliGrezzi;
        String pluraleMaschile;
        String pluraleFemminile;
        AETypeGenere type;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(wikiTitle);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolare = entry.getKey();
                singolare = textService.primaMinuscola(singolare);
                pluraliGrezzi = entry.getValue();

                pluraleMaschile = this.estraeMaschile(pluraliGrezzi);
                pluraleFemminile = this.estraeFemminile(pluraliGrezzi);

                if (textService.isValid(pluraleMaschile) && textService.isValid(pluraleFemminile)) {
                    type = AETypeGenere.entrambi;
                }
                else {
                    if (textService.isValid(pluraleMaschile)) {
                        type = AETypeGenere.maschile;
                    }
                    else {
                        type = AETypeGenere.femminile;
                    }
                }
                if (creaIfNotExist(singolare, type, pluraleMaschile, pluraleFemminile) != null) {
                    size++;
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", wikiTitle);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
        super.fixDownloadSecondi(inizio, wikiTitle, mappa.size(), size);
    }


    /**
     * Funziona solo per il format: {"avvocati","M", "avvocate","F"} oppure: {"avvocati","M"}
     */
    private String estraeMaschile(String testoPlurale) {
        String pluraleMaschile = VUOTA;
        String tagM = "M";
        String tagApi = "\"";

        if (testoPlurale.contains(tagM)) {
            pluraleMaschile = textService.estrae(testoPlurale, tagApi, tagApi);
        }

        return pluraleMaschile;
    }


    /**
     * Funziona solo per il format: {"avvocati","M", "avvocate","F"} oppure: {"avvocate","F"}
     */
    private String estraeFemminile(String testoPlurale) {
        String pluraleFemminile = "";
        String plurale = "";
        String tagIni = "{";
        String tagEnd = "}";
        String tagVir = ",";
        String tagUgu = "=";
        String tagApi = "";
        String tagM = "M";
        String tagF = "F";
        String[] parti;
        String tag = "F";

        // Funziona solo per il format: { "avvocati","M", "avvocate","F"}
        plurale = textService.setNoGraffe(testoPlurale);
        parti = plurale.split(tagVir);
        for (int k = 0; k < parti.length; k++) {
            parti[k] = textService.setNoDoppiApici(parti[k]);
        }

        for (int k = 0; k < parti.length; k++) {
            if (parti[k].equals(tag)) {
                if (k > 0) {
                    pluraleFemminile = parti[k - 1];
                }
            }
        }

        return pluraleFemminile;
    }

    protected Genere creaGenere(String keyCode, Object propertyValue) {
        Genere beanGenere = null;
        MongoCollection<Document> collection;
        MongoDatabase client = mongoService.getDB("wiki24");
        collection = client.getCollection("genere");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(keyCode, propertyValue);
        Document doc = collection.find(whereQuery).first();

        if (doc != null) {
            beanGenere = this.newEntity(doc);
        }

        return beanGenere;
    }

}// end of crud backend class
