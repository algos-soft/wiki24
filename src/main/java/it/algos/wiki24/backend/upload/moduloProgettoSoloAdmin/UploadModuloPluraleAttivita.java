package it.algos.wiki24.backend.upload.moduloProgettoSoloAdmin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Apr-2023
 * Time: 10:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloPluraleAttivita extends UploadModulo {


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloPluraleAttivita() {
        super.wikiTitleModulo =   PATH_MODULO_PLURALE + ATT_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloPluraleAttivita";
    }// end of constructor

}

