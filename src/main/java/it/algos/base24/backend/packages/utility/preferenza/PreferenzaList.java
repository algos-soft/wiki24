package it.algos.base24.backend.packages.utility.preferenza;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
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
    }

    @Override
    public void fixAlert() {
        super.infoScopo = String.format(typeList.getInfoScopo());
        super.fixAlert();
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
