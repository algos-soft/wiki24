package it.algos.wiki24.backend.packages.attplurale;

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
 * Date: Sat, 25-Mar-2023
 * Time: 16:50
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("AttPlurale")
@Route(value = "attPlurale", layout = MainLayout.class)
public class AttPluraleView extends WikiView {


    //--per eventuali metodi specifici
    private AttPluraleBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public AttPluraleView(@Autowired final AttPluraleBackend crudBackend) {
        super(crudBackend, AttPlurale.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "singolari", "numBio", "numSingolari", "superaSoglia", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "singolari", "lista", "nazione", "numBio", "superaSoglia", "esisteLista");

        super.usaBottoneReset = true;
        super.usaReset = true;
        super.usaBottoneDeleteAll = false;
        super.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneStatistiche = true;
        super.usaBottoneUploadStatistiche = true;
        super.usaBottoneUploadAll = true;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneTest = true;
        super.usaBottoneDownload = true;
        super.usaInfoDownload = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        String modulo = PATH_WIKI + PATH_MODULO;

        Anchor anchor1 = new Anchor(modulo + PATH_LINK + ATT_LOWER, PATH_LINK + ATT_LOWER);
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(PATH_WIKI + PATH_STATISTICHE_ATTIVITA, STATISTICHE);
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2));

        message = "Tabella attività plurali del parametro 'attività', ricavate dalla task AttSingolare. ";
        addSpan(ASpan.text(message).verde());
        message = "Tabella dei link alla pagina dell'attività recuperati dal modulo plurale -> attività sul server wiki.";
        addSpan(ASpan.text(message).verde());

        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività plurali sono convertite in minuscolo.";
        addSpan(ASpan.text(message).rosso());

        message = String.format("Reset%sPRIMA esegue un download di AttSingolare, poi crea la tabella ricavandola dalle attività DISTINCT di AttSingolare", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%s1 modulo wiki: %s tramite resetOnlyEmpty()", FORWARD, PATH_LINK + ATT_LOWER);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%scalcola le voci biografiche che usano ogni singola attività plurale e la presenza o meno della pagina con la lista di ogni attività", FORWARD);
        addSpan(ASpan.text(message).verde());
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopStandard() {
        super.fixBottoniTopStandard();

        if (searchField != null) {
            searchField.setPlaceholder(TAG_ALTRE_BY + "plurale");
        }
    }

}// end of crud @Route view class