package it.algos.wiki24.backend.packages.nome;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attplurale.*;
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

    private IndeterminateCheckbox boxDistinti;

    private IndeterminateCheckbox boxIncipit;

    private IndeterminateCheckbox boxDoppi;

    private IndeterminateCheckbox boxSuperaSoglia;

    private IndeterminateCheckbox boxEsisteLista;

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

        super.gridPropertyNamesList = Arrays.asList("nome", "numBio", "distinto", "incipit", "doppio", "superaSoglia", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "numBio", "distinto", "incipit", "doppio", "superaSoglia", "paginaLista", "esisteLista");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDeleteAll = false;
        super.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneUploadAll = true;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneTest = true;
        super.usaInfoDownload = true;
        super.usaBottoneEdit = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        int sogliaMongo = WPref.sogliaNomiMongo.getInt();
        int sogliaWiki = WPref.sogliaNomiWiki.getInt();
        String template = PATH_WIKI + "Template:Incipit lista nomi";
        String nomi = PATH_ANTROPONIMI + TAG_NOMI;
        String lista = PATH_ANTROPONIMI + TAG_LISTA_NOMI;
        String categoria = PATH_CATEGORIA + "Liste di persone per nome";

        Anchor anchor1 = new Anchor(template, "Incipit");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor2 = new Anchor(nomi, TAG_NOMI);
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor3 = new Anchor(lista, TAG_LISTA_NOMI);
        anchor3.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor4 = new Anchor(categoria, "Categoria");
        anchor4.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3, new Label(SEP), anchor4));

        message = "Tabella del parametro 'nome', ricavata dalle biografie.";
        addSpan(ASpan.text(message).verde());

        message = String.format("I nomi mantengono spazi, maiuscole e minuscole come in originale");
        addSpan(ASpan.text(message).rosso());
        message = String.format("Le pagine esistenti con bio<%d sono da cancellare (rosso bold)", sogliaWiki);
        addSpan(ASpan.text(message).rosso());
        message = String.format("Le pagine esistenti con bio>%d sono in visione (verde)", sogliaWiki);
        addSpan(ASpan.text(message).rosso());
        message = String.format("Le pagine non ancora esistenti con bio>%d sono da creare (blu)", sogliaWiki);
        addSpan(ASpan.text(message).rosso());

        message = String.format("ResetOnlyEmpty%sDownload.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sRecupera una lista di nomi distinti dalle biografie. Crea una entity se bio>%d", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download del Template:Incipit. Aggiunge tutti i valori alla lista; anche se bio<%d", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sAggiunge alla lista tutti i nomi doppi.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sEsegue un download. Calcola le voci biografiche che usano ogni singolo nome e la effettiva presenza della paginaLista", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di nomi con bio>%d.", FORWARD, sogliaWiki);
        addSpan(ASpan.text(message).verde());
    }


    protected void fixBottoniTopSpecifici() {
        boxDistinti = new IndeterminateCheckbox();
        boxDistinti.setLabel("Distinti");
        boxDistinti.setIndeterminate(true);
        boxDistinti.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutDistinti = new HorizontalLayout(boxDistinti);
        layoutDistinti.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutDistinti);

        boxIncipit = new IndeterminateCheckbox();
        boxIncipit.setLabel("Incipit");
        boxIncipit.setIndeterminate(true);
        boxIncipit.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutIncipit = new HorizontalLayout(boxIncipit);
        layoutIncipit.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutIncipit);

        boxDoppi = new IndeterminateCheckbox();
        boxDoppi.setLabel("Doppi");
        boxDoppi.setIndeterminate(true);
        boxDoppi.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutDoppi = new HorizontalLayout(boxDoppi);
        layoutDoppi.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutDoppi);

        boxSuperaSoglia = new IndeterminateCheckbox();
        boxSuperaSoglia.setLabel("Soglia");
        boxSuperaSoglia.setIndeterminate(true);
        boxSuperaSoglia.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutSoglia = new HorizontalLayout(boxSuperaSoglia);
        layoutSoglia.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutSoglia);

        boxEsisteLista = new IndeterminateCheckbox();
        boxEsisteLista.setLabel("Lista");
        boxEsisteLista.setIndeterminate(true);
        boxEsisteLista.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutLista = new HorizontalLayout(boxEsisteLista);
        layoutLista.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutLista);

        this.add(topPlaceHolder2);
    }


    /**
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addColumnsOneByOne() {
        super.addColumnsOneByOne();
        int sogliaWiki = WPref.sogliaNomiWiki.getInt();

        grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = textService.primaMaiuscola(((Nome) entity).paginaLista);
            Anchor anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            if (((Nome) entity).numBio < sogliaWiki) {
                anchor.getElement().getStyle().set("color", "red");
            }
            else {
                if (((Nome) entity).isEsisteLista()) {
                    anchor.getElement().getStyle().set("color", "green");
                }
                else {
                    anchor.getElement().getStyle().set("color", "blue");
                }
            }
            return new Span(anchor);
        })).setHeader("PaginaLista").setKey("paginaLista").setFlexGrow(0).setWidth("18em");
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<Nome> items = (List) super.sincroFiltri();

        if (boxDistinti != null && !boxDistinti.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.distinto == boxDistinti.getValue()).toList();
        }
        if (boxIncipit != null && !boxIncipit.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.incipit == boxIncipit.getValue()).toList();
        }
        if (boxDoppi != null && !boxDoppi.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.doppio == boxDoppi.getValue()).toList();
        }
        if (boxSuperaSoglia != null && !boxSuperaSoglia.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.superaSoglia == boxSuperaSoglia.getValue()).toList();
        }
        if (boxEsisteLista != null && !boxEsisteLista.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.esisteLista == boxEsisteLista.getValue()).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }

        return (List) items;
    }

}// end of crud @Route view class