package it.algos.base24.backend.packages.crono.mese;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.list.*;
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
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Mese", "Mese");;
        super.fixHeader();
    }


}// end of CrudList class
