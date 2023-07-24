package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
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
public class UploadNomi extends UploadListe  {

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
        super.summary = "[[Utente:Biobot/nomiBio|nomiBio]]";
        super.typeLista = AETypeLista.nomi;
        super.typeToc = (AETypeToc) WPref.typeTocNomi.getEnumCurrentObj();
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
        super.usaNumeriTitoloParagrafi = WPref.usaNumVociNomi.is();
        super.isIstanzaValidaPatternBuilder = true;
    }

    @Override
    public boolean isValida() {
        return isIstanzaValidaPatternBuilder;
    }


    @Override
    protected String incipit() {
        return isSottopagina ? VUOTA : String.format("{{incipit nomi|nome=%s}}", nomeLista);
    }


    @Override
    protected void fixMappaWrap() {
        if (!isSottopagina) {
            mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).typeLinkParagrafi(typeLinkParagrafi).mappaWrap();
        }


        mappaWrap = appContext
                .getBean(ListaNomi.class, nomeLista)
                .typeLinkParagrafi(typeLinkParagrafi)
                .typeLinkCrono(AETypeLink.linkVoce)
                .icona(false)
                .mappaWrap();
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
        WResult result = null;
        long inizio = System.currentTimeMillis();
        String message;
        int pagineCreate = 0;
        int pagineModificate = 0;
        int pagineEsistenti = 0;
        int pagineControllate = 0;

        List<String> listaNomi = nomeBackend.findAllForKeyByNumBio();
        for (String nome : listaNomi) {
            result = appContext.getBean(UploadNomi.class, nome).esegue();

            switch (result.getTypeResult()) {
                case queryWriteCreata -> pagineCreate++;
                case queryWriteModificata -> pagineModificate++;
                case queryWriteEsistente -> pagineEsistenti++;
                default -> pagineControllate++;
            }

            if (Pref.debug.is()) {
                if (result.isValido()) {
                    if (result.isModificata()) {
                        message = String.format("Upload della singola pagina%s [%s%s]", FORWARD, PATH_NOMI, nome);
                        logService.info(new WrapLog().message(message).type(AETypeLog.upload));
                    }
                    else {
                        message = String.format("La pagina: [%s%s] esisteva già e non è stata modificata", PATH_NOMI, nome);
                        logService.info(new WrapLog().message(message).type(AETypeLog.upload));
                    }
                }
                else {
                    message = String.format("Non sono riuscito a caricare su wiki la pagina: [%s%s]", PATH_NOMI, nome);
                    logService.error(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload).usaDb());
                }
            }
        }
        result.fine();

        logService.info(new WrapLog().message(String.format("Create %d", pagineCreate)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Modificate %d", pagineModificate)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Esistenti %d", pagineEsistenti)).type(AETypeLog.upload));
        logService.info(new WrapLog().message(String.format("Controllate %d", pagineControllate)).type(AETypeLog.upload));

        message = String.format("Upload di %d pagine di nomi con numBio>%d.", listaNomi.size(), WPref.sogliaWikiNomi.getInt());
        message += String.format(" Nuove=%s - Modificate=%s - Esistenti=%s - Controllate=%s.", pagineCreate, pagineModificate, pagineEsistenti, pagineControllate);
        message += String.format(" %s", AETypeTime.minuti.message(result));
        logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());

        fixUploadMinuti(inizio);
        return result;
    }


}
