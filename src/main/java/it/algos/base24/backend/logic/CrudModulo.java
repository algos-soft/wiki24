package it.algos.base24.backend.logic;

import com.mongodb.client.result.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.dialog.*;
import it.algos.base24.ui.form.*;
import it.algos.base24.ui.view.*;
import jakarta.annotation.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 02-Aug-2023
 * Time: 15:05
 */
public abstract class CrudModulo {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    @Autowired
    public ReflectionService reflectionService;

    @Autowired
    public AnnotationService annotationService;

    @Autowired
    public ResourceService resourceService;

    @Autowired
    public TextService textService;

    @Autowired
    public MongoService mongoService;

    @Autowired
    public LogService logger;

    @Autowired
    public WebService webService;

    @Autowired
    public JSonService jSonService;

    @Autowired
    public HtmlService htmlService;

    public Class currentCrudEntityClazz;

    protected Class currentCrudListClazz;

    protected Class currentCrudFormClazz;

    protected String currentCollectionName;

    public List<String> propertyFormNames;

    //    protected CrudOperation operation;

    protected Map<String, AbstractEntity> mappaBeans;

    /**
     * Regola la entityClazz associata a questo Modulo <br>
     */
    public CrudModulo(Class entityClazz) {
        this(entityClazz, DefaultList.class);
    }


    /**
     * Regola la modelClazz associata a questo Modulo <br>
     * Regola la listClazz associata a questo Modulo <br>
     */
    public CrudModulo(Class entityClazz, Class listClazz) {
        //        this(entityClazz, listClazz, DefaultForm.class);
        // @todo ATTENTION QUI
        this(entityClazz, listClazz, null);
    }

    /**
     * Regola la modelClazz associata a questo Modulo <br>
     * Regola la listClazz associata a questo Modulo <br>
     * Regola la formClazz associata a questo Modulo <br>
     */
    public CrudModulo(Class entityClazz, Class listClazz, Class formClazz) {
        this.currentCrudEntityClazz = entityClazz;
        this.currentCrudListClazz = listClazz;
        this.currentCrudFormClazz = formClazz;
    }


    @PostConstruct
    public void postConstruct() {
        this.fixPreferenze();
        this.currentCollectionName = currentCrudEntityClazz != null ? annotationService.getCollectionName(currentCrudEntityClazz) : VUOTA;
        this.checkReset();
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.propertyFormNames = this.getPropertyNames();
        this.mappaBeans = new LinkedHashMap<>();
    }


    protected void checkReset() {
        String message;
        String nomeMetodo = "resetStartup";
        Class clazz = this.getClass();

        //        if (currentCrudEntityClazz != null && annotationService.usaStartupReset(currentCrudEntityClazz)) {
        //            if (reflectionService.isEsisteMetodo(clazz, nomeMetodo)) {
        //                this.resetStartup();
        //            }
        //            else {
        //                message = String.format("La POJO %s ha il flag usaStartupReset=true ma manca il metodo %s() nella classe %s", currentCrudEntityClazz.getSimpleName(), nomeMetodo, clazz.getSimpleName());
        //                logger.warn(new WrapLog().message(message).type(TypeLog.startup));
        //            }
        //        }

        if (currentCrudEntityClazz != null && annotationService.usaStartupReset(currentCrudEntityClazz)) {
            this.resetStartup();
        }

    }


    /**
     * Crea una Lista/Grid <br>
     * Tipicamente invocato da CrudView, ma (eventualmente) anche da altre classi <br>
     */
    public CrudList creaList() {
        if (currentCrudListClazz != null) {
            return (CrudList) appContext.getBean(currentCrudListClazz, this);
        }
        else {
            Notifica.message(String.format("Manca la %s", "currentCrudListClazz"));
            return null;
        }
    }

    /**
     * Crea un Form/Scheda <br>
     */
    public CrudForm creaForm() {
        return creaForm(newEntity(), CrudOperation.add);
    }


