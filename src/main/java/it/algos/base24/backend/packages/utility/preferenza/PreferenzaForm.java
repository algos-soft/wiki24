package it.algos.base24.backend.packages.utility.preferenza;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.form.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.time.*;
import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class PreferenzaForm extends CrudForm {


    public PreferenzaForm(PreferenzaModulo crudModulo, PreferenzaEntity entityBean, CrudOperation operation) {
        this.currentCrudModulo = crudModulo;
        this.currentEntityModel = entityBean;
        this.crudOperation = operation;
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.numResponsiveStepColumn = 1;
    }

    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addFields() {
        super.addFields();

        mappaFields.get("code").setEnabled(false);
        mappaFields.get("type").setEnabled(false);
        mappaFields.get("iniziale").setEnabled(false);
    }

    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected void fixBottom() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("buttons");
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(false);
        buttonLayout.setClassName("confirm-dialog-buttons");

        Div elasticSpace = new Div();
        elasticSpace.getStyle().set("flex-grow", "1");

        Button delete = new Button(BUTTON_DELETE);
        delete.setIcon(new Icon(VaadinIcon.TRASH));
        delete.addClickListener(e -> deleteHandler());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-left", "auto");
        delete.getStyle().set("margin-inline-end", "auto");

        Button iniziale = new Button("Default");
        iniziale.setIcon(new Icon(VaadinIcon.REFRESH));
        iniziale.addClickListener(e -> defaultHandler());

        Button annulla = new Button(BUTTON_CANCELLA);
        annulla.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        annulla.addClickListener(e -> annullaHandler());

        Button registra = new Button(BUTTON_REGISTRA);
        registra.setIcon(new Icon(VaadinIcon.CHECK));
        registra.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registra.addClickListener(e -> saveHandler());

        switch (crudOperation) {
            case shows -> buttonLayout.add(annulla);
            case add -> buttonLayout.add(annulla, registra);
            case update, delete -> buttonLayout.add(delete, elasticSpace, iniziale, annulla, registra);
        } ;

        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        this.getFooter().add(buttonLayout);
    }

    protected void defaultHandler() {
        Object iniziale = mappaFields.get("iniziale").getValue();
        mappaFields.get("corrente").setValue(iniziale);
    }

    /**
     * Valori dal modello alla GUI degli eventuali fields extra binder <br>
     * Chiamato da fixBody() alla creazione del Form <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void valueModelToPresentationFields() {
        PreferenzaEntity pref = (PreferenzaEntity) currentEntityModel;
        TypePref type = pref.getType();

        if (type == null) {
            logger.warn(new WrapLog().exception(new AlgosException("Type senza valore")));
            return;
        }

        setFieldValue(FIELD_NAME_PREF_INIZIALE, type, pref.getIniziale());
        setFieldValue(FIELD_NAME_PREF_CORRENTE, type, pref.getCorrente());
    }


    protected void setFieldValue(String keyNameField, TypePref type, byte[] bytes) {
        AbstractField field = null;
        Class enumClazz;
        List enumObjects;
        List<String> enumStringhe;
        //                ComboField aField;
        Pref enumPref;
        String tag = "Valore";
        String caption;

        field = switch (type) {
            case string -> {
                caption = String.format("%s %s (string)", tag, keyNameField);
                TextField textField = new TextField(caption);
                textField.setRequired(true);
                textField.setValue(type.bytesToString(bytes));
                yield textField;
            }
            case bool -> {
                caption = String.format("%s %s (boolean)", tag, keyNameField);
                Checkbox boxField = new Checkbox(caption);
                boxField.setValue((boolean) type.bytesToObject(bytes));
                yield boxField;
            }
            case integer -> {
                caption = String.format("%s %s (intero)", tag, keyNameField);
                IntegerField intField = new IntegerField(caption);
                intField.setRequired(true);
                intField.setValue((int) type.bytesToObject(bytes));
                yield intField;
            }

            case localdatetime -> {
                caption = String.format("Data completa %s (giorno e orario)", keyNameField);
                DateTimePicker pickerField = new DateTimePicker(caption);
                pickerField.setValue((LocalDateTime) type.bytesToObject(bytes));
                yield pickerField;
            }

            //            case localdate -> {
            //                DatePicker pickerField = new DatePicker("Data (giorno)");
            //                pickerField.setValue((LocalDate) type.getValue().bytesToObject(currentItem.getValue()));
            //                pickerField.setReadOnly(operation == CrudOperation.DELETE);
            //                pickerField.setRequired(true);
            //                valueLayout.add(pickerField);
            //            }
            //            case localtime -> {
            //                TimePicker pickerField = new TimePicker("Time (orario)");
            //                pickerField.setValue((LocalTime) type.getValue().bytesToObject(currentItem.getValue()));
            //                pickerField.setReadOnly(operation == CrudOperation.DELETE);
            //                pickerField.setRequired(true);
            //                valueLayout.add(pickerField);
            //            }

            //            case percentuale -> {
            //                String numTxt;
            //                TextField percentField = new TextField("Value (percentuale)");
            //                if (operation != CrudOperation.ADD) {
            //                    Object obj = type.getValue().bytesToObject(currentItem.getValue());
            //                    if (obj instanceof Integer numero) {
            //                        if (numero > 9999) {
            //                            logger.warn(new WrapLog().message(String.format("Valore %d è troppo grande per una percentuale", numero)));
            //                            break;
            //                        }
            //                        numTxt = numero + VUOTA;
            //                        if (numero > 100) {
            //                            numTxt = numTxt.substring(0, numTxt.length() - 2) + VIRGOLA + numTxt.substring(numTxt.length() - 2);
            //                        }
            //                        numTxt += PERCENTUALE;
            //                        if (operation != CrudOperation.ADD) {
            //                            percentField.setValue(numTxt);
            //                            percentField.setReadOnly(operation == CrudOperation.DELETE);
            //                        }
            //                    }
            //                }
            //                valueLayout.add(percentField);
            //            }
            //            case decimal -> {
            //                BigDecimalField bigDecimalField = new BigDecimalField();
            //                bigDecimalField.setLabel("Value (BigDecimal)");
            //                bigDecimalField.setWidth("240px");
            //
            //                if (operation != CrudOperation.ADD) {
            //                    BigDecimal decimal = (BigDecimal) type.getValue().bytesToObject(currentItem.getValue());
            //                    bigDecimalField.setValue(decimal);
            //                    bigDecimalField.setReadOnly(operation == CrudOperation.DELETE);
            //                }
            //                valueLayout.add(bigDecimalField);
            //            }
            //
            //
            //            case enumerationString -> {
            //                ComboBox<String> combo = new ComboBox<>();
            //                combo.setRequired(true);
            //                combo.setAllowCustomValue(false);
            //                combo.setClearButtonVisible(false);
            //                combo.setReadOnly(false);
            //                combo.addValueChangeListener(event -> sincroCombo());
            //                String allEnumSelection = (String) type.getValue().bytesToObject(currentItem.getValue());
            //                String allValues = textService.getEnumAll(allEnumSelection);
            //                String selectedValue = textService.getEnumValue(allEnumSelection);
            //                List<String> items = arrayService.getList(allValues);
            //                if (items != null) {
            //                    combo.setItems(items);
            //                    if (items.contains(selectedValue)) {
            //                        combo.setValue(selectedValue);
            //                    }
            //                }
            //                valueLayout.add(combo);
            //            }
            //
            //            case enumerationType -> {
            //                enumStringhe = new ArrayList<>();
            //                enumPref = preferenceService.getPref(currentItem.code);
            //                enumClazz = enumPref.getEnumClazz();
            //                Object[] elementi = ((Class<?>) enumClazz).getEnumConstants();
            //                for (Object obj : elementi) {
            //                    if (obj instanceof AITypePref enumeration) {
            //                        enumStringhe.add(enumeration.toString());
            //                    }
            //                }
            //
            //                if (elementi != null) {
            //                    aField = new AComboField(enumStringhe);
            //                    aField.setLabel(currentItem.code);
            //                    aField.setSizeFull();
            //
            //                    String selectedValue = (String) type.getValue().bytesToObject(currentItem.getValue());
            //                    if (enumStringhe.contains(selectedValue)) {
            //                        aField.setValue(selectedValue);
            //                    }
            //                    valueLayout.add(aField);
            //                }
            //                    }

            default -> null;
        };

        if (field != null) {
            mappaFields.put(keyNameField, field);
        }
    }


    /**
     * Valori dagli eventuali fields extra binder della GUI al modello dati <br>
     * Chiamato da saveHandler() alla registrazione delle modifiche <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void valuePresentationFieldsToModel() {
        super.valuePresentationFieldsToModel();
        PreferenzaEntity pref = (PreferenzaEntity) currentEntityModel;
        TypePref type = pref.getType();
        byte[] bytes;

        bytes = getFieldValue(FIELD_NAME_PREF_INIZIALE, type);
        pref.setIniziale(bytes);
        bytes = getFieldValue(FIELD_NAME_PREF_CORRENTE, type);
        pref.setCorrente(bytes);
    }


    public void getFieldValuez(String keyNameField) {
        PreferenzaEntity pref = (PreferenzaEntity) currentEntityModel;
        TypePref type = pref.getType();
        boolean valido = false;
        Component field = mappaFields.get(keyNameField);
        //        valueLayout.getComponentAt(0);
        Component comp;
        Object timeValue;
        String message;

        switch (type) {
            case string -> {
                if (field != null && field instanceof TextField textField) {
                    if (textService.isValid(textField.getValue())) {
                        try {
                            pref.setCorrente(type.objectToBytes(textField.getValue()));
                        } catch (Exception unErrore) {
                            //                            logger.error(unErrore);
                        }
                    }
                    else {
                        //                        Avviso.message("Manca il valore").error().open();
                        //                        logger.info(new WrapLog().exception(new AlgosException("Manca il valore della preferenza che non può essere vuoto")));
                    }
                }
            }
            case bool -> {
                if (field != null && field instanceof Checkbox checkField) {
                    pref.setCorrente(type.objectToBytes(checkField.getValue()));
                }
            }

            case integer -> {
                if (field != null && field instanceof IntegerField intField) {
                    pref.setCorrente(type.objectToBytes(intField.getValue()));
                }
            }

            case localdatetime -> {
                if (field != null && field instanceof DateTimePicker pickerField) {
                    pref.setCorrente(type.objectToBytes(pickerField.getValue()));
                }
            }

            default -> {}
        }
    }

    protected byte[] getFieldValue(String keyNameField, TypePref type) {
        byte[] bytes = null;

        //        PreferenzaEntity pref = (PreferenzaEntity) currentEntityModel;
        //        TypePref type = pref.getType();
        boolean valido = false;
        Component field = mappaFields.get(keyNameField);
        //        valueLayout.getComponentAt(0);
        Component comp;
        Object timeValue;
        String message;

        switch (type) {
            case string -> {
                if (field != null && field instanceof TextField textField) {
                    if (textService.isValid(textField.getValue())) {
                        try {
                            bytes = type.objectToBytes(textField.getValue());
                        } catch (Exception unErrore) {
                            //                            logger.error(unErrore);
                        }
                    }
                    else {
                        //                        Avviso.message("Manca il valore").error().open();
                        //                        logger.info(new WrapLog().exception(new AlgosException("Manca il valore della preferenza che non può essere vuoto")));
                    }
                }
            }
            case bool -> {
                if (field != null && field instanceof Checkbox checkField) {
                    bytes = type.objectToBytes(checkField.getValue());
                }
            }

            case integer -> {
                if (field != null && field instanceof IntegerField intField) {
                    bytes = type.objectToBytes(intField.getValue());
                }
            }

            case localdatetime -> {
                if (field != null && field instanceof DateTimePicker pickerField) {
                    bytes = type.objectToBytes(pickerField.getValue());
                }
            }

            default -> {}
        }

        return bytes;
    }

}// end of CrudForm class
