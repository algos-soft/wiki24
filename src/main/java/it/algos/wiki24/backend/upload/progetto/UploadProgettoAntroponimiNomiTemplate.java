package it.algos.wiki24.backend.upload.progetto;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nomitemplate.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 20-Jun-2023
 * Time: 07:10
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadProgettoAntroponimiNomiTemplate extends UploadProgetto {

    @Autowired
    private NomeTemplateBackend backend;


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadProgettoAntroponimiNomiTemplate() {
        super.wikiTitleModulo = TAG_INCIPIT_NOMI;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "IncipitNomi";
    }// end of constructor

    /**
     * Esegue la scrittura della pagina di test ordinata dopo le modifiche apportate <br>
     */
    public WResult uploadOrdinatoConModifiche() {
        String testoPaginaAll = leggeTestoPagina();
        String testoCoreOld = getTestoSignificativo(testoPaginaAll);
        String testoCoreNew = fixTestoModulo();
        String textDaRegistrare = textService.sostituisce(testoPaginaAll, testoCoreOld, testoCoreNew);

        return wikiApiService.scrive(wikiTitleUpload, textDaRegistrare, summary).typeResult(AETypeResult.uploadValido);
    }

    public String getTestoSignificativo(String testoPaginaAll) {
        String testoCore = super.getTestoSignificativo(testoPaginaAll);
        String tag = ASTERISCO;

        testoCore = textService.levaTestoPrimaDi(testoCore, tag);
        return testoCore;
    }

    public String fixTestoModulo() {
        StringBuffer buffer = new StringBuffer();
        List<String> lista;

        lista = backend.findAllForKeySortKey();
        for (String key : lista) {
            buffer.append(ASTERISCO);
            buffer.append(key);
            buffer.append(CAPO);
        }

        return buffer.toString().trim();
    }


}

