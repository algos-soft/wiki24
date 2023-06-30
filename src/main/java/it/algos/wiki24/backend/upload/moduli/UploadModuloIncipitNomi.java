package it.algos.wiki24.backend.upload.moduli;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nomemodulo.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 30-Jun-2023
 * Time: 08:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloIncipitNomi extends UploadModuli {

    @Autowired
    public NomeModuloBackend nomeModuloBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloIncipitNomi() {
        super.wikiTitleModulo = "Modulo:Incipit nomi";
        super.wikiTitleUpload = wikiTitleModulo;
        super.uploadTest = false;
    }// end of constructor

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.wikiBackend = nomeModuloBackend;
    }

    public UploadModuloIncipitNomi test() {
        super.uploadTest = true;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloIncipitNomi";
        return this;
    }


    @Override
    public WResult esegue() {
        String testoPagina = leggeTestoPagina();
        String testoModuloOld = leggeTestoModulo();
        String testoModuloNew = fixTestoModulo(leggeMappaMongo());
        String textDaRegistrare = textService.sostituisce(testoPagina, testoModuloOld, testoModuloNew);

        return registra(textDaRegistrare);
    }

}