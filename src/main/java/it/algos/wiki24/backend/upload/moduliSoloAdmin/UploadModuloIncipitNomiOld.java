package it.algos.wiki24.backend.upload.moduliSoloAdmin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nomemodulo.*;
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
 * Date: Tue, 20-Jun-2023
 * Time: 07:10
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloIncipitNomiOld extends UploadProgetto {


    @Autowired
    NomeModuloBackend backend;


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiTitleModulo = backend.sorgenteDownload;
        super.wikiTitleUpload = backend.sorgenteDownload;
        super.wikiBackend = backend;
        super.uploadTest = true;
    }

    public UploadModuloIncipitNomiOld test() {
        super.uploadTest = true;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "IncipitNomi";
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
//        return wikiApiService.scrive(wikiTitleUpload, newText, summary).typeResult(AETypeResult.uploadValido);
        return null;
    }

    /**
     * Esegue la scrittura della pagina di test ordinata dopo le modifiche apportate <br>
     */
    public WResult uploadOrdinatoConModifiche() {
        String testoPaginaAll = leggeTestoPagina();
//        String testoCoreOld = backend.getCore();
        String testoCoreNew = fixTestoModulo();
//        String textDaRegistrare = textService.sostituisce(testoPaginaAll, testoCoreOld, testoCoreNew);

//        return wikiApiService.scrive(wikiTitleUpload, textDaRegistrare, summary).typeResult(AETypeResult.uploadValido);
        return null;
    }

    public String getTestoSignificativo(String testoPaginaAll) {
        String testoCore = super.getTestoSignificativo(testoPaginaAll);
        String tag = ASTERISCO;

        testoCore = textService.levaTestoPrimaDi(testoCore, tag);
        return testoCore;
    }

    public String fixTestoModulo() {
        StringBuffer buffer = new StringBuffer();
        List<NomeModulo> lista;

        lista = wikiBackend.findAllSortKey();
        for (NomeModulo entityBean : lista) {
            buffer.append(PIPE);
            buffer.append(entityBean.nome);
            buffer.append(UGUALE_SEMPLICE);
            buffer.append(entityBean.linkPagina);
            buffer.append(CAPO);
        }

        return buffer.toString().trim();
    }


}

