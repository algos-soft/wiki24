package it.algos.wiki24.backend.packages.attplurale;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
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

        super.usaInfoDownload = true;
    }

    @Override
    public void fixAlert() {
        String categoria = TAG_WIKI + "Categoria:Bio attività";
        String modulo = PATH_MODULO;

        Anchor anchor1 = new Anchor(modulo + PATH_LINK + ATT_LOWER, PATH_LINK + ATT_LOWER);
        anchor1.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(categoria, "Categoria");
        anchor2.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        Anchor anchor3 = new Anchor(TAG_WIKI + PATH_STATISTICHE_ATTIVITA, STATISTICHE);
        anchor3.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di base. Costruita dai moduli Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        alertPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2, new Text(SEP), anchor3));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le attività plurali sono convertite in minuscolo.";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto. Esegue un download di AttSingolare. Crea una nuova tavola dai plurali (DISTINCT) di AttSingolare.", FORWARD);
        alertPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Download%sCrea un link alla PaginaLista di ogni attività. Scarica il modulo wiki [%s]. Crea un link ad ogni Attività.", FORWARD, PATH_LINK + ATT_LOWER);
        alertPlaceHolder.add(ASpan.text(message).rosso());
    }

}// end of CrudList class
