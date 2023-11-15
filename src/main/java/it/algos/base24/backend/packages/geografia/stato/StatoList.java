package it.algos.base24.backend.packages.geografia.stato;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.geografia.continente.*;
import it.algos.base24.ui.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class StatoList extends CrudList {

    @Autowired
    public ContinenteModulo continenteModulo;

    private ComboBox comboContinente;

    public StatoList(final StatoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        String message;
        String alfa3 = "ISO 3166-1 alpha-3";
        String capitali = "Capitali degli Stati del mondo";
        String alfa2 = "ISO 3166-1";

        message = String.format("Tavola di base.");
        message += String.format(" Costruita da Wiki con: [%s], [%s] e [%s]", alfa3, capitali, alfa2);
        layout.add(ASpan.text(message).verde());

        super.addAlert(layout);
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboContinente = new ComboBox<>();
        comboContinente.setPlaceholder( "Continenti...");
        comboContinente.setClearButtonVisible(true);
        comboContinente.setWidth("14rem");
        comboContinente.setItems(continenteModulo.findAll());
        comboContinente.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboContinente);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboContinente != null) {
            if (comboContinente.getValue() != null) {
                if (comboContinente.getValue() instanceof ContinenteEntity continente) {
                    filtri.uguale("continente", continente);
                }
            }
            else {
                filtri.remove("continente");
            }
        }
    }


    public ExcelExporter creaExcelExporter() {
        String[] properties = {"ordine", "nome","capitale","alfa3","alfa2"};
        ExcelExporter exporter = new ExcelExporter(StatoEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista degli stati");
        exporter.setColumnWidth("ordine", 8);
        exporter.setColumnWidth("nome", 30);
        exporter.setColumnWidth("capitale", 20);
        exporter.setColumnWidth("alfa3", 8);
        exporter.setColumnWidth("alfa2", 8);

        return exporter;
    }

}// end of CrudList class
