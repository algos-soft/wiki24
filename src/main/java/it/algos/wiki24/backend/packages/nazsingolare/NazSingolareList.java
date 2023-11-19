package it.algos.wiki24.backend.packages.nazsingolare;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NazSingolareList extends CrudList {


    public NazSingolareList(final NazSingolareModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format("Prova")).verde());
        super.addAlert(layout);
    }


    public ExcelExporter creaExcelExporter() {
        String[] properties = {"nome", "plurale","numBio"};
        ExcelExporter exporter = new ExcelExporter(NazSingolareEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista delle nazioni (singolare)");
        exporter.setColumnWidth("nome", 30);
        exporter.setColumnWidth("plurale", 30);
        exporter.setColumnWidth("numBio", 8);

        return exporter;
    }

}// end of CrudList class
