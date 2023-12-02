package it.algos.base24.backend.packages.crono.secolo;

import com.vaadin.flow.spring.annotation.*;
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
    public void fixAlert() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "secoli");
        super.fixAlert();
        alertPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }

}// end of CrudList class
