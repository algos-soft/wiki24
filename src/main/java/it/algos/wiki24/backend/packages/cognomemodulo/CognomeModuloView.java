package it.algos.wiki24.backend.packages.cognomemodulo;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
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
 * Date: Wed, 21-Jun-2023
 * Time: 18:10
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("CognomiModulo")
@Route(value = "cognomemodulo", layout = MainLayout.class)
public class CognomeModuloView extends WikiView {


    //--per eventuali metodi specifici
    private CognomeModuloBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public CognomeModuloView(@Autowired final CognomeModuloBackend crudBackend) {
        super(crudBackend, CognomeModulo.class);
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

        super.gridPropertyNamesList = Arrays.asList("cognome", "linkPagina");
        super.formPropertyNamesList = Arrays.asList("cognome", "linkPagina");

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

        Button button = new Button("Sorgente");
        button.addClickListener(click -> wikiApiService.openWikiPage(backend.sorgenteDownload));
        Button button2 = new Button("Test");
        button2.addClickListener(click -> wikiApiService.openWikiPage(backend.uploadTest));
        alertPlaceHolder.add(new Span(fixButton(button), new Label(SEP), fixButton(button2)));

        message = "Sono elencate le pagine di riferimento per ogni cognome (esempio: 'Bianchi->Bianchi (cognome)') da inserire nell'incipit della lista.";
        addSpan(ASpan.text(message).verde());
        message = "I cognomi vuoti nel modulo puntano, in automatico, ad una pagina con lo stesso cognome.";
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("I cognomi modulo mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale");
        addSpan(ASpan.text(message).rosso().small());
        message = "Quando si crea la lista cognomi, i cognomi template vengono scaricati e aggiunti alla lista stessa.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sCancella tutto e scarica il template wiki: %s.", FORWARD, backend.sorgenteDownload);
        addSpan(ASpan.text(message).verde());
        message = "L'elaborazione delle liste biografiche e gli upload delle liste di cognomi sono gestiti dalla task Cognome.";
        addSpan(ASpan.text(message).rosso().small());

    }

}// end of crud @Route view class