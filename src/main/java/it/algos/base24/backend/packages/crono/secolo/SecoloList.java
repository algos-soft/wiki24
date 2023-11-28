package it.algos.base24.backend.packages.crono.secolo;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class SecoloList extends CrudList {


    public SecoloList(final SecoloModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public VerticalLayout fixAlert() {
        VerticalLayout layout = super.fixAlert();
        String message;

        message = String.format(TEXT_ENUM, "SecoloEnum", "Secolo");
        layout.add(ASpan.text(message).verde().bold());

        layout.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
        return super.addAlert(layout);
    }

}// end of CrudList class
