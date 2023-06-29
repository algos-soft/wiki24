package it.algos.wiki24.backend.packages.nome;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

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

    private IndeterminateCheckbox boxTemplate;

    private IndeterminateCheckbox boxDoppi;

    private IndeterminateCheckbox boxSuperaSoglia;

    private IndeterminateCheckbox boxEsisteLista;

    @Autowired
    private UtilityService utilityService;

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

        super.gridPropertyNamesList = Arrays.asList("nome", "numBio", "distinto", "doppio", "template", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "numBio", "distinto", "doppio", "template", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDeleteAll = false;
        super.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneUploadAll = true;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneUploadStatistiche = true;
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

        String infoTask = VaadTask.info(TaskNomi.class);
        String statisticaNomi = TAG_ANTROPONIMI + TAG_NOMI;
        String statisticaListe = TAG_ANTROPONIMI + TAG_LISTA_NOMI;
        int sogliaMongo = WPref.sogliaMongoNomi.getInt();
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();

        WAnchor anchor = WAnchor.build(CATEGORIA + ":Prenomi composti", CATEGORIA);
        WAnchor anchor2 = WAnchor.build(statisticaNomi, TAG_NOMI);
        WAnchor anchor3 = WAnchor.build(statisticaListe, TAG_LISTA_NOMI);
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Tabella del parametro 'nome', ricavata dalle biografie, da NomeDoppio e NomeTemplate.";
        addSpan(ASpan.text(message).verde());
        message = String.format("Parametri%s", FORWARD);
        message += String.format("%s, %s, %s", WPref.usaTaskNomi.getKeyCode(), WPref.linkParagrafiNomi.getKeyCode(), WPref.typeTocNomi.getKeyCode());
        message += String.format("%s, %s, %s", WPref.sogliaMongoNomi.getKeyCode(), WPref.sogliaWikiNomi.getKeyCode(), WPref.usaSottoNomi.getKeyCode());
        message += String.format("%s, %s, %s, %s", WPref.usaNumVociNomi.getKeyCode(), WPref.elaboraNomi.getKeyCode(), WPref.uploadNomi.getKeyCode(), WPref.linkCrono.getKeyCode());
        addSpan(ASpan.text(message).blue().small());

        message = String.format("I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale.");
        message += String.format(" Le pagine non ancora esistenti con bio>%d sono da creare (blu).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Le pagine esistenti con bio<%d sono da cancellare (rosso bold).", sogliaWiki);
        message += String.format(" Le pagine esistenti con bio>%d sono in visione (verde).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sRecupera una lista di nomi distinti dalle biografie. Crea una entity se numBio>%d", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download di NomiDoppi. Aggiunge tutti i valori alla lista; anche se numBio<%d", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download del NomiTemplate. Aggiunge tutti i valori alla lista; anche se numBio<%d", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sEsegue un download. Calcola le voci biografiche che usano ogni singolo nome e la presenza della paginaLista", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di nomi con numBio>%d.", FORWARD, sogliaWiki);
        addSpan(ASpan.text(message).verde());

        message = String.format("Upload liste%sEseguito da %s", FORWARD, infoTask);
        addSpan(ASpan.text(message).blue().small());
        message = "A) Visualizzazione della lista di paragrafi in testa pagina: forceToc oppure noToc -> default WPref.typeTocNomi.";
        addSpan(ASpan.text(message).rosso().small());
        message = "B) Uso dei paragrafi: sempre.";
        addSpan(ASpan.text(message).rosso().small());
        message = "C) Titolo del singolo paragrafo: link+numeri, titolo+numeri, titolo senza numeri -> default WPref.linkNomi.";
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Upload statistiche%s2 pagine wiki su %s e %s", FORWARD, statisticaNomi, statisticaListe);
        addSpan(ASpan.text(message).blue().small());
        message = "L'elaborazione delle statistiche è gestita dalla task Statistiche.";
        addSpan(ASpan.text(message).rosso().small());
    }


    protected void fixBottoniTopSpecifici() {
        boxDistinti = new IndeterminateCheckbox();
        boxDistinti.setLabel("Distinti");
        boxDistinti.setIndeterminate(true);
        boxDistinti.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutDistinti = new HorizontalLayout(boxDistinti);
        layoutDistinti.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutDistinti);

        boxDoppi = new IndeterminateCheckbox();
        boxDoppi.setLabel("Doppi");
        boxDoppi.setIndeterminate(true);
        boxDoppi.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutDoppi = new HorizontalLayout(boxDoppi);
        layoutDoppi.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutDoppi);

        boxTemplate = new IndeterminateCheckbox();
        boxTemplate.setLabel("Template");
        boxTemplate.setIndeterminate(true);
        boxTemplate.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutIncipit = new HorizontalLayout(boxTemplate);
        layoutIncipit.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutIncipit);

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
        //        int sogliaWiki = WPref.sogliaWikiNomi.getInt();
        //
        //        grid.addComponentColumn(entity -> {
        //            String wikiTitle = textService.primaMaiuscola(((Nome) entity).paginaLista);
        //            Button button = new Button(wikiTitle, click -> {wikiApiService.openWikiPage(wikiTitle);});
        //            if (((Nome) entity).numBio < sogliaWiki) {
        //                button.getStyle().set("color", "red");
        //            }
        //            else {
        //                if (((Nome) entity).isEsisteLista()) {
        //                    button.getStyle().set("color", "green");
        //                }
        //                else {
        //                    button.getStyle().set("color", "blue");
        //                }
        //            }
        //            button.getStyle().set("margin-top", "0");
        //            button.getStyle().set("margin-bottom", "0");
        //            button.getStyle().set("background-color", "transparent");
        //
        //            return button;
        //        }).setHeader("PaginaLista").setKey("paginaLista").setFlexGrow(0).setWidth("18em");
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<Nome> items = (List) super.sincroFiltri();

        if (boxDistinti != null && !boxDistinti.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.distinto == boxDistinti.getValue()).toList();
        }
        if (boxTemplate != null && !boxTemplate.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.template == boxTemplate.getValue()).toList();
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

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected AEntity wikiPage() {
        Nome nome = (Nome) super.wikiPage();

        if (nome != null) {
            wikiApiService.openWikiPage(wikiUtility.wikiTitleNomi(nome.nome));
        }

        return null;
    }

    /**
     * Scrive una voce di prova generica su Utente:Biobot/... <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    public void testPagina() {
        Nome nome = (Nome) super.getBeanSelected();

        if (nome != null) {
            appContext.getBean(UploadNomi.class, nome.nome).test().esegue();
            message = String.format("Scritta una voce di test su %s", UPLOAD_TITLE_DEBUG + nome.nome);
            Avviso.message(message).primary().durata(3).open();
        }
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    public void uploadPagina() {
        WResult result = WResult.crea();
        Nome nome = (Nome) super.getBeanSelected();

        if (nome != null) {
            if (nome.numBio > WPref.sogliaWikiNomi.getInt()) {
                result = appContext.getBean(UploadNomi.class, nome.nome).esegue();
            }
            else {
                message = String.format("Il nome '%s' non raggiunge il numero di voci biografiche. Necessario=%d", nome.nome, WPref.sogliaWikiNomi.getInt());
                Avviso.message(message).primary().open();
            }
        }

        if (result.isValido()) {
            if (result.isModificata()) {
                message = String.format("Upload della singola pagina%s [%s%s]", FORWARD, PATH_NOMI, nome.nome);
                Avviso.message(message).success().open();
                logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
            }
            else {
                message = String.format("La pagina: [%s%s] esisteva già e non è stata modificata", PATH_NOMI, nome.nome);
                Avviso.message(message).primary().open();
                logService.info(new WrapLog().message(message).type(AETypeLog.upload));
            }
        }
        else {
            message = String.format("Non sono riuscito a caricare su wiki la pagina: [%s%s]", PATH_NOMI, nome.nome);
            Avviso.message(message).error().open();
            logService.error(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload).usaDb());
        }

    }

    /**
     * Esegue un azione di upload delle statistiche, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     * Prima esegue una Elaborazione <br>
     */
    @Override
    public void uploadStatistiche() {
        WResult result = appContext.getBean(StatisticheNomi.class).esegue();
        logger.info(new WrapLog().message(result.getMessage()).type(AETypeLog.upload).usaDb());
        super.uploadStatistiche();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    public void uploadAll() {
        appContext.getBean(UploadNomi.class).uploadAll();
        reload();
    }


}// end of crud @Route view class