package it.algos.vaad24.backend.packages.utility.preferenza;

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
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.*;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.fields.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.*;

import javax.annotation.*;
import java.time.*;
import java.util.*;
import java.util.function.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 27-mar-2022
 * Time: 13:42
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreferenzaDialog extends Dialog {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    protected final H2 titleField = new H2();

    /**
     * Corpo centrale del Form <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final FormLayout formLayout = new FormLayout();

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    @Autowired
    public LogService logger;

    @Autowired
    public AnnotationService annotationService;

    @Autowired
    public ArrayService arrayService;

    @Autowired
    public PreferenceService preferenceService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public PreferenzaBackend preferenzaBackend;

    protected String textAnnullaButton = "Annulla";

    protected String textSaveButton = "Registra";

    protected String textDeleteButton = "Si, cancella";

    protected Button annullaButton = new Button(textAnnullaButton);

    protected Button saveButton = new Button(textSaveButton);

    protected Button deleteButton = new Button(textDeleteButton);

    //--collegamento tra i fields e la entityBean
    protected BeanValidationBinder<Preferenza> binder;

    TextField code;

    ComboBox<AETypePref> type;

    TextField descrizione;

    VerticalLayout valueLayout;

    TextArea descrizioneEstesa;

    Checkbox vaadFlow;

    Checkbox needRiavvio;

    Checkbox dinamica;

    private Preferenza currentItem;

    private BiConsumer<Preferenza, CrudOperation> saveHandler;

    private Consumer<Preferenza> deleteHandler;

    private Consumer<Preferenza> annullaHandler;

    private CrudOperation operation;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public PreferenzaDialog() {
    }// end of second constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(PreferenzaDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean The item to edit; it may be an existing or a newly created instance
     * @param operation  The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     */
    public PreferenzaDialog(Preferenza entityBean, CrudOperation operation) {
        this.currentItem = entityBean;
        this.operation = operation;
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
        //--Titolo placeholder del dialogo
        this.add(fixHeader());

        //--Form placeholder standard per i campi
        this.add(getFormLayout());
        //--Lasyout di value
        fixValue();
        //--Corpo centrale del Dialog
        fixBody();

        //--spazio per distanziare i bottoni sottostanti
        this.add(new H3());

        //--Barra placeholder dei bottoni, creati e regolati
        this.add(fixBottom());

        //--Listener varii
        this.fixListener();
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
    public void open(final BiConsumer<Preferenza, CrudOperation> saveHandler, final Consumer<Preferenza> deleteHandler, final Consumer<Preferenza> annullaHandler) {
        this.saveHandler = saveHandler;
        this.deleteHandler = deleteHandler;
        this.annullaHandler = annullaHandler;

        super.open();
    }

    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected Component fixHeader() {
        String tag = switch (operation) {
            case READ -> "Mostra";
            case ADD -> "Nuova";
            case UPDATE -> "Modifica";
            case DELETE -> "Cancella";
            default -> "Edit";
        };

        return new H2(String.format("%s preferenza", tag));
    }


    /**
     * Body placeholder per i campi <br>
     * Normalmente colonna doppia <br>
     */
    protected Div getFormLayout() {
        Div div;

        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 3)
        );

        formLayout.addClassName("no-padding");
        div = new Div(formLayout);
        div.addClassName("has-padding");

        return div;
    }

    protected void fixValue() {
        valueLayout = new VerticalLayout();
        valueLayout.setPadding(false);
        valueLayout.setSpacing(true);
        valueLayout.setMargin(false);
    }


    /**
     * Crea i fields
     * <p>
     * Crea un nuovo binder (vuoto) per questo Dialog e questa Entity
     * <p>
     * Inizializza le properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i fields al binder
     * <p>
     * Aggiunge eventuali fields specifici direttamente al layout grafico (senza binder e senza fieldMap)
     * Legge la entityBean ed inserisce nella UI i valori di eventuali fields NON associati al binder
     */
    protected void fixBody() {
        //--Crea un nuovo binder (vuoto) per questo Dialog e questa entityBean (currentItem)
        binder = new BeanValidationBinder(currentItem.getClass());

        code = new TextField("Code");
        code.setReadOnly(operation == CrudOperation.DELETE);

        type = new ComboBox("Type");
        type.setItems(AETypePref.values());
        type.setRequired(true);
        type.setReadOnly(operation == CrudOperation.DELETE || operation == CrudOperation.UPDATE);

        descrizione = new TextField("Descrizione");
        descrizione.setReadOnly(operation == CrudOperation.DELETE);

        descrizioneEstesa = new TextArea("Descrizione estesa");
        descrizioneEstesa.setReadOnly(operation == CrudOperation.DELETE);

        vaadFlow = new Checkbox("Programma base vaadin23");
        vaadFlow.setReadOnly(operation == CrudOperation.DELETE);

        needRiavvio = new Checkbox("Riavvio necessario");
        needRiavvio.setReadOnly(operation == CrudOperation.DELETE);

        dinamica = new Checkbox("Valore dinamico");
        dinamica.setReadOnly(operation == CrudOperation.DELETE);

        binder.bindInstanceFields(this);

        // Updates the value in each bound field component
        binder.readBean(currentItem);

        // Update the value of preference
        sincroValueToPresentation();

        formLayout.add(code, type, descrizione, valueLayout, descrizioneEstesa, vaadFlow, needRiavvio, dinamica);
        formLayout.setColspan(descrizione, 2);
        formLayout.setColspan(valueLayout, 2);
        formLayout.setColspan(descrizioneEstesa, 3);

    }

    protected void sincroValueToPresentation() {
        valueLayout.removeAll();
        Class enumClazz;
        List enumObjects;
        List<String> enumStringhe;
        AComboField aField;
        AIGenPref enumPref;
        AITypePref typePref;

        if (type.getValue() == null) {
            logger.warn(new WrapLog().exception(new AlgosException("Type senza valore")));
            return;
        }

        switch (type.getValue()) {
            case string -> {
                TextField textField = new TextField("Value (string)");
                textField.setValue(type.getValue().bytesToString(currentItem.getValue()));
                textField.setReadOnly(operation == CrudOperation.DELETE);
                textField.setRequired(true);
                valueLayout.add(textField);
            }
            case bool -> {
                Checkbox boxField = new Checkbox("Value (boolean)");
                boxField.setValue((boolean) type.getValue().bytesToObject(currentItem.getValue()));
                boxField.setReadOnly(operation == CrudOperation.DELETE);
                valueLayout.add(boxField);
            }
            case integer -> {
                TextField numberField = new TextField("Value (intero)");
                numberField.setRequired(true);
                if (operation != CrudOperation.ADD) {
                    numberField.setValue(type.getValue().bytesToObject(currentItem.getValue()) + VUOTA);
                    numberField.setReadOnly(operation == CrudOperation.DELETE);
                }
                valueLayout.add(numberField);
            }
            case localdatetime -> {
                DateTimePicker pickerField = new DateTimePicker("Data completa (giorno e orario)");
                pickerField.setValue((LocalDateTime) type.getValue().bytesToObject(currentItem.getValue()));
                pickerField.setReadOnly(operation == CrudOperation.DELETE);
                valueLayout.add(pickerField);
            }

            case localdate -> {
                DatePicker pickerField = new DatePicker("Data (giorno)");
                pickerField.setValue((LocalDate) type.getValue().bytesToObject(currentItem.getValue()));
                pickerField.setReadOnly(operation == CrudOperation.DELETE);
                pickerField.setRequired(true);
                valueLayout.add(pickerField);
            }
            case localtime -> {
                TimePicker pickerField = new TimePicker("Time (orario)");
                pickerField.setValue((LocalTime) type.getValue().bytesToObject(currentItem.getValue()));
                pickerField.setReadOnly(operation == CrudOperation.DELETE);
                pickerField.setRequired(true);
                valueLayout.add(pickerField);
            }

            case enumerationString -> {
                ComboBox<String> combo = new ComboBox<>();
                combo.setRequired(true);
                combo.setAllowCustomValue(false);
                combo.setClearButtonVisible(false);
                combo.setReadOnly(false);
                combo.addValueChangeListener(event -> sincroCombo());
                String allEnumSelection = (String) type.getValue().bytesToObject(currentItem.getValue());
                String allValues = textService.getEnumAll(allEnumSelection);
                String selectedValue = textService.getEnumValue(allEnumSelection);
                List<String> items = arrayService.getList(allValues);
                if (items != null) {
                    combo.setItems(items);
                    if (items.contains(selectedValue)) {
                        combo.setValue(selectedValue);
                    }
                }
                valueLayout.add(combo);
            }

            case enumerationType -> {
                enumStringhe = new ArrayList<>();
                enumPref = preferenceService.getPref(currentItem.code);
                enumClazz = enumPref.getEnumClazz();
                Object[] elementi = ((Class<?>) enumClazz).getEnumConstants();
                for (Object obj : elementi) {
                    if (obj instanceof AITypePref enumeration) {
                        enumStringhe.add(enumeration.toString());
                    }
                }

                if (elementi != null) {
                    aField = new AComboField(enumStringhe);
                    aField.setLabel(currentItem.code);
                    aField.setSizeFull();

                    String selectedValue = (String) type.getValue().bytesToObject(currentItem.getValue());
                    if (enumStringhe.contains(selectedValue)) {
                        aField.setValue(selectedValue);
                    }
                    valueLayout.add(aField);
                }
            }

            default -> valueLayout.add(new Label("Type non ancora gestito"));
        }
    }

    protected boolean sincroValueToModel() {
        boolean valido = false;
        Component comp = valueLayout.getComponentAt(0);
        Object timeValue;
        String message;

        switch (type.getValue()) {
            case string -> {
                if (comp != null && comp instanceof TextField textField) {
                    if (textService.isValid(textField.getValue())) {
                        try {
                            currentItem.setValue(type.getValue().objectToBytes(textField.getValue()));
                            valido = true;
                        } catch (Exception unErrore) {
                            logger.error(unErrore);
                        }
                    }
                    else {
                        Avviso.message("Manca il valore").error().open();
                        logger.info(new WrapLog().exception(new AlgosException("Manca il valore della preferenza che non può essere vuoto")));
                    }
                }
            }
            case bool -> {
                if (comp != null && comp instanceof Checkbox checkField) {
                    currentItem.setValue(type.getValue().objectToBytes(checkField.getValue()));
                    valido = true;
                }
            }
            case integer -> {
                if (comp != null && comp instanceof TextField textField) {
                    currentItem.setValue(type.getValue().objectToBytes(textField.getValue()));
                    valido = true;
                }
            }

            case localdatetime -> {
                if (comp != null && comp instanceof DateTimePicker pickerField) {
                    timeValue = pickerField.getValue();
                    if (timeValue == null) {
                        timeValue = ROOT_DATA_TIME;
                        message = String.format("Alla preferenza [%s] manca la data%s%s", currentItem.code, FORWARD, timeValue);
                        logger.warn(new WrapLog().type(AETypeLog.preferenze).message(message).usaDb());
                    }
                    currentItem.setValue(type.getValue().objectToBytes(timeValue));
                    valido = true;
                }
            }

            case localdate -> {
                if (comp != null && comp instanceof DatePicker pickerField) {
                    timeValue = pickerField.getValue();
                    if (timeValue == null) {
                        timeValue = ROOT_DATA;
                        message = String.format("Alla preferenza [%s] manca la data%s%s", currentItem.code, FORWARD, timeValue);
                        logger.warn(new WrapLog().type(AETypeLog.preferenze).message(message).usaDb());
                    }
                    currentItem.setValue(type.getValue().objectToBytes(timeValue));
                    valido = true;
                }
            }
            case localtime -> {
                if (comp != null && comp instanceof TimePicker pickerField) {
                    timeValue = pickerField.getValue();
                    if (timeValue == null) {
                        timeValue = ROOT_TIME;
                        message = String.format("Alla preferenza [%s] manca la data%s%s", currentItem.code, FORWARD, timeValue);
                        logger.warn(new WrapLog().type(AETypeLog.preferenze).message(message).usaDb());
                    }
                    currentItem.setValue(type.getValue().objectToBytes(timeValue));
                    valido = true;
                }
            }
            case enumerationString -> {
                if (comp != null && comp instanceof ComboBox combo) {
                    String value = (String) combo.getValue();
                    String allEnumSelection = (String) type.getValue().bytesToObject(currentItem.getValue());
                    value = textService.setEnumValue(allEnumSelection, value);
                    currentItem.setValue(type.getValue().objectToBytes(value));
                    valido = true;
                }
            }
            case enumerationType -> {
                if (comp != null && comp instanceof AComboField combo) {
                    String value = (String) combo.getValue();
                    currentItem.setValue(type.getValue().objectToBytes(value));
                    valido = true;
                }
            }
            default -> Notification.show("Switch non previsto").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        return valido;
    }

    protected void sincroCombo() {
    }

    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected Component fixBottom() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("buttons");
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setClassName("confirm-dialog-buttons");

        Label spazioVuotoEspandibile = new Label("");

        annullaButton.setText(textAnnullaButton);
        annullaButton.getElement().setAttribute("theme", operation == CrudOperation.UPDATE ? "secondary" : "primary");
        annullaButton.addClickListener(e -> annullaHandler());
        annullaButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        annullaButton.addClickShortcut(Key.ARROW_LEFT, KeyModifier.CONTROL); //@todo non funziona
        layout.add(annullaButton);

        saveButton.setText(textSaveButton);
        saveButton.getElement().setAttribute("theme", operation == CrudOperation.UPDATE ? "primary" : "secondary");
        saveButton.addClickListener(e -> saveHandler());
        saveButton.setIcon(new Icon(VaadinIcon.CHECK));
        saveButton.addClickShortcut(Key.ENTER);
        layout.add(saveButton);

        deleteButton.setText(textDeleteButton);
        deleteButton.getElement().setAttribute("theme", "error");
        deleteButton.addClickListener(e -> deleteHandler());
        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        layout.add(deleteButton);

        layout.setFlexGrow(1, spazioVuotoEspandibile);

        //--Controlla la visibilità dei bottoni
        saveButton.setVisible(operation == CrudOperation.ADD || operation == CrudOperation.UPDATE);
        deleteButton.setVisible(operation == CrudOperation.DELETE);

        return layout;
    }

    public void fixListener() {
        type.addValueChangeListener(e -> sincroValueToPresentation());
    }


    public void saveHandler() {
        String message;
        Preferenza oldEntity = preferenzaBackend.findByKeyCode(currentItem.code);

        try {
            if (binder.writeBeanIfValid(currentItem) && sincroValueToModel()) {
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

        if (!currentItem.getValore().equals(oldEntity.getValore())) {
            if (!currentItem.isDinamica()) {
                switch (currentItem.type) {
                    case enumerationString -> {
                        String allValuesOld = (String) oldEntity.getValore();
                        String allValuesNew = (String) currentItem.getValore();
                        String valoriBase = textService.getEnumAll(allValuesOld);
                        String ante = textService.getEnumValue(allValuesOld);
                        String post = textService.getEnumValue(allValuesNew);

                        message = String.format("Modificata [%s] (%s): %s%s%s", currentItem.code, valoriBase, ante, FORWARD, post);
                    }
                    case enumerationType -> {
                        String enumName = currentItem.getEnumClazzName();
                        message = String.format("Modificata [%s] (%s): %s%s%s", currentItem.code, enumName, oldEntity.getValore(), FORWARD, currentItem.getValore());
                    }
                    default -> message = String.format("Modificata [%s]: %s%s%s", currentItem.code, oldEntity.getValore(), FORWARD, currentItem.getValore());
                }
                logger.info(new WrapLog().type(AETypeLog.preferenze).message(message).usaDb());
            }
        }

        preferenzaBackend.update(currentItem);
        switch (operation) {
            case ADD -> Avviso.message("Registrata la preferenza").success().open();
            case UPDATE -> Avviso.message("Registrata la modifica").success().open();
            default -> Avviso.message("Caso non previsto").error().open();
        }

        if (saveHandler != null) {
            saveHandler.accept(currentItem, operation);
        }
        close();
    }

    public void deleteHandler() {
        Preferenza item = currentItem;
        if (deleteHandler != null) {
            deleteHandler.accept(item);
        }
        close();
    }

    public void annullaHandler() {
        switch (operation) {
            case ADD -> Avviso.message("Preferenza non registrata").primary().open();
            case READ -> Avviso.message("Preferenza letta").success().open();
            case UPDATE -> Avviso.message("Preferenza non modificata").primary().open();
            case DELETE -> Avviso.message("Preferenza non cancellata").primary().open();
            default -> Avviso.message("Caso non previsto").error().open();
        }
        if (annullaHandler != null) {
            annullaHandler.accept(currentItem);
        }
        close();
    }

}