package it.algos.base24.backend.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 27-May-2023
 * Time: 19:54
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public FieldService fieldService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
public class FieldService {

    @Autowired
    ApplicationContext applicationContext;

//    @Autowired
//    PreferenzaModulo preferenzaModulo;
    // @todo ATTENTION QUI

    @Autowired
    private ReflectionService reflectionService;

    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private MongoService mongoService;

    @Autowired
    private LogService logger;


    public Map<String, AbstractField> creaAll(Class entityClazz, List<String> propertyNamesList) {
        Map<String, AbstractField> mappaFields = new LinkedHashMap<>();
        AbstractField singoloField;

        if (propertyNamesList != null) {
            for (String key : propertyNamesList) {
                singoloField = crea(entityClazz, key);
                if (singoloField != null) {
                    mappaFields.put(key, singoloField);
                }
            }
        }

        return mappaFields;
    }


    /**
     * Create a single field.
     *
     * @param modelClazz   the class of type CrudModel
     * @param propertyName the property name
     */
    public AbstractField crea(Class modelClazz, String propertyName) {
        AbstractField field;
        Field reflectionJavaField = reflectionService.getJavaField(modelClazz, propertyName);
        TypeField type;
        String key = propertyName;
        String message;
        String caption = annotationService.getCaption(modelClazz, propertyName);
        Class linkClazz = annotationService.getLinkClazz(modelClazz, propertyName);
        Class enumClazz = annotationService.getEnumClazz(modelClazz, propertyName);
        ComboBox combo;
        List items = null;
        List enumObjects;

        if (reflectionJavaField == null) {
            return null;
        }
        type = annotationService.getType(reflectionJavaField);

        field = switch (type) {
            case text:
            case phone:
                yield new TextField(caption);
            case integer, ordine:
                yield new IntegerField(caption);
            case booleano:
                yield new Checkbox(caption);
            case localDateTime:
                yield new DateTimePicker(caption);
            case localDate:
                yield new DatePicker(caption);
            case localTime:
                yield new TimePicker(caption);
            case linkDBRef:
                combo = new ComboBox<>(caption);
                try {
                    items = mongoService.findAll(linkClazz);
                } catch (Exception unErrore) {
                    if (linkClazz == null) {
                        message = String.format("Manca la linkClazz per il ComboBox. Va specificata nel field %s in %s", propertyName, modelClazz.getSimpleName());
                    }
                    else {
                        message = String.format("La linkClazz %s indicata per il ComboBox del field %s in %s, non è adatta", linkClazz.getSimpleName(), propertyName, modelClazz.getSimpleName());
                    }
                    logger.warn(new WrapLog().message(message).type(TypeLog.form));
                    yield null;
                }
                if (items != null) {
                    combo.setItems(items);
                }
                yield combo;
            case enumType:
                combo = new ComboBox<>(caption);
                if (enumClazz != null) {
                    Object[] elementi = enumClazz.getEnumConstants();
                    if (elementi != null) {
                        combo.setItems(elementi);
                    }
                }
                yield combo;
            default:
                yield null;
        };

        if (field != null) {
            field.getElement().setAttribute(KEY_TAG_PROPERTY_KEY, key);
        }

        return field;
    }

}