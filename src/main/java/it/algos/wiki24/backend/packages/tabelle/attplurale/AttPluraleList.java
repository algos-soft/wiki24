package it.algos.wiki24.backend.packages.tabelle.attplurale;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AttPluraleList extends WikiList {


    public AttPluraleList(final AttPluraleModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixAlert() {
        String categoria = TAG_WIKI + "Categoria:Bio attività";

        Anchor anchor2 = new Anchor(categoria, textService.setQuadre("Categoria"));
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        Anchor anchor3 = new Anchor(TAG_WIKI + PATH_STATISTICHE_ATTIVITA, textService.setQuadre(STATISTICHE));
        anchor3.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Vedi pagine wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        alertPlaceHolder.add(new Span(testo, anchor2, new Text(SEP), anchor3));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le attività plurali sono convertite in minuscolo.";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto. Esegue un download di AttSingolare.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Download%sCrea una nuova tavola dai plurali (DISTINCT) di AttSingolare.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Download%sCrea un link alla PaginaLista. Crea un link alla pagina di Attività.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola attività plurale.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = "Gestisce l'elaborazione delle liste biografiche e gli upload delle liste di attività.";
        alertPlaceHolder.add(ASpan.text(message).rosso().small());
    }

}// end of CrudList class
