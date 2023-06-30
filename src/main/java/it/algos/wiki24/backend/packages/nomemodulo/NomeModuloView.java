package it.algos.wiki24.backend.packages.nomemodulo;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.*;
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

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Jun-2023
 * Time: 12:06
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("NomiModulo")
@Route(value = "nomemodulo", layout = MainLayout.class)
public class NomeModuloView extends WikiView {


    //--per eventuali metodi specifici
    private NomeModuloBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NomeModuloView(@Autowired final NomeModuloBackend crudBackend) {
        super(crudBackend, NomeModulo.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "linkPagina");
        super.formPropertyNamesList = Arrays.asList("nome", "linkPagina");

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

        WAnchor anchor = WAnchor.build("Modulo:Incipit nomi", MODULO);
        WAnchor anchor2 = WAnchor.build(backend.sorgenteDownload, TEMPLATE);
        WAnchor anchor3 = WAnchor.build(backend.uploadTestName, TEST);
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Sono elencate le pagine di riferimento per ogni nome (esempio: 'Archibald->Arcibaldo') da inserire nell'incipit della lista.";
        addSpan(ASpan.text(message).verde());
        message = "I nomi mancanti nel template puntano, in automatico, ad una pagina con lo stesso nome.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("I nomi template mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale");
        addSpan(ASpan.text(message).rosso().small());
        message = "Quando si crea la lista nomi, i nomi template vengono scaricati e aggiunti alla lista stessa.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sCancella tutto e scarica il template wiki: %s.", FORWARD, backend.sorgenteDownload);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Upload moduli%s1 lista wiki modificata e riordinata in ordine alfabetico sul test %s. (da copiare poi su %s)", FORWARD, backend.uploadTestName, backend.sorgenteDownload);
        addSpan(ASpan.text(message).blue().small());
        message = "Se non si vogliono le modifiche, fare prima un Download";
        addSpan(ASpan.text(message).rosso().small());
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

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<NomeModulo> items = (List) super.sincroFiltri();


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