package it.algos.wiki24.backend.packages.nazsingolare;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
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
 * Date: Sat, 18-Mar-2023
 * Time: 15:17
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("NazSingolare")
@Route(value = "nazsingolare", layout = MainLayout.class)
public class NazSingolareView extends WikiView {


    //--per eventuali metodi specifici
    private NazSingolareBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NazSingolareView(@Autowired final NazSingolareBackend crudBackend) {
        super(crudBackend, NazSingolare.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "plurale", "numBio");
        super.formPropertyNamesList = Arrays.asList("nome", "plurale", "numBio");

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
        String modulo = PATH_WIKI + PATH_MODULO;

        Anchor anchor1 = new Anchor(modulo + PATH_PLURALE + NAZ_LOWER, NAZ + " singolare/plurale");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1));

        message = "Tabella nazionalità singolari del parametro 'nazionalità' recuperate dal modulo 'singolare/plurale' sul server wiki.";
        addSpan(ASpan.text(message).verde());

        message = "Indipendentemente da come sono scritte nel modulo, tutte le nazionalità singolari sono convertite in minuscolo.";
        addSpan(ASpan.text(message).rosso());

        message = String.format("Reset%sDownload.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sCancella tutto e poi scarica 1 modulo wiki: %s.", FORWARD, PATH_SINGOLARE + PATH_PLURALE + NAZ_LOWER);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sCalcola le voci biografiche che usano ogni singola nazionalità singolare.", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = "Il download dei link alla pagina della nazione, la lista dei plurali, l'elaborazione delle liste biografiche e gli upload sono gestiti dalla task NazPlurale.";
        addSpan(ASpan.text(message).rosso());
        message = String.format("Upload moduli%s1 modulo wiki riordinato in ordine alfabetico in %s", FORWARD, "Utente:Biobot/ModuloPluraleNazionalita");
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
    }

}// end of crud @Route view class