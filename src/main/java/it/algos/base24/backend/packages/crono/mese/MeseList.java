package it.algos.base24.backend.packages.crono.mese;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

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
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format(TEXT_ENUM, "MeseEnum", "Mese")).verde());
        super.addAlert(layout);
    }

    public ExcelExporter creaExcelExporter() {
        String[] properties = {"ordine", "nome", "giorni", "primo", "ultimo"};
        ExcelExporter exporter = new ExcelExporter(MeseEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista dei mesi");
        exporter.setColumnWidth("ordine", 8);
        exporter.setColumnWidth("nome", 20);
        exporter.setColumnWidth("giorni", 8);
        exporter.setColumnWidth("primo", 8);
        exporter.setColumnWidth("ultimo", 8);

        return exporter;
    }

}// end of CrudList class
