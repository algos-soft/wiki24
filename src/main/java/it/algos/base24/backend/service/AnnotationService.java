package it.algos.base24.backend.service;

import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.router.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.view.*;
import org.springframework.stereotype.*;
import org.vaadin.lineawesome.*;

import javax.inject.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 17:17
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public AnnotationService annotationService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AnnotationService {

    @Inject
    TextService textService;

    @Inject
    ReflectionService reflectionService;

    @Inject
    LogService logger;

    //==========================================================================
    // @AEntity
    //==========================================================================

    /**
     * Get the annotation AEntity. <br>
     *
     * @param entityClazz di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AEntity getEntityAnnotation(final Class<? extends AbstractEntity> entityClazz) {
        //--controllo congruità
        if (entityClazz == null) {
            return null;
        }

        return entityClazz.getAnnotation(AEntity.class);
    }


    /**
     * Get the collectionName of the entityClazz.
     *
     * @param entityClazz di riferimento
     *
     * @return the collectionName on the MongoDb database
     */
    public String getCollectionName(final Class entityClazz) {
        String collectionName = VUOTA;
        AEntity annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "getCollectionName")) {
            return collectionName;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            collectionName = annotation.collectionName();
        }

        if (textService.isEmpty(collectionName)) {
            collectionName = entityClazz.getSimpleName();
            collectionName = textService.levaCoda(collectionName, BaseCost.SUFFIX_ENTITY);
        }

        if (textService.isValid(collectionName)) {
            collectionName = textService.primaMinuscola(collectionName);
        }

        return collectionName;
    }


    /**
     * Get the keyPropertyName of the entityClazz.
     *
     * @param entityClazz di riferimento
     *
     * @return the keyPropertyName of entityClazz
     */
    public String getKeyPropertyName(final Class entityClazz) {
        String keyPropertyName = VUOTA; AEntity annotation; List<String> possibiliPropertyKey = BaseCost.FIELD_KEY_NAME_LIST;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "getKeyPropertyName")) {
            return keyPropertyName;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            keyPropertyName = annotation.keyPropertyName();
        }

        if (textService.isEmpty(keyPropertyName)) {
            for (String possibileKey : possibiliPropertyKey) {
                if (reflectionService.isEsiste(entityClazz, possibileKey)) {
                    keyPropertyName = possibileKey; break;
                }
            }
        }

        if (textService.isValid(keyPropertyName)) {
            keyPropertyName = textService.primaMinuscola(keyPropertyName);
        }

        return keyPropertyName;
    }


    /**
     * Get the searchPropertyName of the entityClazz.
     *
     * @param entityClazz di riferimento
     *
     * @return the searchPropertyName of entityClazz
     */
    public String getSearchPropertyName(final Class entityClazz) {
        String searchPropertyName = VUOTA; String keyPropertyName = VUOTA;
        AEntity annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "getSearchPropertyName")) {
            return searchPropertyName;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            searchPropertyName = annotation.searchPropertyName();
        }

        // se manca, usa la keyPropertyName
        if (textService.isEmpty(searchPropertyName)) {
            keyPropertyName = getKeyPropertyName(entityClazz);
            if (textService.isValid(keyPropertyName)) {
                searchPropertyName = keyPropertyName;
            }
        }

        if (textService.isValid(searchPropertyName)) {
            searchPropertyName = textService.primaMinuscola(searchPropertyName);
        }

        return searchPropertyName;
    }

    /**
     * Get the sortPropertyName of the entityClazz.
     *
     * @param entityClazz di riferimento
     *
     * @return the sortPropertyName of entityClazz
     */
    public String getSortPropertyName(final Class entityClazz) {
        String sortPropertyName = VUOTA;
        String keyPropertyName = VUOTA;
        AEntity annotation; List<String> possibiliSortProperty = BaseCost.FIELD_SORT_NAME_LIST;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "getSortPropertyName")) {
            return sortPropertyName;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            sortPropertyName = annotation.sortPropertyName();
        }

        // se manca, cerca in una lista di PROBABILI valori
        if (textService.isEmpty(sortPropertyName)) {
            for (String possibileKey : possibiliSortProperty) {
                if (reflectionService.isEsiste(entityClazz, possibileKey)) {
                    sortPropertyName = possibileKey; break;
                }
            }
        }

        // se manca, usa la keyPropertyName
        if (textService.isEmpty(sortPropertyName)) {
            keyPropertyName = getKeyPropertyName(entityClazz);
            if (textService.isValid(keyPropertyName)) {
                sortPropertyName = keyPropertyName;
            }
        }

        if (textService.isValid(sortPropertyName)) {
            sortPropertyName = textService.primaMinuscola(sortPropertyName);
        }

        return sortPropertyName;
    }

    /**
     * Get the value of startupRest flag.
     *
     * @param entityClazz di riferimento
     *
     * @return value of startupRest flag
     */
    @Deprecated
    public boolean usaStartupReset(final Class entityClazz) {
        boolean usaStartupReset = false;
        AEntity annotation;
        TypeList typeList;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "usaStartupReset")) {
            return usaStartupReset;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            typeList = annotation.typeList();
            if (typeList != null) {
                usaStartupReset = typeList.isUsaStartupReset();
            }
        }

        return usaStartupReset;
    }


    /**
     * Get the typeReset for buttons in list <br>.
     *
     * @param entityClazz di riferimento
     *
     * @return value of startupRest flag
     */
    public TypeList getTypeList(final Class entityClazz) {
        TypeList typeList = TypeList.standard;
        AEntity annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "getTypeList")) {
            return typeList;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            typeList = annotation.typeList();
        }

        return typeList;
    }


    /**
     * Get the value of usaIdPrimaMinuscola flag.
     *
     * @param entityClazz di riferimento
     *
     * @return value of startupRest flag
     */
    public boolean usaIdPrimaMinuscola(final Class entityClazz) {
        boolean usaIdPrimaMinuscola = true;
        AEntity annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, "usaIdPrimaMinuscola")) {
            return usaIdPrimaMinuscola;
        }

        annotation = this.getEntityAnnotation(entityClazz);
        if (annotation != null) {
            usaIdPrimaMinuscola = annotation.usaIdPrimaMinuscola();
        }

        return usaIdPrimaMinuscola;
    }

    //==========================================================================
    // @AView
    //==========================================================================

    /**
     * Get the annotation Algos AView. <br>
     *
     * @param viewClazz di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AView getViewAnnotation(final Class<? extends CrudView> viewClazz) {
        //--controllo congruità
        if (viewClazz == null) {
            logger.warn(new WrapLog().message("Manca la viewClazz"));
            return null;
        }

        return viewClazz.getAnnotation(AView.class);
    }


    /**
     * Get the menuName of the CrudView type clazz.
     *
     * @param genericClazz the class of type CrudView
     *
     * @return the menuName
     */
    public String getMenuName(final Class genericClazz) {
        String menuName = VUOTA;
        Class clazz = null;
        String clazzName = VUOTA;
        AView annotation;
        String message;
        PageTitle pageTitle;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la genericClazz", "getMenuName", "annotationService");
            logger.warn(message);
            return VUOTA;
        }

        pageTitle = (PageTitle) genericClazz.getAnnotation(PageTitle.class);
        if (pageTitle != null) {
            return pageTitle.value();
        }

        // Controlla che i parametri in ingresso siano validi
        if (!CrudView.class.isAssignableFrom(genericClazz)) {
            if (clazz == null && AbstractEntity.class.isAssignableFrom(genericClazz)) {
                clazzName = genericClazz.getCanonicalName();
                clazzName = textService.levaCoda(clazzName, SUFFIX_ENTITY);
                clazzName = clazzName + SUFFIX_VIEW;
                try {
                    clazz = Class.forName(clazzName);
                } catch (Exception unErrore) {
                    clazz = null; logger.error(new WrapLog().exception(unErrore).usaDb());
                }
            } if (clazz == null) {
                return menuName;
            }
        }

        pageTitle = (PageTitle) clazz.getAnnotation(PageTitle.class);
        if (pageTitle != null) {
            return pageTitle.value();
        }

        annotation = this.getViewAnnotation(clazz);
        if (annotation != null) {
            menuName = annotation.menuName();
        }

        if (textService.isEmpty(menuName)) {
            clazzName = clazz.getSimpleName();
            menuName = textService.levaCoda(clazzName, SUFFIX_VIEW);
        }

        if (textService.isValid(menuName)) {
            menuName = textService.primaMaiuscola(menuName);
        }

        return menuName;
    }


    /**
     * Get the menuGroup of the CrudView type clazz.
     *
     * @param viewClazz the class of type CrudView
     *
     * @return the menuName
     */
    public MenuGroup getMenuGroup(final Class viewClazz) {
        MenuGroup menuGroup = MenuGroup.nessuno;
        AView annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkView(viewClazz, "getMenuGroup")) {
            return menuGroup;
        }

        annotation = this.getViewAnnotation(viewClazz);
        if (annotation != null) {
            menuGroup = annotation.menuGroup();
        }

        return menuGroup;
    }

    /**
     * Get the menuGroup of the CrudView type clazz.
     *
     * @param viewClazz the class of type CrudView
     *
     * @return the menuName
     */
    public String getMenuGroupName(final Class viewClazz) {
        String menuGroupName = MenuGroup.nessuno.getTag();
        AView annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkView(viewClazz, "getMenuGroupName")) {
            return menuGroupName;
        }

        annotation = this.getViewAnnotation(viewClazz);
        if (annotation != null) {
            menuGroupName = annotation.menuGroupName();
            if (textService.isEmpty(menuGroupName)) {
                menuGroupName = annotation.menuGroup().getTag();
            }
        }

        return textService.primaMaiuscola(menuGroupName);
    }

    /**
     * Automatic use of menu.
     *
     * @param viewClazz the class of type CrudView
     *
     * @return flag true/false
     */
    public boolean usaMenuAutomatico(final Class viewClazz) {
        boolean usaMenuAutomatico = true;
        AView annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkView(viewClazz, "usaMenuAutomatico")) {
            return usaMenuAutomatico;
        }

        annotation = this.getViewAnnotation(viewClazz);
        if (annotation != null) {
            usaMenuAutomatico = annotation.menuAutomatico();
        }

        return usaMenuAutomatico;
    }


    /**
     * Get the menuIcon of the CrudView type clazz.
     *
     * @param viewClazz the class of type CrudView
     *
     * @return the menuName
     */
    public LineAwesomeIcon getMenuIcon(final Class viewClazz) {
        LineAwesomeIcon menuIcon = LineAwesomeIcon.FOLDER;
        AView annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkView(viewClazz, "getMenuIcon")) {
            return menuIcon;
        }

        annotation = this.getViewAnnotation(viewClazz);
        if (annotation != null) {
            menuIcon = annotation.menuIcon();
        }

        return menuIcon;
    }

    //==========================================================================
    // @AField
    //==========================================================================

    /**
     * Get the annotation Algos AField. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    private AField getFieldAnnotation(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AField.class) : null;
    }


    /**
     * Get the annotation Algos AField. <br>
     *
     * @param entityClazz  di riferimento
     * @param propertyName the property name
     *
     * @return the specific annotation
     */
    public AField getFieldAnnotation(final Class entityClazz, final String propertyName) {
        AField annotation = null;
        Field reflectionJavaField;
        String message;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getField")) {
            return null;
        }

        try {
            reflectionJavaField = reflectionService.getJavaField(entityClazz, propertyName);
            annotation = getFieldAnnotation(reflectionJavaField);
        } catch (Exception unErrore) {
            message = String.format("Manca il field %s", propertyName);
            logger.warn(message);
        }

        return annotation;
    }


    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the type for the specific field
     */
    public TypeField getType(final Field reflectionJavaField) {
        TypeField type;
        TypeField standard = TypeField.text;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getFieldAnnotation(reflectionJavaField);
        if (annotation != null) {
            type = annotation.type();
        }
        else {
            type = standard;
        }

        return type;
    }

    /**
     * Get the type (field) of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the type for the specific field
     */
    public TypeField getType(final Class entityClazz, final String propertyName) {
        Field reflectionJavaField;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getType")) {
            return null;
        }

        reflectionJavaField = reflectionService.getJavaField(entityClazz, propertyName);
        return reflectionJavaField != null ? getType(reflectionJavaField) : TypeField.text;
    }


    /**
     * Get the typeBool (field) of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the typeBool for the specific field
     */
    public TypeBool getTypeBool(final Class entityClazz, final String propertyName) {
        TypeBool typeBool = TypeBool.checkIcon;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getTypeBool")) {
            return typeBool;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            typeBool = annotation.typeBool();
        }

        return typeBool;
    }


    /**
     * Get the typeDate (field) of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the typeDate for the specific field
     */
    public TypeDate getTypeDate(final Class entityClazz, final String propertyName) {
        TypeDate typeDate = TypeDate.iso8601;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getTypeDate")) {
            return typeDate;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            typeDate = annotation.typeDate();
        }

        return typeDate;
    }

    /**
     * Get the widthR of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the width of the field
     */
    public int getWidthInt(final Class entityClazz, final String propertyName) {
        int width = 0;
        AField annotation;
        TypeField typeField;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getWidthInt")) {
            return width;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null && annotation.widthRem() > 0) {
            width = annotation.widthRem();
        } if (annotation != null && width == 0) {
            typeField = annotation.type(); if (typeField != null) {
                width = typeField.getWidthColumnInt();
            }
        }

        return width;
    }

    /**
     * Get the widthREM of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the width of the field expressed in rem
     */
    public String getWidth(final Class entityClazz, final String propertyName) {
        String width = VUOTA;
        AField annotation;
        String tag = BaseCost.HTML_WIDTH_UNIT;
        TypeField typeField;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getWidth")) {
            return width;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null && annotation.widthRem() > 0) {
            width = annotation.widthRem() + tag;
        } if (annotation != null && textService.isEmpty(width)) {
            typeField = annotation.type(); if (typeField != null) {
                width = typeField.getWidthColumn();
            }
        }

        return width;
    }


    /**
     * Get the header of the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the width of the field expressed in rem
     */
    public String getHeaderText(final Class entityClazz, final String propertyName) {
        String headerText = VUOTA;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getHeaderText")) {
            return headerText;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            headerText = annotation.headerText();
        }

        if (textService.isEmpty(headerText)) {
            headerText = textService.primaMaiuscola(propertyName);
        }

        return headerText;
    }

    /**
     * Get the vaadin icon of the property.
     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare e posso quindi escluderlo
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the icon of the field
     */
    public VaadinIcon getHeaderVaadinIcon(final Class entityClazz, final String propertyName) {
        VaadinIcon headerIcon = null;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getHeaderIcon")) {
            return headerIcon;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            headerIcon = annotation.headerIcon();
            headerIcon = (headerIcon == VaadinIcon.YOUTUBE) ? null : headerIcon;
        }

        return headerIcon;
    }


    /**
     * Get the vaadin icon of the property.
     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare e posso quindi escluderlo
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the icon of the field
     */
    public Icon getHeaderIcon(final Class entityClazz, final String propertyName) {
        Icon headerIcon = null;
        VaadinIcon headerVaadinIcon = getHeaderVaadinIcon(entityClazz, propertyName);

        if (headerVaadinIcon != null) {
            headerIcon = new Icon(headerVaadinIcon);
        }

        return headerIcon;
    }


    /**
     * Get the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the property
     */
    public String getCaption(final Class entityClazz, final String propertyName) {
        String caption = VUOTA;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getCaption")) {
            return VUOTA;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            caption = annotation.caption();
        }

        if (textService.isEmpty(caption)) {
            caption = propertyName;
        }

        return textService.primaMaiuscola(caption);
    }


    /**
     * Get the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the property
     */
    public Class getLinkClazz(final Class entityClazz, final String propertyName) {
        Class linkClazz = null;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getLinkClazz")) {
            return null;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            linkClazz = annotation.linkClazz();
        }

        return linkClazz != Object.class ? linkClazz : null;
    }


    /**
     * Get the property.
     *
     * @param entityClazz  the class of type AbstractEntity
     * @param propertyName the property name
     *
     * @return the property
     */
    public Class getEnumClazz(final Class entityClazz, final String propertyName) {
        Class enumClazz = null;
        AField annotation;

        // Controlla che i parametri in ingresso siano validi
        if (!checkEntity(entityClazz, propertyName, "getEnumClazz")) {
            return null;
        }

        annotation = this.getFieldAnnotation(entityClazz, propertyName);
        if (annotation != null) {
            enumClazz = annotation.enumClazz();
        }

        return enumClazz != Object.class ? enumClazz : null;
    }

    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param methodName   the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean checkEntity(final Class genericClazz, final String methodName) {
        String message; Class entityClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(message); return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AbstractEntity", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            logger.warn(message); return false;
        }

        return true;
    }

    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param propertyName the property name
     * @param methodName   the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean checkEntity(final Class genericClazz, final String propertyName, final String methodName) {
        String message;
        Class entityClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(message); return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            entityClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AbstractEntity", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            logger.warn(message); return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(propertyName)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", methodName, this.getClass().getSimpleName(), "propertyName");
            logger.warn(message); return false;
        }

        // Controlla che il parametro in ingresso esista nella classe
        if (reflectionService.nonEsiste(entityClazz, propertyName)) {
            message = String.format("Nella classe [%s], non esiste la property '%s'", entityClazz.getSimpleName(), propertyName);
            logger.warn(message);
            return false;
        }

        return true;
    }


    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param methodName   the method name
     *
     * @return false se mancano parametri o non sono validi
     */
    public boolean checkView(final Class genericClazz, final String methodName) {
        String message; Class viewClazz;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la view clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(message); return false;
        }

        // Controlla che la classe in ingresso implementi CrudView
        if (CrudView.class.isAssignableFrom(genericClazz)) {
            viewClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa CrudView", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            logger.warn(message);
            return false;
        }

        return true;
    }


    public boolean isEsisteKeyPropertyName(final Class modelClazz) {
        return textService.isValid(getKeyPropertyName(modelClazz));
    }

}