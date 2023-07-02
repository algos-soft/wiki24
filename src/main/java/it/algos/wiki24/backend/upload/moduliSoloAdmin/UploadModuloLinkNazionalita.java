package it.algos.wiki24.backend.upload.moduliSoloAdmin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Apr-2023
 * Time: 16:43
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloLinkNazionalita extends UploadModulo {

    @Autowired
    public NazPluraleBackend nazPluraleBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloLinkNazionalita() {
        super.wikiTitleModulo = PATH_MODULO_LINK + NAZ_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloLinkNazionalita";
    }// end of constructor

}
