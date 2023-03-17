package it.algos.vaad24.backend.service;

import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import org.hibernate.validator.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 18:18
 * <p>
 * Gestisce le @Annotation/Interfacce specifiche di Algos contenute nella directory vaad23.backend.annotation <br>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired public AnnotationService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AnnotationService extends AbstractService {

    //==========================================================================
    // Costanti
    //==========================================================================

    //==========================================================================
    // Interfaces Spring/Vaadin
    //==========================================================================

    /**
     * Get the annotation Route. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l' annotation specifica <br>
     *
     * @param clazz of all types
     *
     * @return the specific Annotation
     */
    public Route getRoute(final Class<?> clazz) {
        return clazz != null ? clazz.getAnnotation(Route.class) : null;
    }

    /**
     * Get the annotation Qualifier. <br>
     *
     * @param clazz of all types
     *
     * @return the specific Annotation
     */
    public Qualifier getQualifier(final Class<?> clazz) {
        return clazz != null ? clazz.getAnnotation(Qualifier.class) : null;
    }


    /**
     * Get the annotation Document. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific Annotation
     */
    public Document getDocument(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(Document.class) : null;
    }

    /**
     * Get the annotation PageTitle. <br>
     *
     * @param clazz of all types
     *
     * @return the specific Annotation
     */
    public PageTitle getPageTitle(final Class<?> clazz) {
        return clazz != null ? clazz.getAnnotation(PageTitle.class) : null;
    }


    /**
     * Get the annotation NotNull. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public NotNull getNotNull(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(NotNull.class) : null;
    }


    /**
     * Get the annotation NotBlank. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public NotBlank getNotBlank(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(NotBlank.class) : null;
    }


    /**
     * Get the annotation Indexed. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Indexed getIndexed(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Indexed.class) : null;
    }


    /**
     * Get the annotation Size. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Size getSize(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Size.class) : null;
    }


    /**
     * Get the annotation Range. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Range getRange(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Range.class) : null;
    }


    /**
     * Get the annotation DBRef. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public DBRef getDBRef(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(DBRef.class) : null;
    }

    /**
     * Check if the field is DBRef type. <br>
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return true if field is of type DBRef
     */
    public boolean isDBRef(final Class<? extends AEntity> entityClazz, final String publicFieldName) throws AlgosException {
        Field reflectionJavaField = reflectionService.getField(entityClazz, publicFieldName);
        return reflectionJavaField != null && reflectionJavaField.getAnnotation(DBRef.class) != null;
    }

    //==========================================================================
    // Interfaces Algos
    //==========================================================================

    /**
     * Get the annotation Algos AIView. <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the specific annotation
     */
    public AIView getAIView(final Class<?> entityViewClazz) {

        //--controllo congruità
        if (entityViewClazz == null) {
            //@todo lanciare un errore nei log/logger
        }

        return entityViewClazz.getAnnotation(AIView.class);
    }

    /**
     * Get the annotation Algos AIEntity. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIEntity getAIEntity(final Class<? extends AEntity> entityClazz) {
        //--controllo congruità
        if (entityClazz == null) {
            //@todo lanciare un errore nei log/logger
        }

        return entityClazz.getAnnotation(AIEntity.class);
    }

    /**
     * Get the annotation Algos AIField. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AIField getAIField(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AIField.class) : null;
    }

    /**
     * Get the annotation Algos AIField. <br>
     *
     * @param entityClazz the class of type AEntity
     * @param fieldName   the property name
     *
     * @return the specific annotation
     */
    public AIField getAIField(final Class<? extends AEntity> entityClazz, final String fieldName) {
        AIField annotation = null;
        Field reflectionJavaField;
        String message;

        // Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return null;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(fieldName)) {
            return null;
        }

        // Controlla che il parametro in ingresso non sia stato creato 'al volo'
        if (fieldName.equals(FIELD_KEY_ORDER)) {
            return null;
        }

        try {
            reflectionJavaField = reflectionService.getField(entityClazz, fieldName);
            annotation = getAIField(reflectionJavaField);
        } catch (Exception unErrore) {
            message = String.format("Manca il field %s", fieldName);
            logger.error(new WrapLog().exception(new AlgosException(unErrore, message)).usaDb());
        }

        return annotation;
    }

    //==========================================================================
    // @Route
    //==========================================================================

    /**
     * Check if the view has a @Route annotation. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l'annotation specifica <br>
     *
     * @param clazz of all types
     *
     * @return true if the class as a @Route
     */
    public boolean isRouteView(final Class<?> clazz) {
        return getRoute(clazz) != null;
    }


    /**
     * Get the name of the route. <br>
     *
     * @param clazz of all types
     *
     * @return the name of the vaadin-view @route
     */
    public String getRouteMenuName(final Class<?> clazz) {
        Route annotation = clazz != null ? this.getRoute(clazz) : null;

        return annotation != null ? annotation.value() : VUOTA;
    }

    /**
     * Get the title of the page.
     *
     * @param genericClazz of all types
     *
     * @return the name of the vaadin-view @route
     */
    public String getPageMenu(final Class<?> genericClazz) {
        PageTitle annotation = genericClazz != null ? this.getPageTitle(genericClazz) : null;
        return annotation != null ? annotation.value() : VUOTA;
    }

    //==========================================================================
    // @AIView
    //==========================================================================

    /**
     * Restituisce il nome del menu. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Se la classe è una @Route, recupera @PageTitle e il value di @Route <br>
     * 3) Se manca @PageTitle o se la classe non è una @Route, recupera la Entity corrispondente e il menuName di @AIView <br>
     * 4) Nell'ordine usa: <br>
     * * @AIView -> menuName
     * * @PageTitle -> value
     * * @Route -> value
     * * @AIView della Entity.class associata -> menuName
     * * clazz.getSimpleName()
     *
     * @param clazz of all types
     *
     * @return the name of the spring-view
     */
    public String getMenuName(final Class<?> clazz) {
        String menuName = VUOTA;
        String viewName = VUOTA;
        Class entityClazz = null;
        AIView annotationView;
        PageTitle annotationTitle;
        Route annotationRoute;
        String pageMenu = VUOTA;
        String routeMenu = VUOTA;

        // Se manca la classe non può esserci nessuna annotation
        if (clazz == null) {
            // @todo gestire errore
            //            throw AlgosException.stack("Manca la entityViewClazz in ingresso", getClass(), "getMenuName");
        }

        // @AIView -> menuName è la prima opzione (deve esserci l'annotation @AIView)
        annotationView = this.getAIView(clazz);
        if (annotationView != null) {
            if (textService.isValid(annotationView.menuName())) {
                return textService.primaMaiuscola(annotationView.menuName());
            }
        }

        // PageTitle -> value è la seconda opzione (la classe deve essere una @Route)
        annotationTitle = this.getPageTitle(clazz);
        if (annotationTitle != null) {
            if (textService.isValid(annotationTitle.value())) {
                return textService.primaMaiuscola(annotationTitle.value());
            }
        }

        // @Route -> value è la terza opzione (la classe deve essere una @Route)
        annotationRoute = this.getRoute(clazz);
        if (annotationRoute != null) {
            if (textService.isValid(annotationRoute.value())) {
                return textService.primaMaiuscola(annotationRoute.value());
            }
        }

        // @AIView della Entity.class associata -> menuName è la quarta opzione (deve esistere una Entity associata)
        //        try {
        //            entityClazz = classService.getEntityClazzFromClazz(entityViewClazz);
        //        } catch (AlgosException unErrore) {
        //            if (!isRouteView(entityViewClazz)) {
        //                throw AlgosException.stack(unErrore, this.getClass(), "getMenuName");
        //            }
        //        }

        // Se la classe è una Entity
        // Cerca in @AIView della classe la property 'menuName'
        //        if (entityClazz != null) {
        //            annotationView = this.getAIView(entityClazz);
        //            if (annotationView != null) {
        //                viewName = annotationView.menuName();
        //            }
        //        }

        // clazz.getSimpleName() è la quinta (ultima) opzione

        return textService.primaMaiuscola(menuName);
    }


    /**
     * Valore della VaadinIcon di una view <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l'annotation specifica <br>
     *
     * @param clazz of all types
     *
     * @return the menu vaadin icon
     */
    public VaadinIcon getMenuVaadinIcon(final Class<?> clazz) {
        AIView annotation = this.getAIView(clazz);
        return annotation != null ? annotation.menuIcon() : DEFAULT_ICON;
    }


    /**
     * Valore della Icon di una view <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l'annotation specifica <br>
     *
     * @param clazz of all types
     *
     * @return the menu icon
     */
    public Icon getMenuIcon(final Class<?> clazz) {
        return getMenuVaadinIcon(clazz) != null ? getMenuVaadinIcon(clazz).create() : null;
    }


    /**
     * Nome della Icon di una view <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l'annotation specifica <br>
     *
     * @param clazz of all types
     *
     * @return the menu icon name
     */
    public String getLineawesomeClassnames(final Class<?> clazz) {
        AIView annotation = this.getAIView(clazz);
        return annotation != null ? annotation.lineawesomeClassnames() : DEFAULT_ICON_NAME;
    }

    //==========================================================================
    // @AIEntity
    //==========================================================================

    /**
     * Get the name (lowerCase) of the collection on mongoDB  <br>
     * Controlla che esista l'annotation specifica <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the name of the collection
     */
    public String getCollectionName(final Class<? extends AEntity> entityClazz) {
        String collectionName = VUOTA;
        AIEntity annotation = this.getAIEntity(entityClazz);

        if (annotation != null && annotation.collectionName().length() > 0) {
            collectionName = annotation.collectionName();
        }
        else {
            collectionName = entityClazz.getSimpleName();
            collectionName = textService.levaCoda(collectionName, SUFFIX_ENTITY);
            collectionName = textService.primaMinuscola(collectionName);
        }

        return collectionName;
    }

    /**
     * Usa il metodo resetOnlyEmpty() nella classe xxxBackend <br>
     * Controlla che esista l'annotation specifica <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return if exists the method xxxBackend.resetOnlyEmpty()
     */
    public boolean usaReset(final Class<? extends AEntity> entityClazz) {
        AIEntity annotation = this.getAIEntity(entityClazz);
        return annotation != null ? annotation.usaReset() : false;
    }

    /**
     * Get the name of the EntityClass that is a preReset <br>
     * Controlla che esista l'annotation specifica <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the name of the class that need a reset before
     */
    public String getReset(final Class<? extends AEntity> entityClazz) {
        String ancestorReset = VUOTA;
        AIEntity annotation = this.getAIEntity(entityClazz);
        String collectionName = VUOTA;

        if (annotation != null && annotation.preReset().length() > 0) {
            ancestorReset = annotation.preReset();
        }

        if (textService.isValid(ancestorReset)) {
            collectionName = entityClazz.getSimpleName().toLowerCase();
            collectionName += VIRGOLA;
            collectionName += ancestorReset;
            return collectionName;
        }
        else {
            return entityClazz.getSimpleName().toLowerCase();
        }
    }

    public boolean isEsisteKeyPropertyName(final Class<? extends AEntity> entityClazz) {
        return textService.isValid(getKeyPropertyName(entityClazz));
    }


    /**
     * Restituisce il nome della property chiave <br>
     * Controlla che esista l'annotation specifica <br>
     * Se non la trova, usa il valore di default '_id' <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the key property name
     */
    public String getKeyPropertyName(final Class<? extends AEntity> entityClazz) {
        String keyPropertyName = VUOTA;
        AIEntity annotation = this.getAIEntity(entityClazz);

        if (annotation != null && annotation.keyPropertyName().length() > 0) {
            annotation = getAIEntity(entityClazz);
        }

        if (annotation != null) {
            keyPropertyName = annotation.keyPropertyName();
        }
        else {
            keyPropertyName = FIELD_NAME_ID_CON;
        }

        return keyPropertyName;
    }

    /**
     * Esiste una property chiave <br>
     * Controlla che esista l'annotation specifica <br>
     * Esclude usa il valore di default '_id' <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return if exists a key property
     */
    public boolean usaKeyPropertyName(final Class<? extends AEntity> entityClazz) {
        String keyPropertyName = getKeyPropertyName(entityClazz);

        if (textService.isValid(keyPropertyName) && !keyPropertyName.equals(FIELD_NAME_ID_CON)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Flag per usare solo le lettere minuscole nel campo chiave keyId. <br>
     * Uppercase and lowercase letters are treated equivalent (case-insensitive) and NOT as distinct (case-sensitive) <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaKeyIdMinuscolaCaseInsensitive(final Class<? extends AEntity> entityClazz) {
        boolean usaKeyIdMinuscolaCaseInsensitive = true;
        AIEntity annotation = this.getAIEntity(entityClazz);

        if (annotation != null && annotation.keyPropertyName().length() > 0) {
            annotation = getAIEntity(entityClazz);
        }

        if (annotation != null) {
            usaKeyIdMinuscolaCaseInsensitive = annotation.usaKeyIdMinuscolaCaseInsensitive();
        }

        return usaKeyIdMinuscolaCaseInsensitive;
    }


    /**
     * Flag per non usare lo spazio nel campo chiave keyId. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaKeyIdSenzaSpazi(final Class<? extends AEntity> entityClazz) {
        boolean usaKeyIdSenzaSpazi = true;
        AIEntity annotation = this.getAIEntity(entityClazz);

        if (annotation != null && annotation.keyPropertyName().length() > 0) {
            annotation = getAIEntity(entityClazz);
        }

        if (annotation != null) {
            usaKeyIdSenzaSpazi = annotation.usaKeyIdSenzaSpazi();
        }

        return usaKeyIdSenzaSpazi;
    }

    //==========================================================================
    // @AIField
    //==========================================================================

    /**
     * Get the type (field) of the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the type for the specific field
     */
    public AETypeField getFormType(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        Field reflectionJavaField = reflectionService.getField(entityClazz, publicFieldName);
        return reflectionJavaField != null ? getFormType(reflectionJavaField) : AETypeField.text;
    }

    /**
     * Get the widthEM of the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the width of the field expressed in em
     */
    public String getWidth(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String width = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);
        String tag = "em";

        if (annotation != null && annotation.widthEM() > 0) {
            width = annotation.widthEM() + tag;
        }

        if (width.equals(VUOTA) || width.equals(tag)) {
            width = getFormType(entityClazz, publicFieldName).getWidthColumn() + tag;
        }

        return width;
    }

    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the type for the specific field
     */
    public AETypeField getFormType(final Field reflectionJavaField) {
        AETypeField type;
        AETypeField standard = AETypeField.text;
        AIField annotation;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            type = annotation.type();
        }
        else {
            type = standard;
        }

        return type;
    }

    /**
     * Check if the field can grow <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field can grow
     */
    public boolean isFlexGrow(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        boolean isFlexGrow = false;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            isFlexGrow = annotation.flexGrow();
        }

        return isFlexGrow;
    }

    /**
     * Check if the field has focus <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field has focus
     */
    public boolean hasFocus(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        boolean isFocus = false;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            isFocus = annotation.focus();
        }

        return isFocus;
    }

    /**
     * Get the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the property
     */
    public String getHeader(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String header = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            header = annotation.header();
        }

        if (textService.isEmpty(header)) {
            header = publicFieldName;
        }

        return header;
    }

    /**
     * Get the property for the sort.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the property
     */
    public String getSortProperty(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String sortProperty = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            sortProperty = annotation.sortProperty();
        }

        if (textService.isEmpty(sortProperty)) {
            sortProperty = publicFieldName;
        }

        return sortProperty;
    }

    /**
     * Get the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the property
     */
    public String getCaption(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String caption = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            caption = annotation.caption();
        }

        if (textService.isEmpty(caption)) {
            caption = publicFieldName;
        }

        return caption;
    }

    /**
     * Get the class of the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getEnumClazz(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        Class enumClazz = null;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            enumClazz = annotation.enumClazz();
        }

        return enumClazz == Object.class ? null : enumClazz;
    }

    /**
     * Get the class of the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getLinkClazz(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        Class linkClazz = null;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            linkClazz = annotation.linkClazz();
        }

        return linkClazz == Object.class ? null : linkClazz;
    }


    /**
     * Get the basic values of enumeration/String.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the string of all possible values
     */
    @SuppressWarnings("all")
    public String getValoriBaseEnum(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String values = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            values = annotation.valoriEnum();
        }

        return values;
    }

    /**
     * Get the list of all basic values of enumeration/String.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the list of all possible values
     */
    @SuppressWarnings("all")
    public List<String> getListaValoriEnum(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        List<String> listaValori = new ArrayList<>();
        String values = getValoriBaseEnum(entityClazz, publicFieldName);

        if (values.contains(VIRGOLA)) {
            listaValori = arrayService.getList(values);
        }

        return listaValori;
    }

    /**
     * Get the icon of the property.
     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare e posso quindi escluderlo
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the icon of the field
     */
    public VaadinIcon getHeaderIcon(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        VaadinIcon icon = null;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            icon = annotation.headerIcon();
            icon = (icon == VaadinIcon.YOUTUBE) ? null : icon;
        }
        else {
            icon = null;
        }

        return icon;
    }

    /**
     * Get the color of the property.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the color of the icon
     */
    public String getHeaderIconColor(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        String color = VUOTA;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            color = annotation.headerIconColor();
        }

        return textService.isValid(color) ? color : COLOR_BLUE;
    }


    /**
     * Check if the field is searchable <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field is searchable
     */
    public boolean isSearch(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        boolean isSearch = false;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            isSearch = annotation.search();
        }

        return isSearch;
    }

    /**
     * Check if the field is transient <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field is transient
     */
    public boolean isTransient(final Class<? extends AEntity> entityClazz, final Field field) {
        boolean isTransient = false;

        if (field != null) {
            return field.getDeclaredAnnotation(Transient.class) != null;
        }

        return isTransient;
    }

    /**
     * Check if the field is transient <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field is transient
     */
    public boolean isTransient(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        Field field = reflectionService.getField(entityClazz, publicFieldName);
        return isTransient(entityClazz, field);
    }

    /**
     * Get the specific annotation of the field. <br>
     */
    public AETypeBoolCol getTypeBoolCol(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        AETypeBoolCol type = AETypeBoolCol.boolGrezzo;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            type = annotation.typeBool();
        }

        return type;
    }

    /**
     * Check if the combo is nullable <br>.
     *
     * @param entityClazz     the class of type AEntity
     * @param publicFieldName the property name
     *
     * @return the field is searchable
     */
    public boolean nullSelectionAllowed(final Class<? extends AEntity> entityClazz, final String publicFieldName) {
        boolean nullSelectionAllowed = false;
        AIField annotation = this.getAIField(entityClazz, publicFieldName);

        if (annotation != null) {
            nullSelectionAllowed = annotation.nullSelectionAllowed();
        }

        return nullSelectionAllowed;
    }

}