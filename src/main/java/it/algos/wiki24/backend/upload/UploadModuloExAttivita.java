package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
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
public class UploadModuloExAttivita extends Upload {

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloExAttivita() {
        super.wikiTitle = UPLOAD_TITLE_DEBUG + "ModuloExAttivita";
        super.summary = "Fix ordine alfabeticox";
        super.uploadTest = true;
    }// end of constructor

    public UploadModuloExAttivita result(WResult result) {
        super.result = result;
        return this;
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload() {
        StringBuffer buffer = new StringBuffer();
        String newText = VUOTA;
        String nome;
        String riga;
        List<AttSingolare> lista = attSingolareBackend.findAllByExSortKey();

        if (lista != null && lista.size() > 0) {
            buffer.append("returnxxxx");
            buffer.append(SPAZIO);
            buffer.append(GRAFFA_INI);
            buffer.append(CAPO);

            for (AttSingolare attivita : lista) {
                nome = textService.levaTesta(attivita.nome, "ex");
                riga = String.format("%s%s%s%s", APICETTI, nome, APICETTI, VIRGOLA);
                buffer.append(riga);
                buffer.append(CAPO);
            }
            newText = buffer.toString();
            newText = textService.levaCoda(newText, CAPO);
            newText = textService.levaCoda(newText, VIRGOLA);
            newText += CAPO;
            newText += GRAFFA_END;
        }

        return uploadModulo(newText);
    }

}
