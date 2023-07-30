package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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
public class UploadNomi extends UploadListe {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NomeBackend nomeBackend;


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadNomi.class, nomeLista).esegue() <br>
     * La superclasse usa il metodo @PostConstruct postConstruct() per proseguire dopo l'init del costruttore <br>
     */
    public UploadNomi(String nomeLista) {
        super(nomeLista);
    }// end of constructor


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiBackend = nomeBackend;
        super.wikiTitleUpload = wikiUtility.wikiTitleNomi(nomeLista);
        super.collectionName = "nome";
        super.summary = "[[Utente:Biobot/nomiBio|nomiBio]]";
        super.typeLista = AETypeLista.nomi;
        super.typeToc = (AETypeToc) WPref.typeTocNomi.getEnumCurrentObj();
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
        super.usaNumeriTitoloParagrafi = WPref.usaNumVociNomi.is();
        super.patternCompleto = true;

        super.sogliaSottopagina = WPref.sogliaSottoPagina.getInt();
        super.sogliaDiv = WPref.sogliaDiv.getInt();
        super.usaDiv = WPref.usaDivAttNaz.is();
    }


    /**
     * Pattern Builder <br>
     */
    public UploadNomi typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case nomi -> {
                super.patternCompleto = true;
                yield (UploadNomi) super.typeLista(typeLista);
            }
            default -> this;
        };
    }

    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadNomi test() {
        return (UploadNomi) super.test();
    }

    @Override
    protected String incipit() {
        return isSottopagina ? VUOTA : String.format("{{incipit nomi|nome=%s}}", nomeLista);
    }


    @Override
    public boolean fixMappaWrap() {
        if (!patternCompleto) {
            return false;
        }

        if (!isSottopagina) {
            if (mappaWrap == null) {
                mappaWrap = appContext
                        .getBean(ListaNomi.class, nomeLista)
                        .typeLinkParagrafi(typeLinkParagrafi)
                        .typeLinkCrono(typeLinkCrono)
                        .icona(usaIcona)
                        .mappaWrap();
            }
        }

        return true;
    }

    protected WResult vediSottoPagina(String sottoPagina, List<WrapLista> lista) {
        return appContext.getBean(UploadNomi.class, sottoPagina).test(uploadTest).sottoPagina(lista).upload();
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
