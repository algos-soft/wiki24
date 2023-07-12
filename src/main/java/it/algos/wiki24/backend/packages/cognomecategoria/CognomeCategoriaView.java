package it.algos.wiki24.backend.packages.cognomecategoria;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Jul-2023
 * Time: 19:00
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("CognomiCategoria")
@Route(value = "cognomecategoria", layout = MainLayout.class)
public class CognomeCategoriaView extends WikiView {


    //--per eventuali metodi specifici
    private CognomeCategoriaBackend backend;


    private ComboBox comboLingue;
    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public CognomeCategoriaView(@Autowired final CognomeCategoriaBackend crudBackend) {
        super(crudBackend, CognomeCategoria.class);
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

        super.gridPropertyNamesList = Arrays.asList("cognome", "linkPagina", "lingua");
        super.formPropertyNamesList = Arrays.asList("cognome", "linkPagina", "lingua");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDownload = true;
        super.usaBottoneNew = true;
        super.usaBottoneEdit = true;
        super.usaBottoneTest = false;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneUploadAll = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneUploadModuloAlfabetizzato = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        Anchor anchor = WAnchor.build(CATEGORIA_COGNOMI, CATEGORIA);
        alertPlaceHolder.add(new Span(anchor));

        message = "Cognomi presenti in alcune (sotto)categorie su wiki.";
        addSpan(ASpan.text(message).verde());

        message = String.format("Download%SLegge le sottocategorie di %s.", FORWARD, COGNOMI_LINGUA);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di cognomi sono gestiti dalla task Cognome.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Nessun Upload su wiki: sono categorie in sola lettura", FORWARD, backend.uploadTestName, backend.sorgenteDownload);
        addSpan(ASpan.text(message).blue().small());
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopStandard() {
        super.fixBottoniTopStandard();

        searchFieldPagina = new TextField();
        searchFieldPagina.setPlaceholder(TAG_ALTRE_BY + "pagina");
        searchFieldPagina.setClearButtonVisible(true);
        searchFieldPagina.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPagina);
    }

    @Override
    protected void fixBottoniTopSpecifici() {
        super.fixBottoniTopSpecifici();

        comboLingue = new ComboBox<>();
        comboLingue.setPlaceholder("Lingue");
        comboLingue.setWidth("14rem");
        comboLingue.getElement().setProperty("title", "Filtro di selezione");
        comboLingue.setClearButtonVisible(true);
        comboLingue.setItems(backend.getLingue());
        comboLingue.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboLingue);

    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<CognomeCategoria> items = (List) super.sincroFiltri();

        final String textSearchPagina = searchFieldPagina != null ? searchFieldPagina.getValue() : VUOTA;
        if (textService.isValidNoSpace(textSearchPagina)) {
            items = items
                    .stream()
                    .filter(cat -> cat.linkPagina != null ? cat.linkPagina.matches("^(?i)" + textSearchPagina + ".*$") : false)
                    .toList();
        }
        else {
            if (textSearchPagina.equals(SPAZIO)) {
                items = items
                        .stream()
                        .filter(cat -> cat.linkPagina == null)
                        .toList();
            }
        }

        if (comboLingue != null && comboLingue.getValue() != null) {
            if (comboLingue.getValue() instanceof String lingua) {
                items = items.stream().filter(cognomeCategoria -> cognomeCategoria.lingua.equals(lingua)).toList();
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