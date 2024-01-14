package it.algos.wiki24.backend.packages.tabelle.giorni;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.ui.wrapper.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class GiorniList extends WikiList {

    @Inject
    public MeseModulo meseModulo;

    private ComboBox comboMese;

    public GiorniList(final GiorniModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneResetDelete = true;
        this.usaBottoneDownload = false;
        this.usaBottoneElabora = true;
        this.usaBottoneSearch = typeList.isUsaBottoneSearch();
        this.usaBottoneExport = false;
        this.usaInfoElabora = true;
        super.usaVariantCompact=true;
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboMese = new ComboBox<>();
        comboMese.setPlaceholder( "Mesi...");
        comboMese.setClearButtonVisible(true);
        comboMese.setWidth("12rem");
        comboMese.setItems(meseModulo.findAll());
        comboMese.addValueChangeListener(event -> sincroFiltri());
        buttonBar.add(comboMese);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboMese != null) {
            if (comboMese.getValue() != null) {
                if (comboMese.getValue() instanceof MeseEntity mese) {
                    filtri.uguale("mese", mese);
                }
            }
            else {
                filtri.remove("mese");
            }
        }
    }

}// end of CrudList class
