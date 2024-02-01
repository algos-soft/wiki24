package it.algos.base24.backend.packages.anagrafica.persona;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class PersonaList extends CrudList {


    public PersonaList(final PersonaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        this.usaBottoneResetDelete = true;
        this.usaBottoneResetAdd = false;
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format("Prova")).verde());
    }

}// end of CrudList class
