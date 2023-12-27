package it.algos.wiki24.backend.packages.parsesso;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.ui.wrapper.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ParSessoList extends WikiList {

    private IndeterminateCheckbox boxValido;
    private IndeterminateCheckbox boxPieno;

    public ParSessoList(final ParSessoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneDownload = false;
        this.usaBottoneWikiView = true;
        this.usaBottoneWikiEdit = true;
        this.usaBottoneWikiCrono = true;
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        boxValido = new IndeterminateCheckbox();
        boxValido.setLabel("Valido");
        boxValido.setIndeterminate(false);
        boxValido.setValue(true);
        boxValido.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxValido);
        layout1.setAlignItems(Alignment.CENTER);
        buttonBar.add(layout1);

        boxPieno = new IndeterminateCheckbox();
        boxPieno.setLabel("Pieno");
        boxPieno.setIndeterminate(false);
        boxPieno.setValue(true);
        boxPieno.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxPieno);
        layout2.setAlignItems(Alignment.CENTER);
        buttonBar.add(layout2);

    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (boxValido != null && !boxValido.isIndeterminate()) {
            filtri.uguale("valido", boxValido.getValue());
        }
        else {
            filtri.remove("valido");
        }

        if (boxPieno != null && !boxPieno.isIndeterminate()) {
            filtri.uguale("pieno", boxPieno.getValue());
        }
        else {
            filtri.remove("pieno");
        }
    }

}// end of CrudList class
