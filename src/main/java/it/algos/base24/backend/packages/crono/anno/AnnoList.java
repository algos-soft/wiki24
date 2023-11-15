package it.algos.base24.backend.packages.crono.anno;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
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

    public AnnoList(final AnnoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format(TEXT_BACK,  "Anno")).verde());
        super.addAlert(layout);
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboSecolo = new ComboBox<>();
        comboSecolo.setPlaceholder( "Secoli...");
        comboSecolo.setClearButtonVisible(true);
        comboSecolo.setWidth("12rem");
        comboSecolo.setItems(secoloModulo.findAll());
        comboSecolo.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboSecolo);
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
    }

}// end of CrudList class
