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
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaNomi.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaCognomi(String nomeLista) {
        super(nomeLista);
        super.isIstanzaValidaPatternBuilder = true;
    }// end of constructor not @Autowired and used


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.titoloPagina = wikiUtility.wikiTitleCognomi(nomeLista);
        super.typeLista = AETypeLista.cognomi;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiCognomi.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
    }


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
