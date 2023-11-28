package it.algos.base24.backend.packages.utility.preferenza;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class PreferenzaList extends CrudList {


    private static String TEXT_NEWS = "Creazione automatica di tutti i valori. Non se ne possono creare di nuovi. Si può modificare la descrizione e il valore.";

    private static String TEXT_RESET_STARTUP = "ResetStartup aggiunge nuovi (eventuali) valori della enum lasciando inalterati tutti i valori e le descrizioni esistenti.";
    private static String TEXT_RESET_ADD = "ResetAdd aggiunge nuovi (eventuali) valori della enum e aggiorna tutte le descrizioni esistenti. I valori correnti rimangono inalterati.";

    private ComboBox comboType;

    private static String FIELD_TYPE = "type";

    public PreferenzaList(final PreferenzaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.basicSortOrder = null;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneResetAdd = true;
        super.usaBottoneResetDelete = false;
        this.usaBottoneNew = false;
        this.usaBottoneEdit = true;
        this.usaBottoneDeleteEntity = false;
        this.usaBottoneExport = false;
        this.usaBottoneSearch = true;
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        String message;

        message = String.format(TEXT_ENUM, "Pref", "Preferenza");
        layout.add(ASpan.text(message).verde().bold());

        layout.add(ASpan.text(TEXT_NEWS).rosso());
        layout.add(ASpan.text(TEXT_RESET_STARTUP).rosso());
        layout.add(ASpan.text(TEXT_RESET_ADD).rosso());
        message = String.format(TEXT_SEARCH, "Code");
        message += String.format("%sSearch ...by [%s] apre un popup", SPAZIO,"Type");
        layout.add(ASpan.text(String.format(message)).rosso().italic());

        alertPlaceHolder.add(layout);
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        Button buttonResetAdd = buttonBar.getButtonResetAdd();
        buttonResetAdd.getElement().setProperty("title", TEXT_RESET_ADD);

        comboType = new ComboBox<>();
        comboType.setPlaceholder(TAG_ALTRE_BY + "type");
        comboType.getElement().setProperty("title", "Filtro di selezione");
        comboType.setClearButtonVisible(true);
        comboType.setItems(TypePref.class.getEnumConstants());
        comboType.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboType);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboType != null) {
            if (comboType.getValue() != null) {
                if (comboType.getValue() instanceof TypePref type) {
                    filtri.uguale(FIELD_TYPE, type);
                }
            }
            else {
                filtri.remove(FIELD_TYPE);
            }
        }
    }

}// end of CrudList class
