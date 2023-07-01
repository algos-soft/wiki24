package it.algos.wiki24.backend.packages.nomecategoria;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
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
 * Date: Fri, 30-Jun-2023
 * Time: 21:18
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("NomiCategoria")
@Route(value = "nomecategoria", layout = MainLayout.class)
public class NomeCategoriaView extends WikiView {


    //--per eventuali metodi specifici
    private NomeCategoriaBackend backend;

    private ComboBox<AETypeGenere> comboGenere;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NomeCategoriaView(@Autowired final NomeCategoriaBackend crudBackend) {
        super(crudBackend, NomeCategoria.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "linkPagina", "typeGenere");
        super.formPropertyNamesList = Arrays.asList("nome", "linkPagina", "typeGenere");

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

        Anchor anchor = WAnchor.build(CATEGORIA + DUE_PUNTI + backend.catMaschile, "Maschili");
        Anchor anchor2 = WAnchor.build(CATEGORIA + DUE_PUNTI + backend.catFemminile, "Femminili");
        Anchor anchor3 = WAnchor.build(CATEGORIA + DUE_PUNTI + backend.catEntrambi, "Entrambi");
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Sono elencati i prenomi presenti nelle 3 categorie su wiki.";
        addSpan(ASpan.text(message).verde());

        message = String.format("Download%SLegge i valori da %s.", FORWARD, backend.catMaschile);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%SLegge i valori da %s.", FORWARD, backend.catFemminile);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%SLegge i valori da %s.", FORWARD, backend.catEntrambi);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Nessun Upload su wiki: sono categorie in sola lettura", FORWARD, backend.uploadTestName, backend.sorgenteDownload);
        addSpan(ASpan.text(message).blue().small());
    }


    protected void fixBottoniTopSpecifici() {
        comboGenere = new ComboBox<>();
        comboGenere.setPlaceholder("Genere");
        comboGenere.getElement().setProperty("title", "Filtro di selezione");
        comboGenere.setClearButtonVisible(true);
        comboGenere.setItems(AETypeGenere.getAllEnums());
        comboGenere.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboGenere);

//        this.add(topPlaceHolder2);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<NomeCategoria> items = (List) super.sincroFiltri();

        if (items == null) {
            return null;
        }

        if (comboGenere != null && comboGenere.getValue() != null) {
                items = items.stream().filter(nome -> nome.typeGenere.equals(comboGenere.getValue())).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }

        return (List) items;
    }

}// end of crud @Route view class