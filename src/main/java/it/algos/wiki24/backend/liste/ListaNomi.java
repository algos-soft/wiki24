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
     * Costruttore base senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     */
    public ListaNomi() {
    }// end of constructor

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaNomi.class).nascita/morte(nomeGiorno).mappaWrap() <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune properties. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaNomi(String nome) {
        this.nomeLista = nome;
        super.typeLista = AETypeLista.nomi;
        super.typeLink = (AETypeLink) WPref.linkNomi.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
    }// end of constructor


    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        this.nomeLista = textService.primaMaiuscola(nomeLista);
    }

    public ListaNomi typeLink(AETypeLink typeLink) {
        super.typeLink = typeLink;
        return this;
    }
}
