package it.algos.wiki24.backend.packages.giorno;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Giorni")
@Route(value = "giornoWiki", layout = MainLayout.class)
public class GiornoWikiView extends WikiView {


    //--per eventuali metodi specifici
    private GiornoWikiBackend backend;

    //--per eventuali metodi specifici
    private GiornoWikiDialog dialog;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public GiornoWikiView(@Autowired final GiornoWikiBackend crudBackend) {
        super(crudBackend, GiornoWiki.class);
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

        super.gridPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti");
        super.formPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti");

        super.sortOrder = Sort.by(Sort.Direction.ASC, "ordine");
        super.usaRowIndex = false;

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

        Anchor anchor1 = new Anchor(PATH_WIKI + "Categoria:Liste di nati per giorno", "Nati");
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(PATH_WIKI + "Categoria:Liste di morti per giorno", "Morti");
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor3 = new Anchor(PATH_WIKI + PATH_STATISTICHE_GIORNI, STATISTICHE);
        anchor3.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = String.format("Tabella dei giorni dell'anno recuperati dalla tabella 'Giorno' di %s", VaadVar.frameworkVaadin24);
        addSpan(ASpan.text(message).verde());

        message = String.format("Reset%scostruisce la tabella, azzerando l'elaborazione", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%scalcola le voci biografiche che usano ogni singolo giorno dell'anno.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload %stutte le pagine nati/morti per ogni giorno.", FORWARD, PATH_PLURALE + ATT_LOWER, VIRGOLA_SPAZIO, PATH_EX + ATT_LOWER);
        addSpan(ASpan.text(message).verde());

        int errati = paginaBackend.countGiorniErrati();
        message = String.format("%s: %s", "Pagine dei giorni da cancellare (vedi view)", errati);
        addSpan(ASpan.text(message).rosso().small());
        addSpan(ASpan.text("Scheduled: TaskGiorni (base giornaliera/2 giorni) e TaskStatistiche (base settimanale)").blue().small());
        addSpan(ASpan.text("Elaborazione: Prima di ogni statistica").blue().small());

    }


    /**
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addColumnsOneByOne() {
        super.addColumnsOneByOne();

        grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = ((GiornoWiki) entity).pageNati;
            Anchor anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set("color", "green");
            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            return new Span(anchor);
        })).setHeader("Nati").setKey("pageNati").setFlexGrow(0).setWidth("14em");

        grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = ((GiornoWiki) entity).pageMorti;
            Anchor anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set("color", "green");
            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            return new Span(anchor);
        })).setHeader("Morti").setKey("pageMorti").setFlexGrow(0).setWidth("14em");
    }


    /**
     * Apre una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void viewStatistiche() {
        wikiApiService.openWikiPage(PATH_GIORNI);
    }

    /**
     * Esegue un azione di upload delle statistiche, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     * Prima esegue una Elaborazione <br>
     */
    @Override
    public void uploadStatistiche() {
        WResult result = appContext.getBean(StatisticheGiorni.class).upload();
        logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.upload).usaDb());
        super.uploadStatistiche();
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageGiornoAnno() {
        wikiApiService.openWikiPage(getNomeGiorno());
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageNati() {
        GiornoWiki giorno = (GiornoWiki) super.wikiPage();
        wikiApiService.openWikiPage(giorno.pageNati);
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void wikiPageMorti() {
        GiornoWiki giorno = (GiornoWiki) super.wikiPage();
        wikiApiService.openWikiPage(giorno.pageMorti);
    }

    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void testPaginaNati() {
        appContext.getBean(UploadGiorni.class).nascita().test().upload(getNomeGiorno());
        reload();
    }


    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void testPaginaMorti() {
        appContext.getBean(UploadGiorni.class).morte().test().upload(getNomeGiorno());
        reload();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public void uploadAll() {
        long inizio = System.currentTimeMillis();
        appContext.getBean(UploadGiorni.class).uploadAll();
        super.fixUpload(inizio, "dei giorni");
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void uploadPaginaNati() {
        WResult result = appContext.getBean(UploadGiorni.class).nascita().upload(getNomeGiorno());
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void uploadPaginaMorti() {
        WResult result = appContext.getBean(UploadGiorni.class).morte().upload(getNomeGiorno());
        reload();
    }

    public String getNomeGiorno() {
        GiornoWiki giorno = (GiornoWiki) super.wikiPage();
        return giorno != null ? giorno.nome : VUOTA;
    }

}// end of crud @Route view class