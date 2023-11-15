package it.algos.base24.backend.packages.utility.preferenza;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.datetimepicker.*;
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
     * Valori dal modello alla GUI degli eventuali fields extra binder <br>
     * Chiamato da fixBody() alla creazione del Form <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void valueModelToPresentationFields() {
        AbstractField field = null;
        Class enumClazz;
        List enumObjects;
        List<String> enumStringhe;
        //                ComboField aField;
        Pref enumPref;
        PreferenzaEntity pref = (PreferenzaEntity) currentEntityModel;
        TypePref type = pref.getType();

        if (type == null) {
            logger.warn(new WrapLog().exception(new AlgosException("Type senza valore")));
            return;
        }

        field = switch (type) {
            case string -> {
                TextField textField = new TextField("Value (string)");
                textField.setRequired(true);
                textField.setValue(type.bytesToString(pref.getValue()));
                yield textField;
            }
            case bool -> {
                Checkbox boxField = new Checkbox("Value (boolean)");
                boxField.setValue((boolean) type.bytesToObject(pref.getValue()));
                yield boxField;
            }
            case integer -> {
                IntegerField intField = new IntegerField("Value (intero)");
                intField.setRequired(true);
                intField.setValue((int) type.bytesToObject(pref.getValue()));
                yield intField;
            }

            case localdatetime -> {
                DateTimePicker pickerField = new DateTimePicker("Data completa (giorno e orario)");
                pickerField.setValue((LocalDateTime) type.bytesToObject(pref.getValue()));
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
            mappaFields.put(FIELD_NAME_VALUE, field);
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
        boolean valido = false;
        Component field = mappaFields.get(FIELD_NAME_VALUE);
        //        valueLayout.getComponentAt(0);
        Component comp;
        Object timeValue;
        String message;

        switch (type) {
            case string -> {
                if (field != null && field instanceof TextField textField) {
                    if (textService.isValid(textField.getValue())) {
                        try {
                            pref.setValue(type.objectToBytes(textField.getValue()));
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
                    pref.setValue(type.objectToBytes(checkField.getValue()));
                }
            }

            case integer -> {
                if (field != null && field instanceof IntegerField intField) {
                    pref.setValue(type.objectToBytes(intField.getValue()));
                }
            }

            case localdatetime -> {
                if (field != null && field instanceof DateTimePicker pickerField) {
                    pref.setValue(type.objectToBytes(pickerField.getValue()));
                }
            }

            default -> {}
        }
    }

}// end of CrudForm class
