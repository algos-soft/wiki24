package it.algos.base24.backend.packages.utility.logs;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class LogList extends CrudList {

    private ComboBox comboTypeLog;
    private ComboBox comboTypeLevel;

    public LogList(final LogModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDownload = false;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneShows = true;
    }
    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboTypeLog = new ComboBox<>();
        comboTypeLog.setPlaceholder("TypeLog...");
        comboTypeLog.setClearButtonVisible(true);
        comboTypeLog.setWidth("10rem");
        comboTypeLog.setItems(TypeLog.values());
        comboTypeLog.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboTypeLog);

        comboTypeLevel = new ComboBox<>();
        comboTypeLevel.setPlaceholder("TypeLevel...");
        comboTypeLevel.setClearButtonVisible(true);
        comboTypeLevel.setWidth("8.7rem");
        comboTypeLevel.setItems(LogLevel.values());
        comboTypeLevel.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboTypeLevel);
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
