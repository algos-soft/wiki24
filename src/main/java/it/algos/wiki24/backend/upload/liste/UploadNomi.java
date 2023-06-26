package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.annotation.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 08:08
 * Opzioni:
 * A) Visualizzazione della lista di paragrafi in testa pagina: forceToc oppure noToc -> default WPref.typeTocNomi
 * B) Uso dei paragrafi: sempre
 * C) Titolo del singolo paragrafo: link+numeri, titolo+numeri, titolo senza numeri -> default WPref.linkNomi
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadNomi extends Upload {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NomeBackend nomeBackend;

    /**
     * Costruttore base senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     */
    public UploadNomi() {
        super();
    }// end of constructor

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadNomi(String nome) {
        super(nome);
    }// end of constructor

    @PostConstruct
    protected void postConstruct() {
        super.crudBackend = nomeBackend;

        super.postConstruct();

        super.wikiTitleUpload = wikiUtility.wikiTitleNomi(nomeLista);
        super.typeLista = AETypeLista.nomi;
        super.typeToc = (AETypeToc) WPref.typeTocNomi.getEnumCurrentObj();
        super.typeLink = (AETypeLink) WPref.linkNomi.getEnumCurrentObj();
        super.usaNumeriTitoloParagrafi = WPref.usaNumVociNomi.is();
        //        super.crudBackend = nomeBackend;
    }

    //    protected void fixPreferenzeBackend() {
    //        if (crudBackend != null) {
    //            this.lastUpload = crudBackend.lastUpload;
    //            this.durataUpload = crudBackend.durataUpload;
    //            this.nextUpload = crudBackend.nextUpload;
    //        }
    //    }

    public UploadNomi typeLink(AETypeLink typeLink) {
        super.typeLink = typeLink;
        super.usaNumeriTitoloParagrafi = typeLink != AETypeLink.nessunLink;
        return this;
    }

    public UploadNomi noToc() {
        super.typeToc = AETypeToc.noToc;
        return this;
    }

    public UploadNomi forceToc() {
        super.typeToc = AETypeToc.forceToc;
        return this;
    }

    public UploadNomi siNumVoci() {
        super.usaNumeriTitoloParagrafi = true;
        return this;
    }

    public UploadNomi noNumVoci() {
        super.usaNumeriTitoloParagrafi = false;
        return this;
    }

    public UploadNomi sottoPagina(LinkedHashMap<String, List<WrapLista>> mappa) {
        super.wikiTitleUpload = nomeLista;
        super.mappaWrap = mappa;
        super.isSottopagina = true;
        return this;
    }

    public UploadNomi sottoPagina(List<WrapLista> lista) {
        super.wikiTitleUpload = nomeLista;
        mappaWrap = wikiUtility.creaMappaAlfabetica(lista);
        super.isSottopagina = true;
        return this;
    }

    public UploadNomi test() {
        return test(true);
    }

    public UploadNomi test(boolean uploadTest) {
        if (!isSottopagina) {
            super.wikiTitleUpload = uploadTest ? UPLOAD_TITLE_DEBUG + nomeLista : nomeLista;
        }

        super.uploadTest = uploadTest;
        return this;
    }


    @Override
    protected String incipit() {
        return isSottopagina ? VUOTA : String.format("{{incipit lista nomi|nome=%s}}", nomeLista);
    }


    @Override
    protected void fixMappaWrap() {
        if (!isSottopagina) {
            mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).typeLink(typeLink).mappaWrap();
        }
    }

    @Override
    public String testoBody(Map<String, List<WrapLista>> mappa) {
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

        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
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
                appContext.getBean(UploadNomi.class, sottoPagina).sottoPagina(lista).test(uploadTest).esegue();
            }
            else {
                usaDiv = usaDivBase ? lista.size() > maxDiv : false;
                buffer.append(usaDiv ? "{{Div col}}" + CAPO : VUOTA);
                for (WrapLista wrap : lista) {
                    buffer.append(ASTERISCO);
                    buffer.append(wrap.didascaliaBreve);
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
        buffer.append(String.format("*[[Categoria:Liste di persone per nome|%s]]", cat));

        return buffer.toString();
    }

    /**
     * Esegue la scrittura di tutte le pagine <br>
     */
    public WResult uploadAll() {
        WResult result = WResult.crea();
        long inizio = System.currentTimeMillis();
        String message;

        List<String> listaNomi = nomeBackend.findAllForKeyByNumBio().subList(3, 8);
        for (String nome : listaNomi) {
            result = appContext.getBean(UploadNomi.class, nome).esegue();
        }

        result.fine();

        int minuti = AETypeTime.minuti.durata(result.getInizio());
        message = String.format("Upload di %d pagine di nomi con numBio>%d, in circa %d minuti", listaNomi.size(), WPref.sogliaWikiNomi.getInt(), minuti);
        logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());

        fixUploadMinuti(inizio);
        return result;
    }


}
