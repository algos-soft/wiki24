package it.algos.wiki24.backend.packages.bio.bioserver;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.service.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
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

        super.usaInfoDownload = true;
        super.usaBottoneElabora = false;
        super.usaBottoneSearch = false;
        super.usaSearchPageId = true;
        super.usaSearchWikiTitle = true;
        super.usaBottoneExport = false;
    }

    @Override
    protected void fixHeader() {
        String category = WPref.categoriaBio.getStr();
        Span biografie;
        Span contiene;
        String numPagine;
        String categoria =   CAT + WPref.categoriaBio.getStr();

        WAnchor anchor = WAnchor.build(categoria, textService.setQuadre(category)).bold();

        message = "Biografie grezze del server. Lette dalla categoria: ";
        biografie = new Span(message);
        biografie.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        biografie.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        numPagine = textService.format(queryService.getSizeCat(category));
        message = String.format(" che contiene %s pagine.", numPagine);
        contiene = new Span(message);
        contiene.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        contiene.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        headerPlaceHolder.add(new Span(biografie, anchor, contiene));

        message = "Una piccola differenza tra le pagine del server e gli elementi del DB è fisiologica e dovuta alle voci della " +
                "categoria che NON hanno un tmplBio valido";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());
        message = "Download: effettua anche l'elaborazione di ogni singola BioMongo modificata.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        super.fixHeader();
    }

}// end of CrudList class
