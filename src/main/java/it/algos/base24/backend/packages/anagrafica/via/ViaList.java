package it.algos.base24.backend.packages.anagrafica.via;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ViaList extends CrudList {


    public ViaList(final ViaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "vie");
        super.fixAlert();
    }

    public ExcelExporter creaExcelExporter() {
        String[] properties = {"ordine", "nome"};
        ExcelExporter exporter = new ExcelExporter(ViaEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista delle vie");
        exporter.setColumnWidth("ordine", 8);
        exporter.setColumnWidth("nome", 20);

        return exporter;
    }

}// end of CrudList class
