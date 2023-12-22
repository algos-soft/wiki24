package it.algos.base24.backend.packages.utility.nota;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaList extends CrudList {


    public NotaList(final NotaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        super.infoScopo = "Appunti liberi";
        super.fixAlert();
    }


}// end of CrudList class