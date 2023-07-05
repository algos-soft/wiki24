package it.algos.wiki24.backend.upload.moduloProgettoSoloAdmin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Apr-2023
 * Time: 20:40
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloLinkAttivita extends UploadModulo {

    @Autowired
    public AttPluraleBackend attPluraleBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloLinkAttivita() {
        super.wikiTitleModulo = PATH_MODULO_LINK + ATT_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloLinkAttivita";
    }// end of constructor

}
