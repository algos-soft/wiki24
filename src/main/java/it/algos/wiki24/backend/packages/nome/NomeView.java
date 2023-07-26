package it.algos.wiki24.backend.packages.nome;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
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


    private IndeterminateCheckbox boxCategoria;

    private IndeterminateCheckbox boxDoppi;

    private IndeterminateCheckbox boxModulo;

    private IndeterminateCheckbox boxMongo;


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

        super.gridPropertyNamesList = Arrays.asList("nome", "numBio", "categoria", "doppio", "modulo", "mongo", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "numBio", "categoria", "doppio", "modulo", "mongo", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");

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
        String sogliaMongo = String.format("<span style=\"color:red\"><strong>%s</strong></span>", textService.format(WPref.sogliaMongoNomi.getInt()));
        String sogliaWiki = String.format("<span style=\"color:red\"><strong>%s</strong></span>", textService.format(WPref.sogliaWikiNomi.getInt()));

        Anchor anchor = WAnchor.build(CATEGORIA + ":Liste di persone per nome", CATEGORIA);
        Anchor anchor2 = WAnchor.build(statisticaNomi, TAG_NOMI);
        Anchor anchor3 = WAnchor.build(statisticaListe, TAG_LISTA_NOMI);
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Parametro <span style=\"color:red\"><em>nome</em></span> delle biografie per la creazione di liste <strong>Persone di nome...</strong>.";
        addSpan(ASpan.text(message).verde());
        message = String.format("Parametri%s", FORWARD);
        message += String.format("%s, %s, %s", WPref.usaTaskNomi.getKeyCode(), WPref.linkParagrafiNomi.getKeyCode(), WPref.typeTocNomi.getKeyCode());
        message += String.format("%s, %s, %s", WPref.sogliaMongoNomi.getKeyCode(), WPref.sogliaWikiNomi.getKeyCode(), WPref.usaSottoNomi.getKeyCode());
        message += String.format("%s, %s, %s, %s", WPref.usaNumVociNomi.getKeyCode(), WPref.elaboraNomi.getKeyCode(), WPref.uploadNomi.getKeyCode(), WPref.linkCrono.getKeyCode());
        addSpan(ASpan.text(message).blue().small());

        message = String.format("I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale.");
        message += String.format(" Le pagine non ancora esistenti con bio>%s sono da creare (blu).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Le pagine esistenti con bio<%s sono da cancellare (rosso bold).", sogliaWiki);
        message += String.format(" Le pagine esistenti con bio>%s sono in visione (verde).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sEsegue un Download di NomiCategoria. Aggiunge tutti i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download di NomiDoppi. Aggiunge tutti i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download di NomiIncipit. Aggiunge tutti i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sCostruisce una lista di nomi distinti dalle biografie di Mongo. Crea una entity solo se numBio>%s", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sEsegue un download. Calcola le voci biografiche che usano ogni singolo nome e la presenza della paginaLista", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di nomi con numBio>%s.", FORWARD, sogliaWiki);
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

        boxCategoria = new IndeterminateCheckbox();
        boxCategoria.setLabel("Categorie");
        boxCategoria.setIndeterminate(true);
        boxCategoria.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutCategoria = new HorizontalLayout(boxCategoria);
        layoutCategoria.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutCategoria);

        boxDoppi = new IndeterminateCheckbox();
        boxDoppi.setLabel("Doppi");
        boxDoppi.setIndeterminate(true);
        boxDoppi.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutDoppi = new HorizontalLayout(boxDoppi);
        layoutDoppi.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutDoppi);

        boxModulo = new IndeterminateCheckbox();
        boxModulo.setLabel("Modulo");
        boxModulo.setIndeterminate(true);
        boxModulo.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutIncipit = new HorizontalLayout(boxModulo);
        layoutIncipit.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutIncipit);

        boxMongo = new IndeterminateCheckbox();
        boxMongo.setLabel("Mongo");
        boxMongo.setIndeterminate(true);
        boxMongo.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layoutMongo = new HorizontalLayout(boxMongo);
        layoutMongo.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layoutMongo);

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
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List<AEntity> sincroFiltri() {
        List<Nome> items = (List) super.sincroFiltri();

        if (boxCategoria != null && !boxCategoria.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.categoria == boxCategoria.getValue()).toList();
        }
        if (boxDoppi != null && !boxDoppi.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.doppio == boxDoppi.getValue()).toList();
        }
        if (boxModulo != null && !boxModulo.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.modulo == boxModulo.getValue()).toList();
        }
        if (boxMongo != null && !boxMongo.isIndeterminate()) {
            items = items.stream().filter(nome -> nome.mongo == boxMongo.getValue()).toList();
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
                result = appContext.getBean(UploadNomi.class, nome.nome).upload();

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
        appContext.getBean(StatisticheListeNomi.class).esegue();
        appContext.getBean(StatisticheNomi.class).esegue();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    public void uploadAll() {
        long inizio = System.currentTimeMillis();
        WResult result = backend.uploadAll();
        super.fixUpload(inizio, "dei nomi");
        reload();
    }


}// end of crud @Route view class