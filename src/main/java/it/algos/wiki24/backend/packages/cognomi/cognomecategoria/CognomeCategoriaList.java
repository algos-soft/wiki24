package it.algos.wiki24.backend.packages.cognomi.cognomecategoria;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static it.algos.wiki24.backend.packages.nomi.nomecategoria.NomeCategoriaModulo.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class CognomeCategoriaList extends WikiList {


    public CognomeCategoriaList(final CognomeCategoriaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDownload = true;
        super.usaBottoneElabora = false;
        super.usaBottoneShows = false;
    }

    @Override
    protected void fixHeader() {
        WAnchor anchor;
        String message;

        anchor = WAnchor.build(CAT + COGNOMI_LINGUA, COGNOMI_LINGUA).bold();

        message = "Tavola di base. Costruita dalle categorie wiki: ";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(new Span(testo, anchor));

        message = "I cognomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di cognomi sono gestiti dalla task Cognome.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }

}// end of CrudList class
