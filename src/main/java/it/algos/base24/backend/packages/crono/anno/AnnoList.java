package it.algos.base24.backend.packages.crono.anno;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.ui.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AnnoList extends CrudList {

    @Autowired
    public SecoloModulo secoloModulo;

    private ComboBox comboSecolo;

    private IndeterminateCheckbox boxCristo;

    private IndeterminateCheckbox boxBisestile;

    public AnnoList(final AnnoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Anno"); ;
        super.fixHeader();
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboSecolo = new ComboBox<>();
        comboSecolo.setPlaceholder("Secoli...");
        comboSecolo.setClearButtonVisible(true);
        comboSecolo.setWidth("12rem");
        comboSecolo.setItems(secoloModulo.findAll());
        comboSecolo.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboSecolo);

        boxCristo = new IndeterminateCheckbox();
        boxCristo.setLabel("DopoCristo");
        boxCristo.setIndeterminate(false);
        boxCristo.setValue(true);
        boxCristo.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxCristo);
        layout1.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout1);

        boxBisestile = new IndeterminateCheckbox();
        boxBisestile.setLabel("Bisestile");
        boxBisestile.setIndeterminate(true);
        boxBisestile.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxBisestile);
        layout2.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout2);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboSecolo != null) {
            if (comboSecolo.getValue() != null) {
                if (comboSecolo.getValue() instanceof SecoloEntity secolo) {
                    filtri.uguale("secolo", secolo);
                }
            }
            else {
                filtri.remove("secolo");
            }
        }


        if (boxCristo != null && !boxCristo.isIndeterminate()) {
            filtri.uguale("dopoCristo", boxCristo.getValue());
        }
        else {
            filtri.remove("dopoCristo");
        }

        if (boxBisestile != null && !boxBisestile.isIndeterminate()) {
            filtri.uguale("bisestile", boxBisestile.getValue());
        }
        else {
            filtri.remove("bisestile");
        }
    }

}// end of CrudList class
