package it.algos.wiki24.backend.packages.nazsingolare;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NazSingolareList extends CrudList {


    public NazSingolareList(final NazSingolareModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format("Prova")).verde());
        super.addAlert(layout);
    }

}// end of CrudList class
