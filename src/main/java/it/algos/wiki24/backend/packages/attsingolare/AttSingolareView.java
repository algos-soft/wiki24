package it.algos.wiki24.backend.packages.attsingolare;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.ui.components.*;
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
 * Date: Sat, 25-Mar-2023
 * Time: 16:49
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("AttSingolare")
@Route(value = "attsingolare", layout = MainLayout.class)
public class AttSingolareView extends WikiView {


    //--per eventuali metodi specifici
    private AttSingolareBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public AttSingolareView(@Autowired final AttSingolareBackend crudBackend) {
        super(crudBackend, AttSingolare.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "plurale", "ex", "numBio");
        super.formPropertyNamesList = Arrays.asList("nome", "plurale", "ex", "numBio");

        super.usaBottoneReset = false;
        super.usaBottoneDeleteAll = false;
        this.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        this.usaBottoneUploadAll = false;
        this.usaBottoneTest = false;
        this.usaBottoneDownload = true;
        this.usaBottonePaginaWiki = false;
        this.usaInfoDownload = true;
        super.usaBottoneUploadModuloAlfabetizzato = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        Button button = new Button(ATT + " singolare/plurale");
        button.addClickListener(click -> wikiApiService.openWikiPage(PATH_MODULO_ATTIVITA));
        Button button2 = new Button(PATH_EX + ATT_LOWER);
        button2.addClickListener(click -> wikiApiService.openWikiPage(PATH_MODULO + PATH_EX + ATT_LOWER));
        alertPlaceHolder.add(new Span(fixButton(button), new Label(SEP), fixButton(button2)));

        message = "Tabella attività singolari del (primo) parametro 'attività' recuperate dal modulo 'singolare/plurale' sul server wiki.";
        addSpan(ASpan.text(message).verde());
        message = "Vengono aggiunte anche le 'attività' previste dal modulo 'ex-attività' sul server wiki.";
        addSpan(ASpan.text(message).verde());
        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari sono convertite in minuscolo mentre mantengono spazi e caratteri accentati.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sCancella tutto e scarica 2 moduli wiki: %s%s%s.", FORWARD, PATH_SINGOLARE + PATH_PLURALE + ATT_LOWER, VIRGOLA_SPAZIO, PATH_EX + ATT_LOWER);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola attività singolare.", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = "Il download dei link alla pagina di attività, la lista dei plurali, l'elaborazione delle liste biografiche e gli upload delle liste di Attività sono gestiti dalla task AttPlurale.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Upload moduli%s2 moduli wiki riordinati in ordine alfabetico in %s e %s", FORWARD, "Utente:Biobot/ModuloPluraleAttivita", "Utente:Biobot/ModuloExAttivita");
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

        searchFieldPlurale = new TextField();
        searchFieldPlurale.setPlaceholder(TAG_ALTRE_BY + "plurale");
        searchFieldPlurale.setClearButtonVisible(true);
        searchFieldPlurale.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPlurale);
    }

    protected void fixBottoniTopSpecifici() {
        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Ex attività");
        boxBox.setIndeterminate(true);
        boxBox.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout = new HorizontalLayout(boxBox);
        layout.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout);

        this.add(topPlaceHolder2);
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<AttSingolare> items = (List) super.sincroFiltri();

        if (boxBox != null && !boxBox.isIndeterminate()) {
            items = items.stream().filter(att -> att.ex == boxBox.getValue()).toList();
        }

        final String textSearchPlurale = searchFieldPlurale != null ? searchFieldPlurale.getValue() : VUOTA;
        if (textService.isValidNoSpace(textSearchPlurale)) {
            items = items
                    .stream()
                    .filter(att -> att.plurale != null ? att.plurale.matches("^(?i)" + textSearchPlurale + ".*$") : false)
                    .toList();
        }
        else {
            if (textSearchPlurale.equals(SPAZIO)) {
                items = items
                        .stream()
                        .filter(att -> att.plurale == null)
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