package it.algos.base24.backend.service;

import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.form.*;
import it.algos.base24.ui.view.*;
import org.reflections.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 17:25
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public ReflectionService reflectionService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ReflectionService {

    @Autowired
    private TextService textService;

    @Autowired
    LogService logger;

    /**
     * Field statico di una classe generica. <br>
     *
     * @param genericClazz       da cui estrarre il field statico
     * @param publicPropertyName property statica e pubblica
     *
     * @return the field
     */
    public Field getJavaField(final Class genericClazz, final String publicPropertyName) {
        Field field = null;
        String propertyName = publicPropertyName;
        String message;

        if (genericClazz == null) {
            return null;
        }

        if (textService.isEmpty(publicPropertyName)) {
            return null;
        }

        try {
            propertyName = propertyName.replaceAll(BaseCost.FIELD_NAME_ID_CON, BaseCost.FIELD_NAME_ID_SENZA);
            field = genericClazz.getField(propertyName);
        } catch (Exception unErrore) {
            message = String.format("Manca la property '%s' nella classe [%s] (forse potresti aggiungerla)", propertyName, genericClazz.getSimpleName());
            logger.debug(message);
        }

        return field;
    }


    /**
     * Esistenza di un field statico di una classe generica. <br>
     *
     * @param genericClazz       da cui estrarre il field statico
     * @param publicPropertyName property statica e pubblica
     *
     * @return the field exist
     */
    public boolean isEsiste(final Class<?> genericClazz, final String publicPropertyName) {
        String message;

        // Controlla che il parametro in ingresso non sia nullo
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la classe '%s'", "isEsiste()", this.getClass().getSimpleName(), "(null)");
            logger.warn(message);
            return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(publicPropertyName)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", "isEsiste()", this.getClass().getSimpleName(), "publicPropertyName");
            logger.warn(message);
            return false;
        }

        return getJavaField(genericClazz, publicPropertyName) != null;
    }

    /**
     * Mancanza di un field statico di una classe generica. <br>
     *
     * @param genericClazz       da cui estrarre il field statico
     * @param publicPropertyName property statica e pubblica
     *
     * @return field not exist
     */
    public boolean nonEsiste(final Class<?> genericClazz, final String publicPropertyName) {
        String message;

        // Controlla che il parametro in ingresso non sia nullo
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la classe '%s'", "nonEsiste()", this.getClass().getSimpleName(), "(null)");
            logger.warn(message);
            return true;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(publicPropertyName)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", "nonEsiste()", this.getClass().getSimpleName(), "publicPropertyName");
            logger.warn(message);
            return true;
        }

        return getJavaField(genericClazz, publicPropertyName) == null;
    }

    // @todo ATTENTION QUI

    /**
     * Valore della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the property value
     */
    public Object getPropertyValue(final Object entityBean, final String publicFieldName) {
        Object value = null;
        //        List<Field> fieldsList = null;
        Field[] fieldsArray;

        if (entityBean == null || textService.isEmpty(publicFieldName)) {
            return null;
        }

        //        fieldsList = getAllSuperClassFields(entityBean.getClass());
        fieldsArray = entityBean.getClass().getFields();

        try {
            for (Field field : fieldsArray) {
                if (field.getName().equals(publicFieldName)) {
                    field.setAccessible(true);
                    value = field.get(entityBean);
                }
            }
        } catch (Exception unErrore) {
            //            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        return value;
    }


    /**
     * Valore stringa della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the string value
     */
    public String getPropertyValueStr(final Object entityBean, final String publicFieldName) {
        String value = VUOTA;
        Object objValue = getPropertyValue(entityBean, publicFieldName);

        if (objValue != null) {
            if (objValue instanceof String) {
                value = (String) objValue;
            }
            else {
                value = objValue.toString();
            }
        }

        return value;
    }

    public List<Method> getMethods(final Class clazz) {
        return clazz != null ? Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()) : null;
    }

    public List<String> getMethodNames(final Class clazz) {
        List<Method> methodsArray = getMethods(clazz);
        return methodsArray != null ? methodsArray.stream().map(method -> method.getName()).collect(Collectors.toList()) : null;
    }

    public boolean isEsisteMetodo(final Class clazz, String methodNameDirectClazz) {
        List<String> nomiMetodi = getMethodNames(clazz);
        return nomiMetodi != null ? nomiMetodi.contains(methodNameDirectClazz) : false;
    }

    //        public boolean isEsisteMetodo(String publicClassName, String publicMethodName) {
    //            Class clazz = null;
    //
    //            if (textService.isEmpty(publicClassName) || textService.isEmpty(publicMethodName)) {
    //                return false;
    //            }
    //            publicClassName = textService.slashToPoint(publicClassName);
    //            clazz = classService.getClazzFromName(publicClassName);
    //
    //            return clazz != null && isEsisteMetodo(clazz, publicMethodName);
    //        }

    public List<Class> getSubClazz(Class clazz) {
        return getSubClazz(clazz, PATH_ALGOS);
    }

    public List<Class> getSubClazz(Class clazz, String path) {
        Set<Class> classes;
        String message;
        Reflections reflections;

        if (clazz == null) {
            message = String.format("Manca la clazz");
            logger.warn(new WrapLog().exception(new ArithmeticException(message)));
            return null;
        }
        if (textService.isEmpty(path)) {
            message = String.format("Manca il path");
            logger.warn(new WrapLog().exception(new ArithmeticException(message)));
            return null;
        }
        reflections = new Reflections(path);

        classes = reflections.getSubTypesOf(clazz);
        return classes != null && classes.size() > 0 ? classes.stream().collect(Collectors.toList()) : null;
    }

    public List<Class> getSubClazzEntity() {
        return getSubClazz(AbstractEntity.class, PATH_ALGOS);
    }

    public List<Class> getSubClazzViewBase() {
        return getSubClazz(CrudView.class, BaseVar.pathModuloBase);
    }
    public List<Class> getSubClazzViewProgetto() {
        return getSubClazz(CrudView.class, BaseVar.pathModuloProgetto);
    }

    public List<Class> getSubClazzModulo() {
        return getSubClazz(CrudModulo.class, PATH_ALGOS);
    }

    public List<Class> getSubClazzList() {
        return getSubClazz(CrudList.class, PATH_ALGOS);
    }

    public List<Class> getSubClazzForm() {
        return getSubClazz(CrudForm.class, PATH_ALGOS);
    }


    public List<Class> getSuperClazz(Class clazz) {
        List<Class> classes = null;
        String message;
        Class superClazz;

        if (clazz == null) {
            message = String.format("Manca la clazz");
            logger.warn(new WrapLog().exception(new ArithmeticException(message)));
            return null;
        }

        superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            classes = new ArrayList<>();
            classes.add(clazz);
            do {
                classes.add(superClazz);
                superClazz = superClazz.getSuperclass();
            } while (superClazz != null);
        }

        return classes;
    }

    public List<Field> getFields(final Class genericClazz) {
        List<Field> fieldsArray = null;
        Class entityClazz;
        String message;

        // Controlla che la classe in ingresso implementi AbstractEntity
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("La classe [%s] non implementa AbstractEntity", genericClazz.getSimpleName());
            logger.warn(message);
            return fieldsArray;
        }

        return Arrays.stream(entityClazz.getFields()).toList();
    }


    public List<String> getFieldNames(final Class clazz) {
        List<Field> fieldsArray = getFields(clazz);
        return fieldsArray != null ? fieldsArray.stream().map(field -> field.getName()).collect(Collectors.toList()) : null;
    }

}