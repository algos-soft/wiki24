package it.algos.wiki24.backend.packages.tabelle.nomidoppi;

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
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeDoppioList extends CrudList {


    public NomeDoppioList(final NomeDoppioModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        Anchor anchor;
        String nomiDoppi = "pippox";
        String link;

        link = String.format("%s%s", PATH_MODULO, nomiDoppi);
        anchor = new Anchor(link, textService.setQuadre(nomiDoppi));
        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Tavola di supporto. Costruita dal modulo wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor));

        headerPlaceHolder.add(layout);

    }

}// end of CrudList class
