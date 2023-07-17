package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 08:14
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadNomi con appContext.getBean(ListaNomi.class).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con mappaWrap() <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaNomi extends Lista {

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug in compilazione, aggiungo un costruttore senza parametri da NON utilizzare <br>
     */
    public ListaNomi() {
    }// end of constructor not @Autowired and not used

    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeGiorno) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaNomi(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used

    //    /**
    //     * Costruttore base con parametri <br>
    //     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
    //     * Uso: getBean(ListaNomi.class).nascita/morte(nomeGiorno).mappaWrap() <br>
    //     * Non rimanda al costruttore della superclasse. Regola qui solo alcune properties. <br>
    //     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
    //     */
    //    public ListaNomi(String nome) {
    //        this.nomeLista = nome;
    //        super.typeLista = AETypeLista.nomi;
    //        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
    //        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
    //    }// end of constructor


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.typeLista = AETypeLista.nomi;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
    }
//
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

//    public ListaNomi typeLinkParagrafi(AETypeLink typeLinkParagrafi) {
//        super.typeLinkParagrafi = typeLinkParagrafi;
//        return this;
//    }


}