    /**
     * Crea un Form/Scheda <br>
     */
    public CrudForm creaForm(AbstractEntity entityBean, CrudOperation operation) {
        if (currentCrudFormClazz != null) {
            return ((CrudForm) appContext.getBean(currentCrudFormClazz, this, entityBean, operation));
        }
        else {
            Notifica.message(String.format("Manca la %s", "currentCrudFormClazz"));
            return null;
        }
    }


    public boolean collectionNullOrEmpty() {
        return mongoService.collectionNullOrEmpty(currentCrudEntityClazz);
    }


    public AbstractEntity insertSave(AbstractEntity newBean) {
        if (newBean == null) {
            return newBean;
        }
        if (textService.isEmpty(newBean.id)) {
            return newBean;
        }

        return insertSave(newBean, findOneById(newBean.id));
    }

    public AbstractEntity insertSave(AbstractEntity newBean, AbstractEntity oldBean) {
        if (oldBean == null) {
            return insert(newBean);
        }
        else {
            if (oldBean.equals(newBean)) {
                return newBean;
            }
            else {
                return save(newBean);
            }
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AbstractEntity newEntity() {
        return null;
    }

    public int count() {
        if (currentCrudEntityClazz == null) {
            logger.warn("Errore");
            return 0;
        }

        return mongoService.count(currentCrudEntityClazz);
    }


    public boolean existById(final String idValue) {
        return mongoService.existById(currentCrudEntityClazz, idValue);
    }

    public boolean existByKey(final String keyPropertyValue) {
        return mongoService.existByKey(currentCrudEntityClazz, keyPropertyValue);
    }

    public boolean notExistById(final String idValue) {
        return !existById(idValue);
    }

    public boolean notExistByKey(final String keyPropertyValue) {
        return !existByKey(keyPropertyValue);
    }

    public List findAll() {
        String sortPropertyName = annotationService.getSortPropertyName(currentCrudEntityClazz);
        Sort sort = null;

        if (textService.isValid(sortPropertyName)) {
            sort = Sort.by(Sort.Direction.ASC, sortPropertyName);
        }

        if (sort != null) {
            return mongoService.findAll(currentCrudEntityClazz, sort);
        }
        else {
            return mongoService.findAll(currentCrudEntityClazz);
        }
    }


    public AbstractEntity insert(AbstractEntity entityBean) {
        String entityName = textService.primaMaiuscola(currentCollectionName);
        String modifiche;
        String message;
        AbstractEntity savedEntityBean;
        entityBean = fixKey(entityBean);
        savedEntityBean = mongoService.insert(entityBean);

        if (entityBean != null) {
            modifiche = jSonService.getProperties(entityBean);
            message = String.format("Creata una nuova entity [%s.%s]", entityName, savedEntityBean.getId());
            message = String.format("%s%s%s", message, FORWARD, modifiche);
            logger.wrap(WrapLog.crea(message).success().type(TypeLog.mongo).usaDb());
        }
        else {
            message = String.format("Non sono riuscito a creare la entity [%s.%s]", entityName, savedEntityBean.getId());
            logger.wrap(WrapLog.crea(message).error().errorNotifica().type(TypeLog.mongo).usaDb());
        }

        return entityBean;
    }

    public AbstractEntity save(AbstractEntity modifiedEntityBean) {
        AbstractEntity oldEntityBean;
        String entityName = textService.primaMaiuscola(currentCollectionName);
        String modifiche;
        String message;
        AbstractEntity savedEntityBean;

        if (modifiedEntityBean == null) {
            message = "Tentativo di 'salvare' una entity non esistente";
            Notifica.message(message).error().open();
            logger.warn(message);
            return null;
        }
        modifiedEntityBean = fixKey(modifiedEntityBean);
        oldEntityBean = findOneById(modifiedEntityBean.getId());

        if (oldEntityBean == null) {
            Notifica.message(String.format("Non esiste la entity [%s.%s]", entityName, modifiedEntityBean.getId())).error().open();
            message = String.format("Devi usare 'insert' al posto di 'save'");
            logger.warn(message);
            return null;
        }

        savedEntityBean = mongoService.save(modifiedEntityBean);

        if (savedEntityBean != null) {
            modifiche = jSonService.getModifiche(oldEntityBean, savedEntityBean);
            message = String.format("Modificata la entity [%s.%s]", entityName, savedEntityBean.getId());
            message = String.format("%s%s%s", message, FORWARD, modifiche);
            logger.wrap(WrapLog.crea(message).success().type(TypeLog.mongo).usaDb());
        }
        else {
            message = String.format("Non sono riuscito a modificare la entity [%s.%s]", entityName, savedEntityBean.getId());
            logger.wrap(WrapLog.crea(message).error().errorNotifica().type(TypeLog.mongo).usaDb());
        }

        return savedEntityBean;
    }

    public boolean delete(AbstractEntity entityBean) {
        boolean cancellato = false;
        String message;
        String modifiche;
        DeleteResult result = mongoService.delete(entityBean);
        String entityName = textService.primaMaiuscola(currentCollectionName);

        if (result != null) {
            cancellato = result.getDeletedCount() == 1;
        }

        if (cancellato) {
            message = String.format("Cancellata la entity [%s.%s]", entityName, entityBean);
            modifiche = jSonService.getProperties(entityBean);
            message = String.format("%s%s%s", message, FORWARD, modifiche);
            logger.wrap(WrapLog.crea(message).success().type(TypeLog.mongo).usaDb());
        }
        else {
            message = String.format("Non sono riuscito a cancellare la entity [%s.%s]", entityName, entityBean);
            logger.wrap(WrapLog.crea(message).error().errorNotifica());
        }

        return cancellato;
    }


    public RisultatoDelete deleteAll() {
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);

        if (mongoService.collectionNullOrEmpty(collectionName)) {
            return RisultatoDelete.empty;
        }
        else {
            mongoService.mongoOp.dropCollection(collectionName);
            if (count() == 0) {
                return RisultatoDelete.eseguito;
            }
            else {
                return RisultatoDelete.errore;
            }
        }
    }

    public AbstractEntity findOneById(String idValue) {
        return mongoService.findOneById(currentCrudEntityClazz, idValue);
    }

    public AbstractEntity findOneByKey(Object keyPropertyValue) {
        return mongoService.findOneByKey(currentCrudEntityClazz, keyPropertyValue);
    }

    public AbstractEntity findOneByProperty(String keyPropertyName, Object keyPropertyValue) {
        return mongoService.findOneByProperty(currentCrudEntityClazz, keyPropertyName, keyPropertyValue);
    }

    public boolean deleteById(String idValue) {
        AbstractEntity entityBean = findOneById(idValue);
        return delete(entityBean);
    }

    public boolean deleteByKey(Object keyPropertyValue) {
        AbstractEntity entityBean = findOneByKey(keyPropertyValue);
        return delete(entityBean);
    }


    public RisultatoReset resetStartup() {
        String message;
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);
        int elementi = count();
        RisultatoReset typeReset = RisultatoReset.nessuno;

        if (reflectionService.isEsisteMetodo(getClass(), METHOD_RESET_DELETE)) {
            if (collectionNullOrEmpty()) {

                typeReset = resetDelete();
                if (typeReset == RisultatoReset.esistenteIntegrato) {
                    typeReset = RisultatoReset.vuotoMaCostruito;
                }

                return typeReset;
            }
            else {
                message = String.format("La collection [%s] (usaStartupReset=true) è stata controllata. Esistevano già %d elementi.", collectionName, elementi);
                logger.info(new WrapLog().message(message).type(TypeLog.startup));
                typeReset = RisultatoReset.esistenteNonModificato;
            }
        }
        else {
            message = String.format("La POJO [%s] ha il flag usaStartupReset=true ma manca il metodo %s() nella classe %s", currentCrudEntityClazz.getSimpleName(), METHOD_RESET_DELETE, getClass().getSimpleName());
            logger.warn(new WrapLog().message(message).type(TypeLog.startup));
        }

        return typeReset;
    }

