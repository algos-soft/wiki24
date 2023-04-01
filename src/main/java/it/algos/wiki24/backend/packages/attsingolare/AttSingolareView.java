package it.algos.wiki24.backend.packages.attsingolare;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
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
        String modulo = PATH_WIKI + PATH_MODULO;

        Anchor anchor1 = new Anchor(modulo + PATH_PLURALE + ATT_LOWER, ATT + " singolare/plurale");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(modulo + PATH_EX + ATT_LOWER, PATH_EX + ATT_LOWER);
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2));

        message = "Tabella attività singolari del (primo) parametro 'attività' recuperate dal modulo 'singolare/plurale' sul server wiki.";
        addSpan(ASpan.text(message).verde());
        message = "Vengono aggiunte anche le 'attività' previste dal modulo 'ex-attività' sul server wiki.";
        addSpan(ASpan.text(message).verde());
        message = "L'elaborazione di questa tabella calcola le voci biografiche che usano ogni singola attività singolare.";
        addSpan(ASpan.text(message).verde());

        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari sono convertite in minuscolo.";
        addSpan(ASpan.text(message).rosso());
        message = String.format("ResetOnlyEmpty effettua il download dei due moduli wiki: %s%s%s", PATH_PLURALE + ATT_LOWER, VIRGOLA_SPAZIO, PATH_EX + ATT_LOWER);
        addSpan(ASpan.text(message).rosso().small());
        message = "Il download dei link alla pagina di attività, la lista dei plurali, l'elaborazione delle liste biografiche e gli upload sono gestiti dalla task AttPlurale.";
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

        fixBottoniTopSpecificiAttivita();
    }

    protected void fixBottoniTopSpecificiAttivita() {
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

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }

        return (List) items;
    }


}// end of crud @Route view class