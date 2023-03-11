package it.algos.wiki24.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.utility.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.giorno.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 10-Mar-2023
 * Time: 18:26
 */
@Route(value = Wiki24Cost.WIKI_TAG_UTILITY, layout = MainLayout.class)
public class WikiUtilityView extends UtilityView {

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;


    @Override
    public void body() {
        super.body();
        this.paragrafoElaborazione();
    }

    public void paragrafoElaborazione() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Elaborazione delle task");
        paragrafo.getElement().getStyle().set("color", "blue");

        message = String.format("Esegue il metodo 'elabora' delle varie xxxBackend class");
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(ESEGUIRE));
        layout.add(ASpan.text(FLAG_DEBUG));

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> elaboraAll());

        Button bottone2 = new Button("Giorni");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> elaboraGiorni());

        Button bottone3 = new Button("Anni");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> elaboraAnni());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3));
        this.add(layout);
    }

    public void elaboraAll() {
        elaboraGiorni();
        elaboraAnni();
    }

    public void elaboraGiorni() {
        giornoWikiBackend.elabora();
    }

    public void elaboraAnni() {
        annoWikiBackend.elabora();
    }

}