    public void dialogResetDelete() {
        DialogDelete.reset(this::resetDelete);
    }

    public RisultatoReset resetDelete() {
        return RisultatoReset.getDelete(deleteAll());
    }

//    public void dialogResetAdd() {
//        DialogResetAdd.reset(this::resetAdd);
//    }

    public RisultatoReset resetAdd() {
        return collectionNullOrEmpty() ? RisultatoReset.vuotoIntegrato : RisultatoReset.esistenteIntegrato;
    }

    public void download() {
    }

    public int nextOrdine() {
        int nextOrdine = 1;
        AbstractEntity entityBean = null;
        List<AbstractEntity> lista = null;
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);
        Sort sort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
        Query query = new Query();
        query.addCriteria(Criteria.where(FIELD_NAME_ORDINE).exists(true));
        query.with(sort);

        if (textService.isValid(collectionName)) {
            lista = (List<AbstractEntity>) mongoService.mongoOp.find(query, currentCrudEntityClazz, collectionName);
        }
        if (lista != null && lista.size() > 0) {
            entityBean = lista.get(0);
        }
        if (entityBean != null) {
            Object obj = reflectionService.getPropertyValue(entityBean, FIELD_NAME_ORDINE);
            if (obj instanceof Integer oldOrdine) {
                nextOrdine = oldOrdine + 1;
            }
        }

