package it.algos.wiki24.backend.packages.tabelle.attsingolare;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AttSingolareList extends WikiList {


    private IndeterminateCheckbox checkEx;

    protected TextField searchPlurale = new TextField();

    public AttSingolareList(final AttSingolareModulo crudModulo) {
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
        Anchor anchor3;
        String link;
        String message;
        String plurale = "Plurale attività";
        String ex = "Ex attività";
        String pagina = "Link attività";

        link = String.format("%s%s", PATH_MODULO, plurale);
        anchor1 = new Anchor(link, textService.setQuadre(plurale));
        anchor1.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", PATH_MODULO, ex);
        anchor2 = new Anchor(link,  textService.setQuadre(ex));
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        link = String.format("%s%s", PATH_MODULO, pagina);
        anchor3 = new Anchor(link, textService.setQuadre(pagina));
        anchor3.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Costruita dai moduli Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2,new Text(SEP), anchor3));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le attività singolari sono convertite in minuscolo.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto e scarica i 3 moduli wiki", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola attività singolare.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = "La lista dei plurali, l'elaborazione delle liste biografiche e gli upload delle liste di Attività sono gestiti dalla task AttPlurale.";
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

        checkEx = super.creaFiltroCheckBox(checkEx, "Ex attività");
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

        super.fixFiltroCheckBox(checkEx, "ex");
    }

}// end of CrudList class
