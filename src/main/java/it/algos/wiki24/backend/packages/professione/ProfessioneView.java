package it.algos.wiki24.backend.packages.professione;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 07:19
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Professione")
@Route(value = TAG_PROFESSIONE, layout = MainLayout.class)
public class ProfessioneView extends WikiView {


    //--per eventuali metodi specifici
    private ProfessioneBackend backend;

    private TextField searchFieldPagina;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public ProfessioneView(@Autowired final ProfessioneBackend crudBackend) {
        super(crudBackend, Professione.class);
        this.backend = crudBackend;
    }

    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.gridPropertyNamesList = Arrays.asList("attivita", "pagina", "aggiunta");

        super.sortOrder = Sort.by(Sort.Direction.ASC, "attivita");
//        super.lastDownload = WPref.downloadProfessione;
        super.wikiModuloTitle = PATH_MODULO_PROFESSIONE;

        super.usaBottoneUploadAll = false;
//        super.usaBottoneStatistiche = false;
        super.usaBottoneUploadStatistiche = false;
        super.usaBottoneTest = false;
        super.usaBottoneUploadPagina = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        message = "Contiene la tabella di conversione delle attività passate via parametri 'Attività/Attività2/Attività3',";
        message += " dal nome dell'attività a quello della voce corrispondente per creare dei -piped wikilink-.";
        addSpan(ASpan.text(message).verde());

        message = "Le attività sono elencate all'interno del modulo con la seguente sintassi:";
        message += " [\"attivitaforma1\"]=\"voce di riferimento\"; [\"attivitaforma2\"]=\"voce di riferimento\".";
        addSpan(ASpan.text(message).verde());

        message = "Le attività e le pagine mantengono il maiuscolo/minuscolo previsto nel modulo.";
        message += " Le voci maschili che corrispondono alla pagina (non presenti nel modulo) vengono aggiunte.";
        addSpan(ASpan.text(message).rosso());
        message = " Le voci delle ex-attività (non presenti nel modulo) vengono aggiunte prendendole dal package 'attivita'";
        addSpan(ASpan.text(message).rosso());

        message = "Il package 'attività' deve essere aggiornato prima di regolare 'professione'.";
        addSpan(ASpan.text(message).rosso().bold());
    }

    protected void fixBottoniTopSpecifici() {
        searchFieldPagina = new TextField();
        searchFieldPagina.setPlaceholder(TAG_ALTRE_BY + "pagina");
        searchFieldPagina.setWidth(WIDTH_EM);
        searchFieldPagina.setClearButtonVisible(true);
        searchFieldPagina.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPagina);

        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Aggiunti da Attività");
        boxBox.setIndeterminate(true);
        boxBox.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout = new HorizontalLayout(boxBox);
        layout.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout);
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void sincroFiltri() {
        List<Professione> items = backend.findAllSortCorrente();

        final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(prof -> prof.attivita.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final String textSearchPagina = searchFieldPagina != null ? searchFieldPagina.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(prof -> prof.pagina.matches("^(?i)" + textSearchPagina + ".*$")).toList();
        }

        if (boxBox != null && !boxBox.isIndeterminate()) {
            items = items.stream().filter(prof -> prof.aggiunta == boxBox.getValue()).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
        }
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected AEntity wikiPage() {
        Professione professione = (Professione) super.wikiPage();

        String paginaText = textService.primaMaiuscola(professione.pagina);
        wikiApiService.openWikiPage( paginaText);

        return null;
    }

}// end of crud @Route view class