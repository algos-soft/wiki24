package it.algos.vaad24.ui.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.*;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.fields.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.vaadin.crudui.crud.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 02-apr-2022
 * Time: 07:39
 */
public abstract class CrudDialog extends Dialog {

    protected final H2 titleField = new H2();

    /**
     * Corpo centrale del Form <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final FormLayout formLayout = new FormLayout();

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnotationService annotationService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ReflectionService reflectionService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public HtmlService htmlService;

    @Autowired
    public LogService logger;

    protected CrudBackend crudBackend;

    protected String currentEntityNameForTitle;

    protected String textAnnullaButton = "Annulla";

    protected String textSaveButton = "Registra";

    protected String textDeleteButton = "Cancella";

    protected Button annullaButton = new Button(textAnnullaButton);

    protected Button saveButton;

    protected Button deleteButton;

    protected BiConsumer<AEntity, CrudOperation> saveHandler;

    protected Consumer<AEntity> deleteHandler;

    protected Consumer<AEntity> annullaHandler;

    //--collegamento tra i fields e la entityBean
    protected BeanValidationBinder<AEntity> binder;

    protected AEntity currentItem;

    protected CrudOperation operation;

    protected List<String> fields;

    protected boolean usaUnaSolaColonna = true;

    protected HorizontalLayout bottomPlaceHolder;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public CrudDialog() {
    }// end of second constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(CrudDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean  The item to edit; it may be an existing or a newly created instance
     * @param operation   The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     * @param fields      da costruire in automatico
     */
    public CrudDialog(final AEntity entityBean, final CrudOperation operation, final CrudBackend crudBackend, final List<String> fields) {
        this(entityBean, operation, crudBackend, fields, true);
    }// end of constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(CrudDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean        The item to edit; it may be an existing or a newly created instance
     * @param operation         The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend       service specifico per la businessLogic e il collegamento con la persistenza dei dati
     * @param fields            da costruire in automatico
     * @param usaUnaSolaColonna di default=true
     */
    public CrudDialog(final AEntity entityBean, final CrudOperation operation, final CrudBackend crudBackend, final List<String> fields, final boolean usaUnaSolaColonna) {
        this.currentItem = entityBean;
        this.operation = operation;
        this.crudBackend = crudBackend;
        this.fields = fields;
        this.usaUnaSolaColonna = usaUnaSolaColonna;
    }// end of constructor not @Autowired


    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        //--Preferenze usate da questa 'logica'
        this.fixPreferenze();

        //--Titolo placeholder del dialogo
        this.fixHeader();

        //--Form placeholder standard per i campi
        this.add(getFormLayout());

        //--Crea un nuovo binder (vuoto) per questo Dialog e questa entityBean (currentItem)
        binder = new BeanValidationBinder(currentItem.getClass());

        //--Crea i fields
        //--Corpo centrale del Dialog
        fixBody();

        // Regola il binder
        // Updates the value in each bound field component
        fixBinder();

        //--spazio per distanziare i bottoni sottostanti
        this.add(new H3());

