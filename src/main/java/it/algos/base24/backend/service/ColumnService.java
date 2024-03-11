package it.algos.base24.backend.service;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.data.renderer.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 10-Aug-2023
 * Time: 06:35
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public ColumnService columnService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ColumnService {

    @Inject
    private TextService textService;

    @Inject
    private AnnotationService annotationService;

    @Inject
    private DateService dateService;

    @Inject
    private ReflectionService reflectionService;

    @Inject
    private LogService logger;

    @Inject
    private WebService webService;

    /**
     * Aggiunge in automatico le colonne previste in gridPropertyNamesList <br>
     *
     * @param grid                  a cui aggiungere la colonna
     * @param modelClazz            modello-dati specifico
     * @param gridPropertyNamesList lista di property da creare
     */
    public void addColumnsOneByOne(final Grid grid, Class modelClazz, final List<String> gridPropertyNamesList) {
        if (grid != null && gridPropertyNamesList != null) {
            for (int i = 0; i < gridPropertyNamesList.size(); i++) {
                String propertyName = gridPropertyNamesList.get(i);
                boolean isLastColumn = (i == gridPropertyNamesList.size() - 1);
                this.crea(grid, modelClazz, propertyName, isLastColumn);
            }
        }
    }


    /**
     * Aggiunge in automatico le colonne previste in gridPropertyNamesList <br>
     *
     * @param grid         a cui aggiungere la colonna
     * @param modelClazz   modello-dati specifico
     * @param propertyName della property
     */
    public Grid.Column<AbstractEntity> crea(final Grid grid, Class modelClazz, final String propertyName, boolean isLastColumn) {
        Grid.Column<AbstractEntity> colonna = null;
        TypeField type;
        String width;
        String headerText = VUOTA;
        VaadinIcon vaadinHeaderIcon;
        Icon headerIcon;
        Field field;
        boolean sortable = true;
        String anchorPrefix;

        // Controlla che i parametri in ingresso siano validi
        if (!check(grid, modelClazz, propertyName, "crea")) {
            return null;
        }

        type = annotationService.getType(modelClazz, propertyName);
        width = annotationService.getWidth(modelClazz, propertyName);
        headerText = annotationService.getHeaderText(modelClazz, propertyName);
        headerIcon = annotationService.getHeaderIcon(modelClazz, propertyName);
        field = reflectionService.getJavaField(modelClazz, propertyName);
        anchorPrefix = annotationService.getAnchorPrefix(modelClazz, propertyName);

        colonna = switch (type) {
            case text -> grid.addColumn(propertyName).setSortable(sortable);
            case localDateTime, localDate, localTime -> addLocalDateTime(grid, modelClazz, propertyName).setSortable(sortable);
            case integer, ordine, lungo, doppio -> grid.addColumn(propertyName).setSortable(sortable);
            case bigDecimal -> grid.addColumn(propertyName).setSortable(sortable);
            case booleano -> addBoolean(grid, modelClazz, propertyName);
            case enumType -> grid.addColumn(propertyName).setSortable(sortable);
            case linkStatico -> grid.addColumn(propertyName).setSortable(sortable);
            case linkDBRef -> grid.addColumn(propertyName).setSortable(sortable);
            case linkWiki -> grid.addColumn(propertyName).setSortable(sortable);
            case wikiAnchor -> grid.addColumn(new ComponentRenderer<>(entity -> {
                Object obj = null;
                Anchor anchor;
                String wikiLink = VUOTA;
                String wikiLabel = VUOTA;

                try {
                    obj = field.get(entity);
                } catch (Exception unErrore) {
                    logger.error(new WrapLog().message(unErrore.getMessage()).usaDb());
                }

                if (obj != null && obj instanceof String wikiTitle) {
                    wikiTitle = textService.setNoDoppieQuadre(wikiTitle);
                    if (wikiTitle.contains(PIPE)) {
                        wikiLink = textService.levaCodaDaPrimo(wikiTitle, PIPE);
                        wikiLabel = textService.levaPrimaAncheTag(wikiTitle, PIPE);
                    }
                    else {
                        wikiLink = wikiTitle;
                        wikiLabel = wikiTitle;
                    }
                    anchor = WAnchor.build(  anchorPrefix + wikiLink, wikiLabel);
                    return new Span(anchor);
                }
                return new Span(VUOTA);
            }));
            case linkWikiCheck -> grid.addColumn(new ComponentRenderer<>(entity -> {
                Object obj = null;
                Anchor anchor;

                try {
                    obj = field.get(entity);
                } catch (Exception unErrore) {
                    logger.error(new WrapLog().message(unErrore.getMessage()).usaDb());
                }

                if (obj != null && obj instanceof String wikiTitle) {
                    anchor = new Anchor(anchorPrefix + wikiTitle, "[" + wikiTitle + "]");
                    if (webService.isEsisteWiki(wikiTitle)) {
                        anchor.getElement().getStyle().set("color", "green");
                    }
                    else {
                        anchor.getElement().getStyle().set("color", "red");
                    }
                    anchor.getElement().getStyle().set("fontWeight", "bold");
                    return new Span(anchor);
                }
                return new Span(VUOTA);
            }));

            //            case linkDBRef -> grid.addColumn(new ComponentRenderer<>(entity -> {
            //                Object obj = null;
            //                Object alfa = entity;
            //                Object beta = entity;
            //                try { // prova a eseguire il codice
            //                    obj = field.get(entity);
            //                    Object alfetta = entity;
            //                } catch (Exception unErrore) {// intercetta l'errore
            //                    logger.warn(new WrapLog().exception(unErrore));
            //                }// fine del blocco try-catch
            //                return new Span(obj != null ? obj.toString() : VUOTA);
            //            }));//end of lambda expressions and anonymous inner class

            case preferenza -> addPreferenza(grid, propertyName);
            default -> null;
        };

        if (colonna != null) {
            if (textService.isValid(width)) {
                colonna.setWidth(width);
            }

            if (isLastColumn) {
                colonna.setFlexGrow(1);
            }
            else {
                colonna.setFlexGrow(0);
            }

            //--eventuale aggiunta di una icona e l' header non è una String ma diventa un Component
            //--se c'è l' icona NON usa il nome della property ma solo l' icona
            if (headerIcon != null) {
                //                icon.setSize(widthHeaderIcon);
                //                icon.setColor(colorHeaderIcon);
                colonna.setHeader(headerIcon);
            }
            else {
                colonna.setHeader(textService.primaMaiuscola(headerText));
            }
            if (type == TypeField.ordine) {
                colonna.setHeader("#");
            }
        }
        else {
            return colonna;
        }

        if (textService.isEmpty(colonna.getKey())) {
            colonna.setKey(propertyName);
        }

        colonna.setResizable(true);
        return colonna;
    }


    public Grid.Column<AbstractEntity> addBoolean(final Grid grid, Class<? extends AbstractEntity> entityClazz, final String propertyName) {
        Grid.Column<AbstractEntity> colonna;

        colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
            final TypeBool typeBool = annotationService.getTypeBool(entityClazz, propertyName);
            Object value;
            boolean status = false;
            Icon icon = null;
            String testo;
            Span span = new Span();
            String customText;
            String customVero;
            String customFalso;

            try {
                value = reflectionService.getPropertyValue((AbstractEntity) entity, propertyName);
                if (value instanceof Boolean booleano) {
                    status = booleano;
                }
            } catch (Exception unErrore) {
                //                logger.error(unErrore.toString());
            }

            switch (typeBool) {
                case boolGrezzo:
                    testo = status ? "vero" : "falso";
                    span.setText(testo);
                    if (status) {
                        span.getStyle().set("color", "green");
                    }
                    else {
                        span.getStyle().set("color", "red");
                    }
                    return span;
                case checkBox:
                    return new Checkbox(status);
                case checkIcon:
                    if (status) {
                        icon = new Icon(VaadinIcon.CHECK);
                        icon.setColor(COLOR_VERO);
                    }
                    else {
                        icon = new Icon(VaadinIcon.CLOSE);
                        icon.setColor(COLOR_FALSO);
                    }
                    icon.setSize("1em");
                    return icon;
                case checkIconReverse:
                    if (status) {
                        icon = new Icon(VaadinIcon.CLOSE);
                        icon.setColor(COLOR_FALSO);
                    }
                    else {
                        icon = new Icon(VaadinIcon.CHECK);
                        icon.setColor(COLOR_VERO);
                    }
                    icon.setSize("1em");
                    return icon;
                case thumb:
                    if (status) {
                        icon = new Icon(VaadinIcon.THUMBS_UP);
                        icon.setColor(COLOR_VERO);
                    }
                    else {
                        icon = new Icon(VaadinIcon.THUMBS_DOWN);
                        icon.setColor(COLOR_FALSO);
                    }
                    icon.setSize("1em");
                    return icon;
                case thumbReverse:
                    if (status) {
                        icon = new Icon(VaadinIcon.THUMBS_DOWN);
                        icon.setColor(COLOR_FALSO);
                    }
                    else {
                        icon = new Icon(VaadinIcon.THUMBS_UP);
                        icon.setColor(COLOR_VERO);
                    }
                    icon.setSize("1em");
                    return icon;
                case yesNo:
                    testo = status ? "si" : "no";
                    span.setText(testo);
                    if (status) {
                        span.getStyle().set("color", COLOR_VERO);
                    }
                    else {
                        span.getStyle().set("color", COLOR_FALSO);
                    }
                    return span;
                case yesNoBold:
                    testo = status ? "si" : "no";
                    span.setText(testo);
                    span.getStyle().set("font-weight", "bold");
                    if (status) {
                        span.getStyle().set("color", COLOR_VERO);
                    }
                    else {
                        span.getStyle().set("color", COLOR_FALSO);
                    }
                    return span;
                case yesNoReverse:
                    testo = status ? "no" : "si";
                    span.setText(testo);
                    if (status) {
                        span.getStyle().set("color", COLOR_FALSO);
                    }
                    else {
                        span.getStyle().set("color", COLOR_VERO);
                    }
                    return span;
                case customLabel:
                    customText = annotationService.getCustomBoolean(entityClazz, propertyName);
                    if (textService.isValid(customText)) {
                        customVero = textService.levaCodaDaPrimo(customText, VIRGOLA);
                        customFalso = textService.levaPrimaAncheTag(customText, VIRGOLA);
                        testo = status ? customVero : customFalso;
                        span.setText(testo);
                        if (status) {
                            span.getStyle().set("color", "green");
                        }
                        else {
                            span.getStyle().set("color", "red");
                        }
                    }
                    return span;

                case simpleCheckIcon:
                    if (status) {
                        icon = new Icon(VaadinIcon.CHECK);
                    }
                    else {
                        return null;
                    }
                    icon.setSize("1em");
                    return icon;

                default:
                    break;
            }

            return new Span("g");
        }));//end of lambda expressions and anonymous inner class

        return colonna;
    }


    public Grid.Column<AbstractEntity> addLocalDateTime(final Grid grid, Class<? extends AbstractEntity> entityClazz, final String propertyName) {
        Grid.Column<AbstractEntity> colonna;
        final TypeDate typeDate = annotationService.getTypeDate(entityClazz, propertyName);

        colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
            Object value;
            Span span = new Span();

            try {
                value = reflectionService.getPropertyValue((AbstractEntity) entity, propertyName);
                if (value instanceof LocalDateTime dateTime) {
                    span.setText(dateService.get(dateTime, typeDate));
                }
                if (value instanceof LocalDate date) {
                    span.setText(dateService.get(date, typeDate));
                }
                if (value instanceof LocalTime time) {
                    span.setText(dateService.get(time, typeDate));
                }
            } catch (Exception unErrore) {
                //                logger.error(unErrore.toString());
            }

            return span;
        }));//end of lambda expressions and anonymous inner class

        return colonna.setWidth(typeDate.getWidthEM()).setFlexGrow(0);
    }


    public Grid.Column<AbstractEntity> addPreferenza(final Grid grid, final String propertyName) {
        Grid.Column<AbstractEntity> colonna;
        String headerText = textService.primaMaiuscola(propertyName);
        if (propertyName.equals(FIELD_NAME_PREF_INIZIALE)) {
            headerText = "Default";//non posso metterlo come nome della property perché 'default' è una key word per java
        }

        colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
            Span span = new Span();
            Icon icon;
            TypePref type;
            byte[] bytes = null;

            if (entity instanceof PreferenzaEntity pref) {
                type = pref.getType();
                if (propertyName.equals(FIELD_NAME_PREF_INIZIALE)) {
                    bytes = pref.getIniziale();
                }
                if (propertyName.equals(FIELD_NAME_PREF_CORRENTE)) {
                    bytes = pref.getCorrente();
                }
                if (bytes == null) {
                    return span;
                }

                switch (type) {
                    case string, integer, lungo, email: {
                        span.setText(type.bytesToString(bytes));
                        span.getElement().getStyle().set("color", type.getColor());
                        span.getElement().getStyle().set("fontWeight", "bold");
                        return span;
                    }
                    case localdate, localtime, localdatetime: {
                        span.setText(type.bytesToString(bytes));
                        span.getElement().getStyle().set("color", type.getColor());
                        span.getElement().getStyle().set("fontWeight", "bold");
                        return span;
                    }
                    case bool: {
                        if ((boolean) type.bytesToObject(bytes)) {
                            icon = new Icon(VaadinIcon.CHECK);
                            icon.setColor(COLOR_VERO);
                        }
                        else {
                            icon = new Icon(VaadinIcon.CLOSE);
                            icon.setColor(COLOR_FALSO);
                        }
                        icon.setSize("1em");
                        return icon;
                    }
                    default: {

                    }
                }
            }

            return span;
        }));//end of lambda expressions and anonymous inner class

        if (colonna != null) {
            colonna.setKey(propertyName).setHeader(headerText).setWidth(HTML_WIDTH_UNIT + 12).setFlexGrow(0);
        }

        return colonna;
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
    public boolean check(final Grid grid, final Class genericClazz, final String propertyName, String methodName) {
        String message;
        Class modelClazz;

        // Controlla che la grid in ingresso non sia nulla
        if (grid == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la grid -> %s", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(message);
            return false;
        }

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel metodo '%s' di [%s], manca la model clazz '%s'", methodName, this.getClass().getSimpleName(), "(null)");
            logger.warn(message);
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz)) {
            modelClazz = genericClazz;
        }
        else {
            message = String.format("Nel metodo '%s' di [%s], la classe '%s' non implementa AlgosModel", methodName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            logger.warn(message);
            return false;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (textService.isEmpty(propertyName)) {
            message = String.format("Nel metodo '%s' di [%s], manca il parametro '%s'", methodName, this.getClass().getSimpleName(), "propertyName");
            logger.warn(message);
            return false;
        }

        // Controlla che il parametro in ingresso esista nella classe
        if (reflectionService.nonEsiste(modelClazz, propertyName)) {
            message = String.format("Nella classe [%s], non esiste la property '%s'", modelClazz.getSimpleName(), propertyName);
            logger.warn(message);
            return false;
        }

        return true;
    }

}