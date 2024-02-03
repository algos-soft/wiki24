package it.algos.base24.backend.packages.anagrafica.address;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AddressList extends CrudList {


    public AddressList(final AddressModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        this.usaBottoneResetDelete = true;
        this.usaBottoneResetAdd = false;
    }

    @Override
    public void fixHeader() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text("Blocco per l'indirizzo utilizzabile in anagrafica.").verde());
        layout.add(ASpan.text("Tavola esemplicativa. La singola entity rimane all'interno di Persone.").rosso().small());
        headerPlaceHolder.add(layout);
    }

}// end of CrudList class
