package it.algos.wiki24.backend.packages.parametri.annonato;

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
 * Date: Mon, 01-Jan-2024
 * Time: 09:35
 */
@Service
public class ParAnnoNatoModulo extends ParModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParAnnoNatoModulo() {
        super(ParAnnoNatoEntity.class, ParAnnoNatoList.class, ParAnnoNatoForm.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParAnnoNato;
        super.durataElabora = WPref.elaboraParAnnoNatoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_ANNO_NASCITA;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParAnnoNatoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    public ParAnnoNatoEntity newEntity(long pageId, String wikiTitle, String grezzo) {
        return newEntity(pageId, wikiTitle, grezzo, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParAnnoNatoEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParAnnoNatoEntity newEntityBean = ParAnnoNatoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParAnnoNatoEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String wikiTitle, String grezzo) {
        return elaboraService.fixAnno(wikiTitle, grezzo);
    }

}// end of CrudModulo class
