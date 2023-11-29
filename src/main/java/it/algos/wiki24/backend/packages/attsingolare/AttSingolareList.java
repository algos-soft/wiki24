package it.algos.wiki24.backend.packages.attsingolare;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AttSingolareList extends WikiList {


    public AttSingolareList(final AttSingolareModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaInfoDownload = true;
        super.lastDownload = WPref.lastDownloadAttSin;
        super.durataDownload = WPref.downloadAttSinTime;
        super.lastElaborazione = WPref.lastElaboraAttSin;
        super.durataElaborazione = WPref.elaboraAttSinTime;

        super.unitaMisuraDownload = "secondi";
        super.unitaMisuraElaborazione = "minuti";
    }

    @Override
    public VerticalLayout fixAlert() {
        VerticalLayout layout = super.fixAlert();
        Anchor anchor1;
        Anchor anchor2;
        String link;
        String caption;
        String message;
        String plurale = "Plurale attività";
        String ex = "Ex attività";

        link = String.format("%s%s", TAG_MODULO, plurale);
        caption = String.format("%s%s%s", QUADRA_INI, plurale, QUADRA_END);
        anchor1 = new Anchor(link, caption);
        anchor1.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", TAG_MODULO, ex);
        caption = String.format("%s%s%s", QUADRA_INI, ex, QUADRA_END);
        anchor2 = new Anchor(link, caption);
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Costruita dai Moduli Wiki: ";
        Label testo = new Label(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        layout.add(new Span(testo, anchor1, new Text(SEP), anchor2));
        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari sono convertite in minuscolo.";
        layout.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto e scarica i 2 moduli wiki", FORWARD);
        layout.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola attività singolare.", FORWARD);
        layout.add(ASpan.text(message).rosso());
        message = "Il download dei link alla pagina di attività, la lista dei plurali, l'elaborazione delle liste biografiche e gli upload delle liste di Attività sono gestiti dalla task AttPlurale.";
        layout.add(ASpan.text(message).rosso().small());

        return super.addAlert(layout);
    }


    public ExcelExporter creaExcelExporter() {
        String[] properties = {"nome", "plurale", "numBio"};
        ExcelExporter exporter = new ExcelExporter(AttSingolareEntity.class, filtri, List.of(properties), mongoService);

        exporter.setTitle("Lista delle attività (singolare)");
        exporter.setColumnWidth("nome", 30);
        exporter.setColumnWidth("plurale", 30);
        exporter.setColumnWidth("numBio", 8);

        return exporter;
    }

}// end of CrudList class
