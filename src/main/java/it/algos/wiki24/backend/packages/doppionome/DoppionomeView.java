package it.algos.wiki24.backend.packages.doppionome;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 19:34
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("DoppiNomi")
@Route(value = TAG_DOPPIO_NOME, layout = MainLayout.class)
public class DoppionomeView extends WikiView {


    //--per eventuali metodi specifici
    private DoppionomeBackend backend;


    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public DoppionomeView(@Autowired final DoppionomeBackend crudBackend) {
        super(crudBackend, Doppionome.class);
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
        super.sortOrder = Sort.by(Sort.Direction.ASC, "nome");
        super.wikiModuloTitle = PATH_TABELLA_NOMI_DOPPI;

        super.usaBottoneUploadAll = false;
        super.usaBottoneUploadStatistiche = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneTest = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        message = "Sono elencati i nomi doppi (ad esempio 'Maria Teresa').";
        message += " BioBot crea una lista di biografati una volta superate le 50 biografie (tra nomi e nomi doppi).";
        addSpan(ASpan.text(message).verde());

        message = "Vedi anche la ";
        Span span = ASpan.text(message).verde().bold();
        Anchor anchor = new Anchor(VaadCost.PATH_WIKI + "Categoria:Prenomi composti", "[[categoria:Prenomi composti]]");
        anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        anchor.getElement().getStyle().set(AEFontSize.HTML, AEFontSize.em9.getTag());
        anchor.getElement().getStyle().set(AETypeColor.HTML, AETypeColor.verde.getTag());
        alertPlaceHolder.add(new Span(span, anchor));

        message = "La lista nomi prevede solo nomi singoli a cui vengono aggiunti questi nomi doppi accettabili.";
        message += " Quando si crea la lista nomi, i nomi doppi vengono scaricati e aggiunti alla lista stessa.";
        addSpan(ASpan.text(message).rosso());
    }


}// end of crud @Route view class