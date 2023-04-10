package it.algos.wiki24.backend.upload.moduli;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Apr-2023
 * Time: 09:11
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloPluraleNazionalita extends UploadModuli {


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloPluraleNazionalita() {
        super.wikiTitleModulo = PATH_MODULO + PATH_PLURALE + NAZ_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloPluraleNazionalita";
    }// end of constructor


    public String fixTestoModulo() {
        StringBuffer buffer = new StringBuffer();
        Map<String, String> mappa = getMappaOrdinata();
        String key;
        String value;

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                key = textService.setApicetti(entry.getKey());
                value = textService.setApicetti(entry.getValue());

                buffer.append(textService.setQuadre(key));
                buffer.append(UGUALE_SPAZIATO);
                buffer.append(value);
                buffer.append(VIRGOLA);
                buffer.append(CAPO);
            }
        }

        return fixVirgolaFinale(buffer.toString());
    }

}

