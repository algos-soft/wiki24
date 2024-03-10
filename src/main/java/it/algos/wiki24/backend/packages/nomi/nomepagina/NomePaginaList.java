package it.algos.wiki24.backend.packages.nomi.nomepagina;

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
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomePaginaList extends WikiList {


    public NomePaginaList(final NomePaginaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    protected void fixHeader() {
        Anchor anchor;
        String message;

        anchor = WAnchor.build(MODULO + INCIPIT_NOMI, textService.setQuadre(INCIPIT_NOMI));

        message = "Tavola di base. Costruita dal modulo Wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le attività singolari sono convertite in minuscolo.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = "Pagine di riferimento per ogni nome (es.: [Felix->Felice (nome)]) da inserire nell'incipit della lista.";
        headerPlaceHolder.add(ASpan.text(message).rosso());

        message = String.format("Download%sCancella tutto e scarica il modulo wiki", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
    }


}// end of CrudList class
