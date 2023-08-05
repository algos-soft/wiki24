package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 18-Oct-2022
 * Time: 14:13
 * Classe specializzata per caricare (upload) le liste di cognomi sul server wiki. <br>
 * Usata fondamentalmente da CognomiView con appContext.getBean(UploadCognomi.class).upload(cognomeTxt) <br>
 * <p>
 * Necessita del login come bot <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadCognomi extends UploadListe {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public CognomeBackend cognomeBackend;


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadCognomi(String nomeLista) {
        super(nomeLista);
    }// end of constructor


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiBackend = cognomeBackend;
        super.wikiTitleUpload = wikiUtility.wikiTitleCognomi(nomeLista);
        super.summary = "[[Utente:Biobot/cognomiBio|cognomiBio]]";
        super.typeLista = AETypeLista.cognomi;
        super.typeToc = (AETypeToc) WPref.typeTocCognomi.getEnumCurrentObj();
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiCognomi.getEnumCurrentObj();
        super.usaNumeriTitoloParagrafi = WPref.usaNumVociCognomi.is();
        super.patternCompleto = true;
    }



    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadCognomi typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case cognomi -> {
                super.patternCompleto = true;
                yield (UploadCognomi) super.typeLista(typeLista);
            }
            default -> this;
        };
    }
    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadCognomi test() {
        return (UploadCognomi) super.test();
    }

    @Override
    protected String incipit() {
        return isSottopagina ? VUOTA : String.format("{{incipit cognomi|cognome=%s}}", nomeLista);
    }


    @Override
    public boolean fixMappaWrap() {
        if (!patternCompleto) {
            return false;
        }

        if (!isSottopagina) {
            if (mappaWrap == null) {
                mappaWrap = appContext
                        .getBean(ListaCognomi.class, nomeLista)
                        .typeLinkParagrafi(typeLinkParagrafi)
                        .typeLinkCrono(typeLinkCrono)
                        .icona(usaIcona)
                        .mappaWrap();
            }
        }

        return true;
    }



    protected String portale() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{Portale|antroponimi}}");

        return buffer.toString();
    }

    protected String categorie() {
        StringBuffer buffer = new StringBuffer();
        String cat = textService.primaMaiuscola(nomeLista);

        if (textService.isValid(nomeSottoPagina)) {
            cat += SLASH + nomeSottoPagina;
        }

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(typeLista.getCategoria());
        buffer.append("|");
        buffer.append(cat);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    /**
     * Esegue la scrittura di tutte le pagine di nazionalità <br>
     */
    public WResult uploadAll() {
        WResult result = WResult.errato();
        long inizio = System.currentTimeMillis();

        List<String> listaPlurali = cognomeBackend.findCognomiStampabiliSortNumBio();
        for (String plurale : listaPlurali) {
            //            upload(plurale);
        }

        fixUploadMinuti(inizio);
        return result;
    }

}
