package it.algos.base24.backend.service;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.*;
import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import jakarta.annotation.*;
import org.bson.*;
import org.bson.conversions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 10-Aug-2023
 * Time: 19:48
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public MongoService mongoService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class MongoService<capture> {

    @Autowired
    private TextService textService;

    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private ReflectionService reflectionService;

    @Autowired
    private FileService fileService;

    @Autowired
    private LogService logger;

    public static final String CONNECTION_STRING = "mongodb://localhost:27017/";

    public static final int STANDARD_MONGO_MAX_BYTES = 33554432;

    public static final int EXPECTED_ALGOS_MAX_BYTES = 50151432;

    public MongoOperations mongoOp;

    private String dataBaseName;

    private MongoDatabase dataBase;

    private MongoClient mongoClient;

    private MongoCollection collection;

    private List<String> collezioni;

    /**
     * Costruttore @Autowired. <br>
     */
    @Autowired
    public MongoService(MongoTemplate mongoOp, @Value("${spring.data.mongodb.database}") String dataBaseName) {
        this.mongoOp = mongoOp;
        this.dataBaseName = dataBaseName;
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    public void postConstruct() {
        fixProperties();
    }


    /**
     * Creazione iniziale di eventuali properties indispensabili per l'istanza <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Metodo private che NON può essere sovrascritto <br>
     */
    public void fixProperties() {
        MongoIterable<String> nomiCollezioni;
        dataBase = getDB(dataBaseName);
        if (dataBase != null) {
            collezioni = new ArrayList<>();
            nomiCollezioni = dataBase.listCollectionNames();
            for (String stringa : nomiCollezioni) {
                collezioni.add(stringa);
            }
        }
    }

    /**
     * Restituisce un generico database
     */
    public MongoDatabase getDB(String databaseName) {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING + databaseName);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(mongoClientSettings);

        return mongoClient.getDatabase(databaseName);
    }


    /**
     * Restituisce il database 'admin' di servizio, sempre presente in MongoDB
     */
    public MongoDatabase getDBAdmin() {
        return getDB(BaseCost.DATABASE_ADMIN);
    }

    /**
     * Collezioni esistenti nel database <br>
     *
     * @param dataBase da esaminare
     */
    public List<String> listCollectionNames(MongoDatabase dataBase) {
        List<String> lista = new ArrayList<>();

        MongoIterable iterable = dataBase.listCollectionNames();
        MongoCursor iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Object document = iterator.next();
            lista.add(document.toString());
        }
        return lista;
    }

    /**
     * Versione corrente del database mongoDB <br>
     */
    public String versione() {
        String versione;
        List results = new ArrayList<>();
        Document query;
        Document doc;
        MongoDatabase dataBase = getDBAdmin();
        collection = dataBase.getCollection("system.version");

        if (collection == null) {
            return "Collection non trovata";
        }

        query = new Document("_id", "featureCompatibilityVersion");
        collection.find(query).into(results);
        doc = (Document) results.get(0);
        versione = (String) doc.get("version");

        return versione;
    }


    /**
     * Recupera il valore di un parametro
     *
     * @param parameterName
     *
     * @return value of parameter
     */
    public Object getParameter(String parameterName) {
        Map<String, Object> mappa;
        MongoDatabase dbAdmin = getDBAdmin();
        Document param;

        mappa = new LinkedHashMap<>();
        mappa.put("getParameter", 1);
        mappa.put(parameterName, 1);

        param = dbAdmin.runCommand(new Document(mappa));

        return param.get(parameterName);
    }

    /**
     * Regola il valore di un parametro
     *
     * @param parameterName
     * @param valueToSet    valore da regolare
     *
     * @return true se il valore è stato acquisito dal database
     */
    public boolean setParameter(String parameterName, Object valueToSet) {
        Map<String, Object> mappa;
        MongoDatabase dbAdmin = getDBAdmin();
        Document param;

        mappa = new LinkedHashMap<>();
        mappa.put("setParameter", 1);
        mappa.put(parameterName, valueToSet);

        param = dbAdmin.runCommand(new Document(mappa));
        return (double) param.get("ok") == 1;
    }

    /**
     * Regola il valore del parametro internalQueryExecMaxBlockingSortBytes
     *
     * @param maxBytes da regolare
     *
     * @return true se il valore è stato acquisito dal database
     */
    public boolean setMaxBlockingSortBytes(int maxBytes) {
        return setParameter("internalQueryExecMaxBlockingSortBytes", maxBytes);
    }

    /**
     * Recupera il valore del parametro internalQueryExecMaxBlockingSortBytes
     *
     * @return value of parameter
     */
    public int getMaxBlockingSortBytes() {
        int numBytes;
        String value = "";
        String message;
        //        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));

        numBytes = (int) getParameter("internalQueryExecMaxBlockingSortBytes");
        //        value = textService.format(numBytes);

        //        if (numBytes == STANDARD_MONGO_MAX_BYTES) {
        //            message = String.format("Algos - mongoDB. La variabile internalQueryExecMaxBlockingSortBytes è regolata col valore standard iniziale " +
        //                    "settato da mongoDB: %s", value);
        //            logService.info(new WrapLog().message(message).type(AETypeLog.setup));
        //        }
        //        else {
        //            if (numBytes == EXPECTED_ALGOS_MAX_BYTES) {
        //                message = String.format("Algos - mongoDB. La variabile internalQueryExecMaxBlockingSortBytes è regolata col valore " +
        //                        "richiesto da Algos: %s", value);
        //                logService.info(new WrapLog().message(message).type(AETypeLog.setup));
        //            }
        //            else {
        //                message = String.format("Algos - mongoDB. La variabile internalQueryExecMaxBlockingSortBytes è regolata a cazzo: ", value);
        //                logService.info(new WrapLog().message(message).type(AETypeLog.setup));
        //            }
        //        }
        //
        //        logService.info(new WrapLog().message(VUOTA).type(AETypeLog.setup));
        return numBytes;
    }

    /**
     * Check the existence of a collection. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection exist
     */
    public boolean existsCollectionName(final String collectionName) {
        return textService.isValid(collectionName) ? mongoOp.collectionExists(collectionName) : false;
    }


    /**
     * Check the existence of a collection. <br>
     *
     * @param entityClazz the class of type AlgosModel
     *
     * @return true if the collection exist
     */
    public boolean existsCollectionClazz(final Class entityClazz) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        return textService.isValid(collectionName) ? mongoOp.collectionExists(collectionName) : false;
    }

    /**
     * Controlla che la collezione sia vuota. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection is null or empty
     */
    public boolean collectionNullOrEmpty(final String collectionName) {
        if (existsCollectionName(collectionName)) {
            return count(collectionName) == 0;
        }
        else {
            return true;
        }
    }


    /**
     * Controlla che la collezione sia vuota. <br>
     *
     * @param entityClazz the class of type AlgosModel
     *
     * @return true if the collection is null or empty
     */
    public boolean collectionNullOrEmpty(final Class entityClazz) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        return collectionNullOrEmpty(collectionName);
    }

    /**
     * Controlla che la collezione sia valida (esiste con valori). <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection as one or more entities
     */
    public boolean collectionValida(final String collectionName) {
        if (existsCollectionName(collectionName)) {
            return count(collectionName) > 0;
        }
        else {
            return false;
        }
    }


    /**
     * Conteggio delle entities di una collection <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return numero di entities totali
     */
    public int count(final String collectionName) {
        Long entities;
        Query query = new Query();
        entities = mongoOp.count(query, collectionName);
        return entities > 0 ? entities.intValue() : 0;
    }

    public boolean exists(final Class entityClazz, final String keyPropertyName, final Object keyPropertyValue) {
        String collectionName;
        Query query = new Query();

        // Controlla che i parametri in ingresso siano validi
        if (!check(entityClazz, keyPropertyName, keyPropertyValue, "exists")) {
            return false;
        }
        collectionName = annotationService.getCollectionName(entityClazz);

        query.addCriteria(Criteria.where(keyPropertyName).is(keyPropertyValue));
        return this.mongoOp.exists(query, collectionName);
    }

    public int count(final Class entityClazz) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        if (textService.isValid(collectionName)) {
            return ((Long) this.mongoOp.count(new Query(), entityClazz, collectionName)).intValue();
        }
        else {
            return 0;
        }
    }

    public AbstractEntity findOneById(final Class entityClazz, final String idValue) {
        Query query = new Query();
        String collectionName = annotationService.getCollectionName(entityClazz);

        // Controlla che i parametri in ingresso siano validi
        if (!check(entityClazz, idValue, "findOneById")) {
            return null;
        }

        query.addCriteria(Criteria.where(BaseCost.FIELD_NAME_ID_CON).is(idValue));
        return (AbstractEntity) this.mongoOp.findOne(query, entityClazz, collectionName);
    }


    public AbstractEntity findOneByKey(final Class entityClazz, final Object keyPropertyValue) {
        //        Query query = new Query();
        //        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        //
        //        // Controlla che i parametri in ingresso siano validi
        //        if (!check(entityClazz, keyPropertyName, keyPropertyValue, "findOneByKey")) {
        //            return null;
        //        }
        //
        //        query.addCriteria(Criteria.where(keyPropertyName).is(keyPropertyValue));
        //        return (AbstractEntity) this.mongoOp.findOne(query, entityClazz, collectionName);
        return findOneByProperty(entityClazz, keyPropertyName, keyPropertyValue);
    }

    public AbstractEntity findOneByProperty(final Class entityClazz, final String keyPropertyName, final Object keyPropertyValue) {
        Query query = new Query();
        String collectionName = annotationService.getCollectionName(entityClazz);

        // Controlla che i parametri in ingresso siano validi
        if (!check(entityClazz, keyPropertyName, keyPropertyValue, "findOneByProperty")) {
            return null;
        }

        query.addCriteria(Criteria.where(keyPropertyName).is(keyPropertyValue));
        return (AbstractEntity) this.mongoOp.findOne(query, entityClazz, collectionName);
    }

    public List<AbstractEntity> findAll(final Class entityClazz) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        return this.mongoOp.find(new Query(), entityClazz, collectionName);
    }

    public List<AbstractEntity> findAll(final Class entityClazz, Sort sort) {
        Query query = new Query();
        query.with(sort);
        String collectionName = annotationService.getCollectionName(entityClazz);
        return this.mongoOp.find(query, entityClazz, collectionName);
    }

    public List<AbstractEntity> findSkip(final Class entityClazz, int offset, int limit) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        return this.mongoOp.find(query, entityClazz, collectionName);
    }

    /**
     * Query find()
     */
    public Stream<AbstractEntity> findStream(Query query, Class<AbstractEntity> entityClazz) {
        List<AbstractEntity> lista;
        String collectionName = annotationService.getCollectionName(entityClazz);

        lista = this.mongoOp.find(query, entityClazz, collectionName);
        return lista.stream();
    }

    public List<AbstractEntity> find(Query query, Class entityClazz) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        return this.mongoOp.find(query, entityClazz, collectionName);
    }

    public List<Long> projectionLong(Class<? extends AbstractEntity> entityClazz, String property) {
        List<Long> listaProperty = new ArrayList();
        String message;
        String collectionName = annotationService.getCollectionName(entityClazz);
        collection = getCollection(collectionName);

        if (collection == null) {
            message = String.format("Non esiste la collection", entityClazz.getSimpleName());
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return null;
        }

        Bson bSort = Sorts.ascending(property).toBsonDocument();
        Bson projection = Projections.fields(Projections.include(property), Projections.excludeId());
        FindIterable<Document> documents = collection.find().projection(projection).sort(bSort);

        for (var singolo : documents) {
            listaProperty.add(singolo.get(property, Long.class));
        }
        return listaProperty;
    }

    /**
     * Query count()
     */
    public int count(Query query, Class<AbstractEntity> modelClazz) {
        Long lungo;
        String collectionName = annotationService.getCollectionName(modelClazz);

        lungo = this.mongoOp.count(query, modelClazz, collectionName);
        return lungo > 0 ? lungo.intValue() : 0;
    }

    public boolean existById(final Class modelClazz, final Object keyPropertyValue) {
        return exists(modelClazz, BaseCost.FIELD_NAME_ID_CON, keyPropertyValue);
    }

    public boolean existByKey(final Class modelClazz, final Object keyPropertyValue) {
        String keyPropertyName = annotationService.getKeyPropertyName(modelClazz);
        return textService.isValid(keyPropertyName) ? exists(modelClazz, keyPropertyName, keyPropertyValue) : false;
    }


    public AbstractEntity insert(final AbstractEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityBean != null ? entityBean.getClass() : null);
        return textService.isValid(collectionName) ? mongoOp.insert(entityBean, collectionName) : null;
    }

    public AbstractEntity save(final AbstractEntity entityBean) {
        if (existById(entityBean.getClass(), entityBean.getId())) {
            delete(entityBean);
        }
        return insert(entityBean);
    }

    public DeleteResult delete(final AbstractEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityBean.getClass());
        return textService.isValid(collectionName) ? mongoOp.remove(entityBean, collectionName) : null;
    }

    public boolean deleteAll(final String collectionName) {
        if (existsCollectionName(collectionName)) {
            mongoOp.dropCollection(collectionName);
        }
        return !existsCollectionName(collectionName);
    }

    public boolean deleteAll(final Class clazz) {
        if (existsCollectionClazz(clazz)) {
            mongoOp.dropCollection(clazz);
        }

        return !existsCollectionClazz(clazz);
    }

    /**
     * Regola il valore iniziale del parametro internalQueryExecMaxBlockingSortBytes
     */
    public void fixMaxBytes() {
        this.setMaxBlockingSortBytes(EXPECTED_ALGOS_MAX_BYTES);
    }

    public List<String> getCollezioni() {
        return collezioni;
    }


    /**
     * Check the existence of a collection. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection exist
     */
    public boolean isExistsCollection(final String collectionName) {
        if (textService.isEmpty(collectionName)) {
            logger.info(new WrapLog().exception(new AlgosException("Manca il nome della collection")).usaDb());
        }
        String shortName = fileService.estraeClasseFinale(collectionName);
        shortName = textService.primaMinuscola(shortName);

        return mongoOp.collectionExists(shortName);
    }

    /**
     * Collection del database. <br>
     *
     * @param collectionName The name of the collection or view
     *
     * @return collection if exist
     */
    public MongoCollection<Document> getCollection(final String collectionName) {
        if (textService.isEmpty(collectionName)) {
            return null;
        }
        String shortName = fileService.estraeClasseFinale(collectionName);
        shortName = textService.primaMinuscola(shortName);

        if (textService.isValid(shortName)) {
            if (isExistsCollection(shortName)) {
                return dataBase != null ? dataBase.getCollection(shortName) : null;
            }
            return null;
        }
        else {
            return null;
        }
    }

    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param methodName   the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean check(final Class genericClazz, final String methodName) {
        String message;
        Class entityClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AlgosModel", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        return true;
    }


    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param idValue      da controllare
     * @param methodName   the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean check(final Class genericClazz, final String idValue, final String methodName) {
        String message;
        Class entityClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AlgosModel", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(idValue)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", methodName, this.getClass().getSimpleName(), "keyPropertyName");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        return true;
    }

    /**
     * Check generico dei parametri.
     *
     * @param genericClazz     da controllare
     * @param keyPropertyName  da controllare
     * @param keyPropertyValue da controllare
     * @param methodName       the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean check(final Class genericClazz, final String keyPropertyName, final Object keyPropertyValue, final String methodName) {
        String message;
        Class entityClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(new WrapLog().message(message));
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AlgosModel", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            logger.warn(new WrapLog().message(message));
            return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(keyPropertyName)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", methodName, this.getClass().getSimpleName(), "keyPropertyName");
            logger.warn(new WrapLog().message(message));
            return false;
        }

        // Controlla che la property esista nella classe
        if (reflectionService.nonEsiste(entityClazz, keyPropertyName)) {
            message = String.format("Nel metodo '%s' di [%s], non esiste la property '%s' della classe [%s]", methodName, this.getClass().getSimpleName(), keyPropertyName, entityClazz.getSimpleName());
            logger.warn(new WrapLog().message(message));
            return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (keyPropertyValue == null) {
            message = String.format("Nel metodo '%s' di [%s], manca il valore del parametro '%s'", methodName, this.getClass().getSimpleName(), keyPropertyName);
            logger.warn(new WrapLog().message(message));
            return false;
        }

        // Controlla che il parametro in ingresso non sia vuoto
        if (keyPropertyValue instanceof String keyPropertyValueString) {
            if (textService.isEmpty(keyPropertyValueString)) {
                message = String.format("Nel metodo '%s' di [%s], il parametro '%s' (di tipo String) è vuoto (senza valore)", methodName, this.getClass().getSimpleName(), keyPropertyName);
                logger.warn(new WrapLog().message(message));
                return false;
            }
        }

        return true;
    }

}