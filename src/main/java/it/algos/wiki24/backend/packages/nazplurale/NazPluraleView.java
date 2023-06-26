package it.algos.wiki24.backend.packages.nazplurale;

import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.PATH_WIKI;
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

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Mar-2023
 * Time: 19:20
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("NazPlurale")
@Route(value = "nazplurale", layout = MainLayout.class)
public class NazPluraleView extends WikiView {


    //--per eventuali metodi specifici
    private NazPluraleBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NazPluraleView(@Autowired final NazPluraleBackend crudBackend) {
        super(crudBackend, NazPlurale.class);
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

        super.gridPropertyNamesList = Arrays.asList("nome", "listaSingolari", "numBio", "numSingolari", "superaSoglia", "esisteLista");
        super.formPropertyNamesList = Arrays.asList("nome", "listaSingolari", "numBio", "superaSoglia", "esisteLista");

        super.usaBottoneReset = false;
        super.usaReset = true;
        super.usaBottoneDeleteAll = false;
        super.usaBottoneElabora = true;
        super.usaBottoneDeleteEntity = false;
        super.usaBottoneStatistiche = false;
        super.usaBottoneUploadStatistiche = true;
        super.usaBottoneUploadAll = true;
        super.usaBottoneUploadPagina = true;
        super.usaBottoneTest = true;
        super.usaBottoneDownload = true;
        super.usaInfoDownload = true;
        super.usaBottoneUploadModuloAlfabetizzato = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        String modulo = PATH_WIKI + PATH_MODULO;
        String categoria = PATH_CATEGORIA + "Bio nazionalità";

        Anchor anchor1 = new Anchor(modulo + PATH_LINK + NAZ_LOWER, PATH_LINK + NAZ_LOWER);
        anchor1.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor2 = new Anchor(categoria, "Categoria");
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());

        Anchor anchor3 = new Anchor(PATH_WIKI + PATH_STATISTICHE_NAZIONALITA, STATISTICHE);
        anchor3.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor1, new Label(SEP), anchor2, new Label(SEP), anchor3));

        message = "Tabella nazionalità plurali del parametro 'nazionalità', ricavate dalla task NazSingolare.";
        addSpan(ASpan.text(message).verde());
        message = "Tabella dei link alla pagina della nazione recuperati dal modulo plurale -> nazione sul server wiki.";
        addSpan(ASpan.text(message).verde());
        message = "Indipendentemente da come sono scritte nel modulo, tutte le nazionalità plurali sono convertite in minuscolo.";
        addSpan(ASpan.text(message).rosso());

        message = String.format("ResetOnlyEmpty%sDownload.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sEsegue un Download di NazSingolare.", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sCrea una nuova tabella ricavandola dalle nazionalità DISTINCT di NazSingolare", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sAggiunge un link alla paginaLista di ogni nazionalità in base al nome della nazionalità plurale", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Download%sScarica 1 modulo wiki: %s", FORWARD, PATH_LINK + NAZ_LOWER);
        addSpan(ASpan.text(message).verde());
        message = String.format("Elabora%sCalcola le voci biografiche che usano ogni singola nazionalità plurale e la effettiva presenza della paginaLista", FORWARD);
        addSpan(ASpan.text(message).verde());
        message = String.format("Upload%sPrevisto per tutte le liste di nazionalità plurale con bio>50.", FORWARD);
        addSpan(ASpan.text(message).verde());

        message = String.format("Upload moduli%s1 modulo wiki riordinato in ordine alfabetico in %s", FORWARD, "Utente:Biobot/ModuloLinkNazionalita");
        addSpan(ASpan.text(message).blue().small());
    }


    /**
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addColumnsOneByOne() {
        super.addColumnsOneByOne();

        Grid.Column paginaLista = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = textService.primaMaiuscola(((NazPlurale) entity).paginaLista);
            Label label = new Label(wikiTitle);
            label.getElement().getStyle().set("color", "red");
            Anchor anchor = new Anchor(PATH_WIKI + PATH_NAZIONALITA + SLASH + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set("color", "green");
            anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
            Span span = new Span(anchor);
            if (((NazPlurale) entity).esisteLista) {
                return span;
            }
            else {
                return label;
            }
        })).setHeader("paginaLista").setKey("paginaLista").setFlexGrow(0).setWidth("18em");

        Grid.Column linkNazione = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = textService.primaMaiuscola(((NazPlurale) entity).linkNazione);
            Anchor anchor = new Anchor(PATH_WIKI + wikiTitle, wikiTitle);
            anchor.getElement().getStyle().set("color", "blue");
            return new Span(anchor);
        })).setHeader("linkNazione").setKey("linkNazione").setFlexGrow(0).setWidth("18em");

        Grid.Column nome = grid.getColumnByKey("nome");
        Grid.Column listaSingolari = grid.getColumnByKey("listaSingolari");
        Grid.Column numBio = grid.getColumnByKey("numBio");
        Grid.Column numSingolari = grid.getColumnByKey("numSingolari");
        Grid.Column superaSoglia = grid.getColumnByKey("superaSoglia");
        Grid.Column esisteLista = grid.getColumnByKey("esisteLista");

        grid.setColumnOrder(nome, listaSingolari, numBio, paginaLista, linkNazione, numSingolari, superaSoglia, esisteLista);
    }


    /**
     * Esegue un azione di upload delle statistiche, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     * Prima esegue una Elaborazione <br>
     */
    @Override
    public void uploadStatistiche() {
        WResult result = appContext.getBean(StatisticheNazionalita.class).upload();
        logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.upload).usaDb());
        super.uploadStatistiche();
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void uploadAll() {
        appContext.getBean(UploadNazionalita.class).uploadAll();
    }


    /**
     * Scrive una pagina definitiva sul server wiki <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void uploadPagina() {
        NazPlurale nazPlurale = getNazionalitaCorrente();

        if (nazPlurale != null) {
            backend.uploadPagina(nazPlurale.nome);
            reload();
        }
    }


    /**
     * Scrive una voce di prova su Utente:Biobot/test <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void testPagina() {
        NazPlurale nazionalita;
        String message;

        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            nazionalita = (NazPlurale) entityBean.get();
            if (nazionalita.numBio > WPref.sogliaAttNazWiki.getInt()) {
                appContext.getBean(UploadNazionalita.class).test().upload(nazionalita.nome);
            }
            else {
                message = String.format("La nazionalita %s non raggiunge il necessario numero di voci biografiche", nazionalita.nome);
                Avviso.message(message).primary().open();
            }
        }
    }

    public NazPlurale getNazionalitaCorrente() {
        NazPlurale nazionalita = null;

        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            nazionalita = (NazPlurale) entityBean.get();
        }

        return nazionalita;
    }

}// end of crud @Route view class