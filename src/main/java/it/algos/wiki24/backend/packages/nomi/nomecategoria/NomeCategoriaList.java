package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.geografia.stato.*;
import it.algos.base24.ui.dialog.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static it.algos.wiki24.backend.packages.nomi.nomecategoria.NomeCategoriaModulo.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeCategoriaList extends WikiList {
    private ComboBox comboType;


    public NomeCategoriaList(final NomeCategoriaModulo crudModulo) {
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
        WAnchor anchor1;
        WAnchor anchor2;
        WAnchor anchor3;
        String message;

        anchor1 = WAnchor.build(CAT + CAT_MASCHILE, "Maschili").bold();
        anchor2 = WAnchor.build(CAT + CAT_FEMMINILE, "Femminili").bold();
        anchor3 = WAnchor.build(CAT + CAT_ENTRAMBI, "Entrambi").bold();

        message = "Tavola di base. Costruita dalle categorie wiki: ";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2, new Text(SEP), anchor3));

        message = "I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
        headerPlaceHolder.add(ASpan.text(message).blue());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }

    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboType = new ComboBox<>();
        comboType.setPlaceholder("Genere...");
        comboType.setClearButtonVisible(true);
        comboType.setWidth("14rem");
        comboType.setItems(TypeGenere.values());
        comboType.addValueChangeListener(event -> sincroFiltri());
        wikiTopPlaceHolder.add(comboType);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboType != null) {
            if (comboType.getValue() != null) {
                if (comboType.getValue() instanceof TypeGenere type) {
                    filtri.uguale("type", type);
                }
            }
            else {
                filtri.remove("type");
            }
        }
    }

}// end of CrudList class
