package it.algos.wiki24.backend.packages.cognomeincipit;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.cognomecategoria.*;
import it.algos.wiki24.backend.packages.nomeincipit.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.schedule.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 18:10
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("CognomiIncipit")
@Route(value = "cognomeincipit", layout = MainLayout.class)
public class CognomeIncipitView extends WikiView {


    //--per eventuali metodi specifici
    private CognomeIncipitBackend backend;

    private IndeterminateCheckbox boxAggiunti;
    private IndeterminateCheckbox boxUguali;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public CognomeIncipitView(@Autowired final CognomeIncipitBackend crudBackend) {
        super(crudBackend, CognomeIncipit.class);
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

        super.gridPropertyNamesList = Arrays.asList("cognome", "linkPagina", "aggiunto", "uguale");
        super.formPropertyNamesList = Arrays.asList("cognome", "linkPagina", "aggiunto", "uguale");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDownload = true;
        super.usaBottoneNew = true;
        super.usaBottoneEdit = true;
        super.usaBottoneTest = false;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneUploadAll = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneUploadModuloAlfabetizzato = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        Anchor anchor = WAnchor.build(backend.sorgenteDownload, MODULO);
        alertPlaceHolder.add(new Span(anchor));

        message = "Pagine di riferimento per ogni cognome (es.: [Bianchi->Bianchi (cognome)]) da inserire nell'incipit della lista.";
        addSpan(ASpan.text(message).verde());
        message = String.format("I cognomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale");
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sLegge il modulo: %s.", FORWARD, backend.sorgenteDownload);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sAggiunge i cognomi di %s e controlla gli uguali", FORWARD, "CognomiCategoria");
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sRiscrive il modulo in ordine alfabetico, esclusi gli uguali.", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di cognomi sono gestiti dalla task Cognome.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Upload%sElenco riordinato in ordine alfabetico. Scheduled %s. Esclusi i cognomi con la stessa pagina.", FORWARD, TaskStatistiche.INFO);
        addSpan(ASpan.text(message).blue().small());
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopStandard() {
        super.fixBottoniTopStandard();

        if (searchField != null) {
            searchField.setPlaceholder(TAG_ALTRE_BY + "singolare");
        }

        searchFieldPagina = new TextField();
        searchFieldPagina.setPlaceholder(TAG_ALTRE_BY + "link");
        searchFieldPagina.setClearButtonVisible(true);
        searchFieldPagina.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPagina);
    }

    protected void fixBottoniTopSpecifici() {
        boxAggiunti = new IndeterminateCheckbox();
        boxAggiunti.setLabel("Aggiunti");
        boxAggiunti.setIndeterminate(true);
        boxAggiunti.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutAggiunti = new HorizontalLayout(boxAggiunti);
        layoutAggiunti.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutAggiunti);

        boxUguali = new IndeterminateCheckbox();
        boxUguali.setLabel("Uguali");
        boxUguali.setIndeterminate(true);
        boxUguali.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutUguali = new HorizontalLayout(boxUguali);
        layoutUguali.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutUguali);


        this.add(topPlaceHolder2);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<CognomeIncipit> items = (List) super.sincroFiltri();

        if (boxAggiunti != null && !boxAggiunti.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.aggiunto == boxAggiunti.getValue()).toList();
        }

        if (boxUguali != null && !boxUguali.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.uguale == boxUguali.getValue()).toList();
        }

        final String textSearchPagina = searchFieldPagina != null ? searchFieldPagina.getValue() : VUOTA;
        if (textService.isValidNoSpace(textSearchPagina)) {
            items = items
                    .stream()
                    .filter(bean -> ((String) reflectionService.getPropertyValue(bean, searchFieldName)).matches("^(?i)" + textSearchPagina + ".*$"))
                    .toList();
        }
        else {
            if (textSearchPagina.equals(SPAZIO)) {
                items = items
                        .stream()
                        .filter(nome -> nome.linkPagina == null)
                        .toList();
            }
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }

        return (List) items;
    }

}// end of crud @Route view class