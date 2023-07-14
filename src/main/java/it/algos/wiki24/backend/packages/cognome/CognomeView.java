package it.algos.wiki24.backend.packages.cognome;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.components.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.schedule.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.PATH_WIKI;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.upload.liste.*;
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

    private IndeterminateCheckbox boxCategoria;

    private IndeterminateCheckbox boxModulo;

    private IndeterminateCheckbox boxMongo;

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

        super.gridPropertyNamesList = Arrays.asList("cognome", "numBio", "categoria",  "modulo", "mongo", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("cognome", "numBio", "categoria",  "modulo", "mongo", "superaSoglia", "paginaVoce", "paginaLista", "esisteLista");
        super.sortOrder = super.sortOrder != null ? super.sortOrder : Sort.by(Sort.Direction.ASC, "cognome");

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

        String infoTask = VaadTask.info(TaskCognomi.class);
        String statisticaNomi = TAG_ANTROPONIMI + TAG_COGNOMI;
        String statisticaListe = TAG_ANTROPONIMI + TAG_LISTA_COGNOMI;
        String sogliaMongo = String.format("<span style=\"color:red\"><strong>%s</strong></span>", textService.format(WPref.sogliaMongoCognomi.getInt()));
        String sogliaWiki = String.format("<span style=\"color:red\"><strong>%s</strong></span>", textService.format(WPref.sogliaWikiCognomi.getInt()));

        Anchor anchor = WAnchor.build(CATEGORIA + ":Liste di persone per cognome", CATEGORIA);
        Anchor anchor2 = WAnchor.build(statisticaNomi, TAG_COGNOMI);
        Anchor anchor3 = WAnchor.build(statisticaListe, TAG_LISTA_COGNOMI);
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2, new Label(SEP), anchor3));


        message = "Parametro <span style=\"color:red\"><em>cognome</em></span> delle biografie per la creazione di liste <strong>Persone di cognome...</strong>.";
        addSpan(ASpan.text(message).verde());
        message = String.format("Parametri%s", FORWARD);
        message += String.format("%s, %s, %s", WPref.usaTaskCognomi.getKeyCode(), WPref.linkParagrafiNomi.getKeyCode(), WPref.typeTocCognomi.getKeyCode());
        message += String.format("%s, %s, %s", WPref.sogliaMongoCognomi.getKeyCode(), WPref.sogliaWikiCognomi.getKeyCode(), WPref.usaSottoCognomi.getKeyCode());
        message += String.format("%s, %s, %s, %s", WPref.usaNumVociCognomi.getKeyCode(), WPref.elaboraCognomi.getKeyCode(), WPref.uploadCognomi.getKeyCode(), WPref.linkCrono.getKeyCode());
        addSpan(ASpan.text(message).blue().small());


        message = String.format("I cognomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale.");
        message += String.format(" Le pagine non ancora esistenti con bio>%s sono da creare (blu).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());
        message = String.format("Le pagine esistenti con bio<%s sono da cancellare (rosso bold).", sogliaWiki);
        message += String.format(" Le pagine esistenti con bio>%s sono in visione (verde).", sogliaWiki);
        addSpan(ASpan.text(message).rosso().small());

        message = String.format("Download%sEsegue un Download di CognomiCategoria. Aggiunge tutti i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download di CognomiIncipit. Aggiunge tutti i valori alla lista.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sCostruisce una lista di cognomi distinti dalle biografie di Mongo. Crea una entity solo se numBio>%s", FORWARD, sogliaMongo);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sEsegue un download. Calcola le voci biografiche che usano ogni singolo cognome e la presenza della paginaLista", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di cognomi con numBio>%s.", FORWARD, sogliaWiki);
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
        List<Cognome> items = (List) super.sincroFiltri();

        if (boxCategoria != null && !boxCategoria.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.categoria == boxCategoria.getValue()).toList();
        }
        if (boxModulo != null && !boxModulo.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.modulo == boxModulo.getValue()).toList();
        }
        if (boxMongo != null && !boxMongo.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.mongo == boxMongo.getValue()).toList();
        }
        if (boxSuperaSoglia != null && !boxSuperaSoglia.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.superaSoglia == boxSuperaSoglia.getValue()).toList();
        }
        if (boxEsisteLista != null && !boxEsisteLista.isIndeterminate()) {
            items = items.stream().filter(cognome -> cognome.esisteLista == boxEsisteLista.getValue()).toList();
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
    @Override
    public void uploadAll() {
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