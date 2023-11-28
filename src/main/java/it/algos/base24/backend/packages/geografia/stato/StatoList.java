package it.algos.base24.backend.packages.geografia.stato;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.geografia.continente.*;
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
        Anchor anchor1;
        Anchor anchor2;
        Anchor anchor3;
        String link;
        String caption;
        String alfa3 = "ISO 3166-1 alpha-3";
        String capitali = "Capitali degli Stati del mondo";
        String alfa2 = "ISO 3166-1";

        link = String.format("%s%s", TAG_WIKI, alfa3);
        caption = String.format("%s%s%s", QUADRA_INI, alfa3, QUADRA_END);
        anchor1 = new Anchor(link, caption);
        anchor1.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", TAG_WIKI, capitali);
        caption = String.format("%s%s%s", QUADRA_INI, capitali, QUADRA_END);
        anchor2 = new Anchor(link, caption);
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", TAG_WIKI, alfa2);
        caption = String.format("%s%s%s", QUADRA_INI, alfa2, QUADRA_END);
        anchor3 = new Anchor(link, caption);
        anchor3.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Costruita dalle pagine Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        layout.add(new Span(testo, anchor1, new Text(VIRGOLA_SPAZIO), anchor2, new Text(VIRGOLA_SPAZIO), anchor3));

        super.addAlert(layout);
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboContinente = new ComboBox<>();
        comboContinente.setPlaceholder("Continenti...");
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
        String[] properties = {"ordine", "nome", "capitale", "alfa3", "alfa2"};
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