        //--Barra placeholder dei bottoni, creati e regolati
        this.fixBottom();
    }

    /**
     * Preferenze (eventuali) usate da questo 'dialogo' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        currentEntityNameForTitle = currentItem.getClass().getSimpleName().toLowerCase();
    }

    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected void fixHeader() {
        String message;

        String tag = switch (operation) {
            case READ -> "Mostra";
            case ADD -> "Nuova";
            case UPDATE, DELETE -> "Modifica";
        };

        message = String.format("%s %s", tag, currentEntityNameForTitle);
        Label label = new Label(message);
        label.getStyle().set("font-weight", "bold");
        label.getStyle().set("font-size", "1.2em");
        add(label);
    }

    /**
     * Body placeholder per i campi <br>
     * Normalmente colonna doppia <br>
     */
    protected Div getFormLayout() {
        // Use one column by default
        if (usaUnaSolaColonna) {
            formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        }
        else {
            formLayout.setResponsiveSteps(
                    // Use one column by default
                    new FormLayout.ResponsiveStep("0", 1),
                    // Use two columns, if layout's width exceeds 500px
                    new FormLayout.ResponsiveStep("500px", 2)
            );
        }

        formLayout.addClassName("no-padding");
        Div div = new Div(formLayout);
        div.addClassName("has-padding");

        return div;
    }


    /**
     * Crea i fields
     * Inizializza le properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i fields al binder
     * Aggiunge eventuali fields specifici direttamente al layout grafico (senza binder e senza fieldMap)
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void fixBody() {
        AETypeField type;
        AbstractField field;
        AComboField aField;
        Class enumClazz;
        Class linkClazz;
        boolean hasFocus = false;
        String caption;
        String valoriEnum;
        List enumObjects;
        List items;
        ComboBox combo;
        boolean nullSelectionAllowed;
        EnumTypeField enumField;

        try {
            for (String key : fields) {
                type = annotationService.getFormType(currentItem.getClass(), key);
                hasFocus = annotationService.hasFocus(currentItem.getClass(), key);
                caption = annotationService.getCaption(currentItem.getClass(), key); ;
                nullSelectionAllowed = annotationService.nullSelectionAllowed(currentItem.getClass(), key);

                if (type == AETypeField.listaH) {
                    AListaFieldH cField = new AListaFieldH(caption);
                    formLayout.add(cField);
                    binder.forField(cField).bind(key);
                    continue;
                }
                if (type == AETypeField.listaV) {
                    AListaFieldV cField = new AListaFieldV(caption);
                    formLayout.add(cField);
                    binder.forField(cField).bind(key);
                    continue;
                }

                if (!type.isCustomField()) {
                    field = switch (type) {
                        case text -> new TextField(caption);
                        case integer -> new IntegerField(caption);
                        case lungo -> new TextField(caption);
                        case booleano -> new Checkbox(caption);
                        case enumType -> {
                            combo = new ComboBox(caption);
                            combo.setClearButtonVisible(nullSelectionAllowed);
                            try {
                                enumClazz = annotationService.getEnumClazz(currentItem.getClass(), key);
                                Object[] elementi = enumClazz.getEnumConstants();
                                if (elementi != null) {
                                    enumObjects = Arrays.asList(elementi);
                                    if (enumObjects != null) {
                                        combo.setItems(enumObjects);
                                    }
                                }
                            } catch (Exception unErrore) {
                                logger.error(new WrapLog().exception(unErrore).usaDb());
                            }
                            yield combo;
                        }
                        case enumString -> {
                            combo = new ComboBox(caption);
                            combo.setClearButtonVisible(nullSelectionAllowed);
                            try {
                                enumObjects = annotationService.getListaValoriEnum(currentItem.getClass(), key);
                                if (enumObjects != null) {
                                    combo.setItems(enumObjects);
                                }
                            } catch (Exception unErrore) {
                                logger.error(new WrapLog().exception(unErrore).usaDb());
                            }
                            yield combo;
                        }
                        case linkDinamico, linkStatico -> {
                            combo = new ComboBox(caption);
                            combo.setClearButtonVisible(nullSelectionAllowed);

                            try {
                                linkClazz = annotationService.getLinkClazz(currentItem.getClass(), key);
                                Object obj = appContext.getBean(linkClazz);
                                if (obj instanceof CrudBackend backend) {
                                    items = backend.findAllSortCorrente();
                                    if (items != null) {
                                        combo.setItems(items);
                                    }
                                }
                            } catch (Exception unErrore) {
                                logger.error(new WrapLog().exception(unErrore).usaDb());
                            }
                            yield combo;
                        }
                        case listaH, listaV -> null;
                        case localDateTime -> new DateTimePicker(caption);
                        case localDate -> new DatePicker(caption);
                        case localTime -> new TimePicker(caption);
                        case textArea -> new TextArea(caption);
                        default -> {
                            logger.error(new WrapLog().exception(new AlgosException("Manca il case dello switch")).usaDb());
                            yield new TextField(key);
                        }
                    };
                    if (field == null) {
                        continue;
                    }

                    formLayout.add(field);
                    if (type == AETypeField.lungo) {
                        String message = String.format("%s deve contenere solo caratteri numerici", key);
                        StringToLongConverter longConverter = new StringToLongConverter(0L, message);
                        binder.forField(field)
                                .withConverter(longConverter)
                                .bind(key);
                    }
                    else {
                        binder.forField(field).bind(key);
                    }

                    if (hasFocus && field instanceof TextField textField) {
                        textField.focus();
                        textField.setAutoselect(true);
                    }
                }
                else {
                    aField = switch (type) {
                        case enumType -> {
                            aField = new AComboField();
                            try {
                                enumClazz = annotationService.getEnumClazz(currentItem.getClass(), key);
                                Object[] elementi = enumClazz.getEnumConstants();
                                if (elementi != null) {
                                    enumObjects = Arrays.asList(elementi);
                                    aField = new AComboField(enumObjects);
                                    aField.setLabel(key);
                                    aField.setSizeFull();
                                }
                            } catch (Exception unErrore) {
                                logger.error(new WrapLog().exception(unErrore).usaDb());
                            }
                            yield aField;
                        }
                        default -> {
                            logger.error(new WrapLog().exception(new AlgosException("Manca il case dello switch")).usaDb());
                            yield null;
                        }
                    };

                    formLayout.add(aField);
                    binder.forField(aField).bind(key);
                }
            }
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }
    }


    /**
     * Legge la entityBean e inserisce nella UI i valori di eventuali fields NON associati al binder
     */
    protected void fixBinder() {
        try {
            binder.bindInstanceFields(this);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

        // Updates the value in each bound field component
        try {
            binder.readBean(currentItem);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

    }

    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected void fixBottom() {
        //        bottomPlaceHolder = new HorizontalLayout();
        //        bottomPlaceHolder.setClassName("buttons");
        //        bottomPlaceHolder.setPadding(false);
        //        bottomPlaceHolder.setSpacing(true);
        //        bottomPlaceHolder.setMargin(false);
        //        bottomPlaceHolder.setClassName("confirm-dialog-buttons");

        Div elasticSpace = new Div();
        elasticSpace.getStyle().set("flex-grow", "1");

        if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
            deleteButton = new Button(textDeleteButton);
            deleteButton.getElement().setAttribute("theme", "primary");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> deleteHandler());
            deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
            deleteButton.getStyle().set("margin-left", "auto");
            deleteButton.getElement().setProperty("title", "Shortcut SHIFT+D");
            deleteButton.addClickShortcut(Key.KEY_D, KeyModifier.SHIFT);
            super.getFooter().add(deleteButton);
            super.getFooter().add(elasticSpace);
        }

        annullaButton.setText(textAnnullaButton);
        annullaButton.getElement().setAttribute("theme", "secondary");
        //        annullaButton.getElement().setProperty("title", "Shortcut SHIFT");
        annullaButton.addClickListener(e -> annullaHandler());
        annullaButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        super.getFooter().add(annullaButton);

        if (operation != CrudOperation.READ) {
            saveButton = new Button(textSaveButton);
            saveButton.getElement().setAttribute("theme", "primary");
            saveButton.addClickListener(e -> saveHandler());
            saveButton.setIcon(new Icon(VaadinIcon.CHECK));
            super.getFooter().add(saveButton);
        }

        switch (operation) {
            case READ -> {}
            case ADD -> {
                annullaButton.getElement().setProperty("title", "Shortcut SHIFT+freccia sinistra");
                annullaButton.addClickShortcut(Key.ARROW_LEFT, KeyModifier.SHIFT);
                if (saveButton != null) {
                    saveButton.getElement().setProperty("title", "Shortcut ENTER");
                    saveButton.addClickShortcut(Key.ENTER);
                }
            }
            case UPDATE -> {
                annullaButton.getElement().setProperty("title", "Shortcut ENTER");
                annullaButton.addClickShortcut(Key.ENTER);
                if (saveButton != null) {
                    saveButton.getElement().setProperty("title", "Shortcut SHIFT+ENTER");
                    saveButton.addClickShortcut(Key.ENTER, KeyModifier.SHIFT);
                }
            }
            case DELETE -> {
                annullaButton.getElement().setProperty("title", "Shortcut ENTER");
                annullaButton.addClickShortcut(Key.ENTER);
            }
        }

        //--Controlla la visibilità dei bottoni
        if (saveButton != null) {
            saveButton.setVisible(operation != CrudOperation.READ);
        }
    }

    /**
     * Opens the given item for editing in the dialog. <br>
     * Crea i fields e visualizza il dialogo <br>
     * Gli handler vengono aggiunti qui perché non passano come parametri di appContext.getBean(PreferenzaDialog.class) <br>
     * La view è già pronta, i listener anche e rimane solo da lanciare il metodo open() nella superclasse <br>
     *
     * @param saveHandler    funzione associata al bottone 'accetta' ('registra', 'conferma')
     * @param annullaHandler funzione associata al bottone 'annulla' (bottone obbligatorio, azione facoltativa)
     */
    public void open(final BiConsumer<AEntity, CrudOperation> saveHandler, final Consumer<AEntity> annullaHandler) {
        this.open(saveHandler, null, annullaHandler);
    }

    /**
     * Opens the given item for editing in the dialog. <br>
     * Crea i fields e visualizza il dialogo <br>
     * Gli handler vengono aggiunti qui perché non passano come parametri di appContext.getBean(PreferenzaDialog.class) <br>
     * La view è già pronta, i listener anche e rimane solo da lanciare il metodo open() nella superclasse <br>
     *
     * @param saveHandler    funzione associata al bottone 'accetta' ('registra', 'conferma')
     * @param deleteHandler  funzione associata al bottone 'delete' (eventuale)
     * @param annullaHandler funzione associata al bottone 'annulla' (bottone obbligatorio, azione facoltativa)
     */
    public void open(final BiConsumer<AEntity, CrudOperation> saveHandler, final Consumer<AEntity> deleteHandler, final Consumer<AEntity> annullaHandler) {
        this.saveHandler = saveHandler;
        this.deleteHandler = deleteHandler;
        this.annullaHandler = annullaHandler;

        super.open();
    }


    public void saveHandler() {
        try {
            if (binder.writeBeanIfValid(currentItem)) {
                binder.writeBean(currentItem);
            }
            else {
                logger.info(new WrapLog().exception(new AlgosException("binder non valido")));
                return;
            }
        } catch (ValidationException error) {
            logger.error(error);
            return;
        }
        crudBackend.save(currentItem);
        switch (operation) {
            case ADD -> Avviso.message("Aggiunto un elemento").success().open();
            case UPDATE, DELETE -> Avviso.message("Registrata la modifica").success().open();
            default -> Avviso.message(SWITCH).error().open();
        }

        if (saveHandler != null) {
            saveHandler.accept(currentItem, operation);
        }
        close();
    }

    /**
     * La cancellazione avviene qui (nel dialogo) <br>
     * Delega il service di xxxBackend <br>
     * Poi rimanda al chiamante (se esiste) per eventuali altre incombenze (tipo reload della view) <br>
     */
    public void deleteHandler() {
        AEntity item = currentItem;

        if (item != null && crudBackend != null) {
            crudBackend.delete(item);
        }

        if (deleteHandler != null) {
            deleteHandler.accept(item);
        }

        close();
    }

    public void annullaHandler() {
        switch (operation) {
            case ADD -> Avviso.message("Non registrato").primary().open();
            case READ -> Avviso.message("Letto").success().open();
            case UPDATE -> Avviso.message("Non modificato").primary().open();
            case DELETE -> Avviso.message("Non cancellato").primary().open();
            default -> Avviso.message("Caso non previsto").error().open();
        }
        if (annullaHandler != null) {
            annullaHandler.accept(currentItem);
        }
        close();
    }

}// end of crud generic Dialog class