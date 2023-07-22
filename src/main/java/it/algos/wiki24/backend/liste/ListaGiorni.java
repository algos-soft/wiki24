package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 25-Jul-2022
 * Time: 07:36
 * <p>
 * Lista delle biografie per giorni <br>
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadGiorni con appContext.getBean(ListaGiorni.class).nascita/morte(nomeGiorno).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con nascita/morte(nomeGiorno) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaGiorni extends Lista {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SecoloBackend secoloBackend;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug in compilazione, aggiungo un costruttore senza parametri da NON utilizzare <br>
     */
    public ListaGiorni() {
        super("nomeLista");
    }// end of constructor not @Autowired and not used

    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaGiorni(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used


    protected void fixPreferenze() {
        super.fixPreferenze();

        this.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiGiorniAnni.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ANNO;
    }


    /**
     * Pattern Builder <br>
     */
    public ListaGiorni nascita() {
        super.titoloPagina = wikiUtility.wikiTitleNatiGiorno(nomeLista);
        return (ListaGiorni) super.typeLista(AETypeLista.giornoNascita);
    }

    /**
     * Pattern Builder <br>
     */
    public ListaGiorni morte() {
        super.titoloPagina = wikiUtility.wikiTitleMortiGiorno(nomeLista);
        return (ListaGiorni) super.typeLista(AETypeLista.giornoMorte);
    }


    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappaIn) {
        LinkedHashMap<String, List<WrapLista>> mappaOut = new LinkedHashMap<>();
        List<WrapLista> lista;
        List<String> secoli = secoloBackend.findAllForKeySortOrdine();

        //Ordina le chiavi per secolo
        for (String key : secoli) {
            if (mappaIn.containsKey(key)) {
                lista = mappaIn.get(key);
                mappaOut.put(key, lista);
            }
        }

        //Regola la chiave 'Senza anno specificato'
        if (mappaIn.containsKey(TAG_LISTA_NO_ANNO)) {
            lista = mappaIn.get(TAG_LISTA_NO_ANNO);
            mappaOut.put(TAG_LISTA_NO_ANNO, lista);
        }

        //Ordina per nome all'interno della singola lista
        //        for (String key : mappaOut.keySet()) {
        //            lista = mappaOut.get(key);
        //            lista = lista.stream()
        //                    .sorted(Comparator.comparing(bio -> bio.ordinamento))
        //                    .collect(Collectors.toList());
        //            mappaOut.put(key, lista);
        //        }

        return mappaOut;
    }

}
