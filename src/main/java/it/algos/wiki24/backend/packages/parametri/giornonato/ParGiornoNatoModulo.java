package it.algos.wiki24.backend.packages.parametri.giornonato;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.parametri.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Dec-2023
 * Time: 11:56
 */
@Service
public class ParGiornoNatoModulo extends ParModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParGiornoNatoModulo() {
        super(ParGiornoNatoEntity.class, ParGiornoNatoList.class, ParGiornoNatoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParGiornoNato;
        super.durataElabora = WPref.elaboraParGiornoNatoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_GIORNO_NASCITA;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParGiornoNatoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParGiornoNatoEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParGiornoNatoEntity newEntityBean = ParGiornoNatoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParGiornoNatoEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String wikiTitle, String grezzo) {
        return elaboraService.fixGiorno(wikiTitle, grezzo);
    }

}// end of CrudModulo class
