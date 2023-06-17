package it.algos.wiki24.backend.packages.nome;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
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

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 14-Jun-2023
 * Time: 09:10
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("nomi")
@Route(value = "nomi", layout = MainLayout.class)
public class NomeView extends WikiView {


    //--per eventuali metodi specifici
    private NomeBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NomeView(@Autowired final NomeBackend crudBackend) {
        super(crudBackend, Nome.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "numBio", "doppio", "superaSoglia", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "numBio");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDeleteAll = false;
        super.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneStatistiche = true;
        super.usaBottoneUploadStatistiche = true;
        super.usaBottoneUploadAll = true;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneTest = true;
        super.usaInfoDownload = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        String template = PATH_WIKI + "Template:Incipit lista nomi";
        String lista = PATH_ANTROPONIMI + TAG_LISTA_NOMI;
        String nomi = PATH_ANTROPONIMI + TAG_NOMI;
        String categoria = PATH_CATEGORIA + "Liste di persone per nome";

        Anchor anchor1 = new Anchor(template, "Incipit");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor2 = new Anchor(lista, TAG_LISTA_NOMI);
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor3 = new Anchor(nomi, TAG_NOMI);
        anchor3.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor4 = new Anchor(categoria, "Categoria");
        anchor4.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3, new Label(SEP), anchor4));

        message = String.format("ResetOnlyEmpty%sDownload.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sRecupera una lista di nomi distinti dalle biografie.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download del Template:Incipit. Aggiunge i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sAggiunge alla lista i nomi doppi.", FORWARD);
        addSpan(ASpan.text(message).verde());
//        message = String.format("Download%sCrea una nuova tabella ricavandola dalle attività DISTINCT di AttSingolare", FORWARD);
//        addSpan(ASpan.text(message).verde());
//        message = String.format("Download%sAggiunge un link alla paginaLista di ogni attività in base al nome dell'attività plurale", FORWARD);
//        addSpan(ASpan.text(message).verde());
//        message = String.format("Download%sScarica 1 modulo wiki: %s", FORWARD, PATH_LINK + ATT_LOWER);
//        addSpan(ASpan.text(message).verde());
//        message = String.format("Elabora%sCalcola le voci biografiche che usano ogni singola attività plurale e la effettiva presenza della paginaLista", FORWARD);
//        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di nomi con bio>50.", FORWARD);
        addSpan(ASpan.text(message).verde());
    }


    protected void fixBottoniTopSpecifici() {
        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Nomi doppi");
        boxBox.setIndeterminate(true);
        boxBox.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout = new HorizontalLayout(boxBox);
        layout.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout);

        this.add(topPlaceHolder2);
    }

}// end of crud @Route view class