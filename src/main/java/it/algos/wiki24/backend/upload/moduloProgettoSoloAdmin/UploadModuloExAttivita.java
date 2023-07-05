package it.algos.wiki24.backend.upload.moduloProgettoSoloAdmin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.upload.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Apr-2023
 * Time: 14:14
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloExAttivita extends UploadModulo {


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloExAttivita() {
        super.wikiTitleModulo = PATH_MODULO + PATH_EX + ATT_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloExAttivita";
    }// end of constructor


    public String fixTestoModulo(Map<String, String> mappa) {
        StringBuffer buffer = new StringBuffer();
        String value;

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                value = textService.setApicetti(entry.getValue());
                buffer.append(value);
                buffer.append(VIRGOLA);
                buffer.append(CAPO);
            }
        }

        return fixVirgolaFinale(buffer.toString());
    }

}
