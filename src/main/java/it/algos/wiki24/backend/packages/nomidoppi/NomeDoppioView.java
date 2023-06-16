package it.algos.wiki24.backend.packages.nomidoppi;

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
 * Date: Mon, 12-Jun-2023
 * Time: 18:21
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("NomiDoppi")
@Route(value = "nomedoppio", layout = MainLayout.class)
public class NomeDoppioView extends WikiView {


    //--per eventuali metodi specifici
    private NomeDoppioBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NomeDoppioView(@Autowired final NomeDoppioBackend crudBackend) {
        super(crudBackend, NomeDoppio.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome");
        super.formPropertyNamesList = Arrays.asList("nome");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDownload = true;
        super.usaBottoneTest = false;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneUploadAll = false;
        super.usaBottonePaginaWiki = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        String progetto = PATH_WIKI + PATH_TABELLA_NOMI_DOPPI;
        String categoria = PATH_CATEGORIA + "Prenomi composti";

        Anchor anchor1 = new Anchor(progetto, "Lista progetto");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(categoria, "Categoria");
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2));

        message = "Sono elencati i nomi doppi (ad esempio 'Maria Teresa').";
        addSpan(ASpan.text(message).verde());

        message = String.format("I nomi doppi mantengono le maiuscole e minuscole riportate nella lista su wiki");
        addSpan(ASpan.text(message).rosso());
        message = "Quando si crea la lista nomi, i nomi doppi vengono scaricati e aggiunti alla lista stessa.";
        addSpan(ASpan.text(message).rosso());
        
        message = String.format("ResetOnlyEmpty%sDownload.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sCancella tutto e scarica la lista wiki: %s.", FORWARD, "Antroponimi/Nomi doppi");
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sNon previsto.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sNon previsto", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche è gestito dalla task Nome.";
        addSpan(ASpan.text(message).rosso());
    }

}// end of crud @Route view class