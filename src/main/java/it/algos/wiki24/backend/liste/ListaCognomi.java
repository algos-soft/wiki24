package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 17-Oct-2022
 * Time: 19:07
 * Lista delle biografie per cognomi <br>
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadCognomi con appContext.getBean(ListaCognomi.class).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con mappaWrap() <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaCognomi extends Lista {


    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug in compilazione, aggiungo un costruttore senza parametri da NON utilizzare <br>
     */
    public ListaCognomi() {
    }// end of constructor not @Autowired and not used

    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeGiorno) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaCognomi(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used



    protected void fixPreferenze() {
        super.fixPreferenze();

        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.typeLista = AETypeLista.cognomi;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiCognomi.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
    }



//    /**
//     * Costruttore base senza parametri <br>
//     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
//     * Uso: getBean(ListaGiorni.class).nascita/morte(nomeGiorno).mappaWrap() <br>
//     * Non rimanda al costruttore della superclasse. Regola qui solo alcune properties. <br>
//     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
//     */
//    public ListaCognomi(String cognome) {
//        this.nomeLista = cognome;
//        super.typeLista = AETypeLista.cognomi;
//        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiCognomi.getEnumCurrentObj();
//        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
//    }// end of constructor

//    /**
//     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
//     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
//     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
//     * <p>
//     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
//     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
//     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
//     */
//    @PostConstruct
//    protected void postConstruct() {
//        this.nomeLista = textService.primaMaiuscola(nomeLista);
//    }

//    public ListaCognomi typeLinkParagrafi(AETypeLink typeLinkParagrafi) {
//        super.typeLinkParagrafi = typeLinkParagrafi;
//        return this;
//    }

    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        LinkedHashMap<String, List<WrapLista>> mappaOut;
        List<WrapLista> lista;

        //Ordina le chiavi
        mappaOut = wikiUtility.sort(mappa);

        //Ordina per nome all'interno della singola lista
        for (String key : mappaOut.keySet()) {
            lista = mappaOut.get(key);
            lista = lista.stream()
                    .sorted(Comparator.comparing(bio -> bio.ordinamento))
                    .collect(Collectors.toList());
            mappaOut.put(key, lista);
        }

        return mappaOut;
    }

}
