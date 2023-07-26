package it.algos.wiki24.backend.packages.giorno;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
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

    @Autowired
    public MeseBackend meseBackend;

    private ComboBox comboMese;

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

        super.gridPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti", "pageNati", "pageMorti");
        super.formPropertyNamesList = Arrays.asList("ordine", "nome", "bioNati", "bioMorti", "pageNati", "pageMorti");

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

        Anchor anchor1 = WAnchor.build("Categoria:Liste di nati per giorno", "Cat. Nati");
        Anchor anchor2 = WAnchor.build("Categoria:Liste di morti per giorno", "Cat. Morti");
        Anchor anchor3 = WAnchor.build(PATH_STATISTICHE_GIORNI, STATISTICHE);
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
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopSpecifici() {
        super.fixBottoniTopSpecifici();

        comboMese = new ComboBox<>();
        comboMese.setPlaceholder("Mese");
        comboMese.getElement().setProperty("title", "Filtro di selezione");
        comboMese.setClearButtonVisible(true);
        comboMese.setItems(meseBackend.findAllSortCorrente());
        comboMese.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboMese);

    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<GiornoWiki> items = (List) super.sincroFiltri();
        if (items == null) {
            return null;
        }

        if (comboMese != null && comboMese.getValue() != null) {
            if (comboMese.getValue() instanceof Mese mese) {
                items = items.stream().filter(gio -> gio.mese.nome.equals(mese.nome)).toList();
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
        appContext.getBean(StatisticheGiorni.class).esegue();
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
        appContext.getBean(UploadGiorni.class, getNomeGiorno()).nascita().test().upload();
        reload();
    }


    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void testPaginaMorti() {
        appContext.getBean(UploadGiorni.class, getNomeGiorno()).morte().test().upload();
        reload();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public void uploadAll() {
        WResult result = WResult.errato();
        //        logger.info(new WrapLog().type(AETypeLog.upload).message("Inizio upload liste nati e morti dei giorni"));
        //        List<String> giorni;
        //        String message;
        //        int modificatiNati;
        //        int modificatiMorti;
        //
        //        List<Mese> mesi = meseBackend.findAllSortCorrente();
        //        for (Mese mese : mesi) {
        //            giorni = backend.findAllForNomeByMese(mese);
        //            modificatiNati = 0;
        //            modificatiMorti = 0;
        //            for (String nomeGiorno : giorni.subList(4,7)) {
        //                result = appContext.getBean(UploadGiorni.class).nascita().upload(nomeGiorno);
        //
        //                if (result.isValido() && result.isModificata()) {
        //                    modificatiNati++;
        //                }
        //
        //                result = appContext.getBean(UploadGiorni.class).morte().upload(nomeGiorno);
        //                if (result.isValido() && result.isModificata()) {
        //                    modificatiMorti++;
        //                }
        //            }
        //
        //            if (Pref.debug.is()) {
        //                message = String.format("Modificate sul server %d pagine di 'nati' e %d di 'morti' per il mese di %s", modificatiNati, modificatiMorti, mese);
        //                message += String.format(" in %s", dateService.deltaText(result.getInizio()));
        //                logger.info(new WrapLog().type(AETypeLog.upload).message(message));
        //            }
        //        }

        appContext.getBean(UploadGiorni.class).uploadAll();
        super.fixUpload(result.getInizio(), "dei giorni");
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void uploadPaginaNati() {
        WResult result = appContext.getBean(UploadGiorni.class,getNomeGiorno()).nascita().upload();
        reload();
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void uploadPaginaMorti() {
        WResult result = appContext.getBean(UploadGiorni.class,getNomeGiorno()).morte().upload();
        reload();
    }

    public String getNomeGiorno() {
        GiornoWiki giorno = (GiornoWiki) super.wikiPage();
        return giorno != null ? giorno.nome : VUOTA;
    }

}// end of crud @Route view class