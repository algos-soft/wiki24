package it.algos.base24.backend.packages.crono.secolo;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class SecoloList extends CrudList {

    private IndeterminateCheckbox boxCristo;

    public SecoloList(final SecoloModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "secoli");
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

        boxCristo = new IndeterminateCheckbox();
        boxCristo.setLabel("DopoCristo");
        boxCristo.setIndeterminate(false);
        boxCristo.setValue(true);
        boxCristo.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxCristo);
        layout1.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout1);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (boxCristo != null && !boxCristo.isIndeterminate()) {
            filtri.uguale("dopoCristo", boxCristo.getValue());
        }
        else {
            filtri.remove("dopoCristo");
        }
    }

}// end of CrudList class
