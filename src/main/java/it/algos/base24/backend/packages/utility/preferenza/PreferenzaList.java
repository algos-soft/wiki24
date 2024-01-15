package it.algos.base24.backend.packages.utility.preferenza;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class PreferenzaList extends CrudList {


    private ComboBox comboType;

    private static String FIELD_TYPE = "type";
    private IndeterminateCheckbox boxCritical;
    private IndeterminateCheckbox boxDinamica;
    private IndeterminateCheckbox boxBase24;

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

        boxCritical = new IndeterminateCheckbox();
        boxCritical.setLabel("Critica");
        boxCritical.setIndeterminate(true);
        boxCritical.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxCritical);
        layout1.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout1);

        boxDinamica = new IndeterminateCheckbox();
        boxDinamica.setLabel("Dinamica");
        boxDinamica.setIndeterminate(true);
        boxDinamica.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxDinamica);
        layout2.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout2);

        boxBase24 = new IndeterminateCheckbox();
        boxBase24.setLabel("Base24");
        boxBase24.setIndeterminate(true);
        boxBase24.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout3 = new HorizontalLayout(boxBase24);
        layout3.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout3);
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

        if (boxCritical != null && !boxCritical.isIndeterminate()) {
            filtri.uguale("critical", boxCritical.getValue());
        }
        else {
            filtri.remove("critical");
        }

        if (boxDinamica != null && !boxDinamica.isIndeterminate()) {
            filtri.uguale("dinamica", boxDinamica.getValue());
        }
        else {
            filtri.remove("dinamica");
        }

        if (boxBase24 != null && !boxBase24.isIndeterminate()) {
            filtri.uguale("base24", boxBase24.getValue());
        }
        else {
            filtri.remove("base24");
        }

    }

}// end of CrudList class
