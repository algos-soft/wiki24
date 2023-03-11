package it.algos.wiki24.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.utility.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 10-Mar-2023
 * Time: 18:26
 */
@Route(value = Wiki24Cost.WIKI_TAG_UTILITY, layout = MainLayout.class)
public class WikiUtilityView extends UtilityView {

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;


    @Override
    public void body() {
        super.body();
        this.paragrafoElaborazione();
        this.paragrafoUploadListe();
        this.paragrafoUploadStatistiche();
    }

    public void paragrafoElaborazione() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Elaborazione delle task");
        paragrafo.getElement().getStyle().set("color", "blue");
        List<String> listaClazz;

        message = String.format("Esegue il metodo %s() su tutte le collection che lo implementano.", METHOD_NAME_ELABORA);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(DROP));
        layout.add(ASpan.text(FLAG_DEBUG));

        listaClazz = Arrays.asList("Anno", "Giorno", "Attività", "Nazionalità", "Bio", "Cognome", "Pagina");
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
        //        bottone4.addClickListener(event -> elaboraAttivita());
        bottone4.setEnabled(false);

        Button bottone5 = new Button("Nazionalità");
        bottone5.getElement().setAttribute("theme", "primary");
        //        bottone5.addClickListener(event -> elaboraNazionalita());
        bottone5.setEnabled(false);

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5));
        this.add(layout);
    }

    public void elaboraAll() {
        super.inizioDebug();

        elaboraGiorni();
        elaboraAnni();

        super.fineDebug();
    }

    public void elaboraGiorni() {
        super.inizioDebug();
        logger.info(new WrapLog().message("Utility: elaborazione dei Giorni.").type(AETypeLog.utility));
        giornoWikiBackend.elabora();
        super.fineDebug();
    }

    public void elaboraAnni() {
        super.inizioDebug();
        logger.info(new WrapLog().message("Utility: elaborazione degli Anni.").type(AETypeLog.utility));
        annoWikiBackend.elabora();
        super.fineDebug();
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
        //        bottone4.addClickListener(event -> uploadAttivita());
        bottone4.setEnabled(false);

        Button bottone5 = new Button("Nazionalità");
        bottone5.getElement().setAttribute("theme", "primary");
        //        bottone5.addClickListener(event -> uploadNazionalita());
        bottone5.setEnabled(false);

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2, bottone3, bottone4, bottone5));
        this.add(layout);
    }


    public void uploadAllListe() {
        super.inizioDebug();

        uploadListaGiorni();
        uploadListaAnni();

        super.fineDebug();
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
