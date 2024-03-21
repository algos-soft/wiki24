package it.algos.wiki24.backend.packages.nomi.nomedoppio;

import com.vaadin.flow.component.grid.*;
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

        super.gridSelection = Grid.SelectionMode.NONE;

        super.usaInfoDownload = true;
        super.usaBottoneElabora = false;
        super.usaBottoneShows = false;
    }

    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * <p>
     * Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
     * Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
     * Terzo gruppo (infoLista): Blue, bold, small. Parametri logici di creazione delle liste <br>
     * Quarto gruppo: Rosso, bold, small. Esecuzione upload <br>
     * Quinto gruppo: Rosso, normale, small. Parametri delete/reset <br>
     * Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixHeader() {
        WAnchor anchor;
        String message;
        headerPlaceHolder.removeAll();

        anchor = WAnchor.build(TAG_ANTROPONIMI + DOPPI, textService.setQuadre(DOPPI)).bold();

        //Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
        message = "Tavola di base. Costruita dalla pagina wiki: ";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(new Span(testo, anchor));

        //Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
        message = "Nomi doppi (esempio: 'Maria Teresa') elencati nella pagina di progetto";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        //Quarto gruppo: Rosso, bold, small. Esecuzione upload <br>
        super.infoCreazione = TEXT_HARD;

        //Quinto gruppo: Rosso, normale, small. Parametri delete/reset <br>
        super.infoReset = TEXT_RESET_DELETE;

        //Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
        super.fixHeader();
    }

    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Metodo chiamato da postConstruct() e fixList() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     */
    @Override
    protected void fixBodyLayout() {
        super.fixBodyLayout();
    }

}// end of CrudList class
