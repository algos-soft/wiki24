package it.algos.wiki24.backend.packages.attplurale;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 10-Dec-2023
 * Time: 11:54
 */
@Service
public class AttPluraleModulo extends WikiModulo {

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttPluraleModulo() {
        super(AttPluraleEntity.class, AttPluraleList.class, AttPluraleForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadAttPlu;
        super.durataDownload = WPref.downloadAttPluTime;
        super.unitaMisuraDownload = TypeDurata.secondi;

        super.lastElaborazione = WPref.lastElaboraAttPlu;
        super.durataElaborazione = WPref.elaboraAttPluTime;
        super.unitaMisuraElaborazione = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttPluraleEntity newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA, 0, 0, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param plurale (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttPluraleEntity newEntity(
            String plurale,
            List<String> listaSingolari,
            String paginaLista,
            String linkAttivita,
            int numbio,
            int numSingolari,
            boolean superaSoglia,
            boolean esisteLista) {
        AttPluraleEntity newEntityBean = AttPluraleEntity.builder()
                .plurale(textService.isValid(plurale) ? plurale : null)
                .listaSingolari(listaSingolari)
                .paginaLista(paginaLista)
                .linkAttivita(linkAttivita)
                .numBio(numbio)
                .numSingolari(numSingolari)
                .superaSoglia(superaSoglia)
                .esisteLista(esisteLista)
                .build();

        return (AttPluraleEntity) fixKey(newEntityBean);
    }


    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }


    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Link attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività plurali <br>
     */
    public void download() {
        inizio = System.currentTimeMillis();

        creaTavolaDistinct();
        //        downloadAttivitaExtra(moduloEx);

        super.fixDownload(inizio);
    }


    public void creaTavolaDistinct() {
        String moduloLink = TAG_MODULO + "Link attività";
        List<String> listaDistintiPlurali = null;
        AttPluraleEntity newBean;
        List<String> listaSingolari;

        //        attSingolareModulo.download();
        listaDistintiPlurali = attSingolareModulo.findPluraliByDistinct();

        if (listaDistintiPlurali != null && listaDistintiPlurali.size() > 0) {
            deleteAll();
            for (String key : listaDistintiPlurali) {
                listaSingolari = attSingolareModulo.findSingolariByPlurale(key);
                newBean = newEntity(key, listaSingolari, "Arcieri", "Calcio a 5", 0, 0, false, false);
                insertSave(newBean);
            }
        }

        //        downloadAttivitaExtra(moduloEx);

    }

}// end of CrudModulo class
