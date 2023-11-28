package it.algos.base24.backend.packages.crono.mese;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class MeseList extends CrudList {


    public MeseList(final MeseModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public VerticalLayout fixAlert() {
        VerticalLayout layout = super.fixAlert();

        message = String.format(TEXT_ENUM, "MeseEnum", "Mese");
        layout.add(ASpan.text(message).verde().bold());

        return super.addAlert(layout);
    }


}// end of CrudList class
