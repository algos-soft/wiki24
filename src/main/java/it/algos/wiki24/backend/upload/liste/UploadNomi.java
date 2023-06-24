package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
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
    }// end of constructor


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadNomi(String nome) {
        super.nomeLista = nome;
        super.titoloLinkParagrafo = VUOTA;
        super.titoloLinkVediAnche = PATH_NOMI;
        this.typeCrono = AETypeLista.nomi;
        super.usaParagrafi = WPref.usaParagrafiAttNaz.is();
        super.typeToc = (AETypeToc) WPref.typeTocNomi.getEnumCurrentObj();
    }// end of constructor


    public UploadNomi noToc() {
        super.typeToc = AETypeToc.noToc;
        return this;
    }

    public UploadNomi forceToc() {
        super.typeToc = AETypeToc.forceToc;
        return this;
    }

    public UploadNomi sottoPagina(String sottotitolo, List<WrapLista> lista) {
        super.wikiTitleUpload = sottotitolo;
        LinkedHashMap<String, List<WrapLista>> mappaWrapSottoPagina = new LinkedHashMap<>();
        mappaWrapSottoPagina.put(sottotitolo, lista);
        mappaWrap = mappaWrapSottoPagina;
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
        else {
            super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + nomeLista + textService.levaTestoPrimaDi(wikiTitleUpload, SLASH);
        }

        super.uploadTest = uploadTest;
        return this;
    }


    @Override
    protected String incipit() {
        return String.format("{{incipit lista nomi|nome=%s}}", nomeLista);
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload() {
        if (textService.isValid(nomeLista)) {
            wikiTitleUpload = wikiUtility.wikiTitleNomi(nomeLista);

            mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();

            if (uploadTest) {
                this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + nomeLista;
            }

            if (textService.isValid(wikiTitleUpload) && mappaWrap != null && mappaWrap.size() > 0) {
                return this.esegueUpload(wikiTitleUpload, mappaWrap);
            }
        }

        return WResult.crea();
    }


    protected WResult esegueUpload(String wikiTitle, LinkedHashMap<String, List<WrapLista>> mappa) {
        StringBuffer buffer = new StringBuffer();
        int numVoci = wikiUtility.getSizeAllWrap(mappa);

        if (numVoci < 1) {
            logger.info(new WrapLog().message(String.format("Non creata la pagina %s perché non ci sono abbastanza voci", wikiTitle, numVoci)));
            return WResult.crea();
        }

        buffer.append(avviso());
        buffer.append(CAPO);
        buffer.append(includeIni());
        buffer.append(fixToc());
        buffer.append(fixUnconnected());
        buffer.append(tmpListaBio(numVoci));
        buffer.append(includeEnd());
        buffer.append(CAPO);
        buffer.append(incipit());
        buffer.append(testoBody(mappa));
        buffer.append(CAPO);
        buffer.append(portale());
        buffer.append(categorie());

        return registra(wikiTitle, buffer.toString().trim());
    }


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
        LinkedHashMap<String, List<WrapLista>> mappaWrapSottoPagina;

        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
            numVoci = lista.size();
            titoloParagrafoLink = lista.get(0).titoloParagrafoLink;
            if (!isSottopagina) {
                buffer.append(wikiUtility.fixTitolo(VUOTA, titoloParagrafoLink, numVoci));
            }

            if (WPref.usaSottoNomi.is() && numVoci > max && !isSottopagina) {
                sottoPagina = String.format("%s%s%s%s", titoloLinkVediAnche, textService.primaMaiuscola(nomeLista), SLASH, keyParagrafo);
                vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                buffer.append(vedi + CAPO);
                //                uploadSottoPagine(parente, nomeLista, keyParagrafo, lista);
                appContext.getBean(UploadNomi.class, nomeLista).sottoPagina(sottoPagina, lista).test(uploadTest).esegue();
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

}
