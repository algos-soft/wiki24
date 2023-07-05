package it.algos.wiki24.backend.upload.moduloProgettoAncheBot;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nomeincipit.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 30-Jun-2023
 * Time: 08:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloNomiIncipit extends UploadModulo {

    @Autowired
    public NomeIncipitBackend backend;


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiTitleModulo = backend.sorgenteDownload;
        super.wikiTitleUpload = backend.sorgenteDownload;
        super.wikiBackend = backend;
        super.uploadTest = false;
    }


    public UploadModuloNomiIncipit test() {
        super.uploadTest = true;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloIncipitNomi";
        return this;
    }


    @Override
    public WResult esegue() {
        String testoPagina = leggeTestoPagina();
        String testoModuloOld = leggeTestoModulo();
        String testoModuloNew = fixTestoModulo(leggeMappaMongo());
        String newText = textService.sostituisce(testoPagina, testoModuloOld, testoModuloNew);

        return registra(newText);
    }

}
