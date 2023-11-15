package it.algos.base24.backend.packages.utility.role;

import com.vaadin.flow.component.orderedlayout.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class RoleList extends CrudList {


    public RoleList(final RoleModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format(TEXT_ENUM, "RoleEnum", "Role")).verde());
        super.addAlert(layout);
    }


    public ExcelExporter creaExcelExporter() {
        ExcelExporter exporter = new ExcelExporter(RoleEntity.class, filtri, Arrays.asList("ordine", "code"), mongoService);

        exporter.setTitle("Tabella dei ruoli");
        return exporter;
    }

}// end of CrudList class
