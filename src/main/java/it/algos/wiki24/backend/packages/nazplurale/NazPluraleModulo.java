package it.algos.wiki24.backend.packages.nazplurale;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
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
 * Date: Wed, 13-Dec-2023
 * Time: 08:49
 */
@Service
public class NazPluraleModulo extends WikiModulo {

    @Inject
    private NazSingolareModulo nazSingolareModulo;
    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NazPluraleModulo() {
        super(NazPluraleEntity.class, NazPluraleList.class, NazPluraleForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadNazPlu;
        super.durataDownload = WPref.downloadNazPluTime;
        super.unitaMisuraDownload = TypeDurata.secondi;

        super.lastElaborazione = WPref.lastElaboraNazPlu;
        super.durataElaborazione = WPref.elaboraNazPluTime;
        super.unitaMisuraElaborazione = TypeDurata.minuti;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NazPluraleEntity newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA, 0, 0, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param plurale (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NazPluraleEntity newEntity(
            String plurale,
            List<String> singolari,
            String lista,
            String pagina,
            int bio,
            int numSingolari,
            boolean superaSoglia,
            boolean esisteLista) {
        NazPluraleEntity newEntityBean = NazPluraleEntity.builder()
                .plurale(textService.isValid(plurale) ? plurale : null)
                .singolari(singolari)
                .lista(textService.isValid(lista) ? lista : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .bio(bio)
                .numSingolari(numSingolari)
                .superaSoglia(superaSoglia)
                .esisteLista(esisteLista)
                .build();

        return (NazPluraleEntity) fixKey(newEntityBean);
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

        super.deleteAll();

        nazSingolareModulo.download();
        this.creaTavolaDistinct();

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));

        super.fixDownload(inizio);
    }


    public void creaTavolaDistinct() {
        List<NazSingolareEntity> listaNazSingolariDistinte = nazSingolareModulo.findAllByDistinctPlurale(); ;
        NazPluraleEntity newBean;
        List<String> listaSingolari;

        if (listaNazSingolariDistinte == null || listaNazSingolariDistinte.size() < 1) {
            message = String.format("Non sono riuscito a leggere da mongoDB la collection  %s", "nazsingolare");
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (NazSingolareEntity naz : listaNazSingolariDistinte) {
            listaSingolari = nazSingolareModulo.findSingolariByPlurale(naz);
            newBean = newEntity(naz.plurale, listaSingolari, textService.primaMaiuscola(naz.plurale), textService.primaMaiuscola(naz.pagina), 0, 0, false, false);
            mappaBeans.put(naz.plurale, newBean);
        }
    }

}// end of CrudModulo class
