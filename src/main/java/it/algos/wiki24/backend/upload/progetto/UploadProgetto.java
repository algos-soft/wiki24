package it.algos.wiki24.backend.upload.progetto;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomidoppi.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 19-Jun-2023
 * Time: 07:51
 */
public abstract class UploadProgetto extends Upload {

    protected WikiBackend backend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadProgetto(WikiBackend backend) {
        super.summary = "Aggiunte/modifiche e fix ordine alfabetico";
        super.uploadTest = true;
        this.backend = backend;
    }// end of constructor

    @PostConstruct
    private void postConstruct() {
        super.wikiTitleModulo = backend.sorgenteDownload;
        super.wikiTitleUpload = backend.uploadTest;
    }


    public String leggeTestoPagina() {
        return queryService.legge(wikiTitleModulo);
    }

    /**
     * Esegue la scrittura della pagina di test ordinata senza modifiche (testo originario solo riordinato) <br>
     */
    public WResult uploadOrdinatoSenzaModifiche() {
        return null;
    }

    /**
     * Esegue la scrittura della pagina di test ordinata dopo le modifiche apportate <br>
     */
    public WResult uploadOrdinatoConModifiche() {
        return null;
    }

    public String getTestoSignificativo(String testoPaginaAll) {
        String testoSenzaCategorie = textService.levaCodaDaPrimo(testoPaginaAll, "[[Categoria").trim();
        return testoSenzaCategorie;
    }

}
