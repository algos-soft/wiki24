package it.algos.wiki24.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.utility.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.upload.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 10-Mar-2023
 * Time: 18:26
 */
//@Route(value = Wiki24Cost.WIKI_TAG_UTILITY, layout = MainLayout.class)
@Route(value = TAG_ROUTE_ALIAS_PARTE_PER_PRIMA, layout = MainLayout.class)
public class WikiUtilityView extends UtilityView {

    @Autowired
    public ViaBackend viaBackend;

    @Autowired
    public ContinenteBackend continenteBackend;

    @Autowired
    public GiornoBackend giornoBackend;

    @Autowired
    public AnnoBackend annoBackend;

    @Autowired
    public MeseBackend meseBackend;

    @Autowired
    public SecoloBackend secoloBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Autowired
    public NazSingolareBackend nazSingolareBackend;

    @Autowired
    public NazPluraleBackend nazPluraleBackend;

    @Autowired
    public ElaboraService elaboraService;

    @Autowired
    public BioService bioService;

    @Autowired
    public BioBackend bioBackend;

    @Autowired
    public MongoService mongoService;

    @Override
    protected void postConstruct() {
        super.postConstruct();
    }

    @Override
    public void body() {
        super.body();
        this.paragrafoUploadModuli();
        this.paragrafoDownloadModuli();
        this.paragrafoDownloadBiografie();
        this.paragrafoElaborazione();
        this.paragrafoElaborazioneBiografie();
        this.paragrafoUploadListe();
        this.paragrafoUploadStatistiche();

//        regolazioniFinali();
    }

    public void regolazioniFinali() {
        if (mongoService.isCollectionNullOrEmpty(GiornoWiki.class)) {
            resetGiorno();
        }
        if (mongoService.isCollectionNullOrEmpty(AnnoWiki.class)) {
            resetAnno();
        }
        if (mongoService.isCollectionNullOrEmpty(AttSingolare.class)) {
            downloadAttivitaSingolare();
        }
        if (mongoService.isCollectionNullOrEmpty(AttPlurale.class)) {
            downloadAttivitaPlurale();
        }
        if (mongoService.isCollectionNullOrEmpty(NazSingolare.class)) {
            downloadNazionalitaSingolare();
        }
        if (mongoService.isCollectionNullOrEmpty(NazPlurale.class)) {
            downloadNazionalitaPlurale();
        }
    }

    public void paragrafoReset() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        List<String> lista;
        H3 paragrafo = new H3("Reset di tutte le collection");
        paragrafo.getElement().getStyle().set("color", "blue");

