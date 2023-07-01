package it.algos.wiki24.backend.packages.nomedoppio;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.schedule.*;
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
@Route(value = TAG_NOME_DOPPIO, layout = MainLayout.class)
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

        Anchor anchor = WAnchor.build(backend.sorgenteDownload, PROGETTO);
        alertPlaceHolder.add(new Span(anchor));

        message = String.format("Nomi doppi (esempio: 'Maria Teresa') elencati nella pagina di progetto",FORWARD, backend.sorgenteDownload);
        addSpan(ASpan.text(message).verde());

        message = String.format("I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale");
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sLegge la pagina: %s.", FORWARD, backend.sorgenteDownload);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sRiscrive la pagina in ordine alfabetico.", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Upload%sElenco riordinato in ordine alfabetico. Scheduled %s", FORWARD, TaskStatistiche.INFO);
        addSpan(ASpan.text(message).blue().small());
    }


}// end of crud @Route view class