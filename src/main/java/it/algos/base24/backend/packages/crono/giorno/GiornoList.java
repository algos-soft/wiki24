package it.algos.base24.backend.packages.crono.giorno;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.crono.mese.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class GiornoList extends CrudList {

    @Autowired
    public MeseModulo meseModulo;

    private ComboBox comboMese;

    public GiornoList(final GiornoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Giorno");
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }


    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
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
        topPlaceHolder.add(comboMese);
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