        message = String.format("Esegue il %s() su tutte le collection che implementano %s(). ", METHOD_NAME_RESET_FORCING, METHOD_NAME_RESET_ONLY);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(DROP));
        layout.add(ASpan.text(FLAG_DEBUG));
        //        lista = classService.allModuleEntityResetOrderedName(VaadVar.moduloVaadin24);
        lista = Arrays.asList("Via", "Giorno", "Anno", "Mese", " Secolo", "Continente");
        message = String.format("Modulo %s%s%s", VaadVar.moduloVaadin24, DUE_PUNTI_SPAZIO, lista.toString());
        layout.add(ASpan.text(message));
        lista = Arrays.asList("AnnoWiki", "GiornoWiki");
        message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, lista.toString());
        layout.add(ASpan.text(message));

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> resetAll());
        Button bottone2 = new Button("Vaad24");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> resetVaad());
        Button bottone3 = new Button("AnnoWiki");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> resetAnno());
        Button bottone4 = new Button("GiornoWiki");
        bottone4.getElement().setAttribute("theme", "primary");
        bottone4.addClickListener(event -> resetGiorno());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4));
        this.add(layout);
    }

    public void resetAll() {
        resetVaad();
        resetGiorno();
        resetAnno();
    }

    public void resetVaad() {
        viaBackend.resetForcing();
        continenteBackend.resetForcing();
        giornoBackend.resetForcing();
        annoBackend.resetForcing();
        meseBackend.resetForcing();
        secoloBackend.resetForcing();
    }

    public void resetGiorno() {
        giornoWikiBackend.resetForcing();
    }

    public void resetAnno() {
        annoWikiBackend.resetForcing();
    }


    public void paragrafoUploadModuli() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Test di upload dei moduli");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Legge il contenuto dei moduli, li riordina alfabeticamente (key o value) e li salva come TEST. ", METHOD_NAME_UPLOAD);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("ModuloPluraleAttività", "ModuloExAttività", "ModuloLinkAttività", "ModuloPluraleNazionalità", "ModuloLinkNazionalità");
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, listaClazz);
            layout.add(ASpan.text(message));
        }

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> uploadModuli());

        Button bottone2 = new Button("PluraleAttività + ExAttività");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> uploadModuliAttSingolare());

        Button bottone3 = new Button("LinkAttività");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> uploadModuliAttPlurale());

        Button bottone4 = new Button("PluraleNazionalità");
        bottone4.getElement().setAttribute("theme", "primary");
        bottone4.addClickListener(event -> uploadModuliNazSingolare());

        Button bottone5 = new Button("LinkNazionalità");
        bottone5.getElement().setAttribute("theme", "primary");
        bottone5.addClickListener(event -> uploadModuliNazPlurale());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5));
        this.add(layout);
    }

    public void uploadModuli() {
        uploadModuliAttSingolare();
        uploadModuliAttPlurale();
        uploadModuliNazSingolare();
        uploadModuliNazPlurale();
    }

    public void uploadModuliAttSingolare() {
        attSingolareBackend.riordinaModulo();
    }

    public void uploadModuliAttPlurale() {
        attPluraleBackend.riordinaModulo();
    }

    public void uploadModuliNazSingolare() {
        nazSingolareBackend.riordinaModulo();
    }

    public void uploadModuliNazPlurale() {
        nazPluraleBackend.riordinaModulo();
    }

    public void paragrafoDownloadModuli() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Download dei moduli");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Esegue il metodo %s() su tutte le collection che lo implementano.", METHOD_NAME_DOWLOAD);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(DROP));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("AttSingolare", "AttPlurale", "NazSingolare", "NazPlurale");
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("%s", listaClazz);
            layout.add(ASpan.text(message));
        }

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> downloadAll());

        Button bottone2 = new Button("AttSingolare");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> downloadAttivitaSingolare());
        Button bottone3 = new Button("AttPlurale");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> downloadAttivitaPlurale());

        Button bottone4 = new Button("NazSingolare");
        bottone4.getElement().setAttribute("theme", "primary");
        bottone4.addClickListener(event -> downloadNazionalitaSingolare());

        Button bottone5 = new Button("NazPlurale");
        bottone5.getElement().setAttribute("theme", "primary");
        bottone5.addClickListener(event -> downloadNazionalitaPlurale());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5));
        this.add(layout);
    }

    public void downloadAll() {
        downloadAttivitaSingolare();
        downloadAttivitaPlurale();
        downloadNazionalitaSingolare();
        downloadNazionalitaPlurale();
    }


    public void downloadAttivitaSingolare() {
        super.inizioDebug();
        AResult result;
        String message;
        String task = AttSingolare.class.getSimpleName();

        logger.info(new WrapLog().message("Utility: download delle attività singolari.").type(AETypeLog.utility));
        result = attSingolareBackend.resetDownload();

        if (result.isValido()) {
            message = String.format("Download di %s effettuato", task);
            Avviso.message(message).success().durata(4).open();
        }
        else {
            message = String.format("Download di %s non riuscito", task);
            Avviso.message(message).error().durata(4).open();
        }

        super.fineDebug();
    }

    public void downloadAttivitaPlurale() {
        super.inizioDebug();
        AResult result;
        String message;
        String task = AttPlurale.class.getSimpleName();

        logger.info(new WrapLog().message("Utility: download delle attività plurali.").type(AETypeLog.utility));
        result = attPluraleBackend.resetDownload();

        if (result.isValido()) {
            message = String.format("Download di %s effettuato", task);
            Avviso.message(message).success().durata(4).open();
        }
        else {
            message = String.format("Download di %s non riuscito", task);
            Avviso.message(message).error().durata(4).open();
        }

        super.fineDebug();
    }

    public void downloadNazionalitaSingolare() {
        super.inizioDebug();
        AResult result;
        String message;
        String task = NazSingolare.class.getSimpleName();

        logger.info(new WrapLog().message("Utility: download delle nazionalità singolari.").type(AETypeLog.utility));
        result = nazSingolareBackend.resetDownload();

        if (result.isValido()) {
            message = String.format("Download di %s effettuato", task);
            Avviso.message(message).success().durata(4).open();
        }
        else {
            message = String.format("Download di %s non riuscito", task);
            Avviso.message(message).error().durata(4).open();
        }
        super.fineDebug();
    }

    public void downloadNazionalitaPlurale() {
        super.inizioDebug();
        AResult result;
        String message;
        String task = NazPlurale.class.getSimpleName();

        logger.info(new WrapLog().message("Utility: download delle nazionalità plurali.").type(AETypeLog.utility));
        result = nazPluraleBackend.resetDownload();

        if (result.isValido()) {
            message = String.format("Download di %s effettuato", task);
            Avviso.message(message).success().durata(4).open();
        }
        else {
            message = String.format("Download di %s non riuscito", task);
            Avviso.message(message).error().durata(4).open();
        }
        super.fineDebug();
    }

    public void paragrafoDownloadBiografie() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Download delle biografie");
        paragrafo.getElement().getStyle().set("color", "blue");

        Button bottone = new Button("Download");
        bottone.getElement().setAttribute("theme", "error");
        bottone.addClickListener(event -> downloadBio());

        message = "Download di tutte le biografie. Da eseguire PRIMA delle elaborazioni. Necessarie circa 8-10 ore";
        this.add(paragrafo);
        layout.add(new HorizontalLayout(ASpan.text(message).rosso(), bottone));
        this.add(layout);
    }

    public void downloadBio() {
        String message = "Meglio andare nel package Biografie";
        Avviso.message(message).error().open();
    }

    public void paragrafoElaborazione() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Elaborazioni");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Esegue il metodo %s() su tutte le collection che lo implementano.", METHOD_NAME_ELABORA);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(DROP));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("Giorni", "Anni", "Attività", "Nazionalità", "Bio", "Cognome", "Pagina");
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, listaClazz);
            layout.add(ASpan.text(message));
        }

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> elaboraAll());

        Button bottone2 = new Button("Giorni");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> elaboraGiorni());

        Button bottone3 = new Button("Anni");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> elaboraAnni());

        Button bottone4 = new Button("Attività");
        bottone4.getElement().setAttribute("theme", "primary");
        bottone4.addClickListener(event -> elaboraAttivita());
        bottone4.setEnabled(false);

        Button bottone5 = new Button("NazSingolare");
        bottone5.getElement().setAttribute("theme", "primary");
        bottone5.addClickListener(event -> elaboraNazSingolare());

        Button bottone6 = new Button("NazPlurale");
        bottone6.getElement().setAttribute("theme", "primary");
        bottone6.addClickListener(event -> elaboraNazPlurale());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5, bottone6));
        this.add(layout);
    }

    public void elaboraAll() {
        elaboraGiorni();
        elaboraAnni();
    }

    public void elaboraGiorni() {
        super.inizioDebug();
        WResult result;

        result = giornoWikiBackend.elabora();
        if (result.isValido()) {
            logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.utility).usaDb());
            Avviso.message("Elaborazione effettuata").success().open();
        }
        else {
            Avviso.message("Elaborazione non effettuata").error().open();
        }

        super.fineDebug();
    }

    public void elaboraAnni() {
        super.inizioDebug();
        WResult result;

        logger.info(new WrapLog().message("Utility: elaborazione degli Anni.").type(AETypeLog.utility));
        result = annoWikiBackend.elabora();
        if (result.isValido()) {
            Avviso.message("Elaborazione effettuata").success().open();
        }
        else {
            Avviso.message("Elaborazione non effettuata").error().open();
        }

        super.fineDebug();
    }

    public void elaboraAttivita() {
        super.inizioDebug();
        WResult result;

        logger.info(new WrapLog().message("Utility: elaborazione delle Attività.").type(AETypeLog.utility));
        //        result = attivitaBackend.elabora();
        //        if (result.isValido()) {
        //            Avviso.message("Elaborazione effettuata").success().open();
        //        }
        //        else {
        //            Avviso.message("Elaborazione non effettuata").error().open();
        //        }

        super.fineDebug();
    }

    public void elaboraNazSingolare() {
        super.inizioDebug();
        WResult result;

        logger.info(new WrapLog().message("Utility: elaborazione delle nazionalità singolari.").type(AETypeLog.utility));
        result = nazSingolareBackend.elabora();
        if (result.isValido()) {
            Avviso.message("Elaborazione effettuata").success().open();
        }
        else {
            Avviso.message("Elaborazione non effettuata").error().open();
        }

        super.fineDebug();
    }


    public void elaboraNazPlurale() {
        super.inizioDebug();
        WResult result;

        logger.info(new WrapLog().message("Utility: elaborazione delle nazionalità plurali.").type(AETypeLog.utility));
        result = nazPluraleBackend.elabora();
        if (result.isValido()) {
            Avviso.message("Elaborazione effettuata").success().open();
        }
        else {
            Avviso.message("Elaborazione non effettuata").error().open();
        }

        super.fineDebug();
    }

    public void paragrafoElaborazioneBiografie() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Elaborazione di tutte le biografie");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        Button bottone = new Button("Elabora");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> elaboraBio());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone));
        this.add(layout);
    }

    public void elaboraBio() {
        int elaborate = 0;
        logger.info(new WrapLog().message("Utility: elaborazione delle biografie.").type(AETypeLog.utility));

        for (Bio bio : bioBackend.findAllAll()) {
            elaboraService.esegueSave(bio);
            elaborate = elaborate + 1;
        }
    }

    public void paragrafoUploadListe() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Test di upload delle liste");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Costruisce (come TEST) l'istanza di %s per tutte le collection interessate.", METHOD_NAME_UPLOAD);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("UploadGiorni", "UploadAnni", "UploadAttività", "UploadNazionalità", "UploadCognomi", "UploadNomi");
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, listaClazz);
            layout.add(ASpan.text(message));
        }

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> uploadAllListe());

        Button bottone2 = new Button("Giorni");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> uploadListaGiorni());

        Button bottone3 = new Button("Anni");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> uploadListaAnni());

        Button bottone4 = new Button("Attività");
        bottone4.getElement().setAttribute("theme", "primary");
        bottone4.addClickListener(event -> uploadListaAttivita());

        Button bottone5 = new Button("Nazionalità");
        bottone5.getElement().setAttribute("theme", "primary");
        //        bottone5.addClickListener(event -> uploadNazionalita());
        bottone5.setEnabled(false);

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5));
        this.add(layout);
    }


    public void uploadAllListe() {
        uploadListaGiorni();
        uploadListaAnni();
    }

    public void uploadListaGiorni() {
        String sorgente;
        super.inizioDebug();
        logger.info(new WrapLog().message("Utility: test di upload dei Giorni.").type(AETypeLog.utility));

        sorgente = "23 aprile";
        appContext.getBean(UploadGiorni.class).test().typeCrono(AETypeLista.giornoNascita).upload(sorgente);

        sorgente = "8 dicembre";
        appContext.getBean(UploadGiorni.class).test().typeCrono(AETypeLista.giornoMorte).upload(sorgente);

        super.fineDebug();
    }

    public void uploadListaAnni() {
        String sorgente;
        super.inizioDebug();
        logger.info(new WrapLog().message("Utility: test di upload degli Anni.").type(AETypeLog.utility));

        sorgente = "1875";
        appContext.getBean(UploadAnni.class).test().typeCrono(AETypeLista.annoNascita).upload(sorgente);

        sorgente = "2018";
        appContext.getBean(UploadAnni.class).test().typeCrono(AETypeLista.annoMorte).upload(sorgente);

        super.fineDebug();
    }

    public void uploadListaAttivita() {
        String sorgente;
        super.inizioDebug();
        logger.info(new WrapLog().message("Utility: test di upload delle Attività.").type(AETypeLog.utility));

        sorgente = "Abati e badesse";
        appContext.getBean(UploadAttivita.class).test().upload(sorgente);

        super.fineDebug();
    }

    public void paragrafoUploadStatistiche() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Test di upload delle statistiche");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Costruisce (come TEST) l'istanza di statistica per tutte le collection interessate.");
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("Giorni", "Anni", "Attività", "Nazionalità", "Bio", "Cognomi", "Nomi");
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, listaClazz);
            layout.add(ASpan.text(message));
        }

        Button bottone = new Button("All");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> uploadStatisticheAll());

        Button bottone2 = new Button("Giorni");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> uploadStatisticheGiorni());

        Button bottone3 = new Button("Anni");
        bottone3.getElement().setAttribute("theme", "primary");
        bottone3.addClickListener(event -> uploadStatisticheAnni());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3));
        this.add(layout);
    }


    public void uploadStatisticheAll() {
        super.inizioDebug();

        uploadStatisticheGiorni();
        uploadStatisticheAnni();

        super.fineDebug();
    }

    public void uploadStatisticheGiorni() {
        super.inizioDebug();

        logger.info(new WrapLog().message("Utility: test di upload per le statistiche dei Giorni.").type(AETypeLog.utility));
        appContext.getBean(StatisticheGiorni.class).uploadTest();

        super.fineDebug();
    }

    public void uploadStatisticheAnni() {
        super.inizioDebug();

        logger.info(new WrapLog().message("Utility: test di upload per le statistiche degli Anni.").type(AETypeLog.utility));
        appContext.getBean(StatisticheAnni.class).uploadTest();

        super.fineDebug();
    }

}
