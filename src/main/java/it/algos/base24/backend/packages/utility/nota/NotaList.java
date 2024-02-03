package it.algos.base24.backend.packages.utility.nota;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaList extends CrudList {
    private ComboBox comboTypeLog;
    private ComboBox comboTypeLevel;


    public NotaList(final NotaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        super.infoScopo = "Appunti liberi";
        super.fixHeader();
        message="Data iniziale proposta quella attuale ma modificabile. Data finale inserita automaticamente col flag fatto=true.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());
        message="Filtri selezione per typeLog e typeLevel. Ordinamento decrescente per data iniziale. Descrizione libera.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

    }

    @Override
    protected void fixTop() {
        super.fixTop();

        comboTypeLog = new ComboBox<>();
        comboTypeLog.setPlaceholder("TypeLog...");
        comboTypeLog.setClearButtonVisible(true);
        comboTypeLog.setWidth("10rem");
        comboTypeLog.setItems(TypeLog.values());
        comboTypeLog.addValueChangeListener(event -> sincroFiltri());
        buttonBar.add(comboTypeLog);

        comboTypeLevel = new ComboBox<>();
        comboTypeLevel.setPlaceholder("TypeLevel...");
        comboTypeLevel.setClearButtonVisible(true);
        comboTypeLevel.setWidth("8.7rem");
        comboTypeLevel.setItems(LogLevel.values());
        comboTypeLevel.addValueChangeListener(event -> sincroFiltri());
        buttonBar.add(comboTypeLevel);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboTypeLog != null) {
            if (comboTypeLog.getValue() != null) {
                if (comboTypeLog.getValue() instanceof TypeLog type) {
                    filtri.uguale("typeLog", type);
                }
            }
            else {
                filtri.remove("typeLog");
            }
        }

        if (comboTypeLevel != null) {
            if (comboTypeLevel.getValue() != null) {
                if (comboTypeLevel.getValue() instanceof LogLevel type) {
                    filtri.uguale("typeLevel", type);
                }
            }
            else {
                filtri.remove("typeLevel");
            }
        }
    }


}// end of CrudList class
