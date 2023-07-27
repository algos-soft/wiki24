package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.upload.*;
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
public class UploadCognomi extends Upload {

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
        super.istanzaValida = true;
    }


    @Override
    protected String incipit() {
        return isSottopagina ? VUOTA : String.format("{{incipit cognomi|cognome=%s}}", nomeLista);
    }


    @Override
    public boolean fixMappaWrap() {
        if (!istanzaValida) {
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


    @Override
    public String creaBody() {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista;
        int numVoci;
        int max = WPref.sogliaSottoPagina.getInt();
        int maxDiv = WPref.sogliaDiv.getInt();
        boolean usaDivBase = WPref.usaDivAttNaz.is();
        boolean usaDiv;
        String titoloParagrafoLink;
        String vedi;
        String sottoPagina;

        for (String keyParagrafo : mappaWrap.keySet()) {
            lista = mappaWrap.get(keyParagrafo);
            numVoci = lista.size();
            titoloParagrafoLink = lista.get(0).titoloParagrafoLink;
            if (isSottopagina) {
                buffer.append(wikiUtility.fixTitoloLink(keyParagrafo, titoloParagrafoLink, usaNumeriTitoloParagrafi ? numVoci : 0));
            }
            else {
                buffer.append(wikiUtility.fixTitoloLink(keyParagrafo, titoloParagrafoLink, usaNumeriTitoloParagrafi ? numVoci : 0));
            }

            if (numVoci > max && !isSottopagina) {
                sottoPagina = String.format("%s%s%s", wikiTitleUpload, SLASH, keyParagrafo);
                vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                buffer.append(vedi + CAPO);
                appContext.getBean(UploadCognomi.class, sottoPagina).test(uploadTest).sottoPagina(lista).upload();
            }
            else {
                usaDiv = usaDivBase ? lista.size() > maxDiv : false;
                buffer.append(usaDiv ? "{{Div col}}" + CAPO : VUOTA);
                for (WrapLista wrap : lista) {
                    buffer.append(ASTERISCO);
                    buffer.append(wrap.lista);
                    buffer.append(CAPO);
                }
                buffer.append(usaDiv ? "{{Div col end}}" + CAPO : VUOTA);
            }
        }

        return buffer.toString().trim();
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
        buffer.append(String.format("*[[Categoria:Liste di persone per cognome|%s]]", cat));

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
