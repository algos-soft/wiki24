package it.algos.base24.backend.packages.geografia.comune;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ComuneList extends CrudList {


    public ComuneList(final ComuneModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format("Prova")).verde());
        super.fixHeader();
    }

}// end of CrudList class
