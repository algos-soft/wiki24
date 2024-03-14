package it.algos.wiki24.backend.packages.tabelle.nazsingolare;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NazSingolareList extends WikiList {

    protected TextField searchPlurale = new TextField();

    public NazSingolareList(final NazSingolareModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaInfoDownload = true;
        super.usaInfoElabora = true;
        super.usaInfoUpload = false;

        this.usaBottoneDownload = true;
        this.usaBottoneElabora = true;
        this.usaBottoneUploadAll = false;
    }

    @Override
    protected void fixHeader() {
        Anchor anchor1;
        Anchor anchor2;
        String link;
        String message;
        String plurale = "Plurale nazionalità";
        String pagina = "Link nazionalità";

        link = String.format("%s%s", TAG_MODULO, plurale);
        anchor1 = WAnchor.build(link, textService.setQuadre(plurale));

        link = String.format("%s%s", TAG_MODULO, pagina);
        anchor2 = WAnchor.build(link, textService.setQuadre(pagina));

        message = "Tavola di base. Costruita dai moduli Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le nazionalità singolari sono convertite in minuscolo.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto e scarica i 2 moduli wiki", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola nazionalità singolare.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = "La lista dei plurali, l'elaborazione delle liste biografiche e gli upload delle liste di Nazionalità sono gestiti dalla task NazPlurale.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();
        searchPlurale.setPlaceholder(TAG_ALTRE_BY + "plurale");
        searchPlurale.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + searchFieldName);
        searchPlurale.setClearButtonVisible(true);
        searchPlurale.addValueChangeListener(event -> sincroFiltri());
        wikiTopPlaceHolder.add(searchPlurale);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        String propertyPlurale = "plurale";
        String pluraleValue = VUOTA;
        if (searchPlurale != null) {
            pluraleValue = searchPlurale.getValue();

        }

        if (textService.isValid(pluraleValue)) {
            filtri.inizio(propertyPlurale, pluraleValue);
            filtri.sort(Sort.by(Sort.Direction.ASC,propertyPlurale));
        }
        else {
            filtri.remove(propertyPlurale);
            filtri.sort(basicSort);
        }
    }

}// end of CrudList class
