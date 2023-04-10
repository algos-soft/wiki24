package it.algos.wiki24.backend.upload.moduli;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Apr-2023
 * Time: 09:04
 */
public abstract class UploadModuli extends Upload {

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuli() {
        super.summary = "Fix ordine alfabetico";
        super.uploadTest = true;
    }// end of constructor

    public String leggeTestoPagina() {
        return queryService.legge(wikiTitleModulo);
    }

    public String leggeTestoModulo() {
        String testoPagina = leggeTestoPagina();
        return wikiUtility.estraeTestoModulo(testoPagina);
    }

    public Map<String, String> leggeMappa() {
        String testoModulo = leggeTestoModulo();
        return wikiApiService.getMappaModulo(testoModulo);
    }

    public Map<String, String> getMappaOrdinata() {
        Map<String, String> mappa = leggeMappa();
        return arrayService.sort(mappa);
    }


    public String getWikiTitleUpload() {
        return wikiTitleUpload;
    }

    public String getWikiTitleModulo() {
        return wikiTitleModulo;
    }
    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult uploadOrdinatoSenzaModifiche() {
        String testoPagina = leggeTestoPagina();
        String testoModuloOld = leggeTestoModulo();
        String testoModuloNew = fixTestoModulo();
        String textDaRegistrare = textService.sostituisce(testoPagina, testoModuloOld, testoModuloNew);

        return wikiApiService.scrive(wikiTitleUpload, textDaRegistrare, summary);
    }

    protected String fixTestoModulo() {
        return VUOTA;
    }
    public String fixVirgolaFinale(String testoModulo) {
        testoModulo = textService.levaCoda(testoModulo, CAPO);
        testoModulo = textService.levaCoda(testoModulo, VIRGOLA);
        testoModulo += CAPO;

        return testoModulo;
    }



}
