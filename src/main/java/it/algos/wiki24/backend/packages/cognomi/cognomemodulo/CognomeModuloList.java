package it.algos.wiki24.backend.packages.cognomi.cognomemodulo;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class CognomeModuloList extends WikiList {

    private IndeterminateCheckbox checkAggiunto;

    private IndeterminateCheckbox checkUguale;

    public CognomeModuloList(final CognomeModuloModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneElabora = true;
        super.usaBottoneShows = false;
    }

    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    protected void fixHeader() {
        WAnchor anchor;
        String message;

        anchor = WAnchor.build(MODULO + INCIPIT_COGNOMI, textService.setQuadre(INCIPIT_COGNOMI)).bold();

        message = "Tavola di base. Costruita dalla pagina wiki: ";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(new Span(testo, anchor));


        message = "I cognomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di cognomi sono gestiti dalla task Cognome.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "Elabora: aggiunge tutti i CognomiCategoria che hanno la pagina omonima o che non ce l'hanno proprio";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        checkAggiunto = super.creaFiltroCheckBox(checkAggiunto, "aggiunto");
        checkUguale = super.creaFiltroCheckBox(checkUguale, "uguale");
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        super.fixFiltroCheckBox(checkAggiunto, "aggiunto");
        super.fixFiltroCheckBox(checkUguale, "uguale");
    }

}// end of CrudList class
