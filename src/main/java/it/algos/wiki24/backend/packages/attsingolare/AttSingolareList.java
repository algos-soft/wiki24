package it.algos.wiki24.backend.packages.attsingolare;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.textfield.TextField;
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
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

import javax.inject.*;
import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AttSingolareList extends WikiList {

    @Inject
    AttSingolareModulo attSingolareModulo;

    private IndeterminateCheckbox checkEx;

    protected TextField searchPlurale = new TextField();

    public AttSingolareList(final AttSingolareModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaInfoDownload = true;
    }

    @Override
    public void fixAlert() {
        Anchor anchor1;
        Anchor anchor2;
        String link;
        String caption;
        String message;
        String plurale = "Plurale attività";
        String ex = "Ex attività";

        link = String.format("%s%s", PATH_MODULO, plurale);
        caption = String.format("%s%s%s", QUADRA_INI, plurale, QUADRA_END);
        anchor1 = new Anchor(link, caption);
        anchor1.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", PATH_MODULO, ex);
        caption = String.format("%s%s%s", QUADRA_INI, ex, QUADRA_END);
        anchor2 = new Anchor(link, caption);
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Costruita dai moduli Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        alertPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le attività singolari sono convertite in minuscolo.";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto e scarica i 2 moduli wiki", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola attività singolare.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = "Il download dei link alla pagina di attività, la lista dei plurali, l'elaborazione delle liste biografiche e gli upload delle liste di Attività sono gestiti dalla task AttPlurale.";
        alertPlaceHolder.add(ASpan.text(message).rosso().small());
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
        buttonBar.add(searchPlurale);

        checkEx = new IndeterminateCheckbox("Ex attività");
        checkEx.getStyle().set("margin-top", "0.5rem");
        checkEx.addValueChangeListener(event -> sincroFiltri());
        buttonBar.add(checkEx);
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
            filtri.sort(Sort.Order.asc(propertyPlurale));
        }
        else {
            filtri.remove(propertyPlurale);
            filtri.sort(basicSortOrder);
        }

        String propertyEx = "ex";
        if (checkEx != null) {
            if (checkEx.isIndeterminate()) {
                filtri.remove(propertyEx);
                filtri.sort(basicSortOrder);
            }
            else if (checkEx.getValue()) {
                filtri.add(propertyEx, true);
                filtri.sort(Sort.Order.asc(propertyEx));
            }
            else {
                filtri.add(propertyEx, false);
                filtri.sort(Sort.Order.asc(propertyEx));
            }
        }
    }

}// end of CrudList class
