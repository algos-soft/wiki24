package it.algos.base24.backend.packages.geografia.continente;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ContinenteList extends CrudList {


    public ContinenteList(final ContinenteModulo crudModulo) {
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
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Continente", "Continente"); ;
        super.fixHeader();
    }

    public ExcelExporter creaExcelExporter() {
        String[] properties = {"ordine", "nome"};
        ExcelExporter exporter = new ExcelExporter(ContinenteEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista dei continenti");
        exporter.setColumnWidth("ordine", 8);
        exporter.setColumnWidth("nome", 20);

        return exporter;
    }

}// end of CrudList class
