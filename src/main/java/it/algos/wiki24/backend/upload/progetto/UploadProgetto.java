package it.algos.wiki24.backend.upload.progetto;

import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 19-Jun-2023
 * Time: 07:51
 */
public abstract class UploadProgetto extends Upload {


//    public UploadProgetto() {
//        super();
//    }// end of constructor

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadProgetto.class) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadProgetto() {
    }// end of constructor

    @PostConstruct
    protected void postConstruct() {
//        super.wikiTitleModulo = backend.sorgenteDownload;
//        super.wikiTitleUpload = backend.uploadTestName;
        super.postConstruct();
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.summary = "Aggiunte/modifiche e fix ordine alfabetico";
        super.uploadTest = true;
//        super.wikiTitleModulo = backend.sorgenteDownload;
//        super.wikiTitleUpload = backend.uploadTestName;
//        super.crudBackend = backend;
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

    public WResult esegue() {
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
