package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Apr-2023
 * Time: 10:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloPluraleAttivita extends UploadGiorniAnni {

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloPluraleAttivita() {
        super.wikiTitle = "ModuloPluraleAttivita";
        super.summary = "Fix ordine alfabetico";
        super.uploadTest = true;
    }// end of constructor


    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload() {
        String newText = VUOTA;
        List<AttSingolare> lista =attSingolareBackend.findAll();
        return appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText, summary);
    }


}
