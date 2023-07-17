package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 17-Jul-2022
 * Time: 06:37
 * <p>
 * Lista delle biografie per anni <br>
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadAnni con appContext.getBean(ListaAnni.class).nascita/morte(nomeAnno).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con nascita/morte(nomeAnno) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaAnni extends Lista {


    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug in compilazione, aggiungo un costruttore senza parametri da NON utilizzare <br>
     */
    public ListaAnni() {
    }// end of constructor not @Autowired and not used



    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeGiorno) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAnni(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used



    protected void fixPreferenze() {
        super.fixPreferenze();

        super.paragrafoAltre = TAG_LISTA_NO_GIORNO;
    }

    /**
     * Pattern Builder <br>
     */
    public ListaAnni nascita() {
        return (ListaAnni) super.typeLista(AETypeLista.annoNascita);
    }

    /**
     * Pattern Builder <br>
     */
    public ListaAnni morte() {
        return (ListaAnni) super.typeLista(AETypeLista.annoMorte);
    }


    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        return mappa;
    }

}

