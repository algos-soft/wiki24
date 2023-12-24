package it.algos.wiki24.backend.packages.bioserver;

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
import it.algos.wiki24.backend.service.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioServerList extends WikiList {

    @Inject
    QueryService queryService;

    public BioServerList(final BioServerModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneExport = false;
    }

    @Override
    public void fixAlert() {
        String category = WPref.categoriaBio.getStr();
        Span biografie;
        Span contiene;
        String numPagine;
        String categoria = TAG_WIKI + CAT + WPref.categoriaBio.getStr();

        Anchor anchor = new Anchor(categoria, textService.setQuadre(category));
        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Biografie grezze del server. Lette dalla categoria: ";
        biografie = new Span(message);
        biografie.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        biografie.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        numPagine = textService.format(queryService.getSizeCat(category));
        message = String.format(" che contiene %s pagine.", numPagine);
        contiene = new Span(message);
        contiene.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        contiene.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        alertPlaceHolder.add(new Span(biografie, anchor, contiene));

        message = "Qualche piccola differenza tra le pagine del server e le entities della collezione è fisiologica";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());
        message = "(Differenza dovuta alle voci della categoria che NON hanno un tmplBio valido)";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());
    }

}// end of CrudList class
