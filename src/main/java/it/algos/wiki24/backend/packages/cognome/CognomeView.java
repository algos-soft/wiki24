package it.algos.wiki24.backend.packages.cognome;

import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.PATH_WIKI;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Aug-2022
 * Time: 08:43
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Cognomi")
@Route(value = "cognome", layout = MainLayout.class)
public class CognomeView extends WikiView {


    //--per eventuali metodi specifici
    private CognomeBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public CognomeView(@Autowired final CognomeBackend crudBackend) {
        super(crudBackend, Cognome.class);
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

        super.gridPropertyNamesList = Arrays.asList("cognome", "numBio");
        super.formPropertyNamesList = Arrays.asList("cognome", "numBio");
        super.sortOrder = Sort.by(Sort.Direction.DESC, "numBio");

        super.lastElaborazione = WPref.elaboraCognomi;
        super.durataElaborazione = WPref.elaboraCognomiTime;
        super.lastUpload = WPref.uploadCognomi;
        super.durataUpload = WPref.uploadCognomiTime;
        super.nextUpload = WPref.uploadCognomiPrevisto;

        super.usaBottoneDownload = false;
        super.usaBottoneUploadStatistiche = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneEdit = false;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneElabora = true;

        super.fixPreferenzeBackend();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        String cognomi = PATH_ANTROPONIMI + "Cognomi";
        String categoria = PATH_WIKI + "Categoria:Liste di persone per cognome";
        int numMongo = WPref.sogliaCognomiMongo.getInt();
        int numMWiki = WPref.sogliaCognomiWiki.getInt();

        Anchor anchor1 = new Anchor(cognomi, "Cognomi");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(categoria, "Categoria");
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor3 = new Anchor(PATH_STATISTICHE_COGNOMI, STATISTICHE);
        anchor3.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Sono elencati i cognomi.";
        message += " BioBot crea una lista di biografati una volta superate le 50 biografie che usano quel cognome.";
        addSpan(ASpan.text(message).verde());

//        message = "Vedi anche la ";
//        Span span = ASpan.text(message).verde().bold();
//        Anchor anchor = new Anchor(VaadCost.PATH_WIKI + "Categoria:Liste di persone per cognome", "[[categoria:Liste di persone per cognome]]");
//        anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
//        anchor.getElement().getStyle().set(AEFontSize.HTML, AEFontSize.em9.getTag());
//        anchor.getElement().getStyle().set(AETypeColor.HTML, AETypeColor.verde.getTag());
//        alertPlaceHolder.add(new Span(span, anchor));

        message = String.format("Elabora crea sul database locale mongo tutti i cognomi usati da almeno %d voci.", numMongo);
        addSpan(ASpan.text(message).rosso());
        message = String.format("Upload crea sul server wiki le pagine per tutti i cognomi usati da almeno %d voci.", numMWiki);
        addSpan(ASpan.text(message).rosso());
    }


    /**
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addColumnsOneByOne() {
        super.addColumnsOneByOne();

        Grid.Column pagina = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = textService.primaMaiuscola(((Cognome) entity).cognome);
            Anchor anchor = new Anchor(PATH_WIKI + PATH_COGNOMI + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            if (((Cognome) entity).esisteLista) {
                anchor.getElement().getStyle().set("color", "green");
            }
            else {
                anchor.getElement().getStyle().set("color", "red");
            }
            return new Span(anchor);
        })).setHeader("pagina").setKey("pagina").setFlexGrow(0).setWidth("18em");

        //        Grid.Column daCancellare = grid.addColumn(new ComponentRenderer<>(entity -> {
        //            String link = "https://it.wikipedia.org/w/index.php?title=Progetto:Biografie/Attivit%C3%A0/";
        //            link += textService.primaMaiuscola(((Attivita) entity).pluraleLista);
        //            link += TAG_DELETE;
        //            Label label = new Label("no");
        //            label.getElement().getStyle().set("color", "green");
        //            Anchor anchor = new Anchor(link, "del");
        //            anchor.getElement().getStyle().set("color", "red");
        //            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        //            Span span = new Span(anchor);
        //
        //            if (((Attivita) entity).esistePaginaLista && !((Attivita) entity).superaSoglia) {
        //                return span;
        //            }
        //            else {
        //                return label;
        //            }
        //        })).setHeader("X").setKey("cancella").setFlexGrow(0).setWidth("8em");

        Grid.Column cognome = grid.getColumnByKey("cognome");
        Grid.Column numBio = grid.getColumnByKey("numBio");

        grid.setColumnOrder(cognome, pagina, numBio);
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        long inizio = System.currentTimeMillis();
        List<Cognome> items = backend.findAllSortCorrente();
        logger.info(new WrapLog().exception(new AlgosException(String.format("Items %s", dateService.deltaText(inizio)))));

        final String textCognome = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValidNoSpace(textCognome)) {
            items = items
                    .stream()
                    .filter(cognome -> cognome.cognome != null ? cognome.cognome.matches("^(?i)" + textCognome + ".*$") : false)
                    .toList();
        }
        else {
            if (textCognome.equals(SPAZIO)) {
                items = items
                        .stream()
                        .filter(cognome -> cognome.cognome == null)
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

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void uploadPagina() {
        WResult result;
        Cognome cognome = getCognomeCorrente();

        if (cognome != null) {
            result = backend.uploadPagina(cognome.cognome);
            reload();
        }
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void upload() {
        appContext.getBean(UploadCognomi.class).uploadAll();
    }

    public Cognome getCognomeCorrente() {
        Cognome cognome = null;

        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            cognome = (Cognome) entityBean.get();
        }

        return cognome;
    }

}// end of crud @Route view class