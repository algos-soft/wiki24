package it.algos.wiki24.backend.packages.anno;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.time.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 08-Jul-2022
 * Time: 06:34
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Anni")
@Route(value = "annoWiki", layout = MainLayout.class)
public class AnnoWikiView extends WikiView {


    //--per eventuali metodi specifici
    private AnnoWikiBackend backend;


    @Autowired
    public SecoloBackend secoloBackend;


    private ComboBox comboSecolo;

    private Checkbox checkBoxSecolo;

    //--per eventuali metodi specifici
    private AnnoWikiDialog dialog;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public AnnoWikiView(@Autowired final AnnoWikiBackend crudBackend) {
        super(crudBackend, AnnoWiki.class);
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

        super.gridPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti", "pageNati", "pageMorti", "natiOk", "mortiOk");
        super.formPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti", "pageNati", "pageMorti", "natiOk", "mortiOk");

        super.sortOrder = Sort.by(Sort.Direction.DESC, "ordine");
        super.usaRowIndex = false;
        super.usaDataProvider = true;

        super.usaBottoneReset = true;
        super.usaReset = true;
        super.usaBottoneDownload = false;
        super.usaBottoneElabora = true;
        super.usaBottoneStatistiche = false;
        super.usaBottoneUploadStatistiche = true;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneSearch = false;
        super.usaBottoneDeleteAll = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneGiornoAnno = true;
        super.usaBottonePaginaNati = true;
        super.usaBottonePaginaMorti = true;
        super.usaBottoneTest = false;
        super.usaBottoneTestNati = true;
        super.usaBottoneTestMorti = true;
        super.usaBottoneUploadNati = true;
        super.usaBottoneUploadMorti = true;

        super.fixPreferenzeBackend();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        Anchor anchor1 = WAnchor.build("Categoria:Liste di nati per anno", "Cat. Nati");
        Anchor anchor2 = WAnchor.build("Categoria:Liste di morti per anno", "Cat. Morti");
        Anchor anchor3 = WAnchor.build(PATH_STATISTICHE_ANNI, STATISTICHE);
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = String.format("Tabella degli anni recuperati dalla tabella 'Anno' di %s", VaadVar.frameworkVaadin24);
        addSpan(ASpan.text(message).verde());

        message = String.format("Reset%scostruisce la tabella, azzerando l'elaborazione", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%scalcola le voci biografiche che usano ogni singolo anno.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload %stutte le pagine nati/morti per ogni anno.", FORWARD, PATH_PLURALE + ATT_LOWER, VIRGOLA_SPAZIO, PATH_EX + ATT_LOWER);
        addSpan(ASpan.text(message).verde());

        String message = String.format("%s: %s (vedi view)", "Pagine degli anni da cancellare", backend.countListeDaCancellare()); //@todo va in errore
        addSpan(ASpan.text(message).rosso().small());
        addSpan(ASpan.text("Scheduled: TaskAnni (base giornaliera/2 giorni) e TaskStatistiche (base settimanale)").blue().small());
        addSpan(ASpan.text("Elaborazione: Prima di ogni statistica").blue().small());

    }


    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopSpecifici() {
        super.fixBottoniTopSpecifici();

        comboSecolo = new ComboBox<>();
        comboSecolo.setPlaceholder("Secolo");
        comboSecolo.getElement().setProperty("title", "Filtro di selezione");
        comboSecolo.setClearButtonVisible(true);
        comboSecolo.setItems(secoloBackend.findAllSortCorrente());
        comboSecolo.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboSecolo);

        checkBoxSecolo = new Checkbox("Ascendente");
        checkBoxSecolo.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(checkBoxSecolo);

    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<AnnoWiki> items = null;
        boolean ascendente = false;

        if (comboSecolo != null) {
            if (checkBoxSecolo != null) {
                ascendente = checkBoxSecolo.getValue();
            }
            if (comboSecolo.getValue() != null) {
                if (comboSecolo.getValue() instanceof Secolo secolo) {
                    items = backend.findAllBySecolo(secolo, ascendente);
                }
            }
            else {
                super.fixItems();
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
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    //    @Override
    protected void addColumnsOneByOne2() {
        super.addColumnsOneByOne();

        Grid.Column paginaNati = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = ((AnnoWiki) entity).pageNati;
            int numVoci = ((AnnoWiki) entity).bioNati;
            Anchor anchor;
            if (((AnnoWiki) entity).esistePaginaNati) {
                if (numVoci == 0) {
                    anchor = new Anchor(PATH_WIKI_EDIT + wikiTitle + TAG_DELETE, wikiTitle);
                    anchor.getElement().getStyle().set("color", "red");
                }
                else {
                    anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
                    anchor.getElement().getStyle().set("color", "green");
                }
                anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            }
            else {
                anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
                anchor.getElement().getStyle().set("color", "red");
            }
            return new Span(anchor);
        })).setHeader("Nati").setKey("pageNati").setFlexGrow(0).setWidth("12em");

        Grid.Column paginaMorti = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = ((AnnoWiki) entity).pageMorti;
            int numVoci = ((AnnoWiki) entity).bioMorti;
            Anchor anchor;
            if (((AnnoWiki) entity).esistePaginaMorti) {
                if (numVoci == 0) {
                    anchor = new Anchor(PATH_WIKI_EDIT + wikiTitle + TAG_DELETE, wikiTitle);
                    anchor.getElement().getStyle().set("color", "red");
                }
                else {
                    anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
                    anchor.getElement().getStyle().set("color", "green");
                }
                anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            }
            else {
                anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
                anchor.getElement().getStyle().set("color", "red");
            }
            return new Span(anchor);
        })).setHeader("Morti").setKey("pageMorti").setFlexGrow(0).setWidth("12em");

        Grid.Column ordine = grid.getColumnByKey("ordine");
        Grid.Column nome = grid.getColumnByKey("nome");
        Grid.Column bioNati = grid.getColumnByKey("bioNati");
        Grid.Column bioMorti = grid.getColumnByKey("bioMorti");
        Grid.Column natiOk = grid.getColumnByKey("natiOk");
        Grid.Column mortiOk = grid.getColumnByKey("mortiOk");

        grid.setColumnOrder(ordine, nome, bioNati, bioMorti, paginaNati, paginaMorti, natiOk, mortiOk);
    }

    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     */
    @Override
    protected void fixBodyLayout() {
        super.fixBodyLayout();
    }

    protected void sincroCancellare() {
        List<AnnoWiki> items = null;

        if (boxPagineDaCancellare != null) {
            if (boxPagineDaCancellare.getValue()) {
                items = backend.fetchDaCancellare();
            }
            else {
                items = backend.findAllSortCorrente();
            }
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }
    }

    /**
     * Apre una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void viewStatistiche() {
        wikiApiService.openWikiPage(PATH_ANNI);
    }

    /**
     * Esegue un azione di upload delle statistiche, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    public void uploadStatistiche() {
        appContext.getBean(StatisticheAnni.class).esegue();
    }


    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageGiornoAnno() {
        wikiApiService.openWikiPage(getNomeAnno());
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageNati() {
        AnnoWiki anno = (AnnoWiki) super.wikiPage();
        wikiApiService.openWikiPage(anno.pageNati);
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageMorti() {
        AnnoWiki anno = (AnnoWiki) super.wikiPage();
        wikiApiService.openWikiPage(anno.pageMorti);
    }

    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void testPaginaNati() {
        appContext.getBean(UploadAnni.class, getNomeAnno()).nascita().test().upload();
        reload();
    }


    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void testPaginaMorti() {
        appContext.getBean(UploadAnni.class, getNomeAnno()).morte().test().upload();
        reload();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public void upload() {
        if (comboSecolo != null && comboSecolo.getValue() == null) {
            AUpload.upload(this::uploadAll);
        }
        else {
            AUpload.uploadSelezione(this::uploadSecolo);
        }
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    public void uploadAll() {
        long inizio = System.currentTimeMillis();
        WResult result = backend.uploadAll();
        super.fixUpload(inizio, "degli anni");
        reload();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    public void uploadSecolo() {
        long inizio = System.currentTimeMillis();
        Secolo secolo = null;
        boolean ascendente=false;

        if (checkBoxSecolo != null) {
            ascendente = checkBoxSecolo.getValue();
        }

        if (comboSecolo != null && comboSecolo.getValue() != null) {
            secolo = (Secolo) comboSecolo.getValue();
        }

        WResult result = ((AnnoWikiBackend) backend).uploadSecolo(secolo,ascendente);
        super.fixUpload(inizio, String.format("del secolo %s", secolo != null ? secolo.nome : VUOTA));
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void uploadPaginaNati() {
        appContext.getBean(UploadAnni.class, getNomeAnno()).nascita().upload();
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void uploadPaginaMorti() {
        appContext.getBean(UploadAnni.class, getNomeAnno()).morte().upload();
        reload();
    }

    public String getNomeAnno() {
        AnnoWiki anno = (AnnoWiki) super.wikiPage();
        return anno != null ? anno.nome : VUOTA;
    }

}// end of crud @Route view class