package it.algos.wiki24.backend.upload.progettoAncheBot;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nomedoppio.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 19-Jun-2023
 * Time: 07:51
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadProgettoNomiDoppi extends UploadProgetto {

    @Autowired
    NomeDoppioBackend backend;


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiTitleModulo = backend.sorgenteDownload;
        super.wikiTitleUpload = backend.sorgenteDownload;
        super.wikiBackend = backend;
        super.uploadTest = false;
    }

    public UploadProgettoNomiDoppi test() {
        super.uploadTest = true;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + DOPPI;
        return this;
    }

    /**
     * Esegue la scrittura della pagina di test ordinata dopo le modifiche apportate <br>
     */
    public WResult esegue() {
        String testoPaginaAll = super.leggeTestoPagina();
        String testoCoreOld = backend.getCore();
        String testoCoreNew = this.fixTestoModulo();
        String newText = textService.sostituisce(testoPaginaAll, testoCoreOld, testoCoreNew);

        return registra(newText);
//        return wikiApiService.scrive(wikiTitleUpload, newText, summary).typeResult(AETypeResult.uploadValido);
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
