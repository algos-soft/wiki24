package it.algos.wiki24.backend.packages.nomi.nomedoppio;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeDoppioList extends WikiList {


    public NomeDoppioList(final NomeDoppioModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneElabora = false;
        super.usaBottoneShows = false;
    }

    @Override
    protected void fixHeader() {
        WAnchor anchor;
        String message;

        anchor = WAnchor.build(TAG_ANTROPONIMI + DOPPI, textService.setQuadre(DOPPI)).bold();

        message = "Tavola di base. Costruita dalla pagina wiki: ";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(new Span(testo, anchor));

        message = "Nomi doppi (esempio: 'Maria Teresa') elencati nella pagina di progetto";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }

}// end of CrudList class
