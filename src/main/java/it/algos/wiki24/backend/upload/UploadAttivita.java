package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attivita.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 08-Jun-2022
 * Time: 06:55
 * Classe specializzata per caricare (upload) le liste di attività sul server wiki. <br>
 * Usata fondamentalmente da AttivitaWikiView con appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
 * <p>
 * Necessita del login come bot <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadAttivita extends UploadAttivitaNazionalita {

    public static final String UPLOAD_TITLE_PROJECT_ATTIVITA = UPLOAD_TITLE_PROJECT + "Attività/";

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AttPluraleBackend attPluraleBackend;



    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadAttivita() {
        super.summary = "[[Utente:Biobot/attivitàBio|attivitàBio]]";
        super.titoloLinkParagrafo = TITOLO_LINK_PARAGRAFO_NAZIONALITA;
        super.titoloLinkVediAnche = TITOLO_LINK_PARAGRAFO_ATTIVITA;
        super.typeCrono = AETypeLista.attivitaPlurale;
        super.lastUpload = WPref.uploadAttPlurale;
        super.durataUpload = WPref.uploadAttPluraleTime;
        super.nextUpload = WPref.uploadAttPluralePrevisto;
        this.typeCrono = AETypeLista.attivitaPlurale;
    }// end of constructor


    public UploadAttivita singolare() {
        this.typeCrono = AETypeLista.attivitaSingolare;
        return this;
    }

    public UploadAttivita plurale() {
        this.typeCrono = AETypeLista.attivitaPlurale;
        return this;
    }

    protected String incipit() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Questa");
        buffer.append(textService.setRef(INFO_PAGINA_ATTIVITA));
        buffer.append(" è una lista");
        buffer.append(textService.setRef(INFO_DIDASCALIE));
        buffer.append(textService.setRef(INFO_ORDINE));
        buffer.append(" di persone");
        buffer.append(textService.setRef(INFO_PERSONA_ATTIVITA));
        buffer.append(" presenti");
        buffer.append(textService.setRef(INFO_LISTA));
        buffer.append(" nell'enciclopedia che hanno come attività");
        buffer.append(textService.setRef(INFO_ATTIVITA_PREVISTE));
        buffer.append(String.format(" quella di '''%s'''.", nomeLista));
        buffer.append(" Le persone sono suddivise");
        buffer.append(textService.setRef(INFO_PARAGRAFI_NAZIONALITA));
        buffer.append(" per nazionalità.");
        buffer.append(textService.setRef(INFO_NAZIONALITA_PREVISTE));
        if (mappaWrap.containsKey(TAG_LISTA_NO_NAZIONALITA)) {
            buffer.append(textService.setRef(INFO_ALTRE_NAZIONALITA));
        }

        return buffer.toString();
    }


    protected String incipitSottoPagina(String attivita, String nazionalita, int numVoci) {
        StringBuffer buffer = new StringBuffer();
        String att = textService.primaMaiuscola(attivita);
        String naz = textService.primaMaiuscola(nazionalita);
        String message;

        buffer.append("Questa");
        message = String.format(INFO_SOTTOPAGINA_DI_ATTIVITA, att, naz, numVoci, naz, att);
        buffer.append(textService.setRef(message));
        buffer.append(" è una lista");
        buffer.append(textService.setRef(INFO_DIDASCALIE));
        buffer.append(textService.setRef(INFO_ORDINE));
        buffer.append(" di persone");
        buffer.append(textService.setRef(INFO_PERSONA_ATTIVITA));
        buffer.append(" presenti");
        buffer.append(textService.setRef(INFO_LISTA));
        buffer.append(" nell'enciclopedia che hanno come attività");
        buffer.append(textService.setRef(INFO_ATTIVITA_PREVISTE));
        buffer.append(String.format(" quella di '''%s'''", attivita.toLowerCase()));
        if (nazionalita.equals(TAG_LISTA_ALTRE)) {
            buffer.append(" e non usano il parametro ''nazionalità'' oppure hanno un'nazionalità di difficile elaborazione da parte del '''[[Utente:Biobot|<span style=\"color:green;\">bot</span>]]'''");
        }
        else {
            buffer.append(String.format(" e sono '''%s'''.", nazionalita.toLowerCase()));
        }
        buffer.append(textService.setRef(INFO_NAZIONALITA_PREVISTE));

        return buffer.toString();
    }

    protected String incipitSottoSottoPagina(String attivita, String nazionalita, String keyParagrafo, int numVoci) {
        StringBuffer buffer = new StringBuffer();
        String att = textService.primaMaiuscola(attivita) + SLASH + textService.primaMaiuscola(nazionalita); ;
        String naz = textService.primaMaiuscola(keyParagrafo);
        String message;

        buffer.append("Questa");
        message = String.format(INFO_SOTTOPAGINA_DI_ATTIVITA, att, naz, numVoci, naz, att);
        buffer.append(textService.setRef(message));
        buffer.append(" è una lista");
        buffer.append(textService.setRef(INFO_DIDASCALIE));
        buffer.append(" di persone");
        buffer.append(textService.setRef(INFO_PERSONA_ATTIVITA));
        buffer.append(" presenti");
        buffer.append(textService.setRef(INFO_LISTA));
        buffer.append(" nell'enciclopedia che hanno come attività");
        buffer.append(textService.setRef(INFO_ATTIVITA_PREVISTE));
        buffer.append(String.format(" quella di '''%s'''", attivita.toLowerCase()));
        if (naz.equals(TAG_LISTA_ALTRE)) {
            buffer.append(" e non usano il parametro ''nazionalità'' oppure hanno un'nazionalità di difficile elaborazione da parte del '''[[Utente:Biobot|<span style=\"color:green;\">bot</span>]]");
        }
        else {
            buffer.append(String.format(", sono '''%s'''", nazionalita.toLowerCase()));
        }
        buffer.append(textService.setRef(INFO_NAZIONALITA_PREVISTE));
        buffer.append(" ed il cognome");
        buffer.append(textService.setRef(INFO_ORDINE));
        buffer.append(String.format(" inizia con la lettera '''%s'''.", keyParagrafo.toUpperCase()));

        return buffer.toString();
    }

    public void uploadSottoPagine(String wikiTitle, String attNazPrincipale, String attNazSottoPagina, List<WrapLista> lista) {
        if (uploadTest) {
            appContext.getBean(UploadAttivita.class).test().uploadSottoPagina(wikiTitle, attNazPrincipale, attNazSottoPagina, lista);
        }
        else {
            appContext.getBean(UploadAttivita.class).uploadSottoPagina(wikiTitle, attNazPrincipale, attNazSottoPagina, lista);
        }
    }

    public void uploadSottoSottoPagine(String wikiTitle, String attNazPrincipale, String attNazSottoPagina, String keyParagrafo, List<WrapLista> lista) {
        if (uploadTest) {
            appContext.getBean(UploadAttivita.class).test().uploadSottoSottoPagina(wikiTitle, attNazPrincipale, attNazSottoPagina, keyParagrafo, lista);
        }
        else {
            appContext.getBean(UploadAttivita.class).uploadSottoSottoPagina(wikiTitle, attNazPrincipale, attNazSottoPagina, keyParagrafo, lista);
        }
    }


    protected String correlate() {
        StringBuffer buffer = new StringBuffer();
        String cat = textService.primaMaiuscola(nomeLista);

        buffer.append(wikiUtility.setParagrafo("Voci correlate"));
        buffer.append(String.format("*[[:Categoria:%s]]", cat));
        buffer.append(CAPO);
        buffer.append("*[[Progetto:Biografie/Attività]]");

        return buffer.toString();
    }

    protected String categorie() {
        if (uploadTest) {
            return VUOTA;
        }

        StringBuffer buffer = new StringBuffer();
        String cat = textService.primaMaiuscola(nomeLista);

        if (textService.isValid(nomeSottoPagina)) {
            cat += SLASH + nomeSottoPagina;
        }

        buffer.append(CAPO);
        buffer.append(String.format("*[[Categoria:Bio attività|%s]]", cat));

        return buffer.toString();
    }

    /**
     * Esegue la scrittura di tutte le pagine di nazionalità <br>
     */
    public WResult uploadAll() {
        WResult result = WResult.errato();
        long inizio = System.currentTimeMillis();

        List<String> listaPlurali = attPluraleBackend.findAllForKeySortKey();
        for (String plurale : listaPlurali) {
            upload(plurale);
        }

        fixUploadMinuti(inizio);
        return result;
    }

}