        return nextOrdine;
    }


    /**
     * Regola ObjectId(). <br>
     * Controlla che esista. <br>
     * Se manca e se esiste il valore del campo keyPropertyName, usa quello. <br>
     *
     * @param newEntityBean to be checked
     *
     * @return the checked entity
     */
    public AbstractEntity fixKey(AbstractEntity newEntityBean) {
        String keyPropertyValue;
        String keyPropertyName;

        if (textService.isValid(newEntityBean.getId())) {
            return newEntityBean;
        }

        keyPropertyName = annotationService.getKeyPropertyName(currentCrudEntityClazz);
        if (textService.isValid(keyPropertyName) && !keyPropertyName.equals(FIELD_NAME_ID_CON)) {
            keyPropertyValue = (String) reflectionService.getPropertyValue(newEntityBean, keyPropertyName);
            if (textService.isValid(keyPropertyValue)) {
                if (annotationService.usaIdPrimaMinuscola(currentCrudEntityClazz)) {
                    keyPropertyValue = textService.primaMinuscola(keyPropertyValue);
                }
                newEntityBean.setId(keyPropertyValue);
            }
        }
        else {
            newEntityBean.setId(new ObjectId().toString());
        }

        return newEntityBean;
    }

    public Sort.Order getBasicSortOrder() {
        Sort.Order sortOrder = null;
        String sortPropertyName;
        String keyPropertyName;

        if (reflectionService.isEsiste(currentCrudEntityClazz, FIELD_NAME_ORDINE)) {
            sortOrder = Sort.Order.asc(FIELD_NAME_ORDINE);
        }
        else {
            sortPropertyName = annotationService.getSortPropertyName(currentCrudEntityClazz);
            if (textService.isValid(sortPropertyName)) {
                sortOrder = Sort.Order.asc(sortPropertyName);
            }
            else {
                keyPropertyName = annotationService.getKeyPropertyName(currentCrudEntityClazz);
                if (textService.isValid(keyPropertyName)) {
                    sortOrder = Sort.Order.asc(keyPropertyName);
                }
            }
        }

        return sortOrder;
    }

    public boolean resetEffettivo() {
        return false;
    }


    public Class getCurrentCrudEntityClazz() {
        return currentCrudEntityClazz;
    }

    public void setCurrentCrudEntityClazz(Class currentModelClazz) {
        this.currentCrudEntityClazz = currentModelClazz;
    }

    /**
     * Regola le property di una ModelClazz <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     */
    public List<String> getPropertyNames() {
        List<String> allPropertyNames = new ArrayList<>();

        Field[] fields = currentCrudEntityClazz != null ? currentCrudEntityClazz.getDeclaredFields() : null;
        if (fields != null) {
            for (Field field : fields) {
                allPropertyNames.add(field.getName());
            }
        }

        return allPropertyNames;
    }

}
