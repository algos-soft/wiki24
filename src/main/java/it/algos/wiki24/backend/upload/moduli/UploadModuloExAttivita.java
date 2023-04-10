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
 * Date: Sat, 01-Apr-2023
 * Time: 14:14
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadModuloExAttivita extends UploadModuli {


    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModuloExAttivita() {
        super.wikiTitleModulo = PATH_MODULO + PATH_EX + ATT_LOWER;
        super.wikiTitleUpload = UPLOAD_TITLE_DEBUG + "ModuloExAttivita";
//        super.summary = "Fix ordine alfabetico";
//        super.uploadTest = true;
    }// end of constructor



    /**
     * Esegue la scrittura della pagina <br>
     */
    @Deprecated
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

        return uploadModuloNew("",newText);
    }


    public String fixTestoModulo() {
        StringBuffer buffer = new StringBuffer();
        Map<String, String> mappa = getMappaOrdinata();
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
