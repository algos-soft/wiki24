package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
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
     * Costruttore base senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(ListaAnni.class).nascita/morte(nomeAnno).mappaWrap() <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune properties. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAnni() {
    }// end of constructor


    public ListaAnni nascita(final String nomeAnno) {
        this.nomeLista = nomeAnno;
        super.typeLista = AETypeLista.annoNascita;
        return this;
    }

    public ListaAnni morte(final String nomeAnno) {
        this.nomeLista = nomeAnno;
        super.typeLista = AETypeLista.annoMorte;
        return this;
    }

    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        return mappa;
    }

}

