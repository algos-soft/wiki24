package it.algos.base24.backend.packages.anagrafica.via;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.dialog.*;
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
    public void fixHeader() {
        String link = "vie";
        BAnchor anchor = BAnchor.build(LINK_SERVER_ALGOS + link, textService.setQuadre("algos -> " + link));
        BSpan testo = BSpan.text(TEXT_TAVOLA + SPAZIO + TEXT_CSV).bold().verde();
        headerPlaceHolder.add(new Span(testo, new Text(SPAZIO), anchor));

        super.infoScopo = VUOTA;
        super.infoCreazione = TEXT_NEWS;
        super.infoReset = TEXT_RESET_ADD;

        super.fixHeader();
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
