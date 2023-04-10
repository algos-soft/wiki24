package it.algos.wiki24.backend.upload.moduli;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Apr-2023
 * Time: 20:40
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class  UploadModuloLinkAttivita extends UploadModuli {

        @Autowired
        public AttPluraleBackend attPluraleBackend;

        /**
         * Costruttore base con parametri <br>
         * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
         * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
         * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
         */
        public UploadModuloLinkAttivita() {
            super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloLinkAttivita";
//            super.summary = "Fix ordine alfabetico";
//            super.uploadTest = true;
        }// end of constructor

//        public UploadModuloLinkAttivita result(WResult result) {
//            super.result = result;
//            super.result.setFine(0);
//            return this;
//        }

        /**
         * Esegue la scrittura della pagina <br>
         */
        public WResult upload() {
            StringBuffer buffer = new StringBuffer();
            String newText = VUOTA;
            String riga;
            List<AttPlurale> lista = attPluraleBackend.findAll();

            if (lista != null && lista.size() > 0) {
                buffer.append("-- Traduzione in lua del [[Template:Bio/plurale attività]]");
                buffer.append(CAPO);
                buffer.append("return");
                buffer.append(SPAZIO);
                buffer.append(GRAFFA_INI);
                buffer.append(CAPO);

                for (AttPlurale attivita : lista) {
                    riga = String.format("%s%s%s%s%s%s%s%s%s%s", QUADRA_INI, APICETTI, attivita.nome, APICETTI, QUADRA_END, UGUALE_SPAZIATO, APICETTI, attivita.linkAttivita, APICETTI, VIRGOLA);
                    buffer.append(riga);
                    buffer.append(CAPO);
                }
                newText = buffer.toString();
                newText = textService.levaCoda(newText, CAPO);
                newText = textService.levaCoda(newText, VIRGOLA);
                newText += CAPO;
                newText += GRAFFA_END;
            }

            return uploadModuloNew("",newText);
        }

    }
